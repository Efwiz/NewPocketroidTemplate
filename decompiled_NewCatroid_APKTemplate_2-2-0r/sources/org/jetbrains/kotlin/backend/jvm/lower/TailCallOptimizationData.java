package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: TailCallOptimizationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0010\u001a\u00020\u0011*\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\bH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/TailCallOptimizationData;", "", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "getFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "returnsUnit", "", "getReturnsUnit", "()Z", "tailCalls", "", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "getTailCalls", "()Ljava/util/Set;", "findCallsOnTailPositionWithoutImmediateReturn", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "immediateReturn", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TailCallOptimizationData {
    private final IrSimpleFunction function;
    private final boolean returnsUnit;
    private final Set<IrCall> tailCalls;

    public TailCallOptimizationData(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
        boolean zIsUnit = IrTypePredicatesKt.isUnit(function.getReturnType());
        this.returnsUnit = zIsUnit;
        this.tailCalls = new LinkedHashSet();
        IrBody body = function.getBody();
        if (body instanceof IrBlockBody) {
            IrStatement irStatementFindTailCall = TailCallOptimizationLoweringKt.findTailCall(((IrBlockBody) body).getStatements(), zIsUnit);
            if (irStatementFindTailCall != null) {
                findCallsOnTailPositionWithoutImmediateReturn$default(this, irStatementFindTailCall, false, 1, null);
                return;
            }
            return;
        }
        if (body instanceof IrExpressionBody) {
            findCallsOnTailPositionWithoutImmediateReturn(((IrExpressionBody) body).getExpression(), true);
        }
    }

    public final IrSimpleFunction getFunction() {
        return this.function;
    }

    public final boolean getReturnsUnit() {
        return this.returnsUnit;
    }

    public final Set<IrCall> getTailCalls() {
        return this.tailCalls;
    }

    static /* synthetic */ void findCallsOnTailPositionWithoutImmediateReturn$default(TailCallOptimizationData tailCallOptimizationData, IrStatement irStatement, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        tailCallOptimizationData.findCallsOnTailPositionWithoutImmediateReturn(irStatement, z);
    }

    private final void findCallsOnTailPositionWithoutImmediateReturn(IrStatement irStatement, boolean z) {
        if (irStatement instanceof IrCall) {
            IrCall irCall = (IrCall) irStatement;
            if (IrUtilsKt.isSuspend(irCall) && !z && (this.returnsUnit || Intrinsics.areEqual(irCall.getType(), this.function.getReturnType()))) {
                this.tailCalls.add(irStatement);
                return;
            }
        }
        if ((irStatement instanceof IrBlock) && (!(irStatement instanceof IrInlinedFunctionBlock) || !IrInlineUtilsKt.isFunctionInlining((IrInlinedFunctionBlock) irStatement))) {
            IrStatement irStatementFindTailCall = TailCallOptimizationLoweringKt.findTailCall(((IrBlock) irStatement).getStatements(), this.returnsUnit);
            if (irStatementFindTailCall != null) {
                findCallsOnTailPositionWithoutImmediateReturn$default(this, irStatementFindTailCall, false, 1, null);
                return;
            }
            return;
        }
        if (!(irStatement instanceof IrWhen)) {
            if (irStatement instanceof IrReturn) {
                findCallsOnTailPositionWithoutImmediateReturn(((IrReturn) irStatement).getValue(), true);
                return;
            } else {
                if (irStatement instanceof IrTypeOperatorCall) {
                    IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) irStatement;
                    if (irTypeOperatorCall.getOperator() == IrTypeOperator.IMPLICIT_COERCION_TO_UNIT) {
                        findCallsOnTailPositionWithoutImmediateReturn$default(this, irTypeOperatorCall.getArgument(), false, 1, null);
                        return;
                    }
                    return;
                }
                return;
            }
        }
        Iterator<T> it = ((IrWhen) irStatement).getBranches().iterator();
        while (it.hasNext()) {
            findCallsOnTailPositionWithoutImmediateReturn$default(this, ((IrBranch) it.next()).getResult(), false, 1, null);
        }
    }
}
