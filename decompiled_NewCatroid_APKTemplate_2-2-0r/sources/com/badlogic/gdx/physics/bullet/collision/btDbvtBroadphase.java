package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btDbvtBroadphase extends btBroadphaseInterface {
    public static final int DYNAMIC_SET = 0;
    public static final int FIXED_SET = 1;
    public static final int STAGECOUNT = 2;
    private long swigCPtr;

    protected btDbvtBroadphase(String str, long j, boolean z) {
        super(str, CollisionJNI.btDbvtBroadphase_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDbvtBroadphase(long j, boolean z) {
        this("btDbvtBroadphase", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btDbvtBroadphase_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDbvtBroadphase btdbvtbroadphase) {
        if (btdbvtbroadphase == null) {
            return 0L;
        }
        return btdbvtbroadphase.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btDbvtBroadphase(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setSets(btDbvt btdbvt) {
        CollisionJNI.btDbvtBroadphase_sets_set(this.swigCPtr, this, btDbvt.getCPtr(btdbvt), btdbvt);
    }

    public btDbvt getSets() {
        long jBtDbvtBroadphase_sets_get = CollisionJNI.btDbvtBroadphase_sets_get(this.swigCPtr, this);
        if (jBtDbvtBroadphase_sets_get == 0) {
            return null;
        }
        return new btDbvt(jBtDbvtBroadphase_sets_get, false);
    }

    public void setStageRoots(SWIGTYPE_p_p_btDbvtProxy sWIGTYPE_p_p_btDbvtProxy) {
        CollisionJNI.btDbvtBroadphase_stageRoots_set(this.swigCPtr, this, SWIGTYPE_p_p_btDbvtProxy.getCPtr(sWIGTYPE_p_p_btDbvtProxy));
    }

    public SWIGTYPE_p_p_btDbvtProxy getStageRoots() {
        long jBtDbvtBroadphase_stageRoots_get = CollisionJNI.btDbvtBroadphase_stageRoots_get(this.swigCPtr, this);
        if (jBtDbvtBroadphase_stageRoots_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_p_btDbvtProxy(jBtDbvtBroadphase_stageRoots_get, false);
    }

    public void setPaircache(btOverlappingPairCache btoverlappingpaircache) {
        CollisionJNI.btDbvtBroadphase_paircache_set(this.swigCPtr, this, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache);
    }

    public btOverlappingPairCache getPaircache() {
        long jBtDbvtBroadphase_paircache_get = CollisionJNI.btDbvtBroadphase_paircache_get(this.swigCPtr, this);
        if (jBtDbvtBroadphase_paircache_get == 0) {
            return null;
        }
        return new btOverlappingPairCache(jBtDbvtBroadphase_paircache_get, false);
    }

    public void setPrediction(float f) {
        CollisionJNI.btDbvtBroadphase_prediction_set(this.swigCPtr, this, f);
    }

    public float getPrediction() {
        return CollisionJNI.btDbvtBroadphase_prediction_get(this.swigCPtr, this);
    }

    public void setStageCurrent(int i) {
        CollisionJNI.btDbvtBroadphase_stageCurrent_set(this.swigCPtr, this, i);
    }

    public int getStageCurrent() {
        return CollisionJNI.btDbvtBroadphase_stageCurrent_get(this.swigCPtr, this);
    }

    public void setFupdates(int i) {
        CollisionJNI.btDbvtBroadphase_fupdates_set(this.swigCPtr, this, i);
    }

    public int getFupdates() {
        return CollisionJNI.btDbvtBroadphase_fupdates_get(this.swigCPtr, this);
    }

    public void setDupdates(int i) {
        CollisionJNI.btDbvtBroadphase_dupdates_set(this.swigCPtr, this, i);
    }

    public int getDupdates() {
        return CollisionJNI.btDbvtBroadphase_dupdates_get(this.swigCPtr, this);
    }

    public void setCupdates(int i) {
        CollisionJNI.btDbvtBroadphase_cupdates_set(this.swigCPtr, this, i);
    }

    public int getCupdates() {
        return CollisionJNI.btDbvtBroadphase_cupdates_get(this.swigCPtr, this);
    }

    public void setNewpairs(int i) {
        CollisionJNI.btDbvtBroadphase_newpairs_set(this.swigCPtr, this, i);
    }

    public int getNewpairs() {
        return CollisionJNI.btDbvtBroadphase_newpairs_get(this.swigCPtr, this);
    }

    public void setFixedleft(int i) {
        CollisionJNI.btDbvtBroadphase_fixedleft_set(this.swigCPtr, this, i);
    }

    public int getFixedleft() {
        return CollisionJNI.btDbvtBroadphase_fixedleft_get(this.swigCPtr, this);
    }

    public void setUpdates_call(long j) {
        CollisionJNI.btDbvtBroadphase_updates_call_set(this.swigCPtr, this, j);
    }

    public long getUpdates_call() {
        return CollisionJNI.btDbvtBroadphase_updates_call_get(this.swigCPtr, this);
    }

    public void setUpdates_done(long j) {
        CollisionJNI.btDbvtBroadphase_updates_done_set(this.swigCPtr, this, j);
    }

    public long getUpdates_done() {
        return CollisionJNI.btDbvtBroadphase_updates_done_get(this.swigCPtr, this);
    }

    public void setUpdates_ratio(float f) {
        CollisionJNI.btDbvtBroadphase_updates_ratio_set(this.swigCPtr, this, f);
    }

    public float getUpdates_ratio() {
        return CollisionJNI.btDbvtBroadphase_updates_ratio_get(this.swigCPtr, this);
    }

    public void setPid(int i) {
        CollisionJNI.btDbvtBroadphase_pid_set(this.swigCPtr, this, i);
    }

    public int getPid() {
        return CollisionJNI.btDbvtBroadphase_pid_get(this.swigCPtr, this);
    }

    public void setCid(int i) {
        CollisionJNI.btDbvtBroadphase_cid_set(this.swigCPtr, this, i);
    }

    public int getCid() {
        return CollisionJNI.btDbvtBroadphase_cid_get(this.swigCPtr, this);
    }

    public void setGid(int i) {
        CollisionJNI.btDbvtBroadphase_gid_set(this.swigCPtr, this, i);
    }

    public int getGid() {
        return CollisionJNI.btDbvtBroadphase_gid_get(this.swigCPtr, this);
    }

    public void setReleasepaircache(boolean z) {
        CollisionJNI.btDbvtBroadphase_releasepaircache_set(this.swigCPtr, this, z);
    }

    public boolean getReleasepaircache() {
        return CollisionJNI.btDbvtBroadphase_releasepaircache_get(this.swigCPtr, this);
    }

    public void setDeferedcollide(boolean z) {
        CollisionJNI.btDbvtBroadphase_deferedcollide_set(this.swigCPtr, this, z);
    }

    public boolean getDeferedcollide() {
        return CollisionJNI.btDbvtBroadphase_deferedcollide_get(this.swigCPtr, this);
    }

    public void setNeedcleanup(boolean z) {
        CollisionJNI.btDbvtBroadphase_needcleanup_set(this.swigCPtr, this, z);
    }

    public boolean getNeedcleanup() {
        return CollisionJNI.btDbvtBroadphase_needcleanup_get(this.swigCPtr, this);
    }

    public void setRayTestStacks(SWIGTYPE_p_btAlignedObjectArrayT_btAlignedObjectArrayT_btDbvtNode_const_p_t_t sWIGTYPE_p_btAlignedObjectArrayT_btAlignedObjectArrayT_btDbvtNode_const_p_t_t) {
        CollisionJNI.btDbvtBroadphase_rayTestStacks_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btAlignedObjectArrayT_btDbvtNode_const_p_t_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btAlignedObjectArrayT_btDbvtNode_const_p_t_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btAlignedObjectArrayT_btDbvtNode_const_p_t_t getRayTestStacks() {
        long jBtDbvtBroadphase_rayTestStacks_get = CollisionJNI.btDbvtBroadphase_rayTestStacks_get(this.swigCPtr, this);
        if (jBtDbvtBroadphase_rayTestStacks_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btAlignedObjectArrayT_btDbvtNode_const_p_t_t(jBtDbvtBroadphase_rayTestStacks_get, false);
    }

    public btDbvtBroadphase(btOverlappingPairCache btoverlappingpaircache) {
        this(CollisionJNI.new_btDbvtBroadphase__SWIG_0(btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache), true);
    }

    public btDbvtBroadphase() {
        this(CollisionJNI.new_btDbvtBroadphase__SWIG_1(), true);
    }

    public void collide(btDispatcher btdispatcher) {
        CollisionJNI.btDbvtBroadphase_collide(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void optimize() {
        CollisionJNI.btDbvtBroadphase_optimize(this.swigCPtr, this);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.btDbvtBroadphase_rayTest__SWIG_0(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5, vector6);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5) {
        CollisionJNI.btDbvtBroadphase_rayTest__SWIG_1(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback) {
        CollisionJNI.btDbvtBroadphase_rayTest__SWIG_2(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback);
    }

    public void performDeferredRemoval(btDispatcher btdispatcher) {
        CollisionJNI.btDbvtBroadphase_performDeferredRemoval(this.swigCPtr, this, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void setVelocityPrediction(float f) {
        CollisionJNI.btDbvtBroadphase_setVelocityPrediction(this.swigCPtr, this, f);
    }

    public float getVelocityPrediction() {
        return CollisionJNI.btDbvtBroadphase_getVelocityPrediction(this.swigCPtr, this);
    }

    public void setAabbForceUpdate(btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4, btDispatcher btdispatcher) {
        CollisionJNI.btDbvtBroadphase_setAabbForceUpdate(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, vector3, vector4, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public static void benchmark(btBroadphaseInterface btbroadphaseinterface) {
        CollisionJNI.btDbvtBroadphase_benchmark(btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface);
    }

    public btDbvt getSet(int i) {
        long jBtDbvtBroadphase_getSet = CollisionJNI.btDbvtBroadphase_getSet(this.swigCPtr, this, i);
        if (jBtDbvtBroadphase_getSet == 0) {
            return null;
        }
        return new btDbvt(jBtDbvtBroadphase_getSet, false);
    }

    public btDbvt getSet0() {
        long jBtDbvtBroadphase_getSet0 = CollisionJNI.btDbvtBroadphase_getSet0(this.swigCPtr, this);
        if (jBtDbvtBroadphase_getSet0 == 0) {
            return null;
        }
        return new btDbvt(jBtDbvtBroadphase_getSet0, false);
    }

    public btDbvt getSet1() {
        long jBtDbvtBroadphase_getSet1 = CollisionJNI.btDbvtBroadphase_getSet1(this.swigCPtr, this);
        if (jBtDbvtBroadphase_getSet1 == 0) {
            return null;
        }
        return new btDbvt(jBtDbvtBroadphase_getSet1, false);
    }
}
