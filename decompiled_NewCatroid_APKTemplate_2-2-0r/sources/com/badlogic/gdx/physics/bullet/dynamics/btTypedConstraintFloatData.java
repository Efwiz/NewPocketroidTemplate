package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTypedConstraintFloatData extends BulletBase {
    private long swigCPtr;

    protected btTypedConstraintFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTypedConstraintFloatData(long j, boolean z) {
        this(DynamicsConstants.btTypedConstraintDataName, j, z);
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

    public static long getCPtr(btTypedConstraintFloatData bttypedconstraintfloatdata) {
        if (bttypedconstraintfloatdata == null) {
            return 0L;
        }
        return bttypedconstraintfloatdata.swigCPtr;
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
                DynamicsJNI.delete_btTypedConstraintFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setRbA(btRigidBodyFloatData btrigidbodyfloatdata) {
        DynamicsJNI.btTypedConstraintFloatData_rbA_set(this.swigCPtr, this, btRigidBodyFloatData.getCPtr(btrigidbodyfloatdata), btrigidbodyfloatdata);
    }

    public btRigidBodyFloatData getRbA() {
        long jBtTypedConstraintFloatData_rbA_get = DynamicsJNI.btTypedConstraintFloatData_rbA_get(this.swigCPtr, this);
        if (jBtTypedConstraintFloatData_rbA_get == 0) {
            return null;
        }
        return new btRigidBodyFloatData(jBtTypedConstraintFloatData_rbA_get, false);
    }

    public void setRbB(btRigidBodyFloatData btrigidbodyfloatdata) {
        DynamicsJNI.btTypedConstraintFloatData_rbB_set(this.swigCPtr, this, btRigidBodyFloatData.getCPtr(btrigidbodyfloatdata), btrigidbodyfloatdata);
    }

    public btRigidBodyFloatData getRbB() {
        long jBtTypedConstraintFloatData_rbB_get = DynamicsJNI.btTypedConstraintFloatData_rbB_get(this.swigCPtr, this);
        if (jBtTypedConstraintFloatData_rbB_get == 0) {
            return null;
        }
        return new btRigidBodyFloatData(jBtTypedConstraintFloatData_rbB_get, false);
    }

    public void setName(String str) {
        DynamicsJNI.btTypedConstraintFloatData_name_set(this.swigCPtr, this, str);
    }

    public String getName() {
        return DynamicsJNI.btTypedConstraintFloatData_name_get(this.swigCPtr, this);
    }

    public void setObjectType(int i) {
        DynamicsJNI.btTypedConstraintFloatData_objectType_set(this.swigCPtr, this, i);
    }

    public int getObjectType() {
        return DynamicsJNI.btTypedConstraintFloatData_objectType_get(this.swigCPtr, this);
    }

    public void setUserConstraintType(int i) {
        DynamicsJNI.btTypedConstraintFloatData_userConstraintType_set(this.swigCPtr, this, i);
    }

    public int getUserConstraintType() {
        return DynamicsJNI.btTypedConstraintFloatData_userConstraintType_get(this.swigCPtr, this);
    }

    public void setUserConstraintId(int i) {
        DynamicsJNI.btTypedConstraintFloatData_userConstraintId_set(this.swigCPtr, this, i);
    }

    public int getUserConstraintId() {
        return DynamicsJNI.btTypedConstraintFloatData_userConstraintId_get(this.swigCPtr, this);
    }

    public void setNeedsFeedback(int i) {
        DynamicsJNI.btTypedConstraintFloatData_needsFeedback_set(this.swigCPtr, this, i);
    }

    public int getNeedsFeedback() {
        return DynamicsJNI.btTypedConstraintFloatData_needsFeedback_get(this.swigCPtr, this);
    }

    public void setAppliedImpulse(float f) {
        DynamicsJNI.btTypedConstraintFloatData_appliedImpulse_set(this.swigCPtr, this, f);
    }

    public float getAppliedImpulse() {
        return DynamicsJNI.btTypedConstraintFloatData_appliedImpulse_get(this.swigCPtr, this);
    }

    public void setDbgDrawSize(float f) {
        DynamicsJNI.btTypedConstraintFloatData_dbgDrawSize_set(this.swigCPtr, this, f);
    }

    public float getDbgDrawSize() {
        return DynamicsJNI.btTypedConstraintFloatData_dbgDrawSize_get(this.swigCPtr, this);
    }

    public void setDisableCollisionsBetweenLinkedBodies(int i) {
        DynamicsJNI.btTypedConstraintFloatData_disableCollisionsBetweenLinkedBodies_set(this.swigCPtr, this, i);
    }

    public int getDisableCollisionsBetweenLinkedBodies() {
        return DynamicsJNI.btTypedConstraintFloatData_disableCollisionsBetweenLinkedBodies_get(this.swigCPtr, this);
    }

    public void setOverrideNumSolverIterations(int i) {
        DynamicsJNI.btTypedConstraintFloatData_overrideNumSolverIterations_set(this.swigCPtr, this, i);
    }

    public int getOverrideNumSolverIterations() {
        return DynamicsJNI.btTypedConstraintFloatData_overrideNumSolverIterations_get(this.swigCPtr, this);
    }

    public void setBreakingImpulseThreshold(float f) {
        DynamicsJNI.btTypedConstraintFloatData_breakingImpulseThreshold_set(this.swigCPtr, this, f);
    }

    public float getBreakingImpulseThreshold() {
        return DynamicsJNI.btTypedConstraintFloatData_breakingImpulseThreshold_get(this.swigCPtr, this);
    }

    public void setIsEnabled(int i) {
        DynamicsJNI.btTypedConstraintFloatData_isEnabled_set(this.swigCPtr, this, i);
    }

    public int getIsEnabled() {
        return DynamicsJNI.btTypedConstraintFloatData_isEnabled_get(this.swigCPtr, this);
    }

    public btTypedConstraintFloatData() {
        this(DynamicsJNI.new_btTypedConstraintFloatData(), true);
    }
}
