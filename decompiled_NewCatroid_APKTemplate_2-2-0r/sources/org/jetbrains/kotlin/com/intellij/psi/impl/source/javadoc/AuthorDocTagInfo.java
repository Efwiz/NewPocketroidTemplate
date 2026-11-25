package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;

/* JADX INFO: loaded from: classes3.dex */
class AuthorDocTagInfo extends SimpleDocTagInfo {
    AuthorDocTagInfo() {
        super("author", LanguageLevel.JDK_1_3, false, PsiClass.class, PsiPackage.class, PsiMethod.class, PsiJavaModule.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.SimpleDocTagInfo, org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isValidInContext(PsiElement psiElement) {
        if (!(psiElement instanceof PsiMethod) || psiElement.getIsPhysical()) {
            return super.isValidInContext(psiElement);
        }
        return false;
    }
}
