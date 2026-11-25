package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.ElementManipulator;
import org.jetbrains.kotlin.com.intellij.psi.ElementManipulators;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CachingReference implements PsiReference {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/CachingReference";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/CachingReference";
        } else {
            objArr[1] = "getManipulator";
        }
        if (i != 1) {
            objArr[2] = "isReferenceTo";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    public abstract PsiElement resolveInner();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        return (PsiElement) ResolveCache.getInstance(getPsiElement().getProject()).resolveWithCaching(this, (ResolveCache.AbstractResolver<CachingReference, TResult>) MyResolver.INSTANCE, false, false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        return getPsiElement().getManager().areElementsEquivalent(resolve(), psiElement);
    }

    public static <T extends PsiElement> ElementManipulator<T> getManipulator(T t) {
        ElementManipulator<T> manipulator = ElementManipulators.getManipulator(t);
        if (manipulator == null) {
            throw new IncorrectOperationException("Manipulator for this element is not defined: " + t + "; " + t.getClass());
        }
        if (manipulator == null) {
            $$$reportNull$$$0(1);
        }
        return manipulator;
    }

    private static class MyResolver implements ResolveCache.Resolver {
        private static final MyResolver INSTANCE = new MyResolver();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "ref", "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/CachingReference$MyResolver", "resolve"));
        }

        private MyResolver() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.AbstractResolver
        public PsiElement resolve(PsiReference psiReference, boolean z) {
            if (psiReference == null) {
                $$$reportNull$$$0(0);
            }
            return ((CachingReference) psiReference).resolveInner();
        }
    }
}
