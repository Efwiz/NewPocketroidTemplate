package io.reactivex;

/* JADX INFO: loaded from: classes11.dex */
public interface MaybeConverter<T, R> {
    R apply(Maybe<T> maybe);
}
