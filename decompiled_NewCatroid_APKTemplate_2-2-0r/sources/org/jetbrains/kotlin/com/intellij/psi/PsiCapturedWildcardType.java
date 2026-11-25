package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiCapturedWildcardType extends PsiType.Stub {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final RecursionGuard<Object> guard = RecursionManager.createGuard("captureGuard");
    private final PsiElement myContext;
    private final PsiWildcardType myExistential;
    private final PsiTypeParameter myParameter;
    private PsiType myUpperBound;

    /* JADX WARN: Code duplicated, block: B:21:0x0043  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 12:
            case 13:
            case 19:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
                i2 = 2;
                break;
            case 12:
            case 13:
            case 19:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        if (i != 1 && i != 3) {
            switch (i) {
                case 5:
                    objArr[0] = "context";
                    break;
                case 6:
                    objArr[0] = "typeParameter";
                    break;
                case 7:
                    objArr[0] = "wildcardType";
                    break;
                case 8:
                    objArr[0] = "captureSubstitutor";
                    break;
                case 9:
                case 10:
                case 11:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 20:
                case 21:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiCapturedWildcardType";
                    break;
                case 12:
                    objArr[0] = "text";
                    break;
                case 13:
                    objArr[0] = "visitor";
                    break;
                case 19:
                    objArr[0] = "upperBound";
                    break;
                default:
                    objArr[0] = "existential";
                    break;
            }
        } else {
            objArr[0] = "context";
        }
        switch (i) {
            case 9:
                objArr[1] = "getPresentableText";
                break;
            case 10:
                objArr[1] = "getCanonicalText";
                break;
            case 11:
                objArr[1] = "getInternalCanonicalText";
                break;
            case 12:
            case 13:
            case 19:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiCapturedWildcardType";
                break;
            case 14:
                objArr[1] = "getResolveScope";
                break;
            case 15:
                objArr[1] = "getSuperTypes";
                break;
            case 16:
            case 17:
            case 18:
                objArr[1] = "getUpperBound";
                break;
            case 20:
                objArr[1] = "getWildcard";
                break;
            case 21:
                objArr[1] = "getContext";
                break;
        }
        switch (i) {
            case 4:
            case 5:
                objArr[2] = "<init>";
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "captureUpperBound";
                break;
            case 9:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
                break;
            case 12:
                objArr[2] = "equalsToText";
                break;
            case 13:
                objArr[2] = "accept";
                break;
            case 19:
                objArr[2] = "setUpperBound";
                break;
            default:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
                throw new IllegalStateException(str2);
            case 12:
            case 13:
            case 19:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str != null) {
            return false;
        }
        $$$reportNull$$$0(12);
        return false;
    }

    public static PsiCapturedWildcardType create(PsiWildcardType psiWildcardType, PsiElement psiElement) {
        if (psiWildcardType == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return create(psiWildcardType, psiElement, null);
    }

    public static PsiCapturedWildcardType create(PsiWildcardType psiWildcardType, PsiElement psiElement, PsiTypeParameter psiTypeParameter) {
        if (psiWildcardType == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        return new PsiCapturedWildcardType(psiWildcardType, psiElement, psiTypeParameter);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private PsiCapturedWildcardType(PsiWildcardType psiWildcardType, PsiElement psiElement, PsiTypeParameter psiTypeParameter) {
        super(TypeAnnotationProvider.EMPTY);
        if (psiWildcardType == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        this.myExistential = psiWildcardType;
        this.myContext = psiElement;
        this.myParameter = psiTypeParameter;
        this.myUpperBound = PsiType.getJavaLangObject(psiElement.getManager(), getResolveScope());
    }

    public static boolean isCapture() {
        return guard.currentStack().isEmpty();
    }

    public static PsiType captureUpperBound(PsiTypeParameter psiTypeParameter, PsiWildcardType psiWildcardType, PsiSubstitutor psiSubstitutor) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(6);
        }
        if (psiWildcardType == null) {
            $$$reportNull$$$0(7);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(8);
        }
        PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
        PsiType bound = !psiWildcardType.isSuper() ? psiWildcardType.getBound() : null;
        PsiType greatestLowerBound = bound;
        for (PsiClassType psiClassType : extendsListTypes) {
            PsiType psiTypeSubstitute = psiSubstitutor.substitute(psiClassType);
            if (!(bound instanceof PsiArrayType) || !(psiTypeSubstitute instanceof PsiArrayType) || bound.isAssignableFrom(psiTypeSubstitute) || psiTypeSubstitute.isAssignableFrom(bound)) {
                if (psiTypeSubstitute instanceof PsiCapturedWildcardType) {
                    PsiType psiTypeSubstitute2 = psiSubstitutor.substitute(psiTypeParameter);
                    if (psiTypeSubstitute2 instanceof PsiCapturedWildcardType) {
                        for (PsiType upperBound = psiTypeSubstitute; upperBound instanceof PsiCapturedWildcardType; upperBound = ((PsiCapturedWildcardType) upperBound).getUpperBound()) {
                            if (upperBound == psiTypeSubstitute2) {
                                return null;
                            }
                        }
                    }
                }
                greatestLowerBound = greatestLowerBound == null ? psiTypeSubstitute : getGreatestLowerBound(greatestLowerBound, psiTypeSubstitute, psiWildcardType);
            }
        }
        return greatestLowerBound;
    }

    private static PsiType getGreatestLowerBound(final PsiType psiType, final PsiType psiType2, PsiWildcardType psiWildcardType) {
        return (PsiType) guard.doPreventingRecursion(psiWildcardType, true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return GenericsUtil.getGreatestLowerBound(psiType, psiType2);
            }
        });
    }

    public boolean equals(Object obj) {
        Boolean bool;
        if (!(obj instanceof PsiCapturedWildcardType)) {
            return false;
        }
        final PsiCapturedWildcardType psiCapturedWildcardType = (PsiCapturedWildcardType) obj;
        PsiManager manager = this.myContext.getManager();
        if (!manager.areElementsEquivalent(this.myContext, psiCapturedWildcardType.myContext)) {
            return false;
        }
        if ((this.myExistential.isSuper() || psiCapturedWildcardType.myExistential.isSuper()) && !this.myExistential.equals(psiCapturedWildcardType.myExistential)) {
            return false;
        }
        if (!(this.myContext instanceof PsiTypeParameter) && !manager.areElementsEquivalent(this.myParameter, psiCapturedWildcardType.myParameter)) {
            return false;
        }
        if (this.myParameter == null || !((bool = (Boolean) guard.doPreventingRecursion(this.myContext, true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return this.f$0.m6527xd5193438(psiCapturedWildcardType);
            }
        })) == null || bool.booleanValue())) {
            return this.myExistential.equals(psiCapturedWildcardType.myExistential);
        }
        return true;
    }

    /* JADX INFO: renamed from: lambda$equals$1$org-jetbrains-kotlin-com-intellij-psi-PsiCapturedWildcardType, reason: not valid java name */
    /* synthetic */ Boolean m6527xd5193438(PsiCapturedWildcardType psiCapturedWildcardType) {
        return Boolean.valueOf(Comparing.equal(this.myUpperBound, psiCapturedWildcardType.myUpperBound));
    }

    public int hashCode() {
        return this.myUpperBound.hashCode() + (this.myContext.hashCode() * 31);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText(boolean z) {
        String str = "capture of " + this.myExistential.getPresentableText(z);
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText(boolean z) {
        String canonicalText = this.myExistential.getCanonicalText(z);
        if (canonicalText == null) {
            $$$reportNull$$$0(10);
        }
        return canonicalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        String str = "capture<" + this.myExistential.getInternalCanonicalText() + '>';
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        return this.myExistential.isValid() && this.myContext.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(13);
        }
        return psiTypeVisitor.visitCapturedWildcardType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope resolveScope = this.myExistential.getResolveScope();
        if (resolveScope == null) {
            $$$reportNull$$$0(14);
        }
        return resolveScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        PsiType[] superTypes = this.myExistential.getSuperTypes();
        if (superTypes == null) {
            $$$reportNull$$$0(15);
        }
        return superTypes;
    }

    public PsiType getLowerBound() {
        return this.myExistential.isSuper() ? this.myExistential.getBound() : NULL;
    }

    public PsiType getUpperBound() {
        PsiType upperBound = getUpperBound(true);
        if (upperBound == null) {
            $$$reportNull$$$0(16);
        }
        return upperBound;
    }

    public PsiType getUpperBound(boolean z) {
        PsiType bound = this.myExistential.getBound();
        if (this.myExistential.isExtends() && this.myParameter == null) {
            if (bound == null) {
                $$$reportNull$$$0(17);
            }
            return bound;
        }
        PsiType psiTypeCaptureToplevelWildcards = (isCapture() && z) ? PsiUtil.captureToplevelWildcards(this.myUpperBound, this.myContext) : this.myUpperBound;
        if (psiTypeCaptureToplevelWildcards == null) {
            $$$reportNull$$$0(18);
        }
        return psiTypeCaptureToplevelWildcards;
    }

    public void setUpperBound(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(19);
        }
        this.myUpperBound = psiType;
    }

    public PsiWildcardType getWildcard() {
        PsiWildcardType psiWildcardType = this.myExistential;
        if (psiWildcardType == null) {
            $$$reportNull$$$0(20);
        }
        return psiWildcardType;
    }

    public PsiElement getContext() {
        PsiElement psiElement = this.myContext;
        if (psiElement == null) {
            $$$reportNull$$$0(21);
        }
        return psiElement;
    }

    public PsiTypeParameter getTypeParameter() {
        return this.myParameter;
    }
}
