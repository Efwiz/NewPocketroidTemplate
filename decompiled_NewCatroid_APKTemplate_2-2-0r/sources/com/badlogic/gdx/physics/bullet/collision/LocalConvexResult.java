package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class LocalConvexResult extends BulletBase {
    private long swigCPtr;

    protected LocalConvexResult(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public LocalConvexResult(long j, boolean z) {
        this("LocalConvexResult", j, z);
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

    public static long getCPtr(LocalConvexResult localConvexResult) {
        if (localConvexResult == null) {
            return 0L;
        }
        return localConvexResult.swigCPtr;
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
                CollisionJNI.delete_LocalConvexResult(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public LocalConvexResult(btCollisionObject btcollisionobject, LocalShapeInfo localShapeInfo, Vector3 vector3, Vector3 vector4, float f) {
        this(CollisionJNI.new_LocalConvexResult(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, LocalShapeInfo.getCPtr(localShapeInfo), localShapeInfo, vector3, vector4, f), true);
    }

    public void setHitCollisionObject(btCollisionObject btcollisionobject) {
        CollisionJNI.LocalConvexResult_hitCollisionObject_set(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public btCollisionObject getHitCollisionObject() {
        return btCollisionObject.getInstance(CollisionJNI.LocalConvexResult_hitCollisionObject_get(this.swigCPtr, this), false);
    }

    public void setLocalShapeInfo(LocalShapeInfo localShapeInfo) {
        CollisionJNI.LocalConvexResult_localShapeInfo_set(this.swigCPtr, this, LocalShapeInfo.getCPtr(localShapeInfo), localShapeInfo);
    }

    public LocalShapeInfo getLocalShapeInfo() {
        long jLocalConvexResult_localShapeInfo_get = CollisionJNI.LocalConvexResult_localShapeInfo_get(this.swigCPtr, this);
        if (jLocalConvexResult_localShapeInfo_get == 0) {
            return null;
        }
        return new LocalShapeInfo(jLocalConvexResult_localShapeInfo_get, false);
    }

    public void setHitFraction(float f) {
        CollisionJNI.LocalConvexResult_hitFraction_set(this.swigCPtr, this, f);
    }

    public float getHitFraction() {
        return CollisionJNI.LocalConvexResult_hitFraction_get(this.swigCPtr, this);
    }

    public void getHitNormalLocal(Vector3 vector3) {
        CollisionJNI.LocalConvexResult_getHitNormalLocal(this.swigCPtr, this, vector3);
    }

    public void setHitNormalLocal(Vector3 vector3) {
        CollisionJNI.LocalConvexResult_setHitNormalLocal(this.swigCPtr, this, vector3);
    }

    public void getHitPointLocal(Vector3 vector3) {
        CollisionJNI.LocalConvexResult_getHitPointLocal(this.swigCPtr, this, vector3);
    }

    public void setHitPointLocal(Vector3 vector3) {
        CollisionJNI.LocalConvexResult_setHitPointLocal(this.swigCPtr, this, vector3);
    }
}
