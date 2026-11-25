package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class ClosestNotMeConvexResultCallback extends ClosestConvexResultCallback {
    private long swigCPtr;

    protected ClosestNotMeConvexResultCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.ClosestNotMeConvexResultCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public ClosestNotMeConvexResultCallback(long j, boolean z) {
        this("ClosestNotMeConvexResultCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ClosestConvexResultCallback, com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.ClosestNotMeConvexResultCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(ClosestNotMeConvexResultCallback closestNotMeConvexResultCallback) {
        if (closestNotMeConvexResultCallback == null) {
            return 0L;
        }
        return closestNotMeConvexResultCallback.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ClosestConvexResultCallback, com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ClosestConvexResultCallback, com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_ClosestNotMeConvexResultCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setMe(btCollisionObject btcollisionobject) {
        CollisionJNI.ClosestNotMeConvexResultCallback_me_set(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public btCollisionObject getMe() {
        return btCollisionObject.getInstance(CollisionJNI.ClosestNotMeConvexResultCallback_me_get(this.swigCPtr, this), false);
    }

    public void setAllowedPenetration(float f) {
        CollisionJNI.ClosestNotMeConvexResultCallback_allowedPenetration_set(this.swigCPtr, this, f);
    }

    public float getAllowedPenetration() {
        return CollisionJNI.ClosestNotMeConvexResultCallback_allowedPenetration_get(this.swigCPtr, this);
    }

    public ClosestNotMeConvexResultCallback(btCollisionObject btcollisionobject, Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_ClosestNotMeConvexResultCallback(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, vector3, vector4), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ConvexResultCallback
    public boolean needsCollision(btBroadphaseProxy btbroadphaseproxy) {
        return CollisionJNI.ClosestNotMeConvexResultCallback_needsCollision(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }
}
