package io.reactivex;

/* JADX INFO: loaded from: classes11.dex */
public interface MaybeSource<T> {
    void subscribe(MaybeObserver<? super T> maybeObserver);
}
