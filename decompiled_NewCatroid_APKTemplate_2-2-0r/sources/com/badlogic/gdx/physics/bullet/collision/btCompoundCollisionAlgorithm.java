package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btCompoundCollisionAlgorithm extends btActivatingCollisionAlgorithm {
    private long swigCPtr;

    protected btCompoundCollisionAlgorithm(String str, long j, boolean z) {
        super(str, CollisionJNI.btCompoundCollisionAlgorithm_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btCompoundCollisionAlgorithm(long j, boolean z) {
        this("btCompoundCollisionAlgorithm", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btCompoundCollisionAlgorithm_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCompoundCollisionAlgorithm btcompoundcollisionalgorithm) {
        if (btcompoundcollisionalgorithm == null) {
            return 0L;
        }
        return btcompoundcollisionalgorithm.swigCPtr;
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
                CollisionJNI.delete_btCompoundCollisionAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCompoundCollisionAlgorithm(btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z) {
        this(CollisionJNI.new_btCompoundCollisionAlgorithm(btCollisionAlgorithmConstructionInfo.getCPtr(btcollisionalgorithmconstructioninfo), btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, z), true);
    }

    public btCollisionAlgorithm getChildAlgorithm(int i) {
        long jBtCompoundCollisionAlgorithm_getChildAlgorithm = CollisionJNI.btCompoundCollisionAlgorithm_getChildAlgorithm(this.swigCPtr, this, i);
        if (jBtCompoundCollisionAlgorithm_getChildAlgorithm == 0) {
            return null;
        }
        return new btCollisionAlgorithm(jBtCompoundCollisionAlgorithm_getChildAlgorithm, false);
    }

    public static class CreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected CreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btCompoundCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
            super.reset(CollisionJNI.btCompoundCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
                    CollisionJNI.delete_btCompoundCollisionAlgorithm_CreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public CreateFunc() {
            this(CollisionJNI.new_btCompoundCollisionAlgorithm_CreateFunc(), true);
        }
    }

    public static class SwappedCreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected SwappedCreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btCompoundCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public SwappedCreateFunc(long j, boolean z) {
            this("SwappedCreateFunc", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmCreateFunc, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(CollisionJNI.btCompoundCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(j), z);
        }

        public static long getCPtr(SwappedCreateFunc swappedCreateFunc) {
            if (swappedCreateFunc == null) {
                return 0L;
            }
            return swappedCreateFunc.swigCPtr;
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
                    CollisionJNI.delete_btCompoundCollisionAlgorithm_SwappedCreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public SwappedCreateFunc() {
            this(CollisionJNI.new_btCompoundCollisionAlgorithm_SwappedCreateFunc(), true);
        }
    }
}
