package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexConcaveCollisionAlgorithm extends btActivatingCollisionAlgorithm {
    private long swigCPtr;

    protected btConvexConcaveCollisionAlgorithm(String str, long j, boolean z) {
        super(str, CollisionJNI.btConvexConcaveCollisionAlgorithm_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConvexConcaveCollisionAlgorithm(long j, boolean z) {
        this("btConvexConcaveCollisionAlgorithm", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btActivatingCollisionAlgorithm, com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btConvexConcaveCollisionAlgorithm_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConvexConcaveCollisionAlgorithm btconvexconcavecollisionalgorithm) {
        if (btconvexconcavecollisionalgorithm == null) {
            return 0L;
        }
        return btconvexconcavecollisionalgorithm.swigCPtr;
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
                CollisionJNI.delete_btConvexConcaveCollisionAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btConvexConcaveCollisionAlgorithm_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btConvexConcaveCollisionAlgorithm_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btConvexConcaveCollisionAlgorithm_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btConvexConcaveCollisionAlgorithm_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btConvexConcaveCollisionAlgorithm_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btConvexConcaveCollisionAlgorithm_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btConvexConcaveCollisionAlgorithm_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btConvexConcaveCollisionAlgorithm_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btConvexConcaveCollisionAlgorithm(btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z) {
        this(CollisionJNI.new_btConvexConcaveCollisionAlgorithm(btCollisionAlgorithmConstructionInfo.getCPtr(btcollisionalgorithmconstructioninfo), btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, z), true);
    }

    public void clearCache() {
        CollisionJNI.btConvexConcaveCollisionAlgorithm_clearCache(this.swigCPtr, this);
    }

    public static class CreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected CreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btConvexConcaveCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
            super.reset(CollisionJNI.btConvexConcaveCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
                    CollisionJNI.delete_btConvexConcaveCollisionAlgorithm_CreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public CreateFunc() {
            this(CollisionJNI.new_btConvexConcaveCollisionAlgorithm_CreateFunc(), true);
        }
    }

    public static class SwappedCreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected SwappedCreateFunc(String str, long j, boolean z) {
            super(str, CollisionJNI.btConvexConcaveCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(j), z);
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
            super.reset(CollisionJNI.btConvexConcaveCollisionAlgorithm_SwappedCreateFunc_SWIGUpcast(j), z);
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
                    CollisionJNI.delete_btConvexConcaveCollisionAlgorithm_SwappedCreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public SwappedCreateFunc() {
            this(CollisionJNI.new_btConvexConcaveCollisionAlgorithm_SwappedCreateFunc(), true);
        }
    }
}
