package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btContactSolverInfoFloatData extends BulletBase {
    private long swigCPtr;

    protected btContactSolverInfoFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btContactSolverInfoFloatData(long j, boolean z) {
        this("btContactSolverInfoFloatData", j, z);
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

    public static long getCPtr(btContactSolverInfoFloatData btcontactsolverinfofloatdata) {
        if (btcontactsolverinfofloatdata == null) {
            return 0L;
        }
        return btcontactsolverinfofloatdata.swigCPtr;
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
                DynamicsJNI.delete_btContactSolverInfoFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTau(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_tau_set(this.swigCPtr, this, f);
    }

    public float getTau() {
        return DynamicsJNI.btContactSolverInfoFloatData_tau_get(this.swigCPtr, this);
    }

    public void setDamping(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_damping_set(this.swigCPtr, this, f);
    }

    public float getDamping() {
        return DynamicsJNI.btContactSolverInfoFloatData_damping_get(this.swigCPtr, this);
    }

    public void setFriction(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_friction_set(this.swigCPtr, this, f);
    }

    public float getFriction() {
        return DynamicsJNI.btContactSolverInfoFloatData_friction_get(this.swigCPtr, this);
    }

    public void setTimeStep(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_timeStep_set(this.swigCPtr, this, f);
    }

    public float getTimeStep() {
        return DynamicsJNI.btContactSolverInfoFloatData_timeStep_get(this.swigCPtr, this);
    }

    public void setRestitution(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_restitution_set(this.swigCPtr, this, f);
    }

    public float getRestitution() {
        return DynamicsJNI.btContactSolverInfoFloatData_restitution_get(this.swigCPtr, this);
    }

    public void setMaxErrorReduction(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_maxErrorReduction_set(this.swigCPtr, this, f);
    }

    public float getMaxErrorReduction() {
        return DynamicsJNI.btContactSolverInfoFloatData_maxErrorReduction_get(this.swigCPtr, this);
    }

    public void setSor(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_sor_set(this.swigCPtr, this, f);
    }

    public float getSor() {
        return DynamicsJNI.btContactSolverInfoFloatData_sor_get(this.swigCPtr, this);
    }

    public void setErp(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_erp_set(this.swigCPtr, this, f);
    }

    public float getErp() {
        return DynamicsJNI.btContactSolverInfoFloatData_erp_get(this.swigCPtr, this);
    }

    public void setErp2(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_erp2_set(this.swigCPtr, this, f);
    }

    public float getErp2() {
        return DynamicsJNI.btContactSolverInfoFloatData_erp2_get(this.swigCPtr, this);
    }

    public void setGlobalCfm(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_globalCfm_set(this.swigCPtr, this, f);
    }

    public float getGlobalCfm() {
        return DynamicsJNI.btContactSolverInfoFloatData_globalCfm_get(this.swigCPtr, this);
    }

    public void setSplitImpulsePenetrationThreshold(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_splitImpulsePenetrationThreshold_set(this.swigCPtr, this, f);
    }

    public float getSplitImpulsePenetrationThreshold() {
        return DynamicsJNI.btContactSolverInfoFloatData_splitImpulsePenetrationThreshold_get(this.swigCPtr, this);
    }

    public void setSplitImpulseTurnErp(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_splitImpulseTurnErp_set(this.swigCPtr, this, f);
    }

    public float getSplitImpulseTurnErp() {
        return DynamicsJNI.btContactSolverInfoFloatData_splitImpulseTurnErp_get(this.swigCPtr, this);
    }

    public void setLinearSlop(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_linearSlop_set(this.swigCPtr, this, f);
    }

    public float getLinearSlop() {
        return DynamicsJNI.btContactSolverInfoFloatData_linearSlop_get(this.swigCPtr, this);
    }

    public void setWarmstartingFactor(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_warmstartingFactor_set(this.swigCPtr, this, f);
    }

    public float getWarmstartingFactor() {
        return DynamicsJNI.btContactSolverInfoFloatData_warmstartingFactor_get(this.swigCPtr, this);
    }

    public void setMaxGyroscopicForce(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_maxGyroscopicForce_set(this.swigCPtr, this, f);
    }

    public float getMaxGyroscopicForce() {
        return DynamicsJNI.btContactSolverInfoFloatData_maxGyroscopicForce_get(this.swigCPtr, this);
    }

    public void setSingleAxisRollingFrictionThreshold(float f) {
        DynamicsJNI.btContactSolverInfoFloatData_singleAxisRollingFrictionThreshold_set(this.swigCPtr, this, f);
    }

    public float getSingleAxisRollingFrictionThreshold() {
        return DynamicsJNI.btContactSolverInfoFloatData_singleAxisRollingFrictionThreshold_get(this.swigCPtr, this);
    }

    public void setNumIterations(int i) {
        DynamicsJNI.btContactSolverInfoFloatData_numIterations_set(this.swigCPtr, this, i);
    }

    public int getNumIterations() {
        return DynamicsJNI.btContactSolverInfoFloatData_numIterations_get(this.swigCPtr, this);
    }

    public void setSolverMode(int i) {
        DynamicsJNI.btContactSolverInfoFloatData_solverMode_set(this.swigCPtr, this, i);
    }

    public int getSolverMode() {
        return DynamicsJNI.btContactSolverInfoFloatData_solverMode_get(this.swigCPtr, this);
    }

    public void setRestingContactRestitutionThreshold(int i) {
        DynamicsJNI.btContactSolverInfoFloatData_restingContactRestitutionThreshold_set(this.swigCPtr, this, i);
    }

    public int getRestingContactRestitutionThreshold() {
        return DynamicsJNI.btContactSolverInfoFloatData_restingContactRestitutionThreshold_get(this.swigCPtr, this);
    }

    public void setMinimumSolverBatchSize(int i) {
        DynamicsJNI.btContactSolverInfoFloatData_minimumSolverBatchSize_set(this.swigCPtr, this, i);
    }

    public int getMinimumSolverBatchSize() {
        return DynamicsJNI.btContactSolverInfoFloatData_minimumSolverBatchSize_get(this.swigCPtr, this);
    }

    public void setSplitImpulse(int i) {
        DynamicsJNI.btContactSolverInfoFloatData_splitImpulse_set(this.swigCPtr, this, i);
    }

    public int getSplitImpulse() {
        return DynamicsJNI.btContactSolverInfoFloatData_splitImpulse_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        DynamicsJNI.btContactSolverInfoFloatData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return DynamicsJNI.btContactSolverInfoFloatData_padding_get(this.swigCPtr, this);
    }

    public btContactSolverInfoFloatData() {
        this(DynamicsJNI.new_btContactSolverInfoFloatData(), true);
    }
}
