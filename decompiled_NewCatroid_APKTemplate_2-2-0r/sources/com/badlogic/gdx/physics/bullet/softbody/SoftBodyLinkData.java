package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class SoftBodyLinkData extends BulletBase {
    private long swigCPtr;

    protected SoftBodyLinkData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public SoftBodyLinkData(long j, boolean z) {
        this("SoftBodyLinkData", j, z);
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

    public static long getCPtr(SoftBodyLinkData softBodyLinkData) {
        if (softBodyLinkData == null) {
            return 0L;
        }
        return softBodyLinkData.swigCPtr;
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
                SoftbodyJNI.delete_SoftBodyLinkData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setMaterial(SoftBodyMaterialData softBodyMaterialData) {
        SoftbodyJNI.SoftBodyLinkData_material_set(this.swigCPtr, this, SoftBodyMaterialData.getCPtr(softBodyMaterialData), softBodyMaterialData);
    }

    public SoftBodyMaterialData getMaterial() {
        long jSoftBodyLinkData_material_get = SoftbodyJNI.SoftBodyLinkData_material_get(this.swigCPtr, this);
        if (jSoftBodyLinkData_material_get == 0) {
            return null;
        }
        return new SoftBodyMaterialData(jSoftBodyLinkData_material_get, false);
    }

    public void setNodeIndices(int[] iArr) {
        SoftbodyJNI.SoftBodyLinkData_nodeIndices_set(this.swigCPtr, this, iArr);
    }

    public int[] getNodeIndices() {
        return SoftbodyJNI.SoftBodyLinkData_nodeIndices_get(this.swigCPtr, this);
    }

    public void setRestLength(float f) {
        SoftbodyJNI.SoftBodyLinkData_restLength_set(this.swigCPtr, this, f);
    }

    public float getRestLength() {
        return SoftbodyJNI.SoftBodyLinkData_restLength_get(this.swigCPtr, this);
    }

    public void setBbending(int i) {
        SoftbodyJNI.SoftBodyLinkData_bbending_set(this.swigCPtr, this, i);
    }

    public int getBbending() {
        return SoftbodyJNI.SoftBodyLinkData_bbending_get(this.swigCPtr, this);
    }

    public SoftBodyLinkData() {
        this(SoftbodyJNI.new_SoftBodyLinkData(), true);
    }
}
