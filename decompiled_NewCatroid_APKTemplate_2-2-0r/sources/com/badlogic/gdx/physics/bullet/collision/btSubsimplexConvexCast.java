package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btSubsimplexConvexCast extends btConvexCast {
    private long swigCPtr;

    protected btSubsimplexConvexCast(String str, long j, boolean z) {
        super(str, CollisionJNI.btSubsimplexConvexCast_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSubsimplexConvexCast(long j, boolean z) {
        this("btSubsimplexConvexCast", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexCast, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btSubsimplexConvexCast_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSubsimplexConvexCast btsubsimplexconvexcast) {
        if (btsubsimplexconvexcast == null) {
            return 0L;
        }
        return btsubsimplexconvexcast.swigCPtr;
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
                CollisionJNI.delete_btSubsimplexConvexCast(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSubsimplexConvexCast(btConvexShape btconvexshape, btConvexShape btconvexshape2, btVoronoiSimplexSolver btvoronoisimplexsolver) {
        this(CollisionJNI.new_btSubsimplexConvexCast(btConvexShape.getCPtr(btconvexshape), btconvexshape, btConvexShape.getCPtr(btconvexshape2), btconvexshape2, btVoronoiSimplexSolver.getCPtr(btvoronoisimplexsolver), btvoronoisimplexsolver), true);
    }
}
