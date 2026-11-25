package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class SoftBodyFaceData extends BulletBase {
    private long swigCPtr;

    protected SoftBodyFaceData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public SoftBodyFaceData(long j, boolean z) {
        this("SoftBodyFaceData", j, z);
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

    public static long getCPtr(SoftBodyFaceData softBodyFaceData) {
        if (softBodyFaceData == null) {
            return 0L;
        }
        return softBodyFaceData.swigCPtr;
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
                SoftbodyJNI.delete_SoftBodyFaceData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setNormal(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyFaceData_normal_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getNormal() {
        long jSoftBodyFaceData_normal_get = SoftbodyJNI.SoftBodyFaceData_normal_get(this.swigCPtr, this);
        if (jSoftBodyFaceData_normal_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyFaceData_normal_get, false);
    }

    public void setMaterial(SoftBodyMaterialData softBodyMaterialData) {
        SoftbodyJNI.SoftBodyFaceData_material_set(this.swigCPtr, this, SoftBodyMaterialData.getCPtr(softBodyMaterialData), softBodyMaterialData);
    }

    public SoftBodyMaterialData getMaterial() {
        long jSoftBodyFaceData_material_get = SoftbodyJNI.SoftBodyFaceData_material_get(this.swigCPtr, this);
        if (jSoftBodyFaceData_material_get == 0) {
            return null;
        }
        return new SoftBodyMaterialData(jSoftBodyFaceData_material_get, false);
    }

    public void setNodeIndices(int[] iArr) {
        SoftbodyJNI.SoftBodyFaceData_nodeIndices_set(this.swigCPtr, this, iArr);
    }

    public int[] getNodeIndices() {
        return SoftbodyJNI.SoftBodyFaceData_nodeIndices_get(this.swigCPtr, this);
    }

    public void setRestArea(float f) {
        SoftbodyJNI.SoftBodyFaceData_restArea_set(this.swigCPtr, this, f);
    }

    public float getRestArea() {
        return SoftbodyJNI.SoftBodyFaceData_restArea_get(this.swigCPtr, this);
    }

    public SoftBodyFaceData() {
        this(SoftbodyJNI.new_SoftBodyFaceData(), true);
    }
}
