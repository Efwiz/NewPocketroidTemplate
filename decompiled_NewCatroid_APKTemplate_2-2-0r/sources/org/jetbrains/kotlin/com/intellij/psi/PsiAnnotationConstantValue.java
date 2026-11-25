package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationConstantValue;

/* JADX INFO: compiled from: PsiAnnotationAttributeValues.java */
/* JADX INFO: loaded from: classes3.dex */
class PsiAnnotationConstantValue extends PsiAnnotationAttributeValue<PsiExpression> implements JvmAnnotationConstantValue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/com/intellij/psi/PsiAnnotationConstantValue", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiAnnotationConstantValue(PsiExpression psiExpression) {
        super(psiExpression);
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
    }

    public Object getConstantValue() {
        return JavaPsiFacade.getInstance(((PsiExpression) this.myElement).getProject()).getConstantEvaluationHelper().computeConstantExpression(this.myElement);
    }
}
