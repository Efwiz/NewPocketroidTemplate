package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btBroadphaseInterface extends BulletBase {
    private long swigCPtr;

    protected btBroadphaseInterface(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBroadphaseInterface(long j, boolean z) {
        this("btBroadphaseInterface", j, z);
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

    public static long getCPtr(btBroadphaseInterface btbroadphaseinterface) {
        if (btbroadphaseinterface == null) {
            return 0L;
        }
        return btbroadphaseinterface.swigCPtr;
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
                CollisionJNI.delete_btBroadphaseInterface(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btBroadphaseProxy createProxy(Vector3 vector3, Vector3 vector4, int i, long j, int i2, int i3, btDispatcher btdispatcher) {
        return btBroadphaseProxy.internalTemp(CollisionJNI.btBroadphaseInterface_createProxy(this.swigCPtr, this, vector3, vector4, i, j, i2, i3, btDispatcher.getCPtr(btdispatcher), btdispatcher), false);
    }

    public void destroyProxy(btBroadphaseProxy btbroadphaseproxy, btDispatcher btdispatcher) {
        CollisionJNI.btBroadphaseInterface_destroyProxy(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void setAabb(btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4, btDispatcher btdispatcher) {
        CollisionJNI.btBroadphaseInterface_setAabb(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, vector3, vector4, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void getAabb(btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btBroadphaseInterface_getAabb(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, vector3, vector4);
    }

    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.btBroadphaseInterface_rayTest__SWIG_0(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5, vector6);
    }

    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5) {
        CollisionJNI.btBroadphaseInterface_rayTest__SWIG_1(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5);
    }

    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback) {
        CollisionJNI.btBroadphaseInterface_rayTest__SWIG_2(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback);
    }

    public void aabbTest(Vector3 vector3, Vector3 vector4, btBroadphaseAabbCallback btbroadphaseaabbcallback) {
        CollisionJNI.btBroadphaseInterface_aabbTest(this.swigCPtr, this, vector3, vector4, btBroadphaseAabbCallback.getCPtr(btbroadphaseaabbcallback), btbroadphaseaabbcallback);
    }

    public void calculateOverlappingPairs(btDispatcher btdispatcher) {
        CollisionJNI.btBroadphaseInterface_calculateOverlappingPairs(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public btOverlappingPairCache getOverlappingPairCache() {
        long jBtBroadphaseInterface_getOverlappingPairCache = CollisionJNI.btBroadphaseInterface_getOverlappingPairCache(this.swigCPtr, this);
        if (jBtBroadphaseInterface_getOverlappingPairCache == 0) {
            return null;
        }
        return new btOverlappingPairCache(jBtBroadphaseInterface_getOverlappingPairCache, false);
    }

    public btOverlappingPairCache getOverlappingPairCacheConst() {
        long jBtBroadphaseInterface_getOverlappingPairCacheConst = CollisionJNI.btBroadphaseInterface_getOverlappingPairCacheConst(this.swigCPtr, this);
        if (jBtBroadphaseInterface_getOverlappingPairCacheConst == 0) {
            return null;
        }
        return new btOverlappingPairCache(jBtBroadphaseInterface_getOverlappingPairCacheConst, false);
    }

    public void getBroadphaseAabb(Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btBroadphaseInterface_getBroadphaseAabb(this.swigCPtr, this, vector3, vector4);
    }

    public void resetPool(btDispatcher btdispatcher) {
        CollisionJNI.btBroadphaseInterface_resetPool(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void printStats() {
        CollisionJNI.btBroadphaseInterface_printStats(this.swigCPtr, this);
    }
}
