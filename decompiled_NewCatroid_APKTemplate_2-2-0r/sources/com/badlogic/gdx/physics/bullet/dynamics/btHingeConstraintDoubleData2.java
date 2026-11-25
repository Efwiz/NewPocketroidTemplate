package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btHingeConstraintDoubleData2 extends BulletBase {
    private long swigCPtr;

    protected btHingeConstraintDoubleData2(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btHingeConstraintDoubleData2(long j, boolean z) {
        this("btHingeConstraintDoubleData2", j, z);
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

    public static long getCPtr(btHingeConstraintDoubleData2 bthingeconstraintdoubledata2) {
        if (bthingeconstraintdoubledata2 == null) {
            return 0L;
        }
        return bthingeconstraintdoubledata2.swigCPtr;
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
                DynamicsJNI.delete_btHingeConstraintDoubleData2(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintDoubleData bttypedconstraintdoubledata) {
        DynamicsJNI.btHingeConstraintDoubleData2_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintDoubleData.getCPtr(bttypedconstraintdoubledata), bttypedconstraintdoubledata);
    }

    public btTypedConstraintDoubleData getTypeConstraintData() {
        long jBtHingeConstraintDoubleData2_typeConstraintData_get = DynamicsJNI.btHingeConstraintDoubleData2_typeConstraintData_get(this.swigCPtr, this);
        if (jBtHingeConstraintDoubleData2_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintDoubleData(jBtHingeConstraintDoubleData2_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btHingeConstraintDoubleData2_rbAFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbAFrame() {
        long jBtHingeConstraintDoubleData2_rbAFrame_get = DynamicsJNI.btHingeConstraintDoubleData2_rbAFrame_get(this.swigCPtr, this);
        if (jBtHingeConstraintDoubleData2_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtHingeConstraintDoubleData2_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btHingeConstraintDoubleData2_rbBFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbBFrame() {
        long jBtHingeConstraintDoubleData2_rbBFrame_get = DynamicsJNI.btHingeConstraintDoubleData2_rbBFrame_get(this.swigCPtr, this);
        if (jBtHingeConstraintDoubleData2_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtHingeConstraintDoubleData2_rbBFrame_get, false);
    }

    public void setUseReferenceFrameA(int i) {
        DynamicsJNI.btHingeConstraintDoubleData2_useReferenceFrameA_set(this.swigCPtr, this, i);
    }

    public int getUseReferenceFrameA() {
        return DynamicsJNI.btHingeConstraintDoubleData2_useReferenceFrameA_get(this.swigCPtr, this);
    }

    public void setAngularOnly(int i) {
        DynamicsJNI.btHingeConstraintDoubleData2_angularOnly_set(this.swigCPtr, this, i);
    }

    public int getAngularOnly() {
        return DynamicsJNI.btHingeConstraintDoubleData2_angularOnly_get(this.swigCPtr, this);
    }

    public void setEnableAngularMotor(int i) {
        DynamicsJNI.btHingeConstraintDoubleData2_enableAngularMotor_set(this.swigCPtr, this, i);
    }

    public int getEnableAngularMotor() {
        return DynamicsJNI.btHingeConstraintDoubleData2_enableAngularMotor_get(this.swigCPtr, this);
    }

    public void setMotorTargetVelocity(double d) {
        DynamicsJNI.btHingeConstraintDoubleData2_motorTargetVelocity_set(this.swigCPtr, this, d);
    }

    public double getMotorTargetVelocity() {
        return DynamicsJNI.btHingeConstraintDoubleData2_motorTargetVelocity_get(this.swigCPtr, this);
    }

    public void setMaxMotorImpulse(double d) {
        DynamicsJNI.btHingeConstraintDoubleData2_maxMotorImpulse_set(this.swigCPtr, this, d);
    }

    public double getMaxMotorImpulse() {
        return DynamicsJNI.btHingeConstraintDoubleData2_maxMotorImpulse_get(this.swigCPtr, this);
    }

    public void setLowerLimit(double d) {
        DynamicsJNI.btHingeConstraintDoubleData2_lowerLimit_set(this.swigCPtr, this, d);
    }

    public double getLowerLimit() {
        return DynamicsJNI.btHingeConstraintDoubleData2_lowerLimit_get(this.swigCPtr, this);
    }

    public void setUpperLimit(double d) {
        DynamicsJNI.btHingeConstraintDoubleData2_upperLimit_set(this.swigCPtr, this, d);
    }

    public double getUpperLimit() {
        return DynamicsJNI.btHingeConstraintDoubleData2_upperLimit_get(this.swigCPtr, this);
    }

    public void setLimitSoftness(double d) {
        DynamicsJNI.btHingeConstraintDoubleData2_limitSoftness_set(this.swigCPtr, this, d);
    }

    public double getLimitSoftness() {
        return DynamicsJNI.btHingeConstraintDoubleData2_limitSoftness_get(this.swigCPtr, this);
    }

    public void setBiasFactor(double d) {
        DynamicsJNI.btHingeConstraintDoubleData2_biasFactor_set(this.swigCPtr, this, d);
    }

    public double getBiasFactor() {
        return DynamicsJNI.btHingeConstraintDoubleData2_biasFactor_get(this.swigCPtr, this);
    }

    public void setRelaxationFactor(double d) {
        DynamicsJNI.btHingeConstraintDoubleData2_relaxationFactor_set(this.swigCPtr, this, d);
    }

    public double getRelaxationFactor() {
        return DynamicsJNI.btHingeConstraintDoubleData2_relaxationFactor_get(this.swigCPtr, this);
    }

    public void setPadding1(String str) {
        DynamicsJNI.btHingeConstraintDoubleData2_padding1_set(this.swigCPtr, this, str);
    }

    public String getPadding1() {
        return DynamicsJNI.btHingeConstraintDoubleData2_padding1_get(this.swigCPtr, this);
    }

    public btHingeConstraintDoubleData2() {
        this(DynamicsJNI.new_btHingeConstraintDoubleData2(), true);
    }
}
