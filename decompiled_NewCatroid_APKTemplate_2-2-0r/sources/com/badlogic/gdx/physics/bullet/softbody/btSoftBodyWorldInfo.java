package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBodyWorldInfo extends BulletBase {
    private long swigCPtr;

    protected btSoftBodyWorldInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSoftBodyWorldInfo(long j, boolean z) {
        this("btSoftBodyWorldInfo", j, z);
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

    public static long getCPtr(btSoftBodyWorldInfo btsoftbodyworldinfo) {
        if (btsoftbodyworldinfo == null) {
            return 0L;
        }
        return btsoftbodyworldinfo.swigCPtr;
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
                SoftbodyJNI.delete_btSoftBodyWorldInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setAir_density(float f) {
        SoftbodyJNI.btSoftBodyWorldInfo_air_density_set(this.swigCPtr, this, f);
    }

    public float getAir_density() {
        return SoftbodyJNI.btSoftBodyWorldInfo_air_density_get(this.swigCPtr, this);
    }

    public void setWater_density(float f) {
        SoftbodyJNI.btSoftBodyWorldInfo_water_density_set(this.swigCPtr, this, f);
    }

    public float getWater_density() {
        return SoftbodyJNI.btSoftBodyWorldInfo_water_density_get(this.swigCPtr, this);
    }

    public void setWater_offset(float f) {
        SoftbodyJNI.btSoftBodyWorldInfo_water_offset_set(this.swigCPtr, this, f);
    }

    public float getWater_offset() {
        return SoftbodyJNI.btSoftBodyWorldInfo_water_offset_get(this.swigCPtr, this);
    }

    public void setMaxDisplacement(float f) {
        SoftbodyJNI.btSoftBodyWorldInfo_maxDisplacement_set(this.swigCPtr, this, f);
    }

    public float getMaxDisplacement() {
        return SoftbodyJNI.btSoftBodyWorldInfo_maxDisplacement_get(this.swigCPtr, this);
    }

    public void setWater_normal(btVector3 btvector3) {
        SoftbodyJNI.btSoftBodyWorldInfo_water_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getWater_normal() {
        long jBtSoftBodyWorldInfo_water_normal_get = SoftbodyJNI.btSoftBodyWorldInfo_water_normal_get(this.swigCPtr, this);
        if (jBtSoftBodyWorldInfo_water_normal_get == 0) {
            return null;
        }
        return new btVector3(jBtSoftBodyWorldInfo_water_normal_get, false);
    }

    public void setBroadphase(btBroadphaseInterface btbroadphaseinterface) {
        SoftbodyJNI.btSoftBodyWorldInfo_broadphase_set(this.swigCPtr, this, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface);
    }

    public btBroadphaseInterface getBroadphase() {
        long jBtSoftBodyWorldInfo_broadphase_get = SoftbodyJNI.btSoftBodyWorldInfo_broadphase_get(this.swigCPtr, this);
        if (jBtSoftBodyWorldInfo_broadphase_get == 0) {
            return null;
        }
        return new btBroadphaseInterface(jBtSoftBodyWorldInfo_broadphase_get, false);
    }

    public void setDispatcher(btDispatcher btdispatcher) {
        SoftbodyJNI.btSoftBodyWorldInfo_dispatcher_set(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public btDispatcher getDispatcher() {
        long jBtSoftBodyWorldInfo_dispatcher_get = SoftbodyJNI.btSoftBodyWorldInfo_dispatcher_get(this.swigCPtr, this);
        if (jBtSoftBodyWorldInfo_dispatcher_get == 0) {
            return null;
        }
        return new btDispatcher(jBtSoftBodyWorldInfo_dispatcher_get, false);
    }

    public void setGravity(btVector3 btvector3) {
        SoftbodyJNI.btSoftBodyWorldInfo_gravity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getGravity() {
        long jBtSoftBodyWorldInfo_gravity_get = SoftbodyJNI.btSoftBodyWorldInfo_gravity_get(this.swigCPtr, this);
        if (jBtSoftBodyWorldInfo_gravity_get == 0) {
            return null;
        }
        return new btVector3(jBtSoftBodyWorldInfo_gravity_get, false);
    }

    public void setSparsesdf(btSparseSdf3 btsparsesdf3) {
        SoftbodyJNI.btSoftBodyWorldInfo_sparsesdf_set(this.swigCPtr, this, btSparseSdf3.getCPtr(btsparsesdf3), btsparsesdf3);
    }

    public btSparseSdf3 getSparsesdf() {
        long jBtSoftBodyWorldInfo_sparsesdf_get = SoftbodyJNI.btSoftBodyWorldInfo_sparsesdf_get(this.swigCPtr, this);
        if (jBtSoftBodyWorldInfo_sparsesdf_get == 0) {
            return null;
        }
        return new btSparseSdf3(jBtSoftBodyWorldInfo_sparsesdf_get, false);
    }

    public btSoftBodyWorldInfo() {
        this(SoftbodyJNI.new_btSoftBodyWorldInfo(), true);
    }
}
