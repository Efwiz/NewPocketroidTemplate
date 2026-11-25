package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btPoint2PointConstraintDoubleData extends BulletBase {
    private long swigCPtr;

    protected btPoint2PointConstraintDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPoint2PointConstraintDoubleData(long j, boolean z) {
        this("btPoint2PointConstraintDoubleData", j, z);
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

    public static long getCPtr(btPoint2PointConstraintDoubleData btpoint2pointconstraintdoubledata) {
        if (btpoint2pointconstraintdoubledata == null) {
            return 0L;
        }
        return btpoint2pointconstraintdoubledata.swigCPtr;
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
                DynamicsJNI.delete_btPoint2PointConstraintDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintData bttypedconstraintdata) {
        DynamicsJNI.btPoint2PointConstraintDoubleData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintData.getCPtr(bttypedconstraintdata), bttypedconstraintdata);
    }

    public btTypedConstraintData getTypeConstraintData() {
        long jBtPoint2PointConstraintDoubleData_typeConstraintData_get = DynamicsJNI.btPoint2PointConstraintDoubleData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtPoint2PointConstraintDoubleData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintData(jBtPoint2PointConstraintDoubleData_typeConstraintData_get, false);
    }

    public void setPivotInA(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btPoint2PointConstraintDoubleData_pivotInA_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getPivotInA() {
        long jBtPoint2PointConstraintDoubleData_pivotInA_get = DynamicsJNI.btPoint2PointConstraintDoubleData_pivotInA_get(this.swigCPtr, this);
        if (jBtPoint2PointConstraintDoubleData_pivotInA_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtPoint2PointConstraintDoubleData_pivotInA_get, false);
    }

    public void setPivotInB(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btPoint2PointConstraintDoubleData_pivotInB_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getPivotInB() {
        long jBtPoint2PointConstraintDoubleData_pivotInB_get = DynamicsJNI.btPoint2PointConstraintDoubleData_pivotInB_get(this.swigCPtr, this);
        if (jBtPoint2PointConstraintDoubleData_pivotInB_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtPoint2PointConstraintDoubleData_pivotInB_get, false);
    }

    public btPoint2PointConstraintDoubleData() {
        this(DynamicsJNI.new_btPoint2PointConstraintDoubleData(), true);
    }
}
