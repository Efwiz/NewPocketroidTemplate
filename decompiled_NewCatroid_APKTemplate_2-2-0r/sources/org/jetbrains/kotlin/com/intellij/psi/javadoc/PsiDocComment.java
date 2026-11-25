package org.jetbrains.kotlin.com.intellij.psi.javadoc;

import org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiDocComment extends PsiDocCommentBase {
    PsiDocTag findTagByName(String str);

    PsiDocTag[] findTagsByName(String str);

    PsiElement[] getDescriptionElements();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentBase
    PsiJavaDocumentedElement getOwner();

    PsiDocTag[] getTags();
}
