package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexPointCloudShape extends btPolyhedralConvexAabbCachingShape {
    private long swigCPtr;

    protected btConvexPointCloudShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexPointCloudShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexPointCloudShape(long j, boolean z) {
        this("btConvexPointCloudShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexPointCloudShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexPointCloudShape btconvexpointcloudshape) {
        if (btconvexpointcloudshape == null) {
            return 0L;
        }
        return btconvexpointcloudshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btConvexPointCloudShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btConvexPointCloudShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btConvexPointCloudShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btConvexPointCloudShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btConvexPointCloudShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btConvexPointCloudShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btConvexPointCloudShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btConvexPointCloudShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btConvexPointCloudShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btConvexPointCloudShape() {
        this(CollisionJNI.new_btConvexPointCloudShape__SWIG_0(), true);
    }

    public btConvexPointCloudShape(btVector3 btvector3, int i, Vector3 vector3, boolean z) {
        this(CollisionJNI.new_btConvexPointCloudShape__SWIG_1(btVector3.getCPtr(btvector3), btvector3, i, vector3, z), true);
    }

    public btConvexPointCloudShape(btVector3 btvector3, int i, Vector3 vector3) {
        this(CollisionJNI.new_btConvexPointCloudShape__SWIG_2(btVector3.getCPtr(btvector3), btvector3, i, vector3), true);
    }

    public void setPoints(btVector3 btvector3, int i, boolean z, Vector3 vector3) {
        CollisionJNI.btConvexPointCloudShape_setPoints__SWIG_0(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, z, vector3);
    }

    public void setPoints(btVector3 btvector3, int i, boolean z) {
        CollisionJNI.btConvexPointCloudShape_setPoints__SWIG_1(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, z);
    }

    public void setPoints(btVector3 btvector3, int i) {
        CollisionJNI.btConvexPointCloudShape_setPoints__SWIG_2(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i);
    }

    public btVector3 getUnscaledPoints() {
        long jBtConvexPointCloudShape_getUnscaledPoints = CollisionJNI.btConvexPointCloudShape_getUnscaledPoints(this.swigCPtr, this);
        if (jBtConvexPointCloudShape_getUnscaledPoints == 0) {
            return null;
        }
        return new btVector3(jBtConvexPointCloudShape_getUnscaledPoints, false);
    }

    public btVector3 getUnscaledPointsConst() {
        long jBtConvexPointCloudShape_getUnscaledPointsConst = CollisionJNI.btConvexPointCloudShape_getUnscaledPointsConst(this.swigCPtr, this);
        if (jBtConvexPointCloudShape_getUnscaledPointsConst == 0) {
            return null;
        }
        return new btVector3(jBtConvexPointCloudShape_getUnscaledPointsConst, false);
    }

    public int getNumPoints() {
        return CollisionJNI.btConvexPointCloudShape_getNumPoints(this.swigCPtr, this);
    }

    public Vector3 getScaledPoint(int i) {
        return CollisionJNI.btConvexPointCloudShape_getScaledPoint(this.swigCPtr, this, i);
    }
}
