package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btQuaternionDoubleData extends BulletBase {
    private long swigCPtr;

    protected btQuaternionDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btQuaternionDoubleData(long j, boolean z) {
        this("btQuaternionDoubleData", j, z);
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

    public static long getCPtr(btQuaternionDoubleData btquaterniondoubledata) {
        if (btquaterniondoubledata == null) {
            return 0L;
        }
        return btquaterniondoubledata.swigCPtr;
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
                LinearMathJNI.delete_btQuaternionDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setFloats(double[] dArr) {
        LinearMathJNI.btQuaternionDoubleData_floats_set(this.swigCPtr, this, dArr);
    }

    public double[] getFloats() {
        return LinearMathJNI.btQuaternionDoubleData_floats_get(this.swigCPtr, this);
    }

    public btQuaternionDoubleData() {
        this(LinearMathJNI.new_btQuaternionDoubleData(), true);
    }
}
