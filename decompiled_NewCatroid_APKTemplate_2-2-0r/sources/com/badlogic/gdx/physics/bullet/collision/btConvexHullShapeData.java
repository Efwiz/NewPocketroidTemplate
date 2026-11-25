package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexHullShapeData extends BulletBase {
    private long swigCPtr;

    protected btConvexHullShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConvexHullShapeData(long j, boolean z) {
        this("btConvexHullShapeData", j, z);
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

    public static long getCPtr(btConvexHullShapeData btconvexhullshapedata) {
        if (btconvexhullshapedata == null) {
            return 0L;
        }
        return btconvexhullshapedata.swigCPtr;
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
                CollisionJNI.delete_btConvexHullShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setConvexInternalShapeData(btConvexInternalShapeData btconvexinternalshapedata) {
        CollisionJNI.btConvexHullShapeData_convexInternalShapeData_set(this.swigCPtr, this, btConvexInternalShapeData.getCPtr(btconvexinternalshapedata), btconvexinternalshapedata);
    }

    public btConvexInternalShapeData getConvexInternalShapeData() {
        long jBtConvexHullShapeData_convexInternalShapeData_get = CollisionJNI.btConvexHullShapeData_convexInternalShapeData_get(this.swigCPtr, this);
        if (jBtConvexHullShapeData_convexInternalShapeData_get == 0) {
            return null;
        }
        return new btConvexInternalShapeData(jBtConvexHullShapeData_convexInternalShapeData_get, false);
    }

    public void setUnscaledPointsFloatPtr(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btConvexHullShapeData_unscaledPointsFloatPtr_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getUnscaledPointsFloatPtr() {
        long jBtConvexHullShapeData_unscaledPointsFloatPtr_get = CollisionJNI.btConvexHullShapeData_unscaledPointsFloatPtr_get(this.swigCPtr, this);
        if (jBtConvexHullShapeData_unscaledPointsFloatPtr_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtConvexHullShapeData_unscaledPointsFloatPtr_get, false);
    }

    public void setUnscaledPointsDoublePtr(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btConvexHullShapeData_unscaledPointsDoublePtr_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getUnscaledPointsDoublePtr() {
        long jBtConvexHullShapeData_unscaledPointsDoublePtr_get = CollisionJNI.btConvexHullShapeData_unscaledPointsDoublePtr_get(this.swigCPtr, this);
        if (jBtConvexHullShapeData_unscaledPointsDoublePtr_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtConvexHullShapeData_unscaledPointsDoublePtr_get, false);
    }

    public void setNumUnscaledPoints(int i) {
        CollisionJNI.btConvexHullShapeData_numUnscaledPoints_set(this.swigCPtr, this, i);
    }

    public int getNumUnscaledPoints() {
        return CollisionJNI.btConvexHullShapeData_numUnscaledPoints_get(this.swigCPtr, this);
    }

    public void setPadding3(String str) {
        CollisionJNI.btConvexHullShapeData_padding3_set(this.swigCPtr, this, str);
    }

    public String getPadding3() {
        return CollisionJNI.btConvexHullShapeData_padding3_get(this.swigCPtr, this);
    }

    public btConvexHullShapeData() {
        this(CollisionJNI.new_btConvexHullShapeData(), true);
    }
}
