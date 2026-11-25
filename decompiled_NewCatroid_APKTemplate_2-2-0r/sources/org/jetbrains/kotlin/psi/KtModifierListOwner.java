package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;

/* JADX INFO: loaded from: classes6.dex */
public interface KtModifierListOwner extends PsiElement, KtAnnotated {
    KtAnnotationEntry addAnnotationEntry(KtAnnotationEntry ktAnnotationEntry);

    void addModifier(KtModifierKeywordToken ktModifierKeywordToken);

    KtModifierList getModifierList();

    boolean hasModifier(KtModifierKeywordToken ktModifierKeywordToken);

    void removeModifier(KtModifierKeywordToken ktModifierKeywordToken);
}
