package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmAnnotation;
import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmNestedAnnotationValue;

/* JADX INFO: compiled from: PsiAnnotationAttributeValues.java */
/* JADX INFO: loaded from: classes3.dex */
class PsiNestedAnnotationValue extends PsiAnnotationAttributeValue<PsiAnnotation> implements JvmNestedAnnotationValue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "value";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiNestedAnnotationValue";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiNestedAnnotationValue";
        } else {
            objArr[1] = "getValue";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiNestedAnnotationValue(PsiAnnotation psiAnnotation) {
        super(psiAnnotation);
        if (psiAnnotation == null) {
            $$$reportNull$$$0(0);
        }
    }

    public JvmAnnotation getValue() {
        JvmAnnotation jvmAnnotation = (JvmAnnotation) this.myElement;
        if (jvmAnnotation == null) {
            $$$reportNull$$$0(1);
        }
        return jvmAnnotation;
    }
}
