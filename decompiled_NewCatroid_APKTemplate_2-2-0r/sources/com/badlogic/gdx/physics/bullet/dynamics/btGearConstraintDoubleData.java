package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btGearConstraintDoubleData extends BulletBase {
    private long swigCPtr;

    protected btGearConstraintDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGearConstraintDoubleData(long j, boolean z) {
        this("btGearConstraintDoubleData", j, z);
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

    public static long getCPtr(btGearConstraintDoubleData btgearconstraintdoubledata) {
        if (btgearconstraintdoubledata == null) {
            return 0L;
        }
        return btgearconstraintdoubledata.swigCPtr;
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
                DynamicsJNI.delete_btGearConstraintDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintDoubleData bttypedconstraintdoubledata) {
        DynamicsJNI.btGearConstraintDoubleData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintDoubleData.getCPtr(bttypedconstraintdoubledata), bttypedconstraintdoubledata);
    }

    public btTypedConstraintDoubleData getTypeConstraintData() {
        long jBtGearConstraintDoubleData_typeConstraintData_get = DynamicsJNI.btGearConstraintDoubleData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtGearConstraintDoubleData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintDoubleData(jBtGearConstraintDoubleData_typeConstraintData_get, false);
    }

    public void setAxisInA(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGearConstraintDoubleData_axisInA_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAxisInA() {
        long jBtGearConstraintDoubleData_axisInA_get = DynamicsJNI.btGearConstraintDoubleData_axisInA_get(this.swigCPtr, this);
        if (jBtGearConstraintDoubleData_axisInA_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGearConstraintDoubleData_axisInA_get, false);
    }

    public void setAxisInB(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGearConstraintDoubleData_axisInB_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAxisInB() {
        long jBtGearConstraintDoubleData_axisInB_get = DynamicsJNI.btGearConstraintDoubleData_axisInB_get(this.swigCPtr, this);
        if (jBtGearConstraintDoubleData_axisInB_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGearConstraintDoubleData_axisInB_get, false);
    }

    public void setRatio(double d) {
        DynamicsJNI.btGearConstraintDoubleData_ratio_set(this.swigCPtr, this, d);
    }

    public double getRatio() {
        return DynamicsJNI.btGearConstraintDoubleData_ratio_get(this.swigCPtr, this);
    }

    public btGearConstraintDoubleData() {
        this(DynamicsJNI.new_btGearConstraintDoubleData(), true);
    }
}
