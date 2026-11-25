package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiPackageStatementImpl extends CompositePsiElement implements PsiPackageStatement {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiPackageStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "child";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiPackageStatementImpl";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiPackageStatementImpl() {
        super(JavaElementType.PACKAGE_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement
    public PsiJavaCodeReferenceElement getPackageReference() {
        return (PsiJavaCodeReferenceElement) findChildByRoleAsPsiElement(103);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement
    public String getPackageName() {
        PsiJavaCodeReferenceElement packageReference = getPackageReference();
        if (packageReference == null) {
            return null;
        }
        return JavaSourceUtil.getReferenceText(packageReference);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement
    public PsiModifierList getAnnotationList() {
        return (PsiModifierList) findChildByRoleAsPsiElement(8);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 8) {
            return findChildByType(JavaElementType.MODIFIER_LIST);
        }
        if (i == 22) {
            return TreeUtil.findChildBackward(this, JavaTokenType.SEMICOLON);
        }
        if (i == 102) {
            return findChildByType(JavaTokenType.PACKAGE_KEYWORD);
        }
        if (i != 103) {
            return null;
        }
        return findChildByType(JavaElementType.JAVA_CODE_REFERENCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.PACKAGE_KEYWORD) {
            return 102;
        }
        if (elementType == JavaElementType.JAVA_CODE_REFERENCE) {
            return 103;
        }
        if (elementType == JavaTokenType.SEMICOLON) {
            return 22;
        }
        return elementType == JavaElementType.MODIFIER_LIST ? 8 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitPackageStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPackageStatement:" + getPackageName();
    }
}
