package org.jetbrains.kotlin.backend.common;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
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
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
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
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;

/* JADX INFO: compiled from: CheckIrElementVisitor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000î\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012:\u0010\u0004\u001a6\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0\u0005j\u0002`\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0018\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u0010\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020%H\u0016J\u0014\u0010&\u001a\u00020\f2\n\u0010!\u001a\u0006\u0012\u0002\b\u00030'H\u0016J\u0010\u0010(\u001a\u00020\f2\u0006\u0010$\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\f2\u0006\u0010!\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\f2\u0006\u0010!\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u0010\u0010/\u001a\u00020\f2\u0006\u0010!\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020\f2\u0006\u0010$\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\f2\u0006\u0010!\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\f2\u0006\u0010!\u001a\u000206H\u0016J\u0010\u00107\u001a\u00020\f2\u0006\u0010!\u001a\u000208H\u0016J\u0010\u00109\u001a\u00020\f2\u0006\u0010!\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020\f2\u0006\u0010!\u001a\u00020<H\u0016J\u0010\u0010=\u001a\u00020\f2\u0006\u0010!\u001a\u00020>H\u0016J\u0010\u0010?\u001a\u00020\f2\u0006\u0010!\u001a\u00020@H\u0016J\u0010\u0010A\u001a\u00020\f2\u0006\u0010B\u001a\u00020CH\u0016J\u0010\u0010D\u001a\u00020\f2\u0006\u0010!\u001a\u00020EH\u0016J\u0010\u0010F\u001a\u00020\f2\u0006\u0010!\u001a\u00020GH\u0016J\u0010\u0010H\u001a\u00020\f2\u0006\u0010!\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020\f2\u0006\u0010!\u001a\u00020KH\u0016J\u0010\u0010L\u001a\u00020\f2\u0006\u0010!\u001a\u00020MH\u0016J\u0010\u0010N\u001a\u00020\f2\u0006\u0010!\u001a\u00020OH\u0016J\u0010\u0010P\u001a\u00020\f2\u0006\u0010!\u001a\u00020QH\u0016J\u0014\u0010R\u001a\u00020\f*\u00020\u00062\u0006\u0010S\u001a\u000202H\u0002J\u0014\u0010T\u001a\u00020\f*\u00020U2\u0006\u0010!\u001a\u000200H\u0002J\f\u0010V\u001a\u00020\f*\u000200H\u0002J\u0014\u0010W\u001a\u00020\f*\u0002002\u0006\u0010X\u001a\u00020\u001cH\u0002J\u001c\u0010Y\u001a\u00020\f*\u0002002\u0006\u0010Z\u001a\u00020\u001c2\u0006\u0010X\u001a\u00020\u001cH\u0002R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014RE\u0010\u0004\u001a6\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0\u0005j\u0002`\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0018j\b\u0012\u0004\u0012\u00020\u0006`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006["}, d2 = {"Lorg/jetbrains/kotlin/backend/common/CheckIrElementVisitor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "reportError", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lkotlin/ParameterName;", "name", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "", CrashHianalyticsData.MESSAGE, "", "Lorg/jetbrains/kotlin/backend/common/ReportError;", "config", "Lorg/jetbrains/kotlin/backend/common/IrValidatorConfig;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lkotlin/jvm/functions/Function2;Lorg/jetbrains/kotlin/backend/common/IrValidatorConfig;)V", "getConfig", "()Lorg/jetbrains/kotlin/backend/common/IrValidatorConfig;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getReportError", "()Lkotlin/jvm/functions/Function2;", "visitedElements", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "checkType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "visitBreakContinue", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "visitCall", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitDeclarationReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitElement", "visitExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "visitLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "checkFunction", "function", "ensureBound", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ensureNullable", "ensureTypeIs", "expectedType", "ensureTypesEqual", "actualType", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckIrElementVisitor implements IrElementVisitorVoid {
    private final IrValidatorConfig config;
    private final IrBuiltIns irBuiltIns;
    private final Function2<IrElement, String, Unit> reportError;
    private final HashSet<IrElement> visitedElements;

    /* JADX INFO: compiled from: CheckIrElementVisitor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IrTypeOperator.values().length];
            try {
                iArr[IrTypeOperator.CAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_NOTNULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_COERCION_TO_UNIT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_INTEGER_COERCION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[IrTypeOperator.SAM_CONVERSION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_DYNAMIC_CAST.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[IrTypeOperator.REINTERPRET_CAST.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[IrTypeOperator.SAFE_CAST.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[IrTypeOperator.INSTANCEOF.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[IrTypeOperator.NOT_INSTANCEOF.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CheckIrElementVisitor(IrBuiltIns irBuiltIns, Function2<? super IrElement, ? super String, Unit> reportError, IrValidatorConfig config) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(reportError, "reportError");
        Intrinsics.checkNotNullParameter(config, "config");
        this.irBuiltIns = irBuiltIns;
        this.reportError = reportError;
        this.config = config;
        this.visitedElements = new HashSet<>();
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

    public final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public final Function2<IrElement, String, Unit> getReportError() {
        return this.reportError;
    }

    public final IrValidatorConfig getConfig() {
        return this.config;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    /* JADX INFO: renamed from: visitElement */
    public void mo6335visitElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (!this.config.getEnsureAllNodesAreDifferent() || this.visitedElements.add(element)) {
            return;
        }
        this.reportError.invoke(element, "Duplicate IR node: " + (element instanceof IrTypeParameter ? RenderIrElementKt.render$default(element, (DumpIrTreeOptions) null, 1, (Object) null) + " of " + RenderIrElementKt.render$default(((IrTypeParameter) element).getParent(), (DumpIrTreeOptions) null, 1, (Object) null) : RenderIrElementKt.render$default(element, (DumpIrTreeOptions) null, 1, (Object) null)));
    }

    private final void ensureTypesEqual(IrExpression irExpression, IrType irType, IrType irType2) {
        if (this.config.getCheckTypes() && !Intrinsics.areEqual(irType, irType2)) {
            this.reportError.invoke(irExpression, "unexpected type: expected " + RenderIrElementKt.render$default(irType2, (DumpIrTreeOptions) null, 1, (Object) null) + ", got " + RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
    }

    private final void ensureNullable(IrExpression irExpression) {
        if (this.config.getCheckTypes() && !IrTypeUtilsKt.isNullable(irExpression.getType())) {
            this.reportError.invoke(irExpression, "expected a nullable type, got " + RenderIrElementKt.render$default(irExpression.getType(), (DumpIrTreeOptions) null, 1, (Object) null));
        }
    }

    private final void ensureTypeIs(IrExpression irExpression, IrType irType) {
        ensureTypesEqual(irExpression, irExpression.getType(), irType);
    }

    private final void ensureBound(IrSymbol irSymbol, IrExpression irExpression) {
        if (irSymbol.isBound() || (irExpression.getType() instanceof IrDynamicType)) {
            return;
        }
        this.reportError.invoke(irExpression, "Unbound symbol " + irSymbol);
    }

    private final void checkFunction(IrElement irElement, IrFunction irFunction) {
        if ((irFunction instanceof IrSimpleFunction) && this.config.getCheckProperties()) {
            IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irFunction).getCorrespondingPropertySymbol();
            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
            if (owner != null && !Intrinsics.areEqual(owner.getGetter(), irFunction) && !Intrinsics.areEqual(owner.getSetter(), irFunction)) {
                this.reportError.invoke(irElement, "Orphaned property getter/setter " + RenderIrElementKt.render$default(irFunction, (DumpIrTreeOptions) null, 1, (Object) null));
            }
        }
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        if ((dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null) instanceof IrDynamicType) {
            this.reportError.invoke(irElement, "Dispatch receivers with 'dynamic' type are not allowed");
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConst(IrConst<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitConst(this, expression);
        IrConstKind<?> kind = expression.getKind();
        if (Intrinsics.areEqual(kind, IrConstKind.Null.INSTANCE)) {
            ensureNullable(expression);
            return;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Boolean.INSTANCE)) {
            this.irBuiltIns.getBooleanType();
            return;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Char.INSTANCE)) {
            this.irBuiltIns.getCharType();
            return;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Byte.INSTANCE)) {
            this.irBuiltIns.getByteType();
            return;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Short.INSTANCE)) {
            this.irBuiltIns.getShortType();
            return;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Int.INSTANCE)) {
            this.irBuiltIns.getIntType();
            return;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.Long.INSTANCE)) {
            this.irBuiltIns.getLongType();
            return;
        }
        if (Intrinsics.areEqual(kind, IrConstKind.String.INSTANCE)) {
            this.irBuiltIns.getStringType();
        } else if (Intrinsics.areEqual(kind, IrConstKind.Float.INSTANCE)) {
            this.irBuiltIns.getFloatType();
        } else {
            if (!Intrinsics.areEqual(kind, IrConstKind.Double.INSTANCE)) {
                throw new NoWhenBranchMatchedException();
            }
            this.irBuiltIns.getDoubleType();
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitStringConcatenation(IrStringConcatenation expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, expression);
        ensureTypeIs(expression, this.irBuiltIns.getStringType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetObjectValue(IrGetObjectValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, expression);
        ensureTypeIs(expression, IrTypesKt.createType(expression.getSymbol(), false, CollectionsKt.emptyList()));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetValue(IrGetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitGetValue(this, expression);
        ensureTypeIs(expression, expression.getSymbol().getOwner().getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitSetValue(this, expression);
        IrValueDeclaration owner = expression.getSymbol().getOwner();
        if ((owner instanceof IrValueParameter) && !owner.getIsAssignable()) {
            this.reportError.invoke(expression, "Assignment to value parameters not marked assignable");
        }
        ensureTypeIs(expression, this.irBuiltIns.getUnitType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitGetField(this, expression);
        IrType type = expression.getSymbol().getOwner().getType();
        if (type instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) type;
            if ((irSimpleType.getClassifier() instanceof IrClassSymbol) && irSimpleType.getArguments().isEmpty()) {
                ensureTypeIs(expression, type);
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSetField(IrSetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitSetField(this, expression);
        ensureTypeIs(expression, this.irBuiltIns.getUnitType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitCall(this, expression);
        checkFunction(expression, expression.getSymbol().getOwner());
        IrType returnType = expression.getSymbol().getOwner().getReturnType();
        if (returnType instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) returnType;
            if ((irSimpleType.getClassifier() instanceof IrClassSymbol) && irSimpleType.getArguments().isEmpty()) {
                ensureTypeIs(expression, returnType);
            }
        }
        IrClassSymbol superQualifierSymbol = expression.getSuperQualifierSymbol();
        if (superQualifierSymbol != null) {
            ensureBound(superQualifierSymbol, expression);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, expression);
        ensureTypeIs(expression, this.irBuiltIns.getUnitType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitInstanceInitializerCall(IrInstanceInitializerCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, expression);
        IrInstanceInitializerCall irInstanceInitializerCall = expression;
        ensureTypeIs(irInstanceInitializerCall, this.irBuiltIns.getUnitType());
        ensureBound(expression.getClassSymbol(), irInstanceInitializerCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeOperator(IrTypeOperatorCall expression) {
        IrType irTypeMakeNullable;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, expression);
        IrTypeOperator operator = expression.getOperator();
        IrType typeOperand = expression.getTypeOperand();
        switch (WhenMappings.$EnumSwitchMapping$0[operator.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                irTypeMakeNullable = typeOperand;
                break;
            case 9:
                irTypeMakeNullable = IrTypesKt.makeNullable(typeOperand);
                break;
            case 10:
            case 11:
                irTypeMakeNullable = this.irBuiltIns.getBooleanType();
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        if (operator == IrTypeOperator.IMPLICIT_COERCION_TO_UNIT && !IrTypePredicatesKt.isUnit(typeOperand)) {
            this.reportError.invoke(expression, "typeOperand is " + RenderIrElementKt.render$default(typeOperand, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        ensureTypeIs(expression, irTypeMakeNullable);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitLoop(IrLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        IrElementVisitorVoid.DefaultImpls.visitLoop(this, loop);
        ensureTypeIs(loop, this.irBuiltIns.getUnitType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitBreakContinue(IrBreakContinue jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, jump);
        ensureTypeIs(jump, this.irBuiltIns.getNothingType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitReturn(IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitReturn(this, expression);
        IrReturn irReturn = expression;
        ensureTypeIs(irReturn, this.irBuiltIns.getNothingType());
        ensureBound(expression.getReturnTargetSymbol(), irReturn);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitThrow(IrThrow expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitThrow(this, expression);
        ensureTypeIs(expression, this.irBuiltIns.getNothingType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    /* JADX INFO: renamed from: visitClass */
    public void mo6112visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementVisitorVoid.DefaultImpls.visitClass(this, declaration);
        if (!this.config.getCheckDescriptors() || IrUtilsKt.isAnnotationClass(declaration)) {
            return;
        }
        MemberScope unsubstitutedMemberScope = declaration.getDescriptor().getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, null, null, 3, null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : contributedDescriptors$default) {
            if (obj instanceof CallableMemberDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (!Intrinsics.areEqual(((CallableMemberDescriptor) obj2).getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = arrayList2;
        List<IrDeclaration> declarations = declaration.getDeclarations();
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(declarations, 10));
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            arrayList4.add(((IrDeclaration) it.next()).getDescriptor());
        }
        List listMinus = CollectionsKt.minus((Iterable) arrayList3, (Iterable) arrayList4);
        if (listMinus.isEmpty()) {
            return;
        }
        this.reportError.invoke(declaration, "Missing declarations for descriptors:\n" + CollectionsKt.joinToString$default(listMinus, "\n: ", null, null, 0, null, null, 62, null));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunction(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementVisitorVoid.DefaultImpls.visitFunction(this, declaration);
        checkFunction(declaration, declaration);
        int i = 0;
        int i2 = 0;
        for (IrValueParameter irValueParameter : declaration.getValueParameters()) {
            int i3 = i2 + 1;
            if (irValueParameter.getIndex() != i2) {
                this.reportError.invoke(declaration, "Inconsistent index of value parameter " + irValueParameter.getIndex() + " != " + i2);
            }
            i2 = i3;
        }
        for (IrTypeParameter irTypeParameter : declaration.getTypeParameters()) {
            int i4 = i + 1;
            if (irTypeParameter.getIndex() != i) {
                this.reportError.invoke(declaration, "Inconsistent index of type parameter " + irTypeParameter.getIndex() + " != " + i);
            }
            i = i4;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDeclarationReference(IrDeclarationReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, expression);
        if (expression instanceof IrFieldAccessExpression) {
            IrExpression receiver = ((IrFieldAccessExpression) expression).getReceiver();
            if ((receiver != null ? receiver.getType() : null) instanceof IrDynamicType) {
                return;
            }
        }
        ensureBound(expression.getSymbol(), expression);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitDeclaration(IrDeclarationBase declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, declaration);
        if (declaration instanceof IrOverridableDeclaration) {
            Iterator it = ((IrOverridableDeclaration) declaration).getOverriddenSymbols().iterator();
            while (it.hasNext()) {
                IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
                IrDeclarationWithVisibility irDeclarationWithVisibility = owner instanceof IrDeclarationWithVisibility ? (IrDeclarationWithVisibility) owner : null;
                if (irDeclarationWithVisibility != null && Intrinsics.areEqual(irDeclarationWithVisibility.getVisibility(), DescriptorVisibilities.PRIVATE)) {
                    this.reportError.invoke(declaration, "Overrides private declaration " + irDeclarationWithVisibility);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunctionAccess(IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, expression);
        ensureBound(expression.getSymbol(), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFunctionReference(IrFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, expression);
        checkFunction(expression, expression.getSymbol().getOwner());
        ensureBound(expression.getSymbol(), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitPropertyReference(IrPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, expression);
        IrFieldSymbol field = expression.getField();
        if (field != null) {
            ensureBound(field, expression);
        }
        IrSimpleFunctionSymbol getter = expression.getGetter();
        if (getter != null) {
            ensureBound(getter, expression);
        }
        IrSimpleFunctionSymbol setter = expression.getSetter();
        if (setter != null) {
            ensureBound(setter, expression);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, expression);
        IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference = expression;
        ensureBound(expression.getDelegate(), irLocalDelegatedPropertyReference);
        ensureBound(expression.getGetter(), irLocalDelegatedPropertyReference);
        IrSimpleFunctionSymbol setter = expression.getSetter();
        if (setter != null) {
            ensureBound(setter, irLocalDelegatedPropertyReference);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitExpression(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        checkType(expression.getType(), expression);
        IrElementVisitorVoid.DefaultImpls.visitExpression(this, expression);
    }

    private final void checkType(IrType type, IrElement element) {
        if (type instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) type;
            if (irSimpleType.getClassifier().isBound()) {
                return;
            }
            this.reportError.invoke(element, "Type: " + RenderIrElementKt.render$default(irSimpleType, (DumpIrTreeOptions) null, 1, (Object) null) + " has unbound classifier");
        }
    }
}
