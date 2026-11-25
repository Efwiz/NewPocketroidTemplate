package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class btMLCPSolver extends btSequentialImpulseConstraintSolver {
    private long swigCPtr;

    protected btMLCPSolver(String str, long j, boolean z) {
        super(str, DynamicsJNI.btMLCPSolver_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMLCPSolver(long j, boolean z) {
        this("btMLCPSolver", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver, com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btMLCPSolver_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMLCPSolver btmlcpsolver) {
        if (btmlcpsolver == null) {
            return 0L;
        }
        return btmlcpsolver.swigCPtr;
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
                DynamicsJNI.delete_btMLCPSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btMLCPSolver(btMLCPSolverInterface btmlcpsolverinterface) {
        this(DynamicsJNI.new_btMLCPSolver(btMLCPSolverInterface.getCPtr(btmlcpsolverinterface), btmlcpsolverinterface), true);
    }

    public void setMLCPSolver(btMLCPSolverInterface btmlcpsolverinterface) {
        DynamicsJNI.btMLCPSolver_setMLCPSolver(this.swigCPtr, this, btMLCPSolverInterface.getCPtr(btmlcpsolverinterface), btmlcpsolverinterface);
    }

    public int getNumFallbacks() {
        return DynamicsJNI.btMLCPSolver_getNumFallbacks(this.swigCPtr, this);
    }

    public void setNumFallbacks(int i) {
        DynamicsJNI.btMLCPSolver_setNumFallbacks(this.swigCPtr, this, i);
    }
}
