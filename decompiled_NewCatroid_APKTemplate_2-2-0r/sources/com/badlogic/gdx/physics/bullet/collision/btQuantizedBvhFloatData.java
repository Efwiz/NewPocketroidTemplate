package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btQuantizedBvhFloatData extends BulletBase {
    private long swigCPtr;

    protected btQuantizedBvhFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btQuantizedBvhFloatData(long j, boolean z) {
        this(CollisionConstants.btQuantizedBvhDataName, j, z);
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

    public static long getCPtr(btQuantizedBvhFloatData btquantizedbvhfloatdata) {
        if (btquantizedbvhfloatdata == null) {
            return 0L;
        }
        return btquantizedbvhfloatdata.swigCPtr;
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
                CollisionJNI.delete_btQuantizedBvhFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBvhAabbMin(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btQuantizedBvhFloatData_bvhAabbMin_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getBvhAabbMin() {
        long jBtQuantizedBvhFloatData_bvhAabbMin_get = CollisionJNI.btQuantizedBvhFloatData_bvhAabbMin_get(this.swigCPtr, this);
        if (jBtQuantizedBvhFloatData_bvhAabbMin_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtQuantizedBvhFloatData_bvhAabbMin_get, false);
    }

    public void setBvhAabbMax(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btQuantizedBvhFloatData_bvhAabbMax_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getBvhAabbMax() {
        long jBtQuantizedBvhFloatData_bvhAabbMax_get = CollisionJNI.btQuantizedBvhFloatData_bvhAabbMax_get(this.swigCPtr, this);
        if (jBtQuantizedBvhFloatData_bvhAabbMax_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtQuantizedBvhFloatData_bvhAabbMax_get, false);
    }

    public void setBvhQuantization(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btQuantizedBvhFloatData_bvhQuantization_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getBvhQuantization() {
        long jBtQuantizedBvhFloatData_bvhQuantization_get = CollisionJNI.btQuantizedBvhFloatData_bvhQuantization_get(this.swigCPtr, this);
        if (jBtQuantizedBvhFloatData_bvhQuantization_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtQuantizedBvhFloatData_bvhQuantization_get, false);
    }

    public void setCurNodeIndex(int i) {
        CollisionJNI.btQuantizedBvhFloatData_curNodeIndex_set(this.swigCPtr, this, i);
    }

    public int getCurNodeIndex() {
        return CollisionJNI.btQuantizedBvhFloatData_curNodeIndex_get(this.swigCPtr, this);
    }

    public void setUseQuantization(int i) {
        CollisionJNI.btQuantizedBvhFloatData_useQuantization_set(this.swigCPtr, this, i);
    }

    public int getUseQuantization() {
        return CollisionJNI.btQuantizedBvhFloatData_useQuantization_get(this.swigCPtr, this);
    }

    public void setNumContiguousLeafNodes(int i) {
        CollisionJNI.btQuantizedBvhFloatData_numContiguousLeafNodes_set(this.swigCPtr, this, i);
    }

    public int getNumContiguousLeafNodes() {
        return CollisionJNI.btQuantizedBvhFloatData_numContiguousLeafNodes_get(this.swigCPtr, this);
    }

    public void setNumQuantizedContiguousNodes(int i) {
        CollisionJNI.btQuantizedBvhFloatData_numQuantizedContiguousNodes_set(this.swigCPtr, this, i);
    }

    public int getNumQuantizedContiguousNodes() {
        return CollisionJNI.btQuantizedBvhFloatData_numQuantizedContiguousNodes_get(this.swigCPtr, this);
    }

    public void setContiguousNodesPtr(btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata) {
        CollisionJNI.btQuantizedBvhFloatData_contiguousNodesPtr_set(this.swigCPtr, this, btOptimizedBvhNodeFloatData.getCPtr(btoptimizedbvhnodefloatdata), btoptimizedbvhnodefloatdata);
    }

    public btOptimizedBvhNodeFloatData getContiguousNodesPtr() {
        long jBtQuantizedBvhFloatData_contiguousNodesPtr_get = CollisionJNI.btQuantizedBvhFloatData_contiguousNodesPtr_get(this.swigCPtr, this);
        if (jBtQuantizedBvhFloatData_contiguousNodesPtr_get == 0) {
            return null;
        }
        return new btOptimizedBvhNodeFloatData(jBtQuantizedBvhFloatData_contiguousNodesPtr_get, false);
    }

    public void setQuantizedContiguousNodesPtr(btQuantizedBvhNodeData btquantizedbvhnodedata) {
        CollisionJNI.btQuantizedBvhFloatData_quantizedContiguousNodesPtr_set(this.swigCPtr, this, btQuantizedBvhNodeData.getCPtr(btquantizedbvhnodedata), btquantizedbvhnodedata);
    }

    public btQuantizedBvhNodeData getQuantizedContiguousNodesPtr() {
        long jBtQuantizedBvhFloatData_quantizedContiguousNodesPtr_get = CollisionJNI.btQuantizedBvhFloatData_quantizedContiguousNodesPtr_get(this.swigCPtr, this);
        if (jBtQuantizedBvhFloatData_quantizedContiguousNodesPtr_get == 0) {
            return null;
        }
        return new btQuantizedBvhNodeData(jBtQuantizedBvhFloatData_quantizedContiguousNodesPtr_get, false);
    }

    public void setSubTreeInfoPtr(btBvhSubtreeInfoData btbvhsubtreeinfodata) {
        CollisionJNI.btQuantizedBvhFloatData_subTreeInfoPtr_set(this.swigCPtr, this, btBvhSubtreeInfoData.getCPtr(btbvhsubtreeinfodata), btbvhsubtreeinfodata);
    }

    public btBvhSubtreeInfoData getSubTreeInfoPtr() {
        long jBtQuantizedBvhFloatData_subTreeInfoPtr_get = CollisionJNI.btQuantizedBvhFloatData_subTreeInfoPtr_get(this.swigCPtr, this);
        if (jBtQuantizedBvhFloatData_subTreeInfoPtr_get == 0) {
            return null;
        }
        return new btBvhSubtreeInfoData(jBtQuantizedBvhFloatData_subTreeInfoPtr_get, false);
    }

    public void setTraversalMode(int i) {
        CollisionJNI.btQuantizedBvhFloatData_traversalMode_set(this.swigCPtr, this, i);
    }

    public int getTraversalMode() {
        return CollisionJNI.btQuantizedBvhFloatData_traversalMode_get(this.swigCPtr, this);
    }

    public void setNumSubtreeHeaders(int i) {
        CollisionJNI.btQuantizedBvhFloatData_numSubtreeHeaders_set(this.swigCPtr, this, i);
    }

    public int getNumSubtreeHeaders() {
        return CollisionJNI.btQuantizedBvhFloatData_numSubtreeHeaders_get(this.swigCPtr, this);
    }

    public btQuantizedBvhFloatData() {
        this(CollisionJNI.new_btQuantizedBvhFloatData(), true);
    }
}
