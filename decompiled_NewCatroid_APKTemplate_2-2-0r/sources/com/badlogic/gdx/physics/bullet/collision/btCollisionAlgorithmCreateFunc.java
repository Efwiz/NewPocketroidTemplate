package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionAlgorithmCreateFunc extends BulletBase {
    private long swigCPtr;

    protected btCollisionAlgorithmCreateFunc(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionAlgorithmCreateFunc(long j, boolean z) {
        this("btCollisionAlgorithmCreateFunc", j, z);
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

    public static long getCPtr(btCollisionAlgorithmCreateFunc btcollisionalgorithmcreatefunc) {
        if (btcollisionalgorithmcreatefunc == null) {
            return 0L;
        }
        return btcollisionalgorithmcreatefunc.swigCPtr;
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
                CollisionJNI.delete_btCollisionAlgorithmCreateFunc(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setSwapped(boolean z) {
        CollisionJNI.btCollisionAlgorithmCreateFunc_swapped_set(this.swigCPtr, this, z);
    }

    public boolean getSwapped() {
        return CollisionJNI.btCollisionAlgorithmCreateFunc_swapped_get(this.swigCPtr, this);
    }

    public btCollisionAlgorithmCreateFunc() {
        this(CollisionJNI.new_btCollisionAlgorithmCreateFunc(), true);
    }

    public btCollisionAlgorithm CreateCollisionAlgorithm(btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2) {
        long jBtCollisionAlgorithmCreateFunc_CreateCollisionAlgorithm = CollisionJNI.btCollisionAlgorithmCreateFunc_CreateCollisionAlgorithm(this.swigCPtr, this, btCollisionAlgorithmConstructionInfo.getCPtr(btcollisionalgorithmconstructioninfo), btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2);
        if (jBtCollisionAlgorithmCreateFunc_CreateCollisionAlgorithm == 0) {
            return null;
        }
        return new btCollisionAlgorithm(jBtCollisionAlgorithmCreateFunc_CreateCollisionAlgorithm, false);
    }
}
