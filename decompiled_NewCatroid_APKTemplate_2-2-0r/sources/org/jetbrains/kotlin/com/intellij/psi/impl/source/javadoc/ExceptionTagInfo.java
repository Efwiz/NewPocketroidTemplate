package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.codeInsight.javadoc.JavaDocUtil;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
class ExceptionTagInfo extends ClassReferenceTagInfo {
    ExceptionTagInfo(String str) {
        super(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isValidInContext(PsiElement psiElement) {
        return psiElement instanceof PsiMethod;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.ClassReferenceTagInfo, org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String checkTagValue(PsiDocTagValue psiDocTagValue) {
        PsiMethod psiMethod;
        String strCheckTagValue = super.checkTagValue(psiDocTagValue);
        if (strCheckTagValue != null) {
            return strCheckTagValue;
        }
        PsiClass psiClassResolveClassInTagValue = JavaDocUtil.resolveClassInTagValue(psiDocTagValue);
        if (psiClassResolveClassInTagValue == null) {
            return null;
        }
        PsiClass psiClassFindClass = JavaPsiFacade.getInstance(psiDocTagValue.getProject()).findClass(CommonClassNames.JAVA_LANG_THROWABLE, psiDocTagValue.getResolveScope());
        if (psiClassFindClass != null && !psiClassResolveClassInTagValue.equals(psiClassFindClass) && !psiClassResolveClassInTagValue.isInheritor(psiClassFindClass, true)) {
            return JavaPsiBundle.message("javadoc.exception.tag.class.is.not.throwable", psiClassResolveClassInTagValue.getQualifiedName());
        }
        PsiClass psiClassFindClass2 = JavaPsiFacade.getInstance(psiDocTagValue.getProject()).findClass(CommonClassNames.JAVA_LANG_RUNTIME_EXCEPTION, psiDocTagValue.getResolveScope());
        if (psiClassFindClass2 != null && (psiClassResolveClassInTagValue.isInheritor(psiClassFindClass2, true) || psiClassResolveClassInTagValue.equals(psiClassFindClass2))) {
            return null;
        }
        PsiClass psiClassFindClass3 = JavaPsiFacade.getInstance(psiDocTagValue.getProject()).findClass(CommonClassNames.JAVA_LANG_ERROR, psiDocTagValue.getResolveScope());
        if ((psiClassFindClass3 != null && (psiClassResolveClassInTagValue.isInheritor(psiClassFindClass3, true) || psiClassResolveClassInTagValue.equals(psiClassFindClass3))) || (psiMethod = (PsiMethod) PsiTreeUtil.getParentOfType(psiDocTagValue, PsiMethod.class)) == null) {
            return null;
        }
        for (PsiClassType psiClassType : psiMethod.getThrowsList().getReferencedTypes()) {
            PsiClass psiClassResolve = psiClassType.resolve();
            if (psiClassResolve != null && (psiClassResolveClassInTagValue.isInheritor(psiClassResolve, true) || psiClassResolveClassInTagValue.equals(psiClassResolve))) {
                return null;
            }
        }
        return JavaPsiBundle.message("javadoc.exception.tag.exception.is.not.thrown", psiClassResolveClassInTagValue.getName(), psiMethod.getName());
    }
}
