package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.lower.calls.EnumIntrinsicsUtils;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.backend.js.utils.TypeTranformerKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;

/* JADX INFO: compiled from: BuiltInsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\f\u0010\u0015\u001a\u00020\u0016*\u00020\u0017H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/BuiltInsLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "irBuiltins", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "transformCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "findEqualsMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltInsLowering implements FileLoweringPass {
    private final WasmBackendContext context;
    private final IrBuiltIns irBuiltins;
    private final WasmSymbols symbols;

    public BuiltInsLowering(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.irBuiltins = context.getIrBuiltIns();
        this.symbols = context.getWasmSymbols();
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    private final IrSimpleFunction findEqualsMethod(IrType irType) {
        IrClass owner = IrTypesKt.getClass(irType);
        if (owner == null) {
            owner = this.irBuiltins.getAnyClass().getOwner();
        }
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(owner)) {
            if (MiscKt.isEqualsInheritedFromAny(irSimpleFunction2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        return irSimpleFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformCall(IrCall call, DeclarationIrBuilder builder) {
        IrClass owner;
        FqName fqNameParentOrNull;
        String strAsString;
        IrSimpleFunctionSymbol irSimpleFunctionSymbolFindContentToStringOverload;
        IrType stringType;
        IrSimpleFunctionSymbol symbol = call.getSymbol();
        if (Intrinsics.areEqual(symbol, this.irBuiltins.getIeee754equalsFunByOperandType().get(this.irBuiltins.getFloatClass()))) {
            IrExpression valueArgument = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument);
            if (!IrTypeUtilsKt.isNullable(valueArgument.getType())) {
                IrExpression valueArgument2 = call.getValueArgument(1);
                Intrinsics.checkNotNull(valueArgument2);
                if (!IrTypeUtilsKt.isNullable(valueArgument2.getType())) {
                    return IrUtilsKt.irCall(call, (IrSimpleFunctionSymbol) MapsKt.getValue(this.symbols.getFloatEqualityFunctions(), this.irBuiltins.getFloatType()), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
                }
            }
            return IrUtilsKt.irCall(call, this.symbols.getNullableFloatIeee754Equals(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        }
        if (Intrinsics.areEqual(symbol, this.irBuiltins.getIeee754equalsFunByOperandType().get(this.irBuiltins.getDoubleClass()))) {
            IrExpression valueArgument3 = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument3);
            if (!IrTypeUtilsKt.isNullable(valueArgument3.getType())) {
                IrExpression valueArgument4 = call.getValueArgument(1);
                Intrinsics.checkNotNull(valueArgument4);
                if (!IrTypeUtilsKt.isNullable(valueArgument4.getType())) {
                    return IrUtilsKt.irCall(call, (IrSimpleFunctionSymbol) MapsKt.getValue(this.symbols.getFloatEqualityFunctions(), this.irBuiltins.getDoubleType()), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
                }
            }
            return IrUtilsKt.irCall(call, this.symbols.getNullableDoubleIeee754Equals(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        }
        if (Intrinsics.areEqual(symbol, this.irBuiltins.getEqeqSymbol()) ? true : Intrinsics.areEqual(symbol, this.irBuiltins.getEqeqeqSymbol())) {
            IrExpression valueArgument5 = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument5);
            IrExpression valueArgument6 = call.getValueArgument(1);
            Intrinsics.checkNotNull(valueArgument6);
            if (IrUtilsKt.isNullConst(valueArgument5)) {
                return transformCall$callRefIsNull(this, builder, valueArgument6);
            }
            if (IrUtilsKt.isNullConst(valueArgument6)) {
                return transformCall$callRefIsNull(this, builder, valueArgument5);
            }
            IrType type = valueArgument5.getType();
            if (Intrinsics.areEqual(type, valueArgument6.getType())) {
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.symbols.getEqualityFunctions().get(type);
                if (irSimpleFunctionSymbol == null) {
                    irSimpleFunctionSymbol = call.getSymbol() == this.irBuiltins.getEqeqeqSymbol() ? this.symbols.getFloatEqualityFunctions().get(type) : null;
                }
                IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = irSimpleFunctionSymbol;
                if (irSimpleFunctionSymbol2 != null) {
                    return IrUtilsKt.irCall(call, irSimpleFunctionSymbol2, (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
                }
            }
            if (call.getSymbol() != this.irBuiltins.getEqeqSymbol() || IrTypeUtilsKt.isNullable(type)) {
                return IrUtilsKt.irCall(call, call.getSymbol() == this.irBuiltins.getEqeqeqSymbol() ? this.symbols.getRefEq() : this.symbols.getNullableEquals(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
            }
            return IrUtilsKt.irCall(call, findEqualsMethod(type).getSymbol(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : true, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        }
        if (Intrinsics.areEqual(symbol, this.irBuiltins.getCheckNotNullSymbol())) {
            IrExpression valueArgument7 = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument7);
            if (IrUtilsKt.isNullConst(valueArgument7)) {
                return ExpressionHelpersKt.irCall((IrBuilderWithScope) builder, this.symbols.getThrowNullPointerException());
            }
            DeclarationIrBuilder declarationIrBuilder = builder;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset(), null, null, true);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, valueArgument7, null, null, false, null, 30, null);
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(declarationIrBuilder, IrTypesKt.makeNotNull(valueArgument7.getType()), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default), ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, this.symbols.getThrowNullPointerException()), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default)));
            return irBlockBuilder.getIrBlockBody();
        }
        if (this.symbols.getComparisonBuiltInsToWasmIntrinsics().keySet().contains(symbol)) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = this.symbols.getComparisonBuiltInsToWasmIntrinsics().get(symbol);
            Intrinsics.checkNotNull(irSimpleFunctionSymbol3);
            return IrUtilsKt.irCall(call, irSimpleFunctionSymbol3, (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        }
        if (Intrinsics.areEqual(symbol, this.irBuiltins.getNoWhenBranchMatchedExceptionSymbol())) {
            return ExpressionHelpersKt.irCall$default(builder, this.symbols.getThrowNoBranchMatchedException(), this.irBuiltins.getNothingType(), 0, 0, null, 28, null);
        }
        if (Intrinsics.areEqual(symbol, this.irBuiltins.getIllegalArgumentExceptionSymbol())) {
            IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(builder, this.symbols.getThrowIAE(), this.irBuiltins.getNothingType(), 1, 0, null, 24, null);
            IrExpression valueArgument8 = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument8);
            irCallIrCall$default.putValueArgument(0, valueArgument8);
            return irCallIrCall$default;
        }
        if (Intrinsics.areEqual(symbol, this.irBuiltins.getDataClassArrayMemberHashCodeSymbol()) ? true : Intrinsics.areEqual(symbol, this.irBuiltins.getDataClassArrayMemberToStringSymbol())) {
            IrExpression valueArgument9 = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument9);
            IrType type2 = valueArgument9.getType();
            if (Intrinsics.areEqual(symbol, this.irBuiltins.getDataClassArrayMemberHashCodeSymbol())) {
                irSimpleFunctionSymbolFindContentToStringOverload = this.symbols.findContentHashCodeOverload(type2);
                stringType = this.irBuiltins.getIntType();
            } else {
                irSimpleFunctionSymbolFindContentToStringOverload = this.symbols.findContentToStringOverload(type2);
                stringType = this.irBuiltins.getStringType();
            }
            IrCall irCallIrCall$default2 = ExpressionHelpersKt.irCall$default(builder, irSimpleFunctionSymbolFindContentToStringOverload, stringType, 0, 0, null, 28, null);
            irCallIrCall$default2.setExtensionReceiver(valueArgument9);
            if (Intrinsics.areEqual(IrTypesKt.getClassOrNull(type2), this.irBuiltins.getArrayClass())) {
                irCallIrCall$default2.putTypeArgument(0, IrTypeUtilsKt.getArrayElementType(type2, this.irBuiltins));
            }
            return irCallIrCall$default2;
        }
        if (this.symbols.getStartCoroutineUninterceptedOrReturnIntrinsics().contains(symbol)) {
            IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(this.irBuiltins.suspendFunctionN(this.symbols.getStartCoroutineUninterceptedOrReturnIntrinsics().indexOf(symbol)), "invoke");
            Intrinsics.checkNotNull(simpleFunction);
            return IrUtilsKt.irCall(call, simpleFunction, (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : true, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        }
        if (Intrinsics.areEqual(symbol, this.symbols.getReflectionSymbols().getGetClassData())) {
            IrType typeArgument = call.getTypeArgument(0);
            Intrinsics.checkNotNull(typeArgument);
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(typeArgument);
            if (classOrNull == null || (owner = classOrNull.getOwner()) == null) {
                throw new IllegalStateException("Invalid type".toString());
            }
            DeclarationIrBuilder declarationIrBuilder2 = builder;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder2, this.symbols.getWasmTypeId());
            irCall.putTypeArgument(0, typeArgument);
            if (!IrUtilsKt.isInterface(owner)) {
                IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder2, this.context.getWasmSymbols().getReflectionSymbols().getGetTypeInfoTypeDataByPtr());
                irCall2.putValueArgument(0, irCall);
                return irCall2;
            }
            IrConstructorSymbol irConstructorSymbol = (IrConstructorSymbol) SequencesKt.first(IrUtilsKt.getConstructors(this.symbols.getReflectionSymbols().getWasmTypeInfoData()));
            FqName classFqName = IrTypesKt.getClassFqName(typeArgument);
            Intrinsics.checkNotNull(classFqName);
            String str = (!((Boolean) CommonConfigurationKeysKt.getLanguageVersionSettings(this.context.getConfiguration()).getFlag(AnalysisFlags.getAllowFullyQualifiedNameInKClass())).booleanValue() || (fqNameParentOrNull = FqNamesUtilKt.parentOrNull(classFqName)) == null || (strAsString = fqNameParentOrNull.asString()) == null) ? "" : strAsString;
            String strAsString2 = classFqName.shortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            IrConstructorCall irConstructorCallIrCallConstructor = ExpressionHelpersKt.irCallConstructor(declarationIrBuilder2, irConstructorSymbol, CollectionsKt.emptyList());
            irConstructorCallIrCallConstructor.putValueArgument(0, irCall);
            irConstructorCallIrCallConstructor.putValueArgument(1, IrUtilsKt.toIrConst$default(str, builder.getContext().getIrBuiltIns().getStringType(), 0, 0, 6, null));
            irConstructorCallIrCallConstructor.putValueArgument(2, IrUtilsKt.toIrConst$default(strAsString2, builder.getContext().getIrBuiltIns().getStringType(), 0, 0, 6, null));
            return irConstructorCallIrCallConstructor;
        }
        if (Intrinsics.areEqual(symbol, this.symbols.getEnumValueOfIntrinsic())) {
            return EnumIntrinsicsUtils.INSTANCE.transformEnumValueOfIntrinsic(call);
        }
        if (Intrinsics.areEqual(symbol, this.symbols.getEnumValuesIntrinsic())) {
            return EnumIntrinsicsUtils.INSTANCE.transformEnumValuesIntrinsic(call);
        }
        if (Intrinsics.areEqual(symbol, this.symbols.getEnumEntriesIntrinsic())) {
            return EnumIntrinsicsUtils.INSTANCE.transformEnumEntriesIntrinsic(call);
        }
        return call;
    }

    private static final IrCall transformCall$callRefIsNull(BuiltInsLowering builtInsLowering, DeclarationIrBuilder declarationIrBuilder, IrExpression irExpression) {
        IrClass erasedUpperBound;
        if (builtInsLowering.context.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS) == WasmTarget.WASI && (erasedUpperBound = TypeTranformerKt.getErasedUpperBound(irExpression.getType())) != null && erasedUpperBound.getIsExternal()) {
            throw new IllegalStateException("Unexpected external refs in wasi mode".toString());
        }
        IrClass erasedUpperBound2 = TypeTranformerKt.getErasedUpperBound(irExpression.getType());
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, (erasedUpperBound2 == null || !erasedUpperBound2.getIsExternal()) ? builtInsLowering.symbols.getRefIsNull() : builtInsLowering.symbols.getJsRelatedSymbols().getExternRefIsNull());
        irCall.putValueArgument(0, irExpression);
        return irCall;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        final DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irFile.getSymbol(), 0, 0, 6, (Object) null);
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, new IrElementTransformerVoidWithContext() { // from class: org.jetbrains.kotlin.backend.wasm.lower.BuiltInsLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrExpression irExpressionTransformCall = BuiltInsLowering.this.transformCall(expression, declarationIrBuilderCreateIrBuilder$default);
                IrElementTransformerVoidKt.transformChildrenVoid(irExpressionTransformCall, this);
                return irExpressionTransformCall;
            }
        });
    }
}
