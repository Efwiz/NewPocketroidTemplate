package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btBulletSerializedArrays;
import com.badlogic.gdx.physics.bullet.linearmath.btHashInt;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3;
import com.badlogic.gdx.physics.bullet.linearmath.btPoolAllocator;
import com.badlogic.gdx.physics.bullet.linearmath.btScalarArray;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3Array;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class CollisionJNI {
    public static final native long AllHitsRayResultCallback_SWIGUpcast(long j);

    public static final native float AllHitsRayResultCallback_addSingleResult(long j, AllHitsRayResultCallback allHitsRayResultCallback, long j2, LocalRayResult localRayResult, boolean z);

    public static final native float AllHitsRayResultCallback_addSingleResultSwigExplicitAllHitsRayResultCallback(long j, AllHitsRayResultCallback allHitsRayResultCallback, long j2, LocalRayResult localRayResult, boolean z);

    public static final native void AllHitsRayResultCallback_change_ownership(AllHitsRayResultCallback allHitsRayResultCallback, long j, boolean z);

    public static final native long AllHitsRayResultCallback_collisionObjects_get(long j, AllHitsRayResultCallback allHitsRayResultCallback);

    public static final native void AllHitsRayResultCallback_collisionObjects_set(long j, AllHitsRayResultCallback allHitsRayResultCallback, long j2, btCollisionObjectConstArray btcollisionobjectconstarray);

    public static final native void AllHitsRayResultCallback_director_connect(AllHitsRayResultCallback allHitsRayResultCallback, long j, boolean z, boolean z2);

    public static final native void AllHitsRayResultCallback_getRayFromWorld(long j, AllHitsRayResultCallback allHitsRayResultCallback, Vector3 vector3);

    public static final native void AllHitsRayResultCallback_getRayToWorld(long j, AllHitsRayResultCallback allHitsRayResultCallback, Vector3 vector3);

    public static final native long AllHitsRayResultCallback_hitFractions_get(long j, AllHitsRayResultCallback allHitsRayResultCallback);

    public static final native void AllHitsRayResultCallback_hitFractions_set(long j, AllHitsRayResultCallback allHitsRayResultCallback, long j2, btScalarArray btscalararray);

    public static final native long AllHitsRayResultCallback_hitNormalWorld_get(long j, AllHitsRayResultCallback allHitsRayResultCallback);

    public static final native void AllHitsRayResultCallback_hitNormalWorld_set(long j, AllHitsRayResultCallback allHitsRayResultCallback, long j2, btVector3Array btvector3array);

    public static final native long AllHitsRayResultCallback_hitPointWorld_get(long j, AllHitsRayResultCallback allHitsRayResultCallback);

    public static final native void AllHitsRayResultCallback_hitPointWorld_set(long j, AllHitsRayResultCallback allHitsRayResultCallback, long j2, btVector3Array btvector3array);

    public static final native void AllHitsRayResultCallback_setRayFromWorld(long j, AllHitsRayResultCallback allHitsRayResultCallback, Vector3 vector3);

    public static final native void AllHitsRayResultCallback_setRayToWorld(long j, AllHitsRayResultCallback allHitsRayResultCallback, Vector3 vector3);

    public static final native long BT_BOX_BOX_TRANSFORM_CACHE_AR_get(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache);

    public static final native void BT_BOX_BOX_TRANSFORM_CACHE_AR_set(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long BT_BOX_BOX_TRANSFORM_CACHE_R1to0_get(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache);

    public static final native void BT_BOX_BOX_TRANSFORM_CACHE_R1to0_set(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long BT_BOX_BOX_TRANSFORM_CACHE_T1to0_get(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache);

    public static final native void BT_BOX_BOX_TRANSFORM_CACHE_T1to0_set(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache, long j2, btVector3 btvector3);

    public static final native void BT_BOX_BOX_TRANSFORM_CACHE_calc_absolute_matrix(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache);

    public static final native void BT_BOX_BOX_TRANSFORM_CACHE_calc_from_full_invert(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void BT_BOX_BOX_TRANSFORM_CACHE_calc_from_homogenic(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache, Matrix4 matrix4, Matrix4 matrix5);

    public static final native Vector3 BT_BOX_BOX_TRANSFORM_CACHE_transform(long j, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache, Vector3 vector3);

    public static final native int BT_NULL_PAIR_get();

    public static final native int BT_QUANTIZED_BVH_NODE_escapeIndexOrDataIndex_get(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node);

    public static final native void BT_QUANTIZED_BVH_NODE_escapeIndexOrDataIndex_set(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node, int i);

    public static final native int BT_QUANTIZED_BVH_NODE_getDataIndex(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node);

    public static final native int BT_QUANTIZED_BVH_NODE_getEscapeIndex(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node);

    public static final native boolean BT_QUANTIZED_BVH_NODE_isLeafNode(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node);

    public static final native int[] BT_QUANTIZED_BVH_NODE_quantizedAabbMax_get(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node);

    public static final native void BT_QUANTIZED_BVH_NODE_quantizedAabbMax_set(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node, int[] iArr);

    public static final native int[] BT_QUANTIZED_BVH_NODE_quantizedAabbMin_get(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node);

    public static final native void BT_QUANTIZED_BVH_NODE_quantizedAabbMin_set(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node, int[] iArr);

    public static final native void BT_QUANTIZED_BVH_NODE_setDataIndex(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node, int i);

    public static final native void BT_QUANTIZED_BVH_NODE_setEscapeIndex(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node, int i);

    public static final native boolean BT_QUANTIZED_BVH_NODE_testQuantizedBoxOverlapp(long j, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node, IntBuffer intBuffer, IntBuffer intBuffer2);

    public static final native int BT_SIMPLE_NULL_PAIR_get();

    public static final native long ClosestConvexResultCallback_SWIGUpcast(long j);

    public static final native float ClosestConvexResultCallback_addSingleResult(long j, ClosestConvexResultCallback closestConvexResultCallback, long j2, LocalConvexResult localConvexResult, boolean z);

    public static final native float ClosestConvexResultCallback_addSingleResultSwigExplicitClosestConvexResultCallback(long j, ClosestConvexResultCallback closestConvexResultCallback, long j2, LocalConvexResult localConvexResult, boolean z);

    public static final native void ClosestConvexResultCallback_change_ownership(ClosestConvexResultCallback closestConvexResultCallback, long j, boolean z);

    public static final native long ClosestConvexResultCallback_convexFromWorld_get(long j, ClosestConvexResultCallback closestConvexResultCallback);

    public static final native void ClosestConvexResultCallback_convexFromWorld_set(long j, ClosestConvexResultCallback closestConvexResultCallback, long j2, btVector3 btvector3);

    public static final native long ClosestConvexResultCallback_convexToWorld_get(long j, ClosestConvexResultCallback closestConvexResultCallback);

    public static final native void ClosestConvexResultCallback_convexToWorld_set(long j, ClosestConvexResultCallback closestConvexResultCallback, long j2, btVector3 btvector3);

    public static final native void ClosestConvexResultCallback_director_connect(ClosestConvexResultCallback closestConvexResultCallback, long j, boolean z, boolean z2);

    public static final native void ClosestConvexResultCallback_getConvexFromWorld(long j, ClosestConvexResultCallback closestConvexResultCallback, Vector3 vector3);

    public static final native void ClosestConvexResultCallback_getConvexToWorld(long j, ClosestConvexResultCallback closestConvexResultCallback, Vector3 vector3);

    public static final native void ClosestConvexResultCallback_getHitNormalWorld(long j, ClosestConvexResultCallback closestConvexResultCallback, Vector3 vector3);

    public static final native void ClosestConvexResultCallback_getHitPointWorld(long j, ClosestConvexResultCallback closestConvexResultCallback, Vector3 vector3);

    public static final native long ClosestConvexResultCallback_hitCollisionObject_get(long j, ClosestConvexResultCallback closestConvexResultCallback);

    public static final native void ClosestConvexResultCallback_hitCollisionObject_set(long j, ClosestConvexResultCallback closestConvexResultCallback, long j2, btCollisionObject btcollisionobject);

    public static final native void ClosestConvexResultCallback_setConvexToWorld(long j, ClosestConvexResultCallback closestConvexResultCallback, Vector3 vector3);

    public static final native void ClosestConvexResultCallback_setHitNormalWorld(long j, ClosestConvexResultCallback closestConvexResultCallback, Vector3 vector3);

    public static final native void ClosestConvexResultCallback_setHitPointWorld(long j, ClosestConvexResultCallback closestConvexResultCallback, Vector3 vector3);

    public static final native void ClosestConvexResultCallback_setRayFromWorld(long j, ClosestConvexResultCallback closestConvexResultCallback, Vector3 vector3);

    public static final native long ClosestNotMeConvexResultCallback_SWIGUpcast(long j);

    public static final native float ClosestNotMeConvexResultCallback_allowedPenetration_get(long j, ClosestNotMeConvexResultCallback closestNotMeConvexResultCallback);

    public static final native void ClosestNotMeConvexResultCallback_allowedPenetration_set(long j, ClosestNotMeConvexResultCallback closestNotMeConvexResultCallback, float f);

    public static final native long ClosestNotMeConvexResultCallback_me_get(long j, ClosestNotMeConvexResultCallback closestNotMeConvexResultCallback);

    public static final native void ClosestNotMeConvexResultCallback_me_set(long j, ClosestNotMeConvexResultCallback closestNotMeConvexResultCallback, long j2, btCollisionObject btcollisionobject);

    public static final native boolean ClosestNotMeConvexResultCallback_needsCollision(long j, ClosestNotMeConvexResultCallback closestNotMeConvexResultCallback, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native long ClosestNotMeRayResultCallback_SWIGUpcast(long j);

    public static final native long ClosestRayResultCallback_SWIGUpcast(long j);

    public static final native float ClosestRayResultCallback_addSingleResult(long j, ClosestRayResultCallback closestRayResultCallback, long j2, LocalRayResult localRayResult, boolean z);

    public static final native float ClosestRayResultCallback_addSingleResultSwigExplicitClosestRayResultCallback(long j, ClosestRayResultCallback closestRayResultCallback, long j2, LocalRayResult localRayResult, boolean z);

    public static final native void ClosestRayResultCallback_change_ownership(ClosestRayResultCallback closestRayResultCallback, long j, boolean z);

    public static final native void ClosestRayResultCallback_director_connect(ClosestRayResultCallback closestRayResultCallback, long j, boolean z, boolean z2);

    public static final native void ClosestRayResultCallback_getHitNormalWorld(long j, ClosestRayResultCallback closestRayResultCallback, Vector3 vector3);

    public static final native void ClosestRayResultCallback_getHitPointWorld(long j, ClosestRayResultCallback closestRayResultCallback, Vector3 vector3);

    public static final native void ClosestRayResultCallback_getRayFromWorld(long j, ClosestRayResultCallback closestRayResultCallback, Vector3 vector3);

    public static final native void ClosestRayResultCallback_getRayToWorld(long j, ClosestRayResultCallback closestRayResultCallback, Vector3 vector3);

    public static final native void ClosestRayResultCallback_setHitNormalWorld(long j, ClosestRayResultCallback closestRayResultCallback, Vector3 vector3);

    public static final native void ClosestRayResultCallback_setHitPointWorld(long j, ClosestRayResultCallback closestRayResultCallback, Vector3 vector3);

    public static final native void ClosestRayResultCallback_setRayFromWorld(long j, ClosestRayResultCallback closestRayResultCallback, Vector3 vector3);

    public static final native void ClosestRayResultCallback_setRayToWorld(long j, ClosestRayResultCallback closestRayResultCallback, Vector3 vector3);

    public static final native long CollisionObjectWrapper_getWrapper(long j, CollisionObjectWrapper collisionObjectWrapper);

    public static final native float ContactCache_cacheTime_get(long j, ContactCache contactCache);

    public static final native void ContactCache_cacheTime_set(long j, ContactCache contactCache, float f);

    public static final native void ContactCache_change_ownership(ContactCache contactCache, long j, boolean z);

    public static final native void ContactCache_clear(long j, ContactCache contactCache);

    public static final native void ContactCache_director_connect(ContactCache contactCache, long j, boolean z, boolean z2);

    public static final native void ContactCache_disable(long j, ContactCache contactCache);

    public static final native void ContactCache_enable(long j, ContactCache contactCache);

    public static final native boolean ContactCache_isEnabled(long j, ContactCache contactCache);

    public static final native void ContactCache_onContactEnded(long j, ContactCache contactCache, long j2, btCollisionObject btcollisionobject, boolean z, long j3, btCollisionObject btcollisionobject2, boolean z2);

    public static final native void ContactCache_onContactStarted(long j, ContactCache contactCache, long j2, btPersistentManifold btpersistentmanifold, boolean z, boolean z2);

    public static final native void ContactCache_update(long j, ContactCache contactCache, float f);

    public static final native void ContactListener_change_ownership(ContactListener contactListener, long j, boolean z);

    public static final native void ContactListener_director_connect(ContactListener contactListener, long j, boolean z, boolean z2);

    public static final native void ContactListener_disable(long j, ContactListener contactListener);

    public static final native void ContactListener_disableOnAdded(long j, ContactListener contactListener);

    public static final native void ContactListener_disableOnDestroyed(long j, ContactListener contactListener);

    public static final native void ContactListener_disableOnEnded(long j, ContactListener contactListener);

    public static final native void ContactListener_disableOnProcessed(long j, ContactListener contactListener);

    public static final native void ContactListener_disableOnStarted(long j, ContactListener contactListener);

    public static final native void ContactListener_enable(long j, ContactListener contactListener);

    public static final native void ContactListener_enableOnAdded(long j, ContactListener contactListener);

    public static final native void ContactListener_enableOnDestroyed(long j, ContactListener contactListener);

    public static final native void ContactListener_enableOnEnded(long j, ContactListener contactListener);

    public static final native void ContactListener_enableOnProcessed(long j, ContactListener contactListener);

    public static final native void ContactListener_enableOnStarted(long j, ContactListener contactListener);

    public static final native boolean ContactListener_isOnAddedEnabled(long j, ContactListener contactListener);

    public static final native boolean ContactListener_isOnDestroyedEnabled(long j, ContactListener contactListener);

    public static final native boolean ContactListener_isOnEndedEnabled(long j, ContactListener contactListener);

    public static final native boolean ContactListener_isOnProcessedEnabled(long j, ContactListener contactListener);

    public static final native boolean ContactListener_isOnStartedEnabled(long j, ContactListener contactListener);

    public static final native boolean ContactListener_onContactAdded__SWIG_0(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4);

    public static final native boolean ContactListener_onContactAdded__SWIG_1(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, long j3, btCollisionObject btcollisionobject, int i, int i2, long j4, btCollisionObject btcollisionobject2, int i3, int i4);

    public static final native boolean ContactListener_onContactAdded__SWIG_10(long j, ContactListener contactListener, long j2, btCollisionObject btcollisionobject, int i, int i2, boolean z, long j3, btCollisionObject btcollisionobject2, int i3, int i4, boolean z2);

    public static final native boolean ContactListener_onContactAdded__SWIG_11(long j, ContactListener contactListener, int i, int i2, int i3, boolean z, int i4, int i5, int i6, boolean z2);

    public static final native boolean ContactListener_onContactAdded__SWIG_2(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, int i, int i2, int i3, int i4, int i5, int i6);

    public static final native boolean ContactListener_onContactAdded__SWIG_3(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, boolean z, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4, boolean z2);

    public static final native boolean ContactListener_onContactAdded__SWIG_4(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, long j3, btCollisionObject btcollisionobject, int i, int i2, boolean z, long j4, btCollisionObject btcollisionobject2, int i3, int i4, boolean z2);

    public static final native boolean ContactListener_onContactAdded__SWIG_5(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, int i, int i2, int i3, boolean z, int i4, int i5, int i6, boolean z2);

    public static final native boolean ContactListener_onContactAdded__SWIG_6(long j, ContactListener contactListener, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4);

    public static final native boolean ContactListener_onContactAdded__SWIG_7(long j, ContactListener contactListener, long j2, btCollisionObject btcollisionobject, int i, int i2, long j3, btCollisionObject btcollisionobject2, int i3, int i4);

    public static final native boolean ContactListener_onContactAdded__SWIG_8(long j, ContactListener contactListener, int i, int i2, int i3, int i4, int i5, int i6);

    public static final native boolean ContactListener_onContactAdded__SWIG_9(long j, ContactListener contactListener, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, boolean z, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4, boolean z2);

    public static final native void ContactListener_onContactDestroyed(long j, ContactListener contactListener, int i);

    public static final native void ContactListener_onContactEnded__SWIG_0(long j, ContactListener contactListener, long j2, btPersistentManifold btpersistentmanifold);

    public static final native void ContactListener_onContactEnded__SWIG_1(long j, ContactListener contactListener, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native void ContactListener_onContactEnded__SWIG_2(long j, ContactListener contactListener, int i, int i2);

    public static final native void ContactListener_onContactEnded__SWIG_3(long j, ContactListener contactListener, long j2, btPersistentManifold btpersistentmanifold, boolean z, boolean z2);

    public static final native void ContactListener_onContactEnded__SWIG_4(long j, ContactListener contactListener, long j2, btCollisionObject btcollisionobject, boolean z, long j3, btCollisionObject btcollisionobject2, boolean z2);

    public static final native void ContactListener_onContactEnded__SWIG_5(long j, ContactListener contactListener, int i, boolean z, int i2, boolean z2);

    public static final native void ContactListener_onContactProcessed__SWIG_0(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, long j3, btCollisionObject btcollisionobject, long j4, btCollisionObject btcollisionobject2);

    public static final native void ContactListener_onContactProcessed__SWIG_1(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, int i, int i2);

    public static final native void ContactListener_onContactProcessed__SWIG_2(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, long j3, btCollisionObject btcollisionobject, boolean z, long j4, btCollisionObject btcollisionobject2, boolean z2);

    public static final native void ContactListener_onContactProcessed__SWIG_3(long j, ContactListener contactListener, long j2, btManifoldPoint btmanifoldpoint, int i, boolean z, int i2, boolean z2);

    public static final native void ContactListener_onContactProcessed__SWIG_4(long j, ContactListener contactListener, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native void ContactListener_onContactProcessed__SWIG_5(long j, ContactListener contactListener, int i, int i2);

    public static final native void ContactListener_onContactProcessed__SWIG_6(long j, ContactListener contactListener, long j2, btCollisionObject btcollisionobject, boolean z, long j3, btCollisionObject btcollisionobject2, boolean z2);

    public static final native void ContactListener_onContactProcessed__SWIG_7(long j, ContactListener contactListener, int i, boolean z, int i2, boolean z2);

    public static final native void ContactListener_onContactStarted__SWIG_0(long j, ContactListener contactListener, long j2, btPersistentManifold btpersistentmanifold);

    public static final native void ContactListener_onContactStarted__SWIG_1(long j, ContactListener contactListener, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native void ContactListener_onContactStarted__SWIG_2(long j, ContactListener contactListener, int i, int i2);

    public static final native void ContactListener_onContactStarted__SWIG_3(long j, ContactListener contactListener, long j2, btPersistentManifold btpersistentmanifold, boolean z, boolean z2);

    public static final native void ContactListener_onContactStarted__SWIG_4(long j, ContactListener contactListener, long j2, btCollisionObject btcollisionobject, boolean z, long j3, btCollisionObject btcollisionobject2, boolean z2);

    public static final native void ContactListener_onContactStarted__SWIG_5(long j, ContactListener contactListener, int i, boolean z, int i2, boolean z2);

    public static final native boolean ContactListener_setEvents(long j, ContactListener contactListener);

    public static final native float ContactResultCallback_addSingleResult(long j, ContactResultCallback contactResultCallback, long j2, btManifoldPoint btmanifoldpoint, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, int i, int i2, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, int i3, int i4);

    public static final native void ContactResultCallback_change_ownership(ContactResultCallback contactResultCallback, long j, boolean z);

    public static final native float ContactResultCallback_closestDistanceThreshold_get(long j, ContactResultCallback contactResultCallback);

    public static final native void ContactResultCallback_closestDistanceThreshold_set(long j, ContactResultCallback contactResultCallback, float f);

    public static final native int ContactResultCallback_collisionFilterGroup_get(long j, ContactResultCallback contactResultCallback);

    public static final native void ContactResultCallback_collisionFilterGroup_set(long j, ContactResultCallback contactResultCallback, int i);

    public static final native int ContactResultCallback_collisionFilterMask_get(long j, ContactResultCallback contactResultCallback);

    public static final native void ContactResultCallback_collisionFilterMask_set(long j, ContactResultCallback contactResultCallback, int i);

    public static final native void ContactResultCallback_director_connect(ContactResultCallback contactResultCallback, long j, boolean z, boolean z2);

    public static final native boolean ContactResultCallback_needsCollision(long j, ContactResultCallback contactResultCallback, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native boolean ContactResultCallback_needsCollisionSwigExplicitContactResultCallback(long j, ContactResultCallback contactResultCallback, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native float ConvexResultCallback_addSingleResult(long j, ConvexResultCallback convexResultCallback, long j2, LocalConvexResult localConvexResult, boolean z);

    public static final native void ConvexResultCallback_change_ownership(ConvexResultCallback convexResultCallback, long j, boolean z);

    public static final native float ConvexResultCallback_closestHitFraction_get(long j, ConvexResultCallback convexResultCallback);

    public static final native void ConvexResultCallback_closestHitFraction_set(long j, ConvexResultCallback convexResultCallback, float f);

    public static final native int ConvexResultCallback_collisionFilterGroup_get(long j, ConvexResultCallback convexResultCallback);

    public static final native void ConvexResultCallback_collisionFilterGroup_set(long j, ConvexResultCallback convexResultCallback, int i);

    public static final native int ConvexResultCallback_collisionFilterMask_get(long j, ConvexResultCallback convexResultCallback);

    public static final native void ConvexResultCallback_collisionFilterMask_set(long j, ConvexResultCallback convexResultCallback, int i);

    public static final native void ConvexResultCallback_director_connect(ConvexResultCallback convexResultCallback, long j, boolean z, boolean z2);

    public static final native boolean ConvexResultCallback_hasHit(long j, ConvexResultCallback convexResultCallback);

    public static final native boolean ConvexResultCallback_needsCollision(long j, ConvexResultCallback convexResultCallback, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native boolean ConvexResultCallback_needsCollisionSwigExplicitConvexResultCallback(long j, ConvexResultCallback convexResultCallback, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native long CustomCollisionDispatcher_SWIGUpcast(long j);

    public static final native void CustomCollisionDispatcher_change_ownership(CustomCollisionDispatcher customCollisionDispatcher, long j, boolean z);

    public static final native void CustomCollisionDispatcher_director_connect(CustomCollisionDispatcher customCollisionDispatcher, long j, boolean z, boolean z2);

    public static final native boolean CustomCollisionDispatcher_needsCollision(long j, CustomCollisionDispatcher customCollisionDispatcher, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native boolean CustomCollisionDispatcher_needsCollisionSwigExplicitCustomCollisionDispatcher(long j, CustomCollisionDispatcher customCollisionDispatcher, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native boolean CustomCollisionDispatcher_needsResponse(long j, CustomCollisionDispatcher customCollisionDispatcher, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native boolean CustomCollisionDispatcher_needsResponseSwigExplicitCustomCollisionDispatcher(long j, CustomCollisionDispatcher customCollisionDispatcher, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native void GIM_AABB_appy_transform(long j, GIM_AABB gim_aabb, Matrix4 matrix4);

    public static final native boolean GIM_AABB_collide_plane(long j, GIM_AABB gim_aabb, long j2, btVector4 btvector4);

    public static final native boolean GIM_AABB_collide_ray(long j, GIM_AABB gim_aabb, Vector3 vector3, Vector3 vector4);

    public static final native boolean GIM_AABB_collide_triangle_exact(long j, GIM_AABB gim_aabb, Vector3 vector3, Vector3 vector4, Vector3 vector5, long j2, btVector4 btvector4);

    public static final native void GIM_AABB_copy_with_margin(long j, GIM_AABB gim_aabb, long j2, GIM_AABB gim_aabb2, float f);

    public static final native void GIM_AABB_find_intersection(long j, GIM_AABB gim_aabb, long j2, GIM_AABB gim_aabb2, long j3, GIM_AABB gim_aabb3);

    public static final native void GIM_AABB_get_center_extend(long j, GIM_AABB gim_aabb, Vector3 vector3, Vector3 vector4);

    public static final native boolean GIM_AABB_has_collision(long j, GIM_AABB gim_aabb, long j2, GIM_AABB gim_aabb2);

    public static final native void GIM_AABB_increment_margin(long j, GIM_AABB gim_aabb, float f);

    public static final native void GIM_AABB_invalidate(long j, GIM_AABB gim_aabb);

    public static final native long GIM_AABB_max_get(long j, GIM_AABB gim_aabb);

    public static final native void GIM_AABB_max_set(long j, GIM_AABB gim_aabb, long j2, btVector3 btvector3);

    public static final native void GIM_AABB_merge(long j, GIM_AABB gim_aabb, long j2, GIM_AABB gim_aabb2);

    public static final native long GIM_AABB_min_get(long j, GIM_AABB gim_aabb);

    public static final native void GIM_AABB_min_set(long j, GIM_AABB gim_aabb, long j2, btVector3 btvector3);

    public static final native boolean GIM_AABB_overlapping_trans_cache(long j, GIM_AABB gim_aabb, long j2, GIM_AABB gim_aabb2, long j3, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache, boolean z);

    public static final native boolean GIM_AABB_overlapping_trans_conservative(long j, GIM_AABB gim_aabb, long j2, GIM_AABB gim_aabb2, Matrix4 matrix4);

    public static final native int GIM_AABB_plane_classify(long j, GIM_AABB gim_aabb, long j2, btVector4 btvector4);

    public static final native void GIM_AABB_projection_interval(long j, GIM_AABB gim_aabb, Vector3 vector3, long j2, long j3);

    public static final native long GIM_BOX_BOX_TRANSFORM_CACHE_AR_get(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache);

    public static final native void GIM_BOX_BOX_TRANSFORM_CACHE_AR_set(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long GIM_BOX_BOX_TRANSFORM_CACHE_R1to0_get(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache);

    public static final native void GIM_BOX_BOX_TRANSFORM_CACHE_R1to0_set(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long GIM_BOX_BOX_TRANSFORM_CACHE_T1to0_get(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache);

    public static final native void GIM_BOX_BOX_TRANSFORM_CACHE_T1to0_set(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache, long j2, btVector3 btvector3);

    public static final native void GIM_BOX_BOX_TRANSFORM_CACHE_calc_absolute_matrix(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache);

    public static final native void GIM_BOX_BOX_TRANSFORM_CACHE_calc_from_full_invert(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void GIM_BOX_BOX_TRANSFORM_CACHE_calc_from_homogenic(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache, Matrix4 matrix4, Matrix4 matrix5);

    public static final native Vector3 GIM_BOX_BOX_TRANSFORM_CACHE_transform(long j, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache, Vector3 vector3);

    public static final native long GIM_BVH_DATA_ARRAY_SWIGUpcast(long j);

    public static final native long GIM_BVH_DATA_bound_get(long j, GIM_BVH_DATA gim_bvh_data);

    public static final native void GIM_BVH_DATA_bound_set(long j, GIM_BVH_DATA gim_bvh_data, long j2, btAABB btaabb);

    public static final native int GIM_BVH_DATA_data_get(long j, GIM_BVH_DATA gim_bvh_data);

    public static final native void GIM_BVH_DATA_data_set(long j, GIM_BVH_DATA gim_bvh_data, int i);

    public static final native long GIM_BVH_TREE_NODE_ARRAY_SWIGUpcast(long j);

    public static final native long GIM_BVH_TREE_NODE_bound_get(long j, GIM_BVH_TREE_NODE gim_bvh_tree_node);

    public static final native void GIM_BVH_TREE_NODE_bound_set(long j, GIM_BVH_TREE_NODE gim_bvh_tree_node, long j2, btAABB btaabb);

    public static final native int GIM_BVH_TREE_NODE_getDataIndex(long j, GIM_BVH_TREE_NODE gim_bvh_tree_node);

    public static final native int GIM_BVH_TREE_NODE_getEscapeIndex(long j, GIM_BVH_TREE_NODE gim_bvh_tree_node);

    public static final native boolean GIM_BVH_TREE_NODE_isLeafNode(long j, GIM_BVH_TREE_NODE gim_bvh_tree_node);

    public static final native void GIM_BVH_TREE_NODE_setDataIndex(long j, GIM_BVH_TREE_NODE gim_bvh_tree_node, int i);

    public static final native void GIM_BVH_TREE_NODE_setEscapeIndex(long j, GIM_BVH_TREE_NODE gim_bvh_tree_node, int i);

    public static final native long GIM_CONTACT_calc_key_contact(long j, GIM_CONTACT gim_contact);

    public static final native float GIM_CONTACT_depth_get(long j, GIM_CONTACT gim_contact);

    public static final native void GIM_CONTACT_depth_set(long j, GIM_CONTACT gim_contact, float f);

    public static final native float GIM_CONTACT_distance_get(long j, GIM_CONTACT gim_contact);

    public static final native void GIM_CONTACT_distance_set(long j, GIM_CONTACT gim_contact, float f);

    public static final native int GIM_CONTACT_feature1_get(long j, GIM_CONTACT gim_contact);

    public static final native void GIM_CONTACT_feature1_set(long j, GIM_CONTACT gim_contact, int i);

    public static final native int GIM_CONTACT_feature2_get(long j, GIM_CONTACT gim_contact);

    public static final native void GIM_CONTACT_feature2_set(long j, GIM_CONTACT gim_contact, int i);

    public static final native void GIM_CONTACT_interpolate_normals(long j, GIM_CONTACT gim_contact, long j2, btVector3 btvector3, int i);

    public static final native long GIM_CONTACT_normal_get(long j, GIM_CONTACT gim_contact);

    public static final native void GIM_CONTACT_normal_set(long j, GIM_CONTACT gim_contact, long j2, btVector3 btvector3);

    public static final native long GIM_CONTACT_point_get(long j, GIM_CONTACT gim_contact);

    public static final native void GIM_CONTACT_point_set(long j, GIM_CONTACT gim_contact, long j2, btVector3 btvector3);

    public static final native int GIM_PAIR_index1_get(long j, GIM_PAIR gim_pair);

    public static final native void GIM_PAIR_index1_set(long j, GIM_PAIR gim_pair, int i);

    public static final native int GIM_PAIR_index2_get(long j, GIM_PAIR gim_pair);

    public static final native void GIM_PAIR_index2_set(long j, GIM_PAIR gim_pair, int i);

    public static final native long GIM_QUANTIZED_BVH_NODE_ARRAY_SWIGUpcast(long j);

    public static final native int GIM_RSORT_TOKEN_COMPARATOR_operatorFunctionCall(long j, GIM_RSORT_TOKEN_COMPARATOR gim_rsort_token_comparator, long j2, GIM_RSORT_TOKEN gim_rsort_token, long j3, GIM_RSORT_TOKEN gim_rsort_token2);

    public static final native long GIM_RSORT_TOKEN_key_get(long j, GIM_RSORT_TOKEN gim_rsort_token);

    public static final native void GIM_RSORT_TOKEN_key_set(long j, GIM_RSORT_TOKEN gim_rsort_token, long j2);

    public static final native boolean GIM_RSORT_TOKEN_operatorGreaterThan(long j, GIM_RSORT_TOKEN gim_rsort_token, long j2, GIM_RSORT_TOKEN gim_rsort_token2);

    public static final native boolean GIM_RSORT_TOKEN_operatorLessThan(long j, GIM_RSORT_TOKEN gim_rsort_token, long j2, GIM_RSORT_TOKEN gim_rsort_token2);

    public static final native long GIM_RSORT_TOKEN_value_get(long j, GIM_RSORT_TOKEN gim_rsort_token);

    public static final native void GIM_RSORT_TOKEN_value_set(long j, GIM_RSORT_TOKEN gim_rsort_token, long j2);

    public static final native void GIM_TRIANGLE_CONTACT_DATA_copy_from(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data, long j2, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data2);

    public static final native void GIM_TRIANGLE_CONTACT_DATA_merge_points(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data, long j2, btVector4 btvector4, float f, long j3, btVector3 btvector3, long j4);

    public static final native float GIM_TRIANGLE_CONTACT_DATA_penetration_depth_get(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data);

    public static final native void GIM_TRIANGLE_CONTACT_DATA_penetration_depth_set(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data, float f);

    public static final native long GIM_TRIANGLE_CONTACT_DATA_point_count_get(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data);

    public static final native void GIM_TRIANGLE_CONTACT_DATA_point_count_set(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data, long j2);

    public static final native long GIM_TRIANGLE_CONTACT_DATA_points_get(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data);

    public static final native void GIM_TRIANGLE_CONTACT_DATA_points_set(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data, long j2, btVector3 btvector3);

    public static final native long GIM_TRIANGLE_CONTACT_DATA_separating_normal_get(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data);

    public static final native void GIM_TRIANGLE_CONTACT_DATA_separating_normal_set(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data, long j2, btVector4 btvector4);

    public static final native void GIM_TRIANGLE_CONTACT_copy_from(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact, long j2, GIM_TRIANGLE_CONTACT gim_triangle_contact2);

    public static final native void GIM_TRIANGLE_CONTACT_merge_points(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact, long j2, btVector4 btvector4, float f, long j3, btVector3 btvector3, int i);

    public static final native float GIM_TRIANGLE_CONTACT_penetration_depth_get(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact);

    public static final native void GIM_TRIANGLE_CONTACT_penetration_depth_set(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact, float f);

    public static final native int GIM_TRIANGLE_CONTACT_point_count_get(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact);

    public static final native void GIM_TRIANGLE_CONTACT_point_count_set(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact, int i);

    public static final native long GIM_TRIANGLE_CONTACT_points_get(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact);

    public static final native void GIM_TRIANGLE_CONTACT_points_set(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact, long j2, btVector3 btvector3);

    public static final native long GIM_TRIANGLE_CONTACT_separating_normal_get(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact);

    public static final native void GIM_TRIANGLE_CONTACT_separating_normal_set(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact, long j2, btVector4 btvector4);

    public static final native void GIM_TRIANGLE_apply_transform(long j, GIM_TRIANGLE gim_triangle, Matrix4 matrix4);

    public static final native boolean GIM_TRIANGLE_collide_triangle(long j, GIM_TRIANGLE gim_triangle, long j2, GIM_TRIANGLE gim_triangle2, long j3, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data);

    public static final native boolean GIM_TRIANGLE_collide_triangle_hard_test(long j, GIM_TRIANGLE gim_triangle, long j2, GIM_TRIANGLE gim_triangle2, long j3, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data);

    public static final native long GIM_TRIANGLE_get_box(long j, GIM_TRIANGLE gim_triangle);

    public static final native void GIM_TRIANGLE_get_edge_plane(long j, GIM_TRIANGLE gim_triangle, long j2, Vector3 vector3, long j3, btVector4 btvector4);

    public static final native void GIM_TRIANGLE_get_normal(long j, GIM_TRIANGLE gim_triangle, Vector3 vector3);

    public static final native void GIM_TRIANGLE_get_plane(long j, GIM_TRIANGLE gim_triangle, long j2, btVector4 btvector4);

    public static final native void GIM_TRIANGLE_get_triangle_transform(long j, GIM_TRIANGLE gim_triangle, Matrix4 matrix4);

    public static final native boolean GIM_TRIANGLE_get_uv_parameters(long j, GIM_TRIANGLE gim_triangle, Vector3 vector3, Vector3 vector4, long j2, long j3);

    public static final native boolean GIM_TRIANGLE_is_point_inside(long j, GIM_TRIANGLE gim_triangle, Vector3 vector3, Vector3 vector4);

    public static final native float GIM_TRIANGLE_margin_get(long j, GIM_TRIANGLE gim_triangle);

    public static final native void GIM_TRIANGLE_margin_set(long j, GIM_TRIANGLE gim_triangle, float f);

    public static final native boolean GIM_TRIANGLE_ray_collision__SWIG_0(long j, GIM_TRIANGLE gim_triangle, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, long j2, float f);

    public static final native boolean GIM_TRIANGLE_ray_collision__SWIG_1(long j, GIM_TRIANGLE gim_triangle, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, long j2);

    public static final native boolean GIM_TRIANGLE_ray_collision_front_side__SWIG_0(long j, GIM_TRIANGLE gim_triangle, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, long j2, float f);

    public static final native boolean GIM_TRIANGLE_ray_collision_front_side__SWIG_1(long j, GIM_TRIANGLE gim_triangle, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, long j2);

    public static final native long GIM_TRIANGLE_vertices_get(long j, GIM_TRIANGLE gim_triangle);

    public static final native void GIM_TRIANGLE_vertices_set(long j, GIM_TRIANGLE gim_triangle, long j2, btVector3 btvector3);

    public static final native int GdxCollisionObjectBridge_contactCallbackFilter_get(long j, GdxCollisionObjectBridge gdxCollisionObjectBridge);

    public static final native void GdxCollisionObjectBridge_contactCallbackFilter_set(long j, GdxCollisionObjectBridge gdxCollisionObjectBridge, int i);

    public static final native int GdxCollisionObjectBridge_contactCallbackFlag_get(long j, GdxCollisionObjectBridge gdxCollisionObjectBridge);

    public static final native void GdxCollisionObjectBridge_contactCallbackFlag_set(long j, GdxCollisionObjectBridge gdxCollisionObjectBridge, int i);

    public static final native int GdxCollisionObjectBridge_userValue_get(long j, GdxCollisionObjectBridge gdxCollisionObjectBridge);

    public static final native void GdxCollisionObjectBridge_userValue_set(long j, GdxCollisionObjectBridge gdxCollisionObjectBridge, int i);

    public static final native boolean ICollide_AllLeaves(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode);

    public static final native boolean ICollide_AllLeavesSwigExplicitICollide(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode);

    public static final native boolean ICollide_Descent(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode);

    public static final native boolean ICollide_DescentSwigExplicitICollide(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode);

    public static final native void ICollide_ProcessSwigExplicitICollide__SWIG_0(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode, long j3, btDbvtNode btdbvtnode2);

    public static final native void ICollide_ProcessSwigExplicitICollide__SWIG_1(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode);

    public static final native void ICollide_ProcessSwigExplicitICollide__SWIG_2(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode, float f);

    public static final native void ICollide_Process__SWIG_0(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode, long j3, btDbvtNode btdbvtnode2);

    public static final native void ICollide_Process__SWIG_1(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode);

    public static final native void ICollide_Process__SWIG_2(long j, ICollide iCollide, long j2, btDbvtNode btdbvtnode, float f);

    public static final native void ICollide_change_ownership(ICollide iCollide, long j, boolean z);

    public static final native void ICollide_director_connect(ICollide iCollide, long j, boolean z, boolean z2);

    public static final native boolean Intersect__SWIG_0(long j, btDbvtAabbMm btdbvtaabbmm, long j2, btDbvtAabbMm btdbvtaabbmm2);

    public static final native boolean Intersect__SWIG_1(long j, btDbvtAabbMm btdbvtaabbmm, Vector3 vector3);

    public static final native void LocalConvexResult_getHitNormalLocal(long j, LocalConvexResult localConvexResult, Vector3 vector3);

    public static final native void LocalConvexResult_getHitPointLocal(long j, LocalConvexResult localConvexResult, Vector3 vector3);

    public static final native long LocalConvexResult_hitCollisionObject_get(long j, LocalConvexResult localConvexResult);

    public static final native void LocalConvexResult_hitCollisionObject_set(long j, LocalConvexResult localConvexResult, long j2, btCollisionObject btcollisionobject);

    public static final native float LocalConvexResult_hitFraction_get(long j, LocalConvexResult localConvexResult);

    public static final native void LocalConvexResult_hitFraction_set(long j, LocalConvexResult localConvexResult, float f);

    public static final native long LocalConvexResult_localShapeInfo_get(long j, LocalConvexResult localConvexResult);

    public static final native void LocalConvexResult_localShapeInfo_set(long j, LocalConvexResult localConvexResult, long j2, LocalShapeInfo localShapeInfo);

    public static final native void LocalConvexResult_setHitNormalLocal(long j, LocalConvexResult localConvexResult, Vector3 vector3);

    public static final native void LocalConvexResult_setHitPointLocal(long j, LocalConvexResult localConvexResult, Vector3 vector3);

    public static final native long LocalRayResult_collisionObject_get(long j, LocalRayResult localRayResult);

    public static final native void LocalRayResult_collisionObject_set(long j, LocalRayResult localRayResult, long j2, btCollisionObject btcollisionobject);

    public static final native float LocalRayResult_hitFraction_get(long j, LocalRayResult localRayResult);

    public static final native void LocalRayResult_hitFraction_set(long j, LocalRayResult localRayResult, float f);

    public static final native long LocalRayResult_hitNormalLocal_get(long j, LocalRayResult localRayResult);

    public static final native void LocalRayResult_hitNormalLocal_set(long j, LocalRayResult localRayResult, long j2, btVector3 btvector3);

    public static final native long LocalRayResult_localShapeInfo_get(long j, LocalRayResult localRayResult);

    public static final native void LocalRayResult_localShapeInfo_set(long j, LocalRayResult localRayResult, long j2, LocalShapeInfo localShapeInfo);

    public static final native int LocalShapeInfo_shapePart_get(long j, LocalShapeInfo localShapeInfo);

    public static final native void LocalShapeInfo_shapePart_set(long j, LocalShapeInfo localShapeInfo, int i);

    public static final native int LocalShapeInfo_triangleIndex_get(long j, LocalShapeInfo localShapeInfo);

    public static final native void LocalShapeInfo_triangleIndex_set(long j, LocalShapeInfo localShapeInfo, int i);

    public static final native void Merge(long j, btDbvtAabbMm btdbvtaabbmm, long j2, btDbvtAabbMm btdbvtaabbmm2, long j3, btDbvtAabbMm btdbvtaabbmm3);

    public static final native long MyCallback_SWIGUpcast(long j);

    public static final native int MyCallback_ignorePart_get(long j, MyCallback myCallback);

    public static final native void MyCallback_ignorePart_set(long j, MyCallback myCallback, int i);

    public static final native int MyCallback_ignoreTriangleIndex_get(long j, MyCallback myCallback);

    public static final native void MyCallback_ignoreTriangleIndex_set(long j, MyCallback myCallback, int i);

    public static final native long MyInternalTriangleIndexCallback_SWIGUpcast(long j);

    public static final native long MyInternalTriangleIndexCallback_colShape_get(long j, MyInternalTriangleIndexCallback myInternalTriangleIndexCallback);

    public static final native void MyInternalTriangleIndexCallback_colShape_set(long j, MyInternalTriangleIndexCallback myInternalTriangleIndexCallback, long j2, btCompoundShape btcompoundshape);

    public static final native float MyInternalTriangleIndexCallback_depth_get(long j, MyInternalTriangleIndexCallback myInternalTriangleIndexCallback);

    public static final native void MyInternalTriangleIndexCallback_depth_set(long j, MyInternalTriangleIndexCallback myInternalTriangleIndexCallback, float f);

    public static final native long MyInternalTriangleIndexCallback_gimpactShape_get(long j, MyInternalTriangleIndexCallback myInternalTriangleIndexCallback);

    public static final native void MyInternalTriangleIndexCallback_gimpactShape_set(long j, MyInternalTriangleIndexCallback myInternalTriangleIndexCallback, long j2, btGImpactMeshShape btgimpactmeshshape);

    public static final native boolean NotEqual(long j, btDbvtAabbMm btdbvtaabbmm, long j2, btDbvtAabbMm btdbvtaabbmm2);

    public static final native float Proximity(long j, btDbvtAabbMm btdbvtaabbmm, long j2, btDbvtAabbMm btdbvtaabbmm2);

    public static final native float RayResultCallback_addSingleResult(long j, RayResultCallback rayResultCallback, long j2, LocalRayResult localRayResult, boolean z);

    public static final native void RayResultCallback_change_ownership(RayResultCallback rayResultCallback, long j, boolean z);

    public static final native float RayResultCallback_closestHitFraction_get(long j, RayResultCallback rayResultCallback);

    public static final native void RayResultCallback_closestHitFraction_set(long j, RayResultCallback rayResultCallback, float f);

    public static final native int RayResultCallback_collisionFilterGroup_get(long j, RayResultCallback rayResultCallback);

    public static final native void RayResultCallback_collisionFilterGroup_set(long j, RayResultCallback rayResultCallback, int i);

    public static final native int RayResultCallback_collisionFilterMask_get(long j, RayResultCallback rayResultCallback);

    public static final native void RayResultCallback_collisionFilterMask_set(long j, RayResultCallback rayResultCallback, int i);

    public static final native long RayResultCallback_collisionObject_get(long j, RayResultCallback rayResultCallback);

    public static final native void RayResultCallback_collisionObject_set(long j, RayResultCallback rayResultCallback, long j2, btCollisionObject btcollisionobject);

    public static final native void RayResultCallback_director_connect(RayResultCallback rayResultCallback, long j, boolean z, boolean z2);

    public static final native long RayResultCallback_flags_get(long j, RayResultCallback rayResultCallback);

    public static final native void RayResultCallback_flags_set(long j, RayResultCallback rayResultCallback, long j2);

    public static final native boolean RayResultCallback_hasHit(long j, RayResultCallback rayResultCallback);

    public static final native boolean RayResultCallback_needsCollision(long j, RayResultCallback rayResultCallback, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native boolean RayResultCallback_needsCollisionSwigExplicitRayResultCallback(long j, RayResultCallback rayResultCallback, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native int Select(long j, btDbvtAabbMm btdbvtaabbmm, long j2, btDbvtAabbMm btdbvtaabbmm2, long j3, btDbvtAabbMm btdbvtaabbmm3);

    public static final native long SphereTriangleDetector_SWIGUpcast(long j);

    public static final native boolean SphereTriangleDetector_collide(long j, SphereTriangleDetector sphereTriangleDetector, Vector3 vector3, Vector3 vector4, Vector3 vector5, long j2, long j3, float f);

    public static final native void SphereTriangleDetector_getClosestPoints__SWIG_0(long j, SphereTriangleDetector sphereTriangleDetector, long j2, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j3, btDiscreteCollisionDetectorInterface.Result result, long j4, btIDebugDraw btidebugdraw, boolean z);

    public static final native void SphereTriangleDetector_getClosestPoints__SWIG_1(long j, SphereTriangleDetector sphereTriangleDetector, long j2, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j3, btDiscreteCollisionDetectorInterface.Result result, long j4, btIDebugDraw btidebugdraw);

    public static final native int _btMprSimplex_t_last_get(long j, _btMprSimplex_t _btmprsimplex_t);

    public static final native void _btMprSimplex_t_last_set(long j, _btMprSimplex_t _btmprsimplex_t, int i);

    public static final native long _btMprSimplex_t_ps_get(long j, _btMprSimplex_t _btmprsimplex_t);

    public static final native void _btMprSimplex_t_ps_set(long j, _btMprSimplex_t _btmprsimplex_t, long j2, _btMprSupport_t _btmprsupport_t);

    public static final native long _btMprSupport_t_v1_get(long j, _btMprSupport_t _btmprsupport_t);

    public static final native void _btMprSupport_t_v1_set(long j, _btMprSupport_t _btmprsupport_t, long j2, btVector3 btvector3);

    public static final native long _btMprSupport_t_v2_get(long j, _btMprSupport_t _btmprsupport_t);

    public static final native void _btMprSupport_t_v2_set(long j, _btMprSupport_t _btmprsupport_t, long j2, btVector3 btvector3);

    public static final native long _btMprSupport_t_v_get(long j, _btMprSupport_t _btmprsupport_t);

    public static final native void _btMprSupport_t_v_set(long j, _btMprSupport_t _btmprsupport_t, long j2, btVector3 btvector3);

    public static final native float _btMprVec3PointSegmentDist2(long j, btVector3 btvector3, long j2, btVector3 btvector4, long j3, btVector3 btvector5, long j4, btVector3 btvector6);

    public static final native long bt32BitAxisSweep3_SWIGUpcast(long j);

    public static final native void btAABB_appy_transform(long j, btAABB btaabb, Matrix4 matrix4);

    public static final native void btAABB_appy_transform_trans_cache(long j, btAABB btaabb, long j2, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache);

    public static final native boolean btAABB_collide_plane(long j, btAABB btaabb, long j2, btVector4 btvector4);

    public static final native boolean btAABB_collide_ray(long j, btAABB btaabb, Vector3 vector3, Vector3 vector4);

    public static final native boolean btAABB_collide_triangle_exact(long j, btAABB btaabb, Vector3 vector3, Vector3 vector4, Vector3 vector5, long j2, btVector4 btvector4);

    public static final native void btAABB_copy_with_margin(long j, btAABB btaabb, long j2, btAABB btaabb2, float f);

    public static final native void btAABB_find_intersection(long j, btAABB btaabb, long j2, btAABB btaabb2, long j3, btAABB btaabb3);

    public static final native void btAABB_get_center_extend(long j, btAABB btaabb, Vector3 vector3, Vector3 vector4);

    public static final native boolean btAABB_has_collision(long j, btAABB btaabb, long j2, btAABB btaabb2);

    public static final native void btAABB_increment_margin(long j, btAABB btaabb, float f);

    public static final native void btAABB_invalidate(long j, btAABB btaabb);

    public static final native long btAABB_max_get(long j, btAABB btaabb);

    public static final native void btAABB_max_set(long j, btAABB btaabb, long j2, btVector3 btvector3);

    public static final native void btAABB_merge(long j, btAABB btaabb, long j2, btAABB btaabb2);

    public static final native long btAABB_min_get(long j, btAABB btaabb);

    public static final native void btAABB_min_set(long j, btAABB btaabb, long j2, btVector3 btvector3);

    public static final native boolean btAABB_overlapping_trans_cache(long j, btAABB btaabb, long j2, btAABB btaabb2, long j3, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache, boolean z);

    public static final native boolean btAABB_overlapping_trans_conservative(long j, btAABB btaabb, long j2, btAABB btaabb2, Matrix4 matrix4);

    public static final native boolean btAABB_overlapping_trans_conservative2(long j, btAABB btaabb, long j2, btAABB btaabb2, long j3, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache);

    public static final native int btAABB_plane_classify(long j, btAABB btaabb, long j2, btVector4 btvector4);

    public static final native void btAABB_projection_interval(long j, btAABB btaabb, Vector3 vector3, long j2, long j3);

    public static final native long btActivatingCollisionAlgorithm_SWIGUpcast(long j);

    public static final native void btAdjustInternalEdgeContacts__SWIG_0(long j, btManifoldPoint btmanifoldpoint, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, int i, int i2, int i3);

    public static final native void btAdjustInternalEdgeContacts__SWIG_1(long j, btManifoldPoint btmanifoldpoint, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, int i, int i2);

    public static final native long btAxisSweep3InternalInt_Edge_IsMax(long j, btAxisSweep3InternalInt.Edge edge);

    public static final native long btAxisSweep3InternalInt_Edge_handle_get(long j, btAxisSweep3InternalInt.Edge edge);

    public static final native void btAxisSweep3InternalInt_Edge_handle_set(long j, btAxisSweep3InternalInt.Edge edge, long j2);

    public static final native long btAxisSweep3InternalInt_Edge_pos_get(long j, btAxisSweep3InternalInt.Edge edge);

    public static final native void btAxisSweep3InternalInt_Edge_pos_set(long j, btAxisSweep3InternalInt.Edge edge, long j2);

    public static final native long btAxisSweep3InternalInt_Handle_GetNextFree(long j, btAxisSweep3InternalInt.Handle handle);

    public static final native long btAxisSweep3InternalInt_Handle_SWIGUpcast(long j);

    public static final native void btAxisSweep3InternalInt_Handle_SetNextFree(long j, btAxisSweep3InternalInt.Handle handle, long j2);

    public static final native long btAxisSweep3InternalInt_Handle_dbvtProxy_get(long j, btAxisSweep3InternalInt.Handle handle);

    public static final native void btAxisSweep3InternalInt_Handle_dbvtProxy_set(long j, btAxisSweep3InternalInt.Handle handle, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native long[] btAxisSweep3InternalInt_Handle_maxEdges_get(long j, btAxisSweep3InternalInt.Handle handle);

    public static final native void btAxisSweep3InternalInt_Handle_maxEdges_set(long j, btAxisSweep3InternalInt.Handle handle, long[] jArr);

    public static final native long[] btAxisSweep3InternalInt_Handle_minEdges_get(long j, btAxisSweep3InternalInt.Handle handle);

    public static final native void btAxisSweep3InternalInt_Handle_minEdges_set(long j, btAxisSweep3InternalInt.Handle handle, long[] jArr);

    public static final native void btAxisSweep3InternalInt_Handle_operatorDeleteArray__SWIG_0(long j, btAxisSweep3InternalInt.Handle handle, long j2);

    public static final native void btAxisSweep3InternalInt_Handle_operatorDeleteArray__SWIG_1(long j, btAxisSweep3InternalInt.Handle handle, long j2, long j3);

    public static final native void btAxisSweep3InternalInt_Handle_operatorDelete__SWIG_0(long j, btAxisSweep3InternalInt.Handle handle, long j2);

    public static final native void btAxisSweep3InternalInt_Handle_operatorDelete__SWIG_1(long j, btAxisSweep3InternalInt.Handle handle, long j2, long j3);

    public static final native long btAxisSweep3InternalInt_Handle_operatorNewArray__SWIG_0(long j, btAxisSweep3InternalInt.Handle handle, long j2);

    public static final native long btAxisSweep3InternalInt_Handle_operatorNewArray__SWIG_1(long j, btAxisSweep3InternalInt.Handle handle, long j2, long j3);

    public static final native long btAxisSweep3InternalInt_Handle_operatorNew__SWIG_0(long j, btAxisSweep3InternalInt.Handle handle, long j2);

    public static final native long btAxisSweep3InternalInt_Handle_operatorNew__SWIG_1(long j, btAxisSweep3InternalInt.Handle handle, long j2, long j3);

    public static final native long btAxisSweep3InternalInt_SWIGUpcast(long j);

    public static final native long btAxisSweep3InternalInt_addHandle(long j, btAxisSweep3InternalInt btaxissweep3internalint, Vector3 vector3, Vector3 vector4, long j2, int i, int i2, long j3, btDispatcher btdispatcher);

    public static final native long btAxisSweep3InternalInt_getHandle(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2);

    public static final native long btAxisSweep3InternalInt_getNumHandles(long j, btAxisSweep3InternalInt btaxissweep3internalint);

    public static final native long btAxisSweep3InternalInt_getOverlappingPairUserCallback(long j, btAxisSweep3InternalInt btaxissweep3internalint);

    public static final native void btAxisSweep3InternalInt_operatorDeleteArray__SWIG_0(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2);

    public static final native void btAxisSweep3InternalInt_operatorDeleteArray__SWIG_1(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2, long j3);

    public static final native void btAxisSweep3InternalInt_operatorDelete__SWIG_0(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2);

    public static final native void btAxisSweep3InternalInt_operatorDelete__SWIG_1(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2, long j3);

    public static final native long btAxisSweep3InternalInt_operatorNewArray__SWIG_0(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2);

    public static final native long btAxisSweep3InternalInt_operatorNewArray__SWIG_1(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2, long j3);

    public static final native long btAxisSweep3InternalInt_operatorNew__SWIG_0(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2);

    public static final native long btAxisSweep3InternalInt_operatorNew__SWIG_1(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2, long j3);

    public static final native void btAxisSweep3InternalInt_quantize(long j, btAxisSweep3InternalInt btaxissweep3internalint, LongBuffer longBuffer, Vector3 vector3, int i);

    public static final native void btAxisSweep3InternalInt_rayTest__SWIG_0(long j, btAxisSweep3InternalInt btaxissweep3internalint, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6);

    public static final native void btAxisSweep3InternalInt_rayTest__SWIG_1(long j, btAxisSweep3InternalInt btaxissweep3internalint, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5);

    public static final native void btAxisSweep3InternalInt_rayTest__SWIG_2(long j, btAxisSweep3InternalInt btaxissweep3internalint, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback);

    public static final native void btAxisSweep3InternalInt_removeHandle(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2, long j3, btDispatcher btdispatcher);

    public static final native void btAxisSweep3InternalInt_setOverlappingPairUserCallback(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2, btOverlappingPairCallback btoverlappingpaircallback);

    public static final native boolean btAxisSweep3InternalInt_testAabbOverlap(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native void btAxisSweep3InternalInt_unQuantize(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2, btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4);

    public static final native void btAxisSweep3InternalInt_updateHandle(long j, btAxisSweep3InternalInt btaxissweep3internalint, long j2, Vector3 vector3, Vector3 vector4, long j3, btDispatcher btdispatcher);

    public static final native int btAxisSweep3InternalShort_Edge_IsMax(long j, btAxisSweep3InternalShort.Edge edge);

    public static final native int btAxisSweep3InternalShort_Edge_handle_get(long j, btAxisSweep3InternalShort.Edge edge);

    public static final native void btAxisSweep3InternalShort_Edge_handle_set(long j, btAxisSweep3InternalShort.Edge edge, int i);

    public static final native int btAxisSweep3InternalShort_Edge_pos_get(long j, btAxisSweep3InternalShort.Edge edge);

    public static final native void btAxisSweep3InternalShort_Edge_pos_set(long j, btAxisSweep3InternalShort.Edge edge, int i);

    public static final native int btAxisSweep3InternalShort_Handle_GetNextFree(long j, btAxisSweep3InternalShort.Handle handle);

    public static final native long btAxisSweep3InternalShort_Handle_SWIGUpcast(long j);

    public static final native void btAxisSweep3InternalShort_Handle_SetNextFree(long j, btAxisSweep3InternalShort.Handle handle, int i);

    public static final native long btAxisSweep3InternalShort_Handle_dbvtProxy_get(long j, btAxisSweep3InternalShort.Handle handle);

    public static final native void btAxisSweep3InternalShort_Handle_dbvtProxy_set(long j, btAxisSweep3InternalShort.Handle handle, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native int[] btAxisSweep3InternalShort_Handle_maxEdges_get(long j, btAxisSweep3InternalShort.Handle handle);

    public static final native void btAxisSweep3InternalShort_Handle_maxEdges_set(long j, btAxisSweep3InternalShort.Handle handle, int[] iArr);

    public static final native int[] btAxisSweep3InternalShort_Handle_minEdges_get(long j, btAxisSweep3InternalShort.Handle handle);

    public static final native void btAxisSweep3InternalShort_Handle_minEdges_set(long j, btAxisSweep3InternalShort.Handle handle, int[] iArr);

    public static final native void btAxisSweep3InternalShort_Handle_operatorDeleteArray__SWIG_0(long j, btAxisSweep3InternalShort.Handle handle, long j2);

    public static final native void btAxisSweep3InternalShort_Handle_operatorDeleteArray__SWIG_1(long j, btAxisSweep3InternalShort.Handle handle, long j2, long j3);

    public static final native void btAxisSweep3InternalShort_Handle_operatorDelete__SWIG_0(long j, btAxisSweep3InternalShort.Handle handle, long j2);

    public static final native void btAxisSweep3InternalShort_Handle_operatorDelete__SWIG_1(long j, btAxisSweep3InternalShort.Handle handle, long j2, long j3);

    public static final native long btAxisSweep3InternalShort_Handle_operatorNewArray__SWIG_0(long j, btAxisSweep3InternalShort.Handle handle, long j2);

    public static final native long btAxisSweep3InternalShort_Handle_operatorNewArray__SWIG_1(long j, btAxisSweep3InternalShort.Handle handle, long j2, long j3);

    public static final native long btAxisSweep3InternalShort_Handle_operatorNew__SWIG_0(long j, btAxisSweep3InternalShort.Handle handle, long j2);

    public static final native long btAxisSweep3InternalShort_Handle_operatorNew__SWIG_1(long j, btAxisSweep3InternalShort.Handle handle, long j2, long j3);

    public static final native long btAxisSweep3InternalShort_SWIGUpcast(long j);

    public static final native int btAxisSweep3InternalShort_addHandle(long j, btAxisSweep3InternalShort btaxissweep3internalshort, Vector3 vector3, Vector3 vector4, long j2, int i, int i2, long j3, btDispatcher btdispatcher);

    public static final native long btAxisSweep3InternalShort_getHandle(long j, btAxisSweep3InternalShort btaxissweep3internalshort, int i);

    public static final native int btAxisSweep3InternalShort_getNumHandles(long j, btAxisSweep3InternalShort btaxissweep3internalshort);

    public static final native long btAxisSweep3InternalShort_getOverlappingPairUserCallback(long j, btAxisSweep3InternalShort btaxissweep3internalshort);

    public static final native void btAxisSweep3InternalShort_operatorDeleteArray__SWIG_0(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2);

    public static final native void btAxisSweep3InternalShort_operatorDeleteArray__SWIG_1(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2, long j3);

    public static final native void btAxisSweep3InternalShort_operatorDelete__SWIG_0(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2);

    public static final native void btAxisSweep3InternalShort_operatorDelete__SWIG_1(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2, long j3);

    public static final native long btAxisSweep3InternalShort_operatorNewArray__SWIG_0(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2);

    public static final native long btAxisSweep3InternalShort_operatorNewArray__SWIG_1(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2, long j3);

    public static final native long btAxisSweep3InternalShort_operatorNew__SWIG_0(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2);

    public static final native long btAxisSweep3InternalShort_operatorNew__SWIG_1(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2, long j3);

    public static final native void btAxisSweep3InternalShort_quantize(long j, btAxisSweep3InternalShort btaxissweep3internalshort, IntBuffer intBuffer, Vector3 vector3, int i);

    public static final native void btAxisSweep3InternalShort_rayTest__SWIG_0(long j, btAxisSweep3InternalShort btaxissweep3internalshort, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6);

    public static final native void btAxisSweep3InternalShort_rayTest__SWIG_1(long j, btAxisSweep3InternalShort btaxissweep3internalshort, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5);

    public static final native void btAxisSweep3InternalShort_rayTest__SWIG_2(long j, btAxisSweep3InternalShort btaxissweep3internalshort, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback);

    public static final native void btAxisSweep3InternalShort_removeHandle(long j, btAxisSweep3InternalShort btaxissweep3internalshort, int i, long j2, btDispatcher btdispatcher);

    public static final native void btAxisSweep3InternalShort_setOverlappingPairUserCallback(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2, btOverlappingPairCallback btoverlappingpaircallback);

    public static final native boolean btAxisSweep3InternalShort_testAabbOverlap(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native void btAxisSweep3InternalShort_unQuantize(long j, btAxisSweep3InternalShort btaxissweep3internalshort, long j2, btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4);

    public static final native void btAxisSweep3InternalShort_updateHandle(long j, btAxisSweep3InternalShort btaxissweep3internalshort, int i, Vector3 vector3, Vector3 vector4, long j2, btDispatcher btdispatcher);

    public static final native long btAxisSweep3_SWIGUpcast(long j);

    public static final native long btBU_Simplex1to4_SWIGUpcast(long j);

    public static final native void btBU_Simplex1to4_addVertex(long j, btBU_Simplex1to4 btbu_simplex1to4, Vector3 vector3);

    public static final native int btBU_Simplex1to4_getIndex(long j, btBU_Simplex1to4 btbu_simplex1to4, int i);

    public static final native void btBU_Simplex1to4_operatorDeleteArray__SWIG_0(long j, btBU_Simplex1to4 btbu_simplex1to4, long j2);

    public static final native void btBU_Simplex1to4_operatorDeleteArray__SWIG_1(long j, btBU_Simplex1to4 btbu_simplex1to4, long j2, long j3);

    public static final native void btBU_Simplex1to4_operatorDelete__SWIG_0(long j, btBU_Simplex1to4 btbu_simplex1to4, long j2);

    public static final native void btBU_Simplex1to4_operatorDelete__SWIG_1(long j, btBU_Simplex1to4 btbu_simplex1to4, long j2, long j3);

    public static final native long btBU_Simplex1to4_operatorNewArray__SWIG_0(long j, btBU_Simplex1to4 btbu_simplex1to4, long j2);

    public static final native long btBU_Simplex1to4_operatorNewArray__SWIG_1(long j, btBU_Simplex1to4 btbu_simplex1to4, long j2, long j3);

    public static final native long btBU_Simplex1to4_operatorNew__SWIG_0(long j, btBU_Simplex1to4 btbu_simplex1to4, long j2);

    public static final native long btBU_Simplex1to4_operatorNew__SWIG_1(long j, btBU_Simplex1to4 btbu_simplex1to4, long j2, long j3);

    public static final native void btBU_Simplex1to4_reset(long j, btBU_Simplex1to4 btbu_simplex1to4);

    public static final native long btBox2dBox2dCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btBox2dBox2dCollisionAlgorithm_SWIGUpcast(long j);

    public static final native long btBox2dShape_SWIGUpcast(long j);

    public static final native Vector3 btBox2dShape_getCentroid(long j, btBox2dShape btbox2dshape);

    public static final native Vector3 btBox2dShape_getHalfExtentsWithMargin(long j, btBox2dShape btbox2dshape);

    public static final native Vector3 btBox2dShape_getHalfExtentsWithoutMargin(long j, btBox2dShape btbox2dshape);

    public static final native long btBox2dShape_getNormals(long j, btBox2dShape btbox2dshape);

    public static final native void btBox2dShape_getPlaneEquation(long j, btBox2dShape btbox2dshape, long j2, btVector4 btvector4, int i);

    public static final native int btBox2dShape_getVertexCount(long j, btBox2dShape btbox2dshape);

    public static final native long btBox2dShape_getVertices(long j, btBox2dShape btbox2dshape);

    public static final native void btBox2dShape_operatorDeleteArray__SWIG_0(long j, btBox2dShape btbox2dshape, long j2);

    public static final native void btBox2dShape_operatorDeleteArray__SWIG_1(long j, btBox2dShape btbox2dshape, long j2, long j3);

    public static final native void btBox2dShape_operatorDelete__SWIG_0(long j, btBox2dShape btbox2dshape, long j2);

    public static final native void btBox2dShape_operatorDelete__SWIG_1(long j, btBox2dShape btbox2dshape, long j2, long j3);

    public static final native long btBox2dShape_operatorNewArray__SWIG_0(long j, btBox2dShape btbox2dshape, long j2);

    public static final native long btBox2dShape_operatorNewArray__SWIG_1(long j, btBox2dShape btbox2dshape, long j2, long j3);

    public static final native long btBox2dShape_operatorNew__SWIG_0(long j, btBox2dShape btbox2dshape, long j2);

    public static final native long btBox2dShape_operatorNew__SWIG_1(long j, btBox2dShape btbox2dshape, long j2, long j3);

    public static final native long btBoxBoxCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btBoxBoxCollisionAlgorithm_SWIGUpcast(long j);

    public static final native long btBoxBoxDetector_SWIGUpcast(long j);

    public static final native long btBoxBoxDetector_box1_get(long j, btBoxBoxDetector btboxboxdetector);

    public static final native void btBoxBoxDetector_box1_set(long j, btBoxBoxDetector btboxboxdetector, long j2, btBoxShape btboxshape);

    public static final native long btBoxBoxDetector_box2_get(long j, btBoxBoxDetector btboxboxdetector);

    public static final native void btBoxBoxDetector_box2_set(long j, btBoxBoxDetector btboxboxdetector, long j2, btBoxShape btboxshape);

    public static final native void btBoxBoxDetector_getClosestPoints__SWIG_0(long j, btBoxBoxDetector btboxboxdetector, long j2, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j3, btDiscreteCollisionDetectorInterface.Result result, long j4, btIDebugDraw btidebugdraw, boolean z);

    public static final native void btBoxBoxDetector_getClosestPoints__SWIG_1(long j, btBoxBoxDetector btboxboxdetector, long j2, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j3, btDiscreteCollisionDetectorInterface.Result result, long j4, btIDebugDraw btidebugdraw);

    public static final native long btBoxShape_SWIGUpcast(long j);

    public static final native Vector3 btBoxShape_getHalfExtentsWithMargin(long j, btBoxShape btboxshape);

    public static final native Vector3 btBoxShape_getHalfExtentsWithoutMargin(long j, btBoxShape btboxshape);

    public static final native void btBoxShape_getPlaneEquation(long j, btBoxShape btboxshape, long j2, btVector4 btvector4, int i);

    public static final native void btBoxShape_operatorDeleteArray__SWIG_0(long j, btBoxShape btboxshape, long j2);

    public static final native void btBoxShape_operatorDeleteArray__SWIG_1(long j, btBoxShape btboxshape, long j2, long j3);

    public static final native void btBoxShape_operatorDelete__SWIG_0(long j, btBoxShape btboxshape, long j2);

    public static final native void btBoxShape_operatorDelete__SWIG_1(long j, btBoxShape btboxshape, long j2, long j3);

    public static final native long btBoxShape_operatorNewArray__SWIG_0(long j, btBoxShape btboxshape, long j2);

    public static final native long btBoxShape_operatorNewArray__SWIG_1(long j, btBoxShape btboxshape, long j2, long j3);

    public static final native long btBoxShape_operatorNew__SWIG_0(long j, btBoxShape btboxshape, long j2);

    public static final native long btBoxShape_operatorNew__SWIG_1(long j, btBoxShape btboxshape, long j2, long j3);

    public static final native void btBroadphaseAabbCallback_change_ownership(btBroadphaseAabbCallback btbroadphaseaabbcallback, long j, boolean z);

    public static final native void btBroadphaseAabbCallback_director_connect(btBroadphaseAabbCallback btbroadphaseaabbcallback, long j, boolean z, boolean z2);

    public static final native boolean btBroadphaseAabbCallback_process(long j, btBroadphaseAabbCallback btbroadphaseaabbcallback, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btBroadphaseInterface_aabbTest(long j, btBroadphaseInterface btbroadphaseinterface, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseAabbCallback btbroadphaseaabbcallback);

    public static final native void btBroadphaseInterface_calculateOverlappingPairs(long j, btBroadphaseInterface btbroadphaseinterface, long j2, btDispatcher btdispatcher);

    public static final native long btBroadphaseInterface_createProxy(long j, btBroadphaseInterface btbroadphaseinterface, Vector3 vector3, Vector3 vector4, int i, long j2, int i2, int i3, long j3, btDispatcher btdispatcher);

    public static final native void btBroadphaseInterface_destroyProxy(long j, btBroadphaseInterface btbroadphaseinterface, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btDispatcher btdispatcher);

    public static final native void btBroadphaseInterface_getAabb(long j, btBroadphaseInterface btbroadphaseinterface, long j2, btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4);

    public static final native void btBroadphaseInterface_getBroadphaseAabb(long j, btBroadphaseInterface btbroadphaseinterface, Vector3 vector3, Vector3 vector4);

    public static final native long btBroadphaseInterface_getOverlappingPairCache(long j, btBroadphaseInterface btbroadphaseinterface);

    public static final native long btBroadphaseInterface_getOverlappingPairCacheConst(long j, btBroadphaseInterface btbroadphaseinterface);

    public static final native void btBroadphaseInterface_printStats(long j, btBroadphaseInterface btbroadphaseinterface);

    public static final native void btBroadphaseInterface_rayTest__SWIG_0(long j, btBroadphaseInterface btbroadphaseinterface, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6);

    public static final native void btBroadphaseInterface_rayTest__SWIG_1(long j, btBroadphaseInterface btbroadphaseinterface, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5);

    public static final native void btBroadphaseInterface_rayTest__SWIG_2(long j, btBroadphaseInterface btbroadphaseinterface, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback);

    public static final native void btBroadphaseInterface_resetPool(long j, btBroadphaseInterface btbroadphaseinterface, long j2, btDispatcher btdispatcher);

    public static final native void btBroadphaseInterface_setAabb(long j, btBroadphaseInterface btbroadphaseinterface, long j2, btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4, long j3, btDispatcher btdispatcher);

    public static final native long btBroadphasePairArray_at(long j, btBroadphasePairArray btbroadphasepairarray, int i);

    public static final native int btBroadphasePairArray_getCollisionObjects(long j, btBroadphasePairArray btbroadphasepairarray, int[] iArr, int i, int i2);

    public static final native int btBroadphasePairArray_getCollisionObjectsValue(long j, btBroadphasePairArray btbroadphasepairarray, int[] iArr, int i, int i2);

    public static final native int btBroadphasePairArray_size(long j, btBroadphasePairArray btbroadphasepairarray);

    public static final native boolean btBroadphasePairSortPredicate_operatorFunctionCall(long j, btBroadphasePairSortPredicate btbroadphasepairsortpredicate, btBroadphasePair btbroadphasepair, btBroadphasePair btbroadphasepair2);

    public static final native long btBroadphasePair_algorithm_get(long j, btBroadphasePair btbroadphasepair);

    public static final native void btBroadphasePair_algorithm_set(long j, btBroadphasePair btbroadphasepair, long j2, btCollisionAlgorithm btcollisionalgorithm);

    public static final native long btBroadphasePair_internalInfo1_get(long j, btBroadphasePair btbroadphasepair);

    public static final native void btBroadphasePair_internalInfo1_set(long j, btBroadphasePair btbroadphasepair, long j2);

    public static final native int btBroadphasePair_internalTmpValue_get(long j, btBroadphasePair btbroadphasepair);

    public static final native void btBroadphasePair_internalTmpValue_set(long j, btBroadphasePair btbroadphasepair, int i);

    public static final native void btBroadphasePair_operatorDeleteArray__SWIG_0(long j, btBroadphasePair btbroadphasepair, long j2);

    public static final native void btBroadphasePair_operatorDeleteArray__SWIG_1(long j, btBroadphasePair btbroadphasepair, long j2, long j3);

    public static final native void btBroadphasePair_operatorDelete__SWIG_0(long j, btBroadphasePair btbroadphasepair, long j2);

    public static final native void btBroadphasePair_operatorDelete__SWIG_1(long j, btBroadphasePair btbroadphasepair, long j2, long j3);

    public static final native long btBroadphasePair_operatorNewArray__SWIG_0(long j, btBroadphasePair btbroadphasepair, long j2);

    public static final native long btBroadphasePair_operatorNewArray__SWIG_1(long j, btBroadphasePair btbroadphasepair, long j2, long j3);

    public static final native long btBroadphasePair_operatorNew__SWIG_0(long j, btBroadphasePair btbroadphasepair, long j2);

    public static final native long btBroadphasePair_operatorNew__SWIG_1(long j, btBroadphasePair btbroadphasepair, long j2, long j3);

    public static final native long btBroadphasePair_pProxy0_get(long j, btBroadphasePair btbroadphasepair);

    public static final native void btBroadphasePair_pProxy0_set(long j, btBroadphasePair btbroadphasepair, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native long btBroadphasePair_pProxy1_get(long j, btBroadphasePair btbroadphasepair);

    public static final native void btBroadphasePair_pProxy1_set(long j, btBroadphasePair btbroadphasepair, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native long btBroadphaseProxy_aabbMax_get(long j, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btBroadphaseProxy_aabbMax_set(long j, btBroadphaseProxy btbroadphaseproxy, long j2, btVector3 btvector3);

    public static final native long btBroadphaseProxy_aabbMin_get(long j, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btBroadphaseProxy_aabbMin_set(long j, btBroadphaseProxy btbroadphaseproxy, long j2, btVector3 btvector3);

    public static final native long btBroadphaseProxy_clientObject_get(long j, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btBroadphaseProxy_clientObject_set(long j, btBroadphaseProxy btbroadphaseproxy, long j2);

    public static final native int btBroadphaseProxy_collisionFilterGroup_get(long j, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btBroadphaseProxy_collisionFilterGroup_set(long j, btBroadphaseProxy btbroadphaseproxy, int i);

    public static final native int btBroadphaseProxy_collisionFilterMask_get(long j, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btBroadphaseProxy_collisionFilterMask_set(long j, btBroadphaseProxy btbroadphaseproxy, int i);

    public static final native int btBroadphaseProxy_getUid(long j, btBroadphaseProxy btbroadphaseproxy);

    public static final native boolean btBroadphaseProxy_isCompound(int i);

    public static final native boolean btBroadphaseProxy_isConcave(int i);

    public static final native boolean btBroadphaseProxy_isConvex(int i);

    public static final native boolean btBroadphaseProxy_isConvex2d(int i);

    public static final native boolean btBroadphaseProxy_isInfinite(int i);

    public static final native boolean btBroadphaseProxy_isNonMoving(int i);

    public static final native boolean btBroadphaseProxy_isPolyhedral(int i);

    public static final native boolean btBroadphaseProxy_isSoftBody(int i);

    public static final native void btBroadphaseProxy_operatorDeleteArray__SWIG_0(long j, btBroadphaseProxy btbroadphaseproxy, long j2);

    public static final native void btBroadphaseProxy_operatorDeleteArray__SWIG_1(long j, btBroadphaseProxy btbroadphaseproxy, long j2, long j3);

    public static final native void btBroadphaseProxy_operatorDelete__SWIG_0(long j, btBroadphaseProxy btbroadphaseproxy, long j2);

    public static final native void btBroadphaseProxy_operatorDelete__SWIG_1(long j, btBroadphaseProxy btbroadphaseproxy, long j2, long j3);

    public static final native long btBroadphaseProxy_operatorNewArray__SWIG_0(long j, btBroadphaseProxy btbroadphaseproxy, long j2);

    public static final native long btBroadphaseProxy_operatorNewArray__SWIG_1(long j, btBroadphaseProxy btbroadphaseproxy, long j2, long j3);

    public static final native long btBroadphaseProxy_operatorNew__SWIG_0(long j, btBroadphaseProxy btbroadphaseproxy, long j2);

    public static final native long btBroadphaseProxy_operatorNew__SWIG_1(long j, btBroadphaseProxy btbroadphaseproxy, long j2, long j3);

    public static final native int btBroadphaseProxy_uniqueId_get(long j, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btBroadphaseProxy_uniqueId_set(long j, btBroadphaseProxy btbroadphaseproxy, int i);

    public static final native long btBroadphaseRayCallback_SWIGUpcast(long j);

    public static final native void btBroadphaseRayCallback_change_ownership(btBroadphaseRayCallback btbroadphaseraycallback, long j, boolean z);

    public static final native void btBroadphaseRayCallback_director_connect(btBroadphaseRayCallback btbroadphaseraycallback, long j, boolean z, boolean z2);

    public static final native float btBroadphaseRayCallback_lambda_max_get(long j, btBroadphaseRayCallback btbroadphaseraycallback);

    public static final native void btBroadphaseRayCallback_lambda_max_set(long j, btBroadphaseRayCallback btbroadphaseraycallback, float f);

    public static final native long btBroadphaseRayCallback_rayDirectionInverse_get(long j, btBroadphaseRayCallback btbroadphaseraycallback);

    public static final native void btBroadphaseRayCallback_rayDirectionInverse_set(long j, btBroadphaseRayCallback btbroadphaseraycallback, long j2, btVector3 btvector3);

    public static final native long[] btBroadphaseRayCallback_signs_get(long j, btBroadphaseRayCallback btbroadphaseraycallback);

    public static final native void btBroadphaseRayCallback_signs_set(long j, btBroadphaseRayCallback btbroadphaseraycallback, long[] jArr);

    public static final native int[] btBvhSubtreeInfoData_quantizedAabbMax_get(long j, btBvhSubtreeInfoData btbvhsubtreeinfodata);

    public static final native void btBvhSubtreeInfoData_quantizedAabbMax_set(long j, btBvhSubtreeInfoData btbvhsubtreeinfodata, int[] iArr);

    public static final native int[] btBvhSubtreeInfoData_quantizedAabbMin_get(long j, btBvhSubtreeInfoData btbvhsubtreeinfodata);

    public static final native void btBvhSubtreeInfoData_quantizedAabbMin_set(long j, btBvhSubtreeInfoData btbvhsubtreeinfodata, int[] iArr);

    public static final native int btBvhSubtreeInfoData_rootNodeIndex_get(long j, btBvhSubtreeInfoData btbvhsubtreeinfodata);

    public static final native void btBvhSubtreeInfoData_rootNodeIndex_set(long j, btBvhSubtreeInfoData btbvhsubtreeinfodata, int i);

    public static final native int btBvhSubtreeInfoData_subtreeSize_get(long j, btBvhSubtreeInfoData btbvhsubtreeinfodata);

    public static final native void btBvhSubtreeInfoData_subtreeSize_set(long j, btBvhSubtreeInfoData btbvhsubtreeinfodata, int i);

    public static final native void btBvhSubtreeInfo_operatorDeleteArray__SWIG_0(long j, btBvhSubtreeInfo btbvhsubtreeinfo, long j2);

    public static final native void btBvhSubtreeInfo_operatorDeleteArray__SWIG_1(long j, btBvhSubtreeInfo btbvhsubtreeinfo, long j2, long j3);

    public static final native void btBvhSubtreeInfo_operatorDelete__SWIG_0(long j, btBvhSubtreeInfo btbvhsubtreeinfo, long j2);

    public static final native void btBvhSubtreeInfo_operatorDelete__SWIG_1(long j, btBvhSubtreeInfo btbvhsubtreeinfo, long j2, long j3);

    public static final native long btBvhSubtreeInfo_operatorNewArray__SWIG_0(long j, btBvhSubtreeInfo btbvhsubtreeinfo, long j2);

    public static final native long btBvhSubtreeInfo_operatorNewArray__SWIG_1(long j, btBvhSubtreeInfo btbvhsubtreeinfo, long j2, long j3);

    public static final native long btBvhSubtreeInfo_operatorNew__SWIG_0(long j, btBvhSubtreeInfo btbvhsubtreeinfo, long j2);

    public static final native long btBvhSubtreeInfo_operatorNew__SWIG_1(long j, btBvhSubtreeInfo btbvhsubtreeinfo, long j2, long j3);

    public static final native int[] btBvhSubtreeInfo_padding_get(long j, btBvhSubtreeInfo btbvhsubtreeinfo);

    public static final native void btBvhSubtreeInfo_padding_set(long j, btBvhSubtreeInfo btbvhsubtreeinfo, int[] iArr);

    public static final native int[] btBvhSubtreeInfo_quantizedAabbMax_get(long j, btBvhSubtreeInfo btbvhsubtreeinfo);

    public static final native void btBvhSubtreeInfo_quantizedAabbMax_set(long j, btBvhSubtreeInfo btbvhsubtreeinfo, int[] iArr);

    public static final native int[] btBvhSubtreeInfo_quantizedAabbMin_get(long j, btBvhSubtreeInfo btbvhsubtreeinfo);

    public static final native void btBvhSubtreeInfo_quantizedAabbMin_set(long j, btBvhSubtreeInfo btbvhsubtreeinfo, int[] iArr);

    public static final native int btBvhSubtreeInfo_rootNodeIndex_get(long j, btBvhSubtreeInfo btbvhsubtreeinfo);

    public static final native void btBvhSubtreeInfo_rootNodeIndex_set(long j, btBvhSubtreeInfo btbvhsubtreeinfo, int i);

    public static final native void btBvhSubtreeInfo_setAabbFromQuantizeNode(long j, btBvhSubtreeInfo btbvhsubtreeinfo, long j2, btQuantizedBvhNode btquantizedbvhnode);

    public static final native int btBvhSubtreeInfo_subtreeSize_get(long j, btBvhSubtreeInfo btbvhsubtreeinfo);

    public static final native void btBvhSubtreeInfo_subtreeSize_set(long j, btBvhSubtreeInfo btbvhsubtreeinfo, int i);

    public static final native void btBvhTree_build_tree(long j, btBvhTree btbvhtree, long j2, GIM_BVH_DATA_ARRAY gim_bvh_data_array);

    public static final native void btBvhTree_clearNodes(long j, btBvhTree btbvhtree);

    public static final native int btBvhTree_getEscapeNodeIndex(long j, btBvhTree btbvhtree, int i);

    public static final native int btBvhTree_getLeftNode(long j, btBvhTree btbvhtree, int i);

    public static final native void btBvhTree_getNodeBound(long j, btBvhTree btbvhtree, int i, long j2, btAABB btaabb);

    public static final native int btBvhTree_getNodeCount(long j, btBvhTree btbvhtree);

    public static final native int btBvhTree_getNodeData(long j, btBvhTree btbvhtree, int i);

    public static final native int btBvhTree_getRightNode(long j, btBvhTree btbvhtree, int i);

    public static final native long btBvhTree_get_node_pointer__SWIG_0(long j, btBvhTree btbvhtree, int i);

    public static final native long btBvhTree_get_node_pointer__SWIG_1(long j, btBvhTree btbvhtree);

    public static final native boolean btBvhTree_isLeafNode(long j, btBvhTree btbvhtree, int i);

    public static final native void btBvhTree_setNodeBound(long j, btBvhTree btbvhtree, int i, long j2, btAABB btaabb);

    public static final native long btBvhTriangleMeshShape_SWIGUpcast(long j);

    public static final native void btBvhTriangleMeshShape_buildOptimizedBvh(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape);

    public static final native long btBvhTriangleMeshShape_getOptimizedBvh(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape);

    public static final native boolean btBvhTriangleMeshShape_getOwnsBvh(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape);

    public static final native long btBvhTriangleMeshShape_getTriangleInfoMap__SWIG_0(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape);

    public static final native void btBvhTriangleMeshShape_operatorDeleteArray__SWIG_0(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2);

    public static final native void btBvhTriangleMeshShape_operatorDeleteArray__SWIG_1(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, long j3);

    public static final native void btBvhTriangleMeshShape_operatorDelete__SWIG_0(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2);

    public static final native void btBvhTriangleMeshShape_operatorDelete__SWIG_1(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, long j3);

    public static final native long btBvhTriangleMeshShape_operatorNewArray__SWIG_0(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2);

    public static final native long btBvhTriangleMeshShape_operatorNewArray__SWIG_1(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, long j3);

    public static final native long btBvhTriangleMeshShape_operatorNew__SWIG_0(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2);

    public static final native long btBvhTriangleMeshShape_operatorNew__SWIG_1(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, long j3);

    public static final native void btBvhTriangleMeshShape_partialRefitTree(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, Vector3 vector3, Vector3 vector4);

    public static final native void btBvhTriangleMeshShape_performConvexcast(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, btTriangleCallback bttrianglecallback, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native void btBvhTriangleMeshShape_performRaycast(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, btTriangleCallback bttrianglecallback, Vector3 vector3, Vector3 vector4);

    public static final native void btBvhTriangleMeshShape_refitTree(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, Vector3 vector3, Vector3 vector4);

    public static final native void btBvhTriangleMeshShape_serializeSingleBvh(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, btSerializer btserializer);

    public static final native void btBvhTriangleMeshShape_serializeSingleTriangleInfoMap(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, btSerializer btserializer);

    public static final native void btBvhTriangleMeshShape_setOptimizedBvh__SWIG_0(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, btOptimizedBvh btoptimizedbvh, Vector3 vector3);

    public static final native void btBvhTriangleMeshShape_setOptimizedBvh__SWIG_1(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, btOptimizedBvh btoptimizedbvh);

    public static final native void btBvhTriangleMeshShape_setTriangleInfoMap(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, btTriangleInfoMap bttriangleinfomap);

    public static final native boolean btBvhTriangleMeshShape_usesQuantizedAabbCompression(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape);

    public static final native long btCapsuleShapeData_convexInternalShapeData_get(long j, btCapsuleShapeData btcapsuleshapedata);

    public static final native void btCapsuleShapeData_convexInternalShapeData_set(long j, btCapsuleShapeData btcapsuleshapedata, long j2, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native String btCapsuleShapeData_padding_get(long j, btCapsuleShapeData btcapsuleshapedata);

    public static final native void btCapsuleShapeData_padding_set(long j, btCapsuleShapeData btcapsuleshapedata, String str);

    public static final native int btCapsuleShapeData_upAxis_get(long j, btCapsuleShapeData btcapsuleshapedata);

    public static final native void btCapsuleShapeData_upAxis_set(long j, btCapsuleShapeData btcapsuleshapedata, int i);

    public static final native long btCapsuleShapeX_SWIGUpcast(long j);

    public static final native long btCapsuleShapeZ_SWIGUpcast(long j);

    public static final native long btCapsuleShape_SWIGUpcast(long j);

    public static final native void btCapsuleShape_deSerializeFloat(long j, btCapsuleShape btcapsuleshape, long j2, btCapsuleShapeData btcapsuleshapedata);

    public static final native float btCapsuleShape_getHalfHeight(long j, btCapsuleShape btcapsuleshape);

    public static final native float btCapsuleShape_getRadius(long j, btCapsuleShape btcapsuleshape);

    public static final native int btCapsuleShape_getUpAxis(long j, btCapsuleShape btcapsuleshape);

    public static final native void btCapsuleShape_operatorDeleteArray__SWIG_0(long j, btCapsuleShape btcapsuleshape, long j2);

    public static final native void btCapsuleShape_operatorDeleteArray__SWIG_1(long j, btCapsuleShape btcapsuleshape, long j2, long j3);

    public static final native void btCapsuleShape_operatorDelete__SWIG_0(long j, btCapsuleShape btcapsuleshape, long j2);

    public static final native void btCapsuleShape_operatorDelete__SWIG_1(long j, btCapsuleShape btcapsuleshape, long j2, long j3);

    public static final native long btCapsuleShape_operatorNewArray__SWIG_0(long j, btCapsuleShape btcapsuleshape, long j2);

    public static final native long btCapsuleShape_operatorNewArray__SWIG_1(long j, btCapsuleShape btcapsuleshape, long j2, long j3);

    public static final native long btCapsuleShape_operatorNew__SWIG_0(long j, btCapsuleShape btcapsuleshape, long j2);

    public static final native long btCapsuleShape_operatorNew__SWIG_1(long j, btCapsuleShape btcapsuleshape, long j2, long j3);

    public static final native char btCharIndexTripletData_pad_get(long j, btCharIndexTripletData btcharindextripletdata);

    public static final native void btCharIndexTripletData_pad_set(long j, btCharIndexTripletData btcharindextripletdata, char c);

    public static final native short[] btCharIndexTripletData_values_get(long j, btCharIndexTripletData btcharindextripletdata);

    public static final native void btCharIndexTripletData_values_set(long j, btCharIndexTripletData btcharindextripletdata, short[] sArr);

    public static final native long btCollisionAlgorithmConstructionInfo_dispatcher1_get(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo);

    public static final native void btCollisionAlgorithmConstructionInfo_dispatcher1_set(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j2, btDispatcher btdispatcher);

    public static final native long btCollisionAlgorithmConstructionInfo_manifold_get(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo);

    public static final native void btCollisionAlgorithmConstructionInfo_manifold_set(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j2, btPersistentManifold btpersistentmanifold);

    public static final native long btCollisionAlgorithmCreateFunc_CreateCollisionAlgorithm(long j, btCollisionAlgorithmCreateFunc btcollisionalgorithmcreatefunc, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2);

    public static final native boolean btCollisionAlgorithmCreateFunc_swapped_get(long j, btCollisionAlgorithmCreateFunc btcollisionalgorithmcreatefunc);

    public static final native void btCollisionAlgorithmCreateFunc_swapped_set(long j, btCollisionAlgorithmCreateFunc btcollisionalgorithmcreatefunc, boolean z);

    public static final native float btCollisionAlgorithm_calculateTimeOfImpact(long j, btCollisionAlgorithm btcollisionalgorithm, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2, long j4, btDispatcherInfo btdispatcherinfo, long j5, btManifoldResult btmanifoldresult);

    public static final native void btCollisionAlgorithm_getAllContactManifolds(long j, btCollisionAlgorithm btcollisionalgorithm, long j2, btPersistentManifoldArray btpersistentmanifoldarray);

    public static final native void btCollisionAlgorithm_processCollision(long j, btCollisionAlgorithm btcollisionalgorithm, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, long j4, btDispatcherInfo btdispatcherinfo, long j5, btManifoldResult btmanifoldresult);

    public static final native long btCollisionConfiguration_getClosestPointsAlgorithmCreateFunc(long j, btCollisionConfiguration btcollisionconfiguration, int i, int i2);

    public static final native long btCollisionConfiguration_getCollisionAlgorithmCreateFunc(long j, btCollisionConfiguration btcollisionconfiguration, int i, int i2);

    public static final native long btCollisionConfiguration_getCollisionAlgorithmPool(long j, btCollisionConfiguration btcollisionconfiguration);

    public static final native long btCollisionConfiguration_getPersistentManifoldPool(long j, btCollisionConfiguration btcollisionconfiguration);

    public static final native long btCollisionDispatcherMt_SWIGUpcast(long j);

    public static final native long btCollisionDispatcher_SWIGUpcast(long j);

    public static final native void btCollisionDispatcher_defaultNearCallback(btBroadphasePair btbroadphasepair, long j, btCollisionDispatcher btcollisiondispatcher, long j2, btDispatcherInfo btdispatcherinfo);

    public static final native long btCollisionDispatcher_getCollisionConfiguration(long j, btCollisionDispatcher btcollisiondispatcher);

    public static final native long btCollisionDispatcher_getCollisionConfigurationConst(long j, btCollisionDispatcher btcollisiondispatcher);

    public static final native int btCollisionDispatcher_getDispatcherFlags(long j, btCollisionDispatcher btcollisiondispatcher);

    public static final native long btCollisionDispatcher_getManifoldByIndexInternalConst(long j, btCollisionDispatcher btcollisiondispatcher, int i);

    public static final native long btCollisionDispatcher_getNearCallback(long j, btCollisionDispatcher btcollisiondispatcher);

    public static final native void btCollisionDispatcher_registerClosestPointsCreateFunc(long j, btCollisionDispatcher btcollisiondispatcher, int i, int i2, long j2, btCollisionAlgorithmCreateFunc btcollisionalgorithmcreatefunc);

    public static final native void btCollisionDispatcher_registerCollisionCreateFunc(long j, btCollisionDispatcher btcollisiondispatcher, int i, int i2, long j2, btCollisionAlgorithmCreateFunc btcollisionalgorithmcreatefunc);

    public static final native void btCollisionDispatcher_setCollisionConfiguration(long j, btCollisionDispatcher btcollisiondispatcher, long j2, btCollisionConfiguration btcollisionconfiguration);

    public static final native void btCollisionDispatcher_setDispatcherFlags(long j, btCollisionDispatcher btcollisiondispatcher, int i);

    public static final native void btCollisionDispatcher_setNearCallback(long j, btCollisionDispatcher btcollisiondispatcher, long j2);

    public static final native long btCollisionObjectArray_at(long j, btCollisionObjectArray btcollisionobjectarray, int i);

    public static final native long btCollisionObjectArray_atConst(long j, btCollisionObjectArray btcollisionobjectarray, int i);

    public static final native int btCollisionObjectArray_capacity(long j, btCollisionObjectArray btcollisionobjectarray);

    public static final native void btCollisionObjectArray_clear(long j, btCollisionObjectArray btcollisionobjectarray);

    public static final native void btCollisionObjectArray_copyFromArray(long j, btCollisionObjectArray btcollisionobjectarray, long j2, btCollisionObjectArray btcollisionobjectarray2);

    public static final native long btCollisionObjectArray_expandNonInitializing(long j, btCollisionObjectArray btcollisionobjectarray);

    public static final native long btCollisionObjectArray_expand__SWIG_0(long j, btCollisionObjectArray btcollisionobjectarray, long j2, btCollisionObject btcollisionobject);

    public static final native long btCollisionObjectArray_expand__SWIG_1(long j, btCollisionObjectArray btcollisionobjectarray);

    public static final native int btCollisionObjectArray_findBinarySearch(long j, btCollisionObjectArray btcollisionobjectarray, long j2, btCollisionObject btcollisionobject);

    public static final native int btCollisionObjectArray_findLinearSearch(long j, btCollisionObjectArray btcollisionobjectarray, long j2, btCollisionObject btcollisionobject);

    public static final native int btCollisionObjectArray_findLinearSearch2(long j, btCollisionObjectArray btcollisionobjectarray, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionObjectArray_initializeFromBuffer(long j, btCollisionObjectArray btcollisionobjectarray, long j2, int i, int i2);

    public static final native boolean btCollisionObjectArray_less_operatorFunctionCall(long j, btCollisionObjectArray.less lessVar, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native long btCollisionObjectArray_operatorAssignment(long j, btCollisionObjectArray btcollisionobjectarray, long j2, btCollisionObjectArray btcollisionobjectarray2);

    public static final native long btCollisionObjectArray_operatorSubscript(long j, btCollisionObjectArray btcollisionobjectarray, int i);

    public static final native long btCollisionObjectArray_operatorSubscriptConst(long j, btCollisionObjectArray btcollisionobjectarray, int i);

    public static final native void btCollisionObjectArray_pop_back(long j, btCollisionObjectArray btcollisionobjectarray);

    public static final native void btCollisionObjectArray_push_back(long j, btCollisionObjectArray btcollisionobjectarray, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionObjectArray_remove(long j, btCollisionObjectArray btcollisionobjectarray, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionObjectArray_removeAtIndex(long j, btCollisionObjectArray btcollisionobjectarray, int i);

    public static final native void btCollisionObjectArray_reserve(long j, btCollisionObjectArray btcollisionobjectarray, int i);

    public static final native void btCollisionObjectArray_resizeNoInitialize(long j, btCollisionObjectArray btcollisionobjectarray, int i);

    public static final native void btCollisionObjectArray_resize__SWIG_0(long j, btCollisionObjectArray btcollisionobjectarray, int i, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionObjectArray_resize__SWIG_1(long j, btCollisionObjectArray btcollisionobjectarray, int i);

    public static final native int btCollisionObjectArray_size(long j, btCollisionObjectArray btcollisionobjectarray);

    public static final native void btCollisionObjectArray_swap(long j, btCollisionObjectArray btcollisionobjectarray, int i, int i2);

    public static final native long btCollisionObjectConstArray_at(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i);

    public static final native long btCollisionObjectConstArray_atConst(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i);

    public static final native int btCollisionObjectConstArray_capacity(long j, btCollisionObjectConstArray btcollisionobjectconstarray);

    public static final native void btCollisionObjectConstArray_clear(long j, btCollisionObjectConstArray btcollisionobjectconstarray);

    public static final native void btCollisionObjectConstArray_copyFromArray(long j, btCollisionObjectConstArray btcollisionobjectconstarray, long j2, btCollisionObjectConstArray btcollisionobjectconstarray2);

    public static final native long btCollisionObjectConstArray_expandNonInitializing(long j, btCollisionObjectConstArray btcollisionobjectconstarray);

    public static final native long btCollisionObjectConstArray_expand__SWIG_0(long j, btCollisionObjectConstArray btcollisionobjectconstarray, long j2, btCollisionObject btcollisionobject);

    public static final native long btCollisionObjectConstArray_expand__SWIG_1(long j, btCollisionObjectConstArray btcollisionobjectconstarray);

    public static final native int btCollisionObjectConstArray_findBinarySearch(long j, btCollisionObjectConstArray btcollisionobjectconstarray, long j2, btCollisionObject btcollisionobject);

    public static final native int btCollisionObjectConstArray_findLinearSearch(long j, btCollisionObjectConstArray btcollisionobjectconstarray, long j2, btCollisionObject btcollisionobject);

    public static final native int btCollisionObjectConstArray_findLinearSearch2(long j, btCollisionObjectConstArray btcollisionobjectconstarray, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionObjectConstArray_initializeFromBuffer(long j, btCollisionObjectConstArray btcollisionobjectconstarray, long j2, int i, int i2);

    public static final native boolean btCollisionObjectConstArray_less_operatorFunctionCall(long j, btCollisionObjectConstArray.less lessVar, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native long btCollisionObjectConstArray_operatorAssignment(long j, btCollisionObjectConstArray btcollisionobjectconstarray, long j2, btCollisionObjectConstArray btcollisionobjectconstarray2);

    public static final native long btCollisionObjectConstArray_operatorSubscript(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i);

    public static final native long btCollisionObjectConstArray_operatorSubscriptConst(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i);

    public static final native void btCollisionObjectConstArray_pop_back(long j, btCollisionObjectConstArray btcollisionobjectconstarray);

    public static final native void btCollisionObjectConstArray_push_back(long j, btCollisionObjectConstArray btcollisionobjectconstarray, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionObjectConstArray_remove(long j, btCollisionObjectConstArray btcollisionobjectconstarray, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionObjectConstArray_removeAtIndex(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i);

    public static final native void btCollisionObjectConstArray_reserve(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i);

    public static final native void btCollisionObjectConstArray_resizeNoInitialize(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i);

    public static final native void btCollisionObjectConstArray_resize__SWIG_0(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionObjectConstArray_resize__SWIG_1(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i);

    public static final native int btCollisionObjectConstArray_size(long j, btCollisionObjectConstArray btcollisionobjectconstarray);

    public static final native void btCollisionObjectConstArray_swap(long j, btCollisionObjectConstArray btcollisionobjectconstarray, int i, int i2);

    public static final native int btCollisionObjectDoubleData_activationState1_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_activationState1_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, int i);

    public static final native long btCollisionObjectDoubleData_anisotropicFriction_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_anisotropicFriction_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btCollisionObjectDoubleData_broadphaseHandle_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_broadphaseHandle_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, long j2);

    public static final native double btCollisionObjectDoubleData_ccdMotionThreshold_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_ccdMotionThreshold_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native double btCollisionObjectDoubleData_ccdSweptSphereRadius_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_ccdSweptSphereRadius_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native int btCollisionObjectDoubleData_checkCollideWith_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_checkCollideWith_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, int i);

    public static final native int btCollisionObjectDoubleData_collisionFlags_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_collisionFlags_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, int i);

    public static final native long btCollisionObjectDoubleData_collisionShape_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_collisionShape_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, long j2);

    public static final native int btCollisionObjectDoubleData_companionId_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_companionId_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, int i);

    public static final native double btCollisionObjectDoubleData_contactDamping_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_contactDamping_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native double btCollisionObjectDoubleData_contactProcessingThreshold_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_contactProcessingThreshold_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native double btCollisionObjectDoubleData_contactStiffness_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_contactStiffness_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native double btCollisionObjectDoubleData_deactivationTime_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_deactivationTime_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native double btCollisionObjectDoubleData_friction_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_friction_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native int btCollisionObjectDoubleData_hasAnisotropicFriction_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_hasAnisotropicFriction_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, int i);

    public static final native double btCollisionObjectDoubleData_hitFraction_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_hitFraction_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native int btCollisionObjectDoubleData_internalType_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_internalType_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, int i);

    public static final native long btCollisionObjectDoubleData_interpolationAngularVelocity_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_interpolationAngularVelocity_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btCollisionObjectDoubleData_interpolationLinearVelocity_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_interpolationLinearVelocity_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btCollisionObjectDoubleData_interpolationWorldTransform_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_interpolationWorldTransform_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native int btCollisionObjectDoubleData_islandTag1_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_islandTag1_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, int i);

    public static final native String btCollisionObjectDoubleData_name_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_name_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, String str);

    public static final native String btCollisionObjectDoubleData_padding_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_padding_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, String str);

    public static final native double btCollisionObjectDoubleData_restitution_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_restitution_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native double btCollisionObjectDoubleData_rollingFriction_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_rollingFriction_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, double d);

    public static final native long btCollisionObjectDoubleData_rootCollisionShape_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_rootCollisionShape_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, long j2, btCollisionShapeData btcollisionshapedata);

    public static final native long btCollisionObjectDoubleData_worldTransform_get(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata);

    public static final native void btCollisionObjectDoubleData_worldTransform_set(long j, btCollisionObjectDoubleData btcollisionobjectdoubledata, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native int btCollisionObjectFloatData_activationState1_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_activationState1_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, int i);

    public static final native long btCollisionObjectFloatData_anisotropicFriction_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_anisotropicFriction_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btCollisionObjectFloatData_broadphaseHandle_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_broadphaseHandle_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, long j2);

    public static final native float btCollisionObjectFloatData_ccdMotionThreshold_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_ccdMotionThreshold_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native float btCollisionObjectFloatData_ccdSweptSphereRadius_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_ccdSweptSphereRadius_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native int btCollisionObjectFloatData_checkCollideWith_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_checkCollideWith_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, int i);

    public static final native int btCollisionObjectFloatData_collisionFlags_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_collisionFlags_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, int i);

    public static final native long btCollisionObjectFloatData_collisionShape_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_collisionShape_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, long j2);

    public static final native int btCollisionObjectFloatData_companionId_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_companionId_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, int i);

    public static final native float btCollisionObjectFloatData_contactDamping_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_contactDamping_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native float btCollisionObjectFloatData_contactProcessingThreshold_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_contactProcessingThreshold_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native float btCollisionObjectFloatData_contactStiffness_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_contactStiffness_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native float btCollisionObjectFloatData_deactivationTime_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_deactivationTime_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native float btCollisionObjectFloatData_friction_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_friction_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native int btCollisionObjectFloatData_hasAnisotropicFriction_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_hasAnisotropicFriction_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, int i);

    public static final native float btCollisionObjectFloatData_hitFraction_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_hitFraction_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native int btCollisionObjectFloatData_internalType_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_internalType_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, int i);

    public static final native long btCollisionObjectFloatData_interpolationAngularVelocity_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_interpolationAngularVelocity_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btCollisionObjectFloatData_interpolationLinearVelocity_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_interpolationLinearVelocity_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btCollisionObjectFloatData_interpolationWorldTransform_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_interpolationWorldTransform_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native int btCollisionObjectFloatData_islandTag1_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_islandTag1_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, int i);

    public static final native String btCollisionObjectFloatData_name_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_name_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, String str);

    public static final native String btCollisionObjectFloatData_padding_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_padding_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, String str);

    public static final native float btCollisionObjectFloatData_restitution_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_restitution_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native float btCollisionObjectFloatData_rollingFriction_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_rollingFriction_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, float f);

    public static final native long btCollisionObjectFloatData_rootCollisionShape_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_rootCollisionShape_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, long j2, btCollisionShapeData btcollisionshapedata);

    public static final native long btCollisionObjectFloatData_worldTransform_get(long j, btCollisionObjectFloatData btcollisionobjectfloatdata);

    public static final native void btCollisionObjectFloatData_worldTransform_set(long j, btCollisionObjectFloatData btcollisionobjectfloatdata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native long btCollisionObjectWrapper_collisionObject_get(long j, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btCollisionObjectWrapper_collisionObject_set(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionObject btcollisionobject);

    public static final native long btCollisionObjectWrapper_getCollisionShape(long j, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native int btCollisionObjectWrapper_index_get(long j, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btCollisionObjectWrapper_index_set(long j, btCollisionObjectWrapper btcollisionobjectwrapper, int i);

    public static final native long btCollisionObjectWrapper_parent_get(long j, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btCollisionObjectWrapper_parent_set(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionObjectWrapper btcollisionobjectwrapper2);

    public static final native int btCollisionObjectWrapper_partId_get(long j, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btCollisionObjectWrapper_partId_set(long j, btCollisionObjectWrapper btcollisionobjectwrapper, int i);

    public static final native long btCollisionObjectWrapper_shape_get(long j, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btCollisionObjectWrapper_shape_set(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionShape btcollisionshape);

    public static final native Matrix4 btCollisionObjectWrapper_worldTransform_get(long j, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btCollisionObject_activate__SWIG_0(long j, btCollisionObject btcollisionobject, boolean z);

    public static final native void btCollisionObject_activate__SWIG_1(long j, btCollisionObject btcollisionobject);

    public static final native int btCollisionObject_calculateSerializeBufferSize(long j, btCollisionObject btcollisionobject);

    public static final native boolean btCollisionObject_checkCollideWith(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2);

    public static final native boolean btCollisionObject_checkCollideWithOverride(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2);

    public static final native void btCollisionObject_forceActivationState(long j, btCollisionObject btcollisionobject, int i);

    public static final native int btCollisionObject_getActivationState(long j, btCollisionObject btcollisionobject);

    public static final native Vector3 btCollisionObject_getAnisotropicFriction__SWIG_0(long j, btCollisionObject btcollisionobject);

    public static final native void btCollisionObject_getAnisotropicFriction__SWIG_1(long j, btCollisionObject btcollisionobject, Vector3 vector3);

    public static final native long btCollisionObject_getBroadphaseHandle__SWIG_0(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getCcdMotionThreshold(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getCcdSquareMotionThreshold(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getCcdSweptSphereRadius(long j, btCollisionObject btcollisionobject);

    public static final native int btCollisionObject_getCollisionFlags(long j, btCollisionObject btcollisionobject);

    public static final native int btCollisionObject_getCompanionId(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getContactDamping(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getContactProcessingThreshold(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getContactStiffness(long j, btCollisionObject btcollisionobject);

    public static final native boolean btCollisionObject_getCustomDebugColor(long j, btCollisionObject btcollisionobject, Vector3 vector3);

    public static final native float btCollisionObject_getDeactivationTime(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getFriction(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getHitFraction(long j, btCollisionObject btcollisionobject);

    public static final native int btCollisionObject_getInternalType(long j, btCollisionObject btcollisionobject);

    public static final native Vector3 btCollisionObject_getInterpolationAngularVelocity__SWIG_0(long j, btCollisionObject btcollisionobject);

    public static final native void btCollisionObject_getInterpolationAngularVelocity__SWIG_1(long j, btCollisionObject btcollisionobject, Vector3 vector3);

    public static final native Vector3 btCollisionObject_getInterpolationLinearVelocity__SWIG_0(long j, btCollisionObject btcollisionobject);

    public static final native void btCollisionObject_getInterpolationLinearVelocity__SWIG_1(long j, btCollisionObject btcollisionobject, Vector3 vector3);

    public static final native Matrix4 btCollisionObject_getInterpolationWorldTransform__SWIG_0(long j, btCollisionObject btcollisionobject);

    public static final native void btCollisionObject_getInterpolationWorldTransform__SWIG_2(long j, btCollisionObject btcollisionobject, Matrix4 matrix4);

    public static final native int btCollisionObject_getIslandTag(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getRestitution(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getRollingFriction(long j, btCollisionObject btcollisionobject);

    public static final native float btCollisionObject_getSpinningFriction(long j, btCollisionObject btcollisionobject);

    public static final native int btCollisionObject_getUpdateRevisionInternal(long j, btCollisionObject btcollisionobject);

    public static final native int btCollisionObject_getUserIndex(long j, btCollisionObject btcollisionobject);

    public static final native int btCollisionObject_getUserIndex2(long j, btCollisionObject btcollisionobject);

    public static final native long btCollisionObject_getUserPointer(long j, btCollisionObject btcollisionobject);

    public static final native int btCollisionObject_getWorldArrayIndex(long j, btCollisionObject btcollisionobject);

    public static final native Matrix4 btCollisionObject_getWorldTransform__SWIG_0(long j, btCollisionObject btcollisionobject);

    public static final native void btCollisionObject_getWorldTransform__SWIG_2(long j, btCollisionObject btcollisionobject, Matrix4 matrix4);

    public static final native boolean btCollisionObject_hasAnisotropicFriction__SWIG_0(long j, btCollisionObject btcollisionobject, int i);

    public static final native boolean btCollisionObject_hasAnisotropicFriction__SWIG_1(long j, btCollisionObject btcollisionobject);

    public static final native boolean btCollisionObject_hasContactResponse(long j, btCollisionObject btcollisionobject);

    public static final native long btCollisionObject_internalGetCollisionShape__SWIG_0(long j, btCollisionObject btcollisionobject);

    public static final native long btCollisionObject_internalGetExtensionPointer(long j, btCollisionObject btcollisionobject);

    public static final native long btCollisionObject_internalGetGdxBridge(long j, btCollisionObject btcollisionobject);

    public static final native void btCollisionObject_internalSetCollisionShape(long j, btCollisionObject btcollisionobject, long j2, btCollisionShape btcollisionshape);

    public static final native void btCollisionObject_internalSetExtensionPointer(long j, btCollisionObject btcollisionobject, long j2);

    public static final native void btCollisionObject_internalSetGdxBridge(long j, btCollisionObject btcollisionobject, long j2, GdxCollisionObjectBridge gdxCollisionObjectBridge);

    public static final native boolean btCollisionObject_isActive(long j, btCollisionObject btcollisionobject);

    public static final native boolean btCollisionObject_isKinematicObject(long j, btCollisionObject btcollisionobject);

    public static final native boolean btCollisionObject_isStaticObject(long j, btCollisionObject btcollisionobject);

    public static final native boolean btCollisionObject_isStaticOrKinematicObject(long j, btCollisionObject btcollisionobject);

    public static final native boolean btCollisionObject_mergesSimulationIslands(long j, btCollisionObject btcollisionobject);

    public static final native void btCollisionObject_operatorDeleteArray__SWIG_0(long j, btCollisionObject btcollisionobject, long j2);

    public static final native void btCollisionObject_operatorDeleteArray__SWIG_1(long j, btCollisionObject btcollisionobject, long j2, long j3);

    public static final native void btCollisionObject_operatorDelete__SWIG_0(long j, btCollisionObject btcollisionobject, long j2);

    public static final native void btCollisionObject_operatorDelete__SWIG_1(long j, btCollisionObject btcollisionobject, long j2, long j3);

    public static final native long btCollisionObject_operatorNewArray__SWIG_0(long j, btCollisionObject btcollisionobject, long j2);

    public static final native long btCollisionObject_operatorNewArray__SWIG_1(long j, btCollisionObject btcollisionobject, long j2, long j3);

    public static final native long btCollisionObject_operatorNew__SWIG_0(long j, btCollisionObject btcollisionobject, long j2);

    public static final native long btCollisionObject_operatorNew__SWIG_1(long j, btCollisionObject btcollisionobject, long j2, long j3);

    public static final native void btCollisionObject_removeCustomDebugColor(long j, btCollisionObject btcollisionobject);

    public static final native String btCollisionObject_serialize(long j, btCollisionObject btcollisionobject, long j2, long j3, btSerializer btserializer);

    public static final native void btCollisionObject_serializeSingleObject(long j, btCollisionObject btcollisionobject, long j2, btSerializer btserializer);

    public static final native void btCollisionObject_setActivationState(long j, btCollisionObject btcollisionobject, int i);

    public static final native void btCollisionObject_setAnisotropicFriction__SWIG_0(long j, btCollisionObject btcollisionobject, Vector3 vector3, int i);

    public static final native void btCollisionObject_setAnisotropicFriction__SWIG_1(long j, btCollisionObject btcollisionobject, Vector3 vector3);

    public static final native void btCollisionObject_setBroadphaseHandle(long j, btCollisionObject btcollisionobject, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btCollisionObject_setCcdMotionThreshold(long j, btCollisionObject btcollisionobject, float f);

    public static final native void btCollisionObject_setCcdSweptSphereRadius(long j, btCollisionObject btcollisionobject, float f);

    public static final native void btCollisionObject_setCollisionFlags(long j, btCollisionObject btcollisionobject, int i);

    public static final native void btCollisionObject_setCompanionId(long j, btCollisionObject btcollisionobject, int i);

    public static final native void btCollisionObject_setContactProcessingThreshold(long j, btCollisionObject btcollisionobject, float f);

    public static final native void btCollisionObject_setContactStiffnessAndDamping(long j, btCollisionObject btcollisionobject, float f, float f2);

    public static final native void btCollisionObject_setCustomDebugColor(long j, btCollisionObject btcollisionobject, Vector3 vector3);

    public static final native void btCollisionObject_setDeactivationTime(long j, btCollisionObject btcollisionobject, float f);

    public static final native void btCollisionObject_setFriction(long j, btCollisionObject btcollisionobject, float f);

    public static final native void btCollisionObject_setHitFraction(long j, btCollisionObject btcollisionobject, float f);

    public static final native void btCollisionObject_setIgnoreCollisionCheck(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2, boolean z);

    public static final native void btCollisionObject_setInterpolationAngularVelocity(long j, btCollisionObject btcollisionobject, Vector3 vector3);

    public static final native void btCollisionObject_setInterpolationLinearVelocity(long j, btCollisionObject btcollisionobject, Vector3 vector3);

    public static final native void btCollisionObject_setInterpolationWorldTransform(long j, btCollisionObject btcollisionobject, Matrix4 matrix4);

    public static final native void btCollisionObject_setIslandTag(long j, btCollisionObject btcollisionobject, int i);

    public static final native void btCollisionObject_setRestitution(long j, btCollisionObject btcollisionobject, float f);

    public static final native void btCollisionObject_setRollingFriction(long j, btCollisionObject btcollisionobject, float f);

    public static final native void btCollisionObject_setSpinningFriction(long j, btCollisionObject btcollisionobject, float f);

    public static final native void btCollisionObject_setUserIndex(long j, btCollisionObject btcollisionobject, int i);

    public static final native void btCollisionObject_setUserIndex2(long j, btCollisionObject btcollisionobject, int i);

    public static final native void btCollisionObject_setUserPointer(long j, btCollisionObject btcollisionobject, long j2);

    public static final native void btCollisionObject_setWorldArrayIndex(long j, btCollisionObject btcollisionobject, int i);

    public static final native void btCollisionObject_setWorldTransform(long j, btCollisionObject btcollisionobject, Matrix4 matrix4);

    public static final native String btCollisionShapeData_name_get(long j, btCollisionShapeData btcollisionshapedata);

    public static final native void btCollisionShapeData_name_set(long j, btCollisionShapeData btcollisionshapedata, String str);

    public static final native String btCollisionShapeData_padding_get(long j, btCollisionShapeData btcollisionshapedata);

    public static final native void btCollisionShapeData_padding_set(long j, btCollisionShapeData btcollisionshapedata, String str);

    public static final native int btCollisionShapeData_shapeType_get(long j, btCollisionShapeData btcollisionshapedata);

    public static final native void btCollisionShapeData_shapeType_set(long j, btCollisionShapeData btcollisionshapedata, int i);

    public static final native void btCollisionShape_calculateLocalInertia(long j, btCollisionShape btcollisionshape, float f, Vector3 vector3);

    public static final native int btCollisionShape_calculateSerializeBufferSize(long j, btCollisionShape btcollisionshape);

    public static final native void btCollisionShape_calculateTemporalAabb(long j, btCollisionShape btcollisionshape, Matrix4 matrix4, Vector3 vector3, Vector3 vector4, float f, Vector3 vector5, Vector3 vector6);

    public static final native void btCollisionShape_getAabb(long j, btCollisionShape btcollisionshape, Matrix4 matrix4, Vector3 vector3, Vector3 vector4);

    public static final native float btCollisionShape_getAngularMotionDisc(long j, btCollisionShape btcollisionshape);

    public static final native Vector3 btCollisionShape_getAnisotropicRollingFrictionDirection(long j, btCollisionShape btcollisionshape);

    public static final native void btCollisionShape_getBoundingSphere(long j, btCollisionShape btcollisionshape, Vector3 vector3, long j2);

    public static final native float btCollisionShape_getContactBreakingThreshold(long j, btCollisionShape btcollisionshape, float f);

    public static final native Vector3 btCollisionShape_getLocalScaling(long j, btCollisionShape btcollisionshape);

    public static final native float btCollisionShape_getMargin(long j, btCollisionShape btcollisionshape);

    public static final native String btCollisionShape_getName(long j, btCollisionShape btcollisionshape);

    public static final native int btCollisionShape_getShapeType(long j, btCollisionShape btcollisionshape);

    public static final native int btCollisionShape_getUserIndex(long j, btCollisionShape btcollisionshape);

    public static final native long btCollisionShape_getUserPointer(long j, btCollisionShape btcollisionshape);

    public static final native boolean btCollisionShape_isCompound(long j, btCollisionShape btcollisionshape);

    public static final native boolean btCollisionShape_isConcave(long j, btCollisionShape btcollisionshape);

    public static final native boolean btCollisionShape_isConvex(long j, btCollisionShape btcollisionshape);

    public static final native boolean btCollisionShape_isConvex2d(long j, btCollisionShape btcollisionshape);

    public static final native boolean btCollisionShape_isInfinite(long j, btCollisionShape btcollisionshape);

    public static final native boolean btCollisionShape_isNonMoving(long j, btCollisionShape btcollisionshape);

    public static final native boolean btCollisionShape_isPolyhedral(long j, btCollisionShape btcollisionshape);

    public static final native boolean btCollisionShape_isSoftBody(long j, btCollisionShape btcollisionshape);

    public static final native void btCollisionShape_operatorDeleteArray__SWIG_0(long j, btCollisionShape btcollisionshape, long j2);

    public static final native void btCollisionShape_operatorDeleteArray__SWIG_1(long j, btCollisionShape btcollisionshape, long j2, long j3);

    public static final native void btCollisionShape_operatorDelete__SWIG_0(long j, btCollisionShape btcollisionshape, long j2);

    public static final native void btCollisionShape_operatorDelete__SWIG_1(long j, btCollisionShape btcollisionshape, long j2, long j3);

    public static final native long btCollisionShape_operatorNewArray__SWIG_0(long j, btCollisionShape btcollisionshape, long j2);

    public static final native long btCollisionShape_operatorNewArray__SWIG_1(long j, btCollisionShape btcollisionshape, long j2, long j3);

    public static final native long btCollisionShape_operatorNew__SWIG_0(long j, btCollisionShape btcollisionshape, long j2);

    public static final native long btCollisionShape_operatorNew__SWIG_1(long j, btCollisionShape btcollisionshape, long j2, long j3);

    public static final native String btCollisionShape_serialize(long j, btCollisionShape btcollisionshape, long j2, long j3, btSerializer btserializer);

    public static final native void btCollisionShape_serializeSingleShape(long j, btCollisionShape btcollisionshape, long j2, btSerializer btserializer);

    public static final native void btCollisionShape_setLocalScaling(long j, btCollisionShape btcollisionshape, Vector3 vector3);

    public static final native void btCollisionShape_setMargin(long j, btCollisionShape btcollisionshape, float f);

    public static final native void btCollisionShape_setUserIndex(long j, btCollisionShape btcollisionshape, int i);

    public static final native void btCollisionShape_setUserPointer(long j, btCollisionShape btcollisionshape, long j2);

    public static final native boolean btCollisionWorldImporter_convertAllObjects(long j, btCollisionWorldImporter btcollisionworldimporter, long j2, btBulletSerializedArrays btbulletserializedarrays);

    public static final native long btCollisionWorldImporter_createBoxShape(long j, btCollisionWorldImporter btcollisionworldimporter, Vector3 vector3);

    public static final native long btCollisionWorldImporter_createBvhTriangleMeshShape(long j, btCollisionWorldImporter btcollisionworldimporter, long j2, btStridingMeshInterface btstridingmeshinterface, long j3, btOptimizedBvh btoptimizedbvh);

    public static final native long btCollisionWorldImporter_createCapsuleShapeX(long j, btCollisionWorldImporter btcollisionworldimporter, float f, float f2);

    public static final native long btCollisionWorldImporter_createCapsuleShapeY(long j, btCollisionWorldImporter btcollisionworldimporter, float f, float f2);

    public static final native long btCollisionWorldImporter_createCapsuleShapeZ(long j, btCollisionWorldImporter btcollisionworldimporter, float f, float f2);

    public static final native long btCollisionWorldImporter_createCollisionObject(long j, btCollisionWorldImporter btcollisionworldimporter, Matrix4 matrix4, long j2, btCollisionShape btcollisionshape, String str);

    public static final native long btCollisionWorldImporter_createCompoundShape(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native long btCollisionWorldImporter_createConeShapeX(long j, btCollisionWorldImporter btcollisionworldimporter, float f, float f2);

    public static final native long btCollisionWorldImporter_createConeShapeY(long j, btCollisionWorldImporter btcollisionworldimporter, float f, float f2);

    public static final native long btCollisionWorldImporter_createConeShapeZ(long j, btCollisionWorldImporter btcollisionworldimporter, float f, float f2);

    public static final native long btCollisionWorldImporter_createConvexHullShape(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native long btCollisionWorldImporter_createConvexTriangleMeshShape(long j, btCollisionWorldImporter btcollisionworldimporter, long j2, btStridingMeshInterface btstridingmeshinterface);

    public static final native long btCollisionWorldImporter_createCylinderShapeX(long j, btCollisionWorldImporter btcollisionworldimporter, float f, float f2);

    public static final native long btCollisionWorldImporter_createCylinderShapeY(long j, btCollisionWorldImporter btcollisionworldimporter, float f, float f2);

    public static final native long btCollisionWorldImporter_createCylinderShapeZ(long j, btCollisionWorldImporter btcollisionworldimporter, float f, float f2);

    public static final native long btCollisionWorldImporter_createMeshInterface(long j, btCollisionWorldImporter btcollisionworldimporter, long j2, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native long btCollisionWorldImporter_createMultiSphereShape(long j, btCollisionWorldImporter btcollisionworldimporter, long j2, btVector3 btvector3, FloatBuffer floatBuffer, int i);

    public static final native long btCollisionWorldImporter_createOptimizedBvh(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native long btCollisionWorldImporter_createPlaneShape(long j, btCollisionWorldImporter btcollisionworldimporter, Vector3 vector3, float f);

    public static final native long btCollisionWorldImporter_createScaledTrangleMeshShape(long j, btCollisionWorldImporter btcollisionworldimporter, long j2, btBvhTriangleMeshShape btbvhtrianglemeshshape, Vector3 vector3);

    public static final native long btCollisionWorldImporter_createSphereShape(long j, btCollisionWorldImporter btcollisionworldimporter, float f);

    public static final native long btCollisionWorldImporter_createStridingMeshInterfaceData(long j, btCollisionWorldImporter btcollisionworldimporter, long j2, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native long btCollisionWorldImporter_createTriangleInfoMap(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native long btCollisionWorldImporter_createTriangleMeshContainer(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native void btCollisionWorldImporter_deleteAllData(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native long btCollisionWorldImporter_getBvhByIndex(long j, btCollisionWorldImporter btcollisionworldimporter, int i);

    public static final native long btCollisionWorldImporter_getCollisionObjectByName(long j, btCollisionWorldImporter btcollisionworldimporter, String str);

    public static final native long btCollisionWorldImporter_getCollisionShapeByIndex(long j, btCollisionWorldImporter btcollisionworldimporter, int i);

    public static final native long btCollisionWorldImporter_getCollisionShapeByName(long j, btCollisionWorldImporter btcollisionworldimporter, String str);

    public static final native String btCollisionWorldImporter_getNameForPointer(long j, btCollisionWorldImporter btcollisionworldimporter, long j2);

    public static final native int btCollisionWorldImporter_getNumBvhs(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native int btCollisionWorldImporter_getNumCollisionShapes(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native int btCollisionWorldImporter_getNumRigidBodies(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native int btCollisionWorldImporter_getNumTriangleInfoMaps(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native long btCollisionWorldImporter_getRigidBodyByIndex(long j, btCollisionWorldImporter btcollisionworldimporter, int i);

    public static final native long btCollisionWorldImporter_getTriangleInfoMapByIndex(long j, btCollisionWorldImporter btcollisionworldimporter, int i);

    public static final native int btCollisionWorldImporter_getVerboseMode(long j, btCollisionWorldImporter btcollisionworldimporter);

    public static final native void btCollisionWorldImporter_setVerboseMode(long j, btCollisionWorldImporter btcollisionworldimporter, int i);

    public static final native void btCollisionWorld_addCollisionObject__SWIG_0(long j, btCollisionWorld btcollisionworld, long j2, btCollisionObject btcollisionobject, int i, int i2);

    public static final native void btCollisionWorld_addCollisionObject__SWIG_1(long j, btCollisionWorld btcollisionworld, long j2, btCollisionObject btcollisionobject, int i);

    public static final native void btCollisionWorld_addCollisionObject__SWIG_2(long j, btCollisionWorld btcollisionworld, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionWorld_computeOverlappingPairs(long j, btCollisionWorld btcollisionworld);

    public static final native void btCollisionWorld_contactPairTest(long j, btCollisionWorld btcollisionworld, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2, long j4, ContactResultCallback contactResultCallback);

    public static final native void btCollisionWorld_contactTest(long j, btCollisionWorld btcollisionworld, long j2, btCollisionObject btcollisionobject, long j3, ContactResultCallback contactResultCallback);

    public static final native void btCollisionWorld_convexSweepTest__SWIG_0(long j, btCollisionWorld btcollisionworld, long j2, btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, long j3, ConvexResultCallback convexResultCallback, float f);

    public static final native void btCollisionWorld_convexSweepTest__SWIG_1(long j, btCollisionWorld btcollisionworld, long j2, btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, long j3, ConvexResultCallback convexResultCallback);

    public static final native void btCollisionWorld_debugDrawObject(long j, btCollisionWorld btcollisionworld, Matrix4 matrix4, long j2, btCollisionShape btcollisionshape, Vector3 vector3);

    public static final native void btCollisionWorld_debugDrawWorld(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getBroadphase(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getBroadphaseConst(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getCollisionObjectArray(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getCollisionObjectArrayConst(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getDebugDrawer(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getDispatchInfo(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getDispatchInfoConst(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getDispatcher(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getDispatcherConst(long j, btCollisionWorld btcollisionworld);

    public static final native boolean btCollisionWorld_getForceUpdateAllAabbs(long j, btCollisionWorld btcollisionworld);

    public static final native int btCollisionWorld_getNumCollisionObjects(long j, btCollisionWorld btcollisionworld);

    public static final native long btCollisionWorld_getPairCache(long j, btCollisionWorld btcollisionworld);

    public static final native void btCollisionWorld_objectQuerySingle(long j, btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, long j2, btCollisionObject btcollisionobject, long j3, btCollisionShape btcollisionshape, Matrix4 matrix6, long j4, ConvexResultCallback convexResultCallback, float f);

    public static final native void btCollisionWorld_objectQuerySingleInternal(long j, btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, ConvexResultCallback convexResultCallback, float f);

    public static final native void btCollisionWorld_performDiscreteCollisionDetection(long j, btCollisionWorld btcollisionworld);

    public static final native void btCollisionWorld_rayTest(long j, btCollisionWorld btcollisionworld, Vector3 vector3, Vector3 vector4, long j2, RayResultCallback rayResultCallback);

    public static final native void btCollisionWorld_rayTestSingle(Matrix4 matrix4, Matrix4 matrix5, long j, btCollisionObject btcollisionobject, long j2, btCollisionShape btcollisionshape, Matrix4 matrix6, long j3, RayResultCallback rayResultCallback);

    public static final native void btCollisionWorld_rayTestSingleInternal(Matrix4 matrix4, Matrix4 matrix5, long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, RayResultCallback rayResultCallback);

    public static final native void btCollisionWorld_removeCollisionObject(long j, btCollisionWorld btcollisionworld, long j2, btCollisionObject btcollisionobject);

    public static final native void btCollisionWorld_serialize(long j, btCollisionWorld btcollisionworld, long j2, btSerializer btserializer);

    public static final native void btCollisionWorld_setBroadphase(long j, btCollisionWorld btcollisionworld, long j2, btBroadphaseInterface btbroadphaseinterface);

    public static final native void btCollisionWorld_setDebugDrawer(long j, btCollisionWorld btcollisionworld, long j2, btIDebugDraw btidebugdraw);

    public static final native void btCollisionWorld_setForceUpdateAllAabbs(long j, btCollisionWorld btcollisionworld, boolean z);

    public static final native void btCollisionWorld_updateAabbs(long j, btCollisionWorld btcollisionworld);

    public static final native void btCollisionWorld_updateSingleAabb(long j, btCollisionWorld btcollisionworld, long j2, btCollisionObject btcollisionobject);

    public static final native boolean btCompareTransformsEqual(Matrix4 matrix4, Matrix4 matrix5);

    public static final native long btCompoundCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btCompoundCollisionAlgorithm_SWIGUpcast(long j);

    public static final native long btCompoundCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(long j);

    public static final native long btCompoundCollisionAlgorithm_getChildAlgorithm(long j, btCompoundCollisionAlgorithm btcompoundcollisionalgorithm, int i);

    public static final native long btCompoundCompoundCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btCompoundCompoundCollisionAlgorithm_SWIGUpcast(long j);

    public static final native long btCompoundCompoundCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(long j);

    public static final native long btCompoundFromGimpactShape_SWIGUpcast(long j);

    public static final native void btCompoundFromGimpactShape_operatorDeleteArray__SWIG_0(long j, btCompoundFromGimpactShape btcompoundfromgimpactshape, long j2);

    public static final native void btCompoundFromGimpactShape_operatorDeleteArray__SWIG_1(long j, btCompoundFromGimpactShape btcompoundfromgimpactshape, long j2, long j3);

    public static final native void btCompoundFromGimpactShape_operatorDelete__SWIG_0(long j, btCompoundFromGimpactShape btcompoundfromgimpactshape, long j2);

    public static final native void btCompoundFromGimpactShape_operatorDelete__SWIG_1(long j, btCompoundFromGimpactShape btcompoundfromgimpactshape, long j2, long j3);

    public static final native long btCompoundFromGimpactShape_operatorNewArray__SWIG_0(long j, btCompoundFromGimpactShape btcompoundfromgimpactshape, long j2);

    public static final native long btCompoundFromGimpactShape_operatorNewArray__SWIG_1(long j, btCompoundFromGimpactShape btcompoundfromgimpactshape, long j2, long j3);

    public static final native long btCompoundFromGimpactShape_operatorNew__SWIG_0(long j, btCompoundFromGimpactShape btcompoundfromgimpactshape, long j2);

    public static final native long btCompoundFromGimpactShape_operatorNew__SWIG_1(long j, btCompoundFromGimpactShape btcompoundfromgimpactshape, long j2, long j3);

    public static final native float btCompoundShapeChildData_childMargin_get(long j, btCompoundShapeChildData btcompoundshapechilddata);

    public static final native void btCompoundShapeChildData_childMargin_set(long j, btCompoundShapeChildData btcompoundshapechilddata, float f);

    public static final native int btCompoundShapeChildData_childShapeType_get(long j, btCompoundShapeChildData btcompoundshapechilddata);

    public static final native void btCompoundShapeChildData_childShapeType_set(long j, btCompoundShapeChildData btcompoundshapechilddata, int i);

    public static final native long btCompoundShapeChildData_childShape_get(long j, btCompoundShapeChildData btcompoundshapechilddata);

    public static final native void btCompoundShapeChildData_childShape_set(long j, btCompoundShapeChildData btcompoundshapechilddata, long j2, btCollisionShapeData btcollisionshapedata);

    public static final native long btCompoundShapeChildData_transform_get(long j, btCompoundShapeChildData btcompoundshapechilddata);

    public static final native void btCompoundShapeChildData_transform_set(long j, btCompoundShapeChildData btcompoundshapechilddata, long j2, btTransformFloatData bttransformfloatdata);

    public static final native float btCompoundShapeChild_childMargin_get(long j, btCompoundShapeChild btcompoundshapechild);

    public static final native void btCompoundShapeChild_childMargin_set(long j, btCompoundShapeChild btcompoundshapechild, float f);

    public static final native int btCompoundShapeChild_childShapeType_get(long j, btCompoundShapeChild btcompoundshapechild);

    public static final native void btCompoundShapeChild_childShapeType_set(long j, btCompoundShapeChild btcompoundshapechild, int i);

    public static final native long btCompoundShapeChild_childShape_get(long j, btCompoundShapeChild btcompoundshapechild);

    public static final native void btCompoundShapeChild_childShape_set(long j, btCompoundShapeChild btcompoundshapechild, long j2, btCollisionShape btcollisionshape);

    public static final native long btCompoundShapeChild_node_get(long j, btCompoundShapeChild btcompoundshapechild);

    public static final native void btCompoundShapeChild_node_set(long j, btCompoundShapeChild btcompoundshapechild, long j2, btDbvtNode btdbvtnode);

    public static final native void btCompoundShapeChild_operatorDeleteArray__SWIG_0(long j, btCompoundShapeChild btcompoundshapechild, long j2);

    public static final native void btCompoundShapeChild_operatorDeleteArray__SWIG_1(long j, btCompoundShapeChild btcompoundshapechild, long j2, long j3);

    public static final native void btCompoundShapeChild_operatorDelete__SWIG_0(long j, btCompoundShapeChild btcompoundshapechild, long j2);

    public static final native void btCompoundShapeChild_operatorDelete__SWIG_1(long j, btCompoundShapeChild btcompoundshapechild, long j2, long j3);

    public static final native long btCompoundShapeChild_operatorNewArray__SWIG_0(long j, btCompoundShapeChild btcompoundshapechild, long j2);

    public static final native long btCompoundShapeChild_operatorNewArray__SWIG_1(long j, btCompoundShapeChild btcompoundshapechild, long j2, long j3);

    public static final native long btCompoundShapeChild_operatorNew__SWIG_0(long j, btCompoundShapeChild btcompoundshapechild, long j2);

    public static final native long btCompoundShapeChild_operatorNew__SWIG_1(long j, btCompoundShapeChild btcompoundshapechild, long j2, long j3);

    public static final native long btCompoundShapeChild_transform_get(long j, btCompoundShapeChild btcompoundshapechild);

    public static final native void btCompoundShapeChild_transform_set(long j, btCompoundShapeChild btcompoundshapechild, long j2, btTransform bttransform);

    public static final native long btCompoundShapeData_childShapePtr_get(long j, btCompoundShapeData btcompoundshapedata);

    public static final native void btCompoundShapeData_childShapePtr_set(long j, btCompoundShapeData btcompoundshapedata, long j2, btCompoundShapeChildData btcompoundshapechilddata);

    public static final native float btCompoundShapeData_collisionMargin_get(long j, btCompoundShapeData btcompoundshapedata);

    public static final native void btCompoundShapeData_collisionMargin_set(long j, btCompoundShapeData btcompoundshapedata, float f);

    public static final native long btCompoundShapeData_collisionShapeData_get(long j, btCompoundShapeData btcompoundshapedata);

    public static final native void btCompoundShapeData_collisionShapeData_set(long j, btCompoundShapeData btcompoundshapedata, long j2, btCollisionShapeData btcollisionshapedata);

    public static final native int btCompoundShapeData_numChildShapes_get(long j, btCompoundShapeData btcompoundshapedata);

    public static final native void btCompoundShapeData_numChildShapes_set(long j, btCompoundShapeData btcompoundshapedata, int i);

    public static final native long btCompoundShape_SWIGUpcast(long j);

    public static final native void btCompoundShape_calculatePrincipalAxisTransform(long j, btCompoundShape btcompoundshape, FloatBuffer floatBuffer, Matrix4 matrix4, Vector3 vector3);

    public static final native void btCompoundShape_createAabbTreeFromChildren(long j, btCompoundShape btcompoundshape);

    public static final native long btCompoundShape_getChildList(long j, btCompoundShape btcompoundshape);

    public static final native Matrix4 btCompoundShape_getChildTransform(long j, btCompoundShape btcompoundshape, int i);

    public static final native Matrix4 btCompoundShape_getChildTransformConst(long j, btCompoundShape btcompoundshape, int i);

    public static final native long btCompoundShape_getDynamicAabbTree(long j, btCompoundShape btcompoundshape);

    public static final native long btCompoundShape_getDynamicAabbTreeConst(long j, btCompoundShape btcompoundshape);

    public static final native int btCompoundShape_getNumChildShapes(long j, btCompoundShape btcompoundshape);

    public static final native int btCompoundShape_getUpdateRevision(long j, btCompoundShape btcompoundshape);

    public static final native void btCompoundShape_internalAddChildShape(long j, btCompoundShape btcompoundshape, Matrix4 matrix4, long j2, btCollisionShape btcollisionshape);

    public static final native void btCompoundShape_internalRemoveChildShape(long j, btCompoundShape btcompoundshape, long j2, btCollisionShape btcollisionshape);

    public static final native void btCompoundShape_internalRemoveChildShapeByIndex(long j, btCompoundShape btcompoundshape, int i);

    public static final native void btCompoundShape_operatorDeleteArray__SWIG_0(long j, btCompoundShape btcompoundshape, long j2);

    public static final native void btCompoundShape_operatorDeleteArray__SWIG_1(long j, btCompoundShape btcompoundshape, long j2, long j3);

    public static final native void btCompoundShape_operatorDelete__SWIG_0(long j, btCompoundShape btcompoundshape, long j2);

    public static final native void btCompoundShape_operatorDelete__SWIG_1(long j, btCompoundShape btcompoundshape, long j2, long j3);

    public static final native long btCompoundShape_operatorNewArray__SWIG_0(long j, btCompoundShape btcompoundshape, long j2);

    public static final native long btCompoundShape_operatorNewArray__SWIG_1(long j, btCompoundShape btcompoundshape, long j2, long j3);

    public static final native long btCompoundShape_operatorNew__SWIG_0(long j, btCompoundShape btcompoundshape, long j2);

    public static final native long btCompoundShape_operatorNew__SWIG_1(long j, btCompoundShape btcompoundshape, long j2, long j3);

    public static final native void btCompoundShape_recalculateLocalAabb(long j, btCompoundShape btcompoundshape);

    public static final native void btCompoundShape_updateChildTransform__SWIG_0(long j, btCompoundShape btcompoundshape, int i, Matrix4 matrix4, boolean z);

    public static final native void btCompoundShape_updateChildTransform__SWIG_1(long j, btCompoundShape btcompoundshape, int i, Matrix4 matrix4);

    public static final native long btConcaveShape_SWIGUpcast(long j);

    public static final native void btConcaveShape_operatorDeleteArray__SWIG_0(long j, btConcaveShape btconcaveshape, long j2);

    public static final native void btConcaveShape_operatorDeleteArray__SWIG_1(long j, btConcaveShape btconcaveshape, long j2, long j3);

    public static final native void btConcaveShape_operatorDelete__SWIG_0(long j, btConcaveShape btconcaveshape, long j2);

    public static final native void btConcaveShape_operatorDelete__SWIG_1(long j, btConcaveShape btconcaveshape, long j2, long j3);

    public static final native long btConcaveShape_operatorNewArray__SWIG_0(long j, btConcaveShape btconcaveshape, long j2);

    public static final native long btConcaveShape_operatorNewArray__SWIG_1(long j, btConcaveShape btconcaveshape, long j2, long j3);

    public static final native long btConcaveShape_operatorNew__SWIG_0(long j, btConcaveShape btconcaveshape, long j2);

    public static final native long btConcaveShape_operatorNew__SWIG_1(long j, btConcaveShape btconcaveshape, long j2, long j3);

    public static final native void btConcaveShape_processAllTriangles(long j, btConcaveShape btconcaveshape, long j2, btTriangleCallback bttrianglecallback, Vector3 vector3, Vector3 vector4);

    public static final native long btConeShapeData_convexInternalShapeData_get(long j, btConeShapeData btconeshapedata);

    public static final native void btConeShapeData_convexInternalShapeData_set(long j, btConeShapeData btconeshapedata, long j2, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native String btConeShapeData_padding_get(long j, btConeShapeData btconeshapedata);

    public static final native void btConeShapeData_padding_set(long j, btConeShapeData btconeshapedata, String str);

    public static final native int btConeShapeData_upIndex_get(long j, btConeShapeData btconeshapedata);

    public static final native void btConeShapeData_upIndex_set(long j, btConeShapeData btconeshapedata, int i);

    public static final native long btConeShapeX_SWIGUpcast(long j);

    public static final native long btConeShapeZ_SWIGUpcast(long j);

    public static final native long btConeShape_SWIGUpcast(long j);

    public static final native int btConeShape_getConeUpIndex(long j, btConeShape btconeshape);

    public static final native float btConeShape_getHeight(long j, btConeShape btconeshape);

    public static final native float btConeShape_getRadius(long j, btConeShape btconeshape);

    public static final native void btConeShape_operatorDeleteArray__SWIG_0(long j, btConeShape btconeshape, long j2);

    public static final native void btConeShape_operatorDeleteArray__SWIG_1(long j, btConeShape btconeshape, long j2, long j3);

    public static final native void btConeShape_operatorDelete__SWIG_0(long j, btConeShape btconeshape, long j2);

    public static final native void btConeShape_operatorDelete__SWIG_1(long j, btConeShape btconeshape, long j2, long j3);

    public static final native long btConeShape_operatorNewArray__SWIG_0(long j, btConeShape btconeshape, long j2);

    public static final native long btConeShape_operatorNewArray__SWIG_1(long j, btConeShape btconeshape, long j2, long j3);

    public static final native long btConeShape_operatorNew__SWIG_0(long j, btConeShape btconeshape, long j2);

    public static final native long btConeShape_operatorNew__SWIG_1(long j, btConeShape btconeshape, long j2, long j3);

    public static final native void btConeShape_setConeUpIndex(long j, btConeShape btconeshape, int i);

    public static final native void btConeShape_setHeight(long j, btConeShape btconeshape, float f);

    public static final native void btConeShape_setRadius(long j, btConeShape btconeshape, float f);

    public static final native float btConstraintRow_accumImpulse_get(long j, btConstraintRow btconstraintrow);

    public static final native void btConstraintRow_accumImpulse_set(long j, btConstraintRow btconstraintrow, float f);

    public static final native float btConstraintRow_jacDiagInv_get(long j, btConstraintRow btconstraintrow);

    public static final native void btConstraintRow_jacDiagInv_set(long j, btConstraintRow btconstraintrow, float f);

    public static final native float btConstraintRow_lowerLimit_get(long j, btConstraintRow btconstraintrow);

    public static final native void btConstraintRow_lowerLimit_set(long j, btConstraintRow btconstraintrow, float f);

    public static final native float[] btConstraintRow_normal_get(long j, btConstraintRow btconstraintrow);

    public static final native void btConstraintRow_normal_set(long j, btConstraintRow btconstraintrow, float[] fArr);

    public static final native float btConstraintRow_rhs_get(long j, btConstraintRow btconstraintrow);

    public static final native void btConstraintRow_rhs_set(long j, btConstraintRow btconstraintrow, float f);

    public static final native float btConstraintRow_upperLimit_get(long j, btConstraintRow btconstraintrow);

    public static final native void btConstraintRow_upperLimit_set(long j, btConstraintRow btconstraintrow, float f);

    public static final native long btContactArray_SWIGUpcast(long j);

    public static final native void btContactArray_merge_contacts__SWIG_0(long j, btContactArray btcontactarray, long j2, btContactArray btcontactarray2, boolean z);

    public static final native void btContactArray_merge_contacts__SWIG_1(long j, btContactArray btcontactarray, long j2, btContactArray btcontactarray2);

    public static final native void btContactArray_merge_contacts_unique(long j, btContactArray btcontactarray, long j2, btContactArray btcontactarray2);

    public static final native void btContactArray_push_contact(long j, btContactArray btcontactarray, Vector3 vector3, Vector3 vector4, float f, int i, int i2);

    public static final native void btContactArray_push_triangle_contacts(long j, btContactArray btcontactarray, long j2, GIM_TRIANGLE_CONTACT gim_triangle_contact, int i, int i2);

    public static final native long btContinuousConvexCollision_SWIGUpcast(long j);

    public static final native long btConvex2dConvex2dAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native int btConvex2dConvex2dAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_get(long j, btConvex2dConvex2dAlgorithm.CreateFunc createFunc);

    public static final native void btConvex2dConvex2dAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_set(long j, btConvex2dConvex2dAlgorithm.CreateFunc createFunc, int i);

    public static final native int btConvex2dConvex2dAlgorithm_CreateFunc_numPerturbationIterations_get(long j, btConvex2dConvex2dAlgorithm.CreateFunc createFunc);

    public static final native void btConvex2dConvex2dAlgorithm_CreateFunc_numPerturbationIterations_set(long j, btConvex2dConvex2dAlgorithm.CreateFunc createFunc, int i);

    public static final native long btConvex2dConvex2dAlgorithm_CreateFunc_pdSolver_get(long j, btConvex2dConvex2dAlgorithm.CreateFunc createFunc);

    public static final native void btConvex2dConvex2dAlgorithm_CreateFunc_pdSolver_set(long j, btConvex2dConvex2dAlgorithm.CreateFunc createFunc, long j2, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver);

    public static final native long btConvex2dConvex2dAlgorithm_CreateFunc_simplexSolver_get(long j, btConvex2dConvex2dAlgorithm.CreateFunc createFunc);

    public static final native void btConvex2dConvex2dAlgorithm_CreateFunc_simplexSolver_set(long j, btConvex2dConvex2dAlgorithm.CreateFunc createFunc, long j2, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native long btConvex2dConvex2dAlgorithm_SWIGUpcast(long j);

    public static final native long btConvex2dConvex2dAlgorithm_getManifold(long j, btConvex2dConvex2dAlgorithm btconvex2dconvex2dalgorithm);

    public static final native void btConvex2dConvex2dAlgorithm_setLowLevelOfDetail(long j, btConvex2dConvex2dAlgorithm btconvex2dconvex2dalgorithm, boolean z);

    public static final native long btConvex2dShape_SWIGUpcast(long j);

    public static final native long btConvex2dShape_getChildShape(long j, btConvex2dShape btconvex2dshape);

    public static final native long btConvex2dShape_getChildShapeConst(long j, btConvex2dShape btconvex2dshape);

    public static final native void btConvex2dShape_operatorDeleteArray__SWIG_0(long j, btConvex2dShape btconvex2dshape, long j2);

    public static final native void btConvex2dShape_operatorDeleteArray__SWIG_1(long j, btConvex2dShape btconvex2dshape, long j2, long j3);

    public static final native void btConvex2dShape_operatorDelete__SWIG_0(long j, btConvex2dShape btconvex2dshape, long j2);

    public static final native void btConvex2dShape_operatorDelete__SWIG_1(long j, btConvex2dShape btconvex2dshape, long j2, long j3);

    public static final native long btConvex2dShape_operatorNewArray__SWIG_0(long j, btConvex2dShape btconvex2dshape, long j2);

    public static final native long btConvex2dShape_operatorNewArray__SWIG_1(long j, btConvex2dShape btconvex2dshape, long j2, long j3);

    public static final native long btConvex2dShape_operatorNew__SWIG_0(long j, btConvex2dShape btconvex2dshape, long j2);

    public static final native long btConvex2dShape_operatorNew__SWIG_1(long j, btConvex2dShape btconvex2dshape, long j2, long j3);

    public static final native void btConvexCast_CastResult_DebugDraw(long j, btConvexCast.CastResult castResult, float f);

    public static final native float btConvexCast_CastResult_allowedPenetration_get(long j, btConvexCast.CastResult castResult);

    public static final native void btConvexCast_CastResult_allowedPenetration_set(long j, btConvexCast.CastResult castResult, float f);

    public static final native long btConvexCast_CastResult_debugDrawer_get(long j, btConvexCast.CastResult castResult);

    public static final native void btConvexCast_CastResult_debugDrawer_set(long j, btConvexCast.CastResult castResult, long j2, btIDebugDraw btidebugdraw);

    public static final native void btConvexCast_CastResult_drawCoordSystem(long j, btConvexCast.CastResult castResult, Matrix4 matrix4);

    public static final native float btConvexCast_CastResult_fraction_get(long j, btConvexCast.CastResult castResult);

    public static final native void btConvexCast_CastResult_fraction_set(long j, btConvexCast.CastResult castResult, float f);

    public static final native long btConvexCast_CastResult_hitPoint_get(long j, btConvexCast.CastResult castResult);

    public static final native void btConvexCast_CastResult_hitPoint_set(long j, btConvexCast.CastResult castResult, long j2, btVector3 btvector3);

    public static final native long btConvexCast_CastResult_hitTransformA_get(long j, btConvexCast.CastResult castResult);

    public static final native void btConvexCast_CastResult_hitTransformA_set(long j, btConvexCast.CastResult castResult, long j2, btTransform bttransform);

    public static final native long btConvexCast_CastResult_hitTransformB_get(long j, btConvexCast.CastResult castResult);

    public static final native void btConvexCast_CastResult_hitTransformB_set(long j, btConvexCast.CastResult castResult, long j2, btTransform bttransform);

    public static final native long btConvexCast_CastResult_normal_get(long j, btConvexCast.CastResult castResult);

    public static final native void btConvexCast_CastResult_normal_set(long j, btConvexCast.CastResult castResult, long j2, btVector3 btvector3);

    public static final native void btConvexCast_CastResult_reportFailure(long j, btConvexCast.CastResult castResult, int i, int i2);

    public static final native boolean btConvexCast_calcTimeOfImpact(long j, btConvexCast btconvexcast, Matrix4 matrix4, Matrix4 matrix5, Matrix4 matrix6, Matrix4 matrix7, long j2, btConvexCast.CastResult castResult);

    public static final native long btConvexConcaveCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btConvexConcaveCollisionAlgorithm_SWIGUpcast(long j);

    public static final native long btConvexConcaveCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(long j);

    public static final native void btConvexConcaveCollisionAlgorithm_clearCache(long j, btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm);

    public static final native void btConvexConcaveCollisionAlgorithm_operatorDeleteArray__SWIG_0(long j, btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm, long j2);

    public static final native void btConvexConcaveCollisionAlgorithm_operatorDeleteArray__SWIG_1(long j, btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm, long j2, long j3);

    public static final native void btConvexConcaveCollisionAlgorithm_operatorDelete__SWIG_0(long j, btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm, long j2);

    public static final native void btConvexConcaveCollisionAlgorithm_operatorDelete__SWIG_1(long j, btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm, long j2, long j3);

    public static final native long btConvexConcaveCollisionAlgorithm_operatorNewArray__SWIG_0(long j, btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm, long j2);

    public static final native long btConvexConcaveCollisionAlgorithm_operatorNewArray__SWIG_1(long j, btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm, long j2, long j3);

    public static final native long btConvexConcaveCollisionAlgorithm_operatorNew__SWIG_0(long j, btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm, long j2);

    public static final native long btConvexConcaveCollisionAlgorithm_operatorNew__SWIG_1(long j, btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm, long j2, long j3);

    public static final native long btConvexConvexAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native int btConvexConvexAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_get(long j, btConvexConvexAlgorithm.CreateFunc createFunc);

    public static final native void btConvexConvexAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_set(long j, btConvexConvexAlgorithm.CreateFunc createFunc, int i);

    public static final native int btConvexConvexAlgorithm_CreateFunc_numPerturbationIterations_get(long j, btConvexConvexAlgorithm.CreateFunc createFunc);

    public static final native void btConvexConvexAlgorithm_CreateFunc_numPerturbationIterations_set(long j, btConvexConvexAlgorithm.CreateFunc createFunc, int i);

    public static final native long btConvexConvexAlgorithm_CreateFunc_pdSolver_get(long j, btConvexConvexAlgorithm.CreateFunc createFunc);

    public static final native void btConvexConvexAlgorithm_CreateFunc_pdSolver_set(long j, btConvexConvexAlgorithm.CreateFunc createFunc, long j2, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver);

    public static final native long btConvexConvexAlgorithm_SWIGUpcast(long j);

    public static final native long btConvexConvexAlgorithm_getManifold(long j, btConvexConvexAlgorithm btconvexconvexalgorithm);

    public static final native void btConvexConvexAlgorithm_setLowLevelOfDetail(long j, btConvexConvexAlgorithm btconvexconvexalgorithm, boolean z);

    public static final native long btConvexHullShapeData_convexInternalShapeData_get(long j, btConvexHullShapeData btconvexhullshapedata);

    public static final native void btConvexHullShapeData_convexInternalShapeData_set(long j, btConvexHullShapeData btconvexhullshapedata, long j2, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native int btConvexHullShapeData_numUnscaledPoints_get(long j, btConvexHullShapeData btconvexhullshapedata);

    public static final native void btConvexHullShapeData_numUnscaledPoints_set(long j, btConvexHullShapeData btconvexhullshapedata, int i);

    public static final native String btConvexHullShapeData_padding3_get(long j, btConvexHullShapeData btconvexhullshapedata);

    public static final native void btConvexHullShapeData_padding3_set(long j, btConvexHullShapeData btconvexhullshapedata, String str);

    public static final native long btConvexHullShapeData_unscaledPointsDoublePtr_get(long j, btConvexHullShapeData btconvexhullshapedata);

    public static final native void btConvexHullShapeData_unscaledPointsDoublePtr_set(long j, btConvexHullShapeData btconvexhullshapedata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btConvexHullShapeData_unscaledPointsFloatPtr_get(long j, btConvexHullShapeData btconvexhullshapedata);

    public static final native void btConvexHullShapeData_unscaledPointsFloatPtr_set(long j, btConvexHullShapeData btconvexhullshapedata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btConvexHullShape_SWIGUpcast(long j);

    public static final native void btConvexHullShape_addPoint__SWIG_0(long j, btConvexHullShape btconvexhullshape, Vector3 vector3, boolean z);

    public static final native void btConvexHullShape_addPoint__SWIG_1(long j, btConvexHullShape btconvexhullshape, Vector3 vector3);

    public static final native int btConvexHullShape_getNumPoints(long j, btConvexHullShape btconvexhullshape);

    public static final native long btConvexHullShape_getPoints(long j, btConvexHullShape btconvexhullshape);

    public static final native Vector3 btConvexHullShape_getScaledPoint(long j, btConvexHullShape btconvexhullshape, int i);

    public static final native long btConvexHullShape_getUnscaledPoints(long j, btConvexHullShape btconvexhullshape);

    public static final native long btConvexHullShape_getUnscaledPointsConst(long j, btConvexHullShape btconvexhullshape);

    public static final native void btConvexHullShape_operatorDeleteArray__SWIG_0(long j, btConvexHullShape btconvexhullshape, long j2);

    public static final native void btConvexHullShape_operatorDeleteArray__SWIG_1(long j, btConvexHullShape btconvexhullshape, long j2, long j3);

    public static final native void btConvexHullShape_operatorDelete__SWIG_0(long j, btConvexHullShape btconvexhullshape, long j2);

    public static final native void btConvexHullShape_operatorDelete__SWIG_1(long j, btConvexHullShape btconvexhullshape, long j2, long j3);

    public static final native long btConvexHullShape_operatorNewArray__SWIG_0(long j, btConvexHullShape btconvexhullshape, long j2);

    public static final native long btConvexHullShape_operatorNewArray__SWIG_1(long j, btConvexHullShape btconvexhullshape, long j2, long j3);

    public static final native long btConvexHullShape_operatorNew__SWIG_0(long j, btConvexHullShape btconvexhullshape, long j2);

    public static final native long btConvexHullShape_operatorNew__SWIG_1(long j, btConvexHullShape btconvexhullshape, long j2, long j3);

    public static final native void btConvexHullShape_optimizeConvexHull(long j, btConvexHullShape btconvexhullshape);

    public static final native long btConvexInternalAabbCachingShape_SWIGUpcast(long j);

    public static final native void btConvexInternalAabbCachingShape_recalcLocalAabb(long j, btConvexInternalAabbCachingShape btconvexinternalaabbcachingshape);

    public static final native float btConvexInternalShapeData_collisionMargin_get(long j, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native void btConvexInternalShapeData_collisionMargin_set(long j, btConvexInternalShapeData btconvexinternalshapedata, float f);

    public static final native long btConvexInternalShapeData_collisionShapeData_get(long j, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native void btConvexInternalShapeData_collisionShapeData_set(long j, btConvexInternalShapeData btconvexinternalshapedata, long j2, btCollisionShapeData btcollisionshapedata);

    public static final native long btConvexInternalShapeData_implicitShapeDimensions_get(long j, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native void btConvexInternalShapeData_implicitShapeDimensions_set(long j, btConvexInternalShapeData btconvexinternalshapedata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btConvexInternalShapeData_localScaling_get(long j, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native void btConvexInternalShapeData_localScaling_set(long j, btConvexInternalShapeData btconvexinternalshapedata, long j2, btVector3FloatData btvector3floatdata);

    public static final native int btConvexInternalShapeData_padding_get(long j, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native void btConvexInternalShapeData_padding_set(long j, btConvexInternalShapeData btconvexinternalshapedata, int i);

    public static final native long btConvexInternalShape_SWIGUpcast(long j);

    public static final native Vector3 btConvexInternalShape_getImplicitShapeDimensions(long j, btConvexInternalShape btconvexinternalshape);

    public static final native Vector3 btConvexInternalShape_getLocalScalingNV(long j, btConvexInternalShape btconvexinternalshape);

    public static final native float btConvexInternalShape_getMarginNV(long j, btConvexInternalShape btconvexinternalshape);

    public static final native void btConvexInternalShape_operatorDeleteArray__SWIG_0(long j, btConvexInternalShape btconvexinternalshape, long j2);

    public static final native void btConvexInternalShape_operatorDeleteArray__SWIG_1(long j, btConvexInternalShape btconvexinternalshape, long j2, long j3);

    public static final native void btConvexInternalShape_operatorDelete__SWIG_0(long j, btConvexInternalShape btconvexinternalshape, long j2);

    public static final native void btConvexInternalShape_operatorDelete__SWIG_1(long j, btConvexInternalShape btconvexinternalshape, long j2, long j3);

    public static final native long btConvexInternalShape_operatorNewArray__SWIG_0(long j, btConvexInternalShape btconvexinternalshape, long j2);

    public static final native long btConvexInternalShape_operatorNewArray__SWIG_1(long j, btConvexInternalShape btconvexinternalshape, long j2, long j3);

    public static final native long btConvexInternalShape_operatorNew__SWIG_0(long j, btConvexInternalShape btconvexinternalshape, long j2);

    public static final native long btConvexInternalShape_operatorNew__SWIG_1(long j, btConvexInternalShape btconvexinternalshape, long j2, long j3);

    public static final native void btConvexInternalShape_setImplicitShapeDimensions(long j, btConvexInternalShape btconvexinternalshape, Vector3 vector3);

    public static final native void btConvexInternalShape_setSafeMargin__SWIG_0(long j, btConvexInternalShape btconvexinternalshape, float f, float f2);

    public static final native void btConvexInternalShape_setSafeMargin__SWIG_1(long j, btConvexInternalShape btconvexinternalshape, float f);

    public static final native void btConvexInternalShape_setSafeMargin__SWIG_2(long j, btConvexInternalShape btconvexinternalshape, Vector3 vector3, float f);

    public static final native void btConvexInternalShape_setSafeMargin__SWIG_3(long j, btConvexInternalShape btconvexinternalshape, Vector3 vector3);

    public static final native boolean btConvexPenetrationDepthSolver_calcPenDepth(long j, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver, long j2, btVoronoiSimplexSolver btvoronoisimplexsolver, long j3, btConvexShape btconvexshape, long j4, btConvexShape btconvexshape2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, Vector3 vector5, long j5, btIDebugDraw btidebugdraw);

    public static final native long btConvexPlaneCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native int btConvexPlaneCollisionAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_get(long j, btConvexPlaneCollisionAlgorithm.CreateFunc createFunc);

    public static final native void btConvexPlaneCollisionAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_set(long j, btConvexPlaneCollisionAlgorithm.CreateFunc createFunc, int i);

    public static final native int btConvexPlaneCollisionAlgorithm_CreateFunc_numPerturbationIterations_get(long j, btConvexPlaneCollisionAlgorithm.CreateFunc createFunc);

    public static final native void btConvexPlaneCollisionAlgorithm_CreateFunc_numPerturbationIterations_set(long j, btConvexPlaneCollisionAlgorithm.CreateFunc createFunc, int i);

    public static final native long btConvexPlaneCollisionAlgorithm_SWIGUpcast(long j);

    public static final native void btConvexPlaneCollisionAlgorithm_collideSingleContact(long j, btConvexPlaneCollisionAlgorithm btconvexplanecollisionalgorithm, Quaternion quaternion, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, long j4, btDispatcherInfo btdispatcherinfo, long j5, btManifoldResult btmanifoldresult);

    public static final native long btConvexPointCloudShape_SWIGUpcast(long j);

    public static final native int btConvexPointCloudShape_getNumPoints(long j, btConvexPointCloudShape btconvexpointcloudshape);

    public static final native Vector3 btConvexPointCloudShape_getScaledPoint(long j, btConvexPointCloudShape btconvexpointcloudshape, int i);

    public static final native long btConvexPointCloudShape_getUnscaledPoints(long j, btConvexPointCloudShape btconvexpointcloudshape);

    public static final native long btConvexPointCloudShape_getUnscaledPointsConst(long j, btConvexPointCloudShape btconvexpointcloudshape);

    public static final native void btConvexPointCloudShape_operatorDeleteArray__SWIG_0(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2);

    public static final native void btConvexPointCloudShape_operatorDeleteArray__SWIG_1(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2, long j3);

    public static final native void btConvexPointCloudShape_operatorDelete__SWIG_0(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2);

    public static final native void btConvexPointCloudShape_operatorDelete__SWIG_1(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2, long j3);

    public static final native long btConvexPointCloudShape_operatorNewArray__SWIG_0(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2);

    public static final native long btConvexPointCloudShape_operatorNewArray__SWIG_1(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2, long j3);

    public static final native long btConvexPointCloudShape_operatorNew__SWIG_0(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2);

    public static final native long btConvexPointCloudShape_operatorNew__SWIG_1(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2, long j3);

    public static final native void btConvexPointCloudShape_setPoints__SWIG_0(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2, btVector3 btvector3, int i, boolean z, Vector3 vector3);

    public static final native void btConvexPointCloudShape_setPoints__SWIG_1(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2, btVector3 btvector3, int i, boolean z);

    public static final native void btConvexPointCloudShape_setPoints__SWIG_2(long j, btConvexPointCloudShape btconvexpointcloudshape, long j2, btVector3 btvector3, int i);

    public static final native long btConvexPolyhedron_extents_get(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native void btConvexPolyhedron_extents_set(long j, btConvexPolyhedron btconvexpolyhedron, long j2, btVector3 btvector3);

    public static final native long btConvexPolyhedron_faces_get(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native void btConvexPolyhedron_faces_set(long j, btConvexPolyhedron btconvexpolyhedron, long j2);

    public static final native void btConvexPolyhedron_initialize(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native long btConvexPolyhedron_localCenter_get(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native void btConvexPolyhedron_localCenter_set(long j, btConvexPolyhedron btconvexpolyhedron, long j2, btVector3 btvector3);

    public static final native long btConvexPolyhedron_mC_get(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native void btConvexPolyhedron_mC_set(long j, btConvexPolyhedron btconvexpolyhedron, long j2, btVector3 btvector3);

    public static final native long btConvexPolyhedron_mE_get(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native void btConvexPolyhedron_mE_set(long j, btConvexPolyhedron btconvexpolyhedron, long j2, btVector3 btvector3);

    public static final native void btConvexPolyhedron_operatorDeleteArray__SWIG_0(long j, btConvexPolyhedron btconvexpolyhedron, long j2);

    public static final native void btConvexPolyhedron_operatorDeleteArray__SWIG_1(long j, btConvexPolyhedron btconvexpolyhedron, long j2, long j3);

    public static final native void btConvexPolyhedron_operatorDelete__SWIG_0(long j, btConvexPolyhedron btconvexpolyhedron, long j2);

    public static final native void btConvexPolyhedron_operatorDelete__SWIG_1(long j, btConvexPolyhedron btconvexpolyhedron, long j2, long j3);

    public static final native long btConvexPolyhedron_operatorNewArray__SWIG_0(long j, btConvexPolyhedron btconvexpolyhedron, long j2);

    public static final native long btConvexPolyhedron_operatorNewArray__SWIG_1(long j, btConvexPolyhedron btconvexpolyhedron, long j2, long j3);

    public static final native long btConvexPolyhedron_operatorNew__SWIG_0(long j, btConvexPolyhedron btconvexpolyhedron, long j2);

    public static final native long btConvexPolyhedron_operatorNew__SWIG_1(long j, btConvexPolyhedron btconvexpolyhedron, long j2, long j3);

    public static final native void btConvexPolyhedron_project(long j, btConvexPolyhedron btconvexpolyhedron, Matrix4 matrix4, Vector3 vector3, long j2, long j3, Vector3 vector4, Vector3 vector5);

    public static final native float btConvexPolyhedron_radius_get(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native void btConvexPolyhedron_radius_set(long j, btConvexPolyhedron btconvexpolyhedron, float f);

    public static final native boolean btConvexPolyhedron_testContainment(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native long btConvexPolyhedron_uniqueEdges_get(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native void btConvexPolyhedron_uniqueEdges_set(long j, btConvexPolyhedron btconvexpolyhedron, long j2, btVector3Array btvector3array);

    public static final native long btConvexPolyhedron_vertices_get(long j, btConvexPolyhedron btconvexpolyhedron);

    public static final native void btConvexPolyhedron_vertices_set(long j, btConvexPolyhedron btconvexpolyhedron, long j2, btVector3Array btvector3array);

    public static final native long btConvexShape_SWIGUpcast(long j);

    public static final native void btConvexShape_batchedUnitVectorGetSupportingVertexWithoutMargin(long j, btConvexShape btconvexshape, long j2, btVector3 btvector3, long j3, btVector3 btvector4, int i);

    public static final native void btConvexShape_getAabbNonVirtual(long j, btConvexShape btconvexshape, Matrix4 matrix4, Vector3 vector3, Vector3 vector4);

    public static final native void btConvexShape_getAabbSlow(long j, btConvexShape btconvexshape, Matrix4 matrix4, Vector3 vector3, Vector3 vector4);

    public static final native float btConvexShape_getMarginNonVirtual(long j, btConvexShape btconvexshape);

    public static final native int btConvexShape_getNumPreferredPenetrationDirections(long j, btConvexShape btconvexshape);

    public static final native void btConvexShape_getPreferredPenetrationDirection(long j, btConvexShape btconvexshape, int i, Vector3 vector3);

    public static final native Vector3 btConvexShape_localGetSupportVertexNonVirtual(long j, btConvexShape btconvexshape, Vector3 vector3);

    public static final native Vector3 btConvexShape_localGetSupportVertexWithoutMarginNonVirtual(long j, btConvexShape btconvexshape, Vector3 vector3);

    public static final native Vector3 btConvexShape_localGetSupportingVertex(long j, btConvexShape btconvexshape, Vector3 vector3);

    public static final native Vector3 btConvexShape_localGetSupportingVertexWithoutMargin(long j, btConvexShape btconvexshape, Vector3 vector3);

    public static final native void btConvexShape_operatorDeleteArray__SWIG_0(long j, btConvexShape btconvexshape, long j2);

    public static final native void btConvexShape_operatorDeleteArray__SWIG_1(long j, btConvexShape btconvexshape, long j2, long j3);

    public static final native void btConvexShape_operatorDelete__SWIG_0(long j, btConvexShape btconvexshape, long j2);

    public static final native void btConvexShape_operatorDelete__SWIG_1(long j, btConvexShape btconvexshape, long j2, long j3);

    public static final native long btConvexShape_operatorNewArray__SWIG_0(long j, btConvexShape btconvexshape, long j2);

    public static final native long btConvexShape_operatorNewArray__SWIG_1(long j, btConvexShape btconvexshape, long j2, long j3);

    public static final native long btConvexShape_operatorNew__SWIG_0(long j, btConvexShape btconvexshape, long j2);

    public static final native long btConvexShape_operatorNew__SWIG_1(long j, btConvexShape btconvexshape, long j2, long j3);

    public static final native void btConvexShape_project(long j, btConvexShape btconvexshape, Matrix4 matrix4, Vector3 vector3, long j2, long j3, Vector3 vector4, Vector3 vector5);

    public static final native long btConvexTriangleCallback_SWIGUpcast(long j);

    public static final native void btConvexTriangleCallback_change_ownership(btConvexTriangleCallback btconvextrianglecallback, long j, boolean z);

    public static final native void btConvexTriangleCallback_clearCache(long j, btConvexTriangleCallback btconvextrianglecallback);

    public static final native void btConvexTriangleCallback_clearWrapperData(long j, btConvexTriangleCallback btconvextrianglecallback);

    public static final native void btConvexTriangleCallback_director_connect(btConvexTriangleCallback btconvextrianglecallback, long j, boolean z, boolean z2);

    public static final native Vector3 btConvexTriangleCallback_getAabbMax(long j, btConvexTriangleCallback btconvextrianglecallback);

    public static final native Vector3 btConvexTriangleCallback_getAabbMin(long j, btConvexTriangleCallback btconvextrianglecallback);

    public static final native long btConvexTriangleCallback_manifoldPtr_get(long j, btConvexTriangleCallback btconvextrianglecallback);

    public static final native void btConvexTriangleCallback_manifoldPtr_set(long j, btConvexTriangleCallback btconvextrianglecallback, long j2, btPersistentManifold btpersistentmanifold);

    public static final native void btConvexTriangleCallback_operatorDeleteArray__SWIG_0(long j, btConvexTriangleCallback btconvextrianglecallback, long j2);

    public static final native void btConvexTriangleCallback_operatorDeleteArray__SWIG_1(long j, btConvexTriangleCallback btconvextrianglecallback, long j2, long j3);

    public static final native void btConvexTriangleCallback_operatorDelete__SWIG_0(long j, btConvexTriangleCallback btconvextrianglecallback, long j2);

    public static final native void btConvexTriangleCallback_operatorDelete__SWIG_1(long j, btConvexTriangleCallback btconvextrianglecallback, long j2, long j3);

    public static final native long btConvexTriangleCallback_operatorNewArray__SWIG_0(long j, btConvexTriangleCallback btconvextrianglecallback, long j2);

    public static final native long btConvexTriangleCallback_operatorNewArray__SWIG_1(long j, btConvexTriangleCallback btconvextrianglecallback, long j2, long j3);

    public static final native long btConvexTriangleCallback_operatorNew__SWIG_0(long j, btConvexTriangleCallback btconvextrianglecallback, long j2);

    public static final native long btConvexTriangleCallback_operatorNew__SWIG_1(long j, btConvexTriangleCallback btconvextrianglecallback, long j2, long j3);

    public static final native void btConvexTriangleCallback_processTriangle(long j, btConvexTriangleCallback btconvextrianglecallback, long j2, btVector3 btvector3, int i, int i2);

    public static final native void btConvexTriangleCallback_processTriangleSwigExplicitbtConvexTriangleCallback(long j, btConvexTriangleCallback btconvextrianglecallback, long j2, btVector3 btvector3, int i, int i2);

    public static final native void btConvexTriangleCallback_setTimeStepAndCounters(long j, btConvexTriangleCallback btconvextrianglecallback, float f, long j2, btDispatcherInfo btdispatcherinfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, long j5, btManifoldResult btmanifoldresult);

    public static final native int btConvexTriangleCallback_triangleCount_get(long j, btConvexTriangleCallback btconvextrianglecallback);

    public static final native void btConvexTriangleCallback_triangleCount_set(long j, btConvexTriangleCallback btconvextrianglecallback, int i);

    public static final native long btConvexTriangleMeshShape_SWIGUpcast(long j);

    public static final native void btConvexTriangleMeshShape_calculatePrincipalAxisTransform(long j, btConvexTriangleMeshShape btconvextrianglemeshshape, Matrix4 matrix4, Vector3 vector3, long j2);

    public static final native long btConvexTriangleMeshShape_getMeshInterface(long j, btConvexTriangleMeshShape btconvextrianglemeshshape);

    public static final native long btConvexTriangleMeshShape_getMeshInterfaceConst(long j, btConvexTriangleMeshShape btconvextrianglemeshshape);

    public static final native void btConvexTriangleMeshShape_operatorDeleteArray__SWIG_0(long j, btConvexTriangleMeshShape btconvextrianglemeshshape, long j2);

    public static final native void btConvexTriangleMeshShape_operatorDeleteArray__SWIG_1(long j, btConvexTriangleMeshShape btconvextrianglemeshshape, long j2, long j3);

    public static final native void btConvexTriangleMeshShape_operatorDelete__SWIG_0(long j, btConvexTriangleMeshShape btconvextrianglemeshshape, long j2);

    public static final native void btConvexTriangleMeshShape_operatorDelete__SWIG_1(long j, btConvexTriangleMeshShape btconvextrianglemeshshape, long j2, long j3);

    public static final native long btConvexTriangleMeshShape_operatorNewArray__SWIG_0(long j, btConvexTriangleMeshShape btconvextrianglemeshshape, long j2);

    public static final native long btConvexTriangleMeshShape_operatorNewArray__SWIG_1(long j, btConvexTriangleMeshShape btconvextrianglemeshshape, long j2, long j3);

    public static final native long btConvexTriangleMeshShape_operatorNew__SWIG_0(long j, btConvexTriangleMeshShape btconvextrianglemeshshape, long j2);

    public static final native long btConvexTriangleMeshShape_operatorNew__SWIG_1(long j, btConvexTriangleMeshShape btconvextrianglemeshshape, long j2, long j3);

    public static final native long btCreateCompoundFromGimpactShape(long j, btGImpactMeshShape btgimpactmeshshape, float f);

    public static final native long btCylinderShapeData_convexInternalShapeData_get(long j, btCylinderShapeData btcylindershapedata);

    public static final native void btCylinderShapeData_convexInternalShapeData_set(long j, btCylinderShapeData btcylindershapedata, long j2, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native String btCylinderShapeData_padding_get(long j, btCylinderShapeData btcylindershapedata);

    public static final native void btCylinderShapeData_padding_set(long j, btCylinderShapeData btcylindershapedata, String str);

    public static final native int btCylinderShapeData_upAxis_get(long j, btCylinderShapeData btcylindershapedata);

    public static final native void btCylinderShapeData_upAxis_set(long j, btCylinderShapeData btcylindershapedata, int i);

    public static final native long btCylinderShapeX_SWIGUpcast(long j);

    public static final native void btCylinderShapeX_operatorDeleteArray__SWIG_0(long j, btCylinderShapeX btcylindershapex, long j2);

    public static final native void btCylinderShapeX_operatorDeleteArray__SWIG_1(long j, btCylinderShapeX btcylindershapex, long j2, long j3);

    public static final native void btCylinderShapeX_operatorDelete__SWIG_0(long j, btCylinderShapeX btcylindershapex, long j2);

    public static final native void btCylinderShapeX_operatorDelete__SWIG_1(long j, btCylinderShapeX btcylindershapex, long j2, long j3);

    public static final native long btCylinderShapeX_operatorNewArray__SWIG_0(long j, btCylinderShapeX btcylindershapex, long j2);

    public static final native long btCylinderShapeX_operatorNewArray__SWIG_1(long j, btCylinderShapeX btcylindershapex, long j2, long j3);

    public static final native long btCylinderShapeX_operatorNew__SWIG_0(long j, btCylinderShapeX btcylindershapex, long j2);

    public static final native long btCylinderShapeX_operatorNew__SWIG_1(long j, btCylinderShapeX btcylindershapex, long j2, long j3);

    public static final native long btCylinderShapeZ_SWIGUpcast(long j);

    public static final native void btCylinderShapeZ_operatorDeleteArray__SWIG_0(long j, btCylinderShapeZ btcylindershapez, long j2);

    public static final native void btCylinderShapeZ_operatorDeleteArray__SWIG_1(long j, btCylinderShapeZ btcylindershapez, long j2, long j3);

    public static final native void btCylinderShapeZ_operatorDelete__SWIG_0(long j, btCylinderShapeZ btcylindershapez, long j2);

    public static final native void btCylinderShapeZ_operatorDelete__SWIG_1(long j, btCylinderShapeZ btcylindershapez, long j2, long j3);

    public static final native long btCylinderShapeZ_operatorNewArray__SWIG_0(long j, btCylinderShapeZ btcylindershapez, long j2);

    public static final native long btCylinderShapeZ_operatorNewArray__SWIG_1(long j, btCylinderShapeZ btcylindershapez, long j2, long j3);

    public static final native long btCylinderShapeZ_operatorNew__SWIG_0(long j, btCylinderShapeZ btcylindershapez, long j2);

    public static final native long btCylinderShapeZ_operatorNew__SWIG_1(long j, btCylinderShapeZ btcylindershapez, long j2, long j3);

    public static final native long btCylinderShape_SWIGUpcast(long j);

    public static final native Vector3 btCylinderShape_getHalfExtentsWithMargin(long j, btCylinderShape btcylindershape);

    public static final native Vector3 btCylinderShape_getHalfExtentsWithoutMargin(long j, btCylinderShape btcylindershape);

    public static final native float btCylinderShape_getRadius(long j, btCylinderShape btcylindershape);

    public static final native int btCylinderShape_getUpAxis(long j, btCylinderShape btcylindershape);

    public static final native void btCylinderShape_operatorDeleteArray__SWIG_0(long j, btCylinderShape btcylindershape, long j2);

    public static final native void btCylinderShape_operatorDeleteArray__SWIG_1(long j, btCylinderShape btcylindershape, long j2, long j3);

    public static final native void btCylinderShape_operatorDelete__SWIG_0(long j, btCylinderShape btcylindershape, long j2);

    public static final native void btCylinderShape_operatorDelete__SWIG_1(long j, btCylinderShape btcylindershape, long j2, long j3);

    public static final native long btCylinderShape_operatorNewArray__SWIG_0(long j, btCylinderShape btcylindershape, long j2);

    public static final native long btCylinderShape_operatorNewArray__SWIG_1(long j, btCylinderShape btcylindershape, long j2, long j3);

    public static final native long btCylinderShape_operatorNew__SWIG_0(long j, btCylinderShape btcylindershape, long j2);

    public static final native long btCylinderShape_operatorNew__SWIG_1(long j, btCylinderShape btcylindershape, long j2, long j3);

    public static final native Vector3 btDbvtAabbMm_Center(long j, btDbvtAabbMm btdbvtaabbmm);

    public static final native int btDbvtAabbMm_Classify(long j, btDbvtAabbMm btdbvtaabbmm, Vector3 vector3, float f, int i);

    public static final native boolean btDbvtAabbMm_Contain(long j, btDbvtAabbMm btdbvtaabbmm, long j2, btDbvtAabbMm btdbvtaabbmm2);

    public static final native void btDbvtAabbMm_Expand(long j, btDbvtAabbMm btdbvtaabbmm, Vector3 vector3);

    public static final native Vector3 btDbvtAabbMm_Extents(long j, btDbvtAabbMm btdbvtaabbmm);

    public static final native long btDbvtAabbMm_FromCE(Vector3 vector3, Vector3 vector4);

    public static final native long btDbvtAabbMm_FromCR(Vector3 vector3, float f);

    public static final native long btDbvtAabbMm_FromMM(Vector3 vector3, Vector3 vector4);

    public static final native long btDbvtAabbMm_FromPoints__SWIG_0(long j, btVector3 btvector3, int i);

    public static final native long btDbvtAabbMm_FromPoints__SWIG_1(long j, int i);

    public static final native Vector3 btDbvtAabbMm_Lengths(long j, btDbvtAabbMm btdbvtaabbmm);

    public static final native Vector3 btDbvtAabbMm_Maxs(long j, btDbvtAabbMm btdbvtaabbmm);

    public static final native Vector3 btDbvtAabbMm_Mins(long j, btDbvtAabbMm btdbvtaabbmm);

    public static final native float btDbvtAabbMm_ProjectMinimum(long j, btDbvtAabbMm btdbvtaabbmm, Vector3 vector3, long j2);

    public static final native void btDbvtAabbMm_SignedExpand(long j, btDbvtAabbMm btdbvtaabbmm, Vector3 vector3);

    public static final native Vector3 btDbvtAabbMm_tMaxs(long j, btDbvtAabbMm btdbvtaabbmm);

    public static final native Vector3 btDbvtAabbMm_tMins(long j, btDbvtAabbMm btdbvtaabbmm);

    public static final native long btDbvtBroadphase_SWIGUpcast(long j);

    public static final native void btDbvtBroadphase_benchmark(long j, btBroadphaseInterface btbroadphaseinterface);

    public static final native int btDbvtBroadphase_cid_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_cid_set(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native void btDbvtBroadphase_collide(long j, btDbvtBroadphase btdbvtbroadphase, long j2, btDispatcher btdispatcher);

    public static final native int btDbvtBroadphase_cupdates_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_cupdates_set(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native boolean btDbvtBroadphase_deferedcollide_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_deferedcollide_set(long j, btDbvtBroadphase btdbvtbroadphase, boolean z);

    public static final native int btDbvtBroadphase_dupdates_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_dupdates_set(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native int btDbvtBroadphase_fixedleft_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_fixedleft_set(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native int btDbvtBroadphase_fupdates_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_fupdates_set(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native long btDbvtBroadphase_getSet(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native long btDbvtBroadphase_getSet0(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native long btDbvtBroadphase_getSet1(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native float btDbvtBroadphase_getVelocityPrediction(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native int btDbvtBroadphase_gid_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_gid_set(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native boolean btDbvtBroadphase_needcleanup_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_needcleanup_set(long j, btDbvtBroadphase btdbvtbroadphase, boolean z);

    public static final native int btDbvtBroadphase_newpairs_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_newpairs_set(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native void btDbvtBroadphase_optimize(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native long btDbvtBroadphase_paircache_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_paircache_set(long j, btDbvtBroadphase btdbvtbroadphase, long j2, btOverlappingPairCache btoverlappingpaircache);

    public static final native void btDbvtBroadphase_performDeferredRemoval(long j, btDbvtBroadphase btdbvtbroadphase, long j2, btDispatcher btdispatcher);

    public static final native int btDbvtBroadphase_pid_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_pid_set(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native float btDbvtBroadphase_prediction_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_prediction_set(long j, btDbvtBroadphase btdbvtbroadphase, float f);

    public static final native long btDbvtBroadphase_rayTestStacks_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_rayTestStacks_set(long j, btDbvtBroadphase btdbvtbroadphase, long j2);

    public static final native void btDbvtBroadphase_rayTest__SWIG_0(long j, btDbvtBroadphase btdbvtbroadphase, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6);

    public static final native void btDbvtBroadphase_rayTest__SWIG_1(long j, btDbvtBroadphase btdbvtbroadphase, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5);

    public static final native void btDbvtBroadphase_rayTest__SWIG_2(long j, btDbvtBroadphase btdbvtbroadphase, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback);

    public static final native boolean btDbvtBroadphase_releasepaircache_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_releasepaircache_set(long j, btDbvtBroadphase btdbvtbroadphase, boolean z);

    public static final native void btDbvtBroadphase_setAabbForceUpdate(long j, btDbvtBroadphase btdbvtbroadphase, long j2, btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4, long j3, btDispatcher btdispatcher);

    public static final native void btDbvtBroadphase_setVelocityPrediction(long j, btDbvtBroadphase btdbvtbroadphase, float f);

    public static final native long btDbvtBroadphase_sets_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_sets_set(long j, btDbvtBroadphase btdbvtbroadphase, long j2, btDbvt btdbvt);

    public static final native int btDbvtBroadphase_stageCurrent_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_stageCurrent_set(long j, btDbvtBroadphase btdbvtbroadphase, int i);

    public static final native long btDbvtBroadphase_stageRoots_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_stageRoots_set(long j, btDbvtBroadphase btdbvtbroadphase, long j2);

    public static final native long btDbvtBroadphase_updates_call_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_updates_call_set(long j, btDbvtBroadphase btdbvtbroadphase, long j2);

    public static final native long btDbvtBroadphase_updates_done_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_updates_done_set(long j, btDbvtBroadphase btdbvtbroadphase, long j2);

    public static final native float btDbvtBroadphase_updates_ratio_get(long j, btDbvtBroadphase btdbvtbroadphase);

    public static final native void btDbvtBroadphase_updates_ratio_set(long j, btDbvtBroadphase btdbvtbroadphase, float f);

    public static final native long btDbvtNode_childs_get(long j, btDbvtNode btdbvtnode);

    public static final native void btDbvtNode_childs_set(long j, btDbvtNode btdbvtnode, long j2);

    public static final native int btDbvtNode_dataAsInt_get(long j, btDbvtNode btdbvtnode);

    public static final native void btDbvtNode_dataAsInt_set(long j, btDbvtNode btdbvtnode, int i);

    public static final native long btDbvtNode_data_get(long j, btDbvtNode btdbvtnode);

    public static final native void btDbvtNode_data_set(long j, btDbvtNode btdbvtnode, long j2);

    public static final native long btDbvtNode_getChild(long j, btDbvtNode btdbvtnode, int i);

    public static final native long btDbvtNode_getChild0(long j, btDbvtNode btdbvtnode);

    public static final native long btDbvtNode_getChild1(long j, btDbvtNode btdbvtnode);

    public static final native long btDbvtNode_getDataAsProxy(long j, btDbvtNode btdbvtnode);

    public static final native long btDbvtNode_getDataAsProxyClientObject(long j, btDbvtNode btdbvtnode);

    public static final native boolean btDbvtNode_isinternal(long j, btDbvtNode btdbvtnode);

    public static final native boolean btDbvtNode_isleaf(long j, btDbvtNode btdbvtnode);

    public static final native long btDbvtNode_parent_get(long j, btDbvtNode btdbvtnode);

    public static final native void btDbvtNode_parent_set(long j, btDbvtNode btdbvtnode, long j2, btDbvtNode btdbvtnode2);

    public static final native long btDbvtNode_volume_get(long j, btDbvtNode btdbvtnode);

    public static final native void btDbvtNode_volume_set(long j, btDbvtNode btdbvtnode, long j2, btDbvtAabbMm btdbvtaabbmm);

    public static final native long btDbvtProxy_SWIGUpcast(long j);

    public static final native long btDbvtProxy_leaf_get(long j, btDbvtProxy btdbvtproxy);

    public static final native void btDbvtProxy_leaf_set(long j, btDbvtProxy btdbvtproxy, long j2, btDbvtNode btdbvtnode);

    public static final native long btDbvtProxy_links_get(long j, btDbvtProxy btdbvtproxy);

    public static final native void btDbvtProxy_links_set(long j, btDbvtProxy btdbvtproxy, long j2);

    public static final native int btDbvtProxy_stage_get(long j, btDbvtProxy btdbvtproxy);

    public static final native void btDbvtProxy_stage_set(long j, btDbvtProxy btdbvtproxy, int i);

    public static final native void btDbvt_IClone_CloneLeaf(long j, btDbvt.IClone iClone, long j2, btDbvtNode btdbvtnode);

    public static final native void btDbvt_IWriter_Prepare(long j, btDbvt.IWriter iWriter, long j2, btDbvtNode btdbvtnode, int i);

    public static final native void btDbvt_IWriter_WriteLeaf(long j, btDbvt.IWriter iWriter, long j2, btDbvtNode btdbvtnode, int i, int i2);

    public static final native void btDbvt_IWriter_WriteNode(long j, btDbvt.IWriter iWriter, long j2, btDbvtNode btdbvtnode, int i, int i2, int i3, int i4);

    public static final native int btDbvt_allocate(long j, long j2, long j3, btDbvt.sStkNPS sstknps);

    public static final native void btDbvt_benchmark();

    public static final native void btDbvt_clear(long j, btDbvt btdbvt);

    public static final native void btDbvt_clone__SWIG_0(long j, btDbvt btdbvt, long j2, btDbvt btdbvt2, long j3, btDbvt.IClone iClone);

    public static final native void btDbvt_clone__SWIG_1(long j, btDbvt btdbvt, long j2, btDbvt btdbvt2);

    public static final native void btDbvt_collideKDOP__SWIG_0(long j, btDbvtNode btdbvtnode, long j2, btVector3 btvector3, FloatBuffer floatBuffer, int i, long j3, ICollide iCollide);

    public static final native void btDbvt_collideKDOP__SWIG_1(long j, btDbvtNode btdbvtnode, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, int i, long j2, ICollide iCollide);

    public static final native void btDbvt_collideOCL__SWIG_0(long j, btDbvtNode btdbvtnode, long j2, btVector3 btvector3, FloatBuffer floatBuffer, Vector3 vector3, int i, long j3, ICollide iCollide, boolean z);

    public static final native void btDbvt_collideOCL__SWIG_1(long j, btDbvtNode btdbvtnode, long j2, btVector3 btvector3, FloatBuffer floatBuffer, Vector3 vector3, int i, long j3, ICollide iCollide);

    public static final native void btDbvt_collideOCL__SWIG_2(long j, btDbvtNode btdbvtnode, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, Vector3 vector3, int i, long j2, ICollide iCollide, boolean z);

    public static final native void btDbvt_collideOCL__SWIG_3(long j, btDbvtNode btdbvtnode, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, Vector3 vector3, int i, long j2, ICollide iCollide);

    public static final native void btDbvt_collideTT(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, long j3, btDbvtNode btdbvtnode2, long j4, ICollide iCollide);

    public static final native void btDbvt_collideTTpersistentStack(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, long j3, btDbvtNode btdbvtnode2, long j4, ICollide iCollide);

    public static final native void btDbvt_collideTU(long j, btDbvtNode btdbvtnode, long j2, ICollide iCollide);

    public static final native void btDbvt_collideTV(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, long j3, btDbvtAabbMm btdbvtaabbmm, long j4, ICollide iCollide);

    public static final native void btDbvt_collideTVNoStackAlloc(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, long j3, btDbvtAabbMm btdbvtaabbmm, long j4, long j5, ICollide iCollide);

    public static final native int btDbvt_countLeaves(long j, btDbvtNode btdbvtnode);

    public static final native boolean btDbvt_empty(long j, btDbvt btdbvt);

    public static final native void btDbvt_enumLeaves(long j, btDbvtNode btdbvtnode, long j2, ICollide iCollide);

    public static final native void btDbvt_enumNodes(long j, btDbvtNode btdbvtnode, long j2, ICollide iCollide);

    public static final native void btDbvt_extractLeaves(long j, btDbvtNode btdbvtnode, long j2);

    public static final native long btDbvt_free_get(long j, btDbvt btdbvt);

    public static final native void btDbvt_free_set(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode);

    public static final native long btDbvt_insert(long j, btDbvt btdbvt, long j2, btDbvtAabbMm btdbvtaabbmm, long j3);

    public static final native int btDbvt_leaves_get(long j, btDbvt btdbvt);

    public static final native void btDbvt_leaves_set(long j, btDbvt btdbvt, int i);

    public static final native int btDbvt_lkhd_get(long j, btDbvt btdbvt);

    public static final native void btDbvt_lkhd_set(long j, btDbvt btdbvt, int i);

    public static final native int btDbvt_maxdepth(long j, btDbvtNode btdbvtnode);

    public static final native int btDbvt_nearest(IntBuffer intBuffer, long j, btDbvt.sStkNPS sstknps, float f, int i, int i2);

    public static final native long btDbvt_opath_get(long j, btDbvt btdbvt);

    public static final native void btDbvt_opath_set(long j, btDbvt btdbvt, long j2);

    public static final native void btDbvt_optimizeBottomUp(long j, btDbvt btdbvt);

    public static final native void btDbvt_optimizeIncremental(long j, btDbvt btdbvt, int i);

    public static final native void btDbvt_optimizeTopDown__SWIG_0(long j, btDbvt btdbvt, int i);

    public static final native void btDbvt_optimizeTopDown__SWIG_1(long j, btDbvt btdbvt);

    public static final native void btDbvt_rayTest(long j, btDbvtNode btdbvtnode, Vector3 vector3, Vector3 vector4, long j2, ICollide iCollide);

    public static final native void btDbvt_rayTestInternal(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, Vector3 vector3, Vector3 vector4, Vector3 vector5, long[] jArr, float f, Vector3 vector6, Vector3 vector7, long j3, long j4, ICollide iCollide);

    public static final native void btDbvt_remove(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode);

    public static final native long btDbvt_root_get(long j, btDbvt btdbvt);

    public static final native void btDbvt_root_set(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode);

    public static final native long btDbvt_sStkCLN_node_get(long j, btDbvt.sStkCLN sstkcln);

    public static final native void btDbvt_sStkCLN_node_set(long j, btDbvt.sStkCLN sstkcln, long j2, btDbvtNode btdbvtnode);

    public static final native long btDbvt_sStkCLN_parent_get(long j, btDbvt.sStkCLN sstkcln);

    public static final native void btDbvt_sStkCLN_parent_set(long j, btDbvt.sStkCLN sstkcln, long j2, btDbvtNode btdbvtnode);

    public static final native long btDbvt_sStkNN_a_get(long j, btDbvt.sStkNN sstknn);

    public static final native void btDbvt_sStkNN_a_set(long j, btDbvt.sStkNN sstknn, long j2, btDbvtNode btdbvtnode);

    public static final native long btDbvt_sStkNN_b_get(long j, btDbvt.sStkNN sstknn);

    public static final native void btDbvt_sStkNN_b_set(long j, btDbvt.sStkNN sstknn, long j2, btDbvtNode btdbvtnode);

    public static final native int btDbvt_sStkNPS_mask_get(long j, btDbvt.sStkNPS sstknps);

    public static final native void btDbvt_sStkNPS_mask_set(long j, btDbvt.sStkNPS sstknps, int i);

    public static final native long btDbvt_sStkNPS_node_get(long j, btDbvt.sStkNPS sstknps);

    public static final native void btDbvt_sStkNPS_node_set(long j, btDbvt.sStkNPS sstknps, long j2, btDbvtNode btdbvtnode);

    public static final native float btDbvt_sStkNPS_value_get(long j, btDbvt.sStkNPS sstknps);

    public static final native void btDbvt_sStkNPS_value_set(long j, btDbvt.sStkNPS sstknps, float f);

    public static final native int btDbvt_sStkNP_mask_get(long j, btDbvt.sStkNP sstknp);

    public static final native void btDbvt_sStkNP_mask_set(long j, btDbvt.sStkNP sstknp, int i);

    public static final native long btDbvt_sStkNP_node_get(long j, btDbvt.sStkNP sstknp);

    public static final native void btDbvt_sStkNP_node_set(long j, btDbvt.sStkNP sstknp, long j2, btDbvtNode btdbvtnode);

    public static final native long btDbvt_stkStack_get(long j, btDbvt btdbvt);

    public static final native void btDbvt_stkStack_set(long j, btDbvt btdbvt, long j2);

    public static final native void btDbvt_update__SWIG_0(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, int i);

    public static final native void btDbvt_update__SWIG_1(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode);

    public static final native void btDbvt_update__SWIG_2(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, long j3, btDbvtAabbMm btdbvtaabbmm);

    public static final native boolean btDbvt_update__SWIG_3(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, long j3, btDbvtAabbMm btdbvtaabbmm, Vector3 vector3, float f);

    public static final native boolean btDbvt_update__SWIG_4(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, long j3, btDbvtAabbMm btdbvtaabbmm, Vector3 vector3);

    public static final native boolean btDbvt_update__SWIG_5(long j, btDbvt btdbvt, long j2, btDbvtNode btdbvtnode, long j3, btDbvtAabbMm btdbvtaabbmm, float f);

    public static final native void btDbvt_write(long j, btDbvt btdbvt, long j2, btDbvt.IWriter iWriter);

    public static final native long btDefaultCollisionConfiguration_SWIGUpcast(long j);

    public static final native void btDefaultCollisionConfiguration_setConvexConvexMultipointIterations__SWIG_0(long j, btDefaultCollisionConfiguration btdefaultcollisionconfiguration, int i, int i2);

    public static final native void btDefaultCollisionConfiguration_setConvexConvexMultipointIterations__SWIG_1(long j, btDefaultCollisionConfiguration btdefaultcollisionconfiguration, int i);

    public static final native void btDefaultCollisionConfiguration_setConvexConvexMultipointIterations__SWIG_2(long j, btDefaultCollisionConfiguration btdefaultcollisionconfiguration);

    public static final native void btDefaultCollisionConfiguration_setPlaneConvexMultipointIterations__SWIG_0(long j, btDefaultCollisionConfiguration btdefaultcollisionconfiguration, int i, int i2);

    public static final native void btDefaultCollisionConfiguration_setPlaneConvexMultipointIterations__SWIG_1(long j, btDefaultCollisionConfiguration btdefaultcollisionconfiguration, int i);

    public static final native void btDefaultCollisionConfiguration_setPlaneConvexMultipointIterations__SWIG_2(long j, btDefaultCollisionConfiguration btdefaultcollisionconfiguration);

    public static final native long btDefaultCollisionConstructionInfo_collisionAlgorithmPool_get(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo);

    public static final native void btDefaultCollisionConstructionInfo_collisionAlgorithmPool_set(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo, long j2, btPoolAllocator btpoolallocator);

    public static final native int btDefaultCollisionConstructionInfo_customCollisionAlgorithmMaxElementSize_get(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo);

    public static final native void btDefaultCollisionConstructionInfo_customCollisionAlgorithmMaxElementSize_set(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo, int i);

    public static final native int btDefaultCollisionConstructionInfo_defaultMaxCollisionAlgorithmPoolSize_get(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo);

    public static final native void btDefaultCollisionConstructionInfo_defaultMaxCollisionAlgorithmPoolSize_set(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo, int i);

    public static final native int btDefaultCollisionConstructionInfo_defaultMaxPersistentManifoldPoolSize_get(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo);

    public static final native void btDefaultCollisionConstructionInfo_defaultMaxPersistentManifoldPoolSize_set(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo, int i);

    public static final native long btDefaultCollisionConstructionInfo_persistentManifoldPool_get(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo);

    public static final native void btDefaultCollisionConstructionInfo_persistentManifoldPool_set(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo, long j2, btPoolAllocator btpoolallocator);

    public static final native int btDefaultCollisionConstructionInfo_useEpaPenetrationAlgorithm_get(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo);

    public static final native void btDefaultCollisionConstructionInfo_useEpaPenetrationAlgorithm_set(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo, int i);

    public static final native float btDiscreteCollisionDetectorInterface_ClosestPointInput_maximumDistanceSquared_get(long j, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput);

    public static final native void btDiscreteCollisionDetectorInterface_ClosestPointInput_maximumDistanceSquared_set(long j, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, float f);

    public static final native long btDiscreteCollisionDetectorInterface_ClosestPointInput_transformA_get(long j, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput);

    public static final native void btDiscreteCollisionDetectorInterface_ClosestPointInput_transformA_set(long j, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j2, btTransform bttransform);

    public static final native long btDiscreteCollisionDetectorInterface_ClosestPointInput_transformB_get(long j, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput);

    public static final native void btDiscreteCollisionDetectorInterface_ClosestPointInput_transformB_set(long j, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j2, btTransform bttransform);

    public static final native void btDiscreteCollisionDetectorInterface_Result_addContactPoint(long j, btDiscreteCollisionDetectorInterface.Result result, Vector3 vector3, Vector3 vector4, float f);

    public static final native void btDiscreteCollisionDetectorInterface_Result_setShapeIdentifiersA(long j, btDiscreteCollisionDetectorInterface.Result result, int i, int i2);

    public static final native void btDiscreteCollisionDetectorInterface_Result_setShapeIdentifiersB(long j, btDiscreteCollisionDetectorInterface.Result result, int i, int i2);

    public static final native void btDiscreteCollisionDetectorInterface_getClosestPoints__SWIG_0(long j, btDiscreteCollisionDetectorInterface btdiscretecollisiondetectorinterface, long j2, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j3, btDiscreteCollisionDetectorInterface.Result result, long j4, btIDebugDraw btidebugdraw, boolean z);

    public static final native void btDiscreteCollisionDetectorInterface_getClosestPoints__SWIG_1(long j, btDiscreteCollisionDetectorInterface btdiscretecollisiondetectorinterface, long j2, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j3, btDiscreteCollisionDetectorInterface.Result result, long j4, btIDebugDraw btidebugdraw);

    public static final native float btDispatcherInfo_allowedCcdPenetration_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_allowedCcdPenetration_set(long j, btDispatcherInfo btdispatcherinfo, float f);

    public static final native float btDispatcherInfo_convexConservativeDistanceThreshold_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_convexConservativeDistanceThreshold_set(long j, btDispatcherInfo btdispatcherinfo, float f);

    public static final native long btDispatcherInfo_debugDraw_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_debugDraw_set(long j, btDispatcherInfo btdispatcherinfo, long j2, btIDebugDraw btidebugdraw);

    public static final native int btDispatcherInfo_dispatchFunc_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_dispatchFunc_set(long j, btDispatcherInfo btdispatcherinfo, int i);

    public static final native boolean btDispatcherInfo_enableSPU_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_enableSPU_set(long j, btDispatcherInfo btdispatcherinfo, boolean z);

    public static final native boolean btDispatcherInfo_enableSatConvex_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_enableSatConvex_set(long j, btDispatcherInfo btdispatcherinfo, boolean z);

    public static final native int btDispatcherInfo_stepCount_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_stepCount_set(long j, btDispatcherInfo btdispatcherinfo, int i);

    public static final native float btDispatcherInfo_timeOfImpact_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_timeOfImpact_set(long j, btDispatcherInfo btdispatcherinfo, float f);

    public static final native float btDispatcherInfo_timeStep_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_timeStep_set(long j, btDispatcherInfo btdispatcherinfo, float f);

    public static final native boolean btDispatcherInfo_useContinuous_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_useContinuous_set(long j, btDispatcherInfo btdispatcherinfo, boolean z);

    public static final native boolean btDispatcherInfo_useConvexConservativeDistanceUtil_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_useConvexConservativeDistanceUtil_set(long j, btDispatcherInfo btdispatcherinfo, boolean z);

    public static final native boolean btDispatcherInfo_useEpa_get(long j, btDispatcherInfo btdispatcherinfo);

    public static final native void btDispatcherInfo_useEpa_set(long j, btDispatcherInfo btdispatcherinfo, boolean z);

    public static final native long btDispatcher_allocateCollisionAlgorithm(long j, btDispatcher btdispatcher, int i);

    public static final native void btDispatcher_clearManifold(long j, btDispatcher btdispatcher, long j2, btPersistentManifold btpersistentmanifold);

    public static final native void btDispatcher_dispatchAllCollisionPairs(long j, btDispatcher btdispatcher, long j2, btOverlappingPairCache btoverlappingpaircache, long j3, btDispatcherInfo btdispatcherinfo, long j4, btDispatcher btdispatcher2);

    public static final native long btDispatcher_findAlgorithm(long j, btDispatcher btdispatcher, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, long j4, btPersistentManifold btpersistentmanifold, int i);

    public static final native void btDispatcher_freeCollisionAlgorithm(long j, btDispatcher btdispatcher, long j2);

    public static final native long btDispatcher_getInternalManifoldPointer(long j, btDispatcher btdispatcher);

    public static final native long btDispatcher_getInternalManifoldPool(long j, btDispatcher btdispatcher);

    public static final native long btDispatcher_getInternalManifoldPoolConst(long j, btDispatcher btdispatcher);

    public static final native long btDispatcher_getManifoldByIndexInternal(long j, btDispatcher btdispatcher, int i);

    public static final native long btDispatcher_getNewManifold(long j, btDispatcher btdispatcher, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native int btDispatcher_getNumManifolds(long j, btDispatcher btdispatcher);

    public static final native boolean btDispatcher_needsCollision(long j, btDispatcher btdispatcher, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native boolean btDispatcher_needsResponse(long j, btDispatcher btdispatcher, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native void btDispatcher_releaseManifold(long j, btDispatcher btdispatcher, long j2, btPersistentManifold btpersistentmanifold);

    public static final native int btElement_id_get(long j, btElement btelement);

    public static final native void btElement_id_set(long j, btElement btelement, int i);

    public static final native int btElement_sz_get(long j, btElement btelement);

    public static final native void btElement_sz_set(long j, btElement btelement, int i);

    public static final native long btEmptyAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btEmptyAlgorithm_SWIGUpcast(long j);

    public static final native long btEmptyShape_SWIGUpcast(long j);

    public static final native void btEmptyShape_operatorDeleteArray__SWIG_0(long j, btEmptyShape btemptyshape, long j2);

    public static final native void btEmptyShape_operatorDeleteArray__SWIG_1(long j, btEmptyShape btemptyshape, long j2, long j3);

    public static final native void btEmptyShape_operatorDelete__SWIG_0(long j, btEmptyShape btemptyshape, long j2);

    public static final native void btEmptyShape_operatorDelete__SWIG_1(long j, btEmptyShape btemptyshape, long j2, long j3);

    public static final native long btEmptyShape_operatorNewArray__SWIG_0(long j, btEmptyShape btemptyshape, long j2);

    public static final native long btEmptyShape_operatorNewArray__SWIG_1(long j, btEmptyShape btemptyshape, long j2, long j3);

    public static final native long btEmptyShape_operatorNew__SWIG_0(long j, btEmptyShape btemptyshape, long j2);

    public static final native long btEmptyShape_operatorNew__SWIG_1(long j, btEmptyShape btemptyshape, long j2, long j3);

    public static final native void btExpandPortal(long j, _btMprSimplex_t _btmprsimplex_t, long j2, _btMprSupport_t _btmprsupport_t);

    public static final native long btFace_indices_get(long j, btFace btface);

    public static final native void btFace_indices_set(long j, btFace btface, long j2);

    public static final native float[] btFace_plane_get(long j, btFace btface);

    public static final native void btFace_plane_set(long j, btFace btface, float[] fArr);

    public static final native void btFindPenetrSegment(long j, _btMprSimplex_t _btmprsimplex_t, FloatBuffer floatBuffer, long j2, btVector3 btvector3, long j3, btVector3 btvector4);

    public static final native void btFindPenetrTouch(long j, _btMprSimplex_t _btmprsimplex_t, FloatBuffer floatBuffer, long j2, btVector3 btvector3, long j3, btVector3 btvector4);

    public static final native void btFindPos(long j, _btMprSimplex_t _btmprsimplex_t, long j2, btVector3 btvector3);

    public static final native boolean btGImpactBvh_boxQuery(long j, btGImpactBvh btgimpactbvh, long j2, btAABB btaabb, long j3);

    public static final native boolean btGImpactBvh_boxQueryTrans(long j, btGImpactBvh btgimpactbvh, long j2, btAABB btaabb, Matrix4 matrix4, long j3);

    public static final native void btGImpactBvh_buildSet(long j, btGImpactBvh btgimpactbvh);

    public static final native void btGImpactBvh_find_collision(long j, btGImpactBvh btgimpactbvh, Matrix4 matrix4, long j2, btGImpactBvh btgimpactbvh2, Matrix4 matrix5, long j3, btPairSet btpairset);

    public static final native int btGImpactBvh_getEscapeNodeIndex(long j, btGImpactBvh btgimpactbvh, int i);

    public static final native long btGImpactBvh_getGlobalBox(long j, btGImpactBvh btgimpactbvh);

    public static final native int btGImpactBvh_getLeftNode(long j, btGImpactBvh btgimpactbvh, int i);

    public static final native void btGImpactBvh_getNodeBound(long j, btGImpactBvh btgimpactbvh, int i, long j2, btAABB btaabb);

    public static final native int btGImpactBvh_getNodeCount(long j, btGImpactBvh btgimpactbvh);

    public static final native int btGImpactBvh_getNodeData(long j, btGImpactBvh btgimpactbvh, int i);

    public static final native void btGImpactBvh_getNodeTriangle(long j, btGImpactBvh btgimpactbvh, int i, long j2, btPrimitiveTriangle btprimitivetriangle);

    public static final native long btGImpactBvh_getPrimitiveManager(long j, btGImpactBvh btgimpactbvh);

    public static final native int btGImpactBvh_getRightNode(long j, btGImpactBvh btgimpactbvh, int i);

    public static final native long btGImpactBvh_get_node_pointer__SWIG_0(long j, btGImpactBvh btgimpactbvh, int i);

    public static final native long btGImpactBvh_get_node_pointer__SWIG_1(long j, btGImpactBvh btgimpactbvh);

    public static final native boolean btGImpactBvh_hasHierarchy(long j, btGImpactBvh btgimpactbvh);

    public static final native boolean btGImpactBvh_isLeafNode(long j, btGImpactBvh btgimpactbvh, int i);

    public static final native boolean btGImpactBvh_isTrimesh(long j, btGImpactBvh btgimpactbvh);

    public static final native boolean btGImpactBvh_rayQuery(long j, btGImpactBvh btgimpactbvh, Vector3 vector3, Vector3 vector4, long j2);

    public static final native void btGImpactBvh_setNodeBound(long j, btGImpactBvh btgimpactbvh, int i, long j2, btAABB btaabb);

    public static final native void btGImpactBvh_setPrimitiveManager(long j, btGImpactBvh btgimpactbvh, long j2, btPrimitiveManagerBase btprimitivemanagerbase);

    public static final native void btGImpactBvh_update(long j, btGImpactBvh btgimpactbvh);

    public static final native long btGImpactCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btGImpactCollisionAlgorithm_SWIGUpcast(long j);

    public static final native int btGImpactCollisionAlgorithm_getFace0(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm);

    public static final native int btGImpactCollisionAlgorithm_getFace1(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm);

    public static final native int btGImpactCollisionAlgorithm_getPart0(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm);

    public static final native int btGImpactCollisionAlgorithm_getPart1(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm);

    public static final native void btGImpactCollisionAlgorithm_gimpact_vs_compoundshape(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, long j4, btGImpactShapeInterface btgimpactshapeinterface, long j5, btCompoundShape btcompoundshape, boolean z);

    public static final native void btGImpactCollisionAlgorithm_gimpact_vs_concave(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, long j4, btGImpactShapeInterface btgimpactshapeinterface, long j5, btConcaveShape btconcaveshape, boolean z);

    public static final native void btGImpactCollisionAlgorithm_gimpact_vs_gimpact(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, long j4, btGImpactShapeInterface btgimpactshapeinterface, long j5, btGImpactShapeInterface btgimpactshapeinterface2);

    public static final native void btGImpactCollisionAlgorithm_gimpact_vs_shape(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, long j4, btGImpactShapeInterface btgimpactshapeinterface, long j5, btCollisionShape btcollisionshape, boolean z);

    public static final native long btGImpactCollisionAlgorithm_internalGetResultOut(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm);

    public static final native void btGImpactCollisionAlgorithm_registerAlgorithm(long j, btCollisionDispatcher btcollisiondispatcher);

    public static final native void btGImpactCollisionAlgorithm_setFace0(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm, int i);

    public static final native void btGImpactCollisionAlgorithm_setFace1(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm, int i);

    public static final native void btGImpactCollisionAlgorithm_setPart0(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm, int i);

    public static final native void btGImpactCollisionAlgorithm_setPart1(long j, btGImpactCollisionAlgorithm btgimpactcollisionalgorithm, int i);

    public static final native long btGImpactCompoundShape_CompoundPrimitiveManager_SWIGUpcast(long j);

    public static final native long btGImpactCompoundShape_CompoundPrimitiveManager_compoundShape_get(long j, btGImpactCompoundShape.CompoundPrimitiveManager compoundPrimitiveManager);

    public static final native void btGImpactCompoundShape_CompoundPrimitiveManager_compoundShape_set(long j, btGImpactCompoundShape.CompoundPrimitiveManager compoundPrimitiveManager, long j2, btGImpactCompoundShape btgimpactcompoundshape);

    public static final native long btGImpactCompoundShape_SWIGUpcast(long j);

    public static final native void btGImpactCompoundShape_addChildShape__SWIG_0(long j, btGImpactCompoundShape btgimpactcompoundshape, Matrix4 matrix4, long j2, btCollisionShape btcollisionshape);

    public static final native void btGImpactCompoundShape_addChildShape__SWIG_1(long j, btGImpactCompoundShape btgimpactcompoundshape, long j2, btCollisionShape btcollisionshape);

    public static final native long btGImpactCompoundShape_getCompoundPrimitiveManager(long j, btGImpactCompoundShape btgimpactcompoundshape);

    public static final native float btGImpactMeshShapeData_collisionMargin_get(long j, btGImpactMeshShapeData btgimpactmeshshapedata);

    public static final native void btGImpactMeshShapeData_collisionMargin_set(long j, btGImpactMeshShapeData btgimpactmeshshapedata, float f);

    public static final native long btGImpactMeshShapeData_collisionShapeData_get(long j, btGImpactMeshShapeData btgimpactmeshshapedata);

    public static final native void btGImpactMeshShapeData_collisionShapeData_set(long j, btGImpactMeshShapeData btgimpactmeshshapedata, long j2, btCollisionShapeData btcollisionshapedata);

    public static final native int btGImpactMeshShapeData_gimpactSubType_get(long j, btGImpactMeshShapeData btgimpactmeshshapedata);

    public static final native void btGImpactMeshShapeData_gimpactSubType_set(long j, btGImpactMeshShapeData btgimpactmeshshapedata, int i);

    public static final native long btGImpactMeshShapeData_localScaling_get(long j, btGImpactMeshShapeData btgimpactmeshshapedata);

    public static final native void btGImpactMeshShapeData_localScaling_set(long j, btGImpactMeshShapeData btgimpactmeshshapedata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btGImpactMeshShapeData_meshInterface_get(long j, btGImpactMeshShapeData btgimpactmeshshapedata);

    public static final native void btGImpactMeshShapeData_meshInterface_set(long j, btGImpactMeshShapeData btgimpactmeshshapedata, long j2, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native long btGImpactMeshShapePart_SWIGUpcast(long j);

    public static final native long btGImpactMeshShapePart_TrimeshPrimitiveManager_SWIGUpcast(long j);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_get_bullet_triangle(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i, long j2, btTriangleShapeEx bttriangleshapeex);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_get_indices(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i, long j2, long j3, long j4);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_get_vertex(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, long j2, Vector3 vector3);

    public static final native int btGImpactMeshShapePart_TrimeshPrimitiveManager_get_vertex_count(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native ByteBuffer btGImpactMeshShapePart_TrimeshPrimitiveManager_indexbase_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_indexbase_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, ByteBuffer byteBuffer);

    public static final native int btGImpactMeshShapePart_TrimeshPrimitiveManager_indexstride_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_indexstride_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i);

    public static final native int btGImpactMeshShapePart_TrimeshPrimitiveManager_indicestype_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_indicestype_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_lock(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native int btGImpactMeshShapePart_TrimeshPrimitiveManager_lock_count_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_lock_count_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i);

    public static final native float btGImpactMeshShapePart_TrimeshPrimitiveManager_margin_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_margin_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, float f);

    public static final native long btGImpactMeshShapePart_TrimeshPrimitiveManager_meshInterface_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_meshInterface_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, long j2, btStridingMeshInterface btstridingmeshinterface);

    public static final native int btGImpactMeshShapePart_TrimeshPrimitiveManager_numfaces_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_numfaces_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i);

    public static final native int btGImpactMeshShapePart_TrimeshPrimitiveManager_numverts_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_numverts_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i);

    public static final native int btGImpactMeshShapePart_TrimeshPrimitiveManager_part_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_part_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i);

    public static final native long btGImpactMeshShapePart_TrimeshPrimitiveManager_scale_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_scale_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, long j2, btVector3 btvector3);

    public static final native int btGImpactMeshShapePart_TrimeshPrimitiveManager_stride_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_stride_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i);

    public static final native int btGImpactMeshShapePart_TrimeshPrimitiveManager_type_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_type_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, int i);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_unlock(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native ByteBuffer btGImpactMeshShapePart_TrimeshPrimitiveManager_vertexbase_get(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native void btGImpactMeshShapePart_TrimeshPrimitiveManager_vertexbase_set(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager, ByteBuffer byteBuffer);

    public static final native int btGImpactMeshShapePart_getPart(long j, btGImpactMeshShapePart btgimpactmeshshapepart);

    public static final native long btGImpactMeshShapePart_getTrimeshPrimitiveManager(long j, btGImpactMeshShapePart btgimpactmeshshapepart);

    public static final native void btGImpactMeshShapePart_getVertex(long j, btGImpactMeshShapePart btgimpactmeshshapepart, int i, Vector3 vector3);

    public static final native int btGImpactMeshShapePart_getVertexCount(long j, btGImpactMeshShapePart btgimpactmeshshapepart);

    public static final native long btGImpactMeshShape_SWIGUpcast(long j);

    public static final native long btGImpactMeshShape_getMeshInterface(long j, btGImpactMeshShape btgimpactmeshshape);

    public static final native long btGImpactMeshShape_getMeshInterfaceConst(long j, btGImpactMeshShape btgimpactmeshshape);

    public static final native long btGImpactMeshShape_getMeshPart(long j, btGImpactMeshShape btgimpactmeshshape, int i);

    public static final native long btGImpactMeshShape_getMeshPartConst(long j, btGImpactMeshShape btgimpactmeshshape, int i);

    public static final native int btGImpactMeshShape_getMeshPartCount(long j, btGImpactMeshShape btgimpactmeshshape);

    public static final native boolean btGImpactQuantizedBvh_boxQuery(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, long j2, btAABB btaabb, long j3);

    public static final native boolean btGImpactQuantizedBvh_boxQueryTrans(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, long j2, btAABB btaabb, Matrix4 matrix4, long j3);

    public static final native void btGImpactQuantizedBvh_buildSet(long j, btGImpactQuantizedBvh btgimpactquantizedbvh);

    public static final native void btGImpactQuantizedBvh_find_collision(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, Matrix4 matrix4, long j2, btGImpactQuantizedBvh btgimpactquantizedbvh2, Matrix4 matrix5, long j3, btPairSet btpairset);

    public static final native int btGImpactQuantizedBvh_getEscapeNodeIndex(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, int i);

    public static final native long btGImpactQuantizedBvh_getGlobalBox(long j, btGImpactQuantizedBvh btgimpactquantizedbvh);

    public static final native int btGImpactQuantizedBvh_getLeftNode(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, int i);

    public static final native void btGImpactQuantizedBvh_getNodeBound(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, int i, long j2, btAABB btaabb);

    public static final native int btGImpactQuantizedBvh_getNodeCount(long j, btGImpactQuantizedBvh btgimpactquantizedbvh);

    public static final native int btGImpactQuantizedBvh_getNodeData(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, int i);

    public static final native void btGImpactQuantizedBvh_getNodeTriangle(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, int i, long j2, btPrimitiveTriangle btprimitivetriangle);

    public static final native long btGImpactQuantizedBvh_getPrimitiveManager(long j, btGImpactQuantizedBvh btgimpactquantizedbvh);

    public static final native int btGImpactQuantizedBvh_getRightNode(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, int i);

    public static final native long btGImpactQuantizedBvh_get_node_pointer__SWIG_0(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, int i);

    public static final native long btGImpactQuantizedBvh_get_node_pointer__SWIG_1(long j, btGImpactQuantizedBvh btgimpactquantizedbvh);

    public static final native boolean btGImpactQuantizedBvh_hasHierarchy(long j, btGImpactQuantizedBvh btgimpactquantizedbvh);

    public static final native boolean btGImpactQuantizedBvh_isLeafNode(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, int i);

    public static final native boolean btGImpactQuantizedBvh_isTrimesh(long j, btGImpactQuantizedBvh btgimpactquantizedbvh);

    public static final native boolean btGImpactQuantizedBvh_rayQuery(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, Vector3 vector3, Vector3 vector4, long j2);

    public static final native void btGImpactQuantizedBvh_setNodeBound(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, int i, long j2, btAABB btaabb);

    public static final native void btGImpactQuantizedBvh_setPrimitiveManager(long j, btGImpactQuantizedBvh btgimpactquantizedbvh, long j2, btPrimitiveManagerBase btprimitivemanagerbase);

    public static final native void btGImpactQuantizedBvh_update(long j, btGImpactQuantizedBvh btgimpactquantizedbvh);

    public static final native long btGImpactShapeInterface_SWIGUpcast(long j);

    public static final native boolean btGImpactShapeInterface_childrenHasTransform(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native long btGImpactShapeInterface_getBoxSet(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native void btGImpactShapeInterface_getBulletTetrahedron(long j, btGImpactShapeInterface btgimpactshapeinterface, int i, long j2, btTetrahedronShapeEx bttetrahedronshapeex);

    public static final native void btGImpactShapeInterface_getBulletTriangle(long j, btGImpactShapeInterface btgimpactshapeinterface, int i, long j2, btTriangleShapeEx bttriangleshapeex);

    public static final native void btGImpactShapeInterface_getChildAabb(long j, btGImpactShapeInterface btgimpactshapeinterface, int i, Matrix4 matrix4, Vector3 vector3, Vector3 vector4);

    public static final native long btGImpactShapeInterface_getChildShape(long j, btGImpactShapeInterface btgimpactshapeinterface, int i);

    public static final native long btGImpactShapeInterface_getChildShapeConst(long j, btGImpactShapeInterface btgimpactshapeinterface, int i);

    public static final native Matrix4 btGImpactShapeInterface_getChildTransform(long j, btGImpactShapeInterface btgimpactshapeinterface, int i);

    public static final native int btGImpactShapeInterface_getGImpactShapeType(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native long btGImpactShapeInterface_getLocalBox(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native int btGImpactShapeInterface_getNumChildShapes(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native long btGImpactShapeInterface_getPrimitiveManager(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native void btGImpactShapeInterface_getPrimitiveTriangle(long j, btGImpactShapeInterface btgimpactshapeinterface, int i, long j2, btPrimitiveTriangle btprimitivetriangle);

    public static final native int btGImpactShapeInterface_getShapeType(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native boolean btGImpactShapeInterface_hasBoxSet(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native void btGImpactShapeInterface_lockChildShapes(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native boolean btGImpactShapeInterface_needsRetrieveTetrahedrons(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native boolean btGImpactShapeInterface_needsRetrieveTriangles(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native void btGImpactShapeInterface_postUpdate(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native void btGImpactShapeInterface_processAllTrianglesRay(long j, btGImpactShapeInterface btgimpactshapeinterface, long j2, btTriangleCallback bttrianglecallback, Vector3 vector3, Vector3 vector4);

    public static final native void btGImpactShapeInterface_rayTest(long j, btGImpactShapeInterface btgimpactshapeinterface, Vector3 vector3, Vector3 vector4, long j2, RayResultCallback rayResultCallback);

    public static final native void btGImpactShapeInterface_setChildTransform(long j, btGImpactShapeInterface btgimpactshapeinterface, int i, Matrix4 matrix4);

    public static final native void btGImpactShapeInterface_unlockChildShapes(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native void btGImpactShapeInterface_updateBound(long j, btGImpactShapeInterface btgimpactshapeinterface);

    public static final native void btGenerateInternalEdgeInfo(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, long j2, btTriangleInfoMap bttriangleinfomap);

    public static final native long btGenericMemoryPool_allocate(long j, btGenericMemoryPool btgenericmemorypool, long j2);

    public static final native long btGenericMemoryPool_allocated_count_get(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native void btGenericMemoryPool_allocated_count_set(long j, btGenericMemoryPool btgenericmemorypool, long j2);

    public static final native long btGenericMemoryPool_allocated_sizes_get(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native void btGenericMemoryPool_allocated_sizes_set(long j, btGenericMemoryPool btgenericmemorypool, long j2);

    public static final native void btGenericMemoryPool_end_pool(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native boolean btGenericMemoryPool_freeMemory(long j, btGenericMemoryPool btgenericmemorypool, long j2);

    public static final native long btGenericMemoryPool_free_nodes_count_get(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native void btGenericMemoryPool_free_nodes_count_set(long j, btGenericMemoryPool btgenericmemorypool, long j2);

    public static final native long btGenericMemoryPool_free_nodes_get(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native void btGenericMemoryPool_free_nodes_set(long j, btGenericMemoryPool btgenericmemorypool, long j2);

    public static final native long btGenericMemoryPool_gem_element_size(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native long btGenericMemoryPool_get_allocated_count(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native long btGenericMemoryPool_get_element_data(long j, btGenericMemoryPool btgenericmemorypool, long j2);

    public static final native long btGenericMemoryPool_get_free_positions_count(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native long btGenericMemoryPool_get_max_element_count(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native long btGenericMemoryPool_get_pool_capacity(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native void btGenericMemoryPool_init_pool(long j, btGenericMemoryPool btgenericmemorypool, long j2, long j3);

    public static final native ByteBuffer btGenericMemoryPool_pool_get(long j, btGenericMemoryPool btgenericmemorypool);

    public static final native void btGenericMemoryPool_pool_set(long j, btGenericMemoryPool btgenericmemorypool, ByteBuffer byteBuffer);

    public static final native long btGenericPoolAllocator_allocate(long j, btGenericPoolAllocator btgenericpoolallocator, long j2);

    public static final native boolean btGenericPoolAllocator_freeMemory(long j, btGenericPoolAllocator btgenericpoolallocator, long j2);

    public static final native long btGenericPoolAllocator_get_pool_capacity(long j, btGenericPoolAllocator btgenericpoolallocator);

    public static final native long btGenericPoolAllocator_pool_count_get(long j, btGenericPoolAllocator btgenericpoolallocator);

    public static final native void btGenericPoolAllocator_pool_count_set(long j, btGenericPoolAllocator btgenericpoolallocator, long j2);

    public static final native long btGenericPoolAllocator_pools_get(long j, btGenericPoolAllocator btgenericpoolallocator);

    public static final native void btGenericPoolAllocator_pools_set(long j, btGenericPoolAllocator btgenericpoolallocator, long j2);

    public static final native long btGhostObject_SWIGUpcast(long j);

    public static final native void btGhostObject_addOverlappingObjectInternal__SWIG_0(long j, btGhostObject btghostobject, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native void btGhostObject_addOverlappingObjectInternal__SWIG_1(long j, btGhostObject btghostobject, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native void btGhostObject_convexSweepTest__SWIG_0(long j, btGhostObject btghostobject, long j2, btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, long j3, ConvexResultCallback convexResultCallback, float f);

    public static final native void btGhostObject_convexSweepTest__SWIG_1(long j, btGhostObject btghostobject, long j2, btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, long j3, ConvexResultCallback convexResultCallback);

    public static final native int btGhostObject_getNumOverlappingObjects(long j, btGhostObject btghostobject);

    public static final native long btGhostObject_getOverlappingObject(long j, btGhostObject btghostobject, int i);

    public static final native long btGhostObject_getOverlappingObjectConst(long j, btGhostObject btghostobject, int i);

    public static final native long btGhostObject_getOverlappingPairs(long j, btGhostObject btghostobject);

    public static final native long btGhostObject_getOverlappingPairsConst(long j, btGhostObject btghostobject);

    public static final native void btGhostObject_rayTest(long j, btGhostObject btghostobject, Vector3 vector3, Vector3 vector4, long j2, RayResultCallback rayResultCallback);

    public static final native void btGhostObject_removeOverlappingObjectInternal__SWIG_0(long j, btGhostObject btghostobject, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btDispatcher btdispatcher, long j4, btBroadphaseProxy btbroadphaseproxy2);

    public static final native void btGhostObject_removeOverlappingObjectInternal__SWIG_1(long j, btGhostObject btghostobject, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btDispatcher btdispatcher);

    public static final native long btGhostObject_upcast(long j, btCollisionObject btcollisionobject);

    public static final native long btGhostObject_upcastConstBtCollisionObject(long j, btCollisionObject btcollisionobject);

    public static final native long btGhostPairCallback_SWIGUpcast(long j);

    public static final native void btGhostPairCallback_change_ownership(btGhostPairCallback btghostpaircallback, long j, boolean z);

    public static final native void btGhostPairCallback_director_connect(btGhostPairCallback btghostpaircallback, long j, boolean z, boolean z2);

    public static final native long btGhostPairCallback_removeOverlappingPair(long j, btGhostPairCallback btghostpaircallback, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2, long j4, btDispatcher btdispatcher);

    public static final native long btGhostPairCallback_removeOverlappingPairSwigExplicitbtGhostPairCallback(long j, btGhostPairCallback btghostpaircallback, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2, long j4, btDispatcher btdispatcher);

    public static final native void btGhostPairCallback_removeOverlappingPairsContainingProxy(long j, btGhostPairCallback btghostpaircallback, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btDispatcher btdispatcher);

    public static final native void btGhostPairCallback_removeOverlappingPairsContainingProxySwigExplicitbtGhostPairCallback(long j, btGhostPairCallback btghostpaircallback, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btDispatcher btdispatcher);

    public static final native long btGimBvhDataArray_at(long j, btGimBvhDataArray btgimbvhdataarray, int i);

    public static final native long btGimBvhDataArray_atConst(long j, btGimBvhDataArray btgimbvhdataarray, int i);

    public static final native int btGimBvhDataArray_capacity(long j, btGimBvhDataArray btgimbvhdataarray);

    public static final native void btGimBvhDataArray_clear(long j, btGimBvhDataArray btgimbvhdataarray);

    public static final native void btGimBvhDataArray_copyFromArray(long j, btGimBvhDataArray btgimbvhdataarray, long j2, btGimBvhDataArray btgimbvhdataarray2);

    public static final native long btGimBvhDataArray_expandNonInitializing(long j, btGimBvhDataArray btgimbvhdataarray);

    public static final native long btGimBvhDataArray_expand__SWIG_0(long j, btGimBvhDataArray btgimbvhdataarray, long j2, GIM_BVH_DATA gim_bvh_data);

    public static final native long btGimBvhDataArray_expand__SWIG_1(long j, btGimBvhDataArray btgimbvhdataarray);

    public static final native void btGimBvhDataArray_initializeFromBuffer(long j, btGimBvhDataArray btgimbvhdataarray, long j2, int i, int i2);

    public static final native long btGimBvhDataArray_operatorAssignment(long j, btGimBvhDataArray btgimbvhdataarray, long j2, btGimBvhDataArray btgimbvhdataarray2);

    public static final native long btGimBvhDataArray_operatorSubscript(long j, btGimBvhDataArray btgimbvhdataarray, int i);

    public static final native long btGimBvhDataArray_operatorSubscriptConst(long j, btGimBvhDataArray btgimbvhdataarray, int i);

    public static final native void btGimBvhDataArray_pop_back(long j, btGimBvhDataArray btgimbvhdataarray);

    public static final native void btGimBvhDataArray_push_back(long j, btGimBvhDataArray btgimbvhdataarray, long j2, GIM_BVH_DATA gim_bvh_data);

    public static final native void btGimBvhDataArray_removeAtIndex(long j, btGimBvhDataArray btgimbvhdataarray, int i);

    public static final native void btGimBvhDataArray_reserve(long j, btGimBvhDataArray btgimbvhdataarray, int i);

    public static final native void btGimBvhDataArray_resizeNoInitialize(long j, btGimBvhDataArray btgimbvhdataarray, int i);

    public static final native void btGimBvhDataArray_resize__SWIG_0(long j, btGimBvhDataArray btgimbvhdataarray, int i, long j2, GIM_BVH_DATA gim_bvh_data);

    public static final native void btGimBvhDataArray_resize__SWIG_1(long j, btGimBvhDataArray btgimbvhdataarray, int i);

    public static final native int btGimBvhDataArray_size(long j, btGimBvhDataArray btgimbvhdataarray);

    public static final native void btGimBvhDataArray_swap(long j, btGimBvhDataArray btgimbvhdataarray, int i, int i2);

    public static final native long btGimBvhTreeNodeArray_at(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i);

    public static final native long btGimBvhTreeNodeArray_atConst(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i);

    public static final native int btGimBvhTreeNodeArray_capacity(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray);

    public static final native void btGimBvhTreeNodeArray_clear(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray);

    public static final native void btGimBvhTreeNodeArray_copyFromArray(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, long j2, btGimBvhTreeNodeArray btgimbvhtreenodearray2);

    public static final native long btGimBvhTreeNodeArray_expandNonInitializing(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray);

    public static final native long btGimBvhTreeNodeArray_expand__SWIG_0(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, long j2, GIM_BVH_TREE_NODE gim_bvh_tree_node);

    public static final native long btGimBvhTreeNodeArray_expand__SWIG_1(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray);

    public static final native void btGimBvhTreeNodeArray_initializeFromBuffer(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, long j2, int i, int i2);

    public static final native long btGimBvhTreeNodeArray_operatorAssignment(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, long j2, btGimBvhTreeNodeArray btgimbvhtreenodearray2);

    public static final native long btGimBvhTreeNodeArray_operatorSubscript(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i);

    public static final native long btGimBvhTreeNodeArray_operatorSubscriptConst(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i);

    public static final native void btGimBvhTreeNodeArray_pop_back(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray);

    public static final native void btGimBvhTreeNodeArray_push_back(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, long j2, GIM_BVH_TREE_NODE gim_bvh_tree_node);

    public static final native void btGimBvhTreeNodeArray_removeAtIndex(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i);

    public static final native void btGimBvhTreeNodeArray_reserve(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i);

    public static final native void btGimBvhTreeNodeArray_resizeNoInitialize(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i);

    public static final native void btGimBvhTreeNodeArray_resize__SWIG_0(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i, long j2, GIM_BVH_TREE_NODE gim_bvh_tree_node);

    public static final native void btGimBvhTreeNodeArray_resize__SWIG_1(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i);

    public static final native int btGimBvhTreeNodeArray_size(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray);

    public static final native void btGimBvhTreeNodeArray_swap(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray, int i, int i2);

    public static final native long btGimContactArray_at(long j, btGimContactArray btgimcontactarray, int i);

    public static final native long btGimContactArray_atConst(long j, btGimContactArray btgimcontactarray, int i);

    public static final native int btGimContactArray_capacity(long j, btGimContactArray btgimcontactarray);

    public static final native void btGimContactArray_clear(long j, btGimContactArray btgimcontactarray);

    public static final native void btGimContactArray_copyFromArray(long j, btGimContactArray btgimcontactarray, long j2, btGimContactArray btgimcontactarray2);

    public static final native long btGimContactArray_expandNonInitializing(long j, btGimContactArray btgimcontactarray);

    public static final native long btGimContactArray_expand__SWIG_0(long j, btGimContactArray btgimcontactarray, long j2, GIM_CONTACT gim_contact);

    public static final native long btGimContactArray_expand__SWIG_1(long j, btGimContactArray btgimcontactarray);

    public static final native void btGimContactArray_initializeFromBuffer(long j, btGimContactArray btgimcontactarray, long j2, int i, int i2);

    public static final native long btGimContactArray_operatorAssignment(long j, btGimContactArray btgimcontactarray, long j2, btGimContactArray btgimcontactarray2);

    public static final native long btGimContactArray_operatorSubscript(long j, btGimContactArray btgimcontactarray, int i);

    public static final native long btGimContactArray_operatorSubscriptConst(long j, btGimContactArray btgimcontactarray, int i);

    public static final native void btGimContactArray_pop_back(long j, btGimContactArray btgimcontactarray);

    public static final native void btGimContactArray_push_back(long j, btGimContactArray btgimcontactarray, long j2, GIM_CONTACT gim_contact);

    public static final native void btGimContactArray_removeAtIndex(long j, btGimContactArray btgimcontactarray, int i);

    public static final native void btGimContactArray_reserve(long j, btGimContactArray btgimcontactarray, int i);

    public static final native void btGimContactArray_resizeNoInitialize(long j, btGimContactArray btgimcontactarray, int i);

    public static final native void btGimContactArray_resize__SWIG_0(long j, btGimContactArray btgimcontactarray, int i, long j2, GIM_CONTACT gim_contact);

    public static final native void btGimContactArray_resize__SWIG_1(long j, btGimContactArray btgimcontactarray, int i);

    public static final native int btGimContactArray_size(long j, btGimContactArray btgimcontactarray);

    public static final native void btGimContactArray_swap(long j, btGimContactArray btgimcontactarray, int i, int i2);

    public static final native long btGimPairArray_at(long j, btGimPairArray btgimpairarray, int i);

    public static final native long btGimPairArray_atConst(long j, btGimPairArray btgimpairarray, int i);

    public static final native int btGimPairArray_capacity(long j, btGimPairArray btgimpairarray);

    public static final native void btGimPairArray_clear(long j, btGimPairArray btgimpairarray);

    public static final native void btGimPairArray_copyFromArray(long j, btGimPairArray btgimpairarray, long j2, btGimPairArray btgimpairarray2);

    public static final native long btGimPairArray_expandNonInitializing(long j, btGimPairArray btgimpairarray);

    public static final native long btGimPairArray_expand__SWIG_0(long j, btGimPairArray btgimpairarray, long j2, GIM_PAIR gim_pair);

    public static final native long btGimPairArray_expand__SWIG_1(long j, btGimPairArray btgimpairarray);

    public static final native void btGimPairArray_initializeFromBuffer(long j, btGimPairArray btgimpairarray, long j2, int i, int i2);

    public static final native long btGimPairArray_operatorAssignment(long j, btGimPairArray btgimpairarray, long j2, btGimPairArray btgimpairarray2);

    public static final native long btGimPairArray_operatorSubscript(long j, btGimPairArray btgimpairarray, int i);

    public static final native long btGimPairArray_operatorSubscriptConst(long j, btGimPairArray btgimpairarray, int i);

    public static final native void btGimPairArray_pop_back(long j, btGimPairArray btgimpairarray);

    public static final native void btGimPairArray_push_back(long j, btGimPairArray btgimpairarray, long j2, GIM_PAIR gim_pair);

    public static final native void btGimPairArray_removeAtIndex(long j, btGimPairArray btgimpairarray, int i);

    public static final native void btGimPairArray_reserve(long j, btGimPairArray btgimpairarray, int i);

    public static final native void btGimPairArray_resizeNoInitialize(long j, btGimPairArray btgimpairarray, int i);

    public static final native void btGimPairArray_resize__SWIG_0(long j, btGimPairArray btgimpairarray, int i, long j2, GIM_PAIR gim_pair);

    public static final native void btGimPairArray_resize__SWIG_1(long j, btGimPairArray btgimpairarray, int i);

    public static final native int btGimPairArray_size(long j, btGimPairArray btgimpairarray);

    public static final native void btGimPairArray_swap(long j, btGimPairArray btgimpairarray, int i, int i2);

    public static final native long btGimQuantizedBvhNodeArray_at(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i);

    public static final native long btGimQuantizedBvhNodeArray_atConst(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i);

    public static final native int btGimQuantizedBvhNodeArray_capacity(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray);

    public static final native void btGimQuantizedBvhNodeArray_clear(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray);

    public static final native void btGimQuantizedBvhNodeArray_copyFromArray(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, long j2, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray2);

    public static final native long btGimQuantizedBvhNodeArray_expandNonInitializing(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray);

    public static final native long btGimQuantizedBvhNodeArray_expand__SWIG_0(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, long j2, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node);

    public static final native long btGimQuantizedBvhNodeArray_expand__SWIG_1(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray);

    public static final native void btGimQuantizedBvhNodeArray_initializeFromBuffer(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, long j2, int i, int i2);

    public static final native long btGimQuantizedBvhNodeArray_operatorAssignment(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, long j2, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray2);

    public static final native long btGimQuantizedBvhNodeArray_operatorSubscript(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i);

    public static final native long btGimQuantizedBvhNodeArray_operatorSubscriptConst(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i);

    public static final native void btGimQuantizedBvhNodeArray_pop_back(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray);

    public static final native void btGimQuantizedBvhNodeArray_push_back(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, long j2, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node);

    public static final native void btGimQuantizedBvhNodeArray_removeAtIndex(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i);

    public static final native void btGimQuantizedBvhNodeArray_reserve(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i);

    public static final native void btGimQuantizedBvhNodeArray_resizeNoInitialize(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i);

    public static final native void btGimQuantizedBvhNodeArray_resize__SWIG_0(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i, long j2, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node);

    public static final native void btGimQuantizedBvhNodeArray_resize__SWIG_1(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i);

    public static final native int btGimQuantizedBvhNodeArray_size(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray);

    public static final native void btGimQuantizedBvhNodeArray_swap(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray, int i, int i2);

    public static final native long btGjkCollisionDescription_firstDir_get(long j, btGjkCollisionDescription btgjkcollisiondescription);

    public static final native void btGjkCollisionDescription_firstDir_set(long j, btGjkCollisionDescription btgjkcollisiondescription, long j2, btVector3 btvector3);

    public static final native float btGjkCollisionDescription_gjkRelError2_get(long j, btGjkCollisionDescription btgjkcollisiondescription);

    public static final native void btGjkCollisionDescription_gjkRelError2_set(long j, btGjkCollisionDescription btgjkcollisiondescription, float f);

    public static final native int btGjkCollisionDescription_maxGjkIterations_get(long j, btGjkCollisionDescription btgjkcollisiondescription);

    public static final native void btGjkCollisionDescription_maxGjkIterations_set(long j, btGjkCollisionDescription btgjkcollisiondescription, int i);

    public static final native float btGjkCollisionDescription_maximumDistanceSquared_get(long j, btGjkCollisionDescription btgjkcollisiondescription);

    public static final native void btGjkCollisionDescription_maximumDistanceSquared_set(long j, btGjkCollisionDescription btgjkcollisiondescription, float f);

    public static final native long btGjkConvexCast_SWIGUpcast(long j);

    public static final native long btGjkEpaPenetrationDepthSolver_SWIGUpcast(long j);

    public static final native boolean btGjkEpaSolver2_Distance(long j, btConvexShape btconvexshape, Matrix4 matrix4, long j2, btConvexShape btconvexshape2, Matrix4 matrix5, Vector3 vector3, long j3, btGjkEpaSolver2.sResults sresults);

    public static final native boolean btGjkEpaSolver2_Penetration__SWIG_0(long j, btConvexShape btconvexshape, Matrix4 matrix4, long j2, btConvexShape btconvexshape2, Matrix4 matrix5, Vector3 vector3, long j3, btGjkEpaSolver2.sResults sresults, boolean z);

    public static final native boolean btGjkEpaSolver2_Penetration__SWIG_1(long j, btConvexShape btconvexshape, Matrix4 matrix4, long j2, btConvexShape btconvexshape2, Matrix4 matrix5, Vector3 vector3, long j3, btGjkEpaSolver2.sResults sresults);

    public static final native float btGjkEpaSolver2_SignedDistance__SWIG_0(Vector3 vector3, float f, long j, btConvexShape btconvexshape, Matrix4 matrix4, long j2, btGjkEpaSolver2.sResults sresults);

    public static final native boolean btGjkEpaSolver2_SignedDistance__SWIG_1(long j, btConvexShape btconvexshape, Matrix4 matrix4, long j2, btConvexShape btconvexshape2, Matrix4 matrix5, Vector3 vector3, long j3, btGjkEpaSolver2.sResults sresults);

    public static final native int btGjkEpaSolver2_StackSizeRequirement();

    public static final native float btGjkEpaSolver2_sResults_distance_get(long j, btGjkEpaSolver2.sResults sresults);

    public static final native void btGjkEpaSolver2_sResults_distance_set(long j, btGjkEpaSolver2.sResults sresults, float f);

    public static final native long btGjkEpaSolver2_sResults_normal_get(long j, btGjkEpaSolver2.sResults sresults);

    public static final native void btGjkEpaSolver2_sResults_normal_set(long j, btGjkEpaSolver2.sResults sresults, long j2, btVector3 btvector3);

    public static final native int btGjkEpaSolver2_sResults_status_get(long j, btGjkEpaSolver2.sResults sresults);

    public static final native void btGjkEpaSolver2_sResults_status_set(long j, btGjkEpaSolver2.sResults sresults, int i);

    public static final native long btGjkEpaSolver2_sResults_witnesses_get(long j, btGjkEpaSolver2.sResults sresults);

    public static final native void btGjkEpaSolver2_sResults_witnesses_set(long j, btGjkEpaSolver2.sResults sresults, long j2, btVector3 btvector3);

    public static final native float btGjkEpaSolver3_sResults_distance_get(long j, btGjkEpaSolver3.sResults sresults);

    public static final native void btGjkEpaSolver3_sResults_distance_set(long j, btGjkEpaSolver3.sResults sresults, float f);

    public static final native long btGjkEpaSolver3_sResults_normal_get(long j, btGjkEpaSolver3.sResults sresults);

    public static final native void btGjkEpaSolver3_sResults_normal_set(long j, btGjkEpaSolver3.sResults sresults, long j2, btVector3 btvector3);

    public static final native int btGjkEpaSolver3_sResults_status_get(long j, btGjkEpaSolver3.sResults sresults);

    public static final native void btGjkEpaSolver3_sResults_status_set(long j, btGjkEpaSolver3.sResults sresults, int i);

    public static final native long btGjkEpaSolver3_sResults_witnesses_get(long j, btGjkEpaSolver3.sResults sresults);

    public static final native void btGjkEpaSolver3_sResults_witnesses_set(long j, btGjkEpaSolver3.sResults sresults, long j2, btVector3 btvector3);

    public static final native long btGjkPairDetector_SWIGUpcast(long j);

    public static final native int btGjkPairDetector_catchDegeneracies_get(long j, btGjkPairDetector btgjkpairdetector);

    public static final native void btGjkPairDetector_catchDegeneracies_set(long j, btGjkPairDetector btgjkpairdetector, int i);

    public static final native int btGjkPairDetector_curIter_get(long j, btGjkPairDetector btgjkpairdetector);

    public static final native void btGjkPairDetector_curIter_set(long j, btGjkPairDetector btgjkpairdetector, int i);

    public static final native int btGjkPairDetector_degenerateSimplex_get(long j, btGjkPairDetector btgjkpairdetector);

    public static final native void btGjkPairDetector_degenerateSimplex_set(long j, btGjkPairDetector btgjkpairdetector, int i);

    public static final native int btGjkPairDetector_fixContactNormalDirection_get(long j, btGjkPairDetector btgjkpairdetector);

    public static final native void btGjkPairDetector_fixContactNormalDirection_set(long j, btGjkPairDetector btgjkpairdetector, int i);

    public static final native Vector3 btGjkPairDetector_getCachedSeparatingAxis(long j, btGjkPairDetector btgjkpairdetector);

    public static final native float btGjkPairDetector_getCachedSeparatingDistance(long j, btGjkPairDetector btgjkpairdetector);

    public static final native void btGjkPairDetector_getClosestPointsNonVirtual(long j, btGjkPairDetector btgjkpairdetector, long j2, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j3, btDiscreteCollisionDetectorInterface.Result result, long j4, btIDebugDraw btidebugdraw);

    public static final native void btGjkPairDetector_getClosestPoints__SWIG_0(long j, btGjkPairDetector btgjkpairdetector, long j2, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j3, btDiscreteCollisionDetectorInterface.Result result, long j4, btIDebugDraw btidebugdraw, boolean z);

    public static final native void btGjkPairDetector_getClosestPoints__SWIG_1(long j, btGjkPairDetector btgjkpairdetector, long j2, btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, long j3, btDiscreteCollisionDetectorInterface.Result result, long j4, btIDebugDraw btidebugdraw);

    public static final native int btGjkPairDetector_lastUsedMethod_get(long j, btGjkPairDetector btgjkpairdetector);

    public static final native void btGjkPairDetector_lastUsedMethod_set(long j, btGjkPairDetector btgjkpairdetector, int i);

    public static final native void btGjkPairDetector_setCachedSeperatingAxis(long j, btGjkPairDetector btgjkpairdetector, Vector3 vector3);

    public static final native void btGjkPairDetector_setIgnoreMargin(long j, btGjkPairDetector btgjkpairdetector, boolean z);

    public static final native void btGjkPairDetector_setMinkowskiA(long j, btGjkPairDetector btgjkpairdetector, long j2, btConvexShape btconvexshape);

    public static final native void btGjkPairDetector_setMinkowskiB(long j, btGjkPairDetector btgjkpairdetector, long j2, btConvexShape btconvexshape);

    public static final native void btGjkPairDetector_setPenetrationDepthSolver(long j, btGjkPairDetector btgjkpairdetector, long j2, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver);

    public static final native void btHashMapInternalShortBtHashIntBtTriangleInfo_clear(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo);

    public static final native long btHashMapInternalShortBtHashIntBtTriangleInfo_find(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, long j2, btHashInt bthashint);

    public static final native long btHashMapInternalShortBtHashIntBtTriangleInfo_findConst(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, long j2, btHashInt bthashint);

    public static final native int btHashMapInternalShortBtHashIntBtTriangleInfo_findIndex(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, long j2, btHashInt bthashint);

    public static final native long btHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndex(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, int i);

    public static final native long btHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndexConst(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, int i);

    public static final native long btHashMapInternalShortBtHashIntBtTriangleInfo_getKeyAtIndex(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, int i);

    public static final native long btHashMapInternalShortBtHashIntBtTriangleInfo_getKeyAtIndexConst(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, int i);

    public static final native void btHashMapInternalShortBtHashIntBtTriangleInfo_insert(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, long j2, btHashInt bthashint, long j3, btTriangleInfo bttriangleinfo);

    public static final native long btHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscript(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, long j2, btHashInt bthashint);

    public static final native long btHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscriptConst(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, long j2, btHashInt bthashint);

    public static final native void btHashMapInternalShortBtHashIntBtTriangleInfo_remove(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo, long j2, btHashInt bthashint);

    public static final native int btHashMapInternalShortBtHashIntBtTriangleInfo_size(long j, btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo);

    public static final native int btHashedOverlappingPairCache_GetCount(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache);

    public static final native long btHashedOverlappingPairCache_SWIGUpcast(long j);

    public static final native long btHashedOverlappingPairCache_getOverlapFilterCallback(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache);

    public static final native long btHashedOverlappingPairCache_getOverlappingPairArrayConst(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache);

    public static final native boolean btHashedOverlappingPairCache_needsBroadphaseCollision(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native void btHashedOverlappingPairCache_operatorDeleteArray__SWIG_0(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache, long j2);

    public static final native void btHashedOverlappingPairCache_operatorDeleteArray__SWIG_1(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache, long j2, long j3);

    public static final native void btHashedOverlappingPairCache_operatorDelete__SWIG_0(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache, long j2);

    public static final native void btHashedOverlappingPairCache_operatorDelete__SWIG_1(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache, long j2, long j3);

    public static final native long btHashedOverlappingPairCache_operatorNewArray__SWIG_0(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache, long j2);

    public static final native long btHashedOverlappingPairCache_operatorNewArray__SWIG_1(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache, long j2, long j3);

    public static final native long btHashedOverlappingPairCache_operatorNew__SWIG_0(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache, long j2);

    public static final native long btHashedOverlappingPairCache_operatorNew__SWIG_1(long j, btHashedOverlappingPairCache bthashedoverlappingpaircache, long j2, long j3);

    public static final native int btHashedSimplePairCache_GetCount(long j, btHashedSimplePairCache bthashedsimplepaircache);

    public static final native long btHashedSimplePairCache_addOverlappingPair(long j, btHashedSimplePairCache bthashedsimplepaircache, int i, int i2);

    public static final native long btHashedSimplePairCache_findPair(long j, btHashedSimplePairCache bthashedsimplepaircache, int i, int i2);

    public static final native int btHashedSimplePairCache_getNumOverlappingPairs(long j, btHashedSimplePairCache bthashedsimplepaircache);

    public static final native long btHashedSimplePairCache_getOverlappingPairArray(long j, btHashedSimplePairCache bthashedsimplepaircache);

    public static final native long btHashedSimplePairCache_getOverlappingPairArrayConst(long j, btHashedSimplePairCache bthashedsimplepaircache);

    public static final native long btHashedSimplePairCache_getOverlappingPairArrayPtr(long j, btHashedSimplePairCache bthashedsimplepaircache);

    public static final native long btHashedSimplePairCache_getOverlappingPairArrayPtrConst(long j, btHashedSimplePairCache bthashedsimplepaircache);

    public static final native void btHashedSimplePairCache_removeAllPairs(long j, btHashedSimplePairCache bthashedsimplepaircache);

    public static final native long btHashedSimplePairCache_removeOverlappingPair(long j, btHashedSimplePairCache bthashedsimplepaircache, int i, int i2);

    public static final native long btHeightfieldTerrainShape_SWIGUpcast(long j);

    public static final native void btHeightfieldTerrainShape_operatorDeleteArray__SWIG_0(long j, btHeightfieldTerrainShape btheightfieldterrainshape, long j2);

    public static final native void btHeightfieldTerrainShape_operatorDeleteArray__SWIG_1(long j, btHeightfieldTerrainShape btheightfieldterrainshape, long j2, long j3);

    public static final native void btHeightfieldTerrainShape_operatorDelete__SWIG_0(long j, btHeightfieldTerrainShape btheightfieldterrainshape, long j2);

    public static final native void btHeightfieldTerrainShape_operatorDelete__SWIG_1(long j, btHeightfieldTerrainShape btheightfieldterrainshape, long j2, long j3);

    public static final native long btHeightfieldTerrainShape_operatorNewArray__SWIG_0(long j, btHeightfieldTerrainShape btheightfieldterrainshape, long j2);

    public static final native long btHeightfieldTerrainShape_operatorNewArray__SWIG_1(long j, btHeightfieldTerrainShape btheightfieldterrainshape, long j2, long j3);

    public static final native long btHeightfieldTerrainShape_operatorNew__SWIG_0(long j, btHeightfieldTerrainShape btheightfieldterrainshape, long j2);

    public static final native long btHeightfieldTerrainShape_operatorNew__SWIG_1(long j, btHeightfieldTerrainShape btheightfieldterrainshape, long j2, long j3);

    public static final native void btHeightfieldTerrainShape_setUseDiamondSubdivision__SWIG_0(long j, btHeightfieldTerrainShape btheightfieldterrainshape, boolean z);

    public static final native void btHeightfieldTerrainShape_setUseDiamondSubdivision__SWIG_1(long j, btHeightfieldTerrainShape btheightfieldterrainshape);

    public static final native void btHeightfieldTerrainShape_setUseZigzagSubdivision__SWIG_0(long j, btHeightfieldTerrainShape btheightfieldterrainshape, boolean z);

    public static final native void btHeightfieldTerrainShape_setUseZigzagSubdivision__SWIG_1(long j, btHeightfieldTerrainShape btheightfieldterrainshape);

    public static final native int btIndexedMesh_indexType_get(long j, btIndexedMesh btindexedmesh);

    public static final native void btIndexedMesh_indexType_set(long j, btIndexedMesh btindexedmesh, int i);

    public static final native int btIndexedMesh_numTriangles_get(long j, btIndexedMesh btindexedmesh);

    public static final native void btIndexedMesh_numTriangles_set(long j, btIndexedMesh btindexedmesh, int i);

    public static final native int btIndexedMesh_numVertices_get(long j, btIndexedMesh btindexedmesh);

    public static final native void btIndexedMesh_numVertices_set(long j, btIndexedMesh btindexedmesh, int i);

    public static final native void btIndexedMesh_operatorDeleteArray__SWIG_0(long j, btIndexedMesh btindexedmesh, long j2);

    public static final native void btIndexedMesh_operatorDeleteArray__SWIG_1(long j, btIndexedMesh btindexedmesh, long j2, long j3);

    public static final native void btIndexedMesh_operatorDelete__SWIG_0(long j, btIndexedMesh btindexedmesh, long j2);

    public static final native void btIndexedMesh_operatorDelete__SWIG_1(long j, btIndexedMesh btindexedmesh, long j2, long j3);

    public static final native long btIndexedMesh_operatorNewArray__SWIG_0(long j, btIndexedMesh btindexedmesh, long j2);

    public static final native long btIndexedMesh_operatorNewArray__SWIG_1(long j, btIndexedMesh btindexedmesh, long j2, long j3);

    public static final native long btIndexedMesh_operatorNew__SWIG_0(long j, btIndexedMesh btindexedmesh, long j2);

    public static final native long btIndexedMesh_operatorNew__SWIG_1(long j, btIndexedMesh btindexedmesh, long j2, long j3);

    public static final native void btIndexedMesh_setIndices(long j, btIndexedMesh btindexedmesh, ShortBuffer shortBuffer, int i, int i2);

    public static final native void btIndexedMesh_setTriangleIndexBase(long j, btIndexedMesh btindexedmesh, ShortBuffer shortBuffer);

    public static final native void btIndexedMesh_setVertexBase(long j, btIndexedMesh btindexedmesh, FloatBuffer floatBuffer);

    public static final native void btIndexedMesh_setVertices(long j, btIndexedMesh btindexedmesh, FloatBuffer floatBuffer, int i, int i2, int i3);

    public static final native ByteBuffer btIndexedMesh_triangleIndexBase_get(long j, btIndexedMesh btindexedmesh);

    public static final native void btIndexedMesh_triangleIndexBase_set(long j, btIndexedMesh btindexedmesh, ByteBuffer byteBuffer);

    public static final native int btIndexedMesh_triangleIndexStride_get(long j, btIndexedMesh btindexedmesh);

    public static final native void btIndexedMesh_triangleIndexStride_set(long j, btIndexedMesh btindexedmesh, int i);

    public static final native ByteBuffer btIndexedMesh_vertexBase_get(long j, btIndexedMesh btindexedmesh);

    public static final native void btIndexedMesh_vertexBase_set(long j, btIndexedMesh btindexedmesh, ByteBuffer byteBuffer);

    public static final native int btIndexedMesh_vertexStride_get(long j, btIndexedMesh btindexedmesh);

    public static final native void btIndexedMesh_vertexStride_set(long j, btIndexedMesh btindexedmesh, int i);

    public static final native int btIndexedMesh_vertexType_get(long j, btIndexedMesh btindexedmesh);

    public static final native void btIndexedMesh_vertexType_set(long j, btIndexedMesh btindexedmesh, int i);

    public static final native int btIntIndexData_value_get(long j, btIntIndexData btintindexdata);

    public static final native void btIntIndexData_value_set(long j, btIntIndexData btintindexdata, int i);

    public static final native void btInternalTriangleIndexCallback_change_ownership(btInternalTriangleIndexCallback btinternaltriangleindexcallback, long j, boolean z);

    public static final native void btInternalTriangleIndexCallback_director_connect(btInternalTriangleIndexCallback btinternaltriangleindexcallback, long j, boolean z, boolean z2);

    public static final native void btInternalTriangleIndexCallback_internalProcessTriangleIndex(long j, btInternalTriangleIndexCallback btinternaltriangleindexcallback, long j2, btVector3 btvector3, int i, int i2);

    public static final native float btManifoldPoint_appliedImpulseLateral1_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_appliedImpulseLateral1_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_appliedImpulseLateral2_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_appliedImpulseLateral2_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_appliedImpulse_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_appliedImpulse_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_combinedContactDamping1_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_combinedContactDamping1_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_combinedContactStiffness1_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_combinedContactStiffness1_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_combinedFriction_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_combinedFriction_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_combinedRestitution_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_combinedRestitution_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_combinedRollingFriction_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_combinedRollingFriction_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_combinedSpinningFriction_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_combinedSpinningFriction_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_contactCFM_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_contactCFM_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_contactERP_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_contactERP_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_contactMotion1_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_contactMotion1_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_contactMotion2_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_contactMotion2_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native int btManifoldPoint_contactPointFlags_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_contactPointFlags_set(long j, btManifoldPoint btmanifoldpoint, int i);

    public static final native float btManifoldPoint_distance1_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_distance1_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_frictionCFM_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_frictionCFM_set(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native float btManifoldPoint_getDistance(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_getLateralFrictionDir1(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_getLateralFrictionDir2(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_getLocalPointA(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_getLocalPointB(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_getNormalWorldOnB(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_getPositionWorldOnA(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_getPositionWorldOnB(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native int btManifoldPoint_getUserValue(long j, btManifoldPoint btmanifoldpoint);

    public static final native int btManifoldPoint_index0_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_index0_set(long j, btManifoldPoint btmanifoldpoint, int i);

    public static final native int btManifoldPoint_index1_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_index1_set(long j, btManifoldPoint btmanifoldpoint, int i);

    public static final native int btManifoldPoint_lifeTime_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_lifeTime_set(long j, btManifoldPoint btmanifoldpoint, int i);

    public static final native int btManifoldPoint_partId0_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_partId0_set(long j, btManifoldPoint btmanifoldpoint, int i);

    public static final native int btManifoldPoint_partId1_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_partId1_set(long j, btManifoldPoint btmanifoldpoint, int i);

    public static final native void btManifoldPoint_setDistance(long j, btManifoldPoint btmanifoldpoint, float f);

    public static final native void btManifoldPoint_setLateralFrictionDir1(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_setLateralFrictionDir2(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_setLocalPointA(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_setLocalPointB(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_setNormalWorldOnB(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_setPositionWorldOnA(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_setPositionWorldOnB(long j, btManifoldPoint btmanifoldpoint, Vector3 vector3);

    public static final native void btManifoldPoint_setUserValue(long j, btManifoldPoint btmanifoldpoint, int i);

    public static final native long btManifoldPoint_userPersistentData_get(long j, btManifoldPoint btmanifoldpoint);

    public static final native void btManifoldPoint_userPersistentData_set(long j, btManifoldPoint btmanifoldpoint, long j2);

    public static final native long btManifoldResult_SWIGUpcast(long j);

    public static final native float btManifoldResult_calculateCombinedContactDamping(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2);

    public static final native float btManifoldResult_calculateCombinedContactStiffness(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2);

    public static final native float btManifoldResult_calculateCombinedFriction(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2);

    public static final native float btManifoldResult_calculateCombinedRestitution(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2);

    public static final native float btManifoldResult_calculateCombinedRollingFriction(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2);

    public static final native float btManifoldResult_calculateCombinedSpinningFriction(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2);

    public static final native float btManifoldResult_closestPointDistanceThreshold_get(long j, btManifoldResult btmanifoldresult);

    public static final native void btManifoldResult_closestPointDistanceThreshold_set(long j, btManifoldResult btmanifoldresult, float f);

    public static final native long btManifoldResult_getBody0Internal(long j, btManifoldResult btmanifoldresult);

    public static final native long btManifoldResult_getBody0Wrap(long j, btManifoldResult btmanifoldresult);

    public static final native long btManifoldResult_getBody1Internal(long j, btManifoldResult btmanifoldresult);

    public static final native long btManifoldResult_getBody1Wrap(long j, btManifoldResult btmanifoldresult);

    public static final native long btManifoldResult_getPersistentManifold(long j, btManifoldResult btmanifoldresult);

    public static final native long btManifoldResult_getPersistentManifoldConst(long j, btManifoldResult btmanifoldresult);

    public static final native void btManifoldResult_refreshContactPoints(long j, btManifoldResult btmanifoldresult);

    public static final native void btManifoldResult_setBody0Wrap(long j, btManifoldResult btmanifoldresult, long j2, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btManifoldResult_setBody1Wrap(long j, btManifoldResult btmanifoldresult, long j2, btCollisionObjectWrapper btcollisionobjectwrapper);

    public static final native void btManifoldResult_setPersistentManifold(long j, btManifoldResult btmanifoldresult, long j2, btPersistentManifold btpersistentmanifold);

    public static final native ByteBuffer btMaterialProperties_materialBase_get(long j, btMaterialProperties btmaterialproperties);

    public static final native void btMaterialProperties_materialBase_set(long j, btMaterialProperties btmaterialproperties, ByteBuffer byteBuffer);

    public static final native int btMaterialProperties_materialStride_get(long j, btMaterialProperties btmaterialproperties);

    public static final native void btMaterialProperties_materialStride_set(long j, btMaterialProperties btmaterialproperties, int i);

    public static final native int btMaterialProperties_materialType_get(long j, btMaterialProperties btmaterialproperties);

    public static final native void btMaterialProperties_materialType_set(long j, btMaterialProperties btmaterialproperties, int i);

    public static final native int btMaterialProperties_numMaterials_get(long j, btMaterialProperties btmaterialproperties);

    public static final native void btMaterialProperties_numMaterials_set(long j, btMaterialProperties btmaterialproperties, int i);

    public static final native int btMaterialProperties_numTriangles_get(long j, btMaterialProperties btmaterialproperties);

    public static final native void btMaterialProperties_numTriangles_set(long j, btMaterialProperties btmaterialproperties, int i);

    public static final native int btMaterialProperties_triangleMaterialStride_get(long j, btMaterialProperties btmaterialproperties);

    public static final native void btMaterialProperties_triangleMaterialStride_set(long j, btMaterialProperties btmaterialproperties, int i);

    public static final native ByteBuffer btMaterialProperties_triangleMaterialsBase_get(long j, btMaterialProperties btmaterialproperties);

    public static final native void btMaterialProperties_triangleMaterialsBase_set(long j, btMaterialProperties btmaterialproperties, ByteBuffer byteBuffer);

    public static final native int btMaterialProperties_triangleType_get(long j, btMaterialProperties btmaterialproperties);

    public static final native void btMaterialProperties_triangleType_set(long j, btMaterialProperties btmaterialproperties, int i);

    public static final native float btMaterial_friction_get(long j, btMaterial btmaterial);

    public static final native void btMaterial_friction_set(long j, btMaterial btmaterial, float f);

    public static final native int[] btMaterial_pad_get(long j, btMaterial btmaterial);

    public static final native void btMaterial_pad_set(long j, btMaterial btmaterial, int[] iArr);

    public static final native float btMaterial_restitution_get(long j, btMaterial btmaterial);

    public static final native void btMaterial_restitution_set(long j, btMaterial btmaterial, float f);

    public static final native long btMeshPartData_3indices16_get(long j, btMeshPartData btmeshpartdata);

    public static final native void btMeshPartData_3indices16_set(long j, btMeshPartData btmeshpartdata, long j2, btShortIntIndexTripletData btshortintindextripletdata);

    public static final native long btMeshPartData_3indices8_get(long j, btMeshPartData btmeshpartdata);

    public static final native void btMeshPartData_3indices8_set(long j, btMeshPartData btmeshpartdata, long j2, btCharIndexTripletData btcharindextripletdata);

    public static final native long btMeshPartData_indices16_get(long j, btMeshPartData btmeshpartdata);

    public static final native void btMeshPartData_indices16_set(long j, btMeshPartData btmeshpartdata, long j2, btShortIntIndexData btshortintindexdata);

    public static final native long btMeshPartData_indices32_get(long j, btMeshPartData btmeshpartdata);

    public static final native void btMeshPartData_indices32_set(long j, btMeshPartData btmeshpartdata, long j2, btIntIndexData btintindexdata);

    public static final native int btMeshPartData_numTriangles_get(long j, btMeshPartData btmeshpartdata);

    public static final native void btMeshPartData_numTriangles_set(long j, btMeshPartData btmeshpartdata, int i);

    public static final native int btMeshPartData_numVertices_get(long j, btMeshPartData btmeshpartdata);

    public static final native void btMeshPartData_numVertices_set(long j, btMeshPartData btmeshpartdata, int i);

    public static final native long btMeshPartData_vertices3d_get(long j, btMeshPartData btmeshpartdata);

    public static final native void btMeshPartData_vertices3d_set(long j, btMeshPartData btmeshpartdata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btMeshPartData_vertices3f_get(long j, btMeshPartData btmeshpartdata);

    public static final native void btMeshPartData_vertices3f_set(long j, btMeshPartData btmeshpartdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btMinkowskiPenetrationDepthSolver_SWIGUpcast(long j);

    public static final native Matrix4 btMinkowskiSumShape_GetTransformB(long j, btMinkowskiSumShape btminkowskisumshape);

    public static final native long btMinkowskiSumShape_SWIGUpcast(long j);

    public static final native long btMinkowskiSumShape_getShapeA(long j, btMinkowskiSumShape btminkowskisumshape);

    public static final native long btMinkowskiSumShape_getShapeB(long j, btMinkowskiSumShape btminkowskisumshape);

    public static final native Matrix4 btMinkowskiSumShape_getTransformA(long j, btMinkowskiSumShape btminkowskisumshape);

    public static final native void btMinkowskiSumShape_operatorDeleteArray__SWIG_0(long j, btMinkowskiSumShape btminkowskisumshape, long j2);

    public static final native void btMinkowskiSumShape_operatorDeleteArray__SWIG_1(long j, btMinkowskiSumShape btminkowskisumshape, long j2, long j3);

    public static final native void btMinkowskiSumShape_operatorDelete__SWIG_0(long j, btMinkowskiSumShape btminkowskisumshape, long j2);

    public static final native void btMinkowskiSumShape_operatorDelete__SWIG_1(long j, btMinkowskiSumShape btminkowskisumshape, long j2, long j3);

    public static final native long btMinkowskiSumShape_operatorNewArray__SWIG_0(long j, btMinkowskiSumShape btminkowskisumshape, long j2);

    public static final native long btMinkowskiSumShape_operatorNewArray__SWIG_1(long j, btMinkowskiSumShape btminkowskisumshape, long j2, long j3);

    public static final native long btMinkowskiSumShape_operatorNew__SWIG_0(long j, btMinkowskiSumShape btminkowskisumshape, long j2);

    public static final native long btMinkowskiSumShape_operatorNew__SWIG_1(long j, btMinkowskiSumShape btminkowskisumshape, long j2, long j3);

    public static final native void btMinkowskiSumShape_setTransformA(long j, btMinkowskiSumShape btminkowskisumshape, Matrix4 matrix4);

    public static final native void btMinkowskiSumShape_setTransformB(long j, btMinkowskiSumShape btminkowskisumshape, Matrix4 matrix4);

    public static final native long btMprCollisionDescription_firstDir_get(long j, btMprCollisionDescription btmprcollisiondescription);

    public static final native void btMprCollisionDescription_firstDir_set(long j, btMprCollisionDescription btmprcollisiondescription, long j2, btVector3 btvector3);

    public static final native float btMprCollisionDescription_gjkRelError2_get(long j, btMprCollisionDescription btmprcollisiondescription);

    public static final native void btMprCollisionDescription_gjkRelError2_set(long j, btMprCollisionDescription btmprcollisiondescription, float f);

    public static final native int btMprCollisionDescription_maxGjkIterations_get(long j, btMprCollisionDescription btmprcollisiondescription);

    public static final native void btMprCollisionDescription_maxGjkIterations_set(long j, btMprCollisionDescription btmprcollisiondescription, int i);

    public static final native float btMprCollisionDescription_maximumDistanceSquared_get(long j, btMprCollisionDescription btmprcollisiondescription);

    public static final native void btMprCollisionDescription_maximumDistanceSquared_set(long j, btMprCollisionDescription btmprcollisiondescription, float f);

    public static final native float btMprDistanceInfo_distance_get(long j, btMprDistanceInfo btmprdistanceinfo);

    public static final native void btMprDistanceInfo_distance_set(long j, btMprDistanceInfo btmprdistanceinfo, float f);

    public static final native long btMprDistanceInfo_normalBtoA_get(long j, btMprDistanceInfo btmprdistanceinfo);

    public static final native void btMprDistanceInfo_normalBtoA_set(long j, btMprDistanceInfo btmprdistanceinfo, long j2, btVector3 btvector3);

    public static final native long btMprDistanceInfo_pointOnA_get(long j, btMprDistanceInfo btmprdistanceinfo);

    public static final native void btMprDistanceInfo_pointOnA_set(long j, btMprDistanceInfo btmprdistanceinfo, long j2, btVector3 btvector3);

    public static final native long btMprDistanceInfo_pointOnB_get(long j, btMprDistanceInfo btmprdistanceinfo);

    public static final native void btMprDistanceInfo_pointOnB_set(long j, btMprDistanceInfo btmprdistanceinfo, long j2, btVector3 btvector3);

    public static final native int btMprEq(float f, float f2);

    public static final native int btMprIsZero(float f);

    public static final native long btMprSimplexPoint(long j, _btMprSimplex_t _btmprsimplex_t, int i);

    public static final native long btMprSimplexPointW(long j, _btMprSimplex_t _btmprsimplex_t, int i);

    public static final native void btMprSimplexSet(long j, _btMprSimplex_t _btmprsimplex_t, long j2, long j3, _btMprSupport_t _btmprsupport_t);

    public static final native void btMprSimplexSetSize(long j, _btMprSimplex_t _btmprsimplex_t, int i);

    public static final native int btMprSimplexSize(long j, _btMprSimplex_t _btmprsimplex_t);

    public static final native void btMprSimplexSwap(long j, _btMprSimplex_t _btmprsimplex_t, long j2, long j3);

    public static final native void btMprSupportCopy(long j, _btMprSupport_t _btmprsupport_t, long j2, _btMprSupport_t _btmprsupport_t2);

    public static final native void btMprVec3Add(long j, btVector3 btvector3, long j2, btVector3 btvector4);

    public static final native void btMprVec3Copy(long j, btVector3 btvector3, long j2, btVector3 btvector4);

    public static final native void btMprVec3Cross(long j, btVector3 btvector3, long j2, btVector3 btvector4, long j3, btVector3 btvector5);

    public static final native float btMprVec3Dist2(long j, btVector3 btvector3, long j2, btVector3 btvector4);

    public static final native float btMprVec3Dot(long j, btVector3 btvector3, long j2, btVector3 btvector4);

    public static final native int btMprVec3Eq(long j, btVector3 btvector3, long j2, btVector3 btvector4);

    public static final native float btMprVec3Len2(long j, btVector3 btvector3);

    public static final native void btMprVec3Normalize(long j, btVector3 btvector3);

    public static final native float btMprVec3PointTriDist2(long j, btVector3 btvector3, long j2, btVector3 btvector4, long j3, btVector3 btvector5, long j4, btVector3 btvector6, long j5, btVector3 btvector7);

    public static final native void btMprVec3Scale(long j, btVector3 btvector3, float f);

    public static final native void btMprVec3Set(long j, btVector3 btvector3, float f, float f2, float f3);

    public static final native void btMprVec3Sub2(long j, btVector3 btvector3, long j2, btVector3 btvector4, long j3, btVector3 btvector5);

    public static final native long btMultiSphereShapeData_convexInternalShapeData_get(long j, btMultiSphereShapeData btmultisphereshapedata);

    public static final native void btMultiSphereShapeData_convexInternalShapeData_set(long j, btMultiSphereShapeData btmultisphereshapedata, long j2, btConvexInternalShapeData btconvexinternalshapedata);

    public static final native long btMultiSphereShapeData_localPositionArrayPtr_get(long j, btMultiSphereShapeData btmultisphereshapedata);

    public static final native void btMultiSphereShapeData_localPositionArrayPtr_set(long j, btMultiSphereShapeData btmultisphereshapedata, long j2, btPositionAndRadius btpositionandradius);

    public static final native int btMultiSphereShapeData_localPositionArraySize_get(long j, btMultiSphereShapeData btmultisphereshapedata);

    public static final native void btMultiSphereShapeData_localPositionArraySize_set(long j, btMultiSphereShapeData btmultisphereshapedata, int i);

    public static final native String btMultiSphereShapeData_padding_get(long j, btMultiSphereShapeData btmultisphereshapedata);

    public static final native void btMultiSphereShapeData_padding_set(long j, btMultiSphereShapeData btmultisphereshapedata, String str);

    public static final native long btMultiSphereShape_SWIGUpcast(long j);

    public static final native int btMultiSphereShape_getSphereCount(long j, btMultiSphereShape btmultisphereshape);

    public static final native Vector3 btMultiSphereShape_getSpherePosition(long j, btMultiSphereShape btmultisphereshape, int i);

    public static final native float btMultiSphereShape_getSphereRadius(long j, btMultiSphereShape btmultisphereshape, int i);

    public static final native void btMultiSphereShape_operatorDeleteArray__SWIG_0(long j, btMultiSphereShape btmultisphereshape, long j2);

    public static final native void btMultiSphereShape_operatorDeleteArray__SWIG_1(long j, btMultiSphereShape btmultisphereshape, long j2, long j3);

    public static final native void btMultiSphereShape_operatorDelete__SWIG_0(long j, btMultiSphereShape btmultisphereshape, long j2);

    public static final native void btMultiSphereShape_operatorDelete__SWIG_1(long j, btMultiSphereShape btmultisphereshape, long j2, long j3);

    public static final native long btMultiSphereShape_operatorNewArray__SWIG_0(long j, btMultiSphereShape btmultisphereshape, long j2);

    public static final native long btMultiSphereShape_operatorNewArray__SWIG_1(long j, btMultiSphereShape btmultisphereshape, long j2, long j3);

    public static final native long btMultiSphereShape_operatorNew__SWIG_0(long j, btMultiSphereShape btmultisphereshape, long j2);

    public static final native long btMultiSphereShape_operatorNew__SWIG_1(long j, btMultiSphereShape btmultisphereshape, long j2, long j3);

    public static final native long btMultimaterialTriangleMeshShape_SWIGUpcast(long j);

    public static final native long btMultimaterialTriangleMeshShape_getMaterialProperties(long j, btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape, int i, int i2);

    public static final native void btMultimaterialTriangleMeshShape_operatorDeleteArray__SWIG_0(long j, btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape, long j2);

    public static final native void btMultimaterialTriangleMeshShape_operatorDeleteArray__SWIG_1(long j, btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape, long j2, long j3);

    public static final native void btMultimaterialTriangleMeshShape_operatorDelete__SWIG_0(long j, btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape, long j2);

    public static final native void btMultimaterialTriangleMeshShape_operatorDelete__SWIG_1(long j, btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape, long j2, long j3);

    public static final native long btMultimaterialTriangleMeshShape_operatorNewArray__SWIG_0(long j, btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape, long j2);

    public static final native long btMultimaterialTriangleMeshShape_operatorNewArray__SWIG_1(long j, btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape, long j2, long j3);

    public static final native long btMultimaterialTriangleMeshShape_operatorNew__SWIG_0(long j, btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape, long j2);

    public static final native long btMultimaterialTriangleMeshShape_operatorNew__SWIG_1(long j, btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape, long j2, long j3);

    public static final native void btNodeOverlapCallback_change_ownership(btNodeOverlapCallback btnodeoverlapcallback, long j, boolean z);

    public static final native void btNodeOverlapCallback_director_connect(btNodeOverlapCallback btnodeoverlapcallback, long j, boolean z, boolean z2);

    public static final native void btNodeOverlapCallback_processNode(long j, btNodeOverlapCallback btnodeoverlapcallback, int i, int i2);

    public static final native long btNullPairCache_SWIGUpcast(long j);

    public static final native long btOptimizedBvhNodeDoubleData_aabbMaxOrg_get(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata);

    public static final native void btOptimizedBvhNodeDoubleData_aabbMaxOrg_set(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btOptimizedBvhNodeDoubleData_aabbMinOrg_get(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata);

    public static final native void btOptimizedBvhNodeDoubleData_aabbMinOrg_set(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native int btOptimizedBvhNodeDoubleData_escapeIndex_get(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata);

    public static final native void btOptimizedBvhNodeDoubleData_escapeIndex_set(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata, int i);

    public static final native String btOptimizedBvhNodeDoubleData_pad_get(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata);

    public static final native void btOptimizedBvhNodeDoubleData_pad_set(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata, String str);

    public static final native int btOptimizedBvhNodeDoubleData_subPart_get(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata);

    public static final native void btOptimizedBvhNodeDoubleData_subPart_set(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata, int i);

    public static final native int btOptimizedBvhNodeDoubleData_triangleIndex_get(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata);

    public static final native void btOptimizedBvhNodeDoubleData_triangleIndex_set(long j, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata, int i);

    public static final native long btOptimizedBvhNodeFloatData_aabbMaxOrg_get(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata);

    public static final native void btOptimizedBvhNodeFloatData_aabbMaxOrg_set(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btOptimizedBvhNodeFloatData_aabbMinOrg_get(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata);

    public static final native void btOptimizedBvhNodeFloatData_aabbMinOrg_set(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native int btOptimizedBvhNodeFloatData_escapeIndex_get(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata);

    public static final native void btOptimizedBvhNodeFloatData_escapeIndex_set(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata, int i);

    public static final native String btOptimizedBvhNodeFloatData_pad_get(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata);

    public static final native void btOptimizedBvhNodeFloatData_pad_set(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata, String str);

    public static final native int btOptimizedBvhNodeFloatData_subPart_get(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata);

    public static final native void btOptimizedBvhNodeFloatData_subPart_set(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata, int i);

    public static final native int btOptimizedBvhNodeFloatData_triangleIndex_get(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata);

    public static final native void btOptimizedBvhNodeFloatData_triangleIndex_set(long j, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata, int i);

    public static final native long btOptimizedBvhNode_aabbMaxOrg_get(long j, btOptimizedBvhNode btoptimizedbvhnode);

    public static final native void btOptimizedBvhNode_aabbMaxOrg_set(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2, btVector3 btvector3);

    public static final native long btOptimizedBvhNode_aabbMinOrg_get(long j, btOptimizedBvhNode btoptimizedbvhnode);

    public static final native void btOptimizedBvhNode_aabbMinOrg_set(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2, btVector3 btvector3);

    public static final native int btOptimizedBvhNode_escapeIndex_get(long j, btOptimizedBvhNode btoptimizedbvhnode);

    public static final native void btOptimizedBvhNode_escapeIndex_set(long j, btOptimizedBvhNode btoptimizedbvhnode, int i);

    public static final native void btOptimizedBvhNode_operatorDeleteArray__SWIG_0(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2);

    public static final native void btOptimizedBvhNode_operatorDeleteArray__SWIG_1(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2, long j3);

    public static final native void btOptimizedBvhNode_operatorDelete__SWIG_0(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2);

    public static final native void btOptimizedBvhNode_operatorDelete__SWIG_1(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2, long j3);

    public static final native long btOptimizedBvhNode_operatorNewArray__SWIG_0(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2);

    public static final native long btOptimizedBvhNode_operatorNewArray__SWIG_1(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2, long j3);

    public static final native long btOptimizedBvhNode_operatorNew__SWIG_0(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2);

    public static final native long btOptimizedBvhNode_operatorNew__SWIG_1(long j, btOptimizedBvhNode btoptimizedbvhnode, long j2, long j3);

    public static final native String btOptimizedBvhNode_padding_get(long j, btOptimizedBvhNode btoptimizedbvhnode);

    public static final native void btOptimizedBvhNode_padding_set(long j, btOptimizedBvhNode btoptimizedbvhnode, String str);

    public static final native int btOptimizedBvhNode_subPart_get(long j, btOptimizedBvhNode btoptimizedbvhnode);

    public static final native void btOptimizedBvhNode_subPart_set(long j, btOptimizedBvhNode btoptimizedbvhnode, int i);

    public static final native int btOptimizedBvhNode_triangleIndex_get(long j, btOptimizedBvhNode btoptimizedbvhnode);

    public static final native void btOptimizedBvhNode_triangleIndex_set(long j, btOptimizedBvhNode btoptimizedbvhnode, int i);

    public static final native long btOptimizedBvh_SWIGUpcast(long j);

    public static final native void btOptimizedBvh_build(long j, btOptimizedBvh btoptimizedbvh, long j2, btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4);

    public static final native long btOptimizedBvh_deSerializeInPlace(long j, long j2, boolean z);

    public static final native void btOptimizedBvh_operatorDeleteArray__SWIG_0(long j, btOptimizedBvh btoptimizedbvh, long j2);

    public static final native void btOptimizedBvh_operatorDeleteArray__SWIG_1(long j, btOptimizedBvh btoptimizedbvh, long j2, long j3);

    public static final native void btOptimizedBvh_operatorDelete__SWIG_0(long j, btOptimizedBvh btoptimizedbvh, long j2);

    public static final native void btOptimizedBvh_operatorDelete__SWIG_1(long j, btOptimizedBvh btoptimizedbvh, long j2, long j3);

    public static final native long btOptimizedBvh_operatorNewArray__SWIG_0(long j, btOptimizedBvh btoptimizedbvh, long j2);

    public static final native long btOptimizedBvh_operatorNewArray__SWIG_1(long j, btOptimizedBvh btoptimizedbvh, long j2, long j3);

    public static final native long btOptimizedBvh_operatorNew__SWIG_0(long j, btOptimizedBvh btoptimizedbvh, long j2);

    public static final native long btOptimizedBvh_operatorNew__SWIG_1(long j, btOptimizedBvh btoptimizedbvh, long j2, long j3);

    public static final native void btOptimizedBvh_refit(long j, btOptimizedBvh btoptimizedbvh, long j2, btStridingMeshInterface btstridingmeshinterface, Vector3 vector3, Vector3 vector4);

    public static final native void btOptimizedBvh_refitPartial(long j, btOptimizedBvh btoptimizedbvh, long j2, btStridingMeshInterface btstridingmeshinterface, Vector3 vector3, Vector3 vector4);

    public static final native boolean btOptimizedBvh_serializeInPlace(long j, btOptimizedBvh btoptimizedbvh, long j2, long j3, boolean z);

    public static final native void btOptimizedBvh_updateBvhNodes(long j, btOptimizedBvh btoptimizedbvh, long j2, btStridingMeshInterface btstridingmeshinterface, int i, int i2, int i3);

    public static final native void btOverlapCallback_change_ownership(btOverlapCallback btoverlapcallback, long j, boolean z);

    public static final native void btOverlapCallback_director_connect(btOverlapCallback btoverlapcallback, long j, boolean z, boolean z2);

    public static final native boolean btOverlapCallback_processOverlap(long j, btOverlapCallback btoverlapcallback, btBroadphasePair btbroadphasepair);

    public static final native void btOverlapFilterCallback_change_ownership(btOverlapFilterCallback btoverlapfiltercallback, long j, boolean z);

    public static final native void btOverlapFilterCallback_director_connect(btOverlapFilterCallback btoverlapfiltercallback, long j, boolean z, boolean z2);

    public static final native boolean btOverlapFilterCallback_needBroadphaseCollision(long j, btOverlapFilterCallback btoverlapfiltercallback, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native long btOverlappingPairCache_SWIGUpcast(long j);

    public static final native void btOverlappingPairCache_cleanOverlappingPair(long j, btOverlappingPairCache btoverlappingpaircache, btBroadphasePair btbroadphasepair, long j2, btDispatcher btdispatcher);

    public static final native void btOverlappingPairCache_cleanProxyFromPairs(long j, btOverlappingPairCache btoverlappingpaircache, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btDispatcher btdispatcher);

    public static final native long btOverlappingPairCache_findPair(long j, btOverlappingPairCache btoverlappingpaircache, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native int btOverlappingPairCache_getNumOverlappingPairs(long j, btOverlappingPairCache btoverlappingpaircache);

    public static final native long btOverlappingPairCache_getOverlappingPairArray(long j, btOverlappingPairCache btoverlappingpaircache);

    public static final native long btOverlappingPairCache_getOverlappingPairArrayPtr(long j, btOverlappingPairCache btoverlappingpaircache);

    public static final native long btOverlappingPairCache_getOverlappingPairArrayPtrConst(long j, btOverlappingPairCache btoverlappingpaircache);

    public static final native boolean btOverlappingPairCache_hasDeferredRemoval(long j, btOverlappingPairCache btoverlappingpaircache);

    public static final native void btOverlappingPairCache_processAllOverlappingPairs(long j, btOverlappingPairCache btoverlappingpaircache, long j2, btOverlapCallback btoverlapcallback, long j3, btDispatcher btdispatcher);

    public static final native void btOverlappingPairCache_setInternalGhostPairCallback(long j, btOverlappingPairCache btoverlappingpaircache, long j2, btOverlappingPairCallback btoverlappingpaircallback);

    public static final native void btOverlappingPairCache_setOverlapFilterCallback(long j, btOverlappingPairCache btoverlappingpaircache, long j2, btOverlapFilterCallback btoverlapfiltercallback);

    public static final native void btOverlappingPairCache_sortOverlappingPairs(long j, btOverlappingPairCache btoverlappingpaircache, long j2, btDispatcher btdispatcher);

    public static final native long btOverlappingPairCallback_addOverlappingPair(long j, btOverlappingPairCallback btoverlappingpaircallback, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native long btOverlappingPairCallback_removeOverlappingPair(long j, btOverlappingPairCallback btoverlappingpaircallback, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2, long j4, btDispatcher btdispatcher);

    public static final native void btOverlappingPairCallback_removeOverlappingPairsContainingProxy(long j, btOverlappingPairCallback btoverlappingpaircallback, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btDispatcher btdispatcher);

    public static final native long btPairCachingGhostObject_SWIGUpcast(long j);

    public static final native void btPairCachingGhostObject_addOverlappingObjectInternal__SWIG_0(long j, btPairCachingGhostObject btpaircachingghostobject, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native void btPairCachingGhostObject_addOverlappingObjectInternal__SWIG_1(long j, btPairCachingGhostObject btpaircachingghostobject, long j2, btBroadphaseProxy btbroadphaseproxy);

    public static final native long btPairCachingGhostObject_getOverlappingPairCache(long j, btPairCachingGhostObject btpaircachingghostobject);

    public static final native void btPairCachingGhostObject_removeOverlappingObjectInternal__SWIG_0(long j, btPairCachingGhostObject btpaircachingghostobject, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btDispatcher btdispatcher, long j4, btBroadphaseProxy btbroadphaseproxy2);

    public static final native void btPairCachingGhostObject_removeOverlappingObjectInternal__SWIG_1(long j, btPairCachingGhostObject btpaircachingghostobject, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btDispatcher btdispatcher);

    public static final native long btPairSet_SWIGUpcast(long j);

    public static final native void btPairSet_push_pair(long j, btPairSet btpairset, int i, int i2);

    public static final native void btPairSet_push_pair_inv(long j, btPairSet btpairset, int i, int i2);

    public static final native long btPersistentManifoldArray_at(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i);

    public static final native long btPersistentManifoldArray_atConst(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i);

    public static final native int btPersistentManifoldArray_capacity(long j, btPersistentManifoldArray btpersistentmanifoldarray);

    public static final native void btPersistentManifoldArray_clear(long j, btPersistentManifoldArray btpersistentmanifoldarray);

    public static final native void btPersistentManifoldArray_copyFromArray(long j, btPersistentManifoldArray btpersistentmanifoldarray, long j2, btPersistentManifoldArray btpersistentmanifoldarray2);

    public static final native long btPersistentManifoldArray_expandNonInitializing(long j, btPersistentManifoldArray btpersistentmanifoldarray);

    public static final native long btPersistentManifoldArray_expand__SWIG_0(long j, btPersistentManifoldArray btpersistentmanifoldarray, long j2, btPersistentManifold btpersistentmanifold);

    public static final native long btPersistentManifoldArray_expand__SWIG_1(long j, btPersistentManifoldArray btpersistentmanifoldarray);

    public static final native int btPersistentManifoldArray_findBinarySearch(long j, btPersistentManifoldArray btpersistentmanifoldarray, long j2, btPersistentManifold btpersistentmanifold);

    public static final native int btPersistentManifoldArray_findLinearSearch(long j, btPersistentManifoldArray btpersistentmanifoldarray, long j2, btPersistentManifold btpersistentmanifold);

    public static final native int btPersistentManifoldArray_findLinearSearch2(long j, btPersistentManifoldArray btpersistentmanifoldarray, long j2, btPersistentManifold btpersistentmanifold);

    public static final native void btPersistentManifoldArray_initializeFromBuffer(long j, btPersistentManifoldArray btpersistentmanifoldarray, long j2, int i, int i2);

    public static final native boolean btPersistentManifoldArray_less_operatorFunctionCall(long j, btPersistentManifoldArray.less lessVar, long j2, btPersistentManifold btpersistentmanifold, long j3, btPersistentManifold btpersistentmanifold2);

    public static final native long btPersistentManifoldArray_operatorAssignment(long j, btPersistentManifoldArray btpersistentmanifoldarray, long j2, btPersistentManifoldArray btpersistentmanifoldarray2);

    public static final native long btPersistentManifoldArray_operatorSubscript(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i);

    public static final native long btPersistentManifoldArray_operatorSubscriptConst(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i);

    public static final native void btPersistentManifoldArray_pop_back(long j, btPersistentManifoldArray btpersistentmanifoldarray);

    public static final native void btPersistentManifoldArray_push_back(long j, btPersistentManifoldArray btpersistentmanifoldarray, long j2, btPersistentManifold btpersistentmanifold);

    public static final native void btPersistentManifoldArray_remove(long j, btPersistentManifoldArray btpersistentmanifoldarray, long j2, btPersistentManifold btpersistentmanifold);

    public static final native void btPersistentManifoldArray_removeAtIndex(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i);

    public static final native void btPersistentManifoldArray_reserve(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i);

    public static final native void btPersistentManifoldArray_resizeNoInitialize(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i);

    public static final native void btPersistentManifoldArray_resize__SWIG_0(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i, long j2, btPersistentManifold btpersistentmanifold);

    public static final native void btPersistentManifoldArray_resize__SWIG_1(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i);

    public static final native int btPersistentManifoldArray_size(long j, btPersistentManifoldArray btpersistentmanifoldarray);

    public static final native void btPersistentManifoldArray_swap(long j, btPersistentManifoldArray btpersistentmanifoldarray, int i, int i2);

    public static final native long btPersistentManifold_SWIGUpcast(long j);

    public static final native int btPersistentManifold_addManifoldPoint__SWIG_0(long j, btPersistentManifold btpersistentmanifold, long j2, btManifoldPoint btmanifoldpoint, boolean z);

    public static final native int btPersistentManifold_addManifoldPoint__SWIG_1(long j, btPersistentManifold btpersistentmanifold, long j2, btManifoldPoint btmanifoldpoint);

    public static final native void btPersistentManifold_clearManifold(long j, btPersistentManifold btpersistentmanifold);

    public static final native void btPersistentManifold_clearUserCache(long j, btPersistentManifold btpersistentmanifold, long j2, btManifoldPoint btmanifoldpoint);

    public static final native int btPersistentManifold_companionIdA_get(long j, btPersistentManifold btpersistentmanifold);

    public static final native void btPersistentManifold_companionIdA_set(long j, btPersistentManifold btpersistentmanifold, int i);

    public static final native int btPersistentManifold_companionIdB_get(long j, btPersistentManifold btpersistentmanifold);

    public static final native void btPersistentManifold_companionIdB_set(long j, btPersistentManifold btpersistentmanifold, int i);

    public static final native long btPersistentManifold_getBody0(long j, btPersistentManifold btpersistentmanifold);

    public static final native long btPersistentManifold_getBody1(long j, btPersistentManifold btpersistentmanifold);

    public static final native int btPersistentManifold_getCacheEntry(long j, btPersistentManifold btpersistentmanifold, long j2, btManifoldPoint btmanifoldpoint);

    public static final native float btPersistentManifold_getContactBreakingThreshold(long j, btPersistentManifold btpersistentmanifold);

    public static final native long btPersistentManifold_getContactPoint(long j, btPersistentManifold btpersistentmanifold, int i);

    public static final native long btPersistentManifold_getContactPointConst(long j, btPersistentManifold btpersistentmanifold, int i);

    public static final native float btPersistentManifold_getContactProcessingThreshold(long j, btPersistentManifold btpersistentmanifold);

    public static final native int btPersistentManifold_getNumContacts(long j, btPersistentManifold btpersistentmanifold);

    public static final native int btPersistentManifold_index1a_get(long j, btPersistentManifold btpersistentmanifold);

    public static final native void btPersistentManifold_index1a_set(long j, btPersistentManifold btpersistentmanifold, int i);

    public static final native void btPersistentManifold_operatorDeleteArray__SWIG_0(long j, btPersistentManifold btpersistentmanifold, long j2);

    public static final native void btPersistentManifold_operatorDeleteArray__SWIG_1(long j, btPersistentManifold btpersistentmanifold, long j2, long j3);

    public static final native void btPersistentManifold_operatorDelete__SWIG_0(long j, btPersistentManifold btpersistentmanifold, long j2);

    public static final native void btPersistentManifold_operatorDelete__SWIG_1(long j, btPersistentManifold btpersistentmanifold, long j2, long j3);

    public static final native long btPersistentManifold_operatorNewArray__SWIG_0(long j, btPersistentManifold btpersistentmanifold, long j2);

    public static final native long btPersistentManifold_operatorNewArray__SWIG_1(long j, btPersistentManifold btpersistentmanifold, long j2, long j3);

    public static final native long btPersistentManifold_operatorNew__SWIG_0(long j, btPersistentManifold btpersistentmanifold, long j2);

    public static final native long btPersistentManifold_operatorNew__SWIG_1(long j, btPersistentManifold btpersistentmanifold, long j2, long j3);

    public static final native void btPersistentManifold_refreshContactPoints(long j, btPersistentManifold btpersistentmanifold, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btPersistentManifold_removeContactPoint(long j, btPersistentManifold btpersistentmanifold, int i);

    public static final native void btPersistentManifold_replaceContactPoint(long j, btPersistentManifold btpersistentmanifold, long j2, btManifoldPoint btmanifoldpoint, int i);

    public static final native void btPersistentManifold_setBodies(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionObject btcollisionobject, long j3, btCollisionObject btcollisionobject2);

    public static final native void btPersistentManifold_setContactBreakingThreshold(long j, btPersistentManifold btpersistentmanifold, float f);

    public static final native void btPersistentManifold_setContactProcessingThreshold(long j, btPersistentManifold btpersistentmanifold, float f);

    public static final native void btPersistentManifold_setNumContacts(long j, btPersistentManifold btpersistentmanifold, int i);

    public static final native boolean btPersistentManifold_validContactDistance(long j, btPersistentManifold btpersistentmanifold, long j2, btManifoldPoint btmanifoldpoint);

    public static final native long btPointCollector_SWIGUpcast(long j);

    public static final native float btPointCollector_distance_get(long j, btPointCollector btpointcollector);

    public static final native void btPointCollector_distance_set(long j, btPointCollector btpointcollector, float f);

    public static final native boolean btPointCollector_hasResult_get(long j, btPointCollector btpointcollector);

    public static final native void btPointCollector_hasResult_set(long j, btPointCollector btpointcollector, boolean z);

    public static final native long btPointCollector_normalOnBInWorld_get(long j, btPointCollector btpointcollector);

    public static final native void btPointCollector_normalOnBInWorld_set(long j, btPointCollector btpointcollector, long j2, btVector3 btvector3);

    public static final native long btPointCollector_pointInWorld_get(long j, btPointCollector btpointcollector);

    public static final native void btPointCollector_pointInWorld_set(long j, btPointCollector btpointcollector, long j2, btVector3 btvector3);

    public static final native void btPolyhedralContactClipping_clipFace(long j, btVector3Array btvector3array, long j2, btVector3Array btvector3array2, Vector3 vector3, float f);

    public static final native void btPolyhedralContactClipping_clipFaceAgainstHull(Vector3 vector3, long j, btConvexPolyhedron btconvexpolyhedron, Matrix4 matrix4, long j2, btVector3Array btvector3array, long j3, btVector3Array btvector3array2, float f, float f2, long j4, btDiscreteCollisionDetectorInterface.Result result);

    public static final native void btPolyhedralContactClipping_clipHullAgainstHull(Vector3 vector3, long j, btConvexPolyhedron btconvexpolyhedron, long j2, btConvexPolyhedron btconvexpolyhedron2, Matrix4 matrix4, Matrix4 matrix5, float f, float f2, long j3, btVector3Array btvector3array, long j4, btVector3Array btvector3array2, long j5, btDiscreteCollisionDetectorInterface.Result result);

    public static final native boolean btPolyhedralContactClipping_findSeparatingAxis(long j, btConvexPolyhedron btconvexpolyhedron, long j2, btConvexPolyhedron btconvexpolyhedron2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, long j3, btDiscreteCollisionDetectorInterface.Result result);

    public static final native long btPolyhedralConvexAabbCachingShape_SWIGUpcast(long j);

    public static final native void btPolyhedralConvexAabbCachingShape_getNonvirtualAabb(long j, btPolyhedralConvexAabbCachingShape btpolyhedralconvexaabbcachingshape, Matrix4 matrix4, Vector3 vector3, Vector3 vector4, float f);

    public static final native void btPolyhedralConvexAabbCachingShape_recalcLocalAabb(long j, btPolyhedralConvexAabbCachingShape btpolyhedralconvexaabbcachingshape);

    public static final native long btPolyhedralConvexShape_SWIGUpcast(long j);

    public static final native long btPolyhedralConvexShape_getConvexPolyhedron(long j, btPolyhedralConvexShape btpolyhedralconvexshape);

    public static final native void btPolyhedralConvexShape_getEdge(long j, btPolyhedralConvexShape btpolyhedralconvexshape, int i, Vector3 vector3, Vector3 vector4);

    public static final native int btPolyhedralConvexShape_getNumEdges(long j, btPolyhedralConvexShape btpolyhedralconvexshape);

    public static final native int btPolyhedralConvexShape_getNumPlanes(long j, btPolyhedralConvexShape btpolyhedralconvexshape);

    public static final native int btPolyhedralConvexShape_getNumVertices(long j, btPolyhedralConvexShape btpolyhedralconvexshape);

    public static final native void btPolyhedralConvexShape_getPlane(long j, btPolyhedralConvexShape btpolyhedralconvexshape, Vector3 vector3, Vector3 vector4, int i);

    public static final native void btPolyhedralConvexShape_getVertex(long j, btPolyhedralConvexShape btpolyhedralconvexshape, int i, Vector3 vector3);

    public static final native boolean btPolyhedralConvexShape_initializePolyhedralFeatures__SWIG_0(long j, btPolyhedralConvexShape btpolyhedralconvexshape, int i);

    public static final native boolean btPolyhedralConvexShape_initializePolyhedralFeatures__SWIG_1(long j, btPolyhedralConvexShape btpolyhedralconvexshape);

    public static final native boolean btPolyhedralConvexShape_isInside(long j, btPolyhedralConvexShape btpolyhedralconvexshape, Vector3 vector3, float f);

    public static final native void btPolyhedralConvexShape_operatorDeleteArray__SWIG_0(long j, btPolyhedralConvexShape btpolyhedralconvexshape, long j2);

    public static final native void btPolyhedralConvexShape_operatorDeleteArray__SWIG_1(long j, btPolyhedralConvexShape btpolyhedralconvexshape, long j2, long j3);

    public static final native void btPolyhedralConvexShape_operatorDelete__SWIG_0(long j, btPolyhedralConvexShape btpolyhedralconvexshape, long j2);

    public static final native void btPolyhedralConvexShape_operatorDelete__SWIG_1(long j, btPolyhedralConvexShape btpolyhedralconvexshape, long j2, long j3);

    public static final native long btPolyhedralConvexShape_operatorNewArray__SWIG_0(long j, btPolyhedralConvexShape btpolyhedralconvexshape, long j2);

    public static final native long btPolyhedralConvexShape_operatorNewArray__SWIG_1(long j, btPolyhedralConvexShape btpolyhedralconvexshape, long j2, long j3);

    public static final native long btPolyhedralConvexShape_operatorNew__SWIG_0(long j, btPolyhedralConvexShape btpolyhedralconvexshape, long j2);

    public static final native long btPolyhedralConvexShape_operatorNew__SWIG_1(long j, btPolyhedralConvexShape btpolyhedralconvexshape, long j2, long j3);

    public static final native long btPoolAlloc(long j);

    public static final native void btPoolFree(long j);

    public static final native long btPoolRealloc(long j, long j2, long j3);

    public static final native void btPortalDir(long j, _btMprSimplex_t _btmprsimplex_t, long j2, btVector3 btvector3);

    public static final native long btPositionAndRadius_pos_get(long j, btPositionAndRadius btpositionandradius);

    public static final native void btPositionAndRadius_pos_set(long j, btPositionAndRadius btpositionandradius, long j2, btVector3FloatData btvector3floatdata);

    public static final native float btPositionAndRadius_radius_get(long j, btPositionAndRadius btpositionandradius);

    public static final native void btPositionAndRadius_radius_set(long j, btPositionAndRadius btpositionandradius, float f);

    public static final native void btPrimitiveManagerBase_get_primitive_box(long j, btPrimitiveManagerBase btprimitivemanagerbase, int i, long j2, btAABB btaabb);

    public static final native int btPrimitiveManagerBase_get_primitive_count(long j, btPrimitiveManagerBase btprimitivemanagerbase);

    public static final native void btPrimitiveManagerBase_get_primitive_triangle(long j, btPrimitiveManagerBase btprimitivemanagerbase, int i, long j2, btPrimitiveTriangle btprimitivetriangle);

    public static final native boolean btPrimitiveManagerBase_is_trimesh(long j, btPrimitiveManagerBase btprimitivemanagerbase);

    public static final native void btPrimitiveTriangle_applyTransform(long j, btPrimitiveTriangle btprimitivetriangle, Matrix4 matrix4);

    public static final native void btPrimitiveTriangle_buildTriPlane(long j, btPrimitiveTriangle btprimitivetriangle);

    public static final native int btPrimitiveTriangle_clip_triangle(long j, btPrimitiveTriangle btprimitivetriangle, long j2, btPrimitiveTriangle btprimitivetriangle2, long j3, btVector3 btvector3);

    public static final native float btPrimitiveTriangle_dummy_get(long j, btPrimitiveTriangle btprimitivetriangle);

    public static final native void btPrimitiveTriangle_dummy_set(long j, btPrimitiveTriangle btprimitivetriangle, float f);

    public static final native boolean btPrimitiveTriangle_find_triangle_collision_clip_method(long j, btPrimitiveTriangle btprimitivetriangle, long j2, btPrimitiveTriangle btprimitivetriangle2, long j3, GIM_TRIANGLE_CONTACT gim_triangle_contact);

    public static final native void btPrimitiveTriangle_get_edge_plane(long j, btPrimitiveTriangle btprimitivetriangle, int i, long j2, btVector4 btvector4);

    public static final native float btPrimitiveTriangle_margin_get(long j, btPrimitiveTriangle btprimitivetriangle);

    public static final native void btPrimitiveTriangle_margin_set(long j, btPrimitiveTriangle btprimitivetriangle, float f);

    public static final native boolean btPrimitiveTriangle_overlap_test_conservative(long j, btPrimitiveTriangle btprimitivetriangle, long j2, btPrimitiveTriangle btprimitivetriangle2);

    public static final native long btPrimitiveTriangle_plane_get(long j, btPrimitiveTriangle btprimitivetriangle);

    public static final native void btPrimitiveTriangle_plane_set(long j, btPrimitiveTriangle btprimitivetriangle, long j2, btVector4 btvector4);

    public static final native long btPrimitiveTriangle_vertices_get(long j, btPrimitiveTriangle btprimitivetriangle);

    public static final native void btPrimitiveTriangle_vertices_set(long j, btPrimitiveTriangle btprimitivetriangle, long j2, btVector3 btvector3);

    public static final native long btQuantizedBvhDoubleData_bvhAabbMax_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_bvhAabbMax_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btQuantizedBvhDoubleData_bvhAabbMin_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_bvhAabbMin_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btQuantizedBvhDoubleData_bvhQuantization_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_bvhQuantization_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btQuantizedBvhDoubleData_contiguousNodesPtr_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_contiguousNodesPtr_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, long j2, btOptimizedBvhNodeDoubleData btoptimizedbvhnodedoubledata);

    public static final native int btQuantizedBvhDoubleData_curNodeIndex_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_curNodeIndex_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, int i);

    public static final native int btQuantizedBvhDoubleData_numContiguousLeafNodes_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_numContiguousLeafNodes_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, int i);

    public static final native int btQuantizedBvhDoubleData_numQuantizedContiguousNodes_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_numQuantizedContiguousNodes_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, int i);

    public static final native int btQuantizedBvhDoubleData_numSubtreeHeaders_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_numSubtreeHeaders_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, int i);

    public static final native long btQuantizedBvhDoubleData_quantizedContiguousNodesPtr_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_quantizedContiguousNodesPtr_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, long j2, btQuantizedBvhNodeData btquantizedbvhnodedata);

    public static final native long btQuantizedBvhDoubleData_subTreeInfoPtr_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_subTreeInfoPtr_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, long j2, btBvhSubtreeInfoData btbvhsubtreeinfodata);

    public static final native int btQuantizedBvhDoubleData_traversalMode_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_traversalMode_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, int i);

    public static final native int btQuantizedBvhDoubleData_useQuantization_get(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvhDoubleData_useQuantization_set(long j, btQuantizedBvhDoubleData btquantizedbvhdoubledata, int i);

    public static final native long btQuantizedBvhFloatData_bvhAabbMax_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_bvhAabbMax_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btQuantizedBvhFloatData_bvhAabbMin_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_bvhAabbMin_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btQuantizedBvhFloatData_bvhQuantization_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_bvhQuantization_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btQuantizedBvhFloatData_contiguousNodesPtr_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_contiguousNodesPtr_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, long j2, btOptimizedBvhNodeFloatData btoptimizedbvhnodefloatdata);

    public static final native int btQuantizedBvhFloatData_curNodeIndex_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_curNodeIndex_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, int i);

    public static final native int btQuantizedBvhFloatData_numContiguousLeafNodes_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_numContiguousLeafNodes_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, int i);

    public static final native int btQuantizedBvhFloatData_numQuantizedContiguousNodes_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_numQuantizedContiguousNodes_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, int i);

    public static final native int btQuantizedBvhFloatData_numSubtreeHeaders_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_numSubtreeHeaders_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, int i);

    public static final native long btQuantizedBvhFloatData_quantizedContiguousNodesPtr_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_quantizedContiguousNodesPtr_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, long j2, btQuantizedBvhNodeData btquantizedbvhnodedata);

    public static final native long btQuantizedBvhFloatData_subTreeInfoPtr_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_subTreeInfoPtr_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, long j2, btBvhSubtreeInfoData btbvhsubtreeinfodata);

    public static final native int btQuantizedBvhFloatData_traversalMode_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_traversalMode_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, int i);

    public static final native int btQuantizedBvhFloatData_useQuantization_get(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native void btQuantizedBvhFloatData_useQuantization_set(long j, btQuantizedBvhFloatData btquantizedbvhfloatdata, int i);

    public static final native int btQuantizedBvhNodeData_escapeIndexOrTriangleIndex_get(long j, btQuantizedBvhNodeData btquantizedbvhnodedata);

    public static final native void btQuantizedBvhNodeData_escapeIndexOrTriangleIndex_set(long j, btQuantizedBvhNodeData btquantizedbvhnodedata, int i);

    public static final native int[] btQuantizedBvhNodeData_quantizedAabbMax_get(long j, btQuantizedBvhNodeData btquantizedbvhnodedata);

    public static final native void btQuantizedBvhNodeData_quantizedAabbMax_set(long j, btQuantizedBvhNodeData btquantizedbvhnodedata, int[] iArr);

    public static final native int[] btQuantizedBvhNodeData_quantizedAabbMin_get(long j, btQuantizedBvhNodeData btquantizedbvhnodedata);

    public static final native void btQuantizedBvhNodeData_quantizedAabbMin_set(long j, btQuantizedBvhNodeData btquantizedbvhnodedata, int[] iArr);

    public static final native int btQuantizedBvhNode_escapeIndexOrTriangleIndex_get(long j, btQuantizedBvhNode btquantizedbvhnode);

    public static final native void btQuantizedBvhNode_escapeIndexOrTriangleIndex_set(long j, btQuantizedBvhNode btquantizedbvhnode, int i);

    public static final native int btQuantizedBvhNode_getEscapeIndex(long j, btQuantizedBvhNode btquantizedbvhnode);

    public static final native int btQuantizedBvhNode_getPartId(long j, btQuantizedBvhNode btquantizedbvhnode);

    public static final native int btQuantizedBvhNode_getTriangleIndex(long j, btQuantizedBvhNode btquantizedbvhnode);

    public static final native boolean btQuantizedBvhNode_isLeafNode(long j, btQuantizedBvhNode btquantizedbvhnode);

    public static final native void btQuantizedBvhNode_operatorDeleteArray__SWIG_0(long j, btQuantizedBvhNode btquantizedbvhnode, long j2);

    public static final native void btQuantizedBvhNode_operatorDeleteArray__SWIG_1(long j, btQuantizedBvhNode btquantizedbvhnode, long j2, long j3);

    public static final native void btQuantizedBvhNode_operatorDelete__SWIG_0(long j, btQuantizedBvhNode btquantizedbvhnode, long j2);

    public static final native void btQuantizedBvhNode_operatorDelete__SWIG_1(long j, btQuantizedBvhNode btquantizedbvhnode, long j2, long j3);

    public static final native long btQuantizedBvhNode_operatorNewArray__SWIG_0(long j, btQuantizedBvhNode btquantizedbvhnode, long j2);

    public static final native long btQuantizedBvhNode_operatorNewArray__SWIG_1(long j, btQuantizedBvhNode btquantizedbvhnode, long j2, long j3);

    public static final native long btQuantizedBvhNode_operatorNew__SWIG_0(long j, btQuantizedBvhNode btquantizedbvhnode, long j2);

    public static final native long btQuantizedBvhNode_operatorNew__SWIG_1(long j, btQuantizedBvhNode btquantizedbvhnode, long j2, long j3);

    public static final native int[] btQuantizedBvhNode_quantizedAabbMax_get(long j, btQuantizedBvhNode btquantizedbvhnode);

    public static final native void btQuantizedBvhNode_quantizedAabbMax_set(long j, btQuantizedBvhNode btquantizedbvhnode, int[] iArr);

    public static final native int[] btQuantizedBvhNode_quantizedAabbMin_get(long j, btQuantizedBvhNode btquantizedbvhnode);

    public static final native void btQuantizedBvhNode_quantizedAabbMin_set(long j, btQuantizedBvhNode btquantizedbvhnode, int[] iArr);

    public static final native void btQuantizedBvhTree_build_tree(long j, btQuantizedBvhTree btquantizedbvhtree, long j2, GIM_BVH_DATA_ARRAY gim_bvh_data_array);

    public static final native void btQuantizedBvhTree_clearNodes(long j, btQuantizedBvhTree btquantizedbvhtree);

    public static final native int btQuantizedBvhTree_getEscapeNodeIndex(long j, btQuantizedBvhTree btquantizedbvhtree, int i);

    public static final native int btQuantizedBvhTree_getLeftNode(long j, btQuantizedBvhTree btquantizedbvhtree, int i);

    public static final native void btQuantizedBvhTree_getNodeBound(long j, btQuantizedBvhTree btquantizedbvhtree, int i, long j2, btAABB btaabb);

    public static final native int btQuantizedBvhTree_getNodeCount(long j, btQuantizedBvhTree btquantizedbvhtree);

    public static final native int btQuantizedBvhTree_getNodeData(long j, btQuantizedBvhTree btquantizedbvhtree, int i);

    public static final native int btQuantizedBvhTree_getRightNode(long j, btQuantizedBvhTree btquantizedbvhtree, int i);

    public static final native long btQuantizedBvhTree_get_node_pointer__SWIG_0(long j, btQuantizedBvhTree btquantizedbvhtree, int i);

    public static final native long btQuantizedBvhTree_get_node_pointer__SWIG_1(long j, btQuantizedBvhTree btquantizedbvhtree);

    public static final native boolean btQuantizedBvhTree_isLeafNode(long j, btQuantizedBvhTree btquantizedbvhtree, int i);

    public static final native void btQuantizedBvhTree_quantizePoint(long j, btQuantizedBvhTree btquantizedbvhtree, IntBuffer intBuffer, Vector3 vector3);

    public static final native void btQuantizedBvhTree_setNodeBound(long j, btQuantizedBvhTree btquantizedbvhtree, int i, long j2, btAABB btaabb);

    public static final native boolean btQuantizedBvhTree_testQuantizedBoxOverlapp(long j, btQuantizedBvhTree btquantizedbvhtree, int i, IntBuffer intBuffer, IntBuffer intBuffer2);

    public static final native void btQuantizedBvh_buildInternal(long j, btQuantizedBvh btquantizedbvh);

    public static final native long btQuantizedBvh_calculateSerializeBufferSize(long j, btQuantizedBvh btquantizedbvh);

    public static final native int btQuantizedBvh_calculateSerializeBufferSizeNew(long j, btQuantizedBvh btquantizedbvh);

    public static final native void btQuantizedBvh_deSerializeDouble(long j, btQuantizedBvh btquantizedbvh, long j2, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native void btQuantizedBvh_deSerializeFloat(long j, btQuantizedBvh btquantizedbvh, long j2, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native long btQuantizedBvh_deSerializeInPlace(long j, long j2, boolean z);

    public static final native long btQuantizedBvh_getAlignmentSerializationPadding();

    public static final native long btQuantizedBvh_getLeafNodeArray(long j, btQuantizedBvh btquantizedbvh);

    public static final native long btQuantizedBvh_getQuantizedNodeArray(long j, btQuantizedBvh btquantizedbvh);

    public static final native long btQuantizedBvh_getSubtreeInfoArray(long j, btQuantizedBvh btquantizedbvh);

    public static final native boolean btQuantizedBvh_isQuantized(long j, btQuantizedBvh btquantizedbvh);

    public static final native void btQuantizedBvh_operatorDeleteArray__SWIG_0(long j, btQuantizedBvh btquantizedbvh, long j2);

    public static final native void btQuantizedBvh_operatorDeleteArray__SWIG_1(long j, btQuantizedBvh btquantizedbvh, long j2, long j3);

    public static final native void btQuantizedBvh_operatorDelete__SWIG_0(long j, btQuantizedBvh btquantizedbvh, long j2);

    public static final native void btQuantizedBvh_operatorDelete__SWIG_1(long j, btQuantizedBvh btquantizedbvh, long j2, long j3);

    public static final native long btQuantizedBvh_operatorNewArray__SWIG_0(long j, btQuantizedBvh btquantizedbvh, long j2);

    public static final native long btQuantizedBvh_operatorNewArray__SWIG_1(long j, btQuantizedBvh btquantizedbvh, long j2, long j3);

    public static final native long btQuantizedBvh_operatorNew__SWIG_0(long j, btQuantizedBvh btquantizedbvh, long j2);

    public static final native long btQuantizedBvh_operatorNew__SWIG_1(long j, btQuantizedBvh btquantizedbvh, long j2, long j3);

    public static final native void btQuantizedBvh_quantize(long j, btQuantizedBvh btquantizedbvh, IntBuffer intBuffer, Vector3 vector3, int i);

    public static final native void btQuantizedBvh_quantizeWithClamp(long j, btQuantizedBvh btquantizedbvh, IntBuffer intBuffer, Vector3 vector3, int i);

    public static final native void btQuantizedBvh_reportAabbOverlappingNodex(long j, btQuantizedBvh btquantizedbvh, long j2, btNodeOverlapCallback btnodeoverlapcallback, Vector3 vector3, Vector3 vector4);

    public static final native void btQuantizedBvh_reportBoxCastOverlappingNodex(long j, btQuantizedBvh btquantizedbvh, long j2, btNodeOverlapCallback btnodeoverlapcallback, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native void btQuantizedBvh_reportRayOverlappingNodex(long j, btQuantizedBvh btquantizedbvh, long j2, btNodeOverlapCallback btnodeoverlapcallback, Vector3 vector3, Vector3 vector4);

    public static final native boolean btQuantizedBvh_serialize__SWIG_0(long j, btQuantizedBvh btquantizedbvh, long j2, long j3, boolean z);

    public static final native String btQuantizedBvh_serialize__SWIG_1(long j, btQuantizedBvh btquantizedbvh, long j2, long j3, btSerializer btserializer);

    public static final native void btQuantizedBvh_setQuantizationValues__SWIG_0(long j, btQuantizedBvh btquantizedbvh, Vector3 vector3, Vector3 vector4, float f);

    public static final native void btQuantizedBvh_setQuantizationValues__SWIG_1(long j, btQuantizedBvh btquantizedbvh, Vector3 vector3, Vector3 vector4);

    public static final native void btQuantizedBvh_setTraversalMode(long j, btQuantizedBvh btquantizedbvh, int i);

    public static final native Vector3 btQuantizedBvh_unQuantize(long j, btQuantizedBvh btquantizedbvh, IntBuffer intBuffer);

    public static final native long btScaledBvhTriangleMeshShape_SWIGUpcast(long j);

    public static final native long btScaledBvhTriangleMeshShape_getChildShape(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape);

    public static final native long btScaledBvhTriangleMeshShape_getChildShapeConst(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape);

    public static final native void btScaledBvhTriangleMeshShape_operatorDeleteArray__SWIG_0(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape, long j2);

    public static final native void btScaledBvhTriangleMeshShape_operatorDeleteArray__SWIG_1(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape, long j2, long j3);

    public static final native void btScaledBvhTriangleMeshShape_operatorDelete__SWIG_0(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape, long j2);

    public static final native void btScaledBvhTriangleMeshShape_operatorDelete__SWIG_1(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape, long j2, long j3);

    public static final native long btScaledBvhTriangleMeshShape_operatorNewArray__SWIG_0(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape, long j2);

    public static final native long btScaledBvhTriangleMeshShape_operatorNewArray__SWIG_1(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape, long j2, long j3);

    public static final native long btScaledBvhTriangleMeshShape_operatorNew__SWIG_0(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape, long j2);

    public static final native long btScaledBvhTriangleMeshShape_operatorNew__SWIG_1(long j, btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape, long j2, long j3);

    public static final native long btScaledTriangleMeshShapeData_localScaling_get(long j, btScaledTriangleMeshShapeData btscaledtrianglemeshshapedata);

    public static final native void btScaledTriangleMeshShapeData_localScaling_set(long j, btScaledTriangleMeshShapeData btscaledtrianglemeshshapedata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btScaledTriangleMeshShapeData_trimeshShapeData_get(long j, btScaledTriangleMeshShapeData btscaledtrianglemeshshapedata);

    public static final native void btScaledTriangleMeshShapeData_trimeshShapeData_set(long j, btScaledTriangleMeshShapeData btscaledtrianglemeshshapedata, long j2, btTriangleMeshShapeData bttrianglemeshshapedata);

    public static final native boolean btShapeHull_buildHull(long j, btShapeHull btshapehull, float f);

    public static final native int btShapeHull_getIndex(long j, btShapeHull btshapehull, int i);

    public static final native Vector3 btShapeHull_getVertex(long j, btShapeHull btshapehull, int i);

    public static final native int btShapeHull_numIndices(long j, btShapeHull btshapehull);

    public static final native int btShapeHull_numTriangles(long j, btShapeHull btshapehull);

    public static final native int btShapeHull_numVertices(long j, btShapeHull btshapehull);

    public static final native void btShapeHull_operatorDeleteArray__SWIG_0(long j, btShapeHull btshapehull, long j2);

    public static final native void btShapeHull_operatorDeleteArray__SWIG_1(long j, btShapeHull btshapehull, long j2, long j3);

    public static final native void btShapeHull_operatorDelete__SWIG_0(long j, btShapeHull btshapehull, long j2);

    public static final native void btShapeHull_operatorDelete__SWIG_1(long j, btShapeHull btshapehull, long j2, long j3);

    public static final native long btShapeHull_operatorNewArray__SWIG_0(long j, btShapeHull btshapehull, long j2);

    public static final native long btShapeHull_operatorNewArray__SWIG_1(long j, btShapeHull btshapehull, long j2, long j3);

    public static final native long btShapeHull_operatorNew__SWIG_0(long j, btShapeHull btshapehull, long j2);

    public static final native long btShapeHull_operatorNew__SWIG_1(long j, btShapeHull btshapehull, long j2, long j3);

    public static final native String btShortIntIndexData_pad_get(long j, btShortIntIndexData btshortintindexdata);

    public static final native void btShortIntIndexData_pad_set(long j, btShortIntIndexData btshortintindexdata, String str);

    public static final native short btShortIntIndexData_value_get(long j, btShortIntIndexData btshortintindexdata);

    public static final native void btShortIntIndexData_value_set(long j, btShortIntIndexData btshortintindexdata, short s);

    public static final native String btShortIntIndexTripletData_pad_get(long j, btShortIntIndexTripletData btshortintindextripletdata);

    public static final native void btShortIntIndexTripletData_pad_set(long j, btShortIntIndexTripletData btshortintindextripletdata, String str);

    public static final native short[] btShortIntIndexTripletData_values_get(long j, btShortIntIndexTripletData btshortintindextripletdata);

    public static final native void btShortIntIndexTripletData_values_set(long j, btShortIntIndexTripletData btshortintindextripletdata, short[] sArr);

    public static final native int btSimpleBroadphaseProxy_GetNextFree(long j, btSimpleBroadphaseProxy btsimplebroadphaseproxy);

    public static final native long btSimpleBroadphaseProxy_SWIGUpcast(long j);

    public static final native void btSimpleBroadphaseProxy_SetNextFree(long j, btSimpleBroadphaseProxy btsimplebroadphaseproxy, int i);

    public static final native int btSimpleBroadphaseProxy_nextFree_get(long j, btSimpleBroadphaseProxy btsimplebroadphaseproxy);

    public static final native void btSimpleBroadphaseProxy_nextFree_set(long j, btSimpleBroadphaseProxy btsimplebroadphaseproxy, int i);

    public static final native long btSimpleBroadphase_SWIGUpcast(long j);

    public static final native boolean btSimpleBroadphase_aabbOverlap(long j, btSimpleBroadphaseProxy btsimplebroadphaseproxy, long j2, btSimpleBroadphaseProxy btsimplebroadphaseproxy2);

    public static final native void btSimpleBroadphase_rayTest__SWIG_0(long j, btSimpleBroadphase btsimplebroadphase, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6);

    public static final native void btSimpleBroadphase_rayTest__SWIG_1(long j, btSimpleBroadphase btsimplebroadphase, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5);

    public static final native void btSimpleBroadphase_rayTest__SWIG_2(long j, btSimpleBroadphase btsimplebroadphase, Vector3 vector3, Vector3 vector4, long j2, btBroadphaseRayCallback btbroadphaseraycallback);

    public static final native boolean btSimpleBroadphase_testAabbOverlap(long j, btSimpleBroadphase btsimplebroadphase, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native int btSimplePair_indexA_get(long j, btSimplePair btsimplepair);

    public static final native void btSimplePair_indexA_set(long j, btSimplePair btsimplepair, int i);

    public static final native int btSimplePair_indexB_get(long j, btSimplePair btsimplepair);

    public static final native void btSimplePair_indexB_set(long j, btSimplePair btsimplepair, int i);

    public static final native long btSimplePair_userPointer_get(long j, btSimplePair btsimplepair);

    public static final native void btSimplePair_userPointer_set(long j, btSimplePair btsimplepair, long j2);

    public static final native int btSimplePair_userValue_get(long j, btSimplePair btsimplepair);

    public static final native void btSimplePair_userValue_set(long j, btSimplePair btsimplepair, int i);

    public static final native void btSimulationIslandManager_IslandCallback_processIsland(long j, btSimulationIslandManager.IslandCallback islandCallback, long j2, int i, long j3, int i2, int i3);

    public static final native void btSimulationIslandManager_buildAndProcessIslands(long j, btSimulationIslandManager btsimulationislandmanager, long j2, btDispatcher btdispatcher, long j3, btCollisionWorld btcollisionworld, long j4, btSimulationIslandManager.IslandCallback islandCallback);

    public static final native void btSimulationIslandManager_buildIslands(long j, btSimulationIslandManager btsimulationislandmanager, long j2, btDispatcher btdispatcher, long j3, btCollisionWorld btcollisionworld);

    public static final native void btSimulationIslandManager_findUnions(long j, btSimulationIslandManager btsimulationislandmanager, long j2, btDispatcher btdispatcher, long j3, btCollisionWorld btcollisionworld);

    public static final native boolean btSimulationIslandManager_getSplitIslands(long j, btSimulationIslandManager btsimulationislandmanager);

    public static final native long btSimulationIslandManager_getUnionFind(long j, btSimulationIslandManager btsimulationislandmanager);

    public static final native void btSimulationIslandManager_initUnionFind(long j, btSimulationIslandManager btsimulationislandmanager, int i);

    public static final native void btSimulationIslandManager_setSplitIslands(long j, btSimulationIslandManager btsimulationislandmanager, boolean z);

    public static final native void btSimulationIslandManager_storeIslandActivationState(long j, btSimulationIslandManager btsimulationislandmanager, long j2, btCollisionWorld btcollisionworld);

    public static final native void btSimulationIslandManager_updateActivationState(long j, btSimulationIslandManager btsimulationislandmanager, long j2, btCollisionWorld btcollisionworld, long j3, btDispatcher btdispatcher);

    public static final native long btSortedOverlappingPairCache_SWIGUpcast(long j);

    public static final native long btSortedOverlappingPairCache_getOverlapFilterCallback(long j, btSortedOverlappingPairCache btsortedoverlappingpaircache);

    public static final native long btSortedOverlappingPairCache_getOverlappingPairArrayConst(long j, btSortedOverlappingPairCache btsortedoverlappingpaircache);

    public static final native boolean btSortedOverlappingPairCache_needsBroadphaseCollision(long j, btSortedOverlappingPairCache btsortedoverlappingpaircache, long j2, btBroadphaseProxy btbroadphaseproxy, long j3, btBroadphaseProxy btbroadphaseproxy2);

    public static final native long btSphereBoxCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btSphereBoxCollisionAlgorithm_SWIGUpcast(long j);

    public static final native boolean btSphereBoxCollisionAlgorithm_getSphereDistance(long j, btSphereBoxCollisionAlgorithm btsphereboxcollisionalgorithm, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, Vector3 vector3, Vector3 vector4, long j3, Vector3 vector5, float f, float f2);

    public static final native float btSphereBoxCollisionAlgorithm_getSpherePenetration(long j, btSphereBoxCollisionAlgorithm btsphereboxcollisionalgorithm, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native long btSphereShape_SWIGUpcast(long j);

    public static final native float btSphereShape_getRadius(long j, btSphereShape btsphereshape);

    public static final native void btSphereShape_operatorDeleteArray__SWIG_0(long j, btSphereShape btsphereshape, long j2);

    public static final native void btSphereShape_operatorDeleteArray__SWIG_1(long j, btSphereShape btsphereshape, long j2, long j3);

    public static final native void btSphereShape_operatorDelete__SWIG_0(long j, btSphereShape btsphereshape, long j2);

    public static final native void btSphereShape_operatorDelete__SWIG_1(long j, btSphereShape btsphereshape, long j2, long j3);

    public static final native long btSphereShape_operatorNewArray__SWIG_0(long j, btSphereShape btsphereshape, long j2);

    public static final native long btSphereShape_operatorNewArray__SWIG_1(long j, btSphereShape btsphereshape, long j2, long j3);

    public static final native long btSphereShape_operatorNew__SWIG_0(long j, btSphereShape btsphereshape, long j2);

    public static final native long btSphereShape_operatorNew__SWIG_1(long j, btSphereShape btsphereshape, long j2, long j3);

    public static final native void btSphereShape_setUnscaledRadius(long j, btSphereShape btsphereshape, float f);

    public static final native long btSphereSphereCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btSphereSphereCollisionAlgorithm_SWIGUpcast(long j);

    public static final native long btSphereTriangleCollisionAlgorithm_CreateFunc_SWIGUpcast(long j);

    public static final native long btSphereTriangleCollisionAlgorithm_SWIGUpcast(long j);

    public static final native long btStaticPlaneShapeData_collisionShapeData_get(long j, btStaticPlaneShapeData btstaticplaneshapedata);

    public static final native void btStaticPlaneShapeData_collisionShapeData_set(long j, btStaticPlaneShapeData btstaticplaneshapedata, long j2, btCollisionShapeData btcollisionshapedata);

    public static final native long btStaticPlaneShapeData_localScaling_get(long j, btStaticPlaneShapeData btstaticplaneshapedata);

    public static final native void btStaticPlaneShapeData_localScaling_set(long j, btStaticPlaneShapeData btstaticplaneshapedata, long j2, btVector3FloatData btvector3floatdata);

    public static final native String btStaticPlaneShapeData_pad_get(long j, btStaticPlaneShapeData btstaticplaneshapedata);

    public static final native void btStaticPlaneShapeData_pad_set(long j, btStaticPlaneShapeData btstaticplaneshapedata, String str);

    public static final native float btStaticPlaneShapeData_planeConstant_get(long j, btStaticPlaneShapeData btstaticplaneshapedata);

    public static final native void btStaticPlaneShapeData_planeConstant_set(long j, btStaticPlaneShapeData btstaticplaneshapedata, float f);

    public static final native long btStaticPlaneShapeData_planeNormal_get(long j, btStaticPlaneShapeData btstaticplaneshapedata);

    public static final native void btStaticPlaneShapeData_planeNormal_set(long j, btStaticPlaneShapeData btstaticplaneshapedata, long j2, btVector3FloatData btvector3floatdata);

    public static final native long btStaticPlaneShape_SWIGUpcast(long j);

    public static final native float btStaticPlaneShape_getPlaneConstant(long j, btStaticPlaneShape btstaticplaneshape);

    public static final native Vector3 btStaticPlaneShape_getPlaneNormal(long j, btStaticPlaneShape btstaticplaneshape);

    public static final native void btStaticPlaneShape_operatorDeleteArray__SWIG_0(long j, btStaticPlaneShape btstaticplaneshape, long j2);

    public static final native void btStaticPlaneShape_operatorDeleteArray__SWIG_1(long j, btStaticPlaneShape btstaticplaneshape, long j2, long j3);

    public static final native void btStaticPlaneShape_operatorDelete__SWIG_0(long j, btStaticPlaneShape btstaticplaneshape, long j2);

    public static final native void btStaticPlaneShape_operatorDelete__SWIG_1(long j, btStaticPlaneShape btstaticplaneshape, long j2, long j3);

    public static final native long btStaticPlaneShape_operatorNewArray__SWIG_0(long j, btStaticPlaneShape btstaticplaneshape, long j2);

    public static final native long btStaticPlaneShape_operatorNewArray__SWIG_1(long j, btStaticPlaneShape btstaticplaneshape, long j2, long j3);

    public static final native long btStaticPlaneShape_operatorNew__SWIG_0(long j, btStaticPlaneShape btstaticplaneshape, long j2);

    public static final native long btStaticPlaneShape_operatorNew__SWIG_1(long j, btStaticPlaneShape btstaticplaneshape, long j2, long j3);

    public static final native long btStorageResult_SWIGUpcast(long j);

    public static final native long btStorageResult_closestPointInB_get(long j, btStorageResult btstorageresult);

    public static final native void btStorageResult_closestPointInB_set(long j, btStorageResult btstorageresult, long j2, btVector3 btvector3);

    public static final native float btStorageResult_distance_get(long j, btStorageResult btstorageresult);

    public static final native void btStorageResult_distance_set(long j, btStorageResult btstorageresult, float f);

    public static final native long btStorageResult_normalOnSurfaceB_get(long j, btStorageResult btstorageresult);

    public static final native void btStorageResult_normalOnSurfaceB_set(long j, btStorageResult btstorageresult, long j2, btVector3 btvector3);

    public static final native long btStridingMeshInterfaceData_meshPartsPtr_get(long j, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native void btStridingMeshInterfaceData_meshPartsPtr_set(long j, btStridingMeshInterfaceData btstridingmeshinterfacedata, long j2, btMeshPartData btmeshpartdata);

    public static final native int btStridingMeshInterfaceData_numMeshParts_get(long j, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native void btStridingMeshInterfaceData_numMeshParts_set(long j, btStridingMeshInterfaceData btstridingmeshinterfacedata, int i);

    public static final native String btStridingMeshInterfaceData_padding_get(long j, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native void btStridingMeshInterfaceData_padding_set(long j, btStridingMeshInterfaceData btstridingmeshinterfacedata, String str);

    public static final native long btStridingMeshInterfaceData_scaling_get(long j, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native void btStridingMeshInterfaceData_scaling_set(long j, btStridingMeshInterfaceData btstridingmeshinterfacedata, long j2, btVector3FloatData btvector3floatdata);

    public static final native void btStridingMeshInterface_InternalProcessAllTriangles(long j, btStridingMeshInterface btstridingmeshinterface, long j2, btInternalTriangleIndexCallback btinternaltriangleindexcallback, Vector3 vector3, Vector3 vector4);

    public static final native void btStridingMeshInterface_calculateAabbBruteForce(long j, btStridingMeshInterface btstridingmeshinterface, Vector3 vector3, Vector3 vector4);

    public static final native int btStridingMeshInterface_calculateSerializeBufferSize(long j, btStridingMeshInterface btstridingmeshinterface);

    public static final native void btStridingMeshInterface_getLockedReadOnlyVertexIndexBase__SWIG_0(long j, btStridingMeshInterface btstridingmeshinterface, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, int i);

    public static final native void btStridingMeshInterface_getLockedReadOnlyVertexIndexBase__SWIG_1(long j, btStridingMeshInterface btstridingmeshinterface, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9);

    public static final native void btStridingMeshInterface_getLockedVertexIndexBase__SWIG_0(long j, btStridingMeshInterface btstridingmeshinterface, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, int i);

    public static final native void btStridingMeshInterface_getLockedVertexIndexBase__SWIG_1(long j, btStridingMeshInterface btstridingmeshinterface, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9);

    public static final native int btStridingMeshInterface_getNumSubParts(long j, btStridingMeshInterface btstridingmeshinterface);

    public static final native void btStridingMeshInterface_getPremadeAabb(long j, btStridingMeshInterface btstridingmeshinterface, long j2, btVector3 btvector3, long j3, btVector3 btvector4);

    public static final native Vector3 btStridingMeshInterface_getScaling(long j, btStridingMeshInterface btstridingmeshinterface);

    public static final native boolean btStridingMeshInterface_hasPremadeAabb(long j, btStridingMeshInterface btstridingmeshinterface);

    public static final native void btStridingMeshInterface_operatorDeleteArray__SWIG_0(long j, btStridingMeshInterface btstridingmeshinterface, long j2);

    public static final native void btStridingMeshInterface_operatorDeleteArray__SWIG_1(long j, btStridingMeshInterface btstridingmeshinterface, long j2, long j3);

    public static final native void btStridingMeshInterface_operatorDelete__SWIG_0(long j, btStridingMeshInterface btstridingmeshinterface, long j2);

    public static final native void btStridingMeshInterface_operatorDelete__SWIG_1(long j, btStridingMeshInterface btstridingmeshinterface, long j2, long j3);

    public static final native long btStridingMeshInterface_operatorNewArray__SWIG_0(long j, btStridingMeshInterface btstridingmeshinterface, long j2);

    public static final native long btStridingMeshInterface_operatorNewArray__SWIG_1(long j, btStridingMeshInterface btstridingmeshinterface, long j2, long j3);

    public static final native long btStridingMeshInterface_operatorNew__SWIG_0(long j, btStridingMeshInterface btstridingmeshinterface, long j2);

    public static final native long btStridingMeshInterface_operatorNew__SWIG_1(long j, btStridingMeshInterface btstridingmeshinterface, long j2, long j3);

    public static final native void btStridingMeshInterface_preallocateIndices(long j, btStridingMeshInterface btstridingmeshinterface, int i);

    public static final native void btStridingMeshInterface_preallocateVertices(long j, btStridingMeshInterface btstridingmeshinterface, int i);

    public static final native String btStridingMeshInterface_serialize(long j, btStridingMeshInterface btstridingmeshinterface, long j2, long j3, btSerializer btserializer);

    public static final native void btStridingMeshInterface_setPremadeAabb(long j, btStridingMeshInterface btstridingmeshinterface, Vector3 vector3, Vector3 vector4);

    public static final native void btStridingMeshInterface_setScaling(long j, btStridingMeshInterface btstridingmeshinterface, Vector3 vector3);

    public static final native void btStridingMeshInterface_unLockReadOnlyVertexBase(long j, btStridingMeshInterface btstridingmeshinterface, int i);

    public static final native void btStridingMeshInterface_unLockVertexBase(long j, btStridingMeshInterface btstridingmeshinterface, int i);

    public static final native float[] btSubSimplexClosestResult_barycentricCoords_get(long j, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native void btSubSimplexClosestResult_barycentricCoords_set(long j, btSubSimplexClosestResult btsubsimplexclosestresult, float[] fArr);

    public static final native long btSubSimplexClosestResult_closestPointOnSimplex_get(long j, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native void btSubSimplexClosestResult_closestPointOnSimplex_set(long j, btSubSimplexClosestResult btsubsimplexclosestresult, long j2, btVector3 btvector3);

    public static final native boolean btSubSimplexClosestResult_degenerate_get(long j, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native void btSubSimplexClosestResult_degenerate_set(long j, btSubSimplexClosestResult btsubsimplexclosestresult, boolean z);

    public static final native boolean btSubSimplexClosestResult_isValid(long j, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native void btSubSimplexClosestResult_reset(long j, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native void btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_0(long j, btSubSimplexClosestResult btsubsimplexclosestresult, float f, float f2, float f3, float f4);

    public static final native void btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_1(long j, btSubSimplexClosestResult btsubsimplexclosestresult, float f, float f2, float f3);

    public static final native void btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_2(long j, btSubSimplexClosestResult btsubsimplexclosestresult, float f, float f2);

    public static final native void btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_3(long j, btSubSimplexClosestResult btsubsimplexclosestresult, float f);

    public static final native void btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_4(long j, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native long btSubSimplexClosestResult_usedVertices_get(long j, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native void btSubSimplexClosestResult_usedVertices_set(long j, btSubSimplexClosestResult btsubsimplexclosestresult, long j2, btUsageBitfield btusagebitfield);

    public static final native long btSubsimplexConvexCast_SWIGUpcast(long j);

    public static final native long btTetrahedronShapeEx_SWIGUpcast(long j);

    public static final native void btTetrahedronShapeEx_setVertices(long j, btTetrahedronShapeEx bttetrahedronshapeex, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native long btTriangleBuffer_SWIGUpcast(long j);

    public static final native void btTriangleBuffer_clearBuffer(long j, btTriangleBuffer bttrianglebuffer);

    public static final native int btTriangleBuffer_getNumTriangles(long j, btTriangleBuffer bttrianglebuffer);

    public static final native long btTriangleBuffer_getTriangle(long j, btTriangleBuffer bttrianglebuffer, int i);

    public static final native void btTriangleCallback_change_ownership(btTriangleCallback bttrianglecallback, long j, boolean z);

    public static final native void btTriangleCallback_director_connect(btTriangleCallback bttrianglecallback, long j, boolean z, boolean z2);

    public static final native void btTriangleCallback_processTriangle(long j, btTriangleCallback bttrianglecallback, long j2, btVector3 btvector3, int i, int i2);

    public static final native long btTriangleConvexcastCallback_SWIGUpcast(long j);

    public static final native float btTriangleConvexcastCallback_allowedPenetration_get(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback);

    public static final native void btTriangleConvexcastCallback_allowedPenetration_set(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, float f);

    public static final native void btTriangleConvexcastCallback_change_ownership(btTriangleConvexcastCallback bttriangleconvexcastcallback, long j, boolean z);

    public static final native long btTriangleConvexcastCallback_convexShapeFrom_get(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback);

    public static final native void btTriangleConvexcastCallback_convexShapeFrom_set(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, long j2, btTransform bttransform);

    public static final native long btTriangleConvexcastCallback_convexShapeTo_get(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback);

    public static final native void btTriangleConvexcastCallback_convexShapeTo_set(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, long j2, btTransform bttransform);

    public static final native long btTriangleConvexcastCallback_convexShape_get(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback);

    public static final native void btTriangleConvexcastCallback_convexShape_set(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, long j2, btConvexShape btconvexshape);

    public static final native void btTriangleConvexcastCallback_director_connect(btTriangleConvexcastCallback bttriangleconvexcastcallback, long j, boolean z, boolean z2);

    public static final native float btTriangleConvexcastCallback_hitFraction_get(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback);

    public static final native void btTriangleConvexcastCallback_hitFraction_set(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, float f);

    public static final native void btTriangleConvexcastCallback_processTriangle(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, long j2, btVector3 btvector3, int i, int i2);

    public static final native void btTriangleConvexcastCallback_processTriangleSwigExplicitbtTriangleConvexcastCallback(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, long j2, btVector3 btvector3, int i, int i2);

    public static final native float btTriangleConvexcastCallback_reportHit(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, Vector3 vector3, Vector3 vector4, float f, int i, int i2);

    public static final native float btTriangleConvexcastCallback_triangleCollisionMargin_get(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback);

    public static final native void btTriangleConvexcastCallback_triangleCollisionMargin_set(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, float f);

    public static final native long btTriangleConvexcastCallback_triangleToWorld_get(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback);

    public static final native void btTriangleConvexcastCallback_triangleToWorld_set(long j, btTriangleConvexcastCallback bttriangleconvexcastcallback, long j2, btTransform bttransform);

    public static final native long btTriangleIndexVertexArray_SWIGUpcast(long j);

    public static final native long btTriangleIndexVertexArray_getIndexedMeshArray(long j, btTriangleIndexVertexArray bttriangleindexvertexarray);

    public static final native void btTriangleIndexVertexArray_getLockedReadOnlyVertexIndexBase__SWIG_0(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, int i);

    public static final native void btTriangleIndexVertexArray_getLockedReadOnlyVertexIndexBase__SWIG_1(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9);

    public static final native void btTriangleIndexVertexArray_getLockedVertexIndexBase__SWIG_0(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, int i);

    public static final native void btTriangleIndexVertexArray_getLockedVertexIndexBase__SWIG_1(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9);

    public static final native void btTriangleIndexVertexArray_internalAddIndexedMesh__SWIG_0(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, btIndexedMesh btindexedmesh, int i);

    public static final native void btTriangleIndexVertexArray_internalAddIndexedMesh__SWIG_1(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, btIndexedMesh btindexedmesh);

    public static final native void btTriangleIndexVertexArray_operatorDeleteArray__SWIG_0(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2);

    public static final native void btTriangleIndexVertexArray_operatorDeleteArray__SWIG_1(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, long j3);

    public static final native void btTriangleIndexVertexArray_operatorDelete__SWIG_0(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2);

    public static final native void btTriangleIndexVertexArray_operatorDelete__SWIG_1(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, long j3);

    public static final native long btTriangleIndexVertexArray_operatorNewArray__SWIG_0(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2);

    public static final native long btTriangleIndexVertexArray_operatorNewArray__SWIG_1(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, long j3);

    public static final native long btTriangleIndexVertexArray_operatorNew__SWIG_0(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2);

    public static final native long btTriangleIndexVertexArray_operatorNew__SWIG_1(long j, btTriangleIndexVertexArray bttriangleindexvertexarray, long j2, long j3);

    public static final native long btTriangleIndexVertexMaterialArray_SWIGUpcast(long j);

    public static final native void btTriangleIndexVertexMaterialArray_addMaterialProperties__SWIG_0(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, btMaterialProperties btmaterialproperties, int i);

    public static final native void btTriangleIndexVertexMaterialArray_addMaterialProperties__SWIG_1(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, btMaterialProperties btmaterialproperties);

    public static final native void btTriangleIndexVertexMaterialArray_getLockedMaterialBase__SWIG_0(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, int i);

    public static final native void btTriangleIndexVertexMaterialArray_getLockedMaterialBase__SWIG_1(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9);

    public static final native void btTriangleIndexVertexMaterialArray_getLockedReadOnlyMaterialBase__SWIG_0(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, int i);

    public static final native void btTriangleIndexVertexMaterialArray_getLockedReadOnlyMaterialBase__SWIG_1(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9);

    public static final native void btTriangleIndexVertexMaterialArray_operatorDeleteArray__SWIG_0(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2);

    public static final native void btTriangleIndexVertexMaterialArray_operatorDeleteArray__SWIG_1(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, long j3);

    public static final native void btTriangleIndexVertexMaterialArray_operatorDelete__SWIG_0(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2);

    public static final native void btTriangleIndexVertexMaterialArray_operatorDelete__SWIG_1(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, long j3);

    public static final native long btTriangleIndexVertexMaterialArray_operatorNewArray__SWIG_0(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2);

    public static final native long btTriangleIndexVertexMaterialArray_operatorNewArray__SWIG_1(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, long j3);

    public static final native long btTriangleIndexVertexMaterialArray_operatorNew__SWIG_0(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2);

    public static final native long btTriangleIndexVertexMaterialArray_operatorNew__SWIG_1(long j, btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray, long j2, long j3);

    public static final native float btTriangleInfoData_edgeV0V1Angle_get(long j, btTriangleInfoData bttriangleinfodata);

    public static final native void btTriangleInfoData_edgeV0V1Angle_set(long j, btTriangleInfoData bttriangleinfodata, float f);

    public static final native float btTriangleInfoData_edgeV1V2Angle_get(long j, btTriangleInfoData bttriangleinfodata);

    public static final native void btTriangleInfoData_edgeV1V2Angle_set(long j, btTriangleInfoData bttriangleinfodata, float f);

    public static final native float btTriangleInfoData_edgeV2V0Angle_get(long j, btTriangleInfoData bttriangleinfodata);

    public static final native void btTriangleInfoData_edgeV2V0Angle_set(long j, btTriangleInfoData bttriangleinfodata, float f);

    public static final native int btTriangleInfoData_flags_get(long j, btTriangleInfoData bttriangleinfodata);

    public static final native void btTriangleInfoData_flags_set(long j, btTriangleInfoData bttriangleinfodata, int i);

    public static final native float btTriangleInfoMapData_convexEpsilon_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_convexEpsilon_set(long j, btTriangleInfoMapData bttriangleinfomapdata, float f);

    public static final native float btTriangleInfoMapData_edgeDistanceThreshold_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_edgeDistanceThreshold_set(long j, btTriangleInfoMapData bttriangleinfomapdata, float f);

    public static final native float btTriangleInfoMapData_equalVertexThreshold_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_equalVertexThreshold_set(long j, btTriangleInfoMapData bttriangleinfomapdata, float f);

    public static final native IntBuffer btTriangleInfoMapData_hashTablePtr_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_hashTablePtr_set(long j, btTriangleInfoMapData bttriangleinfomapdata, IntBuffer intBuffer);

    public static final native int btTriangleInfoMapData_hashTableSize_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_hashTableSize_set(long j, btTriangleInfoMapData bttriangleinfomapdata, int i);

    public static final native IntBuffer btTriangleInfoMapData_keyArrayPtr_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_keyArrayPtr_set(long j, btTriangleInfoMapData bttriangleinfomapdata, IntBuffer intBuffer);

    public static final native IntBuffer btTriangleInfoMapData_nextPtr_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_nextPtr_set(long j, btTriangleInfoMapData bttriangleinfomapdata, IntBuffer intBuffer);

    public static final native int btTriangleInfoMapData_nextSize_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_nextSize_set(long j, btTriangleInfoMapData bttriangleinfomapdata, int i);

    public static final native int btTriangleInfoMapData_numKeys_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_numKeys_set(long j, btTriangleInfoMapData bttriangleinfomapdata, int i);

    public static final native int btTriangleInfoMapData_numValues_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_numValues_set(long j, btTriangleInfoMapData bttriangleinfomapdata, int i);

    public static final native String btTriangleInfoMapData_padding_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_padding_set(long j, btTriangleInfoMapData bttriangleinfomapdata, String str);

    public static final native float btTriangleInfoMapData_planarEpsilon_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_planarEpsilon_set(long j, btTriangleInfoMapData bttriangleinfomapdata, float f);

    public static final native long btTriangleInfoMapData_valueArrayPtr_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_valueArrayPtr_set(long j, btTriangleInfoMapData bttriangleinfomapdata, long j2, btTriangleInfoData bttriangleinfodata);

    public static final native float btTriangleInfoMapData_zeroAreaThreshold_get(long j, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native void btTriangleInfoMapData_zeroAreaThreshold_set(long j, btTriangleInfoMapData bttriangleinfomapdata, float f);

    public static final native long btTriangleInfoMap_SWIGUpcast(long j);

    public static final native int btTriangleInfoMap_calculateSerializeBufferSize(long j, btTriangleInfoMap bttriangleinfomap);

    public static final native float btTriangleInfoMap_convexEpsilon_get(long j, btTriangleInfoMap bttriangleinfomap);

    public static final native void btTriangleInfoMap_convexEpsilon_set(long j, btTriangleInfoMap bttriangleinfomap, float f);

    public static final native void btTriangleInfoMap_deSerialize(long j, btTriangleInfoMap bttriangleinfomap, long j2, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native float btTriangleInfoMap_edgeDistanceThreshold_get(long j, btTriangleInfoMap bttriangleinfomap);

    public static final native void btTriangleInfoMap_edgeDistanceThreshold_set(long j, btTriangleInfoMap bttriangleinfomap, float f);

    public static final native float btTriangleInfoMap_equalVertexThreshold_get(long j, btTriangleInfoMap bttriangleinfomap);

    public static final native void btTriangleInfoMap_equalVertexThreshold_set(long j, btTriangleInfoMap bttriangleinfomap, float f);

    public static final native float btTriangleInfoMap_maxEdgeAngleThreshold_get(long j, btTriangleInfoMap bttriangleinfomap);

    public static final native void btTriangleInfoMap_maxEdgeAngleThreshold_set(long j, btTriangleInfoMap bttriangleinfomap, float f);

    public static final native float btTriangleInfoMap_planarEpsilon_get(long j, btTriangleInfoMap bttriangleinfomap);

    public static final native void btTriangleInfoMap_planarEpsilon_set(long j, btTriangleInfoMap bttriangleinfomap, float f);

    public static final native String btTriangleInfoMap_serialize(long j, btTriangleInfoMap bttriangleinfomap, long j2, long j3, btSerializer btserializer);

    public static final native float btTriangleInfoMap_zeroAreaThreshold_get(long j, btTriangleInfoMap bttriangleinfomap);

    public static final native void btTriangleInfoMap_zeroAreaThreshold_set(long j, btTriangleInfoMap bttriangleinfomap, float f);

    public static final native float btTriangleInfo_edgeV0V1Angle_get(long j, btTriangleInfo bttriangleinfo);

    public static final native void btTriangleInfo_edgeV0V1Angle_set(long j, btTriangleInfo bttriangleinfo, float f);

    public static final native float btTriangleInfo_edgeV1V2Angle_get(long j, btTriangleInfo bttriangleinfo);

    public static final native void btTriangleInfo_edgeV1V2Angle_set(long j, btTriangleInfo bttriangleinfo, float f);

    public static final native float btTriangleInfo_edgeV2V0Angle_get(long j, btTriangleInfo bttriangleinfo);

    public static final native void btTriangleInfo_edgeV2V0Angle_set(long j, btTriangleInfo bttriangleinfo, float f);

    public static final native int btTriangleInfo_flags_get(long j, btTriangleInfo bttriangleinfo);

    public static final native void btTriangleInfo_flags_set(long j, btTriangleInfo bttriangleinfo, int i);

    public static final native float btTriangleMeshShapeData_collisionMargin_get(long j, btTriangleMeshShapeData bttrianglemeshshapedata);

    public static final native void btTriangleMeshShapeData_collisionMargin_set(long j, btTriangleMeshShapeData bttrianglemeshshapedata, float f);

    public static final native long btTriangleMeshShapeData_collisionShapeData_get(long j, btTriangleMeshShapeData bttrianglemeshshapedata);

    public static final native void btTriangleMeshShapeData_collisionShapeData_set(long j, btTriangleMeshShapeData bttrianglemeshshapedata, long j2, btCollisionShapeData btcollisionshapedata);

    public static final native long btTriangleMeshShapeData_meshInterface_get(long j, btTriangleMeshShapeData bttrianglemeshshapedata);

    public static final native void btTriangleMeshShapeData_meshInterface_set(long j, btTriangleMeshShapeData bttrianglemeshshapedata, long j2, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native String btTriangleMeshShapeData_pad3_get(long j, btTriangleMeshShapeData bttrianglemeshshapedata);

    public static final native void btTriangleMeshShapeData_pad3_set(long j, btTriangleMeshShapeData bttrianglemeshshapedata, String str);

    public static final native long btTriangleMeshShapeData_quantizedDoubleBvh_get(long j, btTriangleMeshShapeData bttrianglemeshshapedata);

    public static final native void btTriangleMeshShapeData_quantizedDoubleBvh_set(long j, btTriangleMeshShapeData bttrianglemeshshapedata, long j2, btQuantizedBvhDoubleData btquantizedbvhdoubledata);

    public static final native long btTriangleMeshShapeData_quantizedFloatBvh_get(long j, btTriangleMeshShapeData bttrianglemeshshapedata);

    public static final native void btTriangleMeshShapeData_quantizedFloatBvh_set(long j, btTriangleMeshShapeData bttrianglemeshshapedata, long j2, btQuantizedBvhFloatData btquantizedbvhfloatdata);

    public static final native long btTriangleMeshShapeData_triangleInfoMap_get(long j, btTriangleMeshShapeData bttrianglemeshshapedata);

    public static final native void btTriangleMeshShapeData_triangleInfoMap_set(long j, btTriangleMeshShapeData bttrianglemeshshapedata, long j2, btTriangleInfoMapData bttriangleinfomapdata);

    public static final native long btTriangleMeshShape_SWIGUpcast(long j);

    public static final native Vector3 btTriangleMeshShape_getLocalAabbMax(long j, btTriangleMeshShape bttrianglemeshshape);

    public static final native Vector3 btTriangleMeshShape_getLocalAabbMin(long j, btTriangleMeshShape bttrianglemeshshape);

    public static final native long btTriangleMeshShape_getMeshInterface(long j, btTriangleMeshShape bttrianglemeshshape);

    public static final native long btTriangleMeshShape_getMeshInterfaceConst(long j, btTriangleMeshShape bttrianglemeshshape);

    public static final native Vector3 btTriangleMeshShape_localGetSupportingVertex(long j, btTriangleMeshShape bttrianglemeshshape, Vector3 vector3);

    public static final native Vector3 btTriangleMeshShape_localGetSupportingVertexWithoutMargin(long j, btTriangleMeshShape bttrianglemeshshape, Vector3 vector3);

    public static final native void btTriangleMeshShape_operatorDeleteArray__SWIG_0(long j, btTriangleMeshShape bttrianglemeshshape, long j2);

    public static final native void btTriangleMeshShape_operatorDeleteArray__SWIG_1(long j, btTriangleMeshShape bttrianglemeshshape, long j2, long j3);

    public static final native void btTriangleMeshShape_operatorDelete__SWIG_0(long j, btTriangleMeshShape bttrianglemeshshape, long j2);

    public static final native void btTriangleMeshShape_operatorDelete__SWIG_1(long j, btTriangleMeshShape bttrianglemeshshape, long j2, long j3);

    public static final native long btTriangleMeshShape_operatorNewArray__SWIG_0(long j, btTriangleMeshShape bttrianglemeshshape, long j2);

    public static final native long btTriangleMeshShape_operatorNewArray__SWIG_1(long j, btTriangleMeshShape bttrianglemeshshape, long j2, long j3);

    public static final native long btTriangleMeshShape_operatorNew__SWIG_0(long j, btTriangleMeshShape bttrianglemeshshape, long j2);

    public static final native long btTriangleMeshShape_operatorNew__SWIG_1(long j, btTriangleMeshShape bttrianglemeshshape, long j2, long j3);

    public static final native void btTriangleMeshShape_recalcLocalAabb(long j, btTriangleMeshShape bttrianglemeshshape);

    public static final native long btTriangleMesh_SWIGUpcast(long j);

    public static final native void btTriangleMesh_addIndex(long j, btTriangleMesh bttrianglemesh, int i);

    public static final native void btTriangleMesh_addTriangleIndices(long j, btTriangleMesh bttrianglemesh, int i, int i2, int i3);

    public static final native void btTriangleMesh_addTriangle__SWIG_0(long j, btTriangleMesh bttrianglemesh, Vector3 vector3, Vector3 vector4, Vector3 vector5, boolean z);

    public static final native void btTriangleMesh_addTriangle__SWIG_1(long j, btTriangleMesh bttrianglemesh, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native int btTriangleMesh_findOrAddVertex(long j, btTriangleMesh bttrianglemesh, Vector3 vector3, boolean z);

    public static final native int btTriangleMesh_getNumTriangles(long j, btTriangleMesh bttrianglemesh);

    public static final native boolean btTriangleMesh_getUse32bitIndices(long j, btTriangleMesh bttrianglemesh);

    public static final native boolean btTriangleMesh_getUse4componentVertices(long j, btTriangleMesh bttrianglemesh);

    public static final native float btTriangleMesh_weldingThreshold_get(long j, btTriangleMesh bttrianglemesh);

    public static final native void btTriangleMesh_weldingThreshold_set(long j, btTriangleMesh bttrianglemesh, float f);

    public static final native long btTriangleRaycastCallback_SWIGUpcast(long j);

    public static final native void btTriangleRaycastCallback_change_ownership(btTriangleRaycastCallback bttriangleraycastcallback, long j, boolean z);

    public static final native void btTriangleRaycastCallback_director_connect(btTriangleRaycastCallback bttriangleraycastcallback, long j, boolean z, boolean z2);

    public static final native long btTriangleRaycastCallback_flags_get(long j, btTriangleRaycastCallback bttriangleraycastcallback);

    public static final native void btTriangleRaycastCallback_flags_set(long j, btTriangleRaycastCallback bttriangleraycastcallback, long j2);

    public static final native long btTriangleRaycastCallback_from_get(long j, btTriangleRaycastCallback bttriangleraycastcallback);

    public static final native void btTriangleRaycastCallback_from_set(long j, btTriangleRaycastCallback bttriangleraycastcallback, long j2, btVector3 btvector3);

    public static final native float btTriangleRaycastCallback_hitFraction_get(long j, btTriangleRaycastCallback bttriangleraycastcallback);

    public static final native void btTriangleRaycastCallback_hitFraction_set(long j, btTriangleRaycastCallback bttriangleraycastcallback, float f);

    public static final native void btTriangleRaycastCallback_processTriangle(long j, btTriangleRaycastCallback bttriangleraycastcallback, long j2, btVector3 btvector3, int i, int i2);

    public static final native void btTriangleRaycastCallback_processTriangleSwigExplicitbtTriangleRaycastCallback(long j, btTriangleRaycastCallback bttriangleraycastcallback, long j2, btVector3 btvector3, int i, int i2);

    public static final native float btTriangleRaycastCallback_reportHit(long j, btTriangleRaycastCallback bttriangleraycastcallback, Vector3 vector3, float f, int i, int i2);

    public static final native long btTriangleRaycastCallback_to_get(long j, btTriangleRaycastCallback bttriangleraycastcallback);

    public static final native void btTriangleRaycastCallback_to_set(long j, btTriangleRaycastCallback bttriangleraycastcallback, long j2, btVector3 btvector3);

    public static final native long btTriangleShapeEx_SWIGUpcast(long j);

    public static final native void btTriangleShapeEx_applyTransform(long j, btTriangleShapeEx bttriangleshapeex, Matrix4 matrix4);

    public static final native void btTriangleShapeEx_buildTriPlane(long j, btTriangleShapeEx bttriangleshapeex, long j2, btVector4 btvector4);

    public static final native boolean btTriangleShapeEx_overlap_test_conservative(long j, btTriangleShapeEx bttriangleshapeex, long j2, btTriangleShapeEx bttriangleshapeex2);

    public static final native long btTriangleShape_SWIGUpcast(long j);

    public static final native void btTriangleShape_calcNormal(long j, btTriangleShape bttriangleshape, Vector3 vector3);

    public static final native void btTriangleShape_getPlaneEquation(long j, btTriangleShape bttriangleshape, int i, Vector3 vector3, Vector3 vector4);

    public static final native Vector3 btTriangleShape_getVertexPtr(long j, btTriangleShape bttriangleshape, int i);

    public static final native Vector3 btTriangleShape_getVertexPtrConst(long j, btTriangleShape bttriangleshape, int i);

    public static final native void btTriangleShape_operatorDeleteArray__SWIG_0(long j, btTriangleShape bttriangleshape, long j2);

    public static final native void btTriangleShape_operatorDeleteArray__SWIG_1(long j, btTriangleShape bttriangleshape, long j2, long j3);

    public static final native void btTriangleShape_operatorDelete__SWIG_0(long j, btTriangleShape bttriangleshape, long j2);

    public static final native void btTriangleShape_operatorDelete__SWIG_1(long j, btTriangleShape bttriangleshape, long j2, long j3);

    public static final native long btTriangleShape_operatorNewArray__SWIG_0(long j, btTriangleShape bttriangleshape, long j2);

    public static final native long btTriangleShape_operatorNewArray__SWIG_1(long j, btTriangleShape bttriangleshape, long j2, long j3);

    public static final native long btTriangleShape_operatorNew__SWIG_0(long j, btTriangleShape bttriangleshape, long j2);

    public static final native long btTriangleShape_operatorNew__SWIG_1(long j, btTriangleShape bttriangleshape, long j2, long j3);

    public static final native long btTriangleShape_vertices1_get(long j, btTriangleShape bttriangleshape);

    public static final native void btTriangleShape_vertices1_set(long j, btTriangleShape bttriangleshape, long j2, btVector3 btvector3);

    public static final native int btTriangle_partId_get(long j, btTriangle bttriangle);

    public static final native void btTriangle_partId_set(long j, btTriangle bttriangle, int i);

    public static final native int btTriangle_triangleIndex_get(long j, btTriangle bttriangle);

    public static final native void btTriangle_triangleIndex_set(long j, btTriangle bttriangle, int i);

    public static final native long btTriangle_vertex0_get(long j, btTriangle bttriangle);

    public static final native void btTriangle_vertex0_set(long j, btTriangle bttriangle, long j2, btVector3 btvector3);

    public static final native long btTriangle_vertex1_get(long j, btTriangle bttriangle);

    public static final native void btTriangle_vertex1_set(long j, btTriangle bttriangle, long j2, btVector3 btvector3);

    public static final native long btTriangle_vertex2_get(long j, btTriangle bttriangle);

    public static final native void btTriangle_vertex2_set(long j, btTriangle bttriangle, long j2, btVector3 btvector3);

    public static final native long btUniformScalingShape_SWIGUpcast(long j);

    public static final native long btUniformScalingShape_getChildShape(long j, btUniformScalingShape btuniformscalingshape);

    public static final native long btUniformScalingShape_getChildShapeConst(long j, btUniformScalingShape btuniformscalingshape);

    public static final native float btUniformScalingShape_getUniformScalingFactor(long j, btUniformScalingShape btuniformscalingshape);

    public static final native void btUniformScalingShape_operatorDeleteArray__SWIG_0(long j, btUniformScalingShape btuniformscalingshape, long j2);

    public static final native void btUniformScalingShape_operatorDeleteArray__SWIG_1(long j, btUniformScalingShape btuniformscalingshape, long j2, long j3);

    public static final native void btUniformScalingShape_operatorDelete__SWIG_0(long j, btUniformScalingShape btuniformscalingshape, long j2);

    public static final native void btUniformScalingShape_operatorDelete__SWIG_1(long j, btUniformScalingShape btuniformscalingshape, long j2, long j3);

    public static final native long btUniformScalingShape_operatorNewArray__SWIG_0(long j, btUniformScalingShape btuniformscalingshape, long j2);

    public static final native long btUniformScalingShape_operatorNewArray__SWIG_1(long j, btUniformScalingShape btuniformscalingshape, long j2, long j3);

    public static final native long btUniformScalingShape_operatorNew__SWIG_0(long j, btUniformScalingShape btuniformscalingshape, long j2);

    public static final native long btUniformScalingShape_operatorNew__SWIG_1(long j, btUniformScalingShape btuniformscalingshape, long j2, long j3);

    public static final native void btUnionFind_Free(long j, btUnionFind btunionfind);

    public static final native void btUnionFind_allocate(long j, btUnionFind btunionfind, int i);

    public static final native int btUnionFind_find__SWIG_0(long j, btUnionFind btunionfind, int i, int i2);

    public static final native int btUnionFind_find__SWIG_1(long j, btUnionFind btunionfind, int i);

    public static final native long btUnionFind_getElement(long j, btUnionFind btunionfind, int i);

    public static final native long btUnionFind_getElementConst(long j, btUnionFind btunionfind, int i);

    public static final native int btUnionFind_getNumElements(long j, btUnionFind btunionfind);

    public static final native boolean btUnionFind_isRoot(long j, btUnionFind btunionfind, int i);

    public static final native void btUnionFind_reset(long j, btUnionFind btunionfind, int i);

    public static final native void btUnionFind_sortIslands(long j, btUnionFind btunionfind);

    public static final native void btUnionFind_unite(long j, btUnionFind btunionfind, int i, int i2);

    public static final native void btUsageBitfield_reset(long j, btUsageBitfield btusagebitfield);

    public static final native int btUsageBitfield_unused1_get(long j, btUsageBitfield btusagebitfield);

    public static final native void btUsageBitfield_unused1_set(long j, btUsageBitfield btusagebitfield, int i);

    public static final native int btUsageBitfield_unused2_get(long j, btUsageBitfield btusagebitfield);

    public static final native void btUsageBitfield_unused2_set(long j, btUsageBitfield btusagebitfield, int i);

    public static final native int btUsageBitfield_unused3_get(long j, btUsageBitfield btusagebitfield);

    public static final native void btUsageBitfield_unused3_set(long j, btUsageBitfield btusagebitfield, int i);

    public static final native int btUsageBitfield_unused4_get(long j, btUsageBitfield btusagebitfield);

    public static final native void btUsageBitfield_unused4_set(long j, btUsageBitfield btusagebitfield, int i);

    public static final native int btUsageBitfield_usedVertexA_get(long j, btUsageBitfield btusagebitfield);

    public static final native void btUsageBitfield_usedVertexA_set(long j, btUsageBitfield btusagebitfield, int i);

    public static final native int btUsageBitfield_usedVertexB_get(long j, btUsageBitfield btusagebitfield);

    public static final native void btUsageBitfield_usedVertexB_set(long j, btUsageBitfield btusagebitfield, int i);

    public static final native int btUsageBitfield_usedVertexC_get(long j, btUsageBitfield btusagebitfield);

    public static final native void btUsageBitfield_usedVertexC_set(long j, btUsageBitfield btusagebitfield, int i);

    public static final native int btUsageBitfield_usedVertexD_get(long j, btUsageBitfield btusagebitfield);

    public static final native void btUsageBitfield_usedVertexD_set(long j, btUsageBitfield btusagebitfield, int i);

    public static final native void btVoronoiSimplexSolver_addVertex(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native void btVoronoiSimplexSolver_backup_closest(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, Vector3 vector3);

    public static final native long btVoronoiSimplexSolver_cachedBC_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_cachedBC_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native long btVoronoiSimplexSolver_cachedP1_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_cachedP1_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btVector3 btvector3);

    public static final native long btVoronoiSimplexSolver_cachedP2_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_cachedP2_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btVector3 btvector3);

    public static final native long btVoronoiSimplexSolver_cachedV_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_cachedV_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btVector3 btvector3);

    public static final native boolean btVoronoiSimplexSolver_cachedValidClosest_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_cachedValidClosest_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, boolean z);

    public static final native boolean btVoronoiSimplexSolver_closest(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, Vector3 vector3);

    public static final native boolean btVoronoiSimplexSolver_closestPtPointTetrahedron(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, long j2, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native boolean btVoronoiSimplexSolver_closestPtPointTriangle(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, long j2, btSubSimplexClosestResult btsubsimplexclosestresult);

    public static final native void btVoronoiSimplexSolver_compute_points(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, Vector3 vector3, Vector3 vector4);

    public static final native boolean btVoronoiSimplexSolver_emptySimplex(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native float btVoronoiSimplexSolver_equalVertexThreshold_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_equalVertexThreshold_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, float f);

    public static final native boolean btVoronoiSimplexSolver_fullSimplex(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native int btVoronoiSimplexSolver_getSimplex(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btVector3 btvector3, long j3, btVector3 btvector4, long j4, btVector3 btvector5);

    public static final native boolean btVoronoiSimplexSolver_inSimplex(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, Vector3 vector3);

    public static final native long btVoronoiSimplexSolver_lastW_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_lastW_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btVector3 btvector3);

    public static final native int btVoronoiSimplexSolver_m_numVerticesVar(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native float btVoronoiSimplexSolver_maxVertex(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native boolean btVoronoiSimplexSolver_needsUpdate_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_needsUpdate_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, boolean z);

    public static final native int btVoronoiSimplexSolver_numVertices_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_numVertices_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, int i);

    public static final native void btVoronoiSimplexSolver_operatorDeleteArray__SWIG_0(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2);

    public static final native void btVoronoiSimplexSolver_operatorDeleteArray__SWIG_1(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, long j3);

    public static final native void btVoronoiSimplexSolver_operatorDelete__SWIG_0(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2);

    public static final native void btVoronoiSimplexSolver_operatorDelete__SWIG_1(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, long j3);

    public static final native long btVoronoiSimplexSolver_operatorNewArray__SWIG_0(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2);

    public static final native long btVoronoiSimplexSolver_operatorNewArray__SWIG_1(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, long j3);

    public static final native long btVoronoiSimplexSolver_operatorNew__SWIG_0(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2);

    public static final native long btVoronoiSimplexSolver_operatorNew__SWIG_1(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, long j3);

    public static final native int btVoronoiSimplexSolver_pointOutsideOfPlane(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7);

    public static final native void btVoronoiSimplexSolver_reduceVertices(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btUsageBitfield btusagebitfield);

    public static final native void btVoronoiSimplexSolver_removeVertex(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, int i);

    public static final native void btVoronoiSimplexSolver_reset(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native long btVoronoiSimplexSolver_simplexPointsP_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_simplexPointsP_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btVector3 btvector3);

    public static final native long btVoronoiSimplexSolver_simplexPointsQ_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_simplexPointsQ_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btVector3 btvector3);

    public static final native long btVoronoiSimplexSolver_simplexVectorW_get(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void btVoronoiSimplexSolver_simplexVectorW_set(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btVector3 btvector3);

    public static final native boolean btVoronoiSimplexSolver_updateClosestVectorAndPoints(long j, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native void bt_calc_quantization_parameters(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, float f);

    public static final native void bt_closest_point_on_segment(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native float bt_distance_point_plane(long j, btVector4 btvector4, Vector3 vector3);

    public static final native void bt_edge_plane(Vector3 vector3, Vector3 vector4, Vector3 vector5, long j, btVector4 btvector4);

    public static final native int bt_line_plane_collision(long j, btVector4 btvector4, Vector3 vector3, Vector3 vector4, Vector3 vector5, long j2, float f, float f2);

    public static final native float bt_mat3_dot_col(Matrix3 matrix3, Vector3 vector3, int i);

    public static final native int bt_plane_clip_polygon(long j, btVector4 btvector4, long j2, btVector3 btvector3, int i, long j3, btVector3 btvector5);

    public static final native void bt_plane_clip_polygon_collect(Vector3 vector3, Vector3 vector4, float f, float f2, long j, btVector3 btvector3, long j2);

    public static final native int bt_plane_clip_triangle(long j, btVector4 btvector4, Vector3 vector3, Vector3 vector4, Vector3 vector5, long j2, btVector3 btvector3);

    public static final native void bt_quantize_clamp(IntBuffer intBuffer, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native void bt_segment_collision(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8);

    public static final native Vector3 bt_unquantize(IntBuffer intBuffer, Vector3 vector3, Vector3 vector4);

    public static final native void bt_vec_blend(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f);

    public static final native void delete_AllHitsRayResultCallback(long j);

    public static final native void delete_BT_BOX_BOX_TRANSFORM_CACHE(long j);

    public static final native void delete_BT_QUANTIZED_BVH_NODE(long j);

    public static final native void delete_ClosestConvexResultCallback(long j);

    public static final native void delete_ClosestNotMeConvexResultCallback(long j);

    public static final native void delete_ClosestNotMeRayResultCallback(long j);

    public static final native void delete_ClosestRayResultCallback(long j);

    public static final native void delete_CollisionObjectWrapper(long j);

    public static final native void delete_ContactCache(long j);

    public static final native void delete_ContactListener(long j);

    public static final native void delete_ContactResultCallback(long j);

    public static final native void delete_ConvexResultCallback(long j);

    public static final native void delete_CustomCollisionDispatcher(long j);

    public static final native void delete_DISTANCE_PLANE_3D_FUNC(long j);

    public static final native void delete_GIM_AABB(long j);

    public static final native void delete_GIM_BOX_BOX_TRANSFORM_CACHE(long j);

    public static final native void delete_GIM_BVH_DATA(long j);

    public static final native void delete_GIM_BVH_DATA_ARRAY(long j);

    public static final native void delete_GIM_BVH_TREE_NODE(long j);

    public static final native void delete_GIM_BVH_TREE_NODE_ARRAY(long j);

    public static final native void delete_GIM_CONTACT(long j);

    public static final native void delete_GIM_HASH_NODE_CMP_KEY_MACRO(long j);

    public static final native void delete_GIM_HASH_NODE_CMP_MACRO(long j);

    public static final native void delete_GIM_HASH_NODE_GET_KEY(long j);

    public static final native void delete_GIM_PAIR(long j);

    public static final native void delete_GIM_QUANTIZED_BVH_NODE_ARRAY(long j);

    public static final native void delete_GIM_RSORT_TOKEN(long j);

    public static final native void delete_GIM_RSORT_TOKEN_COMPARATOR(long j);

    public static final native void delete_GIM_TRIANGLE(long j);

    public static final native void delete_GIM_TRIANGLE_CONTACT(long j);

    public static final native void delete_GIM_TRIANGLE_CONTACT_DATA(long j);

    public static final native void delete_GdxCollisionObjectBridge(long j);

    public static final native void delete_ICollide(long j);

    public static final native void delete_LocalConvexResult(long j);

    public static final native void delete_LocalRayResult(long j);

    public static final native void delete_LocalShapeInfo(long j);

    public static final native void delete_MyCallback(long j);

    public static final native void delete_MyInternalTriangleIndexCallback(long j);

    public static final native void delete_RayResultCallback(long j);

    public static final native void delete_SphereTriangleDetector(long j);

    public static final native void delete__btMprSimplex_t(long j);

    public static final native void delete__btMprSupport_t(long j);

    public static final native void delete_bt32BitAxisSweep3(long j);

    public static final native void delete_btAABB(long j);

    public static final native void delete_btActivatingCollisionAlgorithm(long j);

    public static final native void delete_btAxisSweep3(long j);

    public static final native void delete_btAxisSweep3InternalInt(long j);

    public static final native void delete_btAxisSweep3InternalInt_Edge(long j);

    public static final native void delete_btAxisSweep3InternalInt_Handle(long j);

    public static final native void delete_btAxisSweep3InternalShort(long j);

    public static final native void delete_btAxisSweep3InternalShort_Edge(long j);

    public static final native void delete_btAxisSweep3InternalShort_Handle(long j);

    public static final native void delete_btBU_Simplex1to4(long j);

    public static final native void delete_btBox2dBox2dCollisionAlgorithm(long j);

    public static final native void delete_btBox2dBox2dCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btBox2dShape(long j);

    public static final native void delete_btBoxBoxCollisionAlgorithm(long j);

    public static final native void delete_btBoxBoxCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btBoxBoxDetector(long j);

    public static final native void delete_btBoxShape(long j);

    public static final native void delete_btBroadphaseAabbCallback(long j);

    public static final native void delete_btBroadphaseInterface(long j);

    public static final native void delete_btBroadphasePair(long j);

    public static final native void delete_btBroadphasePairArray(long j);

    public static final native void delete_btBroadphasePairSortPredicate(long j);

    public static final native void delete_btBroadphaseProxy(long j);

    public static final native void delete_btBroadphaseRayCallback(long j);

    public static final native void delete_btBvhSubtreeInfo(long j);

    public static final native void delete_btBvhSubtreeInfoData(long j);

    public static final native void delete_btBvhTree(long j);

    public static final native void delete_btBvhTriangleMeshShape(long j);

    public static final native void delete_btCapsuleShape(long j);

    public static final native void delete_btCapsuleShapeData(long j);

    public static final native void delete_btCapsuleShapeX(long j);

    public static final native void delete_btCapsuleShapeZ(long j);

    public static final native void delete_btCharIndexTripletData(long j);

    public static final native void delete_btCollisionAlgorithm(long j);

    public static final native void delete_btCollisionAlgorithmConstructionInfo(long j);

    public static final native void delete_btCollisionAlgorithmCreateFunc(long j);

    public static final native void delete_btCollisionConfiguration(long j);

    public static final native void delete_btCollisionDispatcher(long j);

    public static final native void delete_btCollisionDispatcherMt(long j);

    public static final native void delete_btCollisionObject(long j);

    public static final native void delete_btCollisionObjectArray(long j);

    public static final native void delete_btCollisionObjectArray_less(long j);

    public static final native void delete_btCollisionObjectConstArray(long j);

    public static final native void delete_btCollisionObjectConstArray_less(long j);

    public static final native void delete_btCollisionObjectDoubleData(long j);

    public static final native void delete_btCollisionObjectFloatData(long j);

    public static final native void delete_btCollisionShape(long j);

    public static final native void delete_btCollisionShapeData(long j);

    public static final native void delete_btCollisionWorld(long j);

    public static final native void delete_btCollisionWorldImporter(long j);

    public static final native void delete_btCompoundCollisionAlgorithm(long j);

    public static final native void delete_btCompoundCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btCompoundCollisionAlgorithm_SwappedCreateFunc(long j);

    public static final native void delete_btCompoundCompoundCollisionAlgorithm(long j);

    public static final native void delete_btCompoundCompoundCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btCompoundCompoundCollisionAlgorithm_SwappedCreateFunc(long j);

    public static final native void delete_btCompoundFromGimpactShape(long j);

    public static final native void delete_btCompoundShape(long j);

    public static final native void delete_btCompoundShapeChild(long j);

    public static final native void delete_btCompoundShapeChildData(long j);

    public static final native void delete_btCompoundShapeData(long j);

    public static final native void delete_btConcaveShape(long j);

    public static final native void delete_btConeShape(long j);

    public static final native void delete_btConeShapeData(long j);

    public static final native void delete_btConeShapeX(long j);

    public static final native void delete_btConeShapeZ(long j);

    public static final native void delete_btConstraintRow(long j);

    public static final native void delete_btContactArray(long j);

    public static final native void delete_btContinuousConvexCollision(long j);

    public static final native void delete_btConvex2dConvex2dAlgorithm(long j);

    public static final native void delete_btConvex2dConvex2dAlgorithm_CreateFunc(long j);

    public static final native void delete_btConvex2dShape(long j);

    public static final native void delete_btConvexCast(long j);

    public static final native void delete_btConvexCast_CastResult(long j);

    public static final native void delete_btConvexConcaveCollisionAlgorithm(long j);

    public static final native void delete_btConvexConcaveCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btConvexConcaveCollisionAlgorithm_SwappedCreateFunc(long j);

    public static final native void delete_btConvexConvexAlgorithm(long j);

    public static final native void delete_btConvexConvexAlgorithm_CreateFunc(long j);

    public static final native void delete_btConvexHullShape(long j);

    public static final native void delete_btConvexHullShapeData(long j);

    public static final native void delete_btConvexInternalAabbCachingShape(long j);

    public static final native void delete_btConvexInternalShape(long j);

    public static final native void delete_btConvexInternalShapeData(long j);

    public static final native void delete_btConvexPenetrationDepthSolver(long j);

    public static final native void delete_btConvexPlaneCollisionAlgorithm(long j);

    public static final native void delete_btConvexPlaneCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btConvexPointCloudShape(long j);

    public static final native void delete_btConvexPolyhedron(long j);

    public static final native void delete_btConvexShape(long j);

    public static final native void delete_btConvexTriangleCallback(long j);

    public static final native void delete_btConvexTriangleMeshShape(long j);

    public static final native void delete_btCylinderShape(long j);

    public static final native void delete_btCylinderShapeData(long j);

    public static final native void delete_btCylinderShapeX(long j);

    public static final native void delete_btCylinderShapeZ(long j);

    public static final native void delete_btDbvt(long j);

    public static final native void delete_btDbvtAabbMm(long j);

    public static final native void delete_btDbvtBroadphase(long j);

    public static final native void delete_btDbvtNode(long j);

    public static final native void delete_btDbvtProxy(long j);

    public static final native void delete_btDbvt_IClone(long j);

    public static final native void delete_btDbvt_IWriter(long j);

    public static final native void delete_btDbvt_sStkCLN(long j);

    public static final native void delete_btDbvt_sStkNN(long j);

    public static final native void delete_btDbvt_sStkNP(long j);

    public static final native void delete_btDbvt_sStkNPS(long j);

    public static final native void delete_btDefaultCollisionConfiguration(long j);

    public static final native void delete_btDefaultCollisionConstructionInfo(long j);

    public static final native void delete_btDiscreteCollisionDetectorInterface(long j);

    public static final native void delete_btDiscreteCollisionDetectorInterface_ClosestPointInput(long j);

    public static final native void delete_btDiscreteCollisionDetectorInterface_Result(long j);

    public static final native void delete_btDispatcher(long j);

    public static final native void delete_btDispatcherInfo(long j);

    public static final native void delete_btElement(long j);

    public static final native void delete_btEmptyAlgorithm(long j);

    public static final native void delete_btEmptyAlgorithm_CreateFunc(long j);

    public static final native void delete_btEmptyShape(long j);

    public static final native void delete_btFace(long j);

    public static final native void delete_btGImpactBvh(long j);

    public static final native void delete_btGImpactCollisionAlgorithm(long j);

    public static final native void delete_btGImpactCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btGImpactCompoundShape(long j);

    public static final native void delete_btGImpactCompoundShape_CompoundPrimitiveManager(long j);

    public static final native void delete_btGImpactMeshShape(long j);

    public static final native void delete_btGImpactMeshShapeData(long j);

    public static final native void delete_btGImpactMeshShapePart(long j);

    public static final native void delete_btGImpactMeshShapePart_TrimeshPrimitiveManager(long j);

    public static final native void delete_btGImpactQuantizedBvh(long j);

    public static final native void delete_btGImpactShapeInterface(long j);

    public static final native void delete_btGenericMemoryPool(long j);

    public static final native void delete_btGenericPoolAllocator(long j);

    public static final native void delete_btGhostObject(long j);

    public static final native void delete_btGhostPairCallback(long j);

    public static final native void delete_btGimBvhDataArray(long j);

    public static final native void delete_btGimBvhDataArray_less(long j);

    public static final native void delete_btGimBvhTreeNodeArray(long j);

    public static final native void delete_btGimBvhTreeNodeArray_less(long j);

    public static final native void delete_btGimContactArray(long j);

    public static final native void delete_btGimContactArray_less(long j);

    public static final native void delete_btGimPairArray(long j);

    public static final native void delete_btGimPairArray_less(long j);

    public static final native void delete_btGimQuantizedBvhNodeArray(long j);

    public static final native void delete_btGimQuantizedBvhNodeArray_less(long j);

    public static final native void delete_btGjkCollisionDescription(long j);

    public static final native void delete_btGjkConvexCast(long j);

    public static final native void delete_btGjkEpaPenetrationDepthSolver(long j);

    public static final native void delete_btGjkEpaSolver2(long j);

    public static final native void delete_btGjkEpaSolver2_sResults(long j);

    public static final native void delete_btGjkEpaSolver3(long j);

    public static final native void delete_btGjkEpaSolver3_sResults(long j);

    public static final native void delete_btGjkPairDetector(long j);

    public static final native void delete_btHashMapInternalShortBtHashIntBtTriangleInfo(long j);

    public static final native void delete_btHashedOverlappingPairCache(long j);

    public static final native void delete_btHashedSimplePairCache(long j);

    public static final native void delete_btHeightfieldTerrainShape(long j);

    public static final native void delete_btIndexedMesh(long j);

    public static final native void delete_btIntIndexData(long j);

    public static final native void delete_btInternalTriangleIndexCallback(long j);

    public static final native void delete_btManifoldPoint(long j);

    public static final native void delete_btManifoldResult(long j);

    public static final native void delete_btMaterial(long j);

    public static final native void delete_btMaterialProperties(long j);

    public static final native void delete_btMeshPartData(long j);

    public static final native void delete_btMinkowskiPenetrationDepthSolver(long j);

    public static final native void delete_btMinkowskiSumShape(long j);

    public static final native void delete_btMprCollisionDescription(long j);

    public static final native void delete_btMprDistanceInfo(long j);

    public static final native void delete_btMultiSphereShape(long j);

    public static final native void delete_btMultiSphereShapeData(long j);

    public static final native void delete_btMultimaterialTriangleMeshShape(long j);

    public static final native void delete_btNodeOverlapCallback(long j);

    public static final native void delete_btNullPairCache(long j);

    public static final native void delete_btOptimizedBvh(long j);

    public static final native void delete_btOptimizedBvhNode(long j);

    public static final native void delete_btOptimizedBvhNodeDoubleData(long j);

    public static final native void delete_btOptimizedBvhNodeFloatData(long j);

    public static final native void delete_btOverlapCallback(long j);

    public static final native void delete_btOverlapFilterCallback(long j);

    public static final native void delete_btOverlappingPairCache(long j);

    public static final native void delete_btOverlappingPairCallback(long j);

    public static final native void delete_btPairCachingGhostObject(long j);

    public static final native void delete_btPairSet(long j);

    public static final native void delete_btPersistentManifold(long j);

    public static final native void delete_btPersistentManifoldArray(long j);

    public static final native void delete_btPersistentManifoldArray_less(long j);

    public static final native void delete_btPointCollector(long j);

    public static final native void delete_btPolyhedralContactClipping(long j);

    public static final native void delete_btPolyhedralConvexAabbCachingShape(long j);

    public static final native void delete_btPolyhedralConvexShape(long j);

    public static final native void delete_btPositionAndRadius(long j);

    public static final native void delete_btPrimitiveManagerBase(long j);

    public static final native void delete_btPrimitiveTriangle(long j);

    public static final native void delete_btQuantizedBvh(long j);

    public static final native void delete_btQuantizedBvhDoubleData(long j);

    public static final native void delete_btQuantizedBvhFloatData(long j);

    public static final native void delete_btQuantizedBvhNode(long j);

    public static final native void delete_btQuantizedBvhNodeData(long j);

    public static final native void delete_btQuantizedBvhTree(long j);

    public static final native void delete_btScaledBvhTriangleMeshShape(long j);

    public static final native void delete_btScaledTriangleMeshShapeData(long j);

    public static final native void delete_btShapeHull(long j);

    public static final native void delete_btShortIntIndexData(long j);

    public static final native void delete_btShortIntIndexTripletData(long j);

    public static final native void delete_btSimpleBroadphase(long j);

    public static final native void delete_btSimpleBroadphaseProxy(long j);

    public static final native void delete_btSimplePair(long j);

    public static final native void delete_btSimulationIslandManager(long j);

    public static final native void delete_btSimulationIslandManager_IslandCallback(long j);

    public static final native void delete_btSortedOverlappingPairCache(long j);

    public static final native void delete_btSphereBoxCollisionAlgorithm(long j);

    public static final native void delete_btSphereBoxCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btSphereShape(long j);

    public static final native void delete_btSphereSphereCollisionAlgorithm(long j);

    public static final native void delete_btSphereSphereCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btSphereTriangleCollisionAlgorithm(long j);

    public static final native void delete_btSphereTriangleCollisionAlgorithm_CreateFunc(long j);

    public static final native void delete_btStaticPlaneShape(long j);

    public static final native void delete_btStaticPlaneShapeData(long j);

    public static final native void delete_btStorageResult(long j);

    public static final native void delete_btStridingMeshInterface(long j);

    public static final native void delete_btStridingMeshInterfaceData(long j);

    public static final native void delete_btSubSimplexClosestResult(long j);

    public static final native void delete_btSubsimplexConvexCast(long j);

    public static final native void delete_btTetrahedronShapeEx(long j);

    public static final native void delete_btTriangle(long j);

    public static final native void delete_btTriangleBuffer(long j);

    public static final native void delete_btTriangleCallback(long j);

    public static final native void delete_btTriangleConvexcastCallback(long j);

    public static final native void delete_btTriangleIndexVertexArray(long j);

    public static final native void delete_btTriangleIndexVertexMaterialArray(long j);

    public static final native void delete_btTriangleInfo(long j);

    public static final native void delete_btTriangleInfoData(long j);

    public static final native void delete_btTriangleInfoMap(long j);

    public static final native void delete_btTriangleInfoMapData(long j);

    public static final native void delete_btTriangleMesh(long j);

    public static final native void delete_btTriangleMeshShape(long j);

    public static final native void delete_btTriangleMeshShapeData(long j);

    public static final native void delete_btTriangleRaycastCallback(long j);

    public static final native void delete_btTriangleShape(long j);

    public static final native void delete_btTriangleShapeEx(long j);

    public static final native void delete_btUniformScalingShape(long j);

    public static final native void delete_btUnionFind(long j);

    public static final native void delete_btUsageBitfield(long j);

    public static final native void delete_btVoronoiSimplexSolver(long j);

    public static final native void delete_copy_elements_func(long j);

    public static final native void delete_gim_bitset(long j);

    public static final native void delete_gim_contact_array(long j);

    public static final native void delete_gim_contact_array_internal(long j);

    public static final native void delete_integer_comparator(long j);

    public static final native void delete_less_comparator(long j);

    public static final native void delete_memcopy_elements_func(long j);

    public static final native void delete_uint_key_func(long j);

    public static final native int gAddedPairs_get();

    public static final native void gAddedPairs_set(int i);

    public static final native int gAddedSimplePairs_get();

    public static final native void gAddedSimplePairs_set(int i);

    public static final native long gCompoundChildShapePairCallback_get();

    public static final native void gCompoundChildShapePairCallback_set(long j);

    public static final native long gContactAddedCallback_get();

    public static final native void gContactAddedCallback_set(long j);

    public static final native float gContactBreakingThreshold_get();

    public static final native void gContactBreakingThreshold_set(float f);

    public static final native int gFindPairs_get();

    public static final native void gFindPairs_set(int i);

    public static final native int gFindSimplePairs_get();

    public static final native void gFindSimplePairs_set(int i);

    public static final native int gOverlappingPairs_get();

    public static final native void gOverlappingPairs_set(int i);

    public static final native int gOverlappingSimplePairs_get();

    public static final native void gOverlappingSimplePairs_set(int i);

    public static final native int gRemovePairs_get();

    public static final native void gRemovePairs_set(int i);

    public static final native int gRemoveSimplePairs_get();

    public static final native void gRemoveSimplePairs_set(int i);

    public static final native boolean gdxCheckFilter__SWIG_0(int i, int i2);

    public static final native boolean gdxCheckFilter__SWIG_1(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2);

    public static final native long gim_alloc(long j);

    public static final native long gim_alloca(long j);

    public static final native void gim_bitset_clear(long j, gim_bitset gim_bitsetVar, long j2);

    public static final native void gim_bitset_clear_all(long j, gim_bitset gim_bitsetVar);

    public static final native long gim_bitset_container_get(long j, gim_bitset gim_bitsetVar);

    public static final native void gim_bitset_container_set(long j, gim_bitset gim_bitsetVar, long j2);

    public static final native char gim_bitset_get(long j, gim_bitset gim_bitsetVar, long j2);

    public static final native boolean gim_bitset_resize(long j, gim_bitset gim_bitsetVar, long j2);

    public static final native void gim_bitset_set(long j, gim_bitset gim_bitsetVar, long j2);

    public static final native void gim_bitset_set_all(long j, gim_bitset gim_bitsetVar);

    public static final native long gim_bitset_size(long j, gim_bitset gim_bitsetVar);

    public static final native long gim_contact_array_SWIGUpcast(long j);

    public static final native long gim_contact_array_internal_allocated_size_get(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_allocated_size_set(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_at(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_at_const(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_back(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native long gim_contact_array_internal_back_const(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_clear(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_clear_memory(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_clear_range(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_data_get(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_data_set(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2, GIM_CONTACT gim_contact);

    public static final native void gim_contact_array_internal_destroyData(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_erase(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native void gim_contact_array_internal_erase_sorted(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native void gim_contact_array_internal_erase_sorted_mem(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_front(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native long gim_contact_array_internal_front_const(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native long gim_contact_array_internal_get_pointer_at(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_get_pointer_at_const(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native boolean gim_contact_array_internal_growingCheck(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_insert(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2, GIM_CONTACT gim_contact, long j3);

    public static final native void gim_contact_array_internal_insert_mem(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_max_size(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native long gim_contact_array_internal_operatorSubscript(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_operatorSubscriptConst(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_pointer(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native long gim_contact_array_internal_pointer_const(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_pop_back(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_pop_back_mem(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_push_back(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2, GIM_CONTACT gim_contact);

    public static final native void gim_contact_array_internal_push_back_mem(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_push_back_memcpy(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2, GIM_CONTACT gim_contact);

    public static final native void gim_contact_array_internal_refit(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native boolean gim_contact_array_internal_reserve(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native boolean gim_contact_array_internal_resizeData(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native void gim_contact_array_internal_resize__SWIG_0(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2, boolean z, long j3, GIM_CONTACT gim_contact);

    public static final native void gim_contact_array_internal_resize__SWIG_1(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2, boolean z);

    public static final native void gim_contact_array_internal_resize__SWIG_2(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native long gim_contact_array_internal_sizeVal(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native long gim_contact_array_internal_size_get(long j, gim_contact_array_internal gim_contact_array_internalVar);

    public static final native void gim_contact_array_internal_size_set(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2);

    public static final native void gim_contact_array_internal_swap(long j, gim_contact_array_internal gim_contact_array_internalVar, long j2, long j3);

    public static final native void gim_contact_array_merge_contacts__SWIG_0(long j, gim_contact_array gim_contact_arrayVar, long j2, gim_contact_array gim_contact_arrayVar2, boolean z);

    public static final native void gim_contact_array_merge_contacts__SWIG_1(long j, gim_contact_array gim_contact_arrayVar, long j2, gim_contact_array gim_contact_arrayVar2);

    public static final native void gim_contact_array_merge_contacts_unique(long j, gim_contact_array gim_contact_arrayVar, long j2, gim_contact_array gim_contact_arrayVar2);

    public static final native void gim_contact_array_push_contact(long j, gim_contact_array gim_contact_arrayVar, Vector3 vector3, Vector3 vector4, long j2, long j3, long j4);

    public static final native void gim_contact_array_push_triangle_contacts(long j, gim_contact_array gim_contact_arrayVar, long j2, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data, long j3, long j4);

    public static final native void gim_free(long j);

    public static final native long gim_get_alloc_handler();

    public static final native long gim_get_alloca_handler();

    public static final native long gim_get_free_handler();

    public static final native Vector3 gim_get_point_inertia(Vector3 vector3, float f);

    public static final native long gim_get_realloc_handler();

    public static final native Vector3 gim_inertia_add_transformed(Vector3 vector3, Vector3 vector4, Matrix4 matrix4);

    public static final native float gim_inv_sqrt(float f);

    public static final native long gim_next_prime(long j);

    public static final native long gim_prime_list_get();

    public static final native void gim_radix_sort_rtokens(long j, GIM_RSORT_TOKEN gim_rsort_token, long j2, GIM_RSORT_TOKEN gim_rsort_token2, long j3);

    public static final native long gim_realloc(long j, long j2, long j3);

    public static final native void gim_set_alloc_handler(long j);

    public static final native void gim_set_alloca_handler(long j);

    public static final native void gim_set_free_handler(long j);

    public static final native void gim_set_realloc_handler(long j);

    public static final native void gim_simd_memcpy(long j, long j2, long j3);

    public static final native float gim_sqrt(float f);

    public static final native long new_AllHitsRayResultCallback(Vector3 vector3, Vector3 vector4);

    public static final native long new_BT_BOX_BOX_TRANSFORM_CACHE();

    public static final native long new_BT_QUANTIZED_BVH_NODE();

    public static final native long new_ClosestConvexResultCallback(Vector3 vector3, Vector3 vector4);

    public static final native long new_ClosestNotMeConvexResultCallback(long j, btCollisionObject btcollisionobject, Vector3 vector3, Vector3 vector4);

    public static final native long new_ClosestNotMeRayResultCallback(long j, btCollisionObject btcollisionobject);

    public static final native long new_ClosestRayResultCallback(Vector3 vector3, Vector3 vector4);

    public static final native long new_CollisionObjectWrapper__SWIG_0(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionShape btcollisionshape, long j3, btCollisionObject btcollisionobject, Matrix4 matrix4, int i, int i2);

    public static final native long new_CollisionObjectWrapper__SWIG_1(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionShape btcollisionshape, long j3, btCollisionObject btcollisionobject, Matrix4 matrix4, int i);

    public static final native long new_CollisionObjectWrapper__SWIG_10(long j, btCollisionObject btcollisionobject, int i);

    public static final native long new_CollisionObjectWrapper__SWIG_11(long j, btCollisionObject btcollisionobject);

    public static final native long new_CollisionObjectWrapper__SWIG_2(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionShape btcollisionshape, long j3, btCollisionObject btcollisionobject, Matrix4 matrix4);

    public static final native long new_CollisionObjectWrapper__SWIG_3(long j, btCollisionShape btcollisionshape, long j2, btCollisionObject btcollisionobject, Matrix4 matrix4, int i, int i2);

    public static final native long new_CollisionObjectWrapper__SWIG_4(long j, btCollisionShape btcollisionshape, long j2, btCollisionObject btcollisionobject, Matrix4 matrix4, int i);

    public static final native long new_CollisionObjectWrapper__SWIG_5(long j, btCollisionShape btcollisionshape, long j2, btCollisionObject btcollisionobject, Matrix4 matrix4);

    public static final native long new_CollisionObjectWrapper__SWIG_6(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionObject btcollisionobject, int i, int i2);

    public static final native long new_CollisionObjectWrapper__SWIG_7(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionObject btcollisionobject, int i);

    public static final native long new_CollisionObjectWrapper__SWIG_8(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionObject btcollisionobject);

    public static final native long new_CollisionObjectWrapper__SWIG_9(long j, btCollisionObject btcollisionobject, int i, int i2);

    public static final native long new_ContactCache(boolean z);

    public static final native long new_ContactListener(boolean z);

    public static final native long new_ContactResultCallback();

    public static final native long new_ConvexResultCallback();

    public static final native long new_CustomCollisionDispatcher(long j, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_DISTANCE_PLANE_3D_FUNC();

    public static final native long new_GIM_AABB__SWIG_0();

    public static final native long new_GIM_AABB__SWIG_1(Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native long new_GIM_AABB__SWIG_2(Vector3 vector3, Vector3 vector4, Vector3 vector5, long j);

    public static final native long new_GIM_AABB__SWIG_3(long j, GIM_AABB gim_aabb);

    public static final native long new_GIM_AABB__SWIG_4(long j, GIM_AABB gim_aabb, float f);

    public static final native long new_GIM_BOX_BOX_TRANSFORM_CACHE__SWIG_0();

    public static final native long new_GIM_BOX_BOX_TRANSFORM_CACHE__SWIG_1(long j);

    public static final native long new_GIM_BVH_DATA();

    public static final native long new_GIM_BVH_DATA_ARRAY();

    public static final native long new_GIM_BVH_TREE_NODE();

    public static final native long new_GIM_BVH_TREE_NODE_ARRAY();

    public static final native long new_GIM_CONTACT__SWIG_0();

    public static final native long new_GIM_CONTACT__SWIG_1(long j, GIM_CONTACT gim_contact);

    public static final native long new_GIM_CONTACT__SWIG_2(Vector3 vector3, Vector3 vector4, float f, int i, int i2);

    public static final native long new_GIM_HASH_NODE_CMP_KEY_MACRO();

    public static final native long new_GIM_HASH_NODE_CMP_MACRO();

    public static final native long new_GIM_HASH_NODE_GET_KEY();

    public static final native long new_GIM_PAIR__SWIG_0();

    public static final native long new_GIM_PAIR__SWIG_1(long j, GIM_PAIR gim_pair);

    public static final native long new_GIM_PAIR__SWIG_2(int i, int i2);

    public static final native long new_GIM_QUANTIZED_BVH_NODE_ARRAY();

    public static final native long new_GIM_RSORT_TOKEN_COMPARATOR();

    public static final native long new_GIM_RSORT_TOKEN__SWIG_0();

    public static final native long new_GIM_RSORT_TOKEN__SWIG_1(long j, GIM_RSORT_TOKEN gim_rsort_token);

    public static final native long new_GIM_TRIANGLE();

    public static final native long new_GIM_TRIANGLE_CONTACT_DATA__SWIG_0();

    public static final native long new_GIM_TRIANGLE_CONTACT_DATA__SWIG_1(long j, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data);

    public static final native long new_GIM_TRIANGLE_CONTACT__SWIG_0();

    public static final native long new_GIM_TRIANGLE_CONTACT__SWIG_1(long j, GIM_TRIANGLE_CONTACT gim_triangle_contact);

    public static final native long new_GdxCollisionObjectBridge();

    public static final native long new_ICollide();

    public static final native long new_LocalConvexResult(long j, btCollisionObject btcollisionobject, long j2, LocalShapeInfo localShapeInfo, Vector3 vector3, Vector3 vector4, float f);

    public static final native long new_LocalRayResult(long j, btCollisionObject btcollisionobject, long j2, LocalShapeInfo localShapeInfo, Vector3 vector3, float f);

    public static final native long new_LocalShapeInfo();

    public static final native long new_MyCallback(Vector3 vector3, Vector3 vector4, int i, int i2);

    public static final native long new_MyInternalTriangleIndexCallback(long j, btCompoundShape btcompoundshape, long j2, btGImpactMeshShape btgimpactmeshshape, float f);

    public static final native long new_RayResultCallback();

    public static final native long new_SphereTriangleDetector(long j, btSphereShape btsphereshape, long j2, btTriangleShape bttriangleshape, float f);

    public static final native long new__btMprSimplex_t();

    public static final native long new__btMprSupport_t();

    public static final native long new_bt32BitAxisSweep3__SWIG_0(Vector3 vector3, Vector3 vector4, long j, long j2, btOverlappingPairCache btoverlappingpaircache, boolean z);

    public static final native long new_bt32BitAxisSweep3__SWIG_1(Vector3 vector3, Vector3 vector4, long j, long j2, btOverlappingPairCache btoverlappingpaircache);

    public static final native long new_bt32BitAxisSweep3__SWIG_2(Vector3 vector3, Vector3 vector4, long j);

    public static final native long new_bt32BitAxisSweep3__SWIG_3(Vector3 vector3, Vector3 vector4);

    public static final native long new_btAABB__SWIG_0();

    public static final native long new_btAABB__SWIG_1(Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native long new_btAABB__SWIG_2(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f);

    public static final native long new_btAABB__SWIG_3(long j, btAABB btaabb);

    public static final native long new_btAABB__SWIG_4(long j, btAABB btaabb, float f);

    public static final native long new_btAxisSweep3InternalInt_Edge();

    public static final native long new_btAxisSweep3InternalInt_Handle();

    public static final native long new_btAxisSweep3InternalInt__SWIG_0(Vector3 vector3, Vector3 vector4, long j, long j2, long j3, long j4, btOverlappingPairCache btoverlappingpaircache, boolean z);

    public static final native long new_btAxisSweep3InternalInt__SWIG_1(Vector3 vector3, Vector3 vector4, long j, long j2, long j3, long j4, btOverlappingPairCache btoverlappingpaircache);

    public static final native long new_btAxisSweep3InternalInt__SWIG_2(Vector3 vector3, Vector3 vector4, long j, long j2, long j3);

    public static final native long new_btAxisSweep3InternalInt__SWIG_3(Vector3 vector3, Vector3 vector4, long j, long j2);

    public static final native long new_btAxisSweep3InternalShort_Edge();

    public static final native long new_btAxisSweep3InternalShort_Handle();

    public static final native long new_btAxisSweep3InternalShort__SWIG_0(Vector3 vector3, Vector3 vector4, int i, int i2, int i3, long j, btOverlappingPairCache btoverlappingpaircache, boolean z);

    public static final native long new_btAxisSweep3InternalShort__SWIG_1(Vector3 vector3, Vector3 vector4, int i, int i2, int i3, long j, btOverlappingPairCache btoverlappingpaircache);

    public static final native long new_btAxisSweep3InternalShort__SWIG_2(Vector3 vector3, Vector3 vector4, int i, int i2, int i3);

    public static final native long new_btAxisSweep3InternalShort__SWIG_3(Vector3 vector3, Vector3 vector4, int i, int i2);

    public static final native long new_btAxisSweep3__SWIG_0(Vector3 vector3, Vector3 vector4, int i, long j, btOverlappingPairCache btoverlappingpaircache, boolean z);

    public static final native long new_btAxisSweep3__SWIG_1(Vector3 vector3, Vector3 vector4, int i, long j, btOverlappingPairCache btoverlappingpaircache);

    public static final native long new_btAxisSweep3__SWIG_2(Vector3 vector3, Vector3 vector4, int i);

    public static final native long new_btAxisSweep3__SWIG_3(Vector3 vector3, Vector3 vector4);

    public static final native long new_btBU_Simplex1to4__SWIG_0();

    public static final native long new_btBU_Simplex1to4__SWIG_1(Vector3 vector3);

    public static final native long new_btBU_Simplex1to4__SWIG_2(Vector3 vector3, Vector3 vector4);

    public static final native long new_btBU_Simplex1to4__SWIG_3(Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native long new_btBU_Simplex1to4__SWIG_4(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native long new_btBox2dBox2dCollisionAlgorithm_CreateFunc();

    public static final native long new_btBox2dBox2dCollisionAlgorithm__SWIG_0(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo);

    public static final native long new_btBox2dBox2dCollisionAlgorithm__SWIG_1(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2);

    public static final native long new_btBox2dShape(Vector3 vector3);

    public static final native long new_btBoxBoxCollisionAlgorithm_CreateFunc();

    public static final native long new_btBoxBoxCollisionAlgorithm__SWIG_0(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo);

    public static final native long new_btBoxBoxCollisionAlgorithm__SWIG_1(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2);

    public static final native long new_btBoxBoxDetector(long j, btBoxShape btboxshape, long j2, btBoxShape btboxshape2);

    public static final native long new_btBoxShape(Vector3 vector3);

    public static final native long new_btBroadphaseAabbCallback();

    public static final native long new_btBroadphasePairArray();

    public static final native long new_btBroadphasePairSortPredicate();

    public static final native long new_btBroadphasePair__SWIG_0();

    public static final native long new_btBroadphasePair__SWIG_1(btBroadphasePair btbroadphasepair);

    public static final native long new_btBroadphasePair__SWIG_2(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2);

    public static final native long new_btBroadphaseProxy__SWIG_0();

    public static final native long new_btBroadphaseProxy__SWIG_1(Vector3 vector3, Vector3 vector4, long j, int i, int i2);

    public static final native long new_btBroadphaseRayCallback();

    public static final native long new_btBvhSubtreeInfo();

    public static final native long new_btBvhSubtreeInfoData();

    public static final native long new_btBvhTree();

    public static final native long new_btBvhTriangleMeshShape__SWIG_0(boolean z, long j, btStridingMeshInterface btstridingmeshinterface, boolean z2, boolean z3);

    public static final native long new_btBvhTriangleMeshShape__SWIG_1(boolean z, long j, btStridingMeshInterface btstridingmeshinterface, boolean z2);

    public static final native long new_btBvhTriangleMeshShape__SWIG_2(boolean z, long j, btStridingMeshInterface btstridingmeshinterface, boolean z2, Vector3 vector3, Vector3 vector4, boolean z3);

    public static final native long new_btBvhTriangleMeshShape__SWIG_3(boolean z, long j, btStridingMeshInterface btstridingmeshinterface, boolean z2, Vector3 vector3, Vector3 vector4);

    public static final native long new_btCapsuleShapeData();

    public static final native long new_btCapsuleShapeX(float f, float f2);

    public static final native long new_btCapsuleShapeZ(float f, float f2);

    public static final native long new_btCapsuleShape__SWIG_1(float f, float f2);

    public static final native long new_btCharIndexTripletData();

    public static final native long new_btCollisionAlgorithmConstructionInfo__SWIG_0();

    public static final native long new_btCollisionAlgorithmConstructionInfo__SWIG_1(long j, btDispatcher btdispatcher, int i);

    public static final native long new_btCollisionAlgorithmCreateFunc();

    public static final native long new_btCollisionDispatcher(long j, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_btCollisionDispatcherMt__SWIG_0(long j, btCollisionConfiguration btcollisionconfiguration, int i);

    public static final native long new_btCollisionDispatcherMt__SWIG_1(long j, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_btCollisionObject();

    public static final native long new_btCollisionObjectArray__SWIG_0();

    public static final native long new_btCollisionObjectArray__SWIG_1(long j, btCollisionObjectArray btcollisionobjectarray);

    public static final native long new_btCollisionObjectArray_less();

    public static final native long new_btCollisionObjectConstArray__SWIG_0();

    public static final native long new_btCollisionObjectConstArray__SWIG_1(long j, btCollisionObjectConstArray btcollisionobjectconstarray);

    public static final native long new_btCollisionObjectConstArray_less();

    public static final native long new_btCollisionObjectDoubleData();

    public static final native long new_btCollisionObjectFloatData();

    public static final native long new_btCollisionShapeData();

    public static final native long new_btCollisionWorld(long j, btDispatcher btdispatcher, long j2, btBroadphaseInterface btbroadphaseinterface, long j3, btCollisionConfiguration btcollisionconfiguration);

    public static final native long new_btCollisionWorldImporter(long j, btCollisionWorld btcollisionworld);

    public static final native long new_btCompoundCollisionAlgorithm(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z);

    public static final native long new_btCompoundCollisionAlgorithm_CreateFunc();

    public static final native long new_btCompoundCollisionAlgorithm_SwappedCreateFunc();

    public static final native long new_btCompoundCompoundCollisionAlgorithm(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z);

    public static final native long new_btCompoundCompoundCollisionAlgorithm_CreateFunc();

    public static final native long new_btCompoundCompoundCollisionAlgorithm_SwappedCreateFunc();

    public static final native long new_btCompoundFromGimpactShape();

    public static final native long new_btCompoundShapeChild();

    public static final native long new_btCompoundShapeChildData();

    public static final native long new_btCompoundShapeData();

    public static final native long new_btCompoundShape__SWIG_0(boolean z, int i);

    public static final native long new_btCompoundShape__SWIG_1(boolean z);

    public static final native long new_btCompoundShape__SWIG_2();

    public static final native long new_btConeShape(float f, float f2);

    public static final native long new_btConeShapeData();

    public static final native long new_btConeShapeX(float f, float f2);

    public static final native long new_btConeShapeZ(float f, float f2);

    public static final native long new_btConstraintRow();

    public static final native long new_btContactArray();

    public static final native long new_btContinuousConvexCollision__SWIG_0(long j, btConvexShape btconvexshape, long j2, btConvexShape btconvexshape2, long j3, btVoronoiSimplexSolver btvoronoisimplexsolver, long j4, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver);

    public static final native long new_btContinuousConvexCollision__SWIG_1(long j, btConvexShape btconvexshape, long j2, btStaticPlaneShape btstaticplaneshape);

    public static final native long new_btConvex2dConvex2dAlgorithm(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, long j5, btVoronoiSimplexSolver btvoronoisimplexsolver, long j6, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver, int i, int i2);

    public static final native long new_btConvex2dConvex2dAlgorithm_CreateFunc(long j, btVoronoiSimplexSolver btvoronoisimplexsolver, long j2, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver);

    public static final native long new_btConvex2dShape(long j, btConvexShape btconvexshape);

    public static final native long new_btConvexCast_CastResult();

    public static final native long new_btConvexConcaveCollisionAlgorithm(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z);

    public static final native long new_btConvexConcaveCollisionAlgorithm_CreateFunc();

    public static final native long new_btConvexConcaveCollisionAlgorithm_SwappedCreateFunc();

    public static final native long new_btConvexConvexAlgorithm(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, long j5, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver, int i, int i2);

    public static final native long new_btConvexConvexAlgorithm_CreateFunc(long j, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver);

    public static final native long new_btConvexHullShapeData();

    public static final native long new_btConvexHullShape__SWIG_0(FloatBuffer floatBuffer, int i, int i2);

    public static final native long new_btConvexHullShape__SWIG_1(FloatBuffer floatBuffer, int i);

    public static final native long new_btConvexHullShape__SWIG_2(FloatBuffer floatBuffer);

    public static final native long new_btConvexHullShape__SWIG_3();

    public static final native long new_btConvexHullShape__SWIG_4(long j, btShapeHull btshapehull);

    public static final native long new_btConvexInternalShapeData();

    public static final native long new_btConvexPlaneCollisionAlgorithm(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z, int i, int i2);

    public static final native long new_btConvexPlaneCollisionAlgorithm_CreateFunc();

    public static final native long new_btConvexPointCloudShape__SWIG_0();

    public static final native long new_btConvexPointCloudShape__SWIG_1(long j, btVector3 btvector3, int i, Vector3 vector3, boolean z);

    public static final native long new_btConvexPointCloudShape__SWIG_2(long j, btVector3 btvector3, int i, Vector3 vector3);

    public static final native long new_btConvexPolyhedron();

    public static final native long new_btConvexTriangleCallback(long j, btDispatcher btdispatcher, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z);

    public static final native long new_btConvexTriangleMeshShape__SWIG_0(long j, btStridingMeshInterface btstridingmeshinterface, boolean z);

    public static final native long new_btConvexTriangleMeshShape__SWIG_1(long j, btStridingMeshInterface btstridingmeshinterface);

    public static final native long new_btCylinderShape(Vector3 vector3);

    public static final native long new_btCylinderShapeData();

    public static final native long new_btCylinderShapeX(Vector3 vector3);

    public static final native long new_btCylinderShapeZ(Vector3 vector3);

    public static final native long new_btDbvt();

    public static final native long new_btDbvtAabbMm();

    public static final native long new_btDbvtBroadphase__SWIG_0(long j, btOverlappingPairCache btoverlappingpaircache);

    public static final native long new_btDbvtBroadphase__SWIG_1();

    public static final native long new_btDbvtNode();

    public static final native long new_btDbvtProxy(Vector3 vector3, Vector3 vector4, long j, int i, int i2);

    public static final native long new_btDbvt_IClone();

    public static final native long new_btDbvt_sStkCLN(long j, btDbvtNode btdbvtnode, long j2, btDbvtNode btdbvtnode2);

    public static final native long new_btDbvt_sStkNN__SWIG_0();

    public static final native long new_btDbvt_sStkNN__SWIG_1(long j, btDbvtNode btdbvtnode, long j2, btDbvtNode btdbvtnode2);

    public static final native long new_btDbvt_sStkNP(long j, btDbvtNode btdbvtnode, long j2);

    public static final native long new_btDbvt_sStkNPS__SWIG_0();

    public static final native long new_btDbvt_sStkNPS__SWIG_1(long j, btDbvtNode btdbvtnode, long j2, float f);

    public static final native long new_btDefaultCollisionConfiguration__SWIG_0(long j, btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo);

    public static final native long new_btDefaultCollisionConfiguration__SWIG_1();

    public static final native long new_btDefaultCollisionConstructionInfo();

    public static final native long new_btDiscreteCollisionDetectorInterface_ClosestPointInput();

    public static final native long new_btDispatcherInfo();

    public static final native long new_btElement();

    public static final native long new_btEmptyAlgorithm(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo);

    public static final native long new_btEmptyAlgorithm_CreateFunc();

    public static final native long new_btEmptyShape();

    public static final native long new_btFace();

    public static final native long new_btGImpactBvh__SWIG_0();

    public static final native long new_btGImpactBvh__SWIG_1(long j, btPrimitiveManagerBase btprimitivemanagerbase);

    public static final native long new_btGImpactCollisionAlgorithm(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j2, btCollisionObjectWrapper btcollisionobjectwrapper, long j3, btCollisionObjectWrapper btcollisionobjectwrapper2);

    public static final native long new_btGImpactCollisionAlgorithm_CreateFunc();

    public static final native long new_btGImpactCompoundShape_CompoundPrimitiveManager__SWIG_0(long j, btGImpactCompoundShape.CompoundPrimitiveManager compoundPrimitiveManager);

    public static final native long new_btGImpactCompoundShape_CompoundPrimitiveManager__SWIG_1(long j, btGImpactCompoundShape btgimpactcompoundshape);

    public static final native long new_btGImpactCompoundShape_CompoundPrimitiveManager__SWIG_2();

    public static final native long new_btGImpactCompoundShape__SWIG_0(boolean z);

    public static final native long new_btGImpactCompoundShape__SWIG_1();

    public static final native long new_btGImpactMeshShape(long j, btStridingMeshInterface btstridingmeshinterface);

    public static final native long new_btGImpactMeshShapeData();

    public static final native long new_btGImpactMeshShapePart_TrimeshPrimitiveManager__SWIG_0();

    public static final native long new_btGImpactMeshShapePart_TrimeshPrimitiveManager__SWIG_1(long j, btGImpactMeshShapePart.TrimeshPrimitiveManager trimeshPrimitiveManager);

    public static final native long new_btGImpactMeshShapePart_TrimeshPrimitiveManager__SWIG_2(long j, btStridingMeshInterface btstridingmeshinterface, int i);

    public static final native long new_btGImpactMeshShapePart__SWIG_0();

    public static final native long new_btGImpactMeshShapePart__SWIG_1(long j, btStridingMeshInterface btstridingmeshinterface, int i);

    public static final native long new_btGImpactQuantizedBvh__SWIG_0();

    public static final native long new_btGImpactQuantizedBvh__SWIG_1(long j, btPrimitiveManagerBase btprimitivemanagerbase);

    public static final native long new_btGenericMemoryPool(long j, long j2);

    public static final native long new_btGenericPoolAllocator(long j, long j2);

    public static final native long new_btGhostObject();

    public static final native long new_btGhostPairCallback();

    public static final native long new_btGimBvhDataArray__SWIG_0();

    public static final native long new_btGimBvhDataArray__SWIG_1(long j, btGimBvhDataArray btgimbvhdataarray);

    public static final native long new_btGimBvhDataArray_less();

    public static final native long new_btGimBvhTreeNodeArray__SWIG_0();

    public static final native long new_btGimBvhTreeNodeArray__SWIG_1(long j, btGimBvhTreeNodeArray btgimbvhtreenodearray);

    public static final native long new_btGimBvhTreeNodeArray_less();

    public static final native long new_btGimContactArray__SWIG_0();

    public static final native long new_btGimContactArray__SWIG_1(long j, btGimContactArray btgimcontactarray);

    public static final native long new_btGimContactArray_less();

    public static final native long new_btGimPairArray__SWIG_0();

    public static final native long new_btGimPairArray__SWIG_1(long j, btGimPairArray btgimpairarray);

    public static final native long new_btGimPairArray_less();

    public static final native long new_btGimQuantizedBvhNodeArray__SWIG_0();

    public static final native long new_btGimQuantizedBvhNodeArray__SWIG_1(long j, btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray);

    public static final native long new_btGimQuantizedBvhNodeArray_less();

    public static final native long new_btGjkCollisionDescription();

    public static final native long new_btGjkConvexCast(long j, btConvexShape btconvexshape, long j2, btConvexShape btconvexshape2, long j3, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native long new_btGjkEpaPenetrationDepthSolver();

    public static final native long new_btGjkEpaSolver2();

    public static final native long new_btGjkEpaSolver2_sResults();

    public static final native long new_btGjkEpaSolver3();

    public static final native long new_btGjkEpaSolver3_sResults();

    public static final native long new_btGjkPairDetector__SWIG_0(long j, btConvexShape btconvexshape, long j2, btConvexShape btconvexshape2, long j3, btVoronoiSimplexSolver btvoronoisimplexsolver, long j4, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver);

    public static final native long new_btGjkPairDetector__SWIG_1(long j, btConvexShape btconvexshape, long j2, btConvexShape btconvexshape2, int i, int i2, float f, float f2, long j3, btVoronoiSimplexSolver btvoronoisimplexsolver, long j4, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver);

    public static final native long new_btHashMapInternalShortBtHashIntBtTriangleInfo();

    public static final native long new_btHashedOverlappingPairCache();

    public static final native long new_btHashedSimplePairCache();

    public static final native long new_btHeightfieldTerrainShape__SWIG_0(int i, int i2, FloatBuffer floatBuffer, float f, float f2, float f3, int i3, boolean z);

    public static final native long new_btHeightfieldTerrainShape__SWIG_1(int i, int i2, ShortBuffer shortBuffer, float f, float f2, float f3, int i3, boolean z);

    public static final native long new_btIndexedMesh();

    public static final native long new_btIntIndexData();

    public static final native long new_btInternalTriangleIndexCallback();

    public static final native long new_btManifoldPoint__SWIG_0();

    public static final native long new_btManifoldPoint__SWIG_1(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f);

    public static final native long new_btManifoldResult__SWIG_0();

    public static final native long new_btManifoldResult__SWIG_1(long j, btCollisionObjectWrapper btcollisionobjectwrapper, long j2, btCollisionObjectWrapper btcollisionobjectwrapper2);

    public static final native long new_btMaterialProperties();

    public static final native long new_btMaterial__SWIG_0();

    public static final native long new_btMaterial__SWIG_1(float f, float f2);

    public static final native long new_btMeshPartData();

    public static final native long new_btMinkowskiPenetrationDepthSolver();

    public static final native long new_btMinkowskiSumShape(long j, btConvexShape btconvexshape, long j2, btConvexShape btconvexshape2);

    public static final native long new_btMprCollisionDescription();

    public static final native long new_btMprDistanceInfo();

    public static final native long new_btMultiSphereShape(Vector3[] vector3Arr, float[] fArr, int i);

    public static final native long new_btMultiSphereShapeData();

    public static final native long new_btMultimaterialTriangleMeshShape__SWIG_0(long j, btStridingMeshInterface btstridingmeshinterface, boolean z, boolean z2);

    public static final native long new_btMultimaterialTriangleMeshShape__SWIG_1(long j, btStridingMeshInterface btstridingmeshinterface, boolean z);

    public static final native long new_btMultimaterialTriangleMeshShape__SWIG_2(long j, btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4, boolean z2);

    public static final native long new_btMultimaterialTriangleMeshShape__SWIG_3(long j, btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4);

    public static final native long new_btNodeOverlapCallback();

    public static final native long new_btNullPairCache();

    public static final native long new_btOptimizedBvh();

    public static final native long new_btOptimizedBvhNode();

    public static final native long new_btOptimizedBvhNodeDoubleData();

    public static final native long new_btOptimizedBvhNodeFloatData();

    public static final native long new_btOverlapCallback();

    public static final native long new_btOverlapFilterCallback();

    public static final native long new_btPairCachingGhostObject();

    public static final native long new_btPairSet();

    public static final native long new_btPersistentManifoldArray__SWIG_0();

    public static final native long new_btPersistentManifoldArray__SWIG_1(long j, btPersistentManifoldArray btpersistentmanifoldarray);

    public static final native long new_btPersistentManifoldArray_less();

    public static final native long new_btPersistentManifold__SWIG_0();

    public static final native long new_btPersistentManifold__SWIG_1(long j, btCollisionObject btcollisionobject, long j2, btCollisionObject btcollisionobject2, int i, float f, float f2);

    public static final native long new_btPointCollector();

    public static final native long new_btPolyhedralContactClipping();

    public static final native long new_btPositionAndRadius();

    public static final native long new_btPrimitiveTriangle();

    public static final native long new_btQuantizedBvh();

    public static final native long new_btQuantizedBvhDoubleData();

    public static final native long new_btQuantizedBvhFloatData();

    public static final native long new_btQuantizedBvhNode();

    public static final native long new_btQuantizedBvhNodeData();

    public static final native long new_btQuantizedBvhTree();

    public static final native long new_btScaledBvhTriangleMeshShape(long j, btBvhTriangleMeshShape btbvhtrianglemeshshape, Vector3 vector3);

    public static final native long new_btScaledTriangleMeshShapeData();

    public static final native long new_btShapeHull(long j, btConvexShape btconvexshape);

    public static final native long new_btShortIntIndexData();

    public static final native long new_btShortIntIndexTripletData();

    public static final native long new_btSimpleBroadphaseProxy__SWIG_0();

    public static final native long new_btSimpleBroadphaseProxy__SWIG_1(Vector3 vector3, Vector3 vector4, int i, long j, int i2, int i3);

    public static final native long new_btSimpleBroadphase__SWIG_0(int i, long j, btOverlappingPairCache btoverlappingpaircache);

    public static final native long new_btSimpleBroadphase__SWIG_1(int i);

    public static final native long new_btSimpleBroadphase__SWIG_2();

    public static final native long new_btSimplePair(int i, int i2);

    public static final native long new_btSimulationIslandManager();

    public static final native long new_btSortedOverlappingPairCache();

    public static final native long new_btSphereBoxCollisionAlgorithm(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z);

    public static final native long new_btSphereBoxCollisionAlgorithm_CreateFunc();

    public static final native long new_btSphereShape(float f);

    public static final native long new_btSphereSphereCollisionAlgorithm_CreateFunc();

    public static final native long new_btSphereSphereCollisionAlgorithm__SWIG_0(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2);

    public static final native long new_btSphereSphereCollisionAlgorithm__SWIG_1(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo);

    public static final native long new_btSphereTriangleCollisionAlgorithm_CreateFunc();

    public static final native long new_btSphereTriangleCollisionAlgorithm__SWIG_0(long j, btPersistentManifold btpersistentmanifold, long j2, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, long j3, btCollisionObjectWrapper btcollisionobjectwrapper, long j4, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z);

    public static final native long new_btSphereTriangleCollisionAlgorithm__SWIG_1(long j, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo);

    public static final native long new_btStaticPlaneShape(Vector3 vector3, float f);

    public static final native long new_btStaticPlaneShapeData();

    public static final native long new_btStridingMeshInterfaceData();

    public static final native long new_btSubSimplexClosestResult();

    public static final native long new_btSubsimplexConvexCast(long j, btConvexShape btconvexshape, long j2, btConvexShape btconvexshape2, long j3, btVoronoiSimplexSolver btvoronoisimplexsolver);

    public static final native long new_btTetrahedronShapeEx();

    public static final native long new_btTriangle();

    public static final native long new_btTriangleBuffer();

    public static final native long new_btTriangleCallback();

    public static final native long new_btTriangleConvexcastCallback(long j, btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, Matrix4 matrix6, float f);

    public static final native long new_btTriangleIndexVertexArray();

    public static final native long new_btTriangleIndexVertexMaterialArray__SWIG_0();

    public static final native long new_btTriangleIndexVertexMaterialArray__SWIG_1(int i, IntBuffer intBuffer, int i2, int i3, FloatBuffer floatBuffer, int i4, int i5, ByteBuffer byteBuffer, int i6, IntBuffer intBuffer2, int i7);

    public static final native long new_btTriangleInfo();

    public static final native long new_btTriangleInfoData();

    public static final native long new_btTriangleInfoMap();

    public static final native long new_btTriangleInfoMapData();

    public static final native long new_btTriangleMeshShapeData();

    public static final native long new_btTriangleMesh__SWIG_0(boolean z, boolean z2);

    public static final native long new_btTriangleMesh__SWIG_1(boolean z);

    public static final native long new_btTriangleMesh__SWIG_2();

    public static final native long new_btTriangleRaycastCallback__SWIG_0(Vector3 vector3, Vector3 vector4, long j);

    public static final native long new_btTriangleRaycastCallback__SWIG_1(Vector3 vector3, Vector3 vector4);

    public static final native long new_btTriangleShapeEx__SWIG_0();

    public static final native long new_btTriangleShapeEx__SWIG_1(Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native long new_btTriangleShapeEx__SWIG_2(long j, btTriangleShapeEx bttriangleshapeex);

    public static final native long new_btTriangleShape__SWIG_0();

    public static final native long new_btTriangleShape__SWIG_1(Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native long new_btUniformScalingShape(long j, btConvexShape btconvexshape, float f);

    public static final native long new_btUnionFind();

    public static final native long new_btUsageBitfield();

    public static final native long new_btVoronoiSimplexSolver();

    public static final native long new_copy_elements_func();

    public static final native long new_gim_array__SWIG_0();

    public static final native long new_gim_array__SWIG_1(long j);

    public static final native long new_gim_bitset__SWIG_0();

    public static final native long new_gim_bitset__SWIG_1(long j);

    public static final native long new_gim_contact_array();

    public static final native long new_integer_comparator();

    public static final native long new_less_comparator();

    public static final native long new_memcopy_elements_func();

    public static final native long new_uint_key_func();

    public static final native boolean operatorEqualTo__SWIG_3(btBroadphasePair btbroadphasepair, btBroadphasePair btbroadphasepair2);

    public static final native boolean operatorEqualTo__SWIG_4(long j, btCompoundShapeChild btcompoundshapechild, long j2, btCompoundShapeChild btcompoundshapechild2);

    public static final native int portalCanEncapsuleOrigin(long j, _btMprSimplex_t _btmprsimplex_t, long j2, _btMprSupport_t _btmprsupport_t, long j3, btVector3 btvector3);

    public static final native int portalEncapsulesOrigin(long j, _btMprSimplex_t _btmprsimplex_t, long j2, btVector3 btvector3);

    public static final native int portalReachTolerance(long j, _btMprSimplex_t _btmprsimplex_t, long j2, _btMprSupport_t _btmprsupport_t, long j3, btVector3 btvector3);

    private static final native void swig_module_init();

    public static boolean SwigDirector_btBroadphaseAabbCallback_process(btBroadphaseAabbCallback btbroadphaseaabbcallback, long j) {
        return btbroadphaseaabbcallback.process(j == 0 ? null : new btBroadphaseProxy(j, false));
    }

    public static boolean SwigDirector_btBroadphaseRayCallback_process(btBroadphaseRayCallback btbroadphaseraycallback, long j) {
        return btbroadphaseraycallback.process(j == 0 ? null : new btBroadphaseProxy(j, false));
    }

    public static void SwigDirector_btNodeOverlapCallback_processNode(btNodeOverlapCallback btnodeoverlapcallback, int i, int i2) {
        btnodeoverlapcallback.processNode(i, i2);
    }

    public static boolean SwigDirector_btOverlapCallback_processOverlap(btOverlapCallback btoverlapcallback, btBroadphasePair btbroadphasepair) {
        return btoverlapcallback.processOverlap(btbroadphasepair);
    }

    public static boolean SwigDirector_btOverlapFilterCallback_needBroadphaseCollision(btOverlapFilterCallback btoverlapfiltercallback, long j, long j2) {
        return btoverlapfiltercallback.needBroadphaseCollision(j == 0 ? null : new btBroadphaseProxy(j, false), j2 != 0 ? new btBroadphaseProxy(j2, false) : null);
    }

    public static void SwigDirector_btTriangleCallback_processTriangle(btTriangleCallback bttrianglecallback, long j, int i, int i2) {
        bttrianglecallback.processTriangle(j == 0 ? null : new btVector3(j, false), i, i2);
    }

    public static void SwigDirector_btInternalTriangleIndexCallback_internalProcessTriangleIndex(btInternalTriangleIndexCallback btinternaltriangleindexcallback, long j, int i, int i2) {
        btinternaltriangleindexcallback.internalProcessTriangleIndex(j == 0 ? null : new btVector3(j, false), i, i2);
    }

    public static void SwigDirector_ICollide_Process__SWIG_0(ICollide iCollide, long j, long j2) {
        iCollide.Process(btDbvtNode.obtainForArgument(j, false), btDbvtNode.obtainForArgument(j2, false));
    }

    public static void SwigDirector_ICollide_Process__SWIG_1(ICollide iCollide, long j) {
        iCollide.Process(btDbvtNode.obtainForArgument(j, false));
    }

    public static void SwigDirector_ICollide_Process__SWIG_2(ICollide iCollide, long j, float f) {
        iCollide.Process(btDbvtNode.obtainForArgument(j, false), f);
    }

    public static boolean SwigDirector_ICollide_Descent(ICollide iCollide, long j) {
        return iCollide.Descent(btDbvtNode.obtainForArgument(j, false));
    }

    public static boolean SwigDirector_ICollide_AllLeaves(ICollide iCollide, long j) {
        return iCollide.AllLeaves(btDbvtNode.obtainForArgument(j, false));
    }

    public static void SwigDirector_btConvexTriangleCallback_processTriangle(btConvexTriangleCallback btconvextrianglecallback, long j, int i, int i2) {
        btconvextrianglecallback.processTriangle(j == 0 ? null : new btVector3(j, false), i, i2);
    }

    public static long SwigDirector_btGhostPairCallback_removeOverlappingPair(btGhostPairCallback btghostpaircallback, long j, long j2, long j3) {
        return btghostpaircallback.removeOverlappingPair(j == 0 ? null : new btBroadphaseProxy(j, false), j2 == 0 ? null : new btBroadphaseProxy(j2, false), j3 != 0 ? new btDispatcher(j3, false) : null);
    }

    public static void SwigDirector_btGhostPairCallback_removeOverlappingPairsContainingProxy(btGhostPairCallback btghostpaircallback, long j, long j2) {
        btghostpaircallback.removeOverlappingPairsContainingProxy(j == 0 ? null : new btBroadphaseProxy(j, false), j2 != 0 ? new btDispatcher(j2, false) : null);
    }

    public static boolean SwigDirector_RayResultCallback_needsCollision(RayResultCallback rayResultCallback, long j) {
        return rayResultCallback.needsCollision(j == 0 ? null : new btBroadphaseProxy(j, false));
    }

    public static float SwigDirector_RayResultCallback_addSingleResult(RayResultCallback rayResultCallback, long j, boolean z) {
        return rayResultCallback.addSingleResult(new LocalRayResult(j, false), z);
    }

    public static boolean SwigDirector_ClosestRayResultCallback_needsCollision(ClosestRayResultCallback closestRayResultCallback, long j) {
        return closestRayResultCallback.needsCollision(j == 0 ? null : new btBroadphaseProxy(j, false));
    }

    public static float SwigDirector_ClosestRayResultCallback_addSingleResult(ClosestRayResultCallback closestRayResultCallback, long j, boolean z) {
        return closestRayResultCallback.addSingleResult(new LocalRayResult(j, false), z);
    }

    public static boolean SwigDirector_AllHitsRayResultCallback_needsCollision(AllHitsRayResultCallback allHitsRayResultCallback, long j) {
        return allHitsRayResultCallback.needsCollision(j == 0 ? null : new btBroadphaseProxy(j, false));
    }

    public static float SwigDirector_AllHitsRayResultCallback_addSingleResult(AllHitsRayResultCallback allHitsRayResultCallback, long j, boolean z) {
        return allHitsRayResultCallback.addSingleResult(new LocalRayResult(j, false), z);
    }

    public static boolean SwigDirector_ConvexResultCallback_needsCollision(ConvexResultCallback convexResultCallback, long j) {
        return convexResultCallback.needsCollision(j == 0 ? null : new btBroadphaseProxy(j, false));
    }

    public static float SwigDirector_ConvexResultCallback_addSingleResult(ConvexResultCallback convexResultCallback, long j, boolean z) {
        return convexResultCallback.addSingleResult(new LocalConvexResult(j, false), z);
    }

    public static boolean SwigDirector_ClosestConvexResultCallback_needsCollision(ClosestConvexResultCallback closestConvexResultCallback, long j) {
        return closestConvexResultCallback.needsCollision(j == 0 ? null : new btBroadphaseProxy(j, false));
    }

    public static float SwigDirector_ClosestConvexResultCallback_addSingleResult(ClosestConvexResultCallback closestConvexResultCallback, long j, boolean z) {
        return closestConvexResultCallback.addSingleResult(new LocalConvexResult(j, false), z);
    }

    public static boolean SwigDirector_ContactResultCallback_needsCollision(ContactResultCallback contactResultCallback, long j) {
        return contactResultCallback.needsCollision(j == 0 ? null : new btBroadphaseProxy(j, false));
    }

    public static float SwigDirector_ContactResultCallback_addSingleResult(ContactResultCallback contactResultCallback, long j, long j2, int i, int i2, long j3, int i3, int i4) {
        return contactResultCallback.addSingleResult(new btManifoldPoint(j, false), btCollisionObjectWrapper.obtainForArgument(j2, false), i, i2, btCollisionObjectWrapper.obtainForArgument(j3, false), i3, i4);
    }

    public static void SwigDirector_btTriangleRaycastCallback_processTriangle(btTriangleRaycastCallback bttriangleraycastcallback, long j, int i, int i2) {
        bttriangleraycastcallback.processTriangle(j == 0 ? null : new btVector3(j, false), i, i2);
    }

    public static float SwigDirector_btTriangleRaycastCallback_reportHit(btTriangleRaycastCallback bttriangleraycastcallback, Vector3 vector3, float f, int i, int i2) {
        return bttriangleraycastcallback.reportHit(vector3, f, i, i2);
    }

    public static void SwigDirector_btTriangleConvexcastCallback_processTriangle(btTriangleConvexcastCallback bttriangleconvexcastcallback, long j, int i, int i2) {
        bttriangleconvexcastcallback.processTriangle(j == 0 ? null : new btVector3(j, false), i, i2);
    }

    public static float SwigDirector_btTriangleConvexcastCallback_reportHit(btTriangleConvexcastCallback bttriangleconvexcastcallback, Vector3 vector3, Vector3 vector4, float f, int i, int i2) {
        return bttriangleconvexcastcallback.reportHit(vector3, vector4, f, i, i2);
    }

    public static boolean SwigDirector_CustomCollisionDispatcher_needsCollision(CustomCollisionDispatcher customCollisionDispatcher, long j, long j2) {
        return customCollisionDispatcher.needsCollision(btCollisionObject.getInstance(j, false), btCollisionObject.getInstance(j2, false));
    }

    public static boolean SwigDirector_CustomCollisionDispatcher_needsResponse(CustomCollisionDispatcher customCollisionDispatcher, long j, long j2) {
        return customCollisionDispatcher.needsResponse(btCollisionObject.getInstance(j, false), btCollisionObject.getInstance(j2, false));
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_0(ContactListener contactListener, long j, long j2, int i, int i2, long j3, int i3, int i4) {
        return contactListener.onContactAdded(btManifoldPoint.obtainForArgument(j, false), btCollisionObjectWrapper.obtainForArgument(j2, false), i, i2, btCollisionObjectWrapper.obtainForArgument(j3, false), i3, i4);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_1(ContactListener contactListener, long j, long j2, int i, int i2, long j3, int i3, int i4) {
        return contactListener.onContactAdded(btManifoldPoint.obtainForArgument(j, false), btCollisionObject.getInstance(j2, false), i, i2, btCollisionObject.getInstance(j3, false), i3, i4);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_2(ContactListener contactListener, long j, int i, int i2, int i3, int i4, int i5, int i6) {
        return contactListener.onContactAdded(btManifoldPoint.obtainForArgument(j, false), i, i2, i3, i4, i5, i6);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_3(ContactListener contactListener, long j, long j2, int i, int i2, boolean z, long j3, int i3, int i4, boolean z2) {
        return contactListener.onContactAdded(btManifoldPoint.obtainForArgument(j, false), btCollisionObjectWrapper.obtainForArgument(j2, false), i, i2, z, btCollisionObjectWrapper.obtainForArgument(j3, false), i3, i4, z2);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_4(ContactListener contactListener, long j, long j2, int i, int i2, boolean z, long j3, int i3, int i4, boolean z2) {
        return contactListener.onContactAdded(btManifoldPoint.obtainForArgument(j, false), btCollisionObject.getInstance(j2, false), i, i2, z, btCollisionObject.getInstance(j3, false), i3, i4, z2);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_5(ContactListener contactListener, long j, int i, int i2, int i3, boolean z, int i4, int i5, int i6, boolean z2) {
        return contactListener.onContactAdded(btManifoldPoint.obtainForArgument(j, false), i, i2, i3, z, i4, i5, i6, z2);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_6(ContactListener contactListener, long j, int i, int i2, long j2, int i3, int i4) {
        return contactListener.onContactAdded(btCollisionObjectWrapper.obtainForArgument(j, false), i, i2, btCollisionObjectWrapper.obtainForArgument(j2, false), i3, i4);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_7(ContactListener contactListener, long j, int i, int i2, long j2, int i3, int i4) {
        return contactListener.onContactAdded(btCollisionObject.getInstance(j, false), i, i2, btCollisionObject.getInstance(j2, false), i3, i4);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_8(ContactListener contactListener, int i, int i2, int i3, int i4, int i5, int i6) {
        return contactListener.onContactAdded(i, i2, i3, i4, i5, i6);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_9(ContactListener contactListener, long j, int i, int i2, boolean z, long j2, int i3, int i4, boolean z2) {
        return contactListener.onContactAdded(btCollisionObjectWrapper.obtainForArgument(j, false), i, i2, z, btCollisionObjectWrapper.obtainForArgument(j2, false), i3, i4, z2);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_10(ContactListener contactListener, long j, int i, int i2, boolean z, long j2, int i3, int i4, boolean z2) {
        return contactListener.onContactAdded(btCollisionObject.getInstance(j, false), i, i2, z, btCollisionObject.getInstance(j2, false), i3, i4, z2);
    }

    public static boolean SwigDirector_ContactListener_onContactAdded__SWIG_11(ContactListener contactListener, int i, int i2, int i3, boolean z, int i4, int i5, int i6, boolean z2) {
        return contactListener.onContactAdded(i, i2, i3, z, i4, i5, i6, z2);
    }

    public static void SwigDirector_ContactListener_onContactProcessed__SWIG_0(ContactListener contactListener, long j, long j2, long j3) {
        contactListener.onContactProcessed(btManifoldPoint.obtainForArgument(j, false), btCollisionObject.getInstance(j2, false), btCollisionObject.getInstance(j3, false));
    }

    public static void SwigDirector_ContactListener_onContactProcessed__SWIG_1(ContactListener contactListener, long j, int i, int i2) {
        contactListener.onContactProcessed(btManifoldPoint.obtainForArgument(j, false), i, i2);
    }

    public static void SwigDirector_ContactListener_onContactProcessed__SWIG_2(ContactListener contactListener, long j, long j2, boolean z, long j3, boolean z2) {
        contactListener.onContactProcessed(btManifoldPoint.obtainForArgument(j, false), btCollisionObject.getInstance(j2, false), z, btCollisionObject.getInstance(j3, false), z2);
    }

    public static void SwigDirector_ContactListener_onContactProcessed__SWIG_3(ContactListener contactListener, long j, int i, boolean z, int i2, boolean z2) {
        contactListener.onContactProcessed(btManifoldPoint.obtainForArgument(j, false), i, z, i2, z2);
    }

    public static void SwigDirector_ContactListener_onContactProcessed__SWIG_4(ContactListener contactListener, long j, long j2) {
        contactListener.onContactProcessed(btCollisionObject.getInstance(j, false), btCollisionObject.getInstance(j2, false));
    }

    public static void SwigDirector_ContactListener_onContactProcessed__SWIG_5(ContactListener contactListener, int i, int i2) {
        contactListener.onContactProcessed(i, i2);
    }

    public static void SwigDirector_ContactListener_onContactProcessed__SWIG_6(ContactListener contactListener, long j, boolean z, long j2, boolean z2) {
        contactListener.onContactProcessed(btCollisionObject.getInstance(j, false), z, btCollisionObject.getInstance(j2, false), z2);
    }

    public static void SwigDirector_ContactListener_onContactProcessed__SWIG_7(ContactListener contactListener, int i, boolean z, int i2, boolean z2) {
        contactListener.onContactProcessed(i, z, i2, z2);
    }

    public static void SwigDirector_ContactListener_onContactDestroyed(ContactListener contactListener, int i) {
        contactListener.onContactDestroyed(i);
    }

    public static void SwigDirector_ContactListener_onContactStarted__SWIG_0(ContactListener contactListener, long j) {
        contactListener.onContactStarted(j == 0 ? null : new btPersistentManifold(j, false));
    }

    public static void SwigDirector_ContactListener_onContactStarted__SWIG_1(ContactListener contactListener, long j, long j2) {
        contactListener.onContactStarted(btCollisionObject.getInstance(j, false), btCollisionObject.getInstance(j2, false));
    }

    public static void SwigDirector_ContactListener_onContactStarted__SWIG_2(ContactListener contactListener, int i, int i2) {
        contactListener.onContactStarted(i, i2);
    }

    public static void SwigDirector_ContactListener_onContactStarted__SWIG_3(ContactListener contactListener, long j, boolean z, boolean z2) {
        contactListener.onContactStarted(j == 0 ? null : new btPersistentManifold(j, false), z, z2);
    }

    public static void SwigDirector_ContactListener_onContactStarted__SWIG_4(ContactListener contactListener, long j, boolean z, long j2, boolean z2) {
        contactListener.onContactStarted(btCollisionObject.getInstance(j, false), z, btCollisionObject.getInstance(j2, false), z2);
    }

    public static void SwigDirector_ContactListener_onContactStarted__SWIG_5(ContactListener contactListener, int i, boolean z, int i2, boolean z2) {
        contactListener.onContactStarted(i, z, i2, z2);
    }

    public static void SwigDirector_ContactListener_onContactEnded__SWIG_0(ContactListener contactListener, long j) {
        contactListener.onContactEnded(j == 0 ? null : new btPersistentManifold(j, false));
    }

    public static void SwigDirector_ContactListener_onContactEnded__SWIG_1(ContactListener contactListener, long j, long j2) {
        contactListener.onContactEnded(btCollisionObject.getInstance(j, false), btCollisionObject.getInstance(j2, false));
    }

    public static void SwigDirector_ContactListener_onContactEnded__SWIG_2(ContactListener contactListener, int i, int i2) {
        contactListener.onContactEnded(i, i2);
    }

    public static void SwigDirector_ContactListener_onContactEnded__SWIG_3(ContactListener contactListener, long j, boolean z, boolean z2) {
        contactListener.onContactEnded(j == 0 ? null : new btPersistentManifold(j, false), z, z2);
    }

    public static void SwigDirector_ContactListener_onContactEnded__SWIG_4(ContactListener contactListener, long j, boolean z, long j2, boolean z2) {
        contactListener.onContactEnded(btCollisionObject.getInstance(j, false), z, btCollisionObject.getInstance(j2, false), z2);
    }

    public static void SwigDirector_ContactListener_onContactEnded__SWIG_5(ContactListener contactListener, int i, boolean z, int i2, boolean z2) {
        contactListener.onContactEnded(i, z, i2, z2);
    }

    public static void SwigDirector_ContactCache_onContactStarted(ContactCache contactCache, long j, boolean z, boolean z2) {
        contactCache.onContactStarted(j == 0 ? null : new btPersistentManifold(j, false), z, z2);
    }

    public static void SwigDirector_ContactCache_onContactEnded(ContactCache contactCache, long j, boolean z, long j2, boolean z2) {
        contactCache.onContactEnded(btCollisionObject.getInstance(j, false), z, btCollisionObject.getInstance(j2, false), z2);
    }

    static {
        swig_module_init();
    }
}
