package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btJointFeedback extends BulletBase {
    private long swigCPtr;

    protected btJointFeedback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btJointFeedback(long j, boolean z) {
        this("btJointFeedback", j, z);
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

    public static long getCPtr(btJointFeedback btjointfeedback) {
        if (btjointfeedback == null) {
            return 0L;
        }
        return btjointfeedback.swigCPtr;
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
                DynamicsJNI.delete_btJointFeedback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btJointFeedback_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btJointFeedback_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btJointFeedback_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btJointFeedback_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btJointFeedback_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btJointFeedback_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btJointFeedback_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btJointFeedback_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setAppliedForceBodyA(btVector3 btvector3) {
        DynamicsJNI.btJointFeedback_appliedForceBodyA_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAppliedForceBodyA() {
        long jBtJointFeedback_appliedForceBodyA_get = DynamicsJNI.btJointFeedback_appliedForceBodyA_get(this.swigCPtr, this);
        if (jBtJointFeedback_appliedForceBodyA_get == 0) {
            return null;
        }
        return new btVector3(jBtJointFeedback_appliedForceBodyA_get, false);
    }

    public void setAppliedTorqueBodyA(btVector3 btvector3) {
        DynamicsJNI.btJointFeedback_appliedTorqueBodyA_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAppliedTorqueBodyA() {
        long jBtJointFeedback_appliedTorqueBodyA_get = DynamicsJNI.btJointFeedback_appliedTorqueBodyA_get(this.swigCPtr, this);
        if (jBtJointFeedback_appliedTorqueBodyA_get == 0) {
            return null;
        }
        return new btVector3(jBtJointFeedback_appliedTorqueBodyA_get, false);
    }

    public void setAppliedForceBodyB(btVector3 btvector3) {
        DynamicsJNI.btJointFeedback_appliedForceBodyB_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAppliedForceBodyB() {
        long jBtJointFeedback_appliedForceBodyB_get = DynamicsJNI.btJointFeedback_appliedForceBodyB_get(this.swigCPtr, this);
        if (jBtJointFeedback_appliedForceBodyB_get == 0) {
            return null;
        }
        return new btVector3(jBtJointFeedback_appliedForceBodyB_get, false);
    }

    public void setAppliedTorqueBodyB(btVector3 btvector3) {
        DynamicsJNI.btJointFeedback_appliedTorqueBodyB_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAppliedTorqueBodyB() {
        long jBtJointFeedback_appliedTorqueBodyB_get = DynamicsJNI.btJointFeedback_appliedTorqueBodyB_get(this.swigCPtr, this);
        if (jBtJointFeedback_appliedTorqueBodyB_get == 0) {
            return null;
        }
        return new btVector3(jBtJointFeedback_appliedTorqueBodyB_get, false);
    }

    public btJointFeedback() {
        this(DynamicsJNI.new_btJointFeedback(), true);
    }
}
