package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexTriangleCallback extends btTriangleCallback {
    private long swigCPtr;

    protected btConvexTriangleCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexTriangleCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexTriangleCallback(long j, boolean z) {
        this("btConvexTriangleCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexTriangleCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexTriangleCallback btconvextrianglecallback) {
        if (btconvextrianglecallback == null) {
            return 0L;
        }
        return btconvextrianglecallback.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btConvexTriangleCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        CollisionJNI.btConvexTriangleCallback_change_ownership(this, this.swigCPtr, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btConvexTriangleCallback_change_ownership(this, this.swigCPtr, true);
    }

    public long operatorNew(long j) {
        return CollisionJNI.btConvexTriangleCallback_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btConvexTriangleCallback_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btConvexTriangleCallback_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btConvexTriangleCallback_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btConvexTriangleCallback_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btConvexTriangleCallback_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btConvexTriangleCallback_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btConvexTriangleCallback_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setTriangleCount(int i) {
        CollisionJNI.btConvexTriangleCallback_triangleCount_set(this.swigCPtr, this, i);
    }

    public int getTriangleCount() {
        return CollisionJNI.btConvexTriangleCallback_triangleCount_get(this.swigCPtr, this);
    }

    public void setManifoldPtr(btPersistentManifold btpersistentmanifold) {
        CollisionJNI.btConvexTriangleCallback_manifoldPtr_set(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public btPersistentManifold getManifoldPtr() {
        long jBtConvexTriangleCallback_manifoldPtr_get = CollisionJNI.btConvexTriangleCallback_manifoldPtr_get(this.swigCPtr, this);
        if (jBtConvexTriangleCallback_manifoldPtr_get == 0) {
            return null;
        }
        return new btPersistentManifold(jBtConvexTriangleCallback_manifoldPtr_get, false);
    }

    public btConvexTriangleCallback(btDispatcher btdispatcher, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z) {
        this(CollisionJNI.new_btConvexTriangleCallback(btDispatcher.getCPtr(btdispatcher), btdispatcher, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, z), true);
        CollisionJNI.btConvexTriangleCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public void setTimeStepAndCounters(float f, btDispatcherInfo btdispatcherinfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btManifoldResult btmanifoldresult) {
        CollisionJNI.btConvexTriangleCallback_setTimeStepAndCounters(this.swigCPtr, this, f, btDispatcherInfo.getCPtr(btdispatcherinfo), btdispatcherinfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btManifoldResult.getCPtr(btmanifoldresult), btmanifoldresult);
    }

    public void clearWrapperData() {
        CollisionJNI.btConvexTriangleCallback_clearWrapperData(this.swigCPtr, this);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    public void processTriangle(btVector3 btvector3, int i, int i2) {
        if (getClass() == btConvexTriangleCallback.class) {
            CollisionJNI.btConvexTriangleCallback_processTriangle(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, i2);
        } else {
            CollisionJNI.btConvexTriangleCallback_processTriangleSwigExplicitbtConvexTriangleCallback(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, i2);
        }
    }

    public void clearCache() {
        CollisionJNI.btConvexTriangleCallback_clearCache(this.swigCPtr, this);
    }

    public Vector3 getAabbMin() {
        return CollisionJNI.btConvexTriangleCallback_getAabbMin(this.swigCPtr, this);
    }

    public Vector3 getAabbMax() {
        return CollisionJNI.btConvexTriangleCallback_getAabbMax(this.swigCPtr, this);
    }
}
