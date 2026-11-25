package io.reactivex;

/* JADX INFO: loaded from: classes11.dex */
public interface SingleConverter<T, R> {
    R apply(Single<T> single);
}
