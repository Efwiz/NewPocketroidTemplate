package org.jetbrains.kotlin.storage;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: LockBasedLazyResolveStorageManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002:\u0002,-B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0096\u0001¢\u0006\u0002\u0010\tJ%\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u000b\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000eH\u0096\u0001J%\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u0010\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000eH\u0096\u0001J'\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0012\"\b\b\u0000\u0010\u0006*\u00020\u000e2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0096\u0001J;\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0012\"\b\b\u0000\u0010\u0006*\u00020\u000e2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\b2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\u00060\u0014H\u0096\u0001JQ\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0012\"\b\b\u0000\u0010\u0006*\u00020\u000e2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\b2\u0014\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\u0006\u0018\u00010\u00142\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u00020\u00180\u0014H\u0096\u0001J9\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u001a\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000e2\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u0014H\u0096\u0001JS\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u001a\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000e2\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u00142\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\r0\u001bH\u0096\u0001Jg\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u001a\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000e2\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u00142\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\r0\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u000e0\u001dH\u0096\u0001JM\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u001a\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000e2\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u00142\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u000e0\u001dH\u0096\u0001JM\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u001f\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000e2\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u00142\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u000e0\u001dH\u0096\u0001J;\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u001f\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000e2\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u0002H\f\u0012\u0006\u0012\u0004\u0018\u0001H\r0\u0014H\u0096\u0001J)\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00060!\"\b\b\u0000\u0010\u0006*\u00020\u000e2\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00060\bH\u0096\u0001J?\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00060!\"\b\b\u0000\u0010\u0006*\u00020\u000e2\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00060\b2\u0014\u0010\u0017\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u0001H\u0006\u0012\u0004\u0012\u00020\u00180\u0014H\u0096\u0001J4\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0012\"\b\b\u0000\u0010\u0006*\u00020\u000e2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\u0013\u001a\u0002H\u0006H\u0096\u0001¢\u0006\u0002\u0010$J8\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u00060!\"\b\b\u0000\u0010\u0006*\u00020\u000e2\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00060\b2\b\u0010\u0013\u001a\u0004\u0018\u0001H\u0006H\u0096\u0001¢\u0006\u0002\u0010&J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(H\u0016J8\u0010*\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u001a\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000e2\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u0014H\u0016J8\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u001f\"\u0004\b\u0000\u0010\f\"\b\b\u0001\u0010\r*\u00020\u000e2\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/storage/LockBasedLazyResolveStorageManager;", "Lorg/jetbrains/kotlin/storage/StorageManager;", "Lorg/jetbrains/kotlin/storage/LazyResolveStorageManager;", "storageManager", "(Lorg/jetbrains/kotlin/storage/StorageManager;)V", "compute", ExifInterface.GPS_DIRECTION_TRUE, "computable", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "createCacheWithNotNullValues", "Lorg/jetbrains/kotlin/storage/CacheWithNotNullValues;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "createCacheWithNullableValues", "Lorg/jetbrains/kotlin/storage/CacheWithNullableValues;", "createLazyValue", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "onRecursiveCall", "Lkotlin/Function1;", "", "createLazyValueWithPostCompute", "postCompute", "", "createMemoizedFunction", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lkotlin/Function2;", "map", "Ljava/util/concurrent/ConcurrentMap;", "createMemoizedFunctionWithNullableValues", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "createNullableLazyValue", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "createNullableLazyValueWithPostCompute", "createRecursionTolerantLazyValue", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "createRecursionTolerantNullableLazyValue", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "createSafeTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "originalTrace", "createSoftlyRetainedMemoizedFunction", "createSoftlyRetainedMemoizedFunctionWithNullableValues", "LockProtectedContext", "LockProtectedTrace", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LockBasedLazyResolveStorageManager implements LazyResolveStorageManager, StorageManager {
    private final StorageManager storageManager;

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> T compute(Function0<? extends T> computable) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        return (T) this.storageManager.compute(computable);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> CacheWithNotNullValues<K, V> createCacheWithNotNullValues() {
        return this.storageManager.createCacheWithNotNullValues();
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> CacheWithNullableValues<K, V> createCacheWithNullableValues() {
        return this.storageManager.createCacheWithNullableValues();
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NotNullLazyValue<T> createLazyValue(Function0<? extends T> computable) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        return this.storageManager.createLazyValue(computable);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NotNullLazyValue<T> createLazyValue(Function0<? extends T> computable, Function1<? super Boolean, ? extends T> onRecursiveCall) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        Intrinsics.checkNotNullParameter(onRecursiveCall, "onRecursiveCall");
        return this.storageManager.createLazyValue(computable, onRecursiveCall);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NotNullLazyValue<T> createLazyValueWithPostCompute(Function0<? extends T> computable, Function1<? super Boolean, ? extends T> onRecursiveCall, Function1<? super T, Unit> postCompute) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        Intrinsics.checkNotNullParameter(postCompute, "postCompute");
        return this.storageManager.createLazyValueWithPostCompute(computable, onRecursiveCall, postCompute);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        return this.storageManager.createMemoizedFunction(compute);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute, ConcurrentMap<K, Object> map) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        Intrinsics.checkNotNullParameter(map, "map");
        return this.storageManager.createMemoizedFunction(compute, map);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute, Function2<? super K, ? super Boolean, ? extends V> onRecursiveCall) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        Intrinsics.checkNotNullParameter(onRecursiveCall, "onRecursiveCall");
        return this.storageManager.createMemoizedFunction(compute, onRecursiveCall);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute, Function2<? super K, ? super Boolean, ? extends V> onRecursiveCall, ConcurrentMap<K, Object> map) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        Intrinsics.checkNotNullParameter(onRecursiveCall, "onRecursiveCall");
        Intrinsics.checkNotNullParameter(map, "map");
        return this.storageManager.createMemoizedFunction(compute, onRecursiveCall, map);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(Function1<? super K, ? extends V> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        return this.storageManager.createMemoizedFunctionWithNullableValues(compute);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(Function1<? super K, ? extends V> compute, ConcurrentMap<K, Object> map) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        Intrinsics.checkNotNullParameter(map, "map");
        return this.storageManager.createMemoizedFunctionWithNullableValues(compute, map);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NullableLazyValue<T> createNullableLazyValue(Function0<? extends T> computable) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        return this.storageManager.createNullableLazyValue(computable);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NullableLazyValue<T> createNullableLazyValueWithPostCompute(Function0<? extends T> computable, Function1<? super T, Unit> postCompute) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        Intrinsics.checkNotNullParameter(postCompute, "postCompute");
        return this.storageManager.createNullableLazyValueWithPostCompute(computable, postCompute);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NotNullLazyValue<T> createRecursionTolerantLazyValue(Function0<? extends T> computable, T onRecursiveCall) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        Intrinsics.checkNotNullParameter(onRecursiveCall, "onRecursiveCall");
        return this.storageManager.createRecursionTolerantLazyValue(computable, onRecursiveCall);
    }

    @Override // org.jetbrains.kotlin.storage.StorageManager
    public <T> NullableLazyValue<T> createRecursionTolerantNullableLazyValue(Function0<? extends T> computable, T onRecursiveCall) {
        Intrinsics.checkNotNullParameter(computable, "computable");
        return this.storageManager.createRecursionTolerantNullableLazyValue(computable, onRecursiveCall);
    }

    public LockBasedLazyResolveStorageManager(StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.storageManager = storageManager;
    }

    @Override // org.jetbrains.kotlin.storage.LazyResolveStorageManager
    public <K, V> MemoizedFunctionToNotNull<K, V> createSoftlyRetainedMemoizedFunction(Function1<? super K, ? extends V> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        StorageManager storageManager = this.storageManager;
        ConcurrentMap<K, Object> concurrentMapCreateConcurrentSoftValueMap = ContainerUtil.createConcurrentSoftValueMap();
        Intrinsics.checkNotNullExpressionValue(concurrentMapCreateConcurrentSoftValueMap, "createConcurrentSoftValueMap(...)");
        return storageManager.createMemoizedFunction(compute, concurrentMapCreateConcurrentSoftValueMap);
    }

    @Override // org.jetbrains.kotlin.storage.LazyResolveStorageManager
    public <K, V> MemoizedFunctionToNullable<K, V> createSoftlyRetainedMemoizedFunctionWithNullableValues(Function1<? super K, ? extends V> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        StorageManager storageManager = this.storageManager;
        ConcurrentMap<K, Object> concurrentMapCreateConcurrentSoftValueMap = ContainerUtil.createConcurrentSoftValueMap();
        Intrinsics.checkNotNullExpressionValue(concurrentMapCreateConcurrentSoftValueMap, "createConcurrentSoftValueMap(...)");
        return storageManager.createMemoizedFunctionWithNullableValues(compute, concurrentMapCreateConcurrentSoftValueMap);
    }

    @Override // org.jetbrains.kotlin.storage.LazyResolveStorageManager
    public BindingTrace createSafeTrace(BindingTrace originalTrace) {
        Intrinsics.checkNotNullParameter(originalTrace, "originalTrace");
        return new LockProtectedTrace(this.storageManager, originalTrace);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LockBasedLazyResolveStorageManager.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J8\u0010\f\u001a\u0004\u0018\u0001H\r\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\r0\u00102\u0006\u0010\u0011\u001a\u0002H\u000eH\u0096\u0002¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\u0014H\u0016J6\u0010\u0015\u001a\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u0001H\u000eH\u000e0\u0016\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\r0\u0018H\u0016JD\u0010\u0019\u001a\u001e\u0012\f\u0012\n \u0017*\u0004\u0018\u0001H\u000eH\u000e\u0012\f\u0012\n \u0017*\u0004\u0018\u0001H\rH\r0\u001a\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\r0\u0010H\u0016J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/storage/LockBasedLazyResolveStorageManager$LockProtectedContext;", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "context", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "addOwnDataTo", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "commitDiagnostics", "", "get", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "slice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;Ljava/lang/Object;)Ljava/lang/Object;", "getDiagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "getKeys", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "getSliceContents", "Lorg/jetbrains/kotlin/com/google/common/collect/ImmutableMap;", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class LockProtectedContext implements BindingContext {
        private final BindingContext context;
        private final StorageManager storageManager;

        public LockProtectedContext(StorageManager storageManager, BindingContext context) {
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(context, "context");
            this.storageManager = storageManager;
            this.context = context;
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public KotlinType getType(final KtExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (KotlinType) this.storageManager.compute(new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedContext$getType$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final KotlinType invoke() {
                    return this.this$0.context.getType(expression);
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public Diagnostics getDiagnostics() {
            Object objCompute = this.storageManager.compute(new Function0<Diagnostics>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedContext$getDiagnostics$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Diagnostics invoke() {
                    return this.this$0.context.getDiagnostics();
                }
            });
            Intrinsics.checkNotNullExpressionValue(objCompute, "compute(...)");
            return (Diagnostics) objCompute;
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public <K, V> V get(final ReadOnlySlice<K, V> slice, final K key) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            return (V) this.storageManager.compute(new Function0<V>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedContext$get$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final V invoke() {
                    return (V) this.this$0.context.get(slice, key);
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public <K, V> Collection<K> getKeys(final WritableSlice<K, V> slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            Object objCompute = this.storageManager.compute(new Function0<Collection<? extends K>>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedContext$getKeys$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Collection<K> invoke() {
                    return this.this$0.context.getKeys(slice);
                }
            });
            Intrinsics.checkNotNullExpressionValue(objCompute, "compute(...)");
            return (Collection) objCompute;
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public void addOwnDataTo(final BindingTrace trace, final boolean commitDiagnostics) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.storageManager.compute(new Function0<Unit>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedContext$addOwnDataTo$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.context.addOwnDataTo(trace, commitDiagnostics);
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public <K, V> ImmutableMap<K, V> getSliceContents(final ReadOnlySlice<K, V> slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            Object objCompute = this.storageManager.compute(new Function0<ImmutableMap<K, V>>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedContext$getSliceContents$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ImmutableMap<K, V> invoke() {
                    return this.this$0.context.getSliceContents(slice);
                }
            });
            Intrinsics.checkNotNullExpressionValue(objCompute, "compute(...)");
            return (ImmutableMap) objCompute;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LockBasedLazyResolveStorageManager.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J8\u0010\b\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\t0\f2\u0006\u0010\r\u001a\u0002H\nH\u0096\u0002¢\u0006\u0002\u0010\u000eJ\b\u0010\u000f\u001a\u00020\u0007H\u0016J.\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\n0\u0011\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\t0\u0012H\u0016J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J=\u0010\u0017\u001a\u00020\u0018\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\t0\u00122\u0006\u0010\r\u001a\u0002H\n2\u0006\u0010\u0019\u001a\u0002H\tH\u0016¢\u0006\u0002\u0010\u001aJ/\u0010\u0017\u001a\u00020\u0018\"\u0004\b\u0000\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u001b0\u00122\u0006\u0010\r\u001a\u0002H\nH\u0016¢\u0006\u0002\u0010\u001cJ\u001a\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u001e\u001a\u0004\u0018\u00010\u0014H\u0016J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020\u001bH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/storage/LockBasedLazyResolveStorageManager$LockProtectedTrace;", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "trace", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "get", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "slice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;Ljava/lang/Object;)Ljava/lang/Object;", "getBindingContext", "getKeys", "", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", PsiKeyword.RECORD, "", "value", "(Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;Ljava/lang/Object;)V", "", "(Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;)V", "recordType", ModuleXmlParser.TYPE, "report", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "toString", "", "wantsDiagnostics", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class LockProtectedTrace implements BindingTrace {
        private final BindingContext context;
        private final StorageManager storageManager;
        private final BindingTrace trace;

        public LockProtectedTrace(StorageManager storageManager, BindingTrace trace) {
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(trace, "trace");
            this.storageManager = storageManager;
            this.trace = trace;
            BindingContext context = trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            this.context = new LockProtectedContext(storageManager, context);
        }

        @Override // org.jetbrains.kotlin.resolve.BindingTrace
        public void recordType(final KtExpression expression, final KotlinType type) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.storageManager.compute(new Function0<Unit>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedTrace$recordType$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.trace.recordType(expression, type);
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.BindingTrace
        public KotlinType getType(final KtExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (KotlinType) this.storageManager.compute(new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedTrace$getType$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final KotlinType invoke() {
                    return this.this$0.trace.getType(expression);
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.BindingTrace
        /* JADX INFO: renamed from: getBindingContext, reason: from getter */
        public BindingContext getContext() {
            return this.context;
        }

        @Override // org.jetbrains.kotlin.resolve.BindingTrace
        public <K, V> void record(final WritableSlice<K, V> slice, final K key, final V value) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            this.storageManager.compute(new Function0<Unit>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedTrace$record$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.trace.record(slice, key, value);
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.BindingTrace
        public <K> void record(final WritableSlice<K, Boolean> slice, final K key) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            this.storageManager.compute(new Function0<Unit>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedTrace$record$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.trace.record(slice, key);
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.BindingTrace
        public <K, V> V get(final ReadOnlySlice<K, V> slice, final K key) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            return (V) this.storageManager.compute(new Function0<V>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedTrace$get$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final V invoke() {
                    return (V) this.this$0.trace.get(slice, key);
                }
            });
        }

        @Override // org.jetbrains.kotlin.resolve.BindingTrace
        public <K, V> Collection<K> getKeys(final WritableSlice<K, V> slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            Object objCompute = this.storageManager.compute(new Function0<Collection<K>>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedTrace$getKeys$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Collection<K> invoke() {
                    return this.this$0.trace.getKeys(slice);
                }
            });
            Intrinsics.checkNotNullExpressionValue(objCompute, "compute(...)");
            return (Collection) objCompute;
        }

        @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
        public void report(final Diagnostic diagnostic) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            this.storageManager.compute(new Function0<Unit>() { // from class: org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager$LockProtectedTrace$report$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.trace.report(diagnostic);
                }
            });
        }

        @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
        public boolean wantsDiagnostics() {
            return this.trace.wantsDiagnostics();
        }

        public String toString() {
            return "Lock-protected trace of LockBasedLazyResolveStorageManager " + this.storageManager;
        }
    }
}
