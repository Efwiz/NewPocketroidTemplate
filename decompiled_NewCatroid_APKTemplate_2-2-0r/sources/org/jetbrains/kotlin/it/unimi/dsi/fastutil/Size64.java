package org.jetbrains.kotlin.it.unimi.dsi.fastutil;

import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: classes5.dex */
public interface Size64 {
    long size64();

    static long sizeOf(Collection<?> collection) {
        return collection instanceof Size64 ? ((Size64) collection).size64() : collection.size();
    }

    static long sizeOf(Map<?, ?> map) {
        return map instanceof Size64 ? ((Size64) map).size64() : map.size();
    }
}
