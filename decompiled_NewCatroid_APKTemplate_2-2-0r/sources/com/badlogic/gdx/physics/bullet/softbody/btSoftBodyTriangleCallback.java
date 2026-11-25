package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.physics.bullet.collision.btTriangleCallback;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBodyTriangleCallback extends btTriangleCallback {
    private long swigCPtr;

    protected btSoftBodyTriangleCallback(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btSoftBodyTriangleCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSoftBodyTriangleCallback(long j, boolean z) {
        this("btSoftBodyTriangleCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btSoftBodyTriangleCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSoftBodyTriangleCallback btsoftbodytrianglecallback) {
        if (btsoftbodytrianglecallback == null) {
            return 0L;
        }
        return btsoftbodytrianglecallback.swigCPtr;
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
                SoftbodyJNI.delete_btSoftBodyTriangleCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTriangleCount(int i) {
        SoftbodyJNI.btSoftBodyTriangleCallback_triangleCount_set(this.swigCPtr, this, i);
    }

    public int getTriangleCount() {
        return SoftbodyJNI.btSoftBodyTriangleCallback_triangleCount_get(this.swigCPtr, this);
    }

    public btSoftBodyTriangleCallback(btDispatcher btdispatcher, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z) {
        this(SoftbodyJNI.new_btSoftBodyTriangleCallback(btDispatcher.getCPtr(btdispatcher), btdispatcher, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, z), true);
    }

    public void setTimeStepAndCounters(float f, btCollisionObjectWrapper btcollisionobjectwrapper, btDispatcherInfo btdispatcherinfo, btManifoldResult btmanifoldresult) {
        SoftbodyJNI.btSoftBodyTriangleCallback_setTimeStepAndCounters(this.swigCPtr, this, f, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btDispatcherInfo.getCPtr(btdispatcherinfo), btdispatcherinfo, btManifoldResult.getCPtr(btmanifoldresult), btmanifoldresult);
    }

    public void clearCache() {
        SoftbodyJNI.btSoftBodyTriangleCallback_clearCache(this.swigCPtr, this);
    }

    public Vector3 getAabbMin() {
        return SoftbodyJNI.btSoftBodyTriangleCallback_getAabbMin(this.swigCPtr, this);
    }

    public Vector3 getAabbMax() {
        return SoftbodyJNI.btSoftBodyTriangleCallback_getAabbMax(this.swigCPtr, this);
    }
}
