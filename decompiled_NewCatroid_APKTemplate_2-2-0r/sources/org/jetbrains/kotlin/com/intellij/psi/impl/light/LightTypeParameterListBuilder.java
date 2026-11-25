package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;

/* JADX INFO: loaded from: classes3.dex */
public class LightTypeParameterListBuilder extends LightElement implements PsiTypeParameterList {
    private PsiTypeParameter[] cached;
    private final List<PsiTypeParameter> myParameters;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameterListBuilder";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameterListBuilder";
        } else {
            objArr[1] = "getTypeParameters";
        }
        if (i != 1) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public LightTypeParameterListBuilder(PsiManager psiManager, Language language) {
        super(psiManager, language);
        this.myParameters = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "Light type parameter list";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeParameterList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList
    public PsiTypeParameter[] getTypeParameters() {
        if (this.cached == null) {
            if (this.myParameters.isEmpty()) {
                this.cached = PsiTypeParameter.EMPTY_ARRAY;
            } else {
                this.cached = (PsiTypeParameter[]) this.myParameters.toArray(PsiTypeParameter.EMPTY_ARRAY);
            }
        }
        PsiTypeParameter[] psiTypeParameterArr = this.cached;
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList
    public int getTypeParameterIndex(PsiTypeParameter psiTypeParameter) {
        return this.myParameters.indexOf(psiTypeParameter);
    }

    public void addParameter(PsiTypeParameter psiTypeParameter) {
        this.cached = null;
        this.myParameters.add(psiTypeParameter);
    }
}
