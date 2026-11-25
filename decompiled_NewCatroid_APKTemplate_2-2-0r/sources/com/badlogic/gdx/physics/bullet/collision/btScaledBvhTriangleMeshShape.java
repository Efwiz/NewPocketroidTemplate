package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btScaledBvhTriangleMeshShape extends btConcaveShape {
    private long swigCPtr;

    protected btScaledBvhTriangleMeshShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btScaledBvhTriangleMeshShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btScaledBvhTriangleMeshShape(long j, boolean z) {
        this("btScaledBvhTriangleMeshShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btScaledBvhTriangleMeshShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btScaledBvhTriangleMeshShape btscaledbvhtrianglemeshshape) {
        if (btscaledbvhtrianglemeshshape == null) {
            return 0L;
        }
        return btscaledbvhtrianglemeshshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btScaledBvhTriangleMeshShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btScaledBvhTriangleMeshShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btScaledBvhTriangleMeshShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btScaledBvhTriangleMeshShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btScaledBvhTriangleMeshShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btScaledBvhTriangleMeshShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btScaledBvhTriangleMeshShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btScaledBvhTriangleMeshShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btScaledBvhTriangleMeshShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btScaledBvhTriangleMeshShape(btBvhTriangleMeshShape btbvhtrianglemeshshape, Vector3 vector3) {
        this(CollisionJNI.new_btScaledBvhTriangleMeshShape(btBvhTriangleMeshShape.getCPtr(btbvhtrianglemeshshape), btbvhtrianglemeshshape, vector3), true);
    }

    public btBvhTriangleMeshShape getChildShape() {
        long jBtScaledBvhTriangleMeshShape_getChildShape = CollisionJNI.btScaledBvhTriangleMeshShape_getChildShape(this.swigCPtr, this);
        if (jBtScaledBvhTriangleMeshShape_getChildShape == 0) {
            return null;
        }
        return new btBvhTriangleMeshShape(jBtScaledBvhTriangleMeshShape_getChildShape, false);
    }

    public btBvhTriangleMeshShape getChildShapeConst() {
        long jBtScaledBvhTriangleMeshShape_getChildShapeConst = CollisionJNI.btScaledBvhTriangleMeshShape_getChildShapeConst(this.swigCPtr, this);
        if (jBtScaledBvhTriangleMeshShape_getChildShapeConst == 0) {
            return null;
        }
        return new btBvhTriangleMeshShape(jBtScaledBvhTriangleMeshShape_getChildShapeConst, false);
    }
}
