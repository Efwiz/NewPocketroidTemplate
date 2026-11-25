package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiImportList extends PsiElement {
    public static final PsiImportList[] EMPTY_ARRAY = new PsiImportList[0];
    public static final ArrayFactory<PsiImportList> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiImportList$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiImportList.lambda$static$0(i);
        }
    };

    PsiImportStatement findOnDemandImportStatement(String str);

    PsiImportStatement findSingleClassImportStatement(String str);

    PsiImportStatementBase findSingleImportStatement(String str);

    PsiImportStatementBase[] getAllImportStatements();

    PsiImportStatement[] getImportStatements();

    PsiImportStaticStatement[] getImportStaticStatements();

    boolean isReplaceEquivalent(PsiImportList psiImportList);

    static /* synthetic */ PsiImportList[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiImportList[i];
    }
}
