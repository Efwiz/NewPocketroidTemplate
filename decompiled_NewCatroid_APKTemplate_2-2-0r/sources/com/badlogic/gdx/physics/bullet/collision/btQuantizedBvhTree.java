package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btQuantizedBvhTree extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btQuantizedBvhTree(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btQuantizedBvhTree(long j, boolean z) {
        this("btQuantizedBvhTree", j, z);
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

    public static long getCPtr(btQuantizedBvhTree btquantizedbvhtree) {
        if (btquantizedbvhtree == null) {
            return 0L;
        }
        return btquantizedbvhtree.swigCPtr;
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
                CollisionJNI.delete_btQuantizedBvhTree(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btQuantizedBvhTree() {
        this(CollisionJNI.new_btQuantizedBvhTree(), true);
    }

    public void build_tree(GIM_BVH_DATA_ARRAY gim_bvh_data_array) {
        CollisionJNI.btQuantizedBvhTree_build_tree(this.swigCPtr, this, GIM_BVH_DATA_ARRAY.getCPtr(gim_bvh_data_array), gim_bvh_data_array);
    }

    public void quantizePoint(IntBuffer intBuffer, Vector3 vector3) {
        CollisionJNI.btQuantizedBvhTree_quantizePoint(this.swigCPtr, this, intBuffer, vector3);
    }

    public boolean testQuantizedBoxOverlapp(int i, IntBuffer intBuffer, IntBuffer intBuffer2) {
        return CollisionJNI.btQuantizedBvhTree_testQuantizedBoxOverlapp(this.swigCPtr, this, i, intBuffer, intBuffer2);
    }

    public void clearNodes() {
        CollisionJNI.btQuantizedBvhTree_clearNodes(this.swigCPtr, this);
    }

    public int getNodeCount() {
        return CollisionJNI.btQuantizedBvhTree_getNodeCount(this.swigCPtr, this);
    }

    public boolean isLeafNode(int i) {
        return CollisionJNI.btQuantizedBvhTree_isLeafNode(this.swigCPtr, this, i);
    }

    public int getNodeData(int i) {
        return CollisionJNI.btQuantizedBvhTree_getNodeData(this.swigCPtr, this, i);
    }

    public void getNodeBound(int i, btAABB btaabb) {
        CollisionJNI.btQuantizedBvhTree_getNodeBound(this.swigCPtr, this, i, btAABB.getCPtr(btaabb), btaabb);
    }

    public void setNodeBound(int i, btAABB btaabb) {
        CollisionJNI.btQuantizedBvhTree_setNodeBound(this.swigCPtr, this, i, btAABB.getCPtr(btaabb), btaabb);
    }

    public int getLeftNode(int i) {
        return CollisionJNI.btQuantizedBvhTree_getLeftNode(this.swigCPtr, this, i);
    }

    public int getRightNode(int i) {
        return CollisionJNI.btQuantizedBvhTree_getRightNode(this.swigCPtr, this, i);
    }

    public int getEscapeNodeIndex(int i) {
        return CollisionJNI.btQuantizedBvhTree_getEscapeNodeIndex(this.swigCPtr, this, i);
    }

    public BT_QUANTIZED_BVH_NODE get_node_pointer(int i) {
        long jBtQuantizedBvhTree_get_node_pointer__SWIG_0 = CollisionJNI.btQuantizedBvhTree_get_node_pointer__SWIG_0(this.swigCPtr, this, i);
        if (jBtQuantizedBvhTree_get_node_pointer__SWIG_0 == 0) {
            return null;
        }
        return new BT_QUANTIZED_BVH_NODE(jBtQuantizedBvhTree_get_node_pointer__SWIG_0, false);
    }

    public BT_QUANTIZED_BVH_NODE get_node_pointer() {
        long jBtQuantizedBvhTree_get_node_pointer__SWIG_1 = CollisionJNI.btQuantizedBvhTree_get_node_pointer__SWIG_1(this.swigCPtr, this);
        if (jBtQuantizedBvhTree_get_node_pointer__SWIG_1 == 0) {
            return null;
        }
        return new BT_QUANTIZED_BVH_NODE(jBtQuantizedBvhTree_get_node_pointer__SWIG_1, false);
    }
}
