package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btGImpactMeshShapeData extends BulletBase {
    private long swigCPtr;

    protected btGImpactMeshShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGImpactMeshShapeData(long j, boolean z) {
        this("btGImpactMeshShapeData", j, z);
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

    public static long getCPtr(btGImpactMeshShapeData btgimpactmeshshapedata) {
        if (btgimpactmeshshapedata == null) {
            return 0L;
        }
        return btgimpactmeshshapedata.swigCPtr;
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
                CollisionJNI.delete_btGImpactMeshShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCollisionShapeData(btCollisionShapeData btcollisionshapedata) {
        CollisionJNI.btGImpactMeshShapeData_collisionShapeData_set(this.swigCPtr, this, btCollisionShapeData.getCPtr(btcollisionshapedata), btcollisionshapedata);
    }

    public btCollisionShapeData getCollisionShapeData() {
        long jBtGImpactMeshShapeData_collisionShapeData_get = CollisionJNI.btGImpactMeshShapeData_collisionShapeData_get(this.swigCPtr, this);
        if (jBtGImpactMeshShapeData_collisionShapeData_get == 0) {
            return null;
        }
        return new btCollisionShapeData(jBtGImpactMeshShapeData_collisionShapeData_get, false);
    }

    public void setMeshInterface(btStridingMeshInterfaceData btstridingmeshinterfacedata) {
        CollisionJNI.btGImpactMeshShapeData_meshInterface_set(this.swigCPtr, this, btStridingMeshInterfaceData.getCPtr(btstridingmeshinterfacedata), btstridingmeshinterfacedata);
    }

    public btStridingMeshInterfaceData getMeshInterface() {
        long jBtGImpactMeshShapeData_meshInterface_get = CollisionJNI.btGImpactMeshShapeData_meshInterface_get(this.swigCPtr, this);
        if (jBtGImpactMeshShapeData_meshInterface_get == 0) {
            return null;
        }
        return new btStridingMeshInterfaceData(jBtGImpactMeshShapeData_meshInterface_get, false);
    }

    public void setLocalScaling(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btGImpactMeshShapeData_localScaling_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLocalScaling() {
        long jBtGImpactMeshShapeData_localScaling_get = CollisionJNI.btGImpactMeshShapeData_localScaling_get(this.swigCPtr, this);
        if (jBtGImpactMeshShapeData_localScaling_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtGImpactMeshShapeData_localScaling_get, false);
    }

    public void setCollisionMargin(float f) {
        CollisionJNI.btGImpactMeshShapeData_collisionMargin_set(this.swigCPtr, this, f);
    }

    public float getCollisionMargin() {
        return CollisionJNI.btGImpactMeshShapeData_collisionMargin_get(this.swigCPtr, this);
    }

    public void setGimpactSubType(int i) {
        CollisionJNI.btGImpactMeshShapeData_gimpactSubType_set(this.swigCPtr, this, i);
    }

    public int getGimpactSubType() {
        return CollisionJNI.btGImpactMeshShapeData_gimpactSubType_get(this.swigCPtr, this);
    }

    public btGImpactMeshShapeData() {
        this(CollisionJNI.new_btGImpactMeshShapeData(), true);
    }
}
