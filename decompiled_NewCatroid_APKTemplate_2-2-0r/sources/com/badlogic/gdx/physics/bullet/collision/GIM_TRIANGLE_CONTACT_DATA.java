package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_TRIANGLE_CONTACT_DATA extends BulletBase {
    private long swigCPtr;

    protected GIM_TRIANGLE_CONTACT_DATA(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_TRIANGLE_CONTACT_DATA(long j, boolean z) {
        this("GIM_TRIANGLE_CONTACT_DATA", j, z);
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

    public static long getCPtr(GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data) {
        if (gim_triangle_contact_data == null) {
            return 0L;
        }
        return gim_triangle_contact_data.swigCPtr;
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
                CollisionJNI.delete_GIM_TRIANGLE_CONTACT_DATA(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPenetration_depth(float f) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_penetration_depth_set(this.swigCPtr, this, f);
    }

    public float getPenetration_depth() {
        return CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_penetration_depth_get(this.swigCPtr, this);
    }

    public void setPoint_count(long j) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_point_count_set(this.swigCPtr, this, j);
    }

    public long getPoint_count() {
        return CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_point_count_get(this.swigCPtr, this);
    }

    public void setSeparating_normal(btVector4 btvector4) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_separating_normal_set(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4);
    }

    public btVector4 getSeparating_normal() {
        long jGIM_TRIANGLE_CONTACT_DATA_separating_normal_get = CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_separating_normal_get(this.swigCPtr, this);
        if (jGIM_TRIANGLE_CONTACT_DATA_separating_normal_get == 0) {
            return null;
        }
        return new btVector4(jGIM_TRIANGLE_CONTACT_DATA_separating_normal_get, false);
    }

    public void setPoints(btVector3 btvector3) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_points_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getPoints() {
        long jGIM_TRIANGLE_CONTACT_DATA_points_get = CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_points_get(this.swigCPtr, this);
        if (jGIM_TRIANGLE_CONTACT_DATA_points_get == 0) {
            return null;
        }
        return new btVector3(jGIM_TRIANGLE_CONTACT_DATA_points_get, false);
    }

    public void copy_from(GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_copy_from(this.swigCPtr, this, getCPtr(gim_triangle_contact_data), gim_triangle_contact_data);
    }

    public GIM_TRIANGLE_CONTACT_DATA() {
        this(CollisionJNI.new_GIM_TRIANGLE_CONTACT_DATA__SWIG_0(), true);
    }

    public GIM_TRIANGLE_CONTACT_DATA(GIM_TRIANGLE_CONTACT_DATA gim_triangle_contact_data) {
        this(CollisionJNI.new_GIM_TRIANGLE_CONTACT_DATA__SWIG_1(getCPtr(gim_triangle_contact_data), gim_triangle_contact_data), true);
    }

    public void merge_points(btVector4 btvector4, float f, btVector3 btvector3, long j) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_DATA_merge_points(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4, f, btVector3.getCPtr(btvector3), btvector3, j);
    }
}
