package com.koushikdutta.async.future;

/* JADX INFO: loaded from: classes10.dex */
public abstract class TransformFuture<T, F> extends SimpleFuture<T> implements FutureCallback<F> {
    protected abstract void transform(F f) throws Exception;

    @Override // com.koushikdutta.async.future.FutureCallback
    public void onCompleted(Exception exc, F f) {
        if (isCancelled()) {
            return;
        }
        if (exc != null) {
            error(exc);
            return;
        }
        try {
            transform(f);
        } catch (Exception e) {
            error(e);
        }
    }

    protected void error(Exception exc) {
        setComplete(exc);
    }
}
