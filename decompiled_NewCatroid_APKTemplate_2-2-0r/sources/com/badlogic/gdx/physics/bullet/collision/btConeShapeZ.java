package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btConeShapeZ extends btConeShape {
    private long swigCPtr;

    protected btConeShapeZ(String str, long j, boolean z) {
        super(str, CollisionJNI.btConeShapeZ_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConeShapeZ(long j, boolean z) {
        this("btConeShapeZ", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConeShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConeShapeZ_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConeShapeZ btconeshapez) {
        if (btconeshapez == null) {
            return 0L;
        }
        return btconeshapez.swigCPtr;
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
                CollisionJNI.delete_btConeShapeZ(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btConeShapeZ(float f, float f2) {
        this(CollisionJNI.new_btConeShapeZ(f, f2), true);
    }
}
