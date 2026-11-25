package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btPoolAllocator extends BulletBase {
    private long swigCPtr;

    protected btPoolAllocator(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPoolAllocator(long j, boolean z) {
        this("btPoolAllocator", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(j, z);
    }

    public static long getCPtr(btPoolAllocator btpoolallocator) {
        if (btpoolallocator == null) {
            return 0L;
        }
        return btpoolallocator.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                LinearMathJNI.delete_btPoolAllocator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btPoolAllocator(int i, int i2) {
        this(LinearMathJNI.new_btPoolAllocator(i, i2), true);
    }

    public int getFreeCount() {
        return LinearMathJNI.btPoolAllocator_getFreeCount(this.swigCPtr, this);
    }

    public int getUsedCount() {
        return LinearMathJNI.btPoolAllocator_getUsedCount(this.swigCPtr, this);
    }

    public int getMaxCount() {
        return LinearMathJNI.btPoolAllocator_getMaxCount(this.swigCPtr, this);
    }

    public long allocate(int i) {
        return LinearMathJNI.btPoolAllocator_allocate(this.swigCPtr, this, i);
    }

    public boolean validPtr(long j) {
        return LinearMathJNI.btPoolAllocator_validPtr(this.swigCPtr, this, j);
    }

    public void freeMemory(long j) {
        LinearMathJNI.btPoolAllocator_freeMemory(this.swigCPtr, this, j);
    }

    public int getElementSize() {
        return LinearMathJNI.btPoolAllocator_getElementSize(this.swigCPtr, this);
    }

    public ByteBuffer getPoolAddress() {
        return LinearMathJNI.btPoolAllocator_getPoolAddress(this.swigCPtr, this);
    }

    public ByteBuffer getPoolAddressConst() {
        return LinearMathJNI.btPoolAllocator_getPoolAddressConst(this.swigCPtr, this);
    }
}
