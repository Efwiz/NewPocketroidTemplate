package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes11.dex */
class UnpooledUnsafeNoCleanerDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
    UnpooledUnsafeNoCleanerDirectByteBuf(ByteBufAllocator byteBufAllocator, int i, int i2) {
        super(byteBufAllocator, i, i2);
    }

    @Override // io.netty.buffer.UnpooledDirectByteBuf
    protected ByteBuffer allocateDirect(int i) {
        return PlatformDependent.allocateDirectNoCleaner(i);
    }

    ByteBuffer reallocateDirect(ByteBuffer byteBuffer, int i) {
        return PlatformDependent.reallocateDirectNoCleaner(byteBuffer, i);
    }

    @Override // io.netty.buffer.UnpooledDirectByteBuf
    protected void freeDirect(ByteBuffer byteBuffer) {
        PlatformDependent.freeDirectNoCleaner(byteBuffer);
    }

    @Override // io.netty.buffer.UnpooledDirectByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        checkNewCapacity(i);
        if (i == capacity()) {
            return this;
        }
        trimIndicesToCapacity(i);
        setByteBuffer(reallocateDirect(this.buffer, i), false);
        return this;
    }
}
