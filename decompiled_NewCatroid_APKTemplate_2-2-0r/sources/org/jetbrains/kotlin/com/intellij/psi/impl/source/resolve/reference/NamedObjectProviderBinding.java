package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;
import org.jetbrains.kotlin.com.intellij.util.SharedProcessingContext;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class NamedObjectProviderBinding implements ProviderBinding {
    private final Map<String, List<ProviderBinding.ProviderInfo<ElementPattern<?>>>> myNamesToProvidersMap = new HashMap(5);
    private final Map<String, List<ProviderBinding.ProviderInfo<ElementPattern<?>>>> myNamesToProvidersMapInsensitive = new HashMap(5);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "filter";
                break;
            case 2:
            case 6:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 3:
            case 7:
                objArr[0] = "position";
                break;
            case 4:
                objArr[0] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 5:
            case 10:
                objArr[0] = "hints";
                break;
            case 8:
                objArr[0] = "providerList";
                break;
            case 9:
                objArr[0] = "output";
                break;
            default:
                objArr[0] = "names";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/NamedObjectProviderBinding";
        switch (i) {
            case 3:
            case 4:
            case 5:
                objArr[2] = "addAcceptableReferenceProviders";
                break;
            case 6:
                objArr[2] = "unregisterProvider";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "addMatchingProviders";
                break;
            default:
                objArr[2] = "registerProvider";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract String getName(PsiElement psiElement);

    public void registerProvider(String[] strArr, ElementPattern<?> elementPattern, boolean z, PsiReferenceProvider psiReferenceProvider, double d) {
        if (strArr == null) {
            $$$reportNull$$$0(0);
        }
        if (elementPattern == null) {
            $$$reportNull$$$0(1);
        }
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(2);
        }
        Map<String, List<ProviderBinding.ProviderInfo<ElementPattern<?>>>> map = z ? this.myNamesToProvidersMap : this.myNamesToProvidersMapInsensitive;
        for (String lowerCase : strArr) {
            if (!z) {
                lowerCase = StringUtil.toLowerCase(lowerCase);
            }
            List<ProviderBinding.ProviderInfo<ElementPattern<?>>> smartList = map.get(lowerCase);
            if (smartList == null) {
                smartList = new SmartList<>();
                map.put(lowerCase, smartList);
            }
            smartList.add(new ProviderBinding.ProviderInfo<>(psiReferenceProvider, elementPattern, d));
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ProviderBinding
    public void addAcceptableReferenceProviders(PsiElement psiElement, List<? super ProviderBinding.ProviderInfo<ProcessingContext>> list, PsiReferenceService.Hints hints) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        if (hints == null) {
            $$$reportNull$$$0(5);
        }
        String name2 = getName(psiElement);
        if (name2 != null) {
            addMatchingProviders(psiElement, ContainerUtil.notNullize(this.myNamesToProvidersMap.get(name2)), list, hints);
            addMatchingProviders(psiElement, ContainerUtil.notNullize(this.myNamesToProvidersMapInsensitive.get(StringUtil.toLowerCase(name2))), list, hints);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ProviderBinding
    public void unregisterProvider(final PsiReferenceProvider psiReferenceProvider) {
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(6);
        }
        Iterator<List<ProviderBinding.ProviderInfo<ElementPattern<?>>>> it = this.myNamesToProvidersMap.values().iterator();
        while (it.hasNext()) {
            it.next().removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.NamedObjectProviderBinding$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((ProviderBinding.ProviderInfo) obj).provider.equals(psiReferenceProvider);
                }
            });
        }
        Iterator<List<ProviderBinding.ProviderInfo<ElementPattern<?>>>> it2 = this.myNamesToProvidersMapInsensitive.values().iterator();
        while (it2.hasNext()) {
            it2.next().removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.NamedObjectProviderBinding$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((ProviderBinding.ProviderInfo) obj).provider.equals(psiReferenceProvider);
                }
            });
        }
    }

    boolean isEmpty() {
        return this.myNamesToProvidersMap.isEmpty() && this.myNamesToProvidersMapInsensitive.isEmpty();
    }

    static void addMatchingProviders(PsiElement psiElement, List<? extends ProviderBinding.ProviderInfo<ElementPattern<?>>> list, Collection<? super ProviderBinding.ProviderInfo<ProcessingContext>> collection, PsiReferenceService.Hints hints) {
        boolean zAccepts;
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        if (list == null) {
            $$$reportNull$$$0(8);
        }
        if (collection == null) {
            $$$reportNull$$$0(9);
        }
        if (hints == null) {
            $$$reportNull$$$0(10);
        }
        SharedProcessingContext sharedProcessingContext = new SharedProcessingContext();
        for (int i = 0; i < list.size(); i++) {
            ProviderBinding.ProviderInfo<ElementPattern<?>> providerInfo = list.get(i);
            if (hints == PsiReferenceService.Hints.NO_HINTS || providerInfo.provider.acceptsHints(psiElement, hints)) {
                ProcessingContext processingContext = new ProcessingContext(sharedProcessingContext);
                try {
                    zAccepts = providerInfo.processingContext.accepts(psiElement, processingContext);
                } catch (IndexNotReadyException unused) {
                    zAccepts = false;
                }
                if (zAccepts) {
                    collection.add(new ProviderBinding.ProviderInfo(providerInfo.provider, processingContext, providerInfo.priority));
                }
            }
        }
    }
}
