package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btGhostPairCallback extends btOverlappingPairCallback {
    private long swigCPtr;

    protected btGhostPairCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.btGhostPairCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGhostPairCallback(long j, boolean z) {
        this("btGhostPairCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btGhostPairCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGhostPairCallback btghostpaircallback) {
        if (btghostpaircallback == null) {
            return 0L;
        }
        return btghostpaircallback.swigCPtr;
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
                CollisionJNI.delete_btGhostPairCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        CollisionJNI.btGhostPairCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btGhostPairCallback_change_ownership(this, this.swigCPtr, true);
    }

    public btGhostPairCallback() {
        this(CollisionJNI.new_btGhostPairCallback(), true);
        CollisionJNI.btGhostPairCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback
    public long removeOverlappingPair(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2, btDispatcher btdispatcher) {
        if (getClass() == btGhostPairCallback.class) {
            return CollisionJNI.btGhostPairCallback_removeOverlappingPair(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2, btDispatcher.getCPtr(btdispatcher), btdispatcher);
        }
        return CollisionJNI.btGhostPairCallback_removeOverlappingPairSwigExplicitbtGhostPairCallback(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback
    public void removeOverlappingPairsContainingProxy(btBroadphaseProxy btbroadphaseproxy, btDispatcher btdispatcher) {
        if (getClass() == btGhostPairCallback.class) {
            CollisionJNI.btGhostPairCallback_removeOverlappingPairsContainingProxy(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btDispatcher.getCPtr(btdispatcher), btdispatcher);
        } else {
            CollisionJNI.btGhostPairCallback_removeOverlappingPairsContainingProxySwigExplicitbtGhostPairCallback(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btDispatcher.getCPtr(btdispatcher), btdispatcher);
        }
    }
}
