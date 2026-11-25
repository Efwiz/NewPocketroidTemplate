package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class MethodElement extends CompositeElement implements Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) MethodElement.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "child";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/MethodElement";
        if (i != 1) {
            objArr[2] = "deleteChildInternal";
        } else {
            objArr[2] = "getChildRole";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    public MethodElement() {
        super(METHOD);
    }

    protected MethodElement(IElementType iElementType) {
        super(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        ASTNode aSTNodeFindChildByType = findChildByType(IDENTIFIER);
        return aSTNodeFindChildByType != null ? aSTNodeFindChildByType.getStartOffset() : getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        ASTNode aSTNodeFindChildBackward;
        if (treeElement == aSTNode && treeElement.getElementType() == JavaElementType.CODE_BLOCK && (aSTNodeFindChildBackward = TreeUtil.findChildBackward(this, SEMICOLON)) != null) {
            deleteChildInternal(aSTNodeFindChildBackward);
        }
        return super.addInternal(treeElement, aSTNode, aSTNode2, bool);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (aSTNode.getElementType() == CODE_BLOCK) {
            ASTNode aSTNodePrevLeaf = TreeUtil.prevLeaf(aSTNode);
            if (aSTNodePrevLeaf != null && aSTNodePrevLeaf.getElementType() == TokenType.WHITE_SPACE) {
                removeChild(aSTNodePrevLeaf);
            }
            super.deleteChildInternal(aSTNode);
            LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(SEMICOLON, ";", 0, 1, SharedImplUtil.findCharTableByTree(this), getManager());
            addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, null, Boolean.TRUE);
            return;
        }
        if (aSTNode.getElementType() == PARAMETER_LIST) {
            throw new IllegalArgumentException("Deleting parameter list is prohibited");
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 14) {
            return findChildByType(PARAMETER_LIST);
        }
        if (i == 22) {
            return TreeUtil.findChildBackward(this, SEMICOLON);
        }
        if (i == 85) {
            return findChildByType(DEFAULT_KEYWORD);
        }
        if (i == 245) {
            return findChildByType(TYPE_PARAMETER_LIST);
        }
        if (i == 16) {
            return findChildByType(THROWS_LIST);
        }
        if (i != 17) {
            switch (i) {
                case 7:
                    return PsiImplUtil.findDocComment(this);
                case 8:
                    return findChildByType(MODIFIER_LIST);
                case 9:
                    return findChildByType(IDENTIFIER);
                case 10:
                    return findChildByType(TYPE);
                default:
                    return null;
            }
        }
        return findChildByType(CODE_BLOCK);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaDocElementType.DOC_COMMENT) {
            return getChildRole(aSTNode, 7);
        }
        if (elementType == MODIFIER_LIST) {
            return 8;
        }
        if (elementType == TYPE_PARAMETER_LIST) {
            return 245;
        }
        if (elementType == CODE_BLOCK) {
            return 17;
        }
        if (elementType == PARAMETER_LIST) {
            return 14;
        }
        if (elementType == THROWS_LIST) {
            return 16;
        }
        if (elementType == TYPE) {
            return getChildRole(aSTNode, 10);
        }
        if (elementType == IDENTIFIER) {
            return getChildRole(aSTNode, 9);
        }
        if (elementType == SEMICOLON) {
            return getChildRole(aSTNode, 22);
        }
        return elementType == DEFAULT_KEYWORD ? 85 : 0;
    }
}
