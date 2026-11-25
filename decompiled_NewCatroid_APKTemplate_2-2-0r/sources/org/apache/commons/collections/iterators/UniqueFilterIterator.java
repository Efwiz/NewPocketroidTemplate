package org.apache.commons.collections.iterators;

import java.util.Iterator;
import org.apache.commons.collections.functors.UniquePredicate;

/* JADX INFO: loaded from: classes12.dex */
public class UniqueFilterIterator extends FilterIterator {
    public UniqueFilterIterator(Iterator it) {
        super(it, UniquePredicate.getInstance());
    }
}
