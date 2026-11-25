package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiClassInitializer extends PsiMember {
    public static final PsiClassInitializer[] EMPTY_ARRAY = new PsiClassInitializer[0];
    public static final ArrayFactory<PsiClassInitializer> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiClassInitializer.lambda$static$0(i);
        }
    };

    PsiCodeBlock getBody();

    static /* synthetic */ PsiClassInitializer[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiClassInitializer[i];
    }
}
