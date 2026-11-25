package org.jf.dexlib2.dexbacked.util;

import java.util.AbstractList;

/* JADX INFO: loaded from: classes6.dex */
public abstract class FixedSizeList<T> extends AbstractList<T> {
    public abstract T readItem(int i);

    @Override // java.util.AbstractList, java.util.List
    public T get(int i) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return readItem(i);
    }
}
