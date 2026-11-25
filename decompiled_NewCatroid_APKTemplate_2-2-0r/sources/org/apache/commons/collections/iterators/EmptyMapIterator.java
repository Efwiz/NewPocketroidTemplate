package org.apache.commons.collections.iterators;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.ResettableIterator;

/* JADX INFO: loaded from: classes12.dex */
public class EmptyMapIterator extends AbstractEmptyIterator implements MapIterator, ResettableIterator {
    public static final MapIterator INSTANCE = new EmptyMapIterator();

    protected EmptyMapIterator() {
    }
}
