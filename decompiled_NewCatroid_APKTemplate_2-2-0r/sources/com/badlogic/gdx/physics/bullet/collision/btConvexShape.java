package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexShape extends btCollisionShape {
    private long swigCPtr;

    protected btConvexShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexShape(long j, boolean z) {
        this("btConvexShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexShape btconvexshape) {
        if (btconvexshape == null) {
            return 0L;
        }
        return btconvexshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btConvexShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btConvexShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btConvexShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btConvexShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btConvexShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btConvexShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btConvexShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btConvexShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btConvexShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public Vector3 localGetSupportingVertex(Vector3 vector3) {
        return CollisionJNI.btConvexShape_localGetSupportingVertex(this.swigCPtr, this, vector3);
    }

    public Vector3 localGetSupportingVertexWithoutMargin(Vector3 vector3) {
        return CollisionJNI.btConvexShape_localGetSupportingVertexWithoutMargin(this.swigCPtr, this, vector3);
    }

    public Vector3 localGetSupportVertexWithoutMarginNonVirtual(Vector3 vector3) {
        return CollisionJNI.btConvexShape_localGetSupportVertexWithoutMarginNonVirtual(this.swigCPtr, this, vector3);
    }

    public Vector3 localGetSupportVertexNonVirtual(Vector3 vector3) {
        return CollisionJNI.btConvexShape_localGetSupportVertexNonVirtual(this.swigCPtr, this, vector3);
    }

    public float getMarginNonVirtual() {
        return CollisionJNI.btConvexShape_getMarginNonVirtual(this.swigCPtr, this);
    }

    public void getAabbNonVirtual(Matrix4 matrix4, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btConvexShape_getAabbNonVirtual(this.swigCPtr, this, matrix4, vector3, vector4);
    }

    public void project(Matrix4 matrix4, Vector3 vector3, SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2, Vector3 vector4, Vector3 vector5) {
        CollisionJNI.btConvexShape_project(this.swigCPtr, this, matrix4, vector3, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2), vector4, vector5);
    }

    public void batchedUnitVectorGetSupportingVertexWithoutMargin(btVector3 btvector3, btVector3 btvector4, int i) {
        CollisionJNI.btConvexShape_batchedUnitVectorGetSupportingVertexWithoutMargin(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4, i);
    }

    public void getAabbSlow(Matrix4 matrix4, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btConvexShape_getAabbSlow(this.swigCPtr, this, matrix4, vector3, vector4);
    }

    public int getNumPreferredPenetrationDirections() {
        return CollisionJNI.btConvexShape_getNumPreferredPenetrationDirections(this.swigCPtr, this);
    }

    public void getPreferredPenetrationDirection(int i, Vector3 vector3) {
        CollisionJNI.btConvexShape_getPreferredPenetrationDirection(this.swigCPtr, this, i, vector3);
    }
}
