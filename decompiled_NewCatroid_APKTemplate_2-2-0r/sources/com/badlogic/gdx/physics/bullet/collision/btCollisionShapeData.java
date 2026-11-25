package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionShapeData extends BulletBase {
    private long swigCPtr;

    protected btCollisionShapeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionShapeData(long j, boolean z) {
        this("btCollisionShapeData", j, z);
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

    public static long getCPtr(btCollisionShapeData btcollisionshapedata) {
        if (btcollisionshapedata == null) {
            return 0L;
        }
        return btcollisionshapedata.swigCPtr;
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
                CollisionJNI.delete_btCollisionShapeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setName(String str) {
        CollisionJNI.btCollisionShapeData_name_set(this.swigCPtr, this, str);
    }

    public String getName() {
        return CollisionJNI.btCollisionShapeData_name_get(this.swigCPtr, this);
    }

    public void setShapeType(int i) {
        CollisionJNI.btCollisionShapeData_shapeType_set(this.swigCPtr, this, i);
    }

    public int getShapeType() {
        return CollisionJNI.btCollisionShapeData_shapeType_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btCollisionShapeData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btCollisionShapeData_padding_get(this.swigCPtr, this);
    }

    public btCollisionShapeData() {
        this(CollisionJNI.new_btCollisionShapeData(), true);
    }
}
