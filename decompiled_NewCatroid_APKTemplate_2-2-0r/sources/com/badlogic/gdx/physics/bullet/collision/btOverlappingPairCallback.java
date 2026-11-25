package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btOverlappingPairCallback extends BulletBase {
    private long swigCPtr;

    protected btOverlappingPairCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btOverlappingPairCallback(long j, boolean z) {
        this("btOverlappingPairCallback", j, z);
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

    public static long getCPtr(btOverlappingPairCallback btoverlappingpaircallback) {
        if (btoverlappingpaircallback == null) {
            return 0L;
        }
        return btoverlappingpaircallback.swigCPtr;
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
                CollisionJNI.delete_btOverlappingPairCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btBroadphasePair addOverlappingPair(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        return btBroadphasePair.internalTemp(CollisionJNI.btOverlappingPairCallback_addOverlappingPair(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2), false);
    }

    public long removeOverlappingPair(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2, btDispatcher btdispatcher) {
        return CollisionJNI.btOverlappingPairCallback_removeOverlappingPair(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void removeOverlappingPairsContainingProxy(btBroadphaseProxy btbroadphaseproxy, btDispatcher btdispatcher) {
        CollisionJNI.btOverlappingPairCallback_removeOverlappingPairsContainingProxy(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }
}
