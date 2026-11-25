package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btInternalTriangleIndexCallback extends BulletBase {
    private long swigCPtr;

    protected btInternalTriangleIndexCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btInternalTriangleIndexCallback(long j, boolean z) {
        this("btInternalTriangleIndexCallback", j, z);
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

    public static long getCPtr(btInternalTriangleIndexCallback btinternaltriangleindexcallback) {
        if (btinternaltriangleindexcallback == null) {
            return 0L;
        }
        return btinternaltriangleindexcallback.swigCPtr;
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
                CollisionJNI.delete_btInternalTriangleIndexCallback(this.swigCPtr);
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
        CollisionJNI.btInternalTriangleIndexCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btInternalTriangleIndexCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void internalProcessTriangleIndex(btVector3 btvector3, int i, int i2) {
        CollisionJNI.btInternalTriangleIndexCallback_internalProcessTriangleIndex(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, i, i2);
    }

    public btInternalTriangleIndexCallback() {
        this(CollisionJNI.new_btInternalTriangleIndexCallback(), true);
        CollisionJNI.btInternalTriangleIndexCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
