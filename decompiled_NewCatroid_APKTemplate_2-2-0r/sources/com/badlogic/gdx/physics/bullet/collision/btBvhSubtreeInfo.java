package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btBvhSubtreeInfo extends BulletBase {
    private long swigCPtr;

    protected btBvhSubtreeInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBvhSubtreeInfo(long j, boolean z) {
        this("btBvhSubtreeInfo", j, z);
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

    public static long getCPtr(btBvhSubtreeInfo btbvhsubtreeinfo) {
        if (btbvhsubtreeinfo == null) {
            return 0L;
        }
        return btbvhsubtreeinfo.swigCPtr;
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
                CollisionJNI.delete_btBvhSubtreeInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btBvhSubtreeInfo_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btBvhSubtreeInfo_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btBvhSubtreeInfo_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btBvhSubtreeInfo_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btBvhSubtreeInfo_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btBvhSubtreeInfo_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btBvhSubtreeInfo_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btBvhSubtreeInfo_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setQuantizedAabbMin(int[] iArr) {
        CollisionJNI.btBvhSubtreeInfo_quantizedAabbMin_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMin() {
        return CollisionJNI.btBvhSubtreeInfo_quantizedAabbMin_get(this.swigCPtr, this);
    }

    public void setQuantizedAabbMax(int[] iArr) {
        CollisionJNI.btBvhSubtreeInfo_quantizedAabbMax_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMax() {
        return CollisionJNI.btBvhSubtreeInfo_quantizedAabbMax_get(this.swigCPtr, this);
    }

    public void setRootNodeIndex(int i) {
        CollisionJNI.btBvhSubtreeInfo_rootNodeIndex_set(this.swigCPtr, this, i);
    }

    public int getRootNodeIndex() {
        return CollisionJNI.btBvhSubtreeInfo_rootNodeIndex_get(this.swigCPtr, this);
    }

    public void setSubtreeSize(int i) {
        CollisionJNI.btBvhSubtreeInfo_subtreeSize_set(this.swigCPtr, this, i);
    }

    public int getSubtreeSize() {
        return CollisionJNI.btBvhSubtreeInfo_subtreeSize_get(this.swigCPtr, this);
    }

    public void setPadding(int[] iArr) {
        CollisionJNI.btBvhSubtreeInfo_padding_set(this.swigCPtr, this, iArr);
    }

    public int[] getPadding() {
        return CollisionJNI.btBvhSubtreeInfo_padding_get(this.swigCPtr, this);
    }

    public btBvhSubtreeInfo() {
        this(CollisionJNI.new_btBvhSubtreeInfo(), true);
    }

    public void setAabbFromQuantizeNode(btQuantizedBvhNode btquantizedbvhnode) {
        CollisionJNI.btBvhSubtreeInfo_setAabbFromQuantizeNode(this.swigCPtr, this, btQuantizedBvhNode.getCPtr(btquantizedbvhnode), btquantizedbvhnode);
    }
}
