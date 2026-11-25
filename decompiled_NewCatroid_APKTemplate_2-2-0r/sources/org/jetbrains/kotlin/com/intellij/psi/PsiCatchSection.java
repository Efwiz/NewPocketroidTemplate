package org.jetbrains.kotlin.com.intellij.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiCatchSection extends PsiElement {
    public static final PsiCatchSection[] EMPTY_ARRAY = new PsiCatchSection[0];
    public static final ArrayFactory<PsiCatchSection> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiCatchSection.lambda$static$0(i);
        }
    };

    PsiCodeBlock getCatchBlock();

    PsiType getCatchType();

    PsiJavaToken getLParenth();

    PsiParameter getParameter();

    List<PsiType> getPreciseCatchTypes();

    PsiJavaToken getRParenth();

    PsiTryStatement getTryStatement();

    static /* synthetic */ PsiCatchSection[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiCatchSection[i];
    }
}
