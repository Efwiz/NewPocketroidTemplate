package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: KtExpressionImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"isThisWithoutLabel", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtExpressionImplKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isThisWithoutLabel(PsiElement psiElement) {
        return (psiElement instanceof KtThisExpression) && ((KtThisExpression) psiElement).getLabelName() == null;
    }
}
