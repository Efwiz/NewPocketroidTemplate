package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionDispatcherMt extends btCollisionDispatcher {
    private long swigCPtr;

    protected btCollisionDispatcherMt(String str, long j, boolean z) {
        super(str, CollisionJNI.btCollisionDispatcherMt_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btCollisionDispatcherMt(long j, boolean z) {
        this("btCollisionDispatcherMt", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher, com.badlogic.gdx.physics.bullet.collision.btDispatcher, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btCollisionDispatcherMt_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCollisionDispatcherMt btcollisiondispatchermt) {
        if (btcollisiondispatchermt == null) {
            return 0L;
        }
        return btcollisiondispatchermt.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher, com.badlogic.gdx.physics.bullet.collision.btDispatcher, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher, com.badlogic.gdx.physics.bullet.collision.btDispatcher, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btCollisionDispatcherMt(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCollisionDispatcherMt(btCollisionConfiguration btcollisionconfiguration, int i) {
        this(CollisionJNI.new_btCollisionDispatcherMt__SWIG_0(btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration, i), true);
    }

    public btCollisionDispatcherMt(btCollisionConfiguration btcollisionconfiguration) {
        this(CollisionJNI.new_btCollisionDispatcherMt__SWIG_1(btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
    }
}
