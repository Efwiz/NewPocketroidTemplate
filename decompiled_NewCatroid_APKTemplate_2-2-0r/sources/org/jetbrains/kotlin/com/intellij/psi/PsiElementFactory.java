package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiElementFactory extends JVMElementFactory, PsiJavaParserFacade {
    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClass createAnnotationType(String str) throws IncorrectOperationException;

    PsiCatchSection createCatchSection(PsiType psiType, String str, PsiElement psiElement) throws IncorrectOperationException;

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClass createClass(String str) throws IncorrectOperationException;

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClassInitializer createClassInitializer() throws IncorrectOperationException;

    PsiJavaCodeReferenceElement createClassReferenceElement(PsiClass psiClass);

    PsiCodeBlock createCodeBlock();

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiMethod createConstructor();

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiMethod createConstructor(String str);

    PsiElement createDummyHolder(String str, IElementType iElementType, PsiElement psiElement);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClass createEnum(String str) throws IncorrectOperationException;

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiExpression createExpressionFromText(String str, PsiElement psiElement) throws IncorrectOperationException;

    PsiJavaCodeReferenceElement createFQClassNameReferenceElement(String str, GlobalSearchScope globalSearchScope);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiField createField(String str, PsiType psiType) throws IncorrectOperationException;

    PsiIdentifier createIdentifier(String str) throws IncorrectOperationException;

    PsiImportStatement createImportStatement(PsiClass psiClass) throws IncorrectOperationException;

    PsiImportStatement createImportStatementOnDemand(String str) throws IncorrectOperationException;

    PsiImportStaticStatement createImportStaticStatement(PsiClass psiClass, String str) throws IncorrectOperationException;

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClass createInterface(String str) throws IncorrectOperationException;

    PsiKeyword createKeyword(String str) throws IncorrectOperationException;

    PsiKeyword createKeyword(String str, PsiElement psiElement) throws IncorrectOperationException;

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiMethod createMethod(String str, PsiType psiType) throws IncorrectOperationException;

    PsiJavaCodeReferenceElement createPackageReferenceElement(String str) throws IncorrectOperationException;

    PsiJavaCodeReferenceElement createPackageReferenceElement(PsiPackage psiPackage) throws IncorrectOperationException;

    PsiPackageStatement createPackageStatement(String str) throws IncorrectOperationException;

    PsiDocTag createParamTag(String str, String str2) throws IncorrectOperationException;

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiParameter createParameter(String str, PsiType psiType) throws IncorrectOperationException;

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiParameterList createParameterList(String[] strArr, PsiType[] psiTypeArr) throws IncorrectOperationException;

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiPrimitiveType createPrimitiveType(String str);

    PsiSubstitutor createRawSubstitutor(PsiSubstitutor psiSubstitutor, PsiTypeParameter[] psiTypeParameterArr);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiSubstitutor createRawSubstitutor(PsiTypeParameterListOwner psiTypeParameterListOwner);

    PsiClass createRecord(String str) throws IncorrectOperationException;

    PsiJavaCodeReferenceElement createReferenceElementByFQClassName(String str, GlobalSearchScope globalSearchScope);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiJavaCodeReferenceElement createReferenceElementByType(PsiClassType psiClassType);

    PsiReferenceExpression createReferenceExpression(PsiClass psiClass) throws IncorrectOperationException;

    PsiReferenceExpression createReferenceExpression(PsiPackage psiPackage) throws IncorrectOperationException;

    PsiReferenceList createReferenceList(PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArr) throws IncorrectOperationException;

    PsiResourceVariable createResourceVariable(String str, PsiType psiType, PsiExpression psiExpression, PsiElement psiElement);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiSubstitutor createSubstitutor(Map<PsiTypeParameter, PsiType> map);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClassType createType(PsiClass psiClass, PsiSubstitutor psiSubstitutor);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClassType createType(PsiClass psiClass, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClassType createType(PsiClass psiClass, PsiType psiType);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClassType createType(PsiClass psiClass, PsiType... psiTypeArr);

    PsiClassType createType(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClassType createTypeByFQClassName(String str);

    @Override // org.jetbrains.kotlin.com.intellij.psi.JVMElementFactory
    PsiClassType createTypeByFQClassName(String str, GlobalSearchScope globalSearchScope);

    PsiTypeElement createTypeElement(PsiType psiType);

    PsiDeclarationStatement createVariableDeclarationStatement(String str, PsiType psiType, PsiExpression psiExpression) throws IncorrectOperationException;

    PsiDeclarationStatement createVariableDeclarationStatement(String str, PsiType psiType, PsiExpression psiExpression, PsiElement psiElement) throws IncorrectOperationException;

    PsiClass getArrayClass(LanguageLevel languageLevel);

    PsiClassType getArrayClassType(PsiType psiType, LanguageLevel languageLevel);

    @Deprecated
    public static final class SERVICE {
        private SERVICE() {
        }

        public static PsiElementFactory getInstance(Project project) {
            return PsiElementFactory.getInstance(project);
        }
    }

    static PsiElementFactory getInstance(Project project) {
        return (PsiElementFactory) project.getService(PsiElementFactory.class);
    }
}
