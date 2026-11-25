package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBodyFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3FloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class SoftRigidAnchorData extends BulletBase {
    private long swigCPtr;

    protected SoftRigidAnchorData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public SoftRigidAnchorData(long j, boolean z) {
        this("SoftRigidAnchorData", j, z);
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

    public static long getCPtr(SoftRigidAnchorData softRigidAnchorData) {
        if (softRigidAnchorData == null) {
            return 0L;
        }
        return softRigidAnchorData.swigCPtr;
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
                SoftbodyJNI.delete_SoftRigidAnchorData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setC0(btMatrix3x3FloatData btmatrix3x3floatdata) {
        SoftbodyJNI.SoftRigidAnchorData_c0_set(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public btMatrix3x3FloatData getC0() {
        long jSoftRigidAnchorData_c0_get = SoftbodyJNI.SoftRigidAnchorData_c0_get(this.swigCPtr, this);
        if (jSoftRigidAnchorData_c0_get == 0) {
            return null;
        }
        return new btMatrix3x3FloatData(jSoftRigidAnchorData_c0_get, false);
    }

    public void setC1(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftRigidAnchorData_c1_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getC1() {
        long jSoftRigidAnchorData_c1_get = SoftbodyJNI.SoftRigidAnchorData_c1_get(this.swigCPtr, this);
        if (jSoftRigidAnchorData_c1_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftRigidAnchorData_c1_get, false);
    }

    public void setLocalFrame(btVector3FloatData btvector3floatdata) {
        SoftbodyJNI.SoftRigidAnchorData_localFrame_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLocalFrame() {
        long jSoftRigidAnchorData_localFrame_get = SoftbodyJNI.SoftRigidAnchorData_localFrame_get(this.swigCPtr, this);
        if (jSoftRigidAnchorData_localFrame_get == 0) {
            return null;
        }
        return new btVector3FloatData(jSoftRigidAnchorData_localFrame_get, false);
    }

    public void setRigidBody(btRigidBodyFloatData btrigidbodyfloatdata) {
        SoftbodyJNI.SoftRigidAnchorData_rigidBody_set(this.swigCPtr, this, btRigidBodyFloatData.getCPtr(btrigidbodyfloatdata), btrigidbodyfloatdata);
    }

    public btRigidBodyFloatData getRigidBody() {
        long jSoftRigidAnchorData_rigidBody_get = SoftbodyJNI.SoftRigidAnchorData_rigidBody_get(this.swigCPtr, this);
        if (jSoftRigidAnchorData_rigidBody_get == 0) {
            return null;
        }
        return new btRigidBodyFloatData(jSoftRigidAnchorData_rigidBody_get, false);
    }

    public void setNodeIndex(int i) {
        SoftbodyJNI.SoftRigidAnchorData_nodeIndex_set(this.swigCPtr, this, i);
    }

    public int getNodeIndex() {
        return SoftbodyJNI.SoftRigidAnchorData_nodeIndex_get(this.swigCPtr, this);
    }

    public void setC2(float f) {
        SoftbodyJNI.SoftRigidAnchorData_c2_set(this.swigCPtr, this, f);
    }

    public float getC2() {
        return SoftbodyJNI.SoftRigidAnchorData_c2_get(this.swigCPtr, this);
    }

    public SoftRigidAnchorData() {
        this(SoftbodyJNI.new_SoftRigidAnchorData(), true);
    }
}
