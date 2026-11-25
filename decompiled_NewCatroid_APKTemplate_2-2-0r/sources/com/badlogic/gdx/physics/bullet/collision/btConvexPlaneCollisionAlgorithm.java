package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Quaternion;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexPlaneCollisionAlgorithm extends btCollisionAlgorithm {
    private long swigCPtr;

    protected btConvexPlaneCollisionAlgorithm(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexPlaneCollisionAlgorithm_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexPlaneCollisionAlgorithm(long j, boolean z) {
        this("btConvexPlaneCollisionAlgorithm", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexPlaneCollisionAlgorithm_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexPlaneCollisionAlgorithm btconvexplanecollisionalgorithm) {
        if (btconvexplanecollisionalgorithm == null) {
            return 0L;
        }
        return btconvexplanecollisionalgorithm.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btConvexPlaneCollisionAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btConvexPlaneCollisionAlgorithm(btPersistentManifold btpersistentmanifold, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z, int i, int i2) {
        this(CollisionJNI.new_btConvexPlaneCollisionAlgorithm(btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold, btCollisionAlgorithmConstructionInfo.getCPtr(btcollisionalgorithmconstructioninfo), btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, z, i, i2), true);
    }

    public void collideSingleContact(Quaternion quaternion, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btDispatcherInfo btdispatcherinfo, btManifoldResult btmanifoldresult) {
        CollisionJNI.btConvexPlaneCollisionAlgorithm_collideSingleContact(this.swigCPtr, this, quaternion, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btDispatcherInfo.getCPtr(btdispatcherinfo), btdispatcherinfo, btManifoldResult.getCPtr(btmanifoldresult), btmanifoldresult);
    }

    public static class CreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected CreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btConvexPlaneCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
            super.reset(CollisionJNI.btConvexPlaneCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
                    CollisionJNI.delete_btConvexPlaneCollisionAlgorithm_CreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setNumPerturbationIterations(int i) {
            CollisionJNI.btConvexPlaneCollisionAlgorithm_CreateFunc_numPerturbationIterations_set(this.swigCPtr, this, i);
        }

        public int getNumPerturbationIterations() {
            return CollisionJNI.btConvexPlaneCollisionAlgorithm_CreateFunc_numPerturbationIterations_get(this.swigCPtr, this);
        }

        public void setMinimumPointsPerturbationThreshold(int i) {
            CollisionJNI.btConvexPlaneCollisionAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_set(this.swigCPtr, this, i);
        }

        public int getMinimumPointsPerturbationThreshold() {
            return CollisionJNI.btConvexPlaneCollisionAlgorithm_CreateFunc_minimumPointsPerturbationThreshold_get(this.swigCPtr, this);
        }

        public CreateFunc() {
            this(CollisionJNI.new_btConvexPlaneCollisionAlgorithm_CreateFunc(), true);
        }
    }
}
