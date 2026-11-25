package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public class RecordHeaderElement extends CompositeElement implements Constants {
    private final TokenSet RECORD_TOKEN_SET;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/RecordHeaderElement", "deleteChildInternal"));
    }

    public RecordHeaderElement() {
        super(RECORD_HEADER);
        this.RECORD_TOKEN_SET = TokenSet.create(JavaElementType.RECORD_COMPONENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (aSTNode.getElementType() == JavaElementType.RECORD_COMPONENT) {
            JavaSourceUtil.deleteSeparatingComma(this, aSTNode);
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        if (aSTNode2 == null) {
            if (bool == null || bool.booleanValue()) {
                aSTNode2 = findChildByType(JavaTokenType.RPARENTH);
                bool = Boolean.TRUE;
            } else {
                aSTNode2 = findChildByType(JavaTokenType.LPARENTH);
                bool = Boolean.FALSE;
            }
        }
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        if (treeElement == aSTNode && treeElement.getElementType() == JavaElementType.RECORD_COMPONENT) {
            JavaSourceUtil.addSeparatingComma(this, treeElement, this.RECORD_TOKEN_SET);
        }
        return treeElementAddInternal;
    }
}
