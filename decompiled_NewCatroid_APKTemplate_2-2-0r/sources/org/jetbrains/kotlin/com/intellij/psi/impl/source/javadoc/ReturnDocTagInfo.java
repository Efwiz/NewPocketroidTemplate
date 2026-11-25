package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;

/* JADX INFO: loaded from: classes3.dex */
class ReturnDocTagInfo implements JavadocTagInfo {
    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String checkTagValue(PsiDocTagValue psiDocTagValue) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public PsiReference getReference(PsiDocTagValue psiDocTagValue) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isInline() {
        return false;
    }

    ReturnDocTagInfo() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String getName() {
        return "return";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isValidInContext(PsiElement psiElement) {
        PsiType returnType;
        if ((psiElement instanceof PsiMethod) && (returnType = ((PsiMethod) psiElement).getReturnType()) != null) {
            return !PsiType.VOID.equals(returnType);
        }
        return false;
    }
}
