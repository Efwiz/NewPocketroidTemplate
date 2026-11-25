package org.jetbrains.kotlin.com.intellij.openapi.application;

import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.jetbrains.concurrency.CancellablePromise;

/* JADX INFO: loaded from: classes3.dex */
public interface NonBlockingReadAction<T> {
    NonBlockingReadAction<T> coalesceBy(Object... objArr);

    NonBlockingReadAction<T> expireWhen(BooleanSupplier booleanSupplier);

    NonBlockingReadAction<T> finishOnUiThread(ModalityState modalityState, Consumer<? super T> consumer);

    CancellablePromise<T> submit(Executor executor);
}
