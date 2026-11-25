package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btGeneric6DofConstraintData extends BulletBase {
    private long swigCPtr;

    protected btGeneric6DofConstraintData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGeneric6DofConstraintData(long j, boolean z) {
        this(DynamicsConstants.btGeneric6DofConstraintDataName, j, z);
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

    public static long getCPtr(btGeneric6DofConstraintData btgeneric6dofconstraintdata) {
        if (btgeneric6dofconstraintdata == null) {
            return 0L;
        }
        return btgeneric6dofconstraintdata.swigCPtr;
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
                DynamicsJNI.delete_btGeneric6DofConstraintData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintData bttypedconstraintdata) {
        DynamicsJNI.btGeneric6DofConstraintData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintData.getCPtr(bttypedconstraintdata), bttypedconstraintdata);
    }

    public btTypedConstraintData getTypeConstraintData() {
        long jBtGeneric6DofConstraintData_typeConstraintData_get = DynamicsJNI.btGeneric6DofConstraintData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintData(jBtGeneric6DofConstraintData_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btGeneric6DofConstraintData_rbAFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbAFrame() {
        long jBtGeneric6DofConstraintData_rbAFrame_get = DynamicsJNI.btGeneric6DofConstraintData_rbAFrame_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintData_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtGeneric6DofConstraintData_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btGeneric6DofConstraintData_rbBFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbBFrame() {
        long jBtGeneric6DofConstraintData_rbBFrame_get = DynamicsJNI.btGeneric6DofConstraintData_rbBFrame_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintData_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtGeneric6DofConstraintData_rbBFrame_get, false);
    }

    public void setLinearUpperLimit(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofConstraintData_linearUpperLimit_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearUpperLimit() {
        long jBtGeneric6DofConstraintData_linearUpperLimit_get = DynamicsJNI.btGeneric6DofConstraintData_linearUpperLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintData_linearUpperLimit_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofConstraintData_linearUpperLimit_get, false);
    }

    public void setLinearLowerLimit(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofConstraintData_linearLowerLimit_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearLowerLimit() {
        long jBtGeneric6DofConstraintData_linearLowerLimit_get = DynamicsJNI.btGeneric6DofConstraintData_linearLowerLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintData_linearLowerLimit_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofConstraintData_linearLowerLimit_get, false);
    }

    public void setAngularUpperLimit(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofConstraintData_angularUpperLimit_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularUpperLimit() {
        long jBtGeneric6DofConstraintData_angularUpperLimit_get = DynamicsJNI.btGeneric6DofConstraintData_angularUpperLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintData_angularUpperLimit_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofConstraintData_angularUpperLimit_get, false);
    }

    public void setAngularLowerLimit(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofConstraintData_angularLowerLimit_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularLowerLimit() {
        long jBtGeneric6DofConstraintData_angularLowerLimit_get = DynamicsJNI.btGeneric6DofConstraintData_angularLowerLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofConstraintData_angularLowerLimit_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofConstraintData_angularLowerLimit_get, false);
    }

    public void setUseLinearReferenceFrameA(int i) {
        DynamicsJNI.btGeneric6DofConstraintData_useLinearReferenceFrameA_set(this.swigCPtr, this, i);
    }

    public int getUseLinearReferenceFrameA() {
        return DynamicsJNI.btGeneric6DofConstraintData_useLinearReferenceFrameA_get(this.swigCPtr, this);
    }

    public void setUseOffsetForConstraintFrame(int i) {
        DynamicsJNI.btGeneric6DofConstraintData_useOffsetForConstraintFrame_set(this.swigCPtr, this, i);
    }

    public int getUseOffsetForConstraintFrame() {
        return DynamicsJNI.btGeneric6DofConstraintData_useOffsetForConstraintFrame_get(this.swigCPtr, this);
    }

    public btGeneric6DofConstraintData() {
        this(DynamicsJNI.new_btGeneric6DofConstraintData(), true);
    }
}
