package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationAttributeValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;

/* JADX INFO: compiled from: PsiAnnotationAttributeValues.java */
/* JADX INFO: loaded from: classes3.dex */
abstract class PsiAnnotationAttributeValue<T extends PsiAnnotationMemberValue> implements JvmAnnotationAttributeValue {
    protected final T myElement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/com/intellij/psi/PsiAnnotationAttributeValue", "<init>"));
    }

    protected PsiAnnotationAttributeValue(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        this.myElement = t;
    }
}
