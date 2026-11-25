package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiResolveHelper {
    public static final RecursionGuard<PsiExpression> ourGuard = RecursionManager.createGuard("typeArgInference");
    public static final RecursionGuard<PsiElement> ourGraphGuard = RecursionManager.createGuard("graphTypeArgInference");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "languageLevel";
        } else if (i == 2) {
            objArr[0] = "typeParameters";
        } else if (i == 3) {
            objArr[0] = "leftTypes";
        } else if (i == 4) {
            objArr[0] = "rightTypes";
        } else if (i != 5) {
            objArr[0] = "partialSubstitutor";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiResolveHelper";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiResolveHelper";
        } else {
            objArr[1] = "inferTypeArguments";
        }
        if (i != 5) {
            objArr[2] = "inferTypeArguments";
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    LanguageLevel getEffectiveLanguageLevel(VirtualFile virtualFile);

    CandidateInfo[] getReferencedMethodCandidates(PsiCallExpression psiCallExpression, boolean z);

    CandidateInfo[] getReferencedMethodCandidates(PsiCallExpression psiCallExpression, boolean z, boolean z2);

    PsiType getSubstitutionForTypeParameter(PsiTypeParameter psiTypeParameter, PsiType psiType, PsiType psiType2, boolean z, LanguageLevel languageLevel);

    boolean hasOverloads(PsiCallExpression psiCallExpression);

    PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy);

    PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, MethodCandidateInfo methodCandidateInfo, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy, LanguageLevel languageLevel);

    PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, LanguageLevel languageLevel);

    PsiType inferTypeForMethodTypeParameter(PsiTypeParameter psiTypeParameter, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy);

    boolean isAccessible(PsiMember psiMember, PsiElement psiElement, PsiClass psiClass);

    boolean isAccessible(PsiMember psiMember, PsiModifierList psiModifierList, PsiElement psiElement, PsiClass psiClass, PsiElement psiElement2);

    boolean isAccessible(PsiPackage psiPackage, PsiElement psiElement);

    JavaResolveResult[] multiResolveConstructor(PsiClassType psiClassType, PsiExpressionList psiExpressionList, PsiElement psiElement);

    PsiVariable resolveAccessibleReferencedVariable(String str, PsiElement psiElement);

    JavaResolveResult resolveConstructor(PsiClassType psiClassType, PsiExpressionList psiExpressionList, PsiElement psiElement);

    PsiClass resolveReferencedClass(String str, PsiElement psiElement);

    PsiVariable resolveReferencedVariable(String str, PsiElement psiElement);

    public static final class SERVICE {
        private SERVICE() {
        }

        public static PsiResolveHelper getInstance(Project project) {
            return (PsiResolveHelper) project.getService(PsiResolveHelper.class);
        }
    }

    default PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(0);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(2);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(3);
        }
        if (psiTypeArr2 == null) {
            $$$reportNull$$$0(4);
        }
        PsiSubstitutor psiSubstitutorInferTypeArguments = inferTypeArguments(psiTypeParameterArr, psiTypeArr, psiTypeArr2, languageLevel);
        if (psiSubstitutorInferTypeArguments == null) {
            $$$reportNull$$$0(5);
        }
        return psiSubstitutorInferTypeArguments;
    }
}
