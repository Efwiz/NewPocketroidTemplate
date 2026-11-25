package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btStaticPlaneShape extends btConcaveShape {
    private long swigCPtr;

    protected btStaticPlaneShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btStaticPlaneShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btStaticPlaneShape(long j, boolean z) {
        this("btStaticPlaneShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btStaticPlaneShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btStaticPlaneShape btstaticplaneshape) {
        if (btstaticplaneshape == null) {
            return 0L;
        }
        return btstaticplaneshape.swigCPtr;
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
                CollisionJNI.delete_btStaticPlaneShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btStaticPlaneShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btStaticPlaneShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btStaticPlaneShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btStaticPlaneShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btStaticPlaneShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btStaticPlaneShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btStaticPlaneShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btStaticPlaneShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btStaticPlaneShape(Vector3 vector3, float f) {
        this(CollisionJNI.new_btStaticPlaneShape(vector3, f), true);
    }

    public Vector3 getPlaneNormal() {
        return CollisionJNI.btStaticPlaneShape_getPlaneNormal(this.swigCPtr, this);
    }

    public float getPlaneConstant() {
        return CollisionJNI.btStaticPlaneShape_getPlaneConstant(this.swigCPtr, this);
    }
}
