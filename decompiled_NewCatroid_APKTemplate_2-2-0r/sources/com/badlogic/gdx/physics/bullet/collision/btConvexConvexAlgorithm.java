package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexConvexAlgorithm extends btActivatingCollisionAlgorithm {
    private long swigCPtr;

    protected btConvexConvexAlgorithm(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexConvexAlgorithm_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexConvexAlgorithm(long j, boolean z) {
        this("btConvexConvexAlgorithm", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexConvexAlgorithm_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexConvexAlgorithm btconvexconvexalgorithm) {
        if (btconvexconvexalgorithm == null) {
            return 0L;
        }
        return btconvexconvexalgorithm.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btConvexConvexAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btConvexConvexAlgorithm(btPersistentManifold btpersistentmanifold, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver, int i, int i2) {
        this(CollisionJNI.new_btConvexConvexAlgorithm(btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold, btCollisionAlgorithmConstructionInfo.getCPtr(btcollisionalgorithmconstructioninfo), btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver, i, i2), true);
    }

    public void setLowLevelOfDetail(boolean z) {
        CollisionJNI.btConvexConvexAlgorithm_setLowLevelOfDetail(this.swigCPtr, this, z);
    }

    public btPersistentManifold getManifold() {
        long jBtConvexConvexAlgorithm_getManifold = CollisionJNI.btConvexConvexAlgorithm_getManifold(this.swigCPtr, this);
        if (jBtConvexConvexAlgorithm_getManifold == 0) {
            return null;
        }
        return new btPersistentManifold(jBtConvexConvexAlgorithm_getManifold, false);
    }

    public static class CreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected CreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btConvexConvexAlgorithm_CreateFunc_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public CreateFunc(long j, boolean z) {
            this("CreateFunc", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmCreateFunc, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(CollisionJNI.btConvexConvexAlgorithm_CreateFunc_SWIGUpcast(j), z);
        }

        public static long getCPtr(CreateFunc createFunc) {
            if (createFunc == null) {
                return 0L;
            }
            return createFunc.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmCreateFunc, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmCreateFunc, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    CollisionJNI.delete_btConvexConvexAlgorithm_CreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setPdSolver(btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver) {
            CollisionJNI.btConvexConvexAlgorithm_CreateFunc_pdSolver_set(this.swigCPtr, this, btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver);
        }

        public btConvexPenetrationDepthSolver getPdSolver() {
            long jBtConvexConvexAlgorithm_CreateFunc_pdSolver_get = CollisionJNI.btConvexConvexAlgorithm_CreateFunc_pdSolver_get(this.swigCPtr, this);
            if (jBtConvexConvexAlgorithm_CreateFunc_pdSolver_get == 0) {
                return null;
            }
            return new btConvexPenetrationDepthSolver(jBtConvexConvexAlgorithm_CreateFunc_pdSolver_get, false);
        }

        public void setNumPerturbationIterations(int i) {
            CollisionJNI.btConvexConvexAlgorithm_CreateFunc_numPerturbationIterations_set(this.swigCPtr, this, i);
        }

        public int getNumPerturbationIterations() {
            return CollisionJNI.btConvexConvexAlgorithm_CreateFunc_numPerturbationIterations_get(this.swigCPtr, this);
        }

        public void setMinimumPointsPerturbationThreshold(int i) {
            CollisionJNI.btConvexConvexAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_set(this.swigCPtr, this, i);
        }

        public int getMinimumPointsPerturbationThreshold() {
            return CollisionJNI.btConvexConvexAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_get(this.swigCPtr, this);
        }

        public CreateFunc(btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver) {
            this(CollisionJNI.new_btConvexConvexAlgorithm_CreateFunc(btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver), true);
        }
    }
}
