package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveVisitor;

/* JADX INFO: loaded from: classes3.dex */
public abstract class RecursiveTreeElementVisitor extends TreeElementVisitor implements PsiRecursiveVisitor {
    protected abstract boolean visitNode(TreeElement treeElement);

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
    public void visitLeaf(LeafElement leafElement) {
        visitNode(leafElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElementVisitor
    public void visitComposite(CompositeElement compositeElement) {
        if (visitNode(compositeElement)) {
            TreeElement firstChildNode = compositeElement.getFirstChildNode();
            while (firstChildNode != null) {
                TreeElement treeNext = firstChildNode.getTreeNext();
                firstChildNode.acceptTree(this);
                firstChildNode = treeNext;
            }
        }
    }
}
