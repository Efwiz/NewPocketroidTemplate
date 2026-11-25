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
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LightMemberReference extends LightElement implements PsiJavaCodeReferenceElement {
    private LightReferenceParameterList myParameterList;
    private final PsiMember myRefMember;
    private final PsiSubstitutor mySubstitutor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 6 || i == 7 || i == 8 || i == 13 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 6 || i == 7 || i == 8 || i == 13 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "member";
                break;
            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightMemberReference";
                break;
            case 5:
                objArr[0] = "processor";
                break;
            case 9:
                objArr[0] = "newElementName";
                break;
            case 10:
            case 12:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 11:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 2) {
            objArr[1] = "resolve";
        } else if (i == 3 || i == 4) {
            objArr[1] = "multiResolve";
        } else if (i == 6 || i == 7 || i == 8) {
            objArr[1] = "getCanonicalText";
        } else if (i == 13) {
            objArr[1] = "getElement";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightMemberReference";
        } else {
            objArr[1] = "getTypeParameters";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 13:
            case 14:
                break;
            case 5:
                objArr[2] = "processVariants";
                break;
            case 9:
                objArr[2] = "handleElementRename";
                break;
            case 10:
                objArr[2] = "bindToElement";
                break;
            case 11:
                objArr[2] = "accept";
                break;
            case 12:
                objArr[2] = "isReferenceTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 6 && i != 7 && i != 8 && i != 13 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        return this;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightMemberReference(PsiManager psiManager, PsiMember psiMember, PsiSubstitutor psiSubstitutor) {
        super(psiManager, JavaLanguage.INSTANCE);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (psiMember == null) {
            $$$reportNull$$$0(1);
        }
        this.myRefMember = psiMember;
        this.mySubstitutor = psiSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        PsiMember psiMember = this.myRefMember;
        if (psiMember == null) {
            $$$reportNull$$$0(2);
        }
        return psiMember;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        PsiElement psiElementResolve = resolve();
        PsiSubstitutor psiSubstitutor = this.mySubstitutor;
        if (psiSubstitutor == null) {
            psiSubstitutor = PsiSubstitutor.EMPTY;
        }
        return new CandidateInfo(psiElementResolve, psiSubstitutor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        JavaResolveResult javaResolveResultAdvancedResolve = advancedResolve(z);
        if (javaResolveResultAdvancedResolve != JavaResolveResult.EMPTY) {
            return new JavaResolveResult[]{javaResolveResultAdvancedResolve};
        }
        JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
        if (javaResolveResultArr == null) {
            $$$reportNull$$$0(4);
        }
        return javaResolveResultArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public void processVariants(PsiScopeProcessor psiScopeProcessor) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(5);
        }
        throw new RuntimeException("Variants are not available for light references");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiReferenceParameterList getParameterList() {
        LightReferenceParameterList lightReferenceParameterList = this.myParameterList;
        if (lightReferenceParameterList != null) {
            return lightReferenceParameterList;
        }
        LightReferenceParameterList lightReferenceParameterList2 = new LightReferenceParameterList(this.myManager, PsiTypeElement.EMPTY_ARRAY);
        this.myParameterList = lightReferenceParameterList2;
        return lightReferenceParameterList2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        String qualifiedName;
        PsiClass psiClassMo6092getContainingClass = this.myRefMember.mo6092getContainingClass();
        if (psiClassMo6092getContainingClass != null && (qualifiedName = psiClassMo6092getContainingClass.getQualifiedName()) != null) {
            return qualifiedName + '.' + this.myRefMember.getMyName();
        }
        return this.myRefMember.getMyName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    /* JADX INFO: renamed from: getReferenceName */
    public String getCustomReferenceName() {
        return getQualifiedName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myRefMember.getMyName() + getParameterList().getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String qualifiedName = getQualifiedName();
        if (qualifiedName == null) {
            $$$reportNull$$$0(6);
            return null;
        }
        PsiType[] typeParameters = getTypeParameters();
        if (typeParameters.length == 0) {
            if (qualifiedName == null) {
                $$$reportNull$$$0(7);
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
            $$$reportNull$$$0(8);
        }
        return string;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new LightMemberReference(this.myManager, this.myRefMember, this.mySubstitutor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(11);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "LightClassReference:" + this.myRefMember.getMyName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        return (psiElement instanceof PsiClass) && psiElement.getManager().areElementsEquivalent(resolve(), psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        throw new RuntimeException("Variants are not available for light references");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return new TextRange(0, getTextLength());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        PsiReferenceParameterList parameterList = getParameterList();
        if (parameterList == null || parameterList.isValid()) {
            return this.myRefMember.isValid();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiType[] getTypeParameters() {
        PsiReferenceParameterList parameterList = getParameterList();
        PsiType[] typeArguments = parameterList == null ? PsiType.EMPTY_ARRAY : parameterList.getTypeArguments();
        if (typeArguments == null) {
            $$$reportNull$$$0(14);
        }
        return typeArguments;
    }
}
