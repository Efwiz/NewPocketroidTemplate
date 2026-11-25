package org.apache.commons.collections;

/* JADX INFO: loaded from: classes12.dex */
public interface BidiMap extends IterableMap {
    Object getKey(Object obj);

    BidiMap inverseBidiMap();

    @Override // org.apache.commons.collections.IterableMap
    MapIterator mapIterator();

    @Override // java.util.Map
    Object put(Object obj, Object obj2);

    Object removeValue(Object obj);
}
