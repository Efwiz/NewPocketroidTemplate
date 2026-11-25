package com.koushikdutta.async.callback;

/* JADX INFO: loaded from: classes10.dex */
public interface ResultCallback<S, T> {
    void onCompleted(Exception exc, S s, T t);
}
