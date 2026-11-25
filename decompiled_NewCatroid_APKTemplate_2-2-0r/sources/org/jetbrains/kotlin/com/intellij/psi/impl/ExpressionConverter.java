package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ExpressionConverter {
    public static final LanguageExtension<ExpressionConverter> EP = new LanguageExtension<>("org.jetbrains.kotlin.com.intellij.expressionConverter");

    protected abstract PsiElement convert(PsiElement psiElement, Project project);

    public static PsiElement getExpression(PsiElement psiElement, Language language, Project project) {
        if (psiElement.getLanguage() == language) {
            return psiElement;
        }
        ExpressionConverter expressionConverterForLanguage = EP.forLanguage(language);
        if (expressionConverterForLanguage == null) {
            return null;
        }
        return expressionConverterForLanguage.convert(psiElement, project);
    }
}
