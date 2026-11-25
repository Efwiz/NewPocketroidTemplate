package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTypedConstraintDoubleData extends BulletBase {
    private long swigCPtr;

    protected btTypedConstraintDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTypedConstraintDoubleData(long j, boolean z) {
        this("btTypedConstraintDoubleData", j, z);
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

    public static long getCPtr(btTypedConstraintDoubleData bttypedconstraintdoubledata) {
        if (bttypedconstraintdoubledata == null) {
            return 0L;
        }
        return bttypedconstraintdoubledata.swigCPtr;
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
                DynamicsJNI.delete_btTypedConstraintDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setRbA(btRigidBodyDoubleData btrigidbodydoubledata) {
        DynamicsJNI.btTypedConstraintDoubleData_rbA_set(this.swigCPtr, this, btRigidBodyDoubleData.getCPtr(btrigidbodydoubledata), btrigidbodydoubledata);
    }

    public btRigidBodyDoubleData getRbA() {
        long jBtTypedConstraintDoubleData_rbA_get = DynamicsJNI.btTypedConstraintDoubleData_rbA_get(this.swigCPtr, this);
        if (jBtTypedConstraintDoubleData_rbA_get == 0) {
            return null;
        }
        return new btRigidBodyDoubleData(jBtTypedConstraintDoubleData_rbA_get, false);
    }

    public void setRbB(btRigidBodyDoubleData btrigidbodydoubledata) {
        DynamicsJNI.btTypedConstraintDoubleData_rbB_set(this.swigCPtr, this, btRigidBodyDoubleData.getCPtr(btrigidbodydoubledata), btrigidbodydoubledata);
    }

    public btRigidBodyDoubleData getRbB() {
        long jBtTypedConstraintDoubleData_rbB_get = DynamicsJNI.btTypedConstraintDoubleData_rbB_get(this.swigCPtr, this);
        if (jBtTypedConstraintDoubleData_rbB_get == 0) {
            return null;
        }
        return new btRigidBodyDoubleData(jBtTypedConstraintDoubleData_rbB_get, false);
    }

    public void setName(String str) {
        DynamicsJNI.btTypedConstraintDoubleData_name_set(this.swigCPtr, this, str);
    }

    public String getName() {
        return DynamicsJNI.btTypedConstraintDoubleData_name_get(this.swigCPtr, this);
    }

    public void setObjectType(int i) {
        DynamicsJNI.btTypedConstraintDoubleData_objectType_set(this.swigCPtr, this, i);
    }

    public int getObjectType() {
        return DynamicsJNI.btTypedConstraintDoubleData_objectType_get(this.swigCPtr, this);
    }

    public void setUserConstraintType(int i) {
        DynamicsJNI.btTypedConstraintDoubleData_userConstraintType_set(this.swigCPtr, this, i);
    }

    public int getUserConstraintType() {
        return DynamicsJNI.btTypedConstraintDoubleData_userConstraintType_get(this.swigCPtr, this);
    }

    public void setUserConstraintId(int i) {
        DynamicsJNI.btTypedConstraintDoubleData_userConstraintId_set(this.swigCPtr, this, i);
    }

    public int getUserConstraintId() {
        return DynamicsJNI.btTypedConstraintDoubleData_userConstraintId_get(this.swigCPtr, this);
    }

    public void setNeedsFeedback(int i) {
        DynamicsJNI.btTypedConstraintDoubleData_needsFeedback_set(this.swigCPtr, this, i);
    }

    public int getNeedsFeedback() {
        return DynamicsJNI.btTypedConstraintDoubleData_needsFeedback_get(this.swigCPtr, this);
    }

    public void setAppliedImpulse(double d) {
        DynamicsJNI.btTypedConstraintDoubleData_appliedImpulse_set(this.swigCPtr, this, d);
    }

    public double getAppliedImpulse() {
        return DynamicsJNI.btTypedConstraintDoubleData_appliedImpulse_get(this.swigCPtr, this);
    }

    public void setDbgDrawSize(double d) {
        DynamicsJNI.btTypedConstraintDoubleData_dbgDrawSize_set(this.swigCPtr, this, d);
    }

    public double getDbgDrawSize() {
        return DynamicsJNI.btTypedConstraintDoubleData_dbgDrawSize_get(this.swigCPtr, this);
    }

    public void setDisableCollisionsBetweenLinkedBodies(int i) {
        DynamicsJNI.btTypedConstraintDoubleData_disableCollisionsBetweenLinkedBodies_set(this.swigCPtr, this, i);
    }

    public int getDisableCollisionsBetweenLinkedBodies() {
        return DynamicsJNI.btTypedConstraintDoubleData_disableCollisionsBetweenLinkedBodies_get(this.swigCPtr, this);
    }

    public void setOverrideNumSolverIterations(int i) {
        DynamicsJNI.btTypedConstraintDoubleData_overrideNumSolverIterations_set(this.swigCPtr, this, i);
    }

    public int getOverrideNumSolverIterations() {
        return DynamicsJNI.btTypedConstraintDoubleData_overrideNumSolverIterations_get(this.swigCPtr, this);
    }

    public void setBreakingImpulseThreshold(double d) {
        DynamicsJNI.btTypedConstraintDoubleData_breakingImpulseThreshold_set(this.swigCPtr, this, d);
    }

    public double getBreakingImpulseThreshold() {
        return DynamicsJNI.btTypedConstraintDoubleData_breakingImpulseThreshold_get(this.swigCPtr, this);
    }

    public void setIsEnabled(int i) {
        DynamicsJNI.btTypedConstraintDoubleData_isEnabled_set(this.swigCPtr, this, i);
    }

    public int getIsEnabled() {
        return DynamicsJNI.btTypedConstraintDoubleData_isEnabled_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        DynamicsJNI.btTypedConstraintDoubleData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return DynamicsJNI.btTypedConstraintDoubleData_padding_get(this.swigCPtr, this);
    }

    public btTypedConstraintDoubleData() {
        this(DynamicsJNI.new_btTypedConstraintDoubleData(), true);
    }
}
