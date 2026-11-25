package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionAlgorithmConstructionInfo extends BulletBase {
    private long swigCPtr;

    protected btCollisionAlgorithmConstructionInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionAlgorithmConstructionInfo(long j, boolean z) {
        this("btCollisionAlgorithmConstructionInfo", j, z);
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

    public static long getCPtr(btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo) {
        if (btcollisionalgorithmconstructioninfo == null) {
            return 0L;
        }
        return btcollisionalgorithmconstructioninfo.swigCPtr;
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
                CollisionJNI.delete_btCollisionAlgorithmConstructionInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCollisionAlgorithmConstructionInfo() {
        this(CollisionJNI.new_btCollisionAlgorithmConstructionInfo__SWIG_0(), true);
    }

    public btCollisionAlgorithmConstructionInfo(btDispatcher btdispatcher, int i) {
        this(CollisionJNI.new_btCollisionAlgorithmConstructionInfo__SWIG_1(btDispatcher.getCPtr(btdispatcher), btdispatcher, i), true);
    }

    public void setDispatcher1(btDispatcher btdispatcher) {
        CollisionJNI.btCollisionAlgorithmConstructionInfo_dispatcher1_set(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public btDispatcher getDispatcher1() {
        long jBtCollisionAlgorithmConstructionInfo_dispatcher1_get = CollisionJNI.btCollisionAlgorithmConstructionInfo_dispatcher1_get(this.swigCPtr, this);
        if (jBtCollisionAlgorithmConstructionInfo_dispatcher1_get == 0) {
            return null;
        }
        return new btDispatcher(jBtCollisionAlgorithmConstructionInfo_dispatcher1_get, false);
    }

    public void setManifold(btPersistentManifold btpersistentmanifold) {
        CollisionJNI.btCollisionAlgorithmConstructionInfo_manifold_set(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public btPersistentManifold getManifold() {
        long jBtCollisionAlgorithmConstructionInfo_manifold_get = CollisionJNI.btCollisionAlgorithmConstructionInfo_manifold_get(this.swigCPtr, this);
        if (jBtCollisionAlgorithmConstructionInfo_manifold_get == 0) {
            return null;
        }
        return new btPersistentManifold(jBtCollisionAlgorithmConstructionInfo_manifold_get, false);
    }
}
