package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBodyHelpers extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btSoftBodyHelpers(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSoftBodyHelpers(long j, boolean z) {
        this("btSoftBodyHelpers", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(j, z);
    }

    public static long getCPtr(btSoftBodyHelpers btsoftbodyhelpers) {
        if (btsoftbodyhelpers == null) {
            return 0L;
        }
        return btsoftbodyhelpers.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                SoftbodyJNI.delete_btSoftBodyHelpers(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static void Draw(btSoftBody btsoftbody, btIDebugDraw btidebugdraw, int i) {
        SoftbodyJNI.btSoftBodyHelpers_Draw__SWIG_0(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, i);
    }

    public static void Draw(btSoftBody btsoftbody, btIDebugDraw btidebugdraw) {
        SoftbodyJNI.btSoftBodyHelpers_Draw__SWIG_1(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public static void DrawInfos(btSoftBody btsoftbody, btIDebugDraw btidebugdraw, boolean z, boolean z2, boolean z3) {
        SoftbodyJNI.btSoftBodyHelpers_DrawInfos(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, z, z2, z3);
    }

    public static void DrawNodeTree(btSoftBody btsoftbody, btIDebugDraw btidebugdraw, int i, int i2) {
        SoftbodyJNI.btSoftBodyHelpers_DrawNodeTree__SWIG_0(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, i, i2);
    }

    public static void DrawNodeTree(btSoftBody btsoftbody, btIDebugDraw btidebugdraw, int i) {
        SoftbodyJNI.btSoftBodyHelpers_DrawNodeTree__SWIG_1(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, i);
    }

    public static void DrawNodeTree(btSoftBody btsoftbody, btIDebugDraw btidebugdraw) {
        SoftbodyJNI.btSoftBodyHelpers_DrawNodeTree__SWIG_2(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public static void DrawFaceTree(btSoftBody btsoftbody, btIDebugDraw btidebugdraw, int i, int i2) {
        SoftbodyJNI.btSoftBodyHelpers_DrawFaceTree__SWIG_0(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, i, i2);
    }

    public static void DrawFaceTree(btSoftBody btsoftbody, btIDebugDraw btidebugdraw, int i) {
        SoftbodyJNI.btSoftBodyHelpers_DrawFaceTree__SWIG_1(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, i);
    }

    public static void DrawFaceTree(btSoftBody btsoftbody, btIDebugDraw btidebugdraw) {
        SoftbodyJNI.btSoftBodyHelpers_DrawFaceTree__SWIG_2(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public static void DrawClusterTree(btSoftBody btsoftbody, btIDebugDraw btidebugdraw, int i, int i2) {
        SoftbodyJNI.btSoftBodyHelpers_DrawClusterTree__SWIG_0(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, i, i2);
    }

    public static void DrawClusterTree(btSoftBody btsoftbody, btIDebugDraw btidebugdraw, int i) {
        SoftbodyJNI.btSoftBodyHelpers_DrawClusterTree__SWIG_1(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, i);
    }

    public static void DrawClusterTree(btSoftBody btsoftbody, btIDebugDraw btidebugdraw) {
        SoftbodyJNI.btSoftBodyHelpers_DrawClusterTree__SWIG_2(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public static void DrawFrame(btSoftBody btsoftbody, btIDebugDraw btidebugdraw) {
        SoftbodyJNI.btSoftBodyHelpers_DrawFrame(btSoftBody.getCPtr(btsoftbody), btsoftbody, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public static btSoftBody CreateRope(btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, int i, int i2) {
        long jBtSoftBodyHelpers_CreateRope = SoftbodyJNI.btSoftBodyHelpers_CreateRope(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, vector3, vector4, i, i2);
        if (jBtSoftBodyHelpers_CreateRope == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreateRope, false);
    }

    public static btSoftBody CreatePatch(btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, int i, int i2, int i3, boolean z) {
        long jBtSoftBodyHelpers_CreatePatch = SoftbodyJNI.btSoftBodyHelpers_CreatePatch(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, vector3, vector4, vector5, vector6, i, i2, i3, z);
        if (jBtSoftBodyHelpers_CreatePatch == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreatePatch, false);
    }

    public static btSoftBody CreatePatchUV(btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, int i, int i2, int i3, boolean z, FloatBuffer floatBuffer) {
        long jBtSoftBodyHelpers_CreatePatchUV__SWIG_0 = SoftbodyJNI.btSoftBodyHelpers_CreatePatchUV__SWIG_0(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, vector3, vector4, vector5, vector6, i, i2, i3, z, floatBuffer);
        if (jBtSoftBodyHelpers_CreatePatchUV__SWIG_0 == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreatePatchUV__SWIG_0, false);
    }

    public static btSoftBody CreatePatchUV(btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, int i, int i2, int i3, boolean z) {
        long jBtSoftBodyHelpers_CreatePatchUV__SWIG_1 = SoftbodyJNI.btSoftBodyHelpers_CreatePatchUV__SWIG_1(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, vector3, vector4, vector5, vector6, i, i2, i3, z);
        if (jBtSoftBodyHelpers_CreatePatchUV__SWIG_1 == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreatePatchUV__SWIG_1, false);
    }

    public static float CalculateUV(int i, int i2, int i3, int i4, int i5) {
        return SoftbodyJNI.btSoftBodyHelpers_CalculateUV(i, i2, i3, i4, i5);
    }

    public static btSoftBody CreateEllipsoid(btSoftBodyWorldInfo btsoftbodyworldinfo, Vector3 vector3, Vector3 vector4, int i) {
        long jBtSoftBodyHelpers_CreateEllipsoid = SoftbodyJNI.btSoftBodyHelpers_CreateEllipsoid(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, vector3, vector4, i);
        if (jBtSoftBodyHelpers_CreateEllipsoid == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreateEllipsoid, false);
    }

    public static btSoftBody CreateFromTriMesh(btSoftBodyWorldInfo btsoftbodyworldinfo, FloatBuffer floatBuffer, IntBuffer intBuffer, int i, boolean z) {
        long jBtSoftBodyHelpers_CreateFromTriMesh__SWIG_0 = SoftbodyJNI.btSoftBodyHelpers_CreateFromTriMesh__SWIG_0(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, floatBuffer, intBuffer, i, z);
        if (jBtSoftBodyHelpers_CreateFromTriMesh__SWIG_0 == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreateFromTriMesh__SWIG_0, false);
    }

    public static btSoftBody CreateFromTriMesh(btSoftBodyWorldInfo btsoftbodyworldinfo, FloatBuffer floatBuffer, IntBuffer intBuffer, int i) {
        long jBtSoftBodyHelpers_CreateFromTriMesh__SWIG_1 = SoftbodyJNI.btSoftBodyHelpers_CreateFromTriMesh__SWIG_1(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, floatBuffer, intBuffer, i);
        if (jBtSoftBodyHelpers_CreateFromTriMesh__SWIG_1 == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreateFromTriMesh__SWIG_1, false);
    }

    public static btSoftBody CreateFromConvexHull(btSoftBodyWorldInfo btsoftbodyworldinfo, btVector3 btvector3, int i, boolean z) {
        long jBtSoftBodyHelpers_CreateFromConvexHull__SWIG_0 = SoftbodyJNI.btSoftBodyHelpers_CreateFromConvexHull__SWIG_0(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, btVector3.getCPtr(btvector3), btvector3, i, z);
        if (jBtSoftBodyHelpers_CreateFromConvexHull__SWIG_0 == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreateFromConvexHull__SWIG_0, false);
    }

    public static btSoftBody CreateFromConvexHull(btSoftBodyWorldInfo btsoftbodyworldinfo, btVector3 btvector3, int i) {
        long jBtSoftBodyHelpers_CreateFromConvexHull__SWIG_1 = SoftbodyJNI.btSoftBodyHelpers_CreateFromConvexHull__SWIG_1(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, btVector3.getCPtr(btvector3), btvector3, i);
        if (jBtSoftBodyHelpers_CreateFromConvexHull__SWIG_1 == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreateFromConvexHull__SWIG_1, false);
    }

    public static btSoftBody CreateFromTetGenData(btSoftBodyWorldInfo btsoftbodyworldinfo, String str, String str2, String str3, boolean z, boolean z2, boolean z3) {
        long jBtSoftBodyHelpers_CreateFromTetGenData = SoftbodyJNI.btSoftBodyHelpers_CreateFromTetGenData(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, str, str2, str3, z, z2, z3);
        if (jBtSoftBodyHelpers_CreateFromTetGenData == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyHelpers_CreateFromTetGenData, false);
    }

    public static void ReoptimizeLinkOrder(btSoftBody btsoftbody) {
        SoftbodyJNI.btSoftBodyHelpers_ReoptimizeLinkOrder(btSoftBody.getCPtr(btsoftbody), btsoftbody);
    }

    public btSoftBodyHelpers() {
        this(SoftbodyJNI.new_btSoftBodyHelpers(), true);
    }
}
