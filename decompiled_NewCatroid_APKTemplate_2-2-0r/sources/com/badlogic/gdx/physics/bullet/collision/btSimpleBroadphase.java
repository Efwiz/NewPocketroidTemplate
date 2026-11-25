package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btSimpleBroadphase extends btBroadphaseInterface {
    private long swigCPtr;

    protected btSimpleBroadphase(String str, long j, boolean z) {
        super(str, CollisionJNI.btSimpleBroadphase_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSimpleBroadphase(long j, boolean z) {
        this("btSimpleBroadphase", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btSimpleBroadphase_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSimpleBroadphase btsimplebroadphase) {
        if (btsimplebroadphase == null) {
            return 0L;
        }
        return btsimplebroadphase.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btSimpleBroadphase(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSimpleBroadphase(int i, btOverlappingPairCache btoverlappingpaircache) {
        this(CollisionJNI.new_btSimpleBroadphase__SWIG_0(i, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache), true);
    }

    public btSimpleBroadphase(int i) {
        this(CollisionJNI.new_btSimpleBroadphase__SWIG_1(i), true);
    }

    public btSimpleBroadphase() {
        this(CollisionJNI.new_btSimpleBroadphase__SWIG_2(), true);
    }

    public static boolean aabbOverlap(btSimpleBroadphaseProxy btsimplebroadphaseproxy, btSimpleBroadphaseProxy btsimplebroadphaseproxy2) {
        return CollisionJNI.btSimpleBroadphase_aabbOverlap(btSimpleBroadphaseProxy.getCPtr(btsimplebroadphaseproxy), btsimplebroadphaseproxy, btSimpleBroadphaseProxy.getCPtr(btsimplebroadphaseproxy2), btsimplebroadphaseproxy2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.btSimpleBroadphase_rayTest__SWIG_0(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5, vector6);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5) {
        CollisionJNI.btSimpleBroadphase_rayTest__SWIG_1(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback) {
        CollisionJNI.btSimpleBroadphase_rayTest__SWIG_2(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback);
    }

    public boolean testAabbOverlap(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        return CollisionJNI.btSimpleBroadphase_testAabbOverlap(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }
}
