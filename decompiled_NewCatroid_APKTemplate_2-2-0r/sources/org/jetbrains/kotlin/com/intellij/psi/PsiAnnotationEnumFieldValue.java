package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmEnumField;
import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationEnumFieldValue;

/* JADX INFO: compiled from: PsiAnnotationAttributeValues.java */
/* JADX INFO: loaded from: classes3.dex */
class PsiAnnotationEnumFieldValue extends PsiAnnotationAttributeValue<PsiReferenceExpression> implements JvmAnnotationEnumFieldValue {
    private final JvmEnumField myEnumField;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "value";
        } else {
            objArr[0] = "field";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnnotationEnumFieldValue";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiAnnotationEnumFieldValue(PsiReferenceExpression psiReferenceExpression, JvmEnumField jvmEnumField) {
        super(psiReferenceExpression);
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (jvmEnumField == null) {
            $$$reportNull$$$0(1);
        }
        this.myEnumField = jvmEnumField;
    }

    public JvmEnumField getField() {
        return this.myEnumField;
    }
}
