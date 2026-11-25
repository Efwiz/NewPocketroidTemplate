package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btBroadphaseRayCallback extends btBroadphaseAabbCallback {
    private long swigCPtr;

    protected btBroadphaseRayCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.btBroadphaseRayCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btBroadphaseRayCallback(long j, boolean z) {
        this("btBroadphaseRayCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseAabbCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btBroadphaseRayCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(btBroadphaseRayCallback btbroadphaseraycallback) {
        if (btbroadphaseraycallback == null) {
            return 0L;
        }
        return btbroadphaseraycallback.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseAabbCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseAabbCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btBroadphaseRayCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseAabbCallback
    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseAabbCallback
    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        CollisionJNI.btBroadphaseRayCallback_change_ownership(this, this.swigCPtr, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseAabbCallback
    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btBroadphaseRayCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void setRayDirectionInverse(btVector3 btvector3) {
        CollisionJNI.btBroadphaseRayCallback_rayDirectionInverse_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getRayDirectionInverse() {
        long jBtBroadphaseRayCallback_rayDirectionInverse_get = CollisionJNI.btBroadphaseRayCallback_rayDirectionInverse_get(this.swigCPtr, this);
        if (jBtBroadphaseRayCallback_rayDirectionInverse_get == 0) {
            return null;
        }
        return new btVector3(jBtBroadphaseRayCallback_rayDirectionInverse_get, false);
    }

    public void setSigns(long[] jArr) {
        CollisionJNI.btBroadphaseRayCallback_signs_set(this.swigCPtr, this, jArr);
    }

    public long[] getSigns() {
        return CollisionJNI.btBroadphaseRayCallback_signs_get(this.swigCPtr, this);
    }

    public void setLambda_max(float f) {
        CollisionJNI.btBroadphaseRayCallback_lambda_max_set(this.swigCPtr, this, f);
    }

    public float getLambda_max() {
        return CollisionJNI.btBroadphaseRayCallback_lambda_max_get(this.swigCPtr, this);
    }

    protected btBroadphaseRayCallback() {
        this(CollisionJNI.new_btBroadphaseRayCallback(), true);
        CollisionJNI.btBroadphaseRayCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
