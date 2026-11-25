package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btTranslationalLimitMotor extends BulletBase {
    private long swigCPtr;

    protected btTranslationalLimitMotor(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTranslationalLimitMotor(long j, boolean z) {
        this("btTranslationalLimitMotor", j, z);
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

    public static long getCPtr(btTranslationalLimitMotor bttranslationallimitmotor) {
        if (bttranslationallimitmotor == null) {
            return 0L;
        }
        return bttranslationallimitmotor.swigCPtr;
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
                DynamicsJNI.delete_btTranslationalLimitMotor(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setLowerLimit(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_lowerLimit_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getLowerLimit() {
        long jBtTranslationalLimitMotor_lowerLimit_get = DynamicsJNI.btTranslationalLimitMotor_lowerLimit_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_lowerLimit_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_lowerLimit_get, false);
    }

    public void setUpperLimit(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_upperLimit_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getUpperLimit() {
        long jBtTranslationalLimitMotor_upperLimit_get = DynamicsJNI.btTranslationalLimitMotor_upperLimit_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_upperLimit_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_upperLimit_get, false);
    }

    public void setAccumulatedImpulse(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_accumulatedImpulse_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAccumulatedImpulse() {
        long jBtTranslationalLimitMotor_accumulatedImpulse_get = DynamicsJNI.btTranslationalLimitMotor_accumulatedImpulse_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_accumulatedImpulse_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_accumulatedImpulse_get, false);
    }

    public void setLimitSoftness(float f) {
        DynamicsJNI.btTranslationalLimitMotor_limitSoftness_set(this.swigCPtr, this, f);
    }

    public float getLimitSoftness() {
        return DynamicsJNI.btTranslationalLimitMotor_limitSoftness_get(this.swigCPtr, this);
    }

    public void setDamping(float f) {
        DynamicsJNI.btTranslationalLimitMotor_damping_set(this.swigCPtr, this, f);
    }

    public float getDamping() {
        return DynamicsJNI.btTranslationalLimitMotor_damping_get(this.swigCPtr, this);
    }

    public void setRestitution(float f) {
        DynamicsJNI.btTranslationalLimitMotor_restitution_set(this.swigCPtr, this, f);
    }

    public float getRestitution() {
        return DynamicsJNI.btTranslationalLimitMotor_restitution_get(this.swigCPtr, this);
    }

    public void setNormalCFM(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_normalCFM_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getNormalCFM() {
        long jBtTranslationalLimitMotor_normalCFM_get = DynamicsJNI.btTranslationalLimitMotor_normalCFM_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_normalCFM_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_normalCFM_get, false);
    }

    public void setStopERP(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_stopERP_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getStopERP() {
        long jBtTranslationalLimitMotor_stopERP_get = DynamicsJNI.btTranslationalLimitMotor_stopERP_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_stopERP_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_stopERP_get, false);
    }

    public void setStopCFM(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_stopCFM_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getStopCFM() {
        long jBtTranslationalLimitMotor_stopCFM_get = DynamicsJNI.btTranslationalLimitMotor_stopCFM_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_stopCFM_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_stopCFM_get, false);
    }

    public void setEnableMotor(boolean[] zArr) {
        DynamicsJNI.btTranslationalLimitMotor_enableMotor_set(this.swigCPtr, this, zArr);
    }

    public boolean[] getEnableMotor() {
        return DynamicsJNI.btTranslationalLimitMotor_enableMotor_get(this.swigCPtr, this);
    }

    public void setTargetVelocity(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_targetVelocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getTargetVelocity() {
        long jBtTranslationalLimitMotor_targetVelocity_get = DynamicsJNI.btTranslationalLimitMotor_targetVelocity_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_targetVelocity_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_targetVelocity_get, false);
    }

    public void setMaxMotorForce(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_maxMotorForce_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMaxMotorForce() {
        long jBtTranslationalLimitMotor_maxMotorForce_get = DynamicsJNI.btTranslationalLimitMotor_maxMotorForce_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_maxMotorForce_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_maxMotorForce_get, false);
    }

    public void setCurrentLimitError(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_currentLimitError_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getCurrentLimitError() {
        long jBtTranslationalLimitMotor_currentLimitError_get = DynamicsJNI.btTranslationalLimitMotor_currentLimitError_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_currentLimitError_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_currentLimitError_get, false);
    }

    public void setCurrentLinearDiff(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor_currentLinearDiff_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getCurrentLinearDiff() {
        long jBtTranslationalLimitMotor_currentLinearDiff_get = DynamicsJNI.btTranslationalLimitMotor_currentLinearDiff_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor_currentLinearDiff_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor_currentLinearDiff_get, false);
    }

    public void setCurrentLimit(int[] iArr) {
        DynamicsJNI.btTranslationalLimitMotor_currentLimit_set(this.swigCPtr, this, iArr);
    }

    public int[] getCurrentLimit() {
        return DynamicsJNI.btTranslationalLimitMotor_currentLimit_get(this.swigCPtr, this);
    }

    public btTranslationalLimitMotor() {
        this(DynamicsJNI.new_btTranslationalLimitMotor__SWIG_0(), true);
    }

    public btTranslationalLimitMotor(btTranslationalLimitMotor bttranslationallimitmotor) {
        this(DynamicsJNI.new_btTranslationalLimitMotor__SWIG_1(getCPtr(bttranslationallimitmotor), bttranslationallimitmotor), true);
    }

    public boolean isLimited(int i) {
        return DynamicsJNI.btTranslationalLimitMotor_isLimited(this.swigCPtr, this, i);
    }

    public boolean needApplyForce(int i) {
        return DynamicsJNI.btTranslationalLimitMotor_needApplyForce(this.swigCPtr, this, i);
    }

    public int testLimitValue(int i, float f) {
        return DynamicsJNI.btTranslationalLimitMotor_testLimitValue(this.swigCPtr, this, i, f);
    }

    public float solveLinearAxis(float f, float f2, btRigidBody btrigidbody, Vector3 vector3, btRigidBody btrigidbody2, Vector3 vector4, int i, Vector3 vector5, Vector3 vector6) {
        return DynamicsJNI.btTranslationalLimitMotor_solveLinearAxis(this.swigCPtr, this, f, f2, btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector4, i, vector5, vector6);
    }
}
