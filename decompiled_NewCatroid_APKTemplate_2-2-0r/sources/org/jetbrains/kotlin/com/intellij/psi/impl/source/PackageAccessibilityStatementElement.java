package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class PackageAccessibilityStatementElement extends CompositeElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "child";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PackageAccessibilityStatementElement";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "deleteChildInternal";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageAccessibilityStatementElement(IElementType iElementType) {
        super(iElementType);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        ASTNode aSTNode3;
        if (treeElement == aSTNode && treeElement.getElementType() == JavaElementType.MODULE_REFERENCE && aSTNode2 == null) {
            ASTNode lastChildNode = getLastChildNode();
            if (lastChildNode.getElementType() == JavaTokenType.SEMICOLON || lastChildNode.getElementType() == TokenType.ERROR_ELEMENT) {
                ASTNode treePrev = lastChildNode.getTreePrev();
                aSTNode3 = treePrev;
                lastChildNode = PsiImplUtil.skipWhitespaceAndCommentsBack(treePrev);
            } else {
                aSTNode3 = lastChildNode;
            }
            if (lastChildNode != null) {
                CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
                if (lastChildNode.getElementType() == JavaElementType.JAVA_CODE_REFERENCE) {
                    LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.TO_KEYWORD, "to", charTableFindCharTableByTree, getManager());
                    aSTNode2 = super.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, aSTNode3, Boolean.FALSE);
                    bool = Boolean.FALSE;
                } else if (lastChildNode.getElementType() == JavaElementType.MODULE_REFERENCE) {
                    LeafElement leafElementCreateSingleLeafElement2 = Factory.createSingleLeafElement(JavaTokenType.COMMA, ",", charTableFindCharTableByTree, getManager());
                    aSTNode2 = super.addInternal(leafElementCreateSingleLeafElement2, leafElementCreateSingleLeafElement2, aSTNode3, Boolean.FALSE);
                    bool = Boolean.FALSE;
                }
            }
        }
        return super.addInternal(treeElement, aSTNode, aSTNode2, bool);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (aSTNode.getElementType() == JavaElementType.MODULE_REFERENCE) {
            ASTNode aSTNodeFindNearestComma = findNearestComma(aSTNode);
            if (aSTNodeFindNearestComma != null) {
                super.deleteChildInternal(aSTNodeFindNearestComma);
            } else {
                ASTNode aSTNodeFindChildByType = findChildByType(JavaTokenType.TO_KEYWORD);
                if (aSTNodeFindChildByType != null) {
                    super.deleteChildInternal(aSTNodeFindChildByType);
                }
            }
        }
        super.deleteChildInternal(aSTNode);
    }

    private static ASTNode findNearestComma(ASTNode aSTNode) {
        ASTNode aSTNodeSkipWhitespaceAndComments = PsiImplUtil.skipWhitespaceAndComments(aSTNode.getTreeNext());
        if (aSTNodeSkipWhitespaceAndComments != null && aSTNodeSkipWhitespaceAndComments.getElementType() == JavaTokenType.COMMA) {
            return aSTNodeSkipWhitespaceAndComments;
        }
        ASTNode aSTNodeSkipWhitespaceAndCommentsBack = PsiImplUtil.skipWhitespaceAndCommentsBack(aSTNode.getTreePrev());
        if (aSTNodeSkipWhitespaceAndCommentsBack == null || aSTNodeSkipWhitespaceAndCommentsBack.getElementType() != JavaTokenType.COMMA) {
            return null;
        }
        return aSTNodeSkipWhitespaceAndCommentsBack;
    }
}
