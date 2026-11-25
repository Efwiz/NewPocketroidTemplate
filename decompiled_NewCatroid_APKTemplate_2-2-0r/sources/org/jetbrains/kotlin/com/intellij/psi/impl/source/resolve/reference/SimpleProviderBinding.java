package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference;

import java.util.List;
import java.util.function.Predicate;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
class SimpleProviderBinding implements ProviderBinding {
    private final List<ProviderBinding.ProviderInfo<ElementPattern<?>>> myProviderPairs = ContainerUtil.createLockFreeCopyOnWriteList();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "pattern";
        } else if (i == 2) {
            objArr[0] = "position";
        } else if (i == 3) {
            objArr[0] = SchemaSymbols.ATTVAL_LIST;
        } else if (i != 4) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else {
            objArr[0] = "hints";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/SimpleProviderBinding";
        if (i == 2 || i == 3 || i == 4) {
            objArr[2] = "addAcceptableReferenceProviders";
        } else if (i != 5) {
            objArr[2] = "registerProvider";
        } else {
            objArr[2] = "unregisterProvider";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    SimpleProviderBinding() {
    }

    void registerProvider(PsiReferenceProvider psiReferenceProvider, ElementPattern<?> elementPattern, double d) {
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(0);
        }
        if (elementPattern == null) {
            $$$reportNull$$$0(1);
        }
        this.myProviderPairs.add(new ProviderBinding.ProviderInfo<>(psiReferenceProvider, elementPattern, d));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ProviderBinding
    public void addAcceptableReferenceProviders(PsiElement psiElement, List<? super ProviderBinding.ProviderInfo<ProcessingContext>> list, PsiReferenceService.Hints hints) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        if (hints == null) {
            $$$reportNull$$$0(4);
        }
        NamedObjectProviderBinding.addMatchingProviders(psiElement, this.myProviderPairs, list, hints);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ProviderBinding
    public void unregisterProvider(final PsiReferenceProvider psiReferenceProvider) {
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(5);
        }
        this.myProviderPairs.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.SimpleProviderBinding$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ProviderBinding.ProviderInfo) obj).provider.equals(psiReferenceProvider);
            }
        });
    }

    boolean isEmpty() {
        return this.myProviderPairs.isEmpty();
    }
}
