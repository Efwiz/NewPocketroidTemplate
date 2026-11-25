package com.koushikdutta.async.future;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ConvertFuture<T, F> extends TransformFuture<T, F> {
    protected abstract Future<T> convert(F f) throws Exception;

    @Override // com.koushikdutta.async.future.TransformFuture
    protected final void transform(F f) throws Exception {
        setComplete((Future) convert(f));
    }
}
