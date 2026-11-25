package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btUniformScalingShape extends btConvexShape {
    private long swigCPtr;

    protected btUniformScalingShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btUniformScalingShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btUniformScalingShape(long j, boolean z) {
        this("btUniformScalingShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btUniformScalingShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btUniformScalingShape btuniformscalingshape) {
        if (btuniformscalingshape == null) {
            return 0L;
        }
        return btuniformscalingshape.swigCPtr;
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
                CollisionJNI.delete_btUniformScalingShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btUniformScalingShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btUniformScalingShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btUniformScalingShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btUniformScalingShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btUniformScalingShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btUniformScalingShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btUniformScalingShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btUniformScalingShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btUniformScalingShape(btConvexShape btconvexshape, float f) {
        this(CollisionJNI.new_btUniformScalingShape(btConvexShape.getCPtr(btconvexshape), btconvexshape, f), true);
    }

    public float getUniformScalingFactor() {
        return CollisionJNI.btUniformScalingShape_getUniformScalingFactor(this.swigCPtr, this);
    }

    public btConvexShape getChildShape() {
        long jBtUniformScalingShape_getChildShape = CollisionJNI.btUniformScalingShape_getChildShape(this.swigCPtr, this);
        if (jBtUniformScalingShape_getChildShape == 0) {
            return null;
        }
        return new btConvexShape(jBtUniformScalingShape_getChildShape, false);
    }

    public btConvexShape getChildShapeConst() {
        long jBtUniformScalingShape_getChildShapeConst = CollisionJNI.btUniformScalingShape_getChildShapeConst(this.swigCPtr, this);
        if (jBtUniformScalingShape_getChildShapeConst == 0) {
            return null;
        }
        return new btConvexShape(jBtUniformScalingShape_getChildShapeConst, false);
    }
}
