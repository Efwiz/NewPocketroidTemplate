package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btGjkEpaPenetrationDepthSolver extends btConvexPenetrationDepthSolver {
    private long swigCPtr;

    protected btGjkEpaPenetrationDepthSolver(String str, long j, boolean z) {
        super(str, CollisionJNI.btGjkEpaPenetrationDepthSolver_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGjkEpaPenetrationDepthSolver(long j, boolean z) {
        this("btGjkEpaPenetrationDepthSolver", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexPenetrationDepthSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btGjkEpaPenetrationDepthSolver_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGjkEpaPenetrationDepthSolver btgjkepapenetrationdepthsolver) {
        if (btgjkepapenetrationdepthsolver == null) {
            return 0L;
        }
        return btgjkepapenetrationdepthsolver.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexPenetrationDepthSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexPenetrationDepthSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btGjkEpaPenetrationDepthSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGjkEpaPenetrationDepthSolver() {
        this(CollisionJNI.new_btGjkEpaPenetrationDepthSolver(), true);
    }
}
