package org.jetbrains.kotlin.storage;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;

/* JADX INFO: compiled from: ObservableStorageManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J!\u0010\r\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005H\u0016¢\u0006\u0002\u0010\u000fJ$\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\u0011\"\u0004\b\u0000\u0010\n\"\b\b\u0001\u0010\u000b*\u00020\u0012H\u0016J$\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\u0014\"\u0004\b\u0000\u0010\n\"\b\b\u0001\u0010\u000b*\u00020\u0012H\u0016J&\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0016\"\b\b\u0000\u0010\u0006*\u00020\u00122\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005H\u0016J:\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0016\"\b\b\u0000\u0010\u0006*\u00020\u00122\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00060\u00052\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u0002H\u00060\tH\u0016JP\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0016\"\b\b\u0000\u0010\u0006*\u00020\u00122\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00060\u00052\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u0002H\u0006\u0018\u00010\t2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u00020\u001b0\tH\u0016J8\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\u001d\"\u0004\b\u0000\u0010\n\"\b\b\u0001\u0010\u000b*\u00020\u00122\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\tH\u0016JR\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\u001d\"\u0004\b\u0000\u0010\n\"\b\b\u0001\u0010\u000b*\u00020\u00122\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\t2\u0018\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u0002H\u000b0\u001eH\u0016Jf\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\u001d\"\u0004\b\u0000\u0010\n\"\b\b\u0001\u0010\u000b*\u00020\u00122\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\t2\u0018\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u0002H\u000b0\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u00120 H\u0016JL\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\u001d\"\u0004\b\u0000\u0010\n\"\b\b\u0001\u0010\u000b*\u00020\u00122\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\t2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u00120 H\u0016JL\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\"\"\u0004\b\u0000\u0010\n\"\b\b\u0001\u0010\u000b*\u00020\u00122\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\t2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u00120 H\u0016J:\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\"\"\u0004\b\u0000\u0010\n\"\b\b\u0001\u0010\u000b*\u00020\u00122\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u0002H\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000b0\tH\u0016J(\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u00060$\"\b\b\u0000\u0010\u0006*\u00020\u00122\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00060\u0005H\u0016J>\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u00060$\"\b\b\u0000\u0010\u0006*\u00020\u00122\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00060\u00052\u0014\u0010\u001a\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u0001H\u0006\u0012\u0004\u0012\u00020\u001b0\tH\u0016J3\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0016\"\b\b\u0000\u0010\u0006*\u00020\u00122\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00060\u00052\u0006\u0010\u0017\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010'J7\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u00060$\"\b\b\u0000\u0010\u0006*\u00020\u00122\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00060\u00052\b\u0010\u0017\u001a\u0004\u0018\u0001H\u0006H\u0016¢\u0006\u0002\u0010)R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u0005X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR:\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\t\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b*\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\tX¤\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\f¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/storage/ObservableStorageManager;", "Lorg/jetbrains/kotlin/storage/StorageManager;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/storage/StorageManager;)V", "observable", "Lkotlin/Function0;", ExifInterface.GPS_DIRECTION_TRUE, "getObservable", "(Lkotlin/jvm/functions/Function0;)Lkotlin/jvm/functions/Function0;", "Lkotlin/Function1;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "(Lkotlin/jvm/functions/Function1;)Lkotlin/jvm/functions/Function1;", "compute", "computable", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "createCacheWithNotNullValues", "Lorg/jetbrains/kotlin/storage/CacheWithNotNullValues;", "", "createCacheWithNullableValues", "Lorg/jetbrains/kotlin/storage/CacheWithNullableValues;", "createLazyValue", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "onRecursiveCall", "", "createLazyValueWithPostCompute", "postCompute", "", "createMemoizedFunction", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lkotlin/Function2;", "map", "Ljava/util/concurrent/ConcurrentMap;", "createMemoizedFunctionWithNullableValues", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "createNullableLazyValue", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "createNullableLazyValueWithPostCompute", "createRecursionTolerantLazyValue", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "createRecursionTolerantNullableLazyValue", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ObservableStorageManager implements StorageManager {
    private final StorageManager delegate;

    protected abstract <T> Function0<T> getObservable(Function0<? extends T> function0);

    protected abstract <K, V> Function1<K, V> getObservable(Function1<? super K, ? extends V> function1);

    public ObservableStorageManager(StorageManager delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        return this.delegate.createMemoizedFunction(getObservable(compute));
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute, Function2<? super K, ? super Boolean, ? extends V> onRecursiveCall) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        Intrinsics.checkNotNullParameter(onRecursiveCall, "onRecursiveCall");
        return this.delegate.createMemoizedFunction(getObservable(compute), onRecursiveCall);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(Function1<? super K, ? extends V> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        return this.delegate.createMemoizedFunctionWithNullableValues(getObservable(compute));
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute, ConcurrentMap<K, Object> map) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        Intrinsics.checkNotNullParameter(map, "map");
        return this.delegate.createMemoizedFunction(getObservable(compute), map);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute, Function2<? super K, ? super Boolean, ? extends V> onRecursiveCall, ConcurrentMap<K, Object> map) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        Intrinsics.checkNotNullParameter(onRecursiveCall, "onRecursiveCall");
        Intrinsics.checkNotNullParameter(map, "map");
        return this.delegate.createMemoizedFunction(getObservable(compute), onRecursiveCall, map);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(Function1<? super K, ? extends V> compute, ConcurrentMap<K, Object> map) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        Intrinsics.checkNotNullParameter(map, "map");
        return this.delegate.createMemoizedFunctionWithNullableValues(getObservable(compute), map);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> CacheWithNullableValues<K, V> createCacheWithNullableValues() {
        return this.delegate.createCacheWithNullableValues();
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> CacheWithNotNullValues<K, V> createCacheWithNotNullValues() {
        return this.delegate.createCacheWithNotNullValues();
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NotNullLazyValue<T> createLazyValue(Function0<? extends T> computable) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        return this.delegate.createLazyValue(getObservable(computable));
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NotNullLazyValue<T> createLazyValue(Function0<? extends T> computable, Function1<? super Boolean, ? extends T> onRecursiveCall) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        Intrinsics.checkNotNullParameter(onRecursiveCall, "onRecursiveCall");
        return this.delegate.createLazyValue(getObservable(computable), onRecursiveCall);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NotNullLazyValue<T> createRecursionTolerantLazyValue(Function0<? extends T> computable, T onRecursiveCall) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        Intrinsics.checkNotNullParameter(onRecursiveCall, "onRecursiveCall");
        return this.delegate.createRecursionTolerantLazyValue(getObservable(computable), onRecursiveCall);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NotNullLazyValue<T> createLazyValueWithPostCompute(Function0<? extends T> computable, Function1<? super Boolean, ? extends T> onRecursiveCall, Function1<? super T, Unit> postCompute) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        Intrinsics.checkNotNullParameter(postCompute, "postCompute");
        return this.delegate.createLazyValueWithPostCompute(getObservable(computable), onRecursiveCall, postCompute);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NullableLazyValue<T> createNullableLazyValue(Function0<? extends T> computable) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        return this.delegate.createNullableLazyValue(getObservable(computable));
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NullableLazyValue<T> createRecursionTolerantNullableLazyValue(Function0<? extends T> computable, T onRecursiveCall) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        return this.delegate.createRecursionTolerantNullableLazyValue(getObservable(computable), onRecursiveCall);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NullableLazyValue<T> createNullableLazyValueWithPostCompute(Function0<? extends T> computable, Function1<? super T, Unit> postCompute) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        Intrinsics.checkNotNullParameter(postCompute, "postCompute");
        return this.delegate.createNullableLazyValueWithPostCompute(getObservable(computable), postCompute);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> T compute(Function0<? extends T> computable) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        return (T) this.delegate.compute(getObservable(computable));
    }
}
