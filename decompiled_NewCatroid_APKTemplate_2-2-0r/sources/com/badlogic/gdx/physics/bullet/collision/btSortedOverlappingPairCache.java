package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btSortedOverlappingPairCache extends btOverlappingPairCache {
    private long swigCPtr;

    protected btSortedOverlappingPairCache(String str, long j, boolean z) {
        super(str, CollisionJNI.btSortedOverlappingPairCache_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSortedOverlappingPairCache(long j, boolean z) {
        this("btSortedOverlappingPairCache", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCache, com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btSortedOverlappingPairCache_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSortedOverlappingPairCache btsortedoverlappingpaircache) {
        if (btsortedoverlappingpaircache == null) {
            return 0L;
        }
        return btsortedoverlappingpaircache.swigCPtr;
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
                CollisionJNI.delete_btSortedOverlappingPairCache(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSortedOverlappingPairCache() {
        this(CollisionJNI.new_btSortedOverlappingPairCache(), true);
    }

    public boolean needsBroadphaseCollision(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        return CollisionJNI.btSortedOverlappingPairCache_needsBroadphaseCollision(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }

    public btBroadphasePairArray getOverlappingPairArrayConst() {
        return new btBroadphasePairArray(CollisionJNI.btSortedOverlappingPairCache_getOverlappingPairArrayConst(this.swigCPtr, this), false);
    }

    public btOverlapFilterCallback getOverlapFilterCallback() {
        long jBtSortedOverlappingPairCache_getOverlapFilterCallback = CollisionJNI.btSortedOverlappingPairCache_getOverlapFilterCallback(this.swigCPtr, this);
        if (jBtSortedOverlappingPairCache_getOverlapFilterCallback == 0) {
            return null;
        }
        return new btOverlapFilterCallback(jBtSortedOverlappingPairCache_getOverlapFilterCallback, false);
    }
}
