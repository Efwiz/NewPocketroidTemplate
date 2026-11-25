package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public class PsiLambdaParameterType extends PsiType {
    private final PsiParameter myParameter;

    /* JADX WARN: Code duplicated, block: B:20:0x0030  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiLambdaParameterType";
        } else if (i == 2) {
            objArr[0] = "text";
        } else if (i == 3) {
            objArr[0] = "visitor";
        } else if (i != 4) {
            objArr[0] = "parameter";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiLambdaParameterType";
        }
        if (i == 1) {
            objArr[1] = "getPresentableText";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiLambdaParameterType";
        } else {
            objArr[1] = "getSuperTypes";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "equalsToText";
            } else if (i == 3) {
                objArr[2] = "accept";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str != null) {
            return false;
        }
        $$$reportNull$$$0(2);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiLambdaParameterType(PsiParameter psiParameter) {
        super(TypeAnnotationProvider.EMPTY);
        if (psiParameter == null) {
            $$$reportNull$$$0(0);
        }
        this.myParameter = psiParameter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText() {
        String canonicalText = getCanonicalText();
        if (canonicalText == null) {
            $$$reportNull$$$0(1);
        }
        return canonicalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText() {
        return "<lambda parameter>";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        return this.myParameter.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(3);
        }
        return psiTypeVisitor.visitType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
        if (psiTypeArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiTypeArr;
    }

    public PsiParameter getParameter() {
        return this.myParameter;
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof PsiLambdaParameterType) && this.myParameter.equals(((PsiLambdaParameterType) obj).myParameter));
    }

    public int hashCode() {
        return this.myParameter.hashCode();
    }
}
