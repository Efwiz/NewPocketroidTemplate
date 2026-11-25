package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btVector3DoubleData extends BulletBase {
    private long swigCPtr;

    protected btVector3DoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btVector3DoubleData(long j, boolean z) {
        this("btVector3DoubleData", j, z);
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

    public static long getCPtr(btVector3DoubleData btvector3doubledata) {
        if (btvector3doubledata == null) {
            return 0L;
        }
        return btvector3doubledata.swigCPtr;
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
                LinearMathJNI.delete_btVector3DoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setFloats(double[] dArr) {
        LinearMathJNI.btVector3DoubleData_floats_set(this.swigCPtr, this, dArr);
    }

    public double[] getFloats() {
        return LinearMathJNI.btVector3DoubleData_floats_get(this.swigCPtr, this);
    }

    public btVector3DoubleData() {
        this(LinearMathJNI.new_btVector3DoubleData(), true);
    }
}
