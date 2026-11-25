package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBodyJointData extends BulletBase {
    private long swigCPtr;

    protected btSoftBodyJointData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSoftBodyJointData(long j, boolean z) {
        this("btSoftBodyJointData", j, z);
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

    public static long getCPtr(btSoftBodyJointData btsoftbodyjointdata) {
        if (btsoftbodyjointdata == null) {
            return 0L;
        }
        return btsoftbodyjointdata.swigCPtr;
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
                SoftbodyJNI.delete_btSoftBodyJointData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBodyA(long j) {
        SoftbodyJNI.btSoftBodyJointData_bodyA_set(this.swigCPtr, this, j);
    }

    public long getBodyA() {
        return SoftbodyJNI.btSoftBodyJointData_bodyA_get(this.swigCPtr, this);
    }

    public void setBodyB(long j) {
        SoftbodyJNI.btSoftBodyJointData_bodyB_set(this.swigCPtr, this, j);
    }

    public long getBodyB() {
        return SoftbodyJNI.btSoftBodyJointData_bodyB_get(this.swigCPtr, this);
    }

    public void setRefs(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.btSoftBodyJointData_refs_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getRefs() {
        long jBtSoftBodyJointData_refs_get = SoftbodyJNI.btSoftBodyJointData_refs_get(this.swigCPtr, this);
        if (jBtSoftBodyJointData_refs_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtSoftBodyJointData_refs_get, false);
    }

    public void setCfm(float f) {
        SoftbodyJNI.btSoftBodyJointData_cfm_set(this.swigCPtr, this, f);
    }

    public float getCfm() {
        return SoftbodyJNI.btSoftBodyJointData_cfm_get(this.swigCPtr, this);
    }

    public void setErp(float f) {
        SoftbodyJNI.btSoftBodyJointData_erp_set(this.swigCPtr, this, f);
    }

    public float getErp() {
        return SoftbodyJNI.btSoftBodyJointData_erp_get(this.swigCPtr, this);
    }

    public void setSplit(float f) {
        SoftbodyJNI.btSoftBodyJointData_split_set(this.swigCPtr, this, f);
    }

    public float getSplit() {
        return SoftbodyJNI.btSoftBodyJointData_split_get(this.swigCPtr, this);
    }

    public void setDelete(int i) {
        SoftbodyJNI.btSoftBodyJointData_delete_set(this.swigCPtr, this, i);
    }

    public int getDelete() {
        return SoftbodyJNI.btSoftBodyJointData_delete_get(this.swigCPtr, this);
    }

    public void setRelPosition(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.btSoftBodyJointData_relPosition_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getRelPosition() {
        long jBtSoftBodyJointData_relPosition_get = SoftbodyJNI.btSoftBodyJointData_relPosition_get(this.swigCPtr, this);
        if (jBtSoftBodyJointData_relPosition_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtSoftBodyJointData_relPosition_get, false);
    }

    public void setBodyAtype(int i) {
        SoftbodyJNI.btSoftBodyJointData_bodyAtype_set(this.swigCPtr, this, i);
    }

    public int getBodyAtype() {
        return SoftbodyJNI.btSoftBodyJointData_bodyAtype_get(this.swigCPtr, this);
    }

    public void setBodyBtype(int i) {
        SoftbodyJNI.btSoftBodyJointData_bodyBtype_set(this.swigCPtr, this, i);
    }

    public int getBodyBtype() {
        return SoftbodyJNI.btSoftBodyJointData_bodyBtype_get(this.swigCPtr, this);
    }

    public void setJointType(int i) {
        SoftbodyJNI.btSoftBodyJointData_jointType_set(this.swigCPtr, this, i);
    }

    public int getJointType() {
        return SoftbodyJNI.btSoftBodyJointData_jointType_get(this.swigCPtr, this);
    }

    public void setPad(int i) {
        SoftbodyJNI.btSoftBodyJointData_pad_set(this.swigCPtr, this, i);
    }

    public int getPad() {
        return SoftbodyJNI.btSoftBodyJointData_pad_get(this.swigCPtr, this);
    }

    public btSoftBodyJointData() {
        this(SoftbodyJNI.new_btSoftBodyJointData(), true);
    }
}
