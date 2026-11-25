package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiCodeFragment extends PsiFile {
    void forceResolveScope(GlobalSearchScope globalSearchScope);

    GlobalSearchScope getForcedResolveScope();
}
