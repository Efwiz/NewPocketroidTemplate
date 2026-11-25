package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btDbvtAabbMm;
import com.badlogic.gdx.utils.Pool;

/* JADX INFO: loaded from: classes8.dex */
public class Softbody implements SoftbodyConstants {
    public static final Vector3 staticVector3 = new Vector3();
    public static final Pool<Vector3> poolVector3 = new Pool<Vector3>() { // from class: com.badlogic.gdx.physics.bullet.softbody.Softbody.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Vector3 newObject() {
            return new Vector3();
        }
    };
    public static final Quaternion staticQuaternion = new Quaternion();
    public static final Pool<Quaternion> poolQuaternion = new Pool<Quaternion>() { // from class: com.badlogic.gdx.physics.bullet.softbody.Softbody.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Quaternion newObject() {
            return new Quaternion();
        }
    };
    public static final Matrix3 staticMatrix3 = new Matrix3();
    public static final Pool<Matrix3> poolMatrix3 = new Pool<Matrix3>() { // from class: com.badlogic.gdx.physics.bullet.softbody.Softbody.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix3 newObject() {
            return new Matrix3();
        }
    };
    public static final Matrix4 staticMatrix4 = new Matrix4();
    public static final Pool<Matrix4> poolMatrix4 = new Pool<Matrix4>() { // from class: com.badlogic.gdx.physics.bullet.softbody.Softbody.4
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix4 newObject() {
            return new Matrix4();
        }
    };

    public static Matrix3 Lerp(Matrix3 matrix3, Matrix3 matrix4, float f) {
        return SoftbodyJNI.Lerp(matrix3, matrix4, f);
    }

    public static Vector3 Clamp(Vector3 vector3, float f) {
        return SoftbodyJNI.Clamp(vector3, f);
    }

    public static float ClusterMetric(Vector3 vector3, Vector3 vector4) {
        return SoftbodyJNI.ClusterMetric(vector3, vector4);
    }

    public static Matrix3 ScaleAlongAxis(Vector3 vector3, float f) {
        return SoftbodyJNI.ScaleAlongAxis(vector3, f);
    }

    public static Matrix3 Cross(Vector3 vector3) {
        return SoftbodyJNI.Cross(vector3);
    }

    public static Matrix3 Diagonal(float f) {
        return SoftbodyJNI.Diagonal(f);
    }

    public static Matrix3 Add(Matrix3 matrix3, Matrix3 matrix4) {
        return SoftbodyJNI.Add(matrix3, matrix4);
    }

    public static Matrix3 Sub(Matrix3 matrix3, Matrix3 matrix4) {
        return SoftbodyJNI.Sub(matrix3, matrix4);
    }

    public static Matrix3 Mul(Matrix3 matrix3, float f) {
        return SoftbodyJNI.Mul(matrix3, f);
    }

    public static void Orthogonalize(Matrix3 matrix3) {
        SoftbodyJNI.Orthogonalize(matrix3);
    }

    public static Matrix3 MassMatrix(float f, Matrix3 matrix3, Vector3 vector3) {
        return SoftbodyJNI.MassMatrix(f, matrix3, vector3);
    }

    public static Matrix3 ImpulseMatrix(float f, float f2, float f3, Matrix3 matrix3, Vector3 vector3) {
        return SoftbodyJNI.ImpulseMatrix__SWIG_0(f, f2, f3, matrix3, vector3);
    }

    public static Matrix3 ImpulseMatrix(float f, Matrix3 matrix3, Vector3 vector3, float f2, Matrix3 matrix4, Vector3 vector4) {
        return SoftbodyJNI.ImpulseMatrix__SWIG_1(f, matrix3, vector3, f2, matrix4, vector4);
    }

    public static Matrix3 AngularImpulseMatrix(Matrix3 matrix3, Matrix3 matrix4) {
        return SoftbodyJNI.AngularImpulseMatrix(matrix3, matrix4);
    }

    public static Vector3 ProjectOnAxis(Vector3 vector3, Vector3 vector4) {
        return SoftbodyJNI.ProjectOnAxis(vector3, vector4);
    }

    public static Vector3 ProjectOnPlane(Vector3 vector3, Vector3 vector4) {
        return SoftbodyJNI.ProjectOnPlane(vector3, vector4);
    }

    public static void ProjectOrigin(Vector3 vector3, Vector3 vector4, Vector3 vector5, SWIGTYPE_p_float sWIGTYPE_p_float) {
        SoftbodyJNI.ProjectOrigin__SWIG_0(vector3, vector4, vector5, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public static void ProjectOrigin(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, SWIGTYPE_p_float sWIGTYPE_p_float) {
        SoftbodyJNI.ProjectOrigin__SWIG_1(vector3, vector4, vector5, vector6, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public static Vector3 BaryCoord(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        return SoftbodyJNI.BaryCoord(vector3, vector4, vector5, vector6);
    }

    public static float ImplicitSolve(btSoftBody.ImplicitFn implicitFn, Vector3 vector3, Vector3 vector4, float f, int i) {
        return SoftbodyJNI.ImplicitSolve__SWIG_0(btSoftBody.ImplicitFn.getCPtr(implicitFn), implicitFn, vector3, vector4, f, i);
    }

    public static float ImplicitSolve(btSoftBody.ImplicitFn implicitFn, Vector3 vector3, Vector3 vector4, float f) {
        return SoftbodyJNI.ImplicitSolve__SWIG_1(btSoftBody.ImplicitFn.getCPtr(implicitFn), implicitFn, vector3, vector4, f);
    }

    public static void EvaluateMedium(btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, btSoftBody.sMedium smedium) {
        SoftbodyJNI.EvaluateMedium(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, vector3, btSoftBody.sMedium.getCPtr(smedium), smedium);
    }

    public static Vector3 NormalizeAny(Vector3 vector3) {
        return SoftbodyJNI.NormalizeAny(vector3);
    }

    public static btDbvtAabbMm VolumeOf(btSoftBody.Face face, float f) {
        return new btDbvtAabbMm(SoftbodyJNI.VolumeOf__SWIG_0(btSoftBody.Face.getCPtr(face), face, f), true);
    }

    public static Vector3 CenterOf(btSoftBody.Face face) {
        return SoftbodyJNI.CenterOf(btSoftBody.Face.getCPtr(face), face);
    }

    public static float AreaOf(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        return SoftbodyJNI.AreaOf(vector3, vector4, vector5);
    }

    public static float VolumeOf(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        return SoftbodyJNI.VolumeOf__SWIG_1(vector3, vector4, vector5, vector6);
    }

    public static void ApplyClampedForce(btSoftBody.Node node, Vector3 vector3, float f) {
        SoftbodyJNI.ApplyClampedForce(btSoftBody.Node.getCPtr(node), node, vector3, f);
    }

    public static int MatchEdge(btSoftBody.Node node, btSoftBody.Node node2, btSoftBody.Node node3, btSoftBody.Node node4) {
        return SoftbodyJNI.MatchEdge(btSoftBody.Node.getCPtr(node), node, btSoftBody.Node.getCPtr(node2), node2, btSoftBody.Node.getCPtr(node3), node3, btSoftBody.Node.getCPtr(node4), node4);
    }

    public static int PolarDecompose(Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5) {
        return SoftbodyJNI.PolarDecompose(matrix3, matrix4, matrix5);
    }
}
