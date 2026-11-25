package org.jetbrains.kotlin.com.intellij.psi;

import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiReferenceService {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceService";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceService";
        } else {
            objArr[1] = "getContributedReferences";
        }
        if (i != 1) {
            objArr[2] = "getContributedReferences";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract List<PsiReference> getReferences(PsiElement psiElement, Hints hints);

    public static PsiReferenceService getService() {
        return (PsiReferenceService) ApplicationManager.getApplication().getService(PsiReferenceService.class);
    }

    public PsiReference[] getContributedReferences(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        PsiReference[] psiReferenceArr = (PsiReference[]) getReferences(psiElement, Hints.NO_HINTS).toArray(PsiReference.EMPTY_ARRAY);
        if (psiReferenceArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiReferenceArr;
    }

    public static class Hints {
        public static final Hints NO_HINTS = new Hints();
        public final Integer offsetInElement;
        public final PsiElement target;

        public Hints() {
            this.target = null;
            this.offsetInElement = null;
        }

        public Hints(PsiElement psiElement, Integer num) {
            this.target = psiElement;
            this.offsetInElement = num;
        }
    }
}
