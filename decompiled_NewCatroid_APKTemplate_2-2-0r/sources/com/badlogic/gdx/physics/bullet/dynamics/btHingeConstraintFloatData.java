package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btHingeConstraintFloatData extends BulletBase {
    private long swigCPtr;

    protected btHingeConstraintFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btHingeConstraintFloatData(long j, boolean z) {
        this(DynamicsConstants.btHingeConstraintDataName, j, z);
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

    public static long getCPtr(btHingeConstraintFloatData bthingeconstraintfloatdata) {
        if (bthingeconstraintfloatdata == null) {
            return 0L;
        }
        return bthingeconstraintfloatdata.swigCPtr;
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
                DynamicsJNI.delete_btHingeConstraintFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintData bttypedconstraintdata) {
        DynamicsJNI.btHingeConstraintFloatData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintData.getCPtr(bttypedconstraintdata), bttypedconstraintdata);
    }

    public btTypedConstraintData getTypeConstraintData() {
        long jBtHingeConstraintFloatData_typeConstraintData_get = DynamicsJNI.btHingeConstraintFloatData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtHingeConstraintFloatData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintData(jBtHingeConstraintFloatData_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btHingeConstraintFloatData_rbAFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbAFrame() {
        long jBtHingeConstraintFloatData_rbAFrame_get = DynamicsJNI.btHingeConstraintFloatData_rbAFrame_get(this.swigCPtr, this);
        if (jBtHingeConstraintFloatData_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtHingeConstraintFloatData_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btHingeConstraintFloatData_rbBFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbBFrame() {
        long jBtHingeConstraintFloatData_rbBFrame_get = DynamicsJNI.btHingeConstraintFloatData_rbBFrame_get(this.swigCPtr, this);
        if (jBtHingeConstraintFloatData_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtHingeConstraintFloatData_rbBFrame_get, false);
    }

    public void setUseReferenceFrameA(int i) {
        DynamicsJNI.btHingeConstraintFloatData_useReferenceFrameA_set(this.swigCPtr, this, i);
    }

    public int getUseReferenceFrameA() {
        return DynamicsJNI.btHingeConstraintFloatData_useReferenceFrameA_get(this.swigCPtr, this);
    }

    public void setAngularOnly(int i) {
        DynamicsJNI.btHingeConstraintFloatData_angularOnly_set(this.swigCPtr, this, i);
    }

    public int getAngularOnly() {
        return DynamicsJNI.btHingeConstraintFloatData_angularOnly_get(this.swigCPtr, this);
    }

    public void setEnableAngularMotor(int i) {
        DynamicsJNI.btHingeConstraintFloatData_enableAngularMotor_set(this.swigCPtr, this, i);
    }

    public int getEnableAngularMotor() {
        return DynamicsJNI.btHingeConstraintFloatData_enableAngularMotor_get(this.swigCPtr, this);
    }

    public void setMotorTargetVelocity(float f) {
        DynamicsJNI.btHingeConstraintFloatData_motorTargetVelocity_set(this.swigCPtr, this, f);
    }

    public float getMotorTargetVelocity() {
        return DynamicsJNI.btHingeConstraintFloatData_motorTargetVelocity_get(this.swigCPtr, this);
    }

    public void setMaxMotorImpulse(float f) {
        DynamicsJNI.btHingeConstraintFloatData_maxMotorImpulse_set(this.swigCPtr, this, f);
    }

    public float getMaxMotorImpulse() {
        return DynamicsJNI.btHingeConstraintFloatData_maxMotorImpulse_get(this.swigCPtr, this);
    }

    public void setLowerLimit(float f) {
        DynamicsJNI.btHingeConstraintFloatData_lowerLimit_set(this.swigCPtr, this, f);
    }

    public float getLowerLimit() {
        return DynamicsJNI.btHingeConstraintFloatData_lowerLimit_get(this.swigCPtr, this);
    }

    public void setUpperLimit(float f) {
        DynamicsJNI.btHingeConstraintFloatData_upperLimit_set(this.swigCPtr, this, f);
    }

    public float getUpperLimit() {
        return DynamicsJNI.btHingeConstraintFloatData_upperLimit_get(this.swigCPtr, this);
    }

    public void setLimitSoftness(float f) {
        DynamicsJNI.btHingeConstraintFloatData_limitSoftness_set(this.swigCPtr, this, f);
    }

    public float getLimitSoftness() {
        return DynamicsJNI.btHingeConstraintFloatData_limitSoftness_get(this.swigCPtr, this);
    }

    public void setBiasFactor(float f) {
        DynamicsJNI.btHingeConstraintFloatData_biasFactor_set(this.swigCPtr, this, f);
    }

    public float getBiasFactor() {
        return DynamicsJNI.btHingeConstraintFloatData_biasFactor_get(this.swigCPtr, this);
    }

    public void setRelaxationFactor(float f) {
        DynamicsJNI.btHingeConstraintFloatData_relaxationFactor_set(this.swigCPtr, this, f);
    }

    public float getRelaxationFactor() {
        return DynamicsJNI.btHingeConstraintFloatData_relaxationFactor_get(this.swigCPtr, this);
    }

    public btHingeConstraintFloatData() {
        this(DynamicsJNI.new_btHingeConstraintFloatData(), true);
    }
}
