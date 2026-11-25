package org.apache.commons.collections.bag;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.functors.InstanceofPredicate;

/* JADX INFO: loaded from: classes12.dex */
public class TypedBag {
    public static Bag decorate(Bag bag, Class cls) {
        return new PredicatedBag(bag, InstanceofPredicate.getInstance(cls));
    }

    protected TypedBag() {
    }
}
