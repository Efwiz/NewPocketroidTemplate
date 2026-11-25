package org.jctools.queues;

import org.jctools.util.UnsafeRefArrayAccess;

/* JADX INFO: loaded from: classes14.dex */
public final class CircularArrayOffsetCalculator {
    public static <E> E[] allocate(int i) {
        return (E[]) new Object[i];
    }

    public static long calcElementOffset(long j, long j2) {
        return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((j & j2) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT);
    }
}
