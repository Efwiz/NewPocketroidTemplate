package org.jetbrains.kotlin.backend.common.serialization.mangle;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
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
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: ManglerChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000g\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003*\u0001\u000e\u0018\u0000 &2\u00020\u0001:\u0001&B;\u0012\u001e\u0010\u0002\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003\"\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\bH\u0016J\u0010\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016Jn\u0010\u0016\u001a\u0002H\u0017\"\b\b\u0000\u0010\u0018*\u00020\u0019\"\u0004\b\u0001\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00180\u001a2\u0006\u0010\u001b\u001a\u0002H\u00172\u0017\u0010\u001c\u001a\u0013\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00170\u0007¢\u0006\u0002\b\u001d2$\u0010\u001e\u001a \u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u00020\u00110\u001fH\u0002¢\u0006\u0002\u0010 J\u001a\u0010!\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0012\u001a\u00020\u0005H\u0002J\f\u0010\"\u001a\u00020\t*\u00020\u0005H\u0002J\u001a\u0010#\u001a\u00020$*\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0012\u001a\u00020\u0005H\u0002J\u001a\u0010%\u001a\u00020$*\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0012\u001a\u00020\u0005H\u0002R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000f¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ManglerChecker;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "_manglers", "", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "needsChecking", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "", "([Lorg/jetbrains/kotlin/ir/util/KotlinMangler;Lkotlin/jvm/functions/Function1;)V", "manglers", "", "skipper", "org/jetbrains/kotlin/backend/common/serialization/mangle/ManglerChecker$skipper$1", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ManglerChecker$skipper$1;", "visitDeclaration", "", "declaration", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "checkAllEqual", "R", ExifInterface.GPS_DIRECTION_TRUE, "", "", ConfigConstants.CONFIG_INIT_SECTION, "op", "Lkotlin/ExtensionFunctionType;", "onError", "Lkotlin/Function4;", "(Ljava/lang/Iterable;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "isExportCheck", "shouldBeSkipped", "signatureMangle", "", "stringMangle", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ManglerChecker implements IrElementVisitorVoid {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Function1<IrDeclarationBase, Boolean> hasDescriptor = new Function1<IrDeclarationBase, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker$Companion$hasDescriptor$1
        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(IrDeclarationBase it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return Boolean.valueOf(it.getSymbol().getHasDescriptor());
        }
    };
    private final List<KotlinMangler<IrDeclaration>> manglers;
    private final Function1<IrDeclarationBase, Boolean> needsChecking;
    private final ManglerChecker$skipper$1 skipper;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker$skipper$1] */
    public ManglerChecker(KotlinMangler<IrDeclaration>[] _manglers, Function1<? super IrDeclarationBase, Boolean> needsChecking) {
        Intrinsics.checkNotNullParameter(_manglers, "_manglers");
        Intrinsics.checkNotNullParameter(needsChecking, "needsChecking");
        this.needsChecking = needsChecking;
        this.manglers = ArraysKt.toList(_manglers);
        this.skipper = new IrElementVisitor() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker$skipper$1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
            public Boolean visitBlock2(IrBlock irBlock, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
            public Boolean visitBlockBody2(IrBlockBody irBlockBody, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
            public Boolean visitBody2(IrBody irBody, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitBody(this, irBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
            public Boolean visitBranch2(IrBranch irBranch, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
            public Boolean visitBreak2(IrBreak irBreak, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
            public Boolean visitBreakContinue2(IrBreakContinue irBreakContinue, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
            public Boolean visitCall2(IrCall irCall, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitCall(this, irCall, r2);
            }

            public Boolean visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                return visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
            public Boolean visitCatch2(IrCatch irCatch, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
            public Boolean visitClassReference2(IrClassReference irClassReference, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
            public Boolean visitComposite2(IrComposite irComposite, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, r2);
            }

            public Boolean visitConst(IrConst<?> irConst, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitConst(this, irConst, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
                return visitConst((IrConst<?>) irConst, (Void) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
            public Boolean visitConstantArray2(IrConstantArray irConstantArray, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
            public Boolean visitConstantObject2(IrConstantObject irConstantObject, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
            public Boolean visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
            public Boolean visitConstantValue2(IrConstantValue irConstantValue, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
            public Boolean visitConstructor2(IrConstructor irConstructor, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
            public Boolean visitConstructorCall2(IrConstructorCall irConstructorCall, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
            public Boolean visitContainerExpression2(IrContainerExpression irContainerExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
            public Boolean visitContinue2(IrContinue irContinue, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
            public Boolean visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
            public Boolean visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
            public Boolean visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
            public Boolean visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
            public Boolean visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
            public Boolean visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
            public Boolean visitElseBranch2(IrElseBranch irElseBranch, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
            public Boolean visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
            public Boolean visitEnumEntry2(IrEnumEntry irEnumEntry, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
            public Boolean visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
            public Boolean visitErrorExpression2(IrErrorExpression irErrorExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
            public Boolean visitExpression2(IrExpression irExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
            public Boolean visitExpressionBody2(IrExpressionBody irExpressionBody, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
            public Boolean visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
            public Boolean visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
            public Boolean visitFile2(IrFile irFile, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitFile(this, irFile, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
            public Boolean visitFunction2(IrFunction irFunction, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
            public Boolean visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
            public Boolean visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
            public Boolean visitFunctionReference2(IrFunctionReference irFunctionReference, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
            public Boolean visitGetClass2(IrGetClass irGetClass, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
            public Boolean visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
            public Boolean visitGetField2(IrGetField irGetField, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
            public Boolean visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
            public Boolean visitGetValue2(IrGetValue irGetValue, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
            public Boolean visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
            public Boolean visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
            public Boolean visitLoop2(IrLoop irLoop, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, r2);
            }

            public Boolean visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
            public Boolean visitModuleFragment2(IrModuleFragment irModuleFragment, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
            public Boolean visitPackageFragment2(IrPackageFragment irPackageFragment, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
            public Boolean visitPropertyReference2(IrPropertyReference irPropertyReference, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
            public Boolean visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
            public Boolean visitReturn2(IrReturn irReturn, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
            public Boolean visitScript2(IrScript irScript, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitScript(this, irScript, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
            public Boolean visitSetField2(IrSetField irSetField, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
            public Boolean visitSetValue2(IrSetValue irSetValue, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
            public Boolean visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
            public Boolean visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
            public Boolean visitSpreadElement2(IrSpreadElement irSpreadElement, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
            public Boolean visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
            public Boolean visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
            public Boolean visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
            public Boolean visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
            public Boolean visitThrow2(IrThrow irThrow, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
            public Boolean visitTry2(IrTry irTry, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitTry(this, irTry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
            public Boolean visitTypeAlias2(IrTypeAlias irTypeAlias, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
            public Boolean visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
            public Boolean visitTypeParameter2(IrTypeParameter irTypeParameter, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
            public Boolean visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
            public Boolean visitVararg2(IrVararg irVararg, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
            public Boolean visitWhen2(IrWhen irWhen, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
            public Boolean visitWhileLoop2(IrWhileLoop irWhileLoop, Void r2) {
                return (Boolean) IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
            public Boolean visitElement2(IrElement element, Void data) {
                Intrinsics.checkNotNullParameter(element, "element");
                throw new IllegalStateException(("unexpected element: " + RenderIrElementKt.render$default(element, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
            public Boolean visitDeclaration2(IrDeclarationBase declaration, Void data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (!((Boolean) this.this$0.needsChecking.invoke(declaration)).booleanValue()) {
                    return true;
                }
                if (declaration.getParent() instanceof IrPackageFragment) {
                    return Boolean.valueOf(DescriptorVisibilities.isPrivate(((IrDeclarationWithVisibility) declaration).getVisibility()));
                }
                return (Boolean) declaration.getParent().accept(this, data);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
            public Boolean visitAnonymousInitializer2(IrAnonymousInitializer declaration, Void data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return true;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
            public Boolean visitValueParameter2(IrValueParameter declaration, Void data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return true;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
            public Boolean visitVariable2(IrVariable declaration, Void data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return true;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
            public Boolean visitErrorDeclaration2(IrErrorDeclaration declaration, Void data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return true;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
            public Boolean visitLocalDelegatedProperty2(IrLocalDelegatedProperty declaration, Void data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return true;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
            public Boolean visitProperty2(IrProperty declaration, Void data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return true;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
            public Boolean visitClass2(IrClass declaration, Void data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return Boolean.valueOf(Intrinsics.areEqual(declaration.getName(), SpecialNames.NO_NAME_PROVIDED) || ((Boolean) IrElementVisitor.DefaultImpls.visitClass(this, declaration, data)).booleanValue());
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
            public Boolean visitField2(IrField declaration, Void data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return Boolean.valueOf(Intrinsics.areEqual(declaration.getOrigin(), IrDeclarationOrigin.DELEGATE.INSTANCE) || ((Boolean) IrElementVisitor.DefaultImpls.visitField(this, declaration, data)).booleanValue());
            }
        };
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
        visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
        visitBlock(irBlock, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
        visitBlockBody(irBlockBody, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
        visitBody(irBody, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
        visitBranch(irBranch, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
        visitBreak(irBreak, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
        visitBreakContinue(irBreakContinue, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
        visitCall(irCall, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
        visitCatch(irCatch, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
        visitClass(irClass, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
        visitClassReference(irClassReference, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
        visitComposite(irComposite, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
        visitConst((IrConst<?>) irConst, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
        visitConstantArray(irConstantArray, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
        visitConstantObject(irConstantObject, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
        visitConstantPrimitive(irConstantPrimitive, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
        visitConstantValue(irConstantValue, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
        visitConstructor(irConstructor, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
        visitConstructorCall(irConstructorCall, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
        visitContainerExpression(irContainerExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
        visitContinue(irContinue, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
        visitDeclaration(irDeclarationBase, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
        visitDeclarationReference(irDeclarationReference, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
        visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
        visitDoWhileLoop(irDoWhileLoop, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
        visitDynamicExpression(irDynamicExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
        visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
        visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
        visitElement(irElement, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitElement(IrElement irElement, Void r2) {
        IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
        visitElseBranch(irElseBranch, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
        visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
        visitEnumEntry(irEnumEntry, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
        visitErrorCallExpression(irErrorCallExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
        visitErrorDeclaration(irErrorDeclaration, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
        visitErrorExpression(irErrorExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
        visitExpression(irExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
        visitExpressionBody(irExpressionBody, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
        visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
        visitField(irField, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
        visitFieldAccess(irFieldAccessExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
        visitFile(irFile, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
        visitFunction(irFunction, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
        visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
        visitFunctionExpression(irFunctionExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
        visitFunctionReference(irFunctionReference, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
        visitGetClass(irGetClass, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
        visitGetEnumValue(irGetEnumValue, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
        visitGetField(irGetField, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
        visitGetObjectValue(irGetObjectValue, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
        visitGetValue(irGetValue, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
        visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
        visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
        visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
        visitLoop(irLoop, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
        visitModuleFragment(irModuleFragment, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
        visitPackageFragment(irPackageFragment, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
        visitProperty(irProperty, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
        visitPropertyReference(irPropertyReference, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
        visitRawFunctionReference(irRawFunctionReference, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
        visitReturn(irReturn, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
        visitScript(irScript, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
        visitSetField(irSetField, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
        visitSetValue(irSetValue, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
        visitSimpleFunction(irSimpleFunction, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
        visitSingletonReference(irGetSingletonValue, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
        visitSpreadElement(irSpreadElement, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
        visitStringConcatenation(irStringConcatenation, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
        visitSuspendableExpression(irSuspendableExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
        visitSuspensionPoint(irSuspensionPoint, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
        visitSyntheticBody(irSyntheticBody, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
        visitThrow(irThrow, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
        visitTry(irTry, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
        visitTypeAlias(irTypeAlias, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
        visitTypeOperator(irTypeOperatorCall, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
        visitTypeParameter(irTypeParameter, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
        visitValueAccess(irValueAccessExpression, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
        visitValueParameter(irValueParameter, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
        visitVararg(irVararg, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
        visitVariable(irVariable, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
        visitWhen(irWhen, (Void) obj);
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
    public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
        visitWhileLoop(irWhileLoop, (Void) obj);
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

    public /* synthetic */ ManglerChecker(KotlinMangler[] kotlinManglerArr, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinManglerArr, (i & 2) != 0 ? hasDescriptor : function1);
    }

    /* JADX INFO: compiled from: ManglerChecker.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ManglerChecker$Companion;", "", "()V", "hasDescriptor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "", "getHasDescriptor", "()Lkotlin/jvm/functions/Function1;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Function1<IrDeclarationBase, Boolean> getHasDescriptor() {
            return ManglerChecker.hasDescriptor;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    /* JADX INFO: renamed from: visitElement */
    public void mo6335visitElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        IrVisitorsKt.acceptChildrenVoid(element, this);
    }

    private final boolean shouldBeSkipped(IrDeclaration irDeclaration) {
        return ((Boolean) irDeclaration.accept(this.skipper, null)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isExportCheck(KotlinMangler<IrDeclaration> kotlinMangler, IrDeclaration irDeclaration) {
        return !shouldBeSkipped(irDeclaration) && kotlinMangler.isExported(irDeclaration, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String stringMangle(KotlinMangler<IrDeclaration> kotlinMangler, IrDeclaration irDeclaration) {
        return kotlinMangler.mangleString(irDeclaration, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String signatureMangle(KotlinMangler<IrDeclaration> kotlinMangler, IrDeclaration irDeclaration) {
        return kotlinMangler.signatureString(irDeclaration, false);
    }

    private final <T, R> R checkAllEqual(Iterable<? extends T> iterable, R r, Function1<? super T, ? extends R> function1, Function4<? super T, ? super R, ? super T, ? super R, Unit> function4) {
        Iterator<? extends T> it = iterable.iterator();
        T next = null;
        while (it.hasNext()) {
            next = it.next();
            if (next == null) {
                r = function1.invoke(next);
            } else {
                R rInvoke = function1.invoke(next);
                if (!Intrinsics.areEqual(r, rInvoke)) {
                    function4.invoke(next, r, next, rInvoke);
                }
                r = rInvoke;
            }
        }
        return r;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDeclaration(final IrDeclarationBase declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof IrErrorDeclaration) && ((Boolean) checkAllEqual(this.manglers, false, new Function1<KotlinMangler<IrDeclaration>, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker$visitDeclaration$exported$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinMangler<IrDeclaration> checkAllEqual) {
                Intrinsics.checkNotNullParameter(checkAllEqual, "$this$checkAllEqual");
                return Boolean.valueOf(this.this$0.isExportCheck(checkAllEqual, declaration));
            }
        }, new Function4<KotlinMangler<IrDeclaration>, Boolean, KotlinMangler<IrDeclaration>, Boolean, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker$visitDeclaration$exported$2
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ Unit invoke(KotlinMangler<IrDeclaration> kotlinMangler, Boolean bool, KotlinMangler<IrDeclaration> kotlinMangler2, Boolean bool2) {
                invoke(kotlinMangler, bool.booleanValue(), kotlinMangler2, bool2.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(KotlinMangler<IrDeclaration> m1, boolean z, KotlinMangler<IrDeclaration> m2, boolean z2) {
                Intrinsics.checkNotNullParameter(m1, "m1");
                Intrinsics.checkNotNullParameter(m2, "m2");
                throw new IllegalStateException(("isExportCheck: " + RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null) + "\n " + m1.getManglerName() + ": " + z + "\n " + m2.getManglerName() + ": " + z2 + '\n').toString());
            }
        })).booleanValue()) {
            checkAllEqual(this.manglers, "", new Function1<KotlinMangler<IrDeclaration>, String>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker.visitDeclaration.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(KotlinMangler<IrDeclaration> checkAllEqual) {
                    Intrinsics.checkNotNullParameter(checkAllEqual, "$this$checkAllEqual");
                    return ManglerChecker.this.stringMangle(checkAllEqual, declaration);
                }
            }, new Function4<KotlinMangler<IrDeclaration>, String, KotlinMangler<IrDeclaration>, String, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker.visitDeclaration.2
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(KotlinMangler<IrDeclaration> kotlinMangler, String str, KotlinMangler<IrDeclaration> kotlinMangler2, String str2) {
                    invoke2(kotlinMangler, str, kotlinMangler2, str2);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(KotlinMangler<IrDeclaration> m1, String r1, KotlinMangler<IrDeclaration> m2, String r2) {
                    Intrinsics.checkNotNullParameter(m1, "m1");
                    Intrinsics.checkNotNullParameter(r1, "r1");
                    Intrinsics.checkNotNullParameter(m2, "m2");
                    Intrinsics.checkNotNullParameter(r2, "r2");
                    throw new IllegalStateException(("FULL: " + RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null) + "\n " + m1.getManglerName() + ": " + r1 + "\n " + m2.getManglerName() + ": " + r2 + '\n').toString());
                }
            });
            checkAllEqual(this.manglers, "", new Function1<KotlinMangler<IrDeclaration>, String>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker.visitDeclaration.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(KotlinMangler<IrDeclaration> checkAllEqual) {
                    Intrinsics.checkNotNullParameter(checkAllEqual, "$this$checkAllEqual");
                    return ManglerChecker.this.signatureMangle(checkAllEqual, declaration);
                }
            }, new Function4<KotlinMangler<IrDeclaration>, String, KotlinMangler<IrDeclaration>, String, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker.visitDeclaration.4
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(KotlinMangler<IrDeclaration> kotlinMangler, String str, KotlinMangler<IrDeclaration> kotlinMangler2, String str2) {
                    invoke2(kotlinMangler, str, kotlinMangler2, str2);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(KotlinMangler<IrDeclaration> m1, String r1, KotlinMangler<IrDeclaration> m2, String r2) {
                    Intrinsics.checkNotNullParameter(m1, "m1");
                    Intrinsics.checkNotNullParameter(r1, "r1");
                    Intrinsics.checkNotNullParameter(m2, "m2");
                    Intrinsics.checkNotNullParameter(r2, "r2");
                    throw new IllegalStateException(("SIG: " + RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null) + "\n " + m1.getManglerName() + ": " + r1 + "\n " + m2.getManglerName() + ": " + r2 + '\n').toString());
                }
            });
            IrVisitorsKt.acceptChildrenVoid(declaration, this);
        }
    }
}
