package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class ClosestRayResultCallback extends RayResultCallback {
    private long swigCPtr;

    protected ClosestRayResultCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.ClosestRayResultCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public ClosestRayResultCallback(long j, boolean z) {
        this("ClosestRayResultCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.ClosestRayResultCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(ClosestRayResultCallback closestRayResultCallback) {
        if (closestRayResultCallback == null) {
            return 0L;
        }
        return closestRayResultCallback.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_ClosestRayResultCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback
    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback
    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        CollisionJNI.ClosestRayResultCallback_change_ownership(this, this.swigCPtr, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback
    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.ClosestRayResultCallback_change_ownership(this, this.swigCPtr, true);
    }

    public ClosestRayResultCallback(Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_ClosestRayResultCallback(vector3, vector4), true);
        CollisionJNI.ClosestRayResultCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback
    public float addSingleResult(LocalRayResult localRayResult, boolean z) {
        if (getClass() == ClosestRayResultCallback.class) {
            return CollisionJNI.ClosestRayResultCallback_addSingleResult(this.swigCPtr, this, LocalRayResult.getCPtr(localRayResult), localRayResult, z);
        }
        return CollisionJNI.ClosestRayResultCallback_addSingleResultSwigExplicitClosestRayResultCallback(this.swigCPtr, this, LocalRayResult.getCPtr(localRayResult), localRayResult, z);
    }

    public void getRayFromWorld(Vector3 vector3) {
        CollisionJNI.ClosestRayResultCallback_getRayFromWorld(this.swigCPtr, this, vector3);
    }

    public void setRayFromWorld(Vector3 vector3) {
        CollisionJNI.ClosestRayResultCallback_setRayFromWorld(this.swigCPtr, this, vector3);
    }

    public void getRayToWorld(Vector3 vector3) {
        CollisionJNI.ClosestRayResultCallback_getRayToWorld(this.swigCPtr, this, vector3);
    }

    public void setRayToWorld(Vector3 vector3) {
        CollisionJNI.ClosestRayResultCallback_setRayToWorld(this.swigCPtr, this, vector3);
    }

    public void getHitNormalWorld(Vector3 vector3) {
        CollisionJNI.ClosestRayResultCallback_getHitNormalWorld(this.swigCPtr, this, vector3);
    }

    public void setHitNormalWorld(Vector3 vector3) {
        CollisionJNI.ClosestRayResultCallback_setHitNormalWorld(this.swigCPtr, this, vector3);
    }

    public void getHitPointWorld(Vector3 vector3) {
        CollisionJNI.ClosestRayResultCallback_getHitPointWorld(this.swigCPtr, this, vector3);
    }

    public void setHitPointWorld(Vector3 vector3) {
        CollisionJNI.ClosestRayResultCallback_setHitPointWorld(this.swigCPtr, this, vector3);
    }
}
