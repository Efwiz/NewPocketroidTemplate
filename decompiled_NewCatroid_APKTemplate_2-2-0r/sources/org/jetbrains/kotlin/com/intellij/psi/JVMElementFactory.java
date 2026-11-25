package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface JVMElementFactory {
    PsiAnnotation createAnnotationFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiClass createAnnotationType(String str) throws IncorrectOperationException;

    PsiClass createClass(String str) throws IncorrectOperationException;

    PsiClassInitializer createClassInitializer();

    PsiMethod createConstructor();

    PsiMethod createConstructor(String str);

    PsiMethod createConstructor(String str, PsiElement psiElement);

    PsiDocComment createDocCommentFromText(String str);

    PsiClass createEnum(String str) throws IncorrectOperationException;

    PsiElement createExpressionFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiField createField(String str, PsiType psiType) throws IncorrectOperationException;

    PsiClass createInterface(String str) throws IncorrectOperationException;

    PsiMethod createMethod(String str, PsiType psiType) throws IncorrectOperationException;

    PsiMethod createMethod(String str, PsiType psiType, PsiElement psiElement) throws IncorrectOperationException;

    PsiMethod createMethodFromText(String str, PsiElement psiElement);

    PsiParameter createParameter(String str, PsiType psiType) throws IncorrectOperationException;

    PsiParameter createParameter(String str, PsiType psiType, PsiElement psiElement) throws IncorrectOperationException;

    PsiParameterList createParameterList(String[] strArr, PsiType[] psiTypeArr) throws IncorrectOperationException;

    PsiPrimitiveType createPrimitiveType(String str);

    PsiSubstitutor createRawSubstitutor(PsiTypeParameterListOwner psiTypeParameterListOwner);

    PsiElement createReferenceElementByType(PsiClassType psiClassType);

    PsiSubstitutor createSubstitutor(Map<PsiTypeParameter, PsiType> map);

    PsiClassType createType(PsiClass psiClass);

    PsiClassType createType(PsiClass psiClass, PsiSubstitutor psiSubstitutor);

    PsiClassType createType(PsiClass psiClass, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel);

    PsiClassType createType(PsiClass psiClass, PsiType psiType);

    PsiClassType createType(PsiClass psiClass, PsiType... psiTypeArr);

    PsiClassType createTypeByFQClassName(String str);

    PsiClassType createTypeByFQClassName(String str, GlobalSearchScope globalSearchScope);

    PsiTypeParameter createTypeParameter(String str, PsiClassType[] psiClassTypeArr);

    PsiTypeParameterList createTypeParameterList();

    boolean isValidClassName(String str);

    boolean isValidFieldName(String str);

    boolean isValidLocalVariableName(String str);

    boolean isValidMethodName(String str);

    boolean isValidParameterName(String str);
}
