package org.jetbrains.kotlin.com.intellij.psi.javadoc;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;

/* JADX INFO: loaded from: classes3.dex */
public interface JavadocTagInfo {
    public static final ExtensionPointName<JavadocTagInfo> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.javadocTagInfo");

    String checkTagValue(PsiDocTagValue psiDocTagValue);

    String getName();

    PsiReference getReference(PsiDocTagValue psiDocTagValue);

    boolean isInline();

    boolean isValidInContext(PsiElement psiElement);
}
