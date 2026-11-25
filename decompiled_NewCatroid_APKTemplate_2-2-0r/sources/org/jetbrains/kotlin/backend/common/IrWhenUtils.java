package org.jetbrains.kotlin.backend.common;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: IrWhenUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/IrWhenUtils;", "", "()V", "matchConditions", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lkotlin/collections/ArrayList;", "ororSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "condition", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrWhenUtils {
    public static final IrWhenUtils INSTANCE = new IrWhenUtils();

    private IrWhenUtils() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ArrayList<IrCall> matchConditions(IrFunctionSymbol ororSymbol, IrExpression condition) {
        ArrayList<IrCall> arrayListMatchConditions;
        Intrinsics.checkNotNullParameter(ororSymbol, "ororSymbol");
        Intrinsics.checkNotNullParameter(condition, "condition");
        if (condition instanceof IrWhen) {
            IrWhen irWhen = (IrWhen) condition;
            if (Intrinsics.areEqual(irWhen.getOrigin(), IrStatementOrigin.WHEN_COMMA.INSTANCE)) {
                IrTypePredicatesKt.isBoolean(condition.getType());
                ArrayList<IrCall> arrayList = new ArrayList<>();
                for (IrBranch irBranch : irWhen.getBranches()) {
                    ArrayList<IrCall> arrayList2 = arrayList;
                    if (IrUtilsKt.isElseBranch(irBranch)) {
                        IrUtilsKt.isTrueConst(irBranch.getCondition());
                        arrayListMatchConditions = matchConditions(ororSymbol, irBranch.getResult());
                        if (arrayListMatchConditions == null) {
                            return null;
                        }
                    } else if (!IrUtilsKt.isTrueConst(irBranch.getResult()) || (arrayListMatchConditions = matchConditions(ororSymbol, irBranch.getCondition())) == null) {
                        return null;
                    }
                    CollectionsKt.addAll(arrayList2, arrayListMatchConditions);
                }
                ArrayList<IrCall> arrayList3 = arrayList;
                return arrayList3.isEmpty() ? null : arrayList3;
            }
        }
        boolean z = condition instanceof IrCall;
        if (z) {
            IrCall irCall = (IrCall) condition;
            if (Intrinsics.areEqual(irCall.getSymbol(), ororSymbol)) {
                ArrayList<IrCall> arrayList4 = new ArrayList<>();
                int valueArgumentsCount = irCall.getValueArgumentsCount();
                for (int i = 0; i < valueArgumentsCount; i++) {
                    IrExpression valueArgument = irCall.getValueArgument(i);
                    Intrinsics.checkNotNull(valueArgument);
                    ArrayList<IrCall> arrayList5 = arrayList4;
                    ArrayList<IrCall> arrayListMatchConditions2 = matchConditions(ororSymbol, valueArgument);
                    if (arrayListMatchConditions2 == null) {
                        return null;
                    }
                    CollectionsKt.addAll(arrayList5, arrayListMatchConditions2);
                }
                ArrayList<IrCall> arrayList6 = arrayList4;
                return arrayList6.isEmpty() ? null : arrayList6;
            }
        }
        if (z) {
            return CollectionsKt.arrayListOf(condition);
        }
        return null;
    }
}
