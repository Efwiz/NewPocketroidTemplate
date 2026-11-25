package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiCaseLabelElement extends PsiElement {
    public static final PsiCaseLabelElement[] EMPTY_ARRAY = new PsiCaseLabelElement[0];
    public static final ArrayFactory<PsiCaseLabelElement> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElement$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiCaseLabelElement.lambda$static$0(i);
        }
    };

    static /* synthetic */ PsiCaseLabelElement[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiCaseLabelElement[i];
    }
}
