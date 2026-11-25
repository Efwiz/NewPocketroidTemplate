package io.reactivex;

/* JADX INFO: loaded from: classes11.dex */
public interface ObservableConverter<T, R> {
    R apply(Observable<T> observable);
}
