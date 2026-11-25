package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmMethod;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeDeclaration;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeParameter;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmSubstitutor;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmType;
import org.jetbrains.kotlin.com.intellij.psi.JvmPsiConversionHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiJvmSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;

/* JADX INFO: loaded from: classes3.dex */
public class JvmPsiConversionHelperImpl implements JvmPsiConversionHelper {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 8 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 8 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 6:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/JvmPsiConversionHelperImpl";
                break;
            case 2:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 4:
                objArr[0] = "substitutor";
                break;
            case 7:
                objArr[0] = "method";
                break;
            default:
                objArr[0] = "typeParameter";
                break;
        }
        if (i == 1) {
            objArr[1] = "convertTypeParameter";
        } else if (i == 3) {
            objArr[1] = "convertType";
        } else if (i == 8) {
            objArr[1] = "convertMethod";
        } else if (i == 5 || i == 6) {
            objArr[1] = "convertSubstitutor";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/JvmPsiConversionHelperImpl";
        }
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 6:
            case 8:
                break;
            case 2:
                objArr[2] = "convertType";
                break;
            case 4:
                objArr[2] = "convertSubstitutor";
                break;
            case 7:
                objArr[2] = "convertMethod";
                break;
            default:
                objArr[2] = "convertTypeParameter";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 8 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JvmPsiConversionHelper
    public PsiClass convertTypeDeclaration(JvmTypeDeclaration jvmTypeDeclaration) {
        if (jvmTypeDeclaration instanceof PsiClass) {
            return (PsiClass) jvmTypeDeclaration;
        }
        throw new RuntimeException("TODO");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JvmPsiConversionHelper
    public PsiTypeParameter convertTypeParameter(JvmTypeParameter jvmTypeParameter) {
        if (jvmTypeParameter == null) {
            $$$reportNull$$$0(0);
        }
        if (!(jvmTypeParameter instanceof PsiTypeParameter)) {
            throw new RuntimeException("TODO");
        }
        PsiTypeParameter psiTypeParameter = (PsiTypeParameter) jvmTypeParameter;
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeParameter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JvmPsiConversionHelper
    public PsiType convertType(JvmType jvmType) {
        if (jvmType == null) {
            $$$reportNull$$$0(2);
        }
        if (!(jvmType instanceof PsiType)) {
            throw new RuntimeException("TODO");
        }
        PsiType psiType = (PsiType) jvmType;
        if (psiType == null) {
            $$$reportNull$$$0(3);
        }
        return psiType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JvmPsiConversionHelper
    public PsiSubstitutor convertSubstitutor(JvmSubstitutor jvmSubstitutor) {
        if (jvmSubstitutor == null) {
            $$$reportNull$$$0(4);
        }
        if (jvmSubstitutor instanceof PsiJvmSubstitutor) {
            PsiSubstitutor psiSubstitutor = ((PsiJvmSubstitutor) jvmSubstitutor).getPsiSubstitutor();
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(5);
            }
            return psiSubstitutor;
        }
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        for (JvmTypeParameter jvmTypeParameter : jvmSubstitutor.getTypeParameters()) {
            PsiTypeParameter psiTypeParameterConvertTypeParameter = convertTypeParameter(jvmTypeParameter);
            JvmType jvmTypeSubstitute = jvmSubstitutor.substitute(jvmTypeParameter);
            psiSubstitutorPut = psiSubstitutorPut.put(psiTypeParameterConvertTypeParameter, jvmTypeSubstitute == null ? null : convertType(jvmTypeSubstitute));
        }
        if (psiSubstitutorPut == null) {
            $$$reportNull$$$0(6);
        }
        return psiSubstitutorPut;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JvmPsiConversionHelper
    public PsiMethod convertMethod(JvmMethod jvmMethod) {
        if (jvmMethod == null) {
            $$$reportNull$$$0(7);
        }
        if (!(jvmMethod instanceof PsiMethod)) {
            throw new RuntimeException("TODO");
        }
        PsiMethod psiMethod = (PsiMethod) jvmMethod;
        if (psiMethod == null) {
            $$$reportNull$$$0(8);
        }
        return psiMethod;
    }
}
