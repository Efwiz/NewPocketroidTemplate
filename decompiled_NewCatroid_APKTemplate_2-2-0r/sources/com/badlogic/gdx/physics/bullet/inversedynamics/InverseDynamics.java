package com.badlogic.gdx.physics.bullet.inversedynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

/* JADX INFO: loaded from: classes8.dex */
public class InverseDynamics {
    public static final Vector3 staticVector3 = new Vector3();
    public static final Pool<Vector3> poolVector3 = new Pool<Vector3>() { // from class: com.badlogic.gdx.physics.bullet.inversedynamics.InverseDynamics.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Vector3 newObject() {
            return new Vector3();
        }
    };
    public static final Quaternion staticQuaternion = new Quaternion();
    public static final Pool<Quaternion> poolQuaternion = new Pool<Quaternion>() { // from class: com.badlogic.gdx.physics.bullet.inversedynamics.InverseDynamics.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Quaternion newObject() {
            return new Quaternion();
        }
    };
    public static final Matrix3 staticMatrix3 = new Matrix3();
    public static final Pool<Matrix3> poolMatrix3 = new Pool<Matrix3>() { // from class: com.badlogic.gdx.physics.bullet.inversedynamics.InverseDynamics.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix3 newObject() {
            return new Matrix3();
        }
    };
    public static final Matrix4 staticMatrix4 = new Matrix4();
    public static final Pool<Matrix4> poolMatrix4 = new Pool<Matrix4>() { // from class: com.badlogic.gdx.physics.bullet.inversedynamics.InverseDynamics.4
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix4 newObject() {
            return new Matrix4();
        }
    };

    public static void setZero(SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        InverseDynamicsJNI.setZero__SWIG_0(SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public static void setZero(SWIGTYPE_p_vecx sWIGTYPE_p_vecx) {
        InverseDynamicsJNI.setZero__SWIG_1(SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx));
    }

    public static void setZero(SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        InverseDynamicsJNI.setZero__SWIG_2(SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public static void skew(SWIGTYPE_p_vec3 sWIGTYPE_p_vec3, SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        InverseDynamicsJNI.skew(SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3), SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public static float maxAbs(SWIGTYPE_p_vecx sWIGTYPE_p_vecx) {
        return InverseDynamicsJNI.maxAbs__SWIG_0(SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx));
    }

    public static float maxAbs(SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.maxAbs__SWIG_1(SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public static void getVecMatFromDH(float f, float f2, float f3, float f4, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3, SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        InverseDynamicsJNI.getVecMatFromDH(f, f2, f3, f4, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3), SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public static boolean isPositiveDefinite(SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return InverseDynamicsJNI.isPositiveDefinite(SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public static boolean isPositiveSemiDefinite(SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return InverseDynamicsJNI.isPositiveSemiDefinite(SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public static boolean isPositiveSemiDefiniteFuzzy(SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return InverseDynamicsJNI.isPositiveSemiDefiniteFuzzy(SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public static float determinant(SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return InverseDynamicsJNI.determinant(SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public static boolean isValidInertiaMatrix(SWIGTYPE_p_mat33 sWIGTYPE_p_mat33, int i, boolean z) {
        return InverseDynamicsJNI.isValidInertiaMatrix(SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33), i, z);
    }

    public static boolean isValidTransformMatrix(SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return InverseDynamicsJNI.isValidTransformMatrix(SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public static void bodyTParentFromAxisAngle(SWIGTYPE_p_vec3 sWIGTYPE_p_vec3, float f, SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        InverseDynamicsJNI.bodyTParentFromAxisAngle(SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3), f, SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public static boolean isUnitVector(SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.isUnitVector(SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public static SWIGTYPE_p_mat33 tildeOperator(SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return new SWIGTYPE_p_mat33(InverseDynamicsJNI.tildeOperator(SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3)), true);
    }

    public static SWIGTYPE_p_mat33 transformX(float f) {
        return new SWIGTYPE_p_mat33(InverseDynamicsJNI.transformX(f), true);
    }

    public static SWIGTYPE_p_mat33 transformY(float f) {
        return new SWIGTYPE_p_mat33(InverseDynamicsJNI.transformY(f), true);
    }

    public static SWIGTYPE_p_mat33 transformZ(float f) {
        return new SWIGTYPE_p_mat33(InverseDynamicsJNI.transformZ(f), true);
    }

    public static SWIGTYPE_p_vec3 rpyFromMatrix(SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return new SWIGTYPE_p_vec3(InverseDynamicsJNI.rpyFromMatrix(SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33)), true);
    }
}
