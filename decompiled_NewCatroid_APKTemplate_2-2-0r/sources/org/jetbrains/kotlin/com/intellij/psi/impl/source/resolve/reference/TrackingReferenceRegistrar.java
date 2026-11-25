package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference;

import net.bytebuddy.implementation.MethodDelegation;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceRegistrar;

/* JADX INFO: loaded from: classes3.dex */
class TrackingReferenceRegistrar extends PsiReferenceRegistrar {
    private final PsiReferenceRegistrarImpl myDelegate;
    private final Disposable myDisposable;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "disposable";
        } else if (i == 2) {
            objArr[0] = "pattern";
        } else if (i != 3) {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        } else {
            objArr[0] = Constants.EXTRA_PROVIDER;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/TrackingReferenceRegistrar";
        if (i == 2 || i == 3) {
            objArr[2] = "registerReferenceProvider";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    TrackingReferenceRegistrar(PsiReferenceRegistrarImpl psiReferenceRegistrarImpl, Disposable disposable) {
        if (psiReferenceRegistrarImpl == null) {
            $$$reportNull$$$0(0);
        }
        if (disposable == null) {
            $$$reportNull$$$0(1);
        }
        this.myDelegate = psiReferenceRegistrarImpl;
        this.myDisposable = disposable;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceRegistrar
    public <T extends PsiElement> void registerReferenceProvider(ElementPattern<T> elementPattern, PsiReferenceProvider psiReferenceProvider, double d) {
        if (elementPattern == null) {
            $$$reportNull$$$0(2);
        }
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(3);
        }
        this.myDelegate.registerReferenceProvider(elementPattern, psiReferenceProvider, d, this.myDisposable);
    }
}
