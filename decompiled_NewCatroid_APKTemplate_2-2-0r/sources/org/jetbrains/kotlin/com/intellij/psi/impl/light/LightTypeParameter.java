package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;

/* JADX INFO: loaded from: classes3.dex */
public class LightTypeParameter extends LightClass implements PsiTypeParameter {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 4 || i == 5) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 4 || i == 5) {
            objArr[0] = "qualifiedName";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameter";
        }
        switch (i) {
            case 1:
            case 4:
            case 5:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameter";
                break;
            case 2:
                objArr[1] = "getAnnotations";
                break;
            case 3:
                objArr[1] = "getApplicableAnnotations";
                break;
            case 6:
                objArr[1] = "addAnnotation";
                break;
            default:
                objArr[1] = "getDelegate";
                break;
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i == 4) {
            objArr[2] = "findAnnotation";
        } else if (i == 5) {
            objArr[2] = "addAnnotation";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public boolean useDelegateToSubstitute() {
        return true;
    }

    public LightTypeParameter(PsiTypeParameter psiTypeParameter) {
        super(psiTypeParameter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass
    /* JADX INFO: renamed from: getDelegate */
    public PsiTypeParameter mo6090getDelegate() {
        PsiTypeParameter psiTypeParameter = (PsiTypeParameter) super.mo6090getDelegate();
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(0);
        }
        return psiTypeParameter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new LightTypeParameter(mo6090getDelegate());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeParameter(this);
        } else {
            super.accept(psiElementVisitor);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter
    public PsiTypeParameterListOwner getOwner() {
        return mo6090getDelegate().getOwner();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter
    public int getIndex() {
        return mo6090getDelegate().getIndex();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] annotations = mo6090getDelegate().getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(2);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] applicableAnnotations = mo6090getDelegate().getApplicableAnnotations();
        if (applicableAnnotations == null) {
            $$$reportNull$$$0(3);
        }
        return applicableAnnotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return mo6090getDelegate().findAnnotation(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        PsiAnnotation psiAnnotationAddAnnotation = mo6090getDelegate().addAnnotation(str);
        if (psiAnnotationAddAnnotation == null) {
            $$$reportNull$$$0(6);
        }
        return psiAnnotationAddAnnotation;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeParameter:" + getName();
    }
}
