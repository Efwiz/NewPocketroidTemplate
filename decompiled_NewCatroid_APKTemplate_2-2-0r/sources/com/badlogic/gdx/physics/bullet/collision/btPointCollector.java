package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btPointCollector extends btDiscreteCollisionDetectorInterface.Result {
    private long swigCPtr;

    protected btPointCollector(String str, long j, boolean z) {
        super(str, CollisionJNI.btPointCollector_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btPointCollector(long j, boolean z) {
        this("btPointCollector", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface.Result, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btPointCollector_SWIGUpcast(j), z);
    }

    public static long getCPtr(btPointCollector btpointcollector) {
        if (btpointcollector == null) {
            return 0L;
        }
        return btpointcollector.swigCPtr;
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
                CollisionJNI.delete_btPointCollector(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setNormalOnBInWorld(btVector3 btvector3) {
        CollisionJNI.btPointCollector_normalOnBInWorld_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getNormalOnBInWorld() {
        long jBtPointCollector_normalOnBInWorld_get = CollisionJNI.btPointCollector_normalOnBInWorld_get(this.swigCPtr, this);
        if (jBtPointCollector_normalOnBInWorld_get == 0) {
            return null;
        }
        return new btVector3(jBtPointCollector_normalOnBInWorld_get, false);
    }

    public void setPointInWorld(btVector3 btvector3) {
        CollisionJNI.btPointCollector_pointInWorld_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getPointInWorld() {
        long jBtPointCollector_pointInWorld_get = CollisionJNI.btPointCollector_pointInWorld_get(this.swigCPtr, this);
        if (jBtPointCollector_pointInWorld_get == 0) {
            return null;
        }
        return new btVector3(jBtPointCollector_pointInWorld_get, false);
    }

    public void setDistance(float f) {
        CollisionJNI.btPointCollector_distance_set(this.swigCPtr, this, f);
    }

    public float getDistance() {
        return CollisionJNI.btPointCollector_distance_get(this.swigCPtr, this);
    }

    public void setHasResult(boolean z) {
        CollisionJNI.btPointCollector_hasResult_set(this.swigCPtr, this, z);
    }

    public boolean getHasResult() {
        return CollisionJNI.btPointCollector_hasResult_get(this.swigCPtr, this);
    }

    public btPointCollector() {
        this(CollisionJNI.new_btPointCollector(), true);
    }
}
