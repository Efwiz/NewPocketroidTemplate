package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;

/* JADX INFO: loaded from: classes8.dex */
public class btPrimitiveTriangle extends BulletBase {
    private long swigCPtr;

    protected btPrimitiveTriangle(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPrimitiveTriangle(long j, boolean z) {
        this("btPrimitiveTriangle", j, z);
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

    public static long getCPtr(btPrimitiveTriangle btprimitivetriangle) {
        if (btprimitivetriangle == null) {
            return 0L;
        }
        return btprimitivetriangle.swigCPtr;
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
                CollisionJNI.delete_btPrimitiveTriangle(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setVertices(btVector3 btvector3) {
        CollisionJNI.btPrimitiveTriangle_vertices_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getVertices() {
        long jBtPrimitiveTriangle_vertices_get = CollisionJNI.btPrimitiveTriangle_vertices_get(this.swigCPtr, this);
        if (jBtPrimitiveTriangle_vertices_get == 0) {
            return null;
        }
        return new btVector3(jBtPrimitiveTriangle_vertices_get, false);
    }

    public void setPlane(btVector4 btvector4) {
        CollisionJNI.btPrimitiveTriangle_plane_set(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4);
    }

    public btVector4 getPlane() {
        long jBtPrimitiveTriangle_plane_get = CollisionJNI.btPrimitiveTriangle_plane_get(this.swigCPtr, this);
        if (jBtPrimitiveTriangle_plane_get == 0) {
            return null;
        }
        return new btVector4(jBtPrimitiveTriangle_plane_get, false);
    }

    public void setMargin(float f) {
        CollisionJNI.btPrimitiveTriangle_margin_set(this.swigCPtr, this, f);
    }

    public float getMargin() {
        return CollisionJNI.btPrimitiveTriangle_margin_get(this.swigCPtr, this);
    }

    public void setDummy(float f) {
        CollisionJNI.btPrimitiveTriangle_dummy_set(this.swigCPtr, this, f);
    }

    public float getDummy() {
        return CollisionJNI.btPrimitiveTriangle_dummy_get(this.swigCPtr, this);
    }

    public btPrimitiveTriangle() {
        this(CollisionJNI.new_btPrimitiveTriangle(), true);
    }

    public void buildTriPlane() {
        CollisionJNI.btPrimitiveTriangle_buildTriPlane(this.swigCPtr, this);
    }

    public boolean overlap_test_conservative(btPrimitiveTriangle btprimitivetriangle) {
        return CollisionJNI.btPrimitiveTriangle_overlap_test_conservative(this.swigCPtr, this, getCPtr(btprimitivetriangle), btprimitivetriangle);
    }

    public void get_edge_plane(int i, btVector4 btvector4) {
        CollisionJNI.btPrimitiveTriangle_get_edge_plane(this.swigCPtr, this, i, btVector4.getCPtr(btvector4), btvector4);
    }

    public void applyTransform(Matrix4 matrix4) {
        CollisionJNI.btPrimitiveTriangle_applyTransform(this.swigCPtr, this, matrix4);
    }

    public int clip_triangle(btPrimitiveTriangle btprimitivetriangle, btVector3 btvector3) {
        return CollisionJNI.btPrimitiveTriangle_clip_triangle(this.swigCPtr, this, getCPtr(btprimitivetriangle), btprimitivetriangle, btVector3.getCPtr(btvector3), btvector3);
    }

    public boolean find_triangle_collision_clip_method(btPrimitiveTriangle btprimitivetriangle, GIM_TRIANGLE_CONTACT gim_triangle_contact) {
        return CollisionJNI.btPrimitiveTriangle_find_triangle_collision_clip_method(this.swigCPtr, this, getCPtr(btprimitivetriangle), btprimitivetriangle, GIM_TRIANGLE_CONTACT.getCPtr(gim_triangle_contact), gim_triangle_contact);
    }
}
