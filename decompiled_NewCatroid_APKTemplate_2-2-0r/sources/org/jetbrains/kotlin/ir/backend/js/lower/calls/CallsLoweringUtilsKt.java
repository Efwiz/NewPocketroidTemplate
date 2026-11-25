package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.android.dx.rop.code.RegisterSpec;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: CallsLoweringUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aL\u0010\u0000\u001a\u00020\u0001*\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u0002j\u0002`\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0000\u001a@\u0010\u0000\u001a\u00020\u0001*\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u0002j\u0002`\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0000\u001a@\u0010\u0000\u001a\u00020\u0001*\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u0002j\u0002`\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000eH\u0000\u001aP\u0010\u0000\u001a\u00020\u0001*\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u000fj\u0002`\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100\u00132\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0000\u001aD\u0010\u0000\u001a\u00020\u0001*\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u000fj\u0002`\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100\u00132\u0006\u0010\u0015\u001a\u00020\u000eH\u0000\u001aD\u0010\u0000\u001a\u00020\u0001*\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u000fj\u0002`\u00112\u0006\u0010\u0012\u001a\u00020\u00102\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0000\u001aB\u0010\u0000\u001a\u00020\u0001*\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u000fj\u0002`\u00112\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u000e2\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0000\u001a_\u0010\u0018\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0019*\u001a\u0012\u0004\u0012\u0002H\u0019\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u000f2\u0006\u0010\u0012\u001a\u0002H\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00170\u00042\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0000¢\u0006\u0002\u0010\u001c\u001a\n\u0010\u001d\u001a\u00020\u001e*\u00020\t*<\b\u0000\u0010\u001f\"\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004` 2\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u0002*:\u0010!\"\u001a\u0012\u0004\u0012\u00020\u0010\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u000f2\u001a\u0012\u0004\u0012\u00020\u0010\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u000f¨\u0006\""}, d2 = {ProductAction.ACTION_ADD, "", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/SimpleMemberKey;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/MemberToTransformer;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "name", "Lorg/jetbrains/kotlin/name/Name;", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/SymbolToTransformer;", TypedValues.TransitionType.S_FROM, "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "to", "dispatchReceiverAsFirstArgument", "", "addWithPredicate", "K", "predicate", "action", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getPrimitiveType", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/PrimitiveType;", "MemberToTransformer", "Lkotlin/collections/HashMap;", "SymbolToTransformer", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallsLoweringUtilsKt {
    public static final void add(final Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>> map, Map<IrClassifierSymbol, ? extends IrFunctionSymbol> from, final IrSimpleFunctionSymbol to) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        final Function2<IrClassifierSymbol, IrFunctionSymbol, Unit> function2 = new Function2<IrClassifierSymbol, IrFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsLoweringUtilsKt.add.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IrClassifierSymbol irClassifierSymbol, IrFunctionSymbol irFunctionSymbol) {
                invoke2(irClassifierSymbol, irFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrClassifierSymbol irClassifierSymbol, IrFunctionSymbol func) {
                Intrinsics.checkNotNullParameter(irClassifierSymbol, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(func, "func");
                CallsLoweringUtilsKt.add$default(map, func, to, false, 4, null);
            }
        };
        from.forEach(new BiConsumer() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsLoweringUtilsKt$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CallsLoweringUtilsKt.add$lambda$0(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void add$lambda$0(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    public static final void add(final Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>> map, Map<IrClassifierSymbol, ? extends IrFunctionSymbol> from, final Function1<? super IrFunctionAccessExpression, ? extends IrExpression> to) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        final Function2<IrClassifierSymbol, IrFunctionSymbol, Unit> function2 = new Function2<IrClassifierSymbol, IrFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsLoweringUtilsKt.add.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IrClassifierSymbol irClassifierSymbol, IrFunctionSymbol irFunctionSymbol) {
                invoke2(irClassifierSymbol, irFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrClassifierSymbol irClassifierSymbol, IrFunctionSymbol func) {
                Intrinsics.checkNotNullParameter(irClassifierSymbol, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(func, "func");
                CallsLoweringUtilsKt.add(map, func, to);
            }
        };
        from.forEach(new BiConsumer() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsLoweringUtilsKt$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CallsLoweringUtilsKt.add$lambda$1(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void add$lambda$1(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void add(Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>> map, IrFunctionSymbol from, Function1<? super IrFunctionAccessExpression, ? extends IrExpression> to) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        map.put(from, to);
    }

    public static /* synthetic */ void add$default(Map map, IrFunctionSymbol irFunctionSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, irFunctionSymbol, irSimpleFunctionSymbol, z);
    }

    public static final void add(Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>> map, IrFunctionSymbol from, final IrSimpleFunctionSymbol to, final boolean z) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        map.put(from, new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsLoweringUtilsKt.add.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                return IrUtilsKt.irCall(call, to, (12 & 4) != 0 ? false : z, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
            }
        });
    }

    public static final <K> void addWithPredicate(Map<K, Function1<IrFunctionAccessExpression, IrExpression>> map, K k, final Function1<? super IrFunctionAccessExpression, Boolean> predicate, final Function1<? super IrFunctionAccessExpression, ? extends IrExpression> action) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Intrinsics.checkNotNullParameter(action, "action");
        map.put(k, new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsLoweringUtilsKt.addWithPredicate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                boolean zBooleanValue = predicate.invoke(call).booleanValue();
                IrExpression irExpressionInvoke = call;
                if (zBooleanValue) {
                    irExpressionInvoke = action.invoke(call);
                }
                return irExpressionInvoke;
            }
        });
    }

    public static final void add(HashMap<SimpleMemberKey, Function1<IrFunctionAccessExpression, IrExpression>> map, IrType type, Name name2, final IrSimpleFunctionSymbol v) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(v, "v");
        add(map, type, name2, new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsLoweringUtilsKt.add.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return IrUtilsKt.irCall(it, v, (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
            }
        });
    }

    public static final void add(HashMap<SimpleMemberKey, Function1<IrFunctionAccessExpression, IrExpression>> map, IrType type, Name name2, IrSimpleFunction v) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(v, "v");
        add(map, type, name2, v.getSymbol());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void add(HashMap<SimpleMemberKey, Function1<IrFunctionAccessExpression, IrExpression>> map, IrType type, Name name2, Function1<? super IrFunctionAccessExpression, ? extends IrExpression> v) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(v, "v");
        map.put(new SimpleMemberKey(type, name2), v);
    }

    public static final PrimitiveType getPrimitiveType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(irType);
        if (IrTypePredicatesKt.isBoolean(irTypeMakeNotNull)) {
            return PrimitiveType.BOOLEAN;
        }
        if (IrTypePredicatesKt.isByte(irTypeMakeNotNull) || IrTypePredicatesKt.isShort(irTypeMakeNotNull) || IrTypePredicatesKt.isInt(irTypeMakeNotNull)) {
            return PrimitiveType.INTEGER_NUMBER;
        }
        if (IrTypePredicatesKt.isFloat(irTypeMakeNotNull) || IrTypePredicatesKt.isDouble(irTypeMakeNotNull)) {
            return PrimitiveType.FLOATING_POINT_NUMBER;
        }
        return IrTypePredicatesKt.isString(irTypeMakeNotNull) ? PrimitiveType.STRING : PrimitiveType.OTHER;
    }
}
