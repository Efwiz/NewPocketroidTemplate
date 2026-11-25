package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiTypeElement extends PsiAnnotationOwner, PsiElement {
    public static final PsiTypeElement[] EMPTY_ARRAY = new PsiTypeElement[0];
    public static final ArrayFactory<PsiTypeElement> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiTypeElement.lambda$static$0(i);
        }
    };

    default boolean acceptsAnnotations() {
        return true;
    }

    PsiJavaCodeReferenceElement getInnermostComponentReferenceElement();

    PsiType getType();

    default boolean isInferredType() {
        return false;
    }

    static /* synthetic */ PsiTypeElement[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiTypeElement[i];
    }
}
