package org.apache.commons.collections;

import java.util.Map;

/* JADX INFO: loaded from: classes12.dex */
public interface BoundedMap extends Map {
    boolean isFull();

    int maxSize();
}
