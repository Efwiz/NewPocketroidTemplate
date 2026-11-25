package org.apache.commons.collections;

/* JADX INFO: loaded from: classes12.dex */
public interface OrderedBidiMap extends BidiMap, OrderedMap {
    @Override // org.apache.commons.collections.BidiMap
    BidiMap inverseBidiMap();

    OrderedBidiMap inverseOrderedBidiMap();
}
