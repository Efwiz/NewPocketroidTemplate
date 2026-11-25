package org.apache.commons.collections.collection;

import java.util.Collection;
import org.apache.commons.collections.functors.InstanceofPredicate;

/* JADX INFO: loaded from: classes12.dex */
public class TypedCollection {
    public static Collection decorate(Collection collection, Class cls) {
        return new PredicatedCollection(collection, InstanceofPredicate.getInstance(cls));
    }

    protected TypedCollection() {
    }
}
