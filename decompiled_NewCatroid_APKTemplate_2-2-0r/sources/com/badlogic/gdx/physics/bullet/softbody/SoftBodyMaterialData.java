package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class SoftBodyMaterialData extends BulletBase {
    private long swigCPtr;

    protected SoftBodyMaterialData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public SoftBodyMaterialData(long j, boolean z) {
        this("SoftBodyMaterialData", j, z);
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

    public static long getCPtr(SoftBodyMaterialData softBodyMaterialData) {
        if (softBodyMaterialData == null) {
            return 0L;
        }
        return softBodyMaterialData.swigCPtr;
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
                SoftbodyJNI.delete_SoftBodyMaterialData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setLinearStiffness(float f) {
        SoftbodyJNI.SoftBodyMaterialData_linearStiffness_set(this.swigCPtr, this, f);
    }

    public float getLinearStiffness() {
        return SoftbodyJNI.SoftBodyMaterialData_linearStiffness_get(this.swigCPtr, this);
    }

    public void setAngularStiffness(float f) {
        SoftbodyJNI.SoftBodyMaterialData_angularStiffness_set(this.swigCPtr, this, f);
    }

    public float getAngularStiffness() {
        return SoftbodyJNI.SoftBodyMaterialData_angularStiffness_get(this.swigCPtr, this);
    }

    public void setVolumeStiffness(float f) {
        SoftbodyJNI.SoftBodyMaterialData_volumeStiffness_set(this.swigCPtr, this, f);
    }

    public float getVolumeStiffness() {
        return SoftbodyJNI.SoftBodyMaterialData_volumeStiffness_get(this.swigCPtr, this);
    }

    public void setFlags(int i) {
        SoftbodyJNI.SoftBodyMaterialData_flags_set(this.swigCPtr, this, i);
    }

    public int getFlags() {
        return SoftbodyJNI.SoftBodyMaterialData_flags_get(this.swigCPtr, this);
    }

    public SoftBodyMaterialData() {
        this(SoftbodyJNI.new_SoftBodyMaterialData(), true);
    }
}
