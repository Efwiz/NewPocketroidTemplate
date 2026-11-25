package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;

/* JADX INFO: loaded from: classes8.dex */
public class btConstraintSolver extends BulletBase {
    private long swigCPtr;

    protected btConstraintSolver(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConstraintSolver(long j, boolean z) {
        this("btConstraintSolver", j, z);
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

    public static long getCPtr(btConstraintSolver btconstraintsolver) {
        if (btconstraintsolver == null) {
            return 0L;
        }
        return btconstraintsolver.swigCPtr;
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
                DynamicsJNI.delete_btConstraintSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void prepareSolve(int i, int i2) {
        DynamicsJNI.btConstraintSolver_prepareSolve(this.swigCPtr, this, i, i2);
    }

    public float solveGroup(SWIGTYPE_p_p_btCollisionObject sWIGTYPE_p_p_btCollisionObject, int i, SWIGTYPE_p_p_btPersistentManifold sWIGTYPE_p_p_btPersistentManifold, int i2, SWIGTYPE_p_p_btTypedConstraint sWIGTYPE_p_p_btTypedConstraint, int i3, btContactSolverInfo btcontactsolverinfo, btIDebugDraw btidebugdraw, btDispatcher btdispatcher) {
        return DynamicsJNI.btConstraintSolver_solveGroup(this.swigCPtr, this, SWIGTYPE_p_p_btCollisionObject.getCPtr(sWIGTYPE_p_p_btCollisionObject), i, SWIGTYPE_p_p_btPersistentManifold.getCPtr(sWIGTYPE_p_p_btPersistentManifold), i2, SWIGTYPE_p_p_btTypedConstraint.getCPtr(sWIGTYPE_p_p_btTypedConstraint), i3, btContactSolverInfo.getCPtr(btcontactsolverinfo), btcontactsolverinfo, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void allSolved(btContactSolverInfo btcontactsolverinfo, btIDebugDraw btidebugdraw) {
        DynamicsJNI.btConstraintSolver_allSolved(this.swigCPtr, this, btContactSolverInfo.getCPtr(btcontactsolverinfo), btcontactsolverinfo, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public void reset() {
        DynamicsJNI.btConstraintSolver_reset(this.swigCPtr, this);
    }

    public int getSolverType() {
        return DynamicsJNI.btConstraintSolver_getSolverType(this.swigCPtr, this);
    }
}
