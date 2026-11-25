package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btStridingMeshInterfaceData extends BulletBase {
    private long swigCPtr;

    protected btStridingMeshInterfaceData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btStridingMeshInterfaceData(long j, boolean z) {
        this("btStridingMeshInterfaceData", j, z);
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

    public static long getCPtr(btStridingMeshInterfaceData btstridingmeshinterfacedata) {
        if (btstridingmeshinterfacedata == null) {
            return 0L;
        }
        return btstridingmeshinterfacedata.swigCPtr;
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
                CollisionJNI.delete_btStridingMeshInterfaceData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setMeshPartsPtr(btMeshPartData btmeshpartdata) {
        CollisionJNI.btStridingMeshInterfaceData_meshPartsPtr_set(this.swigCPtr, this, btMeshPartData.getCPtr(btmeshpartdata), btmeshpartdata);
    }

    public btMeshPartData getMeshPartsPtr() {
        long jBtStridingMeshInterfaceData_meshPartsPtr_get = CollisionJNI.btStridingMeshInterfaceData_meshPartsPtr_get(this.swigCPtr, this);
        if (jBtStridingMeshInterfaceData_meshPartsPtr_get == 0) {
            return null;
        }
        return new btMeshPartData(jBtStridingMeshInterfaceData_meshPartsPtr_get, false);
    }

    public void setScaling(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btStridingMeshInterfaceData_scaling_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getScaling() {
        long jBtStridingMeshInterfaceData_scaling_get = CollisionJNI.btStridingMeshInterfaceData_scaling_get(this.swigCPtr, this);
        if (jBtStridingMeshInterfaceData_scaling_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtStridingMeshInterfaceData_scaling_get, false);
    }

    public void setNumMeshParts(int i) {
        CollisionJNI.btStridingMeshInterfaceData_numMeshParts_set(this.swigCPtr, this, i);
    }

    public int getNumMeshParts() {
        return CollisionJNI.btStridingMeshInterfaceData_numMeshParts_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btStridingMeshInterfaceData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btStridingMeshInterfaceData_padding_get(this.swigCPtr, this);
    }

    public btStridingMeshInterfaceData() {
        this(CollisionJNI.new_btStridingMeshInterfaceData(), true);
    }
}
