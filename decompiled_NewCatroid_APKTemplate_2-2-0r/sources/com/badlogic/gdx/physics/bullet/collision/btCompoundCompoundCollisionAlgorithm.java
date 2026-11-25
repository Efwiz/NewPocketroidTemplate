package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btCompoundCompoundCollisionAlgorithm extends btCompoundCollisionAlgorithm {
    private long swigCPtr;

    protected btCompoundCompoundCollisionAlgorithm(String str, long j, boolean z) {
        super(str, CollisionJNI.btCompoundCompoundCollisionAlgorithm_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btCompoundCompoundCollisionAlgorithm(long j, boolean z) {
        this("btCompoundCompoundCollisionAlgorithm", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCompoundCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btCompoundCompoundCollisionAlgorithm_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCompoundCompoundCollisionAlgorithm btcompoundcompoundcollisionalgorithm) {
        if (btcompoundcompoundcollisionalgorithm == null) {
            return 0L;
        }
        return btcompoundcompoundcollisionalgorithm.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCompoundCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCompoundCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btCompoundCompoundCollisionAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCompoundCompoundCollisionAlgorithm(btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z) {
        this(CollisionJNI.new_btCompoundCompoundCollisionAlgorithm(btCollisionAlgorithmConstructionInfo.getCPtr(btcollisionalgorithmconstructioninfo), btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, z), true);
    }

    public static class CreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected CreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btCompoundCompoundCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
            super.reset(CollisionJNI.btCompoundCompoundCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
                    CollisionJNI.delete_btCompoundCompoundCollisionAlgorithm_CreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public CreateFunc() {
            this(CollisionJNI.new_btCompoundCompoundCollisionAlgorithm_CreateFunc(), true);
        }
    }

    public static class SwappedCreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected SwappedCreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btCompoundCompoundCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(j), z);
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
            super.reset(CollisionJNI.btCompoundCompoundCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(j), z);
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
                    CollisionJNI.delete_btCompoundCompoundCollisionAlgorithm_SwappedCreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public SwappedCreateFunc() {
            this(CollisionJNI.new_btCompoundCompoundCollisionAlgorithm_SwappedCreateFunc(), true);
        }
    }
}
