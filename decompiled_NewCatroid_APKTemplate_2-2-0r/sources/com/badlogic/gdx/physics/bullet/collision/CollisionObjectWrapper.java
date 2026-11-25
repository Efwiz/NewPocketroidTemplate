package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class CollisionObjectWrapper extends BulletBase {
    private long swigCPtr;
    public btCollisionObjectWrapper wrapper;

    protected CollisionObjectWrapper(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public CollisionObjectWrapper(long j, boolean z) {
        this("CollisionObjectWrapper", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(j, z);
    }

    public static long getCPtr(CollisionObjectWrapper collisionObjectWrapper) {
        if (collisionObjectWrapper == null) {
            return 0L;
        }
        return collisionObjectWrapper.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_CollisionObjectWrapper(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void construct() {
        super.construct();
        this.wrapper = new btCollisionObjectWrapper(getWrapper().getCPointer(), false);
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        btCollisionObjectWrapper btcollisionobjectwrapper = this.wrapper;
        if (btcollisionobjectwrapper != null) {
            btcollisionobjectwrapper.dispose();
            this.wrapper = null;
        }
        super.dispose();
    }

    public CollisionObjectWrapper(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionShape btcollisionshape, btCollisionObject btcollisionobject, Matrix4 matrix4, int i, int i2) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_0(btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, matrix4, i, i2), true);
    }

    public CollisionObjectWrapper(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionShape btcollisionshape, btCollisionObject btcollisionobject, Matrix4 matrix4, int i) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_1(btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, matrix4, i), true);
    }

    public CollisionObjectWrapper(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionShape btcollisionshape, btCollisionObject btcollisionobject, Matrix4 matrix4) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_2(btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, matrix4), true);
    }

    public CollisionObjectWrapper(btCollisionShape btcollisionshape, btCollisionObject btcollisionobject, Matrix4 matrix4, int i, int i2) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_3(btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, matrix4, i, i2), true);
    }

    public CollisionObjectWrapper(btCollisionShape btcollisionshape, btCollisionObject btcollisionobject, Matrix4 matrix4, int i) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_4(btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, matrix4, i), true);
    }

    public CollisionObjectWrapper(btCollisionShape btcollisionshape, btCollisionObject btcollisionobject, Matrix4 matrix4) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_5(btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, matrix4), true);
    }

    public CollisionObjectWrapper(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObject btcollisionobject, int i, int i2) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_6(btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i, i2), true);
    }

    public CollisionObjectWrapper(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObject btcollisionobject, int i) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_7(btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i), true);
    }

    public CollisionObjectWrapper(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObject btcollisionobject) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_8(btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject), true);
    }

    public CollisionObjectWrapper(btCollisionObject btcollisionobject, int i, int i2) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_9(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i, i2), true);
    }

    public CollisionObjectWrapper(btCollisionObject btcollisionobject, int i) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_10(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i), true);
    }

    public CollisionObjectWrapper(btCollisionObject btcollisionobject) {
        this(CollisionJNI.new_CollisionObjectWrapper__SWIG_11(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject), true);
    }

    private btCollisionObjectWrapper getWrapper() {
        return btCollisionObjectWrapper.internalTemp(CollisionJNI.CollisionObjectWrapper_getWrapper(this.swigCPtr, this), false);
    }
}
