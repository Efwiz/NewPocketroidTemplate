package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodySolverConstraint extends BulletBase {
    private long swigCPtr;

    public static final class btSolverConstraintType {
        public static final int BT_SOLVER_CONTACT_1D = 0;
        public static final int BT_SOLVER_FRICTION_1D = 1;
    }

    protected btMultiBodySolverConstraint(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiBodySolverConstraint(long j, boolean z) {
        this("btMultiBodySolverConstraint", j, z);
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

    public static long getCPtr(btMultiBodySolverConstraint btmultibodysolverconstraint) {
        if (btmultibodysolverconstraint == null) {
            return 0L;
        }
        return btmultibodysolverconstraint.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodySolverConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btMultiBodySolverConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btMultiBodySolverConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btMultiBodySolverConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btMultiBodySolverConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btMultiBodySolverConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btMultiBodySolverConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btMultiBodySolverConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btMultiBodySolverConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btMultiBodySolverConstraint() {
        this(DynamicsJNI.new_btMultiBodySolverConstraint(), true);
    }

    public void setDeltaVelAindex(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_deltaVelAindex_set(this.swigCPtr, this, i);
    }

    public int getDeltaVelAindex() {
        return DynamicsJNI.btMultiBodySolverConstraint_deltaVelAindex_get(this.swigCPtr, this);
    }

    public void setJacAindex(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_jacAindex_set(this.swigCPtr, this, i);
    }

    public int getJacAindex() {
        return DynamicsJNI.btMultiBodySolverConstraint_jacAindex_get(this.swigCPtr, this);
    }

    public void setDeltaVelBindex(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_deltaVelBindex_set(this.swigCPtr, this, i);
    }

    public int getDeltaVelBindex() {
        return DynamicsJNI.btMultiBodySolverConstraint_deltaVelBindex_get(this.swigCPtr, this);
    }

    public void setJacBindex(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_jacBindex_set(this.swigCPtr, this, i);
    }

    public int getJacBindex() {
        return DynamicsJNI.btMultiBodySolverConstraint_jacBindex_get(this.swigCPtr, this);
    }

    public void setRelpos1CrossNormal(btVector3 btvector3) {
        DynamicsJNI.btMultiBodySolverConstraint_relpos1CrossNormal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getRelpos1CrossNormal() {
        long jBtMultiBodySolverConstraint_relpos1CrossNormal_get = DynamicsJNI.btMultiBodySolverConstraint_relpos1CrossNormal_get(this.swigCPtr, this);
        if (jBtMultiBodySolverConstraint_relpos1CrossNormal_get == 0) {
            return null;
        }
        return new btVector3(jBtMultiBodySolverConstraint_relpos1CrossNormal_get, false);
    }

    public void setContactNormal1(btVector3 btvector3) {
        DynamicsJNI.btMultiBodySolverConstraint_contactNormal1_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getContactNormal1() {
        long jBtMultiBodySolverConstraint_contactNormal1_get = DynamicsJNI.btMultiBodySolverConstraint_contactNormal1_get(this.swigCPtr, this);
        if (jBtMultiBodySolverConstraint_contactNormal1_get == 0) {
            return null;
        }
        return new btVector3(jBtMultiBodySolverConstraint_contactNormal1_get, false);
    }

    public void setRelpos2CrossNormal(btVector3 btvector3) {
        DynamicsJNI.btMultiBodySolverConstraint_relpos2CrossNormal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getRelpos2CrossNormal() {
        long jBtMultiBodySolverConstraint_relpos2CrossNormal_get = DynamicsJNI.btMultiBodySolverConstraint_relpos2CrossNormal_get(this.swigCPtr, this);
        if (jBtMultiBodySolverConstraint_relpos2CrossNormal_get == 0) {
            return null;
        }
        return new btVector3(jBtMultiBodySolverConstraint_relpos2CrossNormal_get, false);
    }

    public void setContactNormal2(btVector3 btvector3) {
        DynamicsJNI.btMultiBodySolverConstraint_contactNormal2_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getContactNormal2() {
        long jBtMultiBodySolverConstraint_contactNormal2_get = DynamicsJNI.btMultiBodySolverConstraint_contactNormal2_get(this.swigCPtr, this);
        if (jBtMultiBodySolverConstraint_contactNormal2_get == 0) {
            return null;
        }
        return new btVector3(jBtMultiBodySolverConstraint_contactNormal2_get, false);
    }

    public void setAngularComponentA(btVector3 btvector3) {
        DynamicsJNI.btMultiBodySolverConstraint_angularComponentA_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAngularComponentA() {
        long jBtMultiBodySolverConstraint_angularComponentA_get = DynamicsJNI.btMultiBodySolverConstraint_angularComponentA_get(this.swigCPtr, this);
        if (jBtMultiBodySolverConstraint_angularComponentA_get == 0) {
            return null;
        }
        return new btVector3(jBtMultiBodySolverConstraint_angularComponentA_get, false);
    }

    public void setAngularComponentB(btVector3 btvector3) {
        DynamicsJNI.btMultiBodySolverConstraint_angularComponentB_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAngularComponentB() {
        long jBtMultiBodySolverConstraint_angularComponentB_get = DynamicsJNI.btMultiBodySolverConstraint_angularComponentB_get(this.swigCPtr, this);
        if (jBtMultiBodySolverConstraint_angularComponentB_get == 0) {
            return null;
        }
        return new btVector3(jBtMultiBodySolverConstraint_angularComponentB_get, false);
    }

    public void setAppliedPushImpulse(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_appliedPushImpulse_set(this.swigCPtr, this, f);
    }

    public float getAppliedPushImpulse() {
        return DynamicsJNI.btMultiBodySolverConstraint_appliedPushImpulse_get(this.swigCPtr, this);
    }

    public void setAppliedImpulse(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_appliedImpulse_set(this.swigCPtr, this, f);
    }

    public float getAppliedImpulse() {
        return DynamicsJNI.btMultiBodySolverConstraint_appliedImpulse_get(this.swigCPtr, this);
    }

    public void setFriction(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_friction_set(this.swigCPtr, this, f);
    }

    public float getFriction() {
        return DynamicsJNI.btMultiBodySolverConstraint_friction_get(this.swigCPtr, this);
    }

    public void setJacDiagABInv(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_jacDiagABInv_set(this.swigCPtr, this, f);
    }

    public float getJacDiagABInv() {
        return DynamicsJNI.btMultiBodySolverConstraint_jacDiagABInv_get(this.swigCPtr, this);
    }

    public void setRhs(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_rhs_set(this.swigCPtr, this, f);
    }

    public float getRhs() {
        return DynamicsJNI.btMultiBodySolverConstraint_rhs_get(this.swigCPtr, this);
    }

    public void setCfm(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_cfm_set(this.swigCPtr, this, f);
    }

    public float getCfm() {
        return DynamicsJNI.btMultiBodySolverConstraint_cfm_get(this.swigCPtr, this);
    }

    public void setLowerLimit(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_lowerLimit_set(this.swigCPtr, this, f);
    }

    public float getLowerLimit() {
        return DynamicsJNI.btMultiBodySolverConstraint_lowerLimit_get(this.swigCPtr, this);
    }

    public void setUpperLimit(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_upperLimit_set(this.swigCPtr, this, f);
    }

    public float getUpperLimit() {
        return DynamicsJNI.btMultiBodySolverConstraint_upperLimit_get(this.swigCPtr, this);
    }

    public void setRhsPenetration(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_rhsPenetration_set(this.swigCPtr, this, f);
    }

    public float getRhsPenetration() {
        return DynamicsJNI.btMultiBodySolverConstraint_rhsPenetration_get(this.swigCPtr, this);
    }

    public void setOriginalContactPoint(long j) {
        DynamicsJNI.btMultiBodySolverConstraint_originalContactPoint_set(this.swigCPtr, this, j);
    }

    public long getOriginalContactPoint() {
        return DynamicsJNI.btMultiBodySolverConstraint_originalContactPoint_get(this.swigCPtr, this);
    }

    public void setUnusedPadding4(float f) {
        DynamicsJNI.btMultiBodySolverConstraint_unusedPadding4_set(this.swigCPtr, this, f);
    }

    public float getUnusedPadding4() {
        return DynamicsJNI.btMultiBodySolverConstraint_unusedPadding4_get(this.swigCPtr, this);
    }

    public void setOverrideNumSolverIterations(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_overrideNumSolverIterations_set(this.swigCPtr, this, i);
    }

    public int getOverrideNumSolverIterations() {
        return DynamicsJNI.btMultiBodySolverConstraint_overrideNumSolverIterations_get(this.swigCPtr, this);
    }

    public void setFrictionIndex(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_frictionIndex_set(this.swigCPtr, this, i);
    }

    public int getFrictionIndex() {
        return DynamicsJNI.btMultiBodySolverConstraint_frictionIndex_get(this.swigCPtr, this);
    }

    public void setSolverBodyIdA(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_solverBodyIdA_set(this.swigCPtr, this, i);
    }

    public int getSolverBodyIdA() {
        return DynamicsJNI.btMultiBodySolverConstraint_solverBodyIdA_get(this.swigCPtr, this);
    }

    public void setMultiBodyA(btMultiBody btmultibody) {
        DynamicsJNI.btMultiBodySolverConstraint_multiBodyA_set(this.swigCPtr, this, btMultiBody.getCPtr(btmultibody), btmultibody);
    }

    public btMultiBody getMultiBodyA() {
        long jBtMultiBodySolverConstraint_multiBodyA_get = DynamicsJNI.btMultiBodySolverConstraint_multiBodyA_get(this.swigCPtr, this);
        if (jBtMultiBodySolverConstraint_multiBodyA_get == 0) {
            return null;
        }
        return new btMultiBody(jBtMultiBodySolverConstraint_multiBodyA_get, false);
    }

    public void setLinkA(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_linkA_set(this.swigCPtr, this, i);
    }

    public int getLinkA() {
        return DynamicsJNI.btMultiBodySolverConstraint_linkA_get(this.swigCPtr, this);
    }

    public void setSolverBodyIdB(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_solverBodyIdB_set(this.swigCPtr, this, i);
    }

    public int getSolverBodyIdB() {
        return DynamicsJNI.btMultiBodySolverConstraint_solverBodyIdB_get(this.swigCPtr, this);
    }

    public void setMultiBodyB(btMultiBody btmultibody) {
        DynamicsJNI.btMultiBodySolverConstraint_multiBodyB_set(this.swigCPtr, this, btMultiBody.getCPtr(btmultibody), btmultibody);
    }

    public btMultiBody getMultiBodyB() {
        long jBtMultiBodySolverConstraint_multiBodyB_get = DynamicsJNI.btMultiBodySolverConstraint_multiBodyB_get(this.swigCPtr, this);
        if (jBtMultiBodySolverConstraint_multiBodyB_get == 0) {
            return null;
        }
        return new btMultiBody(jBtMultiBodySolverConstraint_multiBodyB_get, false);
    }

    public void setLinkB(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_linkB_set(this.swigCPtr, this, i);
    }

    public int getLinkB() {
        return DynamicsJNI.btMultiBodySolverConstraint_linkB_get(this.swigCPtr, this);
    }

    public void setOrgConstraint(btMultiBodyConstraint btmultibodyconstraint) {
        DynamicsJNI.btMultiBodySolverConstraint_orgConstraint_set(this.swigCPtr, this, btMultiBodyConstraint.getCPtr(btmultibodyconstraint), btmultibodyconstraint);
    }

    public btMultiBodyConstraint getOrgConstraint() {
        long jBtMultiBodySolverConstraint_orgConstraint_get = DynamicsJNI.btMultiBodySolverConstraint_orgConstraint_get(this.swigCPtr, this);
        if (jBtMultiBodySolverConstraint_orgConstraint_get == 0) {
            return null;
        }
        return new btMultiBodyConstraint(jBtMultiBodySolverConstraint_orgConstraint_get, false);
    }

    public void setOrgDofIndex(int i) {
        DynamicsJNI.btMultiBodySolverConstraint_orgDofIndex_set(this.swigCPtr, this, i);
    }

    public int getOrgDofIndex() {
        return DynamicsJNI.btMultiBodySolverConstraint_orgDofIndex_get(this.swigCPtr, this);
    }
}
