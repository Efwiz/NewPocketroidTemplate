package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LightPackageReference extends LightElement implements PsiJavaCodeReferenceElement {
    private final String myPackageName;
    private final PsiPackage myRefPackage;

    /* JADX WARN: Code duplicated, block: B:27:0x003e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 3 : 2];
        if (i == 3) {
            objArr[0] = "newElementName";
        } else if (i == 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 5) {
            objArr[0] = "visitor";
        } else if (i == 6) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightPackageReference";
        } else {
            objArr[0] = "processor";
        }
        switch (i) {
            case 2:
                objArr[1] = "getCanonicalText";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightPackageReference";
                break;
            case 8:
                objArr[1] = "getElement";
                break;
            case 9:
                objArr[1] = "getTypeParameters";
                break;
            default:
                objArr[1] = "multiResolve";
                break;
        }
        if (i == 3) {
            objArr[2] = "handleElementRename";
        } else if (i == 4) {
            objArr[2] = "bindToElement";
        } else if (i == 5) {
            objArr[2] = "accept";
        } else if (i == 6) {
            objArr[2] = "isReferenceTo";
        } else if (i == 7) {
            objArr[2] = "processVariants";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiReferenceParameterList getParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public PsiElement getQualifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiElement getReferenceNameElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public boolean isQualified() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    public LightPackageReference(PsiManager psiManager, PsiPackage psiPackage) {
        super(psiManager, JavaLanguage.INSTANCE);
        this.myPackageName = null;
        this.myRefPackage = psiPackage;
    }

    public LightPackageReference(PsiManager psiManager, String str) {
        super(psiManager, JavaLanguage.INSTANCE);
        this.myPackageName = str;
        this.myRefPackage = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        if (this.myPackageName != null) {
            return JavaPsiFacade.getInstance(this.myManager.getProject()).findPackage(this.myPackageName);
        }
        return this.myRefPackage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        PsiElement psiElementResolve = resolve();
        return psiElementResolve == null ? JavaResolveResult.EMPTY : new CandidateInfo(psiElementResolve, PsiSubstitutor.EMPTY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        JavaResolveResult javaResolveResultAdvancedResolve = advancedResolve(z);
        if (javaResolveResultAdvancedResolve != JavaResolveResult.EMPTY) {
            return new JavaResolveResult[]{javaResolveResultAdvancedResolve};
        }
        JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(1);
        }
        return javaResolveResultArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        String str = this.myPackageName;
        return str != null ? str : this.myRefPackage.getQualifiedName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String text = getText();
        if (text == null) {
            $$$reportNull$$$0(2);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        if (this.myPackageName != null) {
            return new LightPackageReference(this.myManager, this.myPackageName);
        }
        return new LightPackageReference(this.myManager, this.myRefPackage);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiJavaCodeReferenceElement:" + getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElement instanceof PsiPackage) {
            return getManager().areElementsEquivalent(resolve(), psiElement);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        throw new RuntimeException("Variants are not available for light references");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public void processVariants(PsiScopeProcessor psiScopeProcessor) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(7);
        }
        throw new RuntimeException("Variants are not available for light references");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        return getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public String getReferenceName() {
        String str = this.myPackageName;
        if (str != null) {
            return PsiNameHelper.getShortClassName(str);
        }
        return this.myRefPackage.getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return new TextRange(0, getTextLength());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        PsiPackage psiPackage = this.myRefPackage;
        return psiPackage == null || psiPackage.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiType[] getTypeParameters() {
        PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
        if (psiTypeArr == null) {
            $$$reportNull$$$0(9);
        }
        return psiTypeArr;
    }
}
