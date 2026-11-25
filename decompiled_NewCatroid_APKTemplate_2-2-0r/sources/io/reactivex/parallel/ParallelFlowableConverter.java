package io.reactivex.parallel;

/* JADX INFO: loaded from: classes11.dex */
public interface ParallelFlowableConverter<T, R> {
    R apply(ParallelFlowable<T> parallelFlowable);
}
