package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class LocalShapeInfo extends BulletBase {
    private long swigCPtr;

    protected LocalShapeInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public LocalShapeInfo(long j, boolean z) {
        this("LocalShapeInfo", j, z);
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

    public static long getCPtr(LocalShapeInfo localShapeInfo) {
        if (localShapeInfo == null) {
            return 0L;
        }
        return localShapeInfo.swigCPtr;
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
                CollisionJNI.delete_LocalShapeInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setShapePart(int i) {
        CollisionJNI.LocalShapeInfo_shapePart_set(this.swigCPtr, this, i);
    }

    public int getShapePart() {
        return CollisionJNI.LocalShapeInfo_shapePart_get(this.swigCPtr, this);
    }

    public void setTriangleIndex(int i) {
        CollisionJNI.LocalShapeInfo_triangleIndex_set(this.swigCPtr, this, i);
    }

    public int getTriangleIndex() {
        return CollisionJNI.LocalShapeInfo_triangleIndex_get(this.swigCPtr, this);
    }

    public LocalShapeInfo() {
        this(CollisionJNI.new_LocalShapeInfo(), true);
    }
}
