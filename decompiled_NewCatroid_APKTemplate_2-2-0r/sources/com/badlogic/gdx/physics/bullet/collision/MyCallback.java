package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class MyCallback extends btTriangleRaycastCallback {
    private long swigCPtr;

    protected MyCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.MyCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public MyCallback(long j, boolean z) {
        this("MyCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleRaycastCallback, com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.MyCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(MyCallback myCallback) {
        if (myCallback == null) {
            return 0L;
        }
        return myCallback.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleRaycastCallback, com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleRaycastCallback, com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_MyCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setIgnorePart(int i) {
        CollisionJNI.MyCallback_ignorePart_set(this.swigCPtr, this, i);
    }

    public int getIgnorePart() {
        return CollisionJNI.MyCallback_ignorePart_get(this.swigCPtr, this);
    }

    public void setIgnoreTriangleIndex(int i) {
        CollisionJNI.MyCallback_ignoreTriangleIndex_set(this.swigCPtr, this, i);
    }

    public int getIgnoreTriangleIndex() {
        return CollisionJNI.MyCallback_ignoreTriangleIndex_get(this.swigCPtr, this);
    }

    public MyCallback(Vector3 vector3, Vector3 vector4, int i, int i2) {
        this(CollisionJNI.new_MyCallback(vector3, vector4, i, i2), true);
    }
}
