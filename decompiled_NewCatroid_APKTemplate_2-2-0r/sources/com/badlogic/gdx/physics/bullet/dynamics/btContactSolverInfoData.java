package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btContactSolverInfoData extends BulletBase {
    private long swigCPtr;

    protected btContactSolverInfoData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btContactSolverInfoData(long j, boolean z) {
        this("btContactSolverInfoData", j, z);
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

    public static long getCPtr(btContactSolverInfoData btcontactsolverinfodata) {
        if (btcontactsolverinfodata == null) {
            return 0L;
        }
        return btcontactsolverinfodata.swigCPtr;
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
                DynamicsJNI.delete_btContactSolverInfoData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTau(float f) {
        DynamicsJNI.btContactSolverInfoData_tau_set(this.swigCPtr, this, f);
    }

    public float getTau() {
        return DynamicsJNI.btContactSolverInfoData_tau_get(this.swigCPtr, this);
    }

    public void setDamping(float f) {
        DynamicsJNI.btContactSolverInfoData_damping_set(this.swigCPtr, this, f);
    }

    public float getDamping() {
        return DynamicsJNI.btContactSolverInfoData_damping_get(this.swigCPtr, this);
    }

    public void setFriction(float f) {
        DynamicsJNI.btContactSolverInfoData_friction_set(this.swigCPtr, this, f);
    }

    public float getFriction() {
        return DynamicsJNI.btContactSolverInfoData_friction_get(this.swigCPtr, this);
    }

    public void setTimeStep(float f) {
        DynamicsJNI.btContactSolverInfoData_timeStep_set(this.swigCPtr, this, f);
    }

    public float getTimeStep() {
        return DynamicsJNI.btContactSolverInfoData_timeStep_get(this.swigCPtr, this);
    }

    public void setRestitution(float f) {
        DynamicsJNI.btContactSolverInfoData_restitution_set(this.swigCPtr, this, f);
    }

    public float getRestitution() {
        return DynamicsJNI.btContactSolverInfoData_restitution_get(this.swigCPtr, this);
    }

    public void setNumIterations(int i) {
        DynamicsJNI.btContactSolverInfoData_numIterations_set(this.swigCPtr, this, i);
    }

    public int getNumIterations() {
        return DynamicsJNI.btContactSolverInfoData_numIterations_get(this.swigCPtr, this);
    }

    public void setMaxErrorReduction(float f) {
        DynamicsJNI.btContactSolverInfoData_maxErrorReduction_set(this.swigCPtr, this, f);
    }

    public float getMaxErrorReduction() {
        return DynamicsJNI.btContactSolverInfoData_maxErrorReduction_get(this.swigCPtr, this);
    }

    public void setSor(float f) {
        DynamicsJNI.btContactSolverInfoData_sor_set(this.swigCPtr, this, f);
    }

    public float getSor() {
        return DynamicsJNI.btContactSolverInfoData_sor_get(this.swigCPtr, this);
    }

    public void setErp(float f) {
        DynamicsJNI.btContactSolverInfoData_erp_set(this.swigCPtr, this, f);
    }

    public float getErp() {
        return DynamicsJNI.btContactSolverInfoData_erp_get(this.swigCPtr, this);
    }

    public void setErp2(float f) {
        DynamicsJNI.btContactSolverInfoData_erp2_set(this.swigCPtr, this, f);
    }

    public float getErp2() {
        return DynamicsJNI.btContactSolverInfoData_erp2_get(this.swigCPtr, this);
    }

    public void setGlobalCfm(float f) {
        DynamicsJNI.btContactSolverInfoData_globalCfm_set(this.swigCPtr, this, f);
    }

    public float getGlobalCfm() {
        return DynamicsJNI.btContactSolverInfoData_globalCfm_get(this.swigCPtr, this);
    }

    public void setFrictionERP(float f) {
        DynamicsJNI.btContactSolverInfoData_frictionERP_set(this.swigCPtr, this, f);
    }

    public float getFrictionERP() {
        return DynamicsJNI.btContactSolverInfoData_frictionERP_get(this.swigCPtr, this);
    }

    public void setFrictionCFM(float f) {
        DynamicsJNI.btContactSolverInfoData_frictionCFM_set(this.swigCPtr, this, f);
    }

    public float getFrictionCFM() {
        return DynamicsJNI.btContactSolverInfoData_frictionCFM_get(this.swigCPtr, this);
    }

    public void setSplitImpulse(int i) {
        DynamicsJNI.btContactSolverInfoData_splitImpulse_set(this.swigCPtr, this, i);
    }

    public int getSplitImpulse() {
        return DynamicsJNI.btContactSolverInfoData_splitImpulse_get(this.swigCPtr, this);
    }

    public void setSplitImpulsePenetrationThreshold(float f) {
        DynamicsJNI.btContactSolverInfoData_splitImpulsePenetrationThreshold_set(this.swigCPtr, this, f);
    }

    public float getSplitImpulsePenetrationThreshold() {
        return DynamicsJNI.btContactSolverInfoData_splitImpulsePenetrationThreshold_get(this.swigCPtr, this);
    }

    public void setSplitImpulseTurnErp(float f) {
        DynamicsJNI.btContactSolverInfoData_splitImpulseTurnErp_set(this.swigCPtr, this, f);
    }

    public float getSplitImpulseTurnErp() {
        return DynamicsJNI.btContactSolverInfoData_splitImpulseTurnErp_get(this.swigCPtr, this);
    }

    public void setLinearSlop(float f) {
        DynamicsJNI.btContactSolverInfoData_linearSlop_set(this.swigCPtr, this, f);
    }

    public float getLinearSlop() {
        return DynamicsJNI.btContactSolverInfoData_linearSlop_get(this.swigCPtr, this);
    }

    public void setWarmstartingFactor(float f) {
        DynamicsJNI.btContactSolverInfoData_warmstartingFactor_set(this.swigCPtr, this, f);
    }

    public float getWarmstartingFactor() {
        return DynamicsJNI.btContactSolverInfoData_warmstartingFactor_get(this.swigCPtr, this);
    }

    public void setSolverMode(int i) {
        DynamicsJNI.btContactSolverInfoData_solverMode_set(this.swigCPtr, this, i);
    }

    public int getSolverMode() {
        return DynamicsJNI.btContactSolverInfoData_solverMode_get(this.swigCPtr, this);
    }

    public void setRestingContactRestitutionThreshold(int i) {
        DynamicsJNI.btContactSolverInfoData_restingContactRestitutionThreshold_set(this.swigCPtr, this, i);
    }

    public int getRestingContactRestitutionThreshold() {
        return DynamicsJNI.btContactSolverInfoData_restingContactRestitutionThreshold_get(this.swigCPtr, this);
    }

    public void setMinimumSolverBatchSize(int i) {
        DynamicsJNI.btContactSolverInfoData_minimumSolverBatchSize_set(this.swigCPtr, this, i);
    }

    public int getMinimumSolverBatchSize() {
        return DynamicsJNI.btContactSolverInfoData_minimumSolverBatchSize_get(this.swigCPtr, this);
    }

    public void setMaxGyroscopicForce(float f) {
        DynamicsJNI.btContactSolverInfoData_maxGyroscopicForce_set(this.swigCPtr, this, f);
    }

    public float getMaxGyroscopicForce() {
        return DynamicsJNI.btContactSolverInfoData_maxGyroscopicForce_get(this.swigCPtr, this);
    }

    public void setSingleAxisRollingFrictionThreshold(float f) {
        DynamicsJNI.btContactSolverInfoData_singleAxisRollingFrictionThreshold_set(this.swigCPtr, this, f);
    }

    public float getSingleAxisRollingFrictionThreshold() {
        return DynamicsJNI.btContactSolverInfoData_singleAxisRollingFrictionThreshold_get(this.swigCPtr, this);
    }

    public void setLeastSquaresResidualThreshold(float f) {
        DynamicsJNI.btContactSolverInfoData_leastSquaresResidualThreshold_set(this.swigCPtr, this, f);
    }

    public float getLeastSquaresResidualThreshold() {
        return DynamicsJNI.btContactSolverInfoData_leastSquaresResidualThreshold_get(this.swigCPtr, this);
    }

    public void setRestitutionVelocityThreshold(float f) {
        DynamicsJNI.btContactSolverInfoData_restitutionVelocityThreshold_set(this.swigCPtr, this, f);
    }

    public float getRestitutionVelocityThreshold() {
        return DynamicsJNI.btContactSolverInfoData_restitutionVelocityThreshold_get(this.swigCPtr, this);
    }

    public btContactSolverInfoData() {
        this(DynamicsJNI.new_btContactSolverInfoData(), true);
    }
}
