package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTypeVisitorEx<A> extends PsiTypeVisitor<A> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = PsiKeyword.VAR;
        } else {
            objArr[0] = "bottom";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiTypeVisitorEx";
        if (i != 1) {
            objArr[2] = "visitTypeVariable";
        } else {
            objArr[2] = "visitBottom";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public A visitTypeVariable(PsiTypeVariable psiTypeVariable) {
        if (psiTypeVariable == null) {
            $$$reportNull$$$0(0);
        }
        return visitType(psiTypeVariable);
    }

    public A visitBottom(Bottom bottom) {
        if (bottom == null) {
            $$$reportNull$$$0(1);
        }
        return visitType(bottom);
    }
}
