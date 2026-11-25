package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AnonymousClassElementBase extends ClassElement {
    private static final Logger LOG = Logger.getInstance((Class<?>) AnonymousClassElement.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/AnonymousClassElementBase", "getChildRole"));
    }

    public AnonymousClassElementBase(IElementType iElementType) {
        super(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ClassElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 18) {
            return findChildByType(JavaTokenType.LBRACE);
        }
        if (i == 19) {
            return TreeUtil.findChildBackward(this, JavaTokenType.RBRACE);
        }
        if (i != 73) {
            if (i == 78 && getFirstChildNode().getElementType() == JavaElementType.JAVA_CODE_REFERENCE) {
                return getFirstChildNode();
            }
            return null;
        }
        return findChildByType(JavaElementType.EXPRESSION_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ClassElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaElementType.JAVA_CODE_REFERENCE) {
            return getChildRole(aSTNode, 78);
        }
        if (elementType == JavaElementType.EXPRESSION_LIST) {
            return 73;
        }
        if (elementType == JavaElementType.FIELD) {
            return 4;
        }
        if (elementType == JavaElementType.METHOD) {
            return 5;
        }
        if (elementType == JavaElementType.CLASS_INITIALIZER) {
            return 6;
        }
        if (elementType == JavaElementType.CLASS) {
            return 3;
        }
        if (elementType == JavaTokenType.LBRACE) {
            return getChildRole(aSTNode, 18);
        }
        if (elementType == JavaTokenType.RBRACE) {
            return getChildRole(aSTNode, 19);
        }
        return elementType == JavaTokenType.COMMA ? 23 : 0;
    }
}
