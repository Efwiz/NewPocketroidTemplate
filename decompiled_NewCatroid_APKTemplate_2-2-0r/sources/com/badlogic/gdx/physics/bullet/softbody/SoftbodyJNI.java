package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.RayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmConstructionInfo;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectConstArray;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectFloatData;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvt;
import com.badlogic.gdx.physics.bullet.collision.btDbvtNode;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConstructionInfo;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btGjkEpaSolver2;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBodyFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3FloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btScalarArray;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3Array;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class SoftbodyJNI {
    public static final native Matrix3 Add(Matrix3 matrix3, Matrix3 matrix4);

    public static final native Matrix3 AngularImpulseMatrix(Matrix3 matrix3, Matrix3 matrix4);

    public static final native void ApplyClampedForce(long j, btSoftBody.Node node, Vector3 vector3, float f);

    public static final native float AreaOf(Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native Vector3 BaryCoord(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native Vector3 CenterOf(long j, btSoftBody.Face face);

    public static final native Vector3 Clamp(Vector3 vector3, float f);

    public static final native float ClusterMetric(Vector3 vector3, Vector3 vector4);

    public static final native Matrix3 Cross(Vector3 vector3);

    public static final native Matrix3 Diagonal(float f);

    public static final native void EvaluateMedium(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, long j2, btSoftBody.sMedium smedium);

    public static final native float ImplicitSolve__SWIG_0(long j, btSoftBody.ImplicitFn implicitFn, Vector3 vector3, Vector3 vector4, float f, int i);

    public static final native float ImplicitSolve__SWIG_1(long j, btSoftBody.ImplicitFn implicitFn, Vector3 vector3, Vector3 vector4, float f);

    public static final native Matrix3 ImpulseMatrix__SWIG_0(float f, float f2, float f3, Matrix3 matrix3, Vector3 vector3);

    public static final native Matrix3 ImpulseMatrix__SWIG_1(float f, Matrix3 matrix3, Vector3 vector3, float f2, Matrix3 matrix4, Vector3 vector4);

    public static final native Matrix3 Lerp(Matrix3 matrix3, Matrix3 matrix4, float f);

    public static final native Matrix3 MassMatrix(float f, Matrix3 matrix3, Vector3 vector3);

    public static final native int MatchEdge(long j, btSoftBody.Node node, long j2, btSoftBody.Node node2, long j3, btSoftBody.Node node3, long j4, btSoftBody.Node node4);

    public static final native Matrix3 Mul(Matrix3 matrix3, float f);

    public static final native Vector3 NormalizeAny(Vector3 vector3);

    public static final native void Orthogonalize(Matrix3 matrix3);

    public static final native int PolarDecompose(Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5);

    public static final native Vector3 ProjectOnAxis(Vector3 vector3, Vector3 vector4);

    public static final native Vector3 ProjectOnPlane(Vector3 vector3, Vector3 vector4);

    public static final native void ProjectOrigin__SWIG_0(Vector3 vector3, Vector3 vector4, Vector3 vector5, long j);

    public static final native void ProjectOrigin__SWIG_1(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, long j);

    public static final native Matrix3 ScaleAlongAxis(Vector3 vector3, float f);

    public static final native float SoftBodyClusterData_adamping_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_adamping_set(long j, SoftBodyClusterData softBodyClusterData, float f);

    public static final native long SoftBodyClusterData_av_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_av_set(long j, SoftBodyClusterData softBodyClusterData, long j2, btVector3FloatData btvector3floatdata);

    public static final native int SoftBodyClusterData_clusterIndex_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_clusterIndex_set(long j, SoftBodyClusterData softBodyClusterData, int i);

    public static final native int SoftBodyClusterData_collide_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_collide_set(long j, SoftBodyClusterData softBodyClusterData, int i);

    public static final native long SoftBodyClusterData_com_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_com_set(long j, SoftBodyClusterData softBodyClusterData, long j2, btVector3FloatData btvector3floatdata);

    public static final native int SoftBodyClusterData_containsAnchor_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_containsAnchor_set(long j, SoftBodyClusterData softBodyClusterData, int i);

    public static final native long SoftBodyClusterData_dimpulses_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_dimpulses_set(long j, SoftBodyClusterData softBodyClusterData, long j2, btVector3FloatData btvector3floatdata);

    public static final native long SoftBodyClusterData_framerefs_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_framerefs_set(long j, SoftBodyClusterData softBodyClusterData, long j2, btVector3FloatData btvector3floatdata);

    public static final native long SoftBodyClusterData_framexform_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_framexform_set(long j, SoftBodyClusterData softBodyClusterData, long j2, btTransformFloatData bttransformfloatdata);

    public static final native float SoftBodyClusterData_idmass_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_idmass_set(long j, SoftBodyClusterData softBodyClusterData, float f);

    public static final native float SoftBodyClusterData_imass_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_imass_set(long j, SoftBodyClusterData softBodyClusterData, float f);

    public static final native long SoftBodyClusterData_invwi_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_invwi_set(long j, SoftBodyClusterData softBodyClusterData, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native float SoftBodyClusterData_ldamping_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_ldamping_set(long j, SoftBodyClusterData softBodyClusterData, float f);

    public static final native long SoftBodyClusterData_locii_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_locii_set(long j, SoftBodyClusterData softBodyClusterData, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native long SoftBodyClusterData_lv_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_lv_set(long j, SoftBodyClusterData softBodyClusterData, long j2, btVector3FloatData btvector3floatdata);

    public static final native FloatBuffer SoftBodyClusterData_masses_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_masses_set(long j, SoftBodyClusterData softBodyClusterData, FloatBuffer floatBuffer);

    public static final native float SoftBodyClusterData_matching_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_matching_set(long j, SoftBodyClusterData softBodyClusterData, float f);

    public static final native float SoftBodyClusterData_maxSelfCollisionImpulse_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_maxSelfCollisionImpulse_set(long j, SoftBodyClusterData softBodyClusterData, float f);

    public static final native float SoftBodyClusterData_ndamping_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_ndamping_set(long j, SoftBodyClusterData softBodyClusterData, float f);

    public static final native int SoftBodyClusterData_ndimpulses_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_ndimpulses_set(long j, SoftBodyClusterData softBodyClusterData, int i);

    public static final native IntBuffer SoftBodyClusterData_nodeIndices_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_nodeIndices_set(long j, SoftBodyClusterData softBodyClusterData, IntBuffer intBuffer);

    public static final native int SoftBodyClusterData_numFrameRefs_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_numFrameRefs_set(long j, SoftBodyClusterData softBodyClusterData, int i);

    public static final native int SoftBodyClusterData_numMasses_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_numMasses_set(long j, SoftBodyClusterData softBodyClusterData, int i);

    public static final native int SoftBodyClusterData_numNodes_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_numNodes_set(long j, SoftBodyClusterData softBodyClusterData, int i);

    public static final native int SoftBodyClusterData_nvimpulses_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_nvimpulses_set(long j, SoftBodyClusterData softBodyClusterData, int i);

    public static final native float SoftBodyClusterData_selfCollisionImpulseFactor_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_selfCollisionImpulseFactor_set(long j, SoftBodyClusterData softBodyClusterData, float f);

    public static final native long SoftBodyClusterData_vimpulses_get(long j, SoftBodyClusterData softBodyClusterData);

    public static final native void SoftBodyClusterData_vimpulses_set(long j, SoftBodyClusterData softBodyClusterData, long j2, btVector3FloatData btvector3floatdata);

    public static final native int SoftBodyConfigData_aeroModel_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_aeroModel_set(long j, SoftBodyConfigData softBodyConfigData, int i);

    public static final native float SoftBodyConfigData_anchorHardness_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_anchorHardness_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_baumgarte_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_baumgarte_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native int SoftBodyConfigData_clusterIterations_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_clusterIterations_set(long j, SoftBodyConfigData softBodyConfigData, int i);

    public static final native int SoftBodyConfigData_collisionFlags_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_collisionFlags_set(long j, SoftBodyConfigData softBodyConfigData, int i);

    public static final native float SoftBodyConfigData_damping_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_damping_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_drag_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_drag_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native int SoftBodyConfigData_driftIterations_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_driftIterations_set(long j, SoftBodyConfigData softBodyConfigData, int i);

    public static final native float SoftBodyConfigData_dynamicFriction_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_dynamicFriction_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_kineticContactHardness_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_kineticContactHardness_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_lift_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_lift_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_maxVolume_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_maxVolume_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_poseMatch_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_poseMatch_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native int SoftBodyConfigData_positionIterations_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_positionIterations_set(long j, SoftBodyConfigData softBodyConfigData, int i);

    public static final native float SoftBodyConfigData_pressure_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_pressure_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_rigidContactHardness_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_rigidContactHardness_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_softContactHardness_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_softContactHardness_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_softKineticClusterHardness_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_softKineticClusterHardness_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_softKineticClusterImpulseSplit_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_softKineticClusterImpulseSplit_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_softRigidClusterHardness_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_softRigidClusterHardness_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_softRigidClusterImpulseSplit_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_softRigidClusterImpulseSplit_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_softSoftClusterHardness_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_softSoftClusterHardness_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_softSoftClusterImpulseSplit_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_softSoftClusterImpulseSplit_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native float SoftBodyConfigData_timeScale_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_timeScale_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native int SoftBodyConfigData_velocityIterations_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_velocityIterations_set(long j, SoftBodyConfigData softBodyConfigData, int i);

    public static final native float SoftBodyConfigData_volume_get(long j, SoftBodyConfigData softBodyConfigData);

    public static final native void SoftBodyConfigData_volume_set(long j, SoftBodyConfigData softBodyConfigData, float f);

    public static final native long SoftBodyFaceData_material_get(long j, SoftBodyFaceData softBodyFaceData);

    public static final native void SoftBodyFaceData_material_set(long j, SoftBodyFaceData softBodyFaceData, long j2, SoftBodyMaterialData softBodyMaterialData);

    public static final native int[] SoftBodyFaceData_nodeIndices_get(long j, SoftBodyFaceData softBodyFaceData);

    public static final native void SoftBodyFaceData_nodeIndices_set(long j, SoftBodyFaceData softBodyFaceData, int[] iArr);

    public static final native long SoftBodyFaceData_normal_get(long j, SoftBodyFaceData softBodyFaceData);

    public static final native void SoftBodyFaceData_normal_set(long j, SoftBodyFaceData softBodyFaceData, long j2, btVector3FloatData btvector3floatdata);

    public static final native float SoftBodyFaceData_restArea_get(long j, SoftBodyFaceData softBodyFaceData);

    public static final native void SoftBodyFaceData_restArea_set(long j, SoftBodyFaceData softBodyFaceData, float f);

    public static final native int SoftBodyLinkData_bbending_get(long j, SoftBodyLinkData softBodyLinkData);

    public static final native void SoftBodyLinkData_bbending_set(long j, SoftBodyLinkData softBodyLinkData, int i);

    public static final native long SoftBodyLinkData_material_get(long j, SoftBodyLinkData softBodyLinkData);

    public static final native void SoftBodyLinkData_material_set(long j, SoftBodyLinkData softBodyLinkData, long j2, SoftBodyMaterialData softBodyMaterialData);

    public static final native int[] SoftBodyLinkData_nodeIndices_get(long j, SoftBodyLinkData softBodyLinkData);

    public static final native void SoftBodyLinkData_nodeIndices_set(long j, SoftBodyLinkData softBodyLinkData, int[] iArr);

    public static final native float SoftBodyLinkData_restLength_get(long j, SoftBodyLinkData softBodyLinkData);

    public static final native void SoftBodyLinkData_restLength_set(long j, SoftBodyLinkData softBodyLinkData, float f);

    public static final native float SoftBodyMaterialData_angularStiffness_get(long j, SoftBodyMaterialData softBodyMaterialData);

    public static final native void SoftBodyMaterialData_angularStiffness_set(long j, SoftBodyMaterialData softBodyMaterialData, float f);

    public static final native int SoftBodyMaterialData_flags_get(long j, SoftBodyMaterialData softBodyMaterialData);

    public static final native void SoftBodyMaterialData_flags_set(long j, SoftBodyMaterialData softBodyMaterialData, int i);

    public static final native float SoftBodyMaterialData_linearStiffness_get(long j, SoftBodyMaterialData softBodyMaterialData);

    public static final native void SoftBodyMaterialData_linearStiffness_set(long j, SoftBodyMaterialData softBodyMaterialData, float f);

    public static final native float SoftBodyMaterialData_volumeStiffness_get(long j, SoftBodyMaterialData softBodyMaterialData);

    public static final native void SoftBodyMaterialData_volumeStiffness_set(long j, SoftBodyMaterialData softBodyMaterialData, float f);

    public static final native long SoftBodyNodeData_accumulatedForce_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_accumulatedForce_set(long j, SoftBodyNodeData softBodyNodeData, long j2, btVector3FloatData btvector3floatdata);

    public static final native float SoftBodyNodeData_area_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_area_set(long j, SoftBodyNodeData softBodyNodeData, float f);

    public static final native int SoftBodyNodeData_attach_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_attach_set(long j, SoftBodyNodeData softBodyNodeData, int i);

    public static final native float SoftBodyNodeData_inverseMass_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_inverseMass_set(long j, SoftBodyNodeData softBodyNodeData, float f);

    public static final native long SoftBodyNodeData_material_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_material_set(long j, SoftBodyNodeData softBodyNodeData, long j2, SoftBodyMaterialData softBodyMaterialData);

    public static final native long SoftBodyNodeData_normal_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_normal_set(long j, SoftBodyNodeData softBodyNodeData, long j2, btVector3FloatData btvector3floatdata);

    public static final native int SoftBodyNodeData_pad_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_pad_set(long j, SoftBodyNodeData softBodyNodeData, int i);

    public static final native long SoftBodyNodeData_position_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_position_set(long j, SoftBodyNodeData softBodyNodeData, long j2, btVector3FloatData btvector3floatdata);

    public static final native long SoftBodyNodeData_previousPosition_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_previousPosition_set(long j, SoftBodyNodeData softBodyNodeData, long j2, btVector3FloatData btvector3floatdata);

    public static final native long SoftBodyNodeData_velocity_get(long j, SoftBodyNodeData softBodyNodeData);

    public static final native void SoftBodyNodeData_velocity_set(long j, SoftBodyNodeData softBodyNodeData, long j2, btVector3FloatData btvector3floatdata);

    public static final native long SoftBodyPoseData_aqq_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_aqq_set(long j, SoftBodyPoseData softBodyPoseData, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native int SoftBodyPoseData_bframe_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_bframe_set(long j, SoftBodyPoseData softBodyPoseData, int i);

    public static final native int SoftBodyPoseData_bvolume_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_bvolume_set(long j, SoftBodyPoseData softBodyPoseData, int i);

    public static final native long SoftBodyPoseData_com_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_com_set(long j, SoftBodyPoseData softBodyPoseData, long j2, btVector3FloatData btvector3floatdata);

    public static final native int SoftBodyPoseData_numPositions_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_numPositions_set(long j, SoftBodyPoseData softBodyPoseData, int i);

    public static final native int SoftBodyPoseData_numWeigts_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_numWeigts_set(long j, SoftBodyPoseData softBodyPoseData, int i);

    public static final native int SoftBodyPoseData_pad_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_pad_set(long j, SoftBodyPoseData softBodyPoseData, int i);

    public static final native long SoftBodyPoseData_positions_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_positions_set(long j, SoftBodyPoseData softBodyPoseData, long j2, btVector3FloatData btvector3floatdata);

    public static final native float SoftBodyPoseData_restVolume_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_restVolume_set(long j, SoftBodyPoseData softBodyPoseData, float f);

    public static final native long SoftBodyPoseData_rot_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_rot_set(long j, SoftBodyPoseData softBodyPoseData, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native long SoftBodyPoseData_scale_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_scale_set(long j, SoftBodyPoseData softBodyPoseData, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native FloatBuffer SoftBodyPoseData_weights_get(long j, SoftBodyPoseData softBodyPoseData);

    public static final native void SoftBodyPoseData_weights_set(long j, SoftBodyPoseData softBodyPoseData, FloatBuffer floatBuffer);

    public static final native long SoftBodyTetraData_c0_get(long j, SoftBodyTetraData softBodyTetraData);

    public static final native void SoftBodyTetraData_c0_set(long j, SoftBodyTetraData softBodyTetraData, long j2, btVector3FloatData btvector3floatdata);

    public static final native float SoftBodyTetraData_c1_get(long j, SoftBodyTetraData softBodyTetraData);

    public static final native void SoftBodyTetraData_c1_set(long j, SoftBodyTetraData softBodyTetraData, float f);

    public static final native float SoftBodyTetraData_c2_get(long j, SoftBodyTetraData softBodyTetraData);

    public static final native void SoftBodyTetraData_c2_set(long j, SoftBodyTetraData softBodyTetraData, float f);

    public static final native long SoftBodyTetraData_material_get(long j, SoftBodyTetraData softBodyTetraData);

    public static final native void SoftBodyTetraData_material_set(long j, SoftBodyTetraData softBodyTetraData, long j2, SoftBodyMaterialData softBodyMaterialData);

    public static final native int[] SoftBodyTetraData_nodeIndices_get(long j, SoftBodyTetraData softBodyTetraData);

    public static final native void SoftBodyTetraData_nodeIndices_set(long j, SoftBodyTetraData softBodyTetraData, int[] iArr);

    public static final native int SoftBodyTetraData_pad_get(long j, SoftBodyTetraData softBodyTetraData);

    public static final native void SoftBodyTetraData_pad_set(long j, SoftBodyTetraData softBodyTetraData, int i);

    public static final native float SoftBodyTetraData_restVolume_get(long j, SoftBodyTetraData softBodyTetraData);

    public static final native void SoftBodyTetraData_restVolume_set(long j, SoftBodyTetraData softBodyTetraData, float f);

    public static final native long SoftRigidAnchorData_c0_get(long j, SoftRigidAnchorData softRigidAnchorData);

    public static final native void SoftRigidAnchorData_c0_set(long j, SoftRigidAnchorData softRigidAnchorData, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native long SoftRigidAnchorData_c1_get(long j, SoftRigidAnchorData softRigidAnchorData);

    public static final native void SoftRigidAnchorData_c1_set(long j, SoftRigidAnchorData softRigidAnchorData, long j2, btVector3FloatData btvector3floatdata);

    public static final native float SoftRigidAnchorData_c2_get(long j, SoftRigidAnchorData softRigidAnchorData);

    public static final native void SoftRigidAnchorData_c2_set(long j, SoftRigidAnchorData softRigidAnchorData, float f);

    public static final native long SoftRigidAnchorData_localFrame_get(long j, SoftRigidAnchorData softRigidAnchorData);

    public static final native void SoftRigidAnchorData_localFrame_set(long j, SoftRigidAnchorData softRigidAnchorData, long j2, btVector3FloatData btvector3floatdata);

    public static final native int SoftRigidAnchorData_nodeIndex_get(long j, SoftRigidAnchorData softRigidAnchorData);

    public static final native void SoftRigidAnchorData_nodeIndex_set(long j, SoftRigidAnchorData softRigidAnchorData, int i);

    public static final native long SoftRigidAnchorData_rigidBody_get(long j, SoftRigidAnchorData softRigidAnchorData);

    public static final native void SoftRigidAnchorData_rigidBody_set(long j, SoftRigidAnchorData softRigidAnchorData, long j2, btRigidBodyFloatData btrigidbodyfloatdata);

    public static final native Matrix3 Sub(Matrix3 matrix3, Matrix3 matrix4);

    public static final native long VolumeOf__SWIG_0(long j, btSoftBody.Face face, float f);

    public static final native float VolumeOf__SWIG_1(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native long btCPUVertexBufferDescriptor_SWIGUpcast(long j);

    public static final native FloatBuffer btCPUVertexBufferDescriptor_getBasePointer(long j, btCPUVertexBufferDescriptor btcpuvertexbufferdescriptor);

    public static final native long btDefaultSoftBodySolver_SWIGUpcast(long j);

    public static final native void btDefaultSoftBodySolver_copyBackToSoftBodies__SWIG_0(long j, btDefaultSoftBodySolver btdefaultsoftbodysolver, boolean z);

    public static final native void btDefaultSoftBodySolver_copyBackToSoftBodies__SWIG_1(long j, btDefaultSoftBodySolver btdefaultsoftbodysolver);

    public static final native void btDefaultSoftBodySolver_copySoftBodyToVertexBuffer(long j, btDefaultSoftBodySolver btdefaultsoftbodysolver, long j2, btSoftBody btsoftbody, long j3, btVertexBufferDescriptor btvertexbufferdescriptor);

    public static final native void btDefaultSoftBodySolver_optimize__SWIG_0(long j, btDefaultSoftBodySolver btdefaultsoftbodysolver, long j2, boolean z);

    public static final native void btDefaultSoftBodySolver_optimize__SWIG_1(long j, btDefaultSoftBodySolver btdefaultsoftbodysolver, long j2);

    public static final native void btDefaultSoftBodySolver_processCollision__SWIG_0(long j, btDefaultSoftBodySolver btdefaultsoftbodysolver, long j2, btSoftBody btsoftbody, long j3, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btDefaultSoftBodySolver_processCollision__SWIG_1(long j, btDefaultSoftBodySolver btdefaultsoftbodysolver, long j2, btSoftBody btsoftbody, long j3, btSoftBody btsoftbody2);

    public static final native int btEigen_system__SWIG_0(Matrix3 matrix3, long j, btMatrix3x3 btmatrix3x3, long j2, btVector3 btvector3);

    public static final native int btEigen_system__SWIG_1(Matrix3 matrix3, long j, btMatrix3x3 btmatrix3x3);

    public static final native long btSoftBodyCollisionShape_SWIGUpcast(long j);

    public static final native long btSoftBodyCollisionShape_body_get(long j, btSoftBodyCollisionShape btsoftbodycollisionshape);

    public static final native void btSoftBodyCollisionShape_body_set(long j, btSoftBodyCollisionShape btsoftbodycollisionshape, long j2, btSoftBody btsoftbody);

    public static final native long btSoftBodyConcaveCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btSoftBodyConcaveCollisionAlgorithm_SWIGUpcast(long j);

    public static final native long btSoftBodyConcaveCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(long j);

    public static final native void btSoftBodyConcaveCollisionAlgorithm_clearCache(long j, btSoftBodyConcaveCollisionAlgorithm btsoftbodyconcavecollisionalgorithm);

    public static final native long btSoftBodyFloatData_anchors_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_anchors_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, SoftRigidAnchorData softRigidAnchorData);

    public static final native long btSoftBodyFloatData_clusters_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_clusters_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, SoftBodyClusterData softBodyClusterData);

    public static final native long btSoftBodyFloatData_collisionObjectData_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_collisionObjectData_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native long btSoftBodyFloatData_config_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_config_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, SoftBodyConfigData softBodyConfigData);

    public static final native long btSoftBodyFloatData_faces_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_faces_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, SoftBodyFaceData softBodyFaceData);

    public static final native long btSoftBodyFloatData_joints_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_joints_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, btSoftBodyJointData btsoftbodyjointdata);

    public static final native long btSoftBodyFloatData_links_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_links_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, SoftBodyLinkData softBodyLinkData);

    public static final native long btSoftBodyFloatData_materials_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_materials_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2);

    public static final native long btSoftBodyFloatData_nodes_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_nodes_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, SoftBodyNodeData softBodyNodeData);

    public static final native int btSoftBodyFloatData_numAnchors_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_numAnchors_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, int i);

    public static final native int btSoftBodyFloatData_numClusters_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_numClusters_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, int i);

    public static final native int btSoftBodyFloatData_numFaces_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_numFaces_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, int i);

    public static final native int btSoftBodyFloatData_numJoints_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_numJoints_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, int i);

    public static final native int btSoftBodyFloatData_numLinks_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_numLinks_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, int i);

    public static final native int btSoftBodyFloatData_numMaterials_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_numMaterials_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, int i);

    public static final native int btSoftBodyFloatData_numNodes_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_numNodes_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, int i);

    public static final native int btSoftBodyFloatData_numTetrahedra_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_numTetrahedra_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, int i);

    public static final native long btSoftBodyFloatData_pose_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_pose_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, SoftBodyPoseData softBodyPoseData);

    public static final native long btSoftBodyFloatData_tetrahedra_get(long j, btSoftBodyFloatData btsoftbodyfloatdata);

    public static final native void btSoftBodyFloatData_tetrahedra_set(long j, btSoftBodyFloatData btsoftbodyfloatdata, long j2, SoftBodyTetraData softBodyTetraData);

    public static final native float btSoftBodyHelpers_CalculateUV(int i, int i2, int i3, int i4, int i5);

    public static final native long btSoftBodyHelpers_CreateEllipsoid(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, int i);

    public static final native long btSoftBodyHelpers_CreateFromConvexHull__SWIG_0(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, long j2, btVector3 btvector3, int i, boolean z);

    public static final native long btSoftBodyHelpers_CreateFromConvexHull__SWIG_1(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, long j2, btVector3 btvector3, int i);

    public static final native long btSoftBodyHelpers_CreateFromTetGenData(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, String str, String str2, String str3, boolean z, boolean z2, boolean z3);

    public static final native long btSoftBodyHelpers_CreateFromTriMesh__SWIG_0(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, FloatBuffer floatBuffer, IntBuffer intBuffer, int i, boolean z);

    public static final native long btSoftBodyHelpers_CreateFromTriMesh__SWIG_1(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, FloatBuffer floatBuffer, IntBuffer intBuffer, int i);

    public static final native long btSoftBodyHelpers_CreatePatch(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, int i, int i2, int i3, boolean z);

    public static final native long btSoftBodyHelpers_CreatePatchUV__SWIG_0(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, int i, int i2, int i3, boolean z, FloatBuffer floatBuffer);

    public static final native long btSoftBodyHelpers_CreatePatchUV__SWIG_1(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, int i, int i2, int i3, boolean z);

    public static final native long btSoftBodyHelpers_CreateRope(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, int i, int i2);

    public static final native void btSoftBodyHelpers_DrawClusterTree__SWIG_0(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw, int i, int i2);

    public static final native void btSoftBodyHelpers_DrawClusterTree__SWIG_1(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw, int i);

    public static final native void btSoftBodyHelpers_DrawClusterTree__SWIG_2(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw);

    public static final native void btSoftBodyHelpers_DrawFaceTree__SWIG_0(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw, int i, int i2);

    public static final native void btSoftBodyHelpers_DrawFaceTree__SWIG_1(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw, int i);

    public static final native void btSoftBodyHelpers_DrawFaceTree__SWIG_2(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw);

    public static final native void btSoftBodyHelpers_DrawFrame(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw);

    public static final native void btSoftBodyHelpers_DrawInfos(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw, boolean z, boolean z2, boolean z3);

    public static final native void btSoftBodyHelpers_DrawNodeTree__SWIG_0(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw, int i, int i2);

    public static final native void btSoftBodyHelpers_DrawNodeTree__SWIG_1(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw, int i);

    public static final native void btSoftBodyHelpers_DrawNodeTree__SWIG_2(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw);

    public static final native void btSoftBodyHelpers_Draw__SWIG_0(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw, int i);

    public static final native void btSoftBodyHelpers_Draw__SWIG_1(long j, btSoftBody btsoftbody, long j2, btIDebugDraw btidebugdraw);

    public static final native void btSoftBodyHelpers_ReoptimizeLinkOrder(long j, btSoftBody btsoftbody);

    public static final native long btSoftBodyJointData_bodyA_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_bodyA_set(long j, btSoftBodyJointData btsoftbodyjointdata, long j2);

    public static final native int btSoftBodyJointData_bodyAtype_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_bodyAtype_set(long j, btSoftBodyJointData btsoftbodyjointdata, int i);

    public static final native long btSoftBodyJointData_bodyB_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_bodyB_set(long j, btSoftBodyJointData btsoftbodyjointdata, long j2);

    public static final native int btSoftBodyJointData_bodyBtype_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_bodyBtype_set(long j, btSoftBodyJointData btsoftbodyjointdata, int i);

    public static final native float btSoftBodyJointData_cfm_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_cfm_set(long j, btSoftBodyJointData btsoftbodyjointdata, float f);

    public static final native int btSoftBodyJointData_delete_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_delete_set(long j, btSoftBodyJointData btsoftbodyjointdata, int i);

    public static final native float btSoftBodyJointData_erp_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_erp_set(long j, btSoftBodyJointData btsoftbodyjointdata, float f);

    public static final native int btSoftBodyJointData_jointType_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_jointType_set(long j, btSoftBodyJointData btsoftbodyjointdata, int i);

    public static final native int btSoftBodyJointData_pad_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_pad_set(long j, btSoftBodyJointData btsoftbodyjointdata, int i);

    public static final native long btSoftBodyJointData_refs_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_refs_set(long j, btSoftBodyJointData btsoftbodyjointdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btSoftBodyJointData_relPosition_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_relPosition_set(long j, btSoftBodyJointData btsoftbodyjointdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native float btSoftBodyJointData_split_get(long j, btSoftBodyJointData btsoftbodyjointdata);

    public static final native void btSoftBodyJointData_split_set(long j, btSoftBodyJointData btsoftbodyjointdata, float f);

    public static final native long btSoftBodyRigidBodyCollisionConfiguration_SWIGUpcast(long j);

    public static final native void btSoftBodySolverOutput_copySoftBodyToVertexBuffer(long j, btSoftBodySolverOutput btsoftbodysolveroutput, long j2, btSoftBody btsoftbody, long j3, btVertexBufferDescriptor btvertexbufferdescriptor);

    public static final native boolean btSoftBodySolver_checkInitialized(long j, btSoftBodySolver btsoftbodysolver);

    public static final native void btSoftBodySolver_copyBackToSoftBodies__SWIG_0(long j, btSoftBodySolver btsoftbodysolver, boolean z);

    public static final native void btSoftBodySolver_copyBackToSoftBodies__SWIG_1(long j, btSoftBodySolver btsoftbodysolver);

    public static final native int btSoftBodySolver_getNumberOfPositionIterations(long j, btSoftBodySolver btsoftbodysolver);

    public static final native int btSoftBodySolver_getNumberOfVelocityIterations(long j, btSoftBodySolver btsoftbodysolver);

    public static final native int btSoftBodySolver_getSolverType(long j, btSoftBodySolver btsoftbodysolver);

    public static final native float btSoftBodySolver_getTimeScale(long j, btSoftBodySolver btsoftbodysolver);

    public static final native void btSoftBodySolver_optimize__SWIG_0(long j, btSoftBodySolver btsoftbodysolver, long j2, boolean z);

    public static final native void btSoftBodySolver_optimize__SWIG_1(long j, btSoftBodySolver btsoftbodysolver, long j2);

    public static final native void btSoftBodySolver_predictMotion(long j, btSoftBodySolver btsoftbodysolver, float f);

    public static final native void btSoftBodySolver_processCollision__SWIG_0(long j, btSoftBodySolver btsoftbodysolver, long j2, btSoftBody btsoftbody, long j3, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btSoftBodySolver_processCollision__SWIG_1(long j, btSoftBodySolver btsoftbodysolver, long j2, btSoftBody btsoftbody, long j3, btSoftBody btsoftbody2);

    public static final native void btSoftBodySolver_setNumberOfPositionIterations(long j, btSoftBodySolver btsoftbodysolver, int i);

    public static final native void btSoftBodySolver_setNumberOfVelocityIterations(long j, btSoftBodySolver btsoftbodysolver, int i);

    public static final native void btSoftBodySolver_solveConstraints(long j, btSoftBodySolver btsoftbodysolver, float f);

    public static final native void btSoftBodySolver_updateSoftBodies(long j, btSoftBodySolver btsoftbodysolver);

    public static final native long btSoftBodyTriangleCallback_SWIGUpcast(long j);

    public static final native void btSoftBodyTriangleCallback_clearCache(long j, btSoftBodyTriangleCallback btsoftbodytrianglecallback);

    public static final native Vector3 btSoftBodyTriangleCallback_getAabbMax(long j, btSoftBodyTriangleCallback btsoftbodytrianglecallback);

    public static final native Vector3 btSoftBodyTriangleCallback_getAabbMin(long j, btSoftBodyTriangleCallback btsoftbodytrianglecallback);

    public static final native void btSoftBodyTriangleCallback_setTimeStepAndCounters(long j, btSoftBodyTriangleCallback btsoftbodytrianglecallback, float f, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btDispatcherInfo btdispatcherinfo, long j4, btManifoldResult btmanifoldresult);

    public static final native int btSoftBodyTriangleCallback_triangleCount_get(long j, btSoftBodyTriangleCallback btsoftbodytrianglecallback);

    public static final native void btSoftBodyTriangleCallback_triangleCount_set(long j, btSoftBodyTriangleCallback btsoftbodytrianglecallback, int i);

    public static final native float btSoftBodyWorldInfo_air_density_get(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native void btSoftBodyWorldInfo_air_density_set(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, float f);

    public static final native long btSoftBodyWorldInfo_broadphase_get(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native void btSoftBodyWorldInfo_broadphase_set(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, long j2, btBroadphaseInterface btbroadphaseinterface);

    public static final native long btSoftBodyWorldInfo_dispatcher_get(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native void btSoftBodyWorldInfo_dispatcher_set(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, long j2, btDispatcher btdispatcher);

    public static final native long btSoftBodyWorldInfo_gravity_get(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native void btSoftBodyWorldInfo_gravity_set(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, long j2, btVector3 btvector3);

    public static final native float btSoftBodyWorldInfo_maxDisplacement_get(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native void btSoftBodyWorldInfo_maxDisplacement_set(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, float f);

    public static final native long btSoftBodyWorldInfo_sparsesdf_get(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native void btSoftBodyWorldInfo_sparsesdf_set(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, long j2, btSparseSdf3 btsparsesdf3);

    public static final native float btSoftBodyWorldInfo_water_density_get(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native void btSoftBodyWorldInfo_water_density_set(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, float f);

    public static final native long btSoftBodyWorldInfo_water_normal_get(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native void btSoftBodyWorldInfo_water_normal_set(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, long j2, btVector3 btvector3);

    public static final native float btSoftBodyWorldInfo_water_offset_get(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native void btSoftBodyWorldInfo_water_offset_set(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, float f);

    public static final native long btSoftBody_AJoint_IControl_Default();

    public static final native void btSoftBody_AJoint_IControl_Prepare(long j, btSoftBody.AJoint.IControl iControl, long j2, btSoftBody.AJoint aJoint);

    public static final native float btSoftBody_AJoint_IControl_Speed(long j, btSoftBody.AJoint.IControl iControl, long j2, btSoftBody.AJoint aJoint, float f);

    public static final native long btSoftBody_AJoint_SWIGUpcast(long j);

    public static final native long btSoftBody_AJoint_Specs_SWIGUpcast(long j);

    public static final native long btSoftBody_AJoint_Specs_axis_get(long j, btSoftBody.AJoint.Specs specs);

    public static final native void btSoftBody_AJoint_Specs_axis_set(long j, btSoftBody.AJoint.Specs specs, long j2, btVector3 btvector3);

    public static final native long btSoftBody_AJoint_Specs_icontrol_get(long j, btSoftBody.AJoint.Specs specs);

    public static final native void btSoftBody_AJoint_Specs_icontrol_set(long j, btSoftBody.AJoint.Specs specs, long j2, btSoftBody.AJoint.IControl iControl);

    public static final native long btSoftBody_AJoint_axis_get(long j, btSoftBody.AJoint aJoint);

    public static final native void btSoftBody_AJoint_axis_set(long j, btSoftBody.AJoint aJoint, long j2, btVector3 btvector3);

    public static final native long btSoftBody_AJoint_icontrol_get(long j, btSoftBody.AJoint aJoint);

    public static final native void btSoftBody_AJoint_icontrol_set(long j, btSoftBody.AJoint aJoint, long j2, btSoftBody.AJoint.IControl iControl);

    public static final native long btSoftBody_Anchor_body_get(long j, btSoftBody.Anchor anchor);

    public static final native void btSoftBody_Anchor_body_set(long j, btSoftBody.Anchor anchor, long j2, btRigidBody btrigidbody);

    public static final native long btSoftBody_Anchor_c0_get(long j, btSoftBody.Anchor anchor);

    public static final native void btSoftBody_Anchor_c0_set(long j, btSoftBody.Anchor anchor, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long btSoftBody_Anchor_c1_get(long j, btSoftBody.Anchor anchor);

    public static final native void btSoftBody_Anchor_c1_set(long j, btSoftBody.Anchor anchor, long j2, btVector3 btvector3);

    public static final native float btSoftBody_Anchor_c2_get(long j, btSoftBody.Anchor anchor);

    public static final native void btSoftBody_Anchor_c2_set(long j, btSoftBody.Anchor anchor, float f);

    public static final native float btSoftBody_Anchor_influence_get(long j, btSoftBody.Anchor anchor);

    public static final native void btSoftBody_Anchor_influence_set(long j, btSoftBody.Anchor anchor, float f);

    public static final native long btSoftBody_Anchor_local_get(long j, btSoftBody.Anchor anchor);

    public static final native void btSoftBody_Anchor_local_set(long j, btSoftBody.Anchor anchor, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Anchor_node_get(long j, btSoftBody.Anchor anchor);

    public static final native void btSoftBody_Anchor_node_set(long j, btSoftBody.Anchor anchor, long j2, btSoftBody.Node node);

    public static final native void btSoftBody_Body_activate(long j, btSoftBody.Body body);

    public static final native Vector3 btSoftBody_Body_angularVelocity__SWIG_0(long j, btSoftBody.Body body, Vector3 vector3);

    public static final native Vector3 btSoftBody_Body_angularVelocity__SWIG_1(long j, btSoftBody.Body body);

    public static final native void btSoftBody_Body_applyAImpulse(long j, btSoftBody.Body body, long j2, btSoftBody.Impulse impulse);

    public static final native void btSoftBody_Body_applyDAImpulse(long j, btSoftBody.Body body, Vector3 vector3);

    public static final native void btSoftBody_Body_applyDCImpulse(long j, btSoftBody.Body body, Vector3 vector3);

    public static final native void btSoftBody_Body_applyDImpulse(long j, btSoftBody.Body body, Vector3 vector3, Vector3 vector4);

    public static final native void btSoftBody_Body_applyImpulse(long j, btSoftBody.Body body, long j2, btSoftBody.Impulse impulse, Vector3 vector3);

    public static final native void btSoftBody_Body_applyVAImpulse(long j, btSoftBody.Body body, Vector3 vector3);

    public static final native void btSoftBody_Body_applyVImpulse(long j, btSoftBody.Body body, Vector3 vector3, Vector3 vector4);

    public static final native long btSoftBody_Body_collisionObject_get(long j, btSoftBody.Body body);

    public static final native void btSoftBody_Body_collisionObject_set(long j, btSoftBody.Body body, long j2, btCollisionObject btcollisionobject);

    public static final native float btSoftBody_Body_invMass(long j, btSoftBody.Body body);

    public static final native Matrix3 btSoftBody_Body_invWorldInertia(long j, btSoftBody.Body body);

    public static final native Vector3 btSoftBody_Body_linearVelocity(long j, btSoftBody.Body body);

    public static final native long btSoftBody_Body_rigid_get(long j, btSoftBody.Body body);

    public static final native void btSoftBody_Body_rigid_set(long j, btSoftBody.Body body, long j2, btRigidBody btrigidbody);

    public static final native long btSoftBody_Body_soft_get(long j, btSoftBody.Body body);

    public static final native void btSoftBody_Body_soft_set(long j, btSoftBody.Body body, long j2, btSoftBody.Cluster cluster);

    public static final native Vector3 btSoftBody_Body_velocity(long j, btSoftBody.Body body, Vector3 vector3);

    public static final native Matrix4 btSoftBody_Body_xform(long j, btSoftBody.Body body);

    public static final native long btSoftBody_CJoint_SWIGUpcast(long j);

    public static final native float btSoftBody_CJoint_friction_get(long j, btSoftBody.CJoint cJoint);

    public static final native void btSoftBody_CJoint_friction_set(long j, btSoftBody.CJoint cJoint, float f);

    public static final native int btSoftBody_CJoint_life_get(long j, btSoftBody.CJoint cJoint);

    public static final native void btSoftBody_CJoint_life_set(long j, btSoftBody.CJoint cJoint, int i);

    public static final native int btSoftBody_CJoint_maxlife_get(long j, btSoftBody.CJoint cJoint);

    public static final native void btSoftBody_CJoint_maxlife_set(long j, btSoftBody.CJoint cJoint, int i);

    public static final native long btSoftBody_CJoint_normal_get(long j, btSoftBody.CJoint cJoint);

    public static final native void btSoftBody_CJoint_normal_set(long j, btSoftBody.CJoint cJoint, long j2, btVector3 btvector3);

    public static final native long btSoftBody_CJoint_rpos_get(long j, btSoftBody.CJoint cJoint);

    public static final native void btSoftBody_CJoint_rpos_set(long j, btSoftBody.CJoint cJoint, long j2, btVector3 btvector3);

    public static final native float btSoftBody_Cluster_adamping_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_adamping_set(long j, btSoftBody.Cluster cluster, float f);

    public static final native long btSoftBody_Cluster_av_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_av_set(long j, btSoftBody.Cluster cluster, long j2, btVector3 btvector3);

    public static final native int btSoftBody_Cluster_clusterIndex_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_clusterIndex_set(long j, btSoftBody.Cluster cluster, int i);

    public static final native boolean btSoftBody_Cluster_collide_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_collide_set(long j, btSoftBody.Cluster cluster, boolean z);

    public static final native long btSoftBody_Cluster_com_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_com_set(long j, btSoftBody.Cluster cluster, long j2, btVector3 btvector3);

    public static final native boolean btSoftBody_Cluster_containsAnchor_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_containsAnchor_set(long j, btSoftBody.Cluster cluster, boolean z);

    public static final native long btSoftBody_Cluster_dimpulses_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_dimpulses_set(long j, btSoftBody.Cluster cluster, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Cluster_framerefs_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_framerefs_set(long j, btSoftBody.Cluster cluster, long j2, btVector3Array btvector3array);

    public static final native long btSoftBody_Cluster_framexform_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_framexform_set(long j, btSoftBody.Cluster cluster, long j2, btTransform bttransform);

    public static final native float btSoftBody_Cluster_idmass_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_idmass_set(long j, btSoftBody.Cluster cluster, float f);

    public static final native float btSoftBody_Cluster_imass_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_imass_set(long j, btSoftBody.Cluster cluster, float f);

    public static final native long btSoftBody_Cluster_invwi_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_invwi_set(long j, btSoftBody.Cluster cluster, long j2, btMatrix3x3 btmatrix3x3);

    public static final native float btSoftBody_Cluster_ldamping_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_ldamping_set(long j, btSoftBody.Cluster cluster, float f);

    public static final native long btSoftBody_Cluster_leaf_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_leaf_set(long j, btSoftBody.Cluster cluster, long j2, btDbvtNode btdbvtnode);

    public static final native long btSoftBody_Cluster_locii_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_locii_set(long j, btSoftBody.Cluster cluster, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long btSoftBody_Cluster_lv_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_lv_set(long j, btSoftBody.Cluster cluster, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Cluster_masses_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_masses_set(long j, btSoftBody.Cluster cluster, long j2, btScalarArray btscalararray);

    public static final native float btSoftBody_Cluster_matching_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_matching_set(long j, btSoftBody.Cluster cluster, float f);

    public static final native float btSoftBody_Cluster_maxSelfCollisionImpulse_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_maxSelfCollisionImpulse_set(long j, btSoftBody.Cluster cluster, float f);

    public static final native float btSoftBody_Cluster_ndamping_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_ndamping_set(long j, btSoftBody.Cluster cluster, float f);

    public static final native int btSoftBody_Cluster_ndimpulses_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_ndimpulses_set(long j, btSoftBody.Cluster cluster, int i);

    public static final native long btSoftBody_Cluster_nodes_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_nodes_set(long j, btSoftBody.Cluster cluster, long j2);

    public static final native int btSoftBody_Cluster_nvimpulses_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_nvimpulses_set(long j, btSoftBody.Cluster cluster, int i);

    public static final native float btSoftBody_Cluster_selfCollisionImpulseFactor_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_selfCollisionImpulseFactor_set(long j, btSoftBody.Cluster cluster, float f);

    public static final native long btSoftBody_Cluster_vimpulses_get(long j, btSoftBody.Cluster cluster);

    public static final native void btSoftBody_Cluster_vimpulses_set(long j, btSoftBody.Cluster cluster, long j2, btVector3 btvector3);

    public static final native int btSoftBody_Config_aeromodel_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_aeromodel_set(long j, btSoftBody.Config config, int i);

    public static final native int btSoftBody_Config_citerations_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_citerations_set(long j, btSoftBody.Config config, int i);

    public static final native int btSoftBody_Config_collisions_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_collisions_set(long j, btSoftBody.Config config, int i);

    public static final native int btSoftBody_Config_diterations_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_diterations_set(long j, btSoftBody.Config config, int i);

    public static final native long btSoftBody_Config_dsequence_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_dsequence_set(long j, btSoftBody.Config config, long j2);

    public static final native float btSoftBody_Config_kAHR_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kAHR_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kCHR_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kCHR_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kDF_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kDF_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kDG_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kDG_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kDP_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kDP_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kKHR_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kKHR_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kLF_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kLF_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kMT_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kMT_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kPR_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kPR_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kSHR_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kSHR_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kSKHR_CL_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kSKHR_CL_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kSK_SPLT_CL_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kSK_SPLT_CL_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kSRHR_CL_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kSRHR_CL_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kSR_SPLT_CL_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kSR_SPLT_CL_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kSSHR_CL_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kSSHR_CL_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kSS_SPLT_CL_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kSS_SPLT_CL_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kVCF_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kVCF_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_kVC_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_kVC_set(long j, btSoftBody.Config config, float f);

    public static final native float btSoftBody_Config_maxvolume_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_maxvolume_set(long j, btSoftBody.Config config, float f);

    public static final native int btSoftBody_Config_piterations_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_piterations_set(long j, btSoftBody.Config config, int i);

    public static final native long btSoftBody_Config_psequence_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_psequence_set(long j, btSoftBody.Config config, long j2);

    public static final native float btSoftBody_Config_timescale_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_timescale_set(long j, btSoftBody.Config config, float f);

    public static final native int btSoftBody_Config_viterations_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_viterations_set(long j, btSoftBody.Config config, int i);

    public static final native long btSoftBody_Config_vsequence_get(long j, btSoftBody.Config config);

    public static final native void btSoftBody_Config_vsequence_set(long j, btSoftBody.Config config, long j2);

    public static final native long btSoftBody_Element_tag_get(long j, btSoftBody.Element element);

    public static final native void btSoftBody_Element_tag_set(long j, btSoftBody.Element element, long j2);

    public static final native long btSoftBody_Face_SWIGUpcast(long j);

    public static final native long btSoftBody_Face_leaf_get(long j, btSoftBody.Face face);

    public static final native void btSoftBody_Face_leaf_set(long j, btSoftBody.Face face, long j2, btDbvtNode btdbvtnode);

    public static final native long btSoftBody_Face_n_get(long j, btSoftBody.Face face);

    public static final native void btSoftBody_Face_n_set(long j, btSoftBody.Face face, long j2);

    public static final native long btSoftBody_Face_normal_get(long j, btSoftBody.Face face);

    public static final native void btSoftBody_Face_normal_set(long j, btSoftBody.Face face, long j2, btVector3 btvector3);

    public static final native float btSoftBody_Face_ra_get(long j, btSoftBody.Face face);

    public static final native void btSoftBody_Face_ra_set(long j, btSoftBody.Face face, float f);

    public static final native long btSoftBody_Feature_SWIGUpcast(long j);

    public static final native long btSoftBody_Feature_material_get(long j, btSoftBody.Feature feature);

    public static final native void btSoftBody_Feature_material_set(long j, btSoftBody.Feature feature, long j2, btSoftBody.Material material);

    public static final native float btSoftBody_ImplicitFn_Eval(long j, btSoftBody.ImplicitFn implicitFn, Vector3 vector3);

    public static final native int btSoftBody_Impulse_asDrift_get(long j, btSoftBody.Impulse impulse);

    public static final native void btSoftBody_Impulse_asDrift_set(long j, btSoftBody.Impulse impulse, int i);

    public static final native int btSoftBody_Impulse_asVelocity_get(long j, btSoftBody.Impulse impulse);

    public static final native void btSoftBody_Impulse_asVelocity_set(long j, btSoftBody.Impulse impulse, int i);

    public static final native long btSoftBody_Impulse_drift_get(long j, btSoftBody.Impulse impulse);

    public static final native void btSoftBody_Impulse_drift_set(long j, btSoftBody.Impulse impulse, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Impulse_operatorMultiplication(long j, btSoftBody.Impulse impulse, float f);

    public static final native long btSoftBody_Impulse_operatorSubtraction(long j, btSoftBody.Impulse impulse);

    public static final native long btSoftBody_Impulse_velocity_get(long j, btSoftBody.Impulse impulse);

    public static final native void btSoftBody_Impulse_velocity_set(long j, btSoftBody.Impulse impulse, long j2, btVector3 btvector3);

    public static final native void btSoftBody_Joint_Prepare(long j, btSoftBody.Joint joint, float f, int i);

    public static final native void btSoftBody_Joint_Solve(long j, btSoftBody.Joint joint, float f, float f2);

    public static final native float btSoftBody_Joint_Specs_cfm_get(long j, btSoftBody.Joint.Specs specs);

    public static final native void btSoftBody_Joint_Specs_cfm_set(long j, btSoftBody.Joint.Specs specs, float f);

    public static final native float btSoftBody_Joint_Specs_erp_get(long j, btSoftBody.Joint.Specs specs);

    public static final native void btSoftBody_Joint_Specs_erp_set(long j, btSoftBody.Joint.Specs specs, float f);

    public static final native float btSoftBody_Joint_Specs_split_get(long j, btSoftBody.Joint.Specs specs);

    public static final native void btSoftBody_Joint_Specs_split_set(long j, btSoftBody.Joint.Specs specs, float f);

    public static final native void btSoftBody_Joint_Terminate(long j, btSoftBody.Joint joint, float f);

    public static final native int btSoftBody_Joint_Type(long j, btSoftBody.Joint joint);

    public static final native long btSoftBody_Joint_bodies_get(long j, btSoftBody.Joint joint);

    public static final native void btSoftBody_Joint_bodies_set(long j, btSoftBody.Joint joint, long j2, btSoftBody.Body body);

    public static final native float btSoftBody_Joint_cfm_get(long j, btSoftBody.Joint joint);

    public static final native void btSoftBody_Joint_cfm_set(long j, btSoftBody.Joint joint, float f);

    public static final native boolean btSoftBody_Joint_delete_get(long j, btSoftBody.Joint joint);

    public static final native void btSoftBody_Joint_delete_set(long j, btSoftBody.Joint joint, boolean z);

    public static final native long btSoftBody_Joint_drift_get(long j, btSoftBody.Joint joint);

    public static final native void btSoftBody_Joint_drift_set(long j, btSoftBody.Joint joint, long j2, btVector3 btvector3);

    public static final native float btSoftBody_Joint_erp_get(long j, btSoftBody.Joint joint);

    public static final native void btSoftBody_Joint_erp_set(long j, btSoftBody.Joint joint, float f);

    public static final native long btSoftBody_Joint_massmatrix_get(long j, btSoftBody.Joint joint);

    public static final native void btSoftBody_Joint_massmatrix_set(long j, btSoftBody.Joint joint, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long btSoftBody_Joint_refs_get(long j, btSoftBody.Joint joint);

    public static final native void btSoftBody_Joint_refs_set(long j, btSoftBody.Joint joint, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Joint_sdrift_get(long j, btSoftBody.Joint joint);

    public static final native void btSoftBody_Joint_sdrift_set(long j, btSoftBody.Joint joint, long j2, btVector3 btvector3);

    public static final native float btSoftBody_Joint_split_get(long j, btSoftBody.Joint joint);

    public static final native void btSoftBody_Joint_split_set(long j, btSoftBody.Joint joint, float f);

    public static final native long btSoftBody_LJoint_SWIGUpcast(long j);

    public static final native long btSoftBody_LJoint_Specs_SWIGUpcast(long j);

    public static final native long btSoftBody_LJoint_Specs_position_get(long j, btSoftBody.LJoint.Specs specs);

    public static final native void btSoftBody_LJoint_Specs_position_set(long j, btSoftBody.LJoint.Specs specs, long j2, btVector3 btvector3);

    public static final native long btSoftBody_LJoint_rpos_get(long j, btSoftBody.LJoint lJoint);

    public static final native void btSoftBody_LJoint_rpos_set(long j, btSoftBody.LJoint lJoint, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Link_SWIGUpcast(long j);

    public static final native int btSoftBody_Link_bbending_get(long j, btSoftBody.Link link);

    public static final native void btSoftBody_Link_bbending_set(long j, btSoftBody.Link link, int i);

    public static final native float btSoftBody_Link_c0_get(long j, btSoftBody.Link link);

    public static final native void btSoftBody_Link_c0_set(long j, btSoftBody.Link link, float f);

    public static final native float btSoftBody_Link_c1_get(long j, btSoftBody.Link link);

    public static final native void btSoftBody_Link_c1_set(long j, btSoftBody.Link link, float f);

    public static final native float btSoftBody_Link_c2_get(long j, btSoftBody.Link link);

    public static final native void btSoftBody_Link_c2_set(long j, btSoftBody.Link link, float f);

    public static final native long btSoftBody_Link_c3_get(long j, btSoftBody.Link link);

    public static final native void btSoftBody_Link_c3_set(long j, btSoftBody.Link link, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Link_n_get(long j, btSoftBody.Link link);

    public static final native void btSoftBody_Link_n_set(long j, btSoftBody.Link link, long j2);

    public static final native void btSoftBody_Link_operatorDeleteArray__SWIG_0(long j, btSoftBody.Link link, long j2);

    public static final native void btSoftBody_Link_operatorDeleteArray__SWIG_1(long j, btSoftBody.Link link, long j2, long j3);

    public static final native void btSoftBody_Link_operatorDelete__SWIG_0(long j, btSoftBody.Link link, long j2);

    public static final native void btSoftBody_Link_operatorDelete__SWIG_1(long j, btSoftBody.Link link, long j2, long j3);

    public static final native long btSoftBody_Link_operatorNewArray__SWIG_0(long j, btSoftBody.Link link, long j2);

    public static final native long btSoftBody_Link_operatorNewArray__SWIG_1(long j, btSoftBody.Link link, long j2, long j3);

    public static final native long btSoftBody_Link_operatorNew__SWIG_0(long j, btSoftBody.Link link, long j2);

    public static final native long btSoftBody_Link_operatorNew__SWIG_1(long j, btSoftBody.Link link, long j2, long j3);

    public static final native float btSoftBody_Link_rl_get(long j, btSoftBody.Link link);

    public static final native void btSoftBody_Link_rl_set(long j, btSoftBody.Link link, float f);

    public static final native long btSoftBody_Material_SWIGUpcast(long j);

    public static final native int btSoftBody_Material_flags_get(long j, btSoftBody.Material material);

    public static final native void btSoftBody_Material_flags_set(long j, btSoftBody.Material material, int i);

    public static final native float btSoftBody_Material_kAST_get(long j, btSoftBody.Material material);

    public static final native void btSoftBody_Material_kAST_set(long j, btSoftBody.Material material, float f);

    public static final native float btSoftBody_Material_kLST_get(long j, btSoftBody.Material material);

    public static final native void btSoftBody_Material_kLST_set(long j, btSoftBody.Material material, float f);

    public static final native float btSoftBody_Material_kVST_get(long j, btSoftBody.Material material);

    public static final native void btSoftBody_Material_kVST_set(long j, btSoftBody.Material material, float f);

    public static final native long btSoftBody_Node_SWIGUpcast(long j);

    public static final native float btSoftBody_Node_area_get(long j, btSoftBody.Node node);

    public static final native void btSoftBody_Node_area_set(long j, btSoftBody.Node node, float f);

    public static final native int btSoftBody_Node_battach_get(long j, btSoftBody.Node node);

    public static final native void btSoftBody_Node_battach_set(long j, btSoftBody.Node node, int i);

    public static final native long btSoftBody_Node_f_get(long j, btSoftBody.Node node);

    public static final native void btSoftBody_Node_f_set(long j, btSoftBody.Node node, long j2, btVector3 btvector3);

    public static final native float btSoftBody_Node_im_get(long j, btSoftBody.Node node);

    public static final native void btSoftBody_Node_im_set(long j, btSoftBody.Node node, float f);

    public static final native long btSoftBody_Node_leaf_get(long j, btSoftBody.Node node);

    public static final native void btSoftBody_Node_leaf_set(long j, btSoftBody.Node node, long j2, btDbvtNode btdbvtnode);

    public static final native long btSoftBody_Node_n_get(long j, btSoftBody.Node node);

    public static final native void btSoftBody_Node_n_set(long j, btSoftBody.Node node, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Node_q_get(long j, btSoftBody.Node node);

    public static final native void btSoftBody_Node_q_set(long j, btSoftBody.Node node, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Node_v_get(long j, btSoftBody.Node node);

    public static final native void btSoftBody_Node_v_set(long j, btSoftBody.Node node, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Node_x_get(long j, btSoftBody.Node node);

    public static final native void btSoftBody_Node_x_set(long j, btSoftBody.Node node, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Note_SWIGUpcast(long j);

    public static final native float[] btSoftBody_Note_coords_get(long j, btSoftBody.Note note);

    public static final native void btSoftBody_Note_coords_set(long j, btSoftBody.Note note, float[] fArr);

    public static final native long btSoftBody_Note_nodes_get(long j, btSoftBody.Note note);

    public static final native void btSoftBody_Note_nodes_set(long j, btSoftBody.Note note, long j2);

    public static final native long btSoftBody_Note_offset_get(long j, btSoftBody.Note note);

    public static final native void btSoftBody_Note_offset_set(long j, btSoftBody.Note note, long j2, btVector3 btvector3);

    public static final native int btSoftBody_Note_rank_get(long j, btSoftBody.Note note);

    public static final native void btSoftBody_Note_rank_set(long j, btSoftBody.Note note, int i);

    public static final native String btSoftBody_Note_text_get(long j, btSoftBody.Note note);

    public static final native void btSoftBody_PSolve_Anchors(long j, btSoftBody btsoftbody, float f, float f2);

    public static final native void btSoftBody_PSolve_Links(long j, btSoftBody btsoftbody, float f, float f2);

    public static final native void btSoftBody_PSolve_RContacts(long j, btSoftBody btsoftbody, float f, float f2);

    public static final native void btSoftBody_PSolve_SContacts(long j, btSoftBody btsoftbody, float f, float f2);

    public static final native long btSoftBody_Pose_aqq_get(long j, btSoftBody.Pose pose);

    public static final native void btSoftBody_Pose_aqq_set(long j, btSoftBody.Pose pose, long j2, btMatrix3x3 btmatrix3x3);

    public static final native boolean btSoftBody_Pose_bframe_get(long j, btSoftBody.Pose pose);

    public static final native void btSoftBody_Pose_bframe_set(long j, btSoftBody.Pose pose, boolean z);

    public static final native boolean btSoftBody_Pose_bvolume_get(long j, btSoftBody.Pose pose);

    public static final native void btSoftBody_Pose_bvolume_set(long j, btSoftBody.Pose pose, boolean z);

    public static final native long btSoftBody_Pose_com_get(long j, btSoftBody.Pose pose);

    public static final native void btSoftBody_Pose_com_set(long j, btSoftBody.Pose pose, long j2, btVector3 btvector3);

    public static final native long btSoftBody_Pose_pos_get(long j, btSoftBody.Pose pose);

    public static final native void btSoftBody_Pose_pos_set(long j, btSoftBody.Pose pose, long j2, btVector3Array btvector3array);

    public static final native long btSoftBody_Pose_rot_get(long j, btSoftBody.Pose pose);

    public static final native void btSoftBody_Pose_rot_set(long j, btSoftBody.Pose pose, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long btSoftBody_Pose_scl_get(long j, btSoftBody.Pose pose);

    public static final native void btSoftBody_Pose_scl_set(long j, btSoftBody.Pose pose, long j2, btMatrix3x3 btmatrix3x3);

    public static final native float btSoftBody_Pose_volume_get(long j, btSoftBody.Pose pose);

    public static final native void btSoftBody_Pose_volume_set(long j, btSoftBody.Pose pose, float f);

    public static final native long btSoftBody_Pose_wgh_get(long j, btSoftBody.Pose pose);

    public static final native void btSoftBody_Pose_wgh_set(long j, btSoftBody.Pose pose, long j2, btScalarArray btscalararray);

    public static final native long btSoftBody_RContact_c0_get(long j, btSoftBody.RContact rContact);

    public static final native void btSoftBody_RContact_c0_set(long j, btSoftBody.RContact rContact, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long btSoftBody_RContact_c1_get(long j, btSoftBody.RContact rContact);

    public static final native void btSoftBody_RContact_c1_set(long j, btSoftBody.RContact rContact, long j2, btVector3 btvector3);

    public static final native float btSoftBody_RContact_c2_get(long j, btSoftBody.RContact rContact);

    public static final native void btSoftBody_RContact_c2_set(long j, btSoftBody.RContact rContact, float f);

    public static final native float btSoftBody_RContact_c3_get(long j, btSoftBody.RContact rContact);

    public static final native void btSoftBody_RContact_c3_set(long j, btSoftBody.RContact rContact, float f);

    public static final native float btSoftBody_RContact_c4_get(long j, btSoftBody.RContact rContact);

    public static final native void btSoftBody_RContact_c4_set(long j, btSoftBody.RContact rContact, float f);

    public static final native long btSoftBody_RContact_cti_get(long j, btSoftBody.RContact rContact);

    public static final native void btSoftBody_RContact_cti_set(long j, btSoftBody.RContact rContact, long j2, btSoftBody.sCti scti);

    public static final native long btSoftBody_RContact_node_get(long j, btSoftBody.RContact rContact);

    public static final native void btSoftBody_RContact_node_set(long j, btSoftBody.RContact rContact, long j2, btSoftBody.Node node);

    public static final native long btSoftBody_RayFromToCaster_SWIGUpcast(long j);

    public static final native long btSoftBody_RayFromToCaster_face_get(long j, btSoftBody.RayFromToCaster rayFromToCaster);

    public static final native void btSoftBody_RayFromToCaster_face_set(long j, btSoftBody.RayFromToCaster rayFromToCaster, long j2, btSoftBody.Face face);

    public static final native float btSoftBody_RayFromToCaster_mint_get(long j, btSoftBody.RayFromToCaster rayFromToCaster);

    public static final native void btSoftBody_RayFromToCaster_mint_set(long j, btSoftBody.RayFromToCaster rayFromToCaster, float f);

    public static final native float btSoftBody_RayFromToCaster_rayFromToTriangle__SWIG_0(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8, float f);

    public static final native float btSoftBody_RayFromToCaster_rayFromToTriangle__SWIG_1(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8);

    public static final native long btSoftBody_RayFromToCaster_rayFrom_get(long j, btSoftBody.RayFromToCaster rayFromToCaster);

    public static final native void btSoftBody_RayFromToCaster_rayFrom_set(long j, btSoftBody.RayFromToCaster rayFromToCaster, long j2, btVector3 btvector3);

    public static final native long btSoftBody_RayFromToCaster_rayNormalizedDirection_get(long j, btSoftBody.RayFromToCaster rayFromToCaster);

    public static final native void btSoftBody_RayFromToCaster_rayNormalizedDirection_set(long j, btSoftBody.RayFromToCaster rayFromToCaster, long j2, btVector3 btvector3);

    public static final native long btSoftBody_RayFromToCaster_rayTo_get(long j, btSoftBody.RayFromToCaster rayFromToCaster);

    public static final native void btSoftBody_RayFromToCaster_rayTo_set(long j, btSoftBody.RayFromToCaster rayFromToCaster, long j2, btVector3 btvector3);

    public static final native int btSoftBody_RayFromToCaster_tests_get(long j, btSoftBody.RayFromToCaster rayFromToCaster);

    public static final native void btSoftBody_RayFromToCaster_tests_set(long j, btSoftBody.RayFromToCaster rayFromToCaster, int i);

    public static final native float[] btSoftBody_SContact_cfm_get(long j, btSoftBody.SContact sContact);

    public static final native void btSoftBody_SContact_cfm_set(long j, btSoftBody.SContact sContact, float[] fArr);

    public static final native long btSoftBody_SContact_face_get(long j, btSoftBody.SContact sContact);

    public static final native void btSoftBody_SContact_face_set(long j, btSoftBody.SContact sContact, long j2, btSoftBody.Face face);

    public static final native float btSoftBody_SContact_friction_get(long j, btSoftBody.SContact sContact);

    public static final native void btSoftBody_SContact_friction_set(long j, btSoftBody.SContact sContact, float f);

    public static final native float btSoftBody_SContact_margin_get(long j, btSoftBody.SContact sContact);

    public static final native void btSoftBody_SContact_margin_set(long j, btSoftBody.SContact sContact, float f);

    public static final native long btSoftBody_SContact_node_get(long j, btSoftBody.SContact sContact);

    public static final native void btSoftBody_SContact_node_set(long j, btSoftBody.SContact sContact, long j2, btSoftBody.Node node);

    public static final native long btSoftBody_SContact_normal_get(long j, btSoftBody.SContact sContact);

    public static final native void btSoftBody_SContact_normal_set(long j, btSoftBody.SContact sContact, long j2, btVector3 btvector3);

    public static final native long btSoftBody_SContact_weights_get(long j, btSoftBody.SContact sContact);

    public static final native void btSoftBody_SContact_weights_set(long j, btSoftBody.SContact sContact, long j2, btVector3 btvector3);

    public static final native long btSoftBody_SWIGUpcast(long j);

    public static final native float btSoftBody_SolverState_isdt_get(long j, btSoftBody.SolverState solverState);

    public static final native void btSoftBody_SolverState_isdt_set(long j, btSoftBody.SolverState solverState, float f);

    public static final native float btSoftBody_SolverState_radmrg_get(long j, btSoftBody.SolverState solverState);

    public static final native void btSoftBody_SolverState_radmrg_set(long j, btSoftBody.SolverState solverState, float f);

    public static final native float btSoftBody_SolverState_sdt_get(long j, btSoftBody.SolverState solverState);

    public static final native void btSoftBody_SolverState_sdt_set(long j, btSoftBody.SolverState solverState, float f);

    public static final native float btSoftBody_SolverState_updmrg_get(long j, btSoftBody.SolverState solverState);

    public static final native void btSoftBody_SolverState_updmrg_set(long j, btSoftBody.SolverState solverState, float f);

    public static final native float btSoftBody_SolverState_velmrg_get(long j, btSoftBody.SolverState solverState);

    public static final native void btSoftBody_SolverState_velmrg_set(long j, btSoftBody.SolverState solverState, float f);

    public static final native long btSoftBody_Tetra_SWIGUpcast(long j);

    public static final native long btSoftBody_Tetra_c0_get(long j, btSoftBody.Tetra tetra);

    public static final native void btSoftBody_Tetra_c0_set(long j, btSoftBody.Tetra tetra, long j2, btVector3 btvector3);

    public static final native float btSoftBody_Tetra_c1_get(long j, btSoftBody.Tetra tetra);

    public static final native void btSoftBody_Tetra_c1_set(long j, btSoftBody.Tetra tetra, float f);

    public static final native float btSoftBody_Tetra_c2_get(long j, btSoftBody.Tetra tetra);

    public static final native void btSoftBody_Tetra_c2_set(long j, btSoftBody.Tetra tetra, float f);

    public static final native long btSoftBody_Tetra_leaf_get(long j, btSoftBody.Tetra tetra);

    public static final native void btSoftBody_Tetra_leaf_set(long j, btSoftBody.Tetra tetra, long j2, btDbvtNode btdbvtnode);

    public static final native long btSoftBody_Tetra_n_get(long j, btSoftBody.Tetra tetra);

    public static final native void btSoftBody_Tetra_n_set(long j, btSoftBody.Tetra tetra, long j2);

    public static final native float btSoftBody_Tetra_rv_get(long j, btSoftBody.Tetra tetra);

    public static final native void btSoftBody_Tetra_rv_set(long j, btSoftBody.Tetra tetra, float f);

    public static final native void btSoftBody_VSolve_Links(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_addAeroForceToFace(long j, btSoftBody btsoftbody, Vector3 vector3, int i);

    public static final native void btSoftBody_addAeroForceToNode(long j, btSoftBody btsoftbody, Vector3 vector3, int i);

    public static final native void btSoftBody_addForce__SWIG_0(long j, btSoftBody btsoftbody, Vector3 vector3);

    public static final native void btSoftBody_addForce__SWIG_1(long j, btSoftBody btsoftbody, Vector3 vector3, int i);

    public static final native void btSoftBody_addVelocity__SWIG_0(long j, btSoftBody btsoftbody, Vector3 vector3);

    public static final native void btSoftBody_addVelocity__SWIG_1(long j, btSoftBody btsoftbody, Vector3 vector3, int i);

    public static final native long btSoftBody_anchors_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_anchors_set(long j, btSoftBody btsoftbody, long j2);

    public static final native void btSoftBody_appendAnchor__SWIG_0(long j, btSoftBody btsoftbody, int i, long j2, btRigidBody btrigidbody, boolean z, float f);

    public static final native void btSoftBody_appendAnchor__SWIG_1(long j, btSoftBody btsoftbody, int i, long j2, btRigidBody btrigidbody, boolean z);

    public static final native void btSoftBody_appendAnchor__SWIG_2(long j, btSoftBody btsoftbody, int i, long j2, btRigidBody btrigidbody);

    public static final native void btSoftBody_appendAnchor__SWIG_3(long j, btSoftBody btsoftbody, int i, long j2, btRigidBody btrigidbody, Vector3 vector3, boolean z, float f);

    public static final native void btSoftBody_appendAnchor__SWIG_4(long j, btSoftBody btsoftbody, int i, long j2, btRigidBody btrigidbody, Vector3 vector3, boolean z);

    public static final native void btSoftBody_appendAnchor__SWIG_5(long j, btSoftBody btsoftbody, int i, long j2, btRigidBody btrigidbody, Vector3 vector3);

    public static final native void btSoftBody_appendAngularJoint__SWIG_0(long j, btSoftBody btsoftbody, long j2, btSoftBody.AJoint.Specs specs, long j3, btSoftBody.Cluster cluster, long j4, btSoftBody.Body body);

    public static final native void btSoftBody_appendAngularJoint__SWIG_1(long j, btSoftBody btsoftbody, long j2, btSoftBody.AJoint.Specs specs, long j3, btSoftBody.Body body);

    public static final native void btSoftBody_appendAngularJoint__SWIG_2(long j, btSoftBody btsoftbody, long j2, btSoftBody.AJoint.Specs specs);

    public static final native void btSoftBody_appendAngularJoint__SWIG_3(long j, btSoftBody btsoftbody, long j2, btSoftBody.AJoint.Specs specs, long j3, btSoftBody btsoftbody2);

    public static final native void btSoftBody_appendFace__SWIG_0(long j, btSoftBody btsoftbody, int i, long j2, btSoftBody.Material material);

    public static final native void btSoftBody_appendFace__SWIG_1(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_appendFace__SWIG_2(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_appendFace__SWIG_3(long j, btSoftBody btsoftbody, int i, int i2, int i3, long j2, btSoftBody.Material material);

    public static final native void btSoftBody_appendFace__SWIG_4(long j, btSoftBody btsoftbody, int i, int i2, int i3);

    public static final native void btSoftBody_appendLinearJoint__SWIG_0(long j, btSoftBody btsoftbody, long j2, btSoftBody.LJoint.Specs specs, long j3, btSoftBody.Cluster cluster, long j4, btSoftBody.Body body);

    public static final native void btSoftBody_appendLinearJoint__SWIG_1(long j, btSoftBody btsoftbody, long j2, btSoftBody.LJoint.Specs specs, long j3, btSoftBody.Body body);

    public static final native void btSoftBody_appendLinearJoint__SWIG_2(long j, btSoftBody btsoftbody, long j2, btSoftBody.LJoint.Specs specs);

    public static final native void btSoftBody_appendLinearJoint__SWIG_3(long j, btSoftBody btsoftbody, long j2, btSoftBody.LJoint.Specs specs, long j3, btSoftBody btsoftbody2);

    public static final native void btSoftBody_appendLink__SWIG_0(long j, btSoftBody btsoftbody, int i, long j2, btSoftBody.Material material);

    public static final native void btSoftBody_appendLink__SWIG_1(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_appendLink__SWIG_2(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_appendLink__SWIG_3(long j, btSoftBody btsoftbody, int i, int i2, long j2, btSoftBody.Material material, boolean z);

    public static final native void btSoftBody_appendLink__SWIG_4(long j, btSoftBody btsoftbody, int i, int i2, long j2, btSoftBody.Material material);

    public static final native void btSoftBody_appendLink__SWIG_5(long j, btSoftBody btsoftbody, int i, int i2);

    public static final native void btSoftBody_appendLink__SWIG_6(long j, btSoftBody btsoftbody, long j2, btSoftBody.Node node, long j3, btSoftBody.Node node2, long j4, btSoftBody.Material material, boolean z);

    public static final native void btSoftBody_appendLink__SWIG_7(long j, btSoftBody btsoftbody, long j2, btSoftBody.Node node, long j3, btSoftBody.Node node2, long j4, btSoftBody.Material material);

    public static final native void btSoftBody_appendLink__SWIG_8(long j, btSoftBody btsoftbody, long j2, btSoftBody.Node node, long j3, btSoftBody.Node node2);

    public static final native long btSoftBody_appendMaterial(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_appendNode(long j, btSoftBody btsoftbody, Vector3 vector3, float f);

    public static final native void btSoftBody_appendNote__SWIG_0(long j, btSoftBody btsoftbody, String str, Vector3 vector3, long j2, btVector4 btvector4, long j3, btSoftBody.Node node, long j4, btSoftBody.Node node2, long j5, btSoftBody.Node node3, long j6, btSoftBody.Node node4);

    public static final native void btSoftBody_appendNote__SWIG_1(long j, btSoftBody btsoftbody, String str, Vector3 vector3, long j2, btVector4 btvector4, long j3, btSoftBody.Node node, long j4, btSoftBody.Node node2, long j5, btSoftBody.Node node3);

    public static final native void btSoftBody_appendNote__SWIG_2(long j, btSoftBody btsoftbody, String str, Vector3 vector3, long j2, btVector4 btvector4, long j3, btSoftBody.Node node, long j4, btSoftBody.Node node2);

    public static final native void btSoftBody_appendNote__SWIG_3(long j, btSoftBody btsoftbody, String str, Vector3 vector3, long j2, btVector4 btvector4, long j3, btSoftBody.Node node);

    public static final native void btSoftBody_appendNote__SWIG_4(long j, btSoftBody btsoftbody, String str, Vector3 vector3, long j2, btVector4 btvector4);

    public static final native void btSoftBody_appendNote__SWIG_5(long j, btSoftBody btsoftbody, String str, Vector3 vector3);

    public static final native void btSoftBody_appendNote__SWIG_6(long j, btSoftBody btsoftbody, String str, Vector3 vector3, long j2, btSoftBody.Node node);

    public static final native void btSoftBody_appendNote__SWIG_7(long j, btSoftBody btsoftbody, String str, Vector3 vector3, long j2, btSoftBody.Link link);

    public static final native void btSoftBody_appendNote__SWIG_8(long j, btSoftBody btsoftbody, String str, Vector3 vector3, long j2, btSoftBody.Face face);

    public static final native void btSoftBody_appendTetra__SWIG_0(long j, btSoftBody btsoftbody, int i, long j2, btSoftBody.Material material);

    public static final native void btSoftBody_appendTetra__SWIG_1(long j, btSoftBody btsoftbody, int i, int i2, int i3, int i4, long j2, btSoftBody.Material material);

    public static final native void btSoftBody_appendTetra__SWIG_2(long j, btSoftBody btsoftbody, int i, int i2, int i3, int i4);

    public static final native void btSoftBody_applyClusters(long j, btSoftBody btsoftbody, boolean z);

    public static final native void btSoftBody_applyForces(long j, btSoftBody btsoftbody);

    public static final native boolean btSoftBody_bUpdateRtCst_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_bUpdateRtCst_set(long j, btSoftBody btsoftbody, boolean z);

    public static final native long btSoftBody_bounds_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_bounds_set(long j, btSoftBody btsoftbody, long j2, btVector3 btvector3);

    public static final native long btSoftBody_cdbvt_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_cdbvt_set(long j, btSoftBody btsoftbody, long j2, btDbvt btdbvt);

    public static final native long btSoftBody_cfg_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_cfg_set(long j, btSoftBody btsoftbody, long j2, btSoftBody.Config config);

    public static final native boolean btSoftBody_checkContact(long j, btSoftBody btsoftbody, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, Vector3 vector3, float f, long j3, btSoftBody.sCti scti);

    public static final native boolean btSoftBody_checkFace(long j, btSoftBody btsoftbody, int i, int i2, int i3);

    public static final native boolean btSoftBody_checkLink__SWIG_0(long j, btSoftBody btsoftbody, int i, int i2);

    public static final native boolean btSoftBody_checkLink__SWIG_1(long j, btSoftBody btsoftbody, long j2, btSoftBody.Node node, long j3, btSoftBody.Node node2);

    public static final native void btSoftBody_cleanupClusters(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_clusterAImpulse(long j, btSoftBody.Cluster cluster, long j2, btSoftBody.Impulse impulse);

    public static final native Vector3 btSoftBody_clusterCom__SWIG_0(long j, btSoftBody.Cluster cluster);

    public static final native Vector3 btSoftBody_clusterCom__SWIG_1(long j, btSoftBody btsoftbody, int i);

    public static final native long btSoftBody_clusterConnectivity_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_clusterConnectivity_set(long j, btSoftBody btsoftbody, long j2);

    public static final native int btSoftBody_clusterCount(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_clusterDAImpulse(long j, btSoftBody.Cluster cluster, Vector3 vector3);

    public static final native void btSoftBody_clusterDCImpulse(long j, btSoftBody.Cluster cluster, Vector3 vector3);

    public static final native void btSoftBody_clusterDImpulse(long j, btSoftBody.Cluster cluster, Vector3 vector3, Vector3 vector4);

    public static final native void btSoftBody_clusterImpulse(long j, btSoftBody.Cluster cluster, Vector3 vector3, long j2, btSoftBody.Impulse impulse);

    public static final native void btSoftBody_clusterVAImpulse(long j, btSoftBody.Cluster cluster, Vector3 vector3);

    public static final native void btSoftBody_clusterVImpulse(long j, btSoftBody.Cluster cluster, Vector3 vector3, Vector3 vector4);

    public static final native Vector3 btSoftBody_clusterVelocity(long j, btSoftBody.Cluster cluster, Vector3 vector3);

    public static final native long btSoftBody_clusters_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_clusters_set(long j, btSoftBody btsoftbody, long j2);

    public static final native long btSoftBody_collisionDisabledObjects_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_collisionDisabledObjects_set(long j, btSoftBody btsoftbody, long j2, btCollisionObjectConstArray btcollisionobjectconstarray);

    public static final native boolean btSoftBody_cutLink__SWIG_0(long j, btSoftBody btsoftbody, int i, int i2, float f);

    public static final native boolean btSoftBody_cutLink__SWIG_1(long j, btSoftBody btsoftbody, long j2, btSoftBody.Node node, long j3, btSoftBody.Node node2, float f);

    public static final native void btSoftBody_dampClusters(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_defaultCollisionHandler__SWIG_0(long j, btSoftBody btsoftbody, long j2, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btSoftBody_defaultCollisionHandler__SWIG_1(long j, btSoftBody btsoftbody, long j2, btSoftBody btsoftbody2);

    public static final native Vector3 btSoftBody_evaluateCom(long j, btSoftBody btsoftbody);

    public static final native long btSoftBody_faces_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_faces_set(long j, btSoftBody btsoftbody, long j2);

    public static final native long btSoftBody_fdbvt_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_fdbvt_set(long j, btSoftBody btsoftbody, long j2, btDbvt btdbvt);

    public static final native int btSoftBody_generateBendingConstraints__SWIG_0(long j, btSoftBody btsoftbody, int i, long j2, btSoftBody.Material material);

    public static final native int btSoftBody_generateBendingConstraints__SWIG_1(long j, btSoftBody btsoftbody, int i);

    public static final native int btSoftBody_generateClusters__SWIG_0(long j, btSoftBody btsoftbody, int i, int i2);

    public static final native int btSoftBody_generateClusters__SWIG_1(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_getAabb(long j, btSoftBody btsoftbody, Vector3 vector3, Vector3 vector4);

    public static final native long btSoftBody_getFace(long j, btSoftBody btsoftbody, int i);

    public static final native int btSoftBody_getFaceCount(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_getIndices(long j, btSoftBody btsoftbody, ShortBuffer shortBuffer, int i);

    public static final native long btSoftBody_getLink(long j, btSoftBody btsoftbody, int i);

    public static final native int btSoftBody_getLinkCount(long j, btSoftBody btsoftbody);

    public static final native float btSoftBody_getMass(long j, btSoftBody btsoftbody, int i);

    public static final native long btSoftBody_getNode(long j, btSoftBody btsoftbody, int i);

    public static final native int btSoftBody_getNodeCount(long j, btSoftBody btsoftbody);

    public static final native long btSoftBody_getSolver__SWIG_0(int i);

    public static final native float btSoftBody_getTotalMass(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_getVertices__SWIG_0(long j, btSoftBody btsoftbody, FloatBuffer floatBuffer, int i, int i2, int i3);

    public static final native void btSoftBody_getVertices__SWIG_1(long j, btSoftBody btsoftbody, FloatBuffer floatBuffer, int i, int i2, ShortBuffer shortBuffer, int i3, int i4, ShortBuffer shortBuffer2, int i5);

    public static final native void btSoftBody_getVertices__SWIG_2(long j, btSoftBody btsoftbody, FloatBuffer floatBuffer, int i, int i2, int i3, ShortBuffer shortBuffer, int i4, int i5, ShortBuffer shortBuffer2, int i6);

    public static final native float btSoftBody_getVolume(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_indicesToPointers__SWIG_0(long j, btSoftBody btsoftbody, IntBuffer intBuffer);

    public static final native void btSoftBody_indicesToPointers__SWIG_1(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_initDefaults(long j, btSoftBody btsoftbody);

    public static final native long btSoftBody_initialWorldTransform_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_initialWorldTransform_set(long j, btSoftBody btsoftbody, long j2, btTransform bttransform);

    public static final native void btSoftBody_initializeClusters(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_initializeFaceTree(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_integrateMotion(long j, btSoftBody btsoftbody);

    public static final native long btSoftBody_joints_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_joints_set(long j, btSoftBody btsoftbody, long j2);

    public static final native long btSoftBody_links_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_links_set(long j, btSoftBody btsoftbody, long j2);

    public static final native long btSoftBody_materials_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_materials_set(long j, btSoftBody btsoftbody, long j2);

    public static final native long btSoftBody_ndbvt_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_ndbvt_set(long j, btSoftBody btsoftbody, long j2, btDbvt btdbvt);

    public static final native long btSoftBody_nodes_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_nodes_set(long j, btSoftBody btsoftbody, long j2);

    public static final native long btSoftBody_notes_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_notes_set(long j, btSoftBody btsoftbody, long j2);

    public static final native void btSoftBody_pointersToIndices(long j, btSoftBody btsoftbody);

    public static final native long btSoftBody_pose_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_pose_set(long j, btSoftBody btsoftbody, long j2, btSoftBody.Pose pose);

    public static final native void btSoftBody_predictMotion(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_prepareClusters(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_randomizeConstraints(long j, btSoftBody btsoftbody);

    public static final native boolean btSoftBody_rayTest__SWIG_0(long j, btSoftBody btsoftbody, Vector3 vector3, Vector3 vector4, long j2, btSoftBody.sRayCast sraycast);

    public static final native int btSoftBody_rayTest__SWIG_1(long j, btSoftBody btsoftbody, Vector3 vector3, Vector3 vector4, long j2, long j3, long j4, boolean z);

    public static final native long btSoftBody_rcontacts_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_rcontacts_set(long j, btSoftBody btsoftbody, long j2);

    public static final native void btSoftBody_refine(long j, btSoftBody btsoftbody, long j2, btSoftBody.ImplicitFn implicitFn, float f, boolean z);

    public static final native void btSoftBody_releaseCluster(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_releaseClusters(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_resetLinkRestLengths(long j, btSoftBody btsoftbody);

    public static final native float btSoftBody_restLengthScale_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_restLengthScale_set(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_rotate(long j, btSoftBody btsoftbody, Quaternion quaternion);

    public static final native long btSoftBody_sCti_colObj_get(long j, btSoftBody.sCti scti);

    public static final native void btSoftBody_sCti_colObj_set(long j, btSoftBody.sCti scti, long j2, btCollisionObject btcollisionobject);

    public static final native long btSoftBody_sCti_normal_get(long j, btSoftBody.sCti scti);

    public static final native void btSoftBody_sCti_normal_set(long j, btSoftBody.sCti scti, long j2, btVector3 btvector3);

    public static final native float btSoftBody_sCti_offset_get(long j, btSoftBody.sCti scti);

    public static final native void btSoftBody_sCti_offset_set(long j, btSoftBody.sCti scti, float f);

    public static final native float btSoftBody_sMedium_density_get(long j, btSoftBody.sMedium smedium);

    public static final native void btSoftBody_sMedium_density_set(long j, btSoftBody.sMedium smedium, float f);

    public static final native float btSoftBody_sMedium_pressure_get(long j, btSoftBody.sMedium smedium);

    public static final native void btSoftBody_sMedium_pressure_set(long j, btSoftBody.sMedium smedium, float f);

    public static final native long btSoftBody_sMedium_velocity_get(long j, btSoftBody.sMedium smedium);

    public static final native void btSoftBody_sMedium_velocity_set(long j, btSoftBody.sMedium smedium, long j2, btVector3 btvector3);

    public static final native long btSoftBody_sRayCast_body_get(long j, btSoftBody.sRayCast sraycast);

    public static final native void btSoftBody_sRayCast_body_set(long j, btSoftBody.sRayCast sraycast, long j2, btSoftBody btsoftbody);

    public static final native int btSoftBody_sRayCast_feature_get(long j, btSoftBody.sRayCast sraycast);

    public static final native void btSoftBody_sRayCast_feature_set(long j, btSoftBody.sRayCast sraycast, int i);

    public static final native float btSoftBody_sRayCast_fraction_get(long j, btSoftBody.sRayCast sraycast);

    public static final native void btSoftBody_sRayCast_fraction_set(long j, btSoftBody.sRayCast sraycast, float f);

    public static final native int btSoftBody_sRayCast_index_get(long j, btSoftBody.sRayCast sraycast);

    public static final native void btSoftBody_sRayCast_index_set(long j, btSoftBody.sRayCast sraycast, int i);

    public static final native void btSoftBody_scale(long j, btSoftBody btsoftbody, Vector3 vector3);

    public static final native long btSoftBody_scontacts_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_scontacts_set(long j, btSoftBody btsoftbody, long j2);

    public static final native void btSoftBody_setConfig_citerations(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_setConfig_collisions(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_setConfig_diterations(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_setConfig_kAHR(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kCHR(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kDF(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kDG(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kDP(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kKHR(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kLF(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kMT(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kPR(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kSHR(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kSKHR_CL(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kSK_SPLT_CL(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kSRHR_CL(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kSR_SPLT_CL(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kSSHR_CL(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kSS_SPLT_CL(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kVC(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_kVCF(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_maxvolume(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_piterations(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_setConfig_timescale(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setConfig_viterations(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_setMass(long j, btSoftBody btsoftbody, int i, float f);

    public static final native void btSoftBody_setPose(long j, btSoftBody btsoftbody, boolean z, boolean z2);

    public static final native void btSoftBody_setSolver(long j, btSoftBody btsoftbody, int i);

    public static final native void btSoftBody_setTotalDensity(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setTotalMass__SWIG_0(long j, btSoftBody btsoftbody, float f, boolean z);

    public static final native void btSoftBody_setTotalMass__SWIG_1(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setVelocity(long j, btSoftBody btsoftbody, Vector3 vector3);

    public static final native void btSoftBody_setVolumeDensity(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setVolumeMass(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_setWindVelocity(long j, btSoftBody btsoftbody, Vector3 vector3);

    public static final native long btSoftBody_softBodySolver_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_softBodySolver_set(long j, btSoftBody btsoftbody, long j2, btSoftBodySolver btsoftbodysolver);

    public static final native void btSoftBody_solveClusters__SWIG_0(long j);

    public static final native void btSoftBody_solveClusters__SWIG_1(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_solveCommonConstraints(long j, int i, int i2);

    public static final native void btSoftBody_solveConstraints(long j, btSoftBody btsoftbody);

    public static final native long btSoftBody_sst_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_sst_set(long j, btSoftBody btsoftbody, long j2, btSoftBody.SolverState solverState);

    public static final native void btSoftBody_staticSolve(long j, btSoftBody btsoftbody, int i);

    public static final native long btSoftBody_tag_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_tag_set(long j, btSoftBody btsoftbody, long j2);

    public static final native long btSoftBody_tetras_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_tetras_set(long j, btSoftBody btsoftbody, long j2);

    public static final native float btSoftBody_timeacc_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_timeacc_set(long j, btSoftBody btsoftbody, float f);

    public static final native void btSoftBody_transform(long j, btSoftBody btsoftbody, Matrix4 matrix4);

    public static final native void btSoftBody_translate(long j, btSoftBody btsoftbody, Vector3 vector3);

    public static final native long btSoftBody_upcast(long j, btCollisionObject btcollisionobject);

    public static final native long btSoftBody_upcastConstBtCollisionObject(long j, btCollisionObject btcollisionobject);

    public static final native void btSoftBody_updateArea__SWIG_0(long j, btSoftBody btsoftbody, boolean z);

    public static final native void btSoftBody_updateArea__SWIG_1(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_updateBounds(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_updateClusters(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_updateConstants(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_updateLinkConstants(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_updateNormals(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_updatePose(long j, btSoftBody btsoftbody);

    public static final native long btSoftBody_userIndexMapping_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_userIndexMapping_set(long j, btSoftBody btsoftbody, long j2);

    public static final native long btSoftBody_windVelocity_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_windVelocity_set(long j, btSoftBody btsoftbody, long j2, btVector3 btvector3);

    public static final native long btSoftBody_worldInfo_get(long j, btSoftBody btsoftbody);

    public static final native void btSoftBody_worldInfo_set(long j, btSoftBody btsoftbody, long j2, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native long btSoftClusterCollisionShape_SWIGUpcast(long j);

    public static final native long btSoftClusterCollisionShape_cluster_get(long j, btSoftClusterCollisionShape btsoftclustercollisionshape);

    public static final native void btSoftClusterCollisionShape_cluster_set(long j, btSoftClusterCollisionShape btsoftclustercollisionshape, long j2, btSoftBody.Cluster cluster);

    public static final native int btSoftClusterCollisionShape_getShapeType(long j, btSoftClusterCollisionShape btsoftclustercollisionshape);

    public static final native long btSoftColliders_ClusterBase_SWIGUpcast(long j);

    public static final native boolean btSoftColliders_ClusterBase_SolveContact(long j, btSoftColliders.ClusterBase clusterBase, long j2, btGjkEpaSolver2.sResults sresults, long j3, btSoftBody.Body body, long j4, btSoftBody.Body body2, long j5, btSoftBody.CJoint cJoint);

    public static final native float btSoftColliders_ClusterBase_erp_get(long j, btSoftColliders.ClusterBase clusterBase);

    public static final native void btSoftColliders_ClusterBase_erp_set(long j, btSoftColliders.ClusterBase clusterBase, float f);

    public static final native float btSoftColliders_ClusterBase_friction_get(long j, btSoftColliders.ClusterBase clusterBase);

    public static final native void btSoftColliders_ClusterBase_friction_set(long j, btSoftColliders.ClusterBase clusterBase, float f);

    public static final native float btSoftColliders_ClusterBase_idt_get(long j, btSoftColliders.ClusterBase clusterBase);

    public static final native void btSoftColliders_ClusterBase_idt_set(long j, btSoftColliders.ClusterBase clusterBase, float f);

    public static final native float btSoftColliders_ClusterBase_margin_get(long j, btSoftColliders.ClusterBase clusterBase);

    public static final native void btSoftColliders_ClusterBase_margin_set(long j, btSoftColliders.ClusterBase clusterBase, float f);

    public static final native float btSoftColliders_ClusterBase_threshold_get(long j, btSoftColliders.ClusterBase clusterBase);

    public static final native void btSoftColliders_ClusterBase_threshold_set(long j, btSoftColliders.ClusterBase clusterBase, float f);

    public static final native void btSoftColliders_CollideCL_RS_ProcessColObj(long j, btSoftColliders.CollideCL_RS collideCL_RS, long j2, btSoftBody btsoftbody, long j3, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native long btSoftColliders_CollideCL_RS_SWIGUpcast(long j);

    public static final native long btSoftColliders_CollideCL_RS_colObjWrap_get(long j, btSoftColliders.CollideCL_RS collideCL_RS);

    public static final native void btSoftColliders_CollideCL_RS_colObjWrap_set(long j, btSoftColliders.CollideCL_RS collideCL_RS, long j2, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native long btSoftColliders_CollideCL_RS_psb_get(long j, btSoftColliders.CollideCL_RS collideCL_RS);

    public static final native void btSoftColliders_CollideCL_RS_psb_set(long j, btSoftColliders.CollideCL_RS collideCL_RS, long j2, btSoftBody btsoftbody);

    public static final native void btSoftColliders_CollideCL_SS_ProcessSoftSoft(long j, btSoftColliders.CollideCL_SS collideCL_SS, long j2, btSoftBody btsoftbody, long j3, btSoftBody btsoftbody2);

    public static final native long btSoftColliders_CollideCL_SS_SWIGUpcast(long j);

    public static final native long btSoftColliders_CollideCL_SS_bodies_get(long j, btSoftColliders.CollideCL_SS collideCL_SS);

    public static final native void btSoftColliders_CollideCL_SS_bodies_set(long j, btSoftColliders.CollideCL_SS collideCL_SS, long j2);

    public static final native void btSoftColliders_CollideSDF_RS_DoNode(long j, btSoftColliders.CollideSDF_RS collideSDF_RS, long j2, btSoftBody.Node node);

    public static final native long btSoftColliders_CollideSDF_RS_SWIGUpcast(long j);

    public static final native long btSoftColliders_CollideSDF_RS_colObj1Wrap_get(long j, btSoftColliders.CollideSDF_RS collideSDF_RS);

    public static final native void btSoftColliders_CollideSDF_RS_colObj1Wrap_set(long j, btSoftColliders.CollideSDF_RS collideSDF_RS, long j2, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native float btSoftColliders_CollideSDF_RS_dynmargin_get(long j, btSoftColliders.CollideSDF_RS collideSDF_RS);

    public static final native void btSoftColliders_CollideSDF_RS_dynmargin_set(long j, btSoftColliders.CollideSDF_RS collideSDF_RS, float f);

    public static final native long btSoftColliders_CollideSDF_RS_psb_get(long j, btSoftColliders.CollideSDF_RS collideSDF_RS);

    public static final native void btSoftColliders_CollideSDF_RS_psb_set(long j, btSoftColliders.CollideSDF_RS collideSDF_RS, long j2, btSoftBody btsoftbody);

    public static final native long btSoftColliders_CollideSDF_RS_rigidBody_get(long j, btSoftColliders.CollideSDF_RS collideSDF_RS);

    public static final native void btSoftColliders_CollideSDF_RS_rigidBody_set(long j, btSoftColliders.CollideSDF_RS collideSDF_RS, long j2, btRigidBody btrigidbody);

    public static final native float btSoftColliders_CollideSDF_RS_stamargin_get(long j, btSoftColliders.CollideSDF_RS collideSDF_RS);

    public static final native void btSoftColliders_CollideSDF_RS_stamargin_set(long j, btSoftColliders.CollideSDF_RS collideSDF_RS, float f);

    public static final native long btSoftColliders_CollideVF_SS_SWIGUpcast(long j);

    public static final native float btSoftColliders_CollideVF_SS_mrg_get(long j, btSoftColliders.CollideVF_SS collideVF_SS);

    public static final native void btSoftColliders_CollideVF_SS_mrg_set(long j, btSoftColliders.CollideVF_SS collideVF_SS, float f);

    public static final native long btSoftColliders_CollideVF_SS_psb_get(long j, btSoftColliders.CollideVF_SS collideVF_SS);

    public static final native void btSoftColliders_CollideVF_SS_psb_set(long j, btSoftColliders.CollideVF_SS collideVF_SS, long j2);

    public static final native long btSoftMultiBodyDynamicsWorld_SWIGUpcast(long j);

    public static final native void btSoftMultiBodyDynamicsWorld_addSoftBody__SWIG_0(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld, long j2, btSoftBody btsoftbody, int i, int i2);

    public static final native void btSoftMultiBodyDynamicsWorld_addSoftBody__SWIG_1(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld, long j2, btSoftBody btsoftbody, int i);

    public static final native void btSoftMultiBodyDynamicsWorld_addSoftBody__SWIG_2(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld, long j2, btSoftBody btsoftbody);

    public static final native int btSoftMultiBodyDynamicsWorld_getDrawFlags(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld);

    public static final native long btSoftMultiBodyDynamicsWorld_getSoftBodyArray(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld);

    public static final native long btSoftMultiBodyDynamicsWorld_getSoftBodyArrayConst(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld);

    public static final native long btSoftMultiBodyDynamicsWorld_getWorldInfo(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld);

    public static final native long btSoftMultiBodyDynamicsWorld_getWorldInfoConst(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld);

    public static final native void btSoftMultiBodyDynamicsWorld_rayTestSingle(Matrix4 matrix4, Matrix4 matrix5, long j, btCollisionObject btcollisionobject, long j2, btCollisionShape btcollisionshape, Matrix4 matrix6, long j3, RayResultCallback rayResultCallback);

    public static final native void btSoftMultiBodyDynamicsWorld_removeSoftBody(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld, long j2, btSoftBody btsoftbody);

    public static final native void btSoftMultiBodyDynamicsWorld_setDrawFlags(long j, btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld, int i);

    public static final native long btSoftRigidCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btSoftRigidCollisionAlgorithm_SWIGUpcast(long j);

    public static final native long btSoftRigidDynamicsWorld_SWIGUpcast(long j);

    public static final native void btSoftRigidDynamicsWorld_addSoftBody__SWIG_0(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld, long j2, btSoftBody btsoftbody, int i, int i2);

    public static final native void btSoftRigidDynamicsWorld_addSoftBody__SWIG_1(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld, long j2, btSoftBody btsoftbody, int i);

    public static final native void btSoftRigidDynamicsWorld_addSoftBody__SWIG_2(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld, long j2, btSoftBody btsoftbody);

    public static final native int btSoftRigidDynamicsWorld_getDrawFlags(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld);

    public static final native long btSoftRigidDynamicsWorld_getSoftBodyArray(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld);

    public static final native long btSoftRigidDynamicsWorld_getSoftBodyArrayConst(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld);

    public static final native long btSoftRigidDynamicsWorld_getWorldInfo(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld);

    public static final native long btSoftRigidDynamicsWorld_getWorldInfoConst(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld);

    public static final native void btSoftRigidDynamicsWorld_rayTestSingle(Matrix4 matrix4, Matrix4 matrix5, long j, btCollisionObject btcollisionobject, long j2, btCollisionShape btcollisionshape, Matrix4 matrix6, long j3, RayResultCallback rayResultCallback);

    public static final native void btSoftRigidDynamicsWorld_removeSoftBody(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld, long j2, btSoftBody btsoftbody);

    public static final native void btSoftRigidDynamicsWorld_setDrawFlags(long j, btSoftRigidDynamicsWorld btsoftrigiddynamicsworld, int i);

    public static final native long btSoftSoftCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btSoftSoftCollisionAlgorithm_SWIGUpcast(long j);

    public static final native void btSparseSdf3_BuildCell(long j, btSparseSdf3 btsparsesdf3, long j2, btSparseSdf3.Cell cell);

    public static final native int[] btSparseSdf3_Cell_c_get(long j, btSparseSdf3.Cell cell);

    public static final native void btSparseSdf3_Cell_c_set(long j, btSparseSdf3.Cell cell, int[] iArr);

    public static final native long btSparseSdf3_Cell_d_get(long j, btSparseSdf3.Cell cell);

    public static final native void btSparseSdf3_Cell_d_set(long j, btSparseSdf3.Cell cell, long j2);

    public static final native long btSparseSdf3_Cell_hash_get(long j, btSparseSdf3.Cell cell);

    public static final native void btSparseSdf3_Cell_hash_set(long j, btSparseSdf3.Cell cell, long j2);

    public static final native long btSparseSdf3_Cell_next_get(long j, btSparseSdf3.Cell cell);

    public static final native void btSparseSdf3_Cell_next_set(long j, btSparseSdf3.Cell cell, long j2, btSparseSdf3.Cell cell2);

    public static final native long btSparseSdf3_Cell_pclient_get(long j, btSparseSdf3.Cell cell);

    public static final native void btSparseSdf3_Cell_pclient_set(long j, btSparseSdf3.Cell cell, long j2, btCollisionShape btcollisionshape);

    public static final native int btSparseSdf3_Cell_puid_get(long j, btSparseSdf3.Cell cell);

    public static final native void btSparseSdf3_Cell_puid_set(long j, btSparseSdf3.Cell cell, int i);

    public static final native long btSparseSdf3_Decompose(float f);

    public static final native float btSparseSdf3_DistanceToShape(Vector3 vector3, long j, btCollisionShape btcollisionshape);

    public static final native float btSparseSdf3_Evaluate(long j, btSparseSdf3 btsparsesdf3, Vector3 vector3, long j2, btCollisionShape btcollisionshape, Vector3 vector4, float f);

    public static final native void btSparseSdf3_GarbageCollect__SWIG_0(long j, btSparseSdf3 btsparsesdf3, int i);

    public static final native void btSparseSdf3_GarbageCollect__SWIG_1(long j, btSparseSdf3 btsparsesdf3);

    public static final native long btSparseSdf3_Hash(int i, int i2, int i3, long j, btCollisionShape btcollisionshape);

    public static final native void btSparseSdf3_Initialize__SWIG_0(long j, btSparseSdf3 btsparsesdf3, int i, int i2);

    public static final native void btSparseSdf3_Initialize__SWIG_1(long j, btSparseSdf3 btsparsesdf3, int i);

    public static final native void btSparseSdf3_Initialize__SWIG_2(long j, btSparseSdf3 btsparsesdf3);

    public static final native int btSparseSdf3_IntFrac_b_get(long j, btSparseSdf3.IntFrac intFrac);

    public static final native void btSparseSdf3_IntFrac_b_set(long j, btSparseSdf3.IntFrac intFrac, int i);

    public static final native float btSparseSdf3_IntFrac_f_get(long j, btSparseSdf3.IntFrac intFrac);

    public static final native void btSparseSdf3_IntFrac_f_set(long j, btSparseSdf3.IntFrac intFrac, float f);

    public static final native int btSparseSdf3_IntFrac_i_get(long j, btSparseSdf3.IntFrac intFrac);

    public static final native void btSparseSdf3_IntFrac_i_set(long j, btSparseSdf3.IntFrac intFrac, int i);

    public static final native float btSparseSdf3_Lerp(float f, float f2, float f3);

    public static final native int btSparseSdf3_RemoveReferences(long j, btSparseSdf3 btsparsesdf3, long j2, btCollisionShape btcollisionshape);

    public static final native void btSparseSdf3_Reset(long j, btSparseSdf3 btsparsesdf3);

    public static final native long btSparseSdf3_cells_get(long j, btSparseSdf3 btsparsesdf3);

    public static final native void btSparseSdf3_cells_set(long j, btSparseSdf3 btsparsesdf3, long j2);

    public static final native int btSparseSdf3_clampCells_get(long j, btSparseSdf3 btsparsesdf3);

    public static final native void btSparseSdf3_clampCells_set(long j, btSparseSdf3 btsparsesdf3, int i);

    public static final native int btSparseSdf3_ncells_get(long j, btSparseSdf3 btsparsesdf3);

    public static final native void btSparseSdf3_ncells_set(long j, btSparseSdf3 btsparsesdf3, int i);

    public static final native int btSparseSdf3_nprobes_get(long j, btSparseSdf3 btsparsesdf3);

    public static final native void btSparseSdf3_nprobes_set(long j, btSparseSdf3 btsparsesdf3, int i);

    public static final native int btSparseSdf3_nqueries_get(long j, btSparseSdf3 btsparsesdf3);

    public static final native void btSparseSdf3_nqueries_set(long j, btSparseSdf3 btsparsesdf3, int i);

    public static final native int btSparseSdf3_puid_get(long j, btSparseSdf3 btsparsesdf3);

    public static final native void btSparseSdf3_puid_set(long j, btSparseSdf3 btsparsesdf3, int i);

    public static final native float btSparseSdf3_voxelsz_get(long j, btSparseSdf3 btsparsesdf3);

    public static final native void btSparseSdf3_voxelsz_set(long j, btSparseSdf3 btsparsesdf3, float f);

    public static final native int btTriIndex_PartIdTriangleIndex_get(long j, btTriIndex bttriindex);

    public static final native void btTriIndex_PartIdTriangleIndex_set(long j, btTriIndex bttriindex, int i);

    public static final native long btTriIndex_childShape_get(long j, btTriIndex bttriindex);

    public static final native void btTriIndex_childShape_set(long j, btTriIndex bttriindex, long j2, btCollisionShape btcollisionshape);

    public static final native int btTriIndex_getPartId(long j, btTriIndex bttriindex);

    public static final native int btTriIndex_getTriangleIndex(long j, btTriIndex bttriindex);

    public static final native int btTriIndex_getUid(long j, btTriIndex bttriindex);

    public static final native int btVertexBufferDescriptor_getBufferType(long j, btVertexBufferDescriptor btvertexbufferdescriptor);

    public static final native int btVertexBufferDescriptor_getNormalOffset(long j, btVertexBufferDescriptor btvertexbufferdescriptor);

    public static final native int btVertexBufferDescriptor_getNormalStride(long j, btVertexBufferDescriptor btvertexbufferdescriptor);

    public static final native int btVertexBufferDescriptor_getVertexOffset(long j, btVertexBufferDescriptor btvertexbufferdescriptor);

    public static final native int btVertexBufferDescriptor_getVertexStride(long j, btVertexBufferDescriptor btvertexbufferdescriptor);

    public static final native boolean btVertexBufferDescriptor_hasNormals(long j, btVertexBufferDescriptor btvertexbufferdescriptor);

    public static final native boolean btVertexBufferDescriptor_hasVertexPositions(long j, btVertexBufferDescriptor btvertexbufferdescriptor);

    public static final native void delete_SoftBodyClusterData(long j);

    public static final native void delete_SoftBodyConfigData(long j);

    public static final native void delete_SoftBodyFaceData(long j);

    public static final native void delete_SoftBodyLinkData(long j);

    public static final native void delete_SoftBodyMaterialData(long j);

    public static final native void delete_SoftBodyNodeData(long j);

    public static final native void delete_SoftBodyPoseData(long j);

    public static final native void delete_SoftBodyTetraData(long j);

    public static final native void delete_SoftRigidAnchorData(long j);

    public static final native void delete_btCPUVertexBufferDescriptor(long j);

    public static final native void delete_btDefaultSoftBodySolver(long j);

    public static final native void delete_btEigen(long j);

    public static final native void delete_btSoftBody(long j);

    public static final native void delete_btSoftBodyCollisionShape(long j);

    public static final native void delete_btSoftBodyConcaveCollisionAlgorithm(long j);

    public static final native void delete_btSoftBodyConcaveCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btSoftBodyConcaveCollisionAlgorithm_SwappedCreateFunc(long j);

    public static final native void delete_btSoftBodyFloatData(long j);

    public static final native void delete_btSoftBodyHelpers(long j);

    public static final native void delete_btSoftBodyJointData(long j);

    public static final native void delete_btSoftBodyRigidBodyCollisionConfiguration(long j);

    public static final native void delete_btSoftBodySolver(long j);

    public static final native void delete_btSoftBodySolverOutput(long j);

    public static final native void delete_btSoftBodyTriangleCallback(long j);

    public static final native void delete_btSoftBodyWorldInfo(long j);

    public static final native void delete_btSoftBody_AJoint(long j);

    public static final native void delete_btSoftBody_AJoint_IControl(long j);

    public static final native void delete_btSoftBody_AJoint_Specs(long j);

    public static final native void delete_btSoftBody_Anchor(long j);

    public static final native void delete_btSoftBody_Body(long j);

    public static final native void delete_btSoftBody_CJoint(long j);

    public static final native void delete_btSoftBody_Cluster(long j);

    public static final native void delete_btSoftBody_Config(long j);

    public static final native void delete_btSoftBody_Element(long j);

    public static final native void delete_btSoftBody_Face(long j);

    public static final native void delete_btSoftBody_Feature(long j);

    public static final native void delete_btSoftBody_ImplicitFn(long j);

    public static final native void delete_btSoftBody_Impulse(long j);

    public static final native void delete_btSoftBody_Joint(long j);

    public static final native void delete_btSoftBody_Joint_Specs(long j);

    public static final native void delete_btSoftBody_Joint_eType(long j);

    public static final native void delete_btSoftBody_LJoint(long j);

    public static final native void delete_btSoftBody_LJoint_Specs(long j);

    public static final native void delete_btSoftBody_Link(long j);

    public static final native void delete_btSoftBody_Material(long j);

    public static final native void delete_btSoftBody_Node(long j);

    public static final native void delete_btSoftBody_Note(long j);

    public static final native void delete_btSoftBody_Pose(long j);

    public static final native void delete_btSoftBody_RContact(long j);

    public static final native void delete_btSoftBody_RayFromToCaster(long j);

    public static final native void delete_btSoftBody_SContact(long j);

    public static final native void delete_btSoftBody_SolverState(long j);

    public static final native void delete_btSoftBody_Tetra(long j);

    public static final native void delete_btSoftBody_eAeroModel(long j);

    public static final native void delete_btSoftBody_eFeature(long j);

    public static final native void delete_btSoftBody_ePSolver(long j);

    public static final native void delete_btSoftBody_eSolverPresets(long j);

    public static final native void delete_btSoftBody_eVSolver(long j);

    public static final native void delete_btSoftBody_fCollision(long j);

    public static final native void delete_btSoftBody_fMaterial(long j);

    public static final native void delete_btSoftBody_sCti(long j);

    public static final native void delete_btSoftBody_sMedium(long j);

    public static final native void delete_btSoftBody_sRayCast(long j);

    public static final native void delete_btSoftClusterCollisionShape(long j);

    public static final native void delete_btSoftColliders(long j);

    public static final native void delete_btSoftColliders_ClusterBase(long j);

    public static final native void delete_btSoftColliders_CollideCL_RS(long j);

    public static final native void delete_btSoftColliders_CollideCL_SS(long j);

    public static final native void delete_btSoftColliders_CollideSDF_RS(long j);

    public static final native void delete_btSoftColliders_CollideVF_SS(long j);

    public static final native void delete_btSoftMultiBodyDynamicsWorld(long j);

    public static final native void delete_btSoftRigidCollisionAlgorithm(long j);

    public static final native void delete_btSoftRigidCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btSoftRigidDynamicsWorld(long j);

    public static final native void delete_btSoftSoftCollisionAlgorithm(long j);

    public static final native void delete_btSoftSoftCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btSparseSdf3(long j);

    public static final native void delete_btSparseSdf3_Cell(long j);

    public static final native void delete_btSparseSdf3_IntFrac(long j);

    public static final native void delete_btTriIndex(long j);

    public static final native void delete_btVertexBufferDescriptor(long j);

    public static final native void delete_fDrawFlags(long j);

    public static final native long new_SoftBodyClusterData();

    public static final native long new_SoftBodyConfigData();

    public static final native long new_SoftBodyFaceData();

    public static final native long new_SoftBodyLinkData();

    public static final native long new_SoftBodyMaterialData();

    public static final native long new_SoftBodyNodeData();

    public static final native long new_SoftBodyPoseData();

    public static final native long new_SoftBodyTetraData();

    public static final native long new_SoftRigidAnchorData();

    public static final native long new_btCPUVertexBufferDescriptor__SWIG_0(FloatBuffer floatBuffer, int i, int i2);

    public static final native long new_btCPUVertexBufferDescriptor__SWIG_1(FloatBuffer floatBuffer, int i, int i2, int i3, int i4);

    public static final native long new_btDefaultSoftBodySolver();

    public static final native long new_btEigen();

    public static final native long new_btSoftBodyCollisionShape(long j, btSoftBody btsoftbody);

    public static final native long new_btSoftBodyConcaveCollisionAlgorithm(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z);

    public static final native long new_btSoftBodyConcaveCollisionAlgorithm_CreateFunc();

    public static final native long new_btSoftBodyConcaveCollisionAlgorithm_SwappedCreateFunc();

    public static final native long new_btSoftBodyFloatData();

    public static final native long new_btSoftBodyHelpers();

    public static final native long new_btSoftBodyJointData();

    public static final native long new_btSoftBodyRigidBodyCollisionConfiguration__SWIG_0(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo);

    public static final native long new_btSoftBodyRigidBodyCollisionConfiguration__SWIG_1();

    public static final native long new_btSoftBodyTriangleCallback(long j, btDispatcher btdispatcher, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z);

    public static final native long new_btSoftBodyWorldInfo();

    public static final native long new_btSoftBody_AJoint();

    public static final native long new_btSoftBody_AJoint_IControl();

    public static final native long new_btSoftBody_AJoint_Specs();

    public static final native long new_btSoftBody_Anchor();

    public static final native long new_btSoftBody_Body__SWIG_0();

    public static final native long new_btSoftBody_Body__SWIG_1(long j, btSoftBody.Cluster cluster);

    public static final native long new_btSoftBody_Body__SWIG_2(long j, btCollisionObject btcollisionobject);

    public static final native long new_btSoftBody_CJoint();

    public static final native long new_btSoftBody_Cluster();

    public static final native long new_btSoftBody_Config();

    public static final native long new_btSoftBody_Element();

    public static final native long new_btSoftBody_Face();

    public static final native long new_btSoftBody_Feature();

    public static final native long new_btSoftBody_Impulse();

    public static final native long new_btSoftBody_Joint_Specs();

    public static final native long new_btSoftBody_Joint_eType();

    public static final native long new_btSoftBody_LJoint();

    public static final native long new_btSoftBody_LJoint_Specs();

    public static final native long new_btSoftBody_Link();

    public static final native long new_btSoftBody_Material();

    public static final native long new_btSoftBody_Node();

    public static final native long new_btSoftBody_Note();

    public static final native long new_btSoftBody_Pose();

    public static final native long new_btSoftBody_RContact();

    public static final native long new_btSoftBody_RayFromToCaster(Vector3 vector3, Vector3 vector4, float f);

    public static final native long new_btSoftBody_SContact();

    public static final native long new_btSoftBody_SolverState();

    public static final native long new_btSoftBody_Tetra();

    public static final native long new_btSoftBody__SWIG_0(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, int i, long j2, btVector3 btvector3, FloatBuffer floatBuffer);

    public static final native long new_btSoftBody__SWIG_1(long j, btSoftBodyWorldInfo btsoftbodyworldinfo);

    public static final native long new_btSoftBody__SWIG_2(long j, btSoftBodyWorldInfo btsoftbodyworldinfo, FloatBuffer floatBuffer, int i, int i2, int i3, ShortBuffer shortBuffer, int i4, int i5, ShortBuffer shortBuffer2, int i6);

    public static final native long new_btSoftBody_eAeroModel();

    public static final native long new_btSoftBody_eFeature();

    public static final native long new_btSoftBody_ePSolver();

    public static final native long new_btSoftBody_eSolverPresets();

    public static final native long new_btSoftBody_eVSolver();

    public static final native long new_btSoftBody_fCollision();

    public static final native long new_btSoftBody_fMaterial();

    public static final native long new_btSoftBody_sCti();

    public static final native long new_btSoftBody_sMedium();

    public static final native long new_btSoftBody_sRayCast();

    public static final native long new_btSoftClusterCollisionShape(long j, btSoftBody.Cluster cluster);

    public static final native long new_btSoftColliders();

    public static final native long new_btSoftColliders_ClusterBase();

    public static final native long new_btSoftColliders_CollideCL_RS();

    public static final native long new_btSoftColliders_CollideCL_SS();

    public static final native long new_btSoftColliders_CollideSDF_RS();

    public static final native long new_btSoftColliders_CollideVF_SS();

    public static final native long new_btSoftMultiBodyDynamicsWorld__SWIG_0(long j, btDispatcher btdispatcher, long j2, btBroadphaseInterface btbroadphaseinterface, long j3, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j4, btCollisionConfiguration btcollisionconfiguration, long j5, btSoftBodySolver btsoftbodysolver);

    public static final native long new_btSoftMultiBodyDynamicsWorld__SWIG_1(long j, btDispatcher btdispatcher, long j2, btBroadphaseInterface btbroadphaseinterface, long j3, btMultiBodyConstraintSolver btmultibodyconstraintsolver, long j4, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_btSoftRigidCollisionAlgorithm(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z);

    public static final native long new_btSoftRigidCollisionAlgorithm_CreateFunc();

    public static final native long new_btSoftRigidDynamicsWorld__SWIG_0(long j, btDispatcher btdispatcher, long j2, btBroadphaseInterface btbroadphaseinterface, long j3, btConstraintSolver btconstraintsolver, long j4, btCollisionConfiguration btcollisionconfiguration, long j5, btSoftBodySolver btsoftbodysolver);

    public static final native long new_btSoftRigidDynamicsWorld__SWIG_1(long j, btDispatcher btdispatcher, long j2, btBroadphaseInterface btbroadphaseinterface, long j3, btConstraintSolver btconstraintsolver, long j4, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_btSoftSoftCollisionAlgorithm_CreateFunc();

    public static final native long new_btSoftSoftCollisionAlgorithm__SWIG_0(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo);

    public static final native long new_btSoftSoftCollisionAlgorithm__SWIG_1(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2);

    public static final native long new_btSparseSdf3();

    public static final native long new_btSparseSdf3_Cell();

    public static final native long new_btSparseSdf3_IntFrac();

    public static final native long new_btTriIndex(int i, int i2, long j, btCollisionShape btcollisionshape);

    public static final native long new_fDrawFlags();
}
