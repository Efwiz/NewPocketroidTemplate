package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btSimpleBroadphaseProxy extends btBroadphaseProxy {
    private long swigCPtr;

    protected btSimpleBroadphaseProxy(String str, long j, boolean z) {
        super(str, CollisionJNI.btSimpleBroadphaseProxy_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSimpleBroadphaseProxy(long j, boolean z) {
        this("btSimpleBroadphaseProxy", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btSimpleBroadphaseProxy_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSimpleBroadphaseProxy btsimplebroadphaseproxy) {
        if (btsimplebroadphaseproxy == null) {
            return 0L;
        }
        return btsimplebroadphaseproxy.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btSimpleBroadphaseProxy(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setNextFree(int i) {
        CollisionJNI.btSimpleBroadphaseProxy_nextFree_set(this.swigCPtr, this, i);
    }

    public int getNextFree() {
        return CollisionJNI.btSimpleBroadphaseProxy_nextFree_get(this.swigCPtr, this);
    }

    public btSimpleBroadphaseProxy() {
        this(CollisionJNI.new_btSimpleBroadphaseProxy__SWIG_0(), true);
    }

    public btSimpleBroadphaseProxy(Vector3 vector3, Vector3 vector4, int i, long j, int i2, int i3) {
        this(CollisionJNI.new_btSimpleBroadphaseProxy__SWIG_1(vector3, vector4, i, j, i2, i3), true);
    }

    public void SetNextFree(int i) {
        CollisionJNI.btSimpleBroadphaseProxy_SetNextFree(this.swigCPtr, this, i);
    }

    public int GetNextFree() {
        return CollisionJNI.btSimpleBroadphaseProxy_GetNextFree(this.swigCPtr, this);
    }
}
