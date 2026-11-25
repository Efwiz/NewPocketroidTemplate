package com.badlogic.gdx.physics.bullet.inversedynamics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class InverseDynamicsJNI {
    public static final native int MultiBodyTree_addBody(long j, MultiBodyTree multiBodyTree, int i, int i2, int i3, long j2, long j3, long j4, float f, long j5, long j6, int i4, long j7);

    public static final native int MultiBodyTree_addUserForce(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_addUserMoment(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_calculateInverseDynamics(long j, MultiBodyTree multiBodyTree, long j2, long j3, long j4, long j5);

    public static final native int MultiBodyTree_calculateKinematics(long j, MultiBodyTree multiBodyTree, long j2, long j3, long j4);

    public static final native int MultiBodyTree_calculateMassMatrix__SWIG_0(long j, MultiBodyTree multiBodyTree, long j2, boolean z, boolean z2, boolean z3, long j3);

    public static final native int MultiBodyTree_calculateMassMatrix__SWIG_1(long j, MultiBodyTree multiBodyTree, long j2, long j3);

    public static final native int MultiBodyTree_calculatePositionAndVelocityKinematics(long j, MultiBodyTree multiBodyTree, long j2, long j3);

    public static final native int MultiBodyTree_calculatePositionKinematics(long j, MultiBodyTree multiBodyTree, long j2);

    public static final native void MultiBodyTree_clearAllUserForcesAndMoments(long j, MultiBodyTree multiBodyTree);

    public static final native int MultiBodyTree_finalizeInternal(long j, MultiBodyTree multiBodyTree);

    public static final native boolean MultiBodyTree_getAcceptInvalidMassProperties(long j, MultiBodyTree multiBodyTree);

    public static final native int MultiBodyTree_getBodyAngularAcceleration(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyAngularVelocity(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyAxisOfMotion(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyCoM(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyFirstMassMoment(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyLinearAcceleration(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyLinearVelocity(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyLinearVelocityCoM(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyMass(long j, MultiBodyTree multiBodyTree, int i, FloatBuffer floatBuffer);

    public static final native int MultiBodyTree_getBodyOrigin(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodySecondMassMoment(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyTParentRef(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getBodyTransform(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getDoFOffset(long j, MultiBodyTree multiBodyTree, int i, IntBuffer intBuffer);

    public static final native int MultiBodyTree_getJointType(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getJointTypeStr(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getParentIndex(long j, MultiBodyTree multiBodyTree, int i, IntBuffer intBuffer);

    public static final native int MultiBodyTree_getParentRParentBodyRef(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_getUserInt(long j, MultiBodyTree multiBodyTree, int i, IntBuffer intBuffer);

    public static final native int MultiBodyTree_getUserPtr(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_numBodies(long j, MultiBodyTree multiBodyTree);

    public static final native int MultiBodyTree_numDoFs(long j, MultiBodyTree multiBodyTree);

    public static final native void MultiBodyTree_operatorDeleteArray__SWIG_0(long j, MultiBodyTree multiBodyTree, long j2);

    public static final native void MultiBodyTree_operatorDeleteArray__SWIG_1(long j, MultiBodyTree multiBodyTree, long j2, long j3);

    public static final native void MultiBodyTree_operatorDelete__SWIG_0(long j, MultiBodyTree multiBodyTree, long j2);

    public static final native void MultiBodyTree_operatorDelete__SWIG_1(long j, MultiBodyTree multiBodyTree, long j2, long j3);

    public static final native long MultiBodyTree_operatorNewArray__SWIG_0(long j, MultiBodyTree multiBodyTree, long j2);

    public static final native long MultiBodyTree_operatorNewArray__SWIG_1(long j, MultiBodyTree multiBodyTree, long j2, long j3);

    public static final native long MultiBodyTree_operatorNew__SWIG_0(long j, MultiBodyTree multiBodyTree, long j2);

    public static final native long MultiBodyTree_operatorNew__SWIG_1(long j, MultiBodyTree multiBodyTree, long j2, long j3);

    public static final native void MultiBodyTree_printTree(long j, MultiBodyTree multiBodyTree);

    public static final native void MultiBodyTree_printTreeData(long j, MultiBodyTree multiBodyTree);

    public static final native void MultiBodyTree_setAcceptInvalidMassParameters(long j, MultiBodyTree multiBodyTree, boolean z);

    public static final native int MultiBodyTree_setBodyFirstMassMoment(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_setBodyMass(long j, MultiBodyTree multiBodyTree, int i, float f);

    public static final native int MultiBodyTree_setBodySecondMassMoment(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native int MultiBodyTree_setGravityInWorldFrame(long j, MultiBodyTree multiBodyTree, long j2);

    public static final native int MultiBodyTree_setUserInt(long j, MultiBodyTree multiBodyTree, int i, int i2);

    public static final native int MultiBodyTree_setUserPtr(long j, MultiBodyTree multiBodyTree, int i, long j2);

    public static final native void bodyTParentFromAxisAngle(long j, float f, long j2);

    public static final native void delete_MultiBodyTree(long j);

    public static final native float determinant(long j);

    public static final native void getVecMatFromDH(float f, float f2, float f3, float f4, long j, long j2);

    public static final native boolean isPositiveDefinite(long j);

    public static final native boolean isPositiveSemiDefinite(long j);

    public static final native boolean isPositiveSemiDefiniteFuzzy(long j);

    public static final native boolean isUnitVector(long j);

    public static final native boolean isValidInertiaMatrix(long j, int i, boolean z);

    public static final native boolean isValidTransformMatrix(long j);

    public static final native float maxAbs__SWIG_0(long j);

    public static final native float maxAbs__SWIG_1(long j);

    public static final native long new_MultiBodyTree();

    public static final native long rpyFromMatrix(long j);

    public static final native void setZero__SWIG_0(long j);

    public static final native void setZero__SWIG_1(long j);

    public static final native void setZero__SWIG_2(long j);

    public static final native void skew(long j, long j2);

    public static final native long tildeOperator(long j);

    public static final native long transformX(float f);

    public static final native long transformY(float f);

    public static final native long transformZ(float f);
}
