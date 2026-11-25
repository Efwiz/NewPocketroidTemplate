package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexTriangleMeshShape extends btPolyhedralConvexAabbCachingShape {
    private long swigCPtr;

    protected btConvexTriangleMeshShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexTriangleMeshShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexTriangleMeshShape(long j, boolean z) {
        this("btConvexTriangleMeshShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexAabbCachingShape, com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexTriangleMeshShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexTriangleMeshShape btconvextrianglemeshshape) {
        if (btconvextrianglemeshshape == null) {
            return 0L;
        }
        return btconvextrianglemeshshape.swigCPtr;
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
                CollisionJNI.delete_btConvexTriangleMeshShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btConvexTriangleMeshShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btConvexTriangleMeshShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btConvexTriangleMeshShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btConvexTriangleMeshShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btConvexTriangleMeshShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btConvexTriangleMeshShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btConvexTriangleMeshShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btPolyhedralConvexShape, com.badlogic.gdx.physics.bullet.collision.btConvexInternalShape, com.badlogic.gdx.physics.bullet.collision.btConvexShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btConvexTriangleMeshShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btConvexTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, boolean z) {
        this(CollisionJNI.new_btConvexTriangleMeshShape__SWIG_0(btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z), true);
    }

    public btConvexTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface) {
        this(CollisionJNI.new_btConvexTriangleMeshShape__SWIG_1(btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface), true);
    }

    public btStridingMeshInterface getMeshInterface() {
        long jBtConvexTriangleMeshShape_getMeshInterface = CollisionJNI.btConvexTriangleMeshShape_getMeshInterface(this.swigCPtr, this);
        if (jBtConvexTriangleMeshShape_getMeshInterface == 0) {
            return null;
        }
        return new btStridingMeshInterface(jBtConvexTriangleMeshShape_getMeshInterface, false);
    }

    public btStridingMeshInterface getMeshInterfaceConst() {
        long jBtConvexTriangleMeshShape_getMeshInterfaceConst = CollisionJNI.btConvexTriangleMeshShape_getMeshInterfaceConst(this.swigCPtr, this);
        if (jBtConvexTriangleMeshShape_getMeshInterfaceConst == 0) {
            return null;
        }
        return new btStridingMeshInterface(jBtConvexTriangleMeshShape_getMeshInterfaceConst, false);
    }

    public void calculatePrincipalAxisTransform(Matrix4 matrix4, Vector3 vector3, SWIGTYPE_p_float sWIGTYPE_p_float) {
        CollisionJNI.btConvexTriangleMeshShape_calculatePrincipalAxisTransform(this.swigCPtr, this, matrix4, vector3, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }
}
