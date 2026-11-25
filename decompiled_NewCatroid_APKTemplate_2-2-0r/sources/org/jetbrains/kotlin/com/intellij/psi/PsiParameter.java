package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiParameter extends JvmParameter, PsiJvmModifiersOwner, PsiVariable {
    public static final PsiParameter[] EMPTY_ARRAY = new PsiParameter[0];
    public static final ArrayFactory<PsiParameter> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiParameter$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiParameter.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/PsiParameter", "getAnnotations"));
    }

    PsiElement getDeclarationScope();

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    @Override // org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    PsiType getType();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    PsiTypeElement getTypeElement();

    boolean isVarArgs();

    static /* synthetic */ PsiParameter[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiParameter[i];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    default PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] annotations = super.getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(0);
        }
        return annotations;
    }
}
