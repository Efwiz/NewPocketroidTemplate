package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleConvexcastCallback extends btTriangleCallback {
    private long swigCPtr;

    protected btTriangleConvexcastCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.btTriangleConvexcastCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTriangleConvexcastCallback(long j, boolean z) {
        this("btTriangleConvexcastCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTriangleConvexcastCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTriangleConvexcastCallback bttriangleconvexcastcallback) {
        if (bttriangleconvexcastcallback == null) {
            return 0L;
        }
        return bttriangleconvexcastcallback.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btTriangleConvexcastCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        CollisionJNI.btTriangleConvexcastCallback_change_ownership(this, this.swigCPtr, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btTriangleConvexcastCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void setConvexShape(btConvexShape btconvexshape) {
        CollisionJNI.btTriangleConvexcastCallback_convexShape_set(this.swigCPtr, this, btConvexShape.getCPtr(btconvexshape), btconvexshape);
    }

    public btConvexShape getConvexShape() {
        long jBtTriangleConvexcastCallback_convexShape_get = CollisionJNI.btTriangleConvexcastCallback_convexShape_get(this.swigCPtr, this);
        if (jBtTriangleConvexcastCallback_convexShape_get == 0) {
            return null;
        }
        return new btConvexShape(jBtTriangleConvexcastCallback_convexShape_get, false);
    }

    public void setConvexShapeFrom(btTransform bttransform) {
        CollisionJNI.btTriangleConvexcastCallback_convexShapeFrom_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getConvexShapeFrom() {
        long jBtTriangleConvexcastCallback_convexShapeFrom_get = CollisionJNI.btTriangleConvexcastCallback_convexShapeFrom_get(this.swigCPtr, this);
        if (jBtTriangleConvexcastCallback_convexShapeFrom_get == 0) {
            return null;
        }
        return new btTransform(jBtTriangleConvexcastCallback_convexShapeFrom_get, false);
    }

    public void setConvexShapeTo(btTransform bttransform) {
        CollisionJNI.btTriangleConvexcastCallback_convexShapeTo_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getConvexShapeTo() {
        long jBtTriangleConvexcastCallback_convexShapeTo_get = CollisionJNI.btTriangleConvexcastCallback_convexShapeTo_get(this.swigCPtr, this);
        if (jBtTriangleConvexcastCallback_convexShapeTo_get == 0) {
            return null;
        }
        return new btTransform(jBtTriangleConvexcastCallback_convexShapeTo_get, false);
    }

    public void setTriangleToWorld(btTransform bttransform) {
        CollisionJNI.btTriangleConvexcastCallback_triangleToWorld_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getTriangleToWorld() {
        long jBtTriangleConvexcastCallback_triangleToWorld_get = CollisionJNI.btTriangleConvexcastCallback_triangleToWorld_get(this.swigCPtr, this);
        if (jBtTriangleConvexcastCallback_triangleToWorld_get == 0) {
            return null;
        }
        return new btTransform(jBtTriangleConvexcastCallback_triangleToWorld_get, false);
    }

    public void setHitFraction(float f) {
        CollisionJNI.btTriangleConvexcastCallback_hitFraction_set(this.swigCPtr, this, f);
    }

    public float getHitFraction() {
        return CollisionJNI.btTriangleConvexcastCallback_hitFraction_get(this.swigCPtr, this);
    }

    public void setTriangleCollisionMargin(float f) {
        CollisionJNI.btTriangleConvexcastCallback_triangleCollisionMargin_set(this.swigCPtr, this, f);
    }

    public float getTriangleCollisionMargin() {
        return CollisionJNI.btTriangleConvexcastCallback_triangleCollisionMargin_get(this.swigCPtr, this);
    }

    public void setAllowedPenetration(float f) {
        CollisionJNI.btTriangleConvexcastCallback_allowedPenetration_set(this.swigCPtr, this, f);
    }

    public float getAllowedPenetration() {
        return CollisionJNI.btTriangleConvexcastCallback_allowedPenetration_get(this.swigCPtr, this);
    }

    public btTriangleConvexcastCallback(btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, Matrix4 matrix6, float f) {
        this(CollisionJNI.new_btTriangleConvexcastCallback(btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, matrix5, matrix6, f), true);
        CollisionJNI.btTriangleConvexcastCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    public void processTriangle(btVector3 btvector3, int i, int i2) {
        if (getClass() == btTriangleConvexcastCallback.class) {
            CollisionJNI.btTriangleConvexcastCallback_processTriangle(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, i2);
        } else {
            CollisionJNI.btTriangleConvexcastCallback_processTriangleSwigExplicitbtTriangleConvexcastCallback(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, i2);
        }
    }

    public float reportHit(Vector3 vector3, Vector3 vector4, float f, int i, int i2) {
        return CollisionJNI.btTriangleConvexcastCallback_reportHit(this.swigCPtr, this, vector3, vector4, f, i, i2);
    }
}
