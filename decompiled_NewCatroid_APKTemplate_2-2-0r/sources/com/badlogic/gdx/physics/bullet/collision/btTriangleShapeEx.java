package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleShapeEx extends btTriangleShape {
    private long swigCPtr;

    protected btTriangleShapeEx(String str, long j, boolean z) {
        super(str, CollisionJNI.btTriangleShapeEx_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTriangleShapeEx(long j, boolean z) {
        this("btTriangleShapeEx", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTriangleShapeEx_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTriangleShapeEx bttriangleshapeex) {
        if (bttriangleshapeex == null) {
            return 0L;
        }
        return bttriangleshapeex.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btTriangleShapeEx(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btTriangleShapeEx() {
        this(CollisionJNI.new_btTriangleShapeEx__SWIG_0(), true);
    }

    public btTriangleShapeEx(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        this(CollisionJNI.new_btTriangleShapeEx__SWIG_1(vector3, vector4, vector5), true);
    }

    public btTriangleShapeEx(btTriangleShapeEx bttriangleshapeex) {
        this(CollisionJNI.new_btTriangleShapeEx__SWIG_2(getCPtr(bttriangleshapeex), bttriangleshapeex), true);
    }

    public void applyTransform(Matrix4 matrix4) {
        CollisionJNI.btTriangleShapeEx_applyTransform(this.swigCPtr, this, matrix4);
    }

    public void buildTriPlane(btVector4 btvector4) {
        CollisionJNI.btTriangleShapeEx_buildTriPlane(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4);
    }

    public boolean overlap_test_conservative(btTriangleShapeEx bttriangleshapeex) {
        return CollisionJNI.btTriangleShapeEx_overlap_test_conservative(this.swigCPtr, this, getCPtr(bttriangleshapeex), bttriangleshapeex);
    }
}
