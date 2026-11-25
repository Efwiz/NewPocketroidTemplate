package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleMeshShape extends btConcaveShape {
    private long swigCPtr;

    protected btTriangleMeshShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btTriangleMeshShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTriangleMeshShape(long j, boolean z) {
        this("btTriangleMeshShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTriangleMeshShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTriangleMeshShape bttrianglemeshshape) {
        if (bttrianglemeshshape == null) {
            return 0L;
        }
        return bttrianglemeshshape.swigCPtr;
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
                CollisionJNI.delete_btTriangleMeshShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btTriangleMeshShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btTriangleMeshShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btTriangleMeshShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btTriangleMeshShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btTriangleMeshShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btTriangleMeshShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btTriangleMeshShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btTriangleMeshShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public Vector3 localGetSupportingVertex(Vector3 vector3) {
        return CollisionJNI.btTriangleMeshShape_localGetSupportingVertex(this.swigCPtr, this, vector3);
    }

    public Vector3 localGetSupportingVertexWithoutMargin(Vector3 vector3) {
        return CollisionJNI.btTriangleMeshShape_localGetSupportingVertexWithoutMargin(this.swigCPtr, this, vector3);
    }

    public void recalcLocalAabb() {
        CollisionJNI.btTriangleMeshShape_recalcLocalAabb(this.swigCPtr, this);
    }

    public btStridingMeshInterface getMeshInterface() {
        long jBtTriangleMeshShape_getMeshInterface = CollisionJNI.btTriangleMeshShape_getMeshInterface(this.swigCPtr, this);
        if (jBtTriangleMeshShape_getMeshInterface == 0) {
            return null;
        }
        return new btStridingMeshInterface(jBtTriangleMeshShape_getMeshInterface, false);
    }

    public btStridingMeshInterface getMeshInterfaceConst() {
        long jBtTriangleMeshShape_getMeshInterfaceConst = CollisionJNI.btTriangleMeshShape_getMeshInterfaceConst(this.swigCPtr, this);
        if (jBtTriangleMeshShape_getMeshInterfaceConst == 0) {
            return null;
        }
        return new btStridingMeshInterface(jBtTriangleMeshShape_getMeshInterfaceConst, false);
    }

    public Vector3 getLocalAabbMin() {
        return CollisionJNI.btTriangleMeshShape_getLocalAabbMin(this.swigCPtr, this);
    }

    public Vector3 getLocalAabbMax() {
        return CollisionJNI.btTriangleMeshShape_getLocalAabbMax(this.swigCPtr, this);
    }
}
