package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btQuaternion extends btQuadWord {
    private long swigCPtr;

    protected btQuaternion(String str, long j, boolean z) {
        super(str, LinearMathJNI.btQuaternion_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btQuaternion(long j, boolean z) {
        this("btQuaternion", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btQuadWord, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(LinearMathJNI.btQuaternion_SWIGUpcast(j), z);
    }

    public static long getCPtr(btQuaternion btquaternion) {
        if (btquaternion == null) {
            return 0L;
        }
        return btquaternion.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btQuadWord, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btQuadWord, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                LinearMathJNI.delete_btQuaternion(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btQuaternion() {
        this(LinearMathJNI.new_btQuaternion__SWIG_0(), true);
    }

    public btQuaternion(float f, float f2, float f3, float f4) {
        this(LinearMathJNI.new_btQuaternion__SWIG_1(f, f2, f3, f4), true);
    }

    public btQuaternion(Vector3 vector3, float f) {
        this(LinearMathJNI.new_btQuaternion__SWIG_2(vector3, f), true);
    }

    public btQuaternion(float f, float f2, float f3) {
        this(LinearMathJNI.new_btQuaternion__SWIG_3(f, f2, f3), true);
    }

    public void setRotation(Vector3 vector3, float f) {
        LinearMathJNI.btQuaternion_setRotation(this.swigCPtr, this, vector3, f);
    }

    public void setEuler(float f, float f2, float f3) {
        LinearMathJNI.btQuaternion_setEuler(this.swigCPtr, this, f, f2, f3);
    }

    public void setEulerZYX(float f, float f2, float f3) {
        LinearMathJNI.btQuaternion_setEulerZYX(this.swigCPtr, this, f, f2, f3);
    }

    public void getEulerZYX(SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2, SWIGTYPE_p_float sWIGTYPE_p_float3) {
        LinearMathJNI.btQuaternion_getEulerZYX(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float3));
    }

    public Quaternion operatorAdditionAssignment(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_operatorAdditionAssignment(this.swigCPtr, this, quaternion);
    }

    public Quaternion operatorSubtractionAssignment(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_operatorSubtractionAssignment(this.swigCPtr, this, quaternion);
    }

    public Quaternion operatorMultiplicationAssignment(float f) {
        return LinearMathJNI.btQuaternion_operatorMultiplicationAssignment__SWIG_0(this.swigCPtr, this, f);
    }

    public Quaternion operatorMultiplicationAssignment(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_operatorMultiplicationAssignment__SWIG_1(this.swigCPtr, this, quaternion);
    }

    public float dot(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_dot(this.swigCPtr, this, quaternion);
    }

    public float length2() {
        return LinearMathJNI.btQuaternion_length2(this.swigCPtr, this);
    }

    public float length() {
        return LinearMathJNI.btQuaternion_length(this.swigCPtr, this);
    }

    public Quaternion safeNormalize() {
        return LinearMathJNI.btQuaternion_safeNormalize(this.swigCPtr, this);
    }

    public Quaternion normalize() {
        return LinearMathJNI.btQuaternion_normalize(this.swigCPtr, this);
    }

    public Quaternion operatorMultiplication(float f) {
        return LinearMathJNI.btQuaternion_operatorMultiplication(this.swigCPtr, this, f);
    }

    public Quaternion operatorDivision(float f) {
        return LinearMathJNI.btQuaternion_operatorDivision(this.swigCPtr, this, f);
    }

    public Quaternion operatorDivisionAssignment(float f) {
        return LinearMathJNI.btQuaternion_operatorDivisionAssignment(this.swigCPtr, this, f);
    }

    public Quaternion normalized() {
        return LinearMathJNI.btQuaternion_normalized(this.swigCPtr, this);
    }

    public float angle(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_angle(this.swigCPtr, this, quaternion);
    }

    public float angleShortestPath(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_angleShortestPath(this.swigCPtr, this, quaternion);
    }

    public float getAngle() {
        return LinearMathJNI.btQuaternion_getAngle(this.swigCPtr, this);
    }

    public float getAngleShortestPath() {
        return LinearMathJNI.btQuaternion_getAngleShortestPath(this.swigCPtr, this);
    }

    public Vector3 getAxis() {
        return LinearMathJNI.btQuaternion_getAxis(this.swigCPtr, this);
    }

    public Quaternion inverse() {
        return LinearMathJNI.btQuaternion_inverse(this.swigCPtr, this);
    }

    public Quaternion operatorAddition(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_operatorAddition(this.swigCPtr, this, quaternion);
    }

    public Quaternion operatorSubtraction(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_operatorSubtraction__SWIG_0(this.swigCPtr, this, quaternion);
    }

    public Quaternion operatorSubtraction() {
        return LinearMathJNI.btQuaternion_operatorSubtraction__SWIG_1(this.swigCPtr, this);
    }

    public Quaternion farthest(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_farthest(this.swigCPtr, this, quaternion);
    }

    public Quaternion nearest(Quaternion quaternion) {
        return LinearMathJNI.btQuaternion_nearest(this.swigCPtr, this, quaternion);
    }

    public Quaternion slerp(Quaternion quaternion, float f) {
        return LinearMathJNI.btQuaternion_slerp(this.swigCPtr, this, quaternion, f);
    }

    public static Quaternion getIdentity() {
        return LinearMathJNI.btQuaternion_getIdentity();
    }

    public float getW() {
        return LinearMathJNI.btQuaternion_getW(this.swigCPtr, this);
    }

    public void serialize(btQuaternionFloatData btquaternionfloatdata) {
        LinearMathJNI.btQuaternion_serialize(this.swigCPtr, this, btQuaternionFloatData.getCPtr(btquaternionfloatdata), btquaternionfloatdata);
    }

    public void deSerialize(btQuaternionFloatData btquaternionfloatdata) {
        LinearMathJNI.btQuaternion_deSerialize(this.swigCPtr, this, btQuaternionFloatData.getCPtr(btquaternionfloatdata), btquaternionfloatdata);
    }

    public void serializeFloat(btQuaternionFloatData btquaternionfloatdata) {
        LinearMathJNI.btQuaternion_serializeFloat(this.swigCPtr, this, btQuaternionFloatData.getCPtr(btquaternionfloatdata), btquaternionfloatdata);
    }

    public void deSerializeFloat(btQuaternionFloatData btquaternionfloatdata) {
        LinearMathJNI.btQuaternion_deSerializeFloat(this.swigCPtr, this, btQuaternionFloatData.getCPtr(btquaternionfloatdata), btquaternionfloatdata);
    }

    public void serializeDouble(btQuaternionDoubleData btquaterniondoubledata) {
        LinearMathJNI.btQuaternion_serializeDouble(this.swigCPtr, this, btQuaternionDoubleData.getCPtr(btquaterniondoubledata), btquaterniondoubledata);
    }

    public void deSerializeDouble(btQuaternionDoubleData btquaterniondoubledata) {
        LinearMathJNI.btQuaternion_deSerializeDouble(this.swigCPtr, this, btQuaternionDoubleData.getCPtr(btquaterniondoubledata), btquaterniondoubledata);
    }
}
