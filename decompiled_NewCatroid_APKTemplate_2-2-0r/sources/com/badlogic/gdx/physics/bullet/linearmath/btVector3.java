package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btVector3 extends BulletBase {
    private long swigCPtr;

    protected btVector3(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btVector3(long j, boolean z) {
        this("btVector3", j, z);
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

    public static long getCPtr(btVector3 btvector3) {
        if (btvector3 == null) {
            return 0L;
        }
        return btvector3.swigCPtr;
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
                LinearMathJNI.delete_btVector3(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return LinearMathJNI.btVector3_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        LinearMathJNI.btVector3_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return LinearMathJNI.btVector3_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        LinearMathJNI.btVector3_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return LinearMathJNI.btVector3_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        LinearMathJNI.btVector3_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return LinearMathJNI.btVector3_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        LinearMathJNI.btVector3_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setFloats(float[] fArr) {
        LinearMathJNI.btVector3_floats_set(this.swigCPtr, this, fArr);
    }

    public float[] getFloats() {
        return LinearMathJNI.btVector3_floats_get(this.swigCPtr, this);
    }

    public btVector3() {
        this(LinearMathJNI.new_btVector3__SWIG_0(), true);
    }

    public btVector3(float f, float f2, float f3) {
        this(LinearMathJNI.new_btVector3__SWIG_1(f, f2, f3), true);
    }

    public Vector3 operatorAdditionAssignment(Vector3 vector3) {
        return LinearMathJNI.btVector3_operatorAdditionAssignment(this.swigCPtr, this, vector3);
    }

    public Vector3 operatorSubtractionAssignment(Vector3 vector3) {
        return LinearMathJNI.btVector3_operatorSubtractionAssignment(this.swigCPtr, this, vector3);
    }

    public Vector3 operatorMultiplicationAssignment(float f) {
        return LinearMathJNI.btVector3_operatorMultiplicationAssignment__SWIG_0(this.swigCPtr, this, f);
    }

    public Vector3 operatorDivisionAssignment(float f) {
        return LinearMathJNI.btVector3_operatorDivisionAssignment(this.swigCPtr, this, f);
    }

    public float dot(Vector3 vector3) {
        return LinearMathJNI.btVector3_dot(this.swigCPtr, this, vector3);
    }

    public float length2() {
        return LinearMathJNI.btVector3_length2(this.swigCPtr, this);
    }

    public float length() {
        return LinearMathJNI.btVector3_length(this.swigCPtr, this);
    }

    public float norm() {
        return LinearMathJNI.btVector3_norm(this.swigCPtr, this);
    }

    public float safeNorm() {
        return LinearMathJNI.btVector3_safeNorm(this.swigCPtr, this);
    }

    public float distance2(Vector3 vector3) {
        return LinearMathJNI.btVector3_distance2(this.swigCPtr, this, vector3);
    }

    public float distance(Vector3 vector3) {
        return LinearMathJNI.btVector3_distance(this.swigCPtr, this, vector3);
    }

    public Vector3 safeNormalize() {
        return LinearMathJNI.btVector3_safeNormalize(this.swigCPtr, this);
    }

    public Vector3 normalize() {
        return LinearMathJNI.btVector3_normalize(this.swigCPtr, this);
    }

    public Vector3 normalized() {
        return LinearMathJNI.btVector3_normalized(this.swigCPtr, this);
    }

    public Vector3 rotate(Vector3 vector3, float f) {
        return LinearMathJNI.btVector3_rotate(this.swigCPtr, this, vector3, f);
    }

    public float angle(Vector3 vector3) {
        return LinearMathJNI.btVector3_angle(this.swigCPtr, this, vector3);
    }

    public Vector3 absolute() {
        return LinearMathJNI.btVector3_absolute(this.swigCPtr, this);
    }

    public Vector3 cross(Vector3 vector3) {
        return LinearMathJNI.btVector3_cross(this.swigCPtr, this, vector3);
    }

    public float triple(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.btVector3_triple(this.swigCPtr, this, vector3, vector4);
    }

    public int minAxis() {
        return LinearMathJNI.btVector3_minAxis(this.swigCPtr, this);
    }

    public int maxAxis() {
        return LinearMathJNI.btVector3_maxAxis(this.swigCPtr, this);
    }

    public int furthestAxis() {
        return LinearMathJNI.btVector3_furthestAxis(this.swigCPtr, this);
    }

    public int closestAxis() {
        return LinearMathJNI.btVector3_closestAxis(this.swigCPtr, this);
    }

    public void setInterpolate3(Vector3 vector3, Vector3 vector4, float f) {
        LinearMathJNI.btVector3_setInterpolate3(this.swigCPtr, this, vector3, vector4, f);
    }

    public Vector3 lerp(Vector3 vector3, float f) {
        return LinearMathJNI.btVector3_lerp(this.swigCPtr, this, vector3, f);
    }

    public Vector3 operatorMultiplicationAssignment(Vector3 vector3) {
        return LinearMathJNI.btVector3_operatorMultiplicationAssignment__SWIG_1(this.swigCPtr, this, vector3);
    }

    public float getX() {
        return LinearMathJNI.btVector3_getX(this.swigCPtr, this);
    }

    public float getY() {
        return LinearMathJNI.btVector3_getY(this.swigCPtr, this);
    }

    public float getZ() {
        return LinearMathJNI.btVector3_getZ(this.swigCPtr, this);
    }

    public void setX(float f) {
        LinearMathJNI.btVector3_setX(this.swigCPtr, this, f);
    }

    public void setY(float f) {
        LinearMathJNI.btVector3_setY(this.swigCPtr, this, f);
    }

    public void setZ(float f) {
        LinearMathJNI.btVector3_setZ(this.swigCPtr, this, f);
    }

    public void setW(float f) {
        LinearMathJNI.btVector3_setW(this.swigCPtr, this, f);
    }

    public float x() {
        return LinearMathJNI.btVector3_x(this.swigCPtr, this);
    }

    public float y() {
        return LinearMathJNI.btVector3_y(this.swigCPtr, this);
    }

    public float z() {
        return LinearMathJNI.btVector3_z(this.swigCPtr, this);
    }

    public float w() {
        return LinearMathJNI.btVector3_w(this.swigCPtr, this);
    }

    public FloatBuffer operatorbtScalarPtr() {
        return LinearMathJNI.btVector3_operatorbtScalarPtr(this.swigCPtr, this);
    }

    public FloatBuffer operatorbtConstScalarPtr() {
        return LinearMathJNI.btVector3_operatorbtConstScalarPtr(this.swigCPtr, this);
    }

    public boolean operatorEqualTo(Vector3 vector3) {
        return LinearMathJNI.btVector3_operatorEqualTo(this.swigCPtr, this, vector3);
    }

    public boolean operatorNotEqualTo(Vector3 vector3) {
        return LinearMathJNI.btVector3_operatorNotEqualTo(this.swigCPtr, this, vector3);
    }

    public void setMax(Vector3 vector3) {
        LinearMathJNI.btVector3_setMax(this.swigCPtr, this, vector3);
    }

    public void setMin(Vector3 vector3) {
        LinearMathJNI.btVector3_setMin(this.swigCPtr, this, vector3);
    }

    public void setValue(float f, float f2, float f3) {
        LinearMathJNI.btVector3_setValue(this.swigCPtr, this, f, f2, f3);
    }

    public void getSkewSymmetricMatrix(btVector3 btvector3, btVector3 btvector4, btVector3 btvector5) {
        LinearMathJNI.btVector3_getSkewSymmetricMatrix(this.swigCPtr, this, getCPtr(btvector3), btvector3, getCPtr(btvector4), btvector4, getCPtr(btvector5), btvector5);
    }

    public void setZero() {
        LinearMathJNI.btVector3_setZero(this.swigCPtr, this);
    }

    public boolean isZero() {
        return LinearMathJNI.btVector3_isZero(this.swigCPtr, this);
    }

    public boolean fuzzyZero() {
        return LinearMathJNI.btVector3_fuzzyZero(this.swigCPtr, this);
    }

    public void serialize(btVector3FloatData btvector3floatdata) {
        LinearMathJNI.btVector3_serialize(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public void deSerialize(btVector3FloatData btvector3floatdata) {
        LinearMathJNI.btVector3_deSerialize(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public void serializeFloat(btVector3FloatData btvector3floatdata) {
        LinearMathJNI.btVector3_serializeFloat(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public void deSerializeFloat(btVector3FloatData btvector3floatdata) {
        LinearMathJNI.btVector3_deSerializeFloat(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public void serializeDouble(btVector3DoubleData btvector3doubledata) {
        LinearMathJNI.btVector3_serializeDouble(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public void deSerializeDouble(btVector3DoubleData btvector3doubledata) {
        LinearMathJNI.btVector3_deSerializeDouble(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public int maxDot(btVector3 btvector3, int i, SWIGTYPE_p_float sWIGTYPE_p_float) {
        return LinearMathJNI.btVector3_maxDot(this.swigCPtr, this, getCPtr(btvector3), btvector3, i, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public int minDot(btVector3 btvector3, int i, SWIGTYPE_p_float sWIGTYPE_p_float) {
        return LinearMathJNI.btVector3_minDot(this.swigCPtr, this, getCPtr(btvector3), btvector3, i, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public Vector3 dot3(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        return LinearMathJNI.btVector3_dot3(this.swigCPtr, this, vector3, vector4, vector5);
    }
}
