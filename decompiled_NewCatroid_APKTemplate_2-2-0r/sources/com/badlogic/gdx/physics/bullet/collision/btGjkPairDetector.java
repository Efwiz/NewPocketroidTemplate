package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;

/* JADX INFO: loaded from: classes8.dex */
public class btGjkPairDetector extends btDiscreteCollisionDetectorInterface {
    private long swigCPtr;

    protected btGjkPairDetector(String str, long j, boolean z) {
        super(str, CollisionJNI.btGjkPairDetector_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGjkPairDetector(long j, boolean z) {
        this("btGjkPairDetector", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btGjkPairDetector_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGjkPairDetector btgjkpairdetector) {
        if (btgjkpairdetector == null) {
            return 0L;
        }
        return btgjkpairdetector.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btGjkPairDetector(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setLastUsedMethod(int i) {
        CollisionJNI.btGjkPairDetector_lastUsedMethod_set(this.swigCPtr, this, i);
    }

    public int getLastUsedMethod() {
        return CollisionJNI.btGjkPairDetector_lastUsedMethod_get(this.swigCPtr, this);
    }

    public void setCurIter(int i) {
        CollisionJNI.btGjkPairDetector_curIter_set(this.swigCPtr, this, i);
    }

    public int getCurIter() {
        return CollisionJNI.btGjkPairDetector_curIter_get(this.swigCPtr, this);
    }

    public void setDegenerateSimplex(int i) {
        CollisionJNI.btGjkPairDetector_degenerateSimplex_set(this.swigCPtr, this, i);
    }

    public int getDegenerateSimplex() {
        return CollisionJNI.btGjkPairDetector_degenerateSimplex_get(this.swigCPtr, this);
    }

    public void setCatchDegeneracies(int i) {
        CollisionJNI.btGjkPairDetector_catchDegeneracies_set(this.swigCPtr, this, i);
    }

    public int getCatchDegeneracies() {
        return CollisionJNI.btGjkPairDetector_catchDegeneracies_get(this.swigCPtr, this);
    }

    public void setFixContactNormalDirection(int i) {
        CollisionJNI.btGjkPairDetector_fixContactNormalDirection_set(this.swigCPtr, this, i);
    }

    public int getFixContactNormalDirection() {
        return CollisionJNI.btGjkPairDetector_fixContactNormalDirection_get(this.swigCPtr, this);
    }

    public btGjkPairDetector(btConvexShape btconvexshape, btConvexShape btconvexshape2, btVoronoiSimplexSolver btvoronoisimplexsolver, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver) {
        this(CollisionJNI.new_btGjkPairDetector__SWIG_0(btConvexShape.getCPtr(btconvexshape), btconvexshape, btConvexShape.getCPtr(btconvexshape2), btconvexshape2, btVoronoiSimplexSolver.getCPtr(btvoronoisimplexsolver), btvoronoisimplexsolver, btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver), true);
    }

    public btGjkPairDetector(btConvexShape btconvexshape, btConvexShape btconvexshape2, int i, int i2, float f, float f2, btVoronoiSimplexSolver btvoronoisimplexsolver, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver) {
        this(CollisionJNI.new_btGjkPairDetector__SWIG_1(btConvexShape.getCPtr(btconvexshape), btconvexshape, btConvexShape.getCPtr(btconvexshape2), btconvexshape2, i, i2, f, f2, btVoronoiSimplexSolver.getCPtr(btvoronoisimplexsolver), btvoronoisimplexsolver, btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface
    public void getClosestPoints(btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, btDiscreteCollisionDetectorInterface.Result result, btIDebugDraw btidebugdraw, boolean z) {
        CollisionJNI.btGjkPairDetector_getClosestPoints__SWIG_0(this.swigCPtr, this, btDiscreteCollisionDetectorInterface.ClosestPointInput.getCPtr(closestPointInput), closestPointInput, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, z);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface
    public void getClosestPoints(btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, btDiscreteCollisionDetectorInterface.Result result, btIDebugDraw btidebugdraw) {
        CollisionJNI.btGjkPairDetector_getClosestPoints__SWIG_1(this.swigCPtr, this, btDiscreteCollisionDetectorInterface.ClosestPointInput.getCPtr(closestPointInput), closestPointInput, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public void getClosestPointsNonVirtual(btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, btDiscreteCollisionDetectorInterface.Result result, btIDebugDraw btidebugdraw) {
        CollisionJNI.btGjkPairDetector_getClosestPointsNonVirtual(this.swigCPtr, this, btDiscreteCollisionDetectorInterface.ClosestPointInput.getCPtr(closestPointInput), closestPointInput, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public void setMinkowskiA(btConvexShape btconvexshape) {
        CollisionJNI.btGjkPairDetector_setMinkowskiA(this.swigCPtr, this, btConvexShape.getCPtr(btconvexshape), btconvexshape);
    }

    public void setMinkowskiB(btConvexShape btconvexshape) {
        CollisionJNI.btGjkPairDetector_setMinkowskiB(this.swigCPtr, this, btConvexShape.getCPtr(btconvexshape), btconvexshape);
    }

    public void setCachedSeperatingAxis(Vector3 vector3) {
        CollisionJNI.btGjkPairDetector_setCachedSeperatingAxis(this.swigCPtr, this, vector3);
    }

    public Vector3 getCachedSeparatingAxis() {
        return CollisionJNI.btGjkPairDetector_getCachedSeparatingAxis(this.swigCPtr, this);
    }

    public float getCachedSeparatingDistance() {
        return CollisionJNI.btGjkPairDetector_getCachedSeparatingDistance(this.swigCPtr, this);
    }

    public void setPenetrationDepthSolver(btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver) {
        CollisionJNI.btGjkPairDetector_setPenetrationDepthSolver(this.swigCPtr, this, btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver);
    }

    public void setIgnoreMargin(boolean z) {
        CollisionJNI.btGjkPairDetector_setIgnoreMargin(this.swigCPtr, this, z);
    }
}
