package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleInfoMapData extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btTriangleInfoMapData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTriangleInfoMapData(long j, boolean z) {
        this("btTriangleInfoMapData", j, z);
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

    public static long getCPtr(btTriangleInfoMapData bttriangleinfomapdata) {
        if (bttriangleinfomapdata == null) {
            return 0L;
        }
        return bttriangleinfomapdata.swigCPtr;
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
                CollisionJNI.delete_btTriangleInfoMapData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setHashTablePtr(IntBuffer intBuffer) {
        CollisionJNI.btTriangleInfoMapData_hashTablePtr_set(this.swigCPtr, this, intBuffer);
    }

    public IntBuffer getHashTablePtr() {
        return CollisionJNI.btTriangleInfoMapData_hashTablePtr_get(this.swigCPtr, this);
    }

    public void setNextPtr(IntBuffer intBuffer) {
        CollisionJNI.btTriangleInfoMapData_nextPtr_set(this.swigCPtr, this, intBuffer);
    }

    public IntBuffer getNextPtr() {
        return CollisionJNI.btTriangleInfoMapData_nextPtr_get(this.swigCPtr, this);
    }

    public void setValueArrayPtr(btTriangleInfoData bttriangleinfodata) {
        CollisionJNI.btTriangleInfoMapData_valueArrayPtr_set(this.swigCPtr, this, btTriangleInfoData.getCPtr(bttriangleinfodata), bttriangleinfodata);
    }

    public btTriangleInfoData getValueArrayPtr() {
        long jBtTriangleInfoMapData_valueArrayPtr_get = CollisionJNI.btTriangleInfoMapData_valueArrayPtr_get(this.swigCPtr, this);
        if (jBtTriangleInfoMapData_valueArrayPtr_get == 0) {
            return null;
        }
        return new btTriangleInfoData(jBtTriangleInfoMapData_valueArrayPtr_get, false);
    }

    public void setKeyArrayPtr(IntBuffer intBuffer) {
        CollisionJNI.btTriangleInfoMapData_keyArrayPtr_set(this.swigCPtr, this, intBuffer);
    }

    public IntBuffer getKeyArrayPtr() {
        return CollisionJNI.btTriangleInfoMapData_keyArrayPtr_get(this.swigCPtr, this);
    }

    public void setConvexEpsilon(float f) {
        CollisionJNI.btTriangleInfoMapData_convexEpsilon_set(this.swigCPtr, this, f);
    }

    public float getConvexEpsilon() {
        return CollisionJNI.btTriangleInfoMapData_convexEpsilon_get(this.swigCPtr, this);
    }

    public void setPlanarEpsilon(float f) {
        CollisionJNI.btTriangleInfoMapData_planarEpsilon_set(this.swigCPtr, this, f);
    }

    public float getPlanarEpsilon() {
        return CollisionJNI.btTriangleInfoMapData_planarEpsilon_get(this.swigCPtr, this);
    }

    public void setEqualVertexThreshold(float f) {
        CollisionJNI.btTriangleInfoMapData_equalVertexThreshold_set(this.swigCPtr, this, f);
    }

    public float getEqualVertexThreshold() {
        return CollisionJNI.btTriangleInfoMapData_equalVertexThreshold_get(this.swigCPtr, this);
    }

    public void setEdgeDistanceThreshold(float f) {
        CollisionJNI.btTriangleInfoMapData_edgeDistanceThreshold_set(this.swigCPtr, this, f);
    }

    public float getEdgeDistanceThreshold() {
        return CollisionJNI.btTriangleInfoMapData_edgeDistanceThreshold_get(this.swigCPtr, this);
    }

    public void setZeroAreaThreshold(float f) {
        CollisionJNI.btTriangleInfoMapData_zeroAreaThreshold_set(this.swigCPtr, this, f);
    }

    public float getZeroAreaThreshold() {
        return CollisionJNI.btTriangleInfoMapData_zeroAreaThreshold_get(this.swigCPtr, this);
    }

    public void setNextSize(int i) {
        CollisionJNI.btTriangleInfoMapData_nextSize_set(this.swigCPtr, this, i);
    }

    public int getNextSize() {
        return CollisionJNI.btTriangleInfoMapData_nextSize_get(this.swigCPtr, this);
    }

    public void setHashTableSize(int i) {
        CollisionJNI.btTriangleInfoMapData_hashTableSize_set(this.swigCPtr, this, i);
    }

    public int getHashTableSize() {
        return CollisionJNI.btTriangleInfoMapData_hashTableSize_get(this.swigCPtr, this);
    }

    public void setNumValues(int i) {
        CollisionJNI.btTriangleInfoMapData_numValues_set(this.swigCPtr, this, i);
    }

    public int getNumValues() {
        return CollisionJNI.btTriangleInfoMapData_numValues_get(this.swigCPtr, this);
    }

    public void setNumKeys(int i) {
        CollisionJNI.btTriangleInfoMapData_numKeys_set(this.swigCPtr, this, i);
    }

    public int getNumKeys() {
        return CollisionJNI.btTriangleInfoMapData_numKeys_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btTriangleInfoMapData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btTriangleInfoMapData_padding_get(this.swigCPtr, this);
    }

    public btTriangleInfoMapData() {
        this(CollisionJNI.new_btTriangleInfoMapData(), true);
    }
}
