package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationAttributeValue;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiNameValuePair extends JvmAnnotationAttribute, PsiElement {
    public static final PsiNameValuePair[] EMPTY_ARRAY = new PsiNameValuePair[0];
    public static final ArrayFactory<PsiNameValuePair> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiNameValuePair.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/PsiNameValuePair", "getAttributeName"));
    }

    String getLiteralValue();

    String getName();

    PsiIdentifier getNameIdentifier();

    PsiAnnotationMemberValue getValue();

    /* JADX INFO: renamed from: setValue */
    PsiAnnotationMemberValue mo6100setValue(PsiAnnotationMemberValue psiAnnotationMemberValue);

    static /* synthetic */ PsiNameValuePair[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiNameValuePair[i];
    }

    default PsiAnnotationMemberValue getDetachedValue() {
        return getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationAttribute
    default String getAttributeName() {
        String annotationAttributeName = PsiJvmConversionHelper.getAnnotationAttributeName(this);
        if (annotationAttributeName == null) {
            $$$reportNull$$$0(0);
        }
        return annotationAttributeName;
    }

    default JvmAnnotationAttributeValue getAttributeValue() {
        return PsiJvmConversionHelper.getAnnotationAttributeValue(this);
    }
}
