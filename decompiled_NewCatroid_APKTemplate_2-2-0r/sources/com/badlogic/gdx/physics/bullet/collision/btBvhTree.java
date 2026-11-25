package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btBvhTree extends BulletBase {
    private long swigCPtr;

    protected btBvhTree(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBvhTree(long j, boolean z) {
        this("btBvhTree", j, z);
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

    public static long getCPtr(btBvhTree btbvhtree) {
        if (btbvhtree == null) {
            return 0L;
        }
        return btbvhtree.swigCPtr;
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
                CollisionJNI.delete_btBvhTree(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btBvhTree() {
        this(CollisionJNI.new_btBvhTree(), true);
    }

    public void build_tree(GIM_BVH_DATA_ARRAY gim_bvh_data_array) {
        CollisionJNI.btBvhTree_build_tree(this.swigCPtr, this, GIM_BVH_DATA_ARRAY.getCPtr(gim_bvh_data_array), gim_bvh_data_array);
    }

    public void clearNodes() {
        CollisionJNI.btBvhTree_clearNodes(this.swigCPtr, this);
    }

    public int getNodeCount() {
        return CollisionJNI.btBvhTree_getNodeCount(this.swigCPtr, this);
    }

    public boolean isLeafNode(int i) {
        return CollisionJNI.btBvhTree_isLeafNode(this.swigCPtr, this, i);
    }

    public int getNodeData(int i) {
        return CollisionJNI.btBvhTree_getNodeData(this.swigCPtr, this, i);
    }

    public void getNodeBound(int i, btAABB btaabb) {
        CollisionJNI.btBvhTree_getNodeBound(this.swigCPtr, this, i, btAABB.getCPtr(btaabb), btaabb);
    }

    public void setNodeBound(int i, btAABB btaabb) {
        CollisionJNI.btBvhTree_setNodeBound(this.swigCPtr, this, i, btAABB.getCPtr(btaabb), btaabb);
    }

    public int getLeftNode(int i) {
        return CollisionJNI.btBvhTree_getLeftNode(this.swigCPtr, this, i);
    }

    public int getRightNode(int i) {
        return CollisionJNI.btBvhTree_getRightNode(this.swigCPtr, this, i);
    }

    public int getEscapeNodeIndex(int i) {
        return CollisionJNI.btBvhTree_getEscapeNodeIndex(this.swigCPtr, this, i);
    }

    public GIM_BVH_TREE_NODE get_node_pointer(int i) {
        long jBtBvhTree_get_node_pointer__SWIG_0 = CollisionJNI.btBvhTree_get_node_pointer__SWIG_0(this.swigCPtr, this, i);
        if (jBtBvhTree_get_node_pointer__SWIG_0 == 0) {
            return null;
        }
        return new GIM_BVH_TREE_NODE(jBtBvhTree_get_node_pointer__SWIG_0, false);
    }

    public GIM_BVH_TREE_NODE get_node_pointer() {
        long jBtBvhTree_get_node_pointer__SWIG_1 = CollisionJNI.btBvhTree_get_node_pointer__SWIG_1(this.swigCPtr, this);
        if (jBtBvhTree_get_node_pointer__SWIG_1 == 0) {
            return null;
        }
        return new GIM_BVH_TREE_NODE(jBtBvhTree_get_node_pointer__SWIG_1, false);
    }
}
