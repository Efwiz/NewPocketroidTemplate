package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btQuantizedBvhNode extends BulletBase {
    private long swigCPtr;

    protected btQuantizedBvhNode(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btQuantizedBvhNode(long j, boolean z) {
        this("btQuantizedBvhNode", j, z);
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

    public static long getCPtr(btQuantizedBvhNode btquantizedbvhnode) {
        if (btquantizedbvhnode == null) {
            return 0L;
        }
        return btquantizedbvhnode.swigCPtr;
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
                CollisionJNI.delete_btQuantizedBvhNode(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btQuantizedBvhNode_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btQuantizedBvhNode_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btQuantizedBvhNode_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btQuantizedBvhNode_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btQuantizedBvhNode_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btQuantizedBvhNode_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btQuantizedBvhNode_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btQuantizedBvhNode_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setQuantizedAabbMin(int[] iArr) {
        CollisionJNI.btQuantizedBvhNode_quantizedAabbMin_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMin() {
        return CollisionJNI.btQuantizedBvhNode_quantizedAabbMin_get(this.swigCPtr, this);
    }

    public void setQuantizedAabbMax(int[] iArr) {
        CollisionJNI.btQuantizedBvhNode_quantizedAabbMax_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMax() {
        return CollisionJNI.btQuantizedBvhNode_quantizedAabbMax_get(this.swigCPtr, this);
    }

    public void setEscapeIndexOrTriangleIndex(int i) {
        CollisionJNI.btQuantizedBvhNode_escapeIndexOrTriangleIndex_set(this.swigCPtr, this, i);
    }

    public int getEscapeIndexOrTriangleIndex() {
        return CollisionJNI.btQuantizedBvhNode_escapeIndexOrTriangleIndex_get(this.swigCPtr, this);
    }

    public boolean isLeafNode() {
        return CollisionJNI.btQuantizedBvhNode_isLeafNode(this.swigCPtr, this);
    }

    public int getEscapeIndex() {
        return CollisionJNI.btQuantizedBvhNode_getEscapeIndex(this.swigCPtr, this);
    }

    public int getTriangleIndex() {
        return CollisionJNI.btQuantizedBvhNode_getTriangleIndex(this.swigCPtr, this);
    }

    public int getPartId() {
        return CollisionJNI.btQuantizedBvhNode_getPartId(this.swigCPtr, this);
    }

    public btQuantizedBvhNode() {
        this(CollisionJNI.new_btQuantizedBvhNode(), true);
    }
}
