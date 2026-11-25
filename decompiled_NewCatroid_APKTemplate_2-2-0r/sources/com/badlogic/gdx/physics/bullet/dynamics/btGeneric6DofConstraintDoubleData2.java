package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btGeneric6DofConstraintDoubleData2 extends BulletBase {
    private long swigCPtr;

    protected btGeneric6DofConstraintDoubleData2(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGeneric6DofConstraintDoubleData2(long j, boolean z) {
        this("btGeneric6DofConstraintDoubleData2", j, z);
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

    public static long getCPtr(btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2) {
        if (btgeneric6dofconstraintdoubledata2 == null) {
            return 0L;
        }
        return btgeneric6dofconstraintdoubledata2.swigCPtr;
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
                DynamicsJNI.delete_btGeneric6DofConstraintDoubleData2(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintDoubleData bttypedconstraintdoubledata) {
        DynamicsJNI.btGeneric6DofConstraintDoubleData2_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintDoubleData.getCPtr(bttypedconstraintdoubledata), bttypedconstraintdoubledata);
    }

    public btTypedConstraintDoubleData getTypeConstraintData() {
        long jBtGeneric6DofConstraintDoubleData2_typeConstraintData_get = DynamicsJNI.btGeneric6DofConstraintDoubleData2_typeConstraintData_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintDoubleData2_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintDoubleData(jBtGeneric6DofConstraintDoubleData2_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btGeneric6DofConstraintDoubleData2_rbAFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbAFrame() {
        long jBtGeneric6DofConstraintDoubleData2_rbAFrame_get = DynamicsJNI.btGeneric6DofConstraintDoubleData2_rbAFrame_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintDoubleData2_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtGeneric6DofConstraintDoubleData2_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btGeneric6DofConstraintDoubleData2_rbBFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbBFrame() {
        long jBtGeneric6DofConstraintDoubleData2_rbBFrame_get = DynamicsJNI.btGeneric6DofConstraintDoubleData2_rbBFrame_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintDoubleData2_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtGeneric6DofConstraintDoubleData2_rbBFrame_get, false);
    }

    public void setLinearUpperLimit(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofConstraintDoubleData2_linearUpperLimit_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearUpperLimit() {
        long jBtGeneric6DofConstraintDoubleData2_linearUpperLimit_get = DynamicsJNI.btGeneric6DofConstraintDoubleData2_linearUpperLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintDoubleData2_linearUpperLimit_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofConstraintDoubleData2_linearUpperLimit_get, false);
    }

    public void setLinearLowerLimit(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofConstraintDoubleData2_linearLowerLimit_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearLowerLimit() {
        long jBtGeneric6DofConstraintDoubleData2_linearLowerLimit_get = DynamicsJNI.btGeneric6DofConstraintDoubleData2_linearLowerLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintDoubleData2_linearLowerLimit_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofConstraintDoubleData2_linearLowerLimit_get, false);
    }

    public void setAngularUpperLimit(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofConstraintDoubleData2_angularUpperLimit_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularUpperLimit() {
        long jBtGeneric6DofConstraintDoubleData2_angularUpperLimit_get = DynamicsJNI.btGeneric6DofConstraintDoubleData2_angularUpperLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintDoubleData2_angularUpperLimit_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofConstraintDoubleData2_angularUpperLimit_get, false);
    }

    public void setAngularLowerLimit(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofConstraintDoubleData2_angularLowerLimit_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularLowerLimit() {
        long jBtGeneric6DofConstraintDoubleData2_angularLowerLimit_get = DynamicsJNI.btGeneric6DofConstraintDoubleData2_angularLowerLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintDoubleData2_angularLowerLimit_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofConstraintDoubleData2_angularLowerLimit_get, false);
    }

    public void setUseLinearReferenceFrameA(int i) {
        DynamicsJNI.btGeneric6DofConstraintDoubleData2_useLinearReferenceFrameA_set(this.swigCPtr, this, i);
    }

    public int getUseLinearReferenceFrameA() {
        return DynamicsJNI.btGeneric6DofConstraintDoubleData2_useLinearReferenceFrameA_get(this.swigCPtr, this);
    }

    public void setUseOffsetForConstraintFrame(int i) {
        DynamicsJNI.btGeneric6DofConstraintDoubleData2_useOffsetForConstraintFrame_set(this.swigCPtr, this, i);
    }

    public int getUseOffsetForConstraintFrame() {
        return DynamicsJNI.btGeneric6DofConstraintDoubleData2_useOffsetForConstraintFrame_get(this.swigCPtr, this);
    }

    public btGeneric6DofConstraintDoubleData2() {
        this(DynamicsJNI.new_btGeneric6DofConstraintDoubleData2(), true);
    }
}
