package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiModifierList extends PsiAnnotationOwner, PsiElement {
    void checkSetModifierProperty(String str, boolean z) throws IncorrectOperationException;

    boolean hasExplicitModifier(String str);

    boolean hasModifierProperty(String str);

    void setModifierProperty(String str, boolean z) throws IncorrectOperationException;
}
