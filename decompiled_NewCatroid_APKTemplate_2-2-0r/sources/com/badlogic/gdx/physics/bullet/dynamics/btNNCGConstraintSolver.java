package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class btNNCGConstraintSolver extends btSequentialImpulseConstraintSolver {
    private long swigCPtr;

    protected btNNCGConstraintSolver(String str, long j, boolean z) {
        super(str, DynamicsJNI.btNNCGConstraintSolver_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btNNCGConstraintSolver(long j, boolean z) {
        this("btNNCGConstraintSolver", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver, com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btNNCGConstraintSolver_SWIGUpcast(j), z);
    }

    public static long getCPtr(btNNCGConstraintSolver btnncgconstraintsolver) {
        if (btnncgconstraintsolver == null) {
            return 0L;
        }
        return btnncgconstraintsolver.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver, com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver, com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btNNCGConstraintSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public long operatorNew(long j) {
        return DynamicsJNI.btNNCGConstraintSolver_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public void operatorDelete(long j) {
        DynamicsJNI.btNNCGConstraintSolver_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btNNCGConstraintSolver_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btNNCGConstraintSolver_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public long operatorNewArray(long j) {
        return DynamicsJNI.btNNCGConstraintSolver_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btNNCGConstraintSolver_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btNNCGConstraintSolver_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btNNCGConstraintSolver_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btNNCGConstraintSolver() {
        this(DynamicsJNI.new_btNNCGConstraintSolver(), true);
    }

    public void setOnlyForNoneContact(boolean z) {
        DynamicsJNI.btNNCGConstraintSolver_onlyForNoneContact_set(this.swigCPtr, this, z);
    }

    public boolean getOnlyForNoneContact() {
        return DynamicsJNI.btNNCGConstraintSolver_onlyForNoneContact_get(this.swigCPtr, this);
    }
}
