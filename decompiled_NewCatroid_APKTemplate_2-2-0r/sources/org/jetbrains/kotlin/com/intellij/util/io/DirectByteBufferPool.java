package org.jetbrains.kotlin.com.intellij.util.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes3.dex */
public final class DirectByteBufferPool {
    public static final DirectByteBufferPool DEFAULT_POOL = new DirectByteBufferPool();
    private final ConcurrentSkipListMap<Integer, ByteBuffer> pool = new ConcurrentSkipListMap<>();
    private final AtomicInteger count = new AtomicInteger();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/DirectByteBufferPool";
        } else {
            objArr[0] = "buffer";
        }
        if (i != 1) {
            objArr[1] = "allocate";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/DirectByteBufferPool";
        }
        if (i == 1) {
            objArr[2] = "release";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    private static int roundUpInt(int i, int i2) {
        return ((i + i2) - 1) & (-i2);
    }

    public ByteBuffer allocate(int i) {
        Map.Entry<Integer, ByteBuffer> entryCeilingEntry;
        ByteBuffer value;
        int iRoundUpInt = roundUpInt(i, 2048);
        do {
            entryCeilingEntry = this.pool.ceilingEntry(Integer.valueOf(iRoundUpInt));
            if (entryCeilingEntry == null) {
                break;
            }
        } while (!this.pool.remove(entryCeilingEntry.getKey(), entryCeilingEntry.getValue()));
        if (entryCeilingEntry == null) {
            value = ByteBuffer.allocateDirect(iRoundUpInt);
        } else {
            this.count.decrementAndGet();
            value = entryCeilingEntry.getValue();
        }
        value.limit(i);
        if (value == null) {
            $$$reportNull$$$0(0);
        }
        return value;
    }

    public void release(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            $$$reportNull$$$0(1);
        }
        if (byteBuffer.isReadOnly()) {
            return;
        }
        byteBuffer.rewind();
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        if (this.count.get() >= 32 || this.pool.putIfAbsent(Integer.valueOf(byteBuffer.capacity()), byteBuffer) != null) {
            return;
        }
        this.count.incrementAndGet();
    }
}
