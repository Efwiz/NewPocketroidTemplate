package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class LinearMathJNI {
    public static final native void AabbExpand(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native int BT_HASH_NULL_get();

    public static final native long BT_MAX_THREAD_COUNT_get();

    public static final native short ConvexH_HalfEdge_ea_get(long j, ConvexH.HalfEdge halfEdge);

    public static final native void ConvexH_HalfEdge_ea_set(long j, ConvexH.HalfEdge halfEdge, short s);

    public static final native short ConvexH_HalfEdge_p_get(long j, ConvexH.HalfEdge halfEdge);

    public static final native void ConvexH_HalfEdge_p_set(long j, ConvexH.HalfEdge halfEdge, short s);

    public static final native short ConvexH_HalfEdge_v_get(long j, ConvexH.HalfEdge halfEdge);

    public static final native void ConvexH_HalfEdge_v_set(long j, ConvexH.HalfEdge halfEdge, short s);

    public static final native long ConvexH_edges_get(long j, ConvexH convexH);

    public static final native void ConvexH_edges_set(long j, ConvexH convexH, long j2);

    public static final native long ConvexH_facets_get(long j, ConvexH convexH);

    public static final native void ConvexH_facets_set(long j, ConvexH convexH, long j2);

    public static final native long ConvexH_vertices_get(long j, ConvexH convexH);

    public static final native void ConvexH_vertices_set(long j, ConvexH convexH, long j2, btVector3Array btvector3array);

    public static final native long GEN_rand();

    public static final native void GEN_srand(long j);

    public static final native void GrahamScanConvexHull2D(long j, long j2, Vector3 vector3);

    public static final native long GrahamVector3_SWIGUpcast(long j);

    public static final native float GrahamVector3_angle_get(long j, GrahamVector3 grahamVector3);

    public static final native void GrahamVector3_angle_set(long j, GrahamVector3 grahamVector3, float f);

    public static final native int GrahamVector3_orgIndex_get(long j, GrahamVector3 grahamVector3);

    public static final native void GrahamVector3_orgIndex_set(long j, GrahamVector3 grahamVector3, int i);

    public static final native void HullDesc_ClearHullFlag(long j, HullDesc hullDesc, int i);

    public static final native boolean HullDesc_HasHullFlag(long j, HullDesc hullDesc, int i);

    public static final native void HullDesc_SetHullFlag(long j, HullDesc hullDesc, int i);

    public static final native long HullDesc_mFlags_get(long j, HullDesc hullDesc);

    public static final native void HullDesc_mFlags_set(long j, HullDesc hullDesc, long j2);

    public static final native long HullDesc_mMaxFaces_get(long j, HullDesc hullDesc);

    public static final native void HullDesc_mMaxFaces_set(long j, HullDesc hullDesc, long j2);

    public static final native long HullDesc_mMaxVertices_get(long j, HullDesc hullDesc);

    public static final native void HullDesc_mMaxVertices_set(long j, HullDesc hullDesc, long j2);

    public static final native float HullDesc_mNormalEpsilon_get(long j, HullDesc hullDesc);

    public static final native void HullDesc_mNormalEpsilon_set(long j, HullDesc hullDesc, float f);

    public static final native long HullDesc_mVcount_get(long j, HullDesc hullDesc);

    public static final native void HullDesc_mVcount_set(long j, HullDesc hullDesc, long j2);

    public static final native long HullDesc_mVertexStride_get(long j, HullDesc hullDesc);

    public static final native void HullDesc_mVertexStride_set(long j, HullDesc hullDesc, long j2);

    public static final native long HullDesc_mVertices_get(long j, HullDesc hullDesc);

    public static final native void HullDesc_mVertices_set(long j, HullDesc hullDesc, long j2, btVector3 btvector3);

    public static final native int HullLibrary_CreateConvexHull(long j, HullLibrary hullLibrary, long j2, HullDesc hullDesc, long j3, HullResult hullResult);

    public static final native int HullLibrary_ReleaseResult(long j, HullLibrary hullLibrary, long j2, HullResult hullResult);

    public static final native long HullLibrary_vertexIndexMapping_get(long j, HullLibrary hullLibrary);

    public static final native void HullLibrary_vertexIndexMapping_set(long j, HullLibrary hullLibrary, long j2);

    public static final native long HullResult_Indices_get(long j, HullResult hullResult);

    public static final native void HullResult_Indices_set(long j, HullResult hullResult, long j2);

    public static final native long HullResult_OutputVertices_get(long j, HullResult hullResult);

    public static final native void HullResult_OutputVertices_set(long j, HullResult hullResult, long j2, btVector3Array btvector3array);

    public static final native long HullResult_mNumFaces_get(long j, HullResult hullResult);

    public static final native void HullResult_mNumFaces_set(long j, HullResult hullResult, long j2);

    public static final native long HullResult_mNumIndices_get(long j, HullResult hullResult);

    public static final native void HullResult_mNumIndices_set(long j, HullResult hullResult, long j2);

    public static final native long HullResult_mNumOutputVertices_get(long j, HullResult hullResult);

    public static final native void HullResult_mNumOutputVertices_set(long j, HullResult hullResult, long j2);

    public static final native boolean HullResult_mPolygons_get(long j, HullResult hullResult);

    public static final native void HullResult_mPolygons_set(long j, HullResult hullResult, boolean z);

    public static final native long PHullResult_Indices_get(long j, PHullResult pHullResult);

    public static final native void PHullResult_Indices_set(long j, PHullResult pHullResult, long j2);

    public static final native long PHullResult_mFaceCount_get(long j, PHullResult pHullResult);

    public static final native void PHullResult_mFaceCount_set(long j, PHullResult pHullResult, long j2);

    public static final native long PHullResult_mIndexCount_get(long j, PHullResult pHullResult);

    public static final native void PHullResult_mIndexCount_set(long j, PHullResult pHullResult, long j2);

    public static final native long PHullResult_mVcount_get(long j, PHullResult pHullResult);

    public static final native void PHullResult_mVcount_set(long j, PHullResult pHullResult, long j2);

    public static final native long PHullResult_mVertices_get(long j, PHullResult pHullResult);

    public static final native void PHullResult_mVertices_set(long j, PHullResult pHullResult, long j2, btVector3 btvector3);

    public static final native boolean TestAabbAgainstAabb2(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native boolean TestPointAgainstAabb2(Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native boolean TestTriangleAgainstAabb2(long j, btVector3 btvector3, Vector3 vector3, Vector3 vector4);

    public static final native Vector3 btAabbSupport(Vector3 vector3, Vector3 vector4);

    public static final native float btAcos(float f);

    public static final native long btAlignedAllocInternal(long j, int i);

    public static final native void btAlignedAllocSetCustom(long j, long j2);

    public static final native void btAlignedAllocSetCustomAligned(long j, long j2);

    public static final native void btAlignedFreeInternal(long j);

    public static final native long btAngleCompareFunc_anchor_get(long j, btAngleCompareFunc btanglecomparefunc);

    public static final native void btAngleCompareFunc_anchor_set(long j, btAngleCompareFunc btanglecomparefunc, long j2, btVector3 btvector3);

    public static final native boolean btAngleCompareFunc_operatorFunctionCall(long j, btAngleCompareFunc btanglecomparefunc, long j2, GrahamVector3 grahamVector3, long j3, GrahamVector3 grahamVector4);

    public static final native float btAngle__SWIG_0(Vector3 vector3, Vector3 vector4);

    public static final native float btAngle__SWIG_1(Quaternion quaternion, Quaternion quaternion2);

    public static final native float btAsin(float f);

    public static final native float btAtan(float f);

    public static final native float btAtan2(float f, float f2);

    public static final native float btAtan2Fast(float f, float f2);

    public static final native ByteBuffer btBlock_address_get(long j, btBlock btblock);

    public static final native void btBlock_address_set(long j, btBlock btblock, ByteBuffer byteBuffer);

    public static final native long btBlock_previous_get(long j, btBlock btblock);

    public static final native void btBlock_previous_set(long j, btBlock btblock, long j2, btBlock btblock2);

    public static final native long btBulletSerializedArrays_bvhsDouble_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_bvhsDouble_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_bvhsFloat_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_bvhsFloat_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_colShapeData_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_colShapeData_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_collisionObjectDataDouble_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_collisionObjectDataDouble_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_collisionObjectDataFloat_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_collisionObjectDataFloat_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_constraintDataDouble_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_constraintDataDouble_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_constraintDataFloat_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_constraintDataFloat_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_constraintData_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_constraintData_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_dynamicWorldInfoDataDouble_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_dynamicWorldInfoDataDouble_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_dynamicWorldInfoDataFloat_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_dynamicWorldInfoDataFloat_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_rigidBodyDataDouble_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_rigidBodyDataDouble_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_rigidBodyDataFloat_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_rigidBodyDataFloat_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_softBodyDoubleData_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_softBodyDoubleData_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native long btBulletSerializedArrays_softBodyFloatData_get(long j, btBulletSerializedArrays btbulletserializedarrays);

    public static final native void btBulletSerializedArrays_softBodyFloatData_set(long j, btBulletSerializedArrays btbulletserializedarrays, long j2);

    public static final native int btChunk_chunkCode_get(long j, btChunk btchunk);

    public static final native void btChunk_chunkCode_set(long j, btChunk btchunk, int i);

    public static final native int btChunk_dna_nr_get(long j, btChunk btchunk);

    public static final native void btChunk_dna_nr_set(long j, btChunk btchunk, int i);

    public static final native int btChunk_length_get(long j, btChunk btchunk);

    public static final native void btChunk_length_set(long j, btChunk btchunk, int i);

    public static final native int btChunk_number_get(long j, btChunk btchunk);

    public static final native void btChunk_number_set(long j, btChunk btchunk, int i);

    public static final native long btChunk_oldPtr_get(long j, btChunk btchunk);

    public static final native void btChunk_oldPtr_set(long j, btChunk btchunk, long j2);

    public static final native BigInteger btClock_getTimeMicroseconds(long j, btClock btclock);

    public static final native BigInteger btClock_getTimeMilliseconds(long j, btClock btclock);

    public static final native BigInteger btClock_getTimeNanoseconds(long j, btClock btclock);

    public static final native float btClock_getTimeSeconds(long j, btClock btclock);

    public static final native long btClock_operatorAssignment(long j, btClock btclock, long j2, btClock btclock2);

    public static final native void btClock_reset(long j, btClock btclock);

    public static final native long btConvexHullComputer_Edge_getNextEdgeOfFace(long j, btConvexHullComputer.Edge edge);

    public static final native long btConvexHullComputer_Edge_getNextEdgeOfVertex(long j, btConvexHullComputer.Edge edge);

    public static final native long btConvexHullComputer_Edge_getReverseEdge(long j, btConvexHullComputer.Edge edge);

    public static final native int btConvexHullComputer_Edge_getSourceVertex(long j, btConvexHullComputer.Edge edge);

    public static final native int btConvexHullComputer_Edge_getTargetVertex(long j, btConvexHullComputer.Edge edge);

    public static final native float btConvexHullComputer_compute__SWIG_0(long j, btConvexHullComputer btconvexhullcomputer, FloatBuffer floatBuffer, int i, int i2, float f, float f2);

    public static final native float btConvexHullComputer_compute__SWIG_1(long j, btConvexHullComputer btconvexhullcomputer, DoubleBuffer doubleBuffer, int i, int i2, float f, float f2);

    public static final native long btConvexHullComputer_edges_get(long j, btConvexHullComputer btconvexhullcomputer);

    public static final native void btConvexHullComputer_edges_set(long j, btConvexHullComputer btconvexhullcomputer, long j2);

    public static final native long btConvexHullComputer_faces_get(long j, btConvexHullComputer btconvexhullcomputer);

    public static final native void btConvexHullComputer_faces_set(long j, btConvexHullComputer btconvexhullcomputer, long j2);

    public static final native long btConvexHullComputer_vertices_get(long j, btConvexHullComputer btconvexhullcomputer);

    public static final native void btConvexHullComputer_vertices_set(long j, btConvexHullComputer btconvexhullcomputer, long j2, btVector3Array btvector3array);

    public static final native float btConvexSeparatingDistanceUtil_getConservativeSeparatingDistance(long j, btConvexSeparatingDistanceUtil btconvexseparatingdistanceutil);

    public static final native void btConvexSeparatingDistanceUtil_initSeparatingDistance(long j, btConvexSeparatingDistanceUtil btconvexseparatingdistanceutil, Vector3 vector3, float f, Matrix4 matrix4, Matrix4 matrix5);

    public static final native void btConvexSeparatingDistanceUtil_updateSeparatingDistance(long j, btConvexSeparatingDistanceUtil btconvexseparatingdistanceutil, Matrix4 matrix4, Matrix4 matrix5);

    public static final native float btCos(float f);

    public static final native Vector3 btCross(Vector3 vector3, Vector3 vector4);

    public static final native long btDefaultMotionState_SWIGUpcast(long j);

    public static final native long btDefaultMotionState_centerOfMassOffset_get(long j, btDefaultMotionState btdefaultmotionstate);

    public static final native void btDefaultMotionState_centerOfMassOffset_set(long j, btDefaultMotionState btdefaultmotionstate, long j2, btTransform bttransform);

    public static final native void btDefaultMotionState_getCenterOfMassOffset(long j, btDefaultMotionState btdefaultmotionstate, Matrix4 matrix4);

    public static final native void btDefaultMotionState_getGraphicsWorldTrans(long j, btDefaultMotionState btdefaultmotionstate, Matrix4 matrix4);

    public static final native void btDefaultMotionState_getStartWorldTrans(long j, btDefaultMotionState btdefaultmotionstate, Matrix4 matrix4);

    public static final native long btDefaultMotionState_graphicsWorldTrans_get(long j, btDefaultMotionState btdefaultmotionstate);

    public static final native void btDefaultMotionState_graphicsWorldTrans_set(long j, btDefaultMotionState btdefaultmotionstate, long j2, btTransform bttransform);

    public static final native void btDefaultMotionState_operatorDeleteArray__SWIG_0(long j, btDefaultMotionState btdefaultmotionstate, long j2);

    public static final native void btDefaultMotionState_operatorDeleteArray__SWIG_1(long j, btDefaultMotionState btdefaultmotionstate, long j2, long j3);

    public static final native void btDefaultMotionState_operatorDelete__SWIG_0(long j, btDefaultMotionState btdefaultmotionstate, long j2);

    public static final native void btDefaultMotionState_operatorDelete__SWIG_1(long j, btDefaultMotionState btdefaultmotionstate, long j2, long j3);

    public static final native long btDefaultMotionState_operatorNewArray__SWIG_0(long j, btDefaultMotionState btdefaultmotionstate, long j2);

    public static final native long btDefaultMotionState_operatorNewArray__SWIG_1(long j, btDefaultMotionState btdefaultmotionstate, long j2, long j3);

    public static final native long btDefaultMotionState_operatorNew__SWIG_0(long j, btDefaultMotionState btdefaultmotionstate, long j2);

    public static final native long btDefaultMotionState_operatorNew__SWIG_1(long j, btDefaultMotionState btdefaultmotionstate, long j2, long j3);

    public static final native long btDefaultMotionState_startWorldTrans_get(long j, btDefaultMotionState btdefaultmotionstate);

    public static final native void btDefaultMotionState_startWorldTrans_set(long j, btDefaultMotionState btdefaultmotionstate, long j2, btTransform bttransform);

    public static final native long btDefaultMotionState_userPointer_get(long j, btDefaultMotionState btdefaultmotionstate);

    public static final native void btDefaultMotionState_userPointer_set(long j, btDefaultMotionState btdefaultmotionstate, long j2);

    public static final native long btDefaultSerializer_SWIGUpcast(long j);

    public static final native String btDefaultSerializer_getMemoryDna();

    public static final native int btDefaultSerializer_getMemoryDnaSizeInBytes();

    public static final native void btDefaultSerializer_insertHeader(long j, btDefaultSerializer btdefaultserializer);

    public static final native ByteBuffer btDefaultSerializer_internalAlloc(long j, btDefaultSerializer btdefaultserializer, long j2);

    public static final native long btDefaultSerializer_skipPointers_get(long j, btDefaultSerializer btdefaultserializer);

    public static final native void btDefaultSerializer_writeHeader(long j, btDefaultSerializer btdefaultserializer, ByteBuffer byteBuffer);

    public static final native float btDegrees(float f);

    public static final native float btDistance(Vector3 vector3, Vector3 vector4);

    public static final native float btDistance2(Vector3 vector3, Vector3 vector4);

    public static final native float btDot(Vector3 vector3, Vector3 vector4);

    public static final native boolean btEqual(float f, float f2);

    public static final native float btExp(float f);

    public static final native float btFabs(float f);

    public static final native float btFmod(float f, float f2);

    public static final native float btFsel(float f, float f2, float f3);

    public static final native boolean btFuzzyZero(float f);

    public static final native long btGEN_Link_getNext(long j, btGEN_Link btgen_link);

    public static final native long btGEN_Link_getPrev(long j, btGEN_Link btgen_link);

    public static final native void btGEN_Link_insertAfter(long j, btGEN_Link btgen_link, long j2, btGEN_Link btgen_link2);

    public static final native void btGEN_Link_insertBefore(long j, btGEN_Link btgen_link, long j2, btGEN_Link btgen_link2);

    public static final native boolean btGEN_Link_isHead(long j, btGEN_Link btgen_link);

    public static final native boolean btGEN_Link_isTail(long j, btGEN_Link btgen_link);

    public static final native void btGEN_Link_remove(long j, btGEN_Link btgen_link);

    public static final native void btGEN_List_addHead(long j, btGEN_List btgen_list, long j2, btGEN_Link btgen_link);

    public static final native void btGEN_List_addTail(long j, btGEN_List btgen_list, long j2, btGEN_Link btgen_link);

    public static final native long btGEN_List_getHead(long j, btGEN_List btgen_list);

    public static final native long btGEN_List_getTail(long j, btGEN_List btgen_list);

    public static final native boolean btGeometryUtil_areVerticesBehindPlane(Vector3 vector3, long j, btVector3Array btvector3array, float f);

    public static final native void btGeometryUtil_getPlaneEquationsFromVertices(long j, btVector3Array btvector3array, long j2, btVector3Array btvector3array2);

    public static final native void btGeometryUtil_getVerticesFromPlaneEquations(long j, btVector3Array btvector3array, long j2, btVector3Array btvector3array2);

    public static final native boolean btGeometryUtil_isInside(long j, btVector3Array btvector3array, Vector3 vector3, float f);

    public static final native boolean btGeometryUtil_isPointInsidePlanes(long j, btVector3Array btvector3array, Vector3 vector3, float f);

    public static final native long btGetCurrentEnterProfileZoneFunc();

    public static final native long btGetCurrentLeaveProfileZoneFunc();

    public static final native long btGetCurrentThreadIndex();

    public static final native long btGetOpenMPTaskScheduler();

    public static final native long btGetPPLTaskScheduler();

    public static final native long btGetSequentialTaskScheduler();

    public static final native long btGetTBBTaskScheduler();

    public static final native long btGetTaskScheduler();

    public static final native int btGetVersion();

    public static final native boolean btGreaterEqual(float f, float f2);

    public static final native boolean btHashInt_equals(long j, btHashInt bthashint, long j2, btHashInt bthashint2);

    public static final native long btHashInt_getHash(long j, btHashInt bthashint);

    public static final native int btHashInt_getUid1(long j, btHashInt bthashint);

    public static final native void btHashInt_setUid1(long j, btHashInt bthashint, int i);

    public static final native boolean btHashPtr_equals(long j, btHashPtr bthashptr, long j2, btHashPtr bthashptr2);

    public static final native long btHashPtr_getHash(long j, btHashPtr bthashptr);

    public static final native long btHashPtr_getPointer(long j, btHashPtr bthashptr);

    public static final native boolean btHashString_equals(long j, btHashString bthashstring, long j2, btHashString bthashstring2);

    public static final native long btHashString_hash_get(long j, btHashString bthashstring);

    public static final native void btHashString_hash_set(long j, btHashString bthashstring, long j2);

    public static final native int btHashString_portableStringCompare(long j, btHashString bthashstring, String str, String str2);

    public static final native String btHashString_string_get(long j, btHashString bthashstring);

    public static final native long btIDebugDraw_DefaultColors_aabb_get(long j, btIDebugDraw.DefaultColors defaultColors);

    public static final native void btIDebugDraw_DefaultColors_aabb_set(long j, btIDebugDraw.DefaultColors defaultColors, long j2, btVector3 btvector3);

    public static final native long btIDebugDraw_DefaultColors_activeObject_get(long j, btIDebugDraw.DefaultColors defaultColors);

    public static final native void btIDebugDraw_DefaultColors_activeObject_set(long j, btIDebugDraw.DefaultColors defaultColors, long j2, btVector3 btvector3);

    public static final native long btIDebugDraw_DefaultColors_contactPoint_get(long j, btIDebugDraw.DefaultColors defaultColors);

    public static final native void btIDebugDraw_DefaultColors_contactPoint_set(long j, btIDebugDraw.DefaultColors defaultColors, long j2, btVector3 btvector3);

    public static final native long btIDebugDraw_DefaultColors_deactivatedObject_get(long j, btIDebugDraw.DefaultColors defaultColors);

    public static final native void btIDebugDraw_DefaultColors_deactivatedObject_set(long j, btIDebugDraw.DefaultColors defaultColors, long j2, btVector3 btvector3);

    public static final native long btIDebugDraw_DefaultColors_disabledDeactivationObject_get(long j, btIDebugDraw.DefaultColors defaultColors);

    public static final native void btIDebugDraw_DefaultColors_disabledDeactivationObject_set(long j, btIDebugDraw.DefaultColors defaultColors, long j2, btVector3 btvector3);

    public static final native long btIDebugDraw_DefaultColors_disabledSimulationObject_get(long j, btIDebugDraw.DefaultColors defaultColors);

    public static final native void btIDebugDraw_DefaultColors_disabledSimulationObject_set(long j, btIDebugDraw.DefaultColors defaultColors, long j2, btVector3 btvector3);

    public static final native long btIDebugDraw_DefaultColors_wantsDeactivationObject_get(long j, btIDebugDraw.DefaultColors defaultColors);

    public static final native void btIDebugDraw_DefaultColors_wantsDeactivationObject_set(long j, btIDebugDraw.DefaultColors defaultColors, long j2, btVector3 btvector3);

    public static final native void btIDebugDraw_change_ownership(btIDebugDraw btidebugdraw, long j, boolean z);

    public static final native void btIDebugDraw_clearLines(long j, btIDebugDraw btidebugdraw);

    public static final native void btIDebugDraw_clearLinesSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw);

    public static final native void btIDebugDraw_director_connect(btIDebugDraw btidebugdraw, long j, boolean z, boolean z2);

    public static final native void btIDebugDraw_draw3dText(long j, btIDebugDraw btidebugdraw, Vector3 vector3, String str);

    public static final native void btIDebugDraw_drawAabb(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native void btIDebugDraw_drawAabbSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native void btIDebugDraw_drawArcSwigExplicitbtIDebugDraw__SWIG_0(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, Vector3 vector6, boolean z, float f5);

    public static final native void btIDebugDraw_drawArcSwigExplicitbtIDebugDraw__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, Vector3 vector6, boolean z);

    public static final native void btIDebugDraw_drawArc__SWIG_0(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, Vector3 vector6, boolean z, float f5);

    public static final native void btIDebugDraw_drawArc__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, Vector3 vector6, boolean z);

    public static final native void btIDebugDraw_drawBoxSwigExplicitbtIDebugDraw__SWIG_0(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native void btIDebugDraw_drawBoxSwigExplicitbtIDebugDraw__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Matrix4 matrix4, Vector3 vector5);

    public static final native void btIDebugDraw_drawBox__SWIG_0(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native void btIDebugDraw_drawBox__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Matrix4 matrix4, Vector3 vector5);

    public static final native void btIDebugDraw_drawCapsule(long j, btIDebugDraw btidebugdraw, float f, float f2, int i, Matrix4 matrix4, Vector3 vector3);

    public static final native void btIDebugDraw_drawCapsuleSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw, float f, float f2, int i, Matrix4 matrix4, Vector3 vector3);

    public static final native void btIDebugDraw_drawCone(long j, btIDebugDraw btidebugdraw, float f, float f2, int i, Matrix4 matrix4, Vector3 vector3);

    public static final native void btIDebugDraw_drawConeSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw, float f, float f2, int i, Matrix4 matrix4, Vector3 vector3);

    public static final native void btIDebugDraw_drawContactPoint(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, float f, int i, Vector3 vector5);

    public static final native void btIDebugDraw_drawCylinder(long j, btIDebugDraw btidebugdraw, float f, float f2, int i, Matrix4 matrix4, Vector3 vector3);

    public static final native void btIDebugDraw_drawCylinderSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw, float f, float f2, int i, Matrix4 matrix4, Vector3 vector3);

    public static final native void btIDebugDraw_drawLineSwigExplicitbtIDebugDraw__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native void btIDebugDraw_drawLine__SWIG_0(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native void btIDebugDraw_drawLine__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6);

    public static final native void btIDebugDraw_drawPlane(long j, btIDebugDraw btidebugdraw, Vector3 vector3, float f, Matrix4 matrix4, Vector3 vector4);

    public static final native void btIDebugDraw_drawPlaneSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw, Vector3 vector3, float f, Matrix4 matrix4, Vector3 vector4);

    public static final native void btIDebugDraw_drawSpherePatchSwigExplicitbtIDebugDraw__SWIG_0(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6, float f6, boolean z);

    public static final native void btIDebugDraw_drawSpherePatchSwigExplicitbtIDebugDraw__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6, float f6);

    public static final native void btIDebugDraw_drawSpherePatchSwigExplicitbtIDebugDraw__SWIG_2(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6);

    public static final native void btIDebugDraw_drawSpherePatch__SWIG_0(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6, float f6, boolean z);

    public static final native void btIDebugDraw_drawSpherePatch__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6, float f6);

    public static final native void btIDebugDraw_drawSpherePatch__SWIG_2(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6);

    public static final native void btIDebugDraw_drawSphereSwigExplicitbtIDebugDraw__SWIG_0(long j, btIDebugDraw btidebugdraw, float f, Matrix4 matrix4, Vector3 vector3);

    public static final native void btIDebugDraw_drawSphereSwigExplicitbtIDebugDraw__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, float f, Vector3 vector4);

    public static final native void btIDebugDraw_drawSphere__SWIG_0(long j, btIDebugDraw btidebugdraw, float f, Matrix4 matrix4, Vector3 vector3);

    public static final native void btIDebugDraw_drawSphere__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, float f, Vector3 vector4);

    public static final native void btIDebugDraw_drawTransform(long j, btIDebugDraw btidebugdraw, Matrix4 matrix4, float f);

    public static final native void btIDebugDraw_drawTransformSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw, Matrix4 matrix4, float f);

    public static final native void btIDebugDraw_drawTriangleSwigExplicitbtIDebugDraw__SWIG_0(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8, Vector3 vector9, float f);

    public static final native void btIDebugDraw_drawTriangleSwigExplicitbtIDebugDraw__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f);

    public static final native void btIDebugDraw_drawTriangle__SWIG_0(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8, Vector3 vector9, float f);

    public static final native void btIDebugDraw_drawTriangle__SWIG_1(long j, btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f);

    public static final native void btIDebugDraw_flushLines(long j, btIDebugDraw btidebugdraw);

    public static final native void btIDebugDraw_flushLinesSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw);

    public static final native int btIDebugDraw_getDebugMode(long j, btIDebugDraw btidebugdraw);

    public static final native long btIDebugDraw_getDefaultColors(long j, btIDebugDraw btidebugdraw);

    public static final native long btIDebugDraw_getDefaultColorsSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw);

    public static final native void btIDebugDraw_reportErrorWarning(long j, btIDebugDraw btidebugdraw, String str);

    public static final native void btIDebugDraw_setDebugMode(long j, btIDebugDraw btidebugdraw, int i);

    public static final native void btIDebugDraw_setDefaultColors(long j, btIDebugDraw btidebugdraw, long j2, btIDebugDraw.DefaultColors defaultColors);

    public static final native void btIDebugDraw_setDefaultColorsSwigExplicitbtIDebugDraw(long j, btIDebugDraw btidebugdraw, long j2, btIDebugDraw.DefaultColors defaultColors);

    public static final native void btIParallelForBody_forLoop(long j, btIParallelForBody btiparallelforbody, int i, int i2);

    public static final native void btITaskScheduler_activate(long j, btITaskScheduler btitaskscheduler);

    public static final native void btITaskScheduler_deactivate(long j, btITaskScheduler btitaskscheduler);

    public static final native int btITaskScheduler_getMaxNumThreads(long j, btITaskScheduler btitaskscheduler);

    public static final native String btITaskScheduler_getName(long j, btITaskScheduler btitaskscheduler);

    public static final native int btITaskScheduler_getNumThreads(long j, btITaskScheduler btitaskscheduler);

    public static final native void btITaskScheduler_parallelFor(long j, btITaskScheduler btitaskscheduler, int i, int i2, int i3, long j2, btIParallelForBody btiparallelforbody);

    public static final native void btITaskScheduler_setNumThreads(long j, btITaskScheduler btitaskscheduler, int i);

    public static final native boolean btIntSortPredicate_operatorFunctionCall(long j, btIntSortPredicate btintsortpredicate, int i, int i2);

    public static final native boolean btIsMainThread();

    public static final native int btIsNegative(float f);

    public static final native float btLargeDot(FloatBuffer floatBuffer, FloatBuffer floatBuffer2, int i);

    public static final native float btLog(float f);

    public static final native boolean btMachineIsLittleEndian();

    public static final native long btMatrix3x3DoubleData_el_get(long j, btMatrix3x3DoubleData btmatrix3x3doubledata);

    public static final native void btMatrix3x3DoubleData_el_set(long j, btMatrix3x3DoubleData btmatrix3x3doubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btMatrix3x3FloatData_el_get(long j, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native void btMatrix3x3FloatData_el_set(long j, btMatrix3x3FloatData btmatrix3x3floatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native Matrix3 btMatrix3x3_absolute(long j, btMatrix3x3 btmatrix3x3);

    public static final native Matrix3 btMatrix3x3_adjoint(long j, btMatrix3x3 btmatrix3x3);

    public static final native float btMatrix3x3_cofac(long j, btMatrix3x3 btmatrix3x3, int i, int i2, int i3, int i4);

    public static final native void btMatrix3x3_deSerialize(long j, btMatrix3x3 btmatrix3x3, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native void btMatrix3x3_deSerializeDouble(long j, btMatrix3x3 btmatrix3x3, long j2, btMatrix3x3DoubleData btmatrix3x3doubledata);

    public static final native void btMatrix3x3_deSerializeFloat(long j, btMatrix3x3 btmatrix3x3, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native float btMatrix3x3_determinant(long j, btMatrix3x3 btmatrix3x3);

    public static final native void btMatrix3x3_diagonalize__SWIG_0(long j, btMatrix3x3 btmatrix3x3, Matrix3 matrix3, float f, int i);

    public static final native void btMatrix3x3_diagonalize__SWIG_1(long j, btMatrix3x3 btmatrix3x3, Matrix3 matrix3, float f);

    public static final native void btMatrix3x3_diagonalize__SWIG_2(long j, btMatrix3x3 btmatrix3x3, Matrix3 matrix3);

    public static final native void btMatrix3x3_extractRotation__SWIG_0(long j, btMatrix3x3 btmatrix3x3, Quaternion quaternion, float f, int i);

    public static final native void btMatrix3x3_extractRotation__SWIG_1(long j, btMatrix3x3 btmatrix3x3, Quaternion quaternion, float f);

    public static final native void btMatrix3x3_extractRotation__SWIG_2(long j, btMatrix3x3 btmatrix3x3, Quaternion quaternion);

    public static final native Vector3 btMatrix3x3_getColumn(long j, btMatrix3x3 btmatrix3x3, int i);

    public static final native void btMatrix3x3_getEulerYPR(long j, btMatrix3x3 btmatrix3x3, long j2, long j3, long j4);

    public static final native void btMatrix3x3_getEulerZYX__SWIG_0(long j, btMatrix3x3 btmatrix3x3, long j2, long j3, long j4, long j5);

    public static final native void btMatrix3x3_getEulerZYX__SWIG_1(long j, btMatrix3x3 btmatrix3x3, long j2, long j3, long j4);

    public static final native Matrix3 btMatrix3x3_getIdentity();

    public static final native void btMatrix3x3_getOpenGLSubMatrix(long j, btMatrix3x3 btmatrix3x3, FloatBuffer floatBuffer);

    public static final native void btMatrix3x3_getRotation(long j, btMatrix3x3 btmatrix3x3, Quaternion quaternion);

    public static final native Vector3 btMatrix3x3_getRow(long j, btMatrix3x3 btmatrix3x3, int i);

    public static final native Matrix3 btMatrix3x3_inverse(long j, btMatrix3x3 btmatrix3x3);

    public static final native Matrix3 btMatrix3x3_operatorAdditionAssignment(long j, btMatrix3x3 btmatrix3x3, Matrix3 matrix3);

    public static final native Matrix3 btMatrix3x3_operatorAssignment(long j, btMatrix3x3 btmatrix3x3, Matrix3 matrix3);

    public static final native Matrix3 btMatrix3x3_operatorMultiplicationAssignment(long j, btMatrix3x3 btmatrix3x3, Matrix3 matrix3);

    public static final native Vector3 btMatrix3x3_operatorSubscript(long j, btMatrix3x3 btmatrix3x3, int i);

    public static final native Vector3 btMatrix3x3_operatorSubscriptConst(long j, btMatrix3x3 btmatrix3x3, int i);

    public static final native Matrix3 btMatrix3x3_operatorSubtractionAssignment(long j, btMatrix3x3 btmatrix3x3, Matrix3 matrix3);

    public static final native Matrix3 btMatrix3x3_scaled(long j, btMatrix3x3 btmatrix3x3, Vector3 vector3);

    public static final native void btMatrix3x3_serialize(long j, btMatrix3x3 btmatrix3x3, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native void btMatrix3x3_serializeFloat(long j, btMatrix3x3 btmatrix3x3, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native void btMatrix3x3_setEulerYPR(long j, btMatrix3x3 btmatrix3x3, float f, float f2, float f3);

    public static final native void btMatrix3x3_setEulerZYX(long j, btMatrix3x3 btmatrix3x3, float f, float f2, float f3);

    public static final native void btMatrix3x3_setFromOpenGLSubMatrix(long j, btMatrix3x3 btmatrix3x3, FloatBuffer floatBuffer);

    public static final native void btMatrix3x3_setIdentity(long j, btMatrix3x3 btmatrix3x3);

    public static final native void btMatrix3x3_setRotation(long j, btMatrix3x3 btmatrix3x3, Quaternion quaternion);

    public static final native void btMatrix3x3_setValue(long j, btMatrix3x3 btmatrix3x3, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9);

    public static final native Vector3 btMatrix3x3_solve33(long j, btMatrix3x3 btmatrix3x3, Vector3 vector3);

    public static final native float btMatrix3x3_tdotx(long j, btMatrix3x3 btmatrix3x3, Vector3 vector3);

    public static final native float btMatrix3x3_tdoty(long j, btMatrix3x3 btmatrix3x3, Vector3 vector3);

    public static final native float btMatrix3x3_tdotz(long j, btMatrix3x3 btmatrix3x3, Vector3 vector3);

    public static final native Matrix3 btMatrix3x3_timesTranspose(long j, btMatrix3x3 btmatrix3x3, Matrix3 matrix3);

    public static final native Matrix3 btMatrix3x3_transpose(long j, btMatrix3x3 btmatrix3x3);

    public static final native Matrix3 btMatrix3x3_transposeTimes(long j, btMatrix3x3 btmatrix3x3, Matrix3 matrix3);

    public static final native void btMotionState_change_ownership(btMotionState btmotionstate, long j, boolean z);

    public static final native void btMotionState_director_connect(btMotionState btmotionstate, long j, boolean z, boolean z2);

    public static final native void btMotionState_getWorldTransform(long j, btMotionState btmotionstate, Matrix4 matrix4);

    public static final native void btMotionState_setWorldTransform(long j, btMotionState btmotionstate, Matrix4 matrix4);

    public static final native void btMutexLock(long j, btSpinMutex btspinmutex);

    public static final native boolean btMutexTryLock(long j, btSpinMutex btspinmutex);

    public static final native void btMutexUnlock(long j, btSpinMutex btspinmutex);

    public static final native float btNormalizeAngle(float f);

    public static final native int btOutcode(Vector3 vector3, Vector3 vector4);

    public static final native void btParallelFor(int i, int i2, int i3, long j, btIParallelForBody btiparallelforbody);

    public static final native float btPlane_dist_get(long j, btPlane btplane);

    public static final native void btPlane_dist_set(long j, btPlane btplane, float f);

    public static final native long btPlane_normal_get(long j, btPlane btplane);

    public static final native void btPlane_normal_set(long j, btPlane btplane, long j2, btVector3 btvector3);

    public static final native long btPointerUid_ptr_get(long j, btPointerUid btpointeruid);

    public static final native void btPointerUid_ptr_set(long j, btPointerUid btpointeruid, long j2);

    public static final native int[] btPointerUid_uniqueIds_get(long j, btPointerUid btpointeruid);

    public static final native void btPointerUid_uniqueIds_set(long j, btPointerUid btpointeruid, int[] iArr);

    public static final native long btPolarDecomposition_decompose(long j, btPolarDecomposition btpolardecomposition, Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5);

    public static final native long btPolarDecomposition_maxIterations(long j, btPolarDecomposition btpolardecomposition);

    public static final native long btPoolAllocator_allocate(long j, btPoolAllocator btpoolallocator, int i);

    public static final native void btPoolAllocator_freeMemory(long j, btPoolAllocator btpoolallocator, long j2);

    public static final native int btPoolAllocator_getElementSize(long j, btPoolAllocator btpoolallocator);

    public static final native int btPoolAllocator_getFreeCount(long j, btPoolAllocator btpoolallocator);

    public static final native int btPoolAllocator_getMaxCount(long j, btPoolAllocator btpoolallocator);

    public static final native ByteBuffer btPoolAllocator_getPoolAddress(long j, btPoolAllocator btpoolallocator);

    public static final native ByteBuffer btPoolAllocator_getPoolAddressConst(long j, btPoolAllocator btpoolallocator);

    public static final native int btPoolAllocator_getUsedCount(long j, btPoolAllocator btpoolallocator);

    public static final native boolean btPoolAllocator_validPtr(long j, btPoolAllocator btpoolallocator, long j2);

    public static final native float btPow(float f, float f2);

    public static final native float btQuadWord_getX(long j, btQuadWord btquadword);

    public static final native float btQuadWord_getY(long j, btQuadWord btquadword);

    public static final native float btQuadWord_getZ(long j, btQuadWord btquadword);

    public static final native boolean btQuadWord_operatorEqualTo(long j, btQuadWord btquadword, long j2, btQuadWord btquadword2);

    public static final native boolean btQuadWord_operatorNotEqualTo(long j, btQuadWord btquadword, long j2, btQuadWord btquadword2);

    public static final native FloatBuffer btQuadWord_operatorbtConstScalarPtr(long j, btQuadWord btquadword);

    public static final native FloatBuffer btQuadWord_operatorbtScalarPtr(long j, btQuadWord btquadword);

    public static final native void btQuadWord_setMax(long j, btQuadWord btquadword, long j2, btQuadWord btquadword2);

    public static final native void btQuadWord_setMin(long j, btQuadWord btquadword, long j2, btQuadWord btquadword2);

    public static final native void btQuadWord_setValue__SWIG_0(long j, btQuadWord btquadword, float f, float f2, float f3);

    public static final native void btQuadWord_setValue__SWIG_1(long j, btQuadWord btquadword, float f, float f2, float f3, float f4);

    public static final native void btQuadWord_setW(long j, btQuadWord btquadword, float f);

    public static final native void btQuadWord_setX(long j, btQuadWord btquadword, float f);

    public static final native void btQuadWord_setY(long j, btQuadWord btquadword, float f);

    public static final native void btQuadWord_setZ(long j, btQuadWord btquadword, float f);

    public static final native float btQuadWord_w(long j, btQuadWord btquadword);

    public static final native float btQuadWord_x(long j, btQuadWord btquadword);

    public static final native float btQuadWord_y(long j, btQuadWord btquadword);

    public static final native float btQuadWord_z(long j, btQuadWord btquadword);

    public static final native double[] btQuaternionDoubleData_floats_get(long j, btQuaternionDoubleData btquaterniondoubledata);

    public static final native void btQuaternionDoubleData_floats_set(long j, btQuaternionDoubleData btquaterniondoubledata, double[] dArr);

    public static final native float[] btQuaternionFloatData_floats_get(long j, btQuaternionFloatData btquaternionfloatdata);

    public static final native void btQuaternionFloatData_floats_set(long j, btQuaternionFloatData btquaternionfloatdata, float[] fArr);

    public static final native long btQuaternion_SWIGUpcast(long j);

    public static final native float btQuaternion_angle(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native float btQuaternion_angleShortestPath(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native void btQuaternion_deSerialize(long j, btQuaternion btquaternion, long j2, btQuaternionFloatData btquaternionfloatdata);

    public static final native void btQuaternion_deSerializeDouble(long j, btQuaternion btquaternion, long j2, btQuaternionDoubleData btquaterniondoubledata);

    public static final native void btQuaternion_deSerializeFloat(long j, btQuaternion btquaternion, long j2, btQuaternionFloatData btquaternionfloatdata);

    public static final native float btQuaternion_dot(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native Quaternion btQuaternion_farthest(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native float btQuaternion_getAngle(long j, btQuaternion btquaternion);

    public static final native float btQuaternion_getAngleShortestPath(long j, btQuaternion btquaternion);

    public static final native Vector3 btQuaternion_getAxis(long j, btQuaternion btquaternion);

    public static final native void btQuaternion_getEulerZYX(long j, btQuaternion btquaternion, long j2, long j3, long j4);

    public static final native Quaternion btQuaternion_getIdentity();

    public static final native float btQuaternion_getW(long j, btQuaternion btquaternion);

    public static final native Quaternion btQuaternion_inverse(long j, btQuaternion btquaternion);

    public static final native float btQuaternion_length(long j, btQuaternion btquaternion);

    public static final native float btQuaternion_length2(long j, btQuaternion btquaternion);

    public static final native Quaternion btQuaternion_nearest(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native Quaternion btQuaternion_normalize(long j, btQuaternion btquaternion);

    public static final native Quaternion btQuaternion_normalized(long j, btQuaternion btquaternion);

    public static final native Quaternion btQuaternion_operatorAddition(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native Quaternion btQuaternion_operatorAdditionAssignment(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native Quaternion btQuaternion_operatorDivision(long j, btQuaternion btquaternion, float f);

    public static final native Quaternion btQuaternion_operatorDivisionAssignment(long j, btQuaternion btquaternion, float f);

    public static final native Quaternion btQuaternion_operatorMultiplication(long j, btQuaternion btquaternion, float f);

    public static final native Quaternion btQuaternion_operatorMultiplicationAssignment__SWIG_0(long j, btQuaternion btquaternion, float f);

    public static final native Quaternion btQuaternion_operatorMultiplicationAssignment__SWIG_1(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native Quaternion btQuaternion_operatorSubtractionAssignment(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native Quaternion btQuaternion_operatorSubtraction__SWIG_0(long j, btQuaternion btquaternion, Quaternion quaternion);

    public static final native Quaternion btQuaternion_operatorSubtraction__SWIG_1(long j, btQuaternion btquaternion);

    public static final native Quaternion btQuaternion_safeNormalize(long j, btQuaternion btquaternion);

    public static final native void btQuaternion_serialize(long j, btQuaternion btquaternion, long j2, btQuaternionFloatData btquaternionfloatdata);

    public static final native void btQuaternion_serializeDouble(long j, btQuaternion btquaternion, long j2, btQuaternionDoubleData btquaterniondoubledata);

    public static final native void btQuaternion_serializeFloat(long j, btQuaternion btquaternion, long j2, btQuaternionFloatData btquaternionfloatdata);

    public static final native void btQuaternion_setEuler(long j, btQuaternion btquaternion, float f, float f2, float f3);

    public static final native void btQuaternion_setEulerZYX(long j, btQuaternion btquaternion, float f, float f2, float f3);

    public static final native void btQuaternion_setRotation(long j, btQuaternion btquaternion, Vector3 vector3, float f);

    public static final native Quaternion btQuaternion_slerp(long j, btQuaternion btquaternion, Quaternion quaternion, float f);

    public static final native float btRadians(float f);

    public static final native boolean btRayAabb(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, long j, Vector3 vector7);

    public static final native boolean btRayAabb2(Vector3 vector3, Vector3 vector4, long[] jArr, long j, btVector3 btvector3, long j2, float f, float f2);

    public static final native void btResetThreadIndexCounter();

    public static final native long btScalarArray_at(long j, btScalarArray btscalararray, int i);

    public static final native float btScalarArray_atConst(long j, btScalarArray btscalararray, int i);

    public static final native int btScalarArray_capacity(long j, btScalarArray btscalararray);

    public static final native void btScalarArray_clear(long j, btScalarArray btscalararray);

    public static final native void btScalarArray_copyFromArray(long j, btScalarArray btscalararray, long j2, btScalarArray btscalararray2);

    public static final native long btScalarArray_expandNonInitializing(long j, btScalarArray btscalararray);

    public static final native long btScalarArray_expand__SWIG_0(long j, btScalarArray btscalararray, float f);

    public static final native long btScalarArray_expand__SWIG_1(long j, btScalarArray btscalararray);

    public static final native int btScalarArray_findBinarySearch(long j, btScalarArray btscalararray, float f);

    public static final native int btScalarArray_findLinearSearch(long j, btScalarArray btscalararray, float f);

    public static final native int btScalarArray_findLinearSearch2(long j, btScalarArray btscalararray, float f);

    public static final native void btScalarArray_initializeFromBuffer(long j, btScalarArray btscalararray, long j2, int i, int i2);

    public static final native boolean btScalarArray_less_operatorFunctionCall(long j, float f, float f2);

    public static final native long btScalarArray_operatorAssignment(long j, btScalarArray btscalararray, long j2, btScalarArray btscalararray2);

    public static final native long btScalarArray_operatorSubscript(long j, btScalarArray btscalararray, int i);

    public static final native float btScalarArray_operatorSubscriptConst(long j, btScalarArray btscalararray, int i);

    public static final native void btScalarArray_pop_back(long j, btScalarArray btscalararray);

    public static final native void btScalarArray_push_back(long j, btScalarArray btscalararray, float f);

    public static final native void btScalarArray_remove(long j, btScalarArray btscalararray, float f);

    public static final native void btScalarArray_removeAtIndex(long j, btScalarArray btscalararray, int i);

    public static final native void btScalarArray_reserve(long j, btScalarArray btscalararray, int i);

    public static final native void btScalarArray_resizeNoInitialize(long j, btScalarArray btscalararray, int i);

    public static final native void btScalarArray_resize__SWIG_0(long j, btScalarArray btscalararray, int i, float f);

    public static final native void btScalarArray_resize__SWIG_1(long j, btScalarArray btscalararray, int i);

    public static final native int btScalarArray_size(long j, btScalarArray btscalararray);

    public static final native void btScalarArray_swap(long j, btScalarArray btscalararray, int i, int i2);

    public static final native long btSelect__SWIG_0(long j, long j2, long j3);

    public static final native int btSelect__SWIG_1(long j, int i, int i2);

    public static final native float btSelect__SWIG_2(long j, float f, float f2);

    public static final native long btSerializer_allocate(long j, btSerializer btserializer, long j2, int i);

    public static final native void btSerializer_finalizeChunk(long j, btSerializer btserializer, long j2, btChunk btchunk, String str, int i, long j3);

    public static final native String btSerializer_findNameForPointer(long j, btSerializer btserializer, long j2);

    public static final native long btSerializer_findPointer(long j, btSerializer btserializer, long j2);

    public static final native void btSerializer_finishSerialization(long j, btSerializer btserializer);

    public static final native ByteBuffer btSerializer_getBufferPointer(long j, btSerializer btserializer);

    public static final native long btSerializer_getChunk(long j, btSerializer btserializer, int i);

    public static final native int btSerializer_getCurrentBufferSize(long j, btSerializer btserializer);

    public static final native int btSerializer_getNumChunks(long j, btSerializer btserializer);

    public static final native int btSerializer_getSerializationFlags(long j, btSerializer btserializer);

    public static final native long btSerializer_getUniquePointer(long j, btSerializer btserializer, long j2);

    public static final native void btSerializer_registerNameForPointer(long j, btSerializer btserializer, long j2, String str);

    public static final native void btSerializer_serializeName(long j, btSerializer btserializer, String str);

    public static final native void btSerializer_setSerializationFlags(long j, btSerializer btserializer, int i);

    public static final native void btSerializer_startSerialization(long j, btSerializer btserializer);

    public static final native void btSetCustomEnterProfileZoneFunc(long j);

    public static final native void btSetCustomLeaveProfileZoneFunc(long j);

    public static final native void btSetTaskScheduler(long j, btITaskScheduler btitaskscheduler);

    public static final native float btSin(float f);

    public static final native void btSpatialForceVector_addAngular(long j, btSpatialForceVector btspatialforcevector, Vector3 vector3);

    public static final native void btSpatialForceVector_addLinear(long j, btSpatialForceVector btspatialforcevector, Vector3 vector3);

    public static final native void btSpatialForceVector_addValue(long j, btSpatialForceVector btspatialforcevector, float f, float f2, float f3, float f4, float f5, float f6);

    public static final native void btSpatialForceVector_addVector(long j, btSpatialForceVector btspatialforcevector, Vector3 vector3, Vector3 vector4);

    public static final native long btSpatialForceVector_bottomVec_get(long j, btSpatialForceVector btspatialforcevector);

    public static final native void btSpatialForceVector_bottomVec_set(long j, btSpatialForceVector btspatialforcevector, long j2, btVector3 btvector3);

    public static final native Vector3 btSpatialForceVector_getAngular(long j, btSpatialForceVector btspatialforcevector);

    public static final native Vector3 btSpatialForceVector_getLinear(long j, btSpatialForceVector btspatialforcevector);

    public static final native long btSpatialForceVector_operatorAddition(long j, btSpatialForceVector btspatialforcevector, long j2, btSpatialForceVector btspatialforcevector2);

    public static final native long btSpatialForceVector_operatorAdditionAssignment(long j, btSpatialForceVector btspatialforcevector, long j2, btSpatialForceVector btspatialforcevector2);

    public static final native long btSpatialForceVector_operatorMultiplication(long j, btSpatialForceVector btspatialforcevector, float f);

    public static final native long btSpatialForceVector_operatorSubtractionAssignment(long j, btSpatialForceVector btspatialforcevector, long j2, btSpatialForceVector btspatialforcevector2);

    public static final native long btSpatialForceVector_operatorSubtraction__SWIG_0(long j, btSpatialForceVector btspatialforcevector, long j2, btSpatialForceVector btspatialforcevector2);

    public static final native long btSpatialForceVector_operatorSubtraction__SWIG_1(long j, btSpatialForceVector btspatialforcevector);

    public static final native void btSpatialForceVector_setAngular(long j, btSpatialForceVector btspatialforcevector, Vector3 vector3);

    public static final native void btSpatialForceVector_setLinear(long j, btSpatialForceVector btspatialforcevector, Vector3 vector3);

    public static final native void btSpatialForceVector_setValue(long j, btSpatialForceVector btspatialforcevector, float f, float f2, float f3, float f4, float f5, float f6);

    public static final native void btSpatialForceVector_setVector(long j, btSpatialForceVector btspatialforcevector, Vector3 vector3, Vector3 vector4);

    public static final native void btSpatialForceVector_setZero(long j, btSpatialForceVector btspatialforcevector);

    public static final native long btSpatialForceVector_topVec_get(long j, btSpatialForceVector btspatialforcevector);

    public static final native void btSpatialForceVector_topVec_set(long j, btSpatialForceVector btspatialforcevector, long j2, btVector3 btvector3);

    public static final native void btSpatialMotionVector_addAngular(long j, btSpatialMotionVector btspatialmotionvector, Vector3 vector3);

    public static final native void btSpatialMotionVector_addLinear(long j, btSpatialMotionVector btspatialmotionvector, Vector3 vector3);

    public static final native void btSpatialMotionVector_addValue(long j, btSpatialMotionVector btspatialmotionvector, float f, float f2, float f3, float f4, float f5, float f6);

    public static final native void btSpatialMotionVector_addVector(long j, btSpatialMotionVector btspatialmotionvector, Vector3 vector3, Vector3 vector4);

    public static final native long btSpatialMotionVector_bottomVec_get(long j, btSpatialMotionVector btspatialmotionvector);

    public static final native void btSpatialMotionVector_bottomVec_set(long j, btSpatialMotionVector btspatialmotionvector, long j2, btVector3 btvector3);

    public static final native float btSpatialMotionVector_dot(long j, btSpatialMotionVector btspatialmotionvector, long j2, btSpatialForceVector btspatialforcevector);

    public static final native Vector3 btSpatialMotionVector_getAngular(long j, btSpatialMotionVector btspatialmotionvector);

    public static final native Vector3 btSpatialMotionVector_getLinear(long j, btSpatialMotionVector btspatialmotionvector);

    public static final native long btSpatialMotionVector_operatorAddition(long j, btSpatialMotionVector btspatialmotionvector, long j2, btSpatialMotionVector btspatialmotionvector2);

    public static final native long btSpatialMotionVector_operatorAdditionAssignment(long j, btSpatialMotionVector btspatialmotionvector, long j2, btSpatialMotionVector btspatialmotionvector2);

    public static final native long btSpatialMotionVector_operatorMultiplication(long j, btSpatialMotionVector btspatialmotionvector, float f);

    public static final native long btSpatialMotionVector_operatorMultiplicationAssignment(long j, btSpatialMotionVector btspatialmotionvector, float f);

    public static final native long btSpatialMotionVector_operatorSubtractionAssignment(long j, btSpatialMotionVector btspatialmotionvector, long j2, btSpatialMotionVector btspatialmotionvector2);

    public static final native long btSpatialMotionVector_operatorSubtraction__SWIG_0(long j, btSpatialMotionVector btspatialmotionvector, long j2, btSpatialMotionVector btspatialmotionvector2);

    public static final native long btSpatialMotionVector_operatorSubtraction__SWIG_1(long j, btSpatialMotionVector btspatialmotionvector);

    public static final native void btSpatialMotionVector_setAngular(long j, btSpatialMotionVector btspatialmotionvector, Vector3 vector3);

    public static final native void btSpatialMotionVector_setLinear(long j, btSpatialMotionVector btspatialmotionvector, Vector3 vector3);

    public static final native void btSpatialMotionVector_setValue(long j, btSpatialMotionVector btspatialmotionvector, float f, float f2, float f3, float f4, float f5, float f6);

    public static final native void btSpatialMotionVector_setVector(long j, btSpatialMotionVector btspatialmotionvector, Vector3 vector3, Vector3 vector4);

    public static final native void btSpatialMotionVector_setZero(long j, btSpatialMotionVector btspatialmotionvector);

    public static final native long btSpatialMotionVector_topVec_get(long j, btSpatialMotionVector btspatialmotionvector);

    public static final native void btSpatialMotionVector_topVec_set(long j, btSpatialMotionVector btspatialmotionvector, long j2, btVector3 btvector3);

    public static final native long btSpatialTransformationMatrix_rotMat_get(long j, btSpatialTransformationMatrix btspatialtransformationmatrix);

    public static final native void btSpatialTransformationMatrix_rotMat_set(long j, btSpatialTransformationMatrix btspatialtransformationmatrix, long j2, btMatrix3x3 btmatrix3x3);

    public static final native void btSpatialTransformationMatrix_transformInverse__SWIG_2(long j, btSpatialTransformationMatrix btspatialtransformationmatrix, long j2, btSymmetricSpatialDyad btsymmetricspatialdyad, long j3, btSymmetricSpatialDyad btsymmetricspatialdyad2, int i);

    public static final native void btSpatialTransformationMatrix_transformInverse__SWIG_3(long j, btSpatialTransformationMatrix btspatialtransformationmatrix, long j2, btSymmetricSpatialDyad btsymmetricspatialdyad, long j3, btSymmetricSpatialDyad btsymmetricspatialdyad2);

    public static final native long btSpatialTransformationMatrix_trnVec_get(long j, btSpatialTransformationMatrix btspatialtransformationmatrix);

    public static final native void btSpatialTransformationMatrix_trnVec_set(long j, btSpatialTransformationMatrix btspatialtransformationmatrix, long j2, btVector3 btvector3);

    public static final native void btSpinMutex_lock(long j, btSpinMutex btspinmutex);

    public static final native boolean btSpinMutex_tryLock(long j, btSpinMutex btspinmutex);

    public static final native void btSpinMutex_unlock(long j, btSpinMutex btspinmutex);

    public static final native float btSqrt(float f);

    public static final native ByteBuffer btStackAlloc_allocate(long j, btStackAlloc btstackalloc, long j2);

    public static final native long btStackAlloc_beginBlock(long j, btStackAlloc btstackalloc);

    public static final native void btStackAlloc_create(long j, btStackAlloc btstackalloc, long j2);

    public static final native void btStackAlloc_destroy(long j, btStackAlloc btstackalloc);

    public static final native void btStackAlloc_endBlock(long j, btStackAlloc btstackalloc, long j2, btBlock btblock);

    public static final native int btStackAlloc_getAvailableMemory(long j, btStackAlloc btstackalloc);

    public static final native int btStrLen(String str);

    public static final native void btSwapEndianDouble(double d, ByteBuffer byteBuffer);

    public static final native long btSwapEndianFloat(float f);

    public static final native long btSwapEndianInt(int i);

    public static final native long btSwapEndian__SWIG_0(long j);

    public static final native int btSwapEndian__SWIG_1(int i);

    public static final native int btSwapEndian__SWIG_2(short s);

    public static final native void btSwapScalarEndian(float f, long j);

    public static final native void btSwapVector3Endian(Vector3 vector3, Vector3 vector4);

    public static final native void btSymmetricSpatialDyad_addMatrix(long j, btSymmetricSpatialDyad btsymmetricspatialdyad, Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5);

    public static final native long btSymmetricSpatialDyad_bottomLeftMat_get(long j, btSymmetricSpatialDyad btsymmetricspatialdyad);

    public static final native void btSymmetricSpatialDyad_bottomLeftMat_set(long j, btSymmetricSpatialDyad btsymmetricspatialdyad, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long btSymmetricSpatialDyad_operatorMultiplication(long j, btSymmetricSpatialDyad btsymmetricspatialdyad, long j2, btSpatialMotionVector btspatialmotionvector);

    public static final native long btSymmetricSpatialDyad_operatorSubtractionAssignment(long j, btSymmetricSpatialDyad btsymmetricspatialdyad, long j2, btSymmetricSpatialDyad btsymmetricspatialdyad2);

    public static final native void btSymmetricSpatialDyad_setIdentity(long j, btSymmetricSpatialDyad btsymmetricspatialdyad);

    public static final native void btSymmetricSpatialDyad_setMatrix(long j, btSymmetricSpatialDyad btsymmetricspatialdyad, Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5);

    public static final native long btSymmetricSpatialDyad_topLeftMat_get(long j, btSymmetricSpatialDyad btsymmetricspatialdyad);

    public static final native void btSymmetricSpatialDyad_topLeftMat_set(long j, btSymmetricSpatialDyad btsymmetricspatialdyad, long j2, btMatrix3x3 btmatrix3x3);

    public static final native long btSymmetricSpatialDyad_topRightMat_get(long j, btSymmetricSpatialDyad btsymmetricspatialdyad);

    public static final native void btSymmetricSpatialDyad_topRightMat_set(long j, btSymmetricSpatialDyad btsymmetricspatialdyad, long j2, btMatrix3x3 btmatrix3x3);

    public static final native float btTan(float f);

    public static final native boolean btThreadsAreRunning();

    public static final native void btTransformAabb__SWIG_0(Vector3 vector3, float f, Matrix4 matrix4, Vector3 vector4, Vector3 vector5);

    public static final native void btTransformAabb__SWIG_1(Vector3 vector3, Vector3 vector4, float f, Matrix4 matrix4, Vector3 vector5, Vector3 vector6);

    public static final native long btTransformDoubleData_basis_get(long j, btTransformDoubleData bttransformdoubledata);

    public static final native void btTransformDoubleData_basis_set(long j, btTransformDoubleData bttransformdoubledata, long j2, btMatrix3x3DoubleData btmatrix3x3doubledata);

    public static final native long btTransformDoubleData_origin_get(long j, btTransformDoubleData bttransformdoubledata);

    public static final native void btTransformDoubleData_origin_set(long j, btTransformDoubleData bttransformdoubledata, long j2, btVector3DoubleData btvector3doubledata);

    public static final native long btTransformFloatData_basis_get(long j, btTransformFloatData bttransformfloatdata);

    public static final native void btTransformFloatData_basis_set(long j, btTransformFloatData bttransformfloatdata, long j2, btMatrix3x3FloatData btmatrix3x3floatdata);

    public static final native long btTransformFloatData_origin_get(long j, btTransformFloatData bttransformfloatdata);

    public static final native void btTransformFloatData_origin_set(long j, btTransformFloatData bttransformfloatdata, long j2, btVector3FloatData btvector3floatdata);

    public static final native void btTransformUtil_calculateDiffAxisAngle(Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, long j);

    public static final native void btTransformUtil_calculateDiffAxisAngleQuaternion(Quaternion quaternion, Quaternion quaternion2, Vector3 vector3, long j);

    public static final native void btTransformUtil_calculateVelocity(Matrix4 matrix4, Matrix4 matrix5, float f, Vector3 vector3, Vector3 vector4);

    public static final native void btTransformUtil_calculateVelocityQuaternion(Vector3 vector3, Vector3 vector4, Quaternion quaternion, Quaternion quaternion2, float f, Vector3 vector5, Vector3 vector6);

    public static final native void btTransformUtil_integrateTransform(Matrix4 matrix4, Vector3 vector3, Vector3 vector4, float f, Matrix4 matrix5);

    public static final native void btTransform_deSerialize(long j, btTransform bttransform, long j2, btTransformFloatData bttransformfloatdata);

    public static final native void btTransform_deSerializeDouble(long j, btTransform bttransform, long j2, btTransformDoubleData bttransformdoubledata);

    public static final native void btTransform_deSerializeFloat(long j, btTransform bttransform, long j2, btTransformFloatData bttransformfloatdata);

    public static final native Matrix3 btTransform_getBasis(long j, btTransform bttransform);

    public static final native Matrix3 btTransform_getBasisConst(long j, btTransform bttransform);

    public static final native Matrix4 btTransform_getIdentity();

    public static final native void btTransform_getOpenGLMatrix(long j, btTransform bttransform, float[] fArr);

    public static final native Vector3 btTransform_getOrigin(long j, btTransform bttransform);

    public static final native Vector3 btTransform_getOriginConst(long j, btTransform bttransform);

    public static final native Quaternion btTransform_getRotation(long j, btTransform bttransform);

    public static final native Vector3 btTransform_invXform(long j, btTransform bttransform, Vector3 vector3);

    public static final native Matrix4 btTransform_inverse(long j, btTransform bttransform);

    public static final native Matrix4 btTransform_inverseTimes(long j, btTransform bttransform, Matrix4 matrix4);

    public static final native void btTransform_mult(long j, btTransform bttransform, Matrix4 matrix4, Matrix4 matrix5);

    public static final native Matrix4 btTransform_operatorAssignment(long j, btTransform bttransform, Matrix4 matrix4);

    public static final native Vector3 btTransform_operatorFunctionCall(long j, btTransform bttransform, Vector3 vector3);

    public static final native Matrix4 btTransform_operatorMultiplicationAssignment(long j, btTransform bttransform, Matrix4 matrix4);

    public static final native Vector3 btTransform_operatorMultiplication__SWIG_0(long j, btTransform bttransform, Vector3 vector3);

    public static final native Quaternion btTransform_operatorMultiplication__SWIG_1(long j, btTransform bttransform, Quaternion quaternion);

    public static final native Matrix4 btTransform_operatorMultiplication__SWIG_2(long j, btTransform bttransform, Matrix4 matrix4);

    public static final native void btTransform_serialize(long j, btTransform bttransform, long j2, btTransformFloatData bttransformfloatdata);

    public static final native void btTransform_serializeFloat(long j, btTransform bttransform, long j2, btTransformFloatData bttransformfloatdata);

    public static final native void btTransform_setBasis(long j, btTransform bttransform, Matrix3 matrix3);

    public static final native void btTransform_setFromOpenGLMatrix(long j, btTransform bttransform, float[] fArr);

    public static final native void btTransform_setIdentity(long j, btTransform bttransform);

    public static final native void btTransform_setOrigin(long j, btTransform bttransform, Vector3 vector3);

    public static final native void btTransform_setRotation(long j, btTransform bttransform, Quaternion quaternion);

    public static final native float btTriple(Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native int btTypedObject_objectType_get(long j, btTypedObject bttypedobject);

    public static final native void btTypedObject_objectType_set(long j, btTypedObject bttypedobject, int i);

    public static final native void btUnSwapVector3Endian(Vector3 vector3);

    public static final native double btUnswapEndianDouble(ByteBuffer byteBuffer);

    public static final native float btUnswapEndianFloat(long j);

    public static final native Vector3 btVector3Array_at(long j, btVector3Array btvector3array, int i);

    public static final native Vector3 btVector3Array_atConst(long j, btVector3Array btvector3array, int i);

    public static final native int btVector3Array_capacity(long j, btVector3Array btvector3array);

    public static final native void btVector3Array_clear(long j, btVector3Array btvector3array);

    public static final native void btVector3Array_copyFromArray(long j, btVector3Array btvector3array, long j2, btVector3Array btvector3array2);

    public static final native Vector3 btVector3Array_expandNonInitializing(long j, btVector3Array btvector3array);

    public static final native Vector3 btVector3Array_expand__SWIG_0(long j, btVector3Array btvector3array, Vector3 vector3);

    public static final native Vector3 btVector3Array_expand__SWIG_1(long j, btVector3Array btvector3array);

    public static final native int btVector3Array_findBinarySearch(long j, btVector3Array btvector3array, Vector3 vector3);

    public static final native int btVector3Array_findLinearSearch(long j, btVector3Array btvector3array, Vector3 vector3);

    public static final native int btVector3Array_findLinearSearch2(long j, btVector3Array btvector3array, Vector3 vector3);

    public static final native void btVector3Array_initializeFromBuffer(long j, btVector3Array btvector3array, long j2, int i, int i2);

    public static final native boolean btVector3Array_less_operatorFunctionCall(long j, btVector3Array.less lessVar, Vector3 vector3, Vector3 vector4);

    public static final native long btVector3Array_operatorAssignment(long j, btVector3Array btvector3array, long j2, btVector3Array btvector3array2);

    public static final native Vector3 btVector3Array_operatorSubscript(long j, btVector3Array btvector3array, int i);

    public static final native Vector3 btVector3Array_operatorSubscriptConst(long j, btVector3Array btvector3array, int i);

    public static final native void btVector3Array_pop_back(long j, btVector3Array btvector3array);

    public static final native void btVector3Array_push_back(long j, btVector3Array btvector3array, Vector3 vector3);

    public static final native void btVector3Array_remove(long j, btVector3Array btvector3array, Vector3 vector3);

    public static final native void btVector3Array_removeAtIndex(long j, btVector3Array btvector3array, int i);

    public static final native void btVector3Array_reserve(long j, btVector3Array btvector3array, int i);

    public static final native void btVector3Array_resizeNoInitialize(long j, btVector3Array btvector3array, int i);

    public static final native void btVector3Array_resize__SWIG_0(long j, btVector3Array btvector3array, int i, Vector3 vector3);

    public static final native void btVector3Array_resize__SWIG_1(long j, btVector3Array btvector3array, int i);

    public static final native int btVector3Array_size(long j, btVector3Array btvector3array);

    public static final native void btVector3Array_swap(long j, btVector3Array btvector3array, int i, int i2);

    public static final native double[] btVector3DoubleData_floats_get(long j, btVector3DoubleData btvector3doubledata);

    public static final native void btVector3DoubleData_floats_set(long j, btVector3DoubleData btvector3doubledata, double[] dArr);

    public static final native float[] btVector3FloatData_floats_get(long j, btVector3FloatData btvector3floatdata);

    public static final native void btVector3FloatData_floats_set(long j, btVector3FloatData btvector3floatdata, float[] fArr);

    public static final native Vector3 btVector3_absolute(long j, btVector3 btvector3);

    public static final native float btVector3_angle(long j, btVector3 btvector3, Vector3 vector3);

    public static final native int btVector3_closestAxis(long j, btVector3 btvector3);

    public static final native Vector3 btVector3_cross(long j, btVector3 btvector3, Vector3 vector3);

    public static final native void btVector3_deSerialize(long j, btVector3 btvector3, long j2, btVector3FloatData btvector3floatdata);

    public static final native void btVector3_deSerializeDouble(long j, btVector3 btvector3, long j2, btVector3DoubleData btvector3doubledata);

    public static final native void btVector3_deSerializeFloat(long j, btVector3 btvector3, long j2, btVector3FloatData btvector3floatdata);

    public static final native float btVector3_distance(long j, btVector3 btvector3, Vector3 vector3);

    public static final native float btVector3_distance2(long j, btVector3 btvector3, Vector3 vector3);

    public static final native float btVector3_dot(long j, btVector3 btvector3, Vector3 vector3);

    public static final native Vector3 btVector3_dot3(long j, btVector3 btvector3, Vector3 vector3, Vector3 vector4, Vector3 vector5);

    public static final native float[] btVector3_floats_get(long j, btVector3 btvector3);

    public static final native void btVector3_floats_set(long j, btVector3 btvector3, float[] fArr);

    public static final native int btVector3_furthestAxis(long j, btVector3 btvector3);

    public static final native boolean btVector3_fuzzyZero(long j, btVector3 btvector3);

    public static final native void btVector3_getSkewSymmetricMatrix(long j, btVector3 btvector3, long j2, btVector3 btvector4, long j3, btVector3 btvector5, long j4, btVector3 btvector6);

    public static final native float btVector3_getX(long j, btVector3 btvector3);

    public static final native float btVector3_getY(long j, btVector3 btvector3);

    public static final native float btVector3_getZ(long j, btVector3 btvector3);

    public static final native boolean btVector3_isZero(long j, btVector3 btvector3);

    public static final native float btVector3_length(long j, btVector3 btvector3);

    public static final native float btVector3_length2(long j, btVector3 btvector3);

    public static final native Vector3 btVector3_lerp(long j, btVector3 btvector3, Vector3 vector3, float f);

    public static final native int btVector3_maxAxis(long j, btVector3 btvector3);

    public static final native int btVector3_maxDot(long j, btVector3 btvector3, long j2, btVector3 btvector4, int i, long j3);

    public static final native int btVector3_minAxis(long j, btVector3 btvector3);

    public static final native int btVector3_minDot(long j, btVector3 btvector3, long j2, btVector3 btvector4, int i, long j3);

    public static final native float btVector3_norm(long j, btVector3 btvector3);

    public static final native Vector3 btVector3_normalize(long j, btVector3 btvector3);

    public static final native Vector3 btVector3_normalized(long j, btVector3 btvector3);

    public static final native Vector3 btVector3_operatorAdditionAssignment(long j, btVector3 btvector3, Vector3 vector3);

    public static final native void btVector3_operatorDeleteArray__SWIG_0(long j, btVector3 btvector3, long j2);

    public static final native void btVector3_operatorDeleteArray__SWIG_1(long j, btVector3 btvector3, long j2, long j3);

    public static final native void btVector3_operatorDelete__SWIG_0(long j, btVector3 btvector3, long j2);

    public static final native void btVector3_operatorDelete__SWIG_1(long j, btVector3 btvector3, long j2, long j3);

    public static final native Vector3 btVector3_operatorDivisionAssignment(long j, btVector3 btvector3, float f);

    public static final native boolean btVector3_operatorEqualTo(long j, btVector3 btvector3, Vector3 vector3);

    public static final native Vector3 btVector3_operatorMultiplicationAssignment__SWIG_0(long j, btVector3 btvector3, float f);

    public static final native Vector3 btVector3_operatorMultiplicationAssignment__SWIG_1(long j, btVector3 btvector3, Vector3 vector3);

    public static final native long btVector3_operatorNewArray__SWIG_0(long j, btVector3 btvector3, long j2);

    public static final native long btVector3_operatorNewArray__SWIG_1(long j, btVector3 btvector3, long j2, long j3);

    public static final native long btVector3_operatorNew__SWIG_0(long j, btVector3 btvector3, long j2);

    public static final native long btVector3_operatorNew__SWIG_1(long j, btVector3 btvector3, long j2, long j3);

    public static final native boolean btVector3_operatorNotEqualTo(long j, btVector3 btvector3, Vector3 vector3);

    public static final native Vector3 btVector3_operatorSubtractionAssignment(long j, btVector3 btvector3, Vector3 vector3);

    public static final native FloatBuffer btVector3_operatorbtConstScalarPtr(long j, btVector3 btvector3);

    public static final native FloatBuffer btVector3_operatorbtScalarPtr(long j, btVector3 btvector3);

    public static final native Vector3 btVector3_rotate(long j, btVector3 btvector3, Vector3 vector3, float f);

    public static final native float btVector3_safeNorm(long j, btVector3 btvector3);

    public static final native Vector3 btVector3_safeNormalize(long j, btVector3 btvector3);

    public static final native void btVector3_serialize(long j, btVector3 btvector3, long j2, btVector3FloatData btvector3floatdata);

    public static final native void btVector3_serializeDouble(long j, btVector3 btvector3, long j2, btVector3DoubleData btvector3doubledata);

    public static final native void btVector3_serializeFloat(long j, btVector3 btvector3, long j2, btVector3FloatData btvector3floatdata);

    public static final native void btVector3_setInterpolate3(long j, btVector3 btvector3, Vector3 vector3, Vector3 vector4, float f);

    public static final native void btVector3_setMax(long j, btVector3 btvector3, Vector3 vector3);

    public static final native void btVector3_setMin(long j, btVector3 btvector3, Vector3 vector3);

    public static final native void btVector3_setValue(long j, btVector3 btvector3, float f, float f2, float f3);

    public static final native void btVector3_setW(long j, btVector3 btvector3, float f);

    public static final native void btVector3_setX(long j, btVector3 btvector3, float f);

    public static final native void btVector3_setY(long j, btVector3 btvector3, float f);

    public static final native void btVector3_setZ(long j, btVector3 btvector3, float f);

    public static final native void btVector3_setZero(long j, btVector3 btvector3);

    public static final native float btVector3_triple(long j, btVector3 btvector3, Vector3 vector3, Vector3 vector4);

    public static final native float btVector3_w(long j, btVector3 btvector3);

    public static final native float btVector3_x(long j, btVector3 btvector3);

    public static final native float btVector3_y(long j, btVector3 btvector3);

    public static final native float btVector3_z(long j, btVector3 btvector3);

    public static final native long btVector4_SWIGUpcast(long j);

    public static final native long btVector4_absolute4(long j, btVector4 btvector4);

    public static final native int btVector4_closestAxis4(long j, btVector4 btvector4);

    public static final native float btVector4_getW(long j, btVector4 btvector4);

    public static final native int btVector4_maxAxis4(long j, btVector4 btvector4);

    public static final native int btVector4_minAxis4(long j, btVector4 btvector4);

    public static final native void btVector4_setValue(long j, btVector4 btvector4, float f, float f2, float f3, float f4);

    public static final native void delete_CProfileSample(long j);

    public static final native void delete_ConvexH(long j);

    public static final native void delete_ConvexH_HalfEdge(long j);

    public static final native void delete_GrahamVector3(long j);

    public static final native void delete_HullDesc(long j);

    public static final native void delete_HullLibrary(long j);

    public static final native void delete_HullResult(long j);

    public static final native void delete_PHullResult(long j);

    public static final native void delete_btAngleCompareFunc(long j);

    public static final native void delete_btBlock(long j);

    public static final native void delete_btBulletSerializedArrays(long j);

    public static final native void delete_btChunk(long j);

    public static final native void delete_btClock(long j);

    public static final native void delete_btConvexHullComputer(long j);

    public static final native void delete_btConvexHullComputer_Edge(long j);

    public static final native void delete_btConvexSeparatingDistanceUtil(long j);

    public static final native void delete_btDefaultMotionState(long j);

    public static final native void delete_btDefaultSerializer(long j);

    public static final native void delete_btGEN_Link(long j);

    public static final native void delete_btGEN_List(long j);

    public static final native void delete_btGeometryUtil(long j);

    public static final native void delete_btHashInt(long j);

    public static final native void delete_btHashPtr(long j);

    public static final native void delete_btHashString(long j);

    public static final native void delete_btIDebugDraw(long j);

    public static final native void delete_btIDebugDraw_DefaultColors(long j);

    public static final native void delete_btIParallelForBody(long j);

    public static final native void delete_btITaskScheduler(long j);

    public static final native void delete_btIntSortPredicate(long j);

    public static final native void delete_btMatrix3x3(long j);

    public static final native void delete_btMatrix3x3DoubleData(long j);

    public static final native void delete_btMatrix3x3FloatData(long j);

    public static final native void delete_btMotionState(long j);

    public static final native void delete_btPlane(long j);

    public static final native void delete_btPointerUid(long j);

    public static final native void delete_btPolarDecomposition(long j);

    public static final native void delete_btPoolAllocator(long j);

    public static final native void delete_btQuadWord(long j);

    public static final native void delete_btQuaternion(long j);

    public static final native void delete_btQuaternionDoubleData(long j);

    public static final native void delete_btQuaternionFloatData(long j);

    public static final native void delete_btScalarArray(long j);

    public static final native void delete_btScalarArray_less(long j);

    public static final native void delete_btSerializer(long j);

    public static final native void delete_btSpatialForceVector(long j);

    public static final native void delete_btSpatialMotionVector(long j);

    public static final native void delete_btSpatialTransformationMatrix(long j);

    public static final native void delete_btSpinMutex(long j);

    public static final native void delete_btStackAlloc(long j);

    public static final native void delete_btSymmetricSpatialDyad(long j);

    public static final native void delete_btTransform(long j);

    public static final native void delete_btTransformDoubleData(long j);

    public static final native void delete_btTransformFloatData(long j);

    public static final native void delete_btTransformUtil(long j);

    public static final native void delete_btTypedObject(long j);

    public static final native void delete_btVector3(long j);

    public static final native void delete_btVector3Array(long j);

    public static final native void delete_btVector3Array_less(long j);

    public static final native void delete_btVector3DoubleData(long j);

    public static final native void delete_btVector3FloatData(long j);

    public static final native void delete_btVector4(long j);

    public static final native void delete_int4(long j);

    public static final native float dot(Quaternion quaternion, Quaternion quaternion2);

    public static final native long int4_operatorSubscript(long j, int4 int4Var, int i);

    public static final native int int4_operatorSubscriptConst(long j, int4 int4Var, int i);

    public static final native int int4_w_get(long j, int4 int4Var);

    public static final native void int4_w_set(long j, int4 int4Var, int i);

    public static final native int int4_x_get(long j, int4 int4Var);

    public static final native void int4_x_set(long j, int4 int4Var, int i);

    public static final native int int4_y_get(long j, int4 int4Var);

    public static final native void int4_y_set(long j, int4 int4Var, int i);

    public static final native int int4_z_get(long j, int4 int4Var);

    public static final native void int4_z_set(long j, int4 int4Var, int i);

    public static final native Quaternion inverse(Quaternion quaternion);

    public static final native float length(Quaternion quaternion);

    public static final native Vector3 lerp(Vector3 vector3, Vector3 vector4, float f);

    public static final native long new_CProfileSample(String str);

    public static final native long new_ConvexH_HalfEdge__SWIG_0();

    public static final native long new_ConvexH_HalfEdge__SWIG_1(short s, short s2, short s3);

    public static final native long new_ConvexH__SWIG_0();

    public static final native long new_ConvexH__SWIG_1(int i, int i2, int i3);

    public static final native long new_GrahamVector3(Vector3 vector3, int i);

    public static final native long new_HullDesc__SWIG_0();

    public static final native long new_HullDesc__SWIG_1(int i, long j, long j2, btVector3 btvector3, long j3);

    public static final native long new_HullDesc__SWIG_2(int i, long j, long j2, btVector3 btvector3);

    public static final native long new_HullLibrary();

    public static final native long new_HullResult();

    public static final native long new_PHullResult();

    public static final native long new_btAngleCompareFunc(Vector3 vector3);

    public static final native long new_btBlock();

    public static final native long new_btBulletSerializedArrays();

    public static final native long new_btChunk();

    public static final native long new_btClock__SWIG_0();

    public static final native long new_btClock__SWIG_1(long j, btClock btclock);

    public static final native long new_btConvexHullComputer();

    public static final native long new_btConvexHullComputer_Edge();

    public static final native long new_btConvexSeparatingDistanceUtil(float f, float f2);

    public static final native long new_btDefaultMotionState__SWIG_0(Matrix4 matrix4, Matrix4 matrix5);

    public static final native long new_btDefaultMotionState__SWIG_1(Matrix4 matrix4);

    public static final native long new_btDefaultMotionState__SWIG_2();

    public static final native long new_btDefaultSerializer__SWIG_0(int i, ByteBuffer byteBuffer);

    public static final native long new_btDefaultSerializer__SWIG_1(int i);

    public static final native long new_btDefaultSerializer__SWIG_2();

    public static final native long new_btGEN_Link__SWIG_0();

    public static final native long new_btGEN_Link__SWIG_1(long j, btGEN_Link btgen_link, long j2, btGEN_Link btgen_link2);

    public static final native long new_btGEN_List();

    public static final native long new_btGeometryUtil();

    public static final native long new_btHashInt__SWIG_0();

    public static final native long new_btHashInt__SWIG_1(int i);

    public static final native long new_btHashPtr(long j);

    public static final native long new_btHashString(String str);

    public static final native long new_btIDebugDraw();

    public static final native long new_btIDebugDraw_DefaultColors();

    public static final native long new_btIntSortPredicate();

    public static final native long new_btMatrix3x3DoubleData();

    public static final native long new_btMatrix3x3FloatData();

    public static final native long new_btMatrix3x3__SWIG_0();

    public static final native long new_btMatrix3x3__SWIG_1(Quaternion quaternion);

    public static final native long new_btMatrix3x3__SWIG_2(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9);

    public static final native long new_btMatrix3x3__SWIG_3(Matrix3 matrix3);

    public static final native long new_btMotionState();

    public static final native long new_btPlane__SWIG_0(Vector3 vector3, float f);

    public static final native long new_btPlane__SWIG_1();

    public static final native long new_btPointerUid();

    public static final native long new_btPolarDecomposition__SWIG_0(float f, long j);

    public static final native long new_btPolarDecomposition__SWIG_1(float f);

    public static final native long new_btPolarDecomposition__SWIG_2();

    public static final native long new_btPoolAllocator(int i, int i2);

    public static final native long new_btQuadWord__SWIG_0();

    public static final native long new_btQuadWord__SWIG_1(float f, float f2, float f3);

    public static final native long new_btQuadWord__SWIG_2(float f, float f2, float f3, float f4);

    public static final native long new_btQuaternionDoubleData();

    public static final native long new_btQuaternionFloatData();

    public static final native long new_btQuaternion__SWIG_0();

    public static final native long new_btQuaternion__SWIG_1(float f, float f2, float f3, float f4);

    public static final native long new_btQuaternion__SWIG_2(Vector3 vector3, float f);

    public static final native long new_btQuaternion__SWIG_3(float f, float f2, float f3);

    public static final native long new_btScalarArray__SWIG_0();

    public static final native long new_btScalarArray__SWIG_1(long j, btScalarArray btscalararray);

    public static final native long new_btScalarArray_less();

    public static final native long new_btSpatialForceVector__SWIG_0();

    public static final native long new_btSpatialForceVector__SWIG_1(Vector3 vector3, Vector3 vector4);

    public static final native long new_btSpatialForceVector__SWIG_2(float f, float f2, float f3, float f4, float f5, float f6);

    public static final native long new_btSpatialMotionVector__SWIG_0();

    public static final native long new_btSpatialMotionVector__SWIG_1(Vector3 vector3, Vector3 vector4);

    public static final native long new_btSpatialTransformationMatrix();

    public static final native long new_btSpinMutex();

    public static final native long new_btStackAlloc(long j);

    public static final native long new_btSymmetricSpatialDyad__SWIG_0();

    public static final native long new_btSymmetricSpatialDyad__SWIG_1(Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5);

    public static final native long new_btTransformDoubleData();

    public static final native long new_btTransformFloatData();

    public static final native long new_btTransformUtil();

    public static final native long new_btTransform__SWIG_0();

    public static final native long new_btTransform__SWIG_1(Quaternion quaternion, Vector3 vector3);

    public static final native long new_btTransform__SWIG_2(Quaternion quaternion);

    public static final native long new_btTransform__SWIG_3(Matrix3 matrix3, Vector3 vector3);

    public static final native long new_btTransform__SWIG_4(Matrix3 matrix3);

    public static final native long new_btTransform__SWIG_5(Matrix4 matrix4);

    public static final native long new_btTypedObject(int i);

    public static final native long new_btVector3Array__SWIG_0();

    public static final native long new_btVector3Array__SWIG_1(long j, btVector3Array btvector3array);

    public static final native long new_btVector3Array_less();

    public static final native long new_btVector3DoubleData();

    public static final native long new_btVector3FloatData();

    public static final native long new_btVector3__SWIG_0();

    public static final native long new_btVector3__SWIG_1(float f, float f2, float f3);

    public static final native long new_btVector4__SWIG_0();

    public static final native long new_btVector4__SWIG_1(float f, float f2, float f3, float f4);

    public static final native long new_int4__SWIG_0();

    public static final native long new_int4__SWIG_1(int i, int i2, int i3, int i4);

    public static final native Vector3 operatorAddition__SWIG_0(Vector3 vector3, Vector3 vector4);

    public static final native Matrix3 operatorAddition__SWIG_1(Matrix3 matrix3, Matrix3 matrix4);

    public static final native Vector3 operatorDivision__SWIG_0(Vector3 vector3, float f);

    public static final native Vector3 operatorDivision__SWIG_1(Vector3 vector3, Vector3 vector4);

    public static final native boolean operatorEqualTo__SWIG_0(Matrix4 matrix4, Matrix4 matrix5);

    public static final native boolean operatorEqualTo__SWIG_1(Matrix3 matrix3, Matrix3 matrix4);

    public static final native Vector3 operatorMultiplication__SWIG_0(Vector3 vector3, Vector3 vector4);

    public static final native Vector3 operatorMultiplication__SWIG_1(Vector3 vector3, float f);

    public static final native Vector3 operatorMultiplication__SWIG_2(float f, Vector3 vector3);

    public static final native Quaternion operatorMultiplication__SWIG_3(Quaternion quaternion, Quaternion quaternion2);

    public static final native Quaternion operatorMultiplication__SWIG_4(Quaternion quaternion, Vector3 vector3);

    public static final native Quaternion operatorMultiplication__SWIG_5(Vector3 vector3, Quaternion quaternion);

    public static final native Matrix3 operatorMultiplication__SWIG_6(Matrix3 matrix3, float f);

    public static final native Vector3 operatorMultiplication__SWIG_7(Matrix3 matrix3, Vector3 vector3);

    public static final native Vector3 operatorMultiplication__SWIG_8(Vector3 vector3, Matrix3 matrix3);

    public static final native Matrix3 operatorMultiplication__SWIG_9(Matrix3 matrix3, Matrix3 matrix4);

    public static final native Vector3 operatorSubtraction__SWIG_0(Vector3 vector3, Vector3 vector4);

    public static final native Vector3 operatorSubtraction__SWIG_1(Vector3 vector3);

    public static final native Matrix3 operatorSubtraction__SWIG_2(Matrix3 matrix3, Matrix3 matrix4);

    public static final native long polarDecompose(Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5);

    public static final native Vector3 quatRotate(Quaternion quaternion, Vector3 vector3);

    public static final native int sBulletDNAlen64_get();

    public static final native void sBulletDNAlen64_set(int i);

    public static final native int sBulletDNAlen_get();

    public static final native void sBulletDNAlen_set(int i);

    public static final native String sBulletDNAstr64_get();

    public static final native void sBulletDNAstr64_set(String str);

    public static final native String sBulletDNAstr_get();

    public static final native void sBulletDNAstr_set(String str);

    public static final native void setElem__SWIG_0(long j, int i, int i2, double d);

    public static final native void setElem__SWIG_1(long j, int i, int i2, float f);

    public static final native Quaternion shortestArcQuat(Vector3 vector3, Vector3 vector4);

    public static final native Quaternion shortestArcQuatNormalize2(Vector3 vector3, Vector3 vector4);

    public static final native Quaternion slerp(Quaternion quaternion, Quaternion quaternion2, float f);

    private static final native void swig_module_init();

    public static final native long testQuantizedAabbAgainstQuantizedAabb(IntBuffer intBuffer, IntBuffer intBuffer2, IntBuffer intBuffer3, IntBuffer intBuffer4);

    public static long SwigDirector_btIDebugDraw_getDefaultColors(btIDebugDraw btidebugdraw) {
        return btIDebugDraw.DefaultColors.getCPtr(btidebugdraw.getDefaultColors());
    }

    public static void SwigDirector_btIDebugDraw_setDefaultColors(btIDebugDraw btidebugdraw, long j) {
        btidebugdraw.setDefaultColors(new btIDebugDraw.DefaultColors(j, false));
    }

    public static void SwigDirector_btIDebugDraw_drawLine__SWIG_0(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        btidebugdraw.drawLine(vector3, vector4, vector5);
    }

    public static void SwigDirector_btIDebugDraw_drawLine__SWIG_1(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        btidebugdraw.drawLine(vector3, vector4, vector5, vector6);
    }

    public static void SwigDirector_btIDebugDraw_drawSphere__SWIG_0(btIDebugDraw btidebugdraw, float f, Matrix4 matrix4, Vector3 vector3) {
        btidebugdraw.drawSphere(f, matrix4, vector3);
    }

    public static void SwigDirector_btIDebugDraw_drawSphere__SWIG_1(btIDebugDraw btidebugdraw, Vector3 vector3, float f, Vector3 vector4) {
        btidebugdraw.drawSphere(vector3, f, vector4);
    }

    public static void SwigDirector_btIDebugDraw_drawTriangle__SWIG_0(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8, Vector3 vector9, float f) {
        btidebugdraw.drawTriangle(vector3, vector4, vector5, vector6, vector7, vector8, vector9, f);
    }

    public static void SwigDirector_btIDebugDraw_drawTriangle__SWIG_1(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f) {
        btidebugdraw.drawTriangle(vector3, vector4, vector5, vector6, f);
    }

    public static void SwigDirector_btIDebugDraw_drawContactPoint(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, float f, int i, Vector3 vector5) {
        btidebugdraw.drawContactPoint(vector3, vector4, f, i, vector5);
    }

    public static void SwigDirector_btIDebugDraw_reportErrorWarning(btIDebugDraw btidebugdraw, String str) {
        btidebugdraw.reportErrorWarning(str);
    }

    public static void SwigDirector_btIDebugDraw_draw3dText(btIDebugDraw btidebugdraw, Vector3 vector3, String str) {
        btidebugdraw.draw3dText(vector3, str);
    }

    public static void SwigDirector_btIDebugDraw_setDebugMode(btIDebugDraw btidebugdraw, int i) {
        btidebugdraw.setDebugMode(i);
    }

    public static int SwigDirector_btIDebugDraw_getDebugMode(btIDebugDraw btidebugdraw) {
        return btidebugdraw.getDebugMode();
    }

    public static void SwigDirector_btIDebugDraw_drawAabb(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        btidebugdraw.drawAabb(vector3, vector4, vector5);
    }

    public static void SwigDirector_btIDebugDraw_drawTransform(btIDebugDraw btidebugdraw, Matrix4 matrix4, float f) {
        btidebugdraw.drawTransform(matrix4, f);
    }

    public static void SwigDirector_btIDebugDraw_drawArc__SWIG_0(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, Vector3 vector6, boolean z, float f5) {
        btidebugdraw.drawArc(vector3, vector4, vector5, f, f2, f3, f4, vector6, z, f5);
    }

    public static void SwigDirector_btIDebugDraw_drawArc__SWIG_1(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, Vector3 vector6, boolean z) {
        btidebugdraw.drawArc(vector3, vector4, vector5, f, f2, f3, f4, vector6, z);
    }

    public static void SwigDirector_btIDebugDraw_drawSpherePatch__SWIG_0(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6, float f6, boolean z) {
        btidebugdraw.drawSpherePatch(vector3, vector4, vector5, f, f2, f3, f4, f5, vector6, f6, z);
    }

    public static void SwigDirector_btIDebugDraw_drawSpherePatch__SWIG_1(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6, float f6) {
        btidebugdraw.drawSpherePatch(vector3, vector4, vector5, f, f2, f3, f4, f5, vector6, f6);
    }

    public static void SwigDirector_btIDebugDraw_drawSpherePatch__SWIG_2(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6) {
        btidebugdraw.drawSpherePatch(vector3, vector4, vector5, f, f2, f3, f4, f5, vector6);
    }

    public static void SwigDirector_btIDebugDraw_drawBox__SWIG_0(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        btidebugdraw.drawBox(vector3, vector4, vector5);
    }

    public static void SwigDirector_btIDebugDraw_drawBox__SWIG_1(btIDebugDraw btidebugdraw, Vector3 vector3, Vector3 vector4, Matrix4 matrix4, Vector3 vector5) {
        btidebugdraw.drawBox(vector3, vector4, matrix4, vector5);
    }

    public static void SwigDirector_btIDebugDraw_drawCapsule(btIDebugDraw btidebugdraw, float f, float f2, int i, Matrix4 matrix4, Vector3 vector3) {
        btidebugdraw.drawCapsule(f, f2, i, matrix4, vector3);
    }

    public static void SwigDirector_btIDebugDraw_drawCylinder(btIDebugDraw btidebugdraw, float f, float f2, int i, Matrix4 matrix4, Vector3 vector3) {
        btidebugdraw.drawCylinder(f, f2, i, matrix4, vector3);
    }

    public static void SwigDirector_btIDebugDraw_drawCone(btIDebugDraw btidebugdraw, float f, float f2, int i, Matrix4 matrix4, Vector3 vector3) {
        btidebugdraw.drawCone(f, f2, i, matrix4, vector3);
    }

    public static void SwigDirector_btIDebugDraw_drawPlane(btIDebugDraw btidebugdraw, Vector3 vector3, float f, Matrix4 matrix4, Vector3 vector4) {
        btidebugdraw.drawPlane(vector3, f, matrix4, vector4);
    }

    public static void SwigDirector_btIDebugDraw_clearLines(btIDebugDraw btidebugdraw) {
        btidebugdraw.clearLines();
    }

    public static void SwigDirector_btIDebugDraw_flushLines(btIDebugDraw btidebugdraw) {
        btidebugdraw.flushLines();
    }

    public static void SwigDirector_btMotionState_getWorldTransform(btMotionState btmotionstate, Matrix4 matrix4) {
        btmotionstate.getWorldTransform(matrix4);
    }

    public static void SwigDirector_btMotionState_setWorldTransform(btMotionState btmotionstate, Matrix4 matrix4) {
        btmotionstate.setWorldTransform(matrix4);
    }

    static {
        swig_module_init();
    }
}
