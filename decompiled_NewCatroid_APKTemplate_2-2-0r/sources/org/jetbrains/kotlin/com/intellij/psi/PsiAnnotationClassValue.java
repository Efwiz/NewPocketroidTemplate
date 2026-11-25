package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmClass;
import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationClassValue;

/* JADX INFO: compiled from: PsiAnnotationAttributeValues.java */
/* JADX INFO: loaded from: classes3.dex */
class PsiAnnotationClassValue extends PsiAnnotationAttributeValue<PsiClassObjectAccessExpression> implements JvmAnnotationClassValue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/com/intellij/psi/PsiAnnotationClassValue", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiAnnotationClassValue(PsiClassObjectAccessExpression psiClassObjectAccessExpression) {
        super(psiClassObjectAccessExpression);
        if (psiClassObjectAccessExpression == null) {
            $$$reportNull$$$0(0);
        }
    }

    private PsiJavaCodeReferenceElement getReferenceElement() {
        return ((PsiClassObjectAccessExpression) this.myElement).getOperand().getInnermostComponentReferenceElement();
    }

    public String getQualifiedName() {
        PsiJavaCodeReferenceElement referenceElement = getReferenceElement();
        if (referenceElement == null) {
            return null;
        }
        return referenceElement.getQualifiedName();
    }

    public JvmClass getClazz() {
        PsiJavaCodeReferenceElement referenceElement = getReferenceElement();
        if (referenceElement == null) {
            return null;
        }
        PsiElement psiElementResolve = referenceElement.resolve();
        if (psiElementResolve instanceof JvmClass) {
            return (JvmClass) psiElementResolve;
        }
        return null;
    }
}
