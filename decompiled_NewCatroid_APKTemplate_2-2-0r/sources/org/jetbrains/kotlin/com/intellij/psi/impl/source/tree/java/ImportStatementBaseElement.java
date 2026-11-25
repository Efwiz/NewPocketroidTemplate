package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class ImportStatementBaseElement extends CompositeElement {
    private static final Logger LOG = Logger.getInstance((Class<?>) ImportStatementBaseElement.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ImportStatementBaseElement", "getChildRole"));
    }

    protected ImportStatementBaseElement(IElementType iElementType) {
        super(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 22) {
            return TreeUtil.findChildBackward(this, JavaTokenType.SEMICOLON);
        }
        if (i == 98) {
            return getFirstChildNode();
        }
        if (i == 100) {
            return findChildByType(JavaTokenType.DOT);
        }
        if (i != 101) {
            return null;
        }
        return findChildByType(JavaTokenType.ASTERISK);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.IMPORT_KEYWORD) {
            return 98;
        }
        if (elementType == JavaElementType.JAVA_CODE_REFERENCE) {
            return 99;
        }
        if (elementType == JavaTokenType.DOT) {
            return 100;
        }
        if (elementType == JavaTokenType.ASTERISK) {
            return 101;
        }
        return elementType == JavaTokenType.SEMICOLON ? 22 : 0;
    }
}
