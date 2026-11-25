package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleMeshShapeData extends BulletBase {
    private long swigCPtr;

    protected btTriangleMeshShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTriangleMeshShapeData(long j, boolean z) {
        this("btTriangleMeshShapeData", j, z);
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

    public static long getCPtr(btTriangleMeshShapeData bttrianglemeshshapedata) {
        if (bttrianglemeshshapedata == null) {
            return 0L;
        }
        return bttrianglemeshshapedata.swigCPtr;
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
                CollisionJNI.delete_btTriangleMeshShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCollisionShapeData(btCollisionShapeData btcollisionshapedata) {
        CollisionJNI.btTriangleMeshShapeData_collisionShapeData_set(this.swigCPtr, this, btCollisionShapeData.getCPtr(btcollisionshapedata), btcollisionshapedata);
    }

    public btCollisionShapeData getCollisionShapeData() {
        long jBtTriangleMeshShapeData_collisionShapeData_get = CollisionJNI.btTriangleMeshShapeData_collisionShapeData_get(this.swigCPtr, this);
        if (jBtTriangleMeshShapeData_collisionShapeData_get == 0) {
            return null;
        }
        return new btCollisionShapeData(jBtTriangleMeshShapeData_collisionShapeData_get, false);
    }

    public void setMeshInterface(btStridingMeshInterfaceData btstridingmeshinterfacedata) {
        CollisionJNI.btTriangleMeshShapeData_meshInterface_set(this.swigCPtr, this, btStridingMeshInterfaceData.getCPtr(btstridingmeshinterfacedata), btstridingmeshinterfacedata);
    }

    public btStridingMeshInterfaceData getMeshInterface() {
        long jBtTriangleMeshShapeData_meshInterface_get = CollisionJNI.btTriangleMeshShapeData_meshInterface_get(this.swigCPtr, this);
        if (jBtTriangleMeshShapeData_meshInterface_get == 0) {
            return null;
        }
        return new btStridingMeshInterfaceData(jBtTriangleMeshShapeData_meshInterface_get, false);
    }

    public void setQuantizedFloatBvh(btQuantizedBvhFloatData btquantizedbvhfloatdata) {
        CollisionJNI.btTriangleMeshShapeData_quantizedFloatBvh_set(this.swigCPtr, this, btQuantizedBvhFloatData.getCPtr(btquantizedbvhfloatdata), btquantizedbvhfloatdata);
    }

    public btQuantizedBvhFloatData getQuantizedFloatBvh() {
        long jBtTriangleMeshShapeData_quantizedFloatBvh_get = CollisionJNI.btTriangleMeshShapeData_quantizedFloatBvh_get(this.swigCPtr, this);
        if (jBtTriangleMeshShapeData_quantizedFloatBvh_get == 0) {
            return null;
        }
        return new btQuantizedBvhFloatData(jBtTriangleMeshShapeData_quantizedFloatBvh_get, false);
    }

    public void setQuantizedDoubleBvh(btQuantizedBvhDoubleData btquantizedbvhdoubledata) {
        CollisionJNI.btTriangleMeshShapeData_quantizedDoubleBvh_set(this.swigCPtr, this, btQuantizedBvhDoubleData.getCPtr(btquantizedbvhdoubledata), btquantizedbvhdoubledata);
    }

    public btQuantizedBvhDoubleData getQuantizedDoubleBvh() {
        long jBtTriangleMeshShapeData_quantizedDoubleBvh_get = CollisionJNI.btTriangleMeshShapeData_quantizedDoubleBvh_get(this.swigCPtr, this);
        if (jBtTriangleMeshShapeData_quantizedDoubleBvh_get == 0) {
            return null;
        }
        return new btQuantizedBvhDoubleData(jBtTriangleMeshShapeData_quantizedDoubleBvh_get, false);
    }

    public void setTriangleInfoMap(btTriangleInfoMapData bttriangleinfomapdata) {
        CollisionJNI.btTriangleMeshShapeData_triangleInfoMap_set(this.swigCPtr, this, btTriangleInfoMapData.getCPtr(bttriangleinfomapdata), bttriangleinfomapdata);
    }

    public btTriangleInfoMapData getTriangleInfoMap() {
        long jBtTriangleMeshShapeData_triangleInfoMap_get = CollisionJNI.btTriangleMeshShapeData_triangleInfoMap_get(this.swigCPtr, this);
        if (jBtTriangleMeshShapeData_triangleInfoMap_get == 0) {
            return null;
        }
        return new btTriangleInfoMapData(jBtTriangleMeshShapeData_triangleInfoMap_get, false);
    }

    public void setCollisionMargin(float f) {
        CollisionJNI.btTriangleMeshShapeData_collisionMargin_set(this.swigCPtr, this, f);
    }

    public float getCollisionMargin() {
        return CollisionJNI.btTriangleMeshShapeData_collisionMargin_get(this.swigCPtr, this);
    }

    public void setPad3(String str) {
        CollisionJNI.btTriangleMeshShapeData_pad3_set(this.swigCPtr, this, str);
    }

    public String getPad3() {
        return CollisionJNI.btTriangleMeshShapeData_pad3_get(this.swigCPtr, this);
    }

    public btTriangleMeshShapeData() {
        this(CollisionJNI.new_btTriangleMeshShapeData(), true);
    }
}
