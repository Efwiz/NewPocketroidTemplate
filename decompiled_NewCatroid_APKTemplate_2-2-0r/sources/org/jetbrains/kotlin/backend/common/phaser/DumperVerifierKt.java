package org.jetbrains.kotlin.backend.common.phaser;

import io.netty.util.internal.StringUtil;
import java.io.File;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.IrValidator;
import org.jetbrains.kotlin.backend.common.IrValidatorConfig;
import org.jetbrains.kotlin.backend.common.IrValidatorKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: DumperVerifier.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a \u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00032\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004\u001at\u0010\u0014\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\u00050\u0001j\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016`\u0006\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\u00162\u0006\u0010\u0017\u001a\u00020\r20\u0010\u0018\u001a,\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0001j\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016`\u0019\u001al\u0010\u001a\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\u00050\u0001j\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016`\u0006\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\u001620\u0010\u0018\u001a,\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0001j\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016`\u0019\u001aj\u0010\u001b\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\u00050\u0001j\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016`\u0006\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\u00162.\u0010\u0018\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\u00050\u0001j\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016`\u0006\u001aT\u0010\u001c\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\u00050\u0001j\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016`\u0006\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\u00162\u0018\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u00020\u00050\u001e\u001a \u0010\u001f\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\"\u001a\f\u0010#\u001a\u00020\"*\u00020\u0002H\u0002\u001a\f\u0010$\u001a\u00020\"*\u00020\u0002H\u0002\"=\u0010\u0000\u001a.\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00050\u0001j\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0004`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\"9\u0010\t\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00050\u0001j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0018\u0010\f\u001a\u00020\r*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f*J\u0010%\u001a\u0004\b\u0000\u0010\u0015\u001a\u0004\b\u0001\u0010\u0016\"\u001c\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00012\u001c\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u0016\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0001¨\u0006&"}, d2 = {"defaultDumper", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/backend/common/phaser/ActionState;", "Lorg/jetbrains/kotlin/ir/IrElement;", "", "", "Lorg/jetbrains/kotlin/backend/common/phaser/Action;", "getDefaultDumper", "()Lkotlin/jvm/functions/Function3;", "validationAction", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getValidationAction", "elementName", "", "getElementName", "(Lorg/jetbrains/kotlin/ir/IrElement;)Ljava/lang/String;", "dumpIrElement", "actionState", "data", "context", "dumpToFile", "Data", "Context", "fileExtension", "dumper", "Lorg/jetbrains/kotlin/backend/common/phaser/Dumper;", "dumpToStdout", "makeDumpAction", "makeVerifyAction", "verifier", "Lkotlin/Function2;", "validationCallback", "fragment", "checkProperties", "", "isDumpNeeded", "isValidationNeeded", "Dumper", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DumperVerifierKt {
    private static final Function3<ActionState, IrElement, Object, Unit> defaultDumper = makeDumpAction(CompilerPhaseKt.plus(dumpToStdout(DumperVerifierKt$defaultDumper$1.INSTANCE), dumpToFile(KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, DumperVerifierKt$defaultDumper$2.INSTANCE)));
    private static final Function3<ActionState, IrElement, CommonBackendContext, Unit> validationAction = makeVerifyAction(DumperVerifierKt$validationAction$1.INSTANCE);

    /* JADX INFO: compiled from: DumperVerifier.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BeforeOrAfter.values().length];
            try {
                iArr[BeforeOrAfter.BEFORE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BeforeOrAfter.AFTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private static final String getElementName(IrElement irElement) {
        if (irElement instanceof IrModuleFragment) {
            String strAsString = ((IrModuleFragment) irElement).getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }
        if (irElement instanceof IrFile) {
            return IrDeclarationsKt.getName((IrFile) irElement);
        }
        return irElement.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isDumpNeeded(ActionState actionState) {
        int i = WhenMappings.$EnumSwitchMapping$0[actionState.getBeforeOrAfter().ordinal()];
        if (i == 1) {
            return actionState.getConfig().shouldDumpStateBefore(actionState.getPhase());
        }
        if (i == 2) {
            return actionState.getConfig().shouldDumpStateAfter(actionState.getPhase());
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isValidationNeeded(ActionState actionState) {
        int i = WhenMappings.$EnumSwitchMapping$0[actionState.getBeforeOrAfter().ordinal()];
        if (i == 1) {
            return actionState.getConfig().shouldValidateStateBefore(actionState.getPhase());
        }
        if (i == 2) {
            return actionState.getConfig().shouldValidateStateAfter(actionState.getPhase());
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final <Data, Context> Function3<ActionState, Data, Context, Unit> makeDumpAction(final Function3<? super ActionState, ? super Data, ? super Context, Unit> dumper) {
        Intrinsics.checkNotNullParameter(dumper, "dumper");
        return new Function3<ActionState, Data, Context, Unit>() { // from class: org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt.makeDumpAction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ActionState actionState, Object obj, Object obj2) {
                invoke2(actionState, obj, obj2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ActionState phaseState, Data data, Context context) {
                Intrinsics.checkNotNullParameter(phaseState, "phaseState");
                if (DumperVerifierKt.isDumpNeeded(phaseState)) {
                    dumper.invoke(phaseState, data, context);
                }
            }
        };
    }

    public static final <Data, Context> Function3<ActionState, Data, Context, Unit> makeVerifyAction(final Function2<? super Context, ? super Data, Unit> verifier) {
        Intrinsics.checkNotNullParameter(verifier, "verifier");
        return new Function3<ActionState, Data, Context, Unit>() { // from class: org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt.makeVerifyAction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ActionState actionState, Object obj, Object obj2) {
                invoke2(actionState, obj, obj2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ActionState phaseState, Data data, Context context) {
                Intrinsics.checkNotNullParameter(phaseState, "phaseState");
                if (DumperVerifierKt.isValidationNeeded(phaseState)) {
                    verifier.invoke(context, data);
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1, types: [T, java.lang.Object] */
    public static final String dumpIrElement(ActionState actionState, IrElement data, Object obj) {
        Intrinsics.checkNotNullParameter(actionState, "actionState");
        Intrinsics.checkNotNullParameter(data, "data");
        String lowerCaseAsciiOnly = CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(actionState.getBeforeOrAfter().name());
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = "";
        final Function1 function1 = Intrinsics.areEqual(System.getProperty("org.jetbrains.kotlin.compiler.ir.dump.strategy"), "KotlinLike") ? DumperVerifierKt$dumpIrElement$dump$1.INSTANCE : DumperVerifierKt$dumpIrElement$dump$2.INSTANCE;
        final String dumpOnlyFqName = actionState.getConfig().getDumpOnlyFqName();
        if (dumpOnlyFqName != null) {
            IrVisitorsKt.acceptVoid(data, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt.dumpIrElement.1
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitAnonymousInitializer */
                public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj2) {
                    visitAnonymousInitializer(irAnonymousInitializer, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
                    IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBlock */
                public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj2) {
                    visitBlock(irBlock, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBlock(IrBlock irBlock) {
                    IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBlock(IrBlock irBlock, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBlockBody */
                public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj2) {
                    visitBlockBody(irBlockBody, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBlockBody(IrBlockBody irBlockBody) {
                    IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBody */
                public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj2) {
                    visitBody(irBody, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBody(IrBody irBody) {
                    IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBody(IrBody irBody, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBranch */
                public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj2) {
                    visitBranch(irBranch, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBranch(IrBranch irBranch) {
                    IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBranch(IrBranch irBranch, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBreak */
                public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj2) {
                    visitBreak(irBreak, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBreak(IrBreak irBreak) {
                    IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBreak(IrBreak irBreak, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBreakContinue */
                public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj2) {
                    visitBreakContinue(irBreakContinue, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBreakContinue(IrBreakContinue irBreakContinue) {
                    IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitCall */
                public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj2) {
                    visitCall(irCall, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCall(IrCall irCall) {
                    IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCall(IrCall irCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj2) {
                    visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCallableReference(IrCallableReference<?> irCallableReference) {
                    IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitCatch */
                public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj2) {
                    visitCatch(irCatch, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCatch(IrCatch irCatch) {
                    IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCatch(IrCatch irCatch, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitClass */
                public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj2) {
                    visitClass(irClass, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                /* JADX INFO: renamed from: visitClass */
                public void mo6112visitClass(IrClass irClass) {
                    IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitClass(IrClass irClass, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitClassReference */
                public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj2) {
                    visitClassReference(irClassReference, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitClassReference(IrClassReference irClassReference) {
                    IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitClassReference(IrClassReference irClassReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitComposite */
                public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj2) {
                    visitComposite(irComposite, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitComposite(IrComposite irComposite) {
                    IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitComposite(IrComposite irComposite, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj2) {
                    visitConst((IrConst<?>) irConst, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConst(IrConst<?> irConst) {
                    IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConst(IrConst<?> irConst, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantArray */
                public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj2) {
                    visitConstantArray(irConstantArray, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantArray(IrConstantArray irConstantArray) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantObject */
                public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj2) {
                    visitConstantObject(irConstantObject, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantObject(IrConstantObject irConstantObject) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantPrimitive */
                public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj2) {
                    visitConstantPrimitive(irConstantPrimitive, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantValue */
                public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj2) {
                    visitConstantValue(irConstantValue, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantValue(IrConstantValue irConstantValue) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstructor */
                public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj2) {
                    visitConstructor(irConstructor, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstructor(IrConstructor irConstructor) {
                    IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstructor(IrConstructor irConstructor, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstructorCall */
                public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj2) {
                    visitConstructorCall(irConstructorCall, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstructorCall(IrConstructorCall irConstructorCall) {
                    IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitContainerExpression */
                public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj2) {
                    visitContainerExpression(irContainerExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitContainerExpression(IrContainerExpression irContainerExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitContinue */
                public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj2) {
                    visitContinue(irContinue, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitContinue(IrContinue irContinue) {
                    IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitContinue(IrContinue irContinue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDeclaration */
                public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj2) {
                    visitDeclaration(irDeclarationBase, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDeclarationReference */
                public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj2) {
                    visitDeclarationReference(irDeclarationReference, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
                    IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDelegatingConstructorCall */
                public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj2) {
                    visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
                    IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDoWhileLoop */
                public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj2) {
                    visitDoWhileLoop(irDoWhileLoop, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
                    IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicExpression */
                public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj2) {
                    visitDynamicExpression(irDynamicExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicMemberExpression */
                public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj2) {
                    visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicOperatorExpression */
                public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj2) {
                    visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitElement */
                public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj2) {
                    visitElement(irElement, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitElement(IrElement irElement, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitElseBranch */
                public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj2) {
                    visitElseBranch(irElseBranch, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitElseBranch(IrElseBranch irElseBranch) {
                    IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitEnumConstructorCall */
                public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj2) {
                    visitEnumConstructorCall(irEnumConstructorCall, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
                    IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitEnumEntry */
                public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj2) {
                    visitEnumEntry(irEnumEntry, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitEnumEntry(IrEnumEntry irEnumEntry) {
                    IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorCallExpression */
                public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj2) {
                    visitErrorCallExpression(irErrorCallExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorDeclaration */
                public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj2) {
                    visitErrorDeclaration(irErrorDeclaration, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorExpression */
                public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj2) {
                    visitErrorExpression(irErrorExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorExpression(IrErrorExpression irErrorExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExpression */
                public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj2) {
                    visitExpression(irExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExpression(IrExpression irExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExpression(IrExpression irExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExpressionBody */
                public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj2) {
                    visitExpressionBody(irExpressionBody, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExpressionBody(IrExpressionBody irExpressionBody) {
                    IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExternalPackageFragment */
                public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj2) {
                    visitExternalPackageFragment(irExternalPackageFragment, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
                    IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitField */
                public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj2) {
                    visitField(irField, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitField(IrField irField) {
                    IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitField(IrField irField, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFieldAccess */
                public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj2) {
                    visitFieldAccess(irFieldAccessExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFile */
                public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj2) {
                    visitFile(irFile, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFile(IrFile irFile) {
                    IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFile(IrFile irFile, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunction */
                public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj2) {
                    visitFunction(irFunction, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunction(IrFunction irFunction) {
                    IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunction(IrFunction irFunction, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionAccess */
                public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj2) {
                    visitFunctionAccess(irFunctionAccessExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionExpression */
                public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj2) {
                    visitFunctionExpression(irFunctionExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionReference */
                public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj2) {
                    visitFunctionReference(irFunctionReference, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionReference(IrFunctionReference irFunctionReference) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetClass */
                public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj2) {
                    visitGetClass(irGetClass, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetClass(IrGetClass irGetClass) {
                    IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetClass(IrGetClass irGetClass, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetEnumValue */
                public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj2) {
                    visitGetEnumValue(irGetEnumValue, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
                    IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetField */
                public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj2) {
                    visitGetField(irGetField, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetField(IrGetField irGetField) {
                    IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetField(IrGetField irGetField, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetObjectValue */
                public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj2) {
                    visitGetObjectValue(irGetObjectValue, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
                    IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetValue */
                public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj2) {
                    visitGetValue(irGetValue, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetValue(IrGetValue irGetValue) {
                    IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetValue(IrGetValue irGetValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitInstanceInitializerCall */
                public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj2) {
                    visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
                    IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLocalDelegatedProperty */
                public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj2) {
                    visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
                    IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
                public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj2) {
                    visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
                    IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLoop */
                public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj2) {
                    visitLoop(irLoop, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLoop(IrLoop irLoop) {
                    IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLoop(IrLoop irLoop, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj2) {
                    visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitModuleFragment */
                public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj2) {
                    visitModuleFragment(irModuleFragment, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitModuleFragment(IrModuleFragment irModuleFragment) {
                    IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitPackageFragment */
                public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj2) {
                    visitPackageFragment(irPackageFragment, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitPackageFragment(IrPackageFragment irPackageFragment) {
                    IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitProperty */
                public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj2) {
                    visitProperty(irProperty, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitProperty(IrProperty irProperty) {
                    IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitProperty(IrProperty irProperty, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitPropertyReference */
                public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj2) {
                    visitPropertyReference(irPropertyReference, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitPropertyReference(IrPropertyReference irPropertyReference) {
                    IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitRawFunctionReference */
                public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj2) {
                    visitRawFunctionReference(irRawFunctionReference, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
                    IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitReturn */
                public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj2) {
                    visitReturn(irReturn, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitReturn(IrReturn irReturn) {
                    IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitReturn(IrReturn irReturn, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitScript */
                public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj2) {
                    visitScript(irScript, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitScript(IrScript irScript) {
                    IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitScript(IrScript irScript, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSetField */
                public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj2) {
                    visitSetField(irSetField, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSetField(IrSetField irSetField) {
                    IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSetField(IrSetField irSetField, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSetValue */
                public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj2) {
                    visitSetValue(irSetValue, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSetValue(IrSetValue irSetValue) {
                    IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSetValue(IrSetValue irSetValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSimpleFunction */
                public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj2) {
                    visitSimpleFunction(irSimpleFunction, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
                    IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSingletonReference */
                public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj2) {
                    visitSingletonReference(irGetSingletonValue, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
                    IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSpreadElement */
                public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj2) {
                    visitSpreadElement(irSpreadElement, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSpreadElement(IrSpreadElement irSpreadElement) {
                    IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitStringConcatenation */
                public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj2) {
                    visitStringConcatenation(irStringConcatenation, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
                    IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSuspendableExpression */
                public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj2) {
                    visitSuspendableExpression(irSuspendableExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSuspensionPoint */
                public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj2) {
                    visitSuspensionPoint(irSuspensionPoint, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
                    IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSyntheticBody */
                public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj2) {
                    visitSyntheticBody(irSyntheticBody, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
                    IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitThrow */
                public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj2) {
                    visitThrow(irThrow, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitThrow(IrThrow irThrow) {
                    IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitThrow(IrThrow irThrow, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTry */
                public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj2) {
                    visitTry(irTry, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTry(IrTry irTry) {
                    IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTry(IrTry irTry, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeAlias */
                public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj2) {
                    visitTypeAlias(irTypeAlias, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeAlias(IrTypeAlias irTypeAlias) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeOperator */
                public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj2) {
                    visitTypeOperator(irTypeOperatorCall, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeParameter */
                public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj2) {
                    visitTypeParameter(irTypeParameter, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeParameter(IrTypeParameter irTypeParameter) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitValueAccess */
                public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj2) {
                    visitValueAccess(irValueAccessExpression, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitValueParameter */
                public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj2) {
                    visitValueParameter(irValueParameter, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitValueParameter(IrValueParameter irValueParameter) {
                    IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitVararg */
                public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj2) {
                    visitVararg(irVararg, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitVararg(IrVararg irVararg) {
                    IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitVararg(IrVararg irVararg, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitVariable */
                public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj2) {
                    visitVariable(irVariable, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitVariable(IrVariable irVariable) {
                    IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitVariable(IrVariable irVariable, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitWhen */
                public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj2) {
                    visitWhen(irWhen, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitWhen(IrWhen irWhen) {
                    IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitWhen(IrWhen irWhen, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitWhileLoop */
                public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj2) {
                    visitWhileLoop(irWhileLoop, (Void) obj2);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitWhileLoop(IrWhileLoop irWhileLoop) {
                    IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                /* JADX INFO: renamed from: visitElement */
                public void mo6335visitElement(IrElement element) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    IrVisitorsKt.acceptChildrenVoid(element, this);
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r4v4, types: [T, java.lang.String] */
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDeclaration(IrDeclarationBase declaration) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    if ((declaration instanceof IrDeclarationWithName) && Intrinsics.areEqual(new FqName(dumpOnlyFqName), IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) declaration))) {
                        objectRef.element += function1.invoke(declaration);
                    } else {
                        IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, declaration);
                    }
                }
            });
        } else {
            dumpOnlyFqName = getElementName(data);
            objectRef.element = function1.invoke(data);
        }
        return ("// --- IR for " + dumpOnlyFqName + StringUtil.SPACE + lowerCaseAsciiOnly + StringUtil.SPACE + actionState.getPhase().getDescription() + '\n') + ((String) objectRef.element);
    }

    public static final <Data, Context> Function3<ActionState, Data, Context, Unit> dumpToFile(final String fileExtension, final Function3<? super ActionState, ? super Data, ? super Context, String> dumper) {
        Intrinsics.checkNotNullParameter(fileExtension, "fileExtension");
        Intrinsics.checkNotNullParameter(dumper, "dumper");
        return new Function3<ActionState, Data, Context, Unit>() { // from class: org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt.dumpToFile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ActionState actionState, Object obj, Object obj2) {
                invoke2(actionState, obj, obj2);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code duplicated, block: B:16:0x005b  */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ActionState actionState, Data data, Context context) {
                String strInvoke;
                String string;
                Intrinsics.checkNotNullParameter(actionState, "actionState");
                String dumpToDirectory = actionState.getConfig().getDumpToDirectory();
                if (dumpToDirectory == null || (strInvoke = dumper.invoke(actionState, data, context)) == null) {
                    return;
                }
                StringBuilder sbAppend = new StringBuilder().append(dumpToDirectory);
                IrModuleFragment irModuleFragment = data instanceof IrModuleFragment ? (IrModuleFragment) data : null;
                if (irModuleFragment != null) {
                    StringBuilder sb = new StringBuilder("/");
                    String strAsString = irModuleFragment.getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    string = sb.append(StringsKt.removeSurrounding(strAsString, (CharSequence) "<", (CharSequence) ">")).toString();
                    if (string == null) {
                        string = "";
                    }
                } else {
                    string = "";
                }
                File file = new File(sbAppend.append(string).toString());
                if (!file.isDirectory() && !file.mkdirs()) {
                    throw new IllegalStateException(("Can't create directory for IR dumps at " + dumpToDirectory).toString());
                }
                String str = String.format("%02d", Arrays.copyOf(new Object[]{Integer.valueOf(actionState.getPhaseCount())}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                FilesKt.writeText$default(new File(file, str + '_' + actionState.getBeforeOrAfter() + '.' + actionState.getPhase().getName() + '.' + (Intrinsics.areEqual(System.getProperty("org.jetbrains.kotlin.compiler.ir.dump.strategy"), "KotlinLike") ? "kt." : "") + fileExtension), strInvoke, null, 2, null);
            }
        };
    }

    public static final <Data, Context> Function3<ActionState, Data, Context, Unit> dumpToStdout(final Function3<? super ActionState, ? super Data, ? super Context, String> dumper) {
        Intrinsics.checkNotNullParameter(dumper, "dumper");
        return new Function3<ActionState, Data, Context, Unit>() { // from class: org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt.dumpToStdout.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ActionState actionState, Object obj, Object obj2) {
                invoke2(actionState, obj, obj2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ActionState actionState, Data data, Context context) {
                String strInvoke;
                Intrinsics.checkNotNullParameter(actionState, "actionState");
                if (actionState.getConfig().getDumpToDirectory() == null && (strInvoke = dumper.invoke(actionState, data, context)) != null) {
                    System.out.println((Object) "\n\n----------------------------------------------");
                    System.out.println((Object) strInvoke);
                    System.out.println();
                }
            }
        };
    }

    public static final Function3<ActionState, IrElement, Object, Unit> getDefaultDumper() {
        return defaultDumper;
    }

    public static /* synthetic */ void validationCallback$default(CommonBackendContext commonBackendContext, IrElement irElement, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        validationCallback(commonBackendContext, irElement, z);
    }

    public static final void validationCallback(CommonBackendContext context, IrElement fragment, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        fragment.accept(new IrValidator(context, new IrValidatorConfig(true, true, false, false, z, false, 32, null)), null);
        IrValidatorKt.checkDeclarationParents(fragment);
    }

    public static final Function3<ActionState, IrElement, CommonBackendContext, Unit> getValidationAction() {
        return validationAction;
    }
}
