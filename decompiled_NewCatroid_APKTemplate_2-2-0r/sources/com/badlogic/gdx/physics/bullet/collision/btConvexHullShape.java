package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexHullShape extends btPolyhedralConvexAabbCachingShape {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btConvexHullShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexHullShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexHullShape(long j, boolean z) {
        this("btConvexHullShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexHullShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexHullShape btconvexhullshape) {
        if (btconvexhullshape == null) {
            return 0L;
        }
        return btconvexhullshape.swigCPtr;
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
                CollisionJNI.delete_btConvexHullShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btConvexHullShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btConvexHullShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btConvexHullShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btConvexHullShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btConvexHullShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btConvexHullShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btConvexHullShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btConvexHullShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    private static long SwigConstructbtConvexHullShape(FloatBuffer floatBuffer, int i, int i2) {
        return CollisionJNI.new_btConvexHullShape__SWIG_0(floatBuffer, i, i2);
    }

    public btConvexHullShape(FloatBuffer floatBuffer, int i, int i2) {
        this(SwigConstructbtConvexHullShape(floatBuffer, i, i2), true);
    }

    private static long SwigConstructbtConvexHullShape(FloatBuffer floatBuffer, int i) {
        return CollisionJNI.new_btConvexHullShape__SWIG_1(floatBuffer, i);
    }

    public btConvexHullShape(FloatBuffer floatBuffer, int i) {
        this(SwigConstructbtConvexHullShape(floatBuffer, i), true);
    }

    private static long SwigConstructbtConvexHullShape(FloatBuffer floatBuffer) {
        return CollisionJNI.new_btConvexHullShape__SWIG_2(floatBuffer);
    }

    public btConvexHullShape(FloatBuffer floatBuffer) {
        this(SwigConstructbtConvexHullShape(floatBuffer), true);
    }

    public btConvexHullShape() {
        this(CollisionJNI.new_btConvexHullShape__SWIG_3(), true);
    }

    public void addPoint(Vector3 vector3, boolean z) {
        CollisionJNI.btConvexHullShape_addPoint__SWIG_0(this.swigCPtr, this, vector3, z);
    }

    public void addPoint(Vector3 vector3) {
        CollisionJNI.btConvexHullShape_addPoint__SWIG_1(this.swigCPtr, this, vector3);
    }

    public btVector3 getUnscaledPoints() {
        long jBtConvexHullShape_getUnscaledPoints = CollisionJNI.btConvexHullShape_getUnscaledPoints(this.swigCPtr, this);
        if (jBtConvexHullShape_getUnscaledPoints == 0) {
            return null;
        }
        return new btVector3(jBtConvexHullShape_getUnscaledPoints, false);
    }

    public btVector3 getUnscaledPointsConst() {
        long jBtConvexHullShape_getUnscaledPointsConst = CollisionJNI.btConvexHullShape_getUnscaledPointsConst(this.swigCPtr, this);
        if (jBtConvexHullShape_getUnscaledPointsConst == 0) {
            return null;
        }
        return new btVector3(jBtConvexHullShape_getUnscaledPointsConst, false);
    }

    public btVector3 getPoints() {
        long jBtConvexHullShape_getPoints = CollisionJNI.btConvexHullShape_getPoints(this.swigCPtr, this);
        if (jBtConvexHullShape_getPoints == 0) {
            return null;
        }
        return new btVector3(jBtConvexHullShape_getPoints, false);
    }

    public void optimizeConvexHull() {
        CollisionJNI.btConvexHullShape_optimizeConvexHull(this.swigCPtr, this);
    }

    public Vector3 getScaledPoint(int i) {
        return CollisionJNI.btConvexHullShape_getScaledPoint(this.swigCPtr, this, i);
    }

    public int getNumPoints() {
        return CollisionJNI.btConvexHullShape_getNumPoints(this.swigCPtr, this);
    }

    public btConvexHullShape(btShapeHull btshapehull) {
        this(CollisionJNI.new_btConvexHullShape__SWIG_4(btShapeHull.getCPtr(btshapehull), btshapehull), true);
    }
}
