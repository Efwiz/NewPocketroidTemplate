package org.jetbrains.kotlin.com.intellij.util.io;

import java.nio.ByteBuffer;
import java.util.BitSet;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool;

/* JADX INFO: loaded from: classes3.dex */
public class Page {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int PAGE_SIZE = SystemProperties.getIntProperty("idea.io.page.size", 8192);
    private static final LimitedPool<ByteBuffer> ourBufferPool = new LimitedPool.Sync(10, new LimitedPool.ObjectFactory() { // from class: org.jetbrains.kotlin.com.intellij.util.io.Page$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool.ObjectFactory
        public final Object create() {
            return ByteBuffer.allocate(Page.PAGE_SIZE);
        }
    });
    private ByteBuffer buf;
    private boolean dirty;
    private final PageLock lock;
    private final Range myContinuousRange;
    private int myFinalizationId;
    private final PoolPageKey myKey;
    private BitSet myWriteMask;
    private boolean read;

    private static class PageLock {
    }

    private static class Range {
        int end;
        int start;
    }

    private void ensureRead() {
        if (this.read) {
            return;
        }
        if (this.myWriteMask != null) {
            int i = PAGE_SIZE;
            byte[] bArr = new byte[i];
            ByteBuffer buf = getBuf();
            buf.position(0);
            buf.get(bArr, 0, i);
            getOwner().loadPage(this);
            int iNextSetBit = this.myWriteMask.nextSetBit(0);
            while (iNextSetBit >= 0) {
                buf.put(iNextSetBit, bArr[iNextSetBit]);
                iNextSetBit = this.myWriteMask.nextSetBit(iNextSetBit + 1);
            }
            this.myWriteMask = null;
        } else {
            getOwner().loadPage(this);
        }
        this.read = true;
    }

    private Range calcContinuousRange(BitSet bitSet) {
        int iNextSetBit = bitSet.nextSetBit(0);
        if (iNextSetBit < 0) {
            return null;
        }
        int iNextClearBit = bitSet.nextClearBit(iNextSetBit);
        if (iNextClearBit > 0) {
            if (bitSet.nextSetBit(iNextClearBit) >= 0) {
                return null;
            }
            this.myContinuousRange.start = iNextSetBit;
            this.myContinuousRange.end = iNextClearBit;
            return this.myContinuousRange;
        }
        this.myContinuousRange.start = iNextSetBit;
        this.myContinuousRange.end = PAGE_SIZE;
        return this.myContinuousRange;
    }

    public void flush() {
        int i;
        int i2;
        int i3;
        synchronized (this.lock) {
            if (this.dirty) {
                int i4 = PAGE_SIZE;
                BitSet bitSet = this.myWriteMask;
                if (bitSet != null) {
                    Range rangeCalcContinuousRange = calcContinuousRange(bitSet);
                    if (rangeCalcContinuousRange == null) {
                        ensureRead();
                        i3 = i4;
                        i2 = 0;
                    } else {
                        i2 = rangeCalcContinuousRange.start;
                        i3 = rangeCalcContinuousRange.end;
                    }
                    this.myWriteMask = null;
                    int i5 = i3;
                    i = i2;
                    i4 = i5;
                } else {
                    i = 0;
                }
                if (i4 - i > 0) {
                    getOwner().flushPage(this, i, i4);
                }
                this.dirty = false;
            }
        }
    }

    public ByteBuffer getBuf() {
        ByteBuffer byteBuffer;
        synchronized (this.lock) {
            if (this.buf == null) {
                this.buf = ourBufferPool.alloc();
            }
            byteBuffer = this.buf;
        }
        return byteBuffer;
    }

    private void recycle() {
        ByteBuffer byteBuffer = this.buf;
        if (byteBuffer != null) {
            ourBufferPool.recycle(byteBuffer);
        }
        this.buf = null;
        this.read = false;
        this.dirty = false;
        this.myWriteMask = null;
    }

    public long getOffset() {
        return this.myKey.getOffset();
    }

    public FinalizationRequest prepareForFinalization(int i) {
        synchronized (this.lock) {
            if (this.dirty) {
                this.myFinalizationId = i;
                return new FinalizationRequest(this, i);
            }
            recycle();
            return null;
        }
    }

    public RandomAccessDataFile getOwner() {
        return getKey().getOwner();
    }

    public PoolPageKey getKey() {
        return this.myKey;
    }

    public boolean flushIfFinalizationIdIsEqualTo(long j) {
        synchronized (this.lock) {
            if (this.myFinalizationId != j) {
                return false;
            }
            flush();
            return true;
        }
    }

    public boolean recycleIfFinalizationIdIsEqualTo(long j) {
        synchronized (this.lock) {
            if (this.myFinalizationId != j) {
                return false;
            }
            recycle();
            return true;
        }
    }

    public String toString() {
        String str;
        synchronized (this.lock) {
            str = "Page[" + getOwner() + ", dirty: " + this.dirty + ", offset=" + getOffset() + "]";
        }
        return str;
    }
}
