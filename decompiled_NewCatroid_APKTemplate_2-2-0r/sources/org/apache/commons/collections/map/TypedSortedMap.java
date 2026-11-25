package org.apache.commons.collections.map;

import java.util.SortedMap;
import org.apache.commons.collections.functors.InstanceofPredicate;

/* JADX INFO: loaded from: classes12.dex */
public class TypedSortedMap {
    public static SortedMap decorate(SortedMap sortedMap, Class cls, Class cls2) {
        return new PredicatedSortedMap(sortedMap, InstanceofPredicate.getInstance(cls), InstanceofPredicate.getInstance(cls2));
    }

    protected TypedSortedMap() {
    }
}
