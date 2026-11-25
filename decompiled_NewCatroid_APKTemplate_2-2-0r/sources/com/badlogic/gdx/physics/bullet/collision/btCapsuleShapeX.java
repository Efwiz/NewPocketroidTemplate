package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btCapsuleShapeX extends btCapsuleShape {
    private long swigCPtr;

    protected btCapsuleShapeX(String str, long j, boolean z) {
        super(str, CollisionJNI.btCapsuleShapeX_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btCapsuleShapeX(long j, boolean z) {
        this("btCapsuleShapeX", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCapsuleShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btCapsuleShapeX_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCapsuleShapeX btcapsuleshapex) {
        if (btcapsuleshapex == null) {
            return 0L;
        }
        return btcapsuleshapex.swigCPtr;
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
                CollisionJNI.delete_btCapsuleShapeX(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCapsuleShapeX(float f, float f2) {
        this(CollisionJNI.new_btCapsuleShapeX(f, f2), true);
    }
}
