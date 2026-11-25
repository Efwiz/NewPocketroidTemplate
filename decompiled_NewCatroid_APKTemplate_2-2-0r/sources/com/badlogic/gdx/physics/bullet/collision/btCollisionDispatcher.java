package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionDispatcher extends btDispatcher {
    private long swigCPtr;

    public static final class DispatcherFlags {
        public static final int CD_DISABLE_CONTACTPOOL_DYNAMIC_ALLOCATION = 4;
        public static final int CD_STATIC_STATIC_REPORTED = 1;
        public static final int CD_USE_RELATIVE_CONTACT_BREAKING_THRESHOLD = 2;
    }

    protected btCollisionDispatcher(String str, long j, boolean z) {
        super(str, CollisionJNI.btCollisionDispatcher_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btCollisionDispatcher(long j, boolean z) {
        this("btCollisionDispatcher", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDispatcher, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btCollisionDispatcher_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCollisionDispatcher btcollisiondispatcher) {
        if (btcollisiondispatcher == null) {
            return 0L;
        }
        return btcollisiondispatcher.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDispatcher, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDispatcher, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btCollisionDispatcher(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public int getDispatcherFlags() {
        return CollisionJNI.btCollisionDispatcher_getDispatcherFlags(this.swigCPtr, this);
    }

    public void setDispatcherFlags(int i) {
        CollisionJNI.btCollisionDispatcher_setDispatcherFlags(this.swigCPtr, this, i);
    }

    public void registerCollisionCreateFunc(int i, int i2, btCollisionAlgorithmCreateFunc btcollisionalgorithmcreatefunc) {
        CollisionJNI.btCollisionDispatcher_registerCollisionCreateFunc(this.swigCPtr, this, i, i2, btCollisionAlgorithmCreateFunc.getCPtr(btcollisionalgorithmcreatefunc), btcollisionalgorithmcreatefunc);
    }

    public void registerClosestPointsCreateFunc(int i, int i2, btCollisionAlgorithmCreateFunc btcollisionalgorithmcreatefunc) {
        CollisionJNI.btCollisionDispatcher_registerClosestPointsCreateFunc(this.swigCPtr, this, i, i2, btCollisionAlgorithmCreateFunc.getCPtr(btcollisionalgorithmcreatefunc), btcollisionalgorithmcreatefunc);
    }

    public btPersistentManifold getManifoldByIndexInternalConst(int i) {
        long jBtCollisionDispatcher_getManifoldByIndexInternalConst = CollisionJNI.btCollisionDispatcher_getManifoldByIndexInternalConst(this.swigCPtr, this, i);
        if (jBtCollisionDispatcher_getManifoldByIndexInternalConst == 0) {
            return null;
        }
        return new btPersistentManifold(jBtCollisionDispatcher_getManifoldByIndexInternalConst, false);
    }

    public btCollisionDispatcher(btCollisionConfiguration btcollisionconfiguration) {
        this(CollisionJNI.new_btCollisionDispatcher(btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
    }

    public void setNearCallback(SWIGTYPE_p_f_r_btBroadphasePair_r_btCollisionDispatcher_r_q_const__btDispatcherInfo__void sWIGTYPE_p_f_r_btBroadphasePair_r_btCollisionDispatcher_r_q_const__btDispatcherInfo__void) {
        CollisionJNI.btCollisionDispatcher_setNearCallback(this.swigCPtr, this, SWIGTYPE_p_f_r_btBroadphasePair_r_btCollisionDispatcher_r_q_const__btDispatcherInfo__void.getCPtr(sWIGTYPE_p_f_r_btBroadphasePair_r_btCollisionDispatcher_r_q_const__btDispatcherInfo__void));
    }

    public SWIGTYPE_p_f_r_btBroadphasePair_r_btCollisionDispatcher_r_q_const__btDispatcherInfo__void getNearCallback() {
        long jBtCollisionDispatcher_getNearCallback = CollisionJNI.btCollisionDispatcher_getNearCallback(this.swigCPtr, this);
        if (jBtCollisionDispatcher_getNearCallback == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_r_btBroadphasePair_r_btCollisionDispatcher_r_q_const__btDispatcherInfo__void(jBtCollisionDispatcher_getNearCallback, false);
    }

    public static void defaultNearCallback(btBroadphasePair btbroadphasepair, btCollisionDispatcher btcollisiondispatcher, btDispatcherInfo btdispatcherinfo) {
        CollisionJNI.btCollisionDispatcher_defaultNearCallback(btbroadphasepair, getCPtr(btcollisiondispatcher), btcollisiondispatcher, btDispatcherInfo.getCPtr(btdispatcherinfo), btdispatcherinfo);
    }

    public btCollisionConfiguration getCollisionConfiguration() {
        long jBtCollisionDispatcher_getCollisionConfiguration = CollisionJNI.btCollisionDispatcher_getCollisionConfiguration(this.swigCPtr, this);
        if (jBtCollisionDispatcher_getCollisionConfiguration == 0) {
            return null;
        }
        return new btCollisionConfiguration(jBtCollisionDispatcher_getCollisionConfiguration, false);
    }

    public btCollisionConfiguration getCollisionConfigurationConst() {
        long jBtCollisionDispatcher_getCollisionConfigurationConst = CollisionJNI.btCollisionDispatcher_getCollisionConfigurationConst(this.swigCPtr, this);
        if (jBtCollisionDispatcher_getCollisionConfigurationConst == 0) {
            return null;
        }
        return new btCollisionConfiguration(jBtCollisionDispatcher_getCollisionConfigurationConst, false);
    }

    public void setCollisionConfiguration(btCollisionConfiguration btcollisionconfiguration) {
        CollisionJNI.btCollisionDispatcher_setCollisionConfiguration(this.swigCPtr, this, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration);
    }
}
