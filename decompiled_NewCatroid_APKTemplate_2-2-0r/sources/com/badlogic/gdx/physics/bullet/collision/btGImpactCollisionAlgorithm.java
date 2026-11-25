package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btGImpactCollisionAlgorithm extends btActivatingCollisionAlgorithm {
    private long swigCPtr;

    protected btGImpactCollisionAlgorithm(String str, long j, boolean z) {
        super(str, CollisionJNI.btGImpactCollisionAlgorithm_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGImpactCollisionAlgorithm(long j, boolean z) {
        this("btGImpactCollisionAlgorithm", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btGImpactCollisionAlgorithm_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGImpactCollisionAlgorithm btgimpactcollisionalgorithm) {
        if (btgimpactcollisionalgorithm == null) {
            return 0L;
        }
        return btgimpactcollisionalgorithm.swigCPtr;
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
                CollisionJNI.delete_btGImpactCollisionAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGImpactCollisionAlgorithm(btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2) {
        this(CollisionJNI.new_btGImpactCollisionAlgorithm(btCollisionAlgorithmConstructionInfo.getCPtr(btcollisionalgorithmconstructioninfo), btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2), true);
    }

    public btManifoldResult internalGetResultOut() {
        long jBtGImpactCollisionAlgorithm_internalGetResultOut = CollisionJNI.btGImpactCollisionAlgorithm_internalGetResultOut(this.swigCPtr, this);
        if (jBtGImpactCollisionAlgorithm_internalGetResultOut == 0) {
            return null;
        }
        return new btManifoldResult(jBtGImpactCollisionAlgorithm_internalGetResultOut, false);
    }

    public static class CreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected CreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btGImpactCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
            super.reset(CollisionJNI.btGImpactCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
                    CollisionJNI.delete_btGImpactCollisionAlgorithm_CreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public CreateFunc() {
            this(CollisionJNI.new_btGImpactCollisionAlgorithm_CreateFunc(), true);
        }
    }

    public static void registerAlgorithm(btCollisionDispatcher btcollisiondispatcher) {
        CollisionJNI.btGImpactCollisionAlgorithm_registerAlgorithm(btCollisionDispatcher.getCPtr(btcollisiondispatcher), btcollisiondispatcher);
    }

    public void gimpact_vs_gimpact(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btGImpactShapeInterface btgimpactshapeinterface, btGImpactShapeInterface btgimpactshapeinterface2) {
        CollisionJNI.btGImpactCollisionAlgorithm_gimpact_vs_gimpact(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btGImpactShapeInterface.getCPtr(btgimpactshapeinterface), btgimpactshapeinterface, btGImpactShapeInterface.getCPtr(btgimpactshapeinterface2), btgimpactshapeinterface2);
    }

    public void gimpact_vs_shape(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btGImpactShapeInterface btgimpactshapeinterface, btCollisionShape btcollisionshape, boolean z) {
        CollisionJNI.btGImpactCollisionAlgorithm_gimpact_vs_shape(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btGImpactShapeInterface.getCPtr(btgimpactshapeinterface), btgimpactshapeinterface, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, z);
    }

    public void gimpact_vs_compoundshape(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btGImpactShapeInterface btgimpactshapeinterface, btCompoundShape btcompoundshape, boolean z) {
        CollisionJNI.btGImpactCollisionAlgorithm_gimpact_vs_compoundshape(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btGImpactShapeInterface.getCPtr(btgimpactshapeinterface), btgimpactshapeinterface, btCompoundShape.getCPtr(btcompoundshape), btcompoundshape, z);
    }

    public void gimpact_vs_concave(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, btGImpactShapeInterface btgimpactshapeinterface, btConcaveShape btconcaveshape, boolean z) {
        CollisionJNI.btGImpactCollisionAlgorithm_gimpact_vs_concave(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, btGImpactShapeInterface.getCPtr(btgimpactshapeinterface), btgimpactshapeinterface, btConcaveShape.getCPtr(btconcaveshape), btconcaveshape, z);
    }

    public void setFace0(int i) {
        CollisionJNI.btGImpactCollisionAlgorithm_setFace0(this.swigCPtr, this, i);
    }

    public int getFace0() {
        return CollisionJNI.btGImpactCollisionAlgorithm_getFace0(this.swigCPtr, this);
    }

    public void setFace1(int i) {
        CollisionJNI.btGImpactCollisionAlgorithm_setFace1(this.swigCPtr, this, i);
    }

    public int getFace1() {
        return CollisionJNI.btGImpactCollisionAlgorithm_getFace1(this.swigCPtr, this);
    }

    public void setPart0(int i) {
        CollisionJNI.btGImpactCollisionAlgorithm_setPart0(this.swigCPtr, this, i);
    }

    public int getPart0() {
        return CollisionJNI.btGImpactCollisionAlgorithm_getPart0(this.swigCPtr, this);
    }

    public void setPart1(int i) {
        CollisionJNI.btGImpactCollisionAlgorithm_setPart1(this.swigCPtr, this, i);
    }

    public int getPart1() {
        return CollisionJNI.btGImpactCollisionAlgorithm_getPart1(this.swigCPtr, this);
    }
}
