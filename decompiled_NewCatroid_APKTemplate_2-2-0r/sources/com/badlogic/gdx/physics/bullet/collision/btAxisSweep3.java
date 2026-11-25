package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btAxisSweep3 extends btAxisSweep3InternalShort {
    private long swigCPtr;

    protected btAxisSweep3(String str, long j, boolean z) {
        super(str, CollisionJNI.btAxisSweep3_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btAxisSweep3(long j, boolean z) {
        this("btAxisSweep3", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btAxisSweep3InternalShort, com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btAxisSweep3_SWIGUpcast(j), z);
    }

    public static long getCPtr(btAxisSweep3 btaxissweep3) {
        if (btaxissweep3 == null) {
            return 0L;
        }
        return btaxissweep3.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btAxisSweep3InternalShort, com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btAxisSweep3InternalShort, com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btAxisSweep3(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btAxisSweep3(Vector3 vector3, Vector3 vector4, int i, btOverlappingPairCache btoverlappingpaircache, boolean z) {
        this(CollisionJNI.new_btAxisSweep3__SWIG_0(vector3, vector4, i, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache, z), true);
    }

    public btAxisSweep3(Vector3 vector3, Vector3 vector4, int i, btOverlappingPairCache btoverlappingpaircache) {
        this(CollisionJNI.new_btAxisSweep3__SWIG_1(vector3, vector4, i, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache), true);
    }

    public btAxisSweep3(Vector3 vector3, Vector3 vector4, int i) {
        this(CollisionJNI.new_btAxisSweep3__SWIG_2(vector3, vector4, i), true);
    }

    public btAxisSweep3(Vector3 vector3, Vector3 vector4) {
        this(CollisionJNI.new_btAxisSweep3__SWIG_3(vector3, vector4), true);
    }
}
