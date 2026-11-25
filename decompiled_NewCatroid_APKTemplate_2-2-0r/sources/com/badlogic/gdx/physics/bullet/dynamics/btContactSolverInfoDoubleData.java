package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btContactSolverInfoDoubleData extends BulletBase {
    private long swigCPtr;

    protected btContactSolverInfoDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btContactSolverInfoDoubleData(long j, boolean z) {
        this("btContactSolverInfoDoubleData", j, z);
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

    public static long getCPtr(btContactSolverInfoDoubleData btcontactsolverinfodoubledata) {
        if (btcontactsolverinfodoubledata == null) {
            return 0L;
        }
        return btcontactsolverinfodoubledata.swigCPtr;
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
                DynamicsJNI.delete_btContactSolverInfoDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTau(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_tau_set(this.swigCPtr, this, d);
    }

    public double getTau() {
        return DynamicsJNI.btContactSolverInfoDoubleData_tau_get(this.swigCPtr, this);
    }

    public void setDamping(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_damping_set(this.swigCPtr, this, d);
    }

    public double getDamping() {
        return DynamicsJNI.btContactSolverInfoDoubleData_damping_get(this.swigCPtr, this);
    }

    public void setFriction(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_friction_set(this.swigCPtr, this, d);
    }

    public double getFriction() {
        return DynamicsJNI.btContactSolverInfoDoubleData_friction_get(this.swigCPtr, this);
    }

    public void setTimeStep(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_timeStep_set(this.swigCPtr, this, d);
    }

    public double getTimeStep() {
        return DynamicsJNI.btContactSolverInfoDoubleData_timeStep_get(this.swigCPtr, this);
    }

    public void setRestitution(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_restitution_set(this.swigCPtr, this, d);
    }

    public double getRestitution() {
        return DynamicsJNI.btContactSolverInfoDoubleData_restitution_get(this.swigCPtr, this);
    }

    public void setMaxErrorReduction(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_maxErrorReduction_set(this.swigCPtr, this, d);
    }

    public double getMaxErrorReduction() {
        return DynamicsJNI.btContactSolverInfoDoubleData_maxErrorReduction_get(this.swigCPtr, this);
    }

    public void setSor(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_sor_set(this.swigCPtr, this, d);
    }

    public double getSor() {
        return DynamicsJNI.btContactSolverInfoDoubleData_sor_get(this.swigCPtr, this);
    }

    public void setErp(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_erp_set(this.swigCPtr, this, d);
    }

    public double getErp() {
        return DynamicsJNI.btContactSolverInfoDoubleData_erp_get(this.swigCPtr, this);
    }

    public void setErp2(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_erp2_set(this.swigCPtr, this, d);
    }

    public double getErp2() {
        return DynamicsJNI.btContactSolverInfoDoubleData_erp2_get(this.swigCPtr, this);
    }

    public void setGlobalCfm(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_globalCfm_set(this.swigCPtr, this, d);
    }

    public double getGlobalCfm() {
        return DynamicsJNI.btContactSolverInfoDoubleData_globalCfm_get(this.swigCPtr, this);
    }

    public void setSplitImpulsePenetrationThreshold(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_splitImpulsePenetrationThreshold_set(this.swigCPtr, this, d);
    }

    public double getSplitImpulsePenetrationThreshold() {
        return DynamicsJNI.btContactSolverInfoDoubleData_splitImpulsePenetrationThreshold_get(this.swigCPtr, this);
    }

    public void setSplitImpulseTurnErp(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_splitImpulseTurnErp_set(this.swigCPtr, this, d);
    }

    public double getSplitImpulseTurnErp() {
        return DynamicsJNI.btContactSolverInfoDoubleData_splitImpulseTurnErp_get(this.swigCPtr, this);
    }

    public void setLinearSlop(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_linearSlop_set(this.swigCPtr, this, d);
    }

    public double getLinearSlop() {
        return DynamicsJNI.btContactSolverInfoDoubleData_linearSlop_get(this.swigCPtr, this);
    }

    public void setWarmstartingFactor(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_warmstartingFactor_set(this.swigCPtr, this, d);
    }

    public double getWarmstartingFactor() {
        return DynamicsJNI.btContactSolverInfoDoubleData_warmstartingFactor_get(this.swigCPtr, this);
    }

    public void setMaxGyroscopicForce(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_maxGyroscopicForce_set(this.swigCPtr, this, d);
    }

    public double getMaxGyroscopicForce() {
        return DynamicsJNI.btContactSolverInfoDoubleData_maxGyroscopicForce_get(this.swigCPtr, this);
    }

    public void setSingleAxisRollingFrictionThreshold(double d) {
        DynamicsJNI.btContactSolverInfoDoubleData_singleAxisRollingFrictionThreshold_set(this.swigCPtr, this, d);
    }

    public double getSingleAxisRollingFrictionThreshold() {
        return DynamicsJNI.btContactSolverInfoDoubleData_singleAxisRollingFrictionThreshold_get(this.swigCPtr, this);
    }

    public void setNumIterations(int i) {
        DynamicsJNI.btContactSolverInfoDoubleData_numIterations_set(this.swigCPtr, this, i);
    }

    public int getNumIterations() {
        return DynamicsJNI.btContactSolverInfoDoubleData_numIterations_get(this.swigCPtr, this);
    }

    public void setSolverMode(int i) {
        DynamicsJNI.btContactSolverInfoDoubleData_solverMode_set(this.swigCPtr, this, i);
    }

    public int getSolverMode() {
        return DynamicsJNI.btContactSolverInfoDoubleData_solverMode_get(this.swigCPtr, this);
    }

    public void setRestingContactRestitutionThreshold(int i) {
        DynamicsJNI.btContactSolverInfoDoubleData_restingContactRestitutionThreshold_set(this.swigCPtr, this, i);
    }

    public int getRestingContactRestitutionThreshold() {
        return DynamicsJNI.btContactSolverInfoDoubleData_restingContactRestitutionThreshold_get(this.swigCPtr, this);
    }

    public void setMinimumSolverBatchSize(int i) {
        DynamicsJNI.btContactSolverInfoDoubleData_minimumSolverBatchSize_set(this.swigCPtr, this, i);
    }

    public int getMinimumSolverBatchSize() {
        return DynamicsJNI.btContactSolverInfoDoubleData_minimumSolverBatchSize_get(this.swigCPtr, this);
    }

    public void setSplitImpulse(int i) {
        DynamicsJNI.btContactSolverInfoDoubleData_splitImpulse_set(this.swigCPtr, this, i);
    }

    public int getSplitImpulse() {
        return DynamicsJNI.btContactSolverInfoDoubleData_splitImpulse_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        DynamicsJNI.btContactSolverInfoDoubleData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return DynamicsJNI.btContactSolverInfoDoubleData_padding_get(this.swigCPtr, this);
    }

    public btContactSolverInfoDoubleData() {
        this(DynamicsJNI.new_btContactSolverInfoDoubleData(), true);
    }
}
