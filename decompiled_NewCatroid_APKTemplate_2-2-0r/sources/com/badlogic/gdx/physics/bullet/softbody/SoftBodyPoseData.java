package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3FloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class SoftBodyPoseData extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected SoftBodyPoseData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public SoftBodyPoseData(long j, boolean z) {
        this("SoftBodyPoseData", j, z);
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

    public static long getCPtr(SoftBodyPoseData softBodyPoseData) {
        if (softBodyPoseData == null) {
            return 0L;
        }
        return softBodyPoseData.swigCPtr;
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
                SoftbodyJNI.delete_SoftBodyPoseData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setRot(btMatrix3x3FloatData btmatrix3x3floatdata) {
        SoftbodyJNI.SoftBodyPoseData_rot_set(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public btMatrix3x3FloatData getRot() {
        long jSoftBodyPoseData_rot_get = SoftbodyJNI.SoftBodyPoseData_rot_get(this.swigCPtr, this);
        if (jSoftBodyPoseData_rot_get == 0) {
            return null;
        }
        return new btMatrix3x3FloatData(jSoftBodyPoseData_rot_get, false);
    }

    public void setScale(btMatrix3x3FloatData btmatrix3x3floatdata) {
        SoftbodyJNI.SoftBodyPoseData_scale_set(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public btMatrix3x3FloatData getScale() {
        long jSoftBodyPoseData_scale_get = SoftbodyJNI.SoftBodyPoseData_scale_get(this.swigCPtr, this);
        if (jSoftBodyPoseData_scale_get == 0) {
            return null;
        }
        return new btMatrix3x3FloatData(jSoftBodyPoseData_scale_get, false);
    }

    public void setAqq(btMatrix3x3FloatData btmatrix3x3floatdata) {
        SoftbodyJNI.SoftBodyPoseData_aqq_set(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public btMatrix3x3FloatData getAqq() {
        long jSoftBodyPoseData_aqq_get = SoftbodyJNI.SoftBodyPoseData_aqq_get(this.swigCPtr, this);
        if (jSoftBodyPoseData_aqq_get == 0) {
            return null;
        }
        return new btMatrix3x3FloatData(jSoftBodyPoseData_aqq_get, false);
    }

    public void setCom(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyPoseData_com_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getCom() {
        long jSoftBodyPoseData_com_get = SoftbodyJNI.SoftBodyPoseData_com_get(this.swigCPtr, this);
        if (jSoftBodyPoseData_com_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyPoseData_com_get, false);
    }

    public void setPositions(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyPoseData_positions_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getPositions() {
        long jSoftBodyPoseData_positions_get = SoftbodyJNI.SoftBodyPoseData_positions_get(this.swigCPtr, this);
        if (jSoftBodyPoseData_positions_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyPoseData_positions_get, false);
    }

    public void setWeights(FloatBuffer floatBuffer) {
        SoftbodyJNI.SoftBodyPoseData_weights_set(this.swigCPtr, this, floatBuffer);
    }

    public FloatBuffer getWeights() {
        return SoftbodyJNI.SoftBodyPoseData_weights_get(this.swigCPtr, this);
    }

    public void setNumPositions(int i) {
        SoftbodyJNI.SoftBodyPoseData_numPositions_set(this.swigCPtr, this, i);
    }

    public int getNumPositions() {
        return SoftbodyJNI.SoftBodyPoseData_numPositions_get(this.swigCPtr, this);
    }

    public void setNumWeigts(int i) {
        SoftbodyJNI.SoftBodyPoseData_numWeigts_set(this.swigCPtr, this, i);
    }

    public int getNumWeigts() {
        return SoftbodyJNI.SoftBodyPoseData_numWeigts_get(this.swigCPtr, this);
    }

    public void setBvolume(int i) {
        SoftbodyJNI.SoftBodyPoseData_bvolume_set(this.swigCPtr, this, i);
    }

    public int getBvolume() {
        return SoftbodyJNI.SoftBodyPoseData_bvolume_get(this.swigCPtr, this);
    }

    public void setBframe(int i) {
        SoftbodyJNI.SoftBodyPoseData_bframe_set(this.swigCPtr, this, i);
    }

    public int getBframe() {
        return SoftbodyJNI.SoftBodyPoseData_bframe_get(this.swigCPtr, this);
    }

    public void setRestVolume(float f) {
        SoftbodyJNI.SoftBodyPoseData_restVolume_set(this.swigCPtr, this, f);
    }

    public float getRestVolume() {
        return SoftbodyJNI.SoftBodyPoseData_restVolume_get(this.swigCPtr, this);
    }

    public void setPad(int i) {
        SoftbodyJNI.SoftBodyPoseData_pad_set(this.swigCPtr, this, i);
    }

    public int getPad() {
        return SoftbodyJNI.SoftBodyPoseData_pad_get(this.swigCPtr, this);
    }

    public SoftBodyPoseData() {
        this(SoftbodyJNI.new_SoftBodyPoseData(), true);
    }
}
