package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class ClosestNotMeRayResultCallback extends ClosestRayResultCallback {
    private long swigCPtr;

    protected ClosestNotMeRayResultCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.ClosestNotMeRayResultCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public ClosestNotMeRayResultCallback(long j, boolean z) {
        this("ClosestNotMeRayResultCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback, com.badlogic.gdx.physics.bullet.collision.RayResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.ClosestNotMeRayResultCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(ClosestNotMeRayResultCallback closestNotMeRayResultCallback) {
        if (closestNotMeRayResultCallback == null) {
            return 0L;
        }
        return closestNotMeRayResultCallback.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback, com.badlogic.gdx.physics.bullet.collision.RayResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback, com.badlogic.gdx.physics.bullet.collision.RayResultCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_ClosestNotMeRayResultCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public ClosestNotMeRayResultCallback(btCollisionObject btcollisionobject) {
        this(CollisionJNI.new_ClosestNotMeRayResultCallback(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject), true);
    }
}
