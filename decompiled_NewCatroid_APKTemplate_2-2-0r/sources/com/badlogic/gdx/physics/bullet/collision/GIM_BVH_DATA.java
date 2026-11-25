package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_BVH_DATA extends BulletBase {
    private long swigCPtr;

    protected GIM_BVH_DATA(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_BVH_DATA(long j, boolean z) {
        this("GIM_BVH_DATA", j, z);
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

    public static long getCPtr(GIM_BVH_DATA gim_bvh_data) {
        if (gim_bvh_data == null) {
            return 0L;
        }
        return gim_bvh_data.swigCPtr;
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
                CollisionJNI.delete_GIM_BVH_DATA(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBound(btAABB btaabb) {
        CollisionJNI.GIM_BVH_DATA_bound_set(this.swigCPtr, this, btAABB.getCPtr(btaabb), btaabb);
    }

    public btAABB getBound() {
        long jGIM_BVH_DATA_bound_get = CollisionJNI.GIM_BVH_DATA_bound_get(this.swigCPtr, this);
        if (jGIM_BVH_DATA_bound_get == 0) {
            return null;
        }
        return new btAABB(jGIM_BVH_DATA_bound_get, false);
    }

    public void setData(int i) {
        CollisionJNI.GIM_BVH_DATA_data_set(this.swigCPtr, this, i);
    }

    public int getData() {
        return CollisionJNI.GIM_BVH_DATA_data_get(this.swigCPtr, this);
    }

    public GIM_BVH_DATA() {
        this(CollisionJNI.new_GIM_BVH_DATA(), true);
    }
}
