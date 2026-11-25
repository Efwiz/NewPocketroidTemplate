package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btJacobianEntry extends BulletBase {
    private long swigCPtr;

    protected btJacobianEntry(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btJacobianEntry(long j, boolean z) {
        this("btJacobianEntry", j, z);
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

    public static long getCPtr(btJacobianEntry btjacobianentry) {
        if (btjacobianentry == null) {
            return 0L;
        }
        return btjacobianentry.swigCPtr;
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
                DynamicsJNI.delete_btJacobianEntry(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btJacobianEntry() {
        this(DynamicsJNI.new_btJacobianEntry__SWIG_0(), true);
    }

    public btJacobianEntry(Matrix3 matrix3, Matrix3 matrix4, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f, Vector3 vector7, float f2) {
        this(DynamicsJNI.new_btJacobianEntry__SWIG_1(matrix3, matrix4, vector3, vector4, vector5, vector6, f, vector7, f2), true);
    }

    public btJacobianEntry(Vector3 vector3, Matrix3 matrix3, Matrix3 matrix4, Vector3 vector4, Vector3 vector5) {
        this(DynamicsJNI.new_btJacobianEntry__SWIG_2(vector3, matrix3, matrix4, vector4, vector5), true);
    }

    public btJacobianEntry(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        this(DynamicsJNI.new_btJacobianEntry__SWIG_3(vector3, vector4, vector5, vector6), true);
    }

    public btJacobianEntry(Matrix3 matrix3, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f) {
        this(DynamicsJNI.new_btJacobianEntry__SWIG_4(matrix3, vector3, vector4, vector5, vector6, f), true);
    }

    public float getDiagonal() {
        return DynamicsJNI.btJacobianEntry_getDiagonal(this.swigCPtr, this);
    }

    public float getNonDiagonal(btJacobianEntry btjacobianentry, float f) {
        return DynamicsJNI.btJacobianEntry_getNonDiagonal__SWIG_0(this.swigCPtr, this, getCPtr(btjacobianentry), btjacobianentry, f);
    }

    public float getNonDiagonal(btJacobianEntry btjacobianentry, float f, float f2) {
        return DynamicsJNI.btJacobianEntry_getNonDiagonal__SWIG_1(this.swigCPtr, this, getCPtr(btjacobianentry), btjacobianentry, f, f2);
    }

    public float getRelativeVelocity(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        return DynamicsJNI.btJacobianEntry_getRelativeVelocity(this.swigCPtr, this, vector3, vector4, vector5, vector6);
    }

    public void setLinearJointAxis(btVector3 btvector3) {
        DynamicsJNI.btJacobianEntry_linearJointAxis_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getLinearJointAxis() {
        long jBtJacobianEntry_linearJointAxis_get = DynamicsJNI.btJacobianEntry_linearJointAxis_get(this.swigCPtr, this);
        if (jBtJacobianEntry_linearJointAxis_get == 0) {
            return null;
        }
        return new btVector3(jBtJacobianEntry_linearJointAxis_get, false);
    }

    public void setAJ(btVector3 btvector3) {
        DynamicsJNI.btJacobianEntry_aJ_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAJ() {
        long jBtJacobianEntry_aJ_get = DynamicsJNI.btJacobianEntry_aJ_get(this.swigCPtr, this);
        if (jBtJacobianEntry_aJ_get == 0) {
            return null;
        }
        return new btVector3(jBtJacobianEntry_aJ_get, false);
    }

    public void setBJ(btVector3 btvector3) {
        DynamicsJNI.btJacobianEntry_bJ_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getBJ() {
        long jBtJacobianEntry_bJ_get = DynamicsJNI.btJacobianEntry_bJ_get(this.swigCPtr, this);
        if (jBtJacobianEntry_bJ_get == 0) {
            return null;
        }
        return new btVector3(jBtJacobianEntry_bJ_get, false);
    }

    public void set0MinvJt(btVector3 btvector3) {
        DynamicsJNI.btJacobianEntry_0MinvJt_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 get0MinvJt() {
        long jBtJacobianEntry_0MinvJt_get = DynamicsJNI.btJacobianEntry_0MinvJt_get(this.swigCPtr, this);
        if (jBtJacobianEntry_0MinvJt_get == 0) {
            return null;
        }
        return new btVector3(jBtJacobianEntry_0MinvJt_get, false);
    }

    public void set1MinvJt(btVector3 btvector3) {
        DynamicsJNI.btJacobianEntry_1MinvJt_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 get1MinvJt() {
        long jBtJacobianEntry_1MinvJt_get = DynamicsJNI.btJacobianEntry_1MinvJt_get(this.swigCPtr, this);
        if (jBtJacobianEntry_1MinvJt_get == 0) {
            return null;
        }
        return new btVector3(jBtJacobianEntry_1MinvJt_get, false);
    }

    public void setAdiag(float f) {
        DynamicsJNI.btJacobianEntry_Adiag_set(this.swigCPtr, this, f);
    }

    public float getAdiag() {
        return DynamicsJNI.btJacobianEntry_Adiag_get(this.swigCPtr, this);
    }
}
