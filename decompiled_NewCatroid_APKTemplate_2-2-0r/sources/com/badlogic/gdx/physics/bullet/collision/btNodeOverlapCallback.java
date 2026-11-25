package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btNodeOverlapCallback extends BulletBase {
    private long swigCPtr;

    protected btNodeOverlapCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btNodeOverlapCallback(long j, boolean z) {
        this("btNodeOverlapCallback", j, z);
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

    public static long getCPtr(btNodeOverlapCallback btnodeoverlapcallback) {
        if (btnodeoverlapcallback == null) {
            return 0L;
        }
        return btnodeoverlapcallback.swigCPtr;
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
                CollisionJNI.delete_btNodeOverlapCallback(this.swigCPtr);
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
        CollisionJNI.btNodeOverlapCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        CollisionJNI.btNodeOverlapCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void processNode(int i, int i2) {
        CollisionJNI.btNodeOverlapCallback_processNode(this.swigCPtr, this, i, i2);
    }

    public btNodeOverlapCallback() {
        this(CollisionJNI.new_btNodeOverlapCallback(), true);
        CollisionJNI.btNodeOverlapCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
