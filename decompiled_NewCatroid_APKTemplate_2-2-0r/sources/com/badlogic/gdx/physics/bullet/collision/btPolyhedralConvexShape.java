package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btPolyhedralConvexShape extends btConvexInternalShape {
    private long swigCPtr;

    protected btPolyhedralConvexShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btPolyhedralConvexShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btPolyhedralConvexShape(long j, boolean z) {
        this("btPolyhedralConvexShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btPolyhedralConvexShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btPolyhedralConvexShape btpolyhedralconvexshape) {
        if (btpolyhedralconvexshape == null) {
            return 0L;
        }
        return btpolyhedralconvexshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btPolyhedralConvexShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btPolyhedralConvexShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btPolyhedralConvexShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btPolyhedralConvexShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btPolyhedralConvexShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btPolyhedralConvexShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btPolyhedralConvexShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btPolyhedralConvexShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btPolyhedralConvexShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public boolean initializePolyhedralFeatures(int i) {
        return CollisionJNI.btPolyhedralConvexShape_initializePolyhedralFeatures__SWIG_0(this.swigCPtr, this, i);
    }

    public boolean initializePolyhedralFeatures() {
        return CollisionJNI.btPolyhedralConvexShape_initializePolyhedralFeatures__SWIG_1(this.swigCPtr, this);
    }

    public btConvexPolyhedron getConvexPolyhedron() {
        long jBtPolyhedralConvexShape_getConvexPolyhedron = CollisionJNI.btPolyhedralConvexShape_getConvexPolyhedron(this.swigCPtr, this);
        if (jBtPolyhedralConvexShape_getConvexPolyhedron == 0) {
            return null;
        }
        return new btConvexPolyhedron(jBtPolyhedralConvexShape_getConvexPolyhedron, false);
    }

    public int getNumVertices() {
        return CollisionJNI.btPolyhedralConvexShape_getNumVertices(this.swigCPtr, this);
    }

    public int getNumEdges() {
        return CollisionJNI.btPolyhedralConvexShape_getNumEdges(this.swigCPtr, this);
    }

    public void getEdge(int i, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btPolyhedralConvexShape_getEdge(this.swigCPtr, this, i, vector3, vector4);
    }

    public void getVertex(int i, Vector3 vector3) {
        CollisionJNI.btPolyhedralConvexShape_getVertex(this.swigCPtr, this, i, vector3);
    }

    public int getNumPlanes() {
        return CollisionJNI.btPolyhedralConvexShape_getNumPlanes(this.swigCPtr, this);
    }

    public void getPlane(Vector3 vector3, Vector3 vector4, int i) {
        CollisionJNI.btPolyhedralConvexShape_getPlane(this.swigCPtr, this, vector3, vector4, i);
    }

    public boolean isInside(Vector3 vector3, float f) {
        return CollisionJNI.btPolyhedralConvexShape_isInside(this.swigCPtr, this, vector3, f);
    }
}
