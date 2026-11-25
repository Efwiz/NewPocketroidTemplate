package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes6.dex */
public interface KtDeclarationWithBody extends KtDeclaration {
    KtExpression getBodyExpression();

    default KtContractEffectList getContractDescription() {
        return null;
    }

    /* JADX INFO: renamed from: getEqualsToken */
    PsiElement mo7390getEqualsToken();

    @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    List<KtParameter> getValueParameters();

    boolean hasBlockBody();

    boolean hasBody();

    boolean hasDeclaredReturnType();

    default boolean hasContractEffectList() {
        return getContractDescription() != null;
    }

    default KtBlockExpression getBodyBlockExpression() {
        KtExpression bodyExpression = getBodyExpression();
        if (bodyExpression instanceof KtBlockExpression) {
            return (KtBlockExpression) bodyExpression;
        }
        return null;
    }
}
