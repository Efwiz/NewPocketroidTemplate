package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrStatementOrigin.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0004"}, d2 = {"isAssignmentOperator", "", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "isAssignmentOperatorWithResult", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrStatementOriginKt {
    public static final boolean isAssignmentOperatorWithResult(IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irStatementOrigin, "<this>");
        if (Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.PREFIX_INCR.INSTANCE) ? true : Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.PREFIX_DECR.INSTANCE) ? true : Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.POSTFIX_INCR.INSTANCE)) {
            return true;
        }
        return Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.POSTFIX_DECR.INSTANCE);
    }

    public static final boolean isAssignmentOperator(IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irStatementOrigin, "<this>");
        if (Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.EQ.INSTANCE) ? true : Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.PLUSEQ.INSTANCE) ? true : Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.MINUSEQ.INSTANCE) ? true : Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.MULTEQ.INSTANCE) ? true : Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.DIVEQ.INSTANCE) ? true : Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.PERCEQ.INSTANCE)) {
            return true;
        }
        return isAssignmentOperatorWithResult(irStatementOrigin);
    }
}
