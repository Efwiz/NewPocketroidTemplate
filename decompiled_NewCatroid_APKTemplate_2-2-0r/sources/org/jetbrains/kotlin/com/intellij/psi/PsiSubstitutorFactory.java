package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiSubstitutorFactory {
    protected abstract PsiSubstitutor createSubstitutor(Map<? extends PsiTypeParameter, ? extends PsiType> map);

    protected abstract PsiSubstitutor createSubstitutor(PsiClass psiClass, PsiType[] psiTypeArr);

    protected abstract PsiSubstitutor createSubstitutor(PsiTypeParameter psiTypeParameter, PsiType psiType);

    static PsiSubstitutorFactory getInstance() {
        return (PsiSubstitutorFactory) ApplicationManager.getApplication().getService(PsiSubstitutorFactory.class);
    }
}
