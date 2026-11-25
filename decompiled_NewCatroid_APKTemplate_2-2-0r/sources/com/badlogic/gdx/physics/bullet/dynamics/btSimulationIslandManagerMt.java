package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectArray;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifoldArray;
import com.badlogic.gdx.physics.bullet.collision.btSimulationIslandManager;

/* JADX INFO: loaded from: classes8.dex */
public class btSimulationIslandManagerMt extends btSimulationIslandManager {
    private long swigCPtr;

    protected btSimulationIslandManagerMt(String str, long j, boolean z) {
        super(str, DynamicsJNI.btSimulationIslandManagerMt_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSimulationIslandManagerMt(long j, boolean z) {
        this("btSimulationIslandManagerMt", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btSimulationIslandManager, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btSimulationIslandManagerMt_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSimulationIslandManagerMt btsimulationislandmanagermt) {
        if (btsimulationislandmanagermt == null) {
            return 0L;
        }
        return btsimulationislandmanagermt.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btSimulationIslandManager, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btSimulationIslandManager, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btSimulationIslandManagerMt(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class Island extends BulletBase {
        private long swigCPtr;

        protected Island(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Island(long j, boolean z) {
            this("Island", j, z);
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

        public static long getCPtr(Island island) {
            if (island == null) {
                return 0L;
            }
            return island.swigCPtr;
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
                    DynamicsJNI.delete_btSimulationIslandManagerMt_Island(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setBodyArray(btCollisionObjectArray btcollisionobjectarray) {
            DynamicsJNI.btSimulationIslandManagerMt_Island_bodyArray_set(this.swigCPtr, this, btCollisionObjectArray.getCPtr(btcollisionobjectarray), btcollisionobjectarray);
        }

        public btCollisionObjectArray getBodyArray() {
            long jBtSimulationIslandManagerMt_Island_bodyArray_get = DynamicsJNI.btSimulationIslandManagerMt_Island_bodyArray_get(this.swigCPtr, this);
            if (jBtSimulationIslandManagerMt_Island_bodyArray_get == 0) {
                return null;
            }
            return new btCollisionObjectArray(jBtSimulationIslandManagerMt_Island_bodyArray_get, false);
        }

        public void setManifoldArray(btPersistentManifoldArray btpersistentmanifoldarray) {
            DynamicsJNI.btSimulationIslandManagerMt_Island_manifoldArray_set(this.swigCPtr, this, btPersistentManifoldArray.getCPtr(btpersistentmanifoldarray), btpersistentmanifoldarray);
        }

        public btPersistentManifoldArray getManifoldArray() {
            long jBtSimulationIslandManagerMt_Island_manifoldArray_get = DynamicsJNI.btSimulationIslandManagerMt_Island_manifoldArray_get(this.swigCPtr, this);
            if (jBtSimulationIslandManagerMt_Island_manifoldArray_get == 0) {
                return null;
            }
            return new btPersistentManifoldArray(jBtSimulationIslandManagerMt_Island_manifoldArray_get, false);
        }

        public void setConstraintArray(SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t) {
            DynamicsJNI.btSimulationIslandManagerMt_Island_constraintArray_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t));
        }

        public SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t getConstraintArray() {
            long jBtSimulationIslandManagerMt_Island_constraintArray_get = DynamicsJNI.btSimulationIslandManagerMt_Island_constraintArray_get(this.swigCPtr, this);
            if (jBtSimulationIslandManagerMt_Island_constraintArray_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t(jBtSimulationIslandManagerMt_Island_constraintArray_get, false);
        }

        public void setId(int i) {
            DynamicsJNI.btSimulationIslandManagerMt_Island_id_set(this.swigCPtr, this, i);
        }

        public int getId() {
            return DynamicsJNI.btSimulationIslandManagerMt_Island_id_get(this.swigCPtr, this);
        }

        public void setIsSleeping(boolean z) {
            DynamicsJNI.btSimulationIslandManagerMt_Island_isSleeping_set(this.swigCPtr, this, z);
        }

        public boolean getIsSleeping() {
            return DynamicsJNI.btSimulationIslandManagerMt_Island_isSleeping_get(this.swigCPtr, this);
        }

        public void append(Island island) {
            DynamicsJNI.btSimulationIslandManagerMt_Island_append(this.swigCPtr, this, getCPtr(island), island);
        }

        public Island() {
            this(DynamicsJNI.new_btSimulationIslandManagerMt_Island(), true);
        }
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
                    DynamicsJNI.delete_btSimulationIslandManagerMt_IslandCallback(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void processIsland(SWIGTYPE_p_p_btCollisionObject sWIGTYPE_p_p_btCollisionObject, int i, SWIGTYPE_p_p_btPersistentManifold sWIGTYPE_p_p_btPersistentManifold, int i2, SWIGTYPE_p_p_btTypedConstraint sWIGTYPE_p_p_btTypedConstraint, int i3, int i4) {
            DynamicsJNI.btSimulationIslandManagerMt_IslandCallback_processIsland(this.swigCPtr, this, SWIGTYPE_p_p_btCollisionObject.getCPtr(sWIGTYPE_p_p_btCollisionObject), i, SWIGTYPE_p_p_btPersistentManifold.getCPtr(sWIGTYPE_p_p_btPersistentManifold), i2, SWIGTYPE_p_p_btTypedConstraint.getCPtr(sWIGTYPE_p_p_btTypedConstraint), i3, i4);
        }
    }

    public static void serialIslandDispatch(SWIGTYPE_p_btAlignedObjectArrayT_btSimulationIslandManagerMt__Island_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSimulationIslandManagerMt__Island_p_t, IslandCallback islandCallback) {
        DynamicsJNI.btSimulationIslandManagerMt_serialIslandDispatch(SWIGTYPE_p_btAlignedObjectArrayT_btSimulationIslandManagerMt__Island_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSimulationIslandManagerMt__Island_p_t), IslandCallback.getCPtr(islandCallback), islandCallback);
    }

    public static void parallelIslandDispatch(SWIGTYPE_p_btAlignedObjectArrayT_btSimulationIslandManagerMt__Island_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSimulationIslandManagerMt__Island_p_t, IslandCallback islandCallback) {
        DynamicsJNI.btSimulationIslandManagerMt_parallelIslandDispatch(SWIGTYPE_p_btAlignedObjectArrayT_btSimulationIslandManagerMt__Island_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSimulationIslandManagerMt__Island_p_t), IslandCallback.getCPtr(islandCallback), islandCallback);
    }

    public btSimulationIslandManagerMt() {
        this(DynamicsJNI.new_btSimulationIslandManagerMt(), true);
    }

    public void buildAndProcessIslands(btDispatcher btdispatcher, btCollisionWorld btcollisionworld, SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t, IslandCallback islandCallback) {
        DynamicsJNI.btSimulationIslandManagerMt_buildAndProcessIslands(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld, SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraint_p_t), IslandCallback.getCPtr(islandCallback), islandCallback);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btSimulationIslandManager
    public void buildIslands(btDispatcher btdispatcher, btCollisionWorld btcollisionworld) {
        DynamicsJNI.btSimulationIslandManagerMt_buildIslands(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld);
    }

    public int getMinimumSolverBatchSize() {
        return DynamicsJNI.btSimulationIslandManagerMt_getMinimumSolverBatchSize(this.swigCPtr, this);
    }

    public void setMinimumSolverBatchSize(int i) {
        DynamicsJNI.btSimulationIslandManagerMt_setMinimumSolverBatchSize(this.swigCPtr, this, i);
    }

    public SWIGTYPE_p_f_p_btAlignedObjectArray__p_btSimulationIslandManagerMt__Island___p_btSimulationIslandManagerMt__IslandCallback__void getIslandDispatchFunction() {
        long jBtSimulationIslandManagerMt_getIslandDispatchFunction = DynamicsJNI.btSimulationIslandManagerMt_getIslandDispatchFunction(this.swigCPtr, this);
        if (jBtSimulationIslandManagerMt_getIslandDispatchFunction == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_p_btAlignedObjectArray__p_btSimulationIslandManagerMt__Island___p_btSimulationIslandManagerMt__IslandCallback__void(jBtSimulationIslandManagerMt_getIslandDispatchFunction, false);
    }

    public void setIslandDispatchFunction(SWIGTYPE_p_f_p_btAlignedObjectArray__p_btSimulationIslandManagerMt__Island___p_btSimulationIslandManagerMt__IslandCallback__void sWIGTYPE_p_f_p_btAlignedObjectArray__p_btSimulationIslandManagerMt__Island___p_btSimulationIslandManagerMt__IslandCallback__void) {
        DynamicsJNI.btSimulationIslandManagerMt_setIslandDispatchFunction(this.swigCPtr, this, SWIGTYPE_p_f_p_btAlignedObjectArray__p_btSimulationIslandManagerMt__Island___p_btSimulationIslandManagerMt__IslandCallback__void.getCPtr(sWIGTYPE_p_f_p_btAlignedObjectArray__p_btSimulationIslandManagerMt__Island___p_btSimulationIslandManagerMt__IslandCallback__void));
    }
}
