package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class PsiExpressionListImpl extends CompositePsiElement implements PsiExpressionList {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiExpressionListImpl.class);
    private volatile PsiExpression[] myExpressions;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 2 || i == 3) {
            objArr[0] = "child";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiExpressionListImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getExpressionTypes";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiExpressionListImpl";
        } else {
            objArr[1] = "getExpressions";
        }
        if (i == 2) {
            objArr[2] = "getChildRole";
        } else if (i == 3) {
            objArr[2] = "deleteChildInternal";
        } else if (i == 4) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiExpressionListImpl() {
        super(JavaElementType.EXPRESSION_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList
    public PsiExpression[] getExpressions() {
        PsiExpression[] psiExpressionArr = this.myExpressions;
        if (psiExpressionArr == null) {
            psiExpressionArr = (PsiExpression[]) getChildrenAsPsiElements(ElementType.EXPRESSION_BIT_SET, PsiExpression.ARRAY_FACTORY);
            if (psiExpressionArr.length > 10) {
                this.myExpressions = psiExpressionArr;
            }
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiExpressionArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        this.myExpressions = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList
    public int getExpressionCount() {
        PsiExpression[] psiExpressionArr = this.myExpressions;
        if (psiExpressionArr != null) {
            return psiExpressionArr.length;
        }
        return countChildren(ElementType.EXPRESSION_BIT_SET);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList
    public boolean isEmpty() {
        return findChildByType(ElementType.EXPRESSION_BIT_SET) == null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList
    public PsiType[] getExpressionTypes() {
        PsiExpression[] expressions = getExpressions();
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(expressions.length);
        for (int i = 0; i < psiTypeArrCreateArray.length; i++) {
            psiTypeArrCreateArray[i] = expressions[i].getType();
        }
        if (psiTypeArrCreateArray == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeArrCreateArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 24) {
            if (getFirstChildNode() == null || getFirstChildNode().getElementType() != JavaTokenType.LPARENTH) {
                return null;
            }
            return getFirstChildNode();
        }
        if (i == 25 && getLastChildNode() != null && getLastChildNode().getElementType() == JavaTokenType.RPARENTH) {
            return getLastChildNode();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.COMMA) {
            return 23;
        }
        if (elementType == JavaTokenType.LPARENTH) {
            return 24;
        }
        if (elementType == JavaTokenType.RPARENTH) {
            return 25;
        }
        return ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 59 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
        if (aSTNode2 == null) {
            if (bool == null || bool.booleanValue()) {
                ASTNode aSTNodeFindChildByRole = findChildByRole(25);
                if (aSTNodeFindChildByRole == null) {
                    LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.LPARENTH, "(", 0, 1, charTableFindCharTableByTree, getManager());
                    super.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, null, Boolean.FALSE);
                    LeafElement leafElementCreateSingleLeafElement2 = Factory.createSingleLeafElement(JavaTokenType.RPARENTH, Constant.AFTER_QUTO, 0, 1, charTableFindCharTableByTree, getManager());
                    super.addInternal(leafElementCreateSingleLeafElement2, leafElementCreateSingleLeafElement2, null, Boolean.TRUE);
                    ASTNode aSTNodeFindChildByRole2 = findChildByRole(25);
                    LOG.assertTrue(aSTNodeFindChildByRole2 != null);
                    aSTNode2 = aSTNodeFindChildByRole2;
                } else {
                    aSTNode2 = aSTNodeFindChildByRole;
                }
                bool = Boolean.TRUE;
            } else {
                ASTNode aSTNodeFindChildByRole3 = findChildByRole(24);
                if (aSTNodeFindChildByRole3 == null) {
                    LeafElement leafElementCreateSingleLeafElement3 = Factory.createSingleLeafElement(JavaTokenType.LPARENTH, "(", 0, 1, charTableFindCharTableByTree, getManager());
                    super.addInternal(leafElementCreateSingleLeafElement3, leafElementCreateSingleLeafElement3, null, Boolean.FALSE);
                    LeafElement leafElementCreateSingleLeafElement4 = Factory.createSingleLeafElement(JavaTokenType.RPARENTH, Constant.AFTER_QUTO, 0, 1, charTableFindCharTableByTree, getManager());
                    super.addInternal(leafElementCreateSingleLeafElement4, leafElementCreateSingleLeafElement4, null, Boolean.TRUE);
                    ASTNode aSTNodeFindChildByRole4 = findChildByRole(24);
                    LOG.assertTrue(aSTNodeFindChildByRole4 != null);
                    aSTNode2 = aSTNodeFindChildByRole4;
                } else {
                    aSTNode2 = aSTNodeFindChildByRole3;
                }
                bool = Boolean.FALSE;
            }
        }
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        while (!ElementType.EXPRESSION_BIT_SET.contains(treeElement.getElementType())) {
            if (treeElement == aSTNode) {
                return treeElementAddInternal;
            }
            treeElement = treeElement.getTreeNext();
        }
        JavaSourceUtil.addSeparatingComma(this, treeElement, ElementType.EXPRESSION_BIT_SET);
        return treeElementAddInternal;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            JavaSourceUtil.deleteSeparatingComma(this, aSTNode);
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitExpressionList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiExpressionList";
    }
}
