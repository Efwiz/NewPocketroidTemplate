package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btStaticPlaneShapeData extends BulletBase {
    private long swigCPtr;

    protected btStaticPlaneShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btStaticPlaneShapeData(long j, boolean z) {
        this("btStaticPlaneShapeData", j, z);
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

    public static long getCPtr(btStaticPlaneShapeData btstaticplaneshapedata) {
        if (btstaticplaneshapedata == null) {
            return 0L;
        }
        return btstaticplaneshapedata.swigCPtr;
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
                CollisionJNI.delete_btStaticPlaneShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCollisionShapeData(btCollisionShapeData btcollisionshapedata) {
        CollisionJNI.btStaticPlaneShapeData_collisionShapeData_set(this.swigCPtr, this, btCollisionShapeData.getCPtr(btcollisionshapedata), btcollisionshapedata);
    }

    public btCollisionShapeData getCollisionShapeData() {
        long jBtStaticPlaneShapeData_collisionShapeData_get = CollisionJNI.btStaticPlaneShapeData_collisionShapeData_get(this.swigCPtr, this);
        if (jBtStaticPlaneShapeData_collisionShapeData_get == 0) {
            return null;
        }
        return new btCollisionShapeData(jBtStaticPlaneShapeData_collisionShapeData_get, false);
    }

    public void setLocalScaling(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btStaticPlaneShapeData_localScaling_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLocalScaling() {
        long jBtStaticPlaneShapeData_localScaling_get = CollisionJNI.btStaticPlaneShapeData_localScaling_get(this.swigCPtr, this);
        if (jBtStaticPlaneShapeData_localScaling_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtStaticPlaneShapeData_localScaling_get, false);
    }

    public void setPlaneNormal(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btStaticPlaneShapeData_planeNormal_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getPlaneNormal() {
        long jBtStaticPlaneShapeData_planeNormal_get = CollisionJNI.btStaticPlaneShapeData_planeNormal_get(this.swigCPtr, this);
        if (jBtStaticPlaneShapeData_planeNormal_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtStaticPlaneShapeData_planeNormal_get, false);
    }

    public void setPlaneConstant(float f) {
        CollisionJNI.btStaticPlaneShapeData_planeConstant_set(this.swigCPtr, this, f);
    }

    public float getPlaneConstant() {
        return CollisionJNI.btStaticPlaneShapeData_planeConstant_get(this.swigCPtr, this);
    }

    public void setPad(String str) {
        CollisionJNI.btStaticPlaneShapeData_pad_set(this.swigCPtr, this, str);
    }

    public String getPad() {
        return CollisionJNI.btStaticPlaneShapeData_pad_get(this.swigCPtr, this);
    }

    public btStaticPlaneShapeData() {
        this(CollisionJNI.new_btStaticPlaneShapeData(), true);
    }
}
