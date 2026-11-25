package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiJavaParserFacade {
    PsiAnnotation createAnnotationFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiClass createClassFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiCodeBlock createCodeBlockFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiComment createCommentFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiDocComment createDocCommentFromText(String str) throws IncorrectOperationException;

    PsiDocComment createDocCommentFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiDocTag createDocTagFromText(String str) throws IncorrectOperationException;

    PsiEnumConstant createEnumConstantFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiExpression createExpressionFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiField createFieldFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiMethod createMethodFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiMethod createMethodFromText(String str, PsiElement psiElement, LanguageLevel languageLevel) throws IncorrectOperationException;

    PsiJavaModule createModuleFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiJavaModuleReferenceElement createModuleReferenceFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiStatement createModuleStatementFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiParameter createParameterFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiType createPrimitiveTypeFromText(String str) throws IncorrectOperationException;

    PsiRecordHeader createRecordHeaderFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiJavaCodeReferenceElement createReferenceFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiResourceVariable createResourceFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiStatement createStatementFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiTypeElement createTypeElementFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiType createTypeFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiTypeParameter createTypeParameterFromText(String str, PsiElement psiElement) throws IncorrectOperationException;
}
