package io.netty.buffer;

import io.netty.util.internal.ReferenceCountUpdater;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractReferenceCountedByteBuf extends AbstractByteBuf {
    private volatile int refCnt;
    private static final long REFCNT_FIELD_OFFSET = ReferenceCountUpdater.getUnsafeOffset(AbstractReferenceCountedByteBuf.class, "refCnt");
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> AIF_UPDATER = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
    private static final ReferenceCountUpdater<AbstractReferenceCountedByteBuf> updater = new ReferenceCountUpdater<AbstractReferenceCountedByteBuf>() { // from class: io.netty.buffer.AbstractReferenceCountedByteBuf.1
        @Override // io.netty.util.internal.ReferenceCountUpdater
        protected AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> updater() {
            return AbstractReferenceCountedByteBuf.AIF_UPDATER;
        }

        @Override // io.netty.util.internal.ReferenceCountUpdater
        protected long unsafeOffset() {
            return AbstractReferenceCountedByteBuf.REFCNT_FIELD_OFFSET;
        }
    };

    protected abstract void deallocate();

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch(Object obj) {
        return this;
    }

    protected AbstractReferenceCountedByteBuf(int i) {
        super(i);
        this.refCnt = updater.initialValue();
    }

    @Override // io.netty.buffer.ByteBuf
    boolean isAccessible() {
        return updater.isLiveNonVolatile(this);
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return updater.refCnt(this);
    }

    protected final void setRefCnt(int i) {
        updater.setRefCnt(this, i);
    }

    protected final void resetRefCnt() {
        updater.resetRefCnt(this);
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain() {
        return (ByteBuf) updater.retain(this);
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain(int i) {
        return (ByteBuf) updater.retain(this, i);
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return handleRelease(updater.release(this));
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return handleRelease(updater.release(this, i));
    }

    private boolean handleRelease(boolean z) {
        if (z) {
            deallocate();
        }
        return z;
    }
}
