package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ServiceReferenceTagInfo extends ClassReferenceTagInfo {
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.ClassReferenceTagInfo, org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public /* bridge */ /* synthetic */ String checkTagValue(PsiDocTagValue psiDocTagValue) {
        return super.checkTagValue(psiDocTagValue);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.ClassReferenceTagInfo, org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.ClassReferenceTagInfo, org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public /* bridge */ /* synthetic */ PsiReference getReference(PsiDocTagValue psiDocTagValue) {
        return super.getReference(psiDocTagValue);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.ClassReferenceTagInfo, org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public /* bridge */ /* synthetic */ boolean isInline() {
        return super.isInline();
    }

    public ServiceReferenceTagInfo(String str) {
        super(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isValidInContext(PsiElement psiElement) {
        return (psiElement instanceof PsiJavaModule) && PsiUtil.getLanguageLevel(psiElement).isAtLeast(LanguageLevel.JDK_1_9);
    }
}
