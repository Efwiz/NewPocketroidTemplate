package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexInternalAabbCachingShape extends btConvexInternalShape {
    private long swigCPtr;

    protected btConvexInternalAabbCachingShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexInternalAabbCachingShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexInternalAabbCachingShape(long j, boolean z) {
        this("btConvexInternalAabbCachingShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexInternalAabbCachingShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexInternalAabbCachingShape btconvexinternalaabbcachingshape) {
        if (btconvexinternalaabbcachingshape == null) {
            return 0L;
        }
        return btconvexinternalaabbcachingshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btConvexInternalAabbCachingShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void recalcLocalAabb() {
        CollisionJNI.btConvexInternalAabbCachingShape_recalcLocalAabb(this.swigCPtr, this);
    }
}
