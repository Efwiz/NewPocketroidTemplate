package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrArithBuilder;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsAstUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JsBridgesConstruction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J&\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0014J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u0019H\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0019H\u0016J0\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0014*\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020!0%H\u0002J2\u0010&\u001a\u00020!*\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010'\u001a\u00020#2\u0006\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020!0%H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\b0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsBridgesConstruction;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/BridgesConstruction;", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "context", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "calculator", "Lorg/jetbrains/kotlin/ir/backend/js/ir/JsIrArithBuilder;", "jsArguments", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "jsArrayGet", "jsArrayLength", "jsArrayLike2Array", "jsSliceArrayLikeFromIndex", "jsSliceArrayLikeFromIndexToIndex", "primitiveArrays", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "primitiveToLiteralConstructor", "extractValueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "blockBodyBuilder", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "bridge", "getBridgeOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getFunctionSignature", "", "function", "createVarsForTrailingParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "numberOfTrailingParameters", "", "firstTrailingParameterIndexVar", "Lkotlin/Lazy;", "emitCopyVarargToArray", "varargIndex", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsBridgesConstruction extends BridgesConstruction<JsIrBackendContext> {
    private final JsIrArithBuilder calculator;
    private final IrSimpleFunctionSymbol jsArguments;
    private final IrSimpleFunctionSymbol jsArrayGet;
    private final IrSimpleFunctionSymbol jsArrayLength;
    private final IrSimpleFunctionSymbol jsArrayLike2Array;
    private final IrSimpleFunctionSymbol jsSliceArrayLikeFromIndex;
    private final IrSimpleFunctionSymbol jsSliceArrayLikeFromIndexToIndex;
    private final Map<IrClassSymbol, PrimitiveType> primitiveArrays;
    private final Map<PrimitiveType, IrSimpleFunctionSymbol> primitiveToLiteralConstructor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsBridgesConstruction(JsIrBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.calculator = new JsIrArithBuilder(context);
        this.jsArguments = context.getIntrinsics().getJsArguments();
        this.jsArrayGet = context.getIntrinsics().getJsArrayGet();
        this.jsArrayLength = context.getIntrinsics().getJsArrayLength();
        this.jsArrayLike2Array = context.getIntrinsics().getJsArrayLike2Array();
        this.jsSliceArrayLikeFromIndex = context.getIntrinsics().getJsSliceArrayLikeFromIndex();
        this.jsSliceArrayLikeFromIndexToIndex = context.getIntrinsics().getJsSliceArrayLikeFromIndexToIndex();
        this.primitiveArrays = context.getIntrinsics().getPrimitiveArrays();
        this.primitiveToLiteralConstructor = context.getIntrinsics().getPrimitiveToLiteralConstructor();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.BridgesConstruction
    public String getFunctionSignature(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return NameTablesKt.jsFunctionSignature(function, getContext());
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.BridgesConstruction
    public IrDeclarationOrigin getBridgeOrigin(IrSimpleFunction bridge) {
        Intrinsics.checkNotNullParameter(bridge, "bridge");
        if (MiscKt.hasStableJsName(bridge, getContext())) {
            return JsLoweredDeclarationOrigin.BRIDGE_WITH_STABLE_NAME.INSTANCE;
        }
        return bridge.getCorrespondingPropertySymbol() != null ? JsLoweredDeclarationOrigin.BRIDGE_PROPERTY_ACCESSOR.INSTANCE : JsLoweredDeclarationOrigin.BRIDGE_WITHOUT_STABLE_NAME.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.BridgesConstruction
    protected List<IrValueDeclaration> extractValueParameters(final IrBlockBodyBuilder blockBodyBuilder, IrSimpleFunction irFunction, IrSimpleFunction bridge) {
        Intrinsics.checkNotNullParameter(blockBodyBuilder, "blockBodyBuilder");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(bridge, "bridge");
        if (!IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) bridge)) {
            return super.extractValueParameters(blockBodyBuilder, irFunction, bridge);
        }
        int iVarargParameterIndex = JsAstUtilsKt.varargParameterIndex(bridge);
        if (iVarargParameterIndex == -1) {
            return super.extractValueParameters(blockBodyBuilder, irFunction, bridge);
        }
        final int size = bridge.getValueParameters().size() - (iVarargParameterIndex + 1);
        IrBlockBodyBuilder irBlockBodyBuilder = blockBodyBuilder;
        final IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder, this.jsArrayLength);
        irCall.putValueArgument(0, ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder, this.jsArguments));
        irCall.setType(blockBodyBuilder.getContext().getIrBuiltIns().getIntType());
        Lazy<? extends IrVariable> lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrVariable>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.JsBridgesConstruction$extractValueParameters$1$firstTrailingParameterIndexVar$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrVariable invoke() {
                IrCall irCallSub;
                IrBlockBodyBuilder irBlockBodyBuilder2 = blockBodyBuilder;
                if (size == 0) {
                    irCallSub = irCall;
                } else {
                    irCallSub = this.calculator.sub(irCall, ExpressionHelpersKt.irInt$default(blockBodyBuilder, size, null, 2, null));
                }
                return ExpressionHelpersKt.irTemporary$default(irBlockBodyBuilder2, irCallSub, "firstTrailingParameterIndex", null, false, null, 28, null);
            }
        });
        IrVariable irVariableEmitCopyVarargToArray = emitCopyVarargToArray(blockBodyBuilder, bridge, iVarargParameterIndex, size, lazy);
        return CollectionsKt.plus((Collection) CollectionsKt.plus((Collection<? extends IrVariable>) irFunction.getValueParameters(), irVariableEmitCopyVarargToArray), (Iterable) createVarsForTrailingParameters(blockBodyBuilder, bridge, size, lazy));
    }

    private final List<IrVariable> createVarsForTrailingParameters(IrBlockBodyBuilder irBlockBodyBuilder, IrSimpleFunction irSimpleFunction, int i, Lazy<? extends IrVariable> lazy) {
        IrGetValueImpl irGetValueImplAdd;
        List listTakeLast = CollectionsKt.takeLast(irSimpleFunction.getValueParameters(), i);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listTakeLast, 10));
        int i2 = 0;
        for (Object obj : listTakeLast) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) obj;
            if (i2 == 0) {
                irGetValueImplAdd = ExpressionHelpersKt.irGet(irBlockBodyBuilder, lazy.getValue());
            } else {
                IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                irGetValueImplAdd = this.calculator.add(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, lazy.getValue()), ExpressionHelpersKt.irInt$default(irBlockBodyBuilder2, i2, null, 2, null));
            }
            IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder3, this.jsArrayGet);
            irCall.putValueArgument(0, ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder3, this.jsArguments));
            irCall.putValueArgument(1, irGetValueImplAdd);
            arrayList.add(IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBodyBuilder, (IrExpression) irCall, irValueParameter.getName().asString(), false, (IrDeclarationOrigin) null, irValueParameter.getType(), 12, (Object) null));
            i2 = i3;
        }
        return arrayList;
    }

    private final IrVariable emitCopyVarargToArray(IrBlockBodyBuilder irBlockBodyBuilder, IrSimpleFunction irSimpleFunction, int i, int i2, Lazy<? extends IrVariable> lazy) {
        IrCall irCallBoxArrayIfNeeded;
        IrValueParameter irValueParameter = irSimpleFunction.getValueParameters().get(i);
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        List listMutableListOf = CollectionsKt.mutableListOf(ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, this.jsArguments));
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.jsArrayLike2Array;
        if (i != 0 || i2 > 0) {
            listMutableListOf.add(ExpressionHelpersKt.irInt$default(irBlockBodyBuilder2, i, null, 2, null));
            irSimpleFunctionSymbol = this.jsSliceArrayLikeFromIndex;
        }
        if (i2 > 0) {
            listMutableListOf.add(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, lazy.getValue()));
            irSimpleFunctionSymbol = this.jsSliceArrayLikeFromIndexToIndex;
        }
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, irSimpleFunctionSymbol);
        IrType varargElementType = irValueParameter.getVarargElementType();
        Intrinsics.checkNotNull(varargElementType);
        irCall.putTypeArgument(0, varargElementType);
        int i3 = 0;
        for (Object obj : listMutableListOf) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irCall.putValueArgument(i3, (IrExpression) obj);
            i3 = i4;
        }
        JsIrBackendContext context = getContext();
        IrType varargElementType2 = irValueParameter.getVarargElementType();
        Intrinsics.checkNotNull(varargElementType2);
        InlineClassArrayInfo inlineClassArrayInfo = new InlineClassArrayInfo(context, varargElementType2, irValueParameter.getType());
        PrimitiveType primitiveType = this.primitiveArrays.get(IrTypesKt.getClassifierOrNull(inlineClassArrayInfo.getPrimitiveArrayType()));
        if (primitiveType != null) {
            IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, (IrSimpleFunctionSymbol) MapsKt.getValue(this.primitiveToLiteralConstructor, primitiveType));
            irCall2.putValueArgument(0, irCall);
            irCall2.setType(irValueParameter.getType());
            irCallBoxArrayIfNeeded = inlineClassArrayInfo.boxArrayIfNeeded(irCall2);
        } else {
            irCallBoxArrayIfNeeded = irCall;
        }
        return IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBodyBuilder, irCallBoxArrayIfNeeded, irValueParameter.getName().asString(), false, (IrDeclarationOrigin) null, (IrType) null, 28, (Object) null);
    }
}
