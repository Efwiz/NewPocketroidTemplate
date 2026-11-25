package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btSymmetricSpatialDyad extends BulletBase {
    private long swigCPtr;

    protected btSymmetricSpatialDyad(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSymmetricSpatialDyad(long j, boolean z) {
        this("btSymmetricSpatialDyad", j, z);
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

    public static long getCPtr(btSymmetricSpatialDyad btsymmetricspatialdyad) {
        if (btsymmetricspatialdyad == null) {
            return 0L;
        }
        return btsymmetricspatialdyad.swigCPtr;
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
                LinearMathJNI.delete_btSymmetricSpatialDyad(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTopLeftMat(btMatrix3x3 btmatrix3x3) {
        LinearMathJNI.btSymmetricSpatialDyad_topLeftMat_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
    }

    public btMatrix3x3 getTopLeftMat() {
        long jBtSymmetricSpatialDyad_topLeftMat_get = LinearMathJNI.btSymmetricSpatialDyad_topLeftMat_get(this.swigCPtr, this);
        if (jBtSymmetricSpatialDyad_topLeftMat_get == 0) {
            return null;
        }
        return new btMatrix3x3(jBtSymmetricSpatialDyad_topLeftMat_get, false);
    }

    public void setTopRightMat(btMatrix3x3 btmatrix3x3) {
        LinearMathJNI.btSymmetricSpatialDyad_topRightMat_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
    }

    public btMatrix3x3 getTopRightMat() {
        long jBtSymmetricSpatialDyad_topRightMat_get = LinearMathJNI.btSymmetricSpatialDyad_topRightMat_get(this.swigCPtr, this);
        if (jBtSymmetricSpatialDyad_topRightMat_get == 0) {
            return null;
        }
        return new btMatrix3x3(jBtSymmetricSpatialDyad_topRightMat_get, false);
    }

    public void setBottomLeftMat(btMatrix3x3 btmatrix3x3) {
        LinearMathJNI.btSymmetricSpatialDyad_bottomLeftMat_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
    }

    public btMatrix3x3 getBottomLeftMat() {
        long jBtSymmetricSpatialDyad_bottomLeftMat_get = LinearMathJNI.btSymmetricSpatialDyad_bottomLeftMat_get(this.swigCPtr, this);
        if (jBtSymmetricSpatialDyad_bottomLeftMat_get == 0) {
            return null;
        }
        return new btMatrix3x3(jBtSymmetricSpatialDyad_bottomLeftMat_get, false);
    }

    public btSymmetricSpatialDyad() {
        this(LinearMathJNI.new_btSymmetricSpatialDyad__SWIG_0(), true);
    }

    public btSymmetricSpatialDyad(Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5) {
        this(LinearMathJNI.new_btSymmetricSpatialDyad__SWIG_1(matrix3, matrix4, matrix5), true);
    }

    public void setMatrix(Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5) {
        LinearMathJNI.btSymmetricSpatialDyad_setMatrix(this.swigCPtr, this, matrix3, matrix4, matrix5);
    }

    public void addMatrix(Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5) {
        LinearMathJNI.btSymmetricSpatialDyad_addMatrix(this.swigCPtr, this, matrix3, matrix4, matrix5);
    }

    public void setIdentity() {
        LinearMathJNI.btSymmetricSpatialDyad_setIdentity(this.swigCPtr, this);
    }

    public btSymmetricSpatialDyad operatorSubtractionAssignment(btSymmetricSpatialDyad btsymmetricspatialdyad) {
        return new btSymmetricSpatialDyad(LinearMathJNI.btSymmetricSpatialDyad_operatorSubtractionAssignment(this.swigCPtr, this, getCPtr(btsymmetricspatialdyad), btsymmetricspatialdyad), false);
    }

    public btSpatialForceVector operatorMultiplication(btSpatialMotionVector btspatialmotionvector) {
        return new btSpatialForceVector(LinearMathJNI.btSymmetricSpatialDyad_operatorMultiplication(this.swigCPtr, this, btSpatialMotionVector.getCPtr(btspatialmotionvector), btspatialmotionvector), true);
    }
}
