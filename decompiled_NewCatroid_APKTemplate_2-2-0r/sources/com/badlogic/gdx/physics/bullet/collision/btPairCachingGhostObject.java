package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btPairCachingGhostObject extends btGhostObject {
    private long swigCPtr;

    protected btPairCachingGhostObject(String str, long j, boolean z) {
        super(str, CollisionJNI.btPairCachingGhostObject_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btPairCachingGhostObject(long j, boolean z) {
        this("btPairCachingGhostObject", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGhostObject, com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btPairCachingGhostObject_SWIGUpcast(j), z);
    }

    public static long getCPtr(btPairCachingGhostObject btpaircachingghostobject) {
        if (btpaircachingghostobject == null) {
            return 0L;
        }
        return btpaircachingghostobject.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGhostObject, com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGhostObject, com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btPairCachingGhostObject(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btPairCachingGhostObject() {
        this(CollisionJNI.new_btPairCachingGhostObject(), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGhostObject
    public void addOverlappingObjectInternal(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        CollisionJNI.btPairCachingGhostObject_addOverlappingObjectInternal__SWIG_0(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGhostObject
    public void addOverlappingObjectInternal(btBroadphaseProxy btbroadphaseproxy) {
        CollisionJNI.btPairCachingGhostObject_addOverlappingObjectInternal__SWIG_1(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGhostObject
    public void removeOverlappingObjectInternal(btBroadphaseProxy btbroadphaseproxy, btDispatcher btdispatcher, btBroadphaseProxy btbroadphaseproxy2) {
        CollisionJNI.btPairCachingGhostObject_removeOverlappingObjectInternal__SWIG_0(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGhostObject
    public void removeOverlappingObjectInternal(btBroadphaseProxy btbroadphaseproxy, btDispatcher btdispatcher) {
        CollisionJNI.btPairCachingGhostObject_removeOverlappingObjectInternal__SWIG_1(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public btHashedOverlappingPairCache getOverlappingPairCache() {
        long jBtPairCachingGhostObject_getOverlappingPairCache = CollisionJNI.btPairCachingGhostObject_getOverlappingPairCache(this.swigCPtr, this);
        if (jBtPairCachingGhostObject_getOverlappingPairCache == 0) {
            return null;
        }
        return new btHashedOverlappingPairCache(jBtPairCachingGhostObject_getOverlappingPairCache, false);
    }
}
