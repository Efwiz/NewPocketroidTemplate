package org.apache.commons.collections;

import java.util.Collection;

/* JADX INFO: loaded from: classes12.dex */
public interface BoundedCollection extends Collection {
    boolean isFull();

    int maxSize();
}
