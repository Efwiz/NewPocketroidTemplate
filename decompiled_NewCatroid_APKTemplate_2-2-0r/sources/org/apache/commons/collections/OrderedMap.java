package org.apache.commons.collections;

/* JADX INFO: loaded from: classes12.dex */
public interface OrderedMap extends IterableMap {
    Object firstKey();

    Object lastKey();

    Object nextKey(Object obj);

    OrderedMapIterator orderedMapIterator();

    Object previousKey(Object obj);
}
