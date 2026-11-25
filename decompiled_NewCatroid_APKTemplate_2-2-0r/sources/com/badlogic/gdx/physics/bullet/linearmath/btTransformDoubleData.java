package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTransformDoubleData extends BulletBase {
    private long swigCPtr;

    protected btTransformDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTransformDoubleData(long j, boolean z) {
        this("btTransformDoubleData", j, z);
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

    public static long getCPtr(btTransformDoubleData bttransformdoubledata) {
        if (bttransformdoubledata == null) {
            return 0L;
        }
        return bttransformdoubledata.swigCPtr;
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
                LinearMathJNI.delete_btTransformDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBasis(btMatrix3x3DoubleData btmatrix3x3doubledata) {
        LinearMathJNI.btTransformDoubleData_basis_set(this.swigCPtr, this, btMatrix3x3DoubleData.getCPtr(btmatrix3x3doubledata), btmatrix3x3doubledata);
    }

    public btMatrix3x3DoubleData getBasis() {
        long jBtTransformDoubleData_basis_get = LinearMathJNI.btTransformDoubleData_basis_get(this.swigCPtr, this);
        if (jBtTransformDoubleData_basis_get == 0) {
            return null;
        }
        return new btMatrix3x3DoubleData(jBtTransformDoubleData_basis_get, false);
    }

    public void setOrigin(btVector3DoubleData btvector3doubledata) {
        LinearMathJNI.btTransformDoubleData_origin_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getOrigin() {
        long jBtTransformDoubleData_origin_get = LinearMathJNI.btTransformDoubleData_origin_get(this.swigCPtr, this);
        if (jBtTransformDoubleData_origin_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtTransformDoubleData_origin_get, false);
    }

    public btTransformDoubleData() {
        this(LinearMathJNI.new_btTransformDoubleData(), true);
    }
}
