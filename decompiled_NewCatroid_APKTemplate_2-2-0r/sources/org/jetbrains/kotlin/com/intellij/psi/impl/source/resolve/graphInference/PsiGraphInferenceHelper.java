package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiGraphInferenceHelper implements PsiInferenceHelper {
    private final PsiManager myManager;

    /* JADX WARN: Code duplicated, block: B:35:0x006c  */
    /* JADX WARN: Code duplicated, block: B:8:0x0012  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 12 && i != 13 && i != 18) {
            switch (i) {
                case 24:
                case 25:
                case 26:
                case 27:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 12 && i != 13 && i != 18) {
            switch (i) {
                case 24:
                case 25:
                case 26:
                case 27:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 5:
            case 19:
                objArr[0] = "partialSubstitutor";
                break;
            case 2:
            case 7:
                objArr[0] = "policy";
                break;
            case 3:
            case 10:
                objArr[0] = "parameters";
                break;
            case 4:
            case 11:
                objArr[0] = "arguments";
                break;
            case 6:
                objArr[0] = "parent";
                break;
            case 8:
            case 14:
            case 20:
                objArr[0] = "languageLevel";
                break;
            case 9:
            case 15:
            case 21:
                objArr[0] = "typeParameters";
                break;
            case 12:
            case 13:
            case 18:
            case 24:
            case 25:
            case 26:
            case 27:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/PsiGraphInferenceHelper";
                break;
            case 16:
            case 22:
                objArr[0] = "leftTypes";
                break;
            case 17:
            case 23:
                objArr[0] = "rightTypes";
                break;
            default:
                objArr[0] = "typeParameter";
                break;
        }
        if (i != 12 && i != 13 && i != 18) {
            switch (i) {
                case 24:
                case 25:
                case 26:
                case 27:
                    objArr[1] = "inferTypeArguments";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/PsiGraphInferenceHelper";
                    break;
            }
        } else {
            objArr[1] = "inferTypeArguments";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[2] = "inferTypeArguments";
                break;
            case 12:
            case 13:
            case 18:
            case 24:
            case 25:
            case 26:
            case 27:
                break;
            default:
                objArr[2] = "inferTypeForMethodTypeParameter";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 12 && i != 13 && i != 18) {
            switch (i) {
                case 24:
                case 25:
                case 26:
                case 27:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public PsiGraphInferenceHelper(PsiManager psiManager) {
        this.myManager = psiManager;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiType inferTypeForMethodTypeParameter(PsiTypeParameter psiTypeParameter, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        PsiSubstitutor psiSubstitutorInfer;
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(2);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(3);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement != null) {
            psiSubstitutorInfer = inferTypeArguments(new PsiTypeParameter[]{psiTypeParameter}, psiParameterArr, psiExpressionArr, null, psiSubstitutor, psiElement, parameterTypeInferencePolicy, PsiUtil.getLanguageLevel(psiElement));
        } else {
            InferenceSession inferenceSession = new InferenceSession(new PsiTypeParameter[]{psiTypeParameter}, psiSubstitutor, this.myManager, null, parameterTypeInferencePolicy);
            inferenceSession.initExpressionConstraints(psiParameterArr, psiExpressionArr, null, false);
            psiSubstitutorInfer = inferenceSession.infer();
        }
        return psiSubstitutorInfer.substitute(psiTypeParameter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, MethodCandidateInfo methodCandidateInfo, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy, LanguageLevel languageLevel) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(7);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(8);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(9);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(10);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(11);
        }
        if (psiTypeParameterArr.length == 0) {
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(12);
            }
            return psiSubstitutor;
        }
        PsiSubstitutor psiSubstitutorInfer = InferenceSessionContainer.infer(psiTypeParameterArr, psiParameterArr, psiExpressionArr, psiSubstitutor, psiElement, parameterTypeInferencePolicy, methodCandidateInfo);
        if (psiSubstitutorInfer == null) {
            $$$reportNull$$$0(13);
        }
        return psiSubstitutorInfer;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(14);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(15);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(16);
        }
        if (psiTypeArr2 == null) {
            $$$reportNull$$$0(17);
        }
        PsiSubstitutor psiSubstitutorInferTypeArguments = inferTypeArguments(psiTypeParameterArr, psiTypeArr, psiTypeArr2, PsiSubstitutor.EMPTY, languageLevel);
        if (psiSubstitutorInferTypeArguments == null) {
            $$$reportNull$$$0(18);
        }
        return psiSubstitutorInferTypeArguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(19);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(20);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(21);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(22);
        }
        if (psiTypeArr2 == null) {
            $$$reportNull$$$0(23);
        }
        if (psiTypeParameterArr.length == 0) {
            PsiSubstitutor psiSubstitutor2 = PsiSubstitutor.EMPTY;
            if (psiSubstitutor2 == null) {
                $$$reportNull$$$0(24);
            }
            return psiSubstitutor2;
        }
        InferenceSession inferenceSession = new InferenceSession(psiTypeParameterArr, psiTypeArr, psiTypeArr2, psiSubstitutor, this.myManager, null);
        for (PsiType psiType : psiTypeArr) {
            if (!inferenceSession.isProperType(inferenceSession.substituteWithInferenceVariables(psiType))) {
                PsiSubstitutor psiSubstitutorInfer = inferenceSession.infer();
                if (psiSubstitutorInfer == null) {
                    $$$reportNull$$$0(25);
                }
                return psiSubstitutorInfer;
            }
        }
        for (PsiType psiType2 : psiTypeArr2) {
            if (!inferenceSession.isProperType(inferenceSession.substituteWithInferenceVariables(psiType2))) {
                PsiSubstitutor psiSubstitutorInfer2 = inferenceSession.infer();
                if (psiSubstitutorInfer2 == null) {
                    $$$reportNull$$$0(26);
                }
                return psiSubstitutorInfer2;
            }
        }
        PsiSubstitutor psiSubstitutor3 = PsiSubstitutor.EMPTY;
        if (psiSubstitutor3 == null) {
            $$$reportNull$$$0(27);
        }
        return psiSubstitutor3;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper
    public PsiType getSubstitutionForTypeParameter(PsiTypeParameter psiTypeParameter, PsiType psiType, PsiType psiType2, boolean z, LanguageLevel languageLevel) {
        PsiType[] psiTypeArr;
        PsiType[] psiTypeArr2;
        if (PsiType.VOID.equals(psiType2) || PsiType.VOID.equals(psiType)) {
            return PsiType.NULL;
        }
        if ((psiType instanceof PsiArrayType) && (psiType2 instanceof PsiArrayType)) {
            return getSubstitutionForTypeParameter(psiTypeParameter, ((PsiArrayType) psiType).getComponentType(), ((PsiArrayType) psiType2).getComponentType(), z, languageLevel);
        }
        if (!(psiType instanceof PsiClassType)) {
            return PsiType.NULL;
        }
        if (psiType2 == null) {
            return PsiType.NULL;
        }
        PsiClassType psiClassType = (PsiClassType) psiType;
        if (!(psiClassType.resolve() instanceof PsiTypeParameter) && !psiType2.isAssignableFrom(psiClassType.rawType())) {
            return PsiType.NULL;
        }
        if (z) {
            psiTypeArr = new PsiType[]{psiType};
            psiTypeArr2 = new PsiType[]{psiType2};
        } else {
            psiTypeArr = new PsiType[]{psiType2};
            psiTypeArr2 = new PsiType[]{psiType};
        }
        PsiType[] psiTypeArr3 = psiTypeArr;
        PsiType[] psiTypeArr4 = psiTypeArr2;
        PsiTypeParameterListOwner owner = psiTypeParameter.getOwner();
        InferenceSession inferenceSession = new InferenceSession(owner != null ? owner.getTypeParameters() : new PsiTypeParameter[]{psiTypeParameter}, psiTypeArr3, psiTypeArr4, PsiSubstitutor.EMPTY, this.myManager, null);
        if (inferenceSession.isProperType(inferenceSession.substituteWithInferenceVariables(psiType)) && inferenceSession.isProperType(inferenceSession.substituteWithInferenceVariables(psiType2))) {
            for (PsiClassType psiClassType2 : psiTypeParameter.getExtendsListTypes()) {
                if (inferenceSession.isProperType(inferenceSession.substituteWithInferenceVariables(psiClassType2))) {
                }
            }
            return PsiType.NULL;
        }
        return inferenceSession.infer().substitute(psiTypeParameter);
    }
}
