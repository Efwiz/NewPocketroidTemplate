package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btCompoundShapeData extends BulletBase {
    private long swigCPtr;

    protected btCompoundShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCompoundShapeData(long j, boolean z) {
        this("btCompoundShapeData", j, z);
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

    public static long getCPtr(btCompoundShapeData btcompoundshapedata) {
        if (btcompoundshapedata == null) {
            return 0L;
        }
        return btcompoundshapedata.swigCPtr;
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
                CollisionJNI.delete_btCompoundShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCollisionShapeData(btCollisionShapeData btcollisionshapedata) {
        CollisionJNI.btCompoundShapeData_collisionShapeData_set(this.swigCPtr, this, btCollisionShapeData.getCPtr(btcollisionshapedata), btcollisionshapedata);
    }

    public btCollisionShapeData getCollisionShapeData() {
        long jBtCompoundShapeData_collisionShapeData_get = CollisionJNI.btCompoundShapeData_collisionShapeData_get(this.swigCPtr, this);
        if (jBtCompoundShapeData_collisionShapeData_get == 0) {
            return null;
        }
        return new btCollisionShapeData(jBtCompoundShapeData_collisionShapeData_get, false);
    }

    public void setChildShapePtr(btCompoundShapeChildData btcompoundshapechilddata) {
        CollisionJNI.btCompoundShapeData_childShapePtr_set(this.swigCPtr, this, btCompoundShapeChildData.getCPtr(btcompoundshapechilddata), btcompoundshapechilddata);
    }

    public btCompoundShapeChildData getChildShapePtr() {
        long jBtCompoundShapeData_childShapePtr_get = CollisionJNI.btCompoundShapeData_childShapePtr_get(this.swigCPtr, this);
        if (jBtCompoundShapeData_childShapePtr_get == 0) {
            return null;
        }
        return new btCompoundShapeChildData(jBtCompoundShapeData_childShapePtr_get, false);
    }

    public void setNumChildShapes(int i) {
        CollisionJNI.btCompoundShapeData_numChildShapes_set(this.swigCPtr, this, i);
    }

    public int getNumChildShapes() {
        return CollisionJNI.btCompoundShapeData_numChildShapes_get(this.swigCPtr, this);
    }

    public void setCollisionMargin(float f) {
        CollisionJNI.btCompoundShapeData_collisionMargin_set(this.swigCPtr, this, f);
    }

    public float getCollisionMargin() {
        return CollisionJNI.btCompoundShapeData_collisionMargin_get(this.swigCPtr, this);
    }

    public btCompoundShapeData() {
        this(CollisionJNI.new_btCompoundShapeData(), true);
    }
}
