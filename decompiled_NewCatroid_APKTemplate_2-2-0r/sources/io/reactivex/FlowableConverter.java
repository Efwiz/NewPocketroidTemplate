package io.reactivex;

/* JADX INFO: loaded from: classes11.dex */
public interface FlowableConverter<T, R> {
    R apply(Flowable<T> flowable);
}
