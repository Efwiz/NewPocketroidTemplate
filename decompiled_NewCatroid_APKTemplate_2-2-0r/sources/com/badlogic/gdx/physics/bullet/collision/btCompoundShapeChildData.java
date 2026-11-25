package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btCompoundShapeChildData extends BulletBase {
    private long swigCPtr;

    protected btCompoundShapeChildData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCompoundShapeChildData(long j, boolean z) {
        this("btCompoundShapeChildData", j, z);
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

    public static long getCPtr(btCompoundShapeChildData btcompoundshapechilddata) {
        if (btcompoundshapechilddata == null) {
            return 0L;
        }
        return btcompoundshapechilddata.swigCPtr;
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
                CollisionJNI.delete_btCompoundShapeChildData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTransform(btTransformFloatData bttransformfloatdata) {
        CollisionJNI.btCompoundShapeChildData_transform_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getTransform() {
        long jBtCompoundShapeChildData_transform_get = CollisionJNI.btCompoundShapeChildData_transform_get(this.swigCPtr, this);
        if (jBtCompoundShapeChildData_transform_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtCompoundShapeChildData_transform_get, false);
    }

    public void setChildShape(btCollisionShapeData btcollisionshapedata) {
        CollisionJNI.btCompoundShapeChildData_childShape_set(this.swigCPtr, this, btCollisionShapeData.getCPtr(btcollisionshapedata), btcollisionshapedata);
    }

    public btCollisionShapeData getChildShape() {
        long jBtCompoundShapeChildData_childShape_get = CollisionJNI.btCompoundShapeChildData_childShape_get(this.swigCPtr, this);
        if (jBtCompoundShapeChildData_childShape_get == 0) {
            return null;
        }
        return new btCollisionShapeData(jBtCompoundShapeChildData_childShape_get, false);
    }

    public void setChildShapeType(int i) {
        CollisionJNI.btCompoundShapeChildData_childShapeType_set(this.swigCPtr, this, i);
    }

    public int getChildShapeType() {
        return CollisionJNI.btCompoundShapeChildData_childShapeType_get(this.swigCPtr, this);
    }

    public void setChildMargin(float f) {
        CollisionJNI.btCompoundShapeChildData_childMargin_set(this.swigCPtr, this, f);
    }

    public float getChildMargin() {
        return CollisionJNI.btCompoundShapeChildData_childMargin_get(this.swigCPtr, this);
    }

    public btCompoundShapeChildData() {
        this(CollisionJNI.new_btCompoundShapeChildData(), true);
    }
}
