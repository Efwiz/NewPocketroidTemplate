package com.koushikdutta.async.future;

/* JADX INFO: loaded from: classes10.dex */
public interface Cancellable {
    boolean cancel();

    boolean isCancelled();

    boolean isDone();
}
