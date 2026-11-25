package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

/* JADX INFO: loaded from: classes8.dex */
public class btTriIndex extends BulletBase {
    private long swigCPtr;

    protected btTriIndex(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTriIndex(long j, boolean z) {
        this("btTriIndex", j, z);
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

    public static long getCPtr(btTriIndex bttriindex) {
        if (bttriindex == null) {
            return 0L;
        }
        return bttriindex.swigCPtr;
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
                SoftbodyJNI.delete_btTriIndex(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPartIdTriangleIndex(int i) {
        SoftbodyJNI.btTriIndex_PartIdTriangleIndex_set(this.swigCPtr, this, i);
    }

    public int getPartIdTriangleIndex() {
        return SoftbodyJNI.btTriIndex_PartIdTriangleIndex_get(this.swigCPtr, this);
    }

    public void setChildShape(btCollisionShape btcollisionshape) {
        SoftbodyJNI.btTriIndex_childShape_set(this.swigCPtr, this, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    public btCollisionShape getChildShape() {
        long jBtTriIndex_childShape_get = SoftbodyJNI.btTriIndex_childShape_get(this.swigCPtr, this);
        if (jBtTriIndex_childShape_get == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtTriIndex_childShape_get, false);
    }

    public btTriIndex(int i, int i2, btCollisionShape btcollisionshape) {
        this(SoftbodyJNI.new_btTriIndex(i, i2, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape), true);
    }

    public int getTriangleIndex() {
        return SoftbodyJNI.btTriIndex_getTriangleIndex(this.swigCPtr, this);
    }

    public int getPartId() {
        return SoftbodyJNI.btTriIndex_getPartId(this.swigCPtr, this);
    }

    public int getUid() {
        return SoftbodyJNI.btTriIndex_getUid(this.swigCPtr, this);
    }
}
