package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btNullPairCache extends btOverlappingPairCache {
    private long swigCPtr;

    protected btNullPairCache(String str, long j, boolean z) {
        super(str, CollisionJNI.btNullPairCache_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btNullPairCache(long j, boolean z) {
        this("btNullPairCache", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCache, com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btNullPairCache_SWIGUpcast(j), z);
    }

    public static long getCPtr(btNullPairCache btnullpaircache) {
        if (btnullpaircache == null) {
            return 0L;
        }
        return btnullpaircache.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCache, com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCache, com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btNullPairCache(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btNullPairCache() {
        this(CollisionJNI.new_btNullPairCache(), true);
    }
}
