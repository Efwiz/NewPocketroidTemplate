package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btPolyhedralConvexAabbCachingShape extends btPolyhedralConvexShape {
    private long swigCPtr;

    protected btPolyhedralConvexAabbCachingShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btPolyhedralConvexAabbCachingShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btPolyhedralConvexAabbCachingShape(long j, boolean z) {
        this("btPolyhedralConvexAabbCachingShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btPolyhedralConvexAabbCachingShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btPolyhedralConvexAabbCachingShape btpolyhedralconvexaabbcachingshape) {
        if (btpolyhedralconvexaabbcachingshape == null) {
            return 0L;
        }
        return btpolyhedralconvexaabbcachingshape.swigCPtr;
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
                CollisionJNI.delete_btPolyhedralConvexAabbCachingShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void getNonvirtualAabb(Matrix4 matrix4, Vector3 vector3, Vector3 vector4, float f) {
        CollisionJNI.btPolyhedralConvexAabbCachingShape_getNonvirtualAabb(this.swigCPtr, this, matrix4, vector3, vector4, f);
    }

    public void recalcLocalAabb() {
        CollisionJNI.btPolyhedralConvexAabbCachingShape_recalcLocalAabb(this.swigCPtr, this);
    }
}
