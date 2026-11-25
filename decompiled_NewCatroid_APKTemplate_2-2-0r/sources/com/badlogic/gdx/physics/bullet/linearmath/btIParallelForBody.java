package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btIParallelForBody extends BulletBase {
    private long swigCPtr;

    protected btIParallelForBody(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btIParallelForBody(long j, boolean z) {
        this("btIParallelForBody", j, z);
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

    public static long getCPtr(btIParallelForBody btiparallelforbody) {
        if (btiparallelforbody == null) {
            return 0L;
        }
        return btiparallelforbody.swigCPtr;
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
                LinearMathJNI.delete_btIParallelForBody(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void forLoop(int i, int i2) {
        LinearMathJNI.btIParallelForBody_forLoop(this.swigCPtr, this, i, i2);
    }
}
