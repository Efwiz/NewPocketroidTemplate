package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class SoftBodyConfigData extends BulletBase {
    private long swigCPtr;

    protected SoftBodyConfigData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public SoftBodyConfigData(long j, boolean z) {
        this("SoftBodyConfigData", j, z);
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

    public static long getCPtr(SoftBodyConfigData softBodyConfigData) {
        if (softBodyConfigData == null) {
            return 0L;
        }
        return softBodyConfigData.swigCPtr;
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
                SoftbodyJNI.delete_SoftBodyConfigData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setAeroModel(int i) {
        SoftbodyJNI.SoftBodyConfigData_aeroModel_set(this.swigCPtr, this, i);
    }

    public int getAeroModel() {
        return SoftbodyJNI.SoftBodyConfigData_aeroModel_get(this.swigCPtr, this);
    }

    public void setBaumgarte(float f) {
        SoftbodyJNI.SoftBodyConfigData_baumgarte_set(this.swigCPtr, this, f);
    }

    public float getBaumgarte() {
        return SoftbodyJNI.SoftBodyConfigData_baumgarte_get(this.swigCPtr, this);
    }

    public void setDamping(float f) {
        SoftbodyJNI.SoftBodyConfigData_damping_set(this.swigCPtr, this, f);
    }

    public float getDamping() {
        return SoftbodyJNI.SoftBodyConfigData_damping_get(this.swigCPtr, this);
    }

    public void setDrag(float f) {
        SoftbodyJNI.SoftBodyConfigData_drag_set(this.swigCPtr, this, f);
    }

    public float getDrag() {
        return SoftbodyJNI.SoftBodyConfigData_drag_get(this.swigCPtr, this);
    }

    public void setLift(float f) {
        SoftbodyJNI.SoftBodyConfigData_lift_set(this.swigCPtr, this, f);
    }

    public float getLift() {
        return SoftbodyJNI.SoftBodyConfigData_lift_get(this.swigCPtr, this);
    }

    public void setPressure(float f) {
        SoftbodyJNI.SoftBodyConfigData_pressure_set(this.swigCPtr, this, f);
    }

    public float getPressure() {
        return SoftbodyJNI.SoftBodyConfigData_pressure_get(this.swigCPtr, this);
    }

    public void setVolume(float f) {
        SoftbodyJNI.SoftBodyConfigData_volume_set(this.swigCPtr, this, f);
    }

    public float getVolume() {
        return SoftbodyJNI.SoftBodyConfigData_volume_get(this.swigCPtr, this);
    }

    public void setDynamicFriction(float f) {
        SoftbodyJNI.SoftBodyConfigData_dynamicFriction_set(this.swigCPtr, this, f);
    }

    public float getDynamicFriction() {
        return SoftbodyJNI.SoftBodyConfigData_dynamicFriction_get(this.swigCPtr, this);
    }

    public void setPoseMatch(float f) {
        SoftbodyJNI.SoftBodyConfigData_poseMatch_set(this.swigCPtr, this, f);
    }

    public float getPoseMatch() {
        return SoftbodyJNI.SoftBodyConfigData_poseMatch_get(this.swigCPtr, this);
    }

    public void setRigidContactHardness(float f) {
        SoftbodyJNI.SoftBodyConfigData_rigidContactHardness_set(this.swigCPtr, this, f);
    }

    public float getRigidContactHardness() {
        return SoftbodyJNI.SoftBodyConfigData_rigidContactHardness_get(this.swigCPtr, this);
    }

    public void setKineticContactHardness(float f) {
        SoftbodyJNI.SoftBodyConfigData_kineticContactHardness_set(this.swigCPtr, this, f);
    }

    public float getKineticContactHardness() {
        return SoftbodyJNI.SoftBodyConfigData_kineticContactHardness_get(this.swigCPtr, this);
    }

    public void setSoftContactHardness(float f) {
        SoftbodyJNI.SoftBodyConfigData_softContactHardness_set(this.swigCPtr, this, f);
    }

    public float getSoftContactHardness() {
        return SoftbodyJNI.SoftBodyConfigData_softContactHardness_get(this.swigCPtr, this);
    }

    public void setAnchorHardness(float f) {
        SoftbodyJNI.SoftBodyConfigData_anchorHardness_set(this.swigCPtr, this, f);
    }

    public float getAnchorHardness() {
        return SoftbodyJNI.SoftBodyConfigData_anchorHardness_get(this.swigCPtr, this);
    }

    public void setSoftRigidClusterHardness(float f) {
        SoftbodyJNI.SoftBodyConfigData_softRigidClusterHardness_set(this.swigCPtr, this, f);
    }

    public float getSoftRigidClusterHardness() {
        return SoftbodyJNI.SoftBodyConfigData_softRigidClusterHardness_get(this.swigCPtr, this);
    }

    public void setSoftKineticClusterHardness(float f) {
        SoftbodyJNI.SoftBodyConfigData_softKineticClusterHardness_set(this.swigCPtr, this, f);
    }

    public float getSoftKineticClusterHardness() {
        return SoftbodyJNI.SoftBodyConfigData_softKineticClusterHardness_get(this.swigCPtr, this);
    }

    public void setSoftSoftClusterHardness(float f) {
        SoftbodyJNI.SoftBodyConfigData_softSoftClusterHardness_set(this.swigCPtr, this, f);
    }

    public float getSoftSoftClusterHardness() {
        return SoftbodyJNI.SoftBodyConfigData_softSoftClusterHardness_get(this.swigCPtr, this);
    }

    public void setSoftRigidClusterImpulseSplit(float f) {
        SoftbodyJNI.SoftBodyConfigData_softRigidClusterImpulseSplit_set(this.swigCPtr, this, f);
    }

    public float getSoftRigidClusterImpulseSplit() {
        return SoftbodyJNI.SoftBodyConfigData_softRigidClusterImpulseSplit_get(this.swigCPtr, this);
    }

    public void setSoftKineticClusterImpulseSplit(float f) {
        SoftbodyJNI.SoftBodyConfigData_softKineticClusterImpulseSplit_set(this.swigCPtr, this, f);
    }

    public float getSoftKineticClusterImpulseSplit() {
        return SoftbodyJNI.SoftBodyConfigData_softKineticClusterImpulseSplit_get(this.swigCPtr, this);
    }

    public void setSoftSoftClusterImpulseSplit(float f) {
        SoftbodyJNI.SoftBodyConfigData_softSoftClusterImpulseSplit_set(this.swigCPtr, this, f);
    }

    public float getSoftSoftClusterImpulseSplit() {
        return SoftbodyJNI.SoftBodyConfigData_softSoftClusterImpulseSplit_get(this.swigCPtr, this);
    }

    public void setMaxVolume(float f) {
        SoftbodyJNI.SoftBodyConfigData_maxVolume_set(this.swigCPtr, this, f);
    }

    public float getMaxVolume() {
        return SoftbodyJNI.SoftBodyConfigData_maxVolume_get(this.swigCPtr, this);
    }

    public void setTimeScale(float f) {
        SoftbodyJNI.SoftBodyConfigData_timeScale_set(this.swigCPtr, this, f);
    }

    public float getTimeScale() {
        return SoftbodyJNI.SoftBodyConfigData_timeScale_get(this.swigCPtr, this);
    }

    public void setVelocityIterations(int i) {
        SoftbodyJNI.SoftBodyConfigData_velocityIterations_set(this.swigCPtr, this, i);
    }

    public int getVelocityIterations() {
        return SoftbodyJNI.SoftBodyConfigData_velocityIterations_get(this.swigCPtr, this);
    }

    public void setPositionIterations(int i) {
        SoftbodyJNI.SoftBodyConfigData_positionIterations_set(this.swigCPtr, this, i);
    }

    public int getPositionIterations() {
        return SoftbodyJNI.SoftBodyConfigData_positionIterations_get(this.swigCPtr, this);
    }

    public void setDriftIterations(int i) {
        SoftbodyJNI.SoftBodyConfigData_driftIterations_set(this.swigCPtr, this, i);
    }

    public int getDriftIterations() {
        return SoftbodyJNI.SoftBodyConfigData_driftIterations_get(this.swigCPtr, this);
    }

    public void setClusterIterations(int i) {
        SoftbodyJNI.SoftBodyConfigData_clusterIterations_set(this.swigCPtr, this, i);
    }

    public int getClusterIterations() {
        return SoftbodyJNI.SoftBodyConfigData_clusterIterations_get(this.swigCPtr, this);
    }

    public void setCollisionFlags(int i) {
        SoftbodyJNI.SoftBodyConfigData_collisionFlags_set(this.swigCPtr, this, i);
    }

    public int getCollisionFlags() {
        return SoftbodyJNI.SoftBodyConfigData_collisionFlags_get(this.swigCPtr, this);
    }

    public SoftBodyConfigData() {
        this(SoftbodyJNI.new_SoftBodyConfigData(), true);
    }
}
