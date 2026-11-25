package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btSimulationIslandManager extends BulletBase {
    private long swigCPtr;

    protected btSimulationIslandManager(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSimulationIslandManager(long j, boolean z) {
        this("btSimulationIslandManager", j, z);
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

    public static long getCPtr(btSimulationIslandManager btsimulationislandmanager) {
        if (btsimulationislandmanager == null) {
            return 0L;
        }
        return btsimulationislandmanager.swigCPtr;
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
                CollisionJNI.delete_btSimulationIslandManager(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSimulationIslandManager() {
        this(CollisionJNI.new_btSimulationIslandManager(), true);
    }

    public void initUnionFind(int i) {
        CollisionJNI.btSimulationIslandManager_initUnionFind(this.swigCPtr, this, i);
    }

    public btUnionFind getUnionFind() {
        return new btUnionFind(CollisionJNI.btSimulationIslandManager_getUnionFind(this.swigCPtr, this), false);
    }

    public void updateActivationState(btCollisionWorld btcollisionworld, btDispatcher btdispatcher) {
        CollisionJNI.btSimulationIslandManager_updateActivationState(this.swigCPtr, this, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void storeIslandActivationState(btCollisionWorld btcollisionworld) {
        CollisionJNI.btSimulationIslandManager_storeIslandActivationState(this.swigCPtr, this, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld);
    }

    public void findUnions(btDispatcher btdispatcher, btCollisionWorld btcollisionworld) {
        CollisionJNI.btSimulationIslandManager_findUnions(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld);
    }

    public static class IslandCallback extends BulletBase {
        private long swigCPtr;

        protected IslandCallback(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public IslandCallback(long j, boolean z) {
            this("IslandCallback", j, z);
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

        public static long getCPtr(IslandCallback islandCallback) {
            if (islandCallback == null) {
                return 0L;
            }
            return islandCallback.swigCPtr;
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
                    CollisionJNI.delete_btSimulationIslandManager_IslandCallback(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void processIsland(SWIGTYPE_p_p_btCollisionObject sWIGTYPE_p_p_btCollisionObject, int i, SWIGTYPE_p_p_btPersistentManifold sWIGTYPE_p_p_btPersistentManifold, int i2, int i3) {
            CollisionJNI.btSimulationIslandManager_IslandCallback_processIsland(this.swigCPtr, this, SWIGTYPE_p_p_btCollisionObject.getCPtr(sWIGTYPE_p_p_btCollisionObject), i, SWIGTYPE_p_p_btPersistentManifold.getCPtr(sWIGTYPE_p_p_btPersistentManifold), i2, i3);
        }
    }

    public void buildAndProcessIslands(btDispatcher btdispatcher, btCollisionWorld btcollisionworld, IslandCallback islandCallback) {
        CollisionJNI.btSimulationIslandManager_buildAndProcessIslands(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld, IslandCallback.getCPtr(islandCallback), islandCallback);
    }

    public void buildIslands(btDispatcher btdispatcher, btCollisionWorld btcollisionworld) {
        CollisionJNI.btSimulationIslandManager_buildIslands(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld);
    }

    public boolean getSplitIslands() {
        return CollisionJNI.btSimulationIslandManager_getSplitIslands(this.swigCPtr, this);
    }

    public void setSplitIslands(boolean z) {
        CollisionJNI.btSimulationIslandManager_setSplitIslands(this.swigCPtr, this, z);
    }
}
