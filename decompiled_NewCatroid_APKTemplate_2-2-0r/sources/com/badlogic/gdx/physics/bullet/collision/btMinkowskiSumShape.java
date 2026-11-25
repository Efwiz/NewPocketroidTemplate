package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;

/* JADX INFO: loaded from: classes8.dex */
public class btMinkowskiSumShape extends btConvexInternalShape {
    private long swigCPtr;

    protected btMinkowskiSumShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btMinkowskiSumShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMinkowskiSumShape(long j, boolean z) {
        this("btMinkowskiSumShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btMinkowskiSumShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMinkowskiSumShape btminkowskisumshape) {
        if (btminkowskisumshape == null) {
            return 0L;
        }
        return btminkowskisumshape.swigCPtr;
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
                CollisionJNI.delete_btMinkowskiSumShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btMinkowskiSumShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btMinkowskiSumShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btMinkowskiSumShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btMinkowskiSumShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btMinkowskiSumShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btMinkowskiSumShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btMinkowskiSumShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btMinkowskiSumShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btMinkowskiSumShape(btConvexShape btconvexshape, btConvexShape btconvexshape2) {
        this(CollisionJNI.new_btMinkowskiSumShape(btConvexShape.getCPtr(btconvexshape), btconvexshape, btConvexShape.getCPtr(btconvexshape2), btconvexshape2), true);
    }

    public void setTransformA(Matrix4 matrix4) {
        CollisionJNI.btMinkowskiSumShape_setTransformA(this.swigCPtr, this, matrix4);
    }

    public void setTransformB(Matrix4 matrix4) {
        CollisionJNI.btMinkowskiSumShape_setTransformB(this.swigCPtr, this, matrix4);
    }

    public Matrix4 getTransformA() {
        return CollisionJNI.btMinkowskiSumShape_getTransformA(this.swigCPtr, this);
    }

    public Matrix4 GetTransformB() {
        return CollisionJNI.btMinkowskiSumShape_GetTransformB(this.swigCPtr, this);
    }

    public btConvexShape getShapeA() {
        long jBtMinkowskiSumShape_getShapeA = CollisionJNI.btMinkowskiSumShape_getShapeA(this.swigCPtr, this);
        if (jBtMinkowskiSumShape_getShapeA == 0) {
            return null;
        }
        return new btConvexShape(jBtMinkowskiSumShape_getShapeA, false);
    }

    public btConvexShape getShapeB() {
        long jBtMinkowskiSumShape_getShapeB = CollisionJNI.btMinkowskiSumShape_getShapeB(this.swigCPtr, this);
        if (jBtMinkowskiSumShape_getShapeB == 0) {
            return null;
        }
        return new btConvexShape(jBtMinkowskiSumShape_getShapeB, false);
    }
}
