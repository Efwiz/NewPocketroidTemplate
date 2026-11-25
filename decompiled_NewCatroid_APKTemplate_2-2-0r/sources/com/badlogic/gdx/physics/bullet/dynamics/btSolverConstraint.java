package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btSolverConstraint extends BulletBase {
    private long swigCPtr;

    public static final class btSolverConstraintType {
        public static final int BT_SOLVER_CONTACT_1D = 0;
        public static final int BT_SOLVER_FRICTION_1D = 1;
    }

    protected btSolverConstraint(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSolverConstraint(long j, boolean z) {
        this("btSolverConstraint", j, z);
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

    public static long getCPtr(btSolverConstraint btsolverconstraint) {
        if (btsolverconstraint == null) {
            return 0L;
        }
        return btsolverconstraint.swigCPtr;
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
                DynamicsJNI.delete_btSolverConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btSolverConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btSolverConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btSolverConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btSolverConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btSolverConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btSolverConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btSolverConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btSolverConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setRelpos1CrossNormal(btVector3 btvector3) {
        DynamicsJNI.btSolverConstraint_relpos1CrossNormal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getRelpos1CrossNormal() {
        long jBtSolverConstraint_relpos1CrossNormal_get = DynamicsJNI.btSolverConstraint_relpos1CrossNormal_get(this.swigCPtr, this);
        if (jBtSolverConstraint_relpos1CrossNormal_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverConstraint_relpos1CrossNormal_get, false);
    }

    public void setContactNormal1(btVector3 btvector3) {
        DynamicsJNI.btSolverConstraint_contactNormal1_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getContactNormal1() {
        long jBtSolverConstraint_contactNormal1_get = DynamicsJNI.btSolverConstraint_contactNormal1_get(this.swigCPtr, this);
        if (jBtSolverConstraint_contactNormal1_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverConstraint_contactNormal1_get, false);
    }

    public void setRelpos2CrossNormal(btVector3 btvector3) {
        DynamicsJNI.btSolverConstraint_relpos2CrossNormal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getRelpos2CrossNormal() {
        long jBtSolverConstraint_relpos2CrossNormal_get = DynamicsJNI.btSolverConstraint_relpos2CrossNormal_get(this.swigCPtr, this);
        if (jBtSolverConstraint_relpos2CrossNormal_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverConstraint_relpos2CrossNormal_get, false);
    }

    public void setContactNormal2(btVector3 btvector3) {
        DynamicsJNI.btSolverConstraint_contactNormal2_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getContactNormal2() {
        long jBtSolverConstraint_contactNormal2_get = DynamicsJNI.btSolverConstraint_contactNormal2_get(this.swigCPtr, this);
        if (jBtSolverConstraint_contactNormal2_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverConstraint_contactNormal2_get, false);
    }

    public void setAngularComponentA(btVector3 btvector3) {
        DynamicsJNI.btSolverConstraint_angularComponentA_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAngularComponentA() {
        long jBtSolverConstraint_angularComponentA_get = DynamicsJNI.btSolverConstraint_angularComponentA_get(this.swigCPtr, this);
        if (jBtSolverConstraint_angularComponentA_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverConstraint_angularComponentA_get, false);
    }

    public void setAngularComponentB(btVector3 btvector3) {
        DynamicsJNI.btSolverConstraint_angularComponentB_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAngularComponentB() {
        long jBtSolverConstraint_angularComponentB_get = DynamicsJNI.btSolverConstraint_angularComponentB_get(this.swigCPtr, this);
        if (jBtSolverConstraint_angularComponentB_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverConstraint_angularComponentB_get, false);
    }

    public void setAppliedPushImpulse(float f) {
        DynamicsJNI.btSolverConstraint_appliedPushImpulse_set(this.swigCPtr, this, f);
    }

    public float getAppliedPushImpulse() {
        return DynamicsJNI.btSolverConstraint_appliedPushImpulse_get(this.swigCPtr, this);
    }

    public void setAppliedImpulse(float f) {
        DynamicsJNI.btSolverConstraint_appliedImpulse_set(this.swigCPtr, this, f);
    }

    public float getAppliedImpulse() {
        return DynamicsJNI.btSolverConstraint_appliedImpulse_get(this.swigCPtr, this);
    }

    public void setFriction(float f) {
        DynamicsJNI.btSolverConstraint_friction_set(this.swigCPtr, this, f);
    }

    public float getFriction() {
        return DynamicsJNI.btSolverConstraint_friction_get(this.swigCPtr, this);
    }

    public void setJacDiagABInv(float f) {
        DynamicsJNI.btSolverConstraint_jacDiagABInv_set(this.swigCPtr, this, f);
    }

    public float getJacDiagABInv() {
        return DynamicsJNI.btSolverConstraint_jacDiagABInv_get(this.swigCPtr, this);
    }

    public void setRhs(float f) {
        DynamicsJNI.btSolverConstraint_rhs_set(this.swigCPtr, this, f);
    }

    public float getRhs() {
        return DynamicsJNI.btSolverConstraint_rhs_get(this.swigCPtr, this);
    }

    public void setCfm(float f) {
        DynamicsJNI.btSolverConstraint_cfm_set(this.swigCPtr, this, f);
    }

    public float getCfm() {
        return DynamicsJNI.btSolverConstraint_cfm_get(this.swigCPtr, this);
    }

    public void setLowerLimit(float f) {
        DynamicsJNI.btSolverConstraint_lowerLimit_set(this.swigCPtr, this, f);
    }

    public float getLowerLimit() {
        return DynamicsJNI.btSolverConstraint_lowerLimit_get(this.swigCPtr, this);
    }

    public void setUpperLimit(float f) {
        DynamicsJNI.btSolverConstraint_upperLimit_set(this.swigCPtr, this, f);
    }

    public float getUpperLimit() {
        return DynamicsJNI.btSolverConstraint_upperLimit_get(this.swigCPtr, this);
    }

    public void setRhsPenetration(float f) {
        DynamicsJNI.btSolverConstraint_rhsPenetration_set(this.swigCPtr, this, f);
    }

    public float getRhsPenetration() {
        return DynamicsJNI.btSolverConstraint_rhsPenetration_get(this.swigCPtr, this);
    }

    public void setOriginalContactPoint(long j) {
        DynamicsJNI.btSolverConstraint_originalContactPoint_set(this.swigCPtr, this, j);
    }

    public long getOriginalContactPoint() {
        return DynamicsJNI.btSolverConstraint_originalContactPoint_get(this.swigCPtr, this);
    }

    public void setUnusedPadding4(float f) {
        DynamicsJNI.btSolverConstraint_unusedPadding4_set(this.swigCPtr, this, f);
    }

    public float getUnusedPadding4() {
        return DynamicsJNI.btSolverConstraint_unusedPadding4_get(this.swigCPtr, this);
    }

    public void setNumRowsForNonContactConstraint(int i) {
        DynamicsJNI.btSolverConstraint_numRowsForNonContactConstraint_set(this.swigCPtr, this, i);
    }

    public int getNumRowsForNonContactConstraint() {
        return DynamicsJNI.btSolverConstraint_numRowsForNonContactConstraint_get(this.swigCPtr, this);
    }

    public void setOverrideNumSolverIterations(int i) {
        DynamicsJNI.btSolverConstraint_overrideNumSolverIterations_set(this.swigCPtr, this, i);
    }

    public int getOverrideNumSolverIterations() {
        return DynamicsJNI.btSolverConstraint_overrideNumSolverIterations_get(this.swigCPtr, this);
    }

    public void setFrictionIndex(int i) {
        DynamicsJNI.btSolverConstraint_frictionIndex_set(this.swigCPtr, this, i);
    }

    public int getFrictionIndex() {
        return DynamicsJNI.btSolverConstraint_frictionIndex_get(this.swigCPtr, this);
    }

    public void setSolverBodyIdA(int i) {
        DynamicsJNI.btSolverConstraint_solverBodyIdA_set(this.swigCPtr, this, i);
    }

    public int getSolverBodyIdA() {
        return DynamicsJNI.btSolverConstraint_solverBodyIdA_get(this.swigCPtr, this);
    }

    public void setSolverBodyIdB(int i) {
        DynamicsJNI.btSolverConstraint_solverBodyIdB_set(this.swigCPtr, this, i);
    }

    public int getSolverBodyIdB() {
        return DynamicsJNI.btSolverConstraint_solverBodyIdB_get(this.swigCPtr, this);
    }

    public btSolverConstraint() {
        this(DynamicsJNI.new_btSolverConstraint(), true);
    }
}
