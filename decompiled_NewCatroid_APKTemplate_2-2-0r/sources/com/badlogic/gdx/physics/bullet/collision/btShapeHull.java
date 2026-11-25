package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btShapeHull extends BulletBase {
    private long swigCPtr;

    protected btShapeHull(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btShapeHull(long j, boolean z) {
        this("btShapeHull", j, z);
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

    public static long getCPtr(btShapeHull btshapehull) {
        if (btshapehull == null) {
            return 0L;
        }
        return btshapehull.swigCPtr;
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
                CollisionJNI.delete_btShapeHull(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btShapeHull_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btShapeHull_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btShapeHull_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btShapeHull_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btShapeHull_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btShapeHull_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btShapeHull_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btShapeHull_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btShapeHull(btConvexShape btconvexshape) {
        this(CollisionJNI.new_btShapeHull(btConvexShape.getCPtr(btconvexshape), btconvexshape), true);
    }

    public boolean buildHull(float f) {
        return CollisionJNI.btShapeHull_buildHull(this.swigCPtr, this, f);
    }

    public int numTriangles() {
        return CollisionJNI.btShapeHull_numTriangles(this.swigCPtr, this);
    }

    public int numVertices() {
        return CollisionJNI.btShapeHull_numVertices(this.swigCPtr, this);
    }

    public int numIndices() {
        return CollisionJNI.btShapeHull_numIndices(this.swigCPtr, this);
    }

    public Vector3 getVertex(int i) {
        return CollisionJNI.btShapeHull_getVertex(this.swigCPtr, this, i);
    }

    public int getIndex(int i) {
        return CollisionJNI.btShapeHull_getIndex(this.swigCPtr, this, i);
    }
}
