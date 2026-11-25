package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btOptimizedBvhNodeFloatData extends BulletBase {
    private long swigCPtr;

    protected btOptimizedBvhNodeFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btOptimizedBvhNodeFloatData(long j, boolean z) {
        this("btOptimizedBvhNodeFloatData", j, z);
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

    public static long getCPtr(btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata) {
        if (btoptimizedbvhnodefloatdata == null) {
            return 0L;
        }
        return btoptimizedbvhnodefloatdata.swigCPtr;
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
                CollisionJNI.delete_btOptimizedBvhNodeFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setAabbMinOrg(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btOptimizedBvhNodeFloatData_aabbMinOrg_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAabbMinOrg() {
        long jBtOptimizedBvhNodeFloatData_aabbMinOrg_get = CollisionJNI.btOptimizedBvhNodeFloatData_aabbMinOrg_get(this.swigCPtr, this);
        if (jBtOptimizedBvhNodeFloatData_aabbMinOrg_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtOptimizedBvhNodeFloatData_aabbMinOrg_get, false);
    }

    public void setAabbMaxOrg(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btOptimizedBvhNodeFloatData_aabbMaxOrg_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAabbMaxOrg() {
        long jBtOptimizedBvhNodeFloatData_aabbMaxOrg_get = CollisionJNI.btOptimizedBvhNodeFloatData_aabbMaxOrg_get(this.swigCPtr, this);
        if (jBtOptimizedBvhNodeFloatData_aabbMaxOrg_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtOptimizedBvhNodeFloatData_aabbMaxOrg_get, false);
    }

    public void setEscapeIndex(int i) {
        CollisionJNI.btOptimizedBvhNodeFloatData_escapeIndex_set(this.swigCPtr, this, i);
    }

    public int getEscapeIndex() {
        return CollisionJNI.btOptimizedBvhNodeFloatData_escapeIndex_get(this.swigCPtr, this);
    }

    public void setSubPart(int i) {
        CollisionJNI.btOptimizedBvhNodeFloatData_subPart_set(this.swigCPtr, this, i);
    }

    public int getSubPart() {
        return CollisionJNI.btOptimizedBvhNodeFloatData_subPart_get(this.swigCPtr, this);
    }

    public void setTriangleIndex(int i) {
        CollisionJNI.btOptimizedBvhNodeFloatData_triangleIndex_set(this.swigCPtr, this, i);
    }

    public int getTriangleIndex() {
        return CollisionJNI.btOptimizedBvhNodeFloatData_triangleIndex_get(this.swigCPtr, this);
    }

    public void setPad(String str) {
        CollisionJNI.btOptimizedBvhNodeFloatData_pad_set(this.swigCPtr, this, str);
    }

    public String getPad() {
        return CollisionJNI.btOptimizedBvhNodeFloatData_pad_get(this.swigCPtr, this);
    }

    public btOptimizedBvhNodeFloatData() {
        this(CollisionJNI.new_btOptimizedBvhNodeFloatData(), true);
    }
}
