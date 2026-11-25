package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class DISTANCE_PLANE_3D_FUNC extends BulletBase {
    private long swigCPtr;

    protected DISTANCE_PLANE_3D_FUNC(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public DISTANCE_PLANE_3D_FUNC(long j, boolean z) {
        this("DISTANCE_PLANE_3D_FUNC", j, z);
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

    public static long getCPtr(DISTANCE_PLANE_3D_FUNC distance_plane_3d_func) {
        if (distance_plane_3d_func == null) {
            return 0L;
        }
        return distance_plane_3d_func.swigCPtr;
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
                CollisionJNI.delete_DISTANCE_PLANE_3D_FUNC(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public DISTANCE_PLANE_3D_FUNC() {
        this(CollisionJNI.new_DISTANCE_PLANE_3D_FUNC(), true);
    }
}
