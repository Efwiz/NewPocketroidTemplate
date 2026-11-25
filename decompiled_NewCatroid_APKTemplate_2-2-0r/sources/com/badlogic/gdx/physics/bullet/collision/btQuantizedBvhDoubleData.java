package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btQuantizedBvhDoubleData extends BulletBase {
    private long swigCPtr;

    protected btQuantizedBvhDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btQuantizedBvhDoubleData(long j, boolean z) {
        this("btQuantizedBvhDoubleData", j, z);
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

    public static long getCPtr(btQuantizedBvhDoubleData btquantizedbvhdoubledata) {
        if (btquantizedbvhdoubledata == null) {
            return 0L;
        }
        return btquantizedbvhdoubledata.swigCPtr;
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
                CollisionJNI.delete_btQuantizedBvhDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBvhAabbMin(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btQuantizedBvhDoubleData_bvhAabbMin_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getBvhAabbMin() {
        long jBtQuantizedBvhDoubleData_bvhAabbMin_get = CollisionJNI.btQuantizedBvhDoubleData_bvhAabbMin_get(this.swigCPtr, this);
        if (jBtQuantizedBvhDoubleData_bvhAabbMin_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtQuantizedBvhDoubleData_bvhAabbMin_get, false);
    }

    public void setBvhAabbMax(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btQuantizedBvhDoubleData_bvhAabbMax_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getBvhAabbMax() {
        long jBtQuantizedBvhDoubleData_bvhAabbMax_get = CollisionJNI.btQuantizedBvhDoubleData_bvhAabbMax_get(this.swigCPtr, this);
        if (jBtQuantizedBvhDoubleData_bvhAabbMax_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtQuantizedBvhDoubleData_bvhAabbMax_get, false);
    }

    public void setBvhQuantization(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btQuantizedBvhDoubleData_bvhQuantization_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getBvhQuantization() {
        long jBtQuantizedBvhDoubleData_bvhQuantization_get = CollisionJNI.btQuantizedBvhDoubleData_bvhQuantization_get(this.swigCPtr, this);
        if (jBtQuantizedBvhDoubleData_bvhQuantization_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtQuantizedBvhDoubleData_bvhQuantization_get, false);
    }

    public void setCurNodeIndex(int i) {
        CollisionJNI.btQuantizedBvhDoubleData_curNodeIndex_set(this.swigCPtr, this, i);
    }

    public int getCurNodeIndex() {
        return CollisionJNI.btQuantizedBvhDoubleData_curNodeIndex_get(this.swigCPtr, this);
    }

    public void setUseQuantization(int i) {
        CollisionJNI.btQuantizedBvhDoubleData_useQuantization_set(this.swigCPtr, this, i);
    }

    public int getUseQuantization() {
        return CollisionJNI.btQuantizedBvhDoubleData_useQuantization_get(this.swigCPtr, this);
    }

    public void setNumContiguousLeafNodes(int i) {
        CollisionJNI.btQuantizedBvhDoubleData_numContiguousLeafNodes_set(this.swigCPtr, this, i);
    }

    public int getNumContiguousLeafNodes() {
        return CollisionJNI.btQuantizedBvhDoubleData_numContiguousLeafNodes_get(this.swigCPtr, this);
    }

    public void setNumQuantizedContiguousNodes(int i) {
        CollisionJNI.btQuantizedBvhDoubleData_numQuantizedContiguousNodes_set(this.swigCPtr, this, i);
    }

    public int getNumQuantizedContiguousNodes() {
        return CollisionJNI.btQuantizedBvhDoubleData_numQuantizedContiguousNodes_get(this.swigCPtr, this);
    }

    public void setContiguousNodesPtr(btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata) {
        CollisionJNI.btQuantizedBvhDoubleData_contiguousNodesPtr_set(this.swigCPtr, this, btOptimizedBvhNodeDoubleData.getCPtr(btoptimizedbvhnodedoubledata), btoptimizedbvhnodedoubledata);
    }

    public btOptimizedBvhNodeDoubleData getContiguousNodesPtr() {
        long jBtQuantizedBvhDoubleData_contiguousNodesPtr_get = CollisionJNI.btQuantizedBvhDoubleData_contiguousNodesPtr_get(this.swigCPtr, this);
        if (jBtQuantizedBvhDoubleData_contiguousNodesPtr_get == 0) {
            return null;
        }
        return new btOptimizedBvhNodeDoubleData(jBtQuantizedBvhDoubleData_contiguousNodesPtr_get, false);
    }

    public void setQuantizedContiguousNodesPtr(btQuantizedBvhNodeData btquantizedbvhnodedata) {
        CollisionJNI.btQuantizedBvhDoubleData_quantizedContiguousNodesPtr_set(this.swigCPtr, this, btQuantizedBvhNodeData.getCPtr(btquantizedbvhnodedata), btquantizedbvhnodedata);
    }

    public btQuantizedBvhNodeData getQuantizedContiguousNodesPtr() {
        long jBtQuantizedBvhDoubleData_quantizedContiguousNodesPtr_get = CollisionJNI.btQuantizedBvhDoubleData_quantizedContiguousNodesPtr_get(this.swigCPtr, this);
        if (jBtQuantizedBvhDoubleData_quantizedContiguousNodesPtr_get == 0) {
            return null;
        }
        return new btQuantizedBvhNodeData(jBtQuantizedBvhDoubleData_quantizedContiguousNodesPtr_get, false);
    }

    public void setTraversalMode(int i) {
        CollisionJNI.btQuantizedBvhDoubleData_traversalMode_set(this.swigCPtr, this, i);
    }

    public int getTraversalMode() {
        return CollisionJNI.btQuantizedBvhDoubleData_traversalMode_get(this.swigCPtr, this);
    }

    public void setNumSubtreeHeaders(int i) {
        CollisionJNI.btQuantizedBvhDoubleData_numSubtreeHeaders_set(this.swigCPtr, this, i);
    }

    public int getNumSubtreeHeaders() {
        return CollisionJNI.btQuantizedBvhDoubleData_numSubtreeHeaders_get(this.swigCPtr, this);
    }

    public void setSubTreeInfoPtr(btBvhSubtreeInfoData btbvhsubtreeinfodata) {
        CollisionJNI.btQuantizedBvhDoubleData_subTreeInfoPtr_set(this.swigCPtr, this, btBvhSubtreeInfoData.getCPtr(btbvhsubtreeinfodata), btbvhsubtreeinfodata);
    }

    public btBvhSubtreeInfoData getSubTreeInfoPtr() {
        long jBtQuantizedBvhDoubleData_subTreeInfoPtr_get = CollisionJNI.btQuantizedBvhDoubleData_subTreeInfoPtr_get(this.swigCPtr, this);
        if (jBtQuantizedBvhDoubleData_subTreeInfoPtr_get == 0) {
            return null;
        }
        return new btBvhSubtreeInfoData(jBtQuantizedBvhDoubleData_subTreeInfoPtr_get, false);
    }

    public btQuantizedBvhDoubleData() {
        this(CollisionJNI.new_btQuantizedBvhDoubleData(), true);
    }
}
