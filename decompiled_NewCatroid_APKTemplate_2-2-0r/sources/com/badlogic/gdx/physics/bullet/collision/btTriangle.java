package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangle extends BulletBase {
    private long swigCPtr;

    protected btTriangle(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTriangle(long j, boolean z) {
        this("btTriangle", j, z);
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

    public static long getCPtr(btTriangle bttriangle) {
        if (bttriangle == null) {
            return 0L;
        }
        return bttriangle.swigCPtr;
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
                CollisionJNI.delete_btTriangle(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setVertex0(btVector3 btvector3) {
        CollisionJNI.btTriangle_vertex0_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getVertex0() {
        long jBtTriangle_vertex0_get = CollisionJNI.btTriangle_vertex0_get(this.swigCPtr, this);
        if (jBtTriangle_vertex0_get == 0) {
            return null;
        }
        return new btVector3(jBtTriangle_vertex0_get, false);
    }

    public void setVertex1(btVector3 btvector3) {
        CollisionJNI.btTriangle_vertex1_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getVertex1() {
        long jBtTriangle_vertex1_get = CollisionJNI.btTriangle_vertex1_get(this.swigCPtr, this);
        if (jBtTriangle_vertex1_get == 0) {
            return null;
        }
        return new btVector3(jBtTriangle_vertex1_get, false);
    }

    public void setVertex2(btVector3 btvector3) {
        CollisionJNI.btTriangle_vertex2_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getVertex2() {
        long jBtTriangle_vertex2_get = CollisionJNI.btTriangle_vertex2_get(this.swigCPtr, this);
        if (jBtTriangle_vertex2_get == 0) {
            return null;
        }
        return new btVector3(jBtTriangle_vertex2_get, false);
    }

    public void setPartId(int i) {
        CollisionJNI.btTriangle_partId_set(this.swigCPtr, this, i);
    }

    public int getPartId() {
        return CollisionJNI.btTriangle_partId_get(this.swigCPtr, this);
    }

    public void setTriangleIndex(int i) {
        CollisionJNI.btTriangle_triangleIndex_set(this.swigCPtr, this, i);
    }

    public int getTriangleIndex() {
        return CollisionJNI.btTriangle_triangleIndex_get(this.swigCPtr, this);
    }

    public btTriangle() {
        this(CollisionJNI.new_btTriangle(), true);
    }
}
