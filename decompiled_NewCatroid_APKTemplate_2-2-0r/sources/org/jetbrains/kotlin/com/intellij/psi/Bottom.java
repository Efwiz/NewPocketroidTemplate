package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public final class Bottom extends PsiType {
    public static final Bottom BOTTOM = new Bottom();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "text";
        } else if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/Bottom";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/Bottom";
        } else {
            objArr[1] = "getPresentableText";
        }
        if (i == 1) {
            objArr[2] = "equalsToText";
        } else if (i == 2) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        return true;
    }

    private Bottom() {
        super(TypeAnnotationProvider.EMPTY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText() {
        String canonicalText = getCanonicalText();
        if (canonicalText == null) {
            $$$reportNull$$$0(0);
        }
        return canonicalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText() {
        return "_";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str.equals("_");
    }

    public boolean equals(Object obj) {
        return obj instanceof Bottom;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiTypeVisitor instanceof PsiTypeVisitorEx) {
            return (A) ((PsiTypeVisitorEx) psiTypeVisitor).visitBottom(this);
        }
        return psiTypeVisitor.visitType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        throw new UnsupportedOperationException();
    }
}
