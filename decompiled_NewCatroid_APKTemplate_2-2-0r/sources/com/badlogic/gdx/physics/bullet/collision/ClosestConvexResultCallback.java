package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class ClosestConvexResultCallback extends ConvexResultCallback {
    private long swigCPtr;

    protected ClosestConvexResultCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.ClosestConvexResultCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public ClosestConvexResultCallback(long j, boolean z) {
        this("ClosestConvexResultCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.ClosestConvexResultCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(ClosestConvexResultCallback closestConvexResultCallback) {
        if (closestConvexResultCallback == null) {
            return 0L;
        }
        return closestConvexResultCallback.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_ClosestConvexResultCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback
    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback
    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        CollisionJNI.ClosestConvexResultCallback_change_ownership(this, this.swigCPtr, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback
    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.ClosestConvexResultCallback_change_ownership(this, this.swigCPtr, true);
    }

    public ClosestConvexResultCallback(Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_ClosestConvexResultCallback(vector3, vector4), true);
        CollisionJNI.ClosestConvexResultCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public void setConvexFromWorld(btVector3 btvector3) {
        CollisionJNI.ClosestConvexResultCallback_convexFromWorld_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getConvexFromWorld() {
        long jClosestConvexResultCallback_convexFromWorld_get = CollisionJNI.ClosestConvexResultCallback_convexFromWorld_get(this.swigCPtr, this);
        if (jClosestConvexResultCallback_convexFromWorld_get == 0) {
            return null;
        }
        return new btVector3(jClosestConvexResultCallback_convexFromWorld_get, false);
    }

    public void setConvexToWorld(btVector3 btvector3) {
        CollisionJNI.ClosestConvexResultCallback_convexToWorld_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getConvexToWorld() {
        long jClosestConvexResultCallback_convexToWorld_get = CollisionJNI.ClosestConvexResultCallback_convexToWorld_get(this.swigCPtr, this);
        if (jClosestConvexResultCallback_convexToWorld_get == 0) {
            return null;
        }
        return new btVector3(jClosestConvexResultCallback_convexToWorld_get, false);
    }

    public void setHitCollisionObject(btCollisionObject btcollisionobject) {
        CollisionJNI.ClosestConvexResultCallback_hitCollisionObject_set(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public btCollisionObject getHitCollisionObject() {
        return btCollisionObject.getInstance(CollisionJNI.ClosestConvexResultCallback_hitCollisionObject_get(this.swigCPtr, this), false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback
    public float addSingleResult(LocalConvexResult localConvexResult, boolean z) {
        if (getClass() == ClosestConvexResultCallback.class) {
            return CollisionJNI.ClosestConvexResultCallback_addSingleResult(this.swigCPtr, this, LocalConvexResult.getCPtr(localConvexResult), localConvexResult, z);
        }
        return CollisionJNI.ClosestConvexResultCallback_addSingleResultSwigExplicitClosestConvexResultCallback(this.swigCPtr, this, LocalConvexResult.getCPtr(localConvexResult), localConvexResult, z);
    }

    public void getConvexFromWorld(Vector3 vector3) {
        CollisionJNI.ClosestConvexResultCallback_getConvexFromWorld(this.swigCPtr, this, vector3);
    }

    public void setRayFromWorld(Vector3 vector3) {
        CollisionJNI.ClosestConvexResultCallback_setRayFromWorld(this.swigCPtr, this, vector3);
    }

    public void getConvexToWorld(Vector3 vector3) {
        CollisionJNI.ClosestConvexResultCallback_getConvexToWorld(this.swigCPtr, this, vector3);
    }

    public void setConvexToWorld(Vector3 vector3) {
        CollisionJNI.ClosestConvexResultCallback_setConvexToWorld(this.swigCPtr, this, vector3);
    }

    public void getHitNormalWorld(Vector3 vector3) {
        CollisionJNI.ClosestConvexResultCallback_getHitNormalWorld(this.swigCPtr, this, vector3);
    }

    public void setHitNormalWorld(Vector3 vector3) {
        CollisionJNI.ClosestConvexResultCallback_setHitNormalWorld(this.swigCPtr, this, vector3);
    }

    public void getHitPointWorld(Vector3 vector3) {
        CollisionJNI.ClosestConvexResultCallback_getHitPointWorld(this.swigCPtr, this, vector3);
    }

    public void setHitPointWorld(Vector3 vector3) {
        CollisionJNI.ClosestConvexResultCallback_setHitPointWorld(this.swigCPtr, this, vector3);
    }
}
