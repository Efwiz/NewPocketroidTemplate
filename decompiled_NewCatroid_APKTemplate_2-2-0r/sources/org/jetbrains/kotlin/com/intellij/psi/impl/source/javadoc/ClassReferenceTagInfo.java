package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;

/* JADX INFO: loaded from: classes3.dex */
abstract class ClassReferenceTagInfo implements JavadocTagInfo {
    private final String myName;

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public PsiReference getReference(PsiDocTagValue psiDocTagValue) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isInline() {
        return false;
    }

    ClassReferenceTagInfo(String str) {
        this.myName = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String getName() {
        return this.myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String checkTagValue(PsiDocTagValue psiDocTagValue) {
        PsiElement firstChild = psiDocTagValue != null ? psiDocTagValue.getFirstChild() : null;
        if (firstChild == null) {
            return JavaPsiBundle.message("javadoc.ref.tag.class.ref.expected", new Object[0]);
        }
        if (firstChild.getFirstChild() instanceof PsiJavaCodeReferenceElement) {
            return null;
        }
        return JavaPsiBundle.message("javadoc.exception.tag.wrong.tag.value", new Object[0]);
    }
}
