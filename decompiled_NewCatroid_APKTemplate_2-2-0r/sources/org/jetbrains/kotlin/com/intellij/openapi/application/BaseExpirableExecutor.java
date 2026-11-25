package org.jetbrains.kotlin.com.intellij.openapi.application;

import org.jetbrains.concurrency.CancellablePromise;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.BaseExpirableExecutor;

/* JADX INFO: loaded from: classes3.dex */
public interface BaseExpirableExecutor<E extends BaseExpirableExecutor<E>> {
    E expireWith(Disposable disposable);

    CancellablePromise<?> submit(Runnable runnable);
}
