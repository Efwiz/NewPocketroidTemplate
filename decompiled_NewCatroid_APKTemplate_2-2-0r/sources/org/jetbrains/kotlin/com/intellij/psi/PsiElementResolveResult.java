package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collection;
import java.util.Iterator;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: loaded from: classes3.dex */
public class PsiElementResolveResult implements ResolveResult {
    private final PsiElement myElement;
    private final boolean myValidResult;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? 2 : 3];
        if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiElementResolveResult";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        if (i == 2) {
            objArr[1] = "getElement";
        } else if (i == 3 || i == 4 || i == 5 || i == 6) {
            objArr[1] = "createResults";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiElementResolveResult";
        }
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiElementResolveResult(PsiElement psiElement) {
        this(psiElement, true);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
    }

    public PsiElementResolveResult(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        this.myElement = psiElement;
        this.myValidResult = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
    public PsiElement getElement() {
        PsiElement psiElement = this.myElement;
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
    public boolean isValidResult() {
        return this.myValidResult;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.myElement.equals(((PsiElementResolveResult) obj).myElement);
    }

    public int hashCode() {
        return this.myElement.hashCode();
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("PsiElementResolveResult with ").append(this.myElement.getClass()).append(": ");
        PsiElement psiElement = this.myElement;
        return sbAppend.append(psiElement instanceof PsiNamedElement ? ((PsiNamedElement) psiElement).getName() : psiElement.getText()).toString();
    }

    public static ResolveResult[] createResults(Collection<? extends PsiElement> collection) {
        if (collection == null || collection.isEmpty()) {
            ResolveResult[] resolveResultArr = EMPTY_ARRAY;
            if (resolveResultArr == null) {
                $$$reportNull$$$0(3);
            }
            return resolveResultArr;
        }
        ResolveResult[] resolveResultArr2 = new ResolveResult[collection.size()];
        Iterator<? extends PsiElement> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            resolveResultArr2[i] = new PsiElementResolveResult(it.next());
            i++;
        }
        return resolveResultArr2;
    }

    public static ResolveResult[] createResults(PsiElement... psiElementArr) {
        if (psiElementArr == null || psiElementArr.length == 0) {
            ResolveResult[] resolveResultArr = EMPTY_ARRAY;
            if (resolveResultArr == null) {
                $$$reportNull$$$0(5);
            }
            return resolveResultArr;
        }
        ResolveResult[] resolveResultArr2 = new ResolveResult[psiElementArr.length];
        for (int i = 0; i < psiElementArr.length; i++) {
            resolveResultArr2[i] = new PsiElementResolveResult(psiElementArr[i]);
        }
        return resolveResultArr2;
    }
}
