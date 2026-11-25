package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btStackAlloc extends BulletBase {
    private long swigCPtr;

    protected btStackAlloc(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btStackAlloc(long j, boolean z) {
        this("btStackAlloc", j, z);
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

    public static long getCPtr(btStackAlloc btstackalloc) {
        if (btstackalloc == null) {
            return 0L;
        }
        return btstackalloc.swigCPtr;
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
                LinearMathJNI.delete_btStackAlloc(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btStackAlloc(long j) {
        this(LinearMathJNI.new_btStackAlloc(j), true);
    }

    public void create(long j) {
        LinearMathJNI.btStackAlloc_create(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    public void destroy() {
        LinearMathJNI.btStackAlloc_destroy(this.swigCPtr, this);
    }

    public int getAvailableMemory() {
        return LinearMathJNI.btStackAlloc_getAvailableMemory(this.swigCPtr, this);
    }

    public ByteBuffer allocate(long j) {
        return LinearMathJNI.btStackAlloc_allocate(this.swigCPtr, this, j);
    }

    public btBlock beginBlock() {
        long jBtStackAlloc_beginBlock = LinearMathJNI.btStackAlloc_beginBlock(this.swigCPtr, this);
        if (jBtStackAlloc_beginBlock == 0) {
            return null;
        }
        return new btBlock(jBtStackAlloc_beginBlock, false);
    }

    public void endBlock(btBlock btblock) {
        LinearMathJNI.btStackAlloc_endBlock(this.swigCPtr, this, btBlock.getCPtr(btblock), btblock);
    }
}
