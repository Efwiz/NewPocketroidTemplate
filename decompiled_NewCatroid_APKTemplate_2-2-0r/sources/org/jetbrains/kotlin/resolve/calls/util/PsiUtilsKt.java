package org.jetbrains.kotlin.resolve.calls.util;

import kotlin.Metadata;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;

/* JADX INFO: compiled from: psiUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u0002¨\u0006\u0003"}, d2 = {"getCalleeExpressionIfAny", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/psi/KtElement;", "frontend.common-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PsiUtilsKt {
    public static final KtExpression getCalleeExpressionIfAny(KtElement ktElement) {
        if (ktElement instanceof KtExpression) {
            ktElement = KtPsiUtil.deparenthesize((KtExpression) ktElement);
        }
        if (ktElement instanceof KtSimpleNameExpression) {
            return (KtExpression) ktElement;
        }
        if (ktElement instanceof KtCallElement) {
            return ((KtCallElement) ktElement).getCalleeExpression();
        }
        if (ktElement instanceof KtQualifiedExpression) {
            return getCalleeExpressionIfAny(((KtQualifiedExpression) ktElement).getSelectorExpression());
        }
        if (ktElement instanceof KtOperationExpression) {
            return ((KtOperationExpression) ktElement).getOperationReference();
        }
        return null;
    }
}
