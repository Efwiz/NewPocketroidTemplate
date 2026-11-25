package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btQuantizedBvhNodeData extends BulletBase {
    private long swigCPtr;

    protected btQuantizedBvhNodeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btQuantizedBvhNodeData(long j, boolean z) {
        this("btQuantizedBvhNodeData", j, z);
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

    public static long getCPtr(btQuantizedBvhNodeData btquantizedbvhnodedata) {
        if (btquantizedbvhnodedata == null) {
            return 0L;
        }
        return btquantizedbvhnodedata.swigCPtr;
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
                CollisionJNI.delete_btQuantizedBvhNodeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setQuantizedAabbMin(int[] iArr) {
        CollisionJNI.btQuantizedBvhNodeData_quantizedAabbMin_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMin() {
        return CollisionJNI.btQuantizedBvhNodeData_quantizedAabbMin_get(this.swigCPtr, this);
    }

    public void setQuantizedAabbMax(int[] iArr) {
        CollisionJNI.btQuantizedBvhNodeData_quantizedAabbMax_set(this.swigCPtr, this, iArr);
    }

    public int[] getQuantizedAabbMax() {
        return CollisionJNI.btQuantizedBvhNodeData_quantizedAabbMax_get(this.swigCPtr, this);
    }

    public void setEscapeIndexOrTriangleIndex(int i) {
        CollisionJNI.btQuantizedBvhNodeData_escapeIndexOrTriangleIndex_set(this.swigCPtr, this, i);
    }

    public int getEscapeIndexOrTriangleIndex() {
        return CollisionJNI.btQuantizedBvhNodeData_escapeIndexOrTriangleIndex_get(this.swigCPtr, this);
    }

    public btQuantizedBvhNodeData() {
        this(CollisionJNI.new_btQuantizedBvhNodeData(), true);
    }
}
