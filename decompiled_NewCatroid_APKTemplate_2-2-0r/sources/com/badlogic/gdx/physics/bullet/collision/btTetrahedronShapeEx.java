package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btTetrahedronShapeEx extends btBU_Simplex1to4 {
    private long swigCPtr;

    protected btTetrahedronShapeEx(String str, long j, boolean z) {
        super(str, CollisionJNI.btTetrahedronShapeEx_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTetrahedronShapeEx(long j, boolean z) {
        this("btTetrahedronShapeEx", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBU_Simplex1to4, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTetrahedronShapeEx_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTetrahedronShapeEx bttetrahedronshapeex) {
        if (bttetrahedronshapeex == null) {
            return 0L;
        }
        return bttetrahedronshapeex.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBU_Simplex1to4, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBU_Simplex1to4, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btTetrahedronShapeEx(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btTetrahedronShapeEx() {
        this(CollisionJNI.new_btTetrahedronShapeEx(), true);
    }

    public void setVertices(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.btTetrahedronShapeEx_setVertices(this.swigCPtr, this, vector3, vector4, vector5, vector6);
    }
}
