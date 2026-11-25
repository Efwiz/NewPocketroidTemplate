package com.koushikdutta.async.future;

/* JADX INFO: loaded from: classes10.dex */
public interface DependentCancellable extends Cancellable {
    DependentCancellable setParent(Cancellable cancellable);
}
