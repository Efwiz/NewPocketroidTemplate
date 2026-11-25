package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutorFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;

/* JADX INFO: loaded from: classes3.dex */
public class PsiSubstitutorFactoryImpl extends PsiSubstitutorFactory {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "aClass";
        } else if (i != 2) {
            objArr[0] = "typeParameter";
        } else {
            objArr[0] = "map";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSubstitutorFactoryImpl";
        objArr[2] = "createSubstitutor";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutorFactory
    protected PsiSubstitutor createSubstitutor(PsiTypeParameter psiTypeParameter, PsiType psiType) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(0);
        }
        return new PsiSubstitutorImpl(psiTypeParameter, psiType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutorFactory
    protected PsiSubstitutor createSubstitutor(PsiClass psiClass, PsiType[] psiTypeArr) {
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiSubstitutorImpl(psiClass, psiTypeArr);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutorFactory
    protected PsiSubstitutor createSubstitutor(Map<? extends PsiTypeParameter, ? extends PsiType> map) {
        if (map == null) {
            $$$reportNull$$$0(2);
        }
        return new PsiSubstitutorImpl(map);
    }
}
