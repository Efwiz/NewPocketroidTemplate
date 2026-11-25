package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btMatrix3x3DoubleData extends BulletBase {
    private long swigCPtr;

    protected btMatrix3x3DoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMatrix3x3DoubleData(long j, boolean z) {
        this("btMatrix3x3DoubleData", j, z);
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

    public static long getCPtr(btMatrix3x3DoubleData btmatrix3x3doubledata) {
        if (btmatrix3x3doubledata == null) {
            return 0L;
        }
        return btmatrix3x3doubledata.swigCPtr;
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
                LinearMathJNI.delete_btMatrix3x3DoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setEl(btVector3DoubleData btvector3doubledata) {
        LinearMathJNI.btMatrix3x3DoubleData_el_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getEl() {
        long jBtMatrix3x3DoubleData_el_get = LinearMathJNI.btMatrix3x3DoubleData_el_get(this.swigCPtr, this);
        if (jBtMatrix3x3DoubleData_el_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtMatrix3x3DoubleData_el_get, false);
    }

    public btMatrix3x3DoubleData() {
        this(LinearMathJNI.new_btMatrix3x3DoubleData(), true);
    }
}
