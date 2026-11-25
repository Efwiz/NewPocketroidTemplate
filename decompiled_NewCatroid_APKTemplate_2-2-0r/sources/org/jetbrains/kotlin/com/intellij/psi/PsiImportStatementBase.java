package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiImportStatementBase extends PsiElement {
    public static final PsiImportStatementBase[] EMPTY_ARRAY = new PsiImportStatementBase[0];
    public static final ArrayFactory<PsiImportStatementBase> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiImportStatementBase.lambda$static$0(i);
        }
    };

    PsiJavaCodeReferenceElement getImportReference();

    boolean isForeignFileImport();

    boolean isOnDemand();

    PsiElement resolve();

    static /* synthetic */ PsiImportStatementBase[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiImportStatementBase[i];
    }
}
