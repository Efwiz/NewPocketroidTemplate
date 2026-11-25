package org.apache.commons.collections.iterators;

import org.apache.commons.collections.OrderedIterator;
import org.apache.commons.collections.ResettableIterator;

/* JADX INFO: loaded from: classes12.dex */
public class EmptyOrderedIterator extends AbstractEmptyIterator implements OrderedIterator, ResettableIterator {
    public static final OrderedIterator INSTANCE = new EmptyOrderedIterator();

    protected EmptyOrderedIterator() {
    }
}
