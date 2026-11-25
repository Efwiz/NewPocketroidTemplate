package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btGImpactQuantizedBvh extends BulletBase {
    private long swigCPtr;

    protected btGImpactQuantizedBvh(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGImpactQuantizedBvh(long j, boolean z) {
        this("btGImpactQuantizedBvh", j, z);
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

    public static long getCPtr(btGImpactQuantizedBvh btgimpactquantizedbvh) {
        if (btgimpactquantizedbvh == null) {
            return 0L;
        }
        return btgimpactquantizedbvh.swigCPtr;
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
                CollisionJNI.delete_btGImpactQuantizedBvh(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGImpactQuantizedBvh() {
        this(CollisionJNI.new_btGImpactQuantizedBvh__SWIG_0(), true);
    }

    public btGImpactQuantizedBvh(btPrimitiveManagerBase btprimitivemanagerbase) {
        this(CollisionJNI.new_btGImpactQuantizedBvh__SWIG_1(btPrimitiveManagerBase.getCPtr(btprimitivemanagerbase), btprimitivemanagerbase), true);
    }

    public btAABB getGlobalBox() {
        return new btAABB(CollisionJNI.btGImpactQuantizedBvh_getGlobalBox(this.swigCPtr, this), true);
    }

    public void setPrimitiveManager(btPrimitiveManagerBase btprimitivemanagerbase) {
        CollisionJNI.btGImpactQuantizedBvh_setPrimitiveManager(this.swigCPtr, this, btPrimitiveManagerBase.getCPtr(btprimitivemanagerbase), btprimitivemanagerbase);
    }

    public btPrimitiveManagerBase getPrimitiveManager() {
        long jBtGImpactQuantizedBvh_getPrimitiveManager = CollisionJNI.btGImpactQuantizedBvh_getPrimitiveManager(this.swigCPtr, this);
        if (jBtGImpactQuantizedBvh_getPrimitiveManager == 0) {
            return null;
        }
        return new btPrimitiveManagerBase(jBtGImpactQuantizedBvh_getPrimitiveManager, false);
    }

    public void update() {
        CollisionJNI.btGImpactQuantizedBvh_update(this.swigCPtr, this);
    }

    public void buildSet() {
        CollisionJNI.btGImpactQuantizedBvh_buildSet(this.swigCPtr, this);
    }

    public boolean boxQuery(btAABB btaabb, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        return CollisionJNI.btGImpactQuantizedBvh_boxQuery(this.swigCPtr, this, btAABB.getCPtr(btaabb), btaabb, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public boolean boxQueryTrans(btAABB btaabb, Matrix4 matrix4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        return CollisionJNI.btGImpactQuantizedBvh_boxQueryTrans(this.swigCPtr, this, btAABB.getCPtr(btaabb), btaabb, matrix4, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public boolean rayQuery(Vector3 vector3, Vector3 vector4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        return CollisionJNI.btGImpactQuantizedBvh_rayQuery(this.swigCPtr, this, vector3, vector4, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public boolean hasHierarchy() {
        return CollisionJNI.btGImpactQuantizedBvh_hasHierarchy(this.swigCPtr, this);
    }

    public boolean isTrimesh() {
        return CollisionJNI.btGImpactQuantizedBvh_isTrimesh(this.swigCPtr, this);
    }

    public int getNodeCount() {
        return CollisionJNI.btGImpactQuantizedBvh_getNodeCount(this.swigCPtr, this);
    }

    public boolean isLeafNode(int i) {
        return CollisionJNI.btGImpactQuantizedBvh_isLeafNode(this.swigCPtr, this, i);
    }

    public int getNodeData(int i) {
        return CollisionJNI.btGImpactQuantizedBvh_getNodeData(this.swigCPtr, this, i);
    }

    public void getNodeBound(int i, btAABB btaabb) {
        CollisionJNI.btGImpactQuantizedBvh_getNodeBound(this.swigCPtr, this, i, btAABB.getCPtr(btaabb), btaabb);
    }

    public void setNodeBound(int i, btAABB btaabb) {
        CollisionJNI.btGImpactQuantizedBvh_setNodeBound(this.swigCPtr, this, i, btAABB.getCPtr(btaabb), btaabb);
    }

    public int getLeftNode(int i) {
        return CollisionJNI.btGImpactQuantizedBvh_getLeftNode(this.swigCPtr, this, i);
    }

    public int getRightNode(int i) {
        return CollisionJNI.btGImpactQuantizedBvh_getRightNode(this.swigCPtr, this, i);
    }

    public int getEscapeNodeIndex(int i) {
        return CollisionJNI.btGImpactQuantizedBvh_getEscapeNodeIndex(this.swigCPtr, this, i);
    }

    public void getNodeTriangle(int i, btPrimitiveTriangle btprimitivetriangle) {
        CollisionJNI.btGImpactQuantizedBvh_getNodeTriangle(this.swigCPtr, this, i, btPrimitiveTriangle.getCPtr(btprimitivetriangle), btprimitivetriangle);
    }

    public BT_QUANTIZED_BVH_NODE get_node_pointer(int i) {
        long jBtGImpactQuantizedBvh_get_node_pointer__SWIG_0 = CollisionJNI.btGImpactQuantizedBvh_get_node_pointer__SWIG_0(this.swigCPtr, this, i);
        if (jBtGImpactQuantizedBvh_get_node_pointer__SWIG_0 == 0) {
            return null;
        }
        return new BT_QUANTIZED_BVH_NODE(jBtGImpactQuantizedBvh_get_node_pointer__SWIG_0, false);
    }

    public BT_QUANTIZED_BVH_NODE get_node_pointer() {
        long jBtGImpactQuantizedBvh_get_node_pointer__SWIG_1 = CollisionJNI.btGImpactQuantizedBvh_get_node_pointer__SWIG_1(this.swigCPtr, this);
        if (jBtGImpactQuantizedBvh_get_node_pointer__SWIG_1 == 0) {
            return null;
        }
        return new BT_QUANTIZED_BVH_NODE(jBtGImpactQuantizedBvh_get_node_pointer__SWIG_1, false);
    }

    public static void find_collision(btGImpactQuantizedBvh btgimpactquantizedbvh, Matrix4 matrix4, btGImpactQuantizedBvh btgimpactquantizedbvh2, Matrix4 matrix5, btPairSet btpairset) {
        CollisionJNI.btGImpactQuantizedBvh_find_collision(getCPtr(btgimpactquantizedbvh), btgimpactquantizedbvh, matrix4, getCPtr(btgimpactquantizedbvh2), btgimpactquantizedbvh2, matrix5, btPairSet.getCPtr(btpairset), btpairset);
    }
}
