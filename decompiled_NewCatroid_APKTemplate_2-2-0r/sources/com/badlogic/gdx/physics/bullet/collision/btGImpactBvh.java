package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btGImpactBvh extends BulletBase {
    private long swigCPtr;

    protected btGImpactBvh(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGImpactBvh(long j, boolean z) {
        this("btGImpactBvh", j, z);
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

    public static long getCPtr(btGImpactBvh btgimpactbvh) {
        if (btgimpactbvh == null) {
            return 0L;
        }
        return btgimpactbvh.swigCPtr;
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
                CollisionJNI.delete_btGImpactBvh(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGImpactBvh() {
        this(CollisionJNI.new_btGImpactBvh__SWIG_0(), true);
    }

    public btGImpactBvh(btPrimitiveManagerBase btprimitivemanagerbase) {
        this(CollisionJNI.new_btGImpactBvh__SWIG_1(btPrimitiveManagerBase.getCPtr(btprimitivemanagerbase), btprimitivemanagerbase), true);
    }

    public btAABB getGlobalBox() {
        return new btAABB(CollisionJNI.btGImpactBvh_getGlobalBox(this.swigCPtr, this), true);
    }

    public void setPrimitiveManager(btPrimitiveManagerBase btprimitivemanagerbase) {
        CollisionJNI.btGImpactBvh_setPrimitiveManager(this.swigCPtr, this, btPrimitiveManagerBase.getCPtr(btprimitivemanagerbase), btprimitivemanagerbase);
    }

    public btPrimitiveManagerBase getPrimitiveManager() {
        long jBtGImpactBvh_getPrimitiveManager = CollisionJNI.btGImpactBvh_getPrimitiveManager(this.swigCPtr, this);
        if (jBtGImpactBvh_getPrimitiveManager == 0) {
            return null;
        }
        return new btPrimitiveManagerBase(jBtGImpactBvh_getPrimitiveManager, false);
    }

    public void update() {
        CollisionJNI.btGImpactBvh_update(this.swigCPtr, this);
    }

    public void buildSet() {
        CollisionJNI.btGImpactBvh_buildSet(this.swigCPtr, this);
    }

    public boolean boxQuery(btAABB btaabb, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        return CollisionJNI.btGImpactBvh_boxQuery(this.swigCPtr, this, btAABB.getCPtr(btaabb), btaabb, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public boolean boxQueryTrans(btAABB btaabb, Matrix4 matrix4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        return CollisionJNI.btGImpactBvh_boxQueryTrans(this.swigCPtr, this, btAABB.getCPtr(btaabb), btaabb, matrix4, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public boolean rayQuery(Vector3 vector3, Vector3 vector4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        return CollisionJNI.btGImpactBvh_rayQuery(this.swigCPtr, this, vector3, vector4, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public boolean hasHierarchy() {
        return CollisionJNI.btGImpactBvh_hasHierarchy(this.swigCPtr, this);
    }

    public boolean isTrimesh() {
        return CollisionJNI.btGImpactBvh_isTrimesh(this.swigCPtr, this);
    }

    public int getNodeCount() {
        return CollisionJNI.btGImpactBvh_getNodeCount(this.swigCPtr, this);
    }

    public boolean isLeafNode(int i) {
        return CollisionJNI.btGImpactBvh_isLeafNode(this.swigCPtr, this, i);
    }

    public int getNodeData(int i) {
        return CollisionJNI.btGImpactBvh_getNodeData(this.swigCPtr, this, i);
    }

    public void getNodeBound(int i, btAABB btaabb) {
        CollisionJNI.btGImpactBvh_getNodeBound(this.swigCPtr, this, i, btAABB.getCPtr(btaabb), btaabb);
    }

    public void setNodeBound(int i, btAABB btaabb) {
        CollisionJNI.btGImpactBvh_setNodeBound(this.swigCPtr, this, i, btAABB.getCPtr(btaabb), btaabb);
    }

    public int getLeftNode(int i) {
        return CollisionJNI.btGImpactBvh_getLeftNode(this.swigCPtr, this, i);
    }

    public int getRightNode(int i) {
        return CollisionJNI.btGImpactBvh_getRightNode(this.swigCPtr, this, i);
    }

    public int getEscapeNodeIndex(int i) {
        return CollisionJNI.btGImpactBvh_getEscapeNodeIndex(this.swigCPtr, this, i);
    }

    public void getNodeTriangle(int i, btPrimitiveTriangle btprimitivetriangle) {
        CollisionJNI.btGImpactBvh_getNodeTriangle(this.swigCPtr, this, i, btPrimitiveTriangle.getCPtr(btprimitivetriangle), btprimitivetriangle);
    }

    public GIM_BVH_TREE_NODE get_node_pointer(int i) {
        long jBtGImpactBvh_get_node_pointer__SWIG_0 = CollisionJNI.btGImpactBvh_get_node_pointer__SWIG_0(this.swigCPtr, this, i);
        if (jBtGImpactBvh_get_node_pointer__SWIG_0 == 0) {
            return null;
        }
        return new GIM_BVH_TREE_NODE(jBtGImpactBvh_get_node_pointer__SWIG_0, false);
    }

    public GIM_BVH_TREE_NODE get_node_pointer() {
        long jBtGImpactBvh_get_node_pointer__SWIG_1 = CollisionJNI.btGImpactBvh_get_node_pointer__SWIG_1(this.swigCPtr, this);
        if (jBtGImpactBvh_get_node_pointer__SWIG_1 == 0) {
            return null;
        }
        return new GIM_BVH_TREE_NODE(jBtGImpactBvh_get_node_pointer__SWIG_1, false);
    }

    public static void find_collision(btGImpactBvh btgimpactbvh, Matrix4 matrix4, btGImpactBvh btgimpactbvh2, Matrix4 matrix5, btPairSet btpairset) {
        CollisionJNI.btGImpactBvh_find_collision(getCPtr(btgimpactbvh), btgimpactbvh, matrix4, getCPtr(btgimpactbvh2), btgimpactbvh2, matrix5, btPairSet.getCPtr(btpairset), btpairset);
    }
}
