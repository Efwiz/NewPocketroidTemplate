package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btContinuousConvexCollision extends btConvexCast {
    private long swigCPtr;

    protected btContinuousConvexCollision(String str, long j, boolean z) {
        super(str, CollisionJNI.btContinuousConvexCollision_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btContinuousConvexCollision(long j, boolean z) {
        this("btContinuousConvexCollision", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexCast, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btContinuousConvexCollision_SWIGUpcast(j), z);
    }

    public static long getCPtr(btContinuousConvexCollision btcontinuousconvexcollision) {
        if (btcontinuousconvexcollision == null) {
            return 0L;
        }
        return btcontinuousconvexcollision.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexCast, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexCast, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btContinuousConvexCollision(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btContinuousConvexCollision(btConvexShape btconvexshape, btConvexShape btconvexshape2, btVoronoiSimplexSolver btvoronoisimplexsolver, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver) {
        this(CollisionJNI.new_btContinuousConvexCollision__SWIG_0(btConvexShape.getCPtr(btconvexshape), btconvexshape, btConvexShape.getCPtr(btconvexshape2), btconvexshape2, btVoronoiSimplexSolver.getCPtr(btvoronoisimplexsolver), btvoronoisimplexsolver, btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver), true);
    }

    public btContinuousConvexCollision(btConvexShape btconvexshape, btStaticPlaneShape btstaticplaneshape) {
        this(CollisionJNI.new_btContinuousConvexCollision__SWIG_1(btConvexShape.getCPtr(btconvexshape), btconvexshape, btStaticPlaneShape.getCPtr(btstaticplaneshape), btstaticplaneshape), true);
    }
}
