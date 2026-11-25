package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btRotationalLimitMotor2 extends BulletBase {
    private long swigCPtr;

    protected btRotationalLimitMotor2(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btRotationalLimitMotor2(long j, boolean z) {
        this("btRotationalLimitMotor2", j, z);
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

    public static long getCPtr(btRotationalLimitMotor2 btrotationallimitmotor2) {
        if (btrotationallimitmotor2 == null) {
            return 0L;
        }
        return btrotationallimitmotor2.swigCPtr;
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
                DynamicsJNI.delete_btRotationalLimitMotor2(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setLoLimit(float f) {
        DynamicsJNI.btRotationalLimitMotor2_loLimit_set(this.swigCPtr, this, f);
    }

    public float getLoLimit() {
        return DynamicsJNI.btRotationalLimitMotor2_loLimit_get(this.swigCPtr, this);
    }

    public void setHiLimit(float f) {
        DynamicsJNI.btRotationalLimitMotor2_hiLimit_set(this.swigCPtr, this, f);
    }

    public float getHiLimit() {
        return DynamicsJNI.btRotationalLimitMotor2_hiLimit_get(this.swigCPtr, this);
    }

    public void setBounce(float f) {
        DynamicsJNI.btRotationalLimitMotor2_bounce_set(this.swigCPtr, this, f);
    }

    public float getBounce() {
        return DynamicsJNI.btRotationalLimitMotor2_bounce_get(this.swigCPtr, this);
    }

    public void setStopERP(float f) {
        DynamicsJNI.btRotationalLimitMotor2_stopERP_set(this.swigCPtr, this, f);
    }

    public float getStopERP() {
        return DynamicsJNI.btRotationalLimitMotor2_stopERP_get(this.swigCPtr, this);
    }

    public void setStopCFM(float f) {
        DynamicsJNI.btRotationalLimitMotor2_stopCFM_set(this.swigCPtr, this, f);
    }

    public float getStopCFM() {
        return DynamicsJNI.btRotationalLimitMotor2_stopCFM_get(this.swigCPtr, this);
    }

    public void setMotorERP(float f) {
        DynamicsJNI.btRotationalLimitMotor2_motorERP_set(this.swigCPtr, this, f);
    }

    public float getMotorERP() {
        return DynamicsJNI.btRotationalLimitMotor2_motorERP_get(this.swigCPtr, this);
    }

    public void setMotorCFM(float f) {
        DynamicsJNI.btRotationalLimitMotor2_motorCFM_set(this.swigCPtr, this, f);
    }

    public float getMotorCFM() {
        return DynamicsJNI.btRotationalLimitMotor2_motorCFM_get(this.swigCPtr, this);
    }

    public void setEnableMotor(boolean z) {
        DynamicsJNI.btRotationalLimitMotor2_enableMotor_set(this.swigCPtr, this, z);
    }

    public boolean getEnableMotor() {
        return DynamicsJNI.btRotationalLimitMotor2_enableMotor_get(this.swigCPtr, this);
    }

    public void setTargetVelocity(float f) {
        DynamicsJNI.btRotationalLimitMotor2_targetVelocity_set(this.swigCPtr, this, f);
    }

    public float getTargetVelocity() {
        return DynamicsJNI.btRotationalLimitMotor2_targetVelocity_get(this.swigCPtr, this);
    }

    public void setMaxMotorForce(float f) {
        DynamicsJNI.btRotationalLimitMotor2_maxMotorForce_set(this.swigCPtr, this, f);
    }

    public float getMaxMotorForce() {
        return DynamicsJNI.btRotationalLimitMotor2_maxMotorForce_get(this.swigCPtr, this);
    }

    public void setServoMotor(boolean z) {
        DynamicsJNI.btRotationalLimitMotor2_servoMotor_set(this.swigCPtr, this, z);
    }

    public boolean getServoMotor() {
        return DynamicsJNI.btRotationalLimitMotor2_servoMotor_get(this.swigCPtr, this);
    }

    public void setServoTarget(float f) {
        DynamicsJNI.btRotationalLimitMotor2_servoTarget_set(this.swigCPtr, this, f);
    }

    public float getServoTarget() {
        return DynamicsJNI.btRotationalLimitMotor2_servoTarget_get(this.swigCPtr, this);
    }

    public void setEnableSpring(boolean z) {
        DynamicsJNI.btRotationalLimitMotor2_enableSpring_set(this.swigCPtr, this, z);
    }

    public boolean getEnableSpring() {
        return DynamicsJNI.btRotationalLimitMotor2_enableSpring_get(this.swigCPtr, this);
    }

    public void setSpringStiffness(float f) {
        DynamicsJNI.btRotationalLimitMotor2_springStiffness_set(this.swigCPtr, this, f);
    }

    public float getSpringStiffness() {
        return DynamicsJNI.btRotationalLimitMotor2_springStiffness_get(this.swigCPtr, this);
    }

    public void setSpringStiffnessLimited(boolean z) {
        DynamicsJNI.btRotationalLimitMotor2_springStiffnessLimited_set(this.swigCPtr, this, z);
    }

    public boolean getSpringStiffnessLimited() {
        return DynamicsJNI.btRotationalLimitMotor2_springStiffnessLimited_get(this.swigCPtr, this);
    }

    public void setSpringDamping(float f) {
        DynamicsJNI.btRotationalLimitMotor2_springDamping_set(this.swigCPtr, this, f);
    }

    public float getSpringDamping() {
        return DynamicsJNI.btRotationalLimitMotor2_springDamping_get(this.swigCPtr, this);
    }

    public void setSpringDampingLimited(boolean z) {
        DynamicsJNI.btRotationalLimitMotor2_springDampingLimited_set(this.swigCPtr, this, z);
    }

    public boolean getSpringDampingLimited() {
        return DynamicsJNI.btRotationalLimitMotor2_springDampingLimited_get(this.swigCPtr, this);
    }

    public void setEquilibriumPoint(float f) {
        DynamicsJNI.btRotationalLimitMotor2_equilibriumPoint_set(this.swigCPtr, this, f);
    }

    public float getEquilibriumPoint() {
        return DynamicsJNI.btRotationalLimitMotor2_equilibriumPoint_get(this.swigCPtr, this);
    }

    public void setCurrentLimitError(float f) {
        DynamicsJNI.btRotationalLimitMotor2_currentLimitError_set(this.swigCPtr, this, f);
    }

    public float getCurrentLimitError() {
        return DynamicsJNI.btRotationalLimitMotor2_currentLimitError_get(this.swigCPtr, this);
    }

    public void setCurrentLimitErrorHi(float f) {
        DynamicsJNI.btRotationalLimitMotor2_currentLimitErrorHi_set(this.swigCPtr, this, f);
    }

    public float getCurrentLimitErrorHi() {
        return DynamicsJNI.btRotationalLimitMotor2_currentLimitErrorHi_get(this.swigCPtr, this);
    }

    public void setCurrentPosition(float f) {
        DynamicsJNI.btRotationalLimitMotor2_currentPosition_set(this.swigCPtr, this, f);
    }

    public float getCurrentPosition() {
        return DynamicsJNI.btRotationalLimitMotor2_currentPosition_get(this.swigCPtr, this);
    }

    public void setCurrentLimit(int i) {
        DynamicsJNI.btRotationalLimitMotor2_currentLimit_set(this.swigCPtr, this, i);
    }

    public int getCurrentLimit() {
        return DynamicsJNI.btRotationalLimitMotor2_currentLimit_get(this.swigCPtr, this);
    }

    public btRotationalLimitMotor2() {
        this(DynamicsJNI.new_btRotationalLimitMotor2__SWIG_0(), true);
    }

    public btRotationalLimitMotor2(btRotationalLimitMotor2 btrotationallimitmotor2) {
        this(DynamicsJNI.new_btRotationalLimitMotor2__SWIG_1(getCPtr(btrotationallimitmotor2), btrotationallimitmotor2), true);
    }

    public boolean isLimited() {
        return DynamicsJNI.btRotationalLimitMotor2_isLimited(this.swigCPtr, this);
    }

    public void testLimitValue(float f) {
        DynamicsJNI.btRotationalLimitMotor2_testLimitValue(this.swigCPtr, this, f);
    }
}
