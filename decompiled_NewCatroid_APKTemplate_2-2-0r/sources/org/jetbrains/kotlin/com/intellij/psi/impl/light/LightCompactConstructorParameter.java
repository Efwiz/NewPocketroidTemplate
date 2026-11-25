package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.Objects;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: loaded from: classes3.dex */
public class LightCompactConstructorParameter extends LightParameter implements LightRecordMember {
    private final PsiRecordComponent myRecordComponent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i == 2) {
            objArr[0] = "declarationScope";
        } else if (i == 3) {
            objArr[0] = "component";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightCompactConstructorParameter";
        } else {
            objArr[0] = "name";
        }
        if (i == 4) {
            objArr[1] = "getRecordComponent";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightCompactConstructorParameter";
        } else {
            objArr[1] = "getNavigationElement";
        }
        if (i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightCompactConstructorParameter(String str, PsiType psiType, PsiElement psiElement, PsiRecordComponent psiRecordComponent) {
        super(str, psiType, psiElement);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (psiRecordComponent == null) {
            $$$reportNull$$$0(3);
        }
        this.myRecordComponent = psiRecordComponent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return getDeclarationScope();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return getDeclarationScope().getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordMember
    public PsiRecordComponent getRecordComponent() {
        PsiRecordComponent psiRecordComponent = this.myRecordComponent;
        if (psiRecordComponent == null) {
            $$$reportNull$$$0(4);
        }
        return psiRecordComponent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return this.myRecordComponent.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement navigationElement = this.myRecordComponent.getNavigationElement();
        if (navigationElement == null) {
            $$$reportNull$$$0(5);
        }
        return navigationElement;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LightCompactConstructorParameter) && this.myRecordComponent.equals(((LightCompactConstructorParameter) obj).myRecordComponent);
    }

    public int hashCode() {
        return Objects.hash(this.myRecordComponent);
    }
}
