package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btBU_Simplex1to4 extends btPolyhedralConvexAabbCachingShape {
    private long swigCPtr;

    protected btBU_Simplex1to4(String str, long j, boolean z) {
        super(str, CollisionJNI.btBU_Simplex1to4_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btBU_Simplex1to4(long j, boolean z) {
        this("btBU_Simplex1to4", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btBU_Simplex1to4_SWIGUpcast(j), z);
    }

    public static long getCPtr(btBU_Simplex1to4 btbu_simplex1to4) {
        if (btbu_simplex1to4 == null) {
            return 0L;
        }
        return btbu_simplex1to4.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btBU_Simplex1to4(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btBU_Simplex1to4_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btBU_Simplex1to4_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btBU_Simplex1to4_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btBU_Simplex1to4_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btBU_Simplex1to4_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btBU_Simplex1to4_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btBU_Simplex1to4_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btBU_Simplex1to4_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btBU_Simplex1to4() {
        this(CollisionJNI.new_btBU_Simplex1to4__SWIG_0(), true);
    }

    public btBU_Simplex1to4(Vector3 vector3) {
        this(CollisionJNI.new_btBU_Simplex1to4__SWIG_1(vector3), true);
    }

    public btBU_Simplex1to4(Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_btBU_Simplex1to4__SWIG_2(vector3, vector4), true);
    }

    public btBU_Simplex1to4(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        this(CollisionJNI.new_btBU_Simplex1to4__SWIG_3(vector3, vector4, vector5), true);
    }

    public btBU_Simplex1to4(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        this(CollisionJNI.new_btBU_Simplex1to4__SWIG_4(vector3, vector4, vector5, vector6), true);
    }

    public void reset() {
        CollisionJNI.btBU_Simplex1to4_reset(this.swigCPtr, this);
    }

    public void addVertex(Vector3 vector3) {
        CollisionJNI.btBU_Simplex1to4_addVertex(this.swigCPtr, this, vector3);
    }

    public int getIndex(int i) {
        return CollisionJNI.btBU_Simplex1to4_getIndex(this.swigCPtr, this, i);
    }
}
