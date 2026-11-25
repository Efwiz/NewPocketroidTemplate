package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btGeneric6DofSpring2ConstraintData extends BulletBase {
    private long swigCPtr;

    protected btGeneric6DofSpring2ConstraintData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGeneric6DofSpring2ConstraintData(long j, boolean z) {
        this(DynamicsConstants.btGeneric6DofSpring2ConstraintDataName, j, z);
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

    public static long getCPtr(btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata) {
        if (btgeneric6dofspring2constraintdata == null) {
            return 0L;
        }
        return btgeneric6dofspring2constraintdata.swigCPtr;
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
                DynamicsJNI.delete_btGeneric6DofSpring2ConstraintData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintData bttypedconstraintdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintData.getCPtr(bttypedconstraintdata), bttypedconstraintdata);
    }

    public btTypedConstraintData getTypeConstraintData() {
        long jBtGeneric6DofSpring2ConstraintData_typeConstraintData_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintData(jBtGeneric6DofSpring2ConstraintData_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_rbAFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbAFrame() {
        long jBtGeneric6DofSpring2ConstraintData_rbAFrame_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_rbAFrame_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtGeneric6DofSpring2ConstraintData_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_rbBFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbBFrame() {
        long jBtGeneric6DofSpring2ConstraintData_rbBFrame_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_rbBFrame_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtGeneric6DofSpring2ConstraintData_rbBFrame_get, false);
    }

    public void setLinearUpperLimit(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearUpperLimit_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearUpperLimit() {
        long jBtGeneric6DofSpring2ConstraintData_linearUpperLimit_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearUpperLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearUpperLimit_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearUpperLimit_get, false);
    }

    public void setLinearLowerLimit(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearLowerLimit_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearLowerLimit() {
        long jBtGeneric6DofSpring2ConstraintData_linearLowerLimit_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearLowerLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearLowerLimit_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearLowerLimit_get, false);
    }

    public void setLinearBounce(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearBounce_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearBounce() {
        long jBtGeneric6DofSpring2ConstraintData_linearBounce_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearBounce_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearBounce_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearBounce_get, false);
    }

    public void setLinearStopERP(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearStopERP_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearStopERP() {
        long jBtGeneric6DofSpring2ConstraintData_linearStopERP_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearStopERP_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearStopERP_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearStopERP_get, false);
    }

    public void setLinearStopCFM(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearStopCFM_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearStopCFM() {
        long jBtGeneric6DofSpring2ConstraintData_linearStopCFM_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearStopCFM_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearStopCFM_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearStopCFM_get, false);
    }

    public void setLinearMotorERP(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearMotorERP_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearMotorERP() {
        long jBtGeneric6DofSpring2ConstraintData_linearMotorERP_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearMotorERP_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearMotorERP_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearMotorERP_get, false);
    }

    public void setLinearMotorCFM(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearMotorCFM_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearMotorCFM() {
        long jBtGeneric6DofSpring2ConstraintData_linearMotorCFM_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearMotorCFM_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearMotorCFM_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearMotorCFM_get, false);
    }

    public void setLinearTargetVelocity(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearTargetVelocity_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearTargetVelocity() {
        long jBtGeneric6DofSpring2ConstraintData_linearTargetVelocity_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearTargetVelocity_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearTargetVelocity_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearTargetVelocity_get, false);
    }

    public void setLinearMaxMotorForce(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearMaxMotorForce_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearMaxMotorForce() {
        long jBtGeneric6DofSpring2ConstraintData_linearMaxMotorForce_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearMaxMotorForce_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearMaxMotorForce_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearMaxMotorForce_get, false);
    }

    public void setLinearServoTarget(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearServoTarget_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearServoTarget() {
        long jBtGeneric6DofSpring2ConstraintData_linearServoTarget_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearServoTarget_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearServoTarget_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearServoTarget_get, false);
    }

    public void setLinearSpringStiffness(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearSpringStiffness_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearSpringStiffness() {
        long jBtGeneric6DofSpring2ConstraintData_linearSpringStiffness_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearSpringStiffness_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearSpringStiffness_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearSpringStiffness_get, false);
    }

    public void setLinearSpringDamping(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearSpringDamping_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearSpringDamping() {
        long jBtGeneric6DofSpring2ConstraintData_linearSpringDamping_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearSpringDamping_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearSpringDamping_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearSpringDamping_get, false);
    }

    public void setLinearEquilibriumPoint(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearEquilibriumPoint_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearEquilibriumPoint() {
        long jBtGeneric6DofSpring2ConstraintData_linearEquilibriumPoint_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearEquilibriumPoint_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_linearEquilibriumPoint_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_linearEquilibriumPoint_get, false);
    }

    public void setLinearEnableMotor(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearEnableMotor_set(this.swigCPtr, this, str);
    }

    public String getLinearEnableMotor() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearEnableMotor_get(this.swigCPtr, this);
    }

    public void setLinearServoMotor(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearServoMotor_set(this.swigCPtr, this, str);
    }

    public String getLinearServoMotor() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearServoMotor_get(this.swigCPtr, this);
    }

    public void setLinearEnableSpring(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearEnableSpring_set(this.swigCPtr, this, str);
    }

    public String getLinearEnableSpring() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearEnableSpring_get(this.swigCPtr, this);
    }

    public void setLinearSpringStiffnessLimited(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearSpringStiffnessLimited_set(this.swigCPtr, this, str);
    }

    public String getLinearSpringStiffnessLimited() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearSpringStiffnessLimited_get(this.swigCPtr, this);
    }

    public void setLinearSpringDampingLimited(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearSpringDampingLimited_set(this.swigCPtr, this, str);
    }

    public String getLinearSpringDampingLimited() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_linearSpringDampingLimited_get(this.swigCPtr, this);
    }

    public void setPadding1(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_padding1_set(this.swigCPtr, this, str);
    }

    public String getPadding1() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_padding1_get(this.swigCPtr, this);
    }

    public void setAngularUpperLimit(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularUpperLimit_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularUpperLimit() {
        long jBtGeneric6DofSpring2ConstraintData_angularUpperLimit_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularUpperLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularUpperLimit_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularUpperLimit_get, false);
    }

    public void setAngularLowerLimit(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularLowerLimit_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularLowerLimit() {
        long jBtGeneric6DofSpring2ConstraintData_angularLowerLimit_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularLowerLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularLowerLimit_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularLowerLimit_get, false);
    }

    public void setAngularBounce(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularBounce_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularBounce() {
        long jBtGeneric6DofSpring2ConstraintData_angularBounce_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularBounce_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularBounce_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularBounce_get, false);
    }

    public void setAngularStopERP(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularStopERP_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularStopERP() {
        long jBtGeneric6DofSpring2ConstraintData_angularStopERP_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularStopERP_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularStopERP_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularStopERP_get, false);
    }

    public void setAngularStopCFM(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularStopCFM_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularStopCFM() {
        long jBtGeneric6DofSpring2ConstraintData_angularStopCFM_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularStopCFM_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularStopCFM_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularStopCFM_get, false);
    }

    public void setAngularMotorERP(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularMotorERP_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularMotorERP() {
        long jBtGeneric6DofSpring2ConstraintData_angularMotorERP_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularMotorERP_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularMotorERP_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularMotorERP_get, false);
    }

    public void setAngularMotorCFM(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularMotorCFM_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularMotorCFM() {
        long jBtGeneric6DofSpring2ConstraintData_angularMotorCFM_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularMotorCFM_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularMotorCFM_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularMotorCFM_get, false);
    }

    public void setAngularTargetVelocity(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularTargetVelocity_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularTargetVelocity() {
        long jBtGeneric6DofSpring2ConstraintData_angularTargetVelocity_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularTargetVelocity_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularTargetVelocity_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularTargetVelocity_get, false);
    }

    public void setAngularMaxMotorForce(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularMaxMotorForce_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularMaxMotorForce() {
        long jBtGeneric6DofSpring2ConstraintData_angularMaxMotorForce_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularMaxMotorForce_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularMaxMotorForce_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularMaxMotorForce_get, false);
    }

    public void setAngularServoTarget(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularServoTarget_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularServoTarget() {
        long jBtGeneric6DofSpring2ConstraintData_angularServoTarget_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularServoTarget_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularServoTarget_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularServoTarget_get, false);
    }

    public void setAngularSpringStiffness(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularSpringStiffness_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularSpringStiffness() {
        long jBtGeneric6DofSpring2ConstraintData_angularSpringStiffness_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularSpringStiffness_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularSpringStiffness_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularSpringStiffness_get, false);
    }

    public void setAngularSpringDamping(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularSpringDamping_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularSpringDamping() {
        long jBtGeneric6DofSpring2ConstraintData_angularSpringDamping_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularSpringDamping_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularSpringDamping_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularSpringDamping_get, false);
    }

    public void setAngularEquilibriumPoint(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularEquilibriumPoint_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularEquilibriumPoint() {
        long jBtGeneric6DofSpring2ConstraintData_angularEquilibriumPoint_get = DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularEquilibriumPoint_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintData_angularEquilibriumPoint_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGeneric6DofSpring2ConstraintData_angularEquilibriumPoint_get, false);
    }

    public void setAngularEnableMotor(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularEnableMotor_set(this.swigCPtr, this, str);
    }

    public String getAngularEnableMotor() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularEnableMotor_get(this.swigCPtr, this);
    }

    public void setAngularServoMotor(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularServoMotor_set(this.swigCPtr, this, str);
    }

    public String getAngularServoMotor() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularServoMotor_get(this.swigCPtr, this);
    }

    public void setAngularEnableSpring(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularEnableSpring_set(this.swigCPtr, this, str);
    }

    public String getAngularEnableSpring() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularEnableSpring_get(this.swigCPtr, this);
    }

    public void setAngularSpringStiffnessLimited(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularSpringStiffnessLimited_set(this.swigCPtr, this, str);
    }

    public String getAngularSpringStiffnessLimited() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularSpringStiffnessLimited_get(this.swigCPtr, this);
    }

    public void setAngularSpringDampingLimited(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularSpringDampingLimited_set(this.swigCPtr, this, str);
    }

    public String getAngularSpringDampingLimited() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_angularSpringDampingLimited_get(this.swigCPtr, this);
    }

    public void setRotateOrder(int i) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintData_rotateOrder_set(this.swigCPtr, this, i);
    }

    public int getRotateOrder() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintData_rotateOrder_get(this.swigCPtr, this);
    }

    public btGeneric6DofSpring2ConstraintData() {
        this(DynamicsJNI.new_btGeneric6DofSpring2ConstraintData(), true);
    }
}
