package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btHashPtr extends BulletBase {
    private long swigCPtr;

    protected btHashPtr(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btHashPtr(long j, boolean z) {
        this("btHashPtr", j, z);
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

    public static long getCPtr(btHashPtr bthashptr) {
        if (bthashptr == null) {
            return 0L;
        }
        return bthashptr.swigCPtr;
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
                LinearMathJNI.delete_btHashPtr(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btHashPtr(long j) {
        this(LinearMathJNI.new_btHashPtr(j), true);
    }

    public long getPointer() {
        return LinearMathJNI.btHashPtr_getPointer(this.swigCPtr, this);
    }

    public boolean equals(btHashPtr bthashptr) {
        return LinearMathJNI.btHashPtr_equals(this.swigCPtr, this, getCPtr(bthashptr), bthashptr);
    }

    public long getHash() {
        return LinearMathJNI.btHashPtr_getHash(this.swigCPtr, this);
    }
}
