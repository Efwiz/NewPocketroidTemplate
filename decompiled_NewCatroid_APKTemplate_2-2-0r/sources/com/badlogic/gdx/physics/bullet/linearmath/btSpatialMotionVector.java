package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btSpatialMotionVector extends BulletBase {
    private long swigCPtr;

    protected btSpatialMotionVector(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSpatialMotionVector(long j, boolean z) {
        this("btSpatialMotionVector", j, z);
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

    public static long getCPtr(btSpatialMotionVector btspatialmotionvector) {
        if (btspatialmotionvector == null) {
            return 0L;
        }
        return btspatialmotionvector.swigCPtr;
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
                LinearMathJNI.delete_btSpatialMotionVector(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTopVec(btVector3 btvector3) {
        LinearMathJNI.btSpatialMotionVector_topVec_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getTopVec() {
        long jBtSpatialMotionVector_topVec_get = LinearMathJNI.btSpatialMotionVector_topVec_get(this.swigCPtr, this);
        if (jBtSpatialMotionVector_topVec_get == 0) {
            return null;
        }
        return new btVector3(jBtSpatialMotionVector_topVec_get, false);
    }

    public void setBottomVec(btVector3 btvector3) {
        LinearMathJNI.btSpatialMotionVector_bottomVec_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getBottomVec() {
        long jBtSpatialMotionVector_bottomVec_get = LinearMathJNI.btSpatialMotionVector_bottomVec_get(this.swigCPtr, this);
        if (jBtSpatialMotionVector_bottomVec_get == 0) {
            return null;
        }
        return new btVector3(jBtSpatialMotionVector_bottomVec_get, false);
    }

    public btSpatialMotionVector() {
        this(LinearMathJNI.new_btSpatialMotionVector__SWIG_0(), true);
    }

    public btSpatialMotionVector(Vector3 vector3, Vector3 vector4) {
        this(LinearMathJNI.new_btSpatialMotionVector__SWIG_1(vector3, vector4), true);
    }

    public void setVector(Vector3 vector3, Vector3 vector4) {
        LinearMathJNI.btSpatialMotionVector_setVector(this.swigCPtr, this, vector3, vector4);
    }

    public void setValue(float f, float f2, float f3, float f4, float f5, float f6) {
        LinearMathJNI.btSpatialMotionVector_setValue(this.swigCPtr, this, f, f2, f3, f4, f5, f6);
    }

    public void addVector(Vector3 vector3, Vector3 vector4) {
        LinearMathJNI.btSpatialMotionVector_addVector(this.swigCPtr, this, vector3, vector4);
    }

    public void addValue(float f, float f2, float f3, float f4, float f5, float f6) {
        LinearMathJNI.btSpatialMotionVector_addValue(this.swigCPtr, this, f, f2, f3, f4, f5, f6);
    }

    public Vector3 getAngular() {
        return LinearMathJNI.btSpatialMotionVector_getAngular(this.swigCPtr, this);
    }

    public Vector3 getLinear() {
        return LinearMathJNI.btSpatialMotionVector_getLinear(this.swigCPtr, this);
    }

    public void setAngular(Vector3 vector3) {
        LinearMathJNI.btSpatialMotionVector_setAngular(this.swigCPtr, this, vector3);
    }

    public void setLinear(Vector3 vector3) {
        LinearMathJNI.btSpatialMotionVector_setLinear(this.swigCPtr, this, vector3);
    }

    public void addAngular(Vector3 vector3) {
        LinearMathJNI.btSpatialMotionVector_addAngular(this.swigCPtr, this, vector3);
    }

    public void addLinear(Vector3 vector3) {
        LinearMathJNI.btSpatialMotionVector_addLinear(this.swigCPtr, this, vector3);
    }

    public void setZero() {
        LinearMathJNI.btSpatialMotionVector_setZero(this.swigCPtr, this);
    }

    public float dot(btSpatialForceVector btspatialforcevector) {
        return LinearMathJNI.btSpatialMotionVector_dot(this.swigCPtr, this, btSpatialForceVector.getCPtr(btspatialforcevector), btspatialforcevector);
    }

    public btSpatialMotionVector operatorAdditionAssignment(btSpatialMotionVector btspatialmotionvector) {
        return new btSpatialMotionVector(LinearMathJNI.btSpatialMotionVector_operatorAdditionAssignment(this.swigCPtr, this, getCPtr(btspatialmotionvector), btspatialmotionvector), false);
    }

    public btSpatialMotionVector operatorSubtractionAssignment(btSpatialMotionVector btspatialmotionvector) {
        return new btSpatialMotionVector(LinearMathJNI.btSpatialMotionVector_operatorSubtractionAssignment(this.swigCPtr, this, getCPtr(btspatialmotionvector), btspatialmotionvector), false);
    }

    public btSpatialMotionVector operatorMultiplicationAssignment(float f) {
        return new btSpatialMotionVector(LinearMathJNI.btSpatialMotionVector_operatorMultiplicationAssignment(this.swigCPtr, this, f), false);
    }

    public btSpatialMotionVector operatorSubtraction(btSpatialMotionVector btspatialmotionvector) {
        return new btSpatialMotionVector(LinearMathJNI.btSpatialMotionVector_operatorSubtraction__SWIG_0(this.swigCPtr, this, getCPtr(btspatialmotionvector), btspatialmotionvector), true);
    }

    public btSpatialMotionVector operatorAddition(btSpatialMotionVector btspatialmotionvector) {
        return new btSpatialMotionVector(LinearMathJNI.btSpatialMotionVector_operatorAddition(this.swigCPtr, this, getCPtr(btspatialmotionvector), btspatialmotionvector), true);
    }

    public btSpatialMotionVector operatorSubtraction() {
        return new btSpatialMotionVector(LinearMathJNI.btSpatialMotionVector_operatorSubtraction__SWIG_1(this.swigCPtr, this), true);
    }

    public btSpatialMotionVector operatorMultiplication(float f) {
        return new btSpatialMotionVector(LinearMathJNI.btSpatialMotionVector_operatorMultiplication(this.swigCPtr, this, f), true);
    }
}
