package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;
import com.badlogic.gdx.physics.bullet.linearmath.btTypedObject;

/* JADX INFO: loaded from: classes8.dex */
public class btTypedConstraint extends btTypedObject {
    private long swigCPtr;

    protected btTypedConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btTypedConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTypedConstraint(long j, boolean z) {
        this("btTypedConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btTypedConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTypedConstraint bttypedconstraint) {
        if (bttypedconstraint == null) {
            return 0L;
        }
        return bttypedconstraint.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btTypedConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btTypedConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btTypedConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btTypedConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btTypedConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btTypedConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btTypedConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btTypedConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btTypedConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public static class btConstraintInfo1 extends BulletBase {
        private long swigCPtr;

        protected btConstraintInfo1(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public btConstraintInfo1(long j, boolean z) {
            this("btConstraintInfo1", j, z);
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

        public static long getCPtr(btConstraintInfo1 btconstraintinfo1) {
            if (btconstraintinfo1 == null) {
                return 0L;
            }
            return btconstraintinfo1.swigCPtr;
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
                    DynamicsJNI.delete_btTypedConstraint_btConstraintInfo1(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setNumConstraintRows(int i) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo1_numConstraintRows_set(this.swigCPtr, this, i);
        }

        public int getNumConstraintRows() {
            return DynamicsJNI.btTypedConstraint_btConstraintInfo1_numConstraintRows_get(this.swigCPtr, this);
        }

        public void setNub(int i) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo1_nub_set(this.swigCPtr, this, i);
        }

        public int getNub() {
            return DynamicsJNI.btTypedConstraint_btConstraintInfo1_nub_get(this.swigCPtr, this);
        }

        public btConstraintInfo1() {
            this(DynamicsJNI.new_btTypedConstraint_btConstraintInfo1(), true);
        }
    }

    public static btRigidBody getFixedBody() {
        return btRigidBody.getInstance(DynamicsJNI.btTypedConstraint_getFixedBody(), false);
    }

    public static class btConstraintInfo2 extends BulletBase {
        private long swigCPtr;

        protected btConstraintInfo2(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public btConstraintInfo2(long j, boolean z) {
            this("btConstraintInfo2", j, z);
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

        public static long getCPtr(btConstraintInfo2 btconstraintinfo2) {
            if (btconstraintinfo2 == null) {
                return 0L;
            }
            return btconstraintinfo2.swigCPtr;
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
                    DynamicsJNI.delete_btTypedConstraint_btConstraintInfo2(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setFps(float f) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_fps_set(this.swigCPtr, this, f);
        }

        public float getFps() {
            return DynamicsJNI.btTypedConstraint_btConstraintInfo2_fps_get(this.swigCPtr, this);
        }

        public void setErp(float f) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_erp_set(this.swigCPtr, this, f);
        }

        public float getErp() {
            return DynamicsJNI.btTypedConstraint_btConstraintInfo2_erp_get(this.swigCPtr, this);
        }

        public void setJ1linearAxis(SWIGTYPE_p_float sWIGTYPE_p_float) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_J1linearAxis_set(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
        }

        public SWIGTYPE_p_float getJ1linearAxis() {
            long jBtTypedConstraint_btConstraintInfo2_J1linearAxis_get = DynamicsJNI.btTypedConstraint_btConstraintInfo2_J1linearAxis_get(this.swigCPtr, this);
            if (jBtTypedConstraint_btConstraintInfo2_J1linearAxis_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_float(jBtTypedConstraint_btConstraintInfo2_J1linearAxis_get, false);
        }

        public void setJ1angularAxis(SWIGTYPE_p_float sWIGTYPE_p_float) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_J1angularAxis_set(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
        }

        public SWIGTYPE_p_float getJ1angularAxis() {
            long jBtTypedConstraint_btConstraintInfo2_J1angularAxis_get = DynamicsJNI.btTypedConstraint_btConstraintInfo2_J1angularAxis_get(this.swigCPtr, this);
            if (jBtTypedConstraint_btConstraintInfo2_J1angularAxis_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_float(jBtTypedConstraint_btConstraintInfo2_J1angularAxis_get, false);
        }

        public void setJ2linearAxis(SWIGTYPE_p_float sWIGTYPE_p_float) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_J2linearAxis_set(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
        }

        public SWIGTYPE_p_float getJ2linearAxis() {
            long jBtTypedConstraint_btConstraintInfo2_J2linearAxis_get = DynamicsJNI.btTypedConstraint_btConstraintInfo2_J2linearAxis_get(this.swigCPtr, this);
            if (jBtTypedConstraint_btConstraintInfo2_J2linearAxis_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_float(jBtTypedConstraint_btConstraintInfo2_J2linearAxis_get, false);
        }

        public void setJ2angularAxis(SWIGTYPE_p_float sWIGTYPE_p_float) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_J2angularAxis_set(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
        }

        public SWIGTYPE_p_float getJ2angularAxis() {
            long jBtTypedConstraint_btConstraintInfo2_J2angularAxis_get = DynamicsJNI.btTypedConstraint_btConstraintInfo2_J2angularAxis_get(this.swigCPtr, this);
            if (jBtTypedConstraint_btConstraintInfo2_J2angularAxis_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_float(jBtTypedConstraint_btConstraintInfo2_J2angularAxis_get, false);
        }

        public void setRowskip(int i) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_rowskip_set(this.swigCPtr, this, i);
        }

        public int getRowskip() {
            return DynamicsJNI.btTypedConstraint_btConstraintInfo2_rowskip_get(this.swigCPtr, this);
        }

        public void setConstraintError(SWIGTYPE_p_float sWIGTYPE_p_float) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_constraintError_set(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
        }

        public SWIGTYPE_p_float getConstraintError() {
            long jBtTypedConstraint_btConstraintInfo2_constraintError_get = DynamicsJNI.btTypedConstraint_btConstraintInfo2_constraintError_get(this.swigCPtr, this);
            if (jBtTypedConstraint_btConstraintInfo2_constraintError_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_float(jBtTypedConstraint_btConstraintInfo2_constraintError_get, false);
        }

        public void setCfm(SWIGTYPE_p_float sWIGTYPE_p_float) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_cfm_set(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
        }

        public SWIGTYPE_p_float getCfm() {
            long jBtTypedConstraint_btConstraintInfo2_cfm_get = DynamicsJNI.btTypedConstraint_btConstraintInfo2_cfm_get(this.swigCPtr, this);
            if (jBtTypedConstraint_btConstraintInfo2_cfm_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_float(jBtTypedConstraint_btConstraintInfo2_cfm_get, false);
        }

        public void setLowerLimit(SWIGTYPE_p_float sWIGTYPE_p_float) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_lowerLimit_set(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
        }

        public SWIGTYPE_p_float getLowerLimit() {
            long jBtTypedConstraint_btConstraintInfo2_lowerLimit_get = DynamicsJNI.btTypedConstraint_btConstraintInfo2_lowerLimit_get(this.swigCPtr, this);
            if (jBtTypedConstraint_btConstraintInfo2_lowerLimit_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_float(jBtTypedConstraint_btConstraintInfo2_lowerLimit_get, false);
        }

        public void setUpperLimit(SWIGTYPE_p_float sWIGTYPE_p_float) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_upperLimit_set(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
        }

        public SWIGTYPE_p_float getUpperLimit() {
            long jBtTypedConstraint_btConstraintInfo2_upperLimit_get = DynamicsJNI.btTypedConstraint_btConstraintInfo2_upperLimit_get(this.swigCPtr, this);
            if (jBtTypedConstraint_btConstraintInfo2_upperLimit_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_float(jBtTypedConstraint_btConstraintInfo2_upperLimit_get, false);
        }

        public void setNumIterations(int i) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_numIterations_set(this.swigCPtr, this, i);
        }

        public int getNumIterations() {
            return DynamicsJNI.btTypedConstraint_btConstraintInfo2_numIterations_get(this.swigCPtr, this);
        }

        public void setDamping(float f) {
            DynamicsJNI.btTypedConstraint_btConstraintInfo2_damping_set(this.swigCPtr, this, f);
        }

        public float getDamping() {
            return DynamicsJNI.btTypedConstraint_btConstraintInfo2_damping_get(this.swigCPtr, this);
        }

        public btConstraintInfo2() {
            this(DynamicsJNI.new_btTypedConstraint_btConstraintInfo2(), true);
        }
    }

    public int getOverrideNumSolverIterations() {
        return DynamicsJNI.btTypedConstraint_getOverrideNumSolverIterations(this.swigCPtr, this);
    }

    public void setOverrideNumSolverIterations(int i) {
        DynamicsJNI.btTypedConstraint_setOverrideNumSolverIterations(this.swigCPtr, this, i);
    }

    public void buildJacobian() {
        DynamicsJNI.btTypedConstraint_buildJacobian(this.swigCPtr, this);
    }

    public void setupSolverConstraint(SWIGTYPE_p_btAlignedObjectArrayT_btSolverConstraint_t sWIGTYPE_p_btAlignedObjectArrayT_btSolverConstraint_t, int i, int i2, float f) {
        DynamicsJNI.btTypedConstraint_setupSolverConstraint(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSolverConstraint_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSolverConstraint_t), i, i2, f);
    }

    public void getInfo1(btConstraintInfo1 btconstraintinfo1) {
        DynamicsJNI.btTypedConstraint_getInfo1(this.swigCPtr, this, btConstraintInfo1.getCPtr(btconstraintinfo1), btconstraintinfo1);
    }

    public void getInfo2(btConstraintInfo2 btconstraintinfo2) {
        DynamicsJNI.btTypedConstraint_getInfo2(this.swigCPtr, this, btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2);
    }

    public void internalSetAppliedImpulse(float f) {
        DynamicsJNI.btTypedConstraint_internalSetAppliedImpulse(this.swigCPtr, this, f);
    }

    public float internalGetAppliedImpulse() {
        return DynamicsJNI.btTypedConstraint_internalGetAppliedImpulse(this.swigCPtr, this);
    }

    public float getBreakingImpulseThreshold() {
        return DynamicsJNI.btTypedConstraint_getBreakingImpulseThreshold(this.swigCPtr, this);
    }

    public void setBreakingImpulseThreshold(float f) {
        DynamicsJNI.btTypedConstraint_setBreakingImpulseThreshold(this.swigCPtr, this, f);
    }

    public boolean isEnabled() {
        return DynamicsJNI.btTypedConstraint_isEnabled(this.swigCPtr, this);
    }

    public void setEnabled(boolean z) {
        DynamicsJNI.btTypedConstraint_setEnabled(this.swigCPtr, this, z);
    }

    public void solveConstraintObsolete(btSolverBody btsolverbody, btSolverBody btsolverbody2, float f) {
        DynamicsJNI.btTypedConstraint_solveConstraintObsolete(this.swigCPtr, this, btSolverBody.getCPtr(btsolverbody), btsolverbody, btSolverBody.getCPtr(btsolverbody2), btsolverbody2, f);
    }

    public btRigidBody getRigidBodyAConst() {
        return btRigidBody.getInstance(DynamicsJNI.btTypedConstraint_getRigidBodyAConst(this.swigCPtr, this), false);
    }

    public btRigidBody getRigidBodyBConst() {
        return btRigidBody.getInstance(DynamicsJNI.btTypedConstraint_getRigidBodyBConst(this.swigCPtr, this), false);
    }

    public btRigidBody getRigidBodyA() {
        return btRigidBody.getInstance(DynamicsJNI.btTypedConstraint_getRigidBodyA(this.swigCPtr, this), false);
    }

    public btRigidBody getRigidBodyB() {
        return btRigidBody.getInstance(DynamicsJNI.btTypedConstraint_getRigidBodyB(this.swigCPtr, this), false);
    }

    public int getUserConstraintType() {
        return DynamicsJNI.btTypedConstraint_getUserConstraintType(this.swigCPtr, this);
    }

    public void setUserConstraintType(int i) {
        DynamicsJNI.btTypedConstraint_setUserConstraintType(this.swigCPtr, this, i);
    }

    public void setUserConstraintId(int i) {
        DynamicsJNI.btTypedConstraint_setUserConstraintId(this.swigCPtr, this, i);
    }

    public int getUserConstraintId() {
        return DynamicsJNI.btTypedConstraint_getUserConstraintId(this.swigCPtr, this);
    }

    public void setUserConstraintPtr(long j) {
        DynamicsJNI.btTypedConstraint_setUserConstraintPtr(this.swigCPtr, this, j);
    }

    public long getUserConstraintPtr() {
        return DynamicsJNI.btTypedConstraint_getUserConstraintPtr(this.swigCPtr, this);
    }

    public void setJointFeedback(btJointFeedback btjointfeedback) {
        DynamicsJNI.btTypedConstraint_setJointFeedback(this.swigCPtr, this, btJointFeedback.getCPtr(btjointfeedback), btjointfeedback);
    }

    public btJointFeedback getJointFeedbackConst() {
        long jBtTypedConstraint_getJointFeedbackConst = DynamicsJNI.btTypedConstraint_getJointFeedbackConst(this.swigCPtr, this);
        if (jBtTypedConstraint_getJointFeedbackConst == 0) {
            return null;
        }
        return new btJointFeedback(jBtTypedConstraint_getJointFeedbackConst, false);
    }

    public btJointFeedback getJointFeedback() {
        long jBtTypedConstraint_getJointFeedback = DynamicsJNI.btTypedConstraint_getJointFeedback(this.swigCPtr, this);
        if (jBtTypedConstraint_getJointFeedback == 0) {
            return null;
        }
        return new btJointFeedback(jBtTypedConstraint_getJointFeedback, false);
    }

    public int getUid() {
        return DynamicsJNI.btTypedConstraint_getUid(this.swigCPtr, this);
    }

    public boolean needsFeedback() {
        return DynamicsJNI.btTypedConstraint_needsFeedback(this.swigCPtr, this);
    }

    public void enableFeedback(boolean z) {
        DynamicsJNI.btTypedConstraint_enableFeedback(this.swigCPtr, this, z);
    }

    public float getAppliedImpulse() {
        return DynamicsJNI.btTypedConstraint_getAppliedImpulse(this.swigCPtr, this);
    }

    public int getConstraintType() {
        return DynamicsJNI.btTypedConstraint_getConstraintType(this.swigCPtr, this);
    }

    public void setDbgDrawSize(float f) {
        DynamicsJNI.btTypedConstraint_setDbgDrawSize(this.swigCPtr, this, f);
    }

    public float getDbgDrawSize() {
        return DynamicsJNI.btTypedConstraint_getDbgDrawSize(this.swigCPtr, this);
    }

    public void setParam(int i, float f, int i2) {
        DynamicsJNI.btTypedConstraint_setParam__SWIG_0(this.swigCPtr, this, i, f, i2);
    }

    public void setParam(int i, float f) {
        DynamicsJNI.btTypedConstraint_setParam__SWIG_1(this.swigCPtr, this, i, f);
    }

    public float getParam(int i, int i2) {
        return DynamicsJNI.btTypedConstraint_getParam__SWIG_0(this.swigCPtr, this, i, i2);
    }

    public float getParam(int i) {
        return DynamicsJNI.btTypedConstraint_getParam__SWIG_1(this.swigCPtr, this, i);
    }

    public int calculateSerializeBufferSize() {
        return DynamicsJNI.btTypedConstraint_calculateSerializeBufferSize(this.swigCPtr, this);
    }

    public String serialize(long j, btSerializer btserializer) {
        return DynamicsJNI.btTypedConstraint_serialize(this.swigCPtr, this, j, btSerializer.getCPtr(btserializer), btserializer);
    }
}
