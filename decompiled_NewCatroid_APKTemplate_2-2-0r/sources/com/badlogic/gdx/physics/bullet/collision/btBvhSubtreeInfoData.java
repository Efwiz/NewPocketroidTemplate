package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btBvhSubtreeInfoData extends BulletBase {
    private long swigCPtr;

    protected btBvhSubtreeInfoData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBvhSubtreeInfoData(long j, boolean z) {
        this("btBvhSubtreeInfoData", j, z);
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

    public static long getCPtr(btBvhSubtreeInfoData btbvhsubtreeinfodata) {
        if (btbvhsubtreeinfodata == null) {
            return 0L;
        }
        return btbvhsubtreeinfodata.swigCPtr;
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
                CollisionJNI.delete_btBvhSubtreeInfoData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setRootNodeIndex(int i) {
        CollisionJNI.btBvhSubtreeInfoData_rootNodeIndex_set(this.swigCPtr, this, i);
    }

    public int getRootNodeIndex() {
        return CollisionJNI.btBvhSubtreeInfoData_rootNodeIndex_get(this.swigCPtr, this);
    }

    public void setSubtreeSize(int i) {
        CollisionJNI.btBvhSubtreeInfoData_subtreeSize_set(this.swigCPtr, this, i);
    }

    public int getSubtreeSize() {
        return CollisionJNI.btBvhSubtreeInfoData_subtreeSize_get(this.swigCPtr, this);
    }

    public void setQuantizedAabbMin(int[] iArr) {
        CollisionJNI.btBvhSubtreeInfoData_quantizedAabbMin_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMin() {
        return CollisionJNI.btBvhSubtreeInfoData_quantizedAabbMin_get(this.swigCPtr, this);
    }

    public void setQuantizedAabbMax(int[] iArr) {
        CollisionJNI.btBvhSubtreeInfoData_quantizedAabbMax_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMax() {
        return CollisionJNI.btBvhSubtreeInfoData_quantizedAabbMax_get(this.swigCPtr, this);
    }

    public btBvhSubtreeInfoData() {
        this(CollisionJNI.new_btBvhSubtreeInfoData(), true);
    }
}
