package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3Array;

/* JADX INFO: loaded from: classes8.dex */
public class btPolyhedralContactClipping extends BulletBase {
    private long swigCPtr;

    protected btPolyhedralContactClipping(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPolyhedralContactClipping(long j, boolean z) {
        this("btPolyhedralContactClipping", j, z);
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

    public static long getCPtr(btPolyhedralContactClipping btpolyhedralcontactclipping) {
        if (btpolyhedralcontactclipping == null) {
            return 0L;
        }
        return btpolyhedralcontactclipping.swigCPtr;
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
                CollisionJNI.delete_btPolyhedralContactClipping(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static void clipHullAgainstHull(Vector3 vector3, btConvexPolyhedron btconvexpolyhedron, btConvexPolyhedron btconvexpolyhedron2, Matrix4 matrix4, Matrix4 matrix5, float f, float f2, btVector3Array btvector3array, btVector3Array btvector3array2, btDiscreteCollisionDetectorInterface.Result result) {
        CollisionJNI.btPolyhedralContactClipping_clipHullAgainstHull(vector3, btConvexPolyhedron.getCPtr(btconvexpolyhedron), btconvexpolyhedron, btConvexPolyhedron.getCPtr(btconvexpolyhedron2), btconvexpolyhedron2, matrix4, matrix5, f, f2, btVector3Array.getCPtr(btvector3array), btvector3array, btVector3Array.getCPtr(btvector3array2), btvector3array2, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result);
    }

    public static void clipFaceAgainstHull(Vector3 vector3, btConvexPolyhedron btconvexpolyhedron, Matrix4 matrix4, btVector3Array btvector3array, btVector3Array btvector3array2, float f, float f2, btDiscreteCollisionDetectorInterface.Result result) {
        CollisionJNI.btPolyhedralContactClipping_clipFaceAgainstHull(vector3, btConvexPolyhedron.getCPtr(btconvexpolyhedron), btconvexpolyhedron, matrix4, btVector3Array.getCPtr(btvector3array), btvector3array, btVector3Array.getCPtr(btvector3array2), btvector3array2, f, f2, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result);
    }

    public static boolean findSeparatingAxis(btConvexPolyhedron btconvexpolyhedron, btConvexPolyhedron btconvexpolyhedron2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, btDiscreteCollisionDetectorInterface.Result result) {
        return CollisionJNI.btPolyhedralContactClipping_findSeparatingAxis(btConvexPolyhedron.getCPtr(btconvexpolyhedron), btconvexpolyhedron, btConvexPolyhedron.getCPtr(btconvexpolyhedron2), btconvexpolyhedron2, matrix4, matrix5, vector3, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result);
    }

    public static void clipFace(btVector3Array btvector3array, btVector3Array btvector3array2, Vector3 vector3, float f) {
        CollisionJNI.btPolyhedralContactClipping_clipFace(btVector3Array.getCPtr(btvector3array), btvector3array, btVector3Array.getCPtr(btvector3array2), btvector3array2, vector3, f);
    }

    public btPolyhedralContactClipping() {
        this(CollisionJNI.new_btPolyhedralContactClipping(), true);
    }
}
