package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiInferenceHelper {
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
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiInferenceHelper";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiInferenceHelper";
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

    PsiType getSubstitutionForTypeParameter(PsiTypeParameter psiTypeParameter, PsiType psiType, PsiType psiType2, boolean z, LanguageLevel languageLevel);

    PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, MethodCandidateInfo methodCandidateInfo, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy, LanguageLevel languageLevel);

    PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, LanguageLevel languageLevel);

    PsiType inferTypeForMethodTypeParameter(PsiTypeParameter psiTypeParameter, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy);

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
