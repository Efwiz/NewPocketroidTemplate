package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTransform extends BulletBase {
    private long swigCPtr;

    protected btTransform(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTransform(long j, boolean z) {
        this("btTransform", j, z);
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

    public static long getCPtr(btTransform bttransform) {
        if (bttransform == null) {
            return 0L;
        }
        return bttransform.swigCPtr;
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
                LinearMathJNI.delete_btTransform(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btTransform() {
        this(LinearMathJNI.new_btTransform__SWIG_0(), true);
    }

    public btTransform(Quaternion quaternion, Vector3 vector3) {
        this(LinearMathJNI.new_btTransform__SWIG_1(quaternion, vector3), true);
    }

    public btTransform(Quaternion quaternion) {
        this(LinearMathJNI.new_btTransform__SWIG_2(quaternion), true);
    }

    public btTransform(Matrix3 matrix3, Vector3 vector3) {
        this(LinearMathJNI.new_btTransform__SWIG_3(matrix3, vector3), true);
    }

    public btTransform(Matrix3 matrix3) {
        this(LinearMathJNI.new_btTransform__SWIG_4(matrix3), true);
    }

    public btTransform(Matrix4 matrix4) {
        this(LinearMathJNI.new_btTransform__SWIG_5(matrix4), true);
    }

    public Matrix4 operatorAssignment(Matrix4 matrix4) {
        return LinearMathJNI.btTransform_operatorAssignment(this.swigCPtr, this, matrix4);
    }

    public void mult(Matrix4 matrix4, Matrix4 matrix5) {
        LinearMathJNI.btTransform_mult(this.swigCPtr, this, matrix4, matrix5);
    }

    public Vector3 operatorFunctionCall(Vector3 vector3) {
        return LinearMathJNI.btTransform_operatorFunctionCall(this.swigCPtr, this, vector3);
    }

    public Vector3 operatorMultiplication(Vector3 vector3) {
        return LinearMathJNI.btTransform_operatorMultiplication__SWIG_0(this.swigCPtr, this, vector3);
    }

    public Quaternion operatorMultiplication(Quaternion quaternion) {
        return LinearMathJNI.btTransform_operatorMultiplication__SWIG_1(this.swigCPtr, this, quaternion);
    }

    public Matrix3 getBasis() {
        return LinearMathJNI.btTransform_getBasis(this.swigCPtr, this);
    }

    public Matrix3 getBasisConst() {
        return LinearMathJNI.btTransform_getBasisConst(this.swigCPtr, this);
    }

    public Vector3 getOrigin() {
        return LinearMathJNI.btTransform_getOrigin(this.swigCPtr, this);
    }

    public Vector3 getOriginConst() {
        return LinearMathJNI.btTransform_getOriginConst(this.swigCPtr, this);
    }

    public Quaternion getRotation() {
        return LinearMathJNI.btTransform_getRotation(this.swigCPtr, this);
    }

    public void setFromOpenGLMatrix(float[] fArr) {
        LinearMathJNI.btTransform_setFromOpenGLMatrix(this.swigCPtr, this, fArr);
    }

    public void getOpenGLMatrix(float[] fArr) {
        LinearMathJNI.btTransform_getOpenGLMatrix(this.swigCPtr, this, fArr);
    }

    public void setOrigin(Vector3 vector3) {
        LinearMathJNI.btTransform_setOrigin(this.swigCPtr, this, vector3);
    }

    public Vector3 invXform(Vector3 vector3) {
        return LinearMathJNI.btTransform_invXform(this.swigCPtr, this, vector3);
    }

    public void setBasis(Matrix3 matrix3) {
        LinearMathJNI.btTransform_setBasis(this.swigCPtr, this, matrix3);
    }

    public void setRotation(Quaternion quaternion) {
        LinearMathJNI.btTransform_setRotation(this.swigCPtr, this, quaternion);
    }

    public void setIdentity() {
        LinearMathJNI.btTransform_setIdentity(this.swigCPtr, this);
    }

    public Matrix4 operatorMultiplicationAssignment(Matrix4 matrix4) {
        return LinearMathJNI.btTransform_operatorMultiplicationAssignment(this.swigCPtr, this, matrix4);
    }

    public Matrix4 inverse() {
        return LinearMathJNI.btTransform_inverse(this.swigCPtr, this);
    }

    public Matrix4 inverseTimes(Matrix4 matrix4) {
        return LinearMathJNI.btTransform_inverseTimes(this.swigCPtr, this, matrix4);
    }

    public Matrix4 operatorMultiplication(Matrix4 matrix4) {
        return LinearMathJNI.btTransform_operatorMultiplication__SWIG_2(this.swigCPtr, this, matrix4);
    }

    public static Matrix4 getIdentity() {
        return LinearMathJNI.btTransform_getIdentity();
    }

    public void serialize(btTransformFloatData bttransformfloatdata) {
        LinearMathJNI.btTransform_serialize(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public void serializeFloat(btTransformFloatData bttransformfloatdata) {
        LinearMathJNI.btTransform_serializeFloat(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public void deSerialize(btTransformFloatData bttransformfloatdata) {
        LinearMathJNI.btTransform_deSerialize(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public void deSerializeDouble(btTransformDoubleData bttransformdoubledata) {
        LinearMathJNI.btTransform_deSerializeDouble(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public void deSerializeFloat(btTransformFloatData bttransformfloatdata) {
        LinearMathJNI.btTransform_deSerializeFloat(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }
}
