package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleInfoData extends BulletBase {
    private long swigCPtr;

    protected btTriangleInfoData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTriangleInfoData(long j, boolean z) {
        this("btTriangleInfoData", j, z);
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

    public static long getCPtr(btTriangleInfoData bttriangleinfodata) {
        if (bttriangleinfodata == null) {
            return 0L;
        }
        return bttriangleinfodata.swigCPtr;
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
                CollisionJNI.delete_btTriangleInfoData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setFlags(int i) {
        CollisionJNI.btTriangleInfoData_flags_set(this.swigCPtr, this, i);
    }

    public int getFlags() {
        return CollisionJNI.btTriangleInfoData_flags_get(this.swigCPtr, this);
    }

    public void setEdgeV0V1Angle(float f) {
        CollisionJNI.btTriangleInfoData_edgeV0V1Angle_set(this.swigCPtr, this, f);
    }

    public float getEdgeV0V1Angle() {
        return CollisionJNI.btTriangleInfoData_edgeV0V1Angle_get(this.swigCPtr, this);
    }

    public void setEdgeV1V2Angle(float f) {
        CollisionJNI.btTriangleInfoData_edgeV1V2Angle_set(this.swigCPtr, this, f);
    }

    public float getEdgeV1V2Angle() {
        return CollisionJNI.btTriangleInfoData_edgeV1V2Angle_get(this.swigCPtr, this);
    }

    public void setEdgeV2V0Angle(float f) {
        CollisionJNI.btTriangleInfoData_edgeV2V0Angle_set(this.swigCPtr, this, f);
    }

    public float getEdgeV2V0Angle() {
        return CollisionJNI.btTriangleInfoData_edgeV2V0Angle_get(this.swigCPtr, this);
    }

    public btTriangleInfoData() {
        this(CollisionJNI.new_btTriangleInfoData(), true);
    }
}
