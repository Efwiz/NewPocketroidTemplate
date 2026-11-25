package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;

/* JADX INFO: loaded from: classes8.dex */
public class btBox2dShape extends btPolyhedralConvexShape {
    private long swigCPtr;

    protected btBox2dShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btBox2dShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btBox2dShape(long j, boolean z) {
        this("btBox2dShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btBox2dShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btBox2dShape btbox2dshape) {
        if (btbox2dshape == null) {
            return 0L;
        }
        return btbox2dshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btBox2dShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btBox2dShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btBox2dShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btBox2dShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btBox2dShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btBox2dShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btBox2dShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btBox2dShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btBox2dShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public Vector3 getHalfExtentsWithMargin() {
        return CollisionJNI.btBox2dShape_getHalfExtentsWithMargin(this.swigCPtr, this);
    }

    public Vector3 getHalfExtentsWithoutMargin() {
        return CollisionJNI.btBox2dShape_getHalfExtentsWithoutMargin(this.swigCPtr, this);
    }

    public btBox2dShape(Vector3 vector3) {
        this(CollisionJNI.new_btBox2dShape(vector3), true);
    }

    public int getVertexCount() {
        return CollisionJNI.btBox2dShape_getVertexCount(this.swigCPtr, this);
    }

    public btVector3 getVertices() {
        long jBtBox2dShape_getVertices = CollisionJNI.btBox2dShape_getVertices(this.swigCPtr, this);
        if (jBtBox2dShape_getVertices == 0) {
            return null;
        }
        return new btVector3(jBtBox2dShape_getVertices, false);
    }

    public btVector3 getNormals() {
        long jBtBox2dShape_getNormals = CollisionJNI.btBox2dShape_getNormals(this.swigCPtr, this);
        if (jBtBox2dShape_getNormals == 0) {
            return null;
        }
        return new btVector3(jBtBox2dShape_getNormals, false);
    }

    public Vector3 getCentroid() {
        return CollisionJNI.btBox2dShape_getCentroid(this.swigCPtr, this);
    }

    public void getPlaneEquation(btVector4 btvector4, int i) {
        CollisionJNI.btBox2dShape_getPlaneEquation(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4, i);
    }
}
