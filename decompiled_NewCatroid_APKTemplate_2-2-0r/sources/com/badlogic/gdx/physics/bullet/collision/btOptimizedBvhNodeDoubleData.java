package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btOptimizedBvhNodeDoubleData extends BulletBase {
    private long swigCPtr;

    protected btOptimizedBvhNodeDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btOptimizedBvhNodeDoubleData(long j, boolean z) {
        this("btOptimizedBvhNodeDoubleData", j, z);
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

    public static long getCPtr(btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata) {
        if (btoptimizedbvhnodedoubledata == null) {
            return 0L;
        }
        return btoptimizedbvhnodedoubledata.swigCPtr;
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
                CollisionJNI.delete_btOptimizedBvhNodeDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setAabbMinOrg(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btOptimizedBvhNodeDoubleData_aabbMinOrg_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAabbMinOrg() {
        long jBtOptimizedBvhNodeDoubleData_aabbMinOrg_get = CollisionJNI.btOptimizedBvhNodeDoubleData_aabbMinOrg_get(this.swigCPtr, this);
        if (jBtOptimizedBvhNodeDoubleData_aabbMinOrg_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtOptimizedBvhNodeDoubleData_aabbMinOrg_get, false);
    }

    public void setAabbMaxOrg(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btOptimizedBvhNodeDoubleData_aabbMaxOrg_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAabbMaxOrg() {
        long jBtOptimizedBvhNodeDoubleData_aabbMaxOrg_get = CollisionJNI.btOptimizedBvhNodeDoubleData_aabbMaxOrg_get(this.swigCPtr, this);
        if (jBtOptimizedBvhNodeDoubleData_aabbMaxOrg_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtOptimizedBvhNodeDoubleData_aabbMaxOrg_get, false);
    }

    public void setEscapeIndex(int i) {
        CollisionJNI.btOptimizedBvhNodeDoubleData_escapeIndex_set(this.swigCPtr, this, i);
    }

    public int getEscapeIndex() {
        return CollisionJNI.btOptimizedBvhNodeDoubleData_escapeIndex_get(this.swigCPtr, this);
    }

    public void setSubPart(int i) {
        CollisionJNI.btOptimizedBvhNodeDoubleData_subPart_set(this.swigCPtr, this, i);
    }

    public int getSubPart() {
        return CollisionJNI.btOptimizedBvhNodeDoubleData_subPart_get(this.swigCPtr, this);
    }

    public void setTriangleIndex(int i) {
        CollisionJNI.btOptimizedBvhNodeDoubleData_triangleIndex_set(this.swigCPtr, this, i);
    }

    public int getTriangleIndex() {
        return CollisionJNI.btOptimizedBvhNodeDoubleData_triangleIndex_get(this.swigCPtr, this);
    }

    public void setPad(String str) {
        CollisionJNI.btOptimizedBvhNodeDoubleData_pad_set(this.swigCPtr, this, str);
    }

    public String getPad() {
        return CollisionJNI.btOptimizedBvhNodeDoubleData_pad_get(this.swigCPtr, this);
    }

    public btOptimizedBvhNodeDoubleData() {
        this(CollisionJNI.new_btOptimizedBvhNodeDoubleData(), true);
    }
}
