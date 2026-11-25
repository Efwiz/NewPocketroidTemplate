package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;

/* JADX INFO: loaded from: classes11.dex */
public interface ResettableConnectable {
    void resetIf(Disposable disposable);
}
