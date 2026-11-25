package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionAlgorithm extends BulletBase {
    private long swigCPtr;

    protected btCollisionAlgorithm(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionAlgorithm(long j, boolean z) {
        this("btCollisionAlgorithm", j, z);
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

    public static long getCPtr(btCollisionAlgorithm btcollisionalgorithm) {
        if (btcollisionalgorithm == null) {
            return 0L;
        }
        return btcollisionalgorithm.swigCPtr;
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
                CollisionJNI.delete_btCollisionAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void processCollision(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btDispatcherInfo btdispatcherinfo, btManifoldResult btmanifoldresult) {
        CollisionJNI.btCollisionAlgorithm_processCollision(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btDispatcherInfo.getCPtr(btdispatcherinfo), btdispatcherinfo, btManifoldResult.getCPtr(btmanifoldresult), btmanifoldresult);
    }

    public float calculateTimeOfImpact(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2, btDispatcherInfo btdispatcherinfo, btManifoldResult btmanifoldresult) {
        return CollisionJNI.btCollisionAlgorithm_calculateTimeOfImpact(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, btDispatcherInfo.getCPtr(btdispatcherinfo), btdispatcherinfo, btManifoldResult.getCPtr(btmanifoldresult), btmanifoldresult);
    }

    public void getAllContactManifolds(btPersistentManifoldArray btpersistentmanifoldarray) {
        CollisionJNI.btCollisionAlgorithm_getAllContactManifolds(this.swigCPtr, this, btPersistentManifoldArray.getCPtr(btpersistentmanifoldarray), btpersistentmanifoldarray);
    }
}
