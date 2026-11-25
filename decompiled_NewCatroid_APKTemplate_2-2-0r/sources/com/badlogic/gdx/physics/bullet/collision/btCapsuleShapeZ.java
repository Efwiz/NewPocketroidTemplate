package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btCapsuleShapeZ extends btCapsuleShape {
    private long swigCPtr;

    protected btCapsuleShapeZ(String str, long j, boolean z) {
        super(str, CollisionJNI.btCapsuleShapeZ_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btCapsuleShapeZ(long j, boolean z) {
        this("btCapsuleShapeZ", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCapsuleShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btCapsuleShapeZ_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCapsuleShapeZ btcapsuleshapez) {
        if (btcapsuleshapez == null) {
            return 0L;
        }
        return btcapsuleshapez.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCapsuleShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCapsuleShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btCapsuleShapeZ(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCapsuleShapeZ(float f, float f2) {
        this(CollisionJNI.new_btCapsuleShapeZ(f, f2), true);
    }
}
