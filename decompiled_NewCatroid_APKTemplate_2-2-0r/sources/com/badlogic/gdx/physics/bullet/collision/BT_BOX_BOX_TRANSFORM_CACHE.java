package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class BT_BOX_BOX_TRANSFORM_CACHE extends BulletBase {
    private long swigCPtr;

    protected BT_BOX_BOX_TRANSFORM_CACHE(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public BT_BOX_BOX_TRANSFORM_CACHE(long j, boolean z) {
        this("BT_BOX_BOX_TRANSFORM_CACHE", j, z);
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

    public static long getCPtr(BT_BOX_BOX_TRANSFORM_CACHE bt_box_box_transform_cache) {
        if (bt_box_box_transform_cache == null) {
            return 0L;
        }
        return bt_box_box_transform_cache.swigCPtr;
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
                CollisionJNI.delete_BT_BOX_BOX_TRANSFORM_CACHE(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setT1to0(btVector3 btvector3) {
        CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_T1to0_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getT1to0() {
        long jBT_BOX_BOX_TRANSFORM_CACHE_T1to0_get = CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_T1to0_get(this.swigCPtr, this);
        if (jBT_BOX_BOX_TRANSFORM_CACHE_T1to0_get == 0) {
            return null;
        }
        return new btVector3(jBT_BOX_BOX_TRANSFORM_CACHE_T1to0_get, false);
    }

    public void setR1to0(btMatrix3x3 btmatrix3x3) {
        CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_R1to0_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
    }

    public btMatrix3x3 getR1to0() {
        long jBT_BOX_BOX_TRANSFORM_CACHE_R1to0_get = CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_R1to0_get(this.swigCPtr, this);
        if (jBT_BOX_BOX_TRANSFORM_CACHE_R1to0_get == 0) {
            return null;
        }
        return new btMatrix3x3(jBT_BOX_BOX_TRANSFORM_CACHE_R1to0_get, false);
    }

    public void setAR(btMatrix3x3 btmatrix3x3) {
        CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_AR_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
    }

    public btMatrix3x3 getAR() {
        long jBT_BOX_BOX_TRANSFORM_CACHE_AR_get = CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_AR_get(this.swigCPtr, this);
        if (jBT_BOX_BOX_TRANSFORM_CACHE_AR_get == 0) {
            return null;
        }
        return new btMatrix3x3(jBT_BOX_BOX_TRANSFORM_CACHE_AR_get, false);
    }

    public void calc_absolute_matrix() {
        CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_calc_absolute_matrix(this.swigCPtr, this);
    }

    public BT_BOX_BOX_TRANSFORM_CACHE() {
        this(CollisionJNI.new_BT_BOX_BOX_TRANSFORM_CACHE(), true);
    }

    public void calc_from_homogenic(Matrix4 matrix4, Matrix4 matrix5) {
        CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_calc_from_homogenic(this.swigCPtr, this, matrix4, matrix5);
    }

    public void calc_from_full_invert(Matrix4 matrix4, Matrix4 matrix5) {
        CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_calc_from_full_invert(this.swigCPtr, this, matrix4, matrix5);
    }

    public Vector3 transform(Vector3 vector3) {
        return CollisionJNI.BT_BOX_BOX_TRANSFORM_CACHE_transform(this.swigCPtr, this, vector3);
    }
}
