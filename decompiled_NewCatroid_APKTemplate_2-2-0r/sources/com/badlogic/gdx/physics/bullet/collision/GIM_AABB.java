package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_AABB extends BulletBase {
    private long swigCPtr;

    protected GIM_AABB(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_AABB(long j, boolean z) {
        this("GIM_AABB", j, z);
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

    public static long getCPtr(GIM_AABB gim_aabb) {
        if (gim_aabb == null) {
            return 0L;
        }
        return gim_aabb.swigCPtr;
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
                CollisionJNI.delete_GIM_AABB(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setMin(btVector3 btvector3) {
        CollisionJNI.GIM_AABB_min_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMin() {
        long jGIM_AABB_min_get = CollisionJNI.GIM_AABB_min_get(this.swigCPtr, this);
        if (jGIM_AABB_min_get == 0) {
            return null;
        }
        return new btVector3(jGIM_AABB_min_get, false);
    }

    public void setMax(btVector3 btvector3) {
        CollisionJNI.GIM_AABB_max_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMax() {
        long jGIM_AABB_max_get = CollisionJNI.GIM_AABB_max_get(this.swigCPtr, this);
        if (jGIM_AABB_max_get == 0) {
            return null;
        }
        return new btVector3(jGIM_AABB_max_get, false);
    }

    public GIM_AABB() {
        this(CollisionJNI.new_GIM_AABB__SWIG_0(), true);
    }

    public GIM_AABB(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        this(CollisionJNI.new_GIM_AABB__SWIG_1(vector3, vector4, vector5), true);
    }

    public GIM_AABB(Vector3 vector3, Vector3 vector4, Vector3 vector5, SWIGTYPE_p_GREAL sWIGTYPE_p_GREAL) {
        this(CollisionJNI.new_GIM_AABB__SWIG_2(vector3, vector4, vector5, SWIGTYPE_p_GREAL.getCPtr(sWIGTYPE_p_GREAL)), true);
    }

    public GIM_AABB(GIM_AABB gim_aabb) {
        this(CollisionJNI.new_GIM_AABB__SWIG_3(getCPtr(gim_aabb), gim_aabb), true);
    }

    public GIM_AABB(GIM_AABB gim_aabb, float f) {
        this(CollisionJNI.new_GIM_AABB__SWIG_4(getCPtr(gim_aabb), gim_aabb, f), true);
    }

    public void invalidate() {
        CollisionJNI.GIM_AABB_invalidate(this.swigCPtr, this);
    }

    public void increment_margin(float f) {
        CollisionJNI.GIM_AABB_increment_margin(this.swigCPtr, this, f);
    }

    public void copy_with_margin(GIM_AABB gim_aabb, float f) {
        CollisionJNI.GIM_AABB_copy_with_margin(this.swigCPtr, this, getCPtr(gim_aabb), gim_aabb, f);
    }

    public void appy_transform(Matrix4 matrix4) {
        CollisionJNI.GIM_AABB_appy_transform(this.swigCPtr, this, matrix4);
    }

    public void merge(GIM_AABB gim_aabb) {
        CollisionJNI.GIM_AABB_merge(this.swigCPtr, this, getCPtr(gim_aabb), gim_aabb);
    }

    public void get_center_extend(Vector3 vector3, Vector3 vector4) {
        CollisionJNI.GIM_AABB_get_center_extend(this.swigCPtr, this, vector3, vector4);
    }

    public void find_intersection(GIM_AABB gim_aabb, GIM_AABB gim_aabb2) {
        CollisionJNI.GIM_AABB_find_intersection(this.swigCPtr, this, getCPtr(gim_aabb), gim_aabb, getCPtr(gim_aabb2), gim_aabb2);
    }

    public boolean has_collision(GIM_AABB gim_aabb) {
        return CollisionJNI.GIM_AABB_has_collision(this.swigCPtr, this, getCPtr(gim_aabb), gim_aabb);
    }

    public boolean collide_ray(Vector3 vector3, Vector3 vector4) {
        return CollisionJNI.GIM_AABB_collide_ray(this.swigCPtr, this, vector3, vector4);
    }

    public void projection_interval(Vector3 vector3, SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2) {
        CollisionJNI.GIM_AABB_projection_interval(this.swigCPtr, this, vector3, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2));
    }

    public int plane_classify(btVector4 btvector4) {
        return CollisionJNI.GIM_AABB_plane_classify(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4);
    }

    public boolean overlapping_trans_conservative(GIM_AABB gim_aabb, Matrix4 matrix4) {
        return CollisionJNI.GIM_AABB_overlapping_trans_conservative(this.swigCPtr, this, getCPtr(gim_aabb), gim_aabb, matrix4);
    }

    public boolean overlapping_trans_cache(GIM_AABB gim_aabb, GIM_BOX_BOX_TRANSFORM_CACHE gim_box_box_transform_cache, boolean z) {
        return CollisionJNI.GIM_AABB_overlapping_trans_cache(this.swigCPtr, this, getCPtr(gim_aabb), gim_aabb, GIM_BOX_BOX_TRANSFORM_CACHE.getCPtr(gim_box_box_transform_cache), gim_box_box_transform_cache, z);
    }

    public boolean collide_plane(btVector4 btvector4) {
        return CollisionJNI.GIM_AABB_collide_plane(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4);
    }

    public boolean collide_triangle_exact(Vector3 vector3, Vector3 vector4, Vector3 vector5, btVector4 btvector4) {
        return CollisionJNI.GIM_AABB_collide_triangle_exact(this.swigCPtr, this, vector3, vector4, vector5, btVector4.getCPtr(btvector4), btvector4);
    }
}
