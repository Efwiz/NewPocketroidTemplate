package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btConeShapeData extends BulletBase {
    private long swigCPtr;

    protected btConeShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConeShapeData(long j, boolean z) {
        this("btConeShapeData", j, z);
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

    public static long getCPtr(btConeShapeData btconeshapedata) {
        if (btconeshapedata == null) {
            return 0L;
        }
        return btconeshapedata.swigCPtr;
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
                CollisionJNI.delete_btConeShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setConvexInternalShapeData(btConvexInternalShapeData btconvexinternalshapedata) {
        CollisionJNI.btConeShapeData_convexInternalShapeData_set(this.swigCPtr, this, btConvexInternalShapeData.getCPtr(btconvexinternalshapedata), btconvexinternalshapedata);
    }

    public btConvexInternalShapeData getConvexInternalShapeData() {
        long jBtConeShapeData_convexInternalShapeData_get = CollisionJNI.btConeShapeData_convexInternalShapeData_get(this.swigCPtr, this);
        if (jBtConeShapeData_convexInternalShapeData_get == 0) {
            return null;
        }
        return new btConvexInternalShapeData(jBtConeShapeData_convexInternalShapeData_get, false);
    }

    public void setUpIndex(int i) {
        CollisionJNI.btConeShapeData_upIndex_set(this.swigCPtr, this, i);
    }

    public int getUpIndex() {
        return CollisionJNI.btConeShapeData_upIndex_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btConeShapeData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btConeShapeData_padding_get(this.swigCPtr, this);
    }

    public btConeShapeData() {
        this(CollisionJNI.new_btConeShapeData(), true);
    }
}
