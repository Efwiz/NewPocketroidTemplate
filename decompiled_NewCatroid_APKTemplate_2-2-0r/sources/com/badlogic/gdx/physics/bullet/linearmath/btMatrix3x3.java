package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btMatrix3x3 extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btMatrix3x3(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMatrix3x3(long j, boolean z) {
        this("btMatrix3x3", j, z);
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

    public static long getCPtr(btMatrix3x3 btmatrix3x3) {
        if (btmatrix3x3 == null) {
            return 0L;
        }
        return btmatrix3x3.swigCPtr;
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
                LinearMathJNI.delete_btMatrix3x3(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btMatrix3x3() {
        this(LinearMathJNI.new_btMatrix3x3__SWIG_0(), true);
    }

    public btMatrix3x3(Quaternion quaternion) {
        this(LinearMathJNI.new_btMatrix3x3__SWIG_1(quaternion), true);
    }

    public btMatrix3x3(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this(LinearMathJNI.new_btMatrix3x3__SWIG_2(f, f2, f3, f4, f5, f6, f7, f8, f9), true);
    }

    public btMatrix3x3(Matrix3 matrix3) {
        this(LinearMathJNI.new_btMatrix3x3__SWIG_3(matrix3), true);
    }

    public Matrix3 operatorAssignment(Matrix3 matrix3) {
        return LinearMathJNI.btMatrix3x3_operatorAssignment(this.swigCPtr, this, matrix3);
    }

    public Vector3 getColumn(int i) {
        return LinearMathJNI.btMatrix3x3_getColumn(this.swigCPtr, this, i);
    }

    public Vector3 getRow(int i) {
        return LinearMathJNI.btMatrix3x3_getRow(this.swigCPtr, this, i);
    }

    public Vector3 operatorSubscript(int i) {
        return LinearMathJNI.btMatrix3x3_operatorSubscript(this.swigCPtr, this, i);
    }

    public Vector3 operatorSubscriptConst(int i) {
        return LinearMathJNI.btMatrix3x3_operatorSubscriptConst(this.swigCPtr, this, i);
    }

    public Matrix3 operatorMultiplicationAssignment(Matrix3 matrix3) {
        return LinearMathJNI.btMatrix3x3_operatorMultiplicationAssignment(this.swigCPtr, this, matrix3);
    }

    public Matrix3 operatorAdditionAssignment(Matrix3 matrix3) {
        return LinearMathJNI.btMatrix3x3_operatorAdditionAssignment(this.swigCPtr, this, matrix3);
    }

    public Matrix3 operatorSubtractionAssignment(Matrix3 matrix3) {
        return LinearMathJNI.btMatrix3x3_operatorSubtractionAssignment(this.swigCPtr, this, matrix3);
    }

    public void setFromOpenGLSubMatrix(FloatBuffer floatBuffer) {
        LinearMathJNI.btMatrix3x3_setFromOpenGLSubMatrix(this.swigCPtr, this, floatBuffer);
    }

    public void setValue(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        LinearMathJNI.btMatrix3x3_setValue(this.swigCPtr, this, f, f2, f3, f4, f5, f6, f7, f8, f9);
    }

    public void setRotation(Quaternion quaternion) {
        LinearMathJNI.btMatrix3x3_setRotation(this.swigCPtr, this, quaternion);
    }

    public void setEulerYPR(float f, float f2, float f3) {
        LinearMathJNI.btMatrix3x3_setEulerYPR(this.swigCPtr, this, f, f2, f3);
    }

    public void setEulerZYX(float f, float f2, float f3) {
        LinearMathJNI.btMatrix3x3_setEulerZYX(this.swigCPtr, this, f, f2, f3);
    }

    public void setIdentity() {
        LinearMathJNI.btMatrix3x3_setIdentity(this.swigCPtr, this);
    }

    public static Matrix3 getIdentity() {
        return LinearMathJNI.btMatrix3x3_getIdentity();
    }

    public void getOpenGLSubMatrix(FloatBuffer floatBuffer) {
        LinearMathJNI.btMatrix3x3_getOpenGLSubMatrix(this.swigCPtr, this, floatBuffer);
    }

    public void getRotation(Quaternion quaternion) {
        LinearMathJNI.btMatrix3x3_getRotation(this.swigCPtr, this, quaternion);
    }

    public void getEulerYPR(SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2, SWIGTYPE_p_float sWIGTYPE_p_float3) {
        LinearMathJNI.btMatrix3x3_getEulerYPR(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float3));
    }

    public void getEulerZYX(SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2, SWIGTYPE_p_float sWIGTYPE_p_float3, long j) {
        LinearMathJNI.btMatrix3x3_getEulerZYX__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float3), j);
    }

    public void getEulerZYX(SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2, SWIGTYPE_p_float sWIGTYPE_p_float3) {
        LinearMathJNI.btMatrix3x3_getEulerZYX__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float3));
    }

    public Matrix3 scaled(Vector3 vector3) {
        return LinearMathJNI.btMatrix3x3_scaled(this.swigCPtr, this, vector3);
    }

    public float determinant() {
        return LinearMathJNI.btMatrix3x3_determinant(this.swigCPtr, this);
    }

    public Matrix3 adjoint() {
        return LinearMathJNI.btMatrix3x3_adjoint(this.swigCPtr, this);
    }

    public Matrix3 absolute() {
        return LinearMathJNI.btMatrix3x3_absolute(this.swigCPtr, this);
    }

    public Matrix3 transpose() {
        return LinearMathJNI.btMatrix3x3_transpose(this.swigCPtr, this);
    }

    public Matrix3 inverse() {
        return LinearMathJNI.btMatrix3x3_inverse(this.swigCPtr, this);
    }

    public Vector3 solve33(Vector3 vector3) {
        return LinearMathJNI.btMatrix3x3_solve33(this.swigCPtr, this, vector3);
    }

    public Matrix3 transposeTimes(Matrix3 matrix3) {
        return LinearMathJNI.btMatrix3x3_transposeTimes(this.swigCPtr, this, matrix3);
    }

    public Matrix3 timesTranspose(Matrix3 matrix3) {
        return LinearMathJNI.btMatrix3x3_timesTranspose(this.swigCPtr, this, matrix3);
    }

    public float tdotx(Vector3 vector3) {
        return LinearMathJNI.btMatrix3x3_tdotx(this.swigCPtr, this, vector3);
    }

    public float tdoty(Vector3 vector3) {
        return LinearMathJNI.btMatrix3x3_tdoty(this.swigCPtr, this, vector3);
    }

    public float tdotz(Vector3 vector3) {
        return LinearMathJNI.btMatrix3x3_tdotz(this.swigCPtr, this, vector3);
    }

    public void extractRotation(Quaternion quaternion, float f, int i) {
        LinearMathJNI.btMatrix3x3_extractRotation__SWIG_0(this.swigCPtr, this, quaternion, f, i);
    }

    public void extractRotation(Quaternion quaternion, float f) {
        LinearMathJNI.btMatrix3x3_extractRotation__SWIG_1(this.swigCPtr, this, quaternion, f);
    }

    public void extractRotation(Quaternion quaternion) {
        LinearMathJNI.btMatrix3x3_extractRotation__SWIG_2(this.swigCPtr, this, quaternion);
    }

    public void diagonalize(Matrix3 matrix3, float f, int i) {
        LinearMathJNI.btMatrix3x3_diagonalize__SWIG_0(this.swigCPtr, this, matrix3, f, i);
    }

    public void diagonalize(Matrix3 matrix3, float f) {
        LinearMathJNI.btMatrix3x3_diagonalize__SWIG_1(this.swigCPtr, this, matrix3, f);
    }

    public void diagonalize(Matrix3 matrix3) {
        LinearMathJNI.btMatrix3x3_diagonalize__SWIG_2(this.swigCPtr, this, matrix3);
    }

    public float cofac(int i, int i2, int i3, int i4) {
        return LinearMathJNI.btMatrix3x3_cofac(this.swigCPtr, this, i, i2, i3, i4);
    }

    public void serialize(btMatrix3x3FloatData btmatrix3x3floatdata) {
        LinearMathJNI.btMatrix3x3_serialize(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public void serializeFloat(btMatrix3x3FloatData btmatrix3x3floatdata) {
        LinearMathJNI.btMatrix3x3_serializeFloat(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public void deSerialize(btMatrix3x3FloatData btmatrix3x3floatdata) {
        LinearMathJNI.btMatrix3x3_deSerialize(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public void deSerializeFloat(btMatrix3x3FloatData btmatrix3x3floatdata) {
        LinearMathJNI.btMatrix3x3_deSerializeFloat(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public void deSerializeDouble(btMatrix3x3DoubleData btmatrix3x3doubledata) {
        LinearMathJNI.btMatrix3x3_deSerializeDouble(this.swigCPtr, this, btMatrix3x3DoubleData.getCPtr(btmatrix3x3doubledata), btmatrix3x3doubledata);
    }
}
