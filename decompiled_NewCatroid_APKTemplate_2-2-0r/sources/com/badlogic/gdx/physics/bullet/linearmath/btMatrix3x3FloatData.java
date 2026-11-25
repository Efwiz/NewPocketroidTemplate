package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btMatrix3x3FloatData extends BulletBase {
    private long swigCPtr;

    protected btMatrix3x3FloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMatrix3x3FloatData(long j, boolean z) {
        this("btMatrix3x3FloatData", j, z);
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

    public static long getCPtr(btMatrix3x3FloatData btmatrix3x3floatdata) {
        if (btmatrix3x3floatdata == null) {
            return 0L;
        }
        return btmatrix3x3floatdata.swigCPtr;
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
                LinearMathJNI.delete_btMatrix3x3FloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setEl(btVector3FloatData btvector3floatdata) {
        LinearMathJNI.btMatrix3x3FloatData_el_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getEl() {
        long jBtMatrix3x3FloatData_el_get = LinearMathJNI.btMatrix3x3FloatData_el_get(this.swigCPtr, this);
        if (jBtMatrix3x3FloatData_el_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtMatrix3x3FloatData_el_get, false);
    }

    public btMatrix3x3FloatData() {
        this(LinearMathJNI.new_btMatrix3x3FloatData(), true);
    }
}
