package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;

/* JADX INFO: loaded from: classes8.dex */
public class btAABB extends BulletBase {
    private long swigCPtr;

    protected btAABB(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btAABB(long j, boolean z) {
        this("btAABB", j, z);
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

    public static long getCPtr(btAABB btaabb) {
        if (btaabb == null) {
            return 0L;
        }
        return btaabb.swigCPtr;
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
                CollisionJNI.delete_btAABB(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setMin(btVector3 btvector3) {
        CollisionJNI.btAABB_min_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMin() {
        long jBtAABB_min_get = CollisionJNI.btAABB_min_get(this.swigCPtr, this);
        if (jBtAABB_min_get == 0) {
            return null;
        }
        return new btVector3(jBtAABB_min_get, false);
    }

    public void setMax(btVector3 btvector3) {
        CollisionJNI.btAABB_max_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMax() {
        long jBtAABB_max_get = CollisionJNI.btAABB_max_get(this.swigCPtr, this);
        if (jBtAABB_max_get == 0) {
            return null;
        }
        return new btVector3(jBtAABB_max_get, false);
    }

    public btAABB() {
        this(CollisionJNI.new_btAABB__SWIG_0(), true);
    }

    public btAABB(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        this(CollisionJNI.new_btAABB__SWIG_1(vector3, vector4, vector5), true);
    }

    public btAABB(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f) {
        this(CollisionJNI.new_btAABB__SWIG_2(vector3, vector4, vector5, f), true);
    }

    public btAABB(btAABB btaabb) {
        this(CollisionJNI.new_btAABB__SWIG_3(getCPtr(btaabb), btaabb), true);
    }

    public btAABB(btAABB btaabb, float f) {
        this(CollisionJNI.new_btAABB__SWIG_4(getCPtr(btaabb), btaabb, f), true);
    }

    public void invalidate() {
        CollisionJNI.btAABB_invalidate(this.swigCPtr, this);
    }

    public void increment_margin(float f) {
        CollisionJNI.btAABB_increment_margin(this.swigCPtr, this, f);
    }

    public void copy_with_margin(btAABB btaabb, float f) {
        CollisionJNI.btAABB_copy_with_margin(this.swigCPtr, this, getCPtr(btaabb), btaabb, f);
    }

    public void appy_transform(Matrix4 matrix4) {
        CollisionJNI.btAABB_appy_transform(this.swigCPtr, this, matrix4);
    }

    public void appy_transform_trans_cache(BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache) {
        CollisionJNI.btAABB_appy_transform_trans_cache(this.swigCPtr, this, BT_BOX_BOX_TRANSFORM_CACHE.getCPtr(bt_box_box_transform_cache), bt_box_box_transform_cache);
    }

    public void merge(btAABB btaabb) {
        CollisionJNI.btAABB_merge(this.swigCPtr, this, getCPtr(btaabb), btaabb);
    }

    public void get_center_extend(Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btAABB_get_center_extend(this.swigCPtr, this, vector3, vector4);
    }

    public void find_intersection(btAABB btaabb, btAABB btaabb2) {
        CollisionJNI.btAABB_find_intersection(this.swigCPtr, this, getCPtr(btaabb), btaabb, getCPtr(btaabb2), btaabb2);
    }

    public boolean has_collision(btAABB btaabb) {
        return CollisionJNI.btAABB_has_collision(this.swigCPtr, this, getCPtr(btaabb), btaabb);
    }

    public boolean collide_ray(Vector3 vector3, Vector3 vector4) {
        return CollisionJNI.btAABB_collide_ray(this.swigCPtr, this, vector3, vector4);
    }

    public void projection_interval(Vector3 vector3, SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2) {
        CollisionJNI.btAABB_projection_interval(this.swigCPtr, this, vector3, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2));
    }

    public int plane_classify(btVector4 btvector4) {
        return CollisionJNI.btAABB_plane_classify(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4);
    }

    public boolean overlapping_trans_conservative(btAABB btaabb, Matrix4 matrix4) {
        return CollisionJNI.btAABB_overlapping_trans_conservative(this.swigCPtr, this, getCPtr(btaabb), btaabb, matrix4);
    }

    public boolean overlapping_trans_conservative2(btAABB btaabb, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache) {
        return CollisionJNI.btAABB_overlapping_trans_conservative2(this.swigCPtr, this, getCPtr(btaabb), btaabb, BT_BOX_BOX_TRANSFORM_CACHE.getCPtr(bt_box_box_transform_cache), bt_box_box_transform_cache);
    }

    public boolean overlapping_trans_cache(btAABB btaabb, BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache, boolean z) {
        return CollisionJNI.btAABB_overlapping_trans_cache(this.swigCPtr, this, getCPtr(btaabb), btaabb, BT_BOX_BOX_TRANSFORM_CACHE.getCPtr(bt_box_box_transform_cache), bt_box_box_transform_cache, z);
    }

    public boolean collide_plane(btVector4 btvector4) {
        return CollisionJNI.btAABB_collide_plane(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4);
    }

    public boolean collide_triangle_exact(Vector3 vector3, Vector3 vector4, Vector3 vector5, btVector4 btvector4) {
        return CollisionJNI.btAABB_collide_triangle_exact(this.swigCPtr, this, vector3, vector4, vector5, btVector4.getCPtr(btvector4), btvector4);
    }
}
