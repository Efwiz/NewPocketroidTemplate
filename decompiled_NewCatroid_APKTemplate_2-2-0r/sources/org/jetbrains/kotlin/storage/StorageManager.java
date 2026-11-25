package org.jetbrains.kotlin.storage;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: StorageManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0005H&¢\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\b\"\u0004\b\u0000\u0010\t\"\b\b\u0001\u0010\n*\u00020\u0001H&J$\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f\"\u0004\b\u0000\u0010\t\"\b\b\u0001\u0010\n*\u00020\u0001H&J&\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00030\u000e\"\b\b\u0000\u0010\u0003*\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0005H&J:\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00030\u000e\"\b\b\u0000\u0010\u0003*\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u0002H\u00030\u0010H&JP\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00030\u000e\"\b\b\u0000\u0010\u0003*\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u00102\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u00140\u0010H&J8\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u0016\"\u0004\b\u0000\u0010\t\"\b\b\u0001\u0010\n*\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u0010H&JR\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u0016\"\u0004\b\u0000\u0010\t\"\b\b\u0001\u0010\n*\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u00102\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u0002H\n0\u0017H&Jf\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u0016\"\u0004\b\u0000\u0010\t\"\b\b\u0001\u0010\n*\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u00102\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u0002H\n0\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00010\u0019H&JL\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u0016\"\u0004\b\u0000\u0010\t\"\b\b\u0001\u0010\n*\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u00102\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00010\u0019H&JL\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u001b\"\u0004\b\u0000\u0010\t\"\b\b\u0001\u0010\n*\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u00102\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00010\u0019H&J:\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u001b\"\u0004\b\u0000\u0010\t\"\b\b\u0001\u0010\n*\u00020\u00012\u0014\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u0002H\t\u0012\u0006\u0012\u0004\u0018\u0001H\n0\u0010H&J(\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00030\u001d\"\b\b\u0000\u0010\u0003*\u00020\u00012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00030\u0005H&J>\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00030\u001d\"\b\b\u0000\u0010\u0003*\u00020\u00012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00030\u00052\u0014\u0010\u0013\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u0001H\u0003\u0012\u0004\u0012\u00020\u00140\u0010H&J3\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00030\u000e\"\b\b\u0000\u0010\u0003*\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0006\u0010\u000f\u001a\u0002H\u0003H&¢\u0006\u0002\u0010 J7\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00030\u001d\"\b\b\u0000\u0010\u0003*\u00020\u00012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00030\u00052\b\u0010\u000f\u001a\u0004\u0018\u0001H\u0003H&¢\u0006\u0002\u0010\"ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006#À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/storage/StorageManager;", "", "compute", ExifInterface.GPS_DIRECTION_TRUE, "computable", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "createCacheWithNotNullValues", "Lorg/jetbrains/kotlin/storage/CacheWithNotNullValues;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "createCacheWithNullableValues", "Lorg/jetbrains/kotlin/storage/CacheWithNullableValues;", "createLazyValue", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "onRecursiveCall", "Lkotlin/Function1;", "", "createLazyValueWithPostCompute", "postCompute", "", "createMemoizedFunction", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lkotlin/Function2;", "map", "Ljava/util/concurrent/ConcurrentMap;", "createMemoizedFunctionWithNullableValues", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "createNullableLazyValue", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "createNullableLazyValueWithPostCompute", "createRecursionTolerantLazyValue", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "createRecursionTolerantNullableLazyValue", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface StorageManager {
    <T> T compute(Function0<? extends T> computable);

    <K, V> CacheWithNotNullValues<K, V> createCacheWithNotNullValues();

    <K, V> CacheWithNullableValues<K, V> createCacheWithNullableValues();

    <T> NotNullLazyValue<T> createLazyValue(Function0<? extends T> computable);

    <T> NotNullLazyValue<T> createLazyValue(Function0<? extends T> computable, Function1<? super Boolean, ? extends T> onRecursiveCall);

    <T> NotNullLazyValue<T> createLazyValueWithPostCompute(Function0<? extends T> computable, Function1<? super Boolean, ? extends T> onRecursiveCall, Function1<? super T, Unit> postCompute);

    <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute);

    <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute, ConcurrentMap<K, Object> map);

    <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute, Function2<? super K, ? super Boolean, ? extends V> onRecursiveCall);

    <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> compute, Function2<? super K, ? super Boolean, ? extends V> onRecursiveCall, ConcurrentMap<K, Object> map);

    <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(Function1<? super K, ? extends V> compute);

    <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(Function1<? super K, ? extends V> compute, ConcurrentMap<K, Object> map);

    <T> NullableLazyValue<T> createNullableLazyValue(Function0<? extends T> computable);

    <T> NullableLazyValue<T> createNullableLazyValueWithPostCompute(Function0<? extends T> computable, Function1<? super T, Unit> postCompute);

    <T> NotNullLazyValue<T> createRecursionTolerantLazyValue(Function0<? extends T> computable, T onRecursiveCall);

    <T> NullableLazyValue<T> createRecursionTolerantNullableLazyValue(Function0<? extends T> computable, T onRecursiveCall);
}
