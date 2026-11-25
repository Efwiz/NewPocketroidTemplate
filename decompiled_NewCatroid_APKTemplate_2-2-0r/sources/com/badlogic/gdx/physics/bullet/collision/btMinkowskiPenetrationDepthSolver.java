package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btMinkowskiPenetrationDepthSolver extends btConvexPenetrationDepthSolver {
    private long swigCPtr;

    protected btMinkowskiPenetrationDepthSolver(String str, long j, boolean z) {
        super(str, CollisionJNI.btMinkowskiPenetrationDepthSolver_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMinkowskiPenetrationDepthSolver(long j, boolean z) {
        this("btMinkowskiPenetrationDepthSolver", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexPenetrationDepthSolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btMinkowskiPenetrationDepthSolver_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMinkowskiPenetrationDepthSolver btminkowskipenetrationdepthsolver) {
        if (btminkowskipenetrationdepthsolver == null) {
            return 0L;
        }
        return btminkowskipenetrationdepthsolver.swigCPtr;
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
                CollisionJNI.delete_btMinkowskiPenetrationDepthSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btMinkowskiPenetrationDepthSolver() {
        this(CollisionJNI.new_btMinkowskiPenetrationDepthSolver(), true);
    }
}
