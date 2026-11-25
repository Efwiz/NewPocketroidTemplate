package org.jetbrains.kotlin.storage;

import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.resolve.BindingTrace;

/* JADX INFO: loaded from: classes6.dex */
public interface LazyResolveStorageManager extends StorageManager {
    BindingTrace createSafeTrace(BindingTrace bindingTrace);

    <K, V> MemoizedFunctionToNotNull<K, V> createSoftlyRetainedMemoizedFunction(Function1<K, V> function1);

    <K, V> MemoizedFunctionToNullable<K, V> createSoftlyRetainedMemoizedFunctionWithNullableValues(Function1<K, V> function1);
}
