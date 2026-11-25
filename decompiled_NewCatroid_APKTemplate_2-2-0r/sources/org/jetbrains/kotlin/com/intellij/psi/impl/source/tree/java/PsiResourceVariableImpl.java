package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiResourceVariableImpl extends PsiLocalVariableImpl implements PsiResourceVariable {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 2 ? 2 : 3];
        if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiResourceVariableImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getTypeElement";
        } else if (i != 2) {
            objArr[1] = "getDeclarationScope";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiResourceVariableImpl";
        }
        if (i == 2) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalArgumentException(str2);
        }
    }

    public PsiResourceVariableImpl() {
        super(JavaElementType.RESOURCE_VARIABLE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResourceVariable
    public PsiElement[] getDeclarationScope() {
        PsiResourceList psiResourceList = (PsiResourceList) getLightParent();
        PsiCodeBlock tryBlock = ((PsiTryStatement) psiResourceList.getLightParent()).getTryBlock();
        return tryBlock != null ? new PsiElement[]{psiResourceList, tryBlock} : new PsiElement[]{psiResourceList};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLocalVariableImpl, org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        PsiTypeElement psiTypeElement = (PsiTypeElement) PsiTreeUtil.getRequiredChildOfType(this, PsiTypeElement.class);
        if (psiTypeElement == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLocalVariableImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return (PsiModifierList) PsiTreeUtil.getChildOfType(this, PsiModifierList.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        PsiElement psiElementSkipWhitespacesAndCommentsForward = PsiTreeUtil.skipWhitespacesAndCommentsForward(this);
        if (PsiUtil.isJavaToken(psiElementSkipWhitespacesAndCommentsForward, JavaTokenType.SEMICOLON)) {
            getLightParent().deleteChildRange(this, psiElementSkipWhitespacesAndCommentsForward);
            return;
        }
        PsiElement psiElementSkipWhitespacesAndCommentsBackward = PsiTreeUtil.skipWhitespacesAndCommentsBackward(this);
        if (PsiUtil.isJavaToken(psiElementSkipWhitespacesAndCommentsBackward, JavaTokenType.SEMICOLON)) {
            getLightParent().deleteChildRange(psiElementSkipWhitespacesAndCommentsBackward, this);
        } else {
            super.delete();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLocalVariableImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitResourceVariable(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLocalVariableImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        return new LocalSearchScope(getDeclarationScope());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLocalVariableImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiResourceVariable:" + getName();
    }
}
