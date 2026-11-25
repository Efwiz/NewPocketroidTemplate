package org.apache.commons.collections;

/* JADX INFO: loaded from: classes12.dex */
public interface OrderedMapIterator extends MapIterator, OrderedIterator {
    @Override // org.apache.commons.collections.OrderedIterator
    boolean hasPrevious();

    @Override // org.apache.commons.collections.OrderedIterator
    Object previous();
}
