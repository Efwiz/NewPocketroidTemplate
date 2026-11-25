package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.patterns.CaseInsensitiveValuePatternCondition;
import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;
import org.jetbrains.kotlin.com.intellij.patterns.PatternCondition;
import org.jetbrains.kotlin.com.intellij.patterns.PsiNamePatternCondition;
import org.jetbrains.kotlin.com.intellij.patterns.ValuePatternCondition;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;

/* JADX INFO: loaded from: classes3.dex */
public class PsiReferenceRegistrarImpl extends PsiReferenceRegistrar {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiReferenceRegistrarImpl.class);
    private boolean myInitialized;
    private final Map<Class<?>, SimpleProviderBinding> myBindingsMap = new HashMap();
    private final Map<Class<?>, NamedObjectProviderBinding> myNamedBindingsMap = new HashMap();
    private final List<Disposable> myCleanupDisposables = new ArrayList();
    private final ConcurrentMap<Class<?>, ProviderBinding[]> myBindingCache = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.PsiReferenceRegistrarImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return this.f$0.m6669xd9495b26((Class) obj);
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 12 || i == 13) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 12 || i == 13) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 7:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 2:
            case 8:
            default:
                objArr[0] = "pattern";
                break;
            case 4:
                objArr[0] = "scope";
                break;
            case 6:
                objArr[0] = "scopeClass";
                break;
            case 9:
                objArr[0] = "names";
                break;
            case 10:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 11:
                objArr[0] = "hints";
                break;
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/PsiReferenceRegistrarImpl";
                break;
        }
        if (i == 12 || i == 13) {
            objArr[1] = "getPairsByElement";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/PsiReferenceRegistrarImpl";
        }
        switch (i) {
            case 4:
            case 5:
                objArr[2] = "unregisterReferenceProvider";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "registerNamedReferenceProvider";
                break;
            case 10:
            case 11:
                objArr[2] = "getPairsByElement";
                break;
            case 12:
            case 13:
                break;
            default:
                objArr[2] = "registerReferenceProvider";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 12 && i != 13) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    PsiReferenceRegistrarImpl() {
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-resolve-reference-PsiReferenceRegistrarImpl, reason: not valid java name */
    /* synthetic */ ProviderBinding[] m6669xd9495b26(Class cls) {
        SmartList smartList = new SmartList();
        for (Class<?> cls2 : this.myBindingsMap.keySet()) {
            if (cls2.isAssignableFrom(cls)) {
                smartList.add(this.myBindingsMap.get(cls2));
            }
        }
        for (Class<?> cls3 : this.myNamedBindingsMap.keySet()) {
            if (cls3.isAssignableFrom(cls)) {
                smartList.add(this.myNamedBindingsMap.get(cls3));
            }
        }
        return (ProviderBinding[]) smartList.toArray(new ProviderBinding[0]);
    }

    void markInitialized() {
        this.myInitialized = true;
    }

    void cleanup() {
        Iterator it = new ArrayList(this.myCleanupDisposables).iterator();
        while (it.hasNext()) {
            Disposer.dispose((Disposable) it.next());
        }
        this.myCleanupDisposables.clear();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceRegistrar
    public <T extends PsiElement> void registerReferenceProvider(ElementPattern<T> elementPattern, PsiReferenceProvider psiReferenceProvider, double d) {
        if (elementPattern == null) {
            $$$reportNull$$$0(0);
        }
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(1);
        }
        registerReferenceProvider(elementPattern, psiReferenceProvider, d, null);
    }

    public <T extends PsiElement> void registerReferenceProvider(ElementPattern<T> elementPattern, final PsiReferenceProvider psiReferenceProvider, double d, Disposable disposable) {
        if (elementPattern == null) {
            $$$reportNull$$$0(2);
        }
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(3);
        }
        if (this.myInitialized && !ApplicationManager.getApplication().isUnitTestMode() && disposable == null) {
            LOG.error("Reference provider registration is only allowed from PsiReferenceContributor");
        }
        final Class<?> acceptedClass = elementPattern.getCondition().getInitialCondition().getAcceptedClass();
        for (PatternCondition<? super T> patternCondition : elementPattern.getCondition().getConditions()) {
            if (patternCondition instanceof PsiNamePatternCondition) {
                PsiNamePatternCondition<?> psiNamePatternCondition = (PsiNamePatternCondition) patternCondition;
                for (PatternCondition<? super String> patternCondition2 : psiNamePatternCondition.getNamePattern().getCondition().getConditions()) {
                    if (patternCondition2 instanceof ValuePatternCondition) {
                        registerNamedReferenceProvider(ArrayUtilRt.toStringArray(((ValuePatternCondition) patternCondition2).getValues()), psiNamePatternCondition, acceptedClass, true, psiReferenceProvider, d, elementPattern, disposable);
                        return;
                    } else if (patternCondition2 instanceof CaseInsensitiveValuePatternCondition) {
                        registerNamedReferenceProvider(((CaseInsensitiveValuePatternCondition) patternCondition2).getValues(), psiNamePatternCondition, acceptedClass, false, psiReferenceProvider, d, elementPattern, disposable);
                        return;
                    }
                }
                break;
            }
        }
        SimpleProviderBinding simpleProviderBinding = this.myBindingsMap.get(acceptedClass);
        if (simpleProviderBinding == null) {
            Map<Class<?>, SimpleProviderBinding> map = this.myBindingsMap;
            SimpleProviderBinding simpleProviderBinding2 = new SimpleProviderBinding();
            map.put(acceptedClass, simpleProviderBinding2);
            simpleProviderBinding = simpleProviderBinding2;
        }
        simpleProviderBinding.registerProvider(psiReferenceProvider, elementPattern, d);
        if (disposable != null) {
            Disposable disposable2 = new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.PsiReferenceRegistrarImpl.1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
                public void dispose() {
                    PsiReferenceRegistrarImpl.this.unregisterReferenceProvider(acceptedClass, psiReferenceProvider);
                    PsiReferenceRegistrarImpl.this.myCleanupDisposables.remove(this);
                }
            };
            Disposer.register(disposable, disposable2);
            this.myCleanupDisposables.add(disposable2);
        }
        clearBindingsCache();
    }

    void clearBindingsCache() {
        this.myBindingCache.clear();
    }

    public void unregisterReferenceProvider(Class<?> cls, PsiReferenceProvider psiReferenceProvider) {
        if (cls == null) {
            $$$reportNull$$$0(4);
        }
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(5);
        }
        SimpleProviderBinding simpleProviderBinding = this.myBindingsMap.get(cls);
        if (simpleProviderBinding != null) {
            simpleProviderBinding.unregisterProvider(psiReferenceProvider);
            if (simpleProviderBinding.isEmpty()) {
                this.myBindingsMap.remove(cls);
            }
        }
        clearBindingsCache();
    }

    private void registerNamedReferenceProvider(String[] strArr, final PsiNamePatternCondition<?> psiNamePatternCondition, final Class<?> cls, boolean z, final PsiReferenceProvider psiReferenceProvider, double d, ElementPattern<?> elementPattern, Disposable disposable) {
        final NamedObjectProviderBinding namedObjectProviderBinding;
        if (cls == null) {
            $$$reportNull$$$0(6);
        }
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(7);
        }
        if (elementPattern == null) {
            $$$reportNull$$$0(8);
        }
        if (strArr == null) {
            $$$reportNull$$$0(9);
        }
        NamedObjectProviderBinding namedObjectProviderBinding2 = this.myNamedBindingsMap.get(cls);
        if (namedObjectProviderBinding2 == null) {
            Map<Class<?>, NamedObjectProviderBinding> map = this.myNamedBindingsMap;
            NamedObjectProviderBinding namedObjectProviderBinding3 = new NamedObjectProviderBinding() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.PsiReferenceRegistrarImpl.2
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "position", "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/PsiReferenceRegistrarImpl$2", "getName"));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.NamedObjectProviderBinding
                protected String getName(PsiElement psiElement) {
                    if (psiElement == null) {
                        $$$reportNull$$$0(0);
                    }
                    return psiNamePatternCondition.getPropertyValue((Object) psiElement);
                }
            };
            map.put(cls, namedObjectProviderBinding3);
            namedObjectProviderBinding = namedObjectProviderBinding3;
        } else {
            namedObjectProviderBinding = namedObjectProviderBinding2;
        }
        namedObjectProviderBinding.registerProvider(strArr, elementPattern, z, psiReferenceProvider, d);
        if (disposable != null) {
            Disposable disposable2 = new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.PsiReferenceRegistrarImpl.3
                @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
                public void dispose() {
                    namedObjectProviderBinding.unregisterProvider(psiReferenceProvider);
                    if (namedObjectProviderBinding.isEmpty()) {
                        PsiReferenceRegistrarImpl.this.myNamedBindingsMap.remove(cls);
                    }
                    PsiReferenceRegistrarImpl.this.myCleanupDisposables.remove(this);
                }
            };
            this.myCleanupDisposables.add(disposable2);
            Disposer.register(disposable, disposable2);
        }
    }

    List<ProviderBinding.ProviderInfo<ProcessingContext>> getPairsByElement(PsiElement psiElement, PsiReferenceService.Hints hints) {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        if (hints == null) {
            $$$reportNull$$$0(11);
        }
        ProviderBinding[] providerBindingArr = this.myBindingCache.get(psiElement.getClass());
        if (providerBindingArr.length == 0) {
            List<ProviderBinding.ProviderInfo<ProcessingContext>> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(12);
            }
            return listEmptyList;
        }
        SmartList smartList = new SmartList();
        for (ProviderBinding providerBinding : providerBindingArr) {
            providerBinding.addAcceptableReferenceProviders(psiElement, smartList, hints);
        }
        return smartList;
    }
}
