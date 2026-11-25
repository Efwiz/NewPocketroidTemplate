package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiMultiReference implements PsiPolyVariantReference {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Comparator<PsiReference> COMPARATOR = new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return PsiMultiReference.lambda$static$0((PsiReference) obj, (PsiReference) obj2);
        }
    };
    private final PsiElement myElement;
    private final PsiReference[] myReferences;
    private boolean mySorted;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 6 || i == 7 || i == 11 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 6 || i == 7 || i == 11 || i == 12) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/PsiMultiReference";
                break;
            case 4:
                objArr[0] = "reference";
                break;
            case 5:
                objArr[0] = "inElement";
                break;
            case 8:
                objArr[0] = "newElementName";
                break;
            case 9:
            case 10:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = OverlayablePolicy.NAME_references;
                break;
        }
        if (i == 1) {
            objArr[1] = "getReferences";
        } else if (i == 2) {
            objArr[1] = "getElement";
        } else if (i == 3) {
            objArr[1] = "getRangeInElement";
        } else if (i == 6) {
            objArr[1] = "getReferenceRange";
        } else if (i == 7) {
            objArr[1] = "getCanonicalText";
        } else if (i == 11) {
            objArr[1] = "getVariants";
        } else if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/PsiMultiReference";
        } else {
            objArr[1] = "multiResolve";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 11:
            case 12:
                break;
            case 4:
            case 5:
                objArr[2] = "getReferenceRange";
                break;
            case 8:
                objArr[2] = "handleElementRename";
                break;
            case 9:
                objArr[2] = "bindToElement";
                break;
            case 10:
                objArr[2] = "isReferenceTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 6 && i != 7 && i != 11 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ int lambda$static$0(PsiReference psiReference, PsiReference psiReference2) {
        boolean zIsSoft = psiReference.isSoft();
        if (zIsSoft != psiReference2.isSoft()) {
            return zIsSoft ? 1 : -1;
        }
        boolean zResolves = resolves(psiReference);
        boolean zResolves2 = resolves(psiReference2);
        if (zResolves && !zResolves2) {
            return -1;
        }
        if (!zResolves && zResolves2) {
            return 1;
        }
        TextRange rangeInElement = psiReference.getRangeInElement();
        TextRange rangeInElement2 = psiReference2.getRangeInElement();
        if (TextRange.areSegmentsEqual(rangeInElement, rangeInElement2)) {
            return 0;
        }
        if (rangeInElement.getStartOffset() < rangeInElement2.getStartOffset() || rangeInElement.getEndOffset() > rangeInElement2.getEndOffset()) {
            return (rangeInElement2.getStartOffset() < rangeInElement.getStartOffset() || rangeInElement2.getEndOffset() > rangeInElement.getEndOffset()) ? 0 : 1;
        }
        return -1;
    }

    private static boolean resolves(PsiReference psiReference) {
        return ((psiReference instanceof PsiPolyVariantReference) && ((PsiPolyVariantReference) psiReference).multiResolve(false).length > 0) || psiReference.resolve() != null;
    }

    public PsiMultiReference(PsiReference[] psiReferenceArr, PsiElement psiElement) {
        if (psiReferenceArr == null) {
            $$$reportNull$$$0(0);
        }
        this.myReferences = psiReferenceArr;
        this.myElement = psiElement;
    }

    public PsiReference[] getReferences() {
        PsiReference[] psiReferenceArr = (PsiReference[]) this.myReferences.clone();
        if (psiReferenceArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiReferenceArr;
    }

    private synchronized PsiReference chooseReference() {
        if (!this.mySorted) {
            Arrays.sort(this.myReferences, COMPARATOR);
            this.mySorted = true;
        }
        return this.myReferences[0];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement getElement() {
        PsiElement psiElement = this.myElement;
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        TextRange rangeInElementIfSameForAll = getRangeInElementIfSameForAll();
        if (rangeInElementIfSameForAll == null) {
            return getReferenceRange(chooseReference(), this.myElement);
        }
        if (rangeInElementIfSameForAll == null) {
            $$$reportNull$$$0(3);
        }
        return rangeInElementIfSameForAll;
    }

    private TextRange getRangeInElementIfSameForAll() {
        TextRange textRange = null;
        for (PsiReference psiReference : getReferences()) {
            TextRange referenceRange = getReferenceRange(psiReference, this.myElement);
            if (textRange == null) {
                textRange = referenceRange;
            } else if (!textRange.equals(referenceRange)) {
                return null;
            }
        }
        return textRange;
    }

    public static TextRange getReferenceRange(PsiReference psiReference, PsiElement psiElement) {
        if (psiReference == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        TextRange rangeInElement = psiReference.getRangeInElement();
        PsiElement element = psiReference.getElement();
        PsiElement parent = element;
        while (parent != psiElement) {
            int startOffsetInParent = parent.getStartOffsetInParent();
            if (rangeInElement.getStartOffset() + startOffsetInParent < 0) {
                throw new IllegalArgumentException("Inconsistent reference range in #" + psiElement.getLanguage().getID() + ":ref of " + psiReference.getClass() + " on " + classAndRange(element) + " with range " + psiReference.getRangeInElement() + ", requested range in PSI of " + classAndRange(psiElement));
            }
            rangeInElement = rangeInElement.shiftRight(startOffsetInParent);
            parent = parent.getParent();
            if (parent instanceof PsiFile) {
                break;
            }
        }
        if (rangeInElement == null) {
            $$$reportNull$$$0(6);
        }
        return rangeInElement;
    }

    private static String classAndRange(PsiElement psiElement) {
        return psiElement.getClass() + " " + psiElement.getTextRange();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        PsiReference psiReferenceChooseReference = chooseReference();
        if (cannotChoose()) {
            ResolveResult[] resolveResultArrMultiResolve = multiResolve(false);
            if (resolveResultArrMultiResolve.length == 1) {
                return resolveResultArrMultiResolve[0].getElement();
            }
            return null;
        }
        return psiReferenceChooseReference.resolve();
    }

    private boolean cannotChoose() {
        PsiReference[] psiReferenceArr = this.myReferences;
        return psiReferenceArr.length > 1 && COMPARATOR.compare(psiReferenceArr[0], psiReferenceArr[1]) == 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String canonicalText = chooseReference().getCanonicalText();
        if (canonicalText == null) {
            $$$reportNull$$$0(7);
        }
        return canonicalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return chooseReference().handleElementRename(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        return chooseReference().bindToElement(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        for (PsiReference psiReference : this.myReferences) {
            if (psiReference.isReferenceTo(psiElement)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        HashSet hashSet = new HashSet();
        for (PsiReference psiReference : this.myReferences) {
            ContainerUtil.addAll(hashSet, psiReference.getVariants());
        }
        Object[] array = hashSet.toArray();
        if (array == null) {
            $$$reportNull$$$0(11);
        }
        return array;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        for (PsiReference psiReference : getReferences()) {
            if (!psiReference.isSoft()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public ResolveResult[] multiResolve(boolean z) {
        PsiReference[] references = getReferences();
        LinkedHashSet linkedHashSet = new LinkedHashSet(references.length);
        PsiElementResolveResult psiElementResolveResult = null;
        for (PsiReference psiReference : references) {
            if (psiReference instanceof PsiPolyVariantReference) {
                ContainerUtil.addAll(linkedHashSet, ((PsiPolyVariantReference) psiReference).multiResolve(z));
            } else {
                PsiElement psiElementResolve = psiReference.resolve();
                if (psiElementResolve != null) {
                    PsiElementResolveResult psiElementResolveResult2 = new PsiElementResolveResult(psiElementResolve);
                    if (getElement() == psiElementResolve) {
                        psiElementResolveResult = psiElementResolveResult2;
                    } else {
                        linkedHashSet.add(psiElementResolveResult2);
                    }
                }
            }
        }
        if (linkedHashSet.isEmpty() && psiElementResolveResult != null) {
            linkedHashSet.add(psiElementResolveResult);
        }
        ResolveResult[] resolveResultArr = (ResolveResult[]) linkedHashSet.toArray(ResolveResult.EMPTY_ARRAY);
        if (resolveResultArr == null) {
            $$$reportNull$$$0(12);
        }
        return resolveResultArr;
    }

    public String toString() {
        return "PsiMultiReference{" + StringUtil.join(this.myReferences, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiReference) obj).getClass().getName();
            }
        }, ",") + '}';
    }
}
