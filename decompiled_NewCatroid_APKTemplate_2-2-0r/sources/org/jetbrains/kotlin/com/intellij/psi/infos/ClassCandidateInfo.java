package org.jetbrains.kotlin.com.intellij.psi.infos;

import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;

/* JADX INFO: loaded from: classes3.dex */
public class ClassCandidateInfo extends CandidateInfo {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = "substitutor";
        } else if (i != 4) {
            objArr[0] = "candidate";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/infos/ClassCandidateInfo";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/infos/ClassCandidateInfo";
        } else {
            objArr[1] = "getElement";
        }
        if (i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassCandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor, boolean z, PsiElement psiElement2) {
        super(psiElement, psiSubstitutor, z, false, psiElement2);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassCandidateInfo(PsiElement psiElement, PsiSubstitutor psiSubstitutor) {
        super(psiElement, psiSubstitutor, false, false);
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo, org.jetbrains.kotlin.com.intellij.psi.ResolveResult
    public PsiClass getElement() {
        PsiClass psiClass = (PsiClass) super.getElement();
        if (psiClass == null) {
            $$$reportNull$$$0(4);
        }
        return psiClass;
    }
}
