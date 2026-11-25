package org.jctools.queues;

import org.jctools.util.PortableJvmInfo;
import org.jctools.util.UnsafeAccess;

/* JADX INFO: loaded from: classes14.dex */
public abstract class ConcurrentSequencedCircularArrayQueue<E> extends ConcurrentCircularArrayQueue<E> {
    private static final long ARRAY_BASE;
    private static final int ELEMENT_SHIFT;
    protected static final int SEQ_BUFFER_PAD;
    protected final long[] sequenceBuffer;

    static {
        int iArrayIndexScale = UnsafeAccess.UNSAFE.arrayIndexScale(long[].class);
        if (8 == iArrayIndexScale) {
            ELEMENT_SHIFT = 3;
            int i = (PortableJvmInfo.CACHE_LINE_SIZE * 2) / iArrayIndexScale;
            SEQ_BUFFER_PAD = i;
            ARRAY_BASE = UnsafeAccess.UNSAFE.arrayBaseOffset(long[].class) + (i * iArrayIndexScale);
            return;
        }
        throw new IllegalStateException("Unexpected long[] element size");
    }

    public ConcurrentSequencedCircularArrayQueue(int i) {
        super(i);
        int i2 = (int) (this.mask + 1);
        this.sequenceBuffer = new long[(SEQ_BUFFER_PAD * 2) + i2];
        for (long j = 0; j < i2; j++) {
            soSequence(this.sequenceBuffer, calcSequenceOffset(j), j);
        }
    }

    protected final long calcSequenceOffset(long j) {
        return calcSequenceOffset(j, this.mask);
    }

    protected static long calcSequenceOffset(long j, long j2) {
        return ARRAY_BASE + ((j & j2) << ELEMENT_SHIFT);
    }

    protected final void soSequence(long[] jArr, long j, long j2) {
        UnsafeAccess.UNSAFE.putOrderedLong(jArr, j, j2);
    }

    protected final long lvSequence(long[] jArr, long j) {
        return UnsafeAccess.UNSAFE.getLongVolatile(jArr, j);
    }
}
