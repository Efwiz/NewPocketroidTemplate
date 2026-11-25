package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class RayResultCallback extends BulletBase {
    private long swigCPtr;

    protected RayResultCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public RayResultCallback(long j, boolean z) {
        this("RayResultCallback", j, z);
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

    public static long getCPtr(RayResultCallback rayResultCallback) {
        if (rayResultCallback == null) {
            return 0L;
        }
        return rayResultCallback.swigCPtr;
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
                CollisionJNI.delete_RayResultCallback(this.swigCPtr);
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
        CollisionJNI.RayResultCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.RayResultCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void setClosestHitFraction(float f) {
        CollisionJNI.RayResultCallback_closestHitFraction_set(this.swigCPtr, this, f);
    }

    public float getClosestHitFraction() {
        return CollisionJNI.RayResultCallback_closestHitFraction_get(this.swigCPtr, this);
    }

    public void setCollisionObject(btCollisionObject btcollisionobject) {
        CollisionJNI.RayResultCallback_collisionObject_set(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public btCollisionObject getCollisionObject() {
        return btCollisionObject.getInstance(CollisionJNI.RayResultCallback_collisionObject_get(this.swigCPtr, this), false);
    }

    public void setCollisionFilterGroup(int i) {
        CollisionJNI.RayResultCallback_collisionFilterGroup_set(this.swigCPtr, this, i);
    }

    public int getCollisionFilterGroup() {
        return CollisionJNI.RayResultCallback_collisionFilterGroup_get(this.swigCPtr, this);
    }

    public void setCollisionFilterMask(int i) {
        CollisionJNI.RayResultCallback_collisionFilterMask_set(this.swigCPtr, this, i);
    }

    public int getCollisionFilterMask() {
        return CollisionJNI.RayResultCallback_collisionFilterMask_get(this.swigCPtr, this);
    }

    public void setFlags(long j) {
        CollisionJNI.RayResultCallback_flags_set(this.swigCPtr, this, j);
    }

    public long getFlags() {
        return CollisionJNI.RayResultCallback_flags_get(this.swigCPtr, this);
    }

    public boolean hasHit() {
        return CollisionJNI.RayResultCallback_hasHit(this.swigCPtr, this);
    }

    public RayResultCallback() {
        this(CollisionJNI.new_RayResultCallback(), true);
        CollisionJNI.RayResultCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public boolean needsCollision(btBroadphaseProxy btbroadphaseproxy) {
        if (getClass() == RayResultCallback.class) {
            return CollisionJNI.RayResultCallback_needsCollision(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
        }
        return CollisionJNI.RayResultCallback_needsCollisionSwigExplicitRayResultCallback(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    public float addSingleResult(LocalRayResult localRayResult, boolean z) {
        return CollisionJNI.RayResultCallback_addSingleResult(this.swigCPtr, this, LocalRayResult.getCPtr(localRayResult), localRayResult, z);
    }
}
