package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btRotationalLimitMotor extends BulletBase {
    private long swigCPtr;

    protected btRotationalLimitMotor(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btRotationalLimitMotor(long j, boolean z) {
        this("btRotationalLimitMotor", j, z);
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

    public static long getCPtr(btRotationalLimitMotor btrotationallimitmotor) {
        if (btrotationallimitmotor == null) {
            return 0L;
        }
        return btrotationallimitmotor.swigCPtr;
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
                DynamicsJNI.delete_btRotationalLimitMotor(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setLoLimit(float f) {
        DynamicsJNI.btRotationalLimitMotor_loLimit_set(this.swigCPtr, this, f);
    }

    public float getLoLimit() {
        return DynamicsJNI.btRotationalLimitMotor_loLimit_get(this.swigCPtr, this);
    }

    public void setHiLimit(float f) {
        DynamicsJNI.btRotationalLimitMotor_hiLimit_set(this.swigCPtr, this, f);
    }

    public float getHiLimit() {
        return DynamicsJNI.btRotationalLimitMotor_hiLimit_get(this.swigCPtr, this);
    }

    public void setTargetVelocity(float f) {
        DynamicsJNI.btRotationalLimitMotor_targetVelocity_set(this.swigCPtr, this, f);
    }

    public float getTargetVelocity() {
        return DynamicsJNI.btRotationalLimitMotor_targetVelocity_get(this.swigCPtr, this);
    }

    public void setMaxMotorForce(float f) {
        DynamicsJNI.btRotationalLimitMotor_maxMotorForce_set(this.swigCPtr, this, f);
    }

    public float getMaxMotorForce() {
        return DynamicsJNI.btRotationalLimitMotor_maxMotorForce_get(this.swigCPtr, this);
    }

    public void setMaxLimitForce(float f) {
        DynamicsJNI.btRotationalLimitMotor_maxLimitForce_set(this.swigCPtr, this, f);
    }

    public float getMaxLimitForce() {
        return DynamicsJNI.btRotationalLimitMotor_maxLimitForce_get(this.swigCPtr, this);
    }

    public void setDamping(float f) {
        DynamicsJNI.btRotationalLimitMotor_damping_set(this.swigCPtr, this, f);
    }

    public float getDamping() {
        return DynamicsJNI.btRotationalLimitMotor_damping_get(this.swigCPtr, this);
    }

    public void setLimitSoftness(float f) {
        DynamicsJNI.btRotationalLimitMotor_limitSoftness_set(this.swigCPtr, this, f);
    }

    public float getLimitSoftness() {
        return DynamicsJNI.btRotationalLimitMotor_limitSoftness_get(this.swigCPtr, this);
    }

    public void setNormalCFM(float f) {
        DynamicsJNI.btRotationalLimitMotor_normalCFM_set(this.swigCPtr, this, f);
    }

    public float getNormalCFM() {
        return DynamicsJNI.btRotationalLimitMotor_normalCFM_get(this.swigCPtr, this);
    }

    public void setStopERP(float f) {
        DynamicsJNI.btRotationalLimitMotor_stopERP_set(this.swigCPtr, this, f);
    }

    public float getStopERP() {
        return DynamicsJNI.btRotationalLimitMotor_stopERP_get(this.swigCPtr, this);
    }

    public void setStopCFM(float f) {
        DynamicsJNI.btRotationalLimitMotor_stopCFM_set(this.swigCPtr, this, f);
    }

    public float getStopCFM() {
        return DynamicsJNI.btRotationalLimitMotor_stopCFM_get(this.swigCPtr, this);
    }

    public void setBounce(float f) {
        DynamicsJNI.btRotationalLimitMotor_bounce_set(this.swigCPtr, this, f);
    }

    public float getBounce() {
        return DynamicsJNI.btRotationalLimitMotor_bounce_get(this.swigCPtr, this);
    }

    public void setEnableMotor(boolean z) {
        DynamicsJNI.btRotationalLimitMotor_enableMotor_set(this.swigCPtr, this, z);
    }

    public boolean getEnableMotor() {
        return DynamicsJNI.btRotationalLimitMotor_enableMotor_get(this.swigCPtr, this);
    }

    public void setCurrentLimitError(float f) {
        DynamicsJNI.btRotationalLimitMotor_currentLimitError_set(this.swigCPtr, this, f);
    }

    public float getCurrentLimitError() {
        return DynamicsJNI.btRotationalLimitMotor_currentLimitError_get(this.swigCPtr, this);
    }

    public void setCurrentPosition(float f) {
        DynamicsJNI.btRotationalLimitMotor_currentPosition_set(this.swigCPtr, this, f);
    }

    public float getCurrentPosition() {
        return DynamicsJNI.btRotationalLimitMotor_currentPosition_get(this.swigCPtr, this);
    }

    public void setCurrentLimit(int i) {
        DynamicsJNI.btRotationalLimitMotor_currentLimit_set(this.swigCPtr, this, i);
    }

    public int getCurrentLimit() {
        return DynamicsJNI.btRotationalLimitMotor_currentLimit_get(this.swigCPtr, this);
    }

    public void setAccumulatedImpulse(float f) {
        DynamicsJNI.btRotationalLimitMotor_accumulatedImpulse_set(this.swigCPtr, this, f);
    }

    public float getAccumulatedImpulse() {
        return DynamicsJNI.btRotationalLimitMotor_accumulatedImpulse_get(this.swigCPtr, this);
    }

    public btRotationalLimitMotor() {
        this(DynamicsJNI.new_btRotationalLimitMotor__SWIG_0(), true);
    }

    public btRotationalLimitMotor(btRotationalLimitMotor btrotationallimitmotor) {
        this(DynamicsJNI.new_btRotationalLimitMotor__SWIG_1(getCPtr(btrotationallimitmotor), btrotationallimitmotor), true);
    }

    public boolean isLimited() {
        return DynamicsJNI.btRotationalLimitMotor_isLimited(this.swigCPtr, this);
    }

    public boolean needApplyTorques() {
        return DynamicsJNI.btRotationalLimitMotor_needApplyTorques(this.swigCPtr, this);
    }

    public int testLimitValue(float f) {
        return DynamicsJNI.btRotationalLimitMotor_testLimitValue(this.swigCPtr, this, f);
    }

    public float solveAngularLimits(float f, Vector3 vector3, float f2, btRigidBody btrigidbody, btRigidBody btrigidbody2) {
        return DynamicsJNI.btRotationalLimitMotor_solveAngularLimits(this.swigCPtr, this, f, vector3, f2, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2);
    }
}
