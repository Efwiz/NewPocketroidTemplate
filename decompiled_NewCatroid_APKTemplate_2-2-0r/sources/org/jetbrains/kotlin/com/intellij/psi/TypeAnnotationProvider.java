package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes3.dex */
public interface TypeAnnotationProvider {
    public static final TypeAnnotationProvider EMPTY = new TypeAnnotationProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/TypeAnnotationProvider$1", "getAnnotations"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
        public PsiAnnotation[] getAnnotations() {
            PsiAnnotation[] psiAnnotationArr = PsiAnnotation.EMPTY_ARRAY;
            if (psiAnnotationArr == null) {
                $$$reportNull$$$0(0);
            }
            return psiAnnotationArr;
        }

        public String toString() {
            return "EMPTY";
        }
    };

    PsiAnnotation[] getAnnotations();

    public static final class Static implements TypeAnnotationProvider {
        private final PsiAnnotation[] myAnnotations;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[i != 1 ? 2 : 3];
            if (i != 1) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/TypeAnnotationProvider$Static";
            } else {
                objArr[0] = "annotations";
            }
            if (i != 1) {
                objArr[1] = "getAnnotations";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/TypeAnnotationProvider$Static";
            }
            if (i == 1) {
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalArgumentException(str2);
            }
        }

        private Static(PsiAnnotation[] psiAnnotationArr) {
            this.myAnnotations = psiAnnotationArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
        public PsiAnnotation[] getAnnotations() {
            PsiAnnotation[] psiAnnotationArr = this.myAnnotations;
            if (psiAnnotationArr == null) {
                $$$reportNull$$$0(0);
            }
            return psiAnnotationArr;
        }

        public static TypeAnnotationProvider create(PsiAnnotation[] psiAnnotationArr) {
            if (psiAnnotationArr == null) {
                $$$reportNull$$$0(1);
            }
            return psiAnnotationArr.length == 0 ? EMPTY : new Static(psiAnnotationArr);
        }
    }
}
