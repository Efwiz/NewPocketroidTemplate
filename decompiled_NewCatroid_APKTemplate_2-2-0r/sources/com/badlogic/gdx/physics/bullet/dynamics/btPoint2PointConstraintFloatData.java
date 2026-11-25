package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btPoint2PointConstraintFloatData extends BulletBase {
    private long swigCPtr;

    protected btPoint2PointConstraintFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPoint2PointConstraintFloatData(long j, boolean z) {
        this(DynamicsConstants.btPoint2PointConstraintDataName, j, z);
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

    public static long getCPtr(btPoint2PointConstraintFloatData btpoint2pointconstraintfloatdata) {
        if (btpoint2pointconstraintfloatdata == null) {
            return 0L;
        }
        return btpoint2pointconstraintfloatdata.swigCPtr;
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
                DynamicsJNI.delete_btPoint2PointConstraintFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintData bttypedconstraintdata) {
        DynamicsJNI.btPoint2PointConstraintFloatData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintData.getCPtr(bttypedconstraintdata), bttypedconstraintdata);
    }

    public btTypedConstraintData getTypeConstraintData() {
        long jBtPoint2PointConstraintFloatData_typeConstraintData_get = DynamicsJNI.btPoint2PointConstraintFloatData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtPoint2PointConstraintFloatData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintData(jBtPoint2PointConstraintFloatData_typeConstraintData_get, false);
    }

    public void setPivotInA(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btPoint2PointConstraintFloatData_pivotInA_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getPivotInA() {
        long jBtPoint2PointConstraintFloatData_pivotInA_get = DynamicsJNI.btPoint2PointConstraintFloatData_pivotInA_get(this.swigCPtr, this);
        if (jBtPoint2PointConstraintFloatData_pivotInA_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtPoint2PointConstraintFloatData_pivotInA_get, false);
    }

    public void setPivotInB(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btPoint2PointConstraintFloatData_pivotInB_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getPivotInB() {
        long jBtPoint2PointConstraintFloatData_pivotInB_get = DynamicsJNI.btPoint2PointConstraintFloatData_pivotInB_get(this.swigCPtr, this);
        if (jBtPoint2PointConstraintFloatData_pivotInB_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtPoint2PointConstraintFloatData_pivotInB_get, false);
    }

    public btPoint2PointConstraintFloatData() {
        this(DynamicsJNI.new_btPoint2PointConstraintFloatData(), true);
    }
}
