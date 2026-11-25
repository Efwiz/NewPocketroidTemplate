package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3Array;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexPolyhedron extends BulletBase {
    private long swigCPtr;

    protected btConvexPolyhedron(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConvexPolyhedron(long j, boolean z) {
        this("btConvexPolyhedron", j, z);
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

    public static long getCPtr(btConvexPolyhedron btconvexpolyhedron) {
        if (btconvexpolyhedron == null) {
            return 0L;
        }
        return btconvexpolyhedron.swigCPtr;
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
                CollisionJNI.delete_btConvexPolyhedron(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btConvexPolyhedron_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btConvexPolyhedron_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btConvexPolyhedron_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btConvexPolyhedron_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btConvexPolyhedron_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btConvexPolyhedron_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btConvexPolyhedron_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btConvexPolyhedron_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btConvexPolyhedron() {
        this(CollisionJNI.new_btConvexPolyhedron(), true);
    }

    public void setVertices(btVector3Array btvector3array) {
        CollisionJNI.btConvexPolyhedron_vertices_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public btVector3Array getVertices() {
        long jBtConvexPolyhedron_vertices_get = CollisionJNI.btConvexPolyhedron_vertices_get(this.swigCPtr, this);
        if (jBtConvexPolyhedron_vertices_get == 0) {
            return null;
        }
        return new btVector3Array(jBtConvexPolyhedron_vertices_get, false);
    }

    public void setFaces(SWIGTYPE_p_btAlignedObjectArrayT_btFace_t sWIGTYPE_p_btAlignedObjectArrayT_btFace_t) {
        CollisionJNI.btConvexPolyhedron_faces_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btFace_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btFace_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btFace_t getFaces() {
        long jBtConvexPolyhedron_faces_get = CollisionJNI.btConvexPolyhedron_faces_get(this.swigCPtr, this);
        if (jBtConvexPolyhedron_faces_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btFace_t(jBtConvexPolyhedron_faces_get, false);
    }

    public void setUniqueEdges(btVector3Array btvector3array) {
        CollisionJNI.btConvexPolyhedron_uniqueEdges_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public btVector3Array getUniqueEdges() {
        long jBtConvexPolyhedron_uniqueEdges_get = CollisionJNI.btConvexPolyhedron_uniqueEdges_get(this.swigCPtr, this);
        if (jBtConvexPolyhedron_uniqueEdges_get == 0) {
            return null;
        }
        return new btVector3Array(jBtConvexPolyhedron_uniqueEdges_get, false);
    }

    public void setLocalCenter(btVector3 btvector3) {
        CollisionJNI.btConvexPolyhedron_localCenter_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getLocalCenter() {
        long jBtConvexPolyhedron_localCenter_get = CollisionJNI.btConvexPolyhedron_localCenter_get(this.swigCPtr, this);
        if (jBtConvexPolyhedron_localCenter_get == 0) {
            return null;
        }
        return new btVector3(jBtConvexPolyhedron_localCenter_get, false);
    }

    public void setExtents(btVector3 btvector3) {
        CollisionJNI.btConvexPolyhedron_extents_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getExtents() {
        long jBtConvexPolyhedron_extents_get = CollisionJNI.btConvexPolyhedron_extents_get(this.swigCPtr, this);
        if (jBtConvexPolyhedron_extents_get == 0) {
            return null;
        }
        return new btVector3(jBtConvexPolyhedron_extents_get, false);
    }

    public void setRadius(float f) {
        CollisionJNI.btConvexPolyhedron_radius_set(this.swigCPtr, this, f);
    }

    public float getRadius() {
        return CollisionJNI.btConvexPolyhedron_radius_get(this.swigCPtr, this);
    }

    public void setMC(btVector3 btvector3) {
        CollisionJNI.btConvexPolyhedron_mC_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMC() {
        long jBtConvexPolyhedron_mC_get = CollisionJNI.btConvexPolyhedron_mC_get(this.swigCPtr, this);
        if (jBtConvexPolyhedron_mC_get == 0) {
            return null;
        }
        return new btVector3(jBtConvexPolyhedron_mC_get, false);
    }

    public void setME(btVector3 btvector3) {
        CollisionJNI.btConvexPolyhedron_mE_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getME() {
        long jBtConvexPolyhedron_mE_get = CollisionJNI.btConvexPolyhedron_mE_get(this.swigCPtr, this);
        if (jBtConvexPolyhedron_mE_get == 0) {
            return null;
        }
        return new btVector3(jBtConvexPolyhedron_mE_get, false);
    }

    public void initialize() {
        CollisionJNI.btConvexPolyhedron_initialize(this.swigCPtr, this);
    }

    public boolean testContainment() {
        return CollisionJNI.btConvexPolyhedron_testContainment(this.swigCPtr, this);
    }

    public void project(Matrix4 matrix4, Vector3 vector3, SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2, Vector3 vector4, Vector3 vector5) {
        CollisionJNI.btConvexPolyhedron_project(this.swigCPtr, this, matrix4, vector3, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2), vector4, vector5);
    }
}
