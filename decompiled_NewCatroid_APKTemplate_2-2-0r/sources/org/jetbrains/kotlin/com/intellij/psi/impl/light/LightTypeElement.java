package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LightTypeElement extends LightElement implements PsiTypeElement {
    private final PsiType myType;

    /* JADX WARN: Code duplicated, block: B:22:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 5) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeElement";
        } else if (i == 3 || i == 4) {
            objArr[0] = "qualifiedName";
        } else if (i != 5) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeElement";
        }
        if (i == 1) {
            objArr[1] = "getType";
        } else if (i == 2) {
            objArr[1] = "getAnnotations";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeElement";
        } else {
            objArr[1] = "getApplicableAnnotations";
        }
        if (i != 1 && i != 2) {
            if (i == 3) {
                objArr[2] = "findAnnotation";
            } else if (i == 4) {
                objArr[2] = "addAnnotation";
            } else if (i != 5) {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement
    public PsiJavaCodeReferenceElement getInnermostComponentReferenceElement() {
        return null;
    }

    public LightTypeElement(PsiManager psiManager, PsiType psiType) {
        super(psiManager, JavaLanguage.INSTANCE);
        this.myType = PsiUtil.convertAnonymousToBaseType(psiType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeElement:" + getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myType.getPresentableText(true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new LightTypeElement(this.myManager, this.myType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement
    public PsiType getType() {
        PsiType psiType = this.myType;
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        return psiType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return this.myType.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] annotations = this.myType.getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(2);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return this.myType.findAnnotation(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] annotations = getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(5);
        }
        return annotations;
    }
}
