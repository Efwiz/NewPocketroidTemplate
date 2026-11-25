package org.jetbrains.kotlin.it.unimi.dsi.fastutil;

import java.util.Iterator;

/* JADX INFO: loaded from: classes5.dex */
public interface BidirectionalIterator<K> extends Iterator<K> {
    boolean hasPrevious();

    K previous();
}
