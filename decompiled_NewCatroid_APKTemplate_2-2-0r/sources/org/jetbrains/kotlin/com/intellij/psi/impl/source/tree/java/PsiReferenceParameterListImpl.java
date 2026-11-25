package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class PsiReferenceParameterListImpl extends CompositePsiElement implements PsiReferenceParameterList {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiReferenceParameterListImpl.class);
    private static final TokenSet TYPE_SET = TokenSet.create(JavaElementType.TYPE);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 2 || i == 3) {
            objArr[0] = "child";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiReferenceParameterListImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getTypeArguments";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiReferenceParameterListImpl";
        } else {
            objArr[1] = "getTypeParameterElements";
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

    public PsiReferenceParameterListImpl() {
        super(JavaElementType.REFERENCE_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList
    public PsiTypeElement[] getTypeParameterElements() {
        PsiTypeElement[] psiTypeElementArr = (PsiTypeElement[]) getChildrenAsPsiElements(JavaElementType.TYPE, PsiTypeElement.ARRAY_FACTORY);
        if (psiTypeElementArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiTypeElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList
    public PsiType[] getTypeArguments() {
        PsiType[] psiTypeArrTypesByReferenceParameterList = PsiImplUtil.typesByReferenceParameterList(this);
        if (psiTypeArrTypesByReferenceParameterList == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeArrTypesByReferenceParameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaElementType.TYPE) {
            return 120;
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
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 121) {
            if (getFirstChildNode() == null || getFirstChildNode().getElementType() != JavaTokenType.LT) {
                return null;
            }
            return getFirstChildNode();
        }
        if (i == 122 && getLastChildNode() != null && getLastChildNode().getElementType() == JavaTokenType.GT) {
            return getLastChildNode();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        if (treeElement == aSTNode && treeElement.getElementType() == JavaElementType.TYPE && getLastChildNode() != null && getLastChildNode().getElementType() == TokenType.ERROR_ELEMENT) {
            super.deleteChildInternal(getLastChildNode());
        }
        CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
        if (getFirstChildNode() == null || getFirstChildNode().getElementType() != JavaTokenType.LT) {
            LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.LT, "<", 0, 1, charTableFindCharTableByTree, getManager());
            super.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, getFirstChildNode(), Boolean.TRUE);
        }
        if (getLastChildNode() == null || getLastChildNode().getElementType() != JavaTokenType.GT) {
            LeafElement leafElementCreateSingleLeafElement2 = Factory.createSingleLeafElement(JavaTokenType.GT, ">", 0, 1, charTableFindCharTableByTree, getManager());
            super.addInternal(leafElementCreateSingleLeafElement2, leafElementCreateSingleLeafElement2, getLastChildNode(), Boolean.FALSE);
        }
        if (aSTNode2 == null) {
            if (bool == null || bool.booleanValue()) {
                aSTNode2 = findChildByRole(122);
                bool = Boolean.TRUE;
            } else {
                aSTNode2 = findChildByRole(121);
                bool = Boolean.FALSE;
            }
        }
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        if (treeElement == aSTNode && treeElement.getElementType() == JavaElementType.TYPE) {
            JavaSourceUtil.addSeparatingComma(this, treeElement, TYPE_SET);
        }
        return treeElementAddInternal;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (aSTNode.getElementType() == JavaElementType.TYPE) {
            JavaSourceUtil.deleteSeparatingComma(this, aSTNode);
        }
        super.deleteChildInternal(aSTNode);
        if (getTypeParameterElements().length == 0) {
            ASTNode aSTNodeFindChildByRole = findChildByRole(121);
            if (aSTNodeFindChildByRole != null) {
                deleteChildInternal(aSTNodeFindChildByRole);
            }
            ASTNode aSTNodeFindChildByRole2 = findChildByRole(122);
            if (aSTNodeFindChildByRole2 != null) {
                deleteChildInternal(aSTNodeFindChildByRole2);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceParameterList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiReferenceParameterList";
    }
}
