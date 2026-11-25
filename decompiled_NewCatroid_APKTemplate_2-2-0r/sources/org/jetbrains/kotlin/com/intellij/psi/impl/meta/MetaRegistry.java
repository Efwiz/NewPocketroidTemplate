package org.jetbrains.kotlin.com.intellij.psi.impl.meta;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.util.NullUtils;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.meta.MetaDataContributor;
import org.jetbrains.kotlin.com.intellij.psi.meta.MetaDataRegistrar;
import org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaData;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class MetaRegistry extends MetaDataRegistrar {
    private static final Logger LOG = Logger.getInstance((Class<?>) MetaRegistry.class);
    private static final List<MyBinding> ourBindings = ContainerUtil.createLockFreeCopyOnWriteList();
    private static volatile boolean ourContributorsLoaded;

    static {
        MetaDataContributor.EP_NAME.addChangeListener(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.meta.MetaRegistry$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MetaRegistry.clearBindings();
            }
        }, ApplicationManager.getApplication());
    }

    public static PsiMetaData getMeta(PsiElement psiElement) {
        return getMetaBase(psiElement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void clearBindings() {
        List<MyBinding> list = ourBindings;
        synchronized (list) {
            ourContributorsLoaded = false;
            list.clear();
        }
    }

    private static void ensureContributorsLoaded() {
        if (ourContributorsLoaded) {
            return;
        }
        synchronized (ourBindings) {
            if (!ourContributorsLoaded) {
                Iterator<MetaDataContributor> it = MetaDataContributor.EP_NAME.getExtensionList().iterator();
                while (it.hasNext()) {
                    it.next().contributeMetaData(MetaDataRegistrar.getInstance());
                }
                ourContributorsLoaded = true;
            }
        }
    }

    public static PsiMetaData getMetaBase(final PsiElement psiElement) {
        ProgressIndicatorProvider.checkCanceled();
        return (PsiMetaData) CachedValuesManager.getCachedValue(psiElement, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.meta.MetaRegistry$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return MetaRegistry.lambda$getMetaBase$0(psiElement);
            }
        });
    }

    static /* synthetic */ CachedValueProvider.Result lambda$getMetaBase$0(PsiElement psiElement) {
        ensureContributorsLoaded();
        for (MyBinding myBinding : ourBindings) {
            if (myBinding.myFilter.isClassAcceptable(psiElement.getClass()) && myBinding.myFilter.isAcceptable(psiElement, psiElement.getLightParent())) {
                PsiMetaData psiMetaData = (PsiMetaData) myBinding.myDataClass.get();
                psiMetaData.init(psiElement);
                Object[] dependencies = psiMetaData.getDependencies();
                if (NullUtils.hasNull(dependencies)) {
                    LOG.error(psiMetaData + "(" + myBinding.myDataClass + ") provided null dependency");
                }
                return new CachedValueProvider.Result(psiMetaData, ArrayUtil.append((PsiElement[]) dependencies, psiElement));
            }
        }
        return new CachedValueProvider.Result(null, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.meta.MetaDataRegistrar
    public <T extends PsiMetaData> void registerMetaData(ElementFilter elementFilter, final Class<T> cls) {
        ourBindings.add(0, new MyBinding(elementFilter, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.meta.MetaRegistry$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return MetaRegistry.lambda$registerMetaData$1(cls);
            }
        }));
    }

    static /* synthetic */ PsiMetaData lambda$registerMetaData$1(Class cls) {
        try {
            return (PsiMetaData) cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            LOG.error(e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MyBinding {
        private final Supplier<? extends PsiMetaData> myDataClass;
        private final ElementFilter myFilter;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "filter";
            } else {
                objArr[0] = "dataClass";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/meta/MetaRegistry$MyBinding";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        MyBinding(ElementFilter elementFilter, Supplier<? extends PsiMetaData> supplier) {
            if (elementFilter == null) {
                $$$reportNull$$$0(0);
            }
            if (supplier == null) {
                $$$reportNull$$$0(1);
            }
            this.myFilter = elementFilter;
            this.myDataClass = supplier;
        }
    }
}
