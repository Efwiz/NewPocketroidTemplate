package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btScalarArray;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3Array;

/* JADX INFO: loaded from: classes8.dex */
public class AllHitsRayResultCallback extends RayResultCallback {
    private long swigCPtr;

    protected AllHitsRayResultCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.AllHitsRayResultCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public AllHitsRayResultCallback(long j, boolean z) {
        this("AllHitsRayResultCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.AllHitsRayResultCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(AllHitsRayResultCallback allHitsRayResultCallback) {
        if (allHitsRayResultCallback == null) {
            return 0L;
        }
        return allHitsRayResultCallback.swigCPtr;
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
                CollisionJNI.delete_AllHitsRayResultCallback(this.swigCPtr);
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
        CollisionJNI.AllHitsRayResultCallback_change_ownership(this, this.swigCPtr, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback
    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.AllHitsRayResultCallback_change_ownership(this, this.swigCPtr, true);
    }

    public AllHitsRayResultCallback(Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_AllHitsRayResultCallback(vector3, vector4), true);
        CollisionJNI.AllHitsRayResultCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public void setCollisionObjects(btCollisionObjectConstArray btcollisionobjectconstarray) {
        CollisionJNI.AllHitsRayResultCallback_collisionObjects_set(this.swigCPtr, this, btCollisionObjectConstArray.getCPtr(btcollisionobjectconstarray), btcollisionobjectconstarray);
    }

    public btCollisionObjectConstArray getCollisionObjects() {
        long jAllHitsRayResultCallback_collisionObjects_get = CollisionJNI.AllHitsRayResultCallback_collisionObjects_get(this.swigCPtr, this);
        if (jAllHitsRayResultCallback_collisionObjects_get == 0) {
            return null;
        }
        return new btCollisionObjectConstArray(jAllHitsRayResultCallback_collisionObjects_get, false);
    }

    public void setHitNormalWorld(btVector3Array btvector3array) {
        CollisionJNI.AllHitsRayResultCallback_hitNormalWorld_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public btVector3Array getHitNormalWorld() {
        long jAllHitsRayResultCallback_hitNormalWorld_get = CollisionJNI.AllHitsRayResultCallback_hitNormalWorld_get(this.swigCPtr, this);
        if (jAllHitsRayResultCallback_hitNormalWorld_get == 0) {
            return null;
        }
        return new btVector3Array(jAllHitsRayResultCallback_hitNormalWorld_get, false);
    }

    public void setHitPointWorld(btVector3Array btvector3array) {
        CollisionJNI.AllHitsRayResultCallback_hitPointWorld_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public btVector3Array getHitPointWorld() {
        long jAllHitsRayResultCallback_hitPointWorld_get = CollisionJNI.AllHitsRayResultCallback_hitPointWorld_get(this.swigCPtr, this);
        if (jAllHitsRayResultCallback_hitPointWorld_get == 0) {
            return null;
        }
        return new btVector3Array(jAllHitsRayResultCallback_hitPointWorld_get, false);
    }

    public void setHitFractions(btScalarArray btscalararray) {
        CollisionJNI.AllHitsRayResultCallback_hitFractions_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getHitFractions() {
        long jAllHitsRayResultCallback_hitFractions_get = CollisionJNI.AllHitsRayResultCallback_hitFractions_get(this.swigCPtr, this);
        if (jAllHitsRayResultCallback_hitFractions_get == 0) {
            return null;
        }
        return new btScalarArray(jAllHitsRayResultCallback_hitFractions_get, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.RayResultCallback
    public float addSingleResult(LocalRayResult localRayResult, boolean z) {
        if (getClass() == AllHitsRayResultCallback.class) {
            return CollisionJNI.AllHitsRayResultCallback_addSingleResult(this.swigCPtr, this, LocalRayResult.getCPtr(localRayResult), localRayResult, z);
        }
        return CollisionJNI.AllHitsRayResultCallback_addSingleResultSwigExplicitAllHitsRayResultCallback(this.swigCPtr, this, LocalRayResult.getCPtr(localRayResult), localRayResult, z);
    }

    public void getRayFromWorld(Vector3 vector3) {
        CollisionJNI.AllHitsRayResultCallback_getRayFromWorld(this.swigCPtr, this, vector3);
    }

    public void setRayFromWorld(Vector3 vector3) {
        CollisionJNI.AllHitsRayResultCallback_setRayFromWorld(this.swigCPtr, this, vector3);
    }

    public void getRayToWorld(Vector3 vector3) {
        CollisionJNI.AllHitsRayResultCallback_getRayToWorld(this.swigCPtr, this, vector3);
    }

    public void setRayToWorld(Vector3 vector3) {
        CollisionJNI.AllHitsRayResultCallback_setRayToWorld(this.swigCPtr, this, vector3);
    }
}
