package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btScalarArray;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3Array;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyJacobianData extends BulletBase {
    private long swigCPtr;

    protected btMultiBodyJacobianData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiBodyJacobianData(long j, boolean z) {
        this("btMultiBodyJacobianData", j, z);
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

    public static long getCPtr(btMultiBodyJacobianData btmultibodyjacobiandata) {
        if (btmultibodyjacobiandata == null) {
            return 0L;
        }
        return btmultibodyjacobiandata.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodyJacobianData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setJacobians(btScalarArray btscalararray) {
        DynamicsJNI.btMultiBodyJacobianData_jacobians_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getJacobians() {
        long jBtMultiBodyJacobianData_jacobians_get = DynamicsJNI.btMultiBodyJacobianData_jacobians_get(this.swigCPtr, this);
        if (jBtMultiBodyJacobianData_jacobians_get == 0) {
            return null;
        }
        return new btScalarArray(jBtMultiBodyJacobianData_jacobians_get, false);
    }

    public void setDeltaVelocitiesUnitImpulse(btScalarArray btscalararray) {
        DynamicsJNI.btMultiBodyJacobianData_deltaVelocitiesUnitImpulse_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getDeltaVelocitiesUnitImpulse() {
        long jBtMultiBodyJacobianData_deltaVelocitiesUnitImpulse_get = DynamicsJNI.btMultiBodyJacobianData_deltaVelocitiesUnitImpulse_get(this.swigCPtr, this);
        if (jBtMultiBodyJacobianData_deltaVelocitiesUnitImpulse_get == 0) {
            return null;
        }
        return new btScalarArray(jBtMultiBodyJacobianData_deltaVelocitiesUnitImpulse_get, false);
    }

    public void setDeltaVelocities(btScalarArray btscalararray) {
        DynamicsJNI.btMultiBodyJacobianData_deltaVelocities_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getDeltaVelocities() {
        long jBtMultiBodyJacobianData_deltaVelocities_get = DynamicsJNI.btMultiBodyJacobianData_deltaVelocities_get(this.swigCPtr, this);
        if (jBtMultiBodyJacobianData_deltaVelocities_get == 0) {
            return null;
        }
        return new btScalarArray(jBtMultiBodyJacobianData_deltaVelocities_get, false);
    }

    public void setScratch_r(btScalarArray btscalararray) {
        DynamicsJNI.btMultiBodyJacobianData_scratch_r_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getScratch_r() {
        long jBtMultiBodyJacobianData_scratch_r_get = DynamicsJNI.btMultiBodyJacobianData_scratch_r_get(this.swigCPtr, this);
        if (jBtMultiBodyJacobianData_scratch_r_get == 0) {
            return null;
        }
        return new btScalarArray(jBtMultiBodyJacobianData_scratch_r_get, false);
    }

    public void setScratch_v(btVector3Array btvector3array) {
        DynamicsJNI.btMultiBodyJacobianData_scratch_v_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public btVector3Array getScratch_v() {
        long jBtMultiBodyJacobianData_scratch_v_get = DynamicsJNI.btMultiBodyJacobianData_scratch_v_get(this.swigCPtr, this);
        if (jBtMultiBodyJacobianData_scratch_v_get == 0) {
            return null;
        }
        return new btVector3Array(jBtMultiBodyJacobianData_scratch_v_get, false);
    }

    public void setScratch_m(SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t) {
        DynamicsJNI.btMultiBodyJacobianData_scratch_m_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t getScratch_m() {
        long jBtMultiBodyJacobianData_scratch_m_get = DynamicsJNI.btMultiBodyJacobianData_scratch_m_get(this.swigCPtr, this);
        if (jBtMultiBodyJacobianData_scratch_m_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t(jBtMultiBodyJacobianData_scratch_m_get, false);
    }

    public void setSolverBodyPool(SWIGTYPE_p_btAlignedObjectArrayT_btSolverBody_t sWIGTYPE_p_btAlignedObjectArrayT_btSolverBody_t) {
        DynamicsJNI.btMultiBodyJacobianData_solverBodyPool_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSolverBody_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSolverBody_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSolverBody_t getSolverBodyPool() {
        long jBtMultiBodyJacobianData_solverBodyPool_get = DynamicsJNI.btMultiBodyJacobianData_solverBodyPool_get(this.swigCPtr, this);
        if (jBtMultiBodyJacobianData_solverBodyPool_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSolverBody_t(jBtMultiBodyJacobianData_solverBodyPool_get, false);
    }

    public void setFixedBodyId(int i) {
        DynamicsJNI.btMultiBodyJacobianData_fixedBodyId_set(this.swigCPtr, this, i);
    }

    public int getFixedBodyId() {
        return DynamicsJNI.btMultiBodyJacobianData_fixedBodyId_get(this.swigCPtr, this);
    }

    public btMultiBodyJacobianData() {
        this(DynamicsJNI.new_btMultiBodyJacobianData(), true);
    }
}
