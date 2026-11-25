package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btCapsuleShapeData extends BulletBase {
    private long swigCPtr;

    protected btCapsuleShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCapsuleShapeData(long j, boolean z) {
        this("btCapsuleShapeData", j, z);
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

    public static long getCPtr(btCapsuleShapeData btcapsuleshapedata) {
        if (btcapsuleshapedata == null) {
            return 0L;
        }
        return btcapsuleshapedata.swigCPtr;
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
                CollisionJNI.delete_btCapsuleShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setConvexInternalShapeData(btConvexInternalShapeData btconvexinternalshapedata) {
        CollisionJNI.btCapsuleShapeData_convexInternalShapeData_set(this.swigCPtr, this, btConvexInternalShapeData.getCPtr(btconvexinternalshapedata), btconvexinternalshapedata);
    }

    public btConvexInternalShapeData getConvexInternalShapeData() {
        long jBtCapsuleShapeData_convexInternalShapeData_get = CollisionJNI.btCapsuleShapeData_convexInternalShapeData_get(this.swigCPtr, this);
        if (jBtCapsuleShapeData_convexInternalShapeData_get == 0) {
            return null;
        }
        return new btConvexInternalShapeData(jBtCapsuleShapeData_convexInternalShapeData_get, false);
    }

    public void setUpAxis(int i) {
        CollisionJNI.btCapsuleShapeData_upAxis_set(this.swigCPtr, this, i);
    }

    public int getUpAxis() {
        return CollisionJNI.btCapsuleShapeData_upAxis_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btCapsuleShapeData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btCapsuleShapeData_padding_get(this.swigCPtr, this);
    }

    public btCapsuleShapeData() {
        this(CollisionJNI.new_btCapsuleShapeData(), true);
    }
}
