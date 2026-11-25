package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btHashedOverlappingPairCache extends btOverlappingPairCache {
    private long swigCPtr;

    protected btHashedOverlappingPairCache(String str, long j, boolean z) {
        super(str, CollisionJNI.btHashedOverlappingPairCache_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btHashedOverlappingPairCache(long j, boolean z) {
        this("btHashedOverlappingPairCache", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCache, com.badlogic.gdx.physics.bullet.collision.btOverlappingPairCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btHashedOverlappingPairCache_SWIGUpcast(j), z);
    }

    public static long getCPtr(btHashedOverlappingPairCache bthashedoverlappingpaircache) {
        if (bthashedoverlappingpaircache == null) {
            return 0L;
        }
        return bthashedoverlappingpaircache.swigCPtr;
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
                CollisionJNI.delete_btHashedOverlappingPairCache(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btHashedOverlappingPairCache_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btHashedOverlappingPairCache_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btHashedOverlappingPairCache_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btHashedOverlappingPairCache_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btHashedOverlappingPairCache_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btHashedOverlappingPairCache_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btHashedOverlappingPairCache_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btHashedOverlappingPairCache_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btHashedOverlappingPairCache() {
        this(CollisionJNI.new_btHashedOverlappingPairCache(), true);
    }

    public boolean needsBroadphaseCollision(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        return CollisionJNI.btHashedOverlappingPairCache_needsBroadphaseCollision(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }

    public btBroadphasePairArray getOverlappingPairArrayConst() {
        return new btBroadphasePairArray(CollisionJNI.btHashedOverlappingPairCache_getOverlappingPairArrayConst(this.swigCPtr, this), false);
    }

    public int GetCount() {
        return CollisionJNI.btHashedOverlappingPairCache_GetCount(this.swigCPtr, this);
    }

    public btOverlapFilterCallback getOverlapFilterCallback() {
        long jBtHashedOverlappingPairCache_getOverlapFilterCallback = CollisionJNI.btHashedOverlappingPairCache_getOverlapFilterCallback(this.swigCPtr, this);
        if (jBtHashedOverlappingPairCache_getOverlapFilterCallback == 0) {
            return null;
        }
        return new btOverlapFilterCallback(jBtHashedOverlappingPairCache_getOverlapFilterCallback, false);
    }
}
