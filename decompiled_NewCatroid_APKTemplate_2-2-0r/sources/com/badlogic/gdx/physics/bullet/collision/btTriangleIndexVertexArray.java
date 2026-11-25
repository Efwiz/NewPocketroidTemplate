package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleIndexVertexArray extends btStridingMeshInterface {
    protected static final Array<btTriangleIndexVertexArray> instances = new Array<>();
    protected final Array<btIndexedMesh> meshes;
    private long swigCPtr;

    protected btTriangleIndexVertexArray(String str, long j, boolean z) {
        super(str, CollisionJNI.btTriangleIndexVertexArray_SWIGUpcast(j), z);
        this.meshes = new Array<>(1);
        this.swigCPtr = j;
    }

    public btTriangleIndexVertexArray(long j, boolean z) {
        this("btTriangleIndexVertexArray", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTriangleIndexVertexArray_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTriangleIndexVertexArray bttriangleindexvertexarray) {
        if (bttriangleindexvertexarray == null) {
            return 0L;
        }
        return bttriangleindexvertexarray.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btTriangleIndexVertexArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static <T> boolean compare(btTriangleIndexVertexArray bttriangleindexvertexarray, Array<T> array) {
        if (bttriangleindexvertexarray.meshes.size != array.size) {
            return false;
        }
        Array.ArrayIterator<btIndexedMesh> it = bttriangleindexvertexarray.meshes.iterator();
        while (it.hasNext()) {
            Object obj = it.next().tag;
            if (obj == null) {
                return false;
            }
            Array.ArrayIterator<T> it2 = array.iterator();
            while (it2.hasNext()) {
                if (it2.next().equals(obj)) {
                }
            }
            return false;
        }
        return true;
    }

    protected static <T> btTriangleIndexVertexArray getInstance(Array<T> array) {
        Array.ArrayIterator<btTriangleIndexVertexArray> it = instances.iterator();
        while (it.hasNext()) {
            btTriangleIndexVertexArray next = it.next();
            if (compare(next, array)) {
                return next;
            }
        }
        return null;
    }

    public static <T extends MeshPart> btTriangleIndexVertexArray obtain(Array<T> array) {
        btTriangleIndexVertexArray bttriangleindexvertexarray = getInstance(array);
        if (bttriangleindexvertexarray == null) {
            bttriangleindexvertexarray = new btTriangleIndexVertexArray(array);
            instances.add(bttriangleindexvertexarray);
        }
        bttriangleindexvertexarray.obtain();
        return bttriangleindexvertexarray;
    }

    public btTriangleIndexVertexArray(MeshPart meshPart) {
        this();
        addMeshPart(meshPart);
    }

    public <T extends MeshPart> btTriangleIndexVertexArray(Iterable<T> iterable) {
        this();
        addMeshParts(iterable);
    }

    public int getIndexedMeshCount() {
        return this.meshes.size;
    }

    public btIndexedMesh getIndexedMesh(int i) {
        return this.meshes.get(i);
    }

    public btTriangleIndexVertexArray addMeshPart(MeshPart meshPart) {
        btIndexedMesh btindexedmeshObtain = btIndexedMesh.obtain(meshPart);
        addIndexedMesh(btindexedmeshObtain, 3);
        btindexedmeshObtain.release();
        return this;
    }

    public btTriangleIndexVertexArray addMeshParts(MeshPart... meshPartArr) {
        for (MeshPart meshPart : meshPartArr) {
            addMeshPart(meshPart);
        }
        return this;
    }

    public <T extends MeshPart> btTriangleIndexVertexArray addMeshParts(Iterable<T> iterable) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            addMeshPart(it.next());
        }
        return this;
    }

    public <T extends NodePart> btTriangleIndexVertexArray addNodeParts(Iterable<T> iterable) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            addMeshPart(it.next().meshPart);
        }
        return this;
    }

    public btTriangleIndexVertexArray addIndexedMesh(btIndexedMesh btindexedmesh, int i) {
        btindexedmesh.obtain();
        internalAddIndexedMesh(btindexedmesh, i);
        this.meshes.add(btindexedmesh);
        return this;
    }

    public btTriangleIndexVertexArray addIndexedMesh(btIndexedMesh btindexedmesh) {
        return addIndexedMesh(btindexedmesh, 3);
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        Array.ArrayIterator<btIndexedMesh> it = this.meshes.iterator();
        while (it.hasNext()) {
            it.next().release();
        }
        this.meshes.clear();
        super.dispose();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public long operatorNew(long j) {
        return CollisionJNI.btTriangleIndexVertexArray_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void operatorDelete(long j) {
        CollisionJNI.btTriangleIndexVertexArray_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btTriangleIndexVertexArray_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btTriangleIndexVertexArray_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public long operatorNewArray(long j) {
        return CollisionJNI.btTriangleIndexVertexArray_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void operatorDeleteArray(long j) {
        CollisionJNI.btTriangleIndexVertexArray_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btTriangleIndexVertexArray_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btTriangleIndexVertexArray_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btTriangleIndexVertexArray() {
        this(CollisionJNI.new_btTriangleIndexVertexArray(), true);
    }

    private void internalAddIndexedMesh(btIndexedMesh btindexedmesh, int i) {
        CollisionJNI.btTriangleIndexVertexArray_internalAddIndexedMesh__SWIG_0(this.swigCPtr, this, btIndexedMesh.getCPtr(btindexedmesh), btindexedmesh, i);
    }

    private void internalAddIndexedMesh(btIndexedMesh btindexedmesh) {
        CollisionJNI.btTriangleIndexVertexArray_internalAddIndexedMesh__SWIG_1(this.swigCPtr, this, btIndexedMesh.getCPtr(btindexedmesh), btindexedmesh);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void getLockedVertexIndexBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2, int i) {
        CollisionJNI.btTriangleIndexVertexArray_getLockedVertexIndexBase__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2), i);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void getLockedVertexIndexBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2) {
        CollisionJNI.btTriangleIndexVertexArray_getLockedVertexIndexBase__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2));
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void getLockedReadOnlyVertexIndexBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2, int i) {
        CollisionJNI.btTriangleIndexVertexArray_getLockedReadOnlyVertexIndexBase__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2), i);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void getLockedReadOnlyVertexIndexBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2) {
        CollisionJNI.btTriangleIndexVertexArray_getLockedReadOnlyVertexIndexBase__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btIndexedMesh_t getIndexedMeshArray() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btIndexedMesh_t(CollisionJNI.btTriangleIndexVertexArray_getIndexedMeshArray(this.swigCPtr, this), false);
    }
}
