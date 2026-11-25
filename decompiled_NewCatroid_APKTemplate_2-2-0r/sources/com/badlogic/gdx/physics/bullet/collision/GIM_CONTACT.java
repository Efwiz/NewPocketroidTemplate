package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_CONTACT extends BulletBase {
    private long swigCPtr;

    protected GIM_CONTACT(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_CONTACT(long j, boolean z) {
        this("GIM_CONTACT", j, z);
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

    public static long getCPtr(GIM_CONTACT gim_contact) {
        if (gim_contact == null) {
            return 0L;
        }
        return gim_contact.swigCPtr;
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
                CollisionJNI.delete_GIM_CONTACT(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPoint(btVector3 btvector3) {
        CollisionJNI.GIM_CONTACT_point_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getPoint() {
        long jGIM_CONTACT_point_get = CollisionJNI.GIM_CONTACT_point_get(this.swigCPtr, this);
        if (jGIM_CONTACT_point_get == 0) {
            return null;
        }
        return new btVector3(jGIM_CONTACT_point_get, false);
    }

    public void setNormal(btVector3 btvector3) {
        CollisionJNI.GIM_CONTACT_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getNormal() {
        long jGIM_CONTACT_normal_get = CollisionJNI.GIM_CONTACT_normal_get(this.swigCPtr, this);
        if (jGIM_CONTACT_normal_get == 0) {
            return null;
        }
        return new btVector3(jGIM_CONTACT_normal_get, false);
    }

    public void setDepth(float f) {
        CollisionJNI.GIM_CONTACT_depth_set(this.swigCPtr, this, f);
    }

    public float getDepth() {
        return CollisionJNI.GIM_CONTACT_depth_get(this.swigCPtr, this);
    }

    public void setDistance(float f) {
        CollisionJNI.GIM_CONTACT_distance_set(this.swigCPtr, this, f);
    }

    public float getDistance() {
        return CollisionJNI.GIM_CONTACT_distance_get(this.swigCPtr, this);
    }

    public void setFeature1(int i) {
        CollisionJNI.GIM_CONTACT_feature1_set(this.swigCPtr, this, i);
    }

    public int getFeature1() {
        return CollisionJNI.GIM_CONTACT_feature1_get(this.swigCPtr, this);
    }

    public void setFeature2(int i) {
        CollisionJNI.GIM_CONTACT_feature2_set(this.swigCPtr, this, i);
    }

    public int getFeature2() {
        return CollisionJNI.GIM_CONTACT_feature2_get(this.swigCPtr, this);
    }

    public GIM_CONTACT() {
        this(CollisionJNI.new_GIM_CONTACT__SWIG_0(), true);
    }

    public GIM_CONTACT(GIM_CONTACT gim_contact) {
        this(CollisionJNI.new_GIM_CONTACT__SWIG_1(getCPtr(gim_contact), gim_contact), true);
    }

    public GIM_CONTACT(Vector3 vector3, Vector3 vector4, float f, int i, int i2) {
        this(CollisionJNI.new_GIM_CONTACT__SWIG_2(vector3, vector4, f, i, i2), true);
    }

    public long calc_key_contact() {
        return CollisionJNI.GIM_CONTACT_calc_key_contact(this.swigCPtr, this);
    }

    public void interpolate_normals(btVector3 btvector3, int i) {
        CollisionJNI.GIM_CONTACT_interpolate_normals(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i);
    }
}
