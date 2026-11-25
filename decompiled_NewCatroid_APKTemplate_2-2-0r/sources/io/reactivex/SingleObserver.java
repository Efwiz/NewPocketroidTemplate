package io.reactivex;

import io.reactivex.disposables.Disposable;

/* JADX INFO: loaded from: classes11.dex */
public interface SingleObserver<T> {
    void onError(Throwable th);

    void onSubscribe(Disposable disposable);

    void onSuccess(T t);
}
