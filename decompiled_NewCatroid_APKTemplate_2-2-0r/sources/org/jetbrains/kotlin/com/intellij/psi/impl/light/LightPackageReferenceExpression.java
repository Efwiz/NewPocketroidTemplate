package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LightPackageReferenceExpression extends LightPackageReference implements PsiReferenceExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "aClass";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightPackageReferenceExpression";
        if (i != 1) {
            objArr[2] = "bindToElementViaStaticImport";
        } else {
            objArr[2] = "isReferenceTo";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public PsiExpression getQualifierExpression() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return null;
    }

    public LightPackageReferenceExpression(PsiManager psiManager, PsiPackage psiPackage) {
        super(psiManager, psiPackage);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public PsiElement bindToElementViaStaticImport(PsiClass psiClass) throws IncorrectOperationException {
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public void setQualifierExpression(PsiExpression psiExpression) throws IncorrectOperationException {
        throw new IncorrectOperationException("This method should not be called for light elements");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightPackageReference, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return getManager().areElementsEquivalent(resolve(), psiElement);
    }
}
