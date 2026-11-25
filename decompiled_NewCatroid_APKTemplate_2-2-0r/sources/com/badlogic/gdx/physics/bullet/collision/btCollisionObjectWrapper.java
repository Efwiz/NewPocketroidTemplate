package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionObjectWrapper extends BulletBase {
    private long swigCPtr;
    protected static final btCollisionObjectWrapper temp = new btCollisionObjectWrapper(0, false);
    private static btCollisionObjectWrapper[] argumentInstances = {new btCollisionObjectWrapper(0, false), new btCollisionObjectWrapper(0, false), new btCollisionObjectWrapper(0, false), new btCollisionObjectWrapper(0, false)};
    private static int argumentIndex = -1;

    protected btCollisionObjectWrapper(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionObjectWrapper(long j, boolean z) {
        this("btCollisionObjectWrapper", j, z);
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

    public static long getCPtr(btCollisionObjectWrapper btcollisionobjectwrapper) {
        if (btcollisionobjectwrapper == null) {
            return 0L;
        }
        return btcollisionobjectwrapper.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                throw new UnsupportedOperationException("C++ destructor does not have public access");
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static btCollisionObjectWrapper internalTemp(long j, boolean z) {
        btCollisionObjectWrapper btcollisionobjectwrapper = temp;
        btcollisionobjectwrapper.reset(j, z);
        return btcollisionobjectwrapper;
    }

    protected static btCollisionObjectWrapper obtainForArgument(long j, boolean z) {
        btCollisionObjectWrapper[] btcollisionobjectwrapperArr = argumentInstances;
        int i = (argumentIndex + 1) & 3;
        argumentIndex = i;
        btCollisionObjectWrapper btcollisionobjectwrapper = btcollisionobjectwrapperArr[i];
        btcollisionobjectwrapper.reset(j, z);
        return btcollisionobjectwrapper;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    public void setParent(btCollisionObjectWrapper btcollisionobjectwrapper) {
        CollisionJNI.btCollisionObjectWrapper_parent_set(this.swigCPtr, this, getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper);
    }

    public btCollisionObjectWrapper getParent() {
        return internalTemp(CollisionJNI.btCollisionObjectWrapper_parent_get(this.swigCPtr, this), false);
    }

    public void setShape(btCollisionShape btcollisionshape) {
        CollisionJNI.btCollisionObjectWrapper_shape_set(this.swigCPtr, this, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    public btCollisionShape getShape() {
        long jBtCollisionObjectWrapper_shape_get = CollisionJNI.btCollisionObjectWrapper_shape_get(this.swigCPtr, this);
        if (jBtCollisionObjectWrapper_shape_get == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionObjectWrapper_shape_get, false);
    }

    public void setCollisionObject(btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionObjectWrapper_collisionObject_set(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public btCollisionObject getCollisionObject() {
        return btCollisionObject.getInstance(CollisionJNI.btCollisionObjectWrapper_collisionObject_get(this.swigCPtr, this), false);
    }

    public Matrix4 getWorldTransform() {
        return CollisionJNI.btCollisionObjectWrapper_worldTransform_get(this.swigCPtr, this);
    }

    public void setPartId(int i) {
        CollisionJNI.btCollisionObjectWrapper_partId_set(this.swigCPtr, this, i);
    }

    public int getPartId() {
        return CollisionJNI.btCollisionObjectWrapper_partId_get(this.swigCPtr, this);
    }

    public void setIndex(int i) {
        CollisionJNI.btCollisionObjectWrapper_index_set(this.swigCPtr, this, i);
    }

    public int getIndex() {
        return CollisionJNI.btCollisionObjectWrapper_index_get(this.swigCPtr, this);
    }

    public btCollisionShape getCollisionShape() {
        long jBtCollisionObjectWrapper_getCollisionShape = CollisionJNI.btCollisionObjectWrapper_getCollisionShape(this.swigCPtr, this);
        if (jBtCollisionObjectWrapper_getCollisionShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionObjectWrapper_getCollisionShape, false);
    }
}
