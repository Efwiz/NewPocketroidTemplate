package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;

/* JADX INFO: loaded from: classes3.dex */
public class LightTypeParameterBuilder extends LightPsiClassBuilder implements PsiTypeParameter {
    private final int myIndex;
    private final PsiTypeParameterListOwner myOwner;

    /* JADX WARN: Code duplicated, block: B:22:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 5) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameterBuilder";
        } else if (i == 3 || i == 4) {
            objArr[0] = "qualifiedName";
        } else if (i != 5) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameterBuilder";
        }
        if (i == 1) {
            objArr[1] = "getAnnotations";
        } else if (i == 2) {
            objArr[1] = "getApplicableAnnotations";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameterBuilder";
        } else {
            objArr[1] = "addAnnotation";
        }
        if (i != 1 && i != 2) {
            if (i == 3) {
                objArr[2] = "findAnnotation";
            } else if (i == 4) {
                objArr[2] = "addAnnotation";
            } else if (i != 5) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightTypeParameterBuilder(String str, PsiTypeParameterListOwner psiTypeParameterListOwner, int i) {
        super(psiTypeParameterListOwner, str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myOwner = psiTypeParameterListOwner;
        this.myIndex = i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter
    public PsiTypeParameterListOwner getOwner() {
        return this.myOwner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter
    public int getIndex() {
        return this.myIndex;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] annotations = mo6095getModifierList().getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] applicableAnnotations = mo6095getModifierList().getApplicableAnnotations();
        if (applicableAnnotations == null) {
            $$$reportNull$$$0(2);
        }
        return applicableAnnotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return mo6095getModifierList().findAnnotation(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        PsiAnnotation psiAnnotationAddAnnotation = mo6095getModifierList().addAnnotation(str);
        if (psiAnnotationAddAnnotation == null) {
            $$$reportNull$$$0(5);
        }
        return psiAnnotationAddAnnotation;
    }
}
