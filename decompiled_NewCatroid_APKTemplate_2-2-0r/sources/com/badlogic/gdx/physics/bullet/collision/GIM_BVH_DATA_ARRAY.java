package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_BVH_DATA_ARRAY extends btGimBvhDataArray {
    private long swigCPtr;

    protected GIM_BVH_DATA_ARRAY(String str, long j, boolean z) {
        super(str, CollisionJNI.GIM_BVH_DATA_ARRAY_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public GIM_BVH_DATA_ARRAY(long j, boolean z) {
        this("GIM_BVH_DATA_ARRAY", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGimBvhDataArray, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.GIM_BVH_DATA_ARRAY_SWIGUpcast(j), z);
    }

    public static long getCPtr(GIM_BVH_DATA_ARRAY gim_bvh_data_array) {
        if (gim_bvh_data_array == null) {
            return 0L;
        }
        return gim_bvh_data_array.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGimBvhDataArray, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGimBvhDataArray, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_GIM_BVH_DATA_ARRAY(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public GIM_BVH_DATA_ARRAY() {
        this(CollisionJNI.new_GIM_BVH_DATA_ARRAY(), true);
    }
}
