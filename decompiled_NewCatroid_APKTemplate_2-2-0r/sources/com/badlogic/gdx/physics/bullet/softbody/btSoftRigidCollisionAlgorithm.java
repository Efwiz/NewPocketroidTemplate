package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmConstructionInfo;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmCreateFunc;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftRigidCollisionAlgorithm extends btCollisionAlgorithm {
    private long swigCPtr;

    protected btSoftRigidCollisionAlgorithm(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btSoftRigidCollisionAlgorithm_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSoftRigidCollisionAlgorithm(long j, boolean z) {
        this("btSoftRigidCollisionAlgorithm", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btSoftRigidCollisionAlgorithm_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSoftRigidCollisionAlgorithm btsoftrigidcollisionalgorithm) {
        if (btsoftrigidcollisionalgorithm == null) {
            return 0L;
        }
        return btsoftrigidcollisionalgorithm.swigCPtr;
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
                SoftbodyJNI.delete_btSoftRigidCollisionAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSoftRigidCollisionAlgorithm(btPersistentManifold btpersistentmanifold, btCollisionAlgorithmConstructionInfo btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2, boolean z) {
        this(SoftbodyJNI.new_btSoftRigidCollisionAlgorithm(btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold, btCollisionAlgorithmConstructionInfo.getCPtr(btcollisionalgorithmconstructioninfo), btcollisionalgorithmconstructioninfo, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2, z), true);
    }

    public static class CreateFunc extends btCollisionAlgorithmCreateFunc {
        private long swigCPtr;

        protected CreateFunc(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftRigidCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
            super.reset(SoftbodyJNI.btSoftRigidCollisionAlgorithm_CreateFunc_SWIGUpcast(j), z);
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
                    SoftbodyJNI.delete_btSoftRigidCollisionAlgorithm_CreateFunc(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public CreateFunc() {
            this(SoftbodyJNI.new_btSoftRigidCollisionAlgorithm_CreateFunc(), true);
        }
    }
}
