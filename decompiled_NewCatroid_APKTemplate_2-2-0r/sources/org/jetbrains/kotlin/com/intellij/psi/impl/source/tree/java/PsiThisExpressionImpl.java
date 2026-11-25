package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiThisExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImmediateClassType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiThisExpressionImpl extends ExpressionPsiElement implements PsiThisExpression, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiThisExpressionImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "child";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiThisExpressionImpl";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiThisExpressionImpl() {
        super(THIS_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiThisExpression, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedExpression
    public PsiJavaCodeReferenceElement getQualifier() {
        return (PsiJavaCodeReferenceElement) findChildByRoleAsPsiElement(54);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiType thisType;
        PsiJavaCodeReferenceElement qualifier = getQualifier();
        if (qualifier != null) {
            PsiElement psiElementResolve = qualifier.resolve();
            return psiElementResolve instanceof PsiClass ? new PsiImmediateClassType((PsiClass) psiElementResolve, PsiSubstitutor.EMPTY) : new PsiClassReferenceType(qualifier, null);
        }
        PsiElement context = getContext();
        while (context != null) {
            if (context instanceof PsiClass) {
                return new PsiImmediateClassType((PsiClass) context, PsiSubstitutor.EMPTY);
            }
            if ((context instanceof PsiExpressionList) && (context.getLightParent() instanceof PsiAnonymousClass)) {
                context = context.getLightParent();
            } else if ((context instanceof JavaCodeFragment) && (thisType = ((JavaCodeFragment) context).getThisType()) != null) {
                return thisType;
            }
            context = context.getContext();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 54) {
            if (getFirstChildNode().getElementType() == JAVA_CODE_REFERENCE) {
                return getFirstChildNode();
            }
            return null;
        }
        if (i == 55) {
            return findChildByType(DOT);
        }
        if (i != 95) {
            return null;
        }
        return getLastChildNode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JAVA_CODE_REFERENCE) {
            return 54;
        }
        if (elementType == DOT) {
            return 55;
        }
        return elementType == THIS_KEYWORD ? 95 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitThisExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiThisExpression:" + getText();
    }
}
