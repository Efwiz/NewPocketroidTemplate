package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes8.dex */
public class btClock extends BulletBase {
    private long swigCPtr;

    protected btClock(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btClock(long j, boolean z) {
        this("btClock", j, z);
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

    public static long getCPtr(btClock btclock) {
        if (btclock == null) {
            return 0L;
        }
        return btclock.swigCPtr;
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
                LinearMathJNI.delete_btClock(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btClock() {
        this(LinearMathJNI.new_btClock__SWIG_0(), true);
    }

    public btClock(btClock btclock) {
        this(LinearMathJNI.new_btClock__SWIG_1(getCPtr(btclock), btclock), true);
    }

    public btClock operatorAssignment(btClock btclock) {
        return new btClock(LinearMathJNI.btClock_operatorAssignment(this.swigCPtr, this, getCPtr(btclock), btclock), false);
    }

    public void reset() {
        LinearMathJNI.btClock_reset(this.swigCPtr, this);
    }

    public BigInteger getTimeMilliseconds() {
        return LinearMathJNI.btClock_getTimeMilliseconds(this.swigCPtr, this);
    }

    public BigInteger getTimeMicroseconds() {
        return LinearMathJNI.btClock_getTimeMicroseconds(this.swigCPtr, this);
    }

    public BigInteger getTimeNanoseconds() {
        return LinearMathJNI.btClock_getTimeNanoseconds(this.swigCPtr, this);
    }

    public float getTimeSeconds() {
        return LinearMathJNI.btClock_getTimeSeconds(this.swigCPtr, this);
    }
}
