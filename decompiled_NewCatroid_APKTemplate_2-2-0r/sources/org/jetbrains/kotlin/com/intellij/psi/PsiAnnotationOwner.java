package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiAnnotationOwner {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "qualifiedName", "org/jetbrains/kotlin/com/intellij/psi/PsiAnnotationOwner", "hasAnnotation"));
    }

    PsiAnnotation addAnnotation(String str);

    PsiAnnotation findAnnotation(String str);

    /* JADX INFO: renamed from: getAnnotations */
    PsiAnnotation[] mo6533getAnnotations();

    PsiAnnotation[] getApplicableAnnotations();

    default boolean hasAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return findAnnotation(str) != null;
    }
}
