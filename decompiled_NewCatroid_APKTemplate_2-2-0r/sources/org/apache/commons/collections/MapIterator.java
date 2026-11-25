package org.apache.commons.collections;

import java.util.Iterator;

/* JADX INFO: loaded from: classes12.dex */
public interface MapIterator extends Iterator {
    Object getKey();

    Object getValue();

    @Override // java.util.Iterator
    boolean hasNext();

    @Override // java.util.Iterator
    Object next();

    @Override // java.util.Iterator
    void remove();

    Object setValue(Object obj);
}
