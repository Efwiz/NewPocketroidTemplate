package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleInfoMap extends btHashMapInternalShortBtHashIntBtTriangleInfo {
    private long swigCPtr;

    protected btTriangleInfoMap(String str, long j, boolean z) {
        super(str, CollisionJNI.btTriangleInfoMap_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTriangleInfoMap(long j, boolean z) {
        this("btTriangleInfoMap", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btHashMapInternalShortBtHashIntBtTriangleInfo, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTriangleInfoMap_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTriangleInfoMap bttriangleinfomap) {
        if (bttriangleinfomap == null) {
            return 0L;
        }
        return bttriangleinfomap.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btHashMapInternalShortBtHashIntBtTriangleInfo, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btHashMapInternalShortBtHashIntBtTriangleInfo, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btTriangleInfoMap(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setConvexEpsilon(float f) {
        CollisionJNI.btTriangleInfoMap_convexEpsilon_set(this.swigCPtr, this, f);
    }

    public float getConvexEpsilon() {
        return CollisionJNI.btTriangleInfoMap_convexEpsilon_get(this.swigCPtr, this);
    }

    public void setPlanarEpsilon(float f) {
        CollisionJNI.btTriangleInfoMap_planarEpsilon_set(this.swigCPtr, this, f);
    }

    public float getPlanarEpsilon() {
        return CollisionJNI.btTriangleInfoMap_planarEpsilon_get(this.swigCPtr, this);
    }

    public void setEqualVertexThreshold(float f) {
        CollisionJNI.btTriangleInfoMap_equalVertexThreshold_set(this.swigCPtr, this, f);
    }

    public float getEqualVertexThreshold() {
        return CollisionJNI.btTriangleInfoMap_equalVertexThreshold_get(this.swigCPtr, this);
    }

    public void setEdgeDistanceThreshold(float f) {
        CollisionJNI.btTriangleInfoMap_edgeDistanceThreshold_set(this.swigCPtr, this, f);
    }

    public float getEdgeDistanceThreshold() {
        return CollisionJNI.btTriangleInfoMap_edgeDistanceThreshold_get(this.swigCPtr, this);
    }

    public void setMaxEdgeAngleThreshold(float f) {
        CollisionJNI.btTriangleInfoMap_maxEdgeAngleThreshold_set(this.swigCPtr, this, f);
    }

    public float getMaxEdgeAngleThreshold() {
        return CollisionJNI.btTriangleInfoMap_maxEdgeAngleThreshold_get(this.swigCPtr, this);
    }

    public void setZeroAreaThreshold(float f) {
        CollisionJNI.btTriangleInfoMap_zeroAreaThreshold_set(this.swigCPtr, this, f);
    }

    public float getZeroAreaThreshold() {
        return CollisionJNI.btTriangleInfoMap_zeroAreaThreshold_get(this.swigCPtr, this);
    }

    public btTriangleInfoMap() {
        this(CollisionJNI.new_btTriangleInfoMap(), true);
    }

    public int calculateSerializeBufferSize() {
        return CollisionJNI.btTriangleInfoMap_calculateSerializeBufferSize(this.swigCPtr, this);
    }

    public String serialize(long j, btSerializer btserializer) {
        return CollisionJNI.btTriangleInfoMap_serialize(this.swigCPtr, this, j, btSerializer.getCPtr(btserializer), btserializer);
    }

    public void deSerialize(btTriangleInfoMapData bttriangleinfomapdata) {
        CollisionJNI.btTriangleInfoMap_deSerialize(this.swigCPtr, this, btTriangleInfoMapData.getCPtr(bttriangleinfomapdata), bttriangleinfomapdata);
    }
}
