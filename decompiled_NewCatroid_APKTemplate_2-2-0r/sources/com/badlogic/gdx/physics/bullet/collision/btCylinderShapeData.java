package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btCylinderShapeData extends BulletBase {
    private long swigCPtr;

    protected btCylinderShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCylinderShapeData(long j, boolean z) {
        this("btCylinderShapeData", j, z);
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

    public static long getCPtr(btCylinderShapeData btcylindershapedata) {
        if (btcylindershapedata == null) {
            return 0L;
        }
        return btcylindershapedata.swigCPtr;
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
                CollisionJNI.delete_btCylinderShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setConvexInternalShapeData(btConvexInternalShapeData btconvexinternalshapedata) {
        CollisionJNI.btCylinderShapeData_convexInternalShapeData_set(this.swigCPtr, this, btConvexInternalShapeData.getCPtr(btconvexinternalshapedata), btconvexinternalshapedata);
    }

    public btConvexInternalShapeData getConvexInternalShapeData() {
        long jBtCylinderShapeData_convexInternalShapeData_get = CollisionJNI.btCylinderShapeData_convexInternalShapeData_get(this.swigCPtr, this);
        if (jBtCylinderShapeData_convexInternalShapeData_get == 0) {
            return null;
        }
        return new btConvexInternalShapeData(jBtCylinderShapeData_convexInternalShapeData_get, false);
    }

    public void setUpAxis(int i) {
        CollisionJNI.btCylinderShapeData_upAxis_set(this.swigCPtr, this, i);
    }

    public int getUpAxis() {
        return CollisionJNI.btCylinderShapeData_upAxis_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btCylinderShapeData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btCylinderShapeData_padding_get(this.swigCPtr, this);
    }

    public btCylinderShapeData() {
        this(CollisionJNI.new_btCylinderShapeData(), true);
    }
}
