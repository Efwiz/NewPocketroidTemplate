package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiTypeVariable extends PsiType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/PsiTypeVariable", "accept"));
    }

    public abstract int getIndex();

    public abstract boolean isValidInContext(PsiType psiType);

    protected PsiTypeVariable() {
        super(TypeAnnotationProvider.EMPTY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiTypeVisitor instanceof PsiTypeVisitorEx) {
            return (A) ((PsiTypeVisitorEx) psiTypeVisitor).visitTypeVariable(this);
        }
        return psiTypeVisitor.visitType(this);
    }
}
