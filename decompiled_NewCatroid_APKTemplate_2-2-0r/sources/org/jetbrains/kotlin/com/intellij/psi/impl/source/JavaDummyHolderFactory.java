package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class JavaDummyHolderFactory implements HolderFactory {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "manager", "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaDummyHolderFactory", "createHolder"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
    public DummyHolder createHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement) {
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        return new JavaDummyHolder(psiManager, treeElement, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
    public DummyHolder createHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement, CharTable charTable) {
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        return new JavaDummyHolder(psiManager, treeElement, psiElement, charTable);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
    public DummyHolder createHolder(PsiManager psiManager, PsiElement psiElement) {
        if (psiManager == null) {
            $$$reportNull$$$0(2);
        }
        return new JavaDummyHolder(psiManager, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
    public DummyHolder createHolder(PsiManager psiManager, Language language, PsiElement psiElement) {
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
        return language == JavaLanguage.INSTANCE ? new JavaDummyHolder(psiManager, psiElement) : new DummyHolder(psiManager, language, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
    public DummyHolder createHolder(PsiManager psiManager, PsiElement psiElement, CharTable charTable) {
        if (psiManager == null) {
            $$$reportNull$$$0(4);
        }
        return new JavaDummyHolder(psiManager, psiElement, charTable);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
    public DummyHolder createHolder(PsiManager psiManager, CharTable charTable, Language language) {
        if (psiManager == null) {
            $$$reportNull$$$0(5);
        }
        return new JavaDummyHolder(psiManager, charTable);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.HolderFactory
    public DummyHolder createHolder(PsiManager psiManager, CharTable charTable, boolean z) {
        if (psiManager == null) {
            $$$reportNull$$$0(6);
        }
        return new JavaDummyHolder(psiManager, charTable, z);
    }
}
