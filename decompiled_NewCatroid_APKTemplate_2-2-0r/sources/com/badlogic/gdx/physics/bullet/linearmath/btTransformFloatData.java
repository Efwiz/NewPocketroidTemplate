package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTransformFloatData extends BulletBase {
    private long swigCPtr;

    protected btTransformFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTransformFloatData(long j, boolean z) {
        this("btTransformFloatData", j, z);
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

    public static long getCPtr(btTransformFloatData bttransformfloatdata) {
        if (bttransformfloatdata == null) {
            return 0L;
        }
        return bttransformfloatdata.swigCPtr;
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
                LinearMathJNI.delete_btTransformFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBasis(btMatrix3x3FloatData btmatrix3x3floatdata) {
        LinearMathJNI.btTransformFloatData_basis_set(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public btMatrix3x3FloatData getBasis() {
        long jBtTransformFloatData_basis_get = LinearMathJNI.btTransformFloatData_basis_get(this.swigCPtr, this);
        if (jBtTransformFloatData_basis_get == 0) {
            return null;
        }
        return new btMatrix3x3FloatData(jBtTransformFloatData_basis_get, false);
    }

    public void setOrigin(btVector3FloatData btvector3floatdata) {
        LinearMathJNI.btTransformFloatData_origin_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getOrigin() {
        long jBtTransformFloatData_origin_get = LinearMathJNI.btTransformFloatData_origin_get(this.swigCPtr, this);
        if (jBtTransformFloatData_origin_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtTransformFloatData_origin_get, false);
    }

    public btTransformFloatData() {
        this(LinearMathJNI.new_btTransformFloatData(), true);
    }
}
