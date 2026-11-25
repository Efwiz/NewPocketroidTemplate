package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public class PsiArrayInitializerMemberValueImpl extends CompositePsiElement implements PsiArrayInitializerMemberValue {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiArrayInitializerMemberValueImpl.class);
    private static final TokenSet MEMBER_SET = ElementType.ANNOTATION_MEMBER_VALUE_BIT_SET;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "child";
        } else if (i != 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiArrayInitializerMemberValueImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1 || i == 2 || i == 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiArrayInitializerMemberValueImpl";
        } else {
            objArr[1] = "getInitializers";
        }
        if (i == 1) {
            objArr[2] = "getChildRole";
        } else if (i == 2) {
            objArr[2] = "deleteChildInternal";
        } else if (i == 3) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiArrayInitializerMemberValueImpl() {
        super(JavaElementType.ANNOTATION_ARRAY_INITIALIZER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue
    public PsiAnnotationMemberValue[] getInitializers() {
        PsiAnnotationMemberValue[] psiAnnotationMemberValueArr = (PsiAnnotationMemberValue[]) getChildrenAsPsiElements(MEMBER_SET, PsiAnnotationMemberValue.ARRAY_FACTORY);
        if (psiAnnotationMemberValueArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiAnnotationMemberValueArr;
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
        return MEMBER_SET.contains(aSTNode.getElementType()) ? 249 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        TokenSet tokenSet = MEMBER_SET;
        if (tokenSet.contains(treeElement.getElementType()) && tokenSet.contains(aSTNode.getElementType())) {
            TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
            JavaSourceUtil.addSeparatingComma(this, treeElement, tokenSet);
            return treeElementAddInternal;
        }
        return super.addInternal(treeElement, aSTNode, aSTNode2, bool);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (MEMBER_SET.contains(aSTNode.getElementType())) {
            JavaSourceUtil.deleteSeparatingComma(this, aSTNode);
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAnnotationArrayInitializer(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiArrayInitializerMemberValue:" + getText();
    }
}
