package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class SoftBodyTetraData extends BulletBase {
    private long swigCPtr;

    protected SoftBodyTetraData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public SoftBodyTetraData(long j, boolean z) {
        this("SoftBodyTetraData", j, z);
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

    public static long getCPtr(SoftBodyTetraData softBodyTetraData) {
        if (softBodyTetraData == null) {
            return 0L;
        }
        return softBodyTetraData.swigCPtr;
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
                SoftbodyJNI.delete_SoftBodyTetraData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setC0(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyTetraData_c0_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getC0() {
        long jSoftBodyTetraData_c0_get = SoftbodyJNI.SoftBodyTetraData_c0_get(this.swigCPtr, this);
        if (jSoftBodyTetraData_c0_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyTetraData_c0_get, false);
    }

    public void setMaterial(SoftBodyMaterialData softBodyMaterialData) {
        SoftbodyJNI.SoftBodyTetraData_material_set(this.swigCPtr, this, SoftBodyMaterialData.getCPtr(softBodyMaterialData), softBodyMaterialData);
    }

    public SoftBodyMaterialData getMaterial() {
        long jSoftBodyTetraData_material_get = SoftbodyJNI.SoftBodyTetraData_material_get(this.swigCPtr, this);
        if (jSoftBodyTetraData_material_get == 0) {
            return null;
        }
        return new SoftBodyMaterialData(jSoftBodyTetraData_material_get, false);
    }

    public void setNodeIndices(int[] iArr) {
        SoftbodyJNI.SoftBodyTetraData_nodeIndices_set(this.swigCPtr, this, iArr);
    }

    public int[] getNodeIndices() {
        return SoftbodyJNI.SoftBodyTetraData_nodeIndices_get(this.swigCPtr, this);
    }

    public void setRestVolume(float f) {
        SoftbodyJNI.SoftBodyTetraData_restVolume_set(this.swigCPtr, this, f);
    }

    public float getRestVolume() {
        return SoftbodyJNI.SoftBodyTetraData_restVolume_get(this.swigCPtr, this);
    }

    public void setC1(float f) {
        SoftbodyJNI.SoftBodyTetraData_c1_set(this.swigCPtr, this, f);
    }

    public float getC1() {
        return SoftbodyJNI.SoftBodyTetraData_c1_get(this.swigCPtr, this);
    }

    public void setC2(float f) {
        SoftbodyJNI.SoftBodyTetraData_c2_set(this.swigCPtr, this, f);
    }

    public float getC2() {
        return SoftbodyJNI.SoftBodyTetraData_c2_get(this.swigCPtr, this);
    }

    public void setPad(int i) {
        SoftbodyJNI.SoftBodyTetraData_pad_set(this.swigCPtr, this, i);
    }

    public int getPad() {
        return SoftbodyJNI.SoftBodyTetraData_pad_get(this.swigCPtr, this);
    }

    public SoftBodyTetraData() {
        this(SoftbodyJNI.new_SoftBodyTetraData(), true);
    }
}
