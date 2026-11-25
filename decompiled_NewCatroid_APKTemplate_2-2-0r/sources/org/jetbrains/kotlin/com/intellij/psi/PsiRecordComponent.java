package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiRecordComponent extends PsiMember, PsiVariable {
    public static final PsiRecordComponent[] EMPTY_ARRAY = new PsiRecordComponent[0];
    public static final ArrayFactory<PsiRecordComponent> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiRecordComponent.lambda$static$0(i);
        }
    };

    boolean isVarArgs();

    static /* synthetic */ PsiRecordComponent[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiRecordComponent[i];
    }
}
