package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
abstract class LightClassReferenceBase extends LightElement implements PsiJavaCodeReferenceElement {
    private LightReferenceParameterList myParameterList;
    protected final String myText;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5 || i == 6 || i == 11 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5 || i == 6 || i == 11 || i == 12) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "text";
                break;
            case 2:
            case 3:
            case 5:
            case 6:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightClassReferenceBase";
                break;
            case 4:
                objArr[0] = "processor";
                break;
            case 7:
                objArr[0] = "newElementName";
                break;
            case 8:
            case 10:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 9:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "multiResolve";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getCanonicalText";
        } else if (i == 11) {
            objArr[1] = "getElement";
        } else if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightClassReferenceBase";
        } else {
            objArr[1] = "getTypeParameters";
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 11:
            case 12:
                break;
            case 4:
                objArr[2] = "processVariants";
                break;
            case 7:
                objArr[2] = "handleElementRename";
                break;
            case 8:
                objArr[2] = "bindToElement";
                break;
            case 9:
                objArr[2] = "accept";
                break;
            case 10:
                objArr[2] = "isReferenceTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5 && i != 6 && i != 11 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        return this;
    }

    public PsiElement getQualifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return this;
    }

    public PsiElement getReferenceNameElement() {
        return null;
    }

    public boolean isQualified() {
        return false;
    }

    public boolean isSoft() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected LightClassReferenceBase(PsiManager psiManager, String str) {
        super(psiManager, JavaLanguage.INSTANCE);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myText = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        JavaResolveResult javaResolveResultAdvancedResolve = advancedResolve(z);
        if (javaResolveResultAdvancedResolve != JavaResolveResult.EMPTY) {
            return new JavaResolveResult[]{javaResolveResultAdvancedResolve};
        }
        JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(3);
        }
        return javaResolveResultArr;
    }

    public void processVariants(PsiScopeProcessor psiScopeProcessor) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(4);
        }
        throw new RuntimeException("Variants are not available for light references");
    }

    public PsiReferenceParameterList getParameterList() {
        LightReferenceParameterList lightReferenceParameterList = this.myParameterList;
        if (lightReferenceParameterList != null) {
            return lightReferenceParameterList;
        }
        LightReferenceParameterList lightReferenceParameterList2 = new LightReferenceParameterList(this.myManager, PsiTypeElement.EMPTY_ARRAY);
        this.myParameterList = lightReferenceParameterList2;
        return lightReferenceParameterList2;
    }

    public String getQualifiedName() {
        PsiClass psiClass = (PsiClass) resolve();
        if (psiClass != null) {
            return psiClass.getQualifiedName();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myText;
    }

    public String getCanonicalText() {
        String qualifiedName = getQualifiedName();
        if (qualifiedName == null) {
            return "";
        }
        PsiType[] typeParameters = getTypeParameters();
        if (typeParameters.length == 0) {
            if (qualifiedName == null) {
                $$$reportNull$$$0(5);
            }
            return qualifiedName;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(qualifiedName);
        sb.append('<');
        for (int i = 0; i < typeParameters.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(typeParameters[i].getCanonicalText());
        }
        sb.append('>');
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(6);
        }
        return string;
    }

    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        throw new UnsupportedOperationException();
    }

    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getClass().getSimpleName() + getText();
    }

    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        return (psiElement instanceof PsiClass) && getManager().areElementsEquivalent(resolve(), psiElement);
    }

    public Object[] getVariants() {
        throw new RuntimeException("Variants are not available for light references");
    }

    public TextRange getRangeInElement() {
        return new TextRange(0, getTextLength());
    }

    public PsiType[] getTypeParameters() {
        PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
        if (psiTypeArr == null) {
            $$$reportNull$$$0(12);
        }
        return psiTypeArr;
    }
}
