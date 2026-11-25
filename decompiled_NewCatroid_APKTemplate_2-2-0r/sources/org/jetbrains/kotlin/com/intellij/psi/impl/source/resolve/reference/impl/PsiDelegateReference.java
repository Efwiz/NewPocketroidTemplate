package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl;

import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDelegateReference implements PsiReference {
    private final PsiReference myDelegate;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/PsiDelegateReference";
                break;
            case 4:
                objArr[0] = "newElementName";
                break;
            case 5:
            case 6:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
        }
        if (i == 1) {
            objArr[1] = "getElement";
        } else if (i == 2) {
            objArr[1] = "getRangeInElement";
        } else if (i == 3) {
            objArr[1] = "getCanonicalText";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/PsiDelegateReference";
        } else {
            objArr[1] = "getVariants";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 7:
                break;
            case 4:
                objArr[2] = "handleElementRename";
                break;
            case 5:
                objArr[2] = "bindToElement";
                break;
            case 6:
                objArr[2] = "isReferenceTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiDelegateReference(PsiReference psiReference) {
        if (psiReference == null) {
            $$$reportNull$$$0(0);
        }
        this.myDelegate = psiReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement getElement() {
        PsiElement element = this.myDelegate.getElement();
        if (element == null) {
            $$$reportNull$$$0(1);
        }
        return element;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        TextRange rangeInElement = this.myDelegate.getRangeInElement();
        if (rangeInElement == null) {
            $$$reportNull$$$0(2);
        }
        return rangeInElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        return this.myDelegate.resolve();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String canonicalText = this.myDelegate.getCanonicalText();
        if (canonicalText == null) {
            $$$reportNull$$$0(3);
        }
        return canonicalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return this.myDelegate.handleElementRename(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        return this.myDelegate.bindToElement(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        return this.myDelegate.isReferenceTo(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        Object[] variants = this.myDelegate.getVariants();
        if (variants == null) {
            $$$reportNull$$$0(7);
        }
        return variants;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return this.myDelegate.isSoft();
    }

    public static PsiReference createSoft(PsiReference psiReference, final boolean z) {
        return new PsiDelegateReference(psiReference) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.PsiDelegateReference.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.PsiDelegateReference, org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public boolean isSoft() {
                return z;
            }
        };
    }
}
