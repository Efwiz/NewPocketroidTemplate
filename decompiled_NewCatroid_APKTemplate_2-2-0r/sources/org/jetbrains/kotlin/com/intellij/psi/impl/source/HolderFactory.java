package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public interface HolderFactory {
    DummyHolder createHolder(PsiManager psiManager, Language language, PsiElement psiElement);

    DummyHolder createHolder(PsiManager psiManager, PsiElement psiElement);

    DummyHolder createHolder(PsiManager psiManager, PsiElement psiElement, CharTable charTable);

    DummyHolder createHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement);

    DummyHolder createHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement, CharTable charTable);

    DummyHolder createHolder(PsiManager psiManager, CharTable charTable, Language language);

    DummyHolder createHolder(PsiManager psiManager, CharTable charTable, boolean z);
}
