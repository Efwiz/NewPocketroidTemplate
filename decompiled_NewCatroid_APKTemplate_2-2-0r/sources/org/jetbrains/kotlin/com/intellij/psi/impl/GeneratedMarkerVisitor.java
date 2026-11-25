package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public class GeneratedMarkerVisitor extends RecursiveTreeElementWalkingVisitor {
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveTreeElementWalkingVisitor
    protected void visitNode(TreeElement treeElement) {
        CodeEditUtil.setNodeGenerated(treeElement, true);
        super.visitNode(treeElement);
    }

    public static void markGenerated(PsiElement psiElement) {
        ((TreeElement) psiElement.getNode()).acceptTree(new GeneratedMarkerVisitor());
    }
}
