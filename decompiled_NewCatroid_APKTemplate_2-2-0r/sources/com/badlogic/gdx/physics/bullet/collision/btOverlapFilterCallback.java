package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btOverlapFilterCallback extends BulletBase {
    private long swigCPtr;

    protected btOverlapFilterCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btOverlapFilterCallback(long j, boolean z) {
        this("btOverlapFilterCallback", j, z);
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

    public static long getCPtr(btOverlapFilterCallback btoverlapfiltercallback) {
        if (btoverlapfiltercallback == null) {
            return 0L;
        }
        return btoverlapfiltercallback.swigCPtr;
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
                CollisionJNI.delete_btOverlapFilterCallback(this.swigCPtr);
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
        CollisionJNI.btOverlapFilterCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btOverlapFilterCallback_change_ownership(this, this.swigCPtr, true);
    }

    public boolean needBroadphaseCollision(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        return CollisionJNI.btOverlapFilterCallback_needBroadphaseCollision(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }

    public btOverlapFilterCallback() {
        this(CollisionJNI.new_btOverlapFilterCallback(), true);
        CollisionJNI.btOverlapFilterCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
