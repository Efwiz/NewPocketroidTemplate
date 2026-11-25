package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btVector3FloatData extends BulletBase {
    private long swigCPtr;

    protected btVector3FloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btVector3FloatData(long j, boolean z) {
        this(LinearMathConstants.btVector3DataName, j, z);
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

    public static long getCPtr(btVector3FloatData btvector3floatdata) {
        if (btvector3floatdata == null) {
            return 0L;
        }
        return btvector3floatdata.swigCPtr;
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
                LinearMathJNI.delete_btVector3FloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setFloats(float[] fArr) {
        LinearMathJNI.btVector3FloatData_floats_set(this.swigCPtr, this, fArr);
    }

    public float[] getFloats() {
        return LinearMathJNI.btVector3FloatData_floats_get(this.swigCPtr, this);
    }

    public btVector3FloatData() {
        this(LinearMathJNI.new_btVector3FloatData(), true);
    }
}
