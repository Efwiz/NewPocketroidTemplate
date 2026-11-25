package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleMesh extends btTriangleIndexVertexArray {
    private long swigCPtr;

    protected btTriangleMesh(String str, long j, boolean z) {
        super(str, CollisionJNI.btTriangleMesh_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTriangleMesh(long j, boolean z) {
        this("btTriangleMesh", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTriangleMesh_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTriangleMesh bttrianglemesh) {
        if (bttrianglemesh == null) {
            return 0L;
        }
        return bttrianglemesh.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btTriangleMesh(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setWeldingThreshold(float f) {
        CollisionJNI.btTriangleMesh_weldingThreshold_set(this.swigCPtr, this, f);
    }

    public float getWeldingThreshold() {
        return CollisionJNI.btTriangleMesh_weldingThreshold_get(this.swigCPtr, this);
    }

    public btTriangleMesh(boolean z, boolean z2) {
        this(CollisionJNI.new_btTriangleMesh__SWIG_0(z, z2), true);
    }

    public btTriangleMesh(boolean z) {
        this(CollisionJNI.new_btTriangleMesh__SWIG_1(z), true);
    }

    public btTriangleMesh() {
        this(CollisionJNI.new_btTriangleMesh__SWIG_2(), true);
    }

    public boolean getUse32bitIndices() {
        return CollisionJNI.btTriangleMesh_getUse32bitIndices(this.swigCPtr, this);
    }

    public boolean getUse4componentVertices() {
        return CollisionJNI.btTriangleMesh_getUse4componentVertices(this.swigCPtr, this);
    }

    public void addTriangle(Vector3 vector3, Vector3 vector4, Vector3 vector5, boolean z) {
        CollisionJNI.btTriangleMesh_addTriangle__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5, z);
    }

    public void addTriangle(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        CollisionJNI.btTriangleMesh_addTriangle__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5);
    }

    public void addTriangleIndices(int i, int i2, int i3) {
        CollisionJNI.btTriangleMesh_addTriangleIndices(this.swigCPtr, this, i, i2, i3);
    }

    public int getNumTriangles() {
        return CollisionJNI.btTriangleMesh_getNumTriangles(this.swigCPtr, this);
    }

    public int findOrAddVertex(Vector3 vector3, boolean z) {
        return CollisionJNI.btTriangleMesh_findOrAddVertex(this.swigCPtr, this, vector3, z);
    }

    public void addIndex(int i) {
        CollisionJNI.btTriangleMesh_addIndex(this.swigCPtr, this, i);
    }
}
