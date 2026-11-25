package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btActivatingCollisionAlgorithm extends btCollisionAlgorithm {
    private long swigCPtr;

    protected btActivatingCollisionAlgorithm(String str, long j, boolean z) {
        super(str, CollisionJNI.btActivatingCollisionAlgorithm_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btActivatingCollisionAlgorithm(long j, boolean z) {
        this("btActivatingCollisionAlgorithm", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btActivatingCollisionAlgorithm_SWIGUpcast(j), z);
    }

    public static long getCPtr(btActivatingCollisionAlgorithm btactivatingcollisionalgorithm) {
        if (btactivatingcollisionalgorithm == null) {
            return 0L;
        }
        return btactivatingcollisionalgorithm.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btActivatingCollisionAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }
}
