package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.StringJoiner;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;

/* JADX INFO: loaded from: classes3.dex */
public class LightReferenceParameterList extends LightElement implements PsiReferenceParameterList {
    private final String myText;
    private final PsiTypeElement[] myTypeElements;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightReferenceParameterList";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getTypeParameterElements";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightReferenceParameterList";
        } else {
            objArr[1] = "getTypeArguments";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public LightReferenceParameterList(PsiManager psiManager, PsiTypeElement[] psiTypeElementArr) {
        super(psiManager, JavaLanguage.INSTANCE);
        this.myTypeElements = psiTypeElementArr;
        this.myText = calculateText();
    }

    private String calculateText() {
        if (this.myTypeElements.length == 0) {
            return "";
        }
        StringJoiner stringJoiner = new StringJoiner(",", "<", ">");
        for (PsiTypeElement psiTypeElement : this.myTypeElements) {
            stringJoiner.add(psiTypeElement.getText());
        }
        return stringJoiner.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiReferenceParameterList";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceParameterList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        PsiTypeElement[] psiTypeElementArr = new PsiTypeElement[this.myTypeElements.length];
        int i = 0;
        while (true) {
            PsiTypeElement[] psiTypeElementArr2 = this.myTypeElements;
            if (i < psiTypeElementArr2.length) {
                psiTypeElementArr[i] = (PsiTypeElement) psiTypeElementArr2[i].copy();
                i++;
            } else {
                return new LightReferenceParameterList(this.myManager, psiTypeElementArr);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList
    public PsiTypeElement[] getTypeParameterElements() {
        PsiTypeElement[] psiTypeElementArr = this.myTypeElements;
        if (psiTypeElementArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList
    public PsiType[] getTypeArguments() {
        PsiType[] psiTypeArrTypesByTypeElements = PsiImplUtil.typesByTypeElements(this.myTypeElements);
        if (psiTypeArrTypesByTypeElements == null) {
            $$$reportNull$$$0(2);
        }
        return psiTypeArrTypesByTypeElements;
    }
}
