package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btIndexedMesh extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final Array<btIndexedMesh> instances = new Array<>();
    private long swigCPtr;
    public Object tag;

    protected btIndexedMesh(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btIndexedMesh(long j, boolean z) {
        this("btIndexedMesh", j, z);
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

    public static long getCPtr(btIndexedMesh btindexedmesh) {
        if (btindexedmesh == null) {
            return 0L;
        }
        return btindexedmesh.swigCPtr;
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
                CollisionJNI.delete_btIndexedMesh(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected static btIndexedMesh getInstance(Object obj) {
        int i = instances.size;
        for (int i2 = 0; i2 < i; i2++) {
            btIndexedMesh btindexedmesh = instances.get(i2);
            if (obj.equals(btindexedmesh.tag)) {
                return btindexedmesh;
            }
        }
        return null;
    }

    public static btIndexedMesh obtain(MeshPart meshPart) {
        if (meshPart == null) {
            throw new GdxRuntimeException("meshPart cannot be null");
        }
        btIndexedMesh btindexedmesh = getInstance(meshPart);
        if (btindexedmesh == null) {
            btindexedmesh = new btIndexedMesh(meshPart);
            instances.add(btindexedmesh);
        }
        btindexedmesh.obtain();
        return btindexedmesh;
    }

    public static btIndexedMesh obtain(Object obj, FloatBuffer floatBuffer, int i, int i2, int i3, ShortBuffer shortBuffer, int i4, int i5) {
        if (obj == null) {
            throw new GdxRuntimeException("tag cannot be null");
        }
        btIndexedMesh btindexedmesh = getInstance(obj);
        if (btindexedmesh == null) {
            btindexedmesh = new btIndexedMesh(floatBuffer, i, i2, i3, shortBuffer, i4, i5);
            btindexedmesh.tag = obj;
            instances.add(btindexedmesh);
        }
        btindexedmesh.obtain();
        return btindexedmesh;
    }

    public btIndexedMesh(Mesh mesh) {
        this();
        set(mesh);
    }

    public btIndexedMesh(MeshPart meshPart) {
        this();
        set(meshPart);
    }

    public btIndexedMesh(Mesh mesh, int i, int i2) {
        this();
        set(mesh, i, i2);
    }

    public btIndexedMesh(FloatBuffer floatBuffer, int i, int i2, int i3, ShortBuffer shortBuffer, int i4, int i5) {
        this();
        set(floatBuffer, i, i2, i3, shortBuffer, i4, i5);
    }

    public void set(Mesh mesh) {
        set(mesh, mesh, 0, mesh.getNumIndices());
    }

    public void set(Object obj, Mesh mesh) {
        set(obj, mesh, 0, mesh.getNumIndices());
    }

    public void set(MeshPart meshPart) {
        if (meshPart.primitiveType != 4) {
            throw new GdxRuntimeException("Mesh must be indexed and triangulated");
        }
        set(meshPart, meshPart.mesh, meshPart.offset, meshPart.size);
    }

    public void set(Mesh mesh, int i, int i2) {
        set(null, mesh, i, i2);
    }

    public void set(Object obj, Mesh mesh, int i, int i2) {
        if (i2 <= 0 || i2 % 3 != 0) {
            throw new GdxRuntimeException("Mesh must be indexed and triangulated");
        }
        VertexAttribute vertexAttribute = mesh.getVertexAttribute(1);
        if (vertexAttribute == null) {
            throw new GdxRuntimeException("Mesh doesn't have a position attribute");
        }
        set(obj, mesh.getVerticesBuffer(false), mesh.getVertexSize(), mesh.getNumVertices(), vertexAttribute.offset, mesh.getIndicesBuffer(false), i, i2);
    }

    public void set(FloatBuffer floatBuffer, int i, int i2, int i3, ShortBuffer shortBuffer, int i4, int i5) {
        set(null, floatBuffer, i, i2, i3, shortBuffer, i4, i5);
    }

    public void set(Object obj, FloatBuffer floatBuffer, int i, int i2, int i3, ShortBuffer shortBuffer, int i4, int i5) {
        setVertices(floatBuffer, i, i2, i3);
        setIndices(shortBuffer, i4, i5);
        this.tag = obj;
    }

    public long operatorNew(long j) {
        return CollisionJNI.btIndexedMesh_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btIndexedMesh_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btIndexedMesh_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btIndexedMesh_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btIndexedMesh_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btIndexedMesh_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btIndexedMesh_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btIndexedMesh_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setNumTriangles(int i) {
        CollisionJNI.btIndexedMesh_numTriangles_set(this.swigCPtr, this, i);
    }

    public int getNumTriangles() {
        return CollisionJNI.btIndexedMesh_numTriangles_get(this.swigCPtr, this);
    }

    public void setTriangleIndexBase(ByteBuffer byteBuffer) {
        CollisionJNI.btIndexedMesh_triangleIndexBase_set(this.swigCPtr, this, byteBuffer);
    }

    public ByteBuffer getTriangleIndexBase() {
        return CollisionJNI.btIndexedMesh_triangleIndexBase_get(this.swigCPtr, this);
    }

    public void setTriangleIndexStride(int i) {
        CollisionJNI.btIndexedMesh_triangleIndexStride_set(this.swigCPtr, this, i);
    }

    public int getTriangleIndexStride() {
        return CollisionJNI.btIndexedMesh_triangleIndexStride_get(this.swigCPtr, this);
    }

    public void setNumVertices(int i) {
        CollisionJNI.btIndexedMesh_numVertices_set(this.swigCPtr, this, i);
    }

    public int getNumVertices() {
        return CollisionJNI.btIndexedMesh_numVertices_get(this.swigCPtr, this);
    }

    public void setVertexBase(ByteBuffer byteBuffer) {
        CollisionJNI.btIndexedMesh_vertexBase_set(this.swigCPtr, this, byteBuffer);
    }

    public ByteBuffer getVertexBase() {
        return CollisionJNI.btIndexedMesh_vertexBase_get(this.swigCPtr, this);
    }

    public void setVertexStride(int i) {
        CollisionJNI.btIndexedMesh_vertexStride_set(this.swigCPtr, this, i);
    }

    public int getVertexStride() {
        return CollisionJNI.btIndexedMesh_vertexStride_get(this.swigCPtr, this);
    }

    public void setIndexType(int i) {
        CollisionJNI.btIndexedMesh_indexType_set(this.swigCPtr, this, i);
    }

    public int getIndexType() {
        return CollisionJNI.btIndexedMesh_indexType_get(this.swigCPtr, this);
    }

    public void setVertexType(int i) {
        CollisionJNI.btIndexedMesh_vertexType_set(this.swigCPtr, this, i);
    }

    public int getVertexType() {
        return CollisionJNI.btIndexedMesh_vertexType_get(this.swigCPtr, this);
    }

    public btIndexedMesh() {
        this(CollisionJNI.new_btIndexedMesh(), true);
    }

    public void setTriangleIndexBase(ShortBuffer shortBuffer) {
        CollisionJNI.btIndexedMesh_setTriangleIndexBase(this.swigCPtr, this, shortBuffer);
    }

    public void setVertexBase(FloatBuffer floatBuffer) {
        CollisionJNI.btIndexedMesh_setVertexBase(this.swigCPtr, this, floatBuffer);
    }

    public void setVertices(FloatBuffer floatBuffer, int i, int i2, int i3) {
        CollisionJNI.btIndexedMesh_setVertices(this.swigCPtr, this, floatBuffer, i, i2, i3);
    }

    public void setIndices(ShortBuffer shortBuffer, int i, int i2) {
        CollisionJNI.btIndexedMesh_setIndices(this.swigCPtr, this, shortBuffer, i, i2);
    }
}
