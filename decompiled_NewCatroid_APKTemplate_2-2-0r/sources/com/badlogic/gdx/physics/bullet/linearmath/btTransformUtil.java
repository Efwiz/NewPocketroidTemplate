package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTransformUtil extends BulletBase {
    private long swigCPtr;

    protected btTransformUtil(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTransformUtil(long j, boolean z) {
        this("btTransformUtil", j, z);
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

    public static long getCPtr(btTransformUtil bttransformutil) {
        if (bttransformutil == null) {
            return 0L;
        }
        return bttransformutil.swigCPtr;
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
                LinearMathJNI.delete_btTransformUtil(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static void integrateTransform(Matrix4 matrix4, Vector3 vector3, Vector3 vector4, float f, Matrix4 matrix5) {
        LinearMathJNI.btTransformUtil_integrateTransform(matrix4, vector3, vector4, f, matrix5);
    }

    public static void calculateVelocityQuaternion(Vector3 vector3, Vector3 vector4, Quaternion quaternion, Quaternion quaternion2, float f, Vector3 vector5, Vector3 vector6) {
        LinearMathJNI.btTransformUtil_calculateVelocityQuaternion(vector3, vector4, quaternion, quaternion2, f, vector5, vector6);
    }

    public static void calculateDiffAxisAngleQuaternion(Quaternion quaternion, Quaternion quaternion2, Vector3 vector3, SWIGTYPE_p_float sWIGTYPE_p_float) {
        LinearMathJNI.btTransformUtil_calculateDiffAxisAngleQuaternion(quaternion, quaternion2, vector3, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public static void calculateVelocity(Matrix4 matrix4, Matrix4 matrix5, float f, Vector3 vector3, Vector3 vector4) {
        LinearMathJNI.btTransformUtil_calculateVelocity(matrix4, matrix5, f, vector3, vector4);
    }

    public static void calculateDiffAxisAngle(Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, SWIGTYPE_p_float sWIGTYPE_p_float) {
        LinearMathJNI.btTransformUtil_calculateDiffAxisAngle(matrix4, matrix5, vector3, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public btTransformUtil() {
        this(LinearMathJNI.new_btTransformUtil(), true);
    }
}
