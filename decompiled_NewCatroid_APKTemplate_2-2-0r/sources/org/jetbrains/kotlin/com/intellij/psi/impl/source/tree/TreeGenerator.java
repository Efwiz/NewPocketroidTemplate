package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public interface TreeGenerator {
    public static final ExtensionPointName<TreeGenerator> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.treeGenerator");

    TreeElement generateTreeFor(PsiElement psiElement, CharTable charTable, PsiManager psiManager);
}
