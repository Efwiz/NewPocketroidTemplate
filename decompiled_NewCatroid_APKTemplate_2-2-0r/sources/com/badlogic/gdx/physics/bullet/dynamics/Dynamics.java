package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Pool;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class Dynamics implements DynamicsConstants {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Vector3 staticVector3 = new Vector3();
    public static final Pool<Vector3> poolVector3 = new Pool<Vector3>() { // from class: com.badlogic.gdx.physics.bullet.dynamics.Dynamics.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Vector3 newObject() {
            return new Vector3();
        }
    };
    public static final Quaternion staticQuaternion = new Quaternion();
    public static final Pool<Quaternion> poolQuaternion = new Pool<Quaternion>() { // from class: com.badlogic.gdx.physics.bullet.dynamics.Dynamics.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Quaternion newObject() {
            return new Quaternion();
        }
    };
    public static final Matrix3 staticMatrix3 = new Matrix3();
    public static final Pool<Matrix3> poolMatrix3 = new Pool<Matrix3>() { // from class: com.badlogic.gdx.physics.bullet.dynamics.Dynamics.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix3 newObject() {
            return new Matrix3();
        }
    };
    public static final Matrix4 staticMatrix4 = new Matrix4();
    public static final Pool<Matrix4> poolMatrix4 = new Pool<Matrix4>() { // from class: com.badlogic.gdx.physics.bullet.dynamics.Dynamics.4
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix4 newObject() {
            return new Matrix4();
        }
    };

    public static void setGDeactivationTime(float f) {
        DynamicsJNI.gDeactivationTime_set(f);
    }

    public static float getGDeactivationTime() {
        return DynamicsJNI.gDeactivationTime_get();
    }

    public static void setGDisableDeactivation(boolean z) {
        DynamicsJNI.gDisableDeactivation_set(z);
    }

    public static boolean getGDisableDeactivation() {
        return DynamicsJNI.gDisableDeactivation_get();
    }

    public static float btAdjustAngleToLimits(float f, float f2, float f3) {
        return DynamicsJNI.btAdjustAngleToLimits(f, f2, f3);
    }

    public static void InternalTickCallback_CB(btDynamicsWorld btdynamicsworld, float f) {
        DynamicsJNI.InternalTickCallback_CB(btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld, f);
    }

    public static float resolveSingleCollision(btRigidBody btrigidbody, btCollisionObject btcollisionobject, Vector3 vector3, Vector3 vector4, btContactSolverInfo btcontactsolverinfo, float f) {
        return DynamicsJNI.resolveSingleCollision(btRigidBody.getCPtr(btrigidbody), btrigidbody, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, vector3, vector4, btContactSolverInfo.getCPtr(btcontactsolverinfo), btcontactsolverinfo, f);
    }

    public static void resolveSingleBilateral(btRigidBody btrigidbody, Vector3 vector3, btRigidBody btrigidbody2, Vector3 vector4, float f, Vector3 vector5, SWIGTYPE_p_float sWIGTYPE_p_float, float f2) {
        DynamicsJNI.resolveSingleBilateral(btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector4, f, vector5, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), f2);
    }

    public static boolean btSolveDantzigLCP(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, FloatBuffer floatBuffer3, FloatBuffer floatBuffer4, int i2, FloatBuffer floatBuffer5, FloatBuffer floatBuffer6, IntBuffer intBuffer, btDantzigScratchMemory btdantzigscratchmemory) {
        return DynamicsJNI.btSolveDantzigLCP(i, floatBuffer, floatBuffer2, floatBuffer3, floatBuffer4, i2, floatBuffer5, floatBuffer6, intBuffer, btDantzigScratchMemory.getCPtr(btdantzigscratchmemory), btdantzigscratchmemory);
    }
}
