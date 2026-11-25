package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiAnnotationMemberValue extends PsiElement {
    public static final PsiAnnotationMemberValue[] EMPTY_ARRAY = new PsiAnnotationMemberValue[0];
    public static final ArrayFactory<PsiAnnotationMemberValue> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiAnnotationMemberValue.lambda$static$0(i);
        }
    };

    static /* synthetic */ PsiAnnotationMemberValue[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiAnnotationMemberValue[i];
    }
}
