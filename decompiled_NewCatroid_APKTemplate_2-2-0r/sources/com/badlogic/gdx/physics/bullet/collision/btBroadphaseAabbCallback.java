package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btBroadphaseAabbCallback extends BulletBase {
    private long swigCPtr;

    protected btBroadphaseAabbCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBroadphaseAabbCallback(long j, boolean z) {
        this("btBroadphaseAabbCallback", j, z);
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

    public static long getCPtr(btBroadphaseAabbCallback btbroadphaseaabbcallback) {
        if (btbroadphaseaabbcallback == null) {
            return 0L;
        }
        return btbroadphaseaabbcallback.swigCPtr;
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
                CollisionJNI.delete_btBroadphaseAabbCallback(this.swigCPtr);
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
        CollisionJNI.btBroadphaseAabbCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btBroadphaseAabbCallback_change_ownership(this, this.swigCPtr, true);
    }

    public boolean process(btBroadphaseProxy btbroadphaseproxy) {
        return CollisionJNI.btBroadphaseAabbCallback_process(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    public btBroadphaseAabbCallback() {
        this(CollisionJNI.new_btBroadphaseAabbCallback(), true);
        CollisionJNI.btBroadphaseAabbCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
