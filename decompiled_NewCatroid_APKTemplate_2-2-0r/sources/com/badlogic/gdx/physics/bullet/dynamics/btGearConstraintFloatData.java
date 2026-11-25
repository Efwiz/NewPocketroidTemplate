package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btGearConstraintFloatData extends BulletBase {
    private long swigCPtr;

    protected btGearConstraintFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGearConstraintFloatData(long j, boolean z) {
        this(DynamicsConstants.btGearConstraintDataName, j, z);
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

    public static long getCPtr(btGearConstraintFloatData btgearconstraintfloatdata) {
        if (btgearconstraintfloatdata == null) {
            return 0L;
        }
        return btgearconstraintfloatdata.swigCPtr;
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
                DynamicsJNI.delete_btGearConstraintFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintFloatData bttypedconstraintfloatdata) {
        DynamicsJNI.btGearConstraintFloatData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintFloatData.getCPtr(bttypedconstraintfloatdata), bttypedconstraintfloatdata);
    }

    public btTypedConstraintFloatData getTypeConstraintData() {
        long jBtGearConstraintFloatData_typeConstraintData_get = DynamicsJNI.btGearConstraintFloatData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtGearConstraintFloatData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintFloatData(jBtGearConstraintFloatData_typeConstraintData_get, false);
    }

    public void setAxisInA(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGearConstraintFloatData_axisInA_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAxisInA() {
        long jBtGearConstraintFloatData_axisInA_get = DynamicsJNI.btGearConstraintFloatData_axisInA_get(this.swigCPtr, this);
        if (jBtGearConstraintFloatData_axisInA_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGearConstraintFloatData_axisInA_get, false);
    }

    public void setAxisInB(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGearConstraintFloatData_axisInB_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAxisInB() {
        long jBtGearConstraintFloatData_axisInB_get = DynamicsJNI.btGearConstraintFloatData_axisInB_get(this.swigCPtr, this);
        if (jBtGearConstraintFloatData_axisInB_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGearConstraintFloatData_axisInB_get, false);
    }

    public void setRatio(float f) {
        DynamicsJNI.btGearConstraintFloatData_ratio_set(this.swigCPtr, this, f);
    }

    public float getRatio() {
        return DynamicsJNI.btGearConstraintFloatData_ratio_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        DynamicsJNI.btGearConstraintFloatData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return DynamicsJNI.btGearConstraintFloatData_padding_get(this.swigCPtr, this);
    }

    public btGearConstraintFloatData() {
        this(DynamicsJNI.new_btGearConstraintFloatData(), true);
    }
}
