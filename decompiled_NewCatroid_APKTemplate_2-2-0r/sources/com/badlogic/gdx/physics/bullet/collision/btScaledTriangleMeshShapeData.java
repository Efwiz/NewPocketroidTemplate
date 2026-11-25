package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btScaledTriangleMeshShapeData extends BulletBase {
    private long swigCPtr;

    protected btScaledTriangleMeshShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btScaledTriangleMeshShapeData(long j, boolean z) {
        this("btScaledTriangleMeshShapeData", j, z);
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

    public static long getCPtr(btScaledTriangleMeshShapeData btscaledtrianglemeshshapedata) {
        if (btscaledtrianglemeshshapedata == null) {
            return 0L;
        }
        return btscaledtrianglemeshshapedata.swigCPtr;
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
                CollisionJNI.delete_btScaledTriangleMeshShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTrimeshShapeData(btTriangleMeshShapeData bttrianglemeshshapedata) {
        CollisionJNI.btScaledTriangleMeshShapeData_trimeshShapeData_set(this.swigCPtr, this, btTriangleMeshShapeData.getCPtr(bttrianglemeshshapedata), bttrianglemeshshapedata);
    }

    public btTriangleMeshShapeData getTrimeshShapeData() {
        long jBtScaledTriangleMeshShapeData_trimeshShapeData_get = CollisionJNI.btScaledTriangleMeshShapeData_trimeshShapeData_get(this.swigCPtr, this);
        if (jBtScaledTriangleMeshShapeData_trimeshShapeData_get == 0) {
            return null;
        }
        return new btTriangleMeshShapeData(jBtScaledTriangleMeshShapeData_trimeshShapeData_get, false);
    }

    public void setLocalScaling(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btScaledTriangleMeshShapeData_localScaling_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLocalScaling() {
        long jBtScaledTriangleMeshShapeData_localScaling_get = CollisionJNI.btScaledTriangleMeshShapeData_localScaling_get(this.swigCPtr, this);
        if (jBtScaledTriangleMeshShapeData_localScaling_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtScaledTriangleMeshShapeData_localScaling_get, false);
    }

    public btScaledTriangleMeshShapeData() {
        this(CollisionJNI.new_btScaledTriangleMeshShapeData(), true);
    }
}
