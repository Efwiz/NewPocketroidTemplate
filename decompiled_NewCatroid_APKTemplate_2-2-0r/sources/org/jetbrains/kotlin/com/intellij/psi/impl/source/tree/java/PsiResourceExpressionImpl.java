package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiResourceExpressionImpl extends CompositePsiElement implements PsiResourceExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiResourceExpressionImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 1) {
            objArr[1] = "getExpression";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiResourceExpressionImpl";
        }
        if (i == 1) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    public PsiResourceExpressionImpl() {
        super(JavaElementType.RESOURCE_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResourceExpression
    public PsiExpression getExpression() {
        PsiExpression psiExpression = (PsiExpression) getFirstChild();
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResourceListElement
    public PsiType getType() {
        return getExpression().getType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitResourceExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiResourceExpression";
    }
}
