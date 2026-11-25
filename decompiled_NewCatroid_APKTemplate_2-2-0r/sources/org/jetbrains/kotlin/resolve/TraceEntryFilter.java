package org.jetbrains.kotlin.resolve;

import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public interface TraceEntryFilter {
    boolean accept(WritableSlice<?, ?> writableSlice, Object obj);
}
