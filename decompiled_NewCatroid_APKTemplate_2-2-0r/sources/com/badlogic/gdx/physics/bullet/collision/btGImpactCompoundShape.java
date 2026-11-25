package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;

/* JADX INFO: loaded from: classes8.dex */
public class btGImpactCompoundShape extends btGImpactShapeInterface {
    private long swigCPtr;

    protected btGImpactCompoundShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btGImpactCompoundShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGImpactCompoundShape(long j, boolean z) {
        this("btGImpactCompoundShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGImpactShapeInterface, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btGImpactCompoundShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGImpactCompoundShape btgimpactcompoundshape) {
        if (btgimpactcompoundshape == null) {
            return 0L;
        }
        return btgimpactcompoundshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGImpactShapeInterface, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGImpactShapeInterface, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btGImpactCompoundShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class CompoundPrimitiveManager extends btPrimitiveManagerBase {
        private long swigCPtr;

        protected CompoundPrimitiveManager(String str, long j, boolean z) {
            super(str, CollisionJNI.btGImpactCompoundShape_CompoundPrimitiveManager_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public CompoundPrimitiveManager(long j, boolean z) {
            this("CompoundPrimitiveManager", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btPrimitiveManagerBase, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(CollisionJNI.btGImpactCompoundShape_CompoundPrimitiveManager_SWIGUpcast(j), z);
        }

        public static long getCPtr(CompoundPrimitiveManager compoundPrimitiveManager) {
            if (compoundPrimitiveManager == null) {
                return 0L;
            }
            return compoundPrimitiveManager.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btPrimitiveManagerBase, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btPrimitiveManagerBase, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    CollisionJNI.delete_btGImpactCompoundShape_CompoundPrimitiveManager(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setCompoundShape(btGImpactCompoundShape btgimpactcompoundshape) {
            CollisionJNI.btGImpactCompoundShape_CompoundPrimitiveManager_compoundShape_set(this.swigCPtr, this, btGImpactCompoundShape.getCPtr(btgimpactcompoundshape), btgimpactcompoundshape);
        }

        public btGImpactCompoundShape getCompoundShape() {
            long jBtGImpactCompoundShape_CompoundPrimitiveManager_compoundShape_get = CollisionJNI.btGImpactCompoundShape_CompoundPrimitiveManager_compoundShape_get(this.swigCPtr, this);
            if (jBtGImpactCompoundShape_CompoundPrimitiveManager_compoundShape_get == 0) {
                return null;
            }
            return new btGImpactCompoundShape(jBtGImpactCompoundShape_CompoundPrimitiveManager_compoundShape_get, false);
        }

        public CompoundPrimitiveManager(CompoundPrimitiveManager compoundPrimitiveManager) {
            this(CollisionJNI.new_btGImpactCompoundShape_CompoundPrimitiveManager__SWIG_0(getCPtr(compoundPrimitiveManager), compoundPrimitiveManager), true);
        }

        public CompoundPrimitiveManager(btGImpactCompoundShape btgimpactcompoundshape) {
            this(CollisionJNI.new_btGImpactCompoundShape_CompoundPrimitiveManager__SWIG_1(btGImpactCompoundShape.getCPtr(btgimpactcompoundshape), btgimpactcompoundshape), true);
        }

        public CompoundPrimitiveManager() {
            this(CollisionJNI.new_btGImpactCompoundShape_CompoundPrimitiveManager__SWIG_2(), true);
        }
    }

    public btGImpactCompoundShape(boolean z) {
        this(CollisionJNI.new_btGImpactCompoundShape__SWIG_0(z), true);
    }

    public btGImpactCompoundShape() {
        this(CollisionJNI.new_btGImpactCompoundShape__SWIG_1(), true);
    }

    public CompoundPrimitiveManager getCompoundPrimitiveManager() {
        long jBtGImpactCompoundShape_getCompoundPrimitiveManager = CollisionJNI.btGImpactCompoundShape_getCompoundPrimitiveManager(this.swigCPtr, this);
        if (jBtGImpactCompoundShape_getCompoundPrimitiveManager == 0) {
            return null;
        }
        return new CompoundPrimitiveManager(jBtGImpactCompoundShape_getCompoundPrimitiveManager, false);
    }

    public void addChildShape(Matrix4 matrix4, btCollisionShape btcollisionshape) {
        CollisionJNI.btGImpactCompoundShape_addChildShape__SWIG_0(this.swigCPtr, this, matrix4, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    public void addChildShape(btCollisionShape btcollisionshape) {
        CollisionJNI.btGImpactCompoundShape_addChildShape__SWIG_1(this.swigCPtr, this, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }
}
