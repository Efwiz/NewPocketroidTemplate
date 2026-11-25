package org.jetbrains.kotlin.ir.linkage.partial;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;

/* JADX INFO: compiled from: PartialLinkageUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"isPartialLinkageRuntimeError", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageUtilsKt {
    public static final boolean isPartialLinkageRuntimeError(IrStatement irStatement) {
        Intrinsics.checkNotNullParameter(irStatement, "<this>");
        if (irStatement instanceof IrCall) {
            return Intrinsics.areEqual(((IrCall) irStatement).getOrigin(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE);
        }
        if (irStatement instanceof IrContainerExpression) {
            IrContainerExpression irContainerExpression = (IrContainerExpression) irStatement;
            if (!Intrinsics.areEqual(irContainerExpression.getOrigin(), IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE)) {
                List<IrStatement> statements = irContainerExpression.getStatements();
                if (!(statements instanceof Collection) || !statements.isEmpty()) {
                    Iterator<T> it = statements.iterator();
                    while (it.hasNext()) {
                        if (isPartialLinkageRuntimeError((IrStatement) it.next())) {
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
