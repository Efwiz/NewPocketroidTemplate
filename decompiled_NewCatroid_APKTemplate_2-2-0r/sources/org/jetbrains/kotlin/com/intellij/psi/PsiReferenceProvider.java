package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiReferenceProvider {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "hints";
        } else if (i != 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "target";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceProvider";
        if (i != 2) {
            objArr[2] = "acceptsHints";
        } else {
            objArr[2] = "acceptsTarget";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public boolean acceptsTarget(PsiElement psiElement) {
        if (psiElement != null) {
            return true;
        }
        $$$reportNull$$$0(2);
        return true;
    }

    public abstract PsiReference[] getReferencesByElement(PsiElement psiElement, ProcessingContext processingContext);

    public boolean acceptsHints(PsiElement psiElement, PsiReferenceService.Hints hints) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (hints == null) {
            $$$reportNull$$$0(1);
        }
        PsiElement psiElement2 = hints.target;
        return psiElement2 == null || acceptsTarget(psiElement2);
    }
}
