package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class PsiArrayInitializerExpressionImpl extends ExpressionPsiElement implements PsiArrayInitializerExpression {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiArrayInitializerExpressionImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "child";
        } else if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiArrayInitializerExpressionImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiArrayInitializerExpressionImpl";
        } else {
            objArr[1] = "getInitializers";
        }
        if (i == 1) {
            objArr[2] = "getChildRole";
        } else if (i == 2) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiArrayInitializerExpressionImpl() {
        super(JavaElementType.ARRAY_INITIALIZER_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerExpression
    public PsiExpression[] getInitializers() {
        PsiExpression[] psiExpressionArr = (PsiExpression[]) getChildrenAsPsiElements(ElementType.EXPRESSION_BIT_SET, PsiExpression.ARRAY_FACTORY);
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiExpressionArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        if (getTreeParent() instanceof PsiNewExpression) {
            if (getTreeParent().getChildRole(this) == 77) {
                return ((PsiNewExpression) getTreeParent()).getType();
            }
        } else {
            if (getTreeParent() instanceof PsiVariable) {
                return ((PsiVariable) getTreeParent()).getType();
            }
            if (getTreeParent() instanceof PsiArrayInitializerExpression) {
                PsiType type = ((PsiArrayInitializerExpression) getTreeParent()).getType();
                if (!(type instanceof PsiArrayType)) {
                    return null;
                }
                PsiType componentType = ((PsiArrayType) type).getComponentType();
                if (componentType instanceof PsiArrayType) {
                    return componentType;
                }
                return null;
            }
            if (getTreeParent() instanceof FieldElement) {
                return ((PsiField) getLightParent()).getType();
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 18) {
            return findChildByType(JavaTokenType.LBRACE);
        }
        if (i != 19) {
            return null;
        }
        return findChildByType(JavaTokenType.RBRACE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.COMMA) {
            return 23;
        }
        if (elementType == JavaTokenType.LBRACE) {
            return 18;
        }
        if (elementType == JavaTokenType.RBRACE) {
            return 19;
        }
        return ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 59 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitArrayInitializerExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiArrayInitializerExpression:" + getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        if (aSTNode2 == null) {
            if (bool == null || bool.booleanValue()) {
                aSTNode2 = findChildByRole(19);
                bool = Boolean.TRUE;
            } else {
                aSTNode2 = findChildByRole(18);
                bool = Boolean.FALSE;
            }
        }
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        if (ElementType.EXPRESSION_BIT_SET.contains(treeElement.getElementType())) {
            CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
            for (ASTNode treeNext = treeElement.getTreeNext(); treeNext != null && treeNext.getElementType() != JavaTokenType.COMMA; treeNext = treeNext.getTreeNext()) {
                if (ElementType.EXPRESSION_BIT_SET.contains(treeNext.getElementType())) {
                    LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.COMMA, ",", 0, 1, charTableFindCharTableByTree, getManager());
                    super.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, treeElement, Boolean.FALSE);
                    break;
                }
            }
            for (ASTNode treePrev = treeElement.getTreePrev(); treePrev != null && treePrev.getElementType() != JavaTokenType.COMMA; treePrev = treePrev.getTreePrev()) {
                if (ElementType.EXPRESSION_BIT_SET.contains(treePrev.getElementType())) {
                    LeafElement leafElementCreateSingleLeafElement2 = Factory.createSingleLeafElement(JavaTokenType.COMMA, ",", 0, 1, charTableFindCharTableByTree, getManager());
                    super.addInternal(leafElementCreateSingleLeafElement2, leafElementCreateSingleLeafElement2, treePrev, Boolean.FALSE);
                    break;
                }
            }
        }
        return treeElementAddInternal;
    }
}
