package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class LinearMath implements LinearMathConstants {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Vector3 staticVector3 = new Vector3();
    public static final Pool<Vector3> poolVector3 = new Pool<Vector3>() { // from class: com.badlogic.gdx.physics.bullet.linearmath.LinearMath.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Vector3 newObject() {
            return new Vector3();
        }
    };
    public static final Quaternion staticQuaternion = new Quaternion();
    public static final Pool<Quaternion> poolQuaternion = new Pool<Quaternion>() { // from class: com.badlogic.gdx.physics.bullet.linearmath.LinearMath.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Quaternion newObject() {
            return new Quaternion();
        }
    };
    public static final Matrix3 staticMatrix3 = new Matrix3();
    public static final Pool<Matrix3> poolMatrix3 = new Pool<Matrix3>() { // from class: com.badlogic.gdx.physics.bullet.linearmath.LinearMath.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix3 newObject() {
            return new Matrix3();
        }
    };
    public static final Matrix4 staticMatrix4 = new Matrix4();
    public static final Pool<Matrix4> poolMatrix4 = new Pool<Matrix4>() { // from class: com.badlogic.gdx.physics.bullet.linearmath.LinearMath.4
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public Matrix4 newObject() {
            return new Matrix4();
        }
    };

    public static int btGetVersion() {
        return LinearMathJNI.btGetVersion();
    }

    public static float btSqrt(float f) {
        return LinearMathJNI.btSqrt(f);
    }

    public static float btFabs(float f) {
        return LinearMathJNI.btFabs(f);
    }

    public static float btCos(float f) {
        return LinearMathJNI.btCos(f);
    }

    public static float btSin(float f) {
        return LinearMathJNI.btSin(f);
    }

    public static float btTan(float f) {
        return LinearMathJNI.btTan(f);
    }

    public static float btAcos(float f) {
        return LinearMathJNI.btAcos(f);
    }

    public static float btAsin(float f) {
        return LinearMathJNI.btAsin(f);
    }

    public static float btAtan(float f) {
        return LinearMathJNI.btAtan(f);
    }

    public static float btAtan2(float f, float f2) {
        return LinearMathJNI.btAtan2(f, f2);
    }

    public static float btExp(float f) {
        return LinearMathJNI.btExp(f);
    }

    public static float btLog(float f) {
        return LinearMathJNI.btLog(f);
    }

    public static float btPow(float f, float f2) {
        return LinearMathJNI.btPow(f, f2);
    }

    public static float btFmod(float f, float f2) {
        return LinearMathJNI.btFmod(f, f2);
    }

    public static float btAtan2Fast(float f, float f2) {
        return LinearMathJNI.btAtan2Fast(f, f2);
    }

    public static boolean btFuzzyZero(float f) {
        return LinearMathJNI.btFuzzyZero(f);
    }

    public static boolean btEqual(float f, float f2) {
        return LinearMathJNI.btEqual(f, f2);
    }

    public static boolean btGreaterEqual(float f, float f2) {
        return LinearMathJNI.btGreaterEqual(f, f2);
    }

    public static int btIsNegative(float f) {
        return LinearMathJNI.btIsNegative(f);
    }

    public static float btRadians(float f) {
        return LinearMathJNI.btRadians(f);
    }

    public static float btDegrees(float f) {
        return LinearMathJNI.btDegrees(f);
    }

    public static float btFsel(float f, float f2, float f3) {
        return LinearMathJNI.btFsel(f, f2, f3);
    }

    public static boolean btMachineIsLittleEndian() {
        return LinearMathJNI.btMachineIsLittleEndian();
    }

    public static long btSelect(long j, long j2, long j3) {
        return LinearMathJNI.btSelect__SWIG_0(j, j2, j3);
    }

    public static int btSelect(long j, int i, int i2) {
        return LinearMathJNI.btSelect__SWIG_1(j, i, i2);
    }

    public static float btSelect(long j, float f, float f2) {
        return LinearMathJNI.btSelect__SWIG_2(j, f, f2);
    }

    public static long btSwapEndian(long j) {
        return LinearMathJNI.btSwapEndian__SWIG_0(j);
    }

    public static int btSwapEndian(int i) {
        return LinearMathJNI.btSwapEndian__SWIG_1(i);
    }

    public static long btSwapEndianInt(int i) {
        return LinearMathJNI.btSwapEndianInt(i);
    }

    public static int btSwapEndian(short s) {
        return LinearMathJNI.btSwapEndian__SWIG_2(s);
    }

    public static long btSwapEndianFloat(float f) {
        return LinearMathJNI.btSwapEndianFloat(f);
    }

    public static float btUnswapEndianFloat(long j) {
        return LinearMathJNI.btUnswapEndianFloat(j);
    }

    public static void btSwapEndianDouble(double d, ByteBuffer byteBuffer) {
        LinearMathJNI.btSwapEndianDouble(d, byteBuffer);
    }

    public static double btUnswapEndianDouble(ByteBuffer byteBuffer) {
        return LinearMathJNI.btUnswapEndianDouble(byteBuffer);
    }

    public static float btLargeDot(FloatBuffer floatBuffer, FloatBuffer floatBuffer2, int i) {
        return LinearMathJNI.btLargeDot(floatBuffer, floatBuffer2, i);
    }

    public static float btNormalizeAngle(float f) {
        return LinearMathJNI.btNormalizeAngle(f);
    }

    public static boolean operatorEqualTo(Matrix4 matrix4, Matrix4 matrix5) {
        return LinearMathJNI.operatorEqualTo__SWIG_0(matrix4, matrix5);
    }

    public static Vector3 operatorAddition(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.operatorAddition__SWIG_0(vector3, vector4);
    }

    public static Vector3 operatorMultiplication(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.operatorMultiplication__SWIG_0(vector3, vector4);
    }

    public static Vector3 operatorSubtraction(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.operatorSubtraction__SWIG_0(vector3, vector4);
    }

    public static Vector3 operatorSubtraction(Vector3 vector3) {
        return LinearMathJNI.operatorSubtraction__SWIG_1(vector3);
    }

    public static Vector3 operatorMultiplication(Vector3 vector3, float f) {
        return LinearMathJNI.operatorMultiplication__SWIG_1(vector3, f);
    }

    public static Vector3 operatorMultiplication(float f, Vector3 vector3) {
        return LinearMathJNI.operatorMultiplication__SWIG_2(f, vector3);
    }

    public static Vector3 operatorDivision(Vector3 vector3, float f) {
        return LinearMathJNI.operatorDivision__SWIG_0(vector3, f);
    }

    public static Vector3 operatorDivision(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.operatorDivision__SWIG_1(vector3, vector4);
    }

    public static float btDot(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.btDot(vector3, vector4);
    }

    public static float btDistance2(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.btDistance2(vector3, vector4);
    }

    public static float btDistance(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.btDistance(vector3, vector4);
    }

    public static float btAngle(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.btAngle__SWIG_0(vector3, vector4);
    }

    public static Vector3 btCross(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.btCross(vector3, vector4);
    }

    public static float btTriple(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        return LinearMathJNI.btTriple(vector3, vector4, vector5);
    }

    public static Vector3 lerp(Vector3 vector3, Vector3 vector4, float f) {
        return LinearMathJNI.lerp(vector3, vector4, f);
    }

    public static void btSwapScalarEndian(float f, SWIGTYPE_p_float sWIGTYPE_p_float) {
        LinearMathJNI.btSwapScalarEndian(f, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public static void btSwapVector3Endian(Vector3 vector3, Vector3 vector4) {
        LinearMathJNI.btSwapVector3Endian(vector3, vector4);
    }

    public static void btUnSwapVector3Endian(Vector3 vector3) {
        LinearMathJNI.btUnSwapVector3Endian(vector3);
    }

    public static Quaternion operatorMultiplication(Quaternion quaternion, Quaternion quaternion2) {
        return LinearMathJNI.operatorMultiplication__SWIG_3(quaternion, quaternion2);
    }

    public static Quaternion operatorMultiplication(Quaternion quaternion, Vector3 vector3) {
        return LinearMathJNI.operatorMultiplication__SWIG_4(quaternion, vector3);
    }

    public static Quaternion operatorMultiplication(Vector3 vector3, Quaternion quaternion) {
        return LinearMathJNI.operatorMultiplication__SWIG_5(vector3, quaternion);
    }

    public static float dot(Quaternion quaternion, Quaternion quaternion2) {
        return LinearMathJNI.dot(quaternion, quaternion2);
    }

    public static float length(Quaternion quaternion) {
        return LinearMathJNI.length(quaternion);
    }

    public static float btAngle(Quaternion quaternion, Quaternion quaternion2) {
        return LinearMathJNI.btAngle__SWIG_1(quaternion, quaternion2);
    }

    public static Quaternion inverse(Quaternion quaternion) {
        return LinearMathJNI.inverse(quaternion);
    }

    public static Quaternion slerp(Quaternion quaternion, Quaternion quaternion2, float f) {
        return LinearMathJNI.slerp(quaternion, quaternion2, f);
    }

    public static Vector3 quatRotate(Quaternion quaternion, Vector3 vector3) {
        return LinearMathJNI.quatRotate(quaternion, vector3);
    }

    public static Quaternion shortestArcQuat(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.shortestArcQuat(vector3, vector4);
    }

    public static Quaternion shortestArcQuatNormalize2(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.shortestArcQuatNormalize2(vector3, vector4);
    }

    public static Matrix3 operatorMultiplication(Matrix3 matrix3, float f) {
        return LinearMathJNI.operatorMultiplication__SWIG_6(matrix3, f);
    }

    public static Matrix3 operatorAddition(Matrix3 matrix3, Matrix3 matrix4) {
        return LinearMathJNI.operatorAddition__SWIG_1(matrix3, matrix4);
    }

    public static Matrix3 operatorSubtraction(Matrix3 matrix3, Matrix3 matrix4) {
        return LinearMathJNI.operatorSubtraction__SWIG_2(matrix3, matrix4);
    }

    public static Vector3 operatorMultiplication(Matrix3 matrix3, Vector3 vector3) {
        return LinearMathJNI.operatorMultiplication__SWIG_7(matrix3, vector3);
    }

    public static Vector3 operatorMultiplication(Vector3 vector3, Matrix3 matrix3) {
        return LinearMathJNI.operatorMultiplication__SWIG_8(vector3, matrix3);
    }

    public static Matrix3 operatorMultiplication(Matrix3 matrix3, Matrix3 matrix4) {
        return LinearMathJNI.operatorMultiplication__SWIG_9(matrix3, matrix4);
    }

    public static boolean operatorEqualTo(Matrix3 matrix3, Matrix3 matrix4) {
        return LinearMathJNI.operatorEqualTo__SWIG_1(matrix3, matrix4);
    }

    public static void AabbExpand(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        LinearMathJNI.AabbExpand(vector3, vector4, vector5, vector6);
    }

    public static boolean TestPointAgainstAabb2(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        return LinearMathJNI.TestPointAgainstAabb2(vector3, vector4, vector5);
    }

    public static boolean TestAabbAgainstAabb2(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        return LinearMathJNI.TestAabbAgainstAabb2(vector3, vector4, vector5, vector6);
    }

    public static boolean TestTriangleAgainstAabb2(btVector3 btvector3, Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.TestTriangleAgainstAabb2(btVector3.getCPtr(btvector3), btvector3, vector3, vector4);
    }

    public static int btOutcode(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.btOutcode(vector3, vector4);
    }

    public static boolean btRayAabb2(Vector3 vector3, Vector3 vector4, long[] jArr, btVector3 btvector3, SWIGTYPE_p_float sWIGTYPE_p_float, float f, float f2) {
        return LinearMathJNI.btRayAabb2(vector3, vector4, jArr, btVector3.getCPtr(btvector3), btvector3, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), f, f2);
    }

    public static boolean btRayAabb(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, SWIGTYPE_p_float sWIGTYPE_p_float, Vector3 vector7) {
        return LinearMathJNI.btRayAabb(vector3, vector4, vector5, vector6, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), vector7);
    }

    public static void btTransformAabb(Vector3 vector3, float f, Matrix4 matrix4, Vector3 vector4, Vector3 vector5) {
        LinearMathJNI.btTransformAabb__SWIG_0(vector3, f, matrix4, vector4, vector5);
    }

    public static void btTransformAabb(Vector3 vector3, Vector3 vector4, float f, Matrix4 matrix4, Vector3 vector5, Vector3 vector6) {
        LinearMathJNI.btTransformAabb__SWIG_1(vector3, vector4, f, matrix4, vector5, vector6);
    }

    public static long testQuantizedAabbAgainstQuantizedAabb(IntBuffer intBuffer, IntBuffer intBuffer2, IntBuffer intBuffer3, IntBuffer intBuffer4) {
        return LinearMathJNI.testQuantizedAabbAgainstQuantizedAabb(intBuffer, intBuffer2, intBuffer3, intBuffer4);
    }

    public static void GEN_srand(long j) {
        LinearMathJNI.GEN_srand(j);
    }

    public static long GEN_rand() {
        return LinearMathJNI.GEN_rand();
    }

    public static Vector3 btAabbSupport(Vector3 vector3, Vector3 vector4) {
        return LinearMathJNI.btAabbSupport(vector3, vector4);
    }

    public static void GrahamScanConvexHull2D(SWIGTYPE_p_btAlignedObjectArrayT_GrahamVector3_t sWIGTYPE_p_btAlignedObjectArrayT_GrahamVector3_t, SWIGTYPE_p_btAlignedObjectArrayT_GrahamVector3_t sWIGTYPE_p_btAlignedObjectArrayT_GrahamVector3_t2, Vector3 vector3) {
        LinearMathJNI.GrahamScanConvexHull2D(SWIGTYPE_p_btAlignedObjectArrayT_GrahamVector3_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_GrahamVector3_t), SWIGTYPE_p_btAlignedObjectArrayT_GrahamVector3_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_GrahamVector3_t2), vector3);
    }

    public static SWIGTYPE_p_f_p_q_const__char__void btGetCurrentEnterProfileZoneFunc() {
        long jBtGetCurrentEnterProfileZoneFunc = LinearMathJNI.btGetCurrentEnterProfileZoneFunc();
        if (jBtGetCurrentEnterProfileZoneFunc == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_p_q_const__char__void(jBtGetCurrentEnterProfileZoneFunc, false);
    }

    public static SWIGTYPE_p_f___void btGetCurrentLeaveProfileZoneFunc() {
        long jBtGetCurrentLeaveProfileZoneFunc = LinearMathJNI.btGetCurrentLeaveProfileZoneFunc();
        if (jBtGetCurrentLeaveProfileZoneFunc == 0) {
            return null;
        }
        return new SWIGTYPE_p_f___void(jBtGetCurrentLeaveProfileZoneFunc, false);
    }

    public static void btSetCustomEnterProfileZoneFunc(SWIGTYPE_p_f_p_q_const__char__void sWIGTYPE_p_f_p_q_const__char__void) {
        LinearMathJNI.btSetCustomEnterProfileZoneFunc(SWIGTYPE_p_f_p_q_const__char__void.getCPtr(sWIGTYPE_p_f_p_q_const__char__void));
    }

    public static void btSetCustomLeaveProfileZoneFunc(SWIGTYPE_p_f___void sWIGTYPE_p_f___void) {
        LinearMathJNI.btSetCustomLeaveProfileZoneFunc(SWIGTYPE_p_f___void.getCPtr(sWIGTYPE_p_f___void));
    }

    public static long btAlignedAllocInternal(long j, int i) {
        return LinearMathJNI.btAlignedAllocInternal(j, i);
    }

    public static void btAlignedFreeInternal(long j) {
        LinearMathJNI.btAlignedFreeInternal(j);
    }

    public static void btAlignedAllocSetCustom(SWIGTYPE_p_f_size_t__p_void sWIGTYPE_p_f_size_t__p_void, SWIGTYPE_p_f_p_void__void sWIGTYPE_p_f_p_void__void) {
        LinearMathJNI.btAlignedAllocSetCustom(SWIGTYPE_p_f_size_t__p_void.getCPtr(sWIGTYPE_p_f_size_t__p_void), SWIGTYPE_p_f_p_void__void.getCPtr(sWIGTYPE_p_f_p_void__void));
    }

    public static void btAlignedAllocSetCustomAligned(SWIGTYPE_p_f_size_t_int__p_void sWIGTYPE_p_f_size_t_int__p_void, SWIGTYPE_p_f_p_void__void sWIGTYPE_p_f_p_void__void) {
        LinearMathJNI.btAlignedAllocSetCustomAligned(SWIGTYPE_p_f_size_t_int__p_void.getCPtr(sWIGTYPE_p_f_size_t_int__p_void), SWIGTYPE_p_f_p_void__void.getCPtr(sWIGTYPE_p_f_p_void__void));
    }

    public static int getBT_HASH_NULL() {
        return LinearMathJNI.BT_HASH_NULL_get();
    }

    public static void setElem(SWIGTYPE_p_btMatrixXT_double_t sWIGTYPE_p_btMatrixXT_double_t, int i, int i2, double d) {
        LinearMathJNI.setElem__SWIG_0(SWIGTYPE_p_btMatrixXT_double_t.getCPtr(sWIGTYPE_p_btMatrixXT_double_t), i, i2, d);
    }

    public static void setElem(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, int i, int i2, float f) {
        LinearMathJNI.setElem__SWIG_1(SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), i, i2, f);
    }

    public static long polarDecompose(Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5) {
        return LinearMathJNI.polarDecompose(matrix3, matrix4, matrix5);
    }

    public static void setSBulletDNAstr(String str) {
        LinearMathJNI.sBulletDNAstr_set(str);
    }

    public static String getSBulletDNAstr() {
        return LinearMathJNI.sBulletDNAstr_get();
    }

    public static void setSBulletDNAlen(int i) {
        LinearMathJNI.sBulletDNAlen_set(i);
    }

    public static int getSBulletDNAlen() {
        return LinearMathJNI.sBulletDNAlen_get();
    }

    public static void setSBulletDNAstr64(String str) {
        LinearMathJNI.sBulletDNAstr64_set(str);
    }

    public static String getSBulletDNAstr64() {
        return LinearMathJNI.sBulletDNAstr64_get();
    }

    public static void setSBulletDNAlen64(int i) {
        LinearMathJNI.sBulletDNAlen64_set(i);
    }

    public static int getSBulletDNAlen64() {
        return LinearMathJNI.sBulletDNAlen64_get();
    }

    public static int btStrLen(String str) {
        return LinearMathJNI.btStrLen(str);
    }

    public static long getBT_MAX_THREAD_COUNT() {
        return LinearMathJNI.BT_MAX_THREAD_COUNT_get();
    }

    public static boolean btIsMainThread() {
        return LinearMathJNI.btIsMainThread();
    }

    public static boolean btThreadsAreRunning() {
        return LinearMathJNI.btThreadsAreRunning();
    }

    public static long btGetCurrentThreadIndex() {
        return LinearMathJNI.btGetCurrentThreadIndex();
    }

    public static void btResetThreadIndexCounter() {
        LinearMathJNI.btResetThreadIndexCounter();
    }

    public static void btMutexLock(btSpinMutex btspinmutex) {
        LinearMathJNI.btMutexLock(btSpinMutex.getCPtr(btspinmutex), btspinmutex);
    }

    public static void btMutexUnlock(btSpinMutex btspinmutex) {
        LinearMathJNI.btMutexUnlock(btSpinMutex.getCPtr(btspinmutex), btspinmutex);
    }

    public static boolean btMutexTryLock(btSpinMutex btspinmutex) {
        return LinearMathJNI.btMutexTryLock(btSpinMutex.getCPtr(btspinmutex), btspinmutex);
    }

    public static void btSetTaskScheduler(btITaskScheduler btitaskscheduler) {
        LinearMathJNI.btSetTaskScheduler(btITaskScheduler.getCPtr(btitaskscheduler), btitaskscheduler);
    }

    public static btITaskScheduler btGetTaskScheduler() {
        long jBtGetTaskScheduler = LinearMathJNI.btGetTaskScheduler();
        if (jBtGetTaskScheduler == 0) {
            return null;
        }
        return new btITaskScheduler(jBtGetTaskScheduler, false);
    }

    public static btITaskScheduler btGetSequentialTaskScheduler() {
        long jBtGetSequentialTaskScheduler = LinearMathJNI.btGetSequentialTaskScheduler();
        if (jBtGetSequentialTaskScheduler == 0) {
            return null;
        }
        return new btITaskScheduler(jBtGetSequentialTaskScheduler, false);
    }

    public static btITaskScheduler btGetOpenMPTaskScheduler() {
        long jBtGetOpenMPTaskScheduler = LinearMathJNI.btGetOpenMPTaskScheduler();
        if (jBtGetOpenMPTaskScheduler == 0) {
            return null;
        }
        return new btITaskScheduler(jBtGetOpenMPTaskScheduler, false);
    }

    public static btITaskScheduler btGetTBBTaskScheduler() {
        long jBtGetTBBTaskScheduler = LinearMathJNI.btGetTBBTaskScheduler();
        if (jBtGetTBBTaskScheduler == 0) {
            return null;
        }
        return new btITaskScheduler(jBtGetTBBTaskScheduler, false);
    }

    public static btITaskScheduler btGetPPLTaskScheduler() {
        long jBtGetPPLTaskScheduler = LinearMathJNI.btGetPPLTaskScheduler();
        if (jBtGetPPLTaskScheduler == 0) {
            return null;
        }
        return new btITaskScheduler(jBtGetPPLTaskScheduler, false);
    }

    public static void btParallelFor(int i, int i2, int i3, btIParallelForBody btiparallelforbody) {
        LinearMathJNI.btParallelFor(i, i2, i3, btIParallelForBody.getCPtr(btiparallelforbody), btiparallelforbody);
    }
}
