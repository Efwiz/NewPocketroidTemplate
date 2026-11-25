package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LightClassTypeReference extends LightClassReferenceBase implements PsiJavaCodeReferenceElement {
    private final PsiClassType myType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6 || i == 7) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "text";
        } else if (i == 2 || i == 4) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i == 5 || i == 6 || i == 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightClassTypeReference";
        } else {
            objArr[0] = "manager";
        }
        if (i == 5) {
            objArr[1] = "advancedResolve";
        } else if (i == 6) {
            objArr[1] = "getType";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightClassTypeReference";
        } else {
            objArr[1] = "getResolveScope";
        }
        if (i != 5 && i != 6 && i != 7) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 7) {
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public /* bridge */ /* synthetic */ String getQualifiedName() {
        return super.getQualifiedName();
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
    private LightClassTypeReference(PsiManager psiManager, String str, PsiClassType psiClassType) {
        super(psiManager, str);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (psiClassType == null) {
            $$$reportNull$$$0(2);
        }
        this.myType = psiClassType;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightClassTypeReference(PsiManager psiManager, PsiClassType psiClassType) {
        this(psiManager, psiClassType.getCanonicalText(true), psiClassType);
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
        if (psiClassType == null) {
            $$$reportNull$$$0(4);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        return this.myType.resolve();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = this.myType.resolveGenerics();
        if (classResolveResultResolveGenerics == null) {
            $$$reportNull$$$0(5);
        }
        return classResolveResultResolveGenerics;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    /* JADX INFO: renamed from: getReferenceName */
    public String getCustomReferenceName() {
        return this.myType.getClassName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new LightClassTypeReference(this.myManager, this.myText, this.myType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return this.myType.isValid();
    }

    public PsiClassType getType() {
        PsiClassType psiClassType = this.myType;
        if (psiClassType == null) {
            $$$reportNull$$$0(6);
        }
        return psiClassType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope resolveScope = this.myType.getResolveScope();
        if (resolveScope == null) {
            $$$reportNull$$$0(7);
        }
        return resolveScope;
    }
}
