package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btOverlapCallback extends BulletBase {
    private long swigCPtr;

    protected btOverlapCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btOverlapCallback(long j, boolean z) {
        this("btOverlapCallback", j, z);
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

    public static long getCPtr(btOverlapCallback btoverlapcallback) {
        if (btoverlapcallback == null) {
            return 0L;
        }
        return btoverlapcallback.swigCPtr;
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
                CollisionJNI.delete_btOverlapCallback(this.swigCPtr);
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
        CollisionJNI.btOverlapCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btOverlapCallback_change_ownership(this, this.swigCPtr, true);
    }

    public boolean processOverlap(btBroadphasePair btbroadphasepair) {
        return CollisionJNI.btOverlapCallback_processOverlap(this.swigCPtr, this, btbroadphasepair);
    }

    public btOverlapCallback() {
        this(CollisionJNI.new_btOverlapCallback(), true);
        CollisionJNI.btOverlapCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
