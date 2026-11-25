package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btMultimaterialTriangleMeshShape extends btBvhTriangleMeshShape {
    private long swigCPtr;

    protected btMultimaterialTriangleMeshShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btMultimaterialTriangleMeshShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultimaterialTriangleMeshShape(long j, boolean z) {
        this("btMultimaterialTriangleMeshShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btMultimaterialTriangleMeshShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultimaterialTriangleMeshShape btmultimaterialtrianglemeshshape) {
        if (btmultimaterialtrianglemeshshape == null) {
            return 0L;
        }
        return btmultimaterialtrianglemeshshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btMultimaterialTriangleMeshShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btMultimaterialTriangleMeshShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btMultimaterialTriangleMeshShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btMultimaterialTriangleMeshShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btMultimaterialTriangleMeshShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btMultimaterialTriangleMeshShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btMultimaterialTriangleMeshShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btMultimaterialTriangleMeshShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btMultimaterialTriangleMeshShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btMultimaterialTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, boolean z, boolean z2) {
        this(CollisionJNI.new_btMultimaterialTriangleMeshShape__SWIG_0(btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z, z2), true);
    }

    public btMultimaterialTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, boolean z) {
        this(CollisionJNI.new_btMultimaterialTriangleMeshShape__SWIG_1(btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z), true);
    }

    public btMultimaterialTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4, boolean z2) {
        this(CollisionJNI.new_btMultimaterialTriangleMeshShape__SWIG_2(btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z, vector3, vector4, z2), true);
    }

    public btMultimaterialTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_btMultimaterialTriangleMeshShape__SWIG_3(btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z, vector3, vector4), true);
    }

    public btMaterial getMaterialProperties(int i, int i2) {
        long jBtMultimaterialTriangleMeshShape_getMaterialProperties = CollisionJNI.btMultimaterialTriangleMeshShape_getMaterialProperties(this.swigCPtr, this, i, i2);
        if (jBtMultimaterialTriangleMeshShape_getMaterialProperties == 0) {
            return null;
        }
        return new btMaterial(jBtMultimaterialTriangleMeshShape_getMaterialProperties, false);
    }
}
