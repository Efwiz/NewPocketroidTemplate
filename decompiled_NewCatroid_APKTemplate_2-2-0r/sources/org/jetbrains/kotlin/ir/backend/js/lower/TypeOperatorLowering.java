package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrArithBuilder;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.JsInlineClassesUtils;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
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
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: TypeOperatorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\tR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\tR\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001cR\u000e\u0010\u001f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\r0\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\tR\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020\r0\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\tR\u0014\u0010%\u001a\u00020&8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020&8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b*\u0010(R\u0014\u0010+\u001a\u00020&8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b,\u0010(R\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\tR\u001a\u0010/\u001a\b\u0012\u0004\u0012\u00020\r0\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\tR\u001a\u00101\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\tR\u000e\u00103\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00108\u001a\u0002098BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/TypeOperatorLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "booleanMarker", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "getBooleanMarker", "()Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "booleanNot", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "byteMask", "", "getByteMask", "calculator", "Lorg/jetbrains/kotlin/ir/backend/js/ir/JsIrArithBuilder;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "devMode", "", "eqeq", "functionMarker", "getFunctionMarker", "icUtils", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsInlineClassesUtils;", "instanceOfIntrinsicSymbol", "isArraySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "isExternalObjectSymbol", "isInterfaceSymbol", "isSuspendFunctionSymbol", "jsClassIntrinsicSymbol", "lit16", "getLit16", "lit24", "getLit24", "litFalse", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getLitFalse", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "litNull", "getLitNull", "litTrue", "getLitTrue", "numberMarker", "getNumberMarker", "shortMask", "getShortMask", "stringMarker", "getStringMarker", Namer.THROW_CLASS_CAST_EXCEPTION_FUN_NAME, "throwNPE", "typeOfIntrinsicSymbol", "unit", "Lorg/jetbrains/kotlin/ir/types/IrType;", "unitValue", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "getUnitValue", "()Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetObjectValueImpl;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeOperatorLowering implements BodyLoweringPass {
    private final IrSimpleFunctionSymbol booleanNot;
    private final JsIrArithBuilder calculator;
    private final JsIrBackendContext context;
    private final boolean devMode;
    private final IrSimpleFunctionSymbol eqeq;
    private final JsInlineClassesUtils icUtils;
    private final IrSimpleFunctionSymbol instanceOfIntrinsicSymbol;
    private final IrSimpleFunctionSymbol isExternalObjectSymbol;
    private final IrSimpleFunctionSymbol isSuspendFunctionSymbol;
    private final IrSimpleFunctionSymbol jsClassIntrinsicSymbol;
    private final IrSimpleFunctionSymbol throwCCE;
    private final IrSimpleFunctionSymbol throwNPE;
    private final IrSimpleFunctionSymbol typeOfIntrinsicSymbol;
    private final IrType unit;

    public TypeOperatorLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.unit = context.getIrBuiltIns().getUnitType();
        this.calculator = new JsIrArithBuilder(context);
        this.devMode = context.getDevMode();
        this.throwCCE = context.getIr().getSymbols().getThrowTypeCastException();
        this.throwNPE = context.getIr().getSymbols().getThrowNullPointerException();
        this.eqeq = context.getIrBuiltIns().getEqeqSymbol();
        this.booleanNot = context.getIrBuiltIns().getBooleanNotSymbol();
        this.isSuspendFunctionSymbol = context.getIntrinsics().getIsSuspendFunctionSymbol();
        this.instanceOfIntrinsicSymbol = context.getIntrinsics().getJsInstanceOf();
        this.isExternalObjectSymbol = context.getIntrinsics().getIsExternalObject();
        this.typeOfIntrinsicSymbol = context.getIntrinsics().getJsTypeOf();
        this.jsClassIntrinsicSymbol = context.getIntrinsics().getJsClass();
        this.icUtils = context.getInlineClassesUtils();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrGetObjectValueImpl getUnitValue() {
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrType irType = this.unit;
        IrClassifierSymbol classifierOrFail = IrTypesKt.getClassifierOrFail(irType);
        Intrinsics.checkNotNull(classifierOrFail, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
        return jsIrBuilder.buildGetObjectValue(irType, (IrClassSymbol) classifierOrFail);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Integer> getLit24() {
        return JsIrBuilder.INSTANCE.buildInt(this.context.getIrBuiltIns().getIntType(), 24);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Integer> getLit16() {
        return JsIrBuilder.INSTANCE.buildInt(this.context.getIrBuiltIns().getIntType(), 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Integer> getByteMask() {
        return JsIrBuilder.INSTANCE.buildInt(this.context.getIrBuiltIns().getIntType(), 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Integer> getShortMask() {
        return JsIrBuilder.INSTANCE.buildInt(this.context.getIrBuiltIns().getIntType(), 65535);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunctionSymbol isInterfaceSymbol() {
        return this.context.getIntrinsics().getIsInterfaceSymbol();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunctionSymbol isArraySymbol() {
        return this.context.getIntrinsics().getIsArraySymbol();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<String> getStringMarker() {
        return JsIrBuilder.INSTANCE.buildString(this.context.getIrBuiltIns().getStringType(), "string");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<String> getBooleanMarker() {
        return JsIrBuilder.INSTANCE.buildString(this.context.getIrBuiltIns().getStringType(), "boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<String> getFunctionMarker() {
        return JsIrBuilder.INSTANCE.buildString(this.context.getIrBuiltIns().getStringType(), "function");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<String> getNumberMarker() {
        return JsIrBuilder.INSTANCE.buildString(this.context.getIrBuiltIns().getStringType(), "number");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression getLitTrue() {
        return JsIrBuilder.INSTANCE.buildBoolean(this.context.getIrBuiltIns().getBooleanType(), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression getLitFalse() {
        return JsIrBuilder.INSTANCE.buildBoolean(this.context.getIrBuiltIns().getBooleanType(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression getLitNull() {
        return JsIrBuilder.INSTANCE.buildNull(this.context.getIrBuiltIns().getNothingNType());
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformer<IrDeclarationParent> irElementTransformer = new IrElementTransformer<IrDeclarationParent>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.TypeOperatorLowering.lower.1

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.TypeOperatorLowering$lower$1$WhenMappings */
            /* JADX INFO: compiled from: TypeOperatorLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[IrTypeOperator.values().length];
                    try {
                        iArr[IrTypeOperator.IMPLICIT_CAST.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[IrTypeOperator.IMPLICIT_DYNAMIC_CAST.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[IrTypeOperator.IMPLICIT_COERCION_TO_UNIT.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[IrTypeOperator.IMPLICIT_INTEGER_COERCION.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    try {
                        iArr[IrTypeOperator.IMPLICIT_NOTNULL.ordinal()] = 5;
                    } catch (NoSuchFieldError unused5) {
                    }
                    try {
                        iArr[IrTypeOperator.INSTANCEOF.ordinal()] = 6;
                    } catch (NoSuchFieldError unused6) {
                    }
                    try {
                        iArr[IrTypeOperator.NOT_INSTANCEOF.ordinal()] = 7;
                    } catch (NoSuchFieldError unused7) {
                    }
                    try {
                        iArr[IrTypeOperator.CAST.ordinal()] = 8;
                    } catch (NoSuchFieldError unused8) {
                    }
                    try {
                        iArr[IrTypeOperator.SAFE_CAST.ordinal()] = 9;
                    } catch (NoSuchFieldError unused9) {
                    }
                    try {
                        iArr[IrTypeOperator.REINTERPRET_CAST.ordinal()] = 10;
                    } catch (NoSuchFieldError unused10) {
                    }
                    try {
                        iArr[IrTypeOperator.SAM_CONVERSION.ordinal()] = 11;
                    } catch (NoSuchFieldError unused11) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
            public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBlock2(IrBlock irBlock, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitBlockBody2(IrBlockBody irBlockBody, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitBody2(IrBody irBody2, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBody(this, irBody2, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
            public IrBranch visitBranch2(IrBranch irBranch, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBreak2(IrBreak irBreak, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitCall2(IrCall irCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitCall(this, irCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclarationParent) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
                return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclarationParent) obj);
            }

            public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
            public IrCatch visitCatch2(IrCatch irCatch, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
            public IrStatement visitClass2(IrClass irClass, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitClass(this, irClass, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitClassReference2(IrClassReference irClassReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
            public IrExpression visitComposite2(IrComposite irComposite, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
                return visitConst((IrConst<?>) irConst, (IrDeclarationParent) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
                return visitConst((IrConst<?>) irConst, (IrDeclarationParent) obj);
            }

            public IrExpression visitConst(IrConst<?> irConst, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConst(this, irConst, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
            public IrStatement visitConstructor2(IrConstructor irConstructor, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitContinue2(IrContinue irContinue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
            public IrElement visitElement2(IrElement irElement, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitElement(this, irElement, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
            public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
            public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
            public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitExpression2(IrExpression irExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
            public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
            public IrStatement visitField2(IrField irField, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitField(this, irField, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
            public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
            public IrFile visitFile2(IrFile irFile, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFile(this, irFile, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
            public IrStatement visitFunction2(IrFunction irFunction, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetClass2(IrGetClass irGetClass, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetField2(IrGetField irGetField, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetValue2(IrGetValue irGetValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetValue(this, irGetValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
            public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
            public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitLoop2(IrLoop irLoop, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclarationParent) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclarationParent) obj);
            }

            public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
            public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
            public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
            public IrStatement visitProperty2(IrProperty irProperty, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
            public IrExpression visitReturn2(IrReturn irReturn, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
            public IrStatement visitScript2(IrScript irScript, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitScript(this, irScript, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSetField2(IrSetField irSetField, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSetValue2(IrSetValue irSetValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
            public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
            public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
            public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
            public IrExpression visitThrow2(IrThrow irThrow, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
            public IrExpression visitTry2(IrTry irTry, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTry(this, irTry, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
            public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
            public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
            public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
            public IrStatement visitValueParameter2(IrValueParameter irValueParameter, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
            public IrExpression visitVararg2(IrVararg irVararg, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
            public IrStatement visitVariable2(IrVariable irVariable, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
            public IrExpression visitWhen2(IrWhen irWhen, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, irDeclarationParent);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
            public IrStatement visitDeclaration2(IrDeclarationBase declaration, IrDeclarationParent data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                Intrinsics.checkNotNullParameter(data, "data");
                IrDeclarationParent irDeclarationParent = declaration instanceof IrDeclarationParent ? (IrDeclarationParent) declaration : null;
                if (irDeclarationParent != null) {
                    data = irDeclarationParent;
                }
                return IrElementTransformer.DefaultImpls.visitDeclaration(this, declaration, data);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
            public IrExpression visitTypeOperator2(IrTypeOperatorCall expression, IrDeclarationParent data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                IrElementTransformer.DefaultImpls.visitTypeOperator(this, expression, data);
                switch (WhenMappings.$EnumSwitchMapping$0[expression.getOperator().ordinal()]) {
                    case 1:
                        return lowerImplicitCast(expression, data);
                    case 2:
                        return lowerImplicitDynamicCast(expression, data);
                    case 3:
                        return lowerCoercionToUnit(expression);
                    case 4:
                        return lowerIntegerCoercion(expression, data);
                    case 5:
                        return lowerImplicitNotNull(expression, data);
                    case 6:
                        return lowerInstanceOf(expression, data, false);
                    case 7:
                        return lowerInstanceOf(expression, data, true);
                    case 8:
                        return lowerCast(expression, data, false);
                    case 9:
                        return lowerCast(expression, data, true);
                    case 10:
                        return expression;
                    case 11:
                        throw new NotImplementedError("An operation is not implemented: " + ("SAM conversion: " + RenderIrElementKt.render$default(expression, (DumpIrTreeOptions) null, 1, (Object) null)));
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }

            private final IrExpression lowerImplicitNotNull(IrTypeOperatorCall expression, IrDeclarationParent declaration) {
                expression.getOperator();
                IrTypeOperator irTypeOperator = IrTypeOperator.IMPLICIT_NOTNULL;
                IrTypeUtilsKt.isNullable(expression.getTypeOperand());
                IrTypeUtilsKt.isNullable(expression.getArgument().getType());
                ArrayList arrayList = new ArrayList();
                Function0<IrExpression> function0CacheValue = cacheValue(expression.getArgument(), arrayList, declaration);
                arrayList.add(JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, expression.getTypeOperand(), nullCheck(function0CacheValue.invoke()), JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.throwNPE, null, null, null, null, 30, null), function0CacheValue.invoke(), 0, 0, 0, 0, 240, null));
                return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getTypeOperand(), null, arrayList);
            }

            private final boolean needBoxingOrUnboxing(IrType fromType, IrType toType) {
                if ((TypeOperatorLowering.this.icUtils.getInlinedClass(fromType) != null) ^ (TypeOperatorLowering.this.icUtils.getInlinedClass(toType) != null)) {
                    return true;
                }
                return IrTypePredicatesKt.isUnit(fromType) && !IrTypePredicatesKt.isUnit(toType);
            }

            private final IrExpression wrapWithUnsafeCast(IrTypeOperatorCall irTypeOperatorCall, IrExpression irExpression) {
                return !needBoxingOrUnboxing(irExpression.getType(), irTypeOperatorCall.getTypeOperand()) ? new IrTypeOperatorCallImpl(irTypeOperatorCall.getStartOffset(), irTypeOperatorCall.getEndOffset(), irTypeOperatorCall.getType(), IrTypeOperator.REINTERPRET_CAST, irTypeOperatorCall.getTypeOperand(), irExpression) : irExpression;
            }

            private final IrExpression lowerCast(IrTypeOperatorCall expression, IrDeclarationParent declaration, boolean isSafe) {
                IrTypeOperator operator = expression.getOperator();
                if (operator != IrTypeOperator.CAST && operator != IrTypeOperator.SAFE_CAST && operator != IrTypeOperator.IMPLICIT_CAST) {
                    IrTypeOperator irTypeOperator = IrTypeOperator.IMPLICIT_DYNAMIC_CAST;
                }
                IrTypeOperator irTypeOperator2 = IrTypeOperator.SAFE_CAST;
                IrType typeOperand = expression.getTypeOperand();
                IrExpression litNull = isSafe ? TypeOperatorLowering.this.getLitNull() : JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.throwCCE, null, null, null, null, 30, null);
                ArrayList arrayList = new ArrayList();
                Function0<IrExpression> function0CacheValue = cacheValue(expression.getArgument(), arrayList, declaration);
                arrayList.add(JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, expression.getType(), generateTypeCheck(function0CacheValue, typeOperand), wrapWithUnsafeCast(expression, function0CacheValue.invoke()), litNull, 0, 0, 0, 0, 240, null));
                return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), null, arrayList);
            }

            private final IrExpression lowerImplicitCast(IrTypeOperatorCall expression, IrDeclarationParent data) {
                TypeOperatorLowering typeOperatorLowering = TypeOperatorLowering.this;
                expression.getOperator();
                IrTypeOperator irTypeOperator = IrTypeOperator.IMPLICIT_CAST;
                return typeOperatorLowering.devMode ? lowerCast(expression, data, false) : wrapWithUnsafeCast(expression, expression.getArgument());
            }

            private final IrExpression lowerImplicitDynamicCast(IrTypeOperatorCall expression, IrDeclarationParent data) {
                TypeOperatorLowering typeOperatorLowering = TypeOperatorLowering.this;
                expression.getOperator();
                IrTypeOperator irTypeOperator = IrTypeOperator.IMPLICIT_DYNAMIC_CAST;
                return typeOperatorLowering.devMode ? lowerCast(expression, data, false) : wrapWithUnsafeCast(expression, expression.getArgument());
            }

            /* JADX WARN: Code duplicated, block: B:17:0x0037  */
            /* JADX WARN: Code duplicated, block: B:19:0x003d  */
            private final boolean advancedCheckRequired(IrType type) {
                if (!org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isInterface(type)) {
                    if (!org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isTypeParameter(type)) {
                        if (IrTypePredicatesKt.isArray(type)) {
                        }
                    } else {
                        List<IrType> listSuperTypes = org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.superTypes(type);
                        if ((listSuperTypes instanceof Collection) && listSuperTypes.isEmpty()) {
                            if (IrTypePredicatesKt.isArray(type)) {
                            }
                        } else {
                            Iterator<T> it = listSuperTypes.iterator();
                            while (it.hasNext()) {
                                if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isInterface((IrType) it.next())) {
                                }
                            }
                            if (IrTypePredicatesKt.isArray(type) && !org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isPrimitiveArray(type) && !isTypeOfCheckingType(type)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }

            private final boolean isTypeOfCheckingType(IrType type) {
                return IrTypePredicatesKt.isByte(type) || IrTypePredicatesKt.isShort(type) || IrTypePredicatesKt.isInt(type) || IrTypePredicatesKt.isFloat(type) || IrTypePredicatesKt.isDouble(type) || IrTypePredicatesKt.isBoolean(type) || org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunctionOrKFunction(type) || IrTypePredicatesKt.isString(type);
            }

            public final IrExpression lowerInstanceOf(IrTypeOperatorCall expression, IrDeclarationParent declaration, boolean inverted) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (expression.getOperator() != IrTypeOperator.INSTANCEOF) {
                    expression.getOperator();
                    IrTypeOperator irTypeOperator = IrTypeOperator.NOT_INSTANCEOF;
                }
                expression.getOperator();
                IrTypeOperator irTypeOperator2 = IrTypeOperator.NOT_INSTANCEOF;
                IrType typeOperand = expression.getTypeOperand();
                ArrayList arrayList = new ArrayList();
                IrExpression irExpressionGenerateTypeCheck = generateTypeCheck(cacheValue(expression.getArgument(), arrayList, declaration), typeOperand);
                if (inverted) {
                    irExpressionGenerateTypeCheck = TypeOperatorLowering.this.calculator.not(irExpressionGenerateTypeCheck);
                }
                arrayList.add(irExpressionGenerateTypeCheck);
                return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), TypeOperatorLowering.this.getContext().getIrBuiltIns().getBooleanType(), null, arrayList);
            }

            private final IrCall nullCheck(IrExpression value) {
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.eqeq, null, null, null, null, 30, null);
                TypeOperatorLowering typeOperatorLowering = TypeOperatorLowering.this;
                irCallBuildCall$default.putValueArgument(0, value);
                irCallBuildCall$default.putValueArgument(1, typeOperatorLowering.getLitNull());
                return irCallBuildCall$default;
            }

            private final Function0<IrExpression> cacheValue(final IrExpression value, List<IrStatement> newStatements, IrDeclarationParent declaration) {
                Function0<IrExpression> function0;
                if (IrUtilsKt.isPure$default(value, true, false, null, 4, null)) {
                    function0 = new Function0<IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.TypeOperatorLowering$lower$1$cacheValue$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final IrExpression invoke() {
                            IrExpression irExpression = value;
                            DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                            IrVisitorsKt.acceptVoid(irExpression, deepCopySymbolRemapper);
                            DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                            IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpression.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
                            if (irElementPatchDeclarationParents != null) {
                                return (IrExpression) irElementPatchDeclarationParents;
                            }
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                        }
                    };
                } else {
                    final IrVariable irVariableBuildVar = JsIrBuilder.INSTANCE.buildVar(value.getType(), declaration, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : null, (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : value, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
                    newStatements.add(irVariableBuildVar);
                    function0 = new Function0<IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.TypeOperatorLowering$lower$1$cacheValue$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final IrExpression invoke() {
                            return JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar.getSymbol(), null, 2, null);
                        }
                    };
                }
                return function0;
            }

            private final IrExpression generateTypeCheck(Function0<? extends IrExpression> argument, IrType toType) {
                IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(toType);
                IrExpression irExpressionInvoke = argument.invoke();
                IrExpression irExpressionGenerateTypeCheckNonNull = generateTypeCheckNonNull(irExpressionInvoke, irTypeMakeNotNull);
                boolean zIsNullable = IrTypeUtilsKt.isNullable(irExpressionInvoke.getType());
                boolean zIsNullable2 = IrTypeUtilsKt.isNullable(toType);
                boolean zAdvancedCheckRequired = advancedCheckRequired(irTypeMakeNotNull);
                if (!zIsNullable) {
                    return irExpressionGenerateTypeCheckNonNull;
                }
                if (zIsNullable2) {
                    return TypeOperatorLowering.this.calculator.oror(nullCheck(argument.invoke()), irExpressionGenerateTypeCheckNonNull);
                }
                if (!zAdvancedCheckRequired) {
                    return irExpressionGenerateTypeCheckNonNull;
                }
                JsIrArithBuilder jsIrArithBuilder = TypeOperatorLowering.this.calculator;
                return jsIrArithBuilder.andand(jsIrArithBuilder.not(nullCheck(argument.invoke())), irExpressionGenerateTypeCheckNonNull);
            }

            private final IrExpression generateTypeCheckNonNull(IrExpression argument, IrType toType) {
                IrTypePredicatesKt.isMarkedNullable(toType);
                if (toType instanceof IrDynamicType) {
                    return argument;
                }
                if (IrTypePredicatesKt.isAny(toType)) {
                    return generateIsObjectCheck(argument);
                }
                if (IrTypePredicatesKt.isNothing(toType)) {
                    return JsIrBuilder.INSTANCE.buildComposite(TypeOperatorLowering.this.getContext().getIrBuiltIns().getBooleanType(), CollectionsKt.listOf((Object[]) new IrExpression[]{argument, TypeOperatorLowering.this.getLitFalse()}));
                }
                if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isSuspendFunction(toType)) {
                    return generateSuspendFunctionCheck(argument, toType);
                }
                if (isTypeOfCheckingType(toType)) {
                    return generateTypeOfCheck(argument, toType);
                }
                if (IrTypePredicatesKt.isNumber(toType)) {
                    return generateNumberCheck(argument);
                }
                if (IrTypePredicatesKt.isComparable(toType)) {
                    return generateComparableCheck(argument);
                }
                if (IrTypePredicatesKt.isCharSequence(toType)) {
                    return generateCharSequenceCheck(argument);
                }
                if (IrTypePredicatesKt.isArray(toType)) {
                    return generateGenericArrayCheck(argument);
                }
                if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isPrimitiveArray(toType)) {
                    return generatePrimitiveArrayTypeCheck(argument, toType);
                }
                if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isTypeParameter(toType)) {
                    return generateTypeCheckWithTypeParameter(argument, toType);
                }
                if (!org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isInterface(toType)) {
                    return org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isExternalObject(toType) ? generateIsExternalObject(argument, toType) : generateNativeInstanceOf(argument, toType);
                }
                IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(toType).getOwner();
                Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                if (org.jetbrains.kotlin.ir.util.IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner)) {
                    return generateIsObjectCheck(argument);
                }
                return generateInterfaceCheck(argument, toType);
            }

            private final IrCall generateIsObjectCheck(IrExpression argument) {
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.booleanNot, null, null, null, null, 30, null);
                irCallBuildCall$default.setDispatchReceiver(nullCheck(argument));
                return irCallBuildCall$default;
            }

            private final IrExpression generateTypeCheckWithTypeParameter(IrExpression argument, IrType toType) {
                IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(toType);
                IrTypeParameterSymbol irTypeParameterSymbol = classifierOrNull instanceof IrTypeParameterSymbol ? (IrTypeParameterSymbol) classifierOrNull : null;
                if (irTypeParameterSymbol == null) {
                    CompilationExceptionKt.compilationException("expected type parameter, but " + toType, argument);
                    throw null;
                }
                List<IrType> superTypes = irTypeParameterSymbol.getOwner().getSuperTypes();
                TypeOperatorLowering typeOperatorLowering = TypeOperatorLowering.this;
                IrWhen irWhenAndand = null;
                for (IrType irType : superTypes) {
                    IrExpression irExpressionShallowCopyOrNull = org.jetbrains.kotlin.ir.util.IrUtilsKt.shallowCopyOrNull(argument);
                    if (irExpressionShallowCopyOrNull == null) {
                        IrExpression irExpression = argument;
                        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                        IrVisitorsKt.acceptVoid(irExpression, deepCopySymbolRemapper);
                        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpression.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
                        if (irElementPatchDeclarationParents != null) {
                            irExpressionShallowCopyOrNull = (IrExpression) irElementPatchDeclarationParents;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                        }
                    }
                    IrExpression irExpressionGenerateTypeCheckNonNull = generateTypeCheckNonNull(irExpressionShallowCopyOrNull, IrTypesKt.makeNotNull(irType));
                    irWhenAndand = irWhenAndand == null ? irExpressionGenerateTypeCheckNonNull : typeOperatorLowering.calculator.andand(irWhenAndand, irExpressionGenerateTypeCheckNonNull);
                }
                return irWhenAndand == null ? TypeOperatorLowering.this.getLitTrue() : irWhenAndand;
            }

            private final IrExpression generateSuspendFunctionCheck(IrExpression argument, IrType toType) {
                IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(toType).getOwner();
                Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                int size = ((IrClass) owner).getTypeParameters().size() - 1;
                IrBuiltIns irBuiltIns = TypeOperatorLowering.this.getContext().getIrBuiltIns();
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.isSuspendFunctionSymbol, irBuiltIns.getBooleanType(), null, null, null, 28, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                irCallBuildCall$default.putValueArgument(1, JsIrBuilder.INSTANCE.buildInt(irBuiltIns.getIntType(), size));
                return irCallBuildCall$default;
            }

            private final IrExpression generateTypeOfCheck(IrExpression argument, IrType toType) {
                IrConstImpl stringMarker;
                if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunctionOrKFunction(toType)) {
                    stringMarker = TypeOperatorLowering.this.getFunctionMarker();
                } else if (IrTypePredicatesKt.isBoolean(toType)) {
                    stringMarker = TypeOperatorLowering.this.getBooleanMarker();
                } else {
                    stringMarker = IrTypePredicatesKt.isString(toType) ? TypeOperatorLowering.this.getStringMarker() : TypeOperatorLowering.this.getNumberMarker();
                }
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.typeOfIntrinsicSymbol, null, null, null, null, 30, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.eqeq, null, null, null, null, 30, null);
                irCallBuildCall$default2.putValueArgument(0, irCallBuildCall$default);
                irCallBuildCall$default2.putValueArgument(1, stringMarker);
                return irCallBuildCall$default2;
            }

            private final IrCall wrapTypeReference(IrType toType) {
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.jsClassIntrinsicSymbol, null, null, null, null, 30, null);
                irCallBuildCall$default.putTypeArgument(0, toType);
                return irCallBuildCall$default;
            }

            private final IrCall generateGenericArrayCheck(IrExpression argument) {
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.isArraySymbol(), null, null, null, null, 30, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                return irCallBuildCall$default;
            }

            private final IrCall generateNumberCheck(IrExpression argument) {
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.getContext().getIntrinsics().getIsNumberSymbol(), null, null, null, null, 30, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                return irCallBuildCall$default;
            }

            private final IrCall generateComparableCheck(IrExpression argument) {
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.getContext().getIntrinsics().getIsComparableSymbol(), null, null, null, null, 30, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                return irCallBuildCall$default;
            }

            private final IrCall generateCharSequenceCheck(IrExpression argument) {
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.getContext().getIntrinsics().getIsCharSequenceSymbol(), null, null, null, null, 30, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                return irCallBuildCall$default;
            }

            private final IrExpression generatePrimitiveArrayTypeCheck(IrExpression argument, IrType toType) {
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = TypeOperatorLowering.this.getContext().getIntrinsics().isPrimitiveArray().get(org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.getPrimitiveArrayElementType(toType));
                Intrinsics.checkNotNull(irSimpleFunctionSymbol);
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irSimpleFunctionSymbol, null, null, null, null, 30, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                return irCallBuildCall$default;
            }

            private final IrExpression generateInterfaceCheck(IrExpression argument, IrType toType) {
                IrCall irCallWrapTypeReference = wrapTypeReference(toType);
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.isInterfaceSymbol(), null, null, null, null, 30, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                irCallBuildCall$default.putValueArgument(1, irCallWrapTypeReference);
                return irCallBuildCall$default;
            }

            private final IrExpression generateIsExternalObject(IrExpression argument, IrType toType) {
                IrCall irCallWrapTypeReference = wrapTypeReference(toType);
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.isExternalObjectSymbol, null, null, null, null, 30, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                irCallBuildCall$default.putValueArgument(1, irCallWrapTypeReference);
                return irCallBuildCall$default;
            }

            private final IrExpression generateNativeInstanceOf(IrExpression argument, IrType toType) {
                IrCall irCallWrapTypeReference = wrapTypeReference(toType);
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.instanceOfIntrinsicSymbol, null, null, null, null, 30, null);
                irCallBuildCall$default.putValueArgument(0, argument);
                irCallBuildCall$default.putValueArgument(1, irCallWrapTypeReference);
                return irCallBuildCall$default;
            }

            private final IrExpression lowerCoercionToUnit(IrTypeOperatorCall expression) {
                expression.getOperator();
                IrTypeOperator irTypeOperator = IrTypeOperator.IMPLICIT_COERCION_TO_UNIT;
                TypeOperatorLowering typeOperatorLowering = TypeOperatorLowering.this;
                return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), typeOperatorLowering.unit, null, CollectionsKt.listOf((Object[]) new IrExpression[]{expression.getArgument(), typeOperatorLowering.getUnitValue()}));
            }

            private final IrExpression lowerIntegerCoercion(IrTypeOperatorCall expression, IrDeclarationParent declaration) {
                IrCall irCallLowerIntegerCoercion$maskOp;
                expression.getOperator();
                IrTypeOperator irTypeOperator = IrTypeOperator.IMPLICIT_INTEGER_COERCION;
                IrTypePredicatesKt.isInt(expression.getArgument().getType());
                boolean zIsNullable = IrTypeUtilsKt.isNullable(expression.getArgument().getType());
                IrType typeOperand = expression.getTypeOperand();
                ArrayList arrayList = new ArrayList();
                Function0<IrExpression> function0CacheValue = cacheValue(expression.getArgument(), arrayList, declaration);
                if (IrTypePredicatesKt.isByte(typeOperand)) {
                    irCallLowerIntegerCoercion$maskOp = lowerIntegerCoercion$maskOp(TypeOperatorLowering.this, function0CacheValue.invoke(), TypeOperatorLowering.this.getByteMask(), TypeOperatorLowering.this.getLit24());
                } else if (IrTypePredicatesKt.isShort(typeOperand)) {
                    irCallLowerIntegerCoercion$maskOp = lowerIntegerCoercion$maskOp(TypeOperatorLowering.this, function0CacheValue.invoke(), TypeOperatorLowering.this.getShortMask(), TypeOperatorLowering.this.getLit16());
                } else if (IrTypePredicatesKt.isLong(typeOperand)) {
                    IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, TypeOperatorLowering.this.getContext().getIntrinsics().getJsToLong(), null, null, null, null, 30, null);
                    irCallBuildCall$default.putValueArgument(0, function0CacheValue.invoke());
                    irCallLowerIntegerCoercion$maskOp = irCallBuildCall$default;
                } else {
                    CompilationExceptionKt.compilationException("Unreachable execution (coercion to non-Integer type)", expression);
                    throw null;
                }
                IrWhen irWhenBuildIfElse$default = irCallLowerIntegerCoercion$maskOp;
                ArrayList arrayList2 = arrayList;
                if (zIsNullable) {
                    irWhenBuildIfElse$default = JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, typeOperand, nullCheck(function0CacheValue.invoke()), TypeOperatorLowering.this.getLitNull(), irWhenBuildIfElse$default, 0, 0, 0, 0, 240, null);
                }
                arrayList2.add(irWhenBuildIfElse$default);
                return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), typeOperand, null, arrayList);
            }

            private static final IrExpression lowerIntegerCoercion$maskOp(TypeOperatorLowering typeOperatorLowering, IrExpression irExpression, IrExpression irExpression2, IrConst<?> irConst) {
                JsIrArithBuilder jsIrArithBuilder = typeOperatorLowering.calculator;
                IrConst<?> irConst2 = irConst;
                return jsIrArithBuilder.shr(jsIrArithBuilder.shl(jsIrArithBuilder.and(irExpression, irExpression2), irConst2), org.jetbrains.kotlin.ir.util.IrUtilsKt.shallowCopy((IrExpression) irConst2));
            }
        };
        IrDeclarationParent parent = container instanceof IrDeclarationParent ? (IrDeclarationParent) container : null;
        if (parent == null) {
            parent = container.getParent();
        }
        irBody.transformChildren(irElementTransformer, parent);
    }
}
