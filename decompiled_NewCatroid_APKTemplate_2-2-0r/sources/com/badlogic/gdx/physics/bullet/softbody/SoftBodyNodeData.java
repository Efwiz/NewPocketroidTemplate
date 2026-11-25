package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class SoftBodyNodeData extends BulletBase {
    private long swigCPtr;

    protected SoftBodyNodeData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public SoftBodyNodeData(long j, boolean z) {
        this("SoftBodyNodeData", j, z);
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

    public static long getCPtr(SoftBodyNodeData softBodyNodeData) {
        if (softBodyNodeData == null) {
            return 0L;
        }
        return softBodyNodeData.swigCPtr;
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
                SoftbodyJNI.delete_SoftBodyNodeData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setMaterial(SoftBodyMaterialData softBodyMaterialData) {
        SoftbodyJNI.SoftBodyNodeData_material_set(this.swigCPtr, this, SoftBodyMaterialData.getCPtr(softBodyMaterialData), softBodyMaterialData);
    }

    public SoftBodyMaterialData getMaterial() {
        long jSoftBodyNodeData_material_get = SoftbodyJNI.SoftBodyNodeData_material_get(this.swigCPtr, this);
        if (jSoftBodyNodeData_material_get == 0) {
            return null;
        }
        return new SoftBodyMaterialData(jSoftBodyNodeData_material_get, false);
    }

    public void setPosition(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyNodeData_position_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getPosition() {
        long jSoftBodyNodeData_position_get = SoftbodyJNI.SoftBodyNodeData_position_get(this.swigCPtr, this);
        if (jSoftBodyNodeData_position_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyNodeData_position_get, false);
    }

    public void setPreviousPosition(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyNodeData_previousPosition_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getPreviousPosition() {
        long jSoftBodyNodeData_previousPosition_get = SoftbodyJNI.SoftBodyNodeData_previousPosition_get(this.swigCPtr, this);
        if (jSoftBodyNodeData_previousPosition_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyNodeData_previousPosition_get, false);
    }

    public void setVelocity(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyNodeData_velocity_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getVelocity() {
        long jSoftBodyNodeData_velocity_get = SoftbodyJNI.SoftBodyNodeData_velocity_get(this.swigCPtr, this);
        if (jSoftBodyNodeData_velocity_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyNodeData_velocity_get, false);
    }

    public void setAccumulatedForce(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyNodeData_accumulatedForce_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAccumulatedForce() {
        long jSoftBodyNodeData_accumulatedForce_get = SoftbodyJNI.SoftBodyNodeData_accumulatedForce_get(this.swigCPtr, this);
        if (jSoftBodyNodeData_accumulatedForce_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyNodeData_accumulatedForce_get, false);
    }

    public void setNormal(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyNodeData_normal_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getNormal() {
        long jSoftBodyNodeData_normal_get = SoftbodyJNI.SoftBodyNodeData_normal_get(this.swigCPtr, this);
        if (jSoftBodyNodeData_normal_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyNodeData_normal_get, false);
    }

    public void setInverseMass(float f) {
        SoftbodyJNI.SoftBodyNodeData_inverseMass_set(this.swigCPtr, this, f);
    }

    public float getInverseMass() {
        return SoftbodyJNI.SoftBodyNodeData_inverseMass_get(this.swigCPtr, this);
    }

    public void setArea(float f) {
        SoftbodyJNI.SoftBodyNodeData_area_set(this.swigCPtr, this, f);
    }

    public float getArea() {
        return SoftbodyJNI.SoftBodyNodeData_area_get(this.swigCPtr, this);
    }

    public void setAttach(int i) {
        SoftbodyJNI.SoftBodyNodeData_attach_set(this.swigCPtr, this, i);
    }

    public int getAttach() {
        return SoftbodyJNI.SoftBodyNodeData_attach_get(this.swigCPtr, this);
    }

    public void setPad(int i) {
        SoftbodyJNI.SoftBodyNodeData_pad_set(this.swigCPtr, this, i);
    }

    public int getPad() {
        return SoftbodyJNI.SoftBodyNodeData_pad_get(this.swigCPtr, this);
    }

    public SoftBodyNodeData() {
        this(SoftbodyJNI.new_SoftBodyNodeData(), true);
    }
}
