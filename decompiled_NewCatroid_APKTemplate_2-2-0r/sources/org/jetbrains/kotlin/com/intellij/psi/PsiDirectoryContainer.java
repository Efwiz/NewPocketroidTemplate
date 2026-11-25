package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiDirectoryContainer extends PsiNamedElement {
    PsiDirectory[] getDirectories();

    PsiDirectory[] getDirectories(GlobalSearchScope globalSearchScope);
}
