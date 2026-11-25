package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class BT_QUANTIZED_BVH_NODE extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected BT_QUANTIZED_BVH_NODE(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public BT_QUANTIZED_BVH_NODE(long j, boolean z) {
        this("BT_QUANTIZED_BVH_NODE", j, z);
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

    public static long getCPtr(BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node) {
        if (bt_quantized_bvh_node == null) {
            return 0L;
        }
        return bt_quantized_bvh_node.swigCPtr;
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
                CollisionJNI.delete_BT_QUANTIZED_BVH_NODE(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setQuantizedAabbMin(int[] iArr) {
        CollisionJNI.BT_QUANTIZED_BVH_NODE_quantizedAabbMin_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMin() {
        return CollisionJNI.BT_QUANTIZED_BVH_NODE_quantizedAabbMin_get(this.swigCPtr, this);
    }

    public void setQuantizedAabbMax(int[] iArr) {
        CollisionJNI.BT_QUANTIZED_BVH_NODE_quantizedAabbMax_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMax() {
        return CollisionJNI.BT_QUANTIZED_BVH_NODE_quantizedAabbMax_get(this.swigCPtr, this);
    }

    public void setEscapeIndexOrDataIndex(int i) {
        CollisionJNI.BT_QUANTIZED_BVH_NODE_escapeIndexOrDataIndex_set(this.swigCPtr, this, i);
    }

    public int getEscapeIndexOrDataIndex() {
        return CollisionJNI.BT_QUANTIZED_BVH_NODE_escapeIndexOrDataIndex_get(this.swigCPtr, this);
    }

    public BT_QUANTIZED_BVH_NODE() {
        this(CollisionJNI.new_BT_QUANTIZED_BVH_NODE(), true);
    }

    public boolean isLeafNode() {
        return CollisionJNI.BT_QUANTIZED_BVH_NODE_isLeafNode(this.swigCPtr, this);
    }

    public int getEscapeIndex() {
        return CollisionJNI.BT_QUANTIZED_BVH_NODE_getEscapeIndex(this.swigCPtr, this);
    }

    public void setEscapeIndex(int i) {
        CollisionJNI.BT_QUANTIZED_BVH_NODE_setEscapeIndex(this.swigCPtr, this, i);
    }

    public int getDataIndex() {
        return CollisionJNI.BT_QUANTIZED_BVH_NODE_getDataIndex(this.swigCPtr, this);
    }

    public void setDataIndex(int i) {
        CollisionJNI.BT_QUANTIZED_BVH_NODE_setDataIndex(this.swigCPtr, this, i);
    }

    public boolean testQuantizedBoxOverlapp(IntBuffer intBuffer, IntBuffer intBuffer2) {
        return CollisionJNI.BT_QUANTIZED_BVH_NODE_testQuantizedBoxOverlapp(this.swigCPtr, this, intBuffer, intBuffer2);
    }
}
