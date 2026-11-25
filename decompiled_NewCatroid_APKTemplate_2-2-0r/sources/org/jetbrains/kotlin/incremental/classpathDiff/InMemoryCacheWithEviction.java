package org.jetbrains.kotlin.incremental.classpathDiff;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: InMemoryCacheWithEviction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003:\u0001#B-\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n¢\u0006\u0002\u0010\u000bJ'\u0010\u0013\u001a\u00028\u00012\u0006\u0010\u0014\u001a\u00028\u00002\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0016¢\u0006\u0002\u0010\u0017J\u0006\u0010\u0018\u001a\u00020\u0019J\u0017\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00028\u0000H\u0001¢\u0006\u0004\b\u001c\u0010\u001dJ\u0006\u0010\u001e\u001a\u00020\u0019J\"\u0010\u001f\u001a\u0002H\u0002\"\u0004\b\u0002\u0010\u00022\f\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00020\nH\u0082\b¢\u0006\u0002\u0010!J\u0017\u0010\"\u001a\u00020\u00192\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00190\nH\u0082\bR \u0010\f\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/InMemoryCacheWithEviction;", "KEY", "VALUE", "", "maxTimePeriodsToKeepStrongReferences", "", "maxTimePeriodsToKeepSoftReferences", "maxMemoryUsageRatioToKeepStrongReferences", "", "memoryUsageRatio", "Lkotlin/Function0;", "(IIDLkotlin/jvm/functions/Function0;)V", ResponseCacheMiddleware.CACHE, "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/CacheEntryValue;", "currentTimePeriod", "Ljava/util/concurrent/atomic/AtomicInteger;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "computeIfAbsent", ASTExpr.DEFAULT_MAP_KEY_NAME, "valueProvider", "Lkotlin/Function1;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "evictEntries", "", "getEntryState", "Lorg/jetbrains/kotlin/incremental/classpathDiff/InMemoryCacheWithEviction$EntryState;", "getEntryState$incremental_compilation_impl", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/incremental/classpathDiff/InMemoryCacheWithEviction$EntryState;", "newTimePeriod", "readLock", "action", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "writeLock", "EntryState", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InMemoryCacheWithEviction<KEY, VALUE> {
    private final ConcurrentHashMap<KEY, CacheEntryValue<VALUE>> cache;
    private final AtomicInteger currentTimePeriod;
    private final ReentrantReadWriteLock lock;
    private final double maxMemoryUsageRatioToKeepStrongReferences;
    private final int maxTimePeriodsToKeepSoftReferences;
    private final int maxTimePeriodsToKeepStrongReferences;
    private final Function0<Double> memoryUsageRatio;

    /* JADX INFO: compiled from: InMemoryCacheWithEviction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0081\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/InMemoryCacheWithEviction$EntryState;", "", "(Ljava/lang/String;I)V", "STRONG_REF", "SOFT_REF", "ABSENT", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum EntryState {
        STRONG_REF,
        SOFT_REF,
        ABSENT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<EntryState> getEntries() {
            return $ENTRIES;
        }
    }

    public InMemoryCacheWithEviction(int i, int i2, double d, Function0<Double> memoryUsageRatio) {
        Intrinsics.checkNotNullParameter(memoryUsageRatio, "memoryUsageRatio");
        this.maxTimePeriodsToKeepStrongReferences = i;
        this.maxTimePeriodsToKeepSoftReferences = i2;
        this.maxMemoryUsageRatioToKeepStrongReferences = d;
        this.memoryUsageRatio = memoryUsageRatio;
        this.currentTimePeriod = new AtomicInteger(0);
        this.cache = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public /* synthetic */ InMemoryCacheWithEviction(int i, int i2, double d, AnonymousClass1 anonymousClass1, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, d, (i3 & 8) != 0 ? new Function0<Double>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.InMemoryCacheWithEviction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Double invoke() {
                Runtime runtime = Runtime.getRuntime();
                return Double.valueOf(1.0d - (runtime.freeMemory() / runtime.totalMemory()));
            }
        } : anonymousClass1);
    }

    public final void newTimePeriod() {
        this.currentTimePeriod.incrementAndGet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CacheEntryValue computeIfAbsent$lambda$2$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (CacheEntryValue) tmp0.invoke(obj);
    }

    public final VALUE computeIfAbsent(final KEY key, final Function1<? super KEY, ? extends VALUE> valueProvider) {
        VALUE valueInvoke;
        Intrinsics.checkNotNullParameter(valueProvider, "valueProvider");
        this.lock.readLock().lock();
        try {
            ConcurrentHashMap<KEY, CacheEntryValue<VALUE>> concurrentHashMap = this.cache;
            final Function1<KEY, CacheEntryValue<VALUE>> function1 = new Function1<KEY, CacheEntryValue<VALUE>>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.InMemoryCacheWithEviction$computeIfAbsent$1$cacheEntryValue$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CacheEntryValue<VALUE> invoke(KEY key2) {
                    return new CacheEntryValue<>(valueProvider.invoke(key), ((InMemoryCacheWithEviction) this).currentTimePeriod.get());
                }
            };
            CacheEntryValue<VALUE> cacheEntryValueComputeIfAbsent = concurrentHashMap.computeIfAbsent(key, new Function() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.InMemoryCacheWithEviction$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return InMemoryCacheWithEviction.computeIfAbsent$lambda$2$lambda$0(function1, obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(cacheEntryValueComputeIfAbsent, "computeIfAbsent(...)");
            CacheEntryValue<VALUE> cacheEntryValue = cacheEntryValueComputeIfAbsent;
            synchronized (cacheEntryValue) {
                valueInvoke = cacheEntryValue.get();
                if (valueInvoke == null) {
                    valueInvoke = valueProvider.invoke(key);
                }
                cacheEntryValue.setStrongReference(valueInvoke, this.currentTimePeriod.get());
            }
            this.lock.readLock().unlock();
            return valueInvoke;
        } catch (Throwable th) {
            this.lock.readLock().unlock();
            throw th;
        }
    }

    public final void evictEntries() {
        this.lock.writeLock().lock();
        try {
            int i = (this.currentTimePeriod.get() - this.maxTimePeriodsToKeepStrongReferences) + 1;
            int i2 = i - this.maxTimePeriodsToKeepSoftReferences;
            if (this.memoryUsageRatio.invoke().doubleValue() > this.maxMemoryUsageRatioToKeepStrongReferences) {
                Collection<CacheEntryValue<VALUE>> collectionValues = this.cache.values();
                Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
                Iterator<T> it = collectionValues.iterator();
                while (it.hasNext()) {
                    ((CacheEntryValue) it.next()).updateToSoftReference();
                }
            } else {
                ConcurrentHashMap<KEY, CacheEntryValue<VALUE>> concurrentHashMap = this.cache;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry<KEY, CacheEntryValue<VALUE>> entry : concurrentHashMap.entrySet()) {
                    if (entry.getValue().getLastUsed() < i) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                Iterator it2 = linkedHashMap.values().iterator();
                while (it2.hasNext()) {
                    ((CacheEntryValue) it2.next()).updateToSoftReference();
                }
            }
            ConcurrentHashMap<KEY, CacheEntryValue<VALUE>> concurrentHashMap2 = this.cache;
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (Map.Entry<KEY, CacheEntryValue<VALUE>> entry2 : concurrentHashMap2.entrySet()) {
                CacheEntryValue<VALUE> value = entry2.getValue();
                if (value.getLastUsed() < i2 || value.valueWasGarbageCollected()) {
                    linkedHashMap2.put(entry2.getKey(), entry2.getValue());
                }
            }
            Iterator it3 = linkedHashMap2.keySet().iterator();
            while (it3.hasNext()) {
                this.cache.remove(it3.next());
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public final EntryState getEntryState$incremental_compilation_impl(KEY key) {
        EntryState entryState;
        this.lock.readLock().lock();
        try {
            CacheEntryValue<VALUE> cacheEntryValue = this.cache.get(key);
            if (cacheEntryValue != null) {
                Intrinsics.checkNotNull(cacheEntryValue);
                synchronized (cacheEntryValue) {
                    entryState = cacheEntryValue.valueIsAStrongReference() ? EntryState.STRONG_REF : EntryState.SOFT_REF;
                }
                if (entryState == null) {
                    entryState = EntryState.ABSENT;
                }
            } else {
                entryState = EntryState.ABSENT;
            }
            this.lock.readLock().unlock();
            return entryState;
        } catch (Throwable th) {
            this.lock.readLock().unlock();
            throw th;
        }
    }
}
