package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexPenetrationDepthSolver extends BulletBase {
    private long swigCPtr;

    protected btConvexPenetrationDepthSolver(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConvexPenetrationDepthSolver(long j, boolean z) {
        this("btConvexPenetrationDepthSolver", j, z);
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

    public static long getCPtr(btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver) {
        if (btconvexpenetrationdepthsolver == null) {
            return 0L;
        }
        return btconvexpenetrationdepthsolver.swigCPtr;
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
                CollisionJNI.delete_btConvexPenetrationDepthSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public boolean calcPenDepth(btVoronoiSimplexSolver btvoronoisimplexsolver, btConvexShape btconvexshape, btConvexShape btconvexshape2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, Vector3 vector5, btIDebugDraw btidebugdraw) {
        return CollisionJNI.btConvexPenetrationDepthSolver_calcPenDepth(this.swigCPtr, this, btVoronoiSimplexSolver.getCPtr(btvoronoisimplexsolver), btvoronoisimplexsolver, btConvexShape.getCPtr(btconvexshape), btconvexshape, btConvexShape.getCPtr(btconvexshape2), btconvexshape2, matrix4, matrix5, vector3, vector4, vector5, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }
}
