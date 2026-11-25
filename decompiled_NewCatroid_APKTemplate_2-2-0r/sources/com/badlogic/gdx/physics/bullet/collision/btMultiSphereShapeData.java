package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiSphereShapeData extends BulletBase {
    private long swigCPtr;

    protected btMultiSphereShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiSphereShapeData(long j, boolean z) {
        this("btMultiSphereShapeData", j, z);
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

    public static long getCPtr(btMultiSphereShapeData btmultisphereshapedata) {
        if (btmultisphereshapedata == null) {
            return 0L;
        }
        return btmultisphereshapedata.swigCPtr;
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
                CollisionJNI.delete_btMultiSphereShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setConvexInternalShapeData(btConvexInternalShapeData btconvexinternalshapedata) {
        CollisionJNI.btMultiSphereShapeData_convexInternalShapeData_set(this.swigCPtr, this, btConvexInternalShapeData.getCPtr(btconvexinternalshapedata), btconvexinternalshapedata);
    }

    public btConvexInternalShapeData getConvexInternalShapeData() {
        long jBtMultiSphereShapeData_convexInternalShapeData_get = CollisionJNI.btMultiSphereShapeData_convexInternalShapeData_get(this.swigCPtr, this);
        if (jBtMultiSphereShapeData_convexInternalShapeData_get == 0) {
            return null;
        }
        return new btConvexInternalShapeData(jBtMultiSphereShapeData_convexInternalShapeData_get, false);
    }

    public void setLocalPositionArrayPtr(btPositionAndRadius btpositionandradius) {
        CollisionJNI.btMultiSphereShapeData_localPositionArrayPtr_set(this.swigCPtr, this, btPositionAndRadius.getCPtr(btpositionandradius), btpositionandradius);
    }

    public btPositionAndRadius getLocalPositionArrayPtr() {
        long jBtMultiSphereShapeData_localPositionArrayPtr_get = CollisionJNI.btMultiSphereShapeData_localPositionArrayPtr_get(this.swigCPtr, this);
        if (jBtMultiSphereShapeData_localPositionArrayPtr_get == 0) {
            return null;
        }
        return new btPositionAndRadius(jBtMultiSphereShapeData_localPositionArrayPtr_get, false);
    }

    public void setLocalPositionArraySize(int i) {
        CollisionJNI.btMultiSphereShapeData_localPositionArraySize_set(this.swigCPtr, this, i);
    }

    public int getLocalPositionArraySize() {
        return CollisionJNI.btMultiSphereShapeData_localPositionArraySize_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btMultiSphereShapeData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btMultiSphereShapeData_padding_get(this.swigCPtr, this);
    }

    public btMultiSphereShapeData() {
        this(CollisionJNI.new_btMultiSphereShapeData(), true);
    }
}
