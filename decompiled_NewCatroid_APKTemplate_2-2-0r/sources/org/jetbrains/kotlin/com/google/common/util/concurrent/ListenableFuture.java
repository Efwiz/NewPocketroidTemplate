package org.jetbrains.kotlin.com.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.jetbrains.kotlin.com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes3.dex */
@DoNotMock("Use the methods in Futures (like immediateFuture) or SettableFuture")
public interface ListenableFuture<V> extends Future<V> {
    void addListener(Runnable runnable, Executor executor);
}
