package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiPolyVariantCachingReference implements PsiPolyVariantReference {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/PsiPolyVariantCachingReference";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        if (i != 1) {
            objArr[1] = "multiResolve";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/PsiPolyVariantCachingReference";
        }
        if (i == 1) {
            objArr[2] = "isReferenceTo";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    protected abstract ResolveResult[] resolveInner(boolean z, PsiFile psiFile);

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public final ResolveResult[] multiResolve(boolean z) {
        PsiFile containingFile = getPsiElement().getContainingFile();
        ResolveResult[] resolveResultArrResolveWithCaching = ResolveCache.getInstance(containingFile.getProject()).resolveWithCaching(this, (ResolveCache.PolyVariantContextResolver<PsiPolyVariantCachingReference>) MyResolver.INSTANCE, true, z, containingFile);
        if (resolveResultArrResolveWithCaching == null) {
            $$$reportNull$$$0(0);
        }
        return resolveResultArrResolveWithCaching;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        ResolveResult[] resolveResultArrMultiResolve = multiResolve(false);
        if (resolveResultArrMultiResolve.length == 1) {
            return resolveResultArrMultiResolve[0].getElement();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return getPsiElement().getManager().areElementsEquivalent(resolve(), psiElement);
    }

    private static class MyResolver implements ResolveCache.PolyVariantContextResolver<PsiPolyVariantReference> {
        private static final MyResolver INSTANCE = new MyResolver();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "containingFile";
            } else if (i != 2) {
                objArr[0] = "ref";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/PsiPolyVariantCachingReference$MyResolver";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/PsiPolyVariantCachingReference$MyResolver";
            } else {
                objArr[1] = "resolve";
            }
            if (i != 2) {
                objArr[2] = "resolve";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        private MyResolver() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.PolyVariantContextResolver
        public ResolveResult[] resolve(PsiPolyVariantReference psiPolyVariantReference, PsiFile psiFile, boolean z) {
            if (psiPolyVariantReference == null) {
                $$$reportNull$$$0(0);
            }
            if (psiFile == null) {
                $$$reportNull$$$0(1);
            }
            ResolveResult[] resolveResultArrResolveInner = ((PsiPolyVariantCachingReference) psiPolyVariantReference).resolveInner(z, psiFile);
            if (resolveResultArrResolveInner == null) {
                $$$reportNull$$$0(2);
            }
            return resolveResultArrResolveInner;
        }
    }
}
