package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTypedConstraintData extends BulletBase {
    private long swigCPtr;

    protected btTypedConstraintData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTypedConstraintData(long j, boolean z) {
        this("btTypedConstraintData", j, z);
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

    public static long getCPtr(btTypedConstraintData bttypedconstraintdata) {
        if (bttypedconstraintdata == null) {
            return 0L;
        }
        return bttypedconstraintdata.swigCPtr;
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
                DynamicsJNI.delete_btTypedConstraintData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setRbA(btRigidBodyFloatData btrigidbodyfloatdata) {
        DynamicsJNI.btTypedConstraintData_rbA_set(this.swigCPtr, this, btRigidBodyFloatData.getCPtr(btrigidbodyfloatdata), btrigidbodyfloatdata);
    }

    public btRigidBodyFloatData getRbA() {
        long jBtTypedConstraintData_rbA_get = DynamicsJNI.btTypedConstraintData_rbA_get(this.swigCPtr, this);
        if (jBtTypedConstraintData_rbA_get == 0) {
            return null;
        }
        return new btRigidBodyFloatData(jBtTypedConstraintData_rbA_get, false);
    }

    public void setRbB(btRigidBodyFloatData btrigidbodyfloatdata) {
        DynamicsJNI.btTypedConstraintData_rbB_set(this.swigCPtr, this, btRigidBodyFloatData.getCPtr(btrigidbodyfloatdata), btrigidbodyfloatdata);
    }

    public btRigidBodyFloatData getRbB() {
        long jBtTypedConstraintData_rbB_get = DynamicsJNI.btTypedConstraintData_rbB_get(this.swigCPtr, this);
        if (jBtTypedConstraintData_rbB_get == 0) {
            return null;
        }
        return new btRigidBodyFloatData(jBtTypedConstraintData_rbB_get, false);
    }

    public void setName(String str) {
        DynamicsJNI.btTypedConstraintData_name_set(this.swigCPtr, this, str);
    }

    public String getName() {
        return DynamicsJNI.btTypedConstraintData_name_get(this.swigCPtr, this);
    }

    public void setObjectType(int i) {
        DynamicsJNI.btTypedConstraintData_objectType_set(this.swigCPtr, this, i);
    }

    public int getObjectType() {
        return DynamicsJNI.btTypedConstraintData_objectType_get(this.swigCPtr, this);
    }

    public void setUserConstraintType(int i) {
        DynamicsJNI.btTypedConstraintData_userConstraintType_set(this.swigCPtr, this, i);
    }

    public int getUserConstraintType() {
        return DynamicsJNI.btTypedConstraintData_userConstraintType_get(this.swigCPtr, this);
    }

    public void setUserConstraintId(int i) {
        DynamicsJNI.btTypedConstraintData_userConstraintId_set(this.swigCPtr, this, i);
    }

    public int getUserConstraintId() {
        return DynamicsJNI.btTypedConstraintData_userConstraintId_get(this.swigCPtr, this);
    }

    public void setNeedsFeedback(int i) {
        DynamicsJNI.btTypedConstraintData_needsFeedback_set(this.swigCPtr, this, i);
    }

    public int getNeedsFeedback() {
        return DynamicsJNI.btTypedConstraintData_needsFeedback_get(this.swigCPtr, this);
    }

    public void setAppliedImpulse(float f) {
        DynamicsJNI.btTypedConstraintData_appliedImpulse_set(this.swigCPtr, this, f);
    }

    public float getAppliedImpulse() {
        return DynamicsJNI.btTypedConstraintData_appliedImpulse_get(this.swigCPtr, this);
    }

    public void setDbgDrawSize(float f) {
        DynamicsJNI.btTypedConstraintData_dbgDrawSize_set(this.swigCPtr, this, f);
    }

    public float getDbgDrawSize() {
        return DynamicsJNI.btTypedConstraintData_dbgDrawSize_get(this.swigCPtr, this);
    }

    public void setDisableCollisionsBetweenLinkedBodies(int i) {
        DynamicsJNI.btTypedConstraintData_disableCollisionsBetweenLinkedBodies_set(this.swigCPtr, this, i);
    }

    public int getDisableCollisionsBetweenLinkedBodies() {
        return DynamicsJNI.btTypedConstraintData_disableCollisionsBetweenLinkedBodies_get(this.swigCPtr, this);
    }

    public void setOverrideNumSolverIterations(int i) {
        DynamicsJNI.btTypedConstraintData_overrideNumSolverIterations_set(this.swigCPtr, this, i);
    }

    public int getOverrideNumSolverIterations() {
        return DynamicsJNI.btTypedConstraintData_overrideNumSolverIterations_get(this.swigCPtr, this);
    }

    public void setBreakingImpulseThreshold(float f) {
        DynamicsJNI.btTypedConstraintData_breakingImpulseThreshold_set(this.swigCPtr, this, f);
    }

    public float getBreakingImpulseThreshold() {
        return DynamicsJNI.btTypedConstraintData_breakingImpulseThreshold_get(this.swigCPtr, this);
    }

    public void setIsEnabled(int i) {
        DynamicsJNI.btTypedConstraintData_isEnabled_set(this.swigCPtr, this, i);
    }

    public int getIsEnabled() {
        return DynamicsJNI.btTypedConstraintData_isEnabled_get(this.swigCPtr, this);
    }

    public btTypedConstraintData() {
        this(DynamicsJNI.new_btTypedConstraintData(), true);
    }
}
