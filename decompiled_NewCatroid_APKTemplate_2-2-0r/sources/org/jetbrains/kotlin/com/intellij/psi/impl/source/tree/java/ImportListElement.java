package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public class ImportListElement extends CompositeElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ImportListElement", "deleteChildInternal"));
    }

    public ImportListElement() {
        super(JavaElementType.IMPORT_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        if (bool == null && treeElement == aSTNode && (treeElement.getElementType() == JavaElementType.IMPORT_STATEMENT || treeElement.getElementType() == JavaElementType.IMPORT_STATIC_STATEMENT)) {
            PsiImportList psiImportList = (PsiImportList) SourceTreeToPsiMap.treeElementToPsi(this);
            PsiImportStatementBase psiImportStatementBase = (PsiImportStatementBase) SourceTreeToPsiMap.treeElementToPsi(treeElement);
            JavaPsiImplementationHelper javaPsiImplementationHelper = JavaPsiImplementationHelper.getInstance(psiImportList.getProject());
            if (javaPsiImplementationHelper != null) {
                aSTNode2 = javaPsiImplementationHelper.getDefaultImportAnchor(psiImportList, psiImportStatementBase);
            }
            bool = Boolean.TRUE;
        }
        return super.addInternal(treeElement, aSTNode, aSTNode2, bool);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        super.deleteChildInternal(aSTNode);
        TreeElement firstChildNode = getFirstChildNode();
        if (firstChildNode != null && firstChildNode == getLastChildNode() && firstChildNode.getElementType() == JavaTokenType.SEMICOLON) {
            super.deleteChildInternal(firstChildNode);
        }
    }
}
