package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class TypeParameterListElement extends CompositeElement {
    private static final Logger LOG = Logger.getInstance((Class<?>) TypeParameterListElement.class);
    private static final TokenSet TYPE_PARAMETER_SET = TokenSet.create(JavaElementType.TYPE_PARAMETER);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "child";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/TypeParameterListElement";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "deleteChildInternal";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public TypeParameterListElement() {
        super(JavaElementType.TYPE_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaElementType.TYPE_PARAMETER) {
            return 244;
        }
        if (elementType == JavaTokenType.COMMA) {
            return 23;
        }
        if (elementType == JavaTokenType.LT) {
            return 121;
        }
        return elementType == JavaTokenType.GT ? 122 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        TreeElement treeElementCreateSingleLeafElement = (TreeElement) findChildByRole(121);
        CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
        if (treeElementCreateSingleLeafElement == null) {
            treeElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.LT, "<", 0, 1, charTableFindCharTableByTree, getManager());
            super.addInternal(treeElementCreateSingleLeafElement, treeElementCreateSingleLeafElement, getFirstChildNode(), Boolean.TRUE);
        }
        TreeElement treeElementCreateSingleLeafElement2 = (TreeElement) findChildByRole(122);
        if (treeElementCreateSingleLeafElement2 == null) {
            treeElementCreateSingleLeafElement2 = Factory.createSingleLeafElement(JavaTokenType.GT, ">", 0, 1, charTableFindCharTableByTree, getManager());
            super.addInternal(treeElementCreateSingleLeafElement2, treeElementCreateSingleLeafElement2, getLastChildNode(), Boolean.FALSE);
        }
        if (aSTNode2 == null) {
            if (bool == null || bool.booleanValue()) {
                bool = Boolean.TRUE;
                aSTNode2 = treeElementCreateSingleLeafElement2;
            } else {
                bool = Boolean.FALSE;
                aSTNode2 = treeElementCreateSingleLeafElement;
            }
        }
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        if (treeElement == aSTNode && treeElement.getElementType() == JavaElementType.TYPE_PARAMETER) {
            JavaSourceUtil.addSeparatingComma(this, treeElement, TYPE_PARAMETER_SET);
        }
        return treeElementAddInternal;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (aSTNode.getElementType() == JavaElementType.TYPE_PARAMETER) {
            JavaSourceUtil.deleteSeparatingComma(this, aSTNode);
        }
        super.deleteChildInternal(aSTNode);
        if (aSTNode.getElementType() == JavaElementType.TYPE_PARAMETER) {
            ASTNode aSTNodeFindChildByRole = findChildByRole(121);
            ASTNode aSTNodeSkipWhitespaceAndComments = PsiImplUtil.skipWhitespaceAndComments(aSTNodeFindChildByRole.getTreeNext());
            if (aSTNodeSkipWhitespaceAndComments == null || aSTNodeSkipWhitespaceAndComments.getElementType() != JavaTokenType.GT) {
                return;
            }
            deleteChildInternal(aSTNodeFindChildByRole);
            deleteChildInternal(aSTNodeSkipWhitespaceAndComments);
        }
    }
}
