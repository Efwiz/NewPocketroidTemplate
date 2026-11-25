package com.google.android.datatransport.runtime.retries;

/* JADX INFO: loaded from: classes9.dex */
public interface RetryStrategy<TInput, TResult> {
    TInput shouldRetry(TInput tinput, TResult tresult);
}
