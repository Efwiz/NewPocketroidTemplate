package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btConvex2dConvex2dAlgorithm extends btActivatingCollisionAlgorithm {
    private long swigCPtr;

    protected btConvex2dConvex2dAlgorithm(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvex2dConvex2dAlgorithm_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvex2dConvex2dAlgorithm(long j, boolean z) {
        this("btConvex2dConvex2dAlgorithm", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvex2dConvex2dAlgorithm_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvex2dConvex2dAlgorithm btconvex2dconvex2dalgorithm) {
        if (btconvex2dconvex2dalgorithm == null) {
            return 0L;
        }
        return btconvex2dconvex2dalgorithm.swigCPtr;
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
                CollisionJNI.delete_btConvex2dConvex2dAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btConvex2dConvex2dAlgorithm(btPersistentManifold btpersistentmanifold, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btVoronoiSimplexSolver btvoronoisimplexsolver, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver, int i, int i2) {
        this(CollisionJNI.new_btConvex2dConvex2dAlgorithm(btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold, btCollisionAlgorithmConstructionInfo.getCPtr(btcollisionalgorithmconstructioninfo), btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btVoronoiSimplexSolver.getCPtr(btvoronoisimplexsolver), btvoronoisimplexsolver, btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver, i, i2), true);
    }

    public void setLowLevelOfDetail(boolean z) {
        CollisionJNI.btConvex2dConvex2dAlgorithm_setLowLevelOfDetail(this.swigCPtr, this, z);
    }

    public btPersistentManifold getManifold() {
        long jBtConvex2dConvex2dAlgorithm_getManifold = CollisionJNI.btConvex2dConvex2dAlgorithm_getManifold(this.swigCPtr, this);
        if (jBtConvex2dConvex2dAlgorithm_getManifold == 0) {
            return null;
        }
        return new btPersistentManifold(jBtConvex2dConvex2dAlgorithm_getManifold, false);
    }

    public static class CreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected CreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
            super.reset(CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
                    CollisionJNI.delete_btConvex2dConvex2dAlgorithm_CreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setPdSolver(btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver) {
            CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_pdSolver_set(this.swigCPtr, this, btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver);
        }

        public btConvexPenetrationDepthSolver getPdSolver() {
            long jBtConvex2dConvex2dAlgorithm_CreateFunc_pdSolver_get = CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_pdSolver_get(this.swigCPtr, this);
            if (jBtConvex2dConvex2dAlgorithm_CreateFunc_pdSolver_get == 0) {
                return null;
            }
            return new btConvexPenetrationDepthSolver(jBtConvex2dConvex2dAlgorithm_CreateFunc_pdSolver_get, false);
        }

        public void setSimplexSolver(btVoronoiSimplexSolver btvoronoisimplexsolver) {
            CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_simplexSolver_set(this.swigCPtr, this, btVoronoiSimplexSolver.getCPtr(btvoronoisimplexsolver), btvoronoisimplexsolver);
        }

        public btVoronoiSimplexSolver getSimplexSolver() {
            long jBtConvex2dConvex2dAlgorithm_CreateFunc_simplexSolver_get = CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_simplexSolver_get(this.swigCPtr, this);
            if (jBtConvex2dConvex2dAlgorithm_CreateFunc_simplexSolver_get == 0) {
                return null;
            }
            return new btVoronoiSimplexSolver(jBtConvex2dConvex2dAlgorithm_CreateFunc_simplexSolver_get, false);
        }

        public void setNumPerturbationIterations(int i) {
            CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_numPerturbationIterations_set(this.swigCPtr, this, i);
        }

        public int getNumPerturbationIterations() {
            return CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_numPerturbationIterations_get(this.swigCPtr, this);
        }

        public void setMinimumPointsPerturbationThreshold(int i) {
            CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_set(this.swigCPtr, this, i);
        }

        public int getMinimumPointsPerturbationThreshold() {
            return CollisionJNI.btConvex2dConvex2dAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_get(this.swigCPtr, this);
        }

        public CreateFunc(btVoronoiSimplexSolver btvoronoisimplexsolver, btConvexPenetrationDepthSolver btconvexpenetrationdepthsolver) {
            this(CollisionJNI.new_btConvex2dConvex2dAlgorithm_CreateFunc(btVoronoiSimplexSolver.getCPtr(btvoronoisimplexsolver), btvoronoisimplexsolver, btConvexPenetrationDepthSolver.getCPtr(btconvexpenetrationdepthsolver), btconvexpenetrationdepthsolver), true);
        }
    }
}
