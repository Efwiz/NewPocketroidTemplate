package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btCylinderShapeZ extends btCylinderShape {
    private long swigCPtr;

    protected btCylinderShapeZ(String str, long j, boolean z) {
        super(str, CollisionJNI.btCylinderShapeZ_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btCylinderShapeZ(long j, boolean z) {
        this("btCylinderShapeZ", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btCylinderShapeZ_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCylinderShapeZ btcylindershapez) {
        if (btcylindershapez == null) {
            return 0L;
        }
        return btcylindershapez.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btCylinderShapeZ(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btCylinderShapeZ_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btCylinderShapeZ_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btCylinderShapeZ_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btCylinderShapeZ_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btCylinderShapeZ_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btCylinderShapeZ_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btCylinderShapeZ_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCylinderShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btCylinderShapeZ_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btCylinderShapeZ(Vector3 vector3) {
        this(CollisionJNI.new_btCylinderShapeZ(vector3), true);
    }
}
