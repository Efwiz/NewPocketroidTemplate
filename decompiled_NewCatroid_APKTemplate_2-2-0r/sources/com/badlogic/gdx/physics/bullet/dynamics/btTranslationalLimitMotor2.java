package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btTranslationalLimitMotor2 extends BulletBase {
    private long swigCPtr;

    protected btTranslationalLimitMotor2(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTranslationalLimitMotor2(long j, boolean z) {
        this("btTranslationalLimitMotor2", j, z);
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

    public static long getCPtr(btTranslationalLimitMotor2 bttranslationallimitmotor2) {
        if (bttranslationallimitmotor2 == null) {
            return 0L;
        }
        return bttranslationallimitmotor2.swigCPtr;
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
                DynamicsJNI.delete_btTranslationalLimitMotor2(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setLowerLimit(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_lowerLimit_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getLowerLimit() {
        long jBtTranslationalLimitMotor2_lowerLimit_get = DynamicsJNI.btTranslationalLimitMotor2_lowerLimit_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_lowerLimit_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_lowerLimit_get, false);
    }

    public void setUpperLimit(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_upperLimit_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getUpperLimit() {
        long jBtTranslationalLimitMotor2_upperLimit_get = DynamicsJNI.btTranslationalLimitMotor2_upperLimit_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_upperLimit_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_upperLimit_get, false);
    }

    public void setBounce(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_bounce_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getBounce() {
        long jBtTranslationalLimitMotor2_bounce_get = DynamicsJNI.btTranslationalLimitMotor2_bounce_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_bounce_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_bounce_get, false);
    }

    public void setStopERP(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_stopERP_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getStopERP() {
        long jBtTranslationalLimitMotor2_stopERP_get = DynamicsJNI.btTranslationalLimitMotor2_stopERP_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_stopERP_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_stopERP_get, false);
    }

    public void setStopCFM(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_stopCFM_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getStopCFM() {
        long jBtTranslationalLimitMotor2_stopCFM_get = DynamicsJNI.btTranslationalLimitMotor2_stopCFM_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_stopCFM_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_stopCFM_get, false);
    }

    public void setMotorERP(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_motorERP_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMotorERP() {
        long jBtTranslationalLimitMotor2_motorERP_get = DynamicsJNI.btTranslationalLimitMotor2_motorERP_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_motorERP_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_motorERP_get, false);
    }

    public void setMotorCFM(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_motorCFM_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMotorCFM() {
        long jBtTranslationalLimitMotor2_motorCFM_get = DynamicsJNI.btTranslationalLimitMotor2_motorCFM_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_motorCFM_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_motorCFM_get, false);
    }

    public void setEnableMotor(boolean[] zArr) {
        DynamicsJNI.btTranslationalLimitMotor2_enableMotor_set(this.swigCPtr, this, zArr);
    }

    public boolean[] getEnableMotor() {
        return DynamicsJNI.btTranslationalLimitMotor2_enableMotor_get(this.swigCPtr, this);
    }

    public void setServoMotor(boolean[] zArr) {
        DynamicsJNI.btTranslationalLimitMotor2_servoMotor_set(this.swigCPtr, this, zArr);
    }

    public boolean[] getServoMotor() {
        return DynamicsJNI.btTranslationalLimitMotor2_servoMotor_get(this.swigCPtr, this);
    }

    public void setEnableSpring(boolean[] zArr) {
        DynamicsJNI.btTranslationalLimitMotor2_enableSpring_set(this.swigCPtr, this, zArr);
    }

    public boolean[] getEnableSpring() {
        return DynamicsJNI.btTranslationalLimitMotor2_enableSpring_get(this.swigCPtr, this);
    }

    public void setServoTarget(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_servoTarget_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getServoTarget() {
        long jBtTranslationalLimitMotor2_servoTarget_get = DynamicsJNI.btTranslationalLimitMotor2_servoTarget_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_servoTarget_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_servoTarget_get, false);
    }

    public void setSpringStiffness(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_springStiffness_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getSpringStiffness() {
        long jBtTranslationalLimitMotor2_springStiffness_get = DynamicsJNI.btTranslationalLimitMotor2_springStiffness_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_springStiffness_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_springStiffness_get, false);
    }

    public void setSpringStiffnessLimited(boolean[] zArr) {
        DynamicsJNI.btTranslationalLimitMotor2_springStiffnessLimited_set(this.swigCPtr, this, zArr);
    }

    public boolean[] getSpringStiffnessLimited() {
        return DynamicsJNI.btTranslationalLimitMotor2_springStiffnessLimited_get(this.swigCPtr, this);
    }

    public void setSpringDamping(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_springDamping_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getSpringDamping() {
        long jBtTranslationalLimitMotor2_springDamping_get = DynamicsJNI.btTranslationalLimitMotor2_springDamping_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_springDamping_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_springDamping_get, false);
    }

    public void setSpringDampingLimited(boolean[] zArr) {
        DynamicsJNI.btTranslationalLimitMotor2_springDampingLimited_set(this.swigCPtr, this, zArr);
    }

    public boolean[] getSpringDampingLimited() {
        return DynamicsJNI.btTranslationalLimitMotor2_springDampingLimited_get(this.swigCPtr, this);
    }

    public void setEquilibriumPoint(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_equilibriumPoint_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getEquilibriumPoint() {
        long jBtTranslationalLimitMotor2_equilibriumPoint_get = DynamicsJNI.btTranslationalLimitMotor2_equilibriumPoint_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_equilibriumPoint_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_equilibriumPoint_get, false);
    }

    public void setTargetVelocity(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_targetVelocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getTargetVelocity() {
        long jBtTranslationalLimitMotor2_targetVelocity_get = DynamicsJNI.btTranslationalLimitMotor2_targetVelocity_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_targetVelocity_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_targetVelocity_get, false);
    }

    public void setMaxMotorForce(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_maxMotorForce_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMaxMotorForce() {
        long jBtTranslationalLimitMotor2_maxMotorForce_get = DynamicsJNI.btTranslationalLimitMotor2_maxMotorForce_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_maxMotorForce_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_maxMotorForce_get, false);
    }

    public void setCurrentLimitError(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_currentLimitError_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getCurrentLimitError() {
        long jBtTranslationalLimitMotor2_currentLimitError_get = DynamicsJNI.btTranslationalLimitMotor2_currentLimitError_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_currentLimitError_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_currentLimitError_get, false);
    }

    public void setCurrentLimitErrorHi(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_currentLimitErrorHi_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getCurrentLimitErrorHi() {
        long jBtTranslationalLimitMotor2_currentLimitErrorHi_get = DynamicsJNI.btTranslationalLimitMotor2_currentLimitErrorHi_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_currentLimitErrorHi_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_currentLimitErrorHi_get, false);
    }

    public void setCurrentLinearDiff(btVector3 btvector3) {
        DynamicsJNI.btTranslationalLimitMotor2_currentLinearDiff_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getCurrentLinearDiff() {
        long jBtTranslationalLimitMotor2_currentLinearDiff_get = DynamicsJNI.btTranslationalLimitMotor2_currentLinearDiff_get(this.swigCPtr, this);
        if (jBtTranslationalLimitMotor2_currentLinearDiff_get == 0) {
            return null;
        }
        return new btVector3(jBtTranslationalLimitMotor2_currentLinearDiff_get, false);
    }

    public void setCurrentLimit(int[] iArr) {
        DynamicsJNI.btTranslationalLimitMotor2_currentLimit_set(this.swigCPtr, this, iArr);
    }

    public int[] getCurrentLimit() {
        return DynamicsJNI.btTranslationalLimitMotor2_currentLimit_get(this.swigCPtr, this);
    }

    public btTranslationalLimitMotor2() {
        this(DynamicsJNI.new_btTranslationalLimitMotor2__SWIG_0(), true);
    }

    public btTranslationalLimitMotor2(btTranslationalLimitMotor2 bttranslationallimitmotor2) {
        this(DynamicsJNI.new_btTranslationalLimitMotor2__SWIG_1(getCPtr(bttranslationallimitmotor2), bttranslationallimitmotor2), true);
    }

    public boolean isLimited(int i) {
        return DynamicsJNI.btTranslationalLimitMotor2_isLimited(this.swigCPtr, this, i);
    }

    public void testLimitValue(int i, float f) {
        DynamicsJNI.btTranslationalLimitMotor2_testLimitValue(this.swigCPtr, this, i, f);
    }
}
