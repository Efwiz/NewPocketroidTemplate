package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;
import com.badlogic.gdx.utils.Array;

/* JADX INFO: loaded from: classes8.dex */
public class btBvhTriangleMeshShape extends btTriangleMeshShape {
    protected static final Array<btBvhTriangleMeshShape> instances = new Array<>();
    private btStridingMeshInterface meshInterface;
    private long swigCPtr;

    protected btBvhTriangleMeshShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btBvhTriangleMeshShape_SWIGUpcast(j), z);
        this.meshInterface = null;
        this.swigCPtr = j;
    }

    public btBvhTriangleMeshShape(long j, boolean z) {
        this("btBvhTriangleMeshShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btBvhTriangleMeshShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btBvhTriangleMeshShape btbvhtrianglemeshshape) {
        if (btbvhtrianglemeshshape == null) {
            return 0L;
        }
        return btbvhtrianglemeshshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btBvhTriangleMeshShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected static <T extends MeshPart> btBvhTriangleMeshShape getInstance(Array<T> array) {
        Array.ArrayIterator<btBvhTriangleMeshShape> it = instances.iterator();
        while (it.hasNext()) {
            btBvhTriangleMeshShape next = it.next();
            btStridingMeshInterface btstridingmeshinterface = next.meshInterface;
            if ((btstridingmeshinterface instanceof btTriangleIndexVertexArray) && btTriangleIndexVertexArray.compare((btTriangleIndexVertexArray) btstridingmeshinterface, array)) {
                return next;
            }
        }
        return null;
    }

    public static <T extends MeshPart> btBvhTriangleMeshShape obtain(Array<T> array) {
        btBvhTriangleMeshShape btbvhtrianglemeshshape = getInstance(array);
        if (btbvhtrianglemeshshape == null) {
            btbvhtrianglemeshshape = new btBvhTriangleMeshShape((btStridingMeshInterface) btTriangleIndexVertexArray.obtain(array), true);
            instances.add(btbvhtrianglemeshshape);
        }
        btbvhtrianglemeshshape.obtain();
        return btbvhtrianglemeshshape;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape
    public btStridingMeshInterface getMeshInterface() {
        return this.meshInterface;
    }

    public <T extends MeshPart> btBvhTriangleMeshShape(Array<T> array) {
        this((Array) array, true);
    }

    public <T extends MeshPart> btBvhTriangleMeshShape(Array<T> array, boolean z) {
        this(1, btTriangleIndexVertexArray.obtain(array), z);
    }

    public <T extends MeshPart> btBvhTriangleMeshShape(Array<T> array, boolean z, boolean z2) {
        this(1, btTriangleIndexVertexArray.obtain(array), z, z2);
    }

    public <T extends MeshPart> btBvhTriangleMeshShape(Array<T> array, boolean z, Vector3 vector3, Vector3 vector4) {
        this(1, btTriangleIndexVertexArray.obtain(array), z, vector3, vector4);
    }

    public <T extends MeshPart> btBvhTriangleMeshShape(Array<T> array, boolean z, Vector3 vector3, Vector3 vector4, boolean z2) {
        this(1, btTriangleIndexVertexArray.obtain(array), z, vector3, vector4, z2);
    }

    public btBvhTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, boolean z) {
        this(0, btstridingmeshinterface, z);
    }

    public btBvhTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, boolean z, boolean z2) {
        this(0, btstridingmeshinterface, z, z2);
    }

    public btBvhTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4, boolean z2) {
        this(0, btstridingmeshinterface, z, vector3, vector4, z2);
    }

    public btBvhTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4) {
        this(0, btstridingmeshinterface, z, vector3, vector4);
    }

    private btBvhTriangleMeshShape(int i, btStridingMeshInterface btstridingmeshinterface, boolean z) {
        this(true, btstridingmeshinterface, z);
        this.meshInterface = btstridingmeshinterface;
        if (i == 0) {
            btstridingmeshinterface.obtain();
        }
    }

    private btBvhTriangleMeshShape(int i, btStridingMeshInterface btstridingmeshinterface, boolean z, boolean z2) {
        this(true, btstridingmeshinterface, z, z2);
        this.meshInterface = btstridingmeshinterface;
        if (i == 0) {
            btstridingmeshinterface.obtain();
        }
    }

    private btBvhTriangleMeshShape(int i, btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4, boolean z2) {
        this(true, btstridingmeshinterface, z, vector3, vector4, z2);
        this.meshInterface = btstridingmeshinterface;
        if (i == 0) {
            btstridingmeshinterface.obtain();
        }
    }

    private btBvhTriangleMeshShape(int i, btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4) {
        this(true, btstridingmeshinterface, z, vector3, vector4);
        this.meshInterface = btstridingmeshinterface;
        if (i == 0) {
            btstridingmeshinterface.obtain();
        }
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        btStridingMeshInterface btstridingmeshinterface = this.meshInterface;
        if (btstridingmeshinterface != null) {
            btstridingmeshinterface.release();
        }
        this.meshInterface = null;
        super.dispose();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btBvhTriangleMeshShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btBvhTriangleMeshShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btBvhTriangleMeshShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btBvhTriangleMeshShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btBvhTriangleMeshShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btBvhTriangleMeshShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btBvhTriangleMeshShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleMeshShape, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btBvhTriangleMeshShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public boolean getOwnsBvh() {
        return CollisionJNI.btBvhTriangleMeshShape_getOwnsBvh(this.swigCPtr, this);
    }

    public void performRaycast(btTriangleCallback bttrianglecallback, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btBvhTriangleMeshShape_performRaycast(this.swigCPtr, this, btTriangleCallback.getCPtr(bttrianglecallback), bttrianglecallback, vector3, vector4);
    }

    public void performConvexcast(btTriangleCallback bttrianglecallback, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.btBvhTriangleMeshShape_performConvexcast(this.swigCPtr, this, btTriangleCallback.getCPtr(bttrianglecallback), bttrianglecallback, vector3, vector4, vector5, vector6);
    }

    public void refitTree(Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btBvhTriangleMeshShape_refitTree(this.swigCPtr, this, vector3, vector4);
    }

    public void partialRefitTree(Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btBvhTriangleMeshShape_partialRefitTree(this.swigCPtr, this, vector3, vector4);
    }

    public btOptimizedBvh getOptimizedBvh() {
        long jBtBvhTriangleMeshShape_getOptimizedBvh = CollisionJNI.btBvhTriangleMeshShape_getOptimizedBvh(this.swigCPtr, this);
        if (jBtBvhTriangleMeshShape_getOptimizedBvh == 0) {
            return null;
        }
        return new btOptimizedBvh(jBtBvhTriangleMeshShape_getOptimizedBvh, false);
    }

    public void setOptimizedBvh(btOptimizedBvh btoptimizedbvh, Vector3 vector3) {
        CollisionJNI.btBvhTriangleMeshShape_setOptimizedBvh__SWIG_0(this.swigCPtr, this, btOptimizedBvh.getCPtr(btoptimizedbvh), btoptimizedbvh, vector3);
    }

    public void setOptimizedBvh(btOptimizedBvh btoptimizedbvh) {
        CollisionJNI.btBvhTriangleMeshShape_setOptimizedBvh__SWIG_1(this.swigCPtr, this, btOptimizedBvh.getCPtr(btoptimizedbvh), btoptimizedbvh);
    }

    public void buildOptimizedBvh() {
        CollisionJNI.btBvhTriangleMeshShape_buildOptimizedBvh(this.swigCPtr, this);
    }

    public boolean usesQuantizedAabbCompression() {
        return CollisionJNI.btBvhTriangleMeshShape_usesQuantizedAabbCompression(this.swigCPtr, this);
    }

    public void setTriangleInfoMap(btTriangleInfoMap bttriangleinfomap) {
        CollisionJNI.btBvhTriangleMeshShape_setTriangleInfoMap(this.swigCPtr, this, btTriangleInfoMap.getCPtr(bttriangleinfomap), bttriangleinfomap);
    }

    public btTriangleInfoMap getTriangleInfoMap() {
        long jBtBvhTriangleMeshShape_getTriangleInfoMap__SWIG_0 = CollisionJNI.btBvhTriangleMeshShape_getTriangleInfoMap__SWIG_0(this.swigCPtr, this);
        if (jBtBvhTriangleMeshShape_getTriangleInfoMap__SWIG_0 == 0) {
            return null;
        }
        return new btTriangleInfoMap(jBtBvhTriangleMeshShape_getTriangleInfoMap__SWIG_0, false);
    }

    public void serializeSingleBvh(btSerializer btserializer) {
        CollisionJNI.btBvhTriangleMeshShape_serializeSingleBvh(this.swigCPtr, this, btSerializer.getCPtr(btserializer), btserializer);
    }

    public void serializeSingleTriangleInfoMap(btSerializer btserializer) {
        CollisionJNI.btBvhTriangleMeshShape_serializeSingleTriangleInfoMap(this.swigCPtr, this, btSerializer.getCPtr(btserializer), btserializer);
    }

    private btBvhTriangleMeshShape(boolean z, btStridingMeshInterface btstridingmeshinterface, boolean z2, boolean z3) {
        this(CollisionJNI.new_btBvhTriangleMeshShape__SWIG_0(z, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z2, z3), true);
    }

    private btBvhTriangleMeshShape(boolean z, btStridingMeshInterface btstridingmeshinterface, boolean z2) {
        this(CollisionJNI.new_btBvhTriangleMeshShape__SWIG_1(z, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z2), true);
    }

    private btBvhTriangleMeshShape(boolean z, btStridingMeshInterface btstridingmeshinterface, boolean z2, Vector3 vector3, Vector3 vector4, boolean z3) {
        this(CollisionJNI.new_btBvhTriangleMeshShape__SWIG_2(z, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z2, vector3, vector4, z3), true);
    }

    private btBvhTriangleMeshShape(boolean z, btStridingMeshInterface btstridingmeshinterface, boolean z2, Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_btBvhTriangleMeshShape__SWIG_3(z, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z2, vector3, vector4), true);
    }
}
