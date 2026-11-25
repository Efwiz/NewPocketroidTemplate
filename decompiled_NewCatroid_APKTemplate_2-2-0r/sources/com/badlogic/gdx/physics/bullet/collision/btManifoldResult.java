package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btManifoldResult extends btDiscreteCollisionDetectorInterface.Result {
    private long swigCPtr;

    protected btManifoldResult(String str, long j, boolean z) {
        super(str, CollisionJNI.btManifoldResult_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btManifoldResult(long j, boolean z) {
        this("btManifoldResult", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface.Result, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btManifoldResult_SWIGUpcast(j), z);
    }

    public static long getCPtr(btManifoldResult btmanifoldresult) {
        if (btmanifoldresult == null) {
            return 0L;
        }
        return btmanifoldresult.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface.Result, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface.Result, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btManifoldResult(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btManifoldResult() {
        this(CollisionJNI.new_btManifoldResult__SWIG_0(), true);
    }

    public btManifoldResult(btCollisionObjectWrapper btcollisionobjectwrapper, btCollisionObjectWrapper btcollisionobjectwrapper2) {
        this(CollisionJNI.new_btManifoldResult__SWIG_1(btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper2), btcollisionobjectwrapper2), true);
    }

    public void setPersistentManifold(btPersistentManifold btpersistentmanifold) {
        CollisionJNI.btManifoldResult_setPersistentManifold(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public btPersistentManifold getPersistentManifoldConst() {
        long jBtManifoldResult_getPersistentManifoldConst = CollisionJNI.btManifoldResult_getPersistentManifoldConst(this.swigCPtr, this);
        if (jBtManifoldResult_getPersistentManifoldConst == 0) {
            return null;
        }
        return new btPersistentManifold(jBtManifoldResult_getPersistentManifoldConst, false);
    }

    public btPersistentManifold getPersistentManifold() {
        long jBtManifoldResult_getPersistentManifold = CollisionJNI.btManifoldResult_getPersistentManifold(this.swigCPtr, this);
        if (jBtManifoldResult_getPersistentManifold == 0) {
            return null;
        }
        return new btPersistentManifold(jBtManifoldResult_getPersistentManifold, false);
    }

    public void refreshContactPoints() {
        CollisionJNI.btManifoldResult_refreshContactPoints(this.swigCPtr, this);
    }

    public btCollisionObjectWrapper getBody0Wrap() {
        return btCollisionObjectWrapper.internalTemp(CollisionJNI.btManifoldResult_getBody0Wrap(this.swigCPtr, this), false);
    }

    public btCollisionObjectWrapper getBody1Wrap() {
        return btCollisionObjectWrapper.internalTemp(CollisionJNI.btManifoldResult_getBody1Wrap(this.swigCPtr, this), false);
    }

    public void setBody0Wrap(btCollisionObjectWrapper btcollisionobjectwrapper) {
        CollisionJNI.btManifoldResult_setBody0Wrap(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper);
    }

    public void setBody1Wrap(btCollisionObjectWrapper btcollisionobjectwrapper) {
        CollisionJNI.btManifoldResult_setBody1Wrap(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper);
    }

    public btCollisionObject getBody0Internal() {
        return btCollisionObject.getInstance(CollisionJNI.btManifoldResult_getBody0Internal(this.swigCPtr, this), false);
    }

    public btCollisionObject getBody1Internal() {
        return btCollisionObject.getInstance(CollisionJNI.btManifoldResult_getBody1Internal(this.swigCPtr, this), false);
    }

    public void setClosestPointDistanceThreshold(float f) {
        CollisionJNI.btManifoldResult_closestPointDistanceThreshold_set(this.swigCPtr, this, f);
    }

    public float getClosestPointDistanceThreshold() {
        return CollisionJNI.btManifoldResult_closestPointDistanceThreshold_get(this.swigCPtr, this);
    }

    public static float calculateCombinedRestitution(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        return CollisionJNI.btManifoldResult_calculateCombinedRestitution(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public static float calculateCombinedFriction(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        return CollisionJNI.btManifoldResult_calculateCombinedFriction(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public static float calculateCombinedRollingFriction(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        return CollisionJNI.btManifoldResult_calculateCombinedRollingFriction(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public static float calculateCombinedSpinningFriction(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        return CollisionJNI.btManifoldResult_calculateCombinedSpinningFriction(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public static float calculateCombinedContactDamping(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        return CollisionJNI.btManifoldResult_calculateCombinedContactDamping(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public static float calculateCombinedContactStiffness(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        return CollisionJNI.btManifoldResult_calculateCombinedContactStiffness(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }
}
