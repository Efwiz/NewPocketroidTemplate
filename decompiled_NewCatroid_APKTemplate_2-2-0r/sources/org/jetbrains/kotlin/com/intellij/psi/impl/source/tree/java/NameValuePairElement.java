package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import com.huawei.hms.framework.common.ContainerUtils;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class NameValuePairElement extends CompositeElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "child";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/NameValuePairElement";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "deleteChildInternal";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public NameValuePairElement() {
        super(JavaElementType.NAME_VALUE_PAIR);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (ElementType.ANNOTATION_MEMBER_VALUE_BIT_SET.contains(aSTNode.getElementType())) {
            return 249;
        }
        if (aSTNode.getElementType() == JavaTokenType.IDENTIFIER) {
            return 9;
        }
        return aSTNode.getElementType() == JavaTokenType.EQ ? 63 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        if (i == 9) {
            return findChildByType(JavaTokenType.IDENTIFIER);
        }
        if (i == 249) {
            return findChildByType(ElementType.ANNOTATION_MEMBER_VALUE_BIT_SET);
        }
        if (i == 63) {
            return findChildByType(JavaTokenType.EQ);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        if (treeElement == aSTNode && treeElement.getElementType() == JavaTokenType.IDENTIFIER) {
            LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.EQ, ContainerUtils.KEY_VALUE_DELIMITER, 0, 1, charTableFindCharTableByTree, getManager());
            super.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, treeElement, Boolean.FALSE);
        }
        return treeElementAddInternal;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        ASTNode aSTNodeFindChildByRole;
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        super.deleteChildInternal(aSTNode);
        if (aSTNode.getElementType() != JavaTokenType.IDENTIFIER || (aSTNodeFindChildByRole = findChildByRole(63)) == null) {
            return;
        }
        super.deleteChildInternal(aSTNodeFindChildByRole);
    }
}
