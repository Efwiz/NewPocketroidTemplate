package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btPoolAllocator;

/* JADX INFO: loaded from: classes8.dex */
public class btDefaultCollisionConstructionInfo extends BulletBase {
    private long swigCPtr;

    protected btDefaultCollisionConstructionInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDefaultCollisionConstructionInfo(long j, boolean z) {
        this("btDefaultCollisionConstructionInfo", j, z);
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

    public static long getCPtr(btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo) {
        if (btdefaultcollisionconstructioninfo == null) {
            return 0L;
        }
        return btdefaultcollisionconstructioninfo.swigCPtr;
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
                CollisionJNI.delete_btDefaultCollisionConstructionInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPersistentManifoldPool(btPoolAllocator btpoolallocator) {
        CollisionJNI.btDefaultCollisionConstructionInfo_persistentManifoldPool_set(this.swigCPtr, this, btPoolAllocator.getCPtr(btpoolallocator), btpoolallocator);
    }

    public btPoolAllocator getPersistentManifoldPool() {
        long jBtDefaultCollisionConstructionInfo_persistentManifoldPool_get = CollisionJNI.btDefaultCollisionConstructionInfo_persistentManifoldPool_get(this.swigCPtr, this);
        if (jBtDefaultCollisionConstructionInfo_persistentManifoldPool_get == 0) {
            return null;
        }
        return new btPoolAllocator(jBtDefaultCollisionConstructionInfo_persistentManifoldPool_get, false);
    }

    public void setCollisionAlgorithmPool(btPoolAllocator btpoolallocator) {
        CollisionJNI.btDefaultCollisionConstructionInfo_collisionAlgorithmPool_set(this.swigCPtr, this, btPoolAllocator.getCPtr(btpoolallocator), btpoolallocator);
    }

    public btPoolAllocator getCollisionAlgorithmPool() {
        long jBtDefaultCollisionConstructionInfo_collisionAlgorithmPool_get = CollisionJNI.btDefaultCollisionConstructionInfo_collisionAlgorithmPool_get(this.swigCPtr, this);
        if (jBtDefaultCollisionConstructionInfo_collisionAlgorithmPool_get == 0) {
            return null;
        }
        return new btPoolAllocator(jBtDefaultCollisionConstructionInfo_collisionAlgorithmPool_get, false);
    }

    public void setDefaultMaxPersistentManifoldPoolSize(int i) {
        CollisionJNI.btDefaultCollisionConstructionInfo_defaultMaxPersistentManifoldPoolSize_set(this.swigCPtr, this, i);
    }

    public int getDefaultMaxPersistentManifoldPoolSize() {
        return CollisionJNI.btDefaultCollisionConstructionInfo_defaultMaxPersistentManifoldPoolSize_get(this.swigCPtr, this);
    }

    public void setDefaultMaxCollisionAlgorithmPoolSize(int i) {
        CollisionJNI.btDefaultCollisionConstructionInfo_defaultMaxCollisionAlgorithmPoolSize_set(this.swigCPtr, this, i);
    }

    public int getDefaultMaxCollisionAlgorithmPoolSize() {
        return CollisionJNI.btDefaultCollisionConstructionInfo_defaultMaxCollisionAlgorithmPoolSize_get(this.swigCPtr, this);
    }

    public void setCustomCollisionAlgorithmMaxElementSize(int i) {
        CollisionJNI.btDefaultCollisionConstructionInfo_customCollisionAlgorithmMaxElementSize_set(this.swigCPtr, this, i);
    }

    public int getCustomCollisionAlgorithmMaxElementSize() {
        return CollisionJNI.btDefaultCollisionConstructionInfo_customCollisionAlgorithmMaxElementSize_get(this.swigCPtr, this);
    }

    public void setUseEpaPenetrationAlgorithm(int i) {
        CollisionJNI.btDefaultCollisionConstructionInfo_useEpaPenetrationAlgorithm_set(this.swigCPtr, this, i);
    }

    public int getUseEpaPenetrationAlgorithm() {
        return CollisionJNI.btDefaultCollisionConstructionInfo_useEpaPenetrationAlgorithm_get(this.swigCPtr, this);
    }

    public btDefaultCollisionConstructionInfo() {
        this(CollisionJNI.new_btDefaultCollisionConstructionInfo(), true);
    }
}
