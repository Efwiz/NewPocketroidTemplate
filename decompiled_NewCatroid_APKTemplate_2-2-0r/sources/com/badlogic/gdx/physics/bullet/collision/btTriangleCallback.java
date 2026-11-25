package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleCallback extends BulletBase {
    private long swigCPtr;

    protected btTriangleCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTriangleCallback(long j, boolean z) {
        this("btTriangleCallback", j, z);
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

    public static long getCPtr(btTriangleCallback bttrianglecallback) {
        if (bttrianglecallback == null) {
            return 0L;
        }
        return bttrianglecallback.swigCPtr;
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
                CollisionJNI.delete_btTriangleCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        CollisionJNI.btTriangleCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btTriangleCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void processTriangle(btVector3 btvector3, int i, int i2) {
        CollisionJNI.btTriangleCallback_processTriangle(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, i2);
    }

    public btTriangleCallback() {
        this(CollisionJNI.new_btTriangleCallback(), true);
        CollisionJNI.btTriangleCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
