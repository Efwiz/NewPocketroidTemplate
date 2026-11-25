package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btHingeConstraintDoubleData extends BulletBase {
    private long swigCPtr;

    protected btHingeConstraintDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btHingeConstraintDoubleData(long j, boolean z) {
        this("btHingeConstraintDoubleData", j, z);
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

    public static long getCPtr(btHingeConstraintDoubleData bthingeconstraintdoubledata) {
        if (bthingeconstraintdoubledata == null) {
            return 0L;
        }
        return bthingeconstraintdoubledata.swigCPtr;
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
                DynamicsJNI.delete_btHingeConstraintDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintData bttypedconstraintdata) {
        DynamicsJNI.btHingeConstraintDoubleData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintData.getCPtr(bttypedconstraintdata), bttypedconstraintdata);
    }

    public btTypedConstraintData getTypeConstraintData() {
        long jBtHingeConstraintDoubleData_typeConstraintData_get = DynamicsJNI.btHingeConstraintDoubleData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtHingeConstraintDoubleData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintData(jBtHingeConstraintDoubleData_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btHingeConstraintDoubleData_rbAFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbAFrame() {
        long jBtHingeConstraintDoubleData_rbAFrame_get = DynamicsJNI.btHingeConstraintDoubleData_rbAFrame_get(this.swigCPtr, this);
        if (jBtHingeConstraintDoubleData_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtHingeConstraintDoubleData_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btHingeConstraintDoubleData_rbBFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbBFrame() {
        long jBtHingeConstraintDoubleData_rbBFrame_get = DynamicsJNI.btHingeConstraintDoubleData_rbBFrame_get(this.swigCPtr, this);
        if (jBtHingeConstraintDoubleData_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtHingeConstraintDoubleData_rbBFrame_get, false);
    }

    public void setUseReferenceFrameA(int i) {
        DynamicsJNI.btHingeConstraintDoubleData_useReferenceFrameA_set(this.swigCPtr, this, i);
    }

    public int getUseReferenceFrameA() {
        return DynamicsJNI.btHingeConstraintDoubleData_useReferenceFrameA_get(this.swigCPtr, this);
    }

    public void setAngularOnly(int i) {
        DynamicsJNI.btHingeConstraintDoubleData_angularOnly_set(this.swigCPtr, this, i);
    }

    public int getAngularOnly() {
        return DynamicsJNI.btHingeConstraintDoubleData_angularOnly_get(this.swigCPtr, this);
    }

    public void setEnableAngularMotor(int i) {
        DynamicsJNI.btHingeConstraintDoubleData_enableAngularMotor_set(this.swigCPtr, this, i);
    }

    public int getEnableAngularMotor() {
        return DynamicsJNI.btHingeConstraintDoubleData_enableAngularMotor_get(this.swigCPtr, this);
    }

    public void setMotorTargetVelocity(float f) {
        DynamicsJNI.btHingeConstraintDoubleData_motorTargetVelocity_set(this.swigCPtr, this, f);
    }

    public float getMotorTargetVelocity() {
        return DynamicsJNI.btHingeConstraintDoubleData_motorTargetVelocity_get(this.swigCPtr, this);
    }

    public void setMaxMotorImpulse(float f) {
        DynamicsJNI.btHingeConstraintDoubleData_maxMotorImpulse_set(this.swigCPtr, this, f);
    }

    public float getMaxMotorImpulse() {
        return DynamicsJNI.btHingeConstraintDoubleData_maxMotorImpulse_get(this.swigCPtr, this);
    }

    public void setLowerLimit(float f) {
        DynamicsJNI.btHingeConstraintDoubleData_lowerLimit_set(this.swigCPtr, this, f);
    }

    public float getLowerLimit() {
        return DynamicsJNI.btHingeConstraintDoubleData_lowerLimit_get(this.swigCPtr, this);
    }

    public void setUpperLimit(float f) {
        DynamicsJNI.btHingeConstraintDoubleData_upperLimit_set(this.swigCPtr, this, f);
    }

    public float getUpperLimit() {
        return DynamicsJNI.btHingeConstraintDoubleData_upperLimit_get(this.swigCPtr, this);
    }

    public void setLimitSoftness(float f) {
        DynamicsJNI.btHingeConstraintDoubleData_limitSoftness_set(this.swigCPtr, this, f);
    }

    public float getLimitSoftness() {
        return DynamicsJNI.btHingeConstraintDoubleData_limitSoftness_get(this.swigCPtr, this);
    }

    public void setBiasFactor(float f) {
        DynamicsJNI.btHingeConstraintDoubleData_biasFactor_set(this.swigCPtr, this, f);
    }

    public float getBiasFactor() {
        return DynamicsJNI.btHingeConstraintDoubleData_biasFactor_get(this.swigCPtr, this);
    }

    public void setRelaxationFactor(float f) {
        DynamicsJNI.btHingeConstraintDoubleData_relaxationFactor_set(this.swigCPtr, this, f);
    }

    public float getRelaxationFactor() {
        return DynamicsJNI.btHingeConstraintDoubleData_relaxationFactor_get(this.swigCPtr, this);
    }

    public btHingeConstraintDoubleData() {
        this(DynamicsJNI.new_btHingeConstraintDoubleData(), true);
    }
}
