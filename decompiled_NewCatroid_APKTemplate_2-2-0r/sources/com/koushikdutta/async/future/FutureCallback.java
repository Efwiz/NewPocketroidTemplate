package com.koushikdutta.async.future;

/* JADX INFO: loaded from: classes10.dex */
public interface FutureCallback<T> {
    void onCompleted(Exception exc, T t);
}
