package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiStatement extends PsiElement {
    public static final PsiStatement[] EMPTY_ARRAY = new PsiStatement[0];
    public static final ArrayFactory<PsiStatement> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiStatement$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiStatement.lambda$static$0(i);
        }
    };

    static /* synthetic */ PsiStatement[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiStatement[i];
    }
}
