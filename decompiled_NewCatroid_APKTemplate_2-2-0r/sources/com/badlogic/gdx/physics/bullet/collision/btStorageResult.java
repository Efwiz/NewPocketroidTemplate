package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btStorageResult extends btDiscreteCollisionDetectorInterface.Result {
    private long swigCPtr;

    protected btStorageResult(String str, long j, boolean z) {
        super(str, CollisionJNI.btStorageResult_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btStorageResult(long j, boolean z) {
        this("btStorageResult", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface.Result, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btStorageResult_SWIGUpcast(j), z);
    }

    public static long getCPtr(btStorageResult btstorageresult) {
        if (btstorageresult == null) {
            return 0L;
        }
        return btstorageresult.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface.Result, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface.Result, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btStorageResult(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setNormalOnSurfaceB(btVector3 btvector3) {
        CollisionJNI.btStorageResult_normalOnSurfaceB_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getNormalOnSurfaceB() {
        long jBtStorageResult_normalOnSurfaceB_get = CollisionJNI.btStorageResult_normalOnSurfaceB_get(this.swigCPtr, this);
        if (jBtStorageResult_normalOnSurfaceB_get == 0) {
            return null;
        }
        return new btVector3(jBtStorageResult_normalOnSurfaceB_get, false);
    }

    public void setClosestPointInB(btVector3 btvector3) {
        CollisionJNI.btStorageResult_closestPointInB_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getClosestPointInB() {
        long jBtStorageResult_closestPointInB_get = CollisionJNI.btStorageResult_closestPointInB_get(this.swigCPtr, this);
        if (jBtStorageResult_closestPointInB_get == 0) {
            return null;
        }
        return new btVector3(jBtStorageResult_closestPointInB_get, false);
    }

    public void setDistance(float f) {
        CollisionJNI.btStorageResult_distance_set(this.swigCPtr, this, f);
    }

    public float getDistance() {
        return CollisionJNI.btStorageResult_distance_get(this.swigCPtr, this);
    }
}
