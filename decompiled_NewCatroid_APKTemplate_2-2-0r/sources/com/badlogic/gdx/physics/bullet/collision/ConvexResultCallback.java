package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class ConvexResultCallback extends BulletBase {
    private long swigCPtr;

    protected ConvexResultCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public ConvexResultCallback(long j, boolean z) {
        this("ConvexResultCallback", j, z);
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

    public static long getCPtr(ConvexResultCallback convexResultCallback) {
        if (convexResultCallback == null) {
            return 0L;
        }
        return convexResultCallback.swigCPtr;
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
                CollisionJNI.delete_ConvexResultCallback(this.swigCPtr);
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
        CollisionJNI.ConvexResultCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.ConvexResultCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void setClosestHitFraction(float f) {
        CollisionJNI.ConvexResultCallback_closestHitFraction_set(this.swigCPtr, this, f);
    }

    public float getClosestHitFraction() {
        return CollisionJNI.ConvexResultCallback_closestHitFraction_get(this.swigCPtr, this);
    }

    public void setCollisionFilterGroup(int i) {
        CollisionJNI.ConvexResultCallback_collisionFilterGroup_set(this.swigCPtr, this, i);
    }

    public int getCollisionFilterGroup() {
        return CollisionJNI.ConvexResultCallback_collisionFilterGroup_get(this.swigCPtr, this);
    }

    public void setCollisionFilterMask(int i) {
        CollisionJNI.ConvexResultCallback_collisionFilterMask_set(this.swigCPtr, this, i);
    }

    public int getCollisionFilterMask() {
        return CollisionJNI.ConvexResultCallback_collisionFilterMask_get(this.swigCPtr, this);
    }

    public ConvexResultCallback() {
        this(CollisionJNI.new_ConvexResultCallback(), true);
        CollisionJNI.ConvexResultCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public boolean hasHit() {
        return CollisionJNI.ConvexResultCallback_hasHit(this.swigCPtr, this);
    }

    public boolean needsCollision(btBroadphaseProxy btbroadphaseproxy) {
        if (getClass() == ConvexResultCallback.class) {
            return CollisionJNI.ConvexResultCallback_needsCollision(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
        }
        return CollisionJNI.ConvexResultCallback_needsCollisionSwigExplicitConvexResultCallback(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    public float addSingleResult(LocalConvexResult localConvexResult, boolean z) {
        return CollisionJNI.ConvexResultCallback_addSingleResult(this.swigCPtr, this, LocalConvexResult.getCPtr(localConvexResult), localConvexResult, z);
    }
}
