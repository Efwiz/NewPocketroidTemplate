package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btHashInt extends BulletBase {
    private long swigCPtr;

    protected btHashInt(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btHashInt(long j, boolean z) {
        this("btHashInt", j, z);
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

    public static long getCPtr(btHashInt bthashint) {
        if (bthashint == null) {
            return 0L;
        }
        return bthashint.swigCPtr;
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
                LinearMathJNI.delete_btHashInt(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btHashInt() {
        this(LinearMathJNI.new_btHashInt__SWIG_0(), true);
    }

    public btHashInt(int i) {
        this(LinearMathJNI.new_btHashInt__SWIG_1(i), true);
    }

    public int getUid1() {
        return LinearMathJNI.btHashInt_getUid1(this.swigCPtr, this);
    }

    public void setUid1(int i) {
        LinearMathJNI.btHashInt_setUid1(this.swigCPtr, this, i);
    }

    public boolean equals(btHashInt bthashint) {
        return LinearMathJNI.btHashInt_equals(this.swigCPtr, this, getCPtr(bthashint), bthashint);
    }

    public long getHash() {
        return LinearMathJNI.btHashInt_getHash(this.swigCPtr, this);
    }
}
