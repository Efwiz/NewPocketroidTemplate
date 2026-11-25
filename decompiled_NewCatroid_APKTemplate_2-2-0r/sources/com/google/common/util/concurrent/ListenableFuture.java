package com.google.common.util.concurrent;

import com.google.errorprone.annotations.DoNotMock;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/listenablefuture-1.0.jar:com/google/common/util/concurrent/ListenableFuture.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
@DoNotMock("Use the methods in Futures (like immediateFuture) or SettableFuture")
@ElementTypesAreNonnullByDefault
public interface ListenableFuture<V> extends Future<V> {
    void addListener(Runnable runnable, Executor executor);
}
