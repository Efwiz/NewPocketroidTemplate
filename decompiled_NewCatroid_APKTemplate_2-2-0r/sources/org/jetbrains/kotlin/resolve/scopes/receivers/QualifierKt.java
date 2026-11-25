package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: compiled from: Qualifier.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Qualifier;", "getExpression", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/Qualifier;)Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class QualifierKt {
    public static final KtExpression getExpression(Qualifier qualifier) {
        Intrinsics.checkNotNullParameter(qualifier, "<this>");
        KtQualifiedExpression topmostParentQualifiedExpressionForSelector = KtPsiUtilKt.getTopmostParentQualifiedExpressionForSelector(qualifier.getReferenceExpression());
        return topmostParentQualifiedExpressionForSelector != null ? topmostParentQualifiedExpressionForSelector : qualifier.getReferenceExpression();
    }
}
