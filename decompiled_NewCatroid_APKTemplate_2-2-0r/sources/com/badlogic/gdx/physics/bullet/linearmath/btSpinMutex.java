package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btSpinMutex extends BulletBase {
    private long swigCPtr;

    protected btSpinMutex(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSpinMutex(long j, boolean z) {
        this("btSpinMutex", j, z);
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

    public static long getCPtr(btSpinMutex btspinmutex) {
        if (btspinmutex == null) {
            return 0L;
        }
        return btspinmutex.swigCPtr;
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
                LinearMathJNI.delete_btSpinMutex(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSpinMutex() {
        this(LinearMathJNI.new_btSpinMutex(), true);
    }

    public void lock() {
        LinearMathJNI.btSpinMutex_lock(this.swigCPtr, this);
    }

    public void unlock() {
        LinearMathJNI.btSpinMutex_unlock(this.swigCPtr, this);
    }

    public boolean tryLock() {
        return LinearMathJNI.btSpinMutex_tryLock(this.swigCPtr, this);
    }
}
