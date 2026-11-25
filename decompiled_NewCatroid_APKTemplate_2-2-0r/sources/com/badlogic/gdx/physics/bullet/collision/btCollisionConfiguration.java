package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btPoolAllocator;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionConfiguration extends BulletBase {
    private long swigCPtr;

    protected btCollisionConfiguration(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionConfiguration(long j, boolean z) {
        this("btCollisionConfiguration", j, z);
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

    public static long getCPtr(btCollisionConfiguration btcollisionconfiguration) {
        if (btcollisionconfiguration == null) {
            return 0L;
        }
        return btcollisionconfiguration.swigCPtr;
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
                CollisionJNI.delete_btCollisionConfiguration(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btPoolAllocator getPersistentManifoldPool() {
        long jBtCollisionConfiguration_getPersistentManifoldPool = CollisionJNI.btCollisionConfiguration_getPersistentManifoldPool(this.swigCPtr, this);
        if (jBtCollisionConfiguration_getPersistentManifoldPool == 0) {
            return null;
        }
        return new btPoolAllocator(jBtCollisionConfiguration_getPersistentManifoldPool, false);
    }

    public btPoolAllocator getCollisionAlgorithmPool() {
        long jBtCollisionConfiguration_getCollisionAlgorithmPool = CollisionJNI.btCollisionConfiguration_getCollisionAlgorithmPool(this.swigCPtr, this);
        if (jBtCollisionConfiguration_getCollisionAlgorithmPool == 0) {
            return null;
        }
        return new btPoolAllocator(jBtCollisionConfiguration_getCollisionAlgorithmPool, false);
    }

    public btCollisionAlgorithmCreateFunc getCollisionAlgorithmCreateFunc(int i, int i2) {
        long jBtCollisionConfiguration_getCollisionAlgorithmCreateFunc = CollisionJNI.btCollisionConfiguration_getCollisionAlgorithmCreateFunc(this.swigCPtr, this, i, i2);
        if (jBtCollisionConfiguration_getCollisionAlgorithmCreateFunc == 0) {
            return null;
        }
        return new btCollisionAlgorithmCreateFunc(jBtCollisionConfiguration_getCollisionAlgorithmCreateFunc, false);
    }

    public btCollisionAlgorithmCreateFunc getClosestPointsAlgorithmCreateFunc(int i, int i2) {
        long jBtCollisionConfiguration_getClosestPointsAlgorithmCreateFunc = CollisionJNI.btCollisionConfiguration_getClosestPointsAlgorithmCreateFunc(this.swigCPtr, this, i, i2);
        if (jBtCollisionConfiguration_getClosestPointsAlgorithmCreateFunc == 0) {
            return null;
        }
        return new btCollisionAlgorithmCreateFunc(jBtCollisionConfiguration_getClosestPointsAlgorithmCreateFunc, false);
    }
}
