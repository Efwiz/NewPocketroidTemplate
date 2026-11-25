package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class btConstraintSolverPoolMt extends btConstraintSolver {
    private long swigCPtr;

    protected btConstraintSolverPoolMt(String str, long j, boolean z) {
        super(str, DynamicsJNI.btConstraintSolverPoolMt_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConstraintSolverPoolMt(long j, boolean z) {
        this("btConstraintSolverPoolMt", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btConstraintSolverPoolMt_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConstraintSolverPoolMt btconstraintsolverpoolmt) {
        if (btconstraintsolverpoolmt == null) {
            return 0L;
        }
        return btconstraintsolverpoolmt.swigCPtr;
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
                DynamicsJNI.delete_btConstraintSolverPoolMt(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btConstraintSolverPoolMt(int i) {
        this(DynamicsJNI.new_btConstraintSolverPoolMt__SWIG_0(i), true);
    }

    public btConstraintSolverPoolMt(SWIGTYPE_p_p_btConstraintSolver sWIGTYPE_p_p_btConstraintSolver, int i) {
        this(DynamicsJNI.new_btConstraintSolverPoolMt__SWIG_1(SWIGTYPE_p_p_btConstraintSolver.getCPtr(sWIGTYPE_p_p_btConstraintSolver), i), true);
    }
}
