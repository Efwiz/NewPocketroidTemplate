package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.JsIntrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.JsInlineClassesUtils;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: EqualityAndComparisonCallsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000fH\u0002J \u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0010H\u0002J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000fH\u0002J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000fH\u0002J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000fH\u0002J\u0010\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u001bH\u0002J\u0018\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u001c\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e2\u0006\u0010 \u001a\u00020!H\u0002J\f\u0010\"\u001a\u00020\u001e*\u00020\u000fH\u0002J\f\u0010#\u001a\u00020\u001e*\u00020$H\u0002J\u000e\u0010%\u001a\u0004\u0018\u00010&*\u00020'H\u0002J\f\u0010(\u001a\u00020'*\u00020'H\u0002J\f\u0010)\u001a\u00020\u001e*\u00020\u0010H\u0002J\f\u0010*\u001a\u00020\u001e*\u00020$H\u0002J\f\u0010+\u001a\u00020\u001e*\u00020\u0010H\u0002J\f\u0010,\u001a\u00020\u001e*\u00020'H\u0002J\f\u0010-\u001a\u00020\u001e*\u00020.H\u0002J\f\u0010/\u001a\u00020\u001e*\u00020.H\u0002J\u001a\u00100\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010201*\u00020\u0010H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e0\fj\u0002`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/EqualityAndComparisonCallsTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "icUtils", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsInlineClassesUtils;", "intrinsics", "Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolToTransformer", "", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/SymbolToTransformer;", "chooseEqualityOperatorForPrimitiveTypes", "call", "optimizeInlineClassEquality", "lhs", "rhs", "transformCompareToMethodCall", "transformEqeqOperator", "transformEqeqeqOperator", "transformEqualsMethodCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "transformFunctionAccess", "doNotIntrinsify", "", "transformLongComparison", "comparator", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "allValueArgumentsAreNullable", "canBeUsedWithJsEq", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/PrimitiveType;", "findEqualsMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getLowestUnderlyingType", "isBoxIntrinsic", "isBuiltin", "isCharBoxing", "isDefaultEqualsMethod", "isMethodOfPotentiallyPrimitiveJSType", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isMethodOfPrimitiveJSType", "unboxParamWithInlinedClass", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EqualityAndComparisonCallsTransformer implements CallsTransformer {
    private final JsInlineClassesUtils icUtils;
    private final JsIntrinsics intrinsics;
    private final IrBuiltIns irBuiltIns;
    private final Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>> symbolToTransformer;

    public EqualityAndComparisonCallsTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        JsIntrinsics intrinsics = context.getIntrinsics();
        this.intrinsics = intrinsics;
        IrBuiltIns irBuiltIns = context.getIrBuiltIns();
        this.irBuiltIns = irBuiltIns;
        this.icUtils = context.getInlineClassesUtils();
        HashMap map = new HashMap();
        this.symbolToTransformer = map;
        CallsLoweringUtilsKt.add(map, irBuiltIns.getEqeqeqSymbol(), new EqualityAndComparisonCallsTransformer$1$1(this));
        CallsLoweringUtilsKt.add(map, irBuiltIns.getEqeqSymbol(), new EqualityAndComparisonCallsTransformer$1$2(this));
        CallsLoweringUtilsKt.add(map, irBuiltIns.getIeee754equalsFunByOperandType(), new EqualityAndComparisonCallsTransformer$1$3(this));
        CallsLoweringUtilsKt.add$default(map, irBuiltIns.getBooleanNotSymbol(), intrinsics.getJsNot(), false, 4, null);
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> lessFunByOperandType = irBuiltIns.getLessFunByOperandType();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<IrClassifierSymbol, IrSimpleFunctionSymbol> entry : lessFunByOperandType.entrySet()) {
            if (!Intrinsics.areEqual(entry.getKey(), this.irBuiltIns.getLongClass())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        CallsLoweringUtilsKt.add(map, linkedHashMap, this.intrinsics.getJsLt());
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> lessOrEqualFunByOperandType = this.irBuiltIns.getLessOrEqualFunByOperandType();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry<IrClassifierSymbol, IrSimpleFunctionSymbol> entry2 : lessOrEqualFunByOperandType.entrySet()) {
            if (!Intrinsics.areEqual(entry2.getKey(), this.irBuiltIns.getLongClass())) {
                linkedHashMap2.put(entry2.getKey(), entry2.getValue());
            }
        }
        CallsLoweringUtilsKt.add(map, linkedHashMap2, this.intrinsics.getJsLtEq());
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> greaterFunByOperandType = this.irBuiltIns.getGreaterFunByOperandType();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (Map.Entry<IrClassifierSymbol, IrSimpleFunctionSymbol> entry3 : greaterFunByOperandType.entrySet()) {
            if (!Intrinsics.areEqual(entry3.getKey(), this.irBuiltIns.getLongClass())) {
                linkedHashMap3.put(entry3.getKey(), entry3.getValue());
            }
        }
        CallsLoweringUtilsKt.add(map, linkedHashMap3, this.intrinsics.getJsGt());
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> greaterOrEqualFunByOperandType = this.irBuiltIns.getGreaterOrEqualFunByOperandType();
        LinkedHashMap linkedHashMap4 = new LinkedHashMap();
        for (Map.Entry<IrClassifierSymbol, IrSimpleFunctionSymbol> entry4 : greaterOrEqualFunByOperandType.entrySet()) {
            if (!Intrinsics.areEqual(entry4.getKey(), this.irBuiltIns.getLongClass())) {
                linkedHashMap4.put(entry4.getKey(), entry4.getValue());
            }
        }
        CallsLoweringUtilsKt.add(map, linkedHashMap4, this.intrinsics.getJsGtEq());
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.irBuiltIns.getLessFunByOperandType().get(this.irBuiltIns.getLongClass());
        Intrinsics.checkNotNull(irSimpleFunctionSymbol);
        CallsLoweringUtilsKt.add(map, irSimpleFunctionSymbol, transformLongComparison(this.intrinsics.getJsLt()));
        IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = this.irBuiltIns.getLessOrEqualFunByOperandType().get(this.irBuiltIns.getLongClass());
        Intrinsics.checkNotNull(irSimpleFunctionSymbol2);
        CallsLoweringUtilsKt.add(map, irSimpleFunctionSymbol2, transformLongComparison(this.intrinsics.getJsLtEq()));
        IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = this.irBuiltIns.getGreaterFunByOperandType().get(this.irBuiltIns.getLongClass());
        Intrinsics.checkNotNull(irSimpleFunctionSymbol3);
        CallsLoweringUtilsKt.add(map, irSimpleFunctionSymbol3, transformLongComparison(this.intrinsics.getJsGt()));
        IrSimpleFunctionSymbol irSimpleFunctionSymbol4 = this.irBuiltIns.getGreaterOrEqualFunByOperandType().get(this.irBuiltIns.getLongClass());
        Intrinsics.checkNotNull(irSimpleFunctionSymbol4);
        CallsLoweringUtilsKt.add(map, irSimpleFunctionSymbol4, transformLongComparison(this.intrinsics.getJsGtEq()));
    }

    private final Function1<IrFunctionAccessExpression, IrExpression> transformLongComparison(final IrSimpleFunctionSymbol comparator) {
        return new Function1<IrFunctionAccessExpression, IrCallImpl>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.EqualityAndComparisonCallsTransformer.transformLongComparison.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrCallImpl invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                IrCallImpl irCallImpl = new IrCallImpl(call.getStartOffset(), call.getEndOffset(), comparator.getOwner().getReturnType(), comparator, 0, 2, null, null, 192, null);
                EqualityAndComparisonCallsTransformer equalityAndComparisonCallsTransformer = this;
                irCallImpl.putValueArgument(0, IrUtilsKt.irCall(call, equalityAndComparisonCallsTransformer.intrinsics.getLongCompareToLong(), (56 & 4) != 0 ? false : false, (56 & 8) != 0 ? false : true, (56 & 16) != 0 ? null : null, (56 & 32) != 0 ? null : null));
                irCallImpl.putValueArgument(1, JsIrBuilder.INSTANCE.buildInt(equalityAndComparisonCallsTransformer.irBuiltIns.getIntType(), 0));
                return irCallImpl;
            }
        };
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        IrFunctionSymbol symbol = call.getSymbol();
        Function1<IrFunctionAccessExpression, IrExpression> function1 = this.symbolToTransformer.get(symbol);
        if (function1 != null) {
            return function1.invoke(call);
        }
        Name name2 = symbol.getOwner().getName();
        if (Intrinsics.areEqual(name2, Name.identifier("compareTo"))) {
            return doNotIntrinsify ? call : transformCompareToMethodCall(call);
        }
        return Intrinsics.areEqual(name2, Name.identifier(Namer.EQUALS_METHOD_NAME)) ? transformEqualsMethodCall((IrCall) call) : call;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformEqeqeqOperator(IrFunctionAccessExpression call) {
        IrExpression valueArgument = call.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        IrExpression valueArgument2 = call.getValueArgument(1);
        Intrinsics.checkNotNull(valueArgument2);
        if (isCharBoxing(valueArgument) && isCharBoxing(valueArgument2)) {
            return optimizeInlineClassEquality(call, valueArgument, valueArgument2);
        }
        return IrUtilsKt.irCall(call, this.intrinsics.getJsEqeqeq(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
    }

    private final boolean isCharBoxing(IrExpression irExpression) {
        if (!(irExpression instanceof IrCall)) {
            return false;
        }
        IrCall irCall = (IrCall) irExpression;
        if (!Intrinsics.areEqual(irCall.getSymbol(), this.intrinsics.getJsBoxIntrinsic())) {
            return false;
        }
        IrExpression valueArgument = irCall.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        return IrTypePredicatesKt.isChar(valueArgument.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformEqeqOperator(IrFunctionAccessExpression call) {
        IrExpression valueArgument = call.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        IrExpression valueArgument2 = call.getValueArgument(1);
        Intrinsics.checkNotNull(valueArgument2);
        PrimitiveType primitiveType = CallsLoweringUtilsKt.getPrimitiveType(valueArgument.getType());
        PrimitiveType primitiveType2 = CallsLoweringUtilsKt.getPrimitiveType(valueArgument2.getType());
        IrSimpleFunction irSimpleFunctionFindEqualsMethod = findEqualsMethod(valueArgument.getType());
        if (!(valueArgument.getType() instanceof IrDynamicType)) {
            if (!IrUtilsKt.isNullConst(valueArgument) && !IrUtilsKt.isNullConst(valueArgument2)) {
                if (primitiveType == primitiveType2 && canBeUsedWithJsEq(primitiveType)) {
                    return chooseEqualityOperatorForPrimitiveTypes(call);
                }
                if (isBuiltin(primitiveType) || IrTypeUtilsKt.isNullable(valueArgument.getType()) || irSimpleFunctionFindEqualsMethod == null) {
                    if (isBoxIntrinsic(valueArgument) && isBoxIntrinsic(valueArgument2)) {
                        return optimizeInlineClassEquality(call, valueArgument, valueArgument2);
                    }
                    return IrUtilsKt.irCall(call, this.intrinsics.getJsEquals(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
                }
                return IrUtilsKt.irCall(call, irSimpleFunctionFindEqualsMethod.getSymbol(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : true, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
            }
            return IrUtilsKt.irCall(call, this.intrinsics.getJsEqeq(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        }
        return IrUtilsKt.irCall(call, this.intrinsics.getJsEqeq(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression chooseEqualityOperatorForPrimitiveTypes(IrFunctionAccessExpression call) {
        return allValueArgumentsAreNullable(call) ? IrUtilsKt.irCall(call, this.intrinsics.getJsEqeq(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null) : IrUtilsKt.irCall(call, this.intrinsics.getJsEqeqeq(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
    }

    private final boolean allValueArgumentsAreNullable(IrFunctionAccessExpression irFunctionAccessExpression) {
        Iterable iterableUntil = RangesKt.until(0, irFunctionAccessExpression.getValueArgumentsCount());
        if (!(iterableUntil instanceof Collection) || !((Collection) iterableUntil).isEmpty()) {
            Iterator it = iterableUntil.iterator();
            while (it.hasNext()) {
                IrExpression valueArgument = irFunctionAccessExpression.getValueArgument(((IntIterator) it).nextInt());
                Intrinsics.checkNotNull(valueArgument);
                if (!IrTypeUtilsKt.isNullable(valueArgument.getType())) {
                    return false;
                }
            }
        }
        return true;
    }

    private final IrExpression transformCompareToMethodCall(IrFunctionAccessExpression call) {
        IrFunction owner = call.getSymbol().getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) owner;
        if (!(irSimpleFunction.getParent() instanceof IrClass)) {
            return call;
        }
        if (!isMethodOfPrimitiveJSType(irSimpleFunction) && !transformCompareToMethodCall$isFakeOverriddenFromComparable(irSimpleFunction)) {
            return call;
        }
        return IrUtilsKt.irCall(call, this.intrinsics.getJsCompareTo(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
    }

    private static final boolean transformCompareToMethodCall$isFakeOverriddenFromComparable(IrSimpleFunction irSimpleFunction) {
        if (!irSimpleFunction.getIsFakeOverride()) {
            if (IrDeclarationsKt.isStaticMethodOfClass(irSimpleFunction)) {
                return false;
            }
            IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(irSimpleFunction).getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            if (!IrTypePredicatesKt.isComparable(thisReceiver.getType())) {
                return false;
            }
        } else {
            List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
            if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                Iterator<T> it = overriddenSymbols.iterator();
                while (it.hasNext()) {
                    if (!transformCompareToMethodCall$isFakeOverriddenFromComparable(((IrSimpleFunctionSymbol) it.next()).getOwner())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private final IrExpression transformEqualsMethodCall(IrCall call) {
        IrSimpleFunction owner = call.getSymbol().getOwner();
        if (!MiscKt.isEqualsInheritedFromAny(owner)) {
            return call;
        }
        if (!IrUtilsKt.isSuperToAny(call)) {
            if (!isMethodOfPotentiallyPrimitiveJSType(owner)) {
                return call;
            }
            return IrUtilsKt.irCall(call, this.intrinsics.getJsEquals(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        }
        return IrUtilsKt.irCall(call, this.intrinsics.getJsEqeqeq(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
    }

    private final IrSimpleFunction findEqualsMethod(IrType irType) {
        IrClass irClass = IrTypesKt.getClass(irType);
        if (irClass == null) {
            return null;
        }
        if (IrUtilsKt.isEnumClass(irClass) && irClass.getIsExternal()) {
            return null;
        }
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irClass.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.EqualityAndComparisonCallsTransformer$findEqualsMethod$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrSimpleFunction);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return (IrSimpleFunction) MemoryOptimizedCollectionUtilKt.atMostOne(SequencesKt.filter(sequenceFilter, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.EqualityAndComparisonCallsTransformer.findEqualsMethod.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrSimpleFunction irSimpleFunction = it;
                return Boolean.valueOf(MiscKt.isEqualsInheritedFromAny(irSimpleFunction) && !IrUtilsKt.isFakeOverriddenFromAny(irSimpleFunction));
            }
        }));
    }

    private final boolean isMethodOfPrimitiveJSType(IrFunction irFunction) {
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        return (dispatchReceiverParameter == null || CallsLoweringUtilsKt.getPrimitiveType(dispatchReceiverParameter.getType()) == PrimitiveType.OTHER) ? false : true;
    }

    private final boolean isMethodOfPotentiallyPrimitiveJSType(IrFunction irFunction) {
        return isMethodOfPrimitiveJSType(irFunction) || IrUtilsKt.isFakeOverriddenFromAny(irFunction);
    }

    private final boolean isBuiltin(PrimitiveType primitiveType) {
        return primitiveType != PrimitiveType.OTHER;
    }

    private final boolean canBeUsedWithJsEq(PrimitiveType primitiveType) {
        return isBuiltin(primitiveType) && primitiveType != PrimitiveType.FLOATING_POINT_NUMBER;
    }

    private final boolean isDefaultEqualsMethod(IrType irType) {
        if (!IrTypePredicatesKt.isChar(irType)) {
            IrSimpleFunction irSimpleFunctionFindEqualsMethod = findEqualsMethod(irType);
            if ((irSimpleFunctionFindEqualsMethod != null ? irSimpleFunctionFindEqualsMethod.getOrigin() : null) != IrDeclarationOrigin.GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER.INSTANCE) {
                return false;
            }
        }
        return true;
    }

    private final boolean isBoxIntrinsic(IrExpression irExpression) {
        return (irExpression instanceof IrCall) && Intrinsics.areEqual(((IrCall) irExpression).getSymbol(), this.icUtils.getBoxIntrinsic());
    }

    private final Pair<IrExpression, IrClass> unboxParamWithInlinedClass(IrExpression irExpression) {
        Intrinsics.checkNotNull(irExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression");
        IrExpression valueArgument = ((IrFunctionAccessExpression) irExpression).getValueArgument(0);
        if (valueArgument == null) {
            throw new IllegalStateException("Boxed expression is expected".toString());
        }
        return new Pair<>(valueArgument, this.icUtils.getInlinedClass(valueArgument.getType()));
    }

    private final IrExpression optimizeInlineClassEquality(IrFunctionAccessExpression call, IrExpression lhs, IrExpression rhs) {
        Pair<IrExpression, IrClass> pairUnboxParamWithInlinedClass = unboxParamWithInlinedClass(lhs);
        IrExpression irExpressionComponent1 = pairUnboxParamWithInlinedClass.component1();
        IrClass irClassComponent2 = pairUnboxParamWithInlinedClass.component2();
        Pair<IrExpression, IrClass> pairUnboxParamWithInlinedClass2 = unboxParamWithInlinedClass(rhs);
        IrExpression irExpressionComponent2 = pairUnboxParamWithInlinedClass2.component1();
        IrClass irClassComponent3 = pairUnboxParamWithInlinedClass2.component2();
        if (irClassComponent2 != null && irClassComponent2 == irClassComponent3 && isDefaultEqualsMethod(irExpressionComponent1.getType())) {
            call.putValueArgument(0, irExpressionComponent1);
            call.putValueArgument(1, irExpressionComponent2);
            if (IrTypePredicatesKt.isChar(irExpressionComponent1.getType()) || canBeUsedWithJsEq(CallsLoweringUtilsKt.getPrimitiveType(getLowestUnderlyingType(irExpressionComponent1.getType())))) {
                return chooseEqualityOperatorForPrimitiveTypes(call);
            }
        }
        return IrUtilsKt.irCall(call, this.intrinsics.getJsEquals(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
    }

    private final IrType getLowestUnderlyingType(IrType irType) {
        InlineClassRepresentation<IrSimpleType> inlineClassRepresentation;
        if (!isDefaultEqualsMethod(irType)) {
            return irType;
        }
        IrClass inlinedClass = this.icUtils.getInlinedClass(irType);
        IrSimpleType irSimpleType = (inlinedClass == null || (inlineClassRepresentation = IrDeclarationsKt.getInlineClassRepresentation(inlinedClass)) == null) ? null : (IrSimpleType) inlineClassRepresentation.getUnderlyingType();
        return irSimpleType != null ? getLowestUnderlyingType(irSimpleType) : irType;
    }
}
