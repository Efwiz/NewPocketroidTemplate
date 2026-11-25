package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btGeneric6DofSpring2ConstraintDoubleData2 extends BulletBase {
    private long swigCPtr;

    protected btGeneric6DofSpring2ConstraintDoubleData2(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGeneric6DofSpring2ConstraintDoubleData2(long j, boolean z) {
        this("btGeneric6DofSpring2ConstraintDoubleData2", j, z);
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

    public static long getCPtr(btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2) {
        if (btgeneric6dofspring2constraintdoubledata2 == null) {
            return 0L;
        }
        return btgeneric6dofspring2constraintdoubledata2.swigCPtr;
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
                DynamicsJNI.delete_btGeneric6DofSpring2ConstraintDoubleData2(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintDoubleData bttypedconstraintdoubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintDoubleData.getCPtr(bttypedconstraintdoubledata), bttypedconstraintdoubledata);
    }

    public btTypedConstraintDoubleData getTypeConstraintData() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_typeConstraintData_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_typeConstraintData_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintDoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_rbAFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbAFrame() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_rbAFrame_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_rbAFrame_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_rbBFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbBFrame() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_rbBFrame_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_rbBFrame_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_rbBFrame_get, false);
    }

    public void setLinearUpperLimit(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearUpperLimit_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearUpperLimit() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearUpperLimit_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearUpperLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearUpperLimit_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearUpperLimit_get, false);
    }

    public void setLinearLowerLimit(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearLowerLimit_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearLowerLimit() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearLowerLimit_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearLowerLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearLowerLimit_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearLowerLimit_get, false);
    }

    public void setLinearBounce(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearBounce_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearBounce() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearBounce_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearBounce_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearBounce_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearBounce_get, false);
    }

    public void setLinearStopERP(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearStopERP_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearStopERP() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearStopERP_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearStopERP_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearStopERP_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearStopERP_get, false);
    }

    public void setLinearStopCFM(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearStopCFM_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearStopCFM() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearStopCFM_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearStopCFM_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearStopCFM_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearStopCFM_get, false);
    }

    public void setLinearMotorERP(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearMotorERP_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearMotorERP() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearMotorERP_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearMotorERP_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearMotorERP_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearMotorERP_get, false);
    }

    public void setLinearMotorCFM(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearMotorCFM_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearMotorCFM() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearMotorCFM_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearMotorCFM_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearMotorCFM_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearMotorCFM_get, false);
    }

    public void setLinearTargetVelocity(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearTargetVelocity_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearTargetVelocity() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearTargetVelocity_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearTargetVelocity_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearTargetVelocity_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearTargetVelocity_get, false);
    }

    public void setLinearMaxMotorForce(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearMaxMotorForce_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearMaxMotorForce() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearMaxMotorForce_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearMaxMotorForce_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearMaxMotorForce_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearMaxMotorForce_get, false);
    }

    public void setLinearServoTarget(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearServoTarget_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearServoTarget() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearServoTarget_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearServoTarget_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearServoTarget_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearServoTarget_get, false);
    }

    public void setLinearSpringStiffness(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffness_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearSpringStiffness() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffness_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffness_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffness_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffness_get, false);
    }

    public void setLinearSpringDamping(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearSpringDamping_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearSpringDamping() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearSpringDamping_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearSpringDamping_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearSpringDamping_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearSpringDamping_get, false);
    }

    public void setLinearEquilibriumPoint(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearEquilibriumPoint_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearEquilibriumPoint() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_linearEquilibriumPoint_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearEquilibriumPoint_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_linearEquilibriumPoint_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_linearEquilibriumPoint_get, false);
    }

    public void setLinearEnableMotor(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearEnableMotor_set(this.swigCPtr, this, str);
    }

    public String getLinearEnableMotor() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearEnableMotor_get(this.swigCPtr, this);
    }

    public void setLinearServoMotor(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearServoMotor_set(this.swigCPtr, this, str);
    }

    public String getLinearServoMotor() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearServoMotor_get(this.swigCPtr, this);
    }

    public void setLinearEnableSpring(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearEnableSpring_set(this.swigCPtr, this, str);
    }

    public String getLinearEnableSpring() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearEnableSpring_get(this.swigCPtr, this);
    }

    public void setLinearSpringStiffnessLimited(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffnessLimited_set(this.swigCPtr, this, str);
    }

    public String getLinearSpringStiffnessLimited() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffnessLimited_get(this.swigCPtr, this);
    }

    public void setLinearSpringDampingLimited(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearSpringDampingLimited_set(this.swigCPtr, this, str);
    }

    public String getLinearSpringDampingLimited() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_linearSpringDampingLimited_get(this.swigCPtr, this);
    }

    public void setPadding1(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_padding1_set(this.swigCPtr, this, str);
    }

    public String getPadding1() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_padding1_get(this.swigCPtr, this);
    }

    public void setAngularUpperLimit(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularUpperLimit_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularUpperLimit() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularUpperLimit_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularUpperLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularUpperLimit_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularUpperLimit_get, false);
    }

    public void setAngularLowerLimit(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularLowerLimit_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularLowerLimit() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularLowerLimit_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularLowerLimit_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularLowerLimit_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularLowerLimit_get, false);
    }

    public void setAngularBounce(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularBounce_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularBounce() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularBounce_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularBounce_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularBounce_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularBounce_get, false);
    }

    public void setAngularStopERP(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularStopERP_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularStopERP() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularStopERP_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularStopERP_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularStopERP_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularStopERP_get, false);
    }

    public void setAngularStopCFM(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularStopCFM_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularStopCFM() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularStopCFM_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularStopCFM_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularStopCFM_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularStopCFM_get, false);
    }

    public void setAngularMotorERP(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularMotorERP_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularMotorERP() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularMotorERP_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularMotorERP_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularMotorERP_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularMotorERP_get, false);
    }

    public void setAngularMotorCFM(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularMotorCFM_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularMotorCFM() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularMotorCFM_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularMotorCFM_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularMotorCFM_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularMotorCFM_get, false);
    }

    public void setAngularTargetVelocity(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularTargetVelocity_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularTargetVelocity() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularTargetVelocity_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularTargetVelocity_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularTargetVelocity_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularTargetVelocity_get, false);
    }

    public void setAngularMaxMotorForce(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularMaxMotorForce_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularMaxMotorForce() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularMaxMotorForce_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularMaxMotorForce_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularMaxMotorForce_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularMaxMotorForce_get, false);
    }

    public void setAngularServoTarget(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularServoTarget_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularServoTarget() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularServoTarget_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularServoTarget_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularServoTarget_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularServoTarget_get, false);
    }

    public void setAngularSpringStiffness(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffness_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularSpringStiffness() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffness_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffness_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffness_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffness_get, false);
    }

    public void setAngularSpringDamping(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularSpringDamping_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularSpringDamping() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularSpringDamping_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularSpringDamping_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularSpringDamping_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularSpringDamping_get, false);
    }

    public void setAngularEquilibriumPoint(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularEquilibriumPoint_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularEquilibriumPoint() {
        long jBtGeneric6DofSpring2ConstraintDoubleData2_angularEquilibriumPoint_get = DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularEquilibriumPoint_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2ConstraintDoubleData2_angularEquilibriumPoint_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtGeneric6DofSpring2ConstraintDoubleData2_angularEquilibriumPoint_get, false);
    }

    public void setAngularEnableMotor(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularEnableMotor_set(this.swigCPtr, this, str);
    }

    public String getAngularEnableMotor() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularEnableMotor_get(this.swigCPtr, this);
    }

    public void setAngularServoMotor(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularServoMotor_set(this.swigCPtr, this, str);
    }

    public String getAngularServoMotor() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularServoMotor_get(this.swigCPtr, this);
    }

    public void setAngularEnableSpring(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularEnableSpring_set(this.swigCPtr, this, str);
    }

    public String getAngularEnableSpring() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularEnableSpring_get(this.swigCPtr, this);
    }

    public void setAngularSpringStiffnessLimited(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffnessLimited_set(this.swigCPtr, this, str);
    }

    public String getAngularSpringStiffnessLimited() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffnessLimited_get(this.swigCPtr, this);
    }

    public void setAngularSpringDampingLimited(String str) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularSpringDampingLimited_set(this.swigCPtr, this, str);
    }

    public String getAngularSpringDampingLimited() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_angularSpringDampingLimited_get(this.swigCPtr, this);
    }

    public void setRotateOrder(int i) {
        DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_rotateOrder_set(this.swigCPtr, this, i);
    }

    public int getRotateOrder() {
        return DynamicsJNI.btGeneric6DofSpring2ConstraintDoubleData2_rotateOrder_get(this.swigCPtr, this);
    }

    public btGeneric6DofSpring2ConstraintDoubleData2() {
        this(DynamicsJNI.new_btGeneric6DofSpring2ConstraintDoubleData2(), true);
    }
}
