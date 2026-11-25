package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaResolveResult extends ResolveResult {
    public static final JavaResolveResult[] EMPTY_ARRAY = new JavaResolveResult[0];
    public static final JavaResolveResult EMPTY = new JavaResolveResult() { // from class: org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/JavaResolveResult$1", "getSubstitutor"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public PsiElement getCurrentFileResolveScope() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
        public PsiElement getElement() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isAccessible() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isPackagePrefixPackageReference() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public boolean isStaticsScopeCorrect() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
        public boolean isValidResult() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult
        public PsiSubstitutor getSubstitutor() {
            PsiSubstitutor psiSubstitutor = PsiSubstitutor.EMPTY;
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(0);
            }
            return psiSubstitutor;
        }
    };

    PsiElement getCurrentFileResolveScope();

    PsiSubstitutor getSubstitutor();

    boolean isAccessible();

    boolean isPackagePrefixPackageReference();

    boolean isStaticsScopeCorrect();
}
