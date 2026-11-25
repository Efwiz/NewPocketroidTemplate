package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_TRIANGLE extends BulletBase {
    private long swigCPtr;

    protected GIM_TRIANGLE(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_TRIANGLE(long j, boolean z) {
        this("GIM_TRIANGLE", j, z);
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

    public static long getCPtr(GIM_TRIANGLE gim_triangle) {
        if (gim_triangle == null) {
            return 0L;
        }
        return gim_triangle.swigCPtr;
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
                CollisionJNI.delete_GIM_TRIANGLE(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setMargin(float f) {
        CollisionJNI.GIM_TRIANGLE_margin_set(this.swigCPtr, this, f);
    }

    public float getMargin() {
        return CollisionJNI.GIM_TRIANGLE_margin_get(this.swigCPtr, this);
    }

    public void setVertices(btVector3 btvector3) {
        CollisionJNI.GIM_TRIANGLE_vertices_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getVertices() {
        long jGIM_TRIANGLE_vertices_get = CollisionJNI.GIM_TRIANGLE_vertices_get(this.swigCPtr, this);
        if (jGIM_TRIANGLE_vertices_get == 0) {
            return null;
        }
        return new btVector3(jGIM_TRIANGLE_vertices_get, false);
    }

    public GIM_TRIANGLE() {
        this(CollisionJNI.new_GIM_TRIANGLE(), true);
    }

    public GIM_AABB get_box() {
        return new GIM_AABB(CollisionJNI.GIM_TRIANGLE_get_box(this.swigCPtr, this), true);
    }

    public void get_normal(Vector3 vector3) {
        CollisionJNI.GIM_TRIANGLE_get_normal(this.swigCPtr, this, vector3);
    }

    public void get_plane(btVector4 btvector4) {
        CollisionJNI.GIM_TRIANGLE_get_plane(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4);
    }

    public void apply_transform(Matrix4 matrix4) {
        CollisionJNI.GIM_TRIANGLE_apply_transform(this.swigCPtr, this, matrix4);
    }

    public void get_edge_plane(long j, Vector3 vector3, btVector4 btvector4) {
        CollisionJNI.GIM_TRIANGLE_get_edge_plane(this.swigCPtr, this, j, vector3, btVector4.getCPtr(btvector4), btvector4);
    }

    public void get_triangle_transform(Matrix4 matrix4) {
        CollisionJNI.GIM_TRIANGLE_get_triangle_transform(this.swigCPtr, this, matrix4);
    }

    public boolean collide_triangle_hard_test(GIM_TRIANGLE gim_triangle, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data) {
        return CollisionJNI.GIM_TRIANGLE_collide_triangle_hard_test(this.swigCPtr, this, getCPtr(gim_triangle), gim_triangle, GIM_TRIANGLE_CONTACT_DATA.getCPtr(gim_triangle_contact_data), gim_triangle_contact_data);
    }

    public boolean collide_triangle(GIM_TRIANGLE gim_triangle, GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data) {
        return CollisionJNI.GIM_TRIANGLE_collide_triangle(this.swigCPtr, this, getCPtr(gim_triangle), gim_triangle, GIM_TRIANGLE_CONTACT_DATA.getCPtr(gim_triangle_contact_data), gim_triangle_contact_data);
    }

    public boolean get_uv_parameters(Vector3 vector3, Vector3 vector4, SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2) {
        return CollisionJNI.GIM_TRIANGLE_get_uv_parameters(this.swigCPtr, this, vector3, vector4, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2));
    }

    public boolean is_point_inside(Vector3 vector3, Vector3 vector4) {
        return CollisionJNI.GIM_TRIANGLE_is_point_inside(this.swigCPtr, this, vector3, vector4);
    }

    public boolean ray_collision(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, SWIGTYPE_p_float sWIGTYPE_p_float, float f) {
        return CollisionJNI.GIM_TRIANGLE_ray_collision__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5, vector6, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), f);
    }

    public boolean ray_collision(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, SWIGTYPE_p_float sWIGTYPE_p_float) {
        return CollisionJNI.GIM_TRIANGLE_ray_collision__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, vector6, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public boolean ray_collision_front_side(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, SWIGTYPE_p_float sWIGTYPE_p_float, float f) {
        return CollisionJNI.GIM_TRIANGLE_ray_collision_front_side__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5, vector6, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), f);
    }

    public boolean ray_collision_front_side(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, SWIGTYPE_p_float sWIGTYPE_p_float) {
        return CollisionJNI.GIM_TRIANGLE_ray_collision_front_side__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, vector6, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }
}
