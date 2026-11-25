package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaConstantExpressionEvaluator;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ValueDocTagInfo implements JavadocTagInfo {
    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public PsiReference getReference(PsiDocTagValue psiDocTagValue) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isInline() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public boolean isValidInContext(PsiElement psiElement) {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String getName() {
        return "value";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo
    public String checkTagValue(PsiDocTagValue psiDocTagValue) {
        PsiReference reference;
        PsiElement psiElementResolve;
        if (psiDocTagValue != null && psiDocTagValue.getFirstChild() != null && !PsiUtil.isLanguageLevel5OrHigher(psiDocTagValue)) {
            return JavaPsiBundle.message("javadoc.value.tag.jdk15.required", new Object[0]);
        }
        if (psiDocTagValue == null || (reference = psiDocTagValue.getReference()) == null || (psiElementResolve = reference.resolve()) == null) {
            return null;
        }
        if (!(psiElementResolve instanceof PsiField)) {
            return JavaPsiBundle.message("javadoc.value.field.required", new Object[0]);
        }
        PsiField psiField = (PsiField) psiElementResolve;
        if (!psiField.hasModifierProperty("static")) {
            return JavaPsiBundle.message("javadoc.value.static.field.required", new Object[0]);
        }
        if (psiField.getInitializer() == null || JavaConstantExpressionEvaluator.computeConstantExpression(psiField.getInitializer(), false) == null) {
            return JavaPsiBundle.message("javadoc.value.field.with.initializer.required", new Object[0]);
        }
        return null;
    }
}
