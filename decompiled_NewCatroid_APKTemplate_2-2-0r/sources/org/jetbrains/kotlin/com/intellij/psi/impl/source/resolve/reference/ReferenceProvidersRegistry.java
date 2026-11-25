package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ReferenceProvidersRegistry {
    public static final PsiReferenceProvider NULL_REFERENCE_PROVIDER = new PsiReferenceProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "context";
            } else if (i != 2) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ReferenceProvidersRegistry$1";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ReferenceProvidersRegistry$1";
            } else {
                objArr[1] = "getReferencesByElement";
            }
            if (i != 2) {
                objArr[2] = "getReferencesByElement";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider, org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.providers.CustomizableReferenceProvider
        public PsiReference[] getReferencesByElement(PsiElement psiElement, ProcessingContext processingContext) {
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            if (processingContext == null) {
                $$$reportNull$$$0(1);
            }
            PsiReference[] psiReferenceArr = PsiReference.EMPTY_ARRAY;
            if (psiReferenceArr == null) {
                $$$reportNull$$$0(2);
            }
            return psiReferenceArr;
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 2) {
            objArr[0] = "hints";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ReferenceProvidersRegistry";
        } else {
            objArr[0] = "context";
        }
        if (i == 3 || i == 4) {
            objArr[1] = "getReferencesFromProviders";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ReferenceProvidersRegistry";
        }
        if (i != 3 && i != 4) {
            objArr[2] = "getReferencesFromProviders";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract PsiReference[] doGetReferencesFromProviders(PsiElement psiElement, PsiReferenceService.Hints hints);

    public abstract PsiReferenceRegistrar getRegistrar(Language language);

    public abstract void unloadProvidersFor(Language language);

    public static ReferenceProvidersRegistry getInstance() {
        return (ReferenceProvidersRegistry) ApplicationManager.getApplication().getService(ReferenceProvidersRegistry.class);
    }

    public static PsiReference[] getReferencesFromProviders(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        return getReferencesFromProviders(psiElement, PsiReferenceService.Hints.NO_HINTS);
    }

    public static PsiReference[] getReferencesFromProviders(final PsiElement psiElement, PsiReferenceService.Hints hints) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (hints == null) {
            $$$reportNull$$$0(2);
        }
        ProgressIndicatorProvider.checkCanceled();
        if (hints == PsiReferenceService.Hints.NO_HINTS) {
            PsiReference[] psiReferenceArr = (PsiReference[]) ((PsiReference[]) CachedValuesManager.getCachedValue(psiElement, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return CachedValueProvider.Result.create(ReferenceProvidersRegistry.getInstance().doGetReferencesFromProviders(psiElement, PsiReferenceService.Hints.NO_HINTS), PsiModificationTracker.MODIFICATION_COUNT);
                }
            })).clone();
            if (psiReferenceArr == null) {
                $$$reportNull$$$0(3);
            }
            return psiReferenceArr;
        }
        PsiReference[] psiReferenceArrDoGetReferencesFromProviders = getInstance().doGetReferencesFromProviders(psiElement, hints);
        if (psiReferenceArrDoGetReferencesFromProviders == null) {
            $$$reportNull$$$0(4);
        }
        return psiReferenceArrDoGetReferencesFromProviders;
    }
}
