package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;
import com.badlogic.gdx.utils.Pool;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class Collision implements CollisionConstants {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Vector3 staticVector3 = new Vector3();
    public static final Pool<Vector3> poolVector3 = new Pool<Vector3>() { // from class: com.badlogic.gdx.physics.bullet.collision.Collision.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Vector3 newObject() {
            return new Vector3();
        }
    };
    public static final Quaternion staticQuaternion = new Quaternion();
    public static final Pool<Quaternion> poolQuaternion = new Pool<Quaternion>() { // from class: com.badlogic.gdx.physics.bullet.collision.Collision.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Quaternion newObject() {
            return new Quaternion();
        }
    };
    public static final Matrix3 staticMatrix3 = new Matrix3();
    public static final Pool<Matrix3> poolMatrix3 = new Pool<Matrix3>() { // from class: com.badlogic.gdx.physics.bullet.collision.Collision.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix3 newObject() {
            return new Matrix3();
        }
    };
    public static final Matrix4 staticMatrix4 = new Matrix4();
    public static final Pool<Matrix4> poolMatrix4 = new Pool<Matrix4>() { // from class: com.badlogic.gdx.physics.bullet.collision.Collision.4
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix4 newObject() {
            return new Matrix4();
        }
    };

    public static boolean operatorEqualTo(btBroadphasePair btbroadphasepair, btBroadphasePair btbroadphasepair2) {
        return CollisionJNI.operatorEqualTo__SWIG_3(btbroadphasepair, btbroadphasepair2);
    }

    public static void setGOverlappingPairs(int i) {
        CollisionJNI.gOverlappingPairs_set(i);
    }

    public static int getGOverlappingPairs() {
        return CollisionJNI.gOverlappingPairs_get();
    }

    public static void setGRemovePairs(int i) {
        CollisionJNI.gRemovePairs_set(i);
    }

    public static int getGRemovePairs() {
        return CollisionJNI.gRemovePairs_get();
    }

    public static void setGAddedPairs(int i) {
        CollisionJNI.gAddedPairs_set(i);
    }

    public static int getGAddedPairs() {
        return CollisionJNI.gAddedPairs_get();
    }

    public static void setGFindPairs(int i) {
        CollisionJNI.gFindPairs_set(i);
    }

    public static int getGFindPairs() {
        return CollisionJNI.gFindPairs_get();
    }

    public static int getBT_NULL_PAIR() {
        return CollisionJNI.BT_NULL_PAIR_get();
    }

    public static boolean gdxCheckFilter(int i, int i2) {
        return CollisionJNI.gdxCheckFilter__SWIG_0(i, i2);
    }

    public static boolean gdxCheckFilter(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        return CollisionJNI.gdxCheckFilter__SWIG_1(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public static boolean Intersect(btDbvtAabbMm btdbvtaabbmm, btDbvtAabbMm btdbvtaabbmm2) {
        return CollisionJNI.Intersect__SWIG_0(btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, btDbvtAabbMm.getCPtr(btdbvtaabbmm2), btdbvtaabbmm2);
    }

    public static boolean Intersect(btDbvtAabbMm btdbvtaabbmm, Vector3 vector3) {
        return CollisionJNI.Intersect__SWIG_1(btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, vector3);
    }

    public static float Proximity(btDbvtAabbMm btdbvtaabbmm, btDbvtAabbMm btdbvtaabbmm2) {
        return CollisionJNI.Proximity(btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, btDbvtAabbMm.getCPtr(btdbvtaabbmm2), btdbvtaabbmm2);
    }

    public static int Select(btDbvtAabbMm btdbvtaabbmm, btDbvtAabbMm btdbvtaabbmm2, btDbvtAabbMm btdbvtaabbmm3) {
        return CollisionJNI.Select(btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, btDbvtAabbMm.getCPtr(btdbvtaabbmm2), btdbvtaabbmm2, btDbvtAabbMm.getCPtr(btdbvtaabbmm3), btdbvtaabbmm3);
    }

    public static void Merge(btDbvtAabbMm btdbvtaabbmm, btDbvtAabbMm btdbvtaabbmm2, btDbvtAabbMm btdbvtaabbmm3) {
        CollisionJNI.Merge(btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, btDbvtAabbMm.getCPtr(btdbvtaabbmm2), btdbvtaabbmm2, btDbvtAabbMm.getCPtr(btdbvtaabbmm3), btdbvtaabbmm3);
    }

    public static boolean NotEqual(btDbvtAabbMm btdbvtaabbmm, btDbvtAabbMm btdbvtaabbmm2) {
        return CollisionJNI.NotEqual(btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, btDbvtAabbMm.getCPtr(btdbvtaabbmm2), btdbvtaabbmm2);
    }

    public static boolean operatorEqualTo(btCompoundShapeChild btcompoundshapechild, btCompoundShapeChild btcompoundshapechild2) {
        return CollisionJNI.operatorEqualTo__SWIG_4(btCompoundShapeChild.getCPtr(btcompoundshapechild), btcompoundshapechild, btCompoundShapeChild.getCPtr(btcompoundshapechild2), btcompoundshapechild2);
    }

    public static void setGCompoundChildShapePairCallback(SWIGTYPE_p_f_p_q_const__btCollisionShape_p_q_const__btCollisionShape__bool sWIGTYPE_p_f_p_q_const__btCollisionShape_p_q_const__btCollisionShape__bool) {
        CollisionJNI.gCompoundChildShapePairCallback_set(SWIGTYPE_p_f_p_q_const__btCollisionShape_p_q_const__btCollisionShape__bool.getCPtr(sWIGTYPE_p_f_p_q_const__btCollisionShape_p_q_const__btCollisionShape__bool));
    }

    public static SWIGTYPE_p_f_p_q_const__btCollisionShape_p_q_const__btCollisionShape__bool getGCompoundChildShapePairCallback() {
        long jGCompoundChildShapePairCallback_get = CollisionJNI.gCompoundChildShapePairCallback_get();
        if (jGCompoundChildShapePairCallback_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_p_q_const__btCollisionShape_p_q_const__btCollisionShape__bool(jGCompoundChildShapePairCallback_get, false);
    }

    public static void setGContactAddedCallback(SWIGTYPE_p_f_r_btManifoldPoint_p_q_const__btCollisionObjectWrapper_int_int_p_q_const__btCollisionObjectWrapper_int_int__bool sWIGTYPE_p_f_r_btManifoldPoint_p_q_const__btCollisionObjectWrapper_int_int_p_q_const__btCollisionObjectWrapper_int_int__bool) {
        CollisionJNI.gContactAddedCallback_set(SWIGTYPE_p_f_r_btManifoldPoint_p_q_const__btCollisionObjectWrapper_int_int_p_q_const__btCollisionObjectWrapper_int_int__bool.getCPtr(sWIGTYPE_p_f_r_btManifoldPoint_p_q_const__btCollisionObjectWrapper_int_int_p_q_const__btCollisionObjectWrapper_int_int__bool));
    }

    public static SWIGTYPE_p_f_r_btManifoldPoint_p_q_const__btCollisionObjectWrapper_int_int_p_q_const__btCollisionObjectWrapper_int_int__bool getGContactAddedCallback() {
        long jGContactAddedCallback_get = CollisionJNI.gContactAddedCallback_get();
        if (jGContactAddedCallback_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_r_btManifoldPoint_p_q_const__btCollisionObjectWrapper_int_int_p_q_const__btCollisionObjectWrapper_int_int__bool(jGContactAddedCallback_get, false);
    }

    public static int getBT_SIMPLE_NULL_PAIR() {
        return CollisionJNI.BT_SIMPLE_NULL_PAIR_get();
    }

    public static void setGOverlappingSimplePairs(int i) {
        CollisionJNI.gOverlappingSimplePairs_set(i);
    }

    public static int getGOverlappingSimplePairs() {
        return CollisionJNI.gOverlappingSimplePairs_get();
    }

    public static void setGRemoveSimplePairs(int i) {
        CollisionJNI.gRemoveSimplePairs_set(i);
    }

    public static int getGRemoveSimplePairs() {
        return CollisionJNI.gRemoveSimplePairs_get();
    }

    public static void setGAddedSimplePairs(int i) {
        CollisionJNI.gAddedSimplePairs_set(i);
    }

    public static int getGAddedSimplePairs() {
        return CollisionJNI.gAddedSimplePairs_get();
    }

    public static void setGFindSimplePairs(int i) {
        CollisionJNI.gFindSimplePairs_set(i);
    }

    public static int getGFindSimplePairs() {
        return CollisionJNI.gFindSimplePairs_get();
    }

    public static void btGenerateInternalEdgeInfo(btBvhTriangleMeshShape btbvhtrianglemeshshape, btTriangleInfoMap bttriangleinfomap) {
        CollisionJNI.btGenerateInternalEdgeInfo(btBvhTriangleMeshShape.getCPtr(btbvhtrianglemeshshape), btbvhtrianglemeshshape, btTriangleInfoMap.getCPtr(bttriangleinfomap), bttriangleinfomap);
    }

    public static void btAdjustInternalEdgeContacts(btManifoldPoint btmanifoldpoint, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, int i, int i2, int i3) {
        CollisionJNI.btAdjustInternalEdgeContacts__SWIG_0(btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, i, i2, i3);
    }

    public static void btAdjustInternalEdgeContacts(btManifoldPoint btmanifoldpoint, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, int i, int i2) {
        CollisionJNI.btAdjustInternalEdgeContacts__SWIG_1(btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, i, i2);
    }

    public static void setGContactBreakingThreshold(float f) {
        CollisionJNI.gContactBreakingThreshold_set(f);
    }

    public static float getGContactBreakingThreshold() {
        return CollisionJNI.gContactBreakingThreshold_get();
    }

    public static void bt_calc_quantization_parameters(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, float f) {
        CollisionJNI.bt_calc_quantization_parameters(vector3, vector4, vector5, vector6, vector7, f);
    }

    public static void bt_quantize_clamp(IntBuffer intBuffer, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.bt_quantize_clamp(intBuffer, vector3, vector4, vector5, vector6);
    }

    public static Vector3 bt_unquantize(IntBuffer intBuffer, Vector3 vector3, Vector3 vector4) {
        return CollisionJNI.bt_unquantize(intBuffer, vector3, vector4);
    }

    public static float bt_mat3_dot_col(Matrix3 matrix3, Vector3 vector3, int i) {
        return CollisionJNI.bt_mat3_dot_col(matrix3, vector3, i);
    }

    public static boolean btCompareTransformsEqual(Matrix4 matrix4, Matrix4 matrix5) {
        return CollisionJNI.btCompareTransformsEqual(matrix4, matrix5);
    }

    public static float bt_distance_point_plane(btVector4 btvector4, Vector3 vector3) {
        return CollisionJNI.bt_distance_point_plane(btVector4.getCPtr(btvector4), btvector4, vector3);
    }

    public static void bt_vec_blend(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f) {
        CollisionJNI.bt_vec_blend(vector3, vector4, vector5, f);
    }

    public static void bt_plane_clip_polygon_collect(Vector3 vector3, Vector3 vector4, float f, float f2, btVector3 btvector3, SWIGTYPE_p_int sWIGTYPE_p_int) {
        CollisionJNI.bt_plane_clip_polygon_collect(vector3, vector4, f, f2, btVector3.getCPtr(btvector3), btvector3, SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int));
    }

    public static int bt_plane_clip_polygon(btVector4 btvector4, btVector3 btvector3, int i, btVector3 btvector5) {
        return CollisionJNI.bt_plane_clip_polygon(btVector4.getCPtr(btvector4), btvector4, btVector3.getCPtr(btvector3), btvector3, i, btVector3.getCPtr(btvector5), btvector5);
    }

    public static int bt_plane_clip_triangle(btVector4 btvector4, Vector3 vector3, Vector3 vector4, Vector3 vector5, btVector3 btvector3) {
        return CollisionJNI.bt_plane_clip_triangle(btVector4.getCPtr(btvector4), btvector4, vector3, vector4, vector5, btVector3.getCPtr(btvector3), btvector3);
    }

    public static void bt_edge_plane(Vector3 vector3, Vector3 vector4, Vector3 vector5, btVector4 btvector4) {
        CollisionJNI.bt_edge_plane(vector3, vector4, vector5, btVector4.getCPtr(btvector4), btvector4);
    }

    public static void bt_closest_point_on_segment(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.bt_closest_point_on_segment(vector3, vector4, vector5, vector6);
    }

    public static int bt_line_plane_collision(btVector4 btvector4, Vector3 vector3, Vector3 vector4, Vector3 vector5, SWIGTYPE_p_float sWIGTYPE_p_float, float f, float f2) {
        return CollisionJNI.bt_line_plane_collision(btVector4.getCPtr(btvector4), btvector4, vector3, vector4, vector5, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), f, f2);
    }

    public static void bt_segment_collision(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8) {
        CollisionJNI.bt_segment_collision(vector3, vector4, vector5, vector6, vector7, vector8);
    }

    public static btCompoundShape btCreateCompoundFromGimpactShape(btGImpactMeshShape btgimpactmeshshape, float f) {
        long jBtCreateCompoundFromGimpactShape = CollisionJNI.btCreateCompoundFromGimpactShape(btGImpactMeshShape.getCPtr(btgimpactmeshshape), btgimpactmeshshape, f);
        if (jBtCreateCompoundFromGimpactShape == 0) {
            return null;
        }
        return new btCompoundShape(jBtCreateCompoundFromGimpactShape, false);
    }

    public static long btPoolAlloc(long j) {
        return CollisionJNI.btPoolAlloc(j);
    }

    public static long btPoolRealloc(long j, long j2, long j3) {
        return CollisionJNI.btPoolRealloc(j, j2, j3);
    }

    public static void btPoolFree(long j) {
        CollisionJNI.btPoolFree(j);
    }

    public static Vector3 gim_inertia_add_transformed(Vector3 vector3, Vector3 vector4, Matrix4 matrix4) {
        return CollisionJNI.gim_inertia_add_transformed(vector3, vector4, matrix4);
    }

    public static Vector3 gim_get_point_inertia(Vector3 vector3, float f) {
        return CollisionJNI.gim_get_point_inertia(vector3, f);
    }

    public static void gim_set_alloc_handler(SWIGTYPE_p_f_size_t__p_void sWIGTYPE_p_f_size_t__p_void) {
        CollisionJNI.gim_set_alloc_handler(SWIGTYPE_p_f_size_t__p_void.getCPtr(sWIGTYPE_p_f_size_t__p_void));
    }

    public static void gim_set_alloca_handler(SWIGTYPE_p_f_size_t__p_void sWIGTYPE_p_f_size_t__p_void) {
        CollisionJNI.gim_set_alloca_handler(SWIGTYPE_p_f_size_t__p_void.getCPtr(sWIGTYPE_p_f_size_t__p_void));
    }

    public static void gim_set_realloc_handler(SWIGTYPE_p_f_p_void_size_t_size_t__p_void sWIGTYPE_p_f_p_void_size_t_size_t__p_void) {
        CollisionJNI.gim_set_realloc_handler(SWIGTYPE_p_f_p_void_size_t_size_t__p_void.getCPtr(sWIGTYPE_p_f_p_void_size_t_size_t__p_void));
    }

    public static void gim_set_free_handler(SWIGTYPE_p_f_p_void__void sWIGTYPE_p_f_p_void__void) {
        CollisionJNI.gim_set_free_handler(SWIGTYPE_p_f_p_void__void.getCPtr(sWIGTYPE_p_f_p_void__void));
    }

    public static SWIGTYPE_p_f_size_t__p_void gim_get_alloc_handler() {
        long jGim_get_alloc_handler = CollisionJNI.gim_get_alloc_handler();
        if (jGim_get_alloc_handler == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_size_t__p_void(jGim_get_alloc_handler, false);
    }

    public static SWIGTYPE_p_f_size_t__p_void gim_get_alloca_handler() {
        long jGim_get_alloca_handler = CollisionJNI.gim_get_alloca_handler();
        if (jGim_get_alloca_handler == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_size_t__p_void(jGim_get_alloca_handler, false);
    }

    public static SWIGTYPE_p_f_p_void_size_t_size_t__p_void gim_get_realloc_handler() {
        long jGim_get_realloc_handler = CollisionJNI.gim_get_realloc_handler();
        if (jGim_get_realloc_handler == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_p_void_size_t_size_t__p_void(jGim_get_realloc_handler, false);
    }

    public static SWIGTYPE_p_f_p_void__void gim_get_free_handler() {
        long jGim_get_free_handler = CollisionJNI.gim_get_free_handler();
        if (jGim_get_free_handler == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_p_void__void(jGim_get_free_handler, false);
    }

    public static long gim_alloc(long j) {
        return CollisionJNI.gim_alloc(j);
    }

    public static long gim_alloca(long j) {
        return CollisionJNI.gim_alloca(j);
    }

    public static long gim_realloc(long j, long j2, long j3) {
        return CollisionJNI.gim_realloc(j, j2, j3);
    }

    public static void gim_free(long j) {
        CollisionJNI.gim_free(j);
    }

    public static void gim_simd_memcpy(long j, long j2, long j3) {
        CollisionJNI.gim_simd_memcpy(j, j2, j3);
    }

    public static SWIGTYPE_p_GUINT getGim_prime_list() {
        long jGim_prime_list_get = CollisionJNI.gim_prime_list_get();
        if (jGim_prime_list_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_GUINT(jGim_prime_list_get, false);
    }

    public static SWIGTYPE_p_GUINT gim_next_prime(SWIGTYPE_p_GUINT sWIGTYPE_p_GUINT) {
        return new SWIGTYPE_p_GUINT(CollisionJNI.gim_next_prime(SWIGTYPE_p_GUINT.getCPtr(sWIGTYPE_p_GUINT)), true);
    }

    public static float gim_inv_sqrt(float f) {
        return CollisionJNI.gim_inv_sqrt(f);
    }

    public static float gim_sqrt(float f) {
        return CollisionJNI.gim_sqrt(f);
    }

    public static void gim_radix_sort_rtokens(GIM_RSORT_TOKEN gim_rsort_token, GIM_RSORT_TOKEN gim_rsort_token2, long j) {
        CollisionJNI.gim_radix_sort_rtokens(GIM_RSORT_TOKEN.getCPtr(gim_rsort_token), gim_rsort_token, GIM_RSORT_TOKEN.getCPtr(gim_rsort_token2), gim_rsort_token2, j);
    }

    public static _btMprSupport_t btMprSimplexPointW(_btMprSimplex_t _btmprsimplex_t, int i) {
        long jBtMprSimplexPointW = CollisionJNI.btMprSimplexPointW(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, i);
        if (jBtMprSimplexPointW == 0) {
            return null;
        }
        return new _btMprSupport_t(jBtMprSimplexPointW, false);
    }

    public static void btMprSimplexSetSize(_btMprSimplex_t _btmprsimplex_t, int i) {
        CollisionJNI.btMprSimplexSetSize(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, i);
    }

    public static int btMprSimplexSize(_btMprSimplex_t _btmprsimplex_t) {
        return CollisionJNI.btMprSimplexSize(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t);
    }

    public static _btMprSupport_t btMprSimplexPoint(_btMprSimplex_t _btmprsimplex_t, int i) {
        long jBtMprSimplexPoint = CollisionJNI.btMprSimplexPoint(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, i);
        if (jBtMprSimplexPoint == 0) {
            return null;
        }
        return new _btMprSupport_t(jBtMprSimplexPoint, false);
    }

    public static void btMprSupportCopy(_btMprSupport_t _btmprsupport_t, _btMprSupport_t _btmprsupport_t2) {
        CollisionJNI.btMprSupportCopy(_btMprSupport_t.getCPtr(_btmprsupport_t), _btmprsupport_t, _btMprSupport_t.getCPtr(_btmprsupport_t2), _btmprsupport_t2);
    }

    public static void btMprSimplexSet(_btMprSimplex_t _btmprsimplex_t, long j, _btMprSupport_t _btmprsupport_t) {
        CollisionJNI.btMprSimplexSet(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, j, _btMprSupport_t.getCPtr(_btmprsupport_t), _btmprsupport_t);
    }

    public static void btMprSimplexSwap(_btMprSimplex_t _btmprsimplex_t, long j, long j2) {
        CollisionJNI.btMprSimplexSwap(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, j, j2);
    }

    public static int btMprIsZero(float f) {
        return CollisionJNI.btMprIsZero(f);
    }

    public static int btMprEq(float f, float f2) {
        return CollisionJNI.btMprEq(f, f2);
    }

    public static int btMprVec3Eq(btVector3 btvector3, btVector3 btvector4) {
        return CollisionJNI.btMprVec3Eq(btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4);
    }

    public static void btMprVec3Set(btVector3 btvector3, float f, float f2, float f3) {
        CollisionJNI.btMprVec3Set(btVector3.getCPtr(btvector3), btvector3, f, f2, f3);
    }

    public static void btMprVec3Add(btVector3 btvector3, btVector3 btvector4) {
        CollisionJNI.btMprVec3Add(btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4);
    }

    public static void btMprVec3Copy(btVector3 btvector3, btVector3 btvector4) {
        CollisionJNI.btMprVec3Copy(btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4);
    }

    public static void btMprVec3Scale(btVector3 btvector3, float f) {
        CollisionJNI.btMprVec3Scale(btVector3.getCPtr(btvector3), btvector3, f);
    }

    public static float btMprVec3Dot(btVector3 btvector3, btVector3 btvector4) {
        return CollisionJNI.btMprVec3Dot(btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4);
    }

    public static float btMprVec3Len2(btVector3 btvector3) {
        return CollisionJNI.btMprVec3Len2(btVector3.getCPtr(btvector3), btvector3);
    }

    public static void btMprVec3Normalize(btVector3 btvector3) {
        CollisionJNI.btMprVec3Normalize(btVector3.getCPtr(btvector3), btvector3);
    }

    public static void btMprVec3Cross(btVector3 btvector3, btVector3 btvector4, btVector3 btvector5) {
        CollisionJNI.btMprVec3Cross(btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4, btVector3.getCPtr(btvector5), btvector5);
    }

    public static void btMprVec3Sub2(btVector3 btvector3, btVector3 btvector4, btVector3 btvector5) {
        CollisionJNI.btMprVec3Sub2(btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4, btVector3.getCPtr(btvector5), btvector5);
    }

    public static void btPortalDir(_btMprSimplex_t _btmprsimplex_t, btVector3 btvector3) {
        CollisionJNI.btPortalDir(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, btVector3.getCPtr(btvector3), btvector3);
    }

    public static int portalEncapsulesOrigin(_btMprSimplex_t _btmprsimplex_t, btVector3 btvector3) {
        return CollisionJNI.portalEncapsulesOrigin(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, btVector3.getCPtr(btvector3), btvector3);
    }

    public static int portalReachTolerance(_btMprSimplex_t _btmprsimplex_t, _btMprSupport_t _btmprsupport_t, btVector3 btvector3) {
        return CollisionJNI.portalReachTolerance(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, _btMprSupport_t.getCPtr(_btmprsupport_t), _btmprsupport_t, btVector3.getCPtr(btvector3), btvector3);
    }

    public static int portalCanEncapsuleOrigin(_btMprSimplex_t _btmprsimplex_t, _btMprSupport_t _btmprsupport_t, btVector3 btvector3) {
        return CollisionJNI.portalCanEncapsuleOrigin(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, _btMprSupport_t.getCPtr(_btmprsupport_t), _btmprsupport_t, btVector3.getCPtr(btvector3), btvector3);
    }

    public static void btExpandPortal(_btMprSimplex_t _btmprsimplex_t, _btMprSupport_t _btmprsupport_t) {
        CollisionJNI.btExpandPortal(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, _btMprSupport_t.getCPtr(_btmprsupport_t), _btmprsupport_t);
    }

    public static void btFindPos(_btMprSimplex_t _btmprsimplex_t, btVector3 btvector3) {
        CollisionJNI.btFindPos(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, btVector3.getCPtr(btvector3), btvector3);
    }

    public static float btMprVec3Dist2(btVector3 btvector3, btVector3 btvector4) {
        return CollisionJNI.btMprVec3Dist2(btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4);
    }

    public static float _btMprVec3PointSegmentDist2(btVector3 btvector3, btVector3 btvector4, btVector3 btvector5, btVector3 btvector6) {
        return CollisionJNI._btMprVec3PointSegmentDist2(btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4, btVector3.getCPtr(btvector5), btvector5, btVector3.getCPtr(btvector6), btvector6);
    }

    public static float btMprVec3PointTriDist2(btVector3 btvector3, btVector3 btvector4, btVector3 btvector5, btVector3 btvector6, btVector3 btvector7) {
        return CollisionJNI.btMprVec3PointTriDist2(btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4, btVector3.getCPtr(btvector5), btvector5, btVector3.getCPtr(btvector6), btvector6, btVector3.getCPtr(btvector7), btvector7);
    }

    public static void btFindPenetrTouch(_btMprSimplex_t _btmprsimplex_t, FloatBuffer floatBuffer, btVector3 btvector3, btVector3 btvector4) {
        CollisionJNI.btFindPenetrTouch(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, floatBuffer, btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4);
    }

    public static void btFindPenetrSegment(_btMprSimplex_t _btmprsimplex_t, FloatBuffer floatBuffer, btVector3 btvector3, btVector3 btvector4) {
        CollisionJNI.btFindPenetrSegment(_btMprSimplex_t.getCPtr(_btmprsimplex_t), _btmprsimplex_t, floatBuffer, btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4);
    }
}
