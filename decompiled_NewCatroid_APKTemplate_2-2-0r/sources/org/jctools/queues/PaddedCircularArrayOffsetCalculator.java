package org.jctools.queues;

import org.jctools.util.PortableJvmInfo;
import org.jctools.util.UnsafeRefArrayAccess;

/* JADX INFO: loaded from: classes14.dex */
public final class PaddedCircularArrayOffsetCalculator {
    static final long REF_ARRAY_BASE;
    static final int REF_BUFFER_PAD;

    static {
        int i = (PortableJvmInfo.CACHE_LINE_SIZE * 2) >> UnsafeRefArrayAccess.REF_ELEMENT_SHIFT;
        REF_BUFFER_PAD = i;
        REF_ARRAY_BASE = UnsafeRefArrayAccess.REF_ARRAY_BASE + ((long) (i << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT));
    }

    public static <E> E[] allocate(int i) {
        return (E[]) new Object[i + (REF_BUFFER_PAD * 2)];
    }

    protected static long calcElementOffset(long j, long j2) {
        return REF_ARRAY_BASE + ((j & j2) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT);
    }
}
