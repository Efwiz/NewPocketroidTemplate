package org.jetbrains.kotlin.resolve.calls;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;

/* JADX INFO: compiled from: CallExpressionUnroller.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002¨\u0006\u0004"}, d2 = {"unrollToLeftMostQualifiedExpression", "", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "expression", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallExpressionUnrollerKt {
    public static final List<KtQualifiedExpression> unrollToLeftMostQualifiedExpression(KtQualifiedExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        ArrayList arrayList = new ArrayList();
        while (true) {
            arrayList.add(expression);
            KtExpression receiverExpression = expression.getReceiverExpression();
            if (receiverExpression instanceof KtQualifiedExpression) {
                expression = (KtQualifiedExpression) receiverExpression;
            } else {
                return CollectionsKt.asReversedMutable(arrayList);
            }
        }
    }
}
