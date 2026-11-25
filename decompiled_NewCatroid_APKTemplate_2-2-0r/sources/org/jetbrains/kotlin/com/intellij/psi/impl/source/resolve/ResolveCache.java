package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import com.google.common.util.concurrent.Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.lang.ref.ReferenceQueue;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.model.Symbol;
import org.jetbrains.kotlin.com.intellij.model.psi.PsiSymbolReference;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcher;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.impl.AnyPsiChangeListener;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IdempotenceChecker;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;

/* JADX INFO: loaded from: classes3.dex */
public class ResolveCache implements Disposable {
    private static final StrongValueReference<?, ?> EMPTY_RESOLVE_RESULT;
    private static final Object NULL_RESULT;
    private static final StrongValueReference<?, ?> NULL_VALUE_REFERENCE;
    private final AtomicReferenceArray<Map<?, ?>> myNonPhysicalMaps;
    private final AtomicReferenceArray<Map<?, ?>> myPhysicalMaps;

    @FunctionalInterface
    public interface AbstractResolver<TRef extends PsiReference, TResult> {
        TResult resolve(TRef tref, boolean z);
    }

    @FunctionalInterface
    public interface PolyVariantContextResolver<T extends PsiPolyVariantReference> {
        ResolveResult[] resolve(T t, PsiFile psiFile, boolean z);
    }

    @FunctionalInterface
    public interface PolyVariantResolver<T extends PsiPolyVariantReference> extends AbstractResolver<T, ResolveResult[]> {
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.AbstractResolver
        ResolveResult[] resolve(T t, boolean z);
    }

    public interface PsiSymbolReferenceResolver<R extends PsiSymbolReference> {
        Collection<? extends Symbol> resolve(R r);
    }

    @FunctionalInterface
    public interface Resolver extends AbstractResolver<PsiReference, PsiElement> {
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 8 || i == 12 || i == 16 || i == 18 || i == 20 || i == 26 || i == 32) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 8 || i == 12 || i == 16 || i == 18 || i == 20 || i == 26 || i == 32) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "bus";
                break;
            case 3:
                objArr[0] = "array";
                break;
            case 4:
            case 6:
            case 9:
            case 13:
            case 21:
            case 24:
            case 29:
            case 30:
            case 33:
                objArr[0] = "ref";
                break;
            case 5:
            case 7:
            case 10:
            case 14:
            case 17:
            case 19:
            case 23:
            case 25:
            case 31:
                objArr[0] = "resolver";
                break;
            case 8:
            case 12:
            case 16:
            case 18:
            case 20:
            case 26:
            case 32:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveCache";
                break;
            case 11:
            case 15:
                objArr[0] = "containingFile";
                break;
            case 22:
                objArr[0] = ResponseCacheMiddleware.CACHE;
                break;
            case 27:
                objArr[0] = "result";
                break;
            case 28:
                objArr[0] = "resolveResult";
                break;
            case 34:
                objArr[0] = "map";
                break;
            case 35:
                objArr[0] = "doResolve";
                break;
            case 36:
                objArr[0] = "value";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 8 || i == 12 || i == 16 || i == 18 || i == 20) {
            objArr[1] = "resolveWithCaching";
        } else if (i == 26) {
            objArr[1] = "loggingResolver";
        } else if (i != 32) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveCache";
        } else {
            objArr[1] = "getMap";
        }
        switch (i) {
            case 1:
                objArr[2] = "<init>";
                break;
            case 2:
                objArr[2] = "clearCacheOnPsiChange";
                break;
            case 3:
                objArr[2] = "clearArray";
                break;
            case 4:
            case 5:
            case 21:
            case 22:
            case 23:
                objArr[2] = "resolve";
                break;
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 30:
            case 31:
                objArr[2] = "resolveWithCaching";
                break;
            case 8:
            case 12:
            case 16:
            case 18:
            case 20:
            case 26:
            case 32:
                break;
            case 24:
            case 25:
                objArr[2] = "loggingResolver";
                break;
            case 27:
                objArr[2] = "ensureValidResults";
                break;
            case 28:
                objArr[2] = "ensureValidPsi";
                break;
            case 29:
                objArr[2] = "getCachedResults";
                break;
            case 33:
            case 34:
            case 35:
                objArr[2] = ResponseCacheMiddleware.CACHE;
                break;
            case 36:
                objArr[2] = "createStrongReference";
                break;
            default:
                objArr[2] = "getInstance";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 12 && i != 16 && i != 18 && i != 20 && i != 26 && i != 32) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private static int getIndex(boolean z, boolean z2) {
        return ((!z ? 1 : 0) * 2) + (!z2 ? 1 : 0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
    }

    public static ResolveCache getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        ProgressIndicatorProvider.checkCanceled();
        return (ResolveCache) project.getService(ResolveCache.class);
    }

    public ResolveCache(Project project) {
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        this.myPhysicalMaps = new AtomicReferenceArray<>(4);
        this.myNonPhysicalMaps = new AtomicReferenceArray<>(4);
        clearCacheOnPsiChange(project.getMessageBus());
        LowMemoryWatcher.register(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6660x39c31b56();
            }
        }, this);
    }

    private void clearCacheOnPsiChange(MessageBus messageBus) {
        if (messageBus == null) {
            $$$reportNull$$$0(2);
        }
        messageBus.connect().subscribe(PsiManagerImpl.ANY_PSI_CHANGE_TOPIC, new AnyPsiChangeListener() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.AnyPsiChangeListener
            public void beforePsiChanged(boolean z) {
                ResolveCache.this.clearCache(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onLowMemory, reason: merged with bridge method [inline-methods] */
    public void m6660x39c31b56() {
        clearArray(this.myPhysicalMaps);
        clearArray(this.myNonPhysicalMaps);
    }

    private static <K, V> Map<K, V> createWeakMap() {
        return new ConcurrentWeakKeySoftValueHashMap<K, V>(100, 0.75f, Runtime.getRuntime().availableProcessors()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 2 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "queue";
                } else if (i == 2) {
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveCache$2";
                } else if (i != 3) {
                    objArr[0] = "value";
                } else {
                    objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                }
                if (i != 2) {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveCache$2";
                } else {
                    objArr[1] = "createValueReference";
                }
                if (i != 2) {
                    if (i != 3) {
                        objArr[2] = "createValueReference";
                    } else {
                        objArr[2] = "get";
                    }
                }
                String str2 = String.format(str, objArr);
                if (i == 2) {
                    throw new IllegalStateException(str2);
                }
            }

            @Override // java.util.Map
            public boolean equals(Object obj) {
                return this == obj;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap
            protected ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> createValueReference(V v, ReferenceQueue<? super V> referenceQueue) {
                ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> valueReferenceCreateStrongReference;
                if (v == 0) {
                    $$$reportNull$$$0(0);
                }
                if (referenceQueue == null) {
                    $$$reportNull$$$0(1);
                }
                if (v == ResolveCache.NULL_RESULT || ((v instanceof Object[]) && ((Object[]) v).length == 0)) {
                    valueReferenceCreateStrongReference = ResolveCache.createStrongReference(v);
                } else {
                    valueReferenceCreateStrongReference = super.createValueReference(v, referenceQueue);
                }
                if (valueReferenceCreateStrongReference == null) {
                    $$$reportNull$$$0(2);
                }
                return valueReferenceCreateStrongReference;
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap, java.util.Map
            public V get(Object obj) {
                if (obj == null) {
                    $$$reportNull$$$0(3);
                }
                V v = (V) super.get(obj);
                if (v == ResolveCache.NULL_RESULT) {
                    return null;
                }
                return v;
            }

            @Override // java.util.Map
            public int hashCode() {
                return System.identityHashCode(this);
            }
        };
    }

    public void clearCache(boolean z) {
        if (z) {
            clearArray(this.myPhysicalMaps);
        }
        clearArray(this.myNonPhysicalMaps);
    }

    private static void clearArray(AtomicReferenceArray<?> atomicReferenceArray) {
        if (atomicReferenceArray == null) {
            $$$reportNull$$$0(3);
        }
        for (int i = 0; i < atomicReferenceArray.length(); i++) {
            atomicReferenceArray.set(i, null);
        }
    }

    private <TRef extends PsiReference, TResult> TResult resolve(final TRef tref, final AbstractResolver<? super TRef, TResult> abstractResolver, boolean z, final boolean z2, boolean z3, boolean z4) {
        if (tref == null) {
            $$$reportNull$$$0(4);
        }
        if (abstractResolver == null) {
            $$$reportNull$$$0(5);
        }
        ProgressIndicatorProvider.checkCanceled();
        if (z4) {
            ApplicationManager.getApplication().assertReadAccessAllowed();
        }
        return (TResult) resolve(tref, getMap(z4, getIndex(z2, z3)), z, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return abstractResolver.resolve(tref, z2);
            }
        });
    }

    public <T extends PsiPolyVariantReference> ResolveResult[] resolveWithCaching(T t, PolyVariantResolver<T> polyVariantResolver, boolean z, boolean z2) {
        if (t == null) {
            $$$reportNull$$$0(6);
        }
        if (polyVariantResolver == null) {
            $$$reportNull$$$0(7);
        }
        ResolveResult[] resolveResultArrResolveWithCaching = resolveWithCaching(t, polyVariantResolver, z, z2, t.getElement().getContainingFile());
        if (resolveResultArrResolveWithCaching == null) {
            $$$reportNull$$$0(8);
        }
        return resolveResultArrResolveWithCaching;
    }

    public <T extends PsiPolyVariantReference> ResolveResult[] resolveWithCaching(T t, PolyVariantResolver<T> polyVariantResolver, boolean z, boolean z2, PsiFile psiFile) {
        if (t == null) {
            $$$reportNull$$$0(9);
        }
        if (polyVariantResolver == null) {
            $$$reportNull$$$0(10);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(11);
        }
        ResolveResult[] resolveResultArr = (ResolveResult[]) resolve(t, polyVariantResolver, z, z2, true, psiFile.isPhysical());
        if (resolveResultArr == null) {
            resolveResultArr = ResolveResult.EMPTY_ARRAY;
        }
        if (resolveResultArr == null) {
            $$$reportNull$$$0(12);
        }
        return resolveResultArr;
    }

    public <T extends PsiPolyVariantReference> ResolveResult[] resolveWithCaching(final T t, final PolyVariantContextResolver<T> polyVariantContextResolver, boolean z, final boolean z2, final PsiFile psiFile) {
        if (t == null) {
            $$$reportNull$$$0(13);
        }
        if (polyVariantContextResolver == null) {
            $$$reportNull$$$0(14);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(15);
        }
        ProgressIndicatorProvider.checkCanceled();
        ApplicationManager.getApplication().assertReadAccessAllowed();
        ResolveResult[] resolveResultArr = (ResolveResult[]) resolve(t, getMap(psiFile.isPhysical(), getIndex(z2, true)), z, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return polyVariantContextResolver.resolve(t, psiFile, z2);
            }
        });
        if (resolveResultArr == null) {
            resolveResultArr = ResolveResult.EMPTY_ARRAY;
        }
        if (resolveResultArr == null) {
            $$$reportNull$$$0(16);
        }
        return resolveResultArr;
    }

    public <R extends PsiSymbolReference> Collection<? extends Symbol> resolveWithCaching(R r, PsiSymbolReferenceResolver<? super R> psiSymbolReferenceResolver) {
        if (psiSymbolReferenceResolver == null) {
            $$$reportNull$$$0(17);
        }
        Collection<? extends Symbol> collectionResolveWithCaching = resolveWithCaching(r, true, psiSymbolReferenceResolver);
        if (collectionResolveWithCaching == null) {
            $$$reportNull$$$0(18);
        }
        return collectionResolveWithCaching;
    }

    public <R extends PsiSymbolReference> Collection<? extends Symbol> resolveWithCaching(final R r, boolean z, final PsiSymbolReferenceResolver<? super R> psiSymbolReferenceResolver) {
        if (psiSymbolReferenceResolver == null) {
            $$$reportNull$$$0(19);
        }
        ProgressIndicatorProvider.checkCanceled();
        ApplicationManager.getApplication().assertReadAccessAllowed();
        Collection<? extends Symbol> collectionEmptyList = (Collection) resolve(r, getMap(r.getElement().isPhysical(), getIndex(false, true)), z, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return psiSymbolReferenceResolver.resolve(r);
            }
        });
        if (collectionEmptyList == null) {
            collectionEmptyList = Collections.emptyList();
        }
        if (collectionEmptyList == null) {
            $$$reportNull$$$0(20);
        }
        return collectionEmptyList;
    }

    private static <TRef, TResult> TResult resolve(TRef tref, Map<TRef, TResult> map, boolean z, Computable<? extends TResult> computable) {
        PsiElement psiElement;
        if (tref == null) {
            $$$reportNull$$$0(21);
        }
        if (map == null) {
            $$$reportNull$$$0(22);
        }
        if (computable == null) {
            $$$reportNull$$$0(23);
        }
        TResult tresult = map.get(tref);
        if (tresult != null) {
            if (IdempotenceChecker.areRandomChecksEnabled()) {
                IdempotenceChecker.applyForRandomCheck(tresult, tref, loggingResolver(tref, computable));
            }
            return tresult;
        }
        RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
        Computable computableLoggingResolver = loggingResolver(tref, computable);
        if (z) {
            psiElement = (TResult) RecursionManager.doPreventingRecursion(Pair.create(tref, map), true, computableLoggingResolver);
        } else {
            psiElement = (TResult) computableLoggingResolver.get();
        }
        if (psiElement instanceof ResolveResult) {
            ensureValidPsi(psiElement);
        } else if (psiElement instanceof ResolveResult[]) {
            ensureValidResults(psiElement);
        } else if (psiElement instanceof PsiElement) {
            PsiUtilCore.ensureValid(psiElement);
        }
        if (stackStampMarkStack.mayCacheNow()) {
            cache(tref, map, psiElement, computableLoggingResolver);
        }
        return (TResult) psiElement;
    }

    private static <R> Computable<R> loggingResolver(final Object obj, final Computable<? extends R> computable) {
        if (obj == null) {
            $$$reportNull$$$0(24);
        }
        if (computable == null) {
            $$$reportNull$$$0(25);
        }
        return new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return ResolveCache.lambda$loggingResolver$4(obj, computable);
            }
        };
    }

    static /* synthetic */ Object lambda$loggingResolver$4(Object obj, Computable computable) {
        if (IdempotenceChecker.isLoggingEnabled()) {
            IdempotenceChecker.logTrace("Resolving " + obj + " of " + obj.getClass());
        }
        return computable.get();
    }

    private static void ensureValidResults(ResolveResult[] resolveResultArr) {
        if (resolveResultArr == null) {
            $$$reportNull$$$0(27);
        }
        for (ResolveResult resolveResult : resolveResultArr) {
            ensureValidPsi(resolveResult);
        }
    }

    private static void ensureValidPsi(ResolveResult resolveResult) {
        if (resolveResult == null) {
            $$$reportNull$$$0(28);
        }
        PsiElement element = resolveResult.getElement();
        if (element != null) {
            PsiUtilCore.ensureValid(element);
        }
    }

    public <T extends PsiPolyVariantReference> ResolveResult[] getCachedResults(T t, boolean z, boolean z2, boolean z3) {
        if (t == null) {
            $$$reportNull$$$0(29);
        }
        return (ResolveResult[]) getMap(z, getIndex(z2, z3)).get(t);
    }

    public <TRef extends PsiReference, TResult> TResult resolveWithCaching(TRef tref, AbstractResolver<TRef, TResult> abstractResolver, boolean z, boolean z2) {
        if (tref == null) {
            $$$reportNull$$$0(30);
        }
        if (abstractResolver == null) {
            $$$reportNull$$$0(31);
        }
        return (TResult) resolve(tref, abstractResolver, z, z2, false, tref.getElement().isPhysical());
    }

    private <TRef, TResult> Map<TRef, TResult> getMap(boolean z, int i) {
        AtomicReferenceArray<Map<?, ?>> atomicReferenceArray = z ? this.myPhysicalMaps : this.myNonPhysicalMaps;
        Map<TRef, TResult> mapCreateWeakMap = (Map) atomicReferenceArray.get(i);
        while (mapCreateWeakMap == null) {
            mapCreateWeakMap = createWeakMap();
            if (!Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceArray, i, null, mapCreateWeakMap)) {
                mapCreateWeakMap = (Map) atomicReferenceArray.get(i);
            }
        }
        if (mapCreateWeakMap == null) {
            $$$reportNull$$$0(32);
        }
        return mapCreateWeakMap;
    }

    static {
        Object objSentinel = ObjectUtils.sentinel("ResolveCache.NULL_RESULT");
        NULL_RESULT = objSentinel;
        NULL_VALUE_REFERENCE = new StrongValueReference<>(objSentinel);
        EMPTY_RESOLVE_RESULT = new StrongValueReference<>(ResolveResult.EMPTY_ARRAY);
    }

    private static <TRef, TResult> void cache(TRef tref, Map<? super TRef, TResult> map, TResult tresult, Computable<TResult> computable) {
        if (tref == null) {
            $$$reportNull$$$0(33);
        }
        if (map == null) {
            $$$reportNull$$$0(34);
        }
        if (computable == null) {
            $$$reportNull$$$0(35);
        }
        TResult tresult2 = map.get(tref);
        if (tresult2 != null) {
            if (tresult2 == tresult) {
                return;
            } else {
                IdempotenceChecker.checkEquivalence(tresult2, tresult, tref.getClass(), computable);
            }
        }
        if (tresult == null) {
            tresult = (TResult) NULL_RESULT;
        }
        map.put(tref, tresult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> StrongValueReference<K, V> createStrongReference(V v) {
        if (v == null) {
            $$$reportNull$$$0(36);
        }
        if (v == NULL_RESULT) {
            return (StrongValueReference<K, V>) NULL_VALUE_REFERENCE;
        }
        return v == ResolveResult.EMPTY_ARRAY ? (StrongValueReference<K, V>) EMPTY_RESOLVE_RESULT : new StrongValueReference<>(v);
    }

    private static class StrongValueReference<K, V> implements ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> {
        private final V myValue;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveCache$StrongValueReference", "<init>"));
        }

        StrongValueReference(V v) {
            if (v == null) {
                $$$reportNull$$$0(0);
            }
            this.myValue = v;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap.ValueReference
        public ConcurrentWeakKeySoftValueHashMap.KeyReference<K, V> getKeyReference() {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap.ValueReference, java.util.function.Supplier
        public V get() {
            return this.myValue;
        }
    }
}
