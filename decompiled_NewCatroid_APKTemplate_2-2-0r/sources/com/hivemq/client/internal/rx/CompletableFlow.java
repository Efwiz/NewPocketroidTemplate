package com.hivemq.client.internal.rx;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

/* JADX INFO: loaded from: classes10.dex */
public class CompletableFlow implements Disposable {
    private volatile boolean disposed;
    private final CompletableObserver observer;

    public CompletableFlow(CompletableObserver completableObserver) {
        this.observer = completableObserver;
    }

    public void onComplete() {
        this.observer.onComplete();
    }

    public void onError(Throwable th) {
        this.observer.onError(th);
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        this.disposed = true;
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return this.disposed;
    }

    public boolean isCancelled() {
        return isDisposed();
    }
}
