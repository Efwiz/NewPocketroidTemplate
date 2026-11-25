package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.lang.MetaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceContributor;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProviderBean;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.psi.ReferenceRange;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.IdempotenceChecker;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMaps;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;

/* JADX INFO: loaded from: classes3.dex */
public final class ReferenceProvidersRegistryImpl extends ReferenceProvidersRegistry {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final LanguageExtension<PsiReferenceContributor> CONTRIBUTOR_EXTENSION = new LanguageExtension<>(PsiReferenceContributor.EP_NAME);
    private static final LanguageExtension<PsiReferenceProviderBean> REFERENCE_PROVIDER_EXTENSION = new LanguageExtension<>(PsiReferenceProviderBean.EP_NAME.getName());
    private final Map<Language, PsiReferenceRegistrarImpl> myRegistrars = new ConcurrentHashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 5 || i == 13 || i == 21 || i == 9 || i == 10 || i == 17 || i == 18 || i == 24 || i == 25) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 5 || i == 13 || i == 21 || i == 9 || i == 10 || i == 17 || i == 18 || i == 24 || i == 25) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 9:
            case 10:
            case 13:
            case 17:
            case 18:
            case 21:
            case 24:
            case 25:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ReferenceProvidersRegistryImpl";
                break;
            case 2:
                objArr[0] = "registrar";
                break;
            case 3:
                objArr[0] = "contributor";
                break;
            case 4:
            case 6:
            default:
                objArr[0] = "language";
                break;
            case 7:
            case 11:
            case 14:
            case 15:
                objArr[0] = "context";
                break;
            case 8:
                objArr[0] = "hints";
                break;
            case 12:
                objArr[0] = "providers";
                break;
            case 16:
                objArr[0] = "providerInfo";
                break;
            case 19:
                objArr[0] = "allReferencesMap";
                break;
            case 20:
                objArr[0] = "maxPriorityRefs";
                break;
            case 22:
                objArr[0] = "higherPriorityRefs";
                break;
            case 23:
                objArr[0] = "lowerPriorityRefs";
                break;
            case 26:
                objArr[0] = "parentDisposable";
                break;
        }
        if (i == 1) {
            objArr[1] = "createRegistrar";
        } else if (i == 5) {
            objArr[1] = "getRegistrar";
        } else if (i == 13) {
            objArr[1] = "mapNotEmptyReferencesFromProviders";
        } else if (i == 21) {
            objArr[1] = "getLowerPriorityReferences";
        } else if (i == 9 || i == 10) {
            objArr[1] = "doGetReferencesFromProviders";
        } else if (i == 17 || i == 18) {
            objArr[1] = "getReferences";
        } else if (i == 24 || i == 25) {
            objArr[1] = "collectReferences";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ReferenceProvidersRegistryImpl";
        }
        switch (i) {
            case 1:
            case 5:
            case 9:
            case 10:
            case 13:
            case 17:
            case 18:
            case 21:
            case 24:
            case 25:
                break;
            case 2:
            case 3:
                objArr[2] = "registerContributedReferenceProviders";
                break;
            case 4:
                objArr[2] = "getRegistrar";
                break;
            case 6:
                objArr[2] = "unloadProvidersFor";
                break;
            case 7:
            case 8:
                objArr[2] = "doGetReferencesFromProviders";
                break;
            case 11:
            case 12:
                objArr[2] = "mapNotEmptyReferencesFromProviders";
                break;
            case 14:
                objArr[2] = "assertReferenceUnderlyingElement";
                break;
            case 15:
            case 16:
                objArr[2] = "getReferences";
                break;
            case 19:
            case 20:
                objArr[2] = "getLowerPriorityReferences";
                break;
            case 22:
            case 23:
                objArr[2] = "haveNotIntersectedTextRanges";
                break;
            case 26:
                objArr[2] = "disableUnderlyingElementChecks";
                break;
            default:
                objArr[2] = "createRegistrar";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 5 && i != 13 && i != 21 && i != 9 && i != 10 && i != 17 && i != 18 && i != 24 && i != 25) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ReferenceProvidersRegistryImpl() {
        if (ApplicationManager.getApplication().getExtensionArea().hasExtensionPoint(PsiReferenceContributor.EP_NAME)) {
            PsiReferenceContributor.EP_NAME.addExtensionPointListener(new ExtensionPointListener<KeyedLazyInstance<PsiReferenceContributor>>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistryImpl.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i == 1 || i == 3) {
                        objArr[0] = "pluginDescriptor";
                    } else {
                        objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
                    }
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ReferenceProvidersRegistryImpl$1";
                    if (i == 2 || i == 3) {
                        objArr[2] = "extensionRemoved";
                    } else {
                        objArr[2] = "extensionAdded";
                    }
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
                public void extensionAdded(KeyedLazyInstance<PsiReferenceContributor> keyedLazyInstance, PluginDescriptor pluginDescriptor) {
                    if (keyedLazyInstance == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (pluginDescriptor == null) {
                        $$$reportNull$$$0(1);
                    }
                    Language languageFindLanguageByID = Language.findLanguageByID(keyedLazyInstance.getKey());
                    PsiReferenceContributor keyedLazyInstance2 = keyedLazyInstance.getInstance();
                    if (languageFindLanguageByID == Language.ANY) {
                        Iterator it = ReferenceProvidersRegistryImpl.this.myRegistrars.values().iterator();
                        while (it.hasNext()) {
                            ReferenceProvidersRegistryImpl.registerContributedReferenceProviders((PsiReferenceRegistrarImpl) it.next(), keyedLazyInstance2);
                        }
                    } else if (languageFindLanguageByID != null) {
                        registerContributorForLanguageAndDialects(languageFindLanguageByID, keyedLazyInstance2);
                        if (languageFindLanguageByID instanceof MetaLanguage) {
                            Iterator<Language> it2 = ((MetaLanguage) languageFindLanguageByID).getMatchingLanguages().iterator();
                            while (it2.hasNext()) {
                                registerContributorForLanguageAndDialects(it2.next(), keyedLazyInstance2);
                            }
                        }
                    }
                }

                private void registerContributorForLanguageAndDialects(Language language, PsiReferenceContributor psiReferenceContributor) {
                    Iterator<Language> it = LanguageUtil.getAllDerivedLanguages(language).iterator();
                    while (it.hasNext()) {
                        PsiReferenceRegistrarImpl psiReferenceRegistrarImpl = (PsiReferenceRegistrarImpl) ReferenceProvidersRegistryImpl.this.myRegistrars.get(it.next());
                        if (psiReferenceRegistrarImpl != null) {
                            ReferenceProvidersRegistryImpl.registerContributedReferenceProviders(psiReferenceRegistrarImpl, psiReferenceContributor);
                        }
                    }
                }

                @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
                public void extensionRemoved(KeyedLazyInstance<PsiReferenceContributor> keyedLazyInstance, PluginDescriptor pluginDescriptor) {
                    if (keyedLazyInstance == null) {
                        $$$reportNull$$$0(2);
                    }
                    if (pluginDescriptor == null) {
                        $$$reportNull$$$0(3);
                    }
                    Disposer.dispose(keyedLazyInstance.getInstance());
                }
            }, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiReferenceRegistrarImpl createRegistrar(Language language) {
        if (language == null) {
            $$$reportNull$$$0(0);
        }
        PsiReferenceRegistrarImpl psiReferenceRegistrarImpl = new PsiReferenceRegistrarImpl();
        Iterator<PsiReferenceContributor> it = CONTRIBUTOR_EXTENSION.allForLanguageOrAny(language).iterator();
        while (it.hasNext()) {
            registerContributedReferenceProviders(psiReferenceRegistrarImpl, it.next());
        }
        for (final PsiReferenceProviderBean psiReferenceProviderBean : REFERENCE_PROVIDER_EXTENSION.allForLanguageOrAny(language)) {
            ElementPattern<PsiElement> elementPatternCreateElementPattern = psiReferenceProviderBean.createElementPattern();
            if (elementPatternCreateElementPattern != null) {
                psiReferenceRegistrarImpl.registerReferenceProvider(elementPatternCreateElementPattern, new PsiReferenceProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistryImpl.2
                    PsiReferenceProvider myProvider;

                    private static /* synthetic */ void $$$reportNull$$$0(int i) {
                        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                        Object[] objArr = new Object[i != 2 ? 3 : 2];
                        if (i == 1) {
                            objArr[0] = "context";
                        } else if (i != 2) {
                            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                        } else {
                            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ReferenceProvidersRegistryImpl$2";
                        }
                        if (i != 2) {
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/ReferenceProvidersRegistryImpl$2";
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
                        if (this.myProvider == null) {
                            PsiReferenceProvider psiReferenceProviderInstantiate = psiReferenceProviderBean.instantiate();
                            this.myProvider = psiReferenceProviderInstantiate;
                            if (psiReferenceProviderInstantiate == null) {
                                this.myProvider = ReferenceProvidersRegistry.NULL_REFERENCE_PROVIDER;
                            }
                        }
                        PsiReference[] referencesByElement = this.myProvider.getReferencesByElement(psiElement, processingContext);
                        if (referencesByElement == null) {
                            $$$reportNull$$$0(2);
                        }
                        return referencesByElement;
                    }
                });
            }
        }
        psiReferenceRegistrarImpl.markInitialized();
        return psiReferenceRegistrarImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void registerContributedReferenceProviders(PsiReferenceRegistrarImpl psiReferenceRegistrarImpl, PsiReferenceContributor psiReferenceContributor) {
        if (psiReferenceRegistrarImpl == null) {
            $$$reportNull$$$0(2);
        }
        if (psiReferenceContributor == null) {
            $$$reportNull$$$0(3);
        }
        psiReferenceContributor.registerReferenceProviders(new TrackingReferenceRegistrar(psiReferenceRegistrarImpl, psiReferenceContributor));
        Disposer.register(ApplicationManager.getApplication(), psiReferenceContributor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
    public PsiReferenceRegistrarImpl getRegistrar(Language language) {
        if (language == null) {
            $$$reportNull$$$0(4);
        }
        PsiReferenceRegistrarImpl psiReferenceRegistrarImplComputeIfAbsent = this.myRegistrars.computeIfAbsent(language, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistryImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ReferenceProvidersRegistryImpl.createRegistrar((Language) obj);
            }
        });
        if (psiReferenceRegistrarImplComputeIfAbsent == null) {
            $$$reportNull$$$0(5);
        }
        return psiReferenceRegistrarImplComputeIfAbsent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
    public void unloadProvidersFor(Language language) {
        if (language == null) {
            $$$reportNull$$$0(6);
        }
        PsiReferenceRegistrarImpl psiReferenceRegistrarImplRemove = this.myRegistrars.remove(language);
        if (psiReferenceRegistrarImplRemove != null) {
            psiReferenceRegistrarImplRemove.cleanup();
        }
        Iterator<PsiReferenceRegistrarImpl> it = this.myRegistrars.values().iterator();
        while (it.hasNext()) {
            it.next().clearBindingsCache();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet] */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
    protected PsiReference[] doGetReferencesFromProviders(PsiElement psiElement, PsiReferenceService.Hints hints) {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        if (hints == null) {
            $$$reportNull$$$0(8);
        }
        Double2ObjectMap<List<PsiReference[]>> double2ObjectMapMapNotEmptyReferencesFromProviders = mapNotEmptyReferencesFromProviders(psiElement, getRegistrar(psiElement.getLanguage()).getPairsByElement(psiElement, hints));
        if (double2ObjectMapMapNotEmptyReferencesFromProviders.isEmpty()) {
            PsiReference[] psiReferenceArr = PsiReference.EMPTY_ARRAY;
            if (psiReferenceArr == null) {
                $$$reportNull$$$0(9);
            }
            return psiReferenceArr;
        }
        SmartList smartList = new SmartList();
        double dMax = Math.max(-100.0d, ArrayUtil.max(double2ObjectMapMapNotEmptyReferencesFromProviders.keySet().toDoubleArray()));
        List<PsiReference> listCollectReferences = collectReferences(double2ObjectMapMapNotEmptyReferencesFromProviders.get(dMax));
        ContainerUtil.addAllNotNull(smartList, listCollectReferences);
        ContainerUtil.addAllNotNull(smartList, getLowerPriorityReferences(double2ObjectMapMapNotEmptyReferencesFromProviders, dMax, listCollectReferences));
        PsiReference[] psiReferenceArr2 = (PsiReference[]) smartList.toArray(PsiReference.EMPTY_ARRAY);
        if (psiReferenceArr2 == null) {
            $$$reportNull$$$0(10);
        }
        return psiReferenceArr2;
    }

    private static Double2ObjectMap<List<PsiReference[]>> mapNotEmptyReferencesFromProviders(PsiElement psiElement, List<? extends ProviderBinding.ProviderInfo<ProcessingContext>> list) {
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        Double2ObjectOpenHashMap double2ObjectOpenHashMap = new Double2ObjectOpenHashMap();
        for (ProviderBinding.ProviderInfo<ProcessingContext> providerInfo : list) {
            PsiReference[] references = getReferences(psiElement, providerInfo);
            if ((ApplicationManager.getApplication().isUnitTestMode() || ApplicationManager.getApplication().isInternal()) && Registry.is("ide.check.reference.provider.underlying.element")) {
                assertReferenceUnderlyingElement(psiElement, references, providerInfo.provider);
            }
            if (references.length > 0) {
                List smartList = (List) double2ObjectOpenHashMap.get(providerInfo.priority);
                if (smartList == null) {
                    smartList = new SmartList();
                    double2ObjectOpenHashMap.put(providerInfo.priority, smartList);
                }
                smartList.add(references);
                if (IdempotenceChecker.isLoggingEnabled()) {
                    IdempotenceChecker.logTrace(providerInfo.provider + " returned " + Arrays.toString(references));
                }
            }
        }
        return double2ObjectOpenHashMap;
    }

    private static void assertReferenceUnderlyingElement(PsiElement psiElement, PsiReference[] psiReferenceArr, PsiReferenceProvider psiReferenceProvider) {
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        for (PsiReference psiReference : psiReferenceArr) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static PsiReference[] getReferences(PsiElement psiElement, ProviderBinding.ProviderInfo<? extends ProcessingContext> providerInfo) {
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        if (providerInfo == null) {
            $$$reportNull$$$0(16);
        }
        try {
            PsiReference[] referencesByElement = providerInfo.provider.getReferencesByElement(psiElement, (ProcessingContext) providerInfo.processingContext);
            if (referencesByElement == null) {
                $$$reportNull$$$0(17);
            }
            return referencesByElement;
        } catch (IndexNotReadyException unused) {
            PsiReference[] psiReferenceArr = PsiReference.EMPTY_ARRAY;
            if (psiReferenceArr == null) {
                $$$reportNull$$$0(18);
            }
            return psiReferenceArr;
        }
    }

    private static List<PsiReference> getLowerPriorityReferences(Double2ObjectMap<List<PsiReference[]>> double2ObjectMap, double d, List<? extends PsiReference> list) {
        if (double2ObjectMap == null) {
            $$$reportNull$$$0(19);
        }
        if (list == null) {
            $$$reportNull$$$0(20);
        }
        SmartList smartList = new SmartList();
        ObjectIterator it = Double2ObjectMaps.fastIterable(double2ObjectMap).iterator();
        while (it.hasNext()) {
            Double2ObjectMap.Entry entry = (Double2ObjectMap.Entry) it.next();
            if (d != entry.getDoubleKey()) {
                for (PsiReference[] psiReferenceArr : (List) entry.getValue()) {
                    if (haveNotIntersectedTextRanges(list, psiReferenceArr)) {
                        ContainerUtil.addAllNotNull(smartList, psiReferenceArr);
                    }
                }
            }
        }
        return smartList;
    }

    private static boolean haveNotIntersectedTextRanges(List<? extends PsiReference> list, PsiReference[] psiReferenceArr) {
        if (list == null) {
            $$$reportNull$$$0(22);
        }
        if (psiReferenceArr == null) {
            $$$reportNull$$$0(23);
        }
        for (PsiReference psiReference : psiReferenceArr) {
            if (psiReference != null) {
                for (PsiReference psiReference2 : list) {
                    if (psiReference2 != null && ReferenceRange.containsRangeInElement(psiReference2, psiReference.getRangeInElement())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static List<PsiReference> collectReferences(Collection<PsiReference[]> collection) {
        if (collection == null) {
            List<PsiReference> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(24);
            }
            return listEmptyList;
        }
        SmartList smartList = new SmartList();
        Iterator<PsiReference[]> it = collection.iterator();
        while (it.hasNext()) {
            ContainerUtil.addAllNotNull(smartList, it.next());
        }
        return smartList;
    }

    @Deprecated
    public static void disableUnderlyingElementChecks(Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(26);
        }
        Registry.get("ide.check.reference.provider.underlying.element").setValue(false, disposable);
    }
}
