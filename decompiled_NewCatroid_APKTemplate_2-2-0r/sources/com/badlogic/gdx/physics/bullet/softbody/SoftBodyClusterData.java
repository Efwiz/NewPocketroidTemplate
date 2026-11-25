package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3FloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class SoftBodyClusterData extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected SoftBodyClusterData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public SoftBodyClusterData(long j, boolean z) {
        this("SoftBodyClusterData", j, z);
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

    public static long getCPtr(SoftBodyClusterData softBodyClusterData) {
        if (softBodyClusterData == null) {
            return 0L;
        }
        return softBodyClusterData.swigCPtr;
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
                SoftbodyJNI.delete_SoftBodyClusterData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setFramexform(btTransformFloatData bttransformfloatdata) {
        SoftbodyJNI.SoftBodyClusterData_framexform_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getFramexform() {
        long jSoftBodyClusterData_framexform_get = SoftbodyJNI.SoftBodyClusterData_framexform_get(this.swigCPtr, this);
        if (jSoftBodyClusterData_framexform_get == 0) {
            return null;
        }
        return new btTransformFloatData(jSoftBodyClusterData_framexform_get, false);
    }

    public void setLocii(btMatrix3x3FloatData btmatrix3x3floatdata) {
        SoftbodyJNI.SoftBodyClusterData_locii_set(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public btMatrix3x3FloatData getLocii() {
        long jSoftBodyClusterData_locii_get = SoftbodyJNI.SoftBodyClusterData_locii_get(this.swigCPtr, this);
        if (jSoftBodyClusterData_locii_get == 0) {
            return null;
        }
        return new btMatrix3x3FloatData(jSoftBodyClusterData_locii_get, false);
    }

    public void setInvwi(btMatrix3x3FloatData btmatrix3x3floatdata) {
        SoftbodyJNI.SoftBodyClusterData_invwi_set(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public btMatrix3x3FloatData getInvwi() {
        long jSoftBodyClusterData_invwi_get = SoftbodyJNI.SoftBodyClusterData_invwi_get(this.swigCPtr, this);
        if (jSoftBodyClusterData_invwi_get == 0) {
            return null;
        }
        return new btMatrix3x3FloatData(jSoftBodyClusterData_invwi_get, false);
    }

    public void setCom(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyClusterData_com_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getCom() {
        long jSoftBodyClusterData_com_get = SoftbodyJNI.SoftBodyClusterData_com_get(this.swigCPtr, this);
        if (jSoftBodyClusterData_com_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyClusterData_com_get, false);
    }

    public void setVimpulses(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyClusterData_vimpulses_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getVimpulses() {
        long jSoftBodyClusterData_vimpulses_get = SoftbodyJNI.SoftBodyClusterData_vimpulses_get(this.swigCPtr, this);
        if (jSoftBodyClusterData_vimpulses_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyClusterData_vimpulses_get, false);
    }

    public void setDimpulses(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyClusterData_dimpulses_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getDimpulses() {
        long jSoftBodyClusterData_dimpulses_get = SoftbodyJNI.SoftBodyClusterData_dimpulses_get(this.swigCPtr, this);
        if (jSoftBodyClusterData_dimpulses_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyClusterData_dimpulses_get, false);
    }

    public void setLv(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyClusterData_lv_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLv() {
        long jSoftBodyClusterData_lv_get = SoftbodyJNI.SoftBodyClusterData_lv_get(this.swigCPtr, this);
        if (jSoftBodyClusterData_lv_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyClusterData_lv_get, false);
    }

    public void setAv(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyClusterData_av_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAv() {
        long jSoftBodyClusterData_av_get = SoftbodyJNI.SoftBodyClusterData_av_get(this.swigCPtr, this);
        if (jSoftBodyClusterData_av_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyClusterData_av_get, false);
    }

    public void setFramerefs(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftBodyClusterData_framerefs_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getFramerefs() {
        long jSoftBodyClusterData_framerefs_get = SoftbodyJNI.SoftBodyClusterData_framerefs_get(this.swigCPtr, this);
        if (jSoftBodyClusterData_framerefs_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftBodyClusterData_framerefs_get, false);
    }

    public void setNodeIndices(IntBuffer intBuffer) {
        SoftbodyJNI.SoftBodyClusterData_nodeIndices_set(this.swigCPtr, this, intBuffer);
    }

    public IntBuffer getNodeIndices() {
        return SoftbodyJNI.SoftBodyClusterData_nodeIndices_get(this.swigCPtr, this);
    }

    public void setMasses(FloatBuffer floatBuffer) {
        SoftbodyJNI.SoftBodyClusterData_masses_set(this.swigCPtr, this, floatBuffer);
    }

    public FloatBuffer getMasses() {
        return SoftbodyJNI.SoftBodyClusterData_masses_get(this.swigCPtr, this);
    }

    public void setNumFrameRefs(int i) {
        SoftbodyJNI.SoftBodyClusterData_numFrameRefs_set(this.swigCPtr, this, i);
    }

    public int getNumFrameRefs() {
        return SoftbodyJNI.SoftBodyClusterData_numFrameRefs_get(this.swigCPtr, this);
    }

    public void setNumNodes(int i) {
        SoftbodyJNI.SoftBodyClusterData_numNodes_set(this.swigCPtr, this, i);
    }

    public int getNumNodes() {
        return SoftbodyJNI.SoftBodyClusterData_numNodes_get(this.swigCPtr, this);
    }

    public void setNumMasses(int i) {
        SoftbodyJNI.SoftBodyClusterData_numMasses_set(this.swigCPtr, this, i);
    }

    public int getNumMasses() {
        return SoftbodyJNI.SoftBodyClusterData_numMasses_get(this.swigCPtr, this);
    }

    public void setIdmass(float f) {
        SoftbodyJNI.SoftBodyClusterData_idmass_set(this.swigCPtr, this, f);
    }

    public float getIdmass() {
        return SoftbodyJNI.SoftBodyClusterData_idmass_get(this.swigCPtr, this);
    }

    public void setImass(float f) {
        SoftbodyJNI.SoftBodyClusterData_imass_set(this.swigCPtr, this, f);
    }

    public float getImass() {
        return SoftbodyJNI.SoftBodyClusterData_imass_get(this.swigCPtr, this);
    }

    public void setNvimpulses(int i) {
        SoftbodyJNI.SoftBodyClusterData_nvimpulses_set(this.swigCPtr, this, i);
    }

    public int getNvimpulses() {
        return SoftbodyJNI.SoftBodyClusterData_nvimpulses_get(this.swigCPtr, this);
    }

    public void setNdimpulses(int i) {
        SoftbodyJNI.SoftBodyClusterData_ndimpulses_set(this.swigCPtr, this, i);
    }

    public int getNdimpulses() {
        return SoftbodyJNI.SoftBodyClusterData_ndimpulses_get(this.swigCPtr, this);
    }

    public void setNdamping(float f) {
        SoftbodyJNI.SoftBodyClusterData_ndamping_set(this.swigCPtr, this, f);
    }

    public float getNdamping() {
        return SoftbodyJNI.SoftBodyClusterData_ndamping_get(this.swigCPtr, this);
    }

    public void setLdamping(float f) {
        SoftbodyJNI.SoftBodyClusterData_ldamping_set(this.swigCPtr, this, f);
    }

    public float getLdamping() {
        return SoftbodyJNI.SoftBodyClusterData_ldamping_get(this.swigCPtr, this);
    }

    public void setAdamping(float f) {
        SoftbodyJNI.SoftBodyClusterData_adamping_set(this.swigCPtr, this, f);
    }

    public float getAdamping() {
        return SoftbodyJNI.SoftBodyClusterData_adamping_get(this.swigCPtr, this);
    }

    public void setMatching(float f) {
        SoftbodyJNI.SoftBodyClusterData_matching_set(this.swigCPtr, this, f);
    }

    public float getMatching() {
        return SoftbodyJNI.SoftBodyClusterData_matching_get(this.swigCPtr, this);
    }

    public void setMaxSelfCollisionImpulse(float f) {
        SoftbodyJNI.SoftBodyClusterData_maxSelfCollisionImpulse_set(this.swigCPtr, this, f);
    }

    public float getMaxSelfCollisionImpulse() {
        return SoftbodyJNI.SoftBodyClusterData_maxSelfCollisionImpulse_get(this.swigCPtr, this);
    }

    public void setSelfCollisionImpulseFactor(float f) {
        SoftbodyJNI.SoftBodyClusterData_selfCollisionImpulseFactor_set(this.swigCPtr, this, f);
    }

    public float getSelfCollisionImpulseFactor() {
        return SoftbodyJNI.SoftBodyClusterData_selfCollisionImpulseFactor_get(this.swigCPtr, this);
    }

    public void setContainsAnchor(int i) {
        SoftbodyJNI.SoftBodyClusterData_containsAnchor_set(this.swigCPtr, this, i);
    }

    public int getContainsAnchor() {
        return SoftbodyJNI.SoftBodyClusterData_containsAnchor_get(this.swigCPtr, this);
    }

    public void setCollide(int i) {
        SoftbodyJNI.SoftBodyClusterData_collide_set(this.swigCPtr, this, i);
    }

    public int getCollide() {
        return SoftbodyJNI.SoftBodyClusterData_collide_get(this.swigCPtr, this);
    }

    public void setClusterIndex(int i) {
        SoftbodyJNI.SoftBodyClusterData_clusterIndex_set(this.swigCPtr, this, i);
    }

    public int getClusterIndex() {
        return SoftbodyJNI.SoftBodyClusterData_clusterIndex_get(this.swigCPtr, this);
    }

    public SoftBodyClusterData() {
        this(SoftbodyJNI.new_SoftBodyClusterData(), true);
    }
}
