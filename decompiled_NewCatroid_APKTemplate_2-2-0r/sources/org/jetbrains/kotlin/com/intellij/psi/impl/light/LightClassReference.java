package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LightClassReference extends LightClassReferenceBase implements PsiJavaCodeReferenceElement {
    private final String myClassName;
    private final PsiElement myContext;
    private final PsiClass myRefClass;
    private final GlobalSearchScope myResolveScope;
    private final PsiSubstitutor mySubstitutor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 17 || i == 18) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 17 || i == 18) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 8:
            case 12:
            case 15:
                objArr[0] = "text";
                break;
            case 2:
            case 6:
                objArr[0] = "resolveScope";
                break;
            case 3:
            case 7:
            case 11:
            case 14:
            default:
                objArr[0] = "manager";
                break;
            case 5:
            case 9:
                objArr[0] = "className";
                break;
            case 10:
                objArr[0] = "context";
                break;
            case 13:
            case 16:
                objArr[0] = "refClass";
                break;
            case 17:
            case 18:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightClassReference";
                break;
        }
        if (i == 17) {
            objArr[1] = "advancedResolve";
        } else if (i != 18) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightClassReference";
        } else {
            objArr[1] = "getResolveScope";
        }
        if (i != 17 && i != 18) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 17 && i != 18) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ void accept(PsiElementVisitor psiElementVisitor) {
        super.accept(psiElementVisitor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public /* bridge */ /* synthetic */ PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        return super.bindToElement(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public /* bridge */ /* synthetic */ String getCanonicalText() {
        return super.getCanonicalText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public /* bridge */ /* synthetic */ PsiElement getPsiElement() {
        return super.getPsiElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public /* bridge */ /* synthetic */ PsiReferenceParameterList getParameterList() {
        return super.getParameterList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public /* bridge */ /* synthetic */ PsiElement getQualifier() {
        return super.getQualifier();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public /* bridge */ /* synthetic */ TextRange getRangeInElement() {
        return super.getRangeInElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ PsiReference getReference() {
        return super.getReference();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public /* bridge */ /* synthetic */ PsiElement getReferenceNameElement() {
        return super.getReferenceNameElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ String getText() {
        return super.getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public /* bridge */ /* synthetic */ PsiType[] getTypeParameters() {
        return super.getTypeParameters();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public /* bridge */ /* synthetic */ Object[] getVariants() {
        return super.getVariants();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public /* bridge */ /* synthetic */ PsiElement handleElementRename(String str) throws IncorrectOperationException {
        return super.handleElementRename(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public /* bridge */ /* synthetic */ boolean isQualified() {
        return super.isQualified();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public /* bridge */ /* synthetic */ boolean isReferenceTo(PsiElement psiElement) {
        return super.isReferenceTo(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public /* bridge */ /* synthetic */ boolean isSoft() {
        return super.isSoft();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public /* bridge */ /* synthetic */ JavaResolveResult[] multiResolve(boolean z) {
        return super.multiResolve(z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public /* bridge */ /* synthetic */ void processVariants(PsiScopeProcessor psiScopeProcessor) {
        super.processVariants(psiScopeProcessor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private LightClassReference(PsiManager psiManager, String str, String str2, PsiSubstitutor psiSubstitutor, GlobalSearchScope globalSearchScope, PsiElement psiElement, PsiClass psiClass) {
        super(psiManager, str);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(2);
        }
        this.myClassName = str2;
        this.myResolveScope = globalSearchScope;
        this.myContext = psiElement;
        this.myRefClass = psiClass;
        this.mySubstitutor = psiSubstitutor;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightClassReference(PsiManager psiManager, String str, String str2, GlobalSearchScope globalSearchScope) {
        this(psiManager, str, str2, null, globalSearchScope, null, null);
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (str2 == null) {
            $$$reportNull$$$0(5);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(6);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightClassReference(PsiManager psiManager, String str, String str2, PsiSubstitutor psiSubstitutor, PsiElement psiElement) {
        this(psiManager, str, str2, psiSubstitutor, psiElement.getResolveScope(), psiElement, null);
        if (psiManager == null) {
            $$$reportNull$$$0(7);
        }
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (str2 == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightClassReference(PsiManager psiManager, String str, PsiClass psiClass) {
        this(psiManager, str, psiClass, (PsiSubstitutor) null);
        if (psiManager == null) {
            $$$reportNull$$$0(11);
        }
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(13);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightClassReference(PsiManager psiManager, String str, PsiClass psiClass, PsiSubstitutor psiSubstitutor) {
        this(psiManager, str, null, psiSubstitutor, psiClass.getResolveScope(), null, psiClass);
        if (psiManager == null) {
            $$$reportNull$$$0(14);
        }
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(16);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        if (this.myClassName != null) {
            JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(getProject());
            if (this.myContext != null) {
                return javaPsiFacade.getResolveHelper().resolveReferencedClass(this.myClassName, this.myContext);
            }
            return javaPsiFacade.findClass(this.myClassName, this.myResolveScope);
        }
        return this.myRefClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        PsiElement psiElementResolve = resolve();
        if (psiElementResolve == null) {
            JavaResolveResult javaResolveResult = JavaResolveResult.EMPTY;
            if (javaResolveResult == null) {
                $$$reportNull$$$0(17);
            }
            return javaResolveResult;
        }
        PsiSubstitutor psiSubstitutorCreateRawSubstitutor = this.mySubstitutor;
        if (psiSubstitutorCreateRawSubstitutor == null) {
            if (psiElementResolve instanceof PsiClass) {
                psiSubstitutorCreateRawSubstitutor = JavaPsiFacade.getElementFactory(this.myManager.getProject()).createRawSubstitutor((PsiClass) psiElementResolve);
            } else {
                psiSubstitutorCreateRawSubstitutor = PsiSubstitutor.EMPTY;
            }
        }
        return new CandidateInfo(psiElementResolve, psiSubstitutorCreateRawSubstitutor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        PsiClass psiClass;
        if (this.myClassName != null) {
            if (this.myContext != null && (psiClass = (PsiClass) resolve()) != null) {
                return psiClass.getQualifiedName();
            }
            return this.myClassName;
        }
        return this.myRefClass.getQualifiedName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public String getReferenceName() {
        String str = this.myClassName;
        if (str != null) {
            return PsiNameHelper.getShortClassName(str);
        }
        PsiClass psiClass = this.myRefClass;
        if (psiClass instanceof PsiAnonymousClass) {
            return ((PsiAnonymousClass) psiClass).getBaseClassReference().getReferenceName();
        }
        return psiClass.getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        if (this.myClassName != null) {
            if (this.myContext != null) {
                return new LightClassReference(this.myManager, this.myText, this.myClassName, this.mySubstitutor, this.myContext);
            }
            return new LightClassReference(this.myManager, this.myText, this.myClassName, this.mySubstitutor, this.myResolveScope, null, null);
        }
        return new LightClassReference(this.myManager, this.myText, this.myRefClass, this.mySubstitutor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        PsiClass psiClass = this.myRefClass;
        return psiClass == null || psiClass.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope globalSearchScope = this.myResolveScope;
        if (globalSearchScope == null) {
            $$$reportNull$$$0(18);
        }
        return globalSearchScope;
    }
}
