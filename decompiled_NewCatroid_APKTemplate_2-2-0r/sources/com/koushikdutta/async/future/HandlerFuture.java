package com.koushikdutta.async.future;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes10.dex */
public class HandlerFuture<T> extends SimpleFuture<T> {
    Handler handler;

    public HandlerFuture() {
        Looper looperMyLooper = Looper.myLooper();
        this.handler = new Handler(looperMyLooper == null ? Looper.getMainLooper() : looperMyLooper);
    }

    /* JADX INFO: renamed from: com.koushikdutta.async.future.HandlerFuture$1, reason: invalid class name */
    class AnonymousClass1 implements FutureCallback<T> {
        final /* synthetic */ FutureCallback val$callback;

        AnonymousClass1(FutureCallback futureCallback) {
            this.val$callback = futureCallback;
        }

        @Override // com.koushikdutta.async.future.FutureCallback
        public void onCompleted(final Exception exc, final T t) {
            if (Looper.myLooper() == HandlerFuture.this.handler.getLooper()) {
                this.val$callback.onCompleted(exc, t);
            } else {
                HandlerFuture.this.handler.post(new Runnable() { // from class: com.koushikdutta.async.future.HandlerFuture.1.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass1.this.onCompleted(exc, t);
                    }
                });
            }
        }
    }

    @Override // com.koushikdutta.async.future.SimpleFuture, com.koushikdutta.async.future.Future
    public SimpleFuture<T> setCallback(FutureCallback<T> futureCallback) {
        return super.setCallback((FutureCallback) new AnonymousClass1(futureCallback));
    }
}
