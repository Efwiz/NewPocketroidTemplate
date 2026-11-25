package org.apache.commons.collections;

import java.util.Iterator;

/* JADX INFO: loaded from: classes12.dex */
public interface OrderedIterator extends Iterator {
    boolean hasPrevious();

    Object previous();
}
