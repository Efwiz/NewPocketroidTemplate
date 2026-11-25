package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btSpatialForceVector extends BulletBase {
    private long swigCPtr;

    protected btSpatialForceVector(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSpatialForceVector(long j, boolean z) {
        this("btSpatialForceVector", j, z);
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

    public static long getCPtr(btSpatialForceVector btspatialforcevector) {
        if (btspatialforcevector == null) {
            return 0L;
        }
        return btspatialforcevector.swigCPtr;
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
                LinearMathJNI.delete_btSpatialForceVector(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTopVec(btVector3 btvector3) {
        LinearMathJNI.btSpatialForceVector_topVec_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getTopVec() {
        long jBtSpatialForceVector_topVec_get = LinearMathJNI.btSpatialForceVector_topVec_get(this.swigCPtr, this);
        if (jBtSpatialForceVector_topVec_get == 0) {
            return null;
        }
        return new btVector3(jBtSpatialForceVector_topVec_get, false);
    }

    public void setBottomVec(btVector3 btvector3) {
        LinearMathJNI.btSpatialForceVector_bottomVec_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getBottomVec() {
        long jBtSpatialForceVector_bottomVec_get = LinearMathJNI.btSpatialForceVector_bottomVec_get(this.swigCPtr, this);
        if (jBtSpatialForceVector_bottomVec_get == 0) {
            return null;
        }
        return new btVector3(jBtSpatialForceVector_bottomVec_get, false);
    }

    public btSpatialForceVector() {
        this(LinearMathJNI.new_btSpatialForceVector__SWIG_0(), true);
    }

    public btSpatialForceVector(Vector3 vector3, Vector3 vector4) {
        this(LinearMathJNI.new_btSpatialForceVector__SWIG_1(vector3, vector4), true);
    }

    public btSpatialForceVector(float f, float f2, float f3, float f4, float f5, float f6) {
        this(LinearMathJNI.new_btSpatialForceVector__SWIG_2(f, f2, f3, f4, f5, f6), true);
    }

    public void setVector(Vector3 vector3, Vector3 vector4) {
        LinearMathJNI.btSpatialForceVector_setVector(this.swigCPtr, this, vector3, vector4);
    }

    public void setValue(float f, float f2, float f3, float f4, float f5, float f6) {
        LinearMathJNI.btSpatialForceVector_setValue(this.swigCPtr, this, f, f2, f3, f4, f5, f6);
    }

    public void addVector(Vector3 vector3, Vector3 vector4) {
        LinearMathJNI.btSpatialForceVector_addVector(this.swigCPtr, this, vector3, vector4);
    }

    public void addValue(float f, float f2, float f3, float f4, float f5, float f6) {
        LinearMathJNI.btSpatialForceVector_addValue(this.swigCPtr, this, f, f2, f3, f4, f5, f6);
    }

    public Vector3 getLinear() {
        return LinearMathJNI.btSpatialForceVector_getLinear(this.swigCPtr, this);
    }

    public Vector3 getAngular() {
        return LinearMathJNI.btSpatialForceVector_getAngular(this.swigCPtr, this);
    }

    public void setLinear(Vector3 vector3) {
        LinearMathJNI.btSpatialForceVector_setLinear(this.swigCPtr, this, vector3);
    }

    public void setAngular(Vector3 vector3) {
        LinearMathJNI.btSpatialForceVector_setAngular(this.swigCPtr, this, vector3);
    }

    public void addAngular(Vector3 vector3) {
        LinearMathJNI.btSpatialForceVector_addAngular(this.swigCPtr, this, vector3);
    }

    public void addLinear(Vector3 vector3) {
        LinearMathJNI.btSpatialForceVector_addLinear(this.swigCPtr, this, vector3);
    }

    public void setZero() {
        LinearMathJNI.btSpatialForceVector_setZero(this.swigCPtr, this);
    }

    public btSpatialForceVector operatorAdditionAssignment(btSpatialForceVector btspatialforcevector) {
        return new btSpatialForceVector(LinearMathJNI.btSpatialForceVector_operatorAdditionAssignment(this.swigCPtr, this, getCPtr(btspatialforcevector), btspatialforcevector), false);
    }

    public btSpatialForceVector operatorSubtractionAssignment(btSpatialForceVector btspatialforcevector) {
        return new btSpatialForceVector(LinearMathJNI.btSpatialForceVector_operatorSubtractionAssignment(this.swigCPtr, this, getCPtr(btspatialforcevector), btspatialforcevector), false);
    }

    public btSpatialForceVector operatorSubtraction(btSpatialForceVector btspatialforcevector) {
        return new btSpatialForceVector(LinearMathJNI.btSpatialForceVector_operatorSubtraction__SWIG_0(this.swigCPtr, this, getCPtr(btspatialforcevector), btspatialforcevector), true);
    }

    public btSpatialForceVector operatorAddition(btSpatialForceVector btspatialforcevector) {
        return new btSpatialForceVector(LinearMathJNI.btSpatialForceVector_operatorAddition(this.swigCPtr, this, getCPtr(btspatialforcevector), btspatialforcevector), true);
    }

    public btSpatialForceVector operatorSubtraction() {
        return new btSpatialForceVector(LinearMathJNI.btSpatialForceVector_operatorSubtraction__SWIG_1(this.swigCPtr, this), true);
    }

    public btSpatialForceVector operatorMultiplication(float f) {
        return new btSpatialForceVector(LinearMathJNI.btSpatialForceVector_operatorMultiplication(this.swigCPtr, this, f), true);
    }
}
