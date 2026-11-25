package com.reandroid.dex.reference;

import com.reandroid.dex.data.DataItem;

/* JADX INFO: loaded from: classes11.dex */
public interface DataReference<T extends DataItem> extends DexReference<T> {
    T getOrCreate();

    void unlink();
}
