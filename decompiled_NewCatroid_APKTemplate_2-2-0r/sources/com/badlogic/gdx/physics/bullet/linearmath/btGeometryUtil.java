package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btGeometryUtil extends BulletBase {
    private long swigCPtr;

    protected btGeometryUtil(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGeometryUtil(long j, boolean z) {
        this("btGeometryUtil", j, z);
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

    public static long getCPtr(btGeometryUtil btgeometryutil) {
        if (btgeometryutil == null) {
            return 0L;
        }
        return btgeometryutil.swigCPtr;
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
                LinearMathJNI.delete_btGeometryUtil(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static void getPlaneEquationsFromVertices(btVector3Array btvector3array, btVector3Array btvector3array2) {
        LinearMathJNI.btGeometryUtil_getPlaneEquationsFromVertices(btVector3Array.getCPtr(btvector3array), btvector3array, btVector3Array.getCPtr(btvector3array2), btvector3array2);
    }

    public static void getVerticesFromPlaneEquations(btVector3Array btvector3array, btVector3Array btvector3array2) {
        LinearMathJNI.btGeometryUtil_getVerticesFromPlaneEquations(btVector3Array.getCPtr(btvector3array), btvector3array, btVector3Array.getCPtr(btvector3array2), btvector3array2);
    }

    public static boolean isInside(btVector3Array btvector3array, Vector3 vector3, float f) {
        return LinearMathJNI.btGeometryUtil_isInside(btVector3Array.getCPtr(btvector3array), btvector3array, vector3, f);
    }

    public static boolean isPointInsidePlanes(btVector3Array btvector3array, Vector3 vector3, float f) {
        return LinearMathJNI.btGeometryUtil_isPointInsidePlanes(btVector3Array.getCPtr(btvector3array), btvector3array, vector3, f);
    }

    public static boolean areVerticesBehindPlane(Vector3 vector3, btVector3Array btvector3array, float f) {
        return LinearMathJNI.btGeometryUtil_areVerticesBehindPlane(vector3, btVector3Array.getCPtr(btvector3array), btvector3array, f);
    }

    public btGeometryUtil() {
        this(LinearMathJNI.new_btGeometryUtil(), true);
    }
}
