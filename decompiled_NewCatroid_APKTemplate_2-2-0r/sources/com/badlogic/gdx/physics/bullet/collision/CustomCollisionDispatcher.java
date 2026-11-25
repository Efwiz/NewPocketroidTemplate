package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class CustomCollisionDispatcher extends btCollisionDispatcher {
    private long swigCPtr;

    protected CustomCollisionDispatcher(String str, long j, boolean z) {
        super(str, CollisionJNI.CustomCollisionDispatcher_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public CustomCollisionDispatcher(long j, boolean z) {
        this("CustomCollisionDispatcher", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher, com.badlogic.gdx.physics.bullet.collision.btDispatcher, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.CustomCollisionDispatcher_SWIGUpcast(j), z);
    }

    public static long getCPtr(CustomCollisionDispatcher customCollisionDispatcher) {
        if (customCollisionDispatcher == null) {
            return 0L;
        }
        return customCollisionDispatcher.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher, com.badlogic.gdx.physics.bullet.collision.btDispatcher, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher, com.badlogic.gdx.physics.bullet.collision.btDispatcher, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_CustomCollisionDispatcher(this.swigCPtr);
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
        CollisionJNI.CustomCollisionDispatcher_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.CustomCollisionDispatcher_change_ownership(this, this.swigCPtr, true);
    }

    public CustomCollisionDispatcher(btCollisionConfiguration btcollisionconfiguration) {
        this(CollisionJNI.new_CustomCollisionDispatcher(btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
        CollisionJNI.CustomCollisionDispatcher_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDispatcher
    public boolean needsCollision(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        if (getClass() == CustomCollisionDispatcher.class) {
            return CollisionJNI.CustomCollisionDispatcher_needsCollision(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
        }
        return CollisionJNI.CustomCollisionDispatcher_needsCollisionSwigExplicitCustomCollisionDispatcher(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDispatcher
    public boolean needsResponse(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        if (getClass() == CustomCollisionDispatcher.class) {
            return CollisionJNI.CustomCollisionDispatcher_needsResponse(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
        }
        return CollisionJNI.CustomCollisionDispatcher_needsResponseSwigExplicitCustomCollisionDispatcher(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }
}
