package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btMprDistanceInfo extends BulletBase {
    private long swigCPtr;

    protected btMprDistanceInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMprDistanceInfo(long j, boolean z) {
        this("btMprDistanceInfo", j, z);
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

    public static long getCPtr(btMprDistanceInfo btmprdistanceinfo) {
        if (btmprdistanceinfo == null) {
            return 0L;
        }
        return btmprdistanceinfo.swigCPtr;
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
                CollisionJNI.delete_btMprDistanceInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPointOnA(btVector3 btvector3) {
        CollisionJNI.btMprDistanceInfo_pointOnA_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getPointOnA() {
        long jBtMprDistanceInfo_pointOnA_get = CollisionJNI.btMprDistanceInfo_pointOnA_get(this.swigCPtr, this);
        if (jBtMprDistanceInfo_pointOnA_get == 0) {
            return null;
        }
        return new btVector3(jBtMprDistanceInfo_pointOnA_get, false);
    }

    public void setPointOnB(btVector3 btvector3) {
        CollisionJNI.btMprDistanceInfo_pointOnB_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getPointOnB() {
        long jBtMprDistanceInfo_pointOnB_get = CollisionJNI.btMprDistanceInfo_pointOnB_get(this.swigCPtr, this);
        if (jBtMprDistanceInfo_pointOnB_get == 0) {
            return null;
        }
        return new btVector3(jBtMprDistanceInfo_pointOnB_get, false);
    }

    public void setNormalBtoA(btVector3 btvector3) {
        CollisionJNI.btMprDistanceInfo_normalBtoA_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getNormalBtoA() {
        long jBtMprDistanceInfo_normalBtoA_get = CollisionJNI.btMprDistanceInfo_normalBtoA_get(this.swigCPtr, this);
        if (jBtMprDistanceInfo_normalBtoA_get == 0) {
            return null;
        }
        return new btVector3(jBtMprDistanceInfo_normalBtoA_get, false);
    }

    public void setDistance(float f) {
        CollisionJNI.btMprDistanceInfo_distance_set(this.swigCPtr, this, f);
    }

    public float getDistance() {
        return CollisionJNI.btMprDistanceInfo_distance_get(this.swigCPtr, this);
    }

    public btMprDistanceInfo() {
        this(CollisionJNI.new_btMprDistanceInfo(), true);
    }
}
