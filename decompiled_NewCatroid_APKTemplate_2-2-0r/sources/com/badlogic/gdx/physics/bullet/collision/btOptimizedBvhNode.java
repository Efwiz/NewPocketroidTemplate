package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btOptimizedBvhNode extends BulletBase {
    private long swigCPtr;

    protected btOptimizedBvhNode(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btOptimizedBvhNode(long j, boolean z) {
        this("btOptimizedBvhNode", j, z);
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

    public static long getCPtr(btOptimizedBvhNode btoptimizedbvhnode) {
        if (btoptimizedbvhnode == null) {
            return 0L;
        }
        return btoptimizedbvhnode.swigCPtr;
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
                CollisionJNI.delete_btOptimizedBvhNode(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btOptimizedBvhNode_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btOptimizedBvhNode_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btOptimizedBvhNode_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btOptimizedBvhNode_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btOptimizedBvhNode_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btOptimizedBvhNode_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btOptimizedBvhNode_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btOptimizedBvhNode_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setAabbMinOrg(btVector3 btvector3) {
        CollisionJNI.btOptimizedBvhNode_aabbMinOrg_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAabbMinOrg() {
        long jBtOptimizedBvhNode_aabbMinOrg_get = CollisionJNI.btOptimizedBvhNode_aabbMinOrg_get(this.swigCPtr, this);
        if (jBtOptimizedBvhNode_aabbMinOrg_get == 0) {
            return null;
        }
        return new btVector3(jBtOptimizedBvhNode_aabbMinOrg_get, false);
    }

    public void setAabbMaxOrg(btVector3 btvector3) {
        CollisionJNI.btOptimizedBvhNode_aabbMaxOrg_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAabbMaxOrg() {
        long jBtOptimizedBvhNode_aabbMaxOrg_get = CollisionJNI.btOptimizedBvhNode_aabbMaxOrg_get(this.swigCPtr, this);
        if (jBtOptimizedBvhNode_aabbMaxOrg_get == 0) {
            return null;
        }
        return new btVector3(jBtOptimizedBvhNode_aabbMaxOrg_get, false);
    }

    public void setEscapeIndex(int i) {
        CollisionJNI.btOptimizedBvhNode_escapeIndex_set(this.swigCPtr, this, i);
    }

    public int getEscapeIndex() {
        return CollisionJNI.btOptimizedBvhNode_escapeIndex_get(this.swigCPtr, this);
    }

    public void setSubPart(int i) {
        CollisionJNI.btOptimizedBvhNode_subPart_set(this.swigCPtr, this, i);
    }

    public int getSubPart() {
        return CollisionJNI.btOptimizedBvhNode_subPart_get(this.swigCPtr, this);
    }

    public void setTriangleIndex(int i) {
        CollisionJNI.btOptimizedBvhNode_triangleIndex_set(this.swigCPtr, this, i);
    }

    public int getTriangleIndex() {
        return CollisionJNI.btOptimizedBvhNode_triangleIndex_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btOptimizedBvhNode_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btOptimizedBvhNode_padding_get(this.swigCPtr, this);
    }

    public btOptimizedBvhNode() {
        this(CollisionJNI.new_btOptimizedBvhNode(), true);
    }
}
