package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class LocalRayResult extends BulletBase {
    private long swigCPtr;

    protected LocalRayResult(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public LocalRayResult(long j, boolean z) {
        this("LocalRayResult", j, z);
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

    public static long getCPtr(LocalRayResult localRayResult) {
        if (localRayResult == null) {
            return 0L;
        }
        return localRayResult.swigCPtr;
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
                CollisionJNI.delete_LocalRayResult(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public LocalRayResult(btCollisionObject btcollisionobject, LocalShapeInfo localShapeInfo, Vector3 vector3, float f) {
        this(CollisionJNI.new_LocalRayResult(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, LocalShapeInfo.getCPtr(localShapeInfo), localShapeInfo, vector3, f), true);
    }

    public void setCollisionObject(btCollisionObject btcollisionobject) {
        CollisionJNI.LocalRayResult_collisionObject_set(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public btCollisionObject getCollisionObject() {
        return btCollisionObject.getInstance(CollisionJNI.LocalRayResult_collisionObject_get(this.swigCPtr, this), false);
    }

    public void setLocalShapeInfo(LocalShapeInfo localShapeInfo) {
        CollisionJNI.LocalRayResult_localShapeInfo_set(this.swigCPtr, this, LocalShapeInfo.getCPtr(localShapeInfo), localShapeInfo);
    }

    public LocalShapeInfo getLocalShapeInfo() {
        long jLocalRayResult_localShapeInfo_get = CollisionJNI.LocalRayResult_localShapeInfo_get(this.swigCPtr, this);
        if (jLocalRayResult_localShapeInfo_get == 0) {
            return null;
        }
        return new LocalShapeInfo(jLocalRayResult_localShapeInfo_get, false);
    }

    public void setHitNormalLocal(btVector3 btvector3) {
        CollisionJNI.LocalRayResult_hitNormalLocal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getHitNormalLocal() {
        long jLocalRayResult_hitNormalLocal_get = CollisionJNI.LocalRayResult_hitNormalLocal_get(this.swigCPtr, this);
        if (jLocalRayResult_hitNormalLocal_get == 0) {
            return null;
        }
        return new btVector3(jLocalRayResult_hitNormalLocal_get, false);
    }

    public void setHitFraction(float f) {
        CollisionJNI.LocalRayResult_hitFraction_set(this.swigCPtr, this, f);
    }

    public float getHitFraction() {
        return CollisionJNI.LocalRayResult_hitFraction_get(this.swigCPtr, this);
    }
}
