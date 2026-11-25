package org.apache.html.dom;

/* JADX INFO: loaded from: classes13.dex */
class CollectionIndex {
    private int _index;

    CollectionIndex(int i) {
        this._index = i;
    }

    void decrement() {
        this._index--;
    }

    int getIndex() {
        return this._index;
    }

    boolean isZero() {
        return this._index <= 0;
    }
}
