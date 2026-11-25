package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btITaskScheduler extends BulletBase {
    private long swigCPtr;

    protected btITaskScheduler(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btITaskScheduler(long j, boolean z) {
        this("btITaskScheduler", j, z);
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

    public static long getCPtr(btITaskScheduler btitaskscheduler) {
        if (btitaskscheduler == null) {
            return 0L;
        }
        return btitaskscheduler.swigCPtr;
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
                LinearMathJNI.delete_btITaskScheduler(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public String getName() {
        return LinearMathJNI.btITaskScheduler_getName(this.swigCPtr, this);
    }

    public int getMaxNumThreads() {
        return LinearMathJNI.btITaskScheduler_getMaxNumThreads(this.swigCPtr, this);
    }

    public int getNumThreads() {
        return LinearMathJNI.btITaskScheduler_getNumThreads(this.swigCPtr, this);
    }

    public void setNumThreads(int i) {
        LinearMathJNI.btITaskScheduler_setNumThreads(this.swigCPtr, this, i);
    }

    public void parallelFor(int i, int i2, int i3, btIParallelForBody btiparallelforbody) {
        LinearMathJNI.btITaskScheduler_parallelFor(this.swigCPtr, this, i, i2, i3, btIParallelForBody.getCPtr(btiparallelforbody), btiparallelforbody);
    }

    public void activate() {
        LinearMathJNI.btITaskScheduler_activate(this.swigCPtr, this);
    }

    public void deactivate() {
        LinearMathJNI.btITaskScheduler_deactivate(this.swigCPtr, this);
    }
}
