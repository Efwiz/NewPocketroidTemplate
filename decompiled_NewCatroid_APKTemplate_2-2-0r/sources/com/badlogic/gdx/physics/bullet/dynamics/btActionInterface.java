package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;

/* JADX INFO: loaded from: classes8.dex */
public class btActionInterface extends BulletBase {
    private long swigCPtr;

    protected btActionInterface(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btActionInterface(long j, boolean z) {
        this("btActionInterface", j, z);
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

    public static long getCPtr(btActionInterface btactioninterface) {
        if (btactioninterface == null) {
            return 0L;
        }
        return btactioninterface.swigCPtr;
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
                DynamicsJNI.delete_btActionInterface(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void updateAction(btCollisionWorld btcollisionworld, float f) {
        DynamicsJNI.btActionInterface_updateAction(this.swigCPtr, this, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld, f);
    }

    public void debugDraw(btIDebugDraw btidebugdraw) {
        DynamicsJNI.btActionInterface_debugDraw(this.swigCPtr, this, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }
}
