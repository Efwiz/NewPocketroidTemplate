package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_TRIANGLE_CONTACT extends BulletBase {
    private long swigCPtr;

    protected GIM_TRIANGLE_CONTACT(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_TRIANGLE_CONTACT(long j, boolean z) {
        this("GIM_TRIANGLE_CONTACT", j, z);
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

    public static long getCPtr(GIM_TRIANGLE_CONTACT gim_triangle_contact) {
        if (gim_triangle_contact == null) {
            return 0L;
        }
        return gim_triangle_contact.swigCPtr;
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
                CollisionJNI.delete_GIM_TRIANGLE_CONTACT(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPenetration_depth(float f) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_penetration_depth_set(this.swigCPtr, this, f);
    }

    public float getPenetration_depth() {
        return CollisionJNI.GIM_TRIANGLE_CONTACT_penetration_depth_get(this.swigCPtr, this);
    }

    public void setPoint_count(int i) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_point_count_set(this.swigCPtr, this, i);
    }

    public int getPoint_count() {
        return CollisionJNI.GIM_TRIANGLE_CONTACT_point_count_get(this.swigCPtr, this);
    }

    public void setSeparating_normal(btVector4 btvector4) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_separating_normal_set(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4);
    }

    public btVector4 getSeparating_normal() {
        long jGIM_TRIANGLE_CONTACT_separating_normal_get = CollisionJNI.GIM_TRIANGLE_CONTACT_separating_normal_get(this.swigCPtr, this);
        if (jGIM_TRIANGLE_CONTACT_separating_normal_get == 0) {
            return null;
        }
        return new btVector4(jGIM_TRIANGLE_CONTACT_separating_normal_get, false);
    }

    public void setPoints(btVector3 btvector3) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_points_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getPoints() {
        long jGIM_TRIANGLE_CONTACT_points_get = CollisionJNI.GIM_TRIANGLE_CONTACT_points_get(this.swigCPtr, this);
        if (jGIM_TRIANGLE_CONTACT_points_get == 0) {
            return null;
        }
        return new btVector3(jGIM_TRIANGLE_CONTACT_points_get, false);
    }

    public void copy_from(GIM_TRIANGLE_CONTACT gim_triangle_contact) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_copy_from(this.swigCPtr, this, getCPtr(gim_triangle_contact), gim_triangle_contact);
    }

    public GIM_TRIANGLE_CONTACT() {
        this(CollisionJNI.new_GIM_TRIANGLE_CONTACT__SWIG_0(), true);
    }

    public GIM_TRIANGLE_CONTACT(GIM_TRIANGLE_CONTACT gim_triangle_contact) {
        this(CollisionJNI.new_GIM_TRIANGLE_CONTACT__SWIG_1(getCPtr(gim_triangle_contact), gim_triangle_contact), true);
    }

    public void merge_points(btVector4 btvector4, float f, btVector3 btvector3, int i) {
        CollisionJNI.GIM_TRIANGLE_CONTACT_merge_points(this.swigCPtr, this, btVector4.getCPtr(btvector4), btvector4, f, btVector3.getCPtr(btvector3), btvector3, i);
    }
}
