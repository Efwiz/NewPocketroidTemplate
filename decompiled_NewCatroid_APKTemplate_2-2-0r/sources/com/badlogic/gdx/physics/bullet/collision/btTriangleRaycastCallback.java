package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleRaycastCallback extends btTriangleCallback {
    private long swigCPtr;

    public static final class EFlags {
        public static final int kF_FilterBackfaces = 1;
        public static final int kF_KeepUnflippedNormal = 2;
        public static final int kF_None = 0;
        public static final int kF_Terminator = -1;
        public static final int kF_UseGjkConvexCastRaytest = 8;
        public static final int kF_UseSubSimplexConvexCastRaytest = 4;
    }

    protected btTriangleRaycastCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.btTriangleRaycastCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTriangleRaycastCallback(long j, boolean z) {
        this("btTriangleRaycastCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTriangleRaycastCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTriangleRaycastCallback bttriangleraycastcallback) {
        if (bttriangleraycastcallback == null) {
            return 0L;
        }
        return bttriangleraycastcallback.swigCPtr;
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
                CollisionJNI.delete_btTriangleRaycastCallback(this.swigCPtr);
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
        CollisionJNI.btTriangleRaycastCallback_change_ownership(this, this.swigCPtr, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btTriangleRaycastCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void setFrom(btVector3 btvector3) {
        CollisionJNI.btTriangleRaycastCallback_from_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getFrom() {
        long jBtTriangleRaycastCallback_from_get = CollisionJNI.btTriangleRaycastCallback_from_get(this.swigCPtr, this);
        if (jBtTriangleRaycastCallback_from_get == 0) {
            return null;
        }
        return new btVector3(jBtTriangleRaycastCallback_from_get, false);
    }

    public void setTo(btVector3 btvector3) {
        CollisionJNI.btTriangleRaycastCallback_to_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getTo() {
        long jBtTriangleRaycastCallback_to_get = CollisionJNI.btTriangleRaycastCallback_to_get(this.swigCPtr, this);
        if (jBtTriangleRaycastCallback_to_get == 0) {
            return null;
        }
        return new btVector3(jBtTriangleRaycastCallback_to_get, false);
    }

    public void setFlags(long j) {
        CollisionJNI.btTriangleRaycastCallback_flags_set(this.swigCPtr, this, j);
    }

    public long getFlags() {
        return CollisionJNI.btTriangleRaycastCallback_flags_get(this.swigCPtr, this);
    }

    public void setHitFraction(float f) {
        CollisionJNI.btTriangleRaycastCallback_hitFraction_set(this.swigCPtr, this, f);
    }

    public float getHitFraction() {
        return CollisionJNI.btTriangleRaycastCallback_hitFraction_get(this.swigCPtr, this);
    }

    public btTriangleRaycastCallback(Vector3 vector3, Vector3 vector4, long j) {
        this(CollisionJNI.new_btTriangleRaycastCallback__SWIG_0(vector3, vector4, j), true);
        CollisionJNI.btTriangleRaycastCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public btTriangleRaycastCallback(Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_btTriangleRaycastCallback__SWIG_1(vector3, vector4), true);
        CollisionJNI.btTriangleRaycastCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback
    public void processTriangle(btVector3 btvector3, int i, int i2) {
        if (getClass() == btTriangleRaycastCallback.class) {
            CollisionJNI.btTriangleRaycastCallback_processTriangle(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, i2);
        } else {
            CollisionJNI.btTriangleRaycastCallback_processTriangleSwigExplicitbtTriangleRaycastCallback(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, i2);
        }
    }

    public float reportHit(Vector3 vector3, float f, int i, int i2) {
        return CollisionJNI.btTriangleRaycastCallback_reportHit(this.swigCPtr, this, vector3, f, i, i2);
    }
}
