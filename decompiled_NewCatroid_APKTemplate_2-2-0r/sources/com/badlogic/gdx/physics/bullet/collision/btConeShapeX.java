package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btConeShapeX extends btConeShape {
    private long swigCPtr;

    protected btConeShapeX(String str, long j, boolean z) {
        super(str, CollisionJNI.btConeShapeX_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConeShapeX(long j, boolean z) {
        this("btConeShapeX", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConeShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConeShapeX_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConeShapeX btconeshapex) {
        if (btconeshapex == null) {
            return 0L;
        }
        return btconeshapex.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConeShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConeShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btConeShapeX(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btConeShapeX(float f, float f2) {
        this(CollisionJNI.new_btConeShapeX(f, f2), true);
    }
}
