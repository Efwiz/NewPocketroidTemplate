package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.TypeTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.utils.TypeTranformerKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
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
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;

/* JADX INFO: compiled from: WasmTypeOperatorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0014H\u0002J\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u0015H\u0002J\u0018\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u0015H\u0002J\u001e\u0010\u001f\u001a\u00020\u00192\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00190!2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002J\u0018\u0010\"\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u0015H\u0002J\u0018\u0010#\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u0015H\u0002J\u0018\u0010$\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0002J\u0010\u0010)\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&H\u0002J\u0018\u0010*\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\u0006\u0010+\u001a\u00020(H\u0002J\u0010\u0010,\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&H\u0002J \u0010-\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010/\u001a\u00020\u0019H\u0002J\u0010\u00100\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0016J\u001a\u00105\u001a\b\u0012\u0004\u0012\u00020\u00190!*\u0002062\u0006\u0010/\u001a\u00020\u0019H\u0002J\f\u00107\u001a\u00020(*\u00020\u0015H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0013\u001a\u00020\u0014*\u00020\u00158BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmBaseTypeOperatorTransformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "jsToKotlinAnyAdapter", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getJsToKotlinAnyAdapter", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "kotlinToJsAnyAdapter", "getKotlinToJsAnyAdapter", "symbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "eraseToClassOrInterface", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getEraseToClassOrInterface", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateIsExternalClass", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "argument", "klass", "generateIsInterface", "toType", "generateIsSubClass", "generateTypeCheck", "valueProvider", "Lkotlin/Function0;", "generateTypeCheckNonNull", "generateTypeCheckWithTypeParameter", "lowerCast", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "isSafe", "", "lowerImplicitCast", "lowerInstanceOf", "inverted", "lowerIntegerCoercion", "narrowType", "fromType", "value", "visitTypeOperator", "visitVariable", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "cacheValue", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "isInlined", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmBaseTypeOperatorTransformer extends IrElementTransformerVoidWithContext {
    private DeclarationIrBuilder builder;
    private final IrBuiltIns builtIns;
    private final WasmBackendContext context;
    private final WasmSymbols symbols;

    /* JADX INFO: compiled from: WasmTypeOperatorLowering.kt */
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
                iArr[IrTypeOperator.SAM_CONVERSION.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[IrTypeOperator.REINTERPRET_CAST.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public WasmBaseTypeOperatorTransformer(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.symbols = context.getWasmSymbols();
        this.builtIns = context.getIrBuiltIns();
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    private final IrSimpleFunctionSymbol getJsToKotlinAnyAdapter() {
        return this.symbols.getJsRelatedSymbols().getJsInteropAdapters().getJsToKotlinAnyAdapter();
    }

    private final IrSimpleFunctionSymbol getKotlinToJsAnyAdapter() {
        return this.symbols.getJsRelatedSymbols().getJsInteropAdapters().getKotlinToJsAnyAdapter();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        super.visitTypeOperator(expression);
        WasmBackendContext wasmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(wasmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null);
        IrTypeOperatorCall irTypeOperatorCall = expression;
        this.builder = (DeclarationIrBuilder) LowerUtilsKt.at(declarationIrBuilderCreateIrBuilder$default, irTypeOperatorCall);
        switch (WhenMappings.$EnumSwitchMapping$0[expression.getOperator().ordinal()]) {
            case 1:
                return lowerImplicitCast(expression);
            case 2:
                throw new IllegalStateException("Dynamic casts are not supported in Wasm backend".toString());
            case 3:
                return expression;
            case 4:
                return lowerIntegerCoercion(expression);
            case 5:
                return lowerImplicitCast(expression);
            case 6:
                return lowerInstanceOf(expression, false);
            case 7:
                return lowerInstanceOf(expression, true);
            case 8:
                return lowerCast(expression, false);
            case 9:
                return lowerCast(expression, true);
            case 10:
                throw new NotImplementedError("An operation is not implemented: " + ("SAM conversion: " + RenderIrElementKt.render$default(irTypeOperatorCall, (DumpIrTreeOptions) null, 1, (Object) null)));
            case 11:
                return expression;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrExpression initializer = declaration.getInitializer();
        if (initializer != null && !Intrinsics.areEqual(initializer.getType(), declaration.getType())) {
            WasmBackendContext wasmBackendContext = this.context;
            ScopeWithIr currentScope = getCurrentScope();
            Intrinsics.checkNotNull(currentScope);
            this.builder = (DeclarationIrBuilder) LowerUtilsKt.at(LowerUtilsKt.createIrBuilder$default(wasmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null), declaration);
            declaration.setInitializer(narrowType(initializer.getType(), declaration.getType(), initializer));
        }
        return super.visitVariable(declaration);
    }

    private final IrExpression lowerInstanceOf(IrTypeOperatorCall expression, boolean inverted) {
        DeclarationIrBuilder declarationIrBuilder = this.builder;
        DeclarationIrBuilder declarationIrBuilder2 = null;
        if (declarationIrBuilder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            declarationIrBuilder = null;
        }
        DeclarationIrBuilder declarationIrBuilder3 = declarationIrBuilder;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder3.getContext(), declarationIrBuilder3.getScope(), declarationIrBuilder3.getStartOffset(), declarationIrBuilder3.getEndOffset(), null, this.builtIns.getBooleanType(), true);
        IrExpression irExpressionGenerateTypeCheck = generateTypeCheck(cacheValue(irBlockBuilder, expression.getArgument()), expression.getTypeOperand());
        if (inverted) {
            DeclarationIrBuilder declarationIrBuilder4 = this.builder;
            if (declarationIrBuilder4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder2 = declarationIrBuilder4;
            }
            irBlockBuilder.unaryPlus(LowerUtilsKt.irNot(declarationIrBuilder2, irExpressionGenerateTypeCheck));
        } else {
            irBlockBuilder.unaryPlus(irExpressionGenerateTypeCheck);
        }
        return irBlockBuilder.getIrBlockBody();
    }

    private final Function0<IrExpression> cacheValue(IrBlockBuilder irBlockBuilder, final IrExpression irExpression) {
        if (IrUtilsKt.isPure$default(irExpression, true, false, null, 6, null) && org.jetbrains.kotlin.ir.util.IrUtilsKt.isTrivial(irExpression)) {
            return new Function0<IrExpression>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmBaseTypeOperatorTransformer.cacheValue.1
                {
                    super(0);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function0
                public final IrExpression invoke() {
                    IrExpression irExpression2 = irExpression;
                    DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, 0 == true ? 1 : 0);
                    IrVisitorsKt.acceptVoid(irExpression2, deepCopySymbolRemapper);
                    DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                    IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpression2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
                    if (irElementPatchDeclarationParents != null) {
                        return (IrExpression) irElementPatchDeclarationParents;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                }
            };
        }
        final IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, irExpression, (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
        return new Function0<IrGetValueImpl>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmBaseTypeOperatorTransformer.cacheValue.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrGetValueImpl invoke() {
                DeclarationIrBuilder declarationIrBuilder = WasmBaseTypeOperatorTransformer.this.builder;
                if (declarationIrBuilder == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("builder");
                    declarationIrBuilder = null;
                }
                return ExpressionHelpersKt.irGet(declarationIrBuilder, irVariableCreateTmpVariable$default);
            }
        };
    }

    private final boolean isInlined(IrType irType) {
        return this.context.getInlineClassesUtils().isTypeInlined(irType);
    }

    private final IrClass getEraseToClassOrInterface(IrType irType) {
        IrClass erasedUpperBound = TypeTranformerKt.getErasedUpperBound(irType);
        return erasedUpperBound == null ? this.builtIns.getAnyClass().getOwner() : erasedUpperBound;
    }

    private final IrExpression generateTypeCheck(Function0<? extends IrExpression> valueProvider, IrType toType) {
        IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(toType);
        IrExpression irExpressionInvoke = valueProvider.invoke();
        IrType type = irExpressionInvoke.getType();
        DeclarationIrBuilder declarationIrBuilder = null;
        if (isInlined(type)) {
            boolean zIsSubclassOf = org.jetbrains.kotlin.ir.util.IrUtilsKt.isSubclassOf(getEraseToClassOrInterface(type), getEraseToClassOrInterface(toType));
            DeclarationIrBuilder declarationIrBuilder2 = this.builder;
            if (declarationIrBuilder2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder2;
            }
            return ExpressionHelpersKt.irBoolean(declarationIrBuilder, zIsSubclassOf);
        }
        IrExpression irExpressionGenerateTypeCheckNonNull = generateTypeCheckNonNull(irExpressionInvoke, irTypeMakeNotNull);
        boolean zIsNullable = IrTypeUtilsKt.isNullable(irExpressionInvoke.getType());
        boolean zIsNullable2 = IrTypeUtilsKt.isNullable(toType);
        if (!zIsNullable) {
            return irExpressionGenerateTypeCheckNonNull;
        }
        DeclarationIrBuilder declarationIrBuilder3 = this.builder;
        if (declarationIrBuilder3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            declarationIrBuilder3 = null;
        }
        DeclarationIrBuilder declarationIrBuilder4 = declarationIrBuilder3;
        IrType booleanType = this.builtIns.getBooleanType();
        DeclarationIrBuilder declarationIrBuilder5 = this.builder;
        if (declarationIrBuilder5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            declarationIrBuilder5 = null;
        }
        IrExpression irExpressionIrEqualsNull = ExpressionHelpersKt.irEqualsNull(declarationIrBuilder5, valueProvider.invoke());
        DeclarationIrBuilder declarationIrBuilder6 = this.builder;
        if (declarationIrBuilder6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
        } else {
            declarationIrBuilder = declarationIrBuilder6;
        }
        return ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilder4, booleanType, irExpressionIrEqualsNull, ExpressionHelpersKt.irBoolean(declarationIrBuilder, zIsNullable2), irExpressionGenerateTypeCheckNonNull, null, 16, null);
    }

    private final IrExpression lowerIntegerCoercion(IrTypeOperatorCall expression) {
        IrType typeOperand = expression.getTypeOperand();
        if (Intrinsics.areEqual(typeOperand, this.builtIns.getByteType()) ? true : Intrinsics.areEqual(typeOperand, this.builtIns.getShortType())) {
            return expression.getArgument();
        }
        if (Intrinsics.areEqual(typeOperand, this.builtIns.getLongType())) {
            DeclarationIrBuilder declarationIrBuilder = this.builder;
            if (declarationIrBuilder == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
                declarationIrBuilder = null;
            }
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, this.symbols.getIntToLong());
            irCall.putValueArgument(0, expression.getArgument());
            return irCall;
        }
        throw new IllegalStateException("Unreachable execution (coercion to non-Integer type".toString());
    }

    private final IrExpression generateTypeCheckNonNull(IrExpression argument, IrType toType) {
        IrTypePredicatesKt.isMarkedNullable(toType);
        IrClass eraseToClassOrInterface = getEraseToClassOrInterface(toType);
        DeclarationIrBuilder declarationIrBuilder = null;
        if (eraseToClassOrInterface.getIsExternal()) {
            if (eraseToClassOrInterface.getKind() == ClassKind.INTERFACE) {
                DeclarationIrBuilder declarationIrBuilder2 = this.builder;
                if (declarationIrBuilder2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("builder");
                } else {
                    declarationIrBuilder = declarationIrBuilder2;
                }
                return ExpressionHelpersKt.irTrue(declarationIrBuilder);
            }
            return generateIsExternalClass(argument, eraseToClassOrInterface);
        }
        if (!IrTypePredicatesKt.isNothing(toType)) {
            if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isTypeParameter(toType)) {
                return generateTypeCheckWithTypeParameter(argument, toType);
            }
            return org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isInterface(toType) ? generateIsInterface(argument, toType) : generateIsSubClass(argument, toType);
        }
        DeclarationIrBuilder declarationIrBuilder3 = this.builder;
        if (declarationIrBuilder3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
        } else {
            declarationIrBuilder = declarationIrBuilder3;
        }
        return ExpressionHelpersKt.irFalse(declarationIrBuilder);
    }

    private final IrExpression narrowType(IrType fromType, IrType toType, IrExpression value) {
        if (Intrinsics.areEqual(fromType, toType)) {
            return value;
        }
        DeclarationIrBuilder declarationIrBuilder = null;
        if (Intrinsics.areEqual(toType, this.builtIns.getNothingNType())) {
            DeclarationIrBuilder declarationIrBuilder2 = this.builder;
            if (declarationIrBuilder2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
                declarationIrBuilder2 = null;
            }
            DeclarationIrBuilder declarationIrBuilder3 = declarationIrBuilder2;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder3.getContext(), declarationIrBuilder3.getScope(), declarationIrBuilder3.getStartOffset(), declarationIrBuilder3.getEndOffset(), null, this.builtIns.getNothingNType(), true);
            irBlockBuilder.unaryPlus(value);
            DeclarationIrBuilder declarationIrBuilder4 = this.builder;
            if (declarationIrBuilder4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder4;
            }
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irNull(declarationIrBuilder));
            return irBlockBuilder.getIrBlockBody();
        }
        if (Intrinsics.areEqual(toType, this.builtIns.getAnyNType()) && Intrinsics.areEqual(fromType, this.builtIns.getNothingNType()) && (value instanceof IrConst) && Intrinsics.areEqual(((IrConst) value).getKind(), IrConstKind.Null.INSTANCE)) {
            DeclarationIrBuilder declarationIrBuilder5 = this.builder;
            if (declarationIrBuilder5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder5;
            }
            return ExpressionHelpersKt.irNull(declarationIrBuilder, this.builtIns.getNothingNType());
        }
        if (isInlined(toType) && !isInlined(fromType)) {
            DeclarationIrBuilder declarationIrBuilder6 = this.builder;
            if (declarationIrBuilder6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder6;
            }
            IrMemberAccessExpression<?> irMemberAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, this.symbols.getUnboxIntrinsic(), toType, (List<? extends IrType>) CollectionsKt.listOf((Object[]) new IrType[]{fromType, toType}));
            irMemberAccessExpressionIrCall.putValueArgument(0, value);
            return irMemberAccessExpressionIrCall;
        }
        if (!isInlined(toType) && isInlined(fromType)) {
            DeclarationIrBuilder declarationIrBuilder7 = this.builder;
            if (declarationIrBuilder7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder7;
            }
            IrMemberAccessExpression<?> irMemberAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(declarationIrBuilder, this.symbols.getBoxIntrinsic(), toType, (List<? extends IrType>) CollectionsKt.listOf((Object[]) new IrType[]{fromType, toType}));
            irMemberAccessExpressionIrCall2.putValueArgument(0, value);
            return irMemberAccessExpressionIrCall2;
        }
        IrClass eraseToClassOrInterface = getEraseToClassOrInterface(fromType);
        IrClass eraseToClassOrInterface2 = getEraseToClassOrInterface(toType);
        if (eraseToClassOrInterface.getIsExternal() && eraseToClassOrInterface2.getIsExternal()) {
            return value;
        }
        if (org.jetbrains.kotlin.ir.util.IrUtilsKt.isNullConst(value) && eraseToClassOrInterface.getIsExternal() != eraseToClassOrInterface2.getIsExternal()) {
            value.setType(toType);
            return value;
        }
        if (eraseToClassOrInterface.getIsExternal() && !eraseToClassOrInterface2.getIsExternal()) {
            if (this.context.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS) == WasmTarget.WASI) {
                throw new NotImplementedError("An operation is not implemented: Implement externalize adapter for wasi mode");
            }
            DeclarationIrBuilder declarationIrBuilder8 = this.builder;
            if (declarationIrBuilder8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder8;
            }
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, getJsToKotlinAnyAdapter());
            irCall.putValueArgument(0, value);
            return narrowType(this.context.getIrBuiltIns().getAnyType(), toType, irCall);
        }
        if (eraseToClassOrInterface2.getIsExternal() && !eraseToClassOrInterface.getIsExternal()) {
            if (this.context.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS) == WasmTarget.WASI) {
                throw new NotImplementedError("An operation is not implemented: Implement internalize adapter for wasi mode");
            }
            DeclarationIrBuilder declarationIrBuilder9 = this.builder;
            if (declarationIrBuilder9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder9;
            }
            IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, getKotlinToJsAnyAdapter());
            irCall2.putValueArgument(0, value);
            return irCall2;
        }
        if (org.jetbrains.kotlin.ir.util.IrUtilsKt.isSubclassOf(eraseToClassOrInterface, eraseToClassOrInterface2)) {
            return value;
        }
        if (IrTypePredicatesKt.isNothing(toType)) {
            DeclarationIrBuilder declarationIrBuilder10 = this.builder;
            if (declarationIrBuilder10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder10;
            }
            DeclarationIrBuilder declarationIrBuilder11 = declarationIrBuilder;
            IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(declarationIrBuilder11.getContext(), declarationIrBuilder11.getScope(), declarationIrBuilder11.getStartOffset(), declarationIrBuilder11.getEndOffset(), null, this.context.getIrBuiltIns().getNothingType(), true);
            irBlockBuilder2.unaryPlus(value);
            irBlockBuilder2.unaryPlus(ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, this.symbols.getWasmUnreachable()));
            return irBlockBuilder2.getIrBlockBody();
        }
        if (Intrinsics.areEqual(toType, this.symbols.getVoidType())) {
            DeclarationIrBuilder declarationIrBuilder12 = this.builder;
            if (declarationIrBuilder12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder12;
            }
            IrCall irCall3 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, this.symbols.findVoidConsumer(value.getType()));
            irCall3.putValueArgument(0, value);
            return irCall3;
        }
        DeclarationIrBuilder declarationIrBuilder13 = this.builder;
        if (declarationIrBuilder13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
        } else {
            declarationIrBuilder = declarationIrBuilder13;
        }
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(declarationIrBuilder, this.symbols.getRefCastNull(), toType, 0, 0, null, 28, null);
        irCallIrCall$default.putTypeArgument(0, toType);
        irCallIrCall$default.putValueArgument(0, value);
        return irCallIrCall$default;
    }

    private final IrExpression lowerCast(IrTypeOperatorCall expression, boolean isSafe) {
        IrCall irCall;
        IrBlockBuilder irBlockBuilder;
        IrType typeOperand = expression.getTypeOperand();
        IrType type = expression.getArgument().getType();
        if (org.jetbrains.kotlin.ir.util.IrUtilsKt.isSubclassOf(getEraseToClassOrInterface(type), getEraseToClassOrInterface(expression.getType()))) {
            return narrowType(type, expression.getType(), expression.getArgument());
        }
        DeclarationIrBuilder declarationIrBuilder = null;
        if (isSafe) {
            DeclarationIrBuilder declarationIrBuilder2 = this.builder;
            if (declarationIrBuilder2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
                declarationIrBuilder2 = null;
            }
            irCall = ExpressionHelpersKt.irNull(declarationIrBuilder2);
        } else {
            DeclarationIrBuilder declarationIrBuilder3 = this.builder;
            if (declarationIrBuilder3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
                declarationIrBuilder3 = null;
            }
            irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder3, this.context.getIr().getSymbols().getThrowTypeCastException());
        }
        IrExpression irExpression = irCall;
        DeclarationIrBuilder declarationIrBuilder4 = this.builder;
        if (declarationIrBuilder4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            declarationIrBuilder4 = null;
        }
        DeclarationIrBuilder declarationIrBuilder5 = declarationIrBuilder4;
        IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(declarationIrBuilder5.getContext(), declarationIrBuilder5.getScope(), declarationIrBuilder5.getStartOffset(), declarationIrBuilder5.getEndOffset(), null, expression.getType(), true);
        Function0<IrExpression> function0CacheValue = cacheValue(irBlockBuilder2, expression.getArgument());
        IrExpression irExpressionNarrowType = narrowType(type, expression.getType(), function0CacheValue.invoke());
        IrExpression irExpressionGenerateTypeCheck = generateTypeCheck(function0CacheValue, typeOperand);
        if (irExpressionGenerateTypeCheck instanceof IrConst) {
            Object value = ((IrConst) irExpressionGenerateTypeCheck).getValue();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
            if (((Boolean) value).booleanValue()) {
                irBlockBuilder2.unaryPlus(irExpressionNarrowType);
            } else {
                irBlockBuilder2.unaryPlus(irExpression);
            }
            irBlockBuilder = irBlockBuilder2;
        } else {
            DeclarationIrBuilder declarationIrBuilder6 = this.builder;
            if (declarationIrBuilder6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder6;
            }
            irBlockBuilder = irBlockBuilder2;
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilder, expression.getType(), irExpressionGenerateTypeCheck, irExpressionNarrowType, irExpression, null, 16, null));
        }
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression lowerImplicitCast(IrTypeOperatorCall expression) {
        return narrowType(expression.getArgument().getType(), expression.getTypeOperand(), expression.getArgument());
    }

    private final IrExpression generateTypeCheckWithTypeParameter(IrExpression argument, IrType toType) {
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(toType);
        IrSymbolOwner owner = classifierOrNull != null ? classifierOrNull.getOwner() : null;
        IrTypeParameter irTypeParameter = owner instanceof IrTypeParameter ? (IrTypeParameter) owner : null;
        if (irTypeParameter == null) {
            throw new IllegalStateException(("expected type parameter, but got " + toType).toString());
        }
        List<IrType> superTypes = irTypeParameter.getSuperTypes();
        DeclarationIrBuilder declarationIrBuilder = this.builder;
        if (declarationIrBuilder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            declarationIrBuilder = null;
        }
        IrConstImpl<Boolean> irConstImplIrTrue = ExpressionHelpersKt.irTrue(declarationIrBuilder);
        Intrinsics.checkNotNull(irConstImplIrTrue, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        IrCall irCall = irConstImplIrTrue;
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            IrExpression irExpressionGenerateTypeCheckNonNull = generateTypeCheckNonNull(org.jetbrains.kotlin.ir.util.IrUtilsKt.shallowCopy(argument), IrTypesKt.makeNotNull((IrType) it.next()));
            DeclarationIrBuilder declarationIrBuilder2 = this.builder;
            if (declarationIrBuilder2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
                declarationIrBuilder2 = null;
            }
            IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder2, this.symbols.getBooleanAnd());
            irCall2.putValueArgument(0, irCall);
            irCall2.putValueArgument(1, irExpressionGenerateTypeCheckNonNull);
            irCall = irCall2;
        }
        return irCall;
    }

    private final IrExpression generateIsInterface(IrExpression argument, IrType toType) {
        DeclarationIrBuilder declarationIrBuilder = this.builder;
        if (declarationIrBuilder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            declarationIrBuilder = null;
        }
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, this.symbols.getWasmIsInterface());
        irCall.putValueArgument(0, argument);
        irCall.putTypeArgument(0, toType);
        return irCall;
    }

    private final IrExpression generateIsSubClass(IrExpression argument, IrType toType) {
        IrClass runtimeClass = TypeTransformerKt.getRuntimeClass(argument.getType(), this.context.getIrBuiltIns());
        IrClass runtimeClass2 = TypeTransformerKt.getRuntimeClass(toType, this.context.getIrBuiltIns());
        DeclarationIrBuilder declarationIrBuilder = null;
        if (org.jetbrains.kotlin.ir.util.IrUtilsKt.isSubclassOf(runtimeClass, runtimeClass2)) {
            DeclarationIrBuilder declarationIrBuilder2 = this.builder;
            if (declarationIrBuilder2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder2;
            }
            return ExpressionHelpersKt.irTrue(declarationIrBuilder);
        }
        if (!org.jetbrains.kotlin.ir.util.IrUtilsKt.isSubclassOf(runtimeClass2, runtimeClass)) {
            DeclarationIrBuilder declarationIrBuilder3 = this.builder;
            if (declarationIrBuilder3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("builder");
            } else {
                declarationIrBuilder = declarationIrBuilder3;
            }
            return ExpressionHelpersKt.irFalse(declarationIrBuilder);
        }
        DeclarationIrBuilder declarationIrBuilder4 = this.builder;
        if (declarationIrBuilder4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
        } else {
            declarationIrBuilder = declarationIrBuilder4;
        }
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, this.symbols.getRefTest());
        irCall.putValueArgument(0, argument);
        irCall.putTypeArgument(0, toType);
        return irCall;
    }

    private final IrExpression generateIsExternalClass(IrExpression argument, IrClass klass) {
        IrSimpleFunction irSimpleFunction = this.context.getMapping().getWasmExternalClassToInstanceCheck().get(klass);
        Intrinsics.checkNotNull(irSimpleFunction);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        IrSimpleFunction irSimpleFunction3 = this.context.getMapping().getWasmJsInteropFunctionToWrapper().get(irSimpleFunction2);
        if (irSimpleFunction3 != null) {
            irSimpleFunction2 = irSimpleFunction3;
        }
        DeclarationIrBuilder declarationIrBuilder = this.builder;
        if (declarationIrBuilder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            declarationIrBuilder = null;
        }
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, irSimpleFunction2);
        irFunctionAccessExpressionIrCall.putValueArgument(0, narrowType(argument.getType(), this.context.getIrBuiltIns().getAnyType(), argument));
        return irFunctionAccessExpressionIrCall;
    }
}
