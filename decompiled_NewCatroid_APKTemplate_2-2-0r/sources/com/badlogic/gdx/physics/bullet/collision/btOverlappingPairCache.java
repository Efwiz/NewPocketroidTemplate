package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btOverlappingPairCache extends btOverlappingPairCallback {
    private long swigCPtr;

    protected btOverlappingPairCache(String str, long j, boolean z) {
        super(str, CollisionJNI.btOverlappingPairCache_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btOverlappingPairCache(long j, boolean z) {
        this("btOverlappingPairCache", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btOverlappingPairCache_SWIGUpcast(j), z);
    }

    public static long getCPtr(btOverlappingPairCache btoverlappingpaircache) {
        if (btoverlappingpaircache == null) {
            return 0L;
        }
        return btoverlappingpaircache.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btOverlappingPairCache(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btBroadphasePair getOverlappingPairArrayPtr() {
        return btBroadphasePair.internalTemp(CollisionJNI.btOverlappingPairCache_getOverlappingPairArrayPtr(this.swigCPtr, this), false);
    }

    public btBroadphasePair getOverlappingPairArrayPtrConst() {
        return btBroadphasePair.internalTemp(CollisionJNI.btOverlappingPairCache_getOverlappingPairArrayPtrConst(this.swigCPtr, this), false);
    }

    public btBroadphasePairArray getOverlappingPairArray() {
        return new btBroadphasePairArray(CollisionJNI.btOverlappingPairCache_getOverlappingPairArray(this.swigCPtr, this), false);
    }

    public void cleanOverlappingPair(btBroadphasePair btbroadphasepair, btDispatcher btdispatcher) {
        CollisionJNI.btOverlappingPairCache_cleanOverlappingPair(this.swigCPtr, this, btbroadphasepair, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public int getNumOverlappingPairs() {
        return CollisionJNI.btOverlappingPairCache_getNumOverlappingPairs(this.swigCPtr, this);
    }

    public void cleanProxyFromPairs(btBroadphaseProxy btbroadphaseproxy, btDispatcher btdispatcher) {
        CollisionJNI.btOverlappingPairCache_cleanProxyFromPairs(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void setOverlapFilterCallback(btOverlapFilterCallback btoverlapfiltercallback) {
        CollisionJNI.btOverlappingPairCache_setOverlapFilterCallback(this.swigCPtr, this, btOverlapFilterCallback.getCPtr(btoverlapfiltercallback), btoverlapfiltercallback);
    }

    public void processAllOverlappingPairs(btOverlapCallback btoverlapcallback, btDispatcher btdispatcher) {
        CollisionJNI.btOverlappingPairCache_processAllOverlappingPairs(this.swigCPtr, this, btOverlapCallback.getCPtr(btoverlapcallback), btoverlapcallback, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public btBroadphasePair findPair(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        return btBroadphasePair.internalTemp(CollisionJNI.btOverlappingPairCache_findPair(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2), false);
    }

    public boolean hasDeferredRemoval() {
        return CollisionJNI.btOverlappingPairCache_hasDeferredRemoval(this.swigCPtr, this);
    }

    public void setInternalGhostPairCallback(btOverlappingPairCallback btoverlappingpaircallback) {
        CollisionJNI.btOverlappingPairCache_setInternalGhostPairCallback(this.swigCPtr, this, btOverlappingPairCallback.getCPtr(btoverlappingpaircallback), btoverlappingpaircallback);
    }

    public void sortOverlappingPairs(btDispatcher btdispatcher) {
        CollisionJNI.btOverlappingPairCache_sortOverlappingPairs(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }
}
