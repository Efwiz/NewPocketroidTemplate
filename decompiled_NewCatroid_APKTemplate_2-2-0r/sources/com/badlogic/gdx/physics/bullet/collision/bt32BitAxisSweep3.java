package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class bt32BitAxisSweep3 extends btAxisSweep3InternalInt {
    private long swigCPtr;

    protected bt32BitAxisSweep3(String str, long j, boolean z) {
        super(str, CollisionJNI.bt32BitAxisSweep3_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public bt32BitAxisSweep3(long j, boolean z) {
        this("bt32BitAxisSweep3", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btAxisSweep3InternalInt, com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.bt32BitAxisSweep3_SWIGUpcast(j), z);
    }

    public static long getCPtr(bt32BitAxisSweep3 bt32bitaxissweep3) {
        if (bt32bitaxissweep3 == null) {
            return 0L;
        }
        return bt32bitaxissweep3.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btAxisSweep3InternalInt, com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btAxisSweep3InternalInt, com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_bt32BitAxisSweep3(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public bt32BitAxisSweep3(Vector3 vector3, Vector3 vector4, long j, btOverlappingPairCache btoverlappingpaircache, boolean z) {
        this(CollisionJNI.new_bt32BitAxisSweep3__SWIG_0(vector3, vector4, j, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache, z), true);
    }

    public bt32BitAxisSweep3(Vector3 vector3, Vector3 vector4, long j, btOverlappingPairCache btoverlappingpaircache) {
        this(CollisionJNI.new_bt32BitAxisSweep3__SWIG_1(vector3, vector4, j, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache), true);
    }

    public bt32BitAxisSweep3(Vector3 vector3, Vector3 vector4, long j) {
        this(CollisionJNI.new_bt32BitAxisSweep3__SWIG_2(vector3, vector4, j), true);
    }

    public bt32BitAxisSweep3(Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_bt32BitAxisSweep3__SWIG_3(vector3, vector4), true);
    }
}
