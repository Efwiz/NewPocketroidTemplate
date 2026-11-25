package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class ContactResultCallback extends BulletBase {
    private long swigCPtr;

    protected ContactResultCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public ContactResultCallback(long j, boolean z) {
        this("ContactResultCallback", j, z);
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

    public static long getCPtr(ContactResultCallback contactResultCallback) {
        if (contactResultCallback == null) {
            return 0L;
        }
        return contactResultCallback.swigCPtr;
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
                CollisionJNI.delete_ContactResultCallback(this.swigCPtr);
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
        CollisionJNI.ContactResultCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.ContactResultCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void setCollisionFilterGroup(int i) {
        CollisionJNI.ContactResultCallback_collisionFilterGroup_set(this.swigCPtr, this, i);
    }

    public int getCollisionFilterGroup() {
        return CollisionJNI.ContactResultCallback_collisionFilterGroup_get(this.swigCPtr, this);
    }

    public void setCollisionFilterMask(int i) {
        CollisionJNI.ContactResultCallback_collisionFilterMask_set(this.swigCPtr, this, i);
    }

    public int getCollisionFilterMask() {
        return CollisionJNI.ContactResultCallback_collisionFilterMask_get(this.swigCPtr, this);
    }

    public void setClosestDistanceThreshold(float f) {
        CollisionJNI.ContactResultCallback_closestDistanceThreshold_set(this.swigCPtr, this, f);
    }

    public float getClosestDistanceThreshold() {
        return CollisionJNI.ContactResultCallback_closestDistanceThreshold_get(this.swigCPtr, this);
    }

    public ContactResultCallback() {
        this(CollisionJNI.new_ContactResultCallback(), true);
        CollisionJNI.ContactResultCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public boolean needsCollision(btBroadphaseProxy btbroadphaseproxy) {
        if (getClass() == ContactResultCallback.class) {
            return CollisionJNI.ContactResultCallback_needsCollision(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
        }
        return CollisionJNI.ContactResultCallback_needsCollisionSwigExplicitContactResultCallback(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    public float addSingleResult(btManifoldPoint btmanifoldpoint, btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4) {
        return CollisionJNI.ContactResultCallback_addSingleResult(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, i, i2, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, i3, i4);
    }
}
