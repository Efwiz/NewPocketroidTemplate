package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReceiverParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiThisExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiReceiverParameterImpl extends CompositePsiElement implements PsiReceiverParameter {
    /* JADX WARN: Code duplicated, block: B:29:0x0049  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 4 || i == 5) ? 3 : 2];
        if (i == 1 || i == 4) {
            objArr[0] = "name";
        } else if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiReceiverParameterImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiReceiverParameterImpl";
        } else if (i == 2) {
            objArr[1] = "getType";
        } else if (i == 3) {
            objArr[1] = "getTypeElement";
        } else if (i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiReceiverParameterImpl";
        } else {
            objArr[1] = "getIdentifier";
        }
        if (i == 1) {
            objArr[2] = "hasModifierProperty";
        } else if (i == 4) {
            objArr[2] = "setName";
        } else if (i == 5) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
    }

    public PsiReceiverParameterImpl() {
        super(JavaElementType.RECEIVER_PARAMETER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReceiverParameter
    public PsiThisExpression getIdentifier() {
        PsiThisExpression psiThisExpression = (PsiThisExpression) PsiTreeUtil.getRequiredChildOfType(this, PsiThisExpression.class);
        if (psiThisExpression == null) {
            $$$reportNull$$$0(0);
        }
        return psiThisExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return (PsiModifierList) PsiTreeUtil.getChildOfType(this, PsiModifierList.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        PsiModifierList psiModifierListMo6095getModifierList = mo6095getModifierList();
        return psiModifierListMo6095getModifierList != null && psiModifierListMo6095getModifierList.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiType getType() {
        PsiType type = JavaSharedImplUtil.getType(getTypeElement(), getIdentifier());
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        PsiTypeElement psiTypeElement = (PsiTypeElement) PsiTreeUtil.getRequiredChildOfType(this, PsiTypeElement.class);
        if (psiTypeElement == null) {
            $$$reportNull$$$0(3);
        }
        return psiTypeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        throw new IncorrectOperationException("Cannot rename receiver parameter");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReceiverParameter(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return getIdentifier().getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiReceiverParameter";
    }
}
