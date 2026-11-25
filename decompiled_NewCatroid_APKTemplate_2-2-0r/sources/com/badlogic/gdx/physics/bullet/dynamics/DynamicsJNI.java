package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectArray;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectDoubleData;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectFloatData;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifoldArray;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3DoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3FloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.physics.bullet.linearmath.btQuaternion;
import com.badlogic.gdx.physics.bullet.linearmath.btQuaternionDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btQuaternionFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btScalarArray;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;
import com.badlogic.gdx.physics.bullet.linearmath.btSpatialForceVector;
import com.badlogic.gdx.physics.bullet.linearmath.btSpatialMotionVector;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3Array;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class DynamicsJNI {
    public static final native long CustomActionInterface_SWIGUpcast(long j);

    public static final native void CustomActionInterface_change_ownership(CustomActionInterface customActionInterface, long j, boolean z);

    public static final native void CustomActionInterface_debugDraw(long j, CustomActionInterface customActionInterface);

    public static final native void CustomActionInterface_director_connect(CustomActionInterface customActionInterface, long j, boolean z, boolean z2);

    public static final native void CustomActionInterface_updateAction(long j, CustomActionInterface customActionInterface, float f);

    public static final native long FilterableVehicleRaycaster_SWIGUpcast(long j);

    public static final native void FilterableVehicleRaycaster_setCollisionFilterGroup(long j, FilterableVehicleRaycaster filterableVehicleRaycaster, short s);

    public static final native void FilterableVehicleRaycaster_setCollisionFilterMask(long j, FilterableVehicleRaycaster filterableVehicleRaycaster, short s);

    public static final native void InternalTickCallback_CB(long j, btDynamicsWorld btdynamicsworld, float f);

    public static final native void InternalTickCallback_attach__SWIG_0(long j, InternalTickCallback internalTickCallback, long j2, btDynamicsWorld btdynamicsworld, boolean z);

    public static final native void InternalTickCallback_attach__SWIG_1(long j, InternalTickCallback internalTickCallback);

    public static final native void InternalTickCallback_change_ownership(InternalTickCallback internalTickCallback, long j, boolean z);

    public static final native void InternalTickCallback_detach__SWIG_0(long j, InternalTickCallback internalTickCallback);

    public static final native void InternalTickCallback_detach__SWIG_1(long j, btDynamicsWorld btdynamicsworld, boolean z);

    public static final native void InternalTickCallback_director_connect(InternalTickCallback internalTickCallback, long j, boolean z, boolean z2);

    public static final native void InternalTickCallback_onInternalTick(long j, InternalTickCallback internalTickCallback, long j2, btDynamicsWorld btdynamicsworld, float f);

    public static final native void InternalTickCallback_onInternalTickSwigExplicitInternalTickCallback(long j, InternalTickCallback internalTickCallback, long j2, btDynamicsWorld btdynamicsworld, float f);

    public static final native void btActionInterface_debugDraw(long j, btActionInterface btactioninterface, long j2, btIDebugDraw btidebugdraw);

    public static final native void btActionInterface_updateAction(long j, btActionInterface btactioninterface, long j2, btCollisionWorld btcollisionworld, float f);

    public static final native float btAdjustAngleToLimits(float f, float f2, float f3);

    public static final native void btAngularLimit_fit(long j, btAngularLimit btangularlimit, long j2);

    public static final native float btAngularLimit_getBiasFactor(long j, btAngularLimit btangularlimit);

    public static final native float btAngularLimit_getCorrection(long j, btAngularLimit btangularlimit);

    public static final native float btAngularLimit_getError(long j, btAngularLimit btangularlimit);

    public static final native float btAngularLimit_getHalfRange(long j, btAngularLimit btangularlimit);

    public static final native float btAngularLimit_getHigh(long j, btAngularLimit btangularlimit);

    public static final native float btAngularLimit_getLow(long j, btAngularLimit btangularlimit);

    public static final native float btAngularLimit_getRelaxationFactor(long j, btAngularLimit btangularlimit);

    public static final native float btAngularLimit_getSign(long j, btAngularLimit btangularlimit);

    public static final native float btAngularLimit_getSoftness(long j, btAngularLimit btangularlimit);

    public static final native boolean btAngularLimit_isLimit(long j, btAngularLimit btangularlimit);

    public static final native void btAngularLimit_set__SWIG_0(long j, btAngularLimit btangularlimit, float f, float f2, float f3, float f4, float f5);

    public static final native void btAngularLimit_set__SWIG_1(long j, btAngularLimit btangularlimit, float f, float f2, float f3, float f4);

    public static final native void btAngularLimit_set__SWIG_2(long j, btAngularLimit btangularlimit, float f, float f2, float f3);

    public static final native void btAngularLimit_set__SWIG_3(long j, btAngularLimit btangularlimit, float f, float f2);

    public static final native void btAngularLimit_test(long j, btAngularLimit btangularlimit, float f);

    public static final native long btCharacterControllerInterface_SWIGUpcast(long j);

    public static final native boolean btCharacterControllerInterface_canJump(long j, btCharacterControllerInterface btcharactercontrollerinterface);

    public static final native void btCharacterControllerInterface_jump__SWIG_0(long j, btCharacterControllerInterface btcharactercontrollerinterface, Vector3 vector3);

    public static final native void btCharacterControllerInterface_jump__SWIG_1(long j, btCharacterControllerInterface btcharactercontrollerinterface);

    public static final native boolean btCharacterControllerInterface_onGround(long j, btCharacterControllerInterface btcharactercontrollerinterface);

    public static final native void btCharacterControllerInterface_playerStep(long j, btCharacterControllerInterface btcharactercontrollerinterface, long j2, btCollisionWorld btcollisionworld, float f);

    public static final native void btCharacterControllerInterface_preStep(long j, btCharacterControllerInterface btcharactercontrollerinterface, long j2, btCollisionWorld btcollisionworld);

    public static final native void btCharacterControllerInterface_reset(long j, btCharacterControllerInterface btcharactercontrollerinterface, long j2, btCollisionWorld btcollisionworld);

    public static final native void btCharacterControllerInterface_setUpInterpolate(long j, btCharacterControllerInterface btcharactercontrollerinterface, boolean z);

    public static final native void btCharacterControllerInterface_setVelocityForTimeInterval(long j, btCharacterControllerInterface btcharactercontrollerinterface, Vector3 vector3, float f);

    public static final native void btCharacterControllerInterface_setWalkDirection(long j, btCharacterControllerInterface btcharactercontrollerinterface, Vector3 vector3);

    public static final native void btCharacterControllerInterface_warp(long j, btCharacterControllerInterface btcharactercontrollerinterface, Vector3 vector3);

    public static final native float btConeTwistConstraintData_biasFactor_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_biasFactor_set(long j, btConeTwistConstraintData btconetwistconstraintdata, float f);

    public static final native float btConeTwistConstraintData_damping_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_damping_set(long j, btConeTwistConstraintData btconetwistconstraintdata, float f);

    public static final native float btConeTwistConstraintData_limitSoftness_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_limitSoftness_set(long j, btConeTwistConstraintData btconetwistconstraintdata, float f);

    public static final native String btConeTwistConstraintData_pad_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_pad_set(long j, btConeTwistConstraintData btconetwistconstraintdata, String str);

    public static final native long btConeTwistConstraintData_rbAFrame_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_rbAFrame_set(long j, btConeTwistConstraintData btconetwistconstraintdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native long btConeTwistConstraintData_rbBFrame_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_rbBFrame_set(long j, btConeTwistConstraintData btconetwistconstraintdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native float btConeTwistConstraintData_relaxationFactor_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_relaxationFactor_set(long j, btConeTwistConstraintData btconetwistconstraintdata, float f);

    public static final native float btConeTwistConstraintData_swingSpan1_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_swingSpan1_set(long j, btConeTwistConstraintData btconetwistconstraintdata, float f);

    public static final native float btConeTwistConstraintData_swingSpan2_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_swingSpan2_set(long j, btConeTwistConstraintData btconetwistconstraintdata, float f);

    public static final native float btConeTwistConstraintData_twistSpan_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_twistSpan_set(long j, btConeTwistConstraintData btconetwistconstraintdata, float f);

    public static final native long btConeTwistConstraintData_typeConstraintData_get(long j, btConeTwistConstraintData btconetwistconstraintdata);

    public static final native void btConeTwistConstraintData_typeConstraintData_set(long j, btConeTwistConstraintData btconetwistconstraintdata, long j2, btTypedConstraintData bttypedconstraintdata);

    public static final native double btConeTwistConstraintDoubleData_biasFactor_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_biasFactor_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, double d);

    public static final native double btConeTwistConstraintDoubleData_damping_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_damping_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, double d);

    public static final native double btConeTwistConstraintDoubleData_limitSoftness_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_limitSoftness_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, double d);

    public static final native long btConeTwistConstraintDoubleData_rbAFrame_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_rbAFrame_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native long btConeTwistConstraintDoubleData_rbBFrame_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_rbBFrame_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native double btConeTwistConstraintDoubleData_relaxationFactor_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_relaxationFactor_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, double d);

    public static final native double btConeTwistConstraintDoubleData_swingSpan1_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_swingSpan1_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, double d);

    public static final native double btConeTwistConstraintDoubleData_swingSpan2_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_swingSpan2_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, double d);

    public static final native double btConeTwistConstraintDoubleData_twistSpan_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_twistSpan_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, double d);

    public static final native long btConeTwistConstraintDoubleData_typeConstraintData_get(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata);

    public static final native void btConeTwistConstraintDoubleData_typeConstraintData_set(long j, btConeTwistConstraintDoubleData btconetwistconstraintdoubledata, long j2, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native Vector3 btConeTwistConstraint_GetPointForAngle(long j, btConeTwistConstraint btconetwistconstraint, float f, float f2);

    public static final native long btConeTwistConstraint_SWIGUpcast(long j);

    public static final native void btConeTwistConstraint_calcAngleInfo(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native void btConeTwistConstraint_calcAngleInfo2(long j, btConeTwistConstraint btconetwistconstraint, Matrix4 matrix4, Matrix4 matrix5, Matrix3 matrix3, Matrix3 matrix6);

    public static final native void btConeTwistConstraint_enableMotor(long j, btConeTwistConstraint btconetwistconstraint, boolean z);

    public static final native Matrix4 btConeTwistConstraint_getAFrame(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native boolean btConeTwistConstraint_getAngularOnly(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native Matrix4 btConeTwistConstraint_getBFrame(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getBiasFactor(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getDamping(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getFixThresh(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native int btConeTwistConstraint_getFlags(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native Matrix4 btConeTwistConstraint_getFrameOffsetA(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native Matrix4 btConeTwistConstraint_getFrameOffsetB(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native void btConeTwistConstraint_getInfo1NonVirtual(long j, btConeTwistConstraint btconetwistconstraint, long j2, btTypedConstraint.btConstraintInfo1 btconstraintinfo1);

    public static final native void btConeTwistConstraint_getInfo2NonVirtual(long j, btConeTwistConstraint btconetwistconstraint, long j2, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Matrix3 matrix3, Matrix3 matrix6);

    public static final native float btConeTwistConstraint_getLimit(long j, btConeTwistConstraint btconetwistconstraint, int i);

    public static final native float btConeTwistConstraint_getLimitSoftness(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getMaxMotorImpulse(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native Quaternion btConeTwistConstraint_getMotorTarget(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getParam__SWIG_0(long j, btConeTwistConstraint btconetwistconstraint, int i, int i2);

    public static final native float btConeTwistConstraint_getParam__SWIG_1(long j, btConeTwistConstraint btconetwistconstraint, int i);

    public static final native float btConeTwistConstraint_getRelaxationFactor(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native long btConeTwistConstraint_getRigidBodyAConst(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native long btConeTwistConstraint_getRigidBodyBConst(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native int btConeTwistConstraint_getSolveSwingLimit(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native int btConeTwistConstraint_getSolveTwistLimit(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getSwingSpan1(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getSwingSpan2(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getTwistAngle(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getTwistLimitSign(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native float btConeTwistConstraint_getTwistSpan(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native boolean btConeTwistConstraint_isMaxMotorImpulseNormalized(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native boolean btConeTwistConstraint_isMotorEnabled(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native boolean btConeTwistConstraint_isPastSwingLimit(long j, btConeTwistConstraint btconetwistconstraint);

    public static final native void btConeTwistConstraint_operatorDeleteArray__SWIG_0(long j, btConeTwistConstraint btconetwistconstraint, long j2);

    public static final native void btConeTwistConstraint_operatorDeleteArray__SWIG_1(long j, btConeTwistConstraint btconetwistconstraint, long j2, long j3);

    public static final native void btConeTwistConstraint_operatorDelete__SWIG_0(long j, btConeTwistConstraint btconetwistconstraint, long j2);

    public static final native void btConeTwistConstraint_operatorDelete__SWIG_1(long j, btConeTwistConstraint btconetwistconstraint, long j2, long j3);

    public static final native long btConeTwistConstraint_operatorNewArray__SWIG_0(long j, btConeTwistConstraint btconetwistconstraint, long j2);

    public static final native long btConeTwistConstraint_operatorNewArray__SWIG_1(long j, btConeTwistConstraint btconetwistconstraint, long j2, long j3);

    public static final native long btConeTwistConstraint_operatorNew__SWIG_0(long j, btConeTwistConstraint btconetwistconstraint, long j2);

    public static final native long btConeTwistConstraint_operatorNew__SWIG_1(long j, btConeTwistConstraint btconetwistconstraint, long j2, long j3);

    public static final native void btConeTwistConstraint_setAngularOnly(long j, btConeTwistConstraint btconetwistconstraint, boolean z);

    public static final native void btConeTwistConstraint_setDamping(long j, btConeTwistConstraint btconetwistconstraint, float f);

    public static final native void btConeTwistConstraint_setFixThresh(long j, btConeTwistConstraint btconetwistconstraint, float f);

    public static final native void btConeTwistConstraint_setFrames(long j, btConeTwistConstraint btconetwistconstraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btConeTwistConstraint_setLimit__SWIG_0(long j, btConeTwistConstraint btconetwistconstraint, int i, float f);

    public static final native void btConeTwistConstraint_setLimit__SWIG_1(long j, btConeTwistConstraint btconetwistconstraint, float f, float f2, float f3, float f4, float f5, float f6);

    public static final native void btConeTwistConstraint_setLimit__SWIG_2(long j, btConeTwistConstraint btconetwistconstraint, float f, float f2, float f3, float f4, float f5);

    public static final native void btConeTwistConstraint_setLimit__SWIG_3(long j, btConeTwistConstraint btconetwistconstraint, float f, float f2, float f3, float f4);

    public static final native void btConeTwistConstraint_setLimit__SWIG_4(long j, btConeTwistConstraint btconetwistconstraint, float f, float f2, float f3);

    public static final native void btConeTwistConstraint_setMaxMotorImpulse(long j, btConeTwistConstraint btconetwistconstraint, float f);

    public static final native void btConeTwistConstraint_setMaxMotorImpulseNormalized(long j, btConeTwistConstraint btconetwistconstraint, float f);

    public static final native void btConeTwistConstraint_setMotorTarget(long j, btConeTwistConstraint btconetwistconstraint, Quaternion quaternion);

    public static final native void btConeTwistConstraint_setMotorTargetInConstraintSpace(long j, btConeTwistConstraint btconetwistconstraint, Quaternion quaternion);

    public static final native void btConeTwistConstraint_setParam__SWIG_0(long j, btConeTwistConstraint btconetwistconstraint, int i, float f, int i2);

    public static final native void btConeTwistConstraint_setParam__SWIG_1(long j, btConeTwistConstraint btconetwistconstraint, int i, float f);

    public static final native void btConeTwistConstraint_updateRHS(long j, btConeTwistConstraint btconetwistconstraint, float f);

    public static final native float btConstraintSetting_damping_get(long j, btConstraintSetting btconstraintsetting);

    public static final native void btConstraintSetting_damping_set(long j, btConstraintSetting btconstraintsetting, float f);

    public static final native float btConstraintSetting_impulseClamp_get(long j, btConstraintSetting btconstraintsetting);

    public static final native void btConstraintSetting_impulseClamp_set(long j, btConstraintSetting btconstraintsetting, float f);

    public static final native float btConstraintSetting_tau_get(long j, btConstraintSetting btconstraintsetting);

    public static final native void btConstraintSetting_tau_set(long j, btConstraintSetting btconstraintsetting, float f);

    public static final native long btConstraintSolverPoolMt_SWIGUpcast(long j);

    public static final native void btConstraintSolver_allSolved(long j, btConstraintSolver btconstraintsolver, long j2, btContactSolverInfo btcontactsolverinfo, long j3, btIDebugDraw btidebugdraw);

    public static final native int btConstraintSolver_getSolverType(long j, btConstraintSolver btconstraintsolver);

    public static final native void btConstraintSolver_prepareSolve(long j, btConstraintSolver btconstraintsolver, int i, int i2);

    public static final native void btConstraintSolver_reset(long j, btConstraintSolver btconstraintsolver);

    public static final native float btConstraintSolver_solveGroup(long j, btConstraintSolver btconstraintsolver, long j2, int i, long j3, int i2, long j4, int i3, long j5, btContactSolverInfo btcontactsolverinfo, long j6, btIDebugDraw btidebugdraw, long j7, btDispatcher btdispatcher);

    public static final native long btContactConstraint_SWIGUpcast(long j);

    public static final native long btContactConstraint_getContactManifold(long j, btContactConstraint btcontactconstraint);

    public static final native long btContactConstraint_getContactManifoldConst(long j, btContactConstraint btcontactconstraint);

    public static final native void btContactConstraint_setContactManifold(long j, btContactConstraint btcontactconstraint, long j2, btPersistentManifold btpersistentmanifold);

    public static final native float btContactSolverInfoData_damping_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_damping_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_erp2_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_erp2_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_erp_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_erp_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_frictionCFM_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_frictionCFM_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_frictionERP_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_frictionERP_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_friction_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_friction_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_globalCfm_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_globalCfm_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_leastSquaresResidualThreshold_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_leastSquaresResidualThreshold_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_linearSlop_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_linearSlop_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_maxErrorReduction_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_maxErrorReduction_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_maxGyroscopicForce_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_maxGyroscopicForce_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native int btContactSolverInfoData_minimumSolverBatchSize_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_minimumSolverBatchSize_set(long j, btContactSolverInfoData btcontactsolverinfodata, int i);

    public static final native int btContactSolverInfoData_numIterations_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_numIterations_set(long j, btContactSolverInfoData btcontactsolverinfodata, int i);

    public static final native int btContactSolverInfoData_restingContactRestitutionThreshold_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_restingContactRestitutionThreshold_set(long j, btContactSolverInfoData btcontactsolverinfodata, int i);

    public static final native float btContactSolverInfoData_restitutionVelocityThreshold_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_restitutionVelocityThreshold_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_restitution_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_restitution_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_singleAxisRollingFrictionThreshold_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_singleAxisRollingFrictionThreshold_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native int btContactSolverInfoData_solverMode_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_solverMode_set(long j, btContactSolverInfoData btcontactsolverinfodata, int i);

    public static final native float btContactSolverInfoData_sor_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_sor_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_splitImpulsePenetrationThreshold_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_splitImpulsePenetrationThreshold_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_splitImpulseTurnErp_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_splitImpulseTurnErp_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native int btContactSolverInfoData_splitImpulse_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_splitImpulse_set(long j, btContactSolverInfoData btcontactsolverinfodata, int i);

    public static final native float btContactSolverInfoData_tau_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_tau_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_timeStep_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_timeStep_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native float btContactSolverInfoData_warmstartingFactor_get(long j, btContactSolverInfoData btcontactsolverinfodata);

    public static final native void btContactSolverInfoData_warmstartingFactor_set(long j, btContactSolverInfoData btcontactsolverinfodata, float f);

    public static final native double btContactSolverInfoDoubleData_damping_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_damping_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_erp2_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_erp2_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_erp_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_erp_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_friction_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_friction_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_globalCfm_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_globalCfm_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_linearSlop_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_linearSlop_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_maxErrorReduction_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_maxErrorReduction_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_maxGyroscopicForce_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_maxGyroscopicForce_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native int btContactSolverInfoDoubleData_minimumSolverBatchSize_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_minimumSolverBatchSize_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, int i);

    public static final native int btContactSolverInfoDoubleData_numIterations_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_numIterations_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, int i);

    public static final native String btContactSolverInfoDoubleData_padding_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_padding_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, String str);

    public static final native int btContactSolverInfoDoubleData_restingContactRestitutionThreshold_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_restingContactRestitutionThreshold_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, int i);

    public static final native double btContactSolverInfoDoubleData_restitution_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_restitution_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_singleAxisRollingFrictionThreshold_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_singleAxisRollingFrictionThreshold_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native int btContactSolverInfoDoubleData_solverMode_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_solverMode_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, int i);

    public static final native double btContactSolverInfoDoubleData_sor_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_sor_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_splitImpulsePenetrationThreshold_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_splitImpulsePenetrationThreshold_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_splitImpulseTurnErp_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_splitImpulseTurnErp_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native int btContactSolverInfoDoubleData_splitImpulse_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_splitImpulse_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, int i);

    public static final native double btContactSolverInfoDoubleData_tau_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_tau_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_timeStep_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_timeStep_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native double btContactSolverInfoDoubleData_warmstartingFactor_get(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native void btContactSolverInfoDoubleData_warmstartingFactor_set(long j, btContactSolverInfoDoubleData btcontactsolverinfodoubledata, double d);

    public static final native float btContactSolverInfoFloatData_damping_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_damping_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_erp2_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_erp2_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_erp_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_erp_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_friction_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_friction_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_globalCfm_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_globalCfm_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_linearSlop_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_linearSlop_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_maxErrorReduction_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_maxErrorReduction_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_maxGyroscopicForce_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_maxGyroscopicForce_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native int btContactSolverInfoFloatData_minimumSolverBatchSize_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_minimumSolverBatchSize_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, int i);

    public static final native int btContactSolverInfoFloatData_numIterations_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_numIterations_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, int i);

    public static final native String btContactSolverInfoFloatData_padding_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_padding_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, String str);

    public static final native int btContactSolverInfoFloatData_restingContactRestitutionThreshold_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_restingContactRestitutionThreshold_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, int i);

    public static final native float btContactSolverInfoFloatData_restitution_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_restitution_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_singleAxisRollingFrictionThreshold_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_singleAxisRollingFrictionThreshold_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native int btContactSolverInfoFloatData_solverMode_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_solverMode_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, int i);

    public static final native float btContactSolverInfoFloatData_sor_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_sor_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_splitImpulsePenetrationThreshold_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_splitImpulsePenetrationThreshold_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_splitImpulseTurnErp_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_splitImpulseTurnErp_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native int btContactSolverInfoFloatData_splitImpulse_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_splitImpulse_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, int i);

    public static final native float btContactSolverInfoFloatData_tau_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_tau_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_timeStep_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_timeStep_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native float btContactSolverInfoFloatData_warmstartingFactor_get(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native void btContactSolverInfoFloatData_warmstartingFactor_set(long j, btContactSolverInfoFloatData btcontactsolverinfofloatdata, float f);

    public static final native long btContactSolverInfo_SWIGUpcast(long j);

    public static final native long btDantzigScratchMemory_Arows_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_Arows_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2);

    public static final native long btDantzigScratchMemory_C_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_C_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2);

    public static final native long btDantzigScratchMemory_Dell_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_Dell_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2, btScalarArray btscalararray);

    public static final native long btDantzigScratchMemory_L_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_L_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2, btScalarArray btscalararray);

    public static final native long btDantzigScratchMemory_d_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_d_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2, btScalarArray btscalararray);

    public static final native long btDantzigScratchMemory_delta_w_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_delta_w_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2, btScalarArray btscalararray);

    public static final native long btDantzigScratchMemory_delta_x_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_delta_x_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2, btScalarArray btscalararray);

    public static final native long btDantzigScratchMemory_ell_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_ell_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2, btScalarArray btscalararray);

    public static final native long btDantzigScratchMemory_p_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_p_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2);

    public static final native long btDantzigScratchMemory_scratch_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_scratch_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2, btScalarArray btscalararray);

    public static final native long btDantzigScratchMemory_state_get(long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native void btDantzigScratchMemory_state_set(long j, btDantzigScratchMemory btdantzigscratchmemory, long j2);

    public static final native long btDantzigSolver_SWIGUpcast(long j);

    public static final native boolean btDantzigSolver_solveMLCP__SWIG_0(long j, btDantzigSolver btdantzigsolver, long j2, long j3, long j4, long j5, long j6, long j7, int i, boolean z);

    public static final native boolean btDantzigSolver_solveMLCP__SWIG_1(long j, btDantzigSolver btdantzigsolver, long j2, long j3, long j4, long j5, long j6, long j7, int i);

    public static final native long btDefaultVehicleRaycaster_SWIGUpcast(long j);

    public static final native long btDiscreteDynamicsWorldMt_SWIGUpcast(long j);

    public static final native void btDiscreteDynamicsWorldMt_operatorDeleteArray__SWIG_0(long j, btDiscreteDynamicsWorldMt btdiscretedynamicsworldmt, long j2);

    public static final native void btDiscreteDynamicsWorldMt_operatorDeleteArray__SWIG_1(long j, btDiscreteDynamicsWorldMt btdiscretedynamicsworldmt, long j2, long j3);

    public static final native void btDiscreteDynamicsWorldMt_operatorDelete__SWIG_0(long j, btDiscreteDynamicsWorldMt btdiscretedynamicsworldmt, long j2);

    public static final native void btDiscreteDynamicsWorldMt_operatorDelete__SWIG_1(long j, btDiscreteDynamicsWorldMt btdiscretedynamicsworldmt, long j2, long j3);

    public static final native long btDiscreteDynamicsWorldMt_operatorNewArray__SWIG_0(long j, btDiscreteDynamicsWorldMt btdiscretedynamicsworldmt, long j2);

    public static final native long btDiscreteDynamicsWorldMt_operatorNewArray__SWIG_1(long j, btDiscreteDynamicsWorldMt btdiscretedynamicsworldmt, long j2, long j3);

    public static final native long btDiscreteDynamicsWorldMt_operatorNew__SWIG_0(long j, btDiscreteDynamicsWorldMt btdiscretedynamicsworldmt, long j2);

    public static final native long btDiscreteDynamicsWorldMt_operatorNew__SWIG_1(long j, btDiscreteDynamicsWorldMt btdiscretedynamicsworldmt, long j2, long j3);

    public static final native long btDiscreteDynamicsWorld_SWIGUpcast(long j);

    public static final native void btDiscreteDynamicsWorld_addCollisionObject__SWIG_0(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, btCollisionObject btcollisionobject, int i, int i2);

    public static final native void btDiscreteDynamicsWorld_addCollisionObject__SWIG_1(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, btCollisionObject btcollisionobject, int i);

    public static final native void btDiscreteDynamicsWorld_addCollisionObject__SWIG_2(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, btCollisionObject btcollisionobject);

    public static final native void btDiscreteDynamicsWorld_addConstraint__SWIG_0(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, btTypedConstraint bttypedconstraint, boolean z);

    public static final native void btDiscreteDynamicsWorld_addConstraint__SWIG_1(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, btTypedConstraint bttypedconstraint);

    public static final native void btDiscreteDynamicsWorld_addRigidBody__SWIG_0(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, btRigidBody btrigidbody);

    public static final native void btDiscreteDynamicsWorld_addRigidBody__SWIG_1(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, btRigidBody btrigidbody, int i, int i2);

    public static final native void btDiscreteDynamicsWorld_applyGravity(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld);

    public static final native void btDiscreteDynamicsWorld_debugDrawConstraint(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, btTypedConstraint bttypedconstraint);

    public static final native boolean btDiscreteDynamicsWorld_getApplySpeculativeContactRestitution(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld);

    public static final native long btDiscreteDynamicsWorld_getCollisionWorld(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld);

    public static final native boolean btDiscreteDynamicsWorld_getLatencyMotionStateInterpolation(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld);

    public static final native long btDiscreteDynamicsWorld_getSimulationIslandManager(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld);

    public static final native long btDiscreteDynamicsWorld_getSimulationIslandManagerConst(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld);

    public static final native boolean btDiscreteDynamicsWorld_getSynchronizeAllMotionStates(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld);

    public static final native void btDiscreteDynamicsWorld_operatorDeleteArray__SWIG_0(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2);

    public static final native void btDiscreteDynamicsWorld_operatorDeleteArray__SWIG_1(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, long j3);

    public static final native void btDiscreteDynamicsWorld_operatorDelete__SWIG_0(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2);

    public static final native void btDiscreteDynamicsWorld_operatorDelete__SWIG_1(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, long j3);

    public static final native long btDiscreteDynamicsWorld_operatorNewArray__SWIG_0(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2);

    public static final native long btDiscreteDynamicsWorld_operatorNewArray__SWIG_1(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, long j3);

    public static final native long btDiscreteDynamicsWorld_operatorNew__SWIG_0(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2);

    public static final native long btDiscreteDynamicsWorld_operatorNew__SWIG_1(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, long j3);

    public static final native void btDiscreteDynamicsWorld_setApplySpeculativeContactRestitution(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, boolean z);

    public static final native void btDiscreteDynamicsWorld_setLatencyMotionStateInterpolation(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, boolean z);

    public static final native void btDiscreteDynamicsWorld_setNumTasks(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, int i);

    public static final native void btDiscreteDynamicsWorld_setSynchronizeAllMotionStates(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, boolean z);

    public static final native int btDiscreteDynamicsWorld_stepSimulation__SWIG_0(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, float f, int i, float f2);

    public static final native int btDiscreteDynamicsWorld_stepSimulation__SWIG_1(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, float f, int i);

    public static final native int btDiscreteDynamicsWorld_stepSimulation__SWIG_2(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, float f);

    public static final native void btDiscreteDynamicsWorld_synchronizeSingleMotionState(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, long j2, btRigidBody btrigidbody);

    public static final native void btDiscreteDynamicsWorld_updateVehicles(long j, btDiscreteDynamicsWorld btdiscretedynamicsworld, float f);

    public static final native long btDynamicsWorldDoubleData_gravity_get(long j, btDynamicsWorldDoubleData btdynamicsworlddoubledata);

    public static final native void btDynamicsWorldDoubleData_gravity_set(long j, btDynamicsWorldDoubleData btdynamicsworlddoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btDynamicsWorldDoubleData_solverInfo_get(long j, btDynamicsWorldDoubleData btdynamicsworlddoubledata);

    public static final native void btDynamicsWorldDoubleData_solverInfo_set(long j, btDynamicsWorldDoubleData btdynamicsworlddoubledata, long j2, btContactSolverInfoDoubleData btcontactsolverinfodoubledata);

    public static final native long btDynamicsWorldFloatData_gravity_get(long j, btDynamicsWorldFloatData btdynamicsworldfloatdata);

    public static final native void btDynamicsWorldFloatData_gravity_set(long j, btDynamicsWorldFloatData btdynamicsworldfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btDynamicsWorldFloatData_solverInfo_get(long j, btDynamicsWorldFloatData btdynamicsworldfloatdata);

    public static final native void btDynamicsWorldFloatData_solverInfo_set(long j, btDynamicsWorldFloatData btdynamicsworldfloatdata, long j2, btContactSolverInfoFloatData btcontactsolverinfofloatdata);

    public static final native long btDynamicsWorld_SWIGUpcast(long j);

    public static final native void btDynamicsWorld_addAction(long j, btDynamicsWorld btdynamicsworld, long j2, btActionInterface btactioninterface);

    public static final native void btDynamicsWorld_addCharacter(long j, btDynamicsWorld btdynamicsworld, long j2, btActionInterface btactioninterface);

    public static final native void btDynamicsWorld_addConstraint__SWIG_0(long j, btDynamicsWorld btdynamicsworld, long j2, btTypedConstraint bttypedconstraint, boolean z);

    public static final native void btDynamicsWorld_addConstraint__SWIG_1(long j, btDynamicsWorld btdynamicsworld, long j2, btTypedConstraint bttypedconstraint);

    public static final native void btDynamicsWorld_addRigidBody__SWIG_0(long j, btDynamicsWorld btdynamicsworld, long j2, btRigidBody btrigidbody);

    public static final native void btDynamicsWorld_addRigidBody__SWIG_1(long j, btDynamicsWorld btdynamicsworld, long j2, btRigidBody btrigidbody, int i, int i2);

    public static final native void btDynamicsWorld_addVehicle(long j, btDynamicsWorld btdynamicsworld, long j2, btActionInterface btactioninterface);

    public static final native void btDynamicsWorld_clearForces(long j, btDynamicsWorld btdynamicsworld);

    public static final native long btDynamicsWorld_getConstraint(long j, btDynamicsWorld btdynamicsworld, int i);

    public static final native long btDynamicsWorld_getConstraintConst(long j, btDynamicsWorld btdynamicsworld, int i);

    public static final native long btDynamicsWorld_getConstraintSolver(long j, btDynamicsWorld btdynamicsworld);

    public static final native Vector3 btDynamicsWorld_getGravity(long j, btDynamicsWorld btdynamicsworld);

    public static final native int btDynamicsWorld_getNumConstraints(long j, btDynamicsWorld btdynamicsworld);

    public static final native long btDynamicsWorld_getSolverInfo(long j, btDynamicsWorld btdynamicsworld);

    public static final native long btDynamicsWorld_getSolverInfoConst(long j, btDynamicsWorld btdynamicsworld);

    public static final native int btDynamicsWorld_getWorldType(long j, btDynamicsWorld btdynamicsworld);

    public static final native long btDynamicsWorld_getWorldUserInfo(long j, btDynamicsWorld btdynamicsworld);

    public static final native void btDynamicsWorld_removeAction(long j, btDynamicsWorld btdynamicsworld, long j2, btActionInterface btactioninterface);

    public static final native void btDynamicsWorld_removeCharacter(long j, btDynamicsWorld btdynamicsworld, long j2, btActionInterface btactioninterface);

    public static final native void btDynamicsWorld_removeConstraint(long j, btDynamicsWorld btdynamicsworld, long j2, btTypedConstraint bttypedconstraint);

    public static final native void btDynamicsWorld_removeRigidBody(long j, btDynamicsWorld btdynamicsworld, long j2, btRigidBody btrigidbody);

    public static final native void btDynamicsWorld_removeVehicle(long j, btDynamicsWorld btdynamicsworld, long j2, btActionInterface btactioninterface);

    public static final native void btDynamicsWorld_setConstraintSolver(long j, btDynamicsWorld btdynamicsworld, long j2, btConstraintSolver btconstraintsolver);

    public static final native void btDynamicsWorld_setGravity(long j, btDynamicsWorld btdynamicsworld, Vector3 vector3);

    public static final native void btDynamicsWorld_setInternalTickCallback__SWIG_0(long j, btDynamicsWorld btdynamicsworld, long j2, long j3, boolean z);

    public static final native void btDynamicsWorld_setInternalTickCallback__SWIG_1(long j, btDynamicsWorld btdynamicsworld, long j2, long j3);

    public static final native void btDynamicsWorld_setInternalTickCallback__SWIG_2(long j, btDynamicsWorld btdynamicsworld, long j2);

    public static final native void btDynamicsWorld_setWorldUserInfo(long j, btDynamicsWorld btdynamicsworld, long j2);

    public static final native int btDynamicsWorld_stepSimulation__SWIG_0(long j, btDynamicsWorld btdynamicsworld, float f, int i, float f2);

    public static final native int btDynamicsWorld_stepSimulation__SWIG_1(long j, btDynamicsWorld btdynamicsworld, float f, int i);

    public static final native int btDynamicsWorld_stepSimulation__SWIG_2(long j, btDynamicsWorld btdynamicsworld, float f);

    public static final native void btDynamicsWorld_synchronizeMotionStates(long j, btDynamicsWorld btdynamicsworld);

    public static final native long btFixedConstraint_SWIGUpcast(long j);

    public static final native long btGearConstraintDoubleData_axisInA_get(long j, btGearConstraintDoubleData btgearconstraintdoubledata);

    public static final native void btGearConstraintDoubleData_axisInA_set(long j, btGearConstraintDoubleData btgearconstraintdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGearConstraintDoubleData_axisInB_get(long j, btGearConstraintDoubleData btgearconstraintdoubledata);

    public static final native void btGearConstraintDoubleData_axisInB_set(long j, btGearConstraintDoubleData btgearconstraintdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native double btGearConstraintDoubleData_ratio_get(long j, btGearConstraintDoubleData btgearconstraintdoubledata);

    public static final native void btGearConstraintDoubleData_ratio_set(long j, btGearConstraintDoubleData btgearconstraintdoubledata, double d);

    public static final native long btGearConstraintDoubleData_typeConstraintData_get(long j, btGearConstraintDoubleData btgearconstraintdoubledata);

    public static final native void btGearConstraintDoubleData_typeConstraintData_set(long j, btGearConstraintDoubleData btgearconstraintdoubledata, long j2, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native long btGearConstraintFloatData_axisInA_get(long j, btGearConstraintFloatData btgearconstraintfloatdata);

    public static final native void btGearConstraintFloatData_axisInA_set(long j, btGearConstraintFloatData btgearconstraintfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGearConstraintFloatData_axisInB_get(long j, btGearConstraintFloatData btgearconstraintfloatdata);

    public static final native void btGearConstraintFloatData_axisInB_set(long j, btGearConstraintFloatData btgearconstraintfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGearConstraintFloatData_padding_get(long j, btGearConstraintFloatData btgearconstraintfloatdata);

    public static final native void btGearConstraintFloatData_padding_set(long j, btGearConstraintFloatData btgearconstraintfloatdata, String str);

    public static final native float btGearConstraintFloatData_ratio_get(long j, btGearConstraintFloatData btgearconstraintfloatdata);

    public static final native void btGearConstraintFloatData_ratio_set(long j, btGearConstraintFloatData btgearconstraintfloatdata, float f);

    public static final native long btGearConstraintFloatData_typeConstraintData_get(long j, btGearConstraintFloatData btgearconstraintfloatdata);

    public static final native void btGearConstraintFloatData_typeConstraintData_set(long j, btGearConstraintFloatData btgearconstraintfloatdata, long j2, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native long btGearConstraint_SWIGUpcast(long j);

    public static final native Vector3 btGearConstraint_getAxisA(long j, btGearConstraint btgearconstraint);

    public static final native Vector3 btGearConstraint_getAxisB(long j, btGearConstraint btgearconstraint);

    public static final native float btGearConstraint_getParam__SWIG_0(long j, btGearConstraint btgearconstraint, int i, int i2);

    public static final native float btGearConstraint_getParam__SWIG_1(long j, btGearConstraint btgearconstraint, int i);

    public static final native float btGearConstraint_getRatio(long j, btGearConstraint btgearconstraint);

    public static final native void btGearConstraint_setAxisA(long j, btGearConstraint btgearconstraint, Vector3 vector3);

    public static final native void btGearConstraint_setAxisB(long j, btGearConstraint btgearconstraint, Vector3 vector3);

    public static final native void btGearConstraint_setParam__SWIG_0(long j, btGearConstraint btgearconstraint, int i, float f, int i2);

    public static final native void btGearConstraint_setParam__SWIG_1(long j, btGearConstraint btgearconstraint, int i, float f);

    public static final native void btGearConstraint_setRatio(long j, btGearConstraint btgearconstraint, float f);

    public static final native long btGeneric6DofConstraintData_angularLowerLimit_get(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native void btGeneric6DofConstraintData_angularLowerLimit_set(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofConstraintData_angularUpperLimit_get(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native void btGeneric6DofConstraintData_angularUpperLimit_set(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofConstraintData_linearLowerLimit_get(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native void btGeneric6DofConstraintData_linearLowerLimit_set(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofConstraintData_linearUpperLimit_get(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native void btGeneric6DofConstraintData_linearUpperLimit_set(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofConstraintData_rbAFrame_get(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native void btGeneric6DofConstraintData_rbAFrame_set(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native long btGeneric6DofConstraintData_rbBFrame_get(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native void btGeneric6DofConstraintData_rbBFrame_set(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native long btGeneric6DofConstraintData_typeConstraintData_get(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native void btGeneric6DofConstraintData_typeConstraintData_set(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata, long j2, btTypedConstraintData bttypedconstraintdata);

    public static final native int btGeneric6DofConstraintData_useLinearReferenceFrameA_get(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native void btGeneric6DofConstraintData_useLinearReferenceFrameA_set(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata, int i);

    public static final native int btGeneric6DofConstraintData_useOffsetForConstraintFrame_get(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native void btGeneric6DofConstraintData_useOffsetForConstraintFrame_set(long j, btGeneric6DofConstraintData btgeneric6dofconstraintdata, int i);

    public static final native long btGeneric6DofConstraintDoubleData2_angularLowerLimit_get(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native void btGeneric6DofConstraintDoubleData2_angularLowerLimit_set(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofConstraintDoubleData2_angularUpperLimit_get(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native void btGeneric6DofConstraintDoubleData2_angularUpperLimit_set(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofConstraintDoubleData2_linearLowerLimit_get(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native void btGeneric6DofConstraintDoubleData2_linearLowerLimit_set(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofConstraintDoubleData2_linearUpperLimit_get(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native void btGeneric6DofConstraintDoubleData2_linearUpperLimit_set(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofConstraintDoubleData2_rbAFrame_get(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native void btGeneric6DofConstraintDoubleData2_rbAFrame_set(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native long btGeneric6DofConstraintDoubleData2_rbBFrame_get(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native void btGeneric6DofConstraintDoubleData2_rbBFrame_set(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native long btGeneric6DofConstraintDoubleData2_typeConstraintData_get(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native void btGeneric6DofConstraintDoubleData2_typeConstraintData_set(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2, long j2, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native int btGeneric6DofConstraintDoubleData2_useLinearReferenceFrameA_get(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native void btGeneric6DofConstraintDoubleData2_useLinearReferenceFrameA_set(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2, int i);

    public static final native int btGeneric6DofConstraintDoubleData2_useOffsetForConstraintFrame_get(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native void btGeneric6DofConstraintDoubleData2_useOffsetForConstraintFrame_set(long j, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2, int i);

    public static final native long btGeneric6DofConstraint_SWIGUpcast(long j);

    public static final native void btGeneric6DofConstraint_calcAnchorPos(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native void btGeneric6DofConstraint_calculateTransforms__SWIG_0(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btGeneric6DofConstraint_calculateTransforms__SWIG_1(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native float btGeneric6DofConstraint_getAngle(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i);

    public static final native void btGeneric6DofConstraint_getAngularLowerLimit(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Vector3 vector3);

    public static final native void btGeneric6DofConstraint_getAngularUpperLimit(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Vector3 vector3);

    public static final native Vector3 btGeneric6DofConstraint_getAxis(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i);

    public static final native Matrix4 btGeneric6DofConstraint_getCalculatedTransformA(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native Matrix4 btGeneric6DofConstraint_getCalculatedTransformB(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native int btGeneric6DofConstraint_getFlags(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native Matrix4 btGeneric6DofConstraint_getFrameOffsetA(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native Matrix4 btGeneric6DofConstraint_getFrameOffsetAConst(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native Matrix4 btGeneric6DofConstraint_getFrameOffsetB(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native Matrix4 btGeneric6DofConstraint_getFrameOffsetBConst(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native void btGeneric6DofConstraint_getInfo1NonVirtual(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2, btTypedConstraint.btConstraintInfo1 btconstraintinfo1);

    public static final native void btGeneric6DofConstraint_getInfo2NonVirtual(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native void btGeneric6DofConstraint_getLinearLowerLimit(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Vector3 vector3);

    public static final native void btGeneric6DofConstraint_getLinearUpperLimit(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Vector3 vector3);

    public static final native float btGeneric6DofConstraint_getParam__SWIG_0(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i, int i2);

    public static final native float btGeneric6DofConstraint_getParam__SWIG_1(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i);

    public static final native float btGeneric6DofConstraint_getRelativePivotPosition(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i);

    public static final native long btGeneric6DofConstraint_getRotationalLimitMotor(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i);

    public static final native long btGeneric6DofConstraint_getTranslationalLimitMotor(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native boolean btGeneric6DofConstraint_getUseFrameOffset(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native boolean btGeneric6DofConstraint_getUseLinearReferenceFrameA(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native int btGeneric6DofConstraint_get_limit_motor_info2__SWIG_0(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2, btRotationalLimitMotor btrotationallimitmotor, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, long j3, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, int i, Vector3 vector7, int i2, int i3);

    public static final native int btGeneric6DofConstraint_get_limit_motor_info2__SWIG_1(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2, btRotationalLimitMotor btrotationallimitmotor, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, long j3, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, int i, Vector3 vector7, int i2);

    public static final native boolean btGeneric6DofConstraint_isLimited(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i);

    public static final native void btGeneric6DofConstraint_operatorDeleteArray__SWIG_0(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2);

    public static final native void btGeneric6DofConstraint_operatorDeleteArray__SWIG_1(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2, long j3);

    public static final native void btGeneric6DofConstraint_operatorDelete__SWIG_0(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2);

    public static final native void btGeneric6DofConstraint_operatorDelete__SWIG_1(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2, long j3);

    public static final native long btGeneric6DofConstraint_operatorNewArray__SWIG_0(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2);

    public static final native long btGeneric6DofConstraint_operatorNewArray__SWIG_1(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2, long j3);

    public static final native long btGeneric6DofConstraint_operatorNew__SWIG_0(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2);

    public static final native long btGeneric6DofConstraint_operatorNew__SWIG_1(long j, btGeneric6DofConstraint btgeneric6dofconstraint, long j2, long j3);

    public static final native void btGeneric6DofConstraint_setAngularLowerLimit(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Vector3 vector3);

    public static final native void btGeneric6DofConstraint_setAngularUpperLimit(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Vector3 vector3);

    public static final native void btGeneric6DofConstraint_setAxis(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Vector3 vector3, Vector3 vector4);

    public static final native void btGeneric6DofConstraint_setFrames(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btGeneric6DofConstraint_setLimit(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i, float f, float f2);

    public static final native void btGeneric6DofConstraint_setLinearLowerLimit(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Vector3 vector3);

    public static final native void btGeneric6DofConstraint_setLinearUpperLimit(long j, btGeneric6DofConstraint btgeneric6dofconstraint, Vector3 vector3);

    public static final native void btGeneric6DofConstraint_setParam__SWIG_0(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i, float f, int i2);

    public static final native void btGeneric6DofConstraint_setParam__SWIG_1(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i, float f);

    public static final native void btGeneric6DofConstraint_setUseFrameOffset(long j, btGeneric6DofConstraint btgeneric6dofconstraint, boolean z);

    public static final native void btGeneric6DofConstraint_setUseLinearReferenceFrameA(long j, btGeneric6DofConstraint btgeneric6dofconstraint, boolean z);

    public static final native boolean btGeneric6DofConstraint_testAngularLimitMotor(long j, btGeneric6DofConstraint btgeneric6dofconstraint, int i);

    public static final native void btGeneric6DofConstraint_updateRHS(long j, btGeneric6DofConstraint btgeneric6dofconstraint, float f);

    public static final native boolean btGeneric6DofConstraint_useSolveConstraintObsolete_get(long j, btGeneric6DofConstraint btgeneric6dofconstraint);

    public static final native void btGeneric6DofConstraint_useSolveConstraintObsolete_set(long j, btGeneric6DofConstraint btgeneric6dofconstraint, boolean z);

    public static final native long btGeneric6DofSpring2ConstraintData_angularBounce_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularBounce_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGeneric6DofSpring2ConstraintData_angularEnableMotor_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularEnableMotor_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native String btGeneric6DofSpring2ConstraintData_angularEnableSpring_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularEnableSpring_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native long btGeneric6DofSpring2ConstraintData_angularEquilibriumPoint_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularEquilibriumPoint_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_angularLowerLimit_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularLowerLimit_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_angularMaxMotorForce_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularMaxMotorForce_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_angularMotorCFM_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularMotorCFM_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_angularMotorERP_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularMotorERP_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGeneric6DofSpring2ConstraintData_angularServoMotor_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularServoMotor_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native long btGeneric6DofSpring2ConstraintData_angularServoTarget_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularServoTarget_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGeneric6DofSpring2ConstraintData_angularSpringDampingLimited_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularSpringDampingLimited_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native long btGeneric6DofSpring2ConstraintData_angularSpringDamping_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularSpringDamping_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGeneric6DofSpring2ConstraintData_angularSpringStiffnessLimited_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularSpringStiffnessLimited_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native long btGeneric6DofSpring2ConstraintData_angularSpringStiffness_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularSpringStiffness_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_angularStopCFM_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularStopCFM_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_angularStopERP_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularStopERP_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_angularTargetVelocity_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularTargetVelocity_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_angularUpperLimit_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_angularUpperLimit_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_linearBounce_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearBounce_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGeneric6DofSpring2ConstraintData_linearEnableMotor_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearEnableMotor_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native String btGeneric6DofSpring2ConstraintData_linearEnableSpring_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearEnableSpring_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native long btGeneric6DofSpring2ConstraintData_linearEquilibriumPoint_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearEquilibriumPoint_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_linearLowerLimit_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearLowerLimit_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_linearMaxMotorForce_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearMaxMotorForce_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_linearMotorCFM_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearMotorCFM_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_linearMotorERP_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearMotorERP_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGeneric6DofSpring2ConstraintData_linearServoMotor_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearServoMotor_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native long btGeneric6DofSpring2ConstraintData_linearServoTarget_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearServoTarget_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGeneric6DofSpring2ConstraintData_linearSpringDampingLimited_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearSpringDampingLimited_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native long btGeneric6DofSpring2ConstraintData_linearSpringDamping_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearSpringDamping_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGeneric6DofSpring2ConstraintData_linearSpringStiffnessLimited_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearSpringStiffnessLimited_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native long btGeneric6DofSpring2ConstraintData_linearSpringStiffness_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearSpringStiffness_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_linearStopCFM_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearStopCFM_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_linearStopERP_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearStopERP_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_linearTargetVelocity_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearTargetVelocity_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_linearUpperLimit_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_linearUpperLimit_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btGeneric6DofSpring2ConstraintData_padding1_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_padding1_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, String str);

    public static final native long btGeneric6DofSpring2ConstraintData_rbAFrame_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_rbAFrame_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native long btGeneric6DofSpring2ConstraintData_rbBFrame_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_rbBFrame_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native int btGeneric6DofSpring2ConstraintData_rotateOrder_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_rotateOrder_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, int i);

    public static final native long btGeneric6DofSpring2ConstraintData_typeConstraintData_get(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata);

    public static final native void btGeneric6DofSpring2ConstraintData_typeConstraintData_set(long j, btGeneric6DofSpring2ConstraintData btgeneric6dofspring2constraintdata, long j2, btTypedConstraintData bttypedconstraintdata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularBounce_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularBounce_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_angularEnableMotor_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularEnableMotor_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_angularEnableSpring_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularEnableSpring_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularEquilibriumPoint_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularEquilibriumPoint_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularLowerLimit_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularLowerLimit_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularMaxMotorForce_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularMaxMotorForce_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularMotorCFM_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularMotorCFM_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularMotorERP_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularMotorERP_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_angularServoMotor_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularServoMotor_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularServoTarget_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularServoTarget_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_angularSpringDampingLimited_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularSpringDampingLimited_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularSpringDamping_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularSpringDamping_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffnessLimited_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffnessLimited_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffness_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularSpringStiffness_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularStopCFM_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularStopCFM_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularStopERP_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularStopERP_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularTargetVelocity_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularTargetVelocity_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_angularUpperLimit_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_angularUpperLimit_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearBounce_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearBounce_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_linearEnableMotor_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearEnableMotor_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_linearEnableSpring_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearEnableSpring_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearEquilibriumPoint_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearEquilibriumPoint_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearLowerLimit_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearLowerLimit_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearMaxMotorForce_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearMaxMotorForce_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearMotorCFM_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearMotorCFM_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearMotorERP_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearMotorERP_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_linearServoMotor_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearServoMotor_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearServoTarget_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearServoTarget_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_linearSpringDampingLimited_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearSpringDampingLimited_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearSpringDamping_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearSpringDamping_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffnessLimited_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffnessLimited_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffness_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearSpringStiffness_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearStopCFM_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearStopCFM_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearStopERP_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearStopERP_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearTargetVelocity_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearTargetVelocity_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_linearUpperLimit_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_linearUpperLimit_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btGeneric6DofSpring2ConstraintDoubleData2_padding1_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_padding1_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, String str);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_rbAFrame_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_rbAFrame_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_rbBFrame_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_rbBFrame_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native int btGeneric6DofSpring2ConstraintDoubleData2_rotateOrder_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_rotateOrder_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, int i);

    public static final native long btGeneric6DofSpring2ConstraintDoubleData2_typeConstraintData_get(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2);

    public static final native void btGeneric6DofSpring2ConstraintDoubleData2_typeConstraintData_set(long j, btGeneric6DofSpring2ConstraintDoubleData2 btgeneric6dofspring2constraintdoubledata2, long j2, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native long btGeneric6DofSpring2Constraint_SWIGUpcast(long j);

    public static final native float btGeneric6DofSpring2Constraint_btGetMatrixElem(Matrix3 matrix3, int i);

    public static final native void btGeneric6DofSpring2Constraint_calculateTransforms__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btGeneric6DofSpring2Constraint_calculateTransforms__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native void btGeneric6DofSpring2Constraint_enableMotor(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, boolean z);

    public static final native void btGeneric6DofSpring2Constraint_enableSpring(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, boolean z);

    public static final native float btGeneric6DofSpring2Constraint_getAngle(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i);

    public static final native void btGeneric6DofSpring2Constraint_getAngularLowerLimit(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_getAngularLowerLimitReversed(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_getAngularUpperLimit(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_getAngularUpperLimitReversed(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native Vector3 btGeneric6DofSpring2Constraint_getAxis(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i);

    public static final native Matrix4 btGeneric6DofSpring2Constraint_getCalculatedTransformA(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native Matrix4 btGeneric6DofSpring2Constraint_getCalculatedTransformB(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native Matrix4 btGeneric6DofSpring2Constraint_getFrameOffsetA(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native Matrix4 btGeneric6DofSpring2Constraint_getFrameOffsetAConst(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native Matrix4 btGeneric6DofSpring2Constraint_getFrameOffsetB(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native Matrix4 btGeneric6DofSpring2Constraint_getFrameOffsetBConst(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native void btGeneric6DofSpring2Constraint_getLinearLowerLimit(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_getLinearUpperLimit(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native float btGeneric6DofSpring2Constraint_getParam__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, int i2);

    public static final native float btGeneric6DofSpring2Constraint_getParam__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i);

    public static final native float btGeneric6DofSpring2Constraint_getRelativePivotPosition(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i);

    public static final native int btGeneric6DofSpring2Constraint_getRotationOrder(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native long btGeneric6DofSpring2Constraint_getRotationalLimitMotor(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i);

    public static final native long btGeneric6DofSpring2Constraint_getTranslationalLimitMotor(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native boolean btGeneric6DofSpring2Constraint_isLimited(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i);

    public static final native boolean btGeneric6DofSpring2Constraint_matrixToEulerXYZ(Matrix3 matrix3, Vector3 vector3);

    public static final native boolean btGeneric6DofSpring2Constraint_matrixToEulerXZY(Matrix3 matrix3, Vector3 vector3);

    public static final native boolean btGeneric6DofSpring2Constraint_matrixToEulerYXZ(Matrix3 matrix3, Vector3 vector3);

    public static final native boolean btGeneric6DofSpring2Constraint_matrixToEulerYZX(Matrix3 matrix3, Vector3 vector3);

    public static final native boolean btGeneric6DofSpring2Constraint_matrixToEulerZXY(Matrix3 matrix3, Vector3 vector3);

    public static final native boolean btGeneric6DofSpring2Constraint_matrixToEulerZYX(Matrix3 matrix3, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_operatorDeleteArray__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, long j2);

    public static final native void btGeneric6DofSpring2Constraint_operatorDeleteArray__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, long j2, long j3);

    public static final native void btGeneric6DofSpring2Constraint_operatorDelete__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, long j2);

    public static final native void btGeneric6DofSpring2Constraint_operatorDelete__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, long j2, long j3);

    public static final native long btGeneric6DofSpring2Constraint_operatorNewArray__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, long j2);

    public static final native long btGeneric6DofSpring2Constraint_operatorNewArray__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, long j2, long j3);

    public static final native long btGeneric6DofSpring2Constraint_operatorNew__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, long j2);

    public static final native long btGeneric6DofSpring2Constraint_operatorNew__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, long j2, long j3);

    public static final native void btGeneric6DofSpring2Constraint_setAngularLowerLimit(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_setAngularLowerLimitReversed(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_setAngularUpperLimit(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_setAngularUpperLimitReversed(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_setAxis(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3, Vector3 vector4);

    public static final native void btGeneric6DofSpring2Constraint_setBounce(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f);

    public static final native void btGeneric6DofSpring2Constraint_setDamping__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f, boolean z);

    public static final native void btGeneric6DofSpring2Constraint_setDamping__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f);

    public static final native void btGeneric6DofSpring2Constraint_setEquilibriumPoint__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint);

    public static final native void btGeneric6DofSpring2Constraint_setEquilibriumPoint__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i);

    public static final native void btGeneric6DofSpring2Constraint_setEquilibriumPoint__SWIG_2(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f);

    public static final native void btGeneric6DofSpring2Constraint_setFrames(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btGeneric6DofSpring2Constraint_setLimit(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f, float f2);

    public static final native void btGeneric6DofSpring2Constraint_setLimitReversed(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f, float f2);

    public static final native void btGeneric6DofSpring2Constraint_setLinearLowerLimit(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_setLinearUpperLimit(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, Vector3 vector3);

    public static final native void btGeneric6DofSpring2Constraint_setMaxMotorForce(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f);

    public static final native void btGeneric6DofSpring2Constraint_setParam__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f, int i2);

    public static final native void btGeneric6DofSpring2Constraint_setParam__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f);

    public static final native void btGeneric6DofSpring2Constraint_setRotationOrder(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i);

    public static final native void btGeneric6DofSpring2Constraint_setServo(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, boolean z);

    public static final native void btGeneric6DofSpring2Constraint_setServoTarget(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f);

    public static final native void btGeneric6DofSpring2Constraint_setStiffness__SWIG_0(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f, boolean z);

    public static final native void btGeneric6DofSpring2Constraint_setStiffness__SWIG_1(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f);

    public static final native void btGeneric6DofSpring2Constraint_setTargetVelocity(long j, btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint, int i, float f);

    public static final native long btGeneric6DofSpringConstraintData_6dofData_get(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata);

    public static final native void btGeneric6DofSpringConstraintData_6dofData_set(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata, long j2, btGeneric6DofConstraintData btgeneric6dofconstraintdata);

    public static final native float[] btGeneric6DofSpringConstraintData_equilibriumPoint_get(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata);

    public static final native void btGeneric6DofSpringConstraintData_equilibriumPoint_set(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata, float[] fArr);

    public static final native float[] btGeneric6DofSpringConstraintData_springDamping_get(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata);

    public static final native void btGeneric6DofSpringConstraintData_springDamping_set(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata, float[] fArr);

    public static final native int[] btGeneric6DofSpringConstraintData_springEnabled_get(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata);

    public static final native void btGeneric6DofSpringConstraintData_springEnabled_set(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata, int[] iArr);

    public static final native float[] btGeneric6DofSpringConstraintData_springStiffness_get(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata);

    public static final native void btGeneric6DofSpringConstraintData_springStiffness_set(long j, btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata, float[] fArr);

    public static final native long btGeneric6DofSpringConstraintDoubleData2_6dofData_get(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2);

    public static final native void btGeneric6DofSpringConstraintDoubleData2_6dofData_set(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2, long j2, btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2);

    public static final native double[] btGeneric6DofSpringConstraintDoubleData2_equilibriumPoint_get(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2);

    public static final native void btGeneric6DofSpringConstraintDoubleData2_equilibriumPoint_set(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2, double[] dArr);

    public static final native double[] btGeneric6DofSpringConstraintDoubleData2_springDamping_get(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2);

    public static final native void btGeneric6DofSpringConstraintDoubleData2_springDamping_set(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2, double[] dArr);

    public static final native int[] btGeneric6DofSpringConstraintDoubleData2_springEnabled_get(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2);

    public static final native void btGeneric6DofSpringConstraintDoubleData2_springEnabled_set(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2, int[] iArr);

    public static final native double[] btGeneric6DofSpringConstraintDoubleData2_springStiffness_get(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2);

    public static final native void btGeneric6DofSpringConstraintDoubleData2_springStiffness_set(long j, btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2, double[] dArr);

    public static final native long btGeneric6DofSpringConstraint_SWIGUpcast(long j);

    public static final native void btGeneric6DofSpringConstraint_enableSpring(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, int i, boolean z);

    public static final native float btGeneric6DofSpringConstraint_getDamping(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, int i);

    public static final native float btGeneric6DofSpringConstraint_getEquilibriumPoint(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, int i);

    public static final native float btGeneric6DofSpringConstraint_getStiffness(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, int i);

    public static final native boolean btGeneric6DofSpringConstraint_isSpringEnabled(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, int i);

    public static final native void btGeneric6DofSpringConstraint_operatorDeleteArray__SWIG_0(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, long j2);

    public static final native void btGeneric6DofSpringConstraint_operatorDeleteArray__SWIG_1(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, long j2, long j3);

    public static final native void btGeneric6DofSpringConstraint_operatorDelete__SWIG_0(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, long j2);

    public static final native void btGeneric6DofSpringConstraint_operatorDelete__SWIG_1(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, long j2, long j3);

    public static final native long btGeneric6DofSpringConstraint_operatorNewArray__SWIG_0(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, long j2);

    public static final native long btGeneric6DofSpringConstraint_operatorNewArray__SWIG_1(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, long j2, long j3);

    public static final native long btGeneric6DofSpringConstraint_operatorNew__SWIG_0(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, long j2);

    public static final native long btGeneric6DofSpringConstraint_operatorNew__SWIG_1(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, long j2, long j3);

    public static final native void btGeneric6DofSpringConstraint_setAxis(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, Vector3 vector3, Vector3 vector4);

    public static final native void btGeneric6DofSpringConstraint_setDamping(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, int i, float f);

    public static final native void btGeneric6DofSpringConstraint_setEquilibriumPoint__SWIG_0(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint);

    public static final native void btGeneric6DofSpringConstraint_setEquilibriumPoint__SWIG_1(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, int i);

    public static final native void btGeneric6DofSpringConstraint_setEquilibriumPoint__SWIG_2(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, int i, float f);

    public static final native void btGeneric6DofSpringConstraint_setStiffness(long j, btGeneric6DofSpringConstraint btgeneric6dofspringconstraint, int i, float f);

    public static final native long btHinge2Constraint_SWIGUpcast(long j);

    public static final native Vector3 btHinge2Constraint_getAnchor(long j, btHinge2Constraint bthinge2constraint);

    public static final native Vector3 btHinge2Constraint_getAnchor2(long j, btHinge2Constraint bthinge2constraint);

    public static final native float btHinge2Constraint_getAngle1(long j, btHinge2Constraint bthinge2constraint);

    public static final native float btHinge2Constraint_getAngle2(long j, btHinge2Constraint bthinge2constraint);

    public static final native Vector3 btHinge2Constraint_getAxis1(long j, btHinge2Constraint bthinge2constraint);

    public static final native Vector3 btHinge2Constraint_getAxis2(long j, btHinge2Constraint bthinge2constraint);

    public static final native void btHinge2Constraint_operatorDeleteArray__SWIG_0(long j, btHinge2Constraint bthinge2constraint, long j2);

    public static final native void btHinge2Constraint_operatorDeleteArray__SWIG_1(long j, btHinge2Constraint bthinge2constraint, long j2, long j3);

    public static final native void btHinge2Constraint_operatorDelete__SWIG_0(long j, btHinge2Constraint bthinge2constraint, long j2);

    public static final native void btHinge2Constraint_operatorDelete__SWIG_1(long j, btHinge2Constraint bthinge2constraint, long j2, long j3);

    public static final native long btHinge2Constraint_operatorNewArray__SWIG_0(long j, btHinge2Constraint bthinge2constraint, long j2);

    public static final native long btHinge2Constraint_operatorNewArray__SWIG_1(long j, btHinge2Constraint bthinge2constraint, long j2, long j3);

    public static final native long btHinge2Constraint_operatorNew__SWIG_0(long j, btHinge2Constraint bthinge2constraint, long j2);

    public static final native long btHinge2Constraint_operatorNew__SWIG_1(long j, btHinge2Constraint bthinge2constraint, long j2, long j3);

    public static final native void btHinge2Constraint_setLowerLimit(long j, btHinge2Constraint bthinge2constraint, float f);

    public static final native void btHinge2Constraint_setUpperLimit(long j, btHinge2Constraint bthinge2constraint, float f);

    public static final native long btHingeAccumulatedAngleConstraint_SWIGUpcast(long j);

    public static final native float btHingeAccumulatedAngleConstraint_getAccumulatedHingeAngle(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint);

    public static final native void btHingeAccumulatedAngleConstraint_operatorDeleteArray__SWIG_0(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint, long j2);

    public static final native void btHingeAccumulatedAngleConstraint_operatorDeleteArray__SWIG_1(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint, long j2, long j3);

    public static final native void btHingeAccumulatedAngleConstraint_operatorDelete__SWIG_0(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint, long j2);

    public static final native void btHingeAccumulatedAngleConstraint_operatorDelete__SWIG_1(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint, long j2, long j3);

    public static final native long btHingeAccumulatedAngleConstraint_operatorNewArray__SWIG_0(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint, long j2);

    public static final native long btHingeAccumulatedAngleConstraint_operatorNewArray__SWIG_1(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint, long j2, long j3);

    public static final native long btHingeAccumulatedAngleConstraint_operatorNew__SWIG_0(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint, long j2);

    public static final native long btHingeAccumulatedAngleConstraint_operatorNew__SWIG_1(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint, long j2, long j3);

    public static final native void btHingeAccumulatedAngleConstraint_setAccumulatedHingeAngle(long j, btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint, float f);

    public static final native int btHingeConstraintDoubleData2_angularOnly_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_angularOnly_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, int i);

    public static final native double btHingeConstraintDoubleData2_biasFactor_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_biasFactor_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, double d);

    public static final native int btHingeConstraintDoubleData2_enableAngularMotor_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_enableAngularMotor_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, int i);

    public static final native double btHingeConstraintDoubleData2_limitSoftness_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_limitSoftness_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, double d);

    public static final native double btHingeConstraintDoubleData2_lowerLimit_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_lowerLimit_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, double d);

    public static final native double btHingeConstraintDoubleData2_maxMotorImpulse_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_maxMotorImpulse_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, double d);

    public static final native double btHingeConstraintDoubleData2_motorTargetVelocity_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_motorTargetVelocity_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, double d);

    public static final native String btHingeConstraintDoubleData2_padding1_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_padding1_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, String str);

    public static final native long btHingeConstraintDoubleData2_rbAFrame_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_rbAFrame_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native long btHingeConstraintDoubleData2_rbBFrame_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_rbBFrame_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native double btHingeConstraintDoubleData2_relaxationFactor_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_relaxationFactor_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, double d);

    public static final native long btHingeConstraintDoubleData2_typeConstraintData_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_typeConstraintData_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, long j2, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native double btHingeConstraintDoubleData2_upperLimit_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_upperLimit_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, double d);

    public static final native int btHingeConstraintDoubleData2_useReferenceFrameA_get(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2);

    public static final native void btHingeConstraintDoubleData2_useReferenceFrameA_set(long j, btHingeConstraintDoubleData2 bthingeconstraintdoubledata2, int i);

    public static final native int btHingeConstraintDoubleData_angularOnly_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_angularOnly_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, int i);

    public static final native float btHingeConstraintDoubleData_biasFactor_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_biasFactor_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, float f);

    public static final native int btHingeConstraintDoubleData_enableAngularMotor_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_enableAngularMotor_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, int i);

    public static final native float btHingeConstraintDoubleData_limitSoftness_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_limitSoftness_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, float f);

    public static final native float btHingeConstraintDoubleData_lowerLimit_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_lowerLimit_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, float f);

    public static final native float btHingeConstraintDoubleData_maxMotorImpulse_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_maxMotorImpulse_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, float f);

    public static final native float btHingeConstraintDoubleData_motorTargetVelocity_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_motorTargetVelocity_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, float f);

    public static final native long btHingeConstraintDoubleData_rbAFrame_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_rbAFrame_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native long btHingeConstraintDoubleData_rbBFrame_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_rbBFrame_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native float btHingeConstraintDoubleData_relaxationFactor_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_relaxationFactor_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, float f);

    public static final native long btHingeConstraintDoubleData_typeConstraintData_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_typeConstraintData_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, long j2, btTypedConstraintData bttypedconstraintdata);

    public static final native float btHingeConstraintDoubleData_upperLimit_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_upperLimit_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, float f);

    public static final native int btHingeConstraintDoubleData_useReferenceFrameA_get(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata);

    public static final native void btHingeConstraintDoubleData_useReferenceFrameA_set(long j, btHingeConstraintDoubleData bthingeconstraintdoubledata, int i);

    public static final native int btHingeConstraintFloatData_angularOnly_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_angularOnly_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, int i);

    public static final native float btHingeConstraintFloatData_biasFactor_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_biasFactor_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, float f);

    public static final native int btHingeConstraintFloatData_enableAngularMotor_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_enableAngularMotor_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, int i);

    public static final native float btHingeConstraintFloatData_limitSoftness_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_limitSoftness_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, float f);

    public static final native float btHingeConstraintFloatData_lowerLimit_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_lowerLimit_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, float f);

    public static final native float btHingeConstraintFloatData_maxMotorImpulse_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_maxMotorImpulse_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, float f);

    public static final native float btHingeConstraintFloatData_motorTargetVelocity_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_motorTargetVelocity_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, float f);

    public static final native long btHingeConstraintFloatData_rbAFrame_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_rbAFrame_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native long btHingeConstraintFloatData_rbBFrame_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_rbBFrame_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native float btHingeConstraintFloatData_relaxationFactor_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_relaxationFactor_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, float f);

    public static final native long btHingeConstraintFloatData_typeConstraintData_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_typeConstraintData_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, long j2, btTypedConstraintData bttypedconstraintdata);

    public static final native float btHingeConstraintFloatData_upperLimit_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_upperLimit_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, float f);

    public static final native int btHingeConstraintFloatData_useReferenceFrameA_get(long j, btHingeConstraintFloatData bthingeconstraintfloatdata);

    public static final native void btHingeConstraintFloatData_useReferenceFrameA_set(long j, btHingeConstraintFloatData bthingeconstraintfloatdata, int i);

    public static final native long btHingeConstraint_SWIGUpcast(long j);

    public static final native void btHingeConstraint_enableAngularMotor(long j, btHingeConstraint bthingeconstraint, boolean z, float f, float f2);

    public static final native void btHingeConstraint_enableMotor(long j, btHingeConstraint bthingeconstraint, boolean z);

    public static final native Matrix4 btHingeConstraint_getAFrame(long j, btHingeConstraint bthingeconstraint);

    public static final native Matrix4 btHingeConstraint_getAFrameConst(long j, btHingeConstraint bthingeconstraint);

    public static final native boolean btHingeConstraint_getAngularOnly(long j, btHingeConstraint bthingeconstraint);

    public static final native Matrix4 btHingeConstraint_getBFrame(long j, btHingeConstraint bthingeconstraint);

    public static final native Matrix4 btHingeConstraint_getBFrameConst(long j, btHingeConstraint bthingeconstraint);

    public static final native boolean btHingeConstraint_getEnableAngularMotor(long j, btHingeConstraint bthingeconstraint);

    public static final native int btHingeConstraint_getFlags(long j, btHingeConstraint bthingeconstraint);

    public static final native Matrix4 btHingeConstraint_getFrameOffsetA(long j, btHingeConstraint bthingeconstraint);

    public static final native Matrix4 btHingeConstraint_getFrameOffsetB(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getHingeAngle__SWIG_0(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getHingeAngle__SWIG_1(long j, btHingeConstraint bthingeconstraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btHingeConstraint_getInfo1NonVirtual(long j, btHingeConstraint bthingeconstraint, long j2, btTypedConstraint.btConstraintInfo1 btconstraintinfo1);

    public static final native void btHingeConstraint_getInfo2Internal(long j, btHingeConstraint bthingeconstraint, long j2, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4);

    public static final native void btHingeConstraint_getInfo2InternalUsingFrameOffset(long j, btHingeConstraint bthingeconstraint, long j2, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4);

    public static final native void btHingeConstraint_getInfo2NonVirtual(long j, btHingeConstraint bthingeconstraint, long j2, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4);

    public static final native float btHingeConstraint_getLimitBiasFactor(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getLimitRelaxationFactor(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getLimitSign(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getLimitSoftness(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getLowerLimit(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getMaxMotorImpulse(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getMotorTargetVelocity(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getParam__SWIG_0(long j, btHingeConstraint bthingeconstraint, int i, int i2);

    public static final native float btHingeConstraint_getParam__SWIG_1(long j, btHingeConstraint bthingeconstraint, int i);

    public static final native long btHingeConstraint_getRigidBodyA(long j, btHingeConstraint bthingeconstraint);

    public static final native long btHingeConstraint_getRigidBodyAConst(long j, btHingeConstraint bthingeconstraint);

    public static final native long btHingeConstraint_getRigidBodyB(long j, btHingeConstraint bthingeconstraint);

    public static final native long btHingeConstraint_getRigidBodyBConst(long j, btHingeConstraint bthingeconstraint);

    public static final native int btHingeConstraint_getSolveLimit(long j, btHingeConstraint bthingeconstraint);

    public static final native float btHingeConstraint_getUpperLimit(long j, btHingeConstraint bthingeconstraint);

    public static final native boolean btHingeConstraint_getUseFrameOffset(long j, btHingeConstraint bthingeconstraint);

    public static final native boolean btHingeConstraint_getUseReferenceFrameA(long j, btHingeConstraint bthingeconstraint);

    public static final native boolean btHingeConstraint_hasLimit(long j, btHingeConstraint bthingeconstraint);

    public static final native void btHingeConstraint_operatorDeleteArray__SWIG_0(long j, btHingeConstraint bthingeconstraint, long j2);

    public static final native void btHingeConstraint_operatorDeleteArray__SWIG_1(long j, btHingeConstraint bthingeconstraint, long j2, long j3);

    public static final native void btHingeConstraint_operatorDelete__SWIG_0(long j, btHingeConstraint bthingeconstraint, long j2);

    public static final native void btHingeConstraint_operatorDelete__SWIG_1(long j, btHingeConstraint bthingeconstraint, long j2, long j3);

    public static final native long btHingeConstraint_operatorNewArray__SWIG_0(long j, btHingeConstraint bthingeconstraint, long j2);

    public static final native long btHingeConstraint_operatorNewArray__SWIG_1(long j, btHingeConstraint bthingeconstraint, long j2, long j3);

    public static final native long btHingeConstraint_operatorNew__SWIG_0(long j, btHingeConstraint bthingeconstraint, long j2);

    public static final native long btHingeConstraint_operatorNew__SWIG_1(long j, btHingeConstraint bthingeconstraint, long j2, long j3);

    public static final native void btHingeConstraint_setAngularOnly(long j, btHingeConstraint bthingeconstraint, boolean z);

    public static final native void btHingeConstraint_setAxis(long j, btHingeConstraint bthingeconstraint, Vector3 vector3);

    public static final native void btHingeConstraint_setFrames(long j, btHingeConstraint bthingeconstraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btHingeConstraint_setLimit__SWIG_0(long j, btHingeConstraint bthingeconstraint, float f, float f2, float f3, float f4, float f5);

    public static final native void btHingeConstraint_setLimit__SWIG_1(long j, btHingeConstraint bthingeconstraint, float f, float f2, float f3, float f4);

    public static final native void btHingeConstraint_setLimit__SWIG_2(long j, btHingeConstraint bthingeconstraint, float f, float f2, float f3);

    public static final native void btHingeConstraint_setLimit__SWIG_3(long j, btHingeConstraint bthingeconstraint, float f, float f2);

    public static final native void btHingeConstraint_setMaxMotorImpulse(long j, btHingeConstraint bthingeconstraint, float f);

    public static final native void btHingeConstraint_setMotorTargetVelocity(long j, btHingeConstraint bthingeconstraint, float f);

    public static final native void btHingeConstraint_setMotorTarget__SWIG_0(long j, btHingeConstraint bthingeconstraint, Quaternion quaternion, float f);

    public static final native void btHingeConstraint_setMotorTarget__SWIG_1(long j, btHingeConstraint bthingeconstraint, float f, float f2);

    public static final native void btHingeConstraint_setParam__SWIG_0(long j, btHingeConstraint bthingeconstraint, int i, float f, int i2);

    public static final native void btHingeConstraint_setParam__SWIG_1(long j, btHingeConstraint bthingeconstraint, int i, float f);

    public static final native void btHingeConstraint_setUseFrameOffset(long j, btHingeConstraint bthingeconstraint, boolean z);

    public static final native void btHingeConstraint_setUseReferenceFrameA(long j, btHingeConstraint bthingeconstraint, boolean z);

    public static final native void btHingeConstraint_testLimit(long j, btHingeConstraint bthingeconstraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btHingeConstraint_updateRHS(long j, btHingeConstraint bthingeconstraint, float f);

    public static final native long btJacobianEntry_0MinvJt_get(long j, btJacobianEntry btjacobianentry);

    public static final native void btJacobianEntry_0MinvJt_set(long j, btJacobianEntry btjacobianentry, long j2, btVector3 btvector3);

    public static final native long btJacobianEntry_1MinvJt_get(long j, btJacobianEntry btjacobianentry);

    public static final native void btJacobianEntry_1MinvJt_set(long j, btJacobianEntry btjacobianentry, long j2, btVector3 btvector3);

    public static final native float btJacobianEntry_Adiag_get(long j, btJacobianEntry btjacobianentry);

    public static final native void btJacobianEntry_Adiag_set(long j, btJacobianEntry btjacobianentry, float f);

    public static final native long btJacobianEntry_aJ_get(long j, btJacobianEntry btjacobianentry);

    public static final native void btJacobianEntry_aJ_set(long j, btJacobianEntry btjacobianentry, long j2, btVector3 btvector3);

    public static final native long btJacobianEntry_bJ_get(long j, btJacobianEntry btjacobianentry);

    public static final native void btJacobianEntry_bJ_set(long j, btJacobianEntry btjacobianentry, long j2, btVector3 btvector3);

    public static final native float btJacobianEntry_getDiagonal(long j, btJacobianEntry btjacobianentry);

    public static final native float btJacobianEntry_getNonDiagonal__SWIG_0(long j, btJacobianEntry btjacobianentry, long j2, btJacobianEntry btjacobianentry2, float f);

    public static final native float btJacobianEntry_getNonDiagonal__SWIG_1(long j, btJacobianEntry btjacobianentry, long j2, btJacobianEntry btjacobianentry2, float f, float f2);

    public static final native float btJacobianEntry_getRelativeVelocity(long j, btJacobianEntry btjacobianentry, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native long btJacobianEntry_linearJointAxis_get(long j, btJacobianEntry btjacobianentry);

    public static final native void btJacobianEntry_linearJointAxis_set(long j, btJacobianEntry btjacobianentry, long j2, btVector3 btvector3);

    public static final native long btJointFeedback_appliedForceBodyA_get(long j, btJointFeedback btjointfeedback);

    public static final native void btJointFeedback_appliedForceBodyA_set(long j, btJointFeedback btjointfeedback, long j2, btVector3 btvector3);

    public static final native long btJointFeedback_appliedForceBodyB_get(long j, btJointFeedback btjointfeedback);

    public static final native void btJointFeedback_appliedForceBodyB_set(long j, btJointFeedback btjointfeedback, long j2, btVector3 btvector3);

    public static final native long btJointFeedback_appliedTorqueBodyA_get(long j, btJointFeedback btjointfeedback);

    public static final native void btJointFeedback_appliedTorqueBodyA_set(long j, btJointFeedback btjointfeedback, long j2, btVector3 btvector3);

    public static final native long btJointFeedback_appliedTorqueBodyB_get(long j, btJointFeedback btjointfeedback);

    public static final native void btJointFeedback_appliedTorqueBodyB_set(long j, btJointFeedback btjointfeedback, long j2, btVector3 btvector3);

    public static final native void btJointFeedback_operatorDeleteArray__SWIG_0(long j, btJointFeedback btjointfeedback, long j2);

    public static final native void btJointFeedback_operatorDeleteArray__SWIG_1(long j, btJointFeedback btjointfeedback, long j2, long j3);

    public static final native void btJointFeedback_operatorDelete__SWIG_0(long j, btJointFeedback btjointfeedback, long j2);

    public static final native void btJointFeedback_operatorDelete__SWIG_1(long j, btJointFeedback btjointfeedback, long j2, long j3);

    public static final native long btJointFeedback_operatorNewArray__SWIG_0(long j, btJointFeedback btjointfeedback, long j2);

    public static final native long btJointFeedback_operatorNewArray__SWIG_1(long j, btJointFeedback btjointfeedback, long j2, long j3);

    public static final native long btJointFeedback_operatorNew__SWIG_0(long j, btJointFeedback btjointfeedback, long j2);

    public static final native long btJointFeedback_operatorNew__SWIG_1(long j, btJointFeedback btjointfeedback, long j2, long j3);

    public static final native long btKinematicCharacterController_SWIGUpcast(long j);

    public static final native void btKinematicCharacterController_applyImpulse(long j, btKinematicCharacterController btkinematiccharactercontroller, Vector3 vector3);

    public static final native float btKinematicCharacterController_getAngularDamping(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native Vector3 btKinematicCharacterController_getAngularVelocity(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native float btKinematicCharacterController_getFallSpeed(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native long btKinematicCharacterController_getGhostObject(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native Vector3 btKinematicCharacterController_getGravity(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native float btKinematicCharacterController_getJumpSpeed(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native float btKinematicCharacterController_getLinearDamping(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native Vector3 btKinematicCharacterController_getLinearVelocity(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native float btKinematicCharacterController_getMaxPenetrationDepth(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native float btKinematicCharacterController_getMaxSlope(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native float btKinematicCharacterController_getStepHeight(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native Vector3 btKinematicCharacterController_getUp(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native void btKinematicCharacterController_jump__SWIG_0(long j, btKinematicCharacterController btkinematiccharactercontroller, Vector3 vector3);

    public static final native void btKinematicCharacterController_jump__SWIG_1(long j, btKinematicCharacterController btkinematiccharactercontroller);

    public static final native void btKinematicCharacterController_operatorDeleteArray__SWIG_0(long j, btKinematicCharacterController btkinematiccharactercontroller, long j2);

    public static final native void btKinematicCharacterController_operatorDeleteArray__SWIG_1(long j, btKinematicCharacterController btkinematiccharactercontroller, long j2, long j3);

    public static final native void btKinematicCharacterController_operatorDelete__SWIG_0(long j, btKinematicCharacterController btkinematiccharactercontroller, long j2);

    public static final native void btKinematicCharacterController_operatorDelete__SWIG_1(long j, btKinematicCharacterController btkinematiccharactercontroller, long j2, long j3);

    public static final native long btKinematicCharacterController_operatorNewArray__SWIG_0(long j, btKinematicCharacterController btkinematiccharactercontroller, long j2);

    public static final native long btKinematicCharacterController_operatorNewArray__SWIG_1(long j, btKinematicCharacterController btkinematiccharactercontroller, long j2, long j3);

    public static final native long btKinematicCharacterController_operatorNew__SWIG_0(long j, btKinematicCharacterController btkinematiccharactercontroller, long j2);

    public static final native long btKinematicCharacterController_operatorNew__SWIG_1(long j, btKinematicCharacterController btkinematiccharactercontroller, long j2, long j3);

    public static final native void btKinematicCharacterController_setAngularDamping(long j, btKinematicCharacterController btkinematiccharactercontroller, float f);

    public static final native void btKinematicCharacterController_setAngularVelocity(long j, btKinematicCharacterController btkinematiccharactercontroller, Vector3 vector3);

    public static final native void btKinematicCharacterController_setFallSpeed(long j, btKinematicCharacterController btkinematiccharactercontroller, float f);

    public static final native void btKinematicCharacterController_setGravity(long j, btKinematicCharacterController btkinematiccharactercontroller, Vector3 vector3);

    public static final native void btKinematicCharacterController_setJumpSpeed(long j, btKinematicCharacterController btkinematiccharactercontroller, float f);

    public static final native void btKinematicCharacterController_setLinearDamping(long j, btKinematicCharacterController btkinematiccharactercontroller, float f);

    public static final native void btKinematicCharacterController_setLinearVelocity(long j, btKinematicCharacterController btkinematiccharactercontroller, Vector3 vector3);

    public static final native void btKinematicCharacterController_setMaxJumpHeight(long j, btKinematicCharacterController btkinematiccharactercontroller, float f);

    public static final native void btKinematicCharacterController_setMaxPenetrationDepth(long j, btKinematicCharacterController btkinematiccharactercontroller, float f);

    public static final native void btKinematicCharacterController_setMaxSlope(long j, btKinematicCharacterController btkinematiccharactercontroller, float f);

    public static final native void btKinematicCharacterController_setStepHeight(long j, btKinematicCharacterController btkinematiccharactercontroller, float f);

    public static final native void btKinematicCharacterController_setUp(long j, btKinematicCharacterController btkinematiccharactercontroller, Vector3 vector3);

    public static final native void btKinematicCharacterController_setUseGhostSweepTest(long j, btKinematicCharacterController btkinematiccharactercontroller, boolean z);

    public static final native int btLemkeAlgorithm_getInfo(long j, btLemkeAlgorithm btlemkealgorithm);

    public static final native int btLemkeAlgorithm_getSteps(long j, btLemkeAlgorithm btlemkealgorithm);

    public static final native void btLemkeAlgorithm_setSystem(long j, btLemkeAlgorithm btlemkealgorithm, long j2, long j3);

    public static final native long btLemkeAlgorithm_solve__SWIG_0(long j, btLemkeAlgorithm btlemkealgorithm, long j2);

    public static final native long btLemkeAlgorithm_solve__SWIG_1(long j, btLemkeAlgorithm btlemkealgorithm);

    public static final native long btLemkeSolver_SWIGUpcast(long j);

    public static final native int btLemkeSolver_debugLevel_get(long j, btLemkeSolver btlemkesolver);

    public static final native void btLemkeSolver_debugLevel_set(long j, btLemkeSolver btlemkesolver, int i);

    public static final native int btLemkeSolver_maxLoops_get(long j, btLemkeSolver btlemkesolver);

    public static final native void btLemkeSolver_maxLoops_set(long j, btLemkeSolver btlemkesolver, int i);

    public static final native float btLemkeSolver_maxValue_get(long j, btLemkeSolver btlemkesolver);

    public static final native void btLemkeSolver_maxValue_set(long j, btLemkeSolver btlemkesolver, float f);

    public static final native boolean btLemkeSolver_solveMLCP__SWIG_0(long j, btLemkeSolver btlemkesolver, long j2, long j3, long j4, long j5, long j6, long j7, int i, boolean z);

    public static final native boolean btLemkeSolver_solveMLCP__SWIG_1(long j, btLemkeSolver btlemkesolver, long j2, long j3, long j4, long j5, long j6, long j7, int i);

    public static final native boolean btLemkeSolver_useLoHighBounds_get(long j, btLemkeSolver btlemkesolver);

    public static final native void btLemkeSolver_useLoHighBounds_set(long j, btLemkeSolver btlemkesolver, boolean z);

    public static final native boolean btMLCPSolverInterface_solveMLCP__SWIG_0(long j, btMLCPSolverInterface btmlcpsolverinterface, long j2, long j3, long j4, long j5, long j6, long j7, int i, boolean z);

    public static final native boolean btMLCPSolverInterface_solveMLCP__SWIG_1(long j, btMLCPSolverInterface btmlcpsolverinterface, long j2, long j3, long j4, long j5, long j6, long j7, int i);

    public static final native long btMLCPSolver_SWIGUpcast(long j);

    public static final native int btMLCPSolver_getNumFallbacks(long j, btMLCPSolver btmlcpsolver);

    public static final native void btMLCPSolver_setMLCPSolver(long j, btMLCPSolver btmlcpsolver, long j2, btMLCPSolverInterface btmlcpsolverinterface);

    public static final native void btMLCPSolver_setNumFallbacks(long j, btMLCPSolver btmlcpsolver, int i);

    public static final native long btMultiBodyConstraintSolver_SWIGUpcast(long j);

    public static final native void btMultiBodyConstraintSolver_operatorDeleteArray__SWIG_0(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2);

    public static final native void btMultiBodyConstraintSolver_operatorDeleteArray__SWIG_1(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2, long j3);

    public static final native void btMultiBodyConstraintSolver_operatorDelete__SWIG_0(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2);

    public static final native void btMultiBodyConstraintSolver_operatorDelete__SWIG_1(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2, long j3);

    public static final native long btMultiBodyConstraintSolver_operatorNewArray__SWIG_0(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2);

    public static final native long btMultiBodyConstraintSolver_operatorNewArray__SWIG_1(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2, long j3);

    public static final native long btMultiBodyConstraintSolver_operatorNew__SWIG_0(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2);

    public static final native long btMultiBodyConstraintSolver_operatorNew__SWIG_1(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2, long j3);

    public static final native float btMultiBodyConstraintSolver_solveGroupCacheFriendlyFinish(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2, int i, long j3, btContactSolverInfo btcontactsolverinfo);

    public static final native void btMultiBodyConstraintSolver_solveMultiBodyGroup(long j, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j2, int i, long j3, int i2, long j4, int i3, long j5, int i4, long j6, btContactSolverInfo btcontactsolverinfo, long j7, btIDebugDraw btidebugdraw, long j8, btDispatcher btdispatcher);

    public static final native void btMultiBodyConstraint_allocateJacobiansMultiDof(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native void btMultiBodyConstraint_createConstraintRows(long j, btMultiBodyConstraint btmultibodyconstraint, long j2, long j3, btMultiBodyJacobianData btmultibodyjacobiandata, long j4, btContactSolverInfo btcontactsolverinfo);

    public static final native void btMultiBodyConstraint_debugDraw(long j, btMultiBodyConstraint btmultibodyconstraint, long j2, btIDebugDraw btidebugdraw);

    public static final native void btMultiBodyConstraint_finalizeMultiDof(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native float btMultiBodyConstraint_getAppliedImpulse(long j, btMultiBodyConstraint btmultibodyconstraint, int i);

    public static final native int btMultiBodyConstraint_getIslandIdA(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native int btMultiBodyConstraint_getIslandIdB(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native float btMultiBodyConstraint_getMaxAppliedImpulse(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native long btMultiBodyConstraint_getMultiBodyA(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native long btMultiBodyConstraint_getMultiBodyB(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native int btMultiBodyConstraint_getNumRows(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native float btMultiBodyConstraint_getPosition(long j, btMultiBodyConstraint btmultibodyconstraint, int i);

    public static final native void btMultiBodyConstraint_internalSetAppliedImpulse(long j, btMultiBodyConstraint btmultibodyconstraint, int i, float f);

    public static final native boolean btMultiBodyConstraint_isUnilateral(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native FloatBuffer btMultiBodyConstraint_jacobianA(long j, btMultiBodyConstraint btmultibodyconstraint, int i);

    public static final native FloatBuffer btMultiBodyConstraint_jacobianAConst(long j, btMultiBodyConstraint btmultibodyconstraint, int i);

    public static final native FloatBuffer btMultiBodyConstraint_jacobianB(long j, btMultiBodyConstraint btmultibodyconstraint, int i);

    public static final native FloatBuffer btMultiBodyConstraint_jacobianBConst(long j, btMultiBodyConstraint btmultibodyconstraint, int i);

    public static final native void btMultiBodyConstraint_operatorDeleteArray__SWIG_0(long j, btMultiBodyConstraint btmultibodyconstraint, long j2);

    public static final native void btMultiBodyConstraint_operatorDeleteArray__SWIG_1(long j, btMultiBodyConstraint btmultibodyconstraint, long j2, long j3);

    public static final native void btMultiBodyConstraint_operatorDelete__SWIG_0(long j, btMultiBodyConstraint btmultibodyconstraint, long j2);

    public static final native void btMultiBodyConstraint_operatorDelete__SWIG_1(long j, btMultiBodyConstraint btmultibodyconstraint, long j2, long j3);

    public static final native long btMultiBodyConstraint_operatorNewArray__SWIG_0(long j, btMultiBodyConstraint btmultibodyconstraint, long j2);

    public static final native long btMultiBodyConstraint_operatorNewArray__SWIG_1(long j, btMultiBodyConstraint btmultibodyconstraint, long j2, long j3);

    public static final native long btMultiBodyConstraint_operatorNew__SWIG_0(long j, btMultiBodyConstraint btmultibodyconstraint, long j2);

    public static final native long btMultiBodyConstraint_operatorNew__SWIG_1(long j, btMultiBodyConstraint btmultibodyconstraint, long j2, long j3);

    public static final native void btMultiBodyConstraint_setErp(long j, btMultiBodyConstraint btmultibodyconstraint, float f);

    public static final native void btMultiBodyConstraint_setFrameInB(long j, btMultiBodyConstraint btmultibodyconstraint, Matrix3 matrix3);

    public static final native void btMultiBodyConstraint_setGearAuxLink(long j, btMultiBodyConstraint btmultibodyconstraint, int i);

    public static final native void btMultiBodyConstraint_setGearRatio(long j, btMultiBodyConstraint btmultibodyconstraint, float f);

    public static final native void btMultiBodyConstraint_setMaxAppliedImpulse(long j, btMultiBodyConstraint btmultibodyconstraint, float f);

    public static final native void btMultiBodyConstraint_setPivotInB(long j, btMultiBodyConstraint btmultibodyconstraint, Vector3 vector3);

    public static final native void btMultiBodyConstraint_setPosition(long j, btMultiBodyConstraint btmultibodyconstraint, int i, float f);

    public static final native void btMultiBodyConstraint_setRelativePositionTarget(long j, btMultiBodyConstraint btmultibodyconstraint, float f);

    public static final native void btMultiBodyConstraint_updateJacobianSizes(long j, btMultiBodyConstraint btmultibodyconstraint);

    public static final native long btMultiBodyDoubleData_baseCollider_get(long j, btMultiBodyDoubleData btmultibodydoubledata);

    public static final native void btMultiBodyDoubleData_baseCollider_set(long j, btMultiBodyDoubleData btmultibodydoubledata, long j2, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native long btMultiBodyDoubleData_baseInertia_get(long j, btMultiBodyDoubleData btmultibodydoubledata);

    public static final native void btMultiBodyDoubleData_baseInertia_set(long j, btMultiBodyDoubleData btmultibodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native double btMultiBodyDoubleData_baseMass_get(long j, btMultiBodyDoubleData btmultibodydoubledata);

    public static final native void btMultiBodyDoubleData_baseMass_set(long j, btMultiBodyDoubleData btmultibodydoubledata, double d);

    public static final native String btMultiBodyDoubleData_baseName_get(long j, btMultiBodyDoubleData btmultibodydoubledata);

    public static final native void btMultiBodyDoubleData_baseName_set(long j, btMultiBodyDoubleData btmultibodydoubledata, String str);

    public static final native long btMultiBodyDoubleData_baseWorldTransform_get(long j, btMultiBodyDoubleData btmultibodydoubledata);

    public static final native void btMultiBodyDoubleData_baseWorldTransform_set(long j, btMultiBodyDoubleData btmultibodydoubledata, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native long btMultiBodyDoubleData_links_get(long j, btMultiBodyDoubleData btmultibodydoubledata);

    public static final native void btMultiBodyDoubleData_links_set(long j, btMultiBodyDoubleData btmultibodydoubledata, long j2, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native int btMultiBodyDoubleData_numLinks_get(long j, btMultiBodyDoubleData btmultibodydoubledata);

    public static final native void btMultiBodyDoubleData_numLinks_set(long j, btMultiBodyDoubleData btmultibodydoubledata, int i);

    public static final native String btMultiBodyDoubleData_paddingPtr_get(long j, btMultiBodyDoubleData btmultibodydoubledata);

    public static final native void btMultiBodyDoubleData_paddingPtr_set(long j, btMultiBodyDoubleData btmultibodydoubledata, String str);

    public static final native String btMultiBodyDoubleData_padding_get(long j, btMultiBodyDoubleData btmultibodydoubledata);

    public static final native void btMultiBodyDoubleData_padding_set(long j, btMultiBodyDoubleData btmultibodydoubledata, String str);

    public static final native long btMultiBodyDynamicsWorld_SWIGUpcast(long j);

    public static final native void btMultiBodyDynamicsWorld_addMultiBodyConstraint(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, long j2, btMultiBodyConstraint btmultibodyconstraint);

    public static final native void btMultiBodyDynamicsWorld_addMultiBody__SWIG_0(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, long j2, btMultiBody btmultibody, int i, int i2);

    public static final native void btMultiBodyDynamicsWorld_addMultiBody__SWIG_1(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, long j2, btMultiBody btmultibody, int i);

    public static final native void btMultiBodyDynamicsWorld_addMultiBody__SWIG_2(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, long j2, btMultiBody btmultibody);

    public static final native void btMultiBodyDynamicsWorld_clearMultiBodyConstraintForces(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld);

    public static final native void btMultiBodyDynamicsWorld_clearMultiBodyForces(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld);

    public static final native void btMultiBodyDynamicsWorld_debugDrawMultiBodyConstraint(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, long j2, btMultiBodyConstraint btmultibodyconstraint);

    public static final native void btMultiBodyDynamicsWorld_forwardKinematics(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld);

    public static final native long btMultiBodyDynamicsWorld_getMultiBody(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, int i);

    public static final native long btMultiBodyDynamicsWorld_getMultiBodyConst(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, int i);

    public static final native long btMultiBodyDynamicsWorld_getMultiBodyConstraint(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, int i);

    public static final native long btMultiBodyDynamicsWorld_getMultiBodyConstraintConst(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, int i);

    public static final native int btMultiBodyDynamicsWorld_getNumMultiBodyConstraints(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld);

    public static final native int btMultiBodyDynamicsWorld_getNumMultibodies(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld);

    public static final native void btMultiBodyDynamicsWorld_integrateTransforms(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, float f);

    public static final native void btMultiBodyDynamicsWorld_removeMultiBody(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, long j2, btMultiBody btmultibody);

    public static final native void btMultiBodyDynamicsWorld_removeMultiBodyConstraint(long j, btMultiBodyDynamicsWorld btmultibodydynamicsworld, long j2, btMultiBodyConstraint btmultibodyconstraint);

    public static final native long btMultiBodyFixedConstraint_SWIGUpcast(long j);

    public static final native Matrix3 btMultiBodyFixedConstraint_getFrameInA(long j, btMultiBodyFixedConstraint btmultibodyfixedconstraint);

    public static final native Matrix3 btMultiBodyFixedConstraint_getFrameInB(long j, btMultiBodyFixedConstraint btmultibodyfixedconstraint);

    public static final native Vector3 btMultiBodyFixedConstraint_getPivotInA(long j, btMultiBodyFixedConstraint btmultibodyfixedconstraint);

    public static final native Vector3 btMultiBodyFixedConstraint_getPivotInB(long j, btMultiBodyFixedConstraint btmultibodyfixedconstraint);

    public static final native void btMultiBodyFixedConstraint_setFrameInA(long j, btMultiBodyFixedConstraint btmultibodyfixedconstraint, Matrix3 matrix3);

    public static final native void btMultiBodyFixedConstraint_setPivotInA(long j, btMultiBodyFixedConstraint btmultibodyfixedconstraint, Vector3 vector3);

    public static final native long btMultiBodyFloatData_baseCollider_get(long j, btMultiBodyFloatData btmultibodyfloatdata);

    public static final native void btMultiBodyFloatData_baseCollider_set(long j, btMultiBodyFloatData btmultibodyfloatdata, long j2, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native long btMultiBodyFloatData_baseInertia_get(long j, btMultiBodyFloatData btmultibodyfloatdata);

    public static final native void btMultiBodyFloatData_baseInertia_set(long j, btMultiBodyFloatData btmultibodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native float btMultiBodyFloatData_baseMass_get(long j, btMultiBodyFloatData btmultibodyfloatdata);

    public static final native void btMultiBodyFloatData_baseMass_set(long j, btMultiBodyFloatData btmultibodyfloatdata, float f);

    public static final native String btMultiBodyFloatData_baseName_get(long j, btMultiBodyFloatData btmultibodyfloatdata);

    public static final native void btMultiBodyFloatData_baseName_set(long j, btMultiBodyFloatData btmultibodyfloatdata, String str);

    public static final native long btMultiBodyFloatData_baseWorldTransform_get(long j, btMultiBodyFloatData btmultibodyfloatdata);

    public static final native void btMultiBodyFloatData_baseWorldTransform_set(long j, btMultiBodyFloatData btmultibodyfloatdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native long btMultiBodyFloatData_links_get(long j, btMultiBodyFloatData btmultibodyfloatdata);

    public static final native void btMultiBodyFloatData_links_set(long j, btMultiBodyFloatData btmultibodyfloatdata, long j2, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native int btMultiBodyFloatData_numLinks_get(long j, btMultiBodyFloatData btmultibodyfloatdata);

    public static final native void btMultiBodyFloatData_numLinks_set(long j, btMultiBodyFloatData btmultibodyfloatdata, int i);

    public static final native long btMultiBodyGearConstraint_SWIGUpcast(long j);

    public static final native Matrix3 btMultiBodyGearConstraint_getFrameInA(long j, btMultiBodyGearConstraint btmultibodygearconstraint);

    public static final native Matrix3 btMultiBodyGearConstraint_getFrameInB(long j, btMultiBodyGearConstraint btmultibodygearconstraint);

    public static final native Vector3 btMultiBodyGearConstraint_getPivotInA(long j, btMultiBodyGearConstraint btmultibodygearconstraint);

    public static final native Vector3 btMultiBodyGearConstraint_getPivotInB(long j, btMultiBodyGearConstraint btmultibodygearconstraint);

    public static final native void btMultiBodyGearConstraint_setFrameInA(long j, btMultiBodyGearConstraint btmultibodygearconstraint, Matrix3 matrix3);

    public static final native void btMultiBodyGearConstraint_setPivotInA(long j, btMultiBodyGearConstraint btmultibodygearconstraint, Vector3 vector3);

    public static final native long btMultiBodyJacobianData_deltaVelocitiesUnitImpulse_get(long j, btMultiBodyJacobianData btmultibodyjacobiandata);

    public static final native void btMultiBodyJacobianData_deltaVelocitiesUnitImpulse_set(long j, btMultiBodyJacobianData btmultibodyjacobiandata, long j2, btScalarArray btscalararray);

    public static final native long btMultiBodyJacobianData_deltaVelocities_get(long j, btMultiBodyJacobianData btmultibodyjacobiandata);

    public static final native void btMultiBodyJacobianData_deltaVelocities_set(long j, btMultiBodyJacobianData btmultibodyjacobiandata, long j2, btScalarArray btscalararray);

    public static final native int btMultiBodyJacobianData_fixedBodyId_get(long j, btMultiBodyJacobianData btmultibodyjacobiandata);

    public static final native void btMultiBodyJacobianData_fixedBodyId_set(long j, btMultiBodyJacobianData btmultibodyjacobiandata, int i);

    public static final native long btMultiBodyJacobianData_jacobians_get(long j, btMultiBodyJacobianData btmultibodyjacobiandata);

    public static final native void btMultiBodyJacobianData_jacobians_set(long j, btMultiBodyJacobianData btmultibodyjacobiandata, long j2, btScalarArray btscalararray);

    public static final native long btMultiBodyJacobianData_scratch_m_get(long j, btMultiBodyJacobianData btmultibodyjacobiandata);

    public static final native void btMultiBodyJacobianData_scratch_m_set(long j, btMultiBodyJacobianData btmultibodyjacobiandata, long j2);

    public static final native long btMultiBodyJacobianData_scratch_r_get(long j, btMultiBodyJacobianData btmultibodyjacobiandata);

    public static final native void btMultiBodyJacobianData_scratch_r_set(long j, btMultiBodyJacobianData btmultibodyjacobiandata, long j2, btScalarArray btscalararray);

    public static final native long btMultiBodyJacobianData_scratch_v_get(long j, btMultiBodyJacobianData btmultibodyjacobiandata);

    public static final native void btMultiBodyJacobianData_scratch_v_set(long j, btMultiBodyJacobianData btmultibodyjacobiandata, long j2, btVector3Array btvector3array);

    public static final native long btMultiBodyJacobianData_solverBodyPool_get(long j, btMultiBodyJacobianData btmultibodyjacobiandata);

    public static final native void btMultiBodyJacobianData_solverBodyPool_set(long j, btMultiBodyJacobianData btmultibodyjacobiandata, long j2);

    public static final native long btMultiBodyJointFeedback_reactionForces_get(long j, btMultiBodyJointFeedback btmultibodyjointfeedback);

    public static final native void btMultiBodyJointFeedback_reactionForces_set(long j, btMultiBodyJointFeedback btmultibodyjointfeedback, long j2, btSpatialForceVector btspatialforcevector);

    public static final native long btMultiBodyJointLimitConstraint_SWIGUpcast(long j);

    public static final native long btMultiBodyJointMotor_SWIGUpcast(long j);

    public static final native float btMultiBodyJointMotor_getErp(long j, btMultiBodyJointMotor btmultibodyjointmotor);

    public static final native void btMultiBodyJointMotor_setPositionTarget__SWIG_0(long j, btMultiBodyJointMotor btmultibodyjointmotor, float f, float f2);

    public static final native void btMultiBodyJointMotor_setPositionTarget__SWIG_1(long j, btMultiBodyJointMotor btmultibodyjointmotor, float f);

    public static final native void btMultiBodyJointMotor_setRhsClamp(long j, btMultiBodyJointMotor btmultibodyjointmotor, float f);

    public static final native void btMultiBodyJointMotor_setVelocityTarget__SWIG_0(long j, btMultiBodyJointMotor btmultibodyjointmotor, float f, float f2);

    public static final native void btMultiBodyJointMotor_setVelocityTarget__SWIG_1(long j, btMultiBodyJointMotor btmultibodyjointmotor, float f);

    public static final native long btMultiBodyLinkCollider_SWIGUpcast(long j);

    public static final native int btMultiBodyLinkCollider_link_get(long j, btMultiBodyLinkCollider btmultibodylinkcollider);

    public static final native void btMultiBodyLinkCollider_link_set(long j, btMultiBodyLinkCollider btmultibodylinkcollider, int i);

    public static final native long btMultiBodyLinkCollider_multiBody_get(long j, btMultiBodyLinkCollider btmultibodylinkcollider);

    public static final native void btMultiBodyLinkCollider_multiBody_set(long j, btMultiBodyLinkCollider btmultibodylinkcollider, long j2, btMultiBody btmultibody);

    public static final native long btMultiBodyLinkCollider_upcast(long j, btCollisionObject btcollisionobject);

    public static final native long btMultiBodyLinkCollider_upcastConstBtCollisionObject(long j, btCollisionObject btcollisionobject);

    public static final native int btMultiBodyLinkDoubleData_dofCount_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_dofCount_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, int i);

    public static final native long btMultiBodyLinkDoubleData_jointAxisBottom_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointAxisBottom_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btMultiBodyLinkDoubleData_jointAxisTop_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointAxisTop_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native double btMultiBodyLinkDoubleData_jointDamping_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointDamping_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double d);

    public static final native double btMultiBodyLinkDoubleData_jointFriction_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointFriction_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double d);

    public static final native double btMultiBodyLinkDoubleData_jointLowerLimit_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointLowerLimit_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double d);

    public static final native double btMultiBodyLinkDoubleData_jointMaxForce_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointMaxForce_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double d);

    public static final native double btMultiBodyLinkDoubleData_jointMaxVelocity_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointMaxVelocity_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double d);

    public static final native String btMultiBodyLinkDoubleData_jointName_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointName_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, String str);

    public static final native double[] btMultiBodyLinkDoubleData_jointPos_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointPos_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double[] dArr);

    public static final native double[] btMultiBodyLinkDoubleData_jointTorque_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointTorque_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double[] dArr);

    public static final native int btMultiBodyLinkDoubleData_jointType_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointType_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, int i);

    public static final native double btMultiBodyLinkDoubleData_jointUpperLimit_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointUpperLimit_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double d);

    public static final native double[] btMultiBodyLinkDoubleData_jointVel_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_jointVel_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double[] dArr);

    public static final native long btMultiBodyLinkDoubleData_linkCollider_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_linkCollider_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, long j2, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native long btMultiBodyLinkDoubleData_linkInertia_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_linkInertia_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native double btMultiBodyLinkDoubleData_linkMass_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_linkMass_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, double d);

    public static final native String btMultiBodyLinkDoubleData_linkName_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_linkName_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, String str);

    public static final native String btMultiBodyLinkDoubleData_paddingPtr_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_paddingPtr_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, String str);

    public static final native long btMultiBodyLinkDoubleData_parentComToThisComOffset_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_parentComToThisComOffset_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native int btMultiBodyLinkDoubleData_parentIndex_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_parentIndex_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, int i);

    public static final native int btMultiBodyLinkDoubleData_posVarCount_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_posVarCount_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, int i);

    public static final native long btMultiBodyLinkDoubleData_thisPivotToThisComOffset_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_thisPivotToThisComOffset_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btMultiBodyLinkDoubleData_zeroRotParentToThis_get(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata);

    public static final native void btMultiBodyLinkDoubleData_zeroRotParentToThis_set(long j, btMultiBodyLinkDoubleData btmultibodylinkdoubledata, long j2, btQuaternionDoubleData btquaterniondoubledata);

    public static final native int btMultiBodyLinkFloatData_dofCount_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_dofCount_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, int i);

    public static final native long btMultiBodyLinkFloatData_jointAxisBottom_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointAxisBottom_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btMultiBodyLinkFloatData_jointAxisTop_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointAxisTop_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native float btMultiBodyLinkFloatData_jointDamping_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointDamping_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float f);

    public static final native float btMultiBodyLinkFloatData_jointFriction_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointFriction_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float f);

    public static final native float btMultiBodyLinkFloatData_jointLowerLimit_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointLowerLimit_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float f);

    public static final native float btMultiBodyLinkFloatData_jointMaxForce_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointMaxForce_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float f);

    public static final native float btMultiBodyLinkFloatData_jointMaxVelocity_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointMaxVelocity_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float f);

    public static final native String btMultiBodyLinkFloatData_jointName_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointName_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, String str);

    public static final native float[] btMultiBodyLinkFloatData_jointPos_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointPos_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float[] fArr);

    public static final native float[] btMultiBodyLinkFloatData_jointTorque_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointTorque_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float[] fArr);

    public static final native int btMultiBodyLinkFloatData_jointType_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointType_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, int i);

    public static final native float btMultiBodyLinkFloatData_jointUpperLimit_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointUpperLimit_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float f);

    public static final native float[] btMultiBodyLinkFloatData_jointVel_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_jointVel_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float[] fArr);

    public static final native long btMultiBodyLinkFloatData_linkCollider_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_linkCollider_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, long j2, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native long btMultiBodyLinkFloatData_linkInertia_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_linkInertia_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native float btMultiBodyLinkFloatData_linkMass_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_linkMass_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, float f);

    public static final native String btMultiBodyLinkFloatData_linkName_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_linkName_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, String str);

    public static final native String btMultiBodyLinkFloatData_paddingPtr_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_paddingPtr_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, String str);

    public static final native long btMultiBodyLinkFloatData_parentComToThisComOffset_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_parentComToThisComOffset_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native int btMultiBodyLinkFloatData_parentIndex_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_parentIndex_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, int i);

    public static final native int btMultiBodyLinkFloatData_posVarCount_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_posVarCount_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, int i);

    public static final native long btMultiBodyLinkFloatData_thisPivotToThisComOffset_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_thisPivotToThisComOffset_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btMultiBodyLinkFloatData_zeroRotParentToThis_get(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata);

    public static final native void btMultiBodyLinkFloatData_zeroRotParentToThis_set(long j, btMultiBodyLinkFloatData btmultibodylinkfloatdata, long j2, btQuaternionFloatData btquaternionfloatdata);

    public static final native long btMultiBodyPoint2Point_SWIGUpcast(long j);

    public static final native Vector3 btMultiBodyPoint2Point_getPivotInB(long j, btMultiBodyPoint2Point btmultibodypoint2point);

    public static final native void btMultiBodyPoint2Point_operatorDeleteArray__SWIG_0(long j, btMultiBodyPoint2Point btmultibodypoint2point, long j2);

    public static final native void btMultiBodyPoint2Point_operatorDeleteArray__SWIG_1(long j, btMultiBodyPoint2Point btmultibodypoint2point, long j2, long j3);

    public static final native void btMultiBodyPoint2Point_operatorDelete__SWIG_0(long j, btMultiBodyPoint2Point btmultibodypoint2point, long j2);

    public static final native void btMultiBodyPoint2Point_operatorDelete__SWIG_1(long j, btMultiBodyPoint2Point btmultibodypoint2point, long j2, long j3);

    public static final native long btMultiBodyPoint2Point_operatorNewArray__SWIG_0(long j, btMultiBodyPoint2Point btmultibodypoint2point, long j2);

    public static final native long btMultiBodyPoint2Point_operatorNewArray__SWIG_1(long j, btMultiBodyPoint2Point btmultibodypoint2point, long j2, long j3);

    public static final native long btMultiBodyPoint2Point_operatorNew__SWIG_0(long j, btMultiBodyPoint2Point btmultibodypoint2point, long j2);

    public static final native long btMultiBodyPoint2Point_operatorNew__SWIG_1(long j, btMultiBodyPoint2Point btmultibodypoint2point, long j2, long j3);

    public static final native long btMultiBodySliderConstraint_SWIGUpcast(long j);

    public static final native Matrix3 btMultiBodySliderConstraint_getFrameInA(long j, btMultiBodySliderConstraint btmultibodysliderconstraint);

    public static final native Matrix3 btMultiBodySliderConstraint_getFrameInB(long j, btMultiBodySliderConstraint btmultibodysliderconstraint);

    public static final native Vector3 btMultiBodySliderConstraint_getJointAxis(long j, btMultiBodySliderConstraint btmultibodysliderconstraint);

    public static final native Vector3 btMultiBodySliderConstraint_getPivotInA(long j, btMultiBodySliderConstraint btmultibodysliderconstraint);

    public static final native Vector3 btMultiBodySliderConstraint_getPivotInB(long j, btMultiBodySliderConstraint btmultibodysliderconstraint);

    public static final native void btMultiBodySliderConstraint_setFrameInA(long j, btMultiBodySliderConstraint btmultibodysliderconstraint, Matrix3 matrix3);

    public static final native void btMultiBodySliderConstraint_setJointAxis(long j, btMultiBodySliderConstraint btmultibodysliderconstraint, Vector3 vector3);

    public static final native void btMultiBodySliderConstraint_setPivotInA(long j, btMultiBodySliderConstraint btmultibodysliderconstraint, Vector3 vector3);

    public static final native long btMultiBodySolverConstraint_angularComponentA_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_angularComponentA_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, btVector3 btvector3);

    public static final native long btMultiBodySolverConstraint_angularComponentB_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_angularComponentB_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, btVector3 btvector3);

    public static final native float btMultiBodySolverConstraint_appliedImpulse_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_appliedImpulse_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native float btMultiBodySolverConstraint_appliedPushImpulse_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_appliedPushImpulse_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native float btMultiBodySolverConstraint_cfm_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_cfm_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native long btMultiBodySolverConstraint_contactNormal1_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_contactNormal1_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, btVector3 btvector3);

    public static final native long btMultiBodySolverConstraint_contactNormal2_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_contactNormal2_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, btVector3 btvector3);

    public static final native int btMultiBodySolverConstraint_deltaVelAindex_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_deltaVelAindex_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native int btMultiBodySolverConstraint_deltaVelBindex_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_deltaVelBindex_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native int btMultiBodySolverConstraint_frictionIndex_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_frictionIndex_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native float btMultiBodySolverConstraint_friction_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_friction_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native int btMultiBodySolverConstraint_jacAindex_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_jacAindex_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native int btMultiBodySolverConstraint_jacBindex_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_jacBindex_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native float btMultiBodySolverConstraint_jacDiagABInv_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_jacDiagABInv_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native int btMultiBodySolverConstraint_linkA_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_linkA_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native int btMultiBodySolverConstraint_linkB_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_linkB_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native float btMultiBodySolverConstraint_lowerLimit_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_lowerLimit_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native long btMultiBodySolverConstraint_multiBodyA_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_multiBodyA_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, btMultiBody btmultibody);

    public static final native long btMultiBodySolverConstraint_multiBodyB_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_multiBodyB_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, btMultiBody btmultibody);

    public static final native void btMultiBodySolverConstraint_operatorDeleteArray__SWIG_0(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2);

    public static final native void btMultiBodySolverConstraint_operatorDeleteArray__SWIG_1(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, long j3);

    public static final native void btMultiBodySolverConstraint_operatorDelete__SWIG_0(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2);

    public static final native void btMultiBodySolverConstraint_operatorDelete__SWIG_1(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, long j3);

    public static final native long btMultiBodySolverConstraint_operatorNewArray__SWIG_0(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2);

    public static final native long btMultiBodySolverConstraint_operatorNewArray__SWIG_1(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, long j3);

    public static final native long btMultiBodySolverConstraint_operatorNew__SWIG_0(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2);

    public static final native long btMultiBodySolverConstraint_operatorNew__SWIG_1(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, long j3);

    public static final native long btMultiBodySolverConstraint_orgConstraint_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_orgConstraint_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, btMultiBodyConstraint btmultibodyconstraint);

    public static final native int btMultiBodySolverConstraint_orgDofIndex_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_orgDofIndex_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native long btMultiBodySolverConstraint_originalContactPoint_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_originalContactPoint_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2);

    public static final native int btMultiBodySolverConstraint_overrideNumSolverIterations_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_overrideNumSolverIterations_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native long btMultiBodySolverConstraint_relpos1CrossNormal_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_relpos1CrossNormal_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, btVector3 btvector3);

    public static final native long btMultiBodySolverConstraint_relpos2CrossNormal_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_relpos2CrossNormal_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, long j2, btVector3 btvector3);

    public static final native float btMultiBodySolverConstraint_rhsPenetration_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_rhsPenetration_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native float btMultiBodySolverConstraint_rhs_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_rhs_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native int btMultiBodySolverConstraint_solverBodyIdA_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_solverBodyIdA_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native int btMultiBodySolverConstraint_solverBodyIdB_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_solverBodyIdB_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, int i);

    public static final native float btMultiBodySolverConstraint_unusedPadding4_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_unusedPadding4_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native float btMultiBodySolverConstraint_upperLimit_get(long j, btMultiBodySolverConstraint btmultibodysolverconstraint);

    public static final native void btMultiBodySolverConstraint_upperLimit_set(long j, btMultiBodySolverConstraint btmultibodysolverconstraint, float f);

    public static final native void btMultiBody_addBaseConstraintForce(long j, btMultiBody btmultibody, Vector3 vector3);

    public static final native void btMultiBody_addBaseConstraintTorque(long j, btMultiBody btmultibody, Vector3 vector3);

    public static final native void btMultiBody_addBaseForce(long j, btMultiBody btmultibody, Vector3 vector3);

    public static final native void btMultiBody_addBaseTorque(long j, btMultiBody btmultibody, Vector3 vector3);

    public static final native void btMultiBody_addJointTorque(long j, btMultiBody btmultibody, int i, float f);

    public static final native void btMultiBody_addJointTorqueMultiDof__SWIG_0(long j, btMultiBody btmultibody, int i, int i2, float f);

    public static final native void btMultiBody_addJointTorqueMultiDof__SWIG_1(long j, btMultiBody btmultibody, int i, FloatBuffer floatBuffer);

    public static final native void btMultiBody_addLinkConstraintForce(long j, btMultiBody btmultibody, int i, Vector3 vector3);

    public static final native void btMultiBody_addLinkConstraintTorque(long j, btMultiBody btmultibody, int i, Vector3 vector3);

    public static final native void btMultiBody_addLinkForce(long j, btMultiBody btmultibody, int i, Vector3 vector3);

    public static final native void btMultiBody_addLinkTorque(long j, btMultiBody btmultibody, int i, Vector3 vector3);

    public static final native void btMultiBody_applyDeltaVeeMultiDof(long j, btMultiBody btmultibody, FloatBuffer floatBuffer, float f);

    public static final native void btMultiBody_applyDeltaVeeMultiDof2(long j, btMultiBody btmultibody, FloatBuffer floatBuffer, float f);

    public static final native void btMultiBody_calcAccelerationDeltasMultiDof(long j, btMultiBody btmultibody, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, long j2, btScalarArray btscalararray, long j3, btVector3Array btvector3array);

    public static final native int btMultiBody_calculateSerializeBufferSize(long j, btMultiBody btmultibody);

    public static final native void btMultiBody_checkMotionAndSleepIfRequired(long j, btMultiBody btmultibody, float f);

    public static final native void btMultiBody_clearConstraintForces(long j, btMultiBody btmultibody);

    public static final native void btMultiBody_clearForcesAndTorques(long j, btMultiBody btmultibody);

    public static final native void btMultiBody_clearVelocities(long j, btMultiBody btmultibody);

    public static final native void btMultiBody_compTreeLinkVelocities(long j, btMultiBody btmultibody, long j2, btVector3 btvector3, long j3, btVector3 btvector4);

    public static final native void btMultiBody_computeAccelerationsArticulatedBodyAlgorithmMultiDof__SWIG_0(long j, btMultiBody btmultibody, float f, long j2, btScalarArray btscalararray, long j3, btVector3Array btvector3array, long j4, boolean z);

    public static final native void btMultiBody_computeAccelerationsArticulatedBodyAlgorithmMultiDof__SWIG_1(long j, btMultiBody btmultibody, float f, long j2, btScalarArray btscalararray, long j3, btVector3Array btvector3array, long j4);

    public static final native void btMultiBody_fillConstraintJacobianMultiDof(long j, btMultiBody btmultibody, int i, Vector3 vector3, Vector3 vector4, Vector3 vector5, FloatBuffer floatBuffer, long j2, btScalarArray btscalararray, long j3, btVector3Array btvector3array, long j4);

    public static final native void btMultiBody_fillContactJacobianMultiDof(long j, btMultiBody btmultibody, int i, Vector3 vector3, Vector3 vector4, FloatBuffer floatBuffer, long j2, btScalarArray btscalararray, long j3, btVector3Array btvector3array, long j4);

    public static final native void btMultiBody_finalizeMultiDof(long j, btMultiBody btmultibody);

    public static final native void btMultiBody_forwardKinematics(long j, btMultiBody btmultibody, long j2, long j3, btVector3Array btvector3array);

    public static final native float btMultiBody_getAngularDamping(long j, btMultiBody btmultibody);

    public static final native Vector3 btMultiBody_getAngularMomentum(long j, btMultiBody btmultibody);

    public static final native long btMultiBody_getBaseCollider(long j, btMultiBody btmultibody);

    public static final native long btMultiBody_getBaseColliderConst(long j, btMultiBody btmultibody);

    public static final native Vector3 btMultiBody_getBaseForce(long j, btMultiBody btmultibody);

    public static final native Vector3 btMultiBody_getBaseInertia(long j, btMultiBody btmultibody);

    public static final native float btMultiBody_getBaseMass(long j, btMultiBody btmultibody);

    public static final native String btMultiBody_getBaseName(long j, btMultiBody btmultibody);

    public static final native Vector3 btMultiBody_getBaseOmega(long j, btMultiBody btmultibody);

    public static final native Vector3 btMultiBody_getBasePos(long j, btMultiBody btmultibody);

    public static final native Vector3 btMultiBody_getBaseTorque(long j, btMultiBody btmultibody);

    public static final native Vector3 btMultiBody_getBaseVel(long j, btMultiBody btmultibody);

    public static final native Matrix4 btMultiBody_getBaseWorldTransform(long j, btMultiBody btmultibody);

    public static final native boolean btMultiBody_getCanSleep(long j, btMultiBody btmultibody);

    public static final native int btMultiBody_getCompanionId(long j, btMultiBody btmultibody);

    public static final native float btMultiBody_getJointPos(long j, btMultiBody btmultibody, int i);

    public static final native FloatBuffer btMultiBody_getJointPosMultiDof(long j, btMultiBody btmultibody, int i);

    public static final native FloatBuffer btMultiBody_getJointPosMultiDofConst(long j, btMultiBody btmultibody, int i);

    public static final native float btMultiBody_getJointTorque(long j, btMultiBody btmultibody, int i);

    public static final native FloatBuffer btMultiBody_getJointTorqueMultiDof(long j, btMultiBody btmultibody, int i);

    public static final native float btMultiBody_getJointVel(long j, btMultiBody btmultibody, int i);

    public static final native FloatBuffer btMultiBody_getJointVelMultiDof(long j, btMultiBody btmultibody, int i);

    public static final native FloatBuffer btMultiBody_getJointVelMultiDofConst(long j, btMultiBody btmultibody, int i);

    public static final native float btMultiBody_getKineticEnergy(long j, btMultiBody btmultibody);

    public static final native float btMultiBody_getLinearDamping(long j, btMultiBody btmultibody);

    public static final native long btMultiBody_getLink(long j, btMultiBody btmultibody, int i);

    public static final native long btMultiBody_getLinkCollider(long j, btMultiBody btmultibody, int i);

    public static final native long btMultiBody_getLinkConst(long j, btMultiBody btmultibody, int i);

    public static final native Vector3 btMultiBody_getLinkForce(long j, btMultiBody btmultibody, int i);

    public static final native Vector3 btMultiBody_getLinkInertia(long j, btMultiBody btmultibody, int i);

    public static final native float btMultiBody_getLinkMass(long j, btMultiBody btmultibody, int i);

    public static final native Vector3 btMultiBody_getLinkTorque(long j, btMultiBody btmultibody, int i);

    public static final native float btMultiBody_getMaxAppliedImpulse(long j, btMultiBody btmultibody);

    public static final native float btMultiBody_getMaxCoordinateVelocity(long j, btMultiBody btmultibody);

    public static final native int btMultiBody_getNumDofs(long j, btMultiBody btmultibody);

    public static final native int btMultiBody_getNumLinks(long j, btMultiBody btmultibody);

    public static final native int btMultiBody_getNumPosVars(long j, btMultiBody btmultibody);

    public static final native int btMultiBody_getParent(long j, btMultiBody btmultibody, int i);

    public static final native Quaternion btMultiBody_getParentToLocalRot(long j, btMultiBody btmultibody, int i);

    public static final native Vector3 btMultiBody_getRVector(long j, btMultiBody btmultibody, int i);

    public static final native boolean btMultiBody_getUseGyroTerm(long j, btMultiBody btmultibody);

    public static final native int btMultiBody_getUserIndex(long j, btMultiBody btmultibody);

    public static final native int btMultiBody_getUserIndex2(long j, btMultiBody btmultibody);

    public static final native long btMultiBody_getUserPointer(long j, btMultiBody btmultibody);

    public static final native FloatBuffer btMultiBody_getVelocityVector(long j, btMultiBody btmultibody);

    public static final native Quaternion btMultiBody_getWorldToBaseRot(long j, btMultiBody btmultibody);

    public static final native void btMultiBody_goToSleep(long j, btMultiBody btmultibody);

    public static final native boolean btMultiBody_hasFixedBase(long j, btMultiBody btmultibody);

    public static final native boolean btMultiBody_hasSelfCollision(long j, btMultiBody btmultibody);

    public static final native boolean btMultiBody_internalNeedsJointFeedback(long j, btMultiBody btmultibody);

    public static final native boolean btMultiBody_isAwake(long j, btMultiBody btmultibody);

    public static final native boolean btMultiBody_isPosUpdated(long j, btMultiBody btmultibody);

    public static final native boolean btMultiBody_isUsingGlobalVelocities(long j, btMultiBody btmultibody);

    public static final native boolean btMultiBody_isUsingRK4Integration(long j, btMultiBody btmultibody);

    public static final native Vector3 btMultiBody_localDirToWorld(long j, btMultiBody btmultibody, int i, Vector3 vector3);

    public static final native Matrix3 btMultiBody_localFrameToWorld(long j, btMultiBody btmultibody, int i, Matrix3 matrix3);

    public static final native Vector3 btMultiBody_localPosToWorld(long j, btMultiBody btmultibody, int i, Vector3 vector3);

    public static final native void btMultiBody_operatorDeleteArray__SWIG_0(long j, btMultiBody btmultibody, long j2);

    public static final native void btMultiBody_operatorDeleteArray__SWIG_1(long j, btMultiBody btmultibody, long j2, long j3);

    public static final native void btMultiBody_operatorDelete__SWIG_0(long j, btMultiBody btmultibody, long j2);

    public static final native void btMultiBody_operatorDelete__SWIG_1(long j, btMultiBody btmultibody, long j2, long j3);

    public static final native long btMultiBody_operatorNewArray__SWIG_0(long j, btMultiBody btmultibody, long j2);

    public static final native long btMultiBody_operatorNewArray__SWIG_1(long j, btMultiBody btmultibody, long j2, long j3);

    public static final native long btMultiBody_operatorNew__SWIG_0(long j, btMultiBody btmultibody, long j2);

    public static final native long btMultiBody_operatorNew__SWIG_1(long j, btMultiBody btmultibody, long j2, long j3);

    public static final native void btMultiBody_processDeltaVeeMultiDof2(long j, btMultiBody btmultibody);

    public static final native String btMultiBody_serialize(long j, btMultiBody btmultibody, long j2, long j3, btSerializer btserializer);

    public static final native void btMultiBody_setAngularDamping(long j, btMultiBody btmultibody, float f);

    public static final native void btMultiBody_setBaseCollider(long j, btMultiBody btmultibody, long j2, btMultiBodyLinkCollider btmultibodylinkcollider);

    public static final native void btMultiBody_setBaseInertia(long j, btMultiBody btmultibody, Vector3 vector3);

    public static final native void btMultiBody_setBaseMass(long j, btMultiBody btmultibody, float f);

    public static final native void btMultiBody_setBaseName(long j, btMultiBody btmultibody, String str);

    public static final native void btMultiBody_setBaseOmega(long j, btMultiBody btmultibody, Vector3 vector3);

    public static final native void btMultiBody_setBasePos(long j, btMultiBody btmultibody, Vector3 vector3);

    public static final native void btMultiBody_setBaseVel(long j, btMultiBody btmultibody, Vector3 vector3);

    public static final native void btMultiBody_setBaseWorldTransform(long j, btMultiBody btmultibody, Matrix4 matrix4);

    public static final native void btMultiBody_setCanSleep(long j, btMultiBody btmultibody, boolean z);

    public static final native void btMultiBody_setCompanionId(long j, btMultiBody btmultibody, int i);

    public static final native void btMultiBody_setHasSelfCollision(long j, btMultiBody btmultibody, boolean z);

    public static final native void btMultiBody_setJointPos(long j, btMultiBody btmultibody, int i, float f);

    public static final native void btMultiBody_setJointPosMultiDof(long j, btMultiBody btmultibody, int i, FloatBuffer floatBuffer);

    public static final native void btMultiBody_setJointVel(long j, btMultiBody btmultibody, int i, float f);

    public static final native void btMultiBody_setJointVelMultiDof(long j, btMultiBody btmultibody, int i, FloatBuffer floatBuffer);

    public static final native void btMultiBody_setLinearDamping(long j, btMultiBody btmultibody, float f);

    public static final native void btMultiBody_setMaxAppliedImpulse(long j, btMultiBody btmultibody, float f);

    public static final native void btMultiBody_setMaxCoordinateVelocity(long j, btMultiBody btmultibody, float f);

    public static final native void btMultiBody_setNumLinks(long j, btMultiBody btmultibody, int i);

    public static final native void btMultiBody_setPosUpdated(long j, btMultiBody btmultibody, boolean z);

    public static final native void btMultiBody_setUseGyroTerm(long j, btMultiBody btmultibody, boolean z);

    public static final native void btMultiBody_setUserIndex(long j, btMultiBody btmultibody, int i);

    public static final native void btMultiBody_setUserIndex2(long j, btMultiBody btmultibody, int i);

    public static final native void btMultiBody_setUserPointer(long j, btMultiBody btmultibody, long j2);

    public static final native void btMultiBody_setWorldToBaseRot(long j, btMultiBody btmultibody, Quaternion quaternion);

    public static final native void btMultiBody_setupFixed__SWIG_0(long j, btMultiBody btmultibody, int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, boolean z);

    public static final native void btMultiBody_setupFixed__SWIG_1(long j, btMultiBody btmultibody, int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5);

    public static final native void btMultiBody_setupPlanar__SWIG_0(long j, btMultiBody btmultibody, int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, boolean z);

    public static final native void btMultiBody_setupPlanar__SWIG_1(long j, btMultiBody btmultibody, int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5);

    public static final native void btMultiBody_setupPrismatic(long j, btMultiBody btmultibody, int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, Vector3 vector6, boolean z);

    public static final native void btMultiBody_setupRevolute__SWIG_0(long j, btMultiBody btmultibody, int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, Vector3 vector6, boolean z);

    public static final native void btMultiBody_setupRevolute__SWIG_1(long j, btMultiBody btmultibody, int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native void btMultiBody_setupSpherical__SWIG_0(long j, btMultiBody btmultibody, int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, boolean z);

    public static final native void btMultiBody_setupSpherical__SWIG_1(long j, btMultiBody btmultibody, int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5);

    public static final native void btMultiBody_stepPositionsMultiDof__SWIG_0(long j, btMultiBody btmultibody, float f, FloatBuffer floatBuffer, FloatBuffer floatBuffer2);

    public static final native void btMultiBody_stepPositionsMultiDof__SWIG_1(long j, btMultiBody btmultibody, float f, FloatBuffer floatBuffer);

    public static final native void btMultiBody_stepPositionsMultiDof__SWIG_2(long j, btMultiBody btmultibody, float f);

    public static final native void btMultiBody_stepVelocitiesMultiDof__SWIG_0(long j, btMultiBody btmultibody, float f, long j2, btScalarArray btscalararray, long j3, btVector3Array btvector3array, long j4, boolean z);

    public static final native void btMultiBody_stepVelocitiesMultiDof__SWIG_1(long j, btMultiBody btmultibody, float f, long j2, btScalarArray btscalararray, long j3, btVector3Array btvector3array, long j4);

    public static final native void btMultiBody_updateCollisionObjectWorldTransforms(long j, btMultiBody btmultibody, long j2, long j3, btVector3Array btvector3array);

    public static final native void btMultiBody_useGlobalVelocities(long j, btMultiBody btmultibody, boolean z);

    public static final native void btMultiBody_useRK4Integration(long j, btMultiBody btmultibody, boolean z);

    public static final native void btMultiBody_wakeUp(long j, btMultiBody btmultibody);

    public static final native Vector3 btMultiBody_worldDirToLocal(long j, btMultiBody btmultibody, int i, Vector3 vector3);

    public static final native Vector3 btMultiBody_worldPosToLocal(long j, btMultiBody btmultibody, int i, Vector3 vector3);

    public static final native long btMultibodyLink_absFrameLocVelocity_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_absFrameLocVelocity_set(long j, btMultibodyLink btmultibodylink, long j2, btSpatialMotionVector btspatialmotionvector);

    public static final native long btMultibodyLink_absFrameTotVelocity_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_absFrameTotVelocity_set(long j, btMultibodyLink btmultibodylink, long j2, btSpatialMotionVector btspatialmotionvector);

    public static final native long btMultibodyLink_appliedConstraintForce_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_appliedConstraintForce_set(long j, btMultibodyLink btmultibodylink, long j2, btVector3 btvector3);

    public static final native long btMultibodyLink_appliedConstraintTorque_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_appliedConstraintTorque_set(long j, btMultibodyLink btmultibodylink, long j2, btVector3 btvector3);

    public static final native long btMultibodyLink_appliedForce_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_appliedForce_set(long j, btMultibodyLink btmultibodylink, long j2, btVector3 btvector3);

    public static final native long btMultibodyLink_appliedTorque_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_appliedTorque_set(long j, btMultibodyLink btmultibodylink, long j2, btVector3 btvector3);

    public static final native long btMultibodyLink_axes_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_axes_set(long j, btMultibodyLink btmultibodylink, long j2, btSpatialMotionVector btspatialmotionvector);

    public static final native long btMultibodyLink_cachedRVector_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_cachedRVector_set(long j, btMultibodyLink btmultibodylink, long j2, btVector3 btvector3);

    public static final native long btMultibodyLink_cachedRotParentToThis_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_cachedRotParentToThis_set(long j, btMultibodyLink btmultibodylink, long j2, btQuaternion btquaternion);

    public static final native long btMultibodyLink_cachedWorldTransform_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_cachedWorldTransform_set(long j, btMultibodyLink btmultibodylink, long j2, btTransform bttransform);

    public static final native int btMultibodyLink_cfgOffset_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_cfgOffset_set(long j, btMultibodyLink btmultibodylink, int i);

    public static final native long btMultibodyLink_collider_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_collider_set(long j, btMultibodyLink btmultibodylink, long j2, btMultiBodyLinkCollider btmultibodylinkcollider);

    public static final native long btMultibodyLink_dVector_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_dVector_set(long j, btMultibodyLink btmultibodylink, long j2, btVector3 btvector3);

    public static final native int btMultibodyLink_dofCount_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_dofCount_set(long j, btMultibodyLink btmultibodylink, int i);

    public static final native int btMultibodyLink_dofOffset_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_dofOffset_set(long j, btMultibodyLink btmultibodylink, int i);

    public static final native long btMultibodyLink_eVector_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_eVector_set(long j, btMultibodyLink btmultibodylink, long j2, btVector3 btvector3);

    public static final native int btMultibodyLink_flags_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_flags_set(long j, btMultibodyLink btmultibodylink, int i);

    public static final native Vector3 btMultibodyLink_getAxisBottom(long j, btMultibodyLink btmultibodylink, int i);

    public static final native Vector3 btMultibodyLink_getAxisTop(long j, btMultibodyLink btmultibodylink, int i);

    public static final native long btMultibodyLink_inertiaLocal_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_inertiaLocal_set(long j, btMultibodyLink btmultibodylink, long j2, btVector3 btvector3);

    public static final native float btMultibodyLink_jointDamping_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointDamping_set(long j, btMultibodyLink btmultibodylink, float f);

    public static final native long btMultibodyLink_jointFeedback_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointFeedback_set(long j, btMultibodyLink btmultibodylink, long j2, btMultiBodyJointFeedback btmultibodyjointfeedback);

    public static final native float btMultibodyLink_jointFriction_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointFriction_set(long j, btMultibodyLink btmultibodylink, float f);

    public static final native float btMultibodyLink_jointLowerLimit_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointLowerLimit_set(long j, btMultibodyLink btmultibodylink, float f);

    public static final native float btMultibodyLink_jointMaxForce_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointMaxForce_set(long j, btMultibodyLink btmultibodylink, float f);

    public static final native float btMultibodyLink_jointMaxVelocity_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointMaxVelocity_set(long j, btMultibodyLink btmultibodylink, float f);

    public static final native String btMultibodyLink_jointName_get(long j, btMultibodyLink btmultibodylink);

    public static final native float[] btMultibodyLink_jointPos_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointPos_set(long j, btMultibodyLink btmultibodylink, float[] fArr);

    public static final native float[] btMultibodyLink_jointTorque_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointTorque_set(long j, btMultibodyLink btmultibodylink, float[] fArr);

    public static final native int btMultibodyLink_jointType_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointType_set(long j, btMultibodyLink btmultibodylink, int i);

    public static final native float btMultibodyLink_jointUpperLimit_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_jointUpperLimit_set(long j, btMultibodyLink btmultibodylink, float f);

    public static final native String btMultibodyLink_linkName_get(long j, btMultibodyLink btmultibodylink);

    public static final native float btMultibodyLink_mass_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_mass_set(long j, btMultibodyLink btmultibodylink, float f);

    public static final native void btMultibodyLink_operatorDeleteArray__SWIG_0(long j, btMultibodyLink btmultibodylink, long j2);

    public static final native void btMultibodyLink_operatorDeleteArray__SWIG_1(long j, btMultibodyLink btmultibodylink, long j2, long j3);

    public static final native void btMultibodyLink_operatorDelete__SWIG_0(long j, btMultibodyLink btmultibodylink, long j2);

    public static final native void btMultibodyLink_operatorDelete__SWIG_1(long j, btMultibodyLink btmultibodylink, long j2, long j3);

    public static final native long btMultibodyLink_operatorNewArray__SWIG_0(long j, btMultibodyLink btmultibodylink, long j2);

    public static final native long btMultibodyLink_operatorNewArray__SWIG_1(long j, btMultibodyLink btmultibodylink, long j2, long j3);

    public static final native long btMultibodyLink_operatorNew__SWIG_0(long j, btMultibodyLink btmultibodylink, long j2);

    public static final native long btMultibodyLink_operatorNew__SWIG_1(long j, btMultibodyLink btmultibodylink, long j2, long j3);

    public static final native int btMultibodyLink_parent_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_parent_set(long j, btMultibodyLink btmultibodylink, int i);

    public static final native int btMultibodyLink_posVarCount_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_posVarCount_set(long j, btMultibodyLink btmultibodylink, int i);

    public static final native void btMultibodyLink_setAxisBottom__SWIG_0(long j, btMultibodyLink btmultibodylink, int i, Vector3 vector3);

    public static final native void btMultibodyLink_setAxisBottom__SWIG_1(long j, btMultibodyLink btmultibodylink, int i, float f, float f2, float f3);

    public static final native void btMultibodyLink_setAxisTop__SWIG_0(long j, btMultibodyLink btmultibodylink, int i, Vector3 vector3);

    public static final native void btMultibodyLink_setAxisTop__SWIG_1(long j, btMultibodyLink btmultibodylink, int i, float f, float f2, float f3);

    public static final native void btMultibodyLink_updateCacheMultiDof__SWIG_0(long j, btMultibodyLink btmultibodylink, FloatBuffer floatBuffer);

    public static final native void btMultibodyLink_updateCacheMultiDof__SWIG_1(long j, btMultibodyLink btmultibodylink);

    public static final native long btMultibodyLink_userPtr_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_userPtr_set(long j, btMultibodyLink btmultibodylink, long j2);

    public static final native long btMultibodyLink_zeroRotParentToThis_get(long j, btMultibodyLink btmultibodylink);

    public static final native void btMultibodyLink_zeroRotParentToThis_set(long j, btMultibodyLink btmultibodylink, long j2, btQuaternion btquaternion);

    public static final native long btNNCGConstraintSolver_SWIGUpcast(long j);

    public static final native boolean btNNCGConstraintSolver_onlyForNoneContact_get(long j, btNNCGConstraintSolver btnncgconstraintsolver);

    public static final native void btNNCGConstraintSolver_onlyForNoneContact_set(long j, btNNCGConstraintSolver btnncgconstraintsolver, boolean z);

    public static final native void btNNCGConstraintSolver_operatorDeleteArray__SWIG_0(long j, btNNCGConstraintSolver btnncgconstraintsolver, long j2);

    public static final native void btNNCGConstraintSolver_operatorDeleteArray__SWIG_1(long j, btNNCGConstraintSolver btnncgconstraintsolver, long j2, long j3);

    public static final native void btNNCGConstraintSolver_operatorDelete__SWIG_0(long j, btNNCGConstraintSolver btnncgconstraintsolver, long j2);

    public static final native void btNNCGConstraintSolver_operatorDelete__SWIG_1(long j, btNNCGConstraintSolver btnncgconstraintsolver, long j2, long j3);

    public static final native long btNNCGConstraintSolver_operatorNewArray__SWIG_0(long j, btNNCGConstraintSolver btnncgconstraintsolver, long j2);

    public static final native long btNNCGConstraintSolver_operatorNewArray__SWIG_1(long j, btNNCGConstraintSolver btnncgconstraintsolver, long j2, long j3);

    public static final native long btNNCGConstraintSolver_operatorNew__SWIG_0(long j, btNNCGConstraintSolver btnncgconstraintsolver, long j2);

    public static final native long btNNCGConstraintSolver_operatorNew__SWIG_1(long j, btNNCGConstraintSolver btnncgconstraintsolver, long j2, long j3);

    public static final native long btPoint2PointConstraintDoubleData2_pivotInA_get(long j, btPoint2PointConstraintDoubleData2 btpoint2pointconstraintdoubledata2);

    public static final native void btPoint2PointConstraintDoubleData2_pivotInA_set(long j, btPoint2PointConstraintDoubleData2 btpoint2pointconstraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btPoint2PointConstraintDoubleData2_pivotInB_get(long j, btPoint2PointConstraintDoubleData2 btpoint2pointconstraintdoubledata2);

    public static final native void btPoint2PointConstraintDoubleData2_pivotInB_set(long j, btPoint2PointConstraintDoubleData2 btpoint2pointconstraintdoubledata2, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btPoint2PointConstraintDoubleData2_typeConstraintData_get(long j, btPoint2PointConstraintDoubleData2 btpoint2pointconstraintdoubledata2);

    public static final native void btPoint2PointConstraintDoubleData2_typeConstraintData_set(long j, btPoint2PointConstraintDoubleData2 btpoint2pointconstraintdoubledata2, long j2, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native long btPoint2PointConstraintDoubleData_pivotInA_get(long j, btPoint2PointConstraintDoubleData btpoint2pointconstraintdoubledata);

    public static final native void btPoint2PointConstraintDoubleData_pivotInA_set(long j, btPoint2PointConstraintDoubleData btpoint2pointconstraintdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btPoint2PointConstraintDoubleData_pivotInB_get(long j, btPoint2PointConstraintDoubleData btpoint2pointconstraintdoubledata);

    public static final native void btPoint2PointConstraintDoubleData_pivotInB_set(long j, btPoint2PointConstraintDoubleData btpoint2pointconstraintdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btPoint2PointConstraintDoubleData_typeConstraintData_get(long j, btPoint2PointConstraintDoubleData btpoint2pointconstraintdoubledata);

    public static final native void btPoint2PointConstraintDoubleData_typeConstraintData_set(long j, btPoint2PointConstraintDoubleData btpoint2pointconstraintdoubledata, long j2, btTypedConstraintData bttypedconstraintdata);

    public static final native long btPoint2PointConstraintFloatData_pivotInA_get(long j, btPoint2PointConstraintFloatData btpoint2pointconstraintfloatdata);

    public static final native void btPoint2PointConstraintFloatData_pivotInA_set(long j, btPoint2PointConstraintFloatData btpoint2pointconstraintfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btPoint2PointConstraintFloatData_pivotInB_get(long j, btPoint2PointConstraintFloatData btpoint2pointconstraintfloatdata);

    public static final native void btPoint2PointConstraintFloatData_pivotInB_set(long j, btPoint2PointConstraintFloatData btpoint2pointconstraintfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btPoint2PointConstraintFloatData_typeConstraintData_get(long j, btPoint2PointConstraintFloatData btpoint2pointconstraintfloatdata);

    public static final native void btPoint2PointConstraintFloatData_typeConstraintData_set(long j, btPoint2PointConstraintFloatData btpoint2pointconstraintfloatdata, long j2, btTypedConstraintData bttypedconstraintdata);

    public static final native long btPoint2PointConstraint_SWIGUpcast(long j);

    public static final native int btPoint2PointConstraint_getFlags(long j, btPoint2PointConstraint btpoint2pointconstraint);

    public static final native void btPoint2PointConstraint_getInfo1NonVirtual(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2, btTypedConstraint.btConstraintInfo1 btconstraintinfo1);

    public static final native void btPoint2PointConstraint_getInfo2NonVirtual(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5);

    public static final native float btPoint2PointConstraint_getParam__SWIG_0(long j, btPoint2PointConstraint btpoint2pointconstraint, int i, int i2);

    public static final native float btPoint2PointConstraint_getParam__SWIG_1(long j, btPoint2PointConstraint btpoint2pointconstraint, int i);

    public static final native Vector3 btPoint2PointConstraint_getPivotInA(long j, btPoint2PointConstraint btpoint2pointconstraint);

    public static final native Vector3 btPoint2PointConstraint_getPivotInB(long j, btPoint2PointConstraint btpoint2pointconstraint);

    public static final native void btPoint2PointConstraint_operatorDeleteArray__SWIG_0(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2);

    public static final native void btPoint2PointConstraint_operatorDeleteArray__SWIG_1(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2, long j3);

    public static final native void btPoint2PointConstraint_operatorDelete__SWIG_0(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2);

    public static final native void btPoint2PointConstraint_operatorDelete__SWIG_1(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2, long j3);

    public static final native long btPoint2PointConstraint_operatorNewArray__SWIG_0(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2);

    public static final native long btPoint2PointConstraint_operatorNewArray__SWIG_1(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2, long j3);

    public static final native long btPoint2PointConstraint_operatorNew__SWIG_0(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2);

    public static final native long btPoint2PointConstraint_operatorNew__SWIG_1(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2, long j3);

    public static final native void btPoint2PointConstraint_setParam__SWIG_0(long j, btPoint2PointConstraint btpoint2pointconstraint, int i, float f, int i2);

    public static final native void btPoint2PointConstraint_setParam__SWIG_1(long j, btPoint2PointConstraint btpoint2pointconstraint, int i, float f);

    public static final native void btPoint2PointConstraint_setPivotA(long j, btPoint2PointConstraint btpoint2pointconstraint, Vector3 vector3);

    public static final native void btPoint2PointConstraint_setPivotB(long j, btPoint2PointConstraint btpoint2pointconstraint, Vector3 vector3);

    public static final native long btPoint2PointConstraint_setting_get(long j, btPoint2PointConstraint btpoint2pointconstraint);

    public static final native void btPoint2PointConstraint_setting_set(long j, btPoint2PointConstraint btpoint2pointconstraint, long j2, btConstraintSetting btconstraintsetting);

    public static final native void btPoint2PointConstraint_updateRHS(long j, btPoint2PointConstraint btpoint2pointconstraint, float f);

    public static final native boolean btPoint2PointConstraint_useSolveConstraintObsolete_get(long j, btPoint2PointConstraint btpoint2pointconstraint);

    public static final native void btPoint2PointConstraint_useSolveConstraintObsolete_set(long j, btPoint2PointConstraint btpoint2pointconstraint, boolean z);

    public static final native long btRaycastVehicle_SWIGUpcast(long j);

    public static final native long btRaycastVehicle_addWheel(long j, btRaycastVehicle btraycastvehicle, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, long j2, btRaycastVehicle.btVehicleTuning btvehicletuning, boolean z);

    public static final native void btRaycastVehicle_applyEngineForce(long j, btRaycastVehicle btraycastvehicle, float f, int i);

    public static final native float btRaycastVehicle_btVehicleTuning_frictionSlip_get(long j, btRaycastVehicle.btVehicleTuning btvehicletuning);

    public static final native void btRaycastVehicle_btVehicleTuning_frictionSlip_set(long j, btRaycastVehicle.btVehicleTuning btvehicletuning, float f);

    public static final native float btRaycastVehicle_btVehicleTuning_maxSuspensionForce_get(long j, btRaycastVehicle.btVehicleTuning btvehicletuning);

    public static final native void btRaycastVehicle_btVehicleTuning_maxSuspensionForce_set(long j, btRaycastVehicle.btVehicleTuning btvehicletuning, float f);

    public static final native float btRaycastVehicle_btVehicleTuning_maxSuspensionTravelCm_get(long j, btRaycastVehicle.btVehicleTuning btvehicletuning);

    public static final native void btRaycastVehicle_btVehicleTuning_maxSuspensionTravelCm_set(long j, btRaycastVehicle.btVehicleTuning btvehicletuning, float f);

    public static final native float btRaycastVehicle_btVehicleTuning_suspensionCompression_get(long j, btRaycastVehicle.btVehicleTuning btvehicletuning);

    public static final native void btRaycastVehicle_btVehicleTuning_suspensionCompression_set(long j, btRaycastVehicle.btVehicleTuning btvehicletuning, float f);

    public static final native float btRaycastVehicle_btVehicleTuning_suspensionDamping_get(long j, btRaycastVehicle.btVehicleTuning btvehicletuning);

    public static final native void btRaycastVehicle_btVehicleTuning_suspensionDamping_set(long j, btRaycastVehicle.btVehicleTuning btvehicletuning, float f);

    public static final native float btRaycastVehicle_btVehicleTuning_suspensionStiffness_get(long j, btRaycastVehicle.btVehicleTuning btvehicletuning);

    public static final native void btRaycastVehicle_btVehicleTuning_suspensionStiffness_set(long j, btRaycastVehicle.btVehicleTuning btvehicletuning, float f);

    public static final native Matrix4 btRaycastVehicle_getChassisWorldTransform(long j, btRaycastVehicle btraycastvehicle);

    public static final native float btRaycastVehicle_getCurrentSpeedKmHour(long j, btRaycastVehicle btraycastvehicle);

    public static final native int btRaycastVehicle_getForwardAxis(long j, btRaycastVehicle btraycastvehicle);

    public static final native Vector3 btRaycastVehicle_getForwardVector(long j, btRaycastVehicle btraycastvehicle);

    public static final native int btRaycastVehicle_getNumWheels(long j, btRaycastVehicle btraycastvehicle);

    public static final native int btRaycastVehicle_getRightAxis(long j, btRaycastVehicle btraycastvehicle);

    public static final native long btRaycastVehicle_getRigidBody(long j, btRaycastVehicle btraycastvehicle);

    public static final native long btRaycastVehicle_getRigidBodyConst(long j, btRaycastVehicle btraycastvehicle);

    public static final native float btRaycastVehicle_getSteeringValue(long j, btRaycastVehicle btraycastvehicle, int i);

    public static final native int btRaycastVehicle_getUpAxis(long j, btRaycastVehicle btraycastvehicle);

    public static final native int btRaycastVehicle_getUserConstraintId(long j, btRaycastVehicle btraycastvehicle);

    public static final native int btRaycastVehicle_getUserConstraintType(long j, btRaycastVehicle btraycastvehicle);

    public static final native long btRaycastVehicle_getWheelInfo(long j, btRaycastVehicle btraycastvehicle, int i);

    public static final native long btRaycastVehicle_getWheelInfoConst(long j, btRaycastVehicle btraycastvehicle, int i);

    public static final native Matrix4 btRaycastVehicle_getWheelTransformWS(long j, btRaycastVehicle btraycastvehicle, int i);

    public static final native float btRaycastVehicle_rayCast(long j, btRaycastVehicle btraycastvehicle, long j2, btWheelInfo btwheelinfo);

    public static final native void btRaycastVehicle_resetSuspension(long j, btRaycastVehicle btraycastvehicle);

    public static final native void btRaycastVehicle_setBrake(long j, btRaycastVehicle btraycastvehicle, float f, int i);

    public static final native void btRaycastVehicle_setCoordinateSystem(long j, btRaycastVehicle btraycastvehicle, int i, int i2, int i3);

    public static final native void btRaycastVehicle_setPitchControl(long j, btRaycastVehicle btraycastvehicle, float f);

    public static final native void btRaycastVehicle_setSteeringValue(long j, btRaycastVehicle btraycastvehicle, float f, int i);

    public static final native void btRaycastVehicle_setUserConstraintId(long j, btRaycastVehicle btraycastvehicle, int i);

    public static final native void btRaycastVehicle_setUserConstraintType(long j, btRaycastVehicle btraycastvehicle, int i);

    public static final native void btRaycastVehicle_updateFriction(long j, btRaycastVehicle btraycastvehicle, float f);

    public static final native void btRaycastVehicle_updateSuspension(long j, btRaycastVehicle btraycastvehicle, float f);

    public static final native void btRaycastVehicle_updateVehicle(long j, btRaycastVehicle btraycastvehicle, float f);

    public static final native void btRaycastVehicle_updateWheelTransform__SWIG_0(long j, btRaycastVehicle btraycastvehicle, int i, boolean z);

    public static final native void btRaycastVehicle_updateWheelTransform__SWIG_1(long j, btRaycastVehicle btraycastvehicle, int i);

    public static final native void btRaycastVehicle_updateWheelTransformsWS__SWIG_0(long j, btRaycastVehicle btraycastvehicle, long j2, btWheelInfo btwheelinfo, boolean z);

    public static final native void btRaycastVehicle_updateWheelTransformsWS__SWIG_1(long j, btRaycastVehicle btraycastvehicle, long j2, btWheelInfo btwheelinfo);

    public static final native long btRaycastVehicle_wheelInfo_get(long j, btRaycastVehicle btraycastvehicle);

    public static final native void btRaycastVehicle_wheelInfo_set(long j, btRaycastVehicle btraycastvehicle, long j2);

    public static final native double btRigidBodyDoubleData_additionalAngularDampingFactor_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_additionalAngularDampingFactor_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, double d);

    public static final native double btRigidBodyDoubleData_additionalAngularDampingThresholdSqr_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_additionalAngularDampingThresholdSqr_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, double d);

    public static final native double btRigidBodyDoubleData_additionalDampingFactor_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_additionalDampingFactor_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, double d);

    public static final native int btRigidBodyDoubleData_additionalDamping_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_additionalDamping_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, int i);

    public static final native double btRigidBodyDoubleData_additionalLinearDampingThresholdSqr_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_additionalLinearDampingThresholdSqr_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, double d);

    public static final native double btRigidBodyDoubleData_angularDamping_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_angularDamping_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, double d);

    public static final native long btRigidBodyDoubleData_angularFactor_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_angularFactor_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native double btRigidBodyDoubleData_angularSleepingThreshold_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_angularSleepingThreshold_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, double d);

    public static final native long btRigidBodyDoubleData_angularVelocity_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_angularVelocity_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btRigidBodyDoubleData_collisionObjectData_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_collisionObjectData_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native long btRigidBodyDoubleData_gravity_acceleration_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_gravity_acceleration_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btRigidBodyDoubleData_gravity_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_gravity_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btRigidBodyDoubleData_invInertiaLocal_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_invInertiaLocal_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btRigidBodyDoubleData_invInertiaTensorWorld_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_invInertiaTensorWorld_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btMatrix3x3DoubleData btmatrix3x3doubledata);

    public static final native double btRigidBodyDoubleData_inverseMass_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_inverseMass_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, double d);

    public static final native double btRigidBodyDoubleData_linearDamping_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_linearDamping_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, double d);

    public static final native long btRigidBodyDoubleData_linearFactor_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_linearFactor_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native double btRigidBodyDoubleData_linearSleepingThreshold_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_linearSleepingThreshold_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, double d);

    public static final native long btRigidBodyDoubleData_linearVelocity_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_linearVelocity_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native String btRigidBodyDoubleData_padding_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_padding_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, String str);

    public static final native long btRigidBodyDoubleData_totalForce_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_totalForce_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btRigidBodyDoubleData_totalTorque_get(long j, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native void btRigidBodyDoubleData_totalTorque_set(long j, btRigidBodyDoubleData btrigidbodydoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native float btRigidBodyFloatData_additionalAngularDampingFactor_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_additionalAngularDampingFactor_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, float f);

    public static final native float btRigidBodyFloatData_additionalAngularDampingThresholdSqr_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_additionalAngularDampingThresholdSqr_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, float f);

    public static final native float btRigidBodyFloatData_additionalDampingFactor_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_additionalDampingFactor_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, float f);

    public static final native int btRigidBodyFloatData_additionalDamping_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_additionalDamping_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, int i);

    public static final native float btRigidBodyFloatData_additionalLinearDampingThresholdSqr_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_additionalLinearDampingThresholdSqr_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, float f);

    public static final native float btRigidBodyFloatData_angularDamping_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_angularDamping_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, float f);

    public static final native long btRigidBodyFloatData_angularFactor_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_angularFactor_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native float btRigidBodyFloatData_angularSleepingThreshold_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_angularSleepingThreshold_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, float f);

    public static final native long btRigidBodyFloatData_angularVelocity_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_angularVelocity_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btRigidBodyFloatData_collisionObjectData_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_collisionObjectData_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native long btRigidBodyFloatData_gravity_acceleration_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_gravity_acceleration_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btRigidBodyFloatData_gravity_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_gravity_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btRigidBodyFloatData_invInertiaLocal_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_invInertiaLocal_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btRigidBodyFloatData_invInertiaTensorWorld_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_invInertiaTensorWorld_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native float btRigidBodyFloatData_inverseMass_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_inverseMass_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, float f);

    public static final native float btRigidBodyFloatData_linearDamping_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_linearDamping_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, float f);

    public static final native long btRigidBodyFloatData_linearFactor_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_linearFactor_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native float btRigidBodyFloatData_linearSleepingThreshold_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_linearSleepingThreshold_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, float f);

    public static final native long btRigidBodyFloatData_linearVelocity_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_linearVelocity_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btRigidBodyFloatData_totalForce_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_totalForce_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btRigidBodyFloatData_totalTorque_get(long j, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native void btRigidBodyFloatData_totalTorque_set(long j, btRigidBodyFloatData btrigidbodyfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btRigidBody_SWIGUpcast(long j);

    public static final native void btRigidBody_addConstraintRef(long j, btRigidBody btrigidbody, long j2, btTypedConstraint bttypedconstraint);

    public static final native void btRigidBody_applyCentralForce(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_applyCentralImpulse(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_applyDamping(long j, btRigidBody btrigidbody, float f);

    public static final native void btRigidBody_applyForce(long j, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4);

    public static final native void btRigidBody_applyGravity(long j, btRigidBody btrigidbody);

    public static final native void btRigidBody_applyImpulse(long j, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4);

    public static final native void btRigidBody_applyTorque(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_applyTorqueImpulse(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_additionalAngularDampingFactor_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_additionalAngularDampingFactor_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_additionalAngularDampingThresholdSqr_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_additionalAngularDampingThresholdSqr_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_additionalDampingFactor_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_additionalDampingFactor_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native boolean btRigidBody_btRigidBodyConstructionInfo_additionalDamping_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_additionalDamping_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, boolean z);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_additionalLinearDampingThresholdSqr_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_additionalLinearDampingThresholdSqr_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_angularDamping_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_angularDamping_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_angularSleepingThreshold_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_angularSleepingThreshold_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_friction_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_friction_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native long btRigidBody_btRigidBodyConstructionInfo_i_collisionShape_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_i_collisionShape_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, long j2, btCollisionShape btcollisionshape);

    public static final native long btRigidBody_btRigidBodyConstructionInfo_i_motionState_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_i_motionState_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, long j2, btMotionState btmotionstate);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_linearDamping_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_linearDamping_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_linearSleepingThreshold_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_linearSleepingThreshold_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native long btRigidBody_btRigidBodyConstructionInfo_localInertia_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_localInertia_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, long j2, btVector3 btvector3);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_mass_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_mass_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_restitution_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_restitution_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_rollingFriction_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_rollingFriction_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native float btRigidBody_btRigidBodyConstructionInfo_spinningFriction_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_spinningFriction_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, float f);

    public static final native long btRigidBody_btRigidBodyConstructionInfo_startWorldTransform_get(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native void btRigidBody_btRigidBodyConstructionInfo_startWorldTransform_set(long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo, long j2, btTransform bttransform);

    public static final native void btRigidBody_clearForces(long j, btRigidBody btrigidbody);

    public static final native float btRigidBody_computeAngularImpulseDenominator(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native Vector3 btRigidBody_computeGyroscopicForceExplicit(long j, btRigidBody btrigidbody, float f);

    public static final native Vector3 btRigidBody_computeGyroscopicImpulseImplicit_Body(long j, btRigidBody btrigidbody, float f);

    public static final native Vector3 btRigidBody_computeGyroscopicImpulseImplicit_World(long j, btRigidBody btrigidbody, float f);

    public static final native float btRigidBody_computeImpulseDenominator(long j, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4);

    public static final native int btRigidBody_contactSolverType_get(long j, btRigidBody btrigidbody);

    public static final native void btRigidBody_contactSolverType_set(long j, btRigidBody btrigidbody, int i);

    public static final native int btRigidBody_frictionSolverType_get(long j, btRigidBody btrigidbody);

    public static final native void btRigidBody_frictionSolverType_set(long j, btRigidBody btrigidbody, int i);

    public static final native void btRigidBody_getAabb(long j, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4);

    public static final native float btRigidBody_getAngularDamping(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getAngularFactor(long j, btRigidBody btrigidbody);

    public static final native float btRigidBody_getAngularSleepingThreshold(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getAngularVelocity(long j, btRigidBody btrigidbody);

    public static final native long btRigidBody_getBroadphaseProxy(long j, btRigidBody btrigidbody);

    public static final native long btRigidBody_getBroadphaseProxyConst(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getCenterOfMassPosition(long j, btRigidBody btrigidbody);

    public static final native Matrix4 btRigidBody_getCenterOfMassTransform(long j, btRigidBody btrigidbody);

    public static final native long btRigidBody_getConstraintRef(long j, btRigidBody btrigidbody, int i);

    public static final native int btRigidBody_getFlags(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getGravity(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getInvInertiaDiagLocal(long j, btRigidBody btrigidbody);

    public static final native Matrix3 btRigidBody_getInvInertiaTensorWorld(long j, btRigidBody btrigidbody);

    public static final native float btRigidBody_getInvMass(long j, btRigidBody btrigidbody);

    public static final native float btRigidBody_getLinearDamping(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getLinearFactor(long j, btRigidBody btrigidbody);

    public static final native float btRigidBody_getLinearSleepingThreshold(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getLinearVelocity(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getLocalInertia(long j, btRigidBody btrigidbody);

    public static final native long btRigidBody_getMotionStateConst(long j, btRigidBody btrigidbody);

    public static final native int btRigidBody_getNumConstraintRefs(long j, btRigidBody btrigidbody);

    public static final native Quaternion btRigidBody_getOrientation(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getTotalForce(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getTotalTorque(long j, btRigidBody btrigidbody);

    public static final native Vector3 btRigidBody_getVelocityInLocalPoint(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_integrateVelocities(long j, btRigidBody btrigidbody, float f);

    public static final native long btRigidBody_internalGetMotionState(long j, btRigidBody btrigidbody);

    public static final native void btRigidBody_internalSetMotionState(long j, btRigidBody btrigidbody, long j2, btMotionState btmotionstate);

    public static final native boolean btRigidBody_isInWorld(long j, btRigidBody btrigidbody);

    public static final native void btRigidBody_predictIntegratedTransform(long j, btRigidBody btrigidbody, float f, Matrix4 matrix4);

    public static final native void btRigidBody_proceedToTransform(long j, btRigidBody btrigidbody, Matrix4 matrix4);

    public static final native void btRigidBody_removeConstraintRef(long j, btRigidBody btrigidbody, long j2, btTypedConstraint bttypedconstraint);

    public static final native void btRigidBody_saveKinematicState(long j, btRigidBody btrigidbody, float f);

    public static final native void btRigidBody_setAngularFactor__SWIG_0(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_setAngularFactor__SWIG_1(long j, btRigidBody btrigidbody, float f);

    public static final native void btRigidBody_setAngularVelocity(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_setCenterOfMassTransform(long j, btRigidBody btrigidbody, Matrix4 matrix4);

    public static final native void btRigidBody_setDamping(long j, btRigidBody btrigidbody, float f, float f2);

    public static final native void btRigidBody_setFlags(long j, btRigidBody btrigidbody, int i);

    public static final native void btRigidBody_setGravity(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_setInvInertiaDiagLocal(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_setLinearFactor(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_setLinearVelocity(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_setMassProps(long j, btRigidBody btrigidbody, float f, Vector3 vector3);

    public static final native void btRigidBody_setNewBroadphaseProxy(long j, btRigidBody btrigidbody, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btRigidBody_setSleepingThresholds(long j, btRigidBody btrigidbody, float f, float f2);

    public static final native void btRigidBody_translate(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btRigidBody_updateDeactivation(long j, btRigidBody btrigidbody, float f);

    public static final native void btRigidBody_updateInertiaTensor(long j, btRigidBody btrigidbody);

    public static final native boolean btRigidBody_wantsSleeping(long j, btRigidBody btrigidbody);

    public static final native float btRotationalLimitMotor2_bounce_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_bounce_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor2_currentLimitErrorHi_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_currentLimitErrorHi_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor2_currentLimitError_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_currentLimitError_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native int btRotationalLimitMotor2_currentLimit_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_currentLimit_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, int i);

    public static final native float btRotationalLimitMotor2_currentPosition_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_currentPosition_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native boolean btRotationalLimitMotor2_enableMotor_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_enableMotor_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, boolean z);

    public static final native boolean btRotationalLimitMotor2_enableSpring_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_enableSpring_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, boolean z);

    public static final native float btRotationalLimitMotor2_equilibriumPoint_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_equilibriumPoint_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor2_hiLimit_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_hiLimit_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native boolean btRotationalLimitMotor2_isLimited(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native float btRotationalLimitMotor2_loLimit_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_loLimit_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor2_maxMotorForce_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_maxMotorForce_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor2_motorCFM_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_motorCFM_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor2_motorERP_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_motorERP_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native boolean btRotationalLimitMotor2_servoMotor_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_servoMotor_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, boolean z);

    public static final native float btRotationalLimitMotor2_servoTarget_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_servoTarget_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native boolean btRotationalLimitMotor2_springDampingLimited_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_springDampingLimited_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, boolean z);

    public static final native float btRotationalLimitMotor2_springDamping_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_springDamping_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native boolean btRotationalLimitMotor2_springStiffnessLimited_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_springStiffnessLimited_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, boolean z);

    public static final native float btRotationalLimitMotor2_springStiffness_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_springStiffness_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor2_stopCFM_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_stopCFM_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor2_stopERP_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_stopERP_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor2_targetVelocity_get(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native void btRotationalLimitMotor2_targetVelocity_set(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native void btRotationalLimitMotor2_testLimitValue(long j, btRotationalLimitMotor2 btrotationallimitmotor2, float f);

    public static final native float btRotationalLimitMotor_accumulatedImpulse_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_accumulatedImpulse_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native float btRotationalLimitMotor_bounce_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_bounce_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native float btRotationalLimitMotor_currentLimitError_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_currentLimitError_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native int btRotationalLimitMotor_currentLimit_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_currentLimit_set(long j, btRotationalLimitMotor btrotationallimitmotor, int i);

    public static final native float btRotationalLimitMotor_currentPosition_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_currentPosition_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native float btRotationalLimitMotor_damping_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_damping_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native boolean btRotationalLimitMotor_enableMotor_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_enableMotor_set(long j, btRotationalLimitMotor btrotationallimitmotor, boolean z);

    public static final native float btRotationalLimitMotor_hiLimit_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_hiLimit_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native boolean btRotationalLimitMotor_isLimited(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native float btRotationalLimitMotor_limitSoftness_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_limitSoftness_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native float btRotationalLimitMotor_loLimit_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_loLimit_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native float btRotationalLimitMotor_maxLimitForce_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_maxLimitForce_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native float btRotationalLimitMotor_maxMotorForce_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_maxMotorForce_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native boolean btRotationalLimitMotor_needApplyTorques(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native float btRotationalLimitMotor_normalCFM_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_normalCFM_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native float btRotationalLimitMotor_solveAngularLimits(long j, btRotationalLimitMotor btrotationallimitmotor, float f, Vector3 vector3, float f2, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2);

    public static final native float btRotationalLimitMotor_stopCFM_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_stopCFM_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native float btRotationalLimitMotor_stopERP_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_stopERP_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native float btRotationalLimitMotor_targetVelocity_get(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native void btRotationalLimitMotor_targetVelocity_set(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native int btRotationalLimitMotor_testLimitValue(long j, btRotationalLimitMotor btrotationallimitmotor, float f);

    public static final native long btSequentialImpulseConstraintSolver_SWIGUpcast(long j);

    public static final native long btSequentialImpulseConstraintSolver_btRand2(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver);

    public static final native int btSequentialImpulseConstraintSolver_btRandInt2(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, int i);

    public static final native long btSequentialImpulseConstraintSolver_getActiveConstraintRowSolverGeneric(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver);

    public static final native long btSequentialImpulseConstraintSolver_getActiveConstraintRowSolverLowerLimit(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver);

    public static final native long btSequentialImpulseConstraintSolver_getRandSeed(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver);

    public static final native long btSequentialImpulseConstraintSolver_getScalarConstraintRowSolverGeneric(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver);

    public static final native long btSequentialImpulseConstraintSolver_getScalarConstraintRowSolverLowerLimit(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver);

    public static final native void btSequentialImpulseConstraintSolver_operatorDeleteArray__SWIG_0(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2);

    public static final native void btSequentialImpulseConstraintSolver_operatorDeleteArray__SWIG_1(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2, long j3);

    public static final native void btSequentialImpulseConstraintSolver_operatorDelete__SWIG_0(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2);

    public static final native void btSequentialImpulseConstraintSolver_operatorDelete__SWIG_1(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2, long j3);

    public static final native long btSequentialImpulseConstraintSolver_operatorNewArray__SWIG_0(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2);

    public static final native long btSequentialImpulseConstraintSolver_operatorNewArray__SWIG_1(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2, long j3);

    public static final native long btSequentialImpulseConstraintSolver_operatorNew__SWIG_0(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2);

    public static final native long btSequentialImpulseConstraintSolver_operatorNew__SWIG_1(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2, long j3);

    public static final native void btSequentialImpulseConstraintSolver_setConstraintRowSolverGeneric(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2);

    public static final native void btSequentialImpulseConstraintSolver_setConstraintRowSolverLowerLimit(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2);

    public static final native void btSequentialImpulseConstraintSolver_setRandSeed(long j, btSequentialImpulseConstraintSolver btsequentialimpulseconstraintsolver, long j2);

    public static final native long btSimpleDynamicsWorld_SWIGUpcast(long j);

    public static final native void btSimpleDynamicsWorld_addRigidBody__SWIG_0(long j, btSimpleDynamicsWorld btsimpledynamicsworld, long j2, btRigidBody btrigidbody);

    public static final native void btSimpleDynamicsWorld_addRigidBody__SWIG_1(long j, btSimpleDynamicsWorld btsimpledynamicsworld, long j2, btRigidBody btrigidbody, int i, int i2);

    public static final native int btSimpleDynamicsWorld_stepSimulation__SWIG_0(long j, btSimpleDynamicsWorld btsimpledynamicsworld, float f, int i, float f2);

    public static final native int btSimpleDynamicsWorld_stepSimulation__SWIG_1(long j, btSimpleDynamicsWorld btsimpledynamicsworld, float f, int i);

    public static final native int btSimpleDynamicsWorld_stepSimulation__SWIG_2(long j, btSimpleDynamicsWorld btsimpledynamicsworld, float f);

    public static final native void btSimulationIslandManagerMt_IslandCallback_processIsland(long j, btSimulationIslandManagerMt.IslandCallback islandCallback, long j2, int i, long j3, int i2, long j4, int i3, int i4);

    public static final native void btSimulationIslandManagerMt_Island_append(long j, btSimulationIslandManagerMt.Island island, long j2, btSimulationIslandManagerMt.Island island2);

    public static final native long btSimulationIslandManagerMt_Island_bodyArray_get(long j, btSimulationIslandManagerMt.Island island);

    public static final native void btSimulationIslandManagerMt_Island_bodyArray_set(long j, btSimulationIslandManagerMt.Island island, long j2, btCollisionObjectArray btcollisionobjectarray);

    public static final native long btSimulationIslandManagerMt_Island_constraintArray_get(long j, btSimulationIslandManagerMt.Island island);

    public static final native void btSimulationIslandManagerMt_Island_constraintArray_set(long j, btSimulationIslandManagerMt.Island island, long j2);

    public static final native int btSimulationIslandManagerMt_Island_id_get(long j, btSimulationIslandManagerMt.Island island);

    public static final native void btSimulationIslandManagerMt_Island_id_set(long j, btSimulationIslandManagerMt.Island island, int i);

    public static final native boolean btSimulationIslandManagerMt_Island_isSleeping_get(long j, btSimulationIslandManagerMt.Island island);

    public static final native void btSimulationIslandManagerMt_Island_isSleeping_set(long j, btSimulationIslandManagerMt.Island island, boolean z);

    public static final native long btSimulationIslandManagerMt_Island_manifoldArray_get(long j, btSimulationIslandManagerMt.Island island);

    public static final native void btSimulationIslandManagerMt_Island_manifoldArray_set(long j, btSimulationIslandManagerMt.Island island, long j2, btPersistentManifoldArray btpersistentmanifoldarray);

    public static final native long btSimulationIslandManagerMt_SWIGUpcast(long j);

    public static final native void btSimulationIslandManagerMt_buildAndProcessIslands(long j, btSimulationIslandManagerMt btsimulationislandmanagermt, long j2, btDispatcher btdispatcher, long j3, btCollisionWorld btcollisionworld, long j4, long j5, btSimulationIslandManagerMt.IslandCallback islandCallback);

    public static final native void btSimulationIslandManagerMt_buildIslands(long j, btSimulationIslandManagerMt btsimulationislandmanagermt, long j2, btDispatcher btdispatcher, long j3, btCollisionWorld btcollisionworld);

    public static final native long btSimulationIslandManagerMt_getIslandDispatchFunction(long j, btSimulationIslandManagerMt btsimulationislandmanagermt);

    public static final native int btSimulationIslandManagerMt_getMinimumSolverBatchSize(long j, btSimulationIslandManagerMt btsimulationislandmanagermt);

    public static final native void btSimulationIslandManagerMt_parallelIslandDispatch(long j, long j2, btSimulationIslandManagerMt.IslandCallback islandCallback);

    public static final native void btSimulationIslandManagerMt_serialIslandDispatch(long j, long j2, btSimulationIslandManagerMt.IslandCallback islandCallback);

    public static final native void btSimulationIslandManagerMt_setIslandDispatchFunction(long j, btSimulationIslandManagerMt btsimulationislandmanagermt, long j2);

    public static final native void btSimulationIslandManagerMt_setMinimumSolverBatchSize(long j, btSimulationIslandManagerMt btsimulationislandmanagermt, int i);

    public static final native float btSliderConstraintData_angularLowerLimit_get(long j, btSliderConstraintData btsliderconstraintdata);

    public static final native void btSliderConstraintData_angularLowerLimit_set(long j, btSliderConstraintData btsliderconstraintdata, float f);

    public static final native float btSliderConstraintData_angularUpperLimit_get(long j, btSliderConstraintData btsliderconstraintdata);

    public static final native void btSliderConstraintData_angularUpperLimit_set(long j, btSliderConstraintData btsliderconstraintdata, float f);

    public static final native float btSliderConstraintData_linearLowerLimit_get(long j, btSliderConstraintData btsliderconstraintdata);

    public static final native void btSliderConstraintData_linearLowerLimit_set(long j, btSliderConstraintData btsliderconstraintdata, float f);

    public static final native float btSliderConstraintData_linearUpperLimit_get(long j, btSliderConstraintData btsliderconstraintdata);

    public static final native void btSliderConstraintData_linearUpperLimit_set(long j, btSliderConstraintData btsliderconstraintdata, float f);

    public static final native long btSliderConstraintData_rbAFrame_get(long j, btSliderConstraintData btsliderconstraintdata);

    public static final native void btSliderConstraintData_rbAFrame_set(long j, btSliderConstraintData btsliderconstraintdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native long btSliderConstraintData_rbBFrame_get(long j, btSliderConstraintData btsliderconstraintdata);

    public static final native void btSliderConstraintData_rbBFrame_set(long j, btSliderConstraintData btsliderconstraintdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native long btSliderConstraintData_typeConstraintData_get(long j, btSliderConstraintData btsliderconstraintdata);

    public static final native void btSliderConstraintData_typeConstraintData_set(long j, btSliderConstraintData btsliderconstraintdata, long j2, btTypedConstraintData bttypedconstraintdata);

    public static final native int btSliderConstraintData_useLinearReferenceFrameA_get(long j, btSliderConstraintData btsliderconstraintdata);

    public static final native void btSliderConstraintData_useLinearReferenceFrameA_set(long j, btSliderConstraintData btsliderconstraintdata, int i);

    public static final native int btSliderConstraintData_useOffsetForConstraintFrame_get(long j, btSliderConstraintData btsliderconstraintdata);

    public static final native void btSliderConstraintData_useOffsetForConstraintFrame_set(long j, btSliderConstraintData btsliderconstraintdata, int i);

    public static final native double btSliderConstraintDoubleData_angularLowerLimit_get(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata);

    public static final native void btSliderConstraintDoubleData_angularLowerLimit_set(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata, double d);

    public static final native double btSliderConstraintDoubleData_angularUpperLimit_get(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata);

    public static final native void btSliderConstraintDoubleData_angularUpperLimit_set(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata, double d);

    public static final native double btSliderConstraintDoubleData_linearLowerLimit_get(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata);

    public static final native void btSliderConstraintDoubleData_linearLowerLimit_set(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata, double d);

    public static final native double btSliderConstraintDoubleData_linearUpperLimit_get(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata);

    public static final native void btSliderConstraintDoubleData_linearUpperLimit_set(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata, double d);

    public static final native long btSliderConstraintDoubleData_rbAFrame_get(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata);

    public static final native void btSliderConstraintDoubleData_rbAFrame_set(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native long btSliderConstraintDoubleData_rbBFrame_get(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata);

    public static final native void btSliderConstraintDoubleData_rbBFrame_set(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native long btSliderConstraintDoubleData_typeConstraintData_get(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata);

    public static final native void btSliderConstraintDoubleData_typeConstraintData_set(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata, long j2, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native int btSliderConstraintDoubleData_useLinearReferenceFrameA_get(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata);

    public static final native void btSliderConstraintDoubleData_useLinearReferenceFrameA_set(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata, int i);

    public static final native int btSliderConstraintDoubleData_useOffsetForConstraintFrame_get(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata);

    public static final native void btSliderConstraintDoubleData_useOffsetForConstraintFrame_set(long j, btSliderConstraintDoubleData btsliderconstraintdoubledata, int i);

    public static final native long btSliderConstraint_SWIGUpcast(long j);

    public static final native void btSliderConstraint_calculateTransforms(long j, btSliderConstraint btsliderconstraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native Vector3 btSliderConstraint_getAncorInA(long j, btSliderConstraint btsliderconstraint);

    public static final native Vector3 btSliderConstraint_getAncorInB(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getAngDepth(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getAngularPos(long j, btSliderConstraint btsliderconstraint);

    public static final native Matrix4 btSliderConstraint_getCalculatedTransformA(long j, btSliderConstraint btsliderconstraint);

    public static final native Matrix4 btSliderConstraint_getCalculatedTransformB(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getDampingDirAng(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getDampingDirLin(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getDampingLimAng(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getDampingLimLin(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getDampingOrthoAng(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getDampingOrthoLin(long j, btSliderConstraint btsliderconstraint);

    public static final native int btSliderConstraint_getFlags(long j, btSliderConstraint btsliderconstraint);

    public static final native Matrix4 btSliderConstraint_getFrameOffsetA(long j, btSliderConstraint btsliderconstraint);

    public static final native Matrix4 btSliderConstraint_getFrameOffsetAConst(long j, btSliderConstraint btsliderconstraint);

    public static final native Matrix4 btSliderConstraint_getFrameOffsetB(long j, btSliderConstraint btsliderconstraint);

    public static final native Matrix4 btSliderConstraint_getFrameOffsetBConst(long j, btSliderConstraint btsliderconstraint);

    public static final native void btSliderConstraint_getInfo1NonVirtual(long j, btSliderConstraint btsliderconstraint, long j2, btTypedConstraint.btConstraintInfo1 btconstraintinfo1);

    public static final native void btSliderConstraint_getInfo2NonVirtual(long j, btSliderConstraint btsliderconstraint, long j2, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, float f, float f2);

    public static final native float btSliderConstraint_getLinDepth(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getLinearPos(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getLowerAngLimit(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getLowerLinLimit(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getMaxAngMotorForce(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getMaxLinMotorForce(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getParam__SWIG_0(long j, btSliderConstraint btsliderconstraint, int i, int i2);

    public static final native float btSliderConstraint_getParam__SWIG_1(long j, btSliderConstraint btsliderconstraint, int i);

    public static final native boolean btSliderConstraint_getPoweredAngMotor(long j, btSliderConstraint btsliderconstraint);

    public static final native boolean btSliderConstraint_getPoweredLinMotor(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getRestitutionDirAng(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getRestitutionDirLin(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getRestitutionLimAng(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getRestitutionLimLin(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getRestitutionOrthoAng(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getRestitutionOrthoLin(long j, btSliderConstraint btsliderconstraint);

    public static final native long btSliderConstraint_getRigidBodyAConst(long j, btSliderConstraint btsliderconstraint);

    public static final native long btSliderConstraint_getRigidBodyBConst(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getSoftnessDirAng(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getSoftnessDirLin(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getSoftnessLimAng(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getSoftnessLimLin(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getSoftnessOrthoAng(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getSoftnessOrthoLin(long j, btSliderConstraint btsliderconstraint);

    public static final native boolean btSliderConstraint_getSolveAngLimit(long j, btSliderConstraint btsliderconstraint);

    public static final native boolean btSliderConstraint_getSolveLinLimit(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getTargetAngMotorVelocity(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getTargetLinMotorVelocity(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getUpperAngLimit(long j, btSliderConstraint btsliderconstraint);

    public static final native float btSliderConstraint_getUpperLinLimit(long j, btSliderConstraint btsliderconstraint);

    public static final native boolean btSliderConstraint_getUseFrameOffset(long j, btSliderConstraint btsliderconstraint);

    public static final native boolean btSliderConstraint_getUseLinearReferenceFrameA(long j, btSliderConstraint btsliderconstraint);

    public static final native void btSliderConstraint_operatorDeleteArray__SWIG_0(long j, btSliderConstraint btsliderconstraint, long j2);

    public static final native void btSliderConstraint_operatorDeleteArray__SWIG_1(long j, btSliderConstraint btsliderconstraint, long j2, long j3);

    public static final native void btSliderConstraint_operatorDelete__SWIG_0(long j, btSliderConstraint btsliderconstraint, long j2);

    public static final native void btSliderConstraint_operatorDelete__SWIG_1(long j, btSliderConstraint btsliderconstraint, long j2, long j3);

    public static final native long btSliderConstraint_operatorNewArray__SWIG_0(long j, btSliderConstraint btsliderconstraint, long j2);

    public static final native long btSliderConstraint_operatorNewArray__SWIG_1(long j, btSliderConstraint btsliderconstraint, long j2, long j3);

    public static final native long btSliderConstraint_operatorNew__SWIG_0(long j, btSliderConstraint btsliderconstraint, long j2);

    public static final native long btSliderConstraint_operatorNew__SWIG_1(long j, btSliderConstraint btsliderconstraint, long j2, long j3);

    public static final native void btSliderConstraint_setDampingDirAng(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setDampingDirLin(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setDampingLimAng(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setDampingLimLin(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setDampingOrthoAng(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setDampingOrthoLin(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setFrames(long j, btSliderConstraint btsliderconstraint, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btSliderConstraint_setLowerAngLimit(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setLowerLinLimit(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setMaxAngMotorForce(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setMaxLinMotorForce(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setParam__SWIG_0(long j, btSliderConstraint btsliderconstraint, int i, float f, int i2);

    public static final native void btSliderConstraint_setParam__SWIG_1(long j, btSliderConstraint btsliderconstraint, int i, float f);

    public static final native void btSliderConstraint_setPoweredAngMotor(long j, btSliderConstraint btsliderconstraint, boolean z);

    public static final native void btSliderConstraint_setPoweredLinMotor(long j, btSliderConstraint btsliderconstraint, boolean z);

    public static final native void btSliderConstraint_setRestitutionDirAng(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setRestitutionDirLin(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setRestitutionLimAng(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setRestitutionLimLin(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setRestitutionOrthoAng(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setRestitutionOrthoLin(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setSoftnessDirAng(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setSoftnessDirLin(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setSoftnessLimAng(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setSoftnessLimLin(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setSoftnessOrthoAng(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setSoftnessOrthoLin(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setTargetAngMotorVelocity(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setTargetLinMotorVelocity(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setUpperAngLimit(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setUpperLinLimit(long j, btSliderConstraint btsliderconstraint, float f);

    public static final native void btSliderConstraint_setUseFrameOffset(long j, btSliderConstraint btsliderconstraint, boolean z);

    public static final native void btSliderConstraint_testAngLimits(long j, btSliderConstraint btsliderconstraint);

    public static final native void btSliderConstraint_testLinLimits(long j, btSliderConstraint btsliderconstraint);

    public static final native void btSolve2LinearConstraint_resolveBilateralPairConstraint(long j, btSolve2LinearConstraint btsolve2linearconstraint, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Matrix3 matrix3, Matrix3 matrix4, Vector3 vector3, float f, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, float f2, Vector3 vector8, Vector3 vector9, Vector3 vector10, float f3, Vector3 vector11, Vector3 vector12, Vector3 vector13, float f4, Vector3 vector14, long j4, long j5);

    public static final native void btSolve2LinearConstraint_resolveUnilateralPairConstraint(long j, btSolve2LinearConstraint btsolve2linearconstraint, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Matrix3 matrix3, Matrix3 matrix4, Vector3 vector3, float f, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, float f2, Vector3 vector8, Vector3 vector9, Vector3 vector10, float f3, Vector3 vector11, Vector3 vector12, Vector3 vector13, float f4, Vector3 vector14, long j4, long j5);

    public static final native boolean btSolveDantzigLCP(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, FloatBuffer floatBuffer3, FloatBuffer floatBuffer4, int i2, FloatBuffer floatBuffer5, FloatBuffer floatBuffer6, IntBuffer intBuffer, long j, btDantzigScratchMemory btdantzigscratchmemory);

    public static final native long btSolveProjectedGaussSeidel_SWIGUpcast(long j);

    public static final native float btSolveProjectedGaussSeidel_leastSquaresResidualThreshold_get(long j, btSolveProjectedGaussSeidel btsolveprojectedgaussseidel);

    public static final native void btSolveProjectedGaussSeidel_leastSquaresResidualThreshold_set(long j, btSolveProjectedGaussSeidel btsolveprojectedgaussseidel, float f);

    public static final native float btSolveProjectedGaussSeidel_leastSquaresResidual_get(long j, btSolveProjectedGaussSeidel btsolveprojectedgaussseidel);

    public static final native void btSolveProjectedGaussSeidel_leastSquaresResidual_set(long j, btSolveProjectedGaussSeidel btsolveprojectedgaussseidel, float f);

    public static final native boolean btSolveProjectedGaussSeidel_solveMLCP__SWIG_0(long j, btSolveProjectedGaussSeidel btsolveprojectedgaussseidel, long j2, long j3, long j4, long j5, long j6, long j7, int i, boolean z);

    public static final native boolean btSolveProjectedGaussSeidel_solveMLCP__SWIG_1(long j, btSolveProjectedGaussSeidel btsolveprojectedgaussseidel, long j2, long j3, long j4, long j5, long j6, long j7, int i);

    public static final native long btSolverBody_angularFactor_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_angularFactor_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native long btSolverBody_angularVelocity_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_angularVelocity_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native void btSolverBody_applyImpulse(long j, btSolverBody btsolverbody, Vector3 vector3, Vector3 vector4, float f);

    public static final native long btSolverBody_deltaAngularVelocity_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_deltaAngularVelocity_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native long btSolverBody_deltaLinearVelocity_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_deltaLinearVelocity_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native long btSolverBody_externalForceImpulse_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_externalForceImpulse_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native long btSolverBody_externalTorqueImpulse_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_externalTorqueImpulse_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native void btSolverBody_getAngularVelocity(long j, btSolverBody btsolverbody, Vector3 vector3);

    public static final native void btSolverBody_getVelocityInLocalPointNoDelta(long j, btSolverBody btsolverbody, Vector3 vector3, Vector3 vector4);

    public static final native void btSolverBody_getVelocityInLocalPointObsolete(long j, btSolverBody btsolverbody, Vector3 vector3, Vector3 vector4);

    public static final native void btSolverBody_internalApplyImpulse(long j, btSolverBody btsolverbody, Vector3 vector3, Vector3 vector4, float f);

    public static final native void btSolverBody_internalApplyPushImpulse(long j, btSolverBody btsolverbody, Vector3 vector3, Vector3 vector4, float f);

    public static final native Vector3 btSolverBody_internalGetAngularFactor(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_internalGetAngularVelocity(long j, btSolverBody btsolverbody, Vector3 vector3);

    public static final native Vector3 btSolverBody_internalGetDeltaAngularVelocity(long j, btSolverBody btsolverbody);

    public static final native Vector3 btSolverBody_internalGetDeltaLinearVelocity(long j, btSolverBody btsolverbody);

    public static final native Vector3 btSolverBody_internalGetInvMass(long j, btSolverBody btsolverbody);

    public static final native Vector3 btSolverBody_internalGetPushVelocity(long j, btSolverBody btsolverbody);

    public static final native Vector3 btSolverBody_internalGetTurnVelocity(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_internalGetVelocityInLocalPointObsolete(long j, btSolverBody btsolverbody, Vector3 vector3, Vector3 vector4);

    public static final native void btSolverBody_internalSetInvMass(long j, btSolverBody btsolverbody, Vector3 vector3);

    public static final native long btSolverBody_invMass_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_invMass_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native long btSolverBody_linearFactor_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_linearFactor_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native long btSolverBody_linearVelocity_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_linearVelocity_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native void btSolverBody_operatorDeleteArray__SWIG_0(long j, btSolverBody btsolverbody, long j2);

    public static final native void btSolverBody_operatorDeleteArray__SWIG_1(long j, btSolverBody btsolverbody, long j2, long j3);

    public static final native void btSolverBody_operatorDelete__SWIG_0(long j, btSolverBody btsolverbody, long j2);

    public static final native void btSolverBody_operatorDelete__SWIG_1(long j, btSolverBody btsolverbody, long j2, long j3);

    public static final native long btSolverBody_operatorNewArray__SWIG_0(long j, btSolverBody btsolverbody, long j2);

    public static final native long btSolverBody_operatorNewArray__SWIG_1(long j, btSolverBody btsolverbody, long j2, long j3);

    public static final native long btSolverBody_operatorNew__SWIG_0(long j, btSolverBody btsolverbody, long j2);

    public static final native long btSolverBody_operatorNew__SWIG_1(long j, btSolverBody btsolverbody, long j2, long j3);

    public static final native long btSolverBody_originalBody_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_originalBody_set(long j, btSolverBody btsolverbody, long j2, btRigidBody btrigidbody);

    public static final native long btSolverBody_pushVelocity_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_pushVelocity_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native long btSolverBody_turnVelocity_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_turnVelocity_set(long j, btSolverBody btsolverbody, long j2, btVector3 btvector3);

    public static final native long btSolverBody_worldTransform_get(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_worldTransform_set(long j, btSolverBody btsolverbody, long j2, btTransform bttransform);

    public static final native void btSolverBody_writebackVelocity(long j, btSolverBody btsolverbody);

    public static final native void btSolverBody_writebackVelocityAndTransform(long j, btSolverBody btsolverbody, float f, float f2);

    public static final native long btSolverConstraint_angularComponentA_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_angularComponentA_set(long j, btSolverConstraint btsolverconstraint, long j2, btVector3 btvector3);

    public static final native long btSolverConstraint_angularComponentB_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_angularComponentB_set(long j, btSolverConstraint btsolverconstraint, long j2, btVector3 btvector3);

    public static final native float btSolverConstraint_appliedImpulse_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_appliedImpulse_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native float btSolverConstraint_appliedPushImpulse_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_appliedPushImpulse_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native float btSolverConstraint_cfm_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_cfm_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native long btSolverConstraint_contactNormal1_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_contactNormal1_set(long j, btSolverConstraint btsolverconstraint, long j2, btVector3 btvector3);

    public static final native long btSolverConstraint_contactNormal2_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_contactNormal2_set(long j, btSolverConstraint btsolverconstraint, long j2, btVector3 btvector3);

    public static final native int btSolverConstraint_frictionIndex_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_frictionIndex_set(long j, btSolverConstraint btsolverconstraint, int i);

    public static final native float btSolverConstraint_friction_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_friction_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native float btSolverConstraint_jacDiagABInv_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_jacDiagABInv_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native float btSolverConstraint_lowerLimit_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_lowerLimit_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native int btSolverConstraint_numRowsForNonContactConstraint_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_numRowsForNonContactConstraint_set(long j, btSolverConstraint btsolverconstraint, int i);

    public static final native void btSolverConstraint_operatorDeleteArray__SWIG_0(long j, btSolverConstraint btsolverconstraint, long j2);

    public static final native void btSolverConstraint_operatorDeleteArray__SWIG_1(long j, btSolverConstraint btsolverconstraint, long j2, long j3);

    public static final native void btSolverConstraint_operatorDelete__SWIG_0(long j, btSolverConstraint btsolverconstraint, long j2);

    public static final native void btSolverConstraint_operatorDelete__SWIG_1(long j, btSolverConstraint btsolverconstraint, long j2, long j3);

    public static final native long btSolverConstraint_operatorNewArray__SWIG_0(long j, btSolverConstraint btsolverconstraint, long j2);

    public static final native long btSolverConstraint_operatorNewArray__SWIG_1(long j, btSolverConstraint btsolverconstraint, long j2, long j3);

    public static final native long btSolverConstraint_operatorNew__SWIG_0(long j, btSolverConstraint btsolverconstraint, long j2);

    public static final native long btSolverConstraint_operatorNew__SWIG_1(long j, btSolverConstraint btsolverconstraint, long j2, long j3);

    public static final native long btSolverConstraint_originalContactPoint_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_originalContactPoint_set(long j, btSolverConstraint btsolverconstraint, long j2);

    public static final native int btSolverConstraint_overrideNumSolverIterations_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_overrideNumSolverIterations_set(long j, btSolverConstraint btsolverconstraint, int i);

    public static final native long btSolverConstraint_relpos1CrossNormal_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_relpos1CrossNormal_set(long j, btSolverConstraint btsolverconstraint, long j2, btVector3 btvector3);

    public static final native long btSolverConstraint_relpos2CrossNormal_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_relpos2CrossNormal_set(long j, btSolverConstraint btsolverconstraint, long j2, btVector3 btvector3);

    public static final native float btSolverConstraint_rhsPenetration_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_rhsPenetration_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native float btSolverConstraint_rhs_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_rhs_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native int btSolverConstraint_solverBodyIdA_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_solverBodyIdA_set(long j, btSolverConstraint btsolverconstraint, int i);

    public static final native int btSolverConstraint_solverBodyIdB_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_solverBodyIdB_set(long j, btSolverConstraint btsolverconstraint, int i);

    public static final native float btSolverConstraint_unusedPadding4_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_unusedPadding4_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native float btSolverConstraint_upperLimit_get(long j, btSolverConstraint btsolverconstraint);

    public static final native void btSolverConstraint_upperLimit_set(long j, btSolverConstraint btsolverconstraint, float f);

    public static final native long btTranslationalLimitMotor2_bounce_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_bounce_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor2_currentLimitErrorHi_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_currentLimitErrorHi_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor2_currentLimitError_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_currentLimitError_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native int[] btTranslationalLimitMotor2_currentLimit_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_currentLimit_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, int[] iArr);

    public static final native long btTranslationalLimitMotor2_currentLinearDiff_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_currentLinearDiff_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native boolean[] btTranslationalLimitMotor2_enableMotor_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_enableMotor_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, boolean[] zArr);

    public static final native boolean[] btTranslationalLimitMotor2_enableSpring_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_enableSpring_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, boolean[] zArr);

    public static final native long btTranslationalLimitMotor2_equilibriumPoint_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_equilibriumPoint_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native boolean btTranslationalLimitMotor2_isLimited(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, int i);

    public static final native long btTranslationalLimitMotor2_lowerLimit_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_lowerLimit_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor2_maxMotorForce_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_maxMotorForce_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor2_motorCFM_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_motorCFM_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor2_motorERP_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_motorERP_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native boolean[] btTranslationalLimitMotor2_servoMotor_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_servoMotor_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, boolean[] zArr);

    public static final native long btTranslationalLimitMotor2_servoTarget_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_servoTarget_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native boolean[] btTranslationalLimitMotor2_springDampingLimited_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_springDampingLimited_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, boolean[] zArr);

    public static final native long btTranslationalLimitMotor2_springDamping_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_springDamping_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native boolean[] btTranslationalLimitMotor2_springStiffnessLimited_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_springStiffnessLimited_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, boolean[] zArr);

    public static final native long btTranslationalLimitMotor2_springStiffness_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_springStiffness_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor2_stopCFM_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_stopCFM_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor2_stopERP_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_stopERP_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor2_targetVelocity_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_targetVelocity_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native void btTranslationalLimitMotor2_testLimitValue(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, int i, float f);

    public static final native long btTranslationalLimitMotor2_upperLimit_get(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native void btTranslationalLimitMotor2_upperLimit_set(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor_accumulatedImpulse_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_accumulatedImpulse_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor_currentLimitError_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_currentLimitError_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native int[] btTranslationalLimitMotor_currentLimit_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_currentLimit_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, int[] iArr);

    public static final native long btTranslationalLimitMotor_currentLinearDiff_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_currentLinearDiff_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native float btTranslationalLimitMotor_damping_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_damping_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, float f);

    public static final native boolean[] btTranslationalLimitMotor_enableMotor_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_enableMotor_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, boolean[] zArr);

    public static final native boolean btTranslationalLimitMotor_isLimited(long j, btTranslationalLimitMotor bttranslationallimitmotor, int i);

    public static final native float btTranslationalLimitMotor_limitSoftness_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_limitSoftness_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, float f);

    public static final native long btTranslationalLimitMotor_lowerLimit_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_lowerLimit_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor_maxMotorForce_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_maxMotorForce_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native boolean btTranslationalLimitMotor_needApplyForce(long j, btTranslationalLimitMotor bttranslationallimitmotor, int i);

    public static final native long btTranslationalLimitMotor_normalCFM_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_normalCFM_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native float btTranslationalLimitMotor_restitution_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_restitution_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, float f);

    public static final native float btTranslationalLimitMotor_solveLinearAxis(long j, btTranslationalLimitMotor bttranslationallimitmotor, float f, float f2, long j2, btRigidBody btrigidbody, Vector3 vector3, long j3, btRigidBody btrigidbody2, Vector3 vector4, int i, Vector3 vector5, Vector3 vector6);

    public static final native long btTranslationalLimitMotor_stopCFM_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_stopCFM_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor_stopERP_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_stopERP_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native long btTranslationalLimitMotor_targetVelocity_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_targetVelocity_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native int btTranslationalLimitMotor_testLimitValue(long j, btTranslationalLimitMotor bttranslationallimitmotor, int i, float f);

    public static final native long btTranslationalLimitMotor_upperLimit_get(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native void btTranslationalLimitMotor_upperLimit_set(long j, btTranslationalLimitMotor bttranslationallimitmotor, long j2, btVector3 btvector3);

    public static final native float btTypedConstraintData_appliedImpulse_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_appliedImpulse_set(long j, btTypedConstraintData bttypedconstraintdata, float f);

    public static final native float btTypedConstraintData_breakingImpulseThreshold_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_breakingImpulseThreshold_set(long j, btTypedConstraintData bttypedconstraintdata, float f);

    public static final native float btTypedConstraintData_dbgDrawSize_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_dbgDrawSize_set(long j, btTypedConstraintData bttypedconstraintdata, float f);

    public static final native int btTypedConstraintData_disableCollisionsBetweenLinkedBodies_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_disableCollisionsBetweenLinkedBodies_set(long j, btTypedConstraintData bttypedconstraintdata, int i);

    public static final native int btTypedConstraintData_isEnabled_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_isEnabled_set(long j, btTypedConstraintData bttypedconstraintdata, int i);

    public static final native String btTypedConstraintData_name_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_name_set(long j, btTypedConstraintData bttypedconstraintdata, String str);

    public static final native int btTypedConstraintData_needsFeedback_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_needsFeedback_set(long j, btTypedConstraintData bttypedconstraintdata, int i);

    public static final native int btTypedConstraintData_objectType_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_objectType_set(long j, btTypedConstraintData bttypedconstraintdata, int i);

    public static final native int btTypedConstraintData_overrideNumSolverIterations_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_overrideNumSolverIterations_set(long j, btTypedConstraintData bttypedconstraintdata, int i);

    public static final native long btTypedConstraintData_rbA_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_rbA_set(long j, btTypedConstraintData bttypedconstraintdata, long j2, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native long btTypedConstraintData_rbB_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_rbB_set(long j, btTypedConstraintData bttypedconstraintdata, long j2, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native int btTypedConstraintData_userConstraintId_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_userConstraintId_set(long j, btTypedConstraintData bttypedconstraintdata, int i);

    public static final native int btTypedConstraintData_userConstraintType_get(long j, btTypedConstraintData bttypedconstraintdata);

    public static final native void btTypedConstraintData_userConstraintType_set(long j, btTypedConstraintData bttypedconstraintdata, int i);

    public static final native double btTypedConstraintDoubleData_appliedImpulse_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_appliedImpulse_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, double d);

    public static final native double btTypedConstraintDoubleData_breakingImpulseThreshold_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_breakingImpulseThreshold_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, double d);

    public static final native double btTypedConstraintDoubleData_dbgDrawSize_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_dbgDrawSize_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, double d);

    public static final native int btTypedConstraintDoubleData_disableCollisionsBetweenLinkedBodies_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_disableCollisionsBetweenLinkedBodies_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, int i);

    public static final native int btTypedConstraintDoubleData_isEnabled_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_isEnabled_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, int i);

    public static final native String btTypedConstraintDoubleData_name_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_name_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, String str);

    public static final native int btTypedConstraintDoubleData_needsFeedback_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_needsFeedback_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, int i);

    public static final native int btTypedConstraintDoubleData_objectType_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_objectType_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, int i);

    public static final native int btTypedConstraintDoubleData_overrideNumSolverIterations_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_overrideNumSolverIterations_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, int i);

    public static final native String btTypedConstraintDoubleData_padding_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_padding_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, String str);

    public static final native long btTypedConstraintDoubleData_rbA_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_rbA_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, long j2, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native long btTypedConstraintDoubleData_rbB_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_rbB_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, long j2, btRigidBodyDoubleData btrigidbodydoubledata);

    public static final native int btTypedConstraintDoubleData_userConstraintId_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_userConstraintId_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, int i);

    public static final native int btTypedConstraintDoubleData_userConstraintType_get(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata);

    public static final native void btTypedConstraintDoubleData_userConstraintType_set(long j, btTypedConstraintDoubleData bttypedconstraintdoubledata, int i);

    public static final native float btTypedConstraintFloatData_appliedImpulse_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_appliedImpulse_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, float f);

    public static final native float btTypedConstraintFloatData_breakingImpulseThreshold_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_breakingImpulseThreshold_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, float f);

    public static final native float btTypedConstraintFloatData_dbgDrawSize_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_dbgDrawSize_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, float f);

    public static final native int btTypedConstraintFloatData_disableCollisionsBetweenLinkedBodies_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_disableCollisionsBetweenLinkedBodies_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, int i);

    public static final native int btTypedConstraintFloatData_isEnabled_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_isEnabled_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, int i);

    public static final native String btTypedConstraintFloatData_name_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_name_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, String str);

    public static final native int btTypedConstraintFloatData_needsFeedback_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_needsFeedback_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, int i);

    public static final native int btTypedConstraintFloatData_objectType_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_objectType_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, int i);

    public static final native int btTypedConstraintFloatData_overrideNumSolverIterations_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_overrideNumSolverIterations_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, int i);

    public static final native long btTypedConstraintFloatData_rbA_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_rbA_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, long j2, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native long btTypedConstraintFloatData_rbB_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_rbB_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, long j2, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native int btTypedConstraintFloatData_userConstraintId_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_userConstraintId_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, int i);

    public static final native int btTypedConstraintFloatData_userConstraintType_get(long j, btTypedConstraintFloatData bttypedconstraintfloatdata);

    public static final native void btTypedConstraintFloatData_userConstraintType_set(long j, btTypedConstraintFloatData bttypedconstraintfloatdata, int i);

    public static final native long btTypedConstraint_SWIGUpcast(long j);

    public static final native int btTypedConstraint_btConstraintInfo1_nub_get(long j, btTypedConstraint.btConstraintInfo1 btconstraintinfo1);

    public static final native void btTypedConstraint_btConstraintInfo1_nub_set(long j, btTypedConstraint.btConstraintInfo1 btconstraintinfo1, int i);

    public static final native int btTypedConstraint_btConstraintInfo1_numConstraintRows_get(long j, btTypedConstraint.btConstraintInfo1 btconstraintinfo1);

    public static final native void btTypedConstraint_btConstraintInfo1_numConstraintRows_set(long j, btTypedConstraint.btConstraintInfo1 btconstraintinfo1, int i);

    public static final native long btTypedConstraint_btConstraintInfo2_J1angularAxis_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_J1angularAxis_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, long j2);

    public static final native long btTypedConstraint_btConstraintInfo2_J1linearAxis_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_J1linearAxis_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, long j2);

    public static final native long btTypedConstraint_btConstraintInfo2_J2angularAxis_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_J2angularAxis_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, long j2);

    public static final native long btTypedConstraint_btConstraintInfo2_J2linearAxis_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_J2linearAxis_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, long j2);

    public static final native long btTypedConstraint_btConstraintInfo2_cfm_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_cfm_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, long j2);

    public static final native long btTypedConstraint_btConstraintInfo2_constraintError_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_constraintError_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, long j2);

    public static final native float btTypedConstraint_btConstraintInfo2_damping_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_damping_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, float f);

    public static final native float btTypedConstraint_btConstraintInfo2_erp_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_erp_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, float f);

    public static final native float btTypedConstraint_btConstraintInfo2_fps_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_fps_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, float f);

    public static final native long btTypedConstraint_btConstraintInfo2_lowerLimit_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_lowerLimit_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, long j2);

    public static final native int btTypedConstraint_btConstraintInfo2_numIterations_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_numIterations_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, int i);

    public static final native int btTypedConstraint_btConstraintInfo2_rowskip_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_rowskip_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, int i);

    public static final native long btTypedConstraint_btConstraintInfo2_upperLimit_get(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native void btTypedConstraint_btConstraintInfo2_upperLimit_set(long j, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, long j2);

    public static final native void btTypedConstraint_buildJacobian(long j, btTypedConstraint bttypedconstraint);

    public static final native int btTypedConstraint_calculateSerializeBufferSize(long j, btTypedConstraint bttypedconstraint);

    public static final native void btTypedConstraint_enableFeedback(long j, btTypedConstraint bttypedconstraint, boolean z);

    public static final native float btTypedConstraint_getAppliedImpulse(long j, btTypedConstraint bttypedconstraint);

    public static final native float btTypedConstraint_getBreakingImpulseThreshold(long j, btTypedConstraint bttypedconstraint);

    public static final native int btTypedConstraint_getConstraintType(long j, btTypedConstraint bttypedconstraint);

    public static final native float btTypedConstraint_getDbgDrawSize(long j, btTypedConstraint bttypedconstraint);

    public static final native long btTypedConstraint_getFixedBody();

    public static final native void btTypedConstraint_getInfo1(long j, btTypedConstraint bttypedconstraint, long j2, btTypedConstraint.btConstraintInfo1 btconstraintinfo1);

    public static final native void btTypedConstraint_getInfo2(long j, btTypedConstraint bttypedconstraint, long j2, btTypedConstraint.btConstraintInfo2 btconstraintinfo2);

    public static final native long btTypedConstraint_getJointFeedback(long j, btTypedConstraint bttypedconstraint);

    public static final native long btTypedConstraint_getJointFeedbackConst(long j, btTypedConstraint bttypedconstraint);

    public static final native int btTypedConstraint_getOverrideNumSolverIterations(long j, btTypedConstraint bttypedconstraint);

    public static final native float btTypedConstraint_getParam__SWIG_0(long j, btTypedConstraint bttypedconstraint, int i, int i2);

    public static final native float btTypedConstraint_getParam__SWIG_1(long j, btTypedConstraint bttypedconstraint, int i);

    public static final native long btTypedConstraint_getRigidBodyA(long j, btTypedConstraint bttypedconstraint);

    public static final native long btTypedConstraint_getRigidBodyAConst(long j, btTypedConstraint bttypedconstraint);

    public static final native long btTypedConstraint_getRigidBodyB(long j, btTypedConstraint bttypedconstraint);

    public static final native long btTypedConstraint_getRigidBodyBConst(long j, btTypedConstraint bttypedconstraint);

    public static final native int btTypedConstraint_getUid(long j, btTypedConstraint bttypedconstraint);

    public static final native int btTypedConstraint_getUserConstraintId(long j, btTypedConstraint bttypedconstraint);

    public static final native long btTypedConstraint_getUserConstraintPtr(long j, btTypedConstraint bttypedconstraint);

    public static final native int btTypedConstraint_getUserConstraintType(long j, btTypedConstraint bttypedconstraint);

    public static final native float btTypedConstraint_internalGetAppliedImpulse(long j, btTypedConstraint bttypedconstraint);

    public static final native void btTypedConstraint_internalSetAppliedImpulse(long j, btTypedConstraint bttypedconstraint, float f);

    public static final native boolean btTypedConstraint_isEnabled(long j, btTypedConstraint bttypedconstraint);

    public static final native boolean btTypedConstraint_needsFeedback(long j, btTypedConstraint bttypedconstraint);

    public static final native void btTypedConstraint_operatorDeleteArray__SWIG_0(long j, btTypedConstraint bttypedconstraint, long j2);

    public static final native void btTypedConstraint_operatorDeleteArray__SWIG_1(long j, btTypedConstraint bttypedconstraint, long j2, long j3);

    public static final native void btTypedConstraint_operatorDelete__SWIG_0(long j, btTypedConstraint bttypedconstraint, long j2);

    public static final native void btTypedConstraint_operatorDelete__SWIG_1(long j, btTypedConstraint bttypedconstraint, long j2, long j3);

    public static final native long btTypedConstraint_operatorNewArray__SWIG_0(long j, btTypedConstraint bttypedconstraint, long j2);

    public static final native long btTypedConstraint_operatorNewArray__SWIG_1(long j, btTypedConstraint bttypedconstraint, long j2, long j3);

    public static final native long btTypedConstraint_operatorNew__SWIG_0(long j, btTypedConstraint bttypedconstraint, long j2);

    public static final native long btTypedConstraint_operatorNew__SWIG_1(long j, btTypedConstraint bttypedconstraint, long j2, long j3);

    public static final native String btTypedConstraint_serialize(long j, btTypedConstraint bttypedconstraint, long j2, long j3, btSerializer btserializer);

    public static final native void btTypedConstraint_setBreakingImpulseThreshold(long j, btTypedConstraint bttypedconstraint, float f);

    public static final native void btTypedConstraint_setDbgDrawSize(long j, btTypedConstraint bttypedconstraint, float f);

    public static final native void btTypedConstraint_setEnabled(long j, btTypedConstraint bttypedconstraint, boolean z);

    public static final native void btTypedConstraint_setJointFeedback(long j, btTypedConstraint bttypedconstraint, long j2, btJointFeedback btjointfeedback);

    public static final native void btTypedConstraint_setOverrideNumSolverIterations(long j, btTypedConstraint bttypedconstraint, int i);

    public static final native void btTypedConstraint_setParam__SWIG_0(long j, btTypedConstraint bttypedconstraint, int i, float f, int i2);

    public static final native void btTypedConstraint_setParam__SWIG_1(long j, btTypedConstraint bttypedconstraint, int i, float f);

    public static final native void btTypedConstraint_setUserConstraintId(long j, btTypedConstraint bttypedconstraint, int i);

    public static final native void btTypedConstraint_setUserConstraintPtr(long j, btTypedConstraint bttypedconstraint, long j2);

    public static final native void btTypedConstraint_setUserConstraintType(long j, btTypedConstraint bttypedconstraint, int i);

    public static final native void btTypedConstraint_setupSolverConstraint(long j, btTypedConstraint bttypedconstraint, long j2, int i, int i2, float f);

    public static final native void btTypedConstraint_solveConstraintObsolete(long j, btTypedConstraint bttypedconstraint, long j2, btSolverBody btsolverbody, long j3, btSolverBody btsolverbody2, float f);

    public static final native long btUniversalConstraint_SWIGUpcast(long j);

    public static final native Vector3 btUniversalConstraint_getAnchor(long j, btUniversalConstraint btuniversalconstraint);

    public static final native Vector3 btUniversalConstraint_getAnchor2(long j, btUniversalConstraint btuniversalconstraint);

    public static final native float btUniversalConstraint_getAngle1(long j, btUniversalConstraint btuniversalconstraint);

    public static final native float btUniversalConstraint_getAngle2(long j, btUniversalConstraint btuniversalconstraint);

    public static final native Vector3 btUniversalConstraint_getAxis1(long j, btUniversalConstraint btuniversalconstraint);

    public static final native Vector3 btUniversalConstraint_getAxis2(long j, btUniversalConstraint btuniversalconstraint);

    public static final native void btUniversalConstraint_operatorDeleteArray__SWIG_0(long j, btUniversalConstraint btuniversalconstraint, long j2);

    public static final native void btUniversalConstraint_operatorDeleteArray__SWIG_1(long j, btUniversalConstraint btuniversalconstraint, long j2, long j3);

    public static final native void btUniversalConstraint_operatorDelete__SWIG_0(long j, btUniversalConstraint btuniversalconstraint, long j2);

    public static final native void btUniversalConstraint_operatorDelete__SWIG_1(long j, btUniversalConstraint btuniversalconstraint, long j2, long j3);

    public static final native long btUniversalConstraint_operatorNewArray__SWIG_0(long j, btUniversalConstraint btuniversalconstraint, long j2);

    public static final native long btUniversalConstraint_operatorNewArray__SWIG_1(long j, btUniversalConstraint btuniversalconstraint, long j2, long j3);

    public static final native long btUniversalConstraint_operatorNew__SWIG_0(long j, btUniversalConstraint btuniversalconstraint, long j2);

    public static final native long btUniversalConstraint_operatorNew__SWIG_1(long j, btUniversalConstraint btuniversalconstraint, long j2, long j3);

    public static final native void btUniversalConstraint_setAxis(long j, btUniversalConstraint btuniversalconstraint, Vector3 vector3, Vector3 vector4);

    public static final native void btUniversalConstraint_setLowerLimit(long j, btUniversalConstraint btuniversalconstraint, float f, float f2);

    public static final native void btUniversalConstraint_setUpperLimit(long j, btUniversalConstraint btuniversalconstraint, float f, float f2);

    public static final native float btVehicleRaycaster_btVehicleRaycasterResult_distFraction_get(long j, btVehicleRaycaster.btVehicleRaycasterResult btvehicleraycasterresult);

    public static final native void btVehicleRaycaster_btVehicleRaycasterResult_distFraction_set(long j, btVehicleRaycaster.btVehicleRaycasterResult btvehicleraycasterresult, float f);

    public static final native long btVehicleRaycaster_btVehicleRaycasterResult_hitNormalInWorld_get(long j, btVehicleRaycaster.btVehicleRaycasterResult btvehicleraycasterresult);

    public static final native void btVehicleRaycaster_btVehicleRaycasterResult_hitNormalInWorld_set(long j, btVehicleRaycaster.btVehicleRaycasterResult btvehicleraycasterresult, long j2, btVector3 btvector3);

    public static final native long btVehicleRaycaster_btVehicleRaycasterResult_hitPointInWorld_get(long j, btVehicleRaycaster.btVehicleRaycasterResult btvehicleraycasterresult);

    public static final native void btVehicleRaycaster_btVehicleRaycasterResult_hitPointInWorld_set(long j, btVehicleRaycaster.btVehicleRaycasterResult btvehicleraycasterresult, long j2, btVector3 btvector3);

    public static final native long btVehicleRaycaster_castRay(long j, btVehicleRaycaster btvehicleraycaster, Vector3 vector3, Vector3 vector4, long j2, btVehicleRaycaster.btVehicleRaycasterResult btvehicleraycasterresult);

    public static final native boolean btWheelInfoConstructionInfo_bIsFrontWheel_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_bIsFrontWheel_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, boolean z);

    public static final native long btWheelInfoConstructionInfo_chassisConnectionCS_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_chassisConnectionCS_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, long j2, btVector3 btvector3);

    public static final native float btWheelInfoConstructionInfo_frictionSlip_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_frictionSlip_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, float f);

    public static final native float btWheelInfoConstructionInfo_maxSuspensionForce_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_maxSuspensionForce_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, float f);

    public static final native float btWheelInfoConstructionInfo_maxSuspensionTravelCm_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_maxSuspensionTravelCm_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, float f);

    public static final native float btWheelInfoConstructionInfo_suspensionRestLength_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_suspensionRestLength_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, float f);

    public static final native float btWheelInfoConstructionInfo_suspensionStiffness_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_suspensionStiffness_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, float f);

    public static final native long btWheelInfoConstructionInfo_wheelAxleCS_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_wheelAxleCS_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, long j2, btVector3 btvector3);

    public static final native long btWheelInfoConstructionInfo_wheelDirectionCS_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_wheelDirectionCS_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, long j2, btVector3 btvector3);

    public static final native float btWheelInfoConstructionInfo_wheelRadius_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_wheelRadius_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, float f);

    public static final native float btWheelInfoConstructionInfo_wheelsDampingCompression_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_wheelsDampingCompression_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, float f);

    public static final native float btWheelInfoConstructionInfo_wheelsDampingRelaxation_get(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void btWheelInfoConstructionInfo_wheelsDampingRelaxation_set(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo, float f);

    public static final native long btWheelInfo_RaycastInfo_contactNormalWS_get(long j, btWheelInfo.RaycastInfo raycastInfo);

    public static final native void btWheelInfo_RaycastInfo_contactNormalWS_set(long j, btWheelInfo.RaycastInfo raycastInfo, long j2, btVector3 btvector3);

    public static final native long btWheelInfo_RaycastInfo_contactPointWS_get(long j, btWheelInfo.RaycastInfo raycastInfo);

    public static final native void btWheelInfo_RaycastInfo_contactPointWS_set(long j, btWheelInfo.RaycastInfo raycastInfo, long j2, btVector3 btvector3);

    public static final native long btWheelInfo_RaycastInfo_groundObject_get(long j, btWheelInfo.RaycastInfo raycastInfo);

    public static final native void btWheelInfo_RaycastInfo_groundObject_set(long j, btWheelInfo.RaycastInfo raycastInfo, long j2);

    public static final native long btWheelInfo_RaycastInfo_hardPointWS_get(long j, btWheelInfo.RaycastInfo raycastInfo);

    public static final native void btWheelInfo_RaycastInfo_hardPointWS_set(long j, btWheelInfo.RaycastInfo raycastInfo, long j2, btVector3 btvector3);

    public static final native boolean btWheelInfo_RaycastInfo_isInContact_get(long j, btWheelInfo.RaycastInfo raycastInfo);

    public static final native void btWheelInfo_RaycastInfo_isInContact_set(long j, btWheelInfo.RaycastInfo raycastInfo, boolean z);

    public static final native float btWheelInfo_RaycastInfo_suspensionLength_get(long j, btWheelInfo.RaycastInfo raycastInfo);

    public static final native void btWheelInfo_RaycastInfo_suspensionLength_set(long j, btWheelInfo.RaycastInfo raycastInfo, float f);

    public static final native long btWheelInfo_RaycastInfo_wheelAxleWS_get(long j, btWheelInfo.RaycastInfo raycastInfo);

    public static final native void btWheelInfo_RaycastInfo_wheelAxleWS_set(long j, btWheelInfo.RaycastInfo raycastInfo, long j2, btVector3 btvector3);

    public static final native long btWheelInfo_RaycastInfo_wheelDirectionWS_get(long j, btWheelInfo.RaycastInfo raycastInfo);

    public static final native void btWheelInfo_RaycastInfo_wheelDirectionWS_set(long j, btWheelInfo.RaycastInfo raycastInfo, long j2, btVector3 btvector3);

    public static final native boolean btWheelInfo_bIsFrontWheel_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_bIsFrontWheel_set(long j, btWheelInfo btwheelinfo, boolean z);

    public static final native float btWheelInfo_brake_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_brake_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native long btWheelInfo_chassisConnectionPointCS_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_chassisConnectionPointCS_set(long j, btWheelInfo btwheelinfo, long j2, btVector3 btvector3);

    public static final native long btWheelInfo_clientInfo_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_clientInfo_set(long j, btWheelInfo btwheelinfo, long j2);

    public static final native float btWheelInfo_clippedInvContactDotSuspension_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_clippedInvContactDotSuspension_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_deltaRotation_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_deltaRotation_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_engineForce_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_engineForce_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_frictionSlip_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_frictionSlip_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_getSuspensionRestLength(long j, btWheelInfo btwheelinfo);

    public static final native float btWheelInfo_maxSuspensionForce_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_maxSuspensionForce_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_maxSuspensionTravelCm_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_maxSuspensionTravelCm_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native long btWheelInfo_raycastInfo_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_raycastInfo_set(long j, btWheelInfo btwheelinfo, long j2, btWheelInfo.RaycastInfo raycastInfo);

    public static final native float btWheelInfo_rollInfluence_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_rollInfluence_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_rotation_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_rotation_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_skidInfo_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_skidInfo_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_steering_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_steering_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_suspensionRelativeVelocity_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_suspensionRelativeVelocity_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_suspensionRestLength1_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_suspensionRestLength1_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_suspensionStiffness_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_suspensionStiffness_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native void btWheelInfo_updateWheel(long j, btWheelInfo btwheelinfo, long j2, btRigidBody btrigidbody, long j3, btWheelInfo.RaycastInfo raycastInfo);

    public static final native long btWheelInfo_wheelAxleCS_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_wheelAxleCS_set(long j, btWheelInfo btwheelinfo, long j2, btVector3 btvector3);

    public static final native long btWheelInfo_wheelDirectionCS_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_wheelDirectionCS_set(long j, btWheelInfo btwheelinfo, long j2, btVector3 btvector3);

    public static final native float btWheelInfo_wheelsDampingCompression_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_wheelsDampingCompression_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_wheelsDampingRelaxation_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_wheelsDampingRelaxation_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_wheelsRadius_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_wheelsRadius_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native float btWheelInfo_wheelsSuspensionForce_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_wheelsSuspensionForce_set(long j, btWheelInfo btwheelinfo, float f);

    public static final native long btWheelInfo_worldTransform_get(long j, btWheelInfo btwheelinfo);

    public static final native void btWheelInfo_worldTransform_set(long j, btWheelInfo btwheelinfo, long j2, btTransform bttransform);

    public static final native void delete_CustomActionInterface(long j);

    public static final native void delete_FilterableVehicleRaycaster(long j);

    public static final native void delete_InternalTickCallback(long j);

    public static final native void delete_btActionInterface(long j);

    public static final native void delete_btAngularLimit(long j);

    public static final native void delete_btCharacterControllerInterface(long j);

    public static final native void delete_btConeTwistConstraint(long j);

    public static final native void delete_btConeTwistConstraintData(long j);

    public static final native void delete_btConeTwistConstraintDoubleData(long j);

    public static final native void delete_btConstraintSetting(long j);

    public static final native void delete_btConstraintSolver(long j);

    public static final native void delete_btConstraintSolverPoolMt(long j);

    public static final native void delete_btContactConstraint(long j);

    public static final native void delete_btContactSolverInfo(long j);

    public static final native void delete_btContactSolverInfoData(long j);

    public static final native void delete_btContactSolverInfoDoubleData(long j);

    public static final native void delete_btContactSolverInfoFloatData(long j);

    public static final native void delete_btDantzigScratchMemory(long j);

    public static final native void delete_btDantzigSolver(long j);

    public static final native void delete_btDefaultVehicleRaycaster(long j);

    public static final native void delete_btDiscreteDynamicsWorld(long j);

    public static final native void delete_btDiscreteDynamicsWorldMt(long j);

    public static final native void delete_btDynamicsWorld(long j);

    public static final native void delete_btDynamicsWorldDoubleData(long j);

    public static final native void delete_btDynamicsWorldFloatData(long j);

    public static final native void delete_btFixedConstraint(long j);

    public static final native void delete_btGearConstraint(long j);

    public static final native void delete_btGearConstraintDoubleData(long j);

    public static final native void delete_btGearConstraintFloatData(long j);

    public static final native void delete_btGeneric6DofConstraint(long j);

    public static final native void delete_btGeneric6DofConstraintData(long j);

    public static final native void delete_btGeneric6DofConstraintDoubleData2(long j);

    public static final native void delete_btGeneric6DofSpring2Constraint(long j);

    public static final native void delete_btGeneric6DofSpring2ConstraintData(long j);

    public static final native void delete_btGeneric6DofSpring2ConstraintDoubleData2(long j);

    public static final native void delete_btGeneric6DofSpringConstraint(long j);

    public static final native void delete_btGeneric6DofSpringConstraintData(long j);

    public static final native void delete_btGeneric6DofSpringConstraintDoubleData2(long j);

    public static final native void delete_btHinge2Constraint(long j);

    public static final native void delete_btHingeAccumulatedAngleConstraint(long j);

    public static final native void delete_btHingeConstraint(long j);

    public static final native void delete_btHingeConstraintDoubleData(long j);

    public static final native void delete_btHingeConstraintDoubleData2(long j);

    public static final native void delete_btHingeConstraintFloatData(long j);

    public static final native void delete_btJacobianEntry(long j);

    public static final native void delete_btJointFeedback(long j);

    public static final native void delete_btKinematicCharacterController(long j);

    public static final native void delete_btLemkeAlgorithm(long j);

    public static final native void delete_btLemkeSolver(long j);

    public static final native void delete_btMLCPSolver(long j);

    public static final native void delete_btMLCPSolverInterface(long j);

    public static final native void delete_btMultiBody(long j);

    public static final native void delete_btMultiBodyConstraint(long j);

    public static final native void delete_btMultiBodyConstraintSolver(long j);

    public static final native void delete_btMultiBodyDoubleData(long j);

    public static final native void delete_btMultiBodyDynamicsWorld(long j);

    public static final native void delete_btMultiBodyFixedConstraint(long j);

    public static final native void delete_btMultiBodyFloatData(long j);

    public static final native void delete_btMultiBodyGearConstraint(long j);

    public static final native void delete_btMultiBodyJacobianData(long j);

    public static final native void delete_btMultiBodyJointFeedback(long j);

    public static final native void delete_btMultiBodyJointLimitConstraint(long j);

    public static final native void delete_btMultiBodyJointMotor(long j);

    public static final native void delete_btMultiBodyLinkCollider(long j);

    public static final native void delete_btMultiBodyLinkDoubleData(long j);

    public static final native void delete_btMultiBodyLinkFloatData(long j);

    public static final native void delete_btMultiBodyPoint2Point(long j);

    public static final native void delete_btMultiBodySliderConstraint(long j);

    public static final native void delete_btMultiBodySolverConstraint(long j);

    public static final native void delete_btMultibodyLink(long j);

    public static final native void delete_btNNCGConstraintSolver(long j);

    public static final native void delete_btPoint2PointConstraint(long j);

    public static final native void delete_btPoint2PointConstraintDoubleData(long j);

    public static final native void delete_btPoint2PointConstraintDoubleData2(long j);

    public static final native void delete_btPoint2PointConstraintFloatData(long j);

    public static final native void delete_btRaycastVehicle(long j);

    public static final native void delete_btRaycastVehicle_btVehicleTuning(long j);

    public static final native void delete_btRigidBody(long j);

    public static final native void delete_btRigidBodyDoubleData(long j);

    public static final native void delete_btRigidBodyFloatData(long j);

    public static final native void delete_btRigidBody_btRigidBodyConstructionInfo(long j);

    public static final native void delete_btRotationalLimitMotor(long j);

    public static final native void delete_btRotationalLimitMotor2(long j);

    public static final native void delete_btSequentialImpulseConstraintSolver(long j);

    public static final native void delete_btSimpleDynamicsWorld(long j);

    public static final native void delete_btSimulationIslandManagerMt(long j);

    public static final native void delete_btSimulationIslandManagerMt_Island(long j);

    public static final native void delete_btSimulationIslandManagerMt_IslandCallback(long j);

    public static final native void delete_btSliderConstraint(long j);

    public static final native void delete_btSliderConstraintData(long j);

    public static final native void delete_btSliderConstraintDoubleData(long j);

    public static final native void delete_btSolve2LinearConstraint(long j);

    public static final native void delete_btSolveProjectedGaussSeidel(long j);

    public static final native void delete_btSolverBody(long j);

    public static final native void delete_btSolverConstraint(long j);

    public static final native void delete_btTranslationalLimitMotor(long j);

    public static final native void delete_btTranslationalLimitMotor2(long j);

    public static final native void delete_btTypedConstraint(long j);

    public static final native void delete_btTypedConstraintData(long j);

    public static final native void delete_btTypedConstraintDoubleData(long j);

    public static final native void delete_btTypedConstraintFloatData(long j);

    public static final native void delete_btTypedConstraint_btConstraintInfo1(long j);

    public static final native void delete_btTypedConstraint_btConstraintInfo2(long j);

    public static final native void delete_btUniversalConstraint(long j);

    public static final native void delete_btVehicleRaycaster(long j);

    public static final native void delete_btVehicleRaycaster_btVehicleRaycasterResult(long j);

    public static final native void delete_btWheelInfo(long j);

    public static final native void delete_btWheelInfoConstructionInfo(long j);

    public static final native void delete_btWheelInfo_RaycastInfo(long j);

    public static final native float gDeactivationTime_get();

    public static final native void gDeactivationTime_set(float f);

    public static final native boolean gDisableDeactivation_get();

    public static final native void gDisableDeactivation_set(boolean z);

    public static final native long new_CustomActionInterface();

    public static final native long new_FilterableVehicleRaycaster(long j, btDynamicsWorld btdynamicsworld);

    public static final native long new_InternalTickCallback__SWIG_0(long j, btDynamicsWorld btdynamicsworld, boolean z);

    public static final native long new_InternalTickCallback__SWIG_1(long j, btDynamicsWorld btdynamicsworld);

    public static final native long new_InternalTickCallback__SWIG_2();

    public static final native long new_btAngularLimit();

    public static final native long new_btConeTwistConstraintData();

    public static final native long new_btConeTwistConstraintDoubleData();

    public static final native long new_btConeTwistConstraint__SWIG_0(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5);

    public static final native long new_btConeTwistConstraint__SWIG_1(long j, btRigidBody btrigidbody, Matrix4 matrix4);

    public static final native long new_btConstraintSetting();

    public static final native long new_btConstraintSolverPoolMt__SWIG_0(int i);

    public static final native long new_btConstraintSolverPoolMt__SWIG_1(long j, int i);

    public static final native long new_btContactSolverInfo();

    public static final native long new_btContactSolverInfoData();

    public static final native long new_btContactSolverInfoDoubleData();

    public static final native long new_btContactSolverInfoFloatData();

    public static final native long new_btDantzigScratchMemory();

    public static final native long new_btDantzigSolver();

    public static final native long new_btDefaultVehicleRaycaster(long j, btDynamicsWorld btdynamicsworld);

    public static final native long new_btDiscreteDynamicsWorld(long j, btDispatcher btdispatcher, long j2, btBroadphaseInterface btbroadphaseinterface, long j3, btConstraintSolver btconstraintsolver, long j4, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_btDiscreteDynamicsWorldMt(long j, btDispatcher btdispatcher, long j2, btBroadphaseInterface btbroadphaseinterface, long j3, btConstraintSolverPoolMt btconstraintsolverpoolmt, long j4, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_btDynamicsWorldDoubleData();

    public static final native long new_btDynamicsWorldFloatData();

    public static final native long new_btFixedConstraint(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5);

    public static final native long new_btGearConstraintDoubleData();

    public static final native long new_btGearConstraintFloatData();

    public static final native long new_btGearConstraint__SWIG_0(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, float f);

    public static final native long new_btGearConstraint__SWIG_1(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4);

    public static final native long new_btGeneric6DofConstraintData();

    public static final native long new_btGeneric6DofConstraintDoubleData2();

    public static final native long new_btGeneric6DofConstraint__SWIG_0(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z);

    public static final native long new_btGeneric6DofConstraint__SWIG_1(long j, btRigidBody btrigidbody, Matrix4 matrix4, boolean z);

    public static final native long new_btGeneric6DofSpring2ConstraintData();

    public static final native long new_btGeneric6DofSpring2ConstraintDoubleData2();

    public static final native long new_btGeneric6DofSpring2Constraint__SWIG_0(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, int i);

    public static final native long new_btGeneric6DofSpring2Constraint__SWIG_1(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5);

    public static final native long new_btGeneric6DofSpring2Constraint__SWIG_2(long j, btRigidBody btrigidbody, Matrix4 matrix4, int i);

    public static final native long new_btGeneric6DofSpring2Constraint__SWIG_3(long j, btRigidBody btrigidbody, Matrix4 matrix4);

    public static final native long new_btGeneric6DofSpringConstraintData();

    public static final native long new_btGeneric6DofSpringConstraintDoubleData2();

    public static final native long new_btGeneric6DofSpringConstraint__SWIG_0(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z);

    public static final native long new_btGeneric6DofSpringConstraint__SWIG_1(long j, btRigidBody btrigidbody, Matrix4 matrix4, boolean z);

    public static final native long new_btHinge2Constraint(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native long new_btHingeAccumulatedAngleConstraint__SWIG_0(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, boolean z);

    public static final native long new_btHingeAccumulatedAngleConstraint__SWIG_1(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native long new_btHingeAccumulatedAngleConstraint__SWIG_2(long j, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4, boolean z);

    public static final native long new_btHingeAccumulatedAngleConstraint__SWIG_3(long j, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4);

    public static final native long new_btHingeAccumulatedAngleConstraint__SWIG_4(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z);

    public static final native long new_btHingeAccumulatedAngleConstraint__SWIG_5(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5);

    public static final native long new_btHingeAccumulatedAngleConstraint__SWIG_6(long j, btRigidBody btrigidbody, Matrix4 matrix4, boolean z);

    public static final native long new_btHingeAccumulatedAngleConstraint__SWIG_7(long j, btRigidBody btrigidbody, Matrix4 matrix4);

    public static final native long new_btHingeConstraintDoubleData();

    public static final native long new_btHingeConstraintDoubleData2();

    public static final native long new_btHingeConstraintFloatData();

    public static final native long new_btHingeConstraint__SWIG_0(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, boolean z);

    public static final native long new_btHingeConstraint__SWIG_1(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native long new_btHingeConstraint__SWIG_2(long j, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4, boolean z);

    public static final native long new_btHingeConstraint__SWIG_3(long j, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4);

    public static final native long new_btHingeConstraint__SWIG_4(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z);

    public static final native long new_btHingeConstraint__SWIG_5(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5);

    public static final native long new_btHingeConstraint__SWIG_6(long j, btRigidBody btrigidbody, Matrix4 matrix4, boolean z);

    public static final native long new_btHingeConstraint__SWIG_7(long j, btRigidBody btrigidbody, Matrix4 matrix4);

    public static final native long new_btJacobianEntry__SWIG_0();

    public static final native long new_btJacobianEntry__SWIG_1(Matrix3 matrix3, Matrix3 matrix4, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f, Vector3 vector7, float f2);

    public static final native long new_btJacobianEntry__SWIG_2(Vector3 vector3, Matrix3 matrix3, Matrix3 matrix4, Vector3 vector4, Vector3 vector5);

    public static final native long new_btJacobianEntry__SWIG_3(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native long new_btJacobianEntry__SWIG_4(Matrix3 matrix3, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f);

    public static final native long new_btJointFeedback();

    public static final native long new_btKinematicCharacterController__SWIG_0(long j, btPairCachingGhostObject btpaircachingghostobject, long j2, btConvexShape btconvexshape, float f, Vector3 vector3);

    public static final native long new_btKinematicCharacterController__SWIG_1(long j, btPairCachingGhostObject btpaircachingghostobject, long j2, btConvexShape btconvexshape, float f);

    public static final native long new_btLemkeAlgorithm__SWIG_0(long j, long j2, int i);

    public static final native long new_btLemkeAlgorithm__SWIG_1(long j, long j2);

    public static final native long new_btLemkeSolver();

    public static final native long new_btMLCPSolver(long j, btMLCPSolverInterface btmlcpsolverinterface);

    public static final native long new_btMultiBodyConstraintSolver();

    public static final native long new_btMultiBodyDoubleData();

    public static final native long new_btMultiBodyDynamicsWorld(long j, btDispatcher btdispatcher, long j2, btBroadphaseInterface btbroadphaseinterface, long j3, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j4, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_btMultiBodyFixedConstraint__SWIG_0(long j, btMultiBody btmultibody, int i, long j2, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4, Matrix3 matrix3, Matrix3 matrix4);

    public static final native long new_btMultiBodyFixedConstraint__SWIG_1(long j, btMultiBody btmultibody, int i, long j2, btMultiBody btmultibody2, int i2, Vector3 vector3, Vector3 vector4, Matrix3 matrix3, Matrix3 matrix4);

    public static final native long new_btMultiBodyFloatData();

    public static final native long new_btMultiBodyGearConstraint(long j, btMultiBody btmultibody, int i, long j2, btMultiBody btmultibody2, int i2, Vector3 vector3, Vector3 vector4, Matrix3 matrix3, Matrix3 matrix4);

    public static final native long new_btMultiBodyJacobianData();

    public static final native long new_btMultiBodyJointFeedback();

    public static final native long new_btMultiBodyJointLimitConstraint(long j, btMultiBody btmultibody, int i, float f, float f2);

    public static final native long new_btMultiBodyJointMotor__SWIG_0(long j, btMultiBody btmultibody, int i, float f, float f2);

    public static final native long new_btMultiBodyJointMotor__SWIG_1(long j, btMultiBody btmultibody, int i, int i2, float f, float f2);

    public static final native long new_btMultiBodyLinkCollider(long j, btMultiBody btmultibody, int i);

    public static final native long new_btMultiBodyLinkDoubleData();

    public static final native long new_btMultiBodyLinkFloatData();

    public static final native long new_btMultiBodyPoint2Point__SWIG_0(long j, btMultiBody btmultibody, int i, long j2, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4);

    public static final native long new_btMultiBodyPoint2Point__SWIG_1(long j, btMultiBody btmultibody, int i, long j2, btMultiBody btmultibody2, int i2, Vector3 vector3, Vector3 vector4);

    public static final native long new_btMultiBodySliderConstraint__SWIG_0(long j, btMultiBody btmultibody, int i, long j2, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4, Matrix3 matrix3, Matrix3 matrix4, Vector3 vector5);

    public static final native long new_btMultiBodySliderConstraint__SWIG_1(long j, btMultiBody btmultibody, int i, long j2, btMultiBody btmultibody2, int i2, Vector3 vector3, Vector3 vector4, Matrix3 matrix3, Matrix3 matrix4, Vector3 vector5);

    public static final native long new_btMultiBodySolverConstraint();

    public static final native long new_btMultiBody__SWIG_0(int i, float f, Vector3 vector3, boolean z, boolean z2, boolean z3);

    public static final native long new_btMultiBody__SWIG_1(int i, float f, Vector3 vector3, boolean z, boolean z2);

    public static final native long new_btMultibodyLink();

    public static final native long new_btNNCGConstraintSolver();

    public static final native long new_btPoint2PointConstraintDoubleData();

    public static final native long new_btPoint2PointConstraintDoubleData2();

    public static final native long new_btPoint2PointConstraintFloatData();

    public static final native long new_btPoint2PointConstraint__SWIG_0(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4);

    public static final native long new_btPoint2PointConstraint__SWIG_1(long j, btRigidBody btrigidbody, Vector3 vector3);

    public static final native long new_btRaycastVehicle(long j, btRaycastVehicle.btVehicleTuning btvehicletuning, long j2, btRigidBody btrigidbody, long j3, btVehicleRaycaster btvehicleraycaster);

    public static final native long new_btRaycastVehicle_btVehicleTuning();

    public static final native long new_btRigidBodyDoubleData();

    public static final native long new_btRigidBodyFloatData();

    public static final native long new_btRigidBody__SWIG_0(boolean z, long j, btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo);

    public static final native long new_btRigidBody__SWIG_1(boolean z, float f, long j, btMotionState btmotionstate, long j2, btCollisionShape btcollisionshape, Vector3 vector3);

    public static final native long new_btRigidBody__SWIG_2(boolean z, float f, long j, btMotionState btmotionstate, long j2, btCollisionShape btcollisionshape);

    public static final native long new_btRigidBody_btRigidBodyConstructionInfo__SWIG_0(boolean z, float f, long j, btMotionState btmotionstate, long j2, btCollisionShape btcollisionshape, Vector3 vector3);

    public static final native long new_btRigidBody_btRigidBodyConstructionInfo__SWIG_1(boolean z, float f, long j, btMotionState btmotionstate, long j2, btCollisionShape btcollisionshape);

    public static final native long new_btRotationalLimitMotor2__SWIG_0();

    public static final native long new_btRotationalLimitMotor2__SWIG_1(long j, btRotationalLimitMotor2 btrotationallimitmotor2);

    public static final native long new_btRotationalLimitMotor__SWIG_0();

    public static final native long new_btRotationalLimitMotor__SWIG_1(long j, btRotationalLimitMotor btrotationallimitmotor);

    public static final native long new_btSequentialImpulseConstraintSolver();

    public static final native long new_btSimpleDynamicsWorld(long j, btDispatcher btdispatcher, long j2, btBroadphaseInterface btbroadphaseinterface, long j3, btConstraintSolver btconstraintsolver, long j4, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_btSimulationIslandManagerMt();

    public static final native long new_btSimulationIslandManagerMt_Island();

    public static final native long new_btSliderConstraintData();

    public static final native long new_btSliderConstraintDoubleData();

    public static final native long new_btSliderConstraint__SWIG_0(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z);

    public static final native long new_btSliderConstraint__SWIG_1(long j, btRigidBody btrigidbody, Matrix4 matrix4, boolean z);

    public static final native long new_btSolve2LinearConstraint(float f, float f2);

    public static final native long new_btSolveProjectedGaussSeidel();

    public static final native long new_btSolverBody();

    public static final native long new_btSolverConstraint();

    public static final native long new_btTranslationalLimitMotor2__SWIG_0();

    public static final native long new_btTranslationalLimitMotor2__SWIG_1(long j, btTranslationalLimitMotor2 bttranslationallimitmotor2);

    public static final native long new_btTranslationalLimitMotor__SWIG_0();

    public static final native long new_btTranslationalLimitMotor__SWIG_1(long j, btTranslationalLimitMotor bttranslationallimitmotor);

    public static final native long new_btTypedConstraintData();

    public static final native long new_btTypedConstraintDoubleData();

    public static final native long new_btTypedConstraintFloatData();

    public static final native long new_btTypedConstraint_btConstraintInfo1();

    public static final native long new_btTypedConstraint_btConstraintInfo2();

    public static final native long new_btUniversalConstraint(long j, btRigidBody btrigidbody, long j2, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native long new_btVehicleRaycaster_btVehicleRaycasterResult();

    public static final native long new_btWheelInfoConstructionInfo();

    public static final native long new_btWheelInfo_RaycastInfo();

    public static final native long new_btWheelInfo__SWIG_0();

    public static final native long new_btWheelInfo__SWIG_1(long j, btWheelInfoConstructionInfo btwheelinfoconstructioninfo);

    public static final native void resolveSingleBilateral(long j, btRigidBody btrigidbody, Vector3 vector3, long j2, btRigidBody btrigidbody2, Vector3 vector4, float f, Vector3 vector5, long j3, float f2);

    public static final native float resolveSingleCollision(long j, btRigidBody btrigidbody, long j2, btCollisionObject btcollisionobject, Vector3 vector3, Vector3 vector4, long j3, btContactSolverInfo btcontactsolverinfo, float f);

    private static final native void swig_module_init();

    public static void SwigDirector_InternalTickCallback_onInternalTick(InternalTickCallback internalTickCallback, long j, float f) {
        internalTickCallback.onInternalTick(j == 0 ? null : new btDynamicsWorld(j, false), f);
    }

    public static void SwigDirector_CustomActionInterface_updateAction(CustomActionInterface customActionInterface, float f) {
        customActionInterface.updateAction(f);
    }

    public static void SwigDirector_CustomActionInterface_debugDraw(CustomActionInterface customActionInterface) {
        customActionInterface.debugDraw();
    }

    static {
        swig_module_init();
    }
}
