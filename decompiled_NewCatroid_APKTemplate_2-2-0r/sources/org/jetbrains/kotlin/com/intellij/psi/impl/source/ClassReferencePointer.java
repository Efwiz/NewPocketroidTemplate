package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;

/* JADX INFO: loaded from: classes3.dex */
interface ClassReferencePointer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "ref", "org/jetbrains/kotlin/com/intellij/psi/impl/source/ClassReferencePointer", "constant"));
    }

    PsiJavaCodeReferenceElement retrieveNonNullReference();

    PsiJavaCodeReferenceElement retrieveReference();

    static ClassReferencePointer constant(final PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(0);
        }
        return new ClassReferencePointer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassReferencePointer.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/ClassReferencePointer$1";
                if (i != 1) {
                    objArr[1] = "retrieveReference";
                } else {
                    objArr[1] = "retrieveNonNullReference";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassReferencePointer
            public PsiJavaCodeReferenceElement retrieveReference() {
                PsiJavaCodeReferenceElement psiJavaCodeReferenceElement2 = psiJavaCodeReferenceElement;
                if (psiJavaCodeReferenceElement2 == null) {
                    $$$reportNull$$$0(0);
                }
                return psiJavaCodeReferenceElement2;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassReferencePointer
            public PsiJavaCodeReferenceElement retrieveNonNullReference() {
                PsiJavaCodeReferenceElement psiJavaCodeReferenceElement2 = psiJavaCodeReferenceElement;
                if (psiJavaCodeReferenceElement2 == null) {
                    $$$reportNull$$$0(1);
                }
                return psiJavaCodeReferenceElement2;
            }
        };
    }
}
