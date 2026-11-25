package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btPoolAllocator;

/* JADX INFO: loaded from: classes8.dex */
public class btDispatcher extends BulletBase {
    private long swigCPtr;

    protected btDispatcher(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDispatcher(long j, boolean z) {
        this("btDispatcher", j, z);
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

    public static long getCPtr(btDispatcher btdispatcher) {
        if (btdispatcher == null) {
            return 0L;
        }
        return btdispatcher.swigCPtr;
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
                CollisionJNI.delete_btDispatcher(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCollisionAlgorithm findAlgorithm(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btPersistentManifold btpersistentmanifold, int i) {
        long jBtDispatcher_findAlgorithm = CollisionJNI.btDispatcher_findAlgorithm(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold, i);
        if (jBtDispatcher_findAlgorithm == 0) {
            return null;
        }
        return new btCollisionAlgorithm(jBtDispatcher_findAlgorithm, false);
    }

    public btPersistentManifold getNewManifold(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        long jBtDispatcher_getNewManifold = CollisionJNI.btDispatcher_getNewManifold(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
        if (jBtDispatcher_getNewManifold == 0) {
            return null;
        }
        return new btPersistentManifold(jBtDispatcher_getNewManifold, false);
    }

    public void releaseManifold(btPersistentManifold btpersistentmanifold) {
        CollisionJNI.btDispatcher_releaseManifold(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public void clearManifold(btPersistentManifold btpersistentmanifold) {
        CollisionJNI.btDispatcher_clearManifold(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public boolean needsCollision(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        return CollisionJNI.btDispatcher_needsCollision(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public boolean needsResponse(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        return CollisionJNI.btDispatcher_needsResponse(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public void dispatchAllCollisionPairs(btOverlappingPairCache btoverlappingpaircache, btDispatcherInfo btdispatcherinfo, btDispatcher btdispatcher) {
        CollisionJNI.btDispatcher_dispatchAllCollisionPairs(this.swigCPtr, this, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache, btDispatcherInfo.getCPtr(btdispatcherinfo), btdispatcherinfo, getCPtr(btdispatcher), btdispatcher);
    }

    public int getNumManifolds() {
        return CollisionJNI.btDispatcher_getNumManifolds(this.swigCPtr, this);
    }

    public btPersistentManifold getManifoldByIndexInternal(int i) {
        long jBtDispatcher_getManifoldByIndexInternal = CollisionJNI.btDispatcher_getManifoldByIndexInternal(this.swigCPtr, this, i);
        if (jBtDispatcher_getManifoldByIndexInternal == 0) {
            return null;
        }
        return new btPersistentManifold(jBtDispatcher_getManifoldByIndexInternal, false);
    }

    public SWIGTYPE_p_p_btPersistentManifold getInternalManifoldPointer() {
        long jBtDispatcher_getInternalManifoldPointer = CollisionJNI.btDispatcher_getInternalManifoldPointer(this.swigCPtr, this);
        if (jBtDispatcher_getInternalManifoldPointer == 0) {
            return null;
        }
        return new SWIGTYPE_p_p_btPersistentManifold(jBtDispatcher_getInternalManifoldPointer, false);
    }

    public btPoolAllocator getInternalManifoldPool() {
        long jBtDispatcher_getInternalManifoldPool = CollisionJNI.btDispatcher_getInternalManifoldPool(this.swigCPtr, this);
        if (jBtDispatcher_getInternalManifoldPool == 0) {
            return null;
        }
        return new btPoolAllocator(jBtDispatcher_getInternalManifoldPool, false);
    }

    public btPoolAllocator getInternalManifoldPoolConst() {
        long jBtDispatcher_getInternalManifoldPoolConst = CollisionJNI.btDispatcher_getInternalManifoldPoolConst(this.swigCPtr, this);
        if (jBtDispatcher_getInternalManifoldPoolConst == 0) {
            return null;
        }
        return new btPoolAllocator(jBtDispatcher_getInternalManifoldPoolConst, false);
    }

    public long allocateCollisionAlgorithm(int i) {
        return CollisionJNI.btDispatcher_allocateCollisionAlgorithm(this.swigCPtr, this, i);
    }

    public void freeCollisionAlgorithm(long j) {
        CollisionJNI.btDispatcher_freeCollisionAlgorithm(this.swigCPtr, this, j);
    }
}
