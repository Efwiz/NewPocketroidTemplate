package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
class SeeDocTagInfo implements JavadocTagInfo {
    private final boolean myInline;
    private final String myName;

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String checkTagValue(PsiDocTagValue psiDocTagValue) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public PsiReference getReference(PsiDocTagValue psiDocTagValue) {
        return null;
    }

    SeeDocTagInfo(String str, boolean z) {
        this.myName = str;
        this.myInline = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String getName() {
        return this.myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isInline() {
        return this.myInline;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isValidInContext(PsiElement psiElement) {
        return !this.myInline || !this.myName.equals("linkplain") || psiElement == null || PsiUtil.getLanguageLevel(psiElement).compareTo(LanguageLevel.JDK_1_4) >= 0;
    }
}
