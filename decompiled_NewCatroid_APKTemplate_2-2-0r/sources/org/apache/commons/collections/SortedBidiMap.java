package org.apache.commons.collections;

import java.util.SortedMap;

/* JADX INFO: loaded from: classes12.dex */
public interface SortedBidiMap extends OrderedBidiMap, SortedMap {
    @Override // org.apache.commons.collections.OrderedBidiMap, org.apache.commons.collections.BidiMap
    BidiMap inverseBidiMap();

    SortedBidiMap inverseSortedBidiMap();
}
