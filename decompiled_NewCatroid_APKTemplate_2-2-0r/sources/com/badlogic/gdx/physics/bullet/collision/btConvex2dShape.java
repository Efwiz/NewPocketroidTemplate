package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btConvex2dShape extends btConvexShape {
    private long swigCPtr;

    protected btConvex2dShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvex2dShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvex2dShape(long j, boolean z) {
        this("btConvex2dShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvex2dShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvex2dShape btconvex2dshape) {
        if (btconvex2dshape == null) {
            return 0L;
        }
        return btconvex2dshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btConvex2dShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btConvex2dShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btConvex2dShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btConvex2dShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btConvex2dShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btConvex2dShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btConvex2dShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btConvex2dShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btConvex2dShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btConvex2dShape(btConvexShape btconvexshape) {
        this(CollisionJNI.new_btConvex2dShape(btConvexShape.getCPtr(btconvexshape), btconvexshape), true);
    }

    public btConvexShape getChildShape() {
        long jBtConvex2dShape_getChildShape = CollisionJNI.btConvex2dShape_getChildShape(this.swigCPtr, this);
        if (jBtConvex2dShape_getChildShape == 0) {
            return null;
        }
        return new btConvexShape(jBtConvex2dShape_getChildShape, false);
    }

    public btConvexShape getChildShapeConst() {
        long jBtConvex2dShape_getChildShapeConst = CollisionJNI.btConvex2dShape_getChildShapeConst(this.swigCPtr, this);
        if (jBtConvex2dShape_getChildShapeConst == 0) {
            return null;
        }
        return new btConvexShape(jBtConvex2dShape_getChildShapeConst, false);
    }
}
