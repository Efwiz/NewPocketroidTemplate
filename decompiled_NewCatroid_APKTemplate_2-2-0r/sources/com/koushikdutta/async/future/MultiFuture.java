package com.koushikdutta.async.future;

import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class MultiFuture<T> extends SimpleFuture<T> {
    final FutureCallback<T> callback = new FutureCallback<T>() { // from class: com.koushikdutta.async.future.MultiFuture.1
        @Override // com.koushikdutta.async.future.FutureCallback
        public void onCompleted(Exception exc, T t) {
            ArrayList<FutureCallback<T>> arrayList;
            synchronized (MultiFuture.this) {
                arrayList = MultiFuture.this.callbacks;
                MultiFuture.this.callbacks = null;
            }
            if (arrayList == null) {
                return;
            }
            Iterator<FutureCallback<T>> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onCompleted(exc, t);
            }
        }
    };
    ArrayList<FutureCallback<T>> callbacks;

    @Override // com.koushikdutta.async.future.SimpleFuture, com.koushikdutta.async.future.Future
    public MultiFuture<T> setCallback(FutureCallback<T> futureCallback) {
        synchronized (this) {
            if (this.callbacks == null) {
                this.callbacks = new ArrayList<>();
            }
            this.callbacks.add(futureCallback);
        }
        super.setCallback((FutureCallback) this.callback);
        return this;
    }
}
