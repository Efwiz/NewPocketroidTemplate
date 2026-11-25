package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btMultiBody;
import com.badlogic.gdx.physics.bullet.inversedynamics.MultiBodyTree;
import com.badlogic.gdx.utils.Pool;
import java.nio.DoubleBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class Extras {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Vector3 staticVector3 = new Vector3();
    public static final Pool<Vector3> poolVector3 = new Pool<Vector3>() { // from class: com.badlogic.gdx.physics.bullet.extras.Extras.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Vector3 newObject() {
            return new Vector3();
        }
    };
    public static final Quaternion staticQuaternion = new Quaternion();
    public static final Pool<Quaternion> poolQuaternion = new Pool<Quaternion>() { // from class: com.badlogic.gdx.physics.bullet.extras.Extras.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Quaternion newObject() {
            return new Quaternion();
        }
    };
    public static final Matrix3 staticMatrix3 = new Matrix3();
    public static final Pool<Matrix3> poolMatrix3 = new Pool<Matrix3>() { // from class: com.badlogic.gdx.physics.bullet.extras.Extras.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix3 newObject() {
            return new Matrix3();
        }
    };
    public static final Matrix4 staticMatrix4 = new Matrix4();
    public static final Pool<Matrix4> poolMatrix4 = new Pool<Matrix4>() { // from class: com.badlogic.gdx.physics.bullet.extras.Extras.4
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix4 newObject() {
            return new Matrix4();
        }
    };

    public static MultiBodyTree CreateMultiBodyTree(MultiBodyTreeCreator multiBodyTreeCreator) {
        long jCreateMultiBodyTree = ExtrasJNI.CreateMultiBodyTree(MultiBodyTreeCreator.getCPtr(multiBodyTreeCreator), multiBodyTreeCreator);
        if (jCreateMultiBodyTree == 0) {
            return null;
        }
        return new MultiBodyTree(jCreateMultiBodyTree, false);
    }

    public static void randomInit() {
        ExtrasJNI.randomInit__SWIG_0();
    }

    public static void randomInit(long j) {
        ExtrasJNI.randomInit__SWIG_1(j);
    }

    public static int randomInt(int i, int i2) {
        return ExtrasJNI.randomInt(i, i2);
    }

    public static float randomFloat(float f, float f2) {
        return ExtrasJNI.randomFloat(f, f2);
    }

    public static float randomMass() {
        return ExtrasJNI.randomMass();
    }

    public static SWIGTYPE_p_vec3 randomInertiaPrincipal() {
        return new SWIGTYPE_p_vec3(ExtrasJNI.randomInertiaPrincipal(), true);
    }

    public static SWIGTYPE_p_mat33 randomInertiaMatrix() {
        return new SWIGTYPE_p_mat33(ExtrasJNI.randomInertiaMatrix(), true);
    }

    public static SWIGTYPE_p_vec3 randomAxis() {
        return new SWIGTYPE_p_vec3(ExtrasJNI.randomAxis(), true);
    }

    public static int writeGraphvizDotFile(MultiBodyTree multiBodyTree, MultiBodyNameMap multiBodyNameMap, String str) {
        return ExtrasJNI.writeGraphvizDotFile(MultiBodyTree.getCPtr(multiBodyTree), multiBodyTree, MultiBodyNameMap.getCPtr(multiBodyNameMap), multiBodyNameMap, str);
    }

    public static int compareInverseAndForwardDynamics(SWIGTYPE_p_vecx sWIGTYPE_p_vecx, SWIGTYPE_p_vecx sWIGTYPE_p_vecx2, SWIGTYPE_p_vecx sWIGTYPE_p_vecx3, Vector3 vector3, boolean z, btMultiBody btmultibody, MultiBodyTree multiBodyTree, DoubleBuffer doubleBuffer, DoubleBuffer doubleBuffer2) {
        return ExtrasJNI.compareInverseAndForwardDynamics(SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx), SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx2), SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx3), vector3, z, btMultiBody.getCPtr(btmultibody), btmultibody, MultiBodyTree.getCPtr(multiBodyTree), multiBodyTree, doubleBuffer, doubleBuffer2);
    }
}
