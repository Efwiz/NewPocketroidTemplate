package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class btSequentialImpulseConstraintSolver extends btConstraintSolver {
    private long swigCPtr;

    protected btSequentialImpulseConstraintSolver(String str, long j, boolean z) {
        super(str, DynamicsJNI.btSequentialImpulseConstraintSolver_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSequentialImpulseConstraintSolver(long j, boolean z) {
        this("btSequentialImpulseConstraintSolver", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btSequentialImpulseConstraintSolver_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver) {
        if (btsequentialimpulseconstraintsolver == null) {
            return 0L;
        }
        return btsequentialimpulseconstraintsolver.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btSequentialImpulseConstraintSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btSequentialImpulseConstraintSolver_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btSequentialImpulseConstraintSolver_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btSequentialImpulseConstraintSolver_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btSequentialImpulseConstraintSolver_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btSequentialImpulseConstraintSolver_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btSequentialImpulseConstraintSolver_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btSequentialImpulseConstraintSolver_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btSequentialImpulseConstraintSolver_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btSequentialImpulseConstraintSolver() {
        this(DynamicsJNI.new_btSequentialImpulseConstraintSolver(), true);
    }

    public long btRand2() {
        return DynamicsJNI.btSequentialImpulseConstraintSolver_btRand2(this.swigCPtr, this);
    }

    public int btRandInt2(int i) {
        return DynamicsJNI.btSequentialImpulseConstraintSolver_btRandInt2(this.swigCPtr, this, i);
    }

    public void setRandSeed(long j) {
        DynamicsJNI.btSequentialImpulseConstraintSolver_setRandSeed(this.swigCPtr, this, j);
    }

    public long getRandSeed() {
        return DynamicsJNI.btSequentialImpulseConstraintSolver_getRandSeed(this.swigCPtr, this);
    }

    public SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar getActiveConstraintRowSolverGeneric() {
        long jBtSequentialImpulseConstraintSolver_getActiveConstraintRowSolverGeneric = DynamicsJNI.btSequentialImpulseConstraintSolver_getActiveConstraintRowSolverGeneric(this.swigCPtr, this);
        if (jBtSequentialImpulseConstraintSolver_getActiveConstraintRowSolverGeneric == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar(jBtSequentialImpulseConstraintSolver_getActiveConstraintRowSolverGeneric, false);
    }

    public void setConstraintRowSolverGeneric(SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar sWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar) {
        DynamicsJNI.btSequentialImpulseConstraintSolver_setConstraintRowSolverGeneric(this.swigCPtr, this, SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar.getCPtr(sWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar));
    }

    public SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar getActiveConstraintRowSolverLowerLimit() {
        long jBtSequentialImpulseConstraintSolver_getActiveConstraintRowSolverLowerLimit = DynamicsJNI.btSequentialImpulseConstraintSolver_getActiveConstraintRowSolverLowerLimit(this.swigCPtr, this);
        if (jBtSequentialImpulseConstraintSolver_getActiveConstraintRowSolverLowerLimit == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar(jBtSequentialImpulseConstraintSolver_getActiveConstraintRowSolverLowerLimit, false);
    }

    public void setConstraintRowSolverLowerLimit(SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar sWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar) {
        DynamicsJNI.btSequentialImpulseConstraintSolver_setConstraintRowSolverLowerLimit(this.swigCPtr, this, SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar.getCPtr(sWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar));
    }

    public SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar getScalarConstraintRowSolverGeneric() {
        long jBtSequentialImpulseConstraintSolver_getScalarConstraintRowSolverGeneric = DynamicsJNI.btSequentialImpulseConstraintSolver_getScalarConstraintRowSolverGeneric(this.swigCPtr, this);
        if (jBtSequentialImpulseConstraintSolver_getScalarConstraintRowSolverGeneric == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar(jBtSequentialImpulseConstraintSolver_getScalarConstraintRowSolverGeneric, false);
    }

    public SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar getScalarConstraintRowSolverLowerLimit() {
        long jBtSequentialImpulseConstraintSolver_getScalarConstraintRowSolverLowerLimit = DynamicsJNI.btSequentialImpulseConstraintSolver_getScalarConstraintRowSolverLowerLimit(this.swigCPtr, this);
        if (jBtSequentialImpulseConstraintSolver_getScalarConstraintRowSolverLowerLimit == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_r_btSolverBody_r_btSolverBody_r_q_const__btSolverConstraint__btSimdScalar(jBtSequentialImpulseConstraintSolver_getScalarConstraintRowSolverLowerLimit, false);
    }
}
