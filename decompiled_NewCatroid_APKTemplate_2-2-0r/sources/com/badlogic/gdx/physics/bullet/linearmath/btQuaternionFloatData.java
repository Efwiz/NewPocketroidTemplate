package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btQuaternionFloatData extends BulletBase {
    private long swigCPtr;

    protected btQuaternionFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btQuaternionFloatData(long j, boolean z) {
        this(LinearMathConstants.btQuaternionDataName, j, z);
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

    public static long getCPtr(btQuaternionFloatData btquaternionfloatdata) {
        if (btquaternionfloatdata == null) {
            return 0L;
        }
        return btquaternionfloatdata.swigCPtr;
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
                LinearMathJNI.delete_btQuaternionFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setFloats(float[] fArr) {
        LinearMathJNI.btQuaternionFloatData_floats_set(this.swigCPtr, this, fArr);
    }

    public float[] getFloats() {
        return LinearMathJNI.btQuaternionFloatData_floats_get(this.swigCPtr, this);
    }

    public btQuaternionFloatData() {
        this(LinearMathJNI.new_btQuaternionFloatData(), true);
    }
}
