package com.artemis;

import com.artemis.utils.Bag;

/* JADX INFO: loaded from: classes8.dex */
class WildBag<T> extends Bag<T> {
    public WildBag(Class<T> cls) {
        super(cls);
    }

    void setSize(int i) {
        this.size = i;
    }
}
