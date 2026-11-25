package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiJavaCodeReferenceElement extends PsiJavaReference, PsiQualifiedReferenceElement {
    public static final PsiJavaCodeReferenceElement[] EMPTY_ARRAY = new PsiJavaCodeReferenceElement[0];
    public static final ArrayFactory<PsiJavaCodeReferenceElement> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiJavaCodeReferenceElement.lambda$static$0(i);
        }
    };

    PsiReferenceParameterList getParameterList();

    String getQualifiedName();

    PsiElement getReferenceNameElement();

    PsiType[] getTypeParameters();

    boolean isQualified();

    static /* synthetic */ PsiJavaCodeReferenceElement[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiJavaCodeReferenceElement[i];
    }
}
