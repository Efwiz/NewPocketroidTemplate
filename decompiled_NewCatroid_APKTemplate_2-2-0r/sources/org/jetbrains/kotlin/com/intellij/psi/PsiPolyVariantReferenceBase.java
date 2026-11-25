package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiPolyVariantReferenceBase<T extends PsiElement> extends PsiReferenceBase<T> implements PsiPolyVariantReference {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 3 || i == 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "psiElement";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiPolyVariantReferenceBase";
        if (i != 4) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "isReferenceTo";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiPolyVariantReferenceBase(T t) {
        super(t);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiPolyVariantReferenceBase(T t, TextRange textRange) {
        super(t, textRange);
        if (t == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiPolyVariantReferenceBase(T t, boolean z) {
        super(t, z);
        if (t == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiPolyVariantReferenceBase(T t, TextRange textRange, boolean z) {
        super(t, textRange, z);
        if (t == null) {
            $$$reportNull$$$0(3);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        ResolveResult[] resolveResultArrMultiResolve = multiResolve(false);
        if (resolveResultArrMultiResolve.length == 1) {
            return resolveResultArrMultiResolve[0].getElement();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        for (ResolveResult resolveResult : multiResolve(false)) {
            if (getElement().getManager().areElementsEquivalent(resolveResult.getElement(), psiElement)) {
                return true;
            }
        }
        return false;
    }
}
