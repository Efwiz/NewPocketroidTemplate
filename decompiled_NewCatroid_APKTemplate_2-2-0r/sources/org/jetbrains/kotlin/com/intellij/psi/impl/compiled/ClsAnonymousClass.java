package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;

/* JADX INFO: loaded from: classes3.dex */
public class ClsAnonymousClass extends ClsClassImpl implements PsiAnonymousClass {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsAnonymousClass";
        } else {
            objArr[0] = "stub";
        }
        if (i == 1) {
            objArr[1] = "getBaseClassReference";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsAnonymousClass";
        } else {
            objArr[1] = "getBaseClassType";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiExpressionList getArgumentList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public boolean isInQualifiedNew() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsAnonymousClass(PsiClassStub psiClassStub) {
        super(psiClassStub);
        if (psiClassStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiJavaCodeReferenceElement getBaseClassReference() {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsAnonymousClass$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6584xfed9f4c6();
            }
        });
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(1);
        }
        return psiJavaCodeReferenceElement;
    }

    /* JADX INFO: renamed from: lambda$getBaseClassReference$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsAnonymousClass, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6584xfed9f4c6() {
        PsiJavaCodeReferenceElement[] referenceElements = getExtendsList().getReferenceElements();
        if (referenceElements.length == 0) {
            referenceElements = getImplementsList().getReferenceElements();
        }
        return CachedValueProvider.Result.create(referenceElements.length == 0 ? new ClsJavaCodeReferenceElementImpl(this, CommonClassNames.JAVA_LANG_OBJECT) : referenceElements[0], this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiClassType getBaseClassType() {
        PsiClassType psiClassType = (PsiClassType) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsAnonymousClass$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6585x68913ed2();
            }
        });
        if (psiClassType == null) {
            $$$reportNull$$$0(2);
        }
        return psiClassType;
    }

    /* JADX INFO: renamed from: lambda$getBaseClassType$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsAnonymousClass, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6585x68913ed2() {
        PsiClassType[] referencedTypes = getExtendsList().getReferencedTypes();
        if (referencedTypes.length == 0) {
            referencedTypes = getImplementsList().getReferencedTypes();
        }
        return CachedValueProvider.Result.create(referencedTypes.length == 0 ? PsiType.getJavaLangObject(getManager(), getResolveScope()) : referencedTypes[0], this);
    }
}
