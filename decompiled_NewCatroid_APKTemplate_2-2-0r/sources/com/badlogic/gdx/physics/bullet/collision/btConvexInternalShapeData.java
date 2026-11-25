package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexInternalShapeData extends BulletBase {
    private long swigCPtr;

    protected btConvexInternalShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConvexInternalShapeData(long j, boolean z) {
        this("btConvexInternalShapeData", j, z);
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

    public static long getCPtr(btConvexInternalShapeData btconvexinternalshapedata) {
        if (btconvexinternalshapedata == null) {
            return 0L;
        }
        return btconvexinternalshapedata.swigCPtr;
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
                CollisionJNI.delete_btConvexInternalShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCollisionShapeData(btCollisionShapeData btcollisionshapedata) {
        CollisionJNI.btConvexInternalShapeData_collisionShapeData_set(this.swigCPtr, this, btCollisionShapeData.getCPtr(btcollisionshapedata), btcollisionshapedata);
    }

    public btCollisionShapeData getCollisionShapeData() {
        long jBtConvexInternalShapeData_collisionShapeData_get = CollisionJNI.btConvexInternalShapeData_collisionShapeData_get(this.swigCPtr, this);
        if (jBtConvexInternalShapeData_collisionShapeData_get == 0) {
            return null;
        }
        return new btCollisionShapeData(jBtConvexInternalShapeData_collisionShapeData_get, false);
    }

    public void setLocalScaling(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btConvexInternalShapeData_localScaling_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLocalScaling() {
        long jBtConvexInternalShapeData_localScaling_get = CollisionJNI.btConvexInternalShapeData_localScaling_get(this.swigCPtr, this);
        if (jBtConvexInternalShapeData_localScaling_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtConvexInternalShapeData_localScaling_get, false);
    }

    public void setImplicitShapeDimensions(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btConvexInternalShapeData_implicitShapeDimensions_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getImplicitShapeDimensions() {
        long jBtConvexInternalShapeData_implicitShapeDimensions_get = CollisionJNI.btConvexInternalShapeData_implicitShapeDimensions_get(this.swigCPtr, this);
        if (jBtConvexInternalShapeData_implicitShapeDimensions_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtConvexInternalShapeData_implicitShapeDimensions_get, false);
    }

    public void setCollisionMargin(float f) {
        CollisionJNI.btConvexInternalShapeData_collisionMargin_set(this.swigCPtr, this, f);
    }

    public float getCollisionMargin() {
        return CollisionJNI.btConvexInternalShapeData_collisionMargin_get(this.swigCPtr, this);
    }

    public void setPadding(int i) {
        CollisionJNI.btConvexInternalShapeData_padding_set(this.swigCPtr, this, i);
    }

    public int getPadding() {
        return CollisionJNI.btConvexInternalShapeData_padding_get(this.swigCPtr, this);
    }

    public btConvexInternalShapeData() {
        this(CollisionJNI.new_btConvexInternalShapeData(), true);
    }
}
