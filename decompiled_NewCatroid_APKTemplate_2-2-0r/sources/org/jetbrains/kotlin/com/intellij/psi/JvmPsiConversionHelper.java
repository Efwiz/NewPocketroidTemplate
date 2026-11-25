package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmMethod;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeDeclaration;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeParameter;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmSubstitutor;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public interface JvmPsiConversionHelper {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "project";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/JvmPsiConversionHelper";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/JvmPsiConversionHelper";
        } else {
            objArr[1] = "getInstance";
        }
        if (i != 1) {
            objArr[2] = "getInstance";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    PsiMethod convertMethod(JvmMethod jvmMethod);

    PsiSubstitutor convertSubstitutor(JvmSubstitutor jvmSubstitutor);

    PsiType convertType(JvmType jvmType);

    PsiClass convertTypeDeclaration(JvmTypeDeclaration jvmTypeDeclaration);

    PsiTypeParameter convertTypeParameter(JvmTypeParameter jvmTypeParameter);

    static JvmPsiConversionHelper getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        JvmPsiConversionHelper jvmPsiConversionHelper = (JvmPsiConversionHelper) project.getService(JvmPsiConversionHelper.class);
        if (jvmPsiConversionHelper == null) {
            $$$reportNull$$$0(1);
        }
        return jvmPsiConversionHelper;
    }
}
