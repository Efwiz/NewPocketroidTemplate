package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyConstraintSolver extends btSequentialImpulseConstraintSolver {
    private long swigCPtr;

    protected btMultiBodyConstraintSolver(String str, long j, boolean z) {
        super(str, DynamicsJNI.btMultiBodyConstraintSolver_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultiBodyConstraintSolver(long j, boolean z) {
        this("btMultiBodyConstraintSolver", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver, com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btMultiBodyConstraintSolver_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultiBodyConstraintSolver btmultibodyconstraintsolver) {
        if (btmultibodyconstraintsolver == null) {
            return 0L;
        }
        return btmultibodyconstraintsolver.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodyConstraintSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public long operatorNew(long j) {
        return DynamicsJNI.btMultiBodyConstraintSolver_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public void operatorDelete(long j) {
        DynamicsJNI.btMultiBodyConstraintSolver_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btMultiBodyConstraintSolver_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btMultiBodyConstraintSolver_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public long operatorNewArray(long j) {
        return DynamicsJNI.btMultiBodyConstraintSolver_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btMultiBodyConstraintSolver_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btMultiBodyConstraintSolver_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btMultiBodyConstraintSolver_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public float solveGroupCacheFriendlyFinish(SWIGTYPE_p_p_btCollisionObject sWIGTYPE_p_p_btCollisionObject, int i, btContactSolverInfo btcontactsolverinfo) {
        return DynamicsJNI.btMultiBodyConstraintSolver_solveGroupCacheFriendlyFinish(this.swigCPtr, this, SWIGTYPE_p_p_btCollisionObject.getCPtr(sWIGTYPE_p_p_btCollisionObject), i, btContactSolverInfo.getCPtr(btcontactsolverinfo), btcontactsolverinfo);
    }

    public void solveMultiBodyGroup(SWIGTYPE_p_p_btCollisionObject sWIGTYPE_p_p_btCollisionObject, int i, SWIGTYPE_p_p_btPersistentManifold sWIGTYPE_p_p_btPersistentManifold, int i2, SWIGTYPE_p_p_btTypedConstraint sWIGTYPE_p_p_btTypedConstraint, int i3, SWIGTYPE_p_p_btMultiBodyConstraint sWIGTYPE_p_p_btMultiBodyConstraint, int i4, btContactSolverInfo btcontactsolverinfo, btIDebugDraw btidebugdraw, btDispatcher btdispatcher) {
        DynamicsJNI.btMultiBodyConstraintSolver_solveMultiBodyGroup(this.swigCPtr, this, SWIGTYPE_p_p_btCollisionObject.getCPtr(sWIGTYPE_p_p_btCollisionObject), i, SWIGTYPE_p_p_btPersistentManifold.getCPtr(sWIGTYPE_p_p_btPersistentManifold), i2, SWIGTYPE_p_p_btTypedConstraint.getCPtr(sWIGTYPE_p_p_btTypedConstraint), i3, SWIGTYPE_p_p_btMultiBodyConstraint.getCPtr(sWIGTYPE_p_p_btMultiBodyConstraint), i4, btContactSolverInfo.getCPtr(btcontactsolverinfo), btcontactsolverinfo, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public btMultiBodyConstraintSolver() {
        this(DynamicsJNI.new_btMultiBodyConstraintSolver(), true);
    }
}
