package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexInternalShape extends btConvexShape {
    private long swigCPtr;

    protected btConvexInternalShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexInternalShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexInternalShape(long j, boolean z) {
        this("btConvexInternalShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexInternalShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexInternalShape btconvexinternalshape) {
        if (btconvexinternalshape == null) {
            return 0L;
        }
        return btconvexinternalshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btConvexInternalShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btConvexInternalShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btConvexInternalShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btConvexInternalShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btConvexInternalShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btConvexInternalShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btConvexInternalShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btConvexInternalShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btConvexInternalShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public Vector3 getImplicitShapeDimensions() {
        return CollisionJNI.btConvexInternalShape_getImplicitShapeDimensions(this.swigCPtr, this);
    }

    public void setImplicitShapeDimensions(Vector3 vector3) {
        CollisionJNI.btConvexInternalShape_setImplicitShapeDimensions(this.swigCPtr, this, vector3);
    }

    public void setSafeMargin(float f, float f2) {
        CollisionJNI.btConvexInternalShape_setSafeMargin__SWIG_0(this.swigCPtr, this, f, f2);
    }

    public void setSafeMargin(float f) {
        CollisionJNI.btConvexInternalShape_setSafeMargin__SWIG_1(this.swigCPtr, this, f);
    }

    public void setSafeMargin(Vector3 vector3, float f) {
        CollisionJNI.btConvexInternalShape_setSafeMargin__SWIG_2(this.swigCPtr, this, vector3, f);
    }

    public void setSafeMargin(Vector3 vector3) {
        CollisionJNI.btConvexInternalShape_setSafeMargin__SWIG_3(this.swigCPtr, this, vector3);
    }

    public Vector3 getLocalScalingNV() {
        return CollisionJNI.btConvexInternalShape_getLocalScalingNV(this.swigCPtr, this);
    }

    public float getMarginNV() {
        return CollisionJNI.btConvexInternalShape_getMarginNV(this.swigCPtr, this);
    }
}
