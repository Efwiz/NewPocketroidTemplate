package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.utils.Pool;

/* JADX INFO: loaded from: classes8.dex */
public class btBroadphasePair extends BulletBase {
    private long swigCPtr;
    protected static final btBroadphasePair temp = new btBroadphasePair(0L, false);
    protected static final Pool<btBroadphasePair> pool = new Pool<btBroadphasePair>() { // from class: com.badlogic.gdx.physics.bullet.collision.btBroadphasePair.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public btBroadphasePair newObject() {
            return new btBroadphasePair(0L, false);
        }
    };

    protected btBroadphasePair(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBroadphasePair(long j, boolean z) {
        this("btBroadphasePair", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(j, z);
    }

    public static long getCPtr(btBroadphasePair btbroadphasepair) {
        if (btbroadphasepair == null) {
            return 0L;
        }
        return btbroadphasepair.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btBroadphasePair(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static btBroadphasePair internalTemp(long j, boolean z) {
        btBroadphasePair btbroadphasepair = temp;
        btbroadphasepair.reset(j, z);
        return btbroadphasepair;
    }

    public static btBroadphasePair obtain(long j, boolean z) {
        btBroadphasePair btbroadphasepairObtain = pool.obtain();
        btbroadphasepairObtain.reset(j, z);
        return btbroadphasepairObtain;
    }

    public static void free(btBroadphasePair btbroadphasepair) {
        btbroadphasepair.dispose();
        pool.free(btbroadphasepair);
    }

    public btBroadphasePair() {
        this(CollisionJNI.new_btBroadphasePair__SWIG_0(), true);
    }

    public long operatorNew(long j) {
        return CollisionJNI.btBroadphasePair_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btBroadphasePair_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btBroadphasePair_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btBroadphasePair_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btBroadphasePair_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btBroadphasePair_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btBroadphasePair_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btBroadphasePair_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btBroadphasePair(btBroadphasePair btbroadphasepair) {
        this(CollisionJNI.new_btBroadphasePair__SWIG_1(btbroadphasepair), true);
    }

    public btBroadphasePair(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        this(CollisionJNI.new_btBroadphasePair__SWIG_2(btbroadphaseproxy, btbroadphaseproxy2), true);
    }

    public void setPProxy0(btBroadphaseProxy btbroadphaseproxy) {
        CollisionJNI.btBroadphasePair_pProxy0_set(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    public btBroadphaseProxy getPProxy0() {
        return btBroadphaseProxy.internalTemp(CollisionJNI.btBroadphasePair_pProxy0_get(this.swigCPtr, this), false);
    }

    public void setPProxy1(btBroadphaseProxy btbroadphaseproxy) {
        CollisionJNI.btBroadphasePair_pProxy1_set(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    public btBroadphaseProxy getPProxy1() {
        return btBroadphaseProxy.internalTemp(CollisionJNI.btBroadphasePair_pProxy1_get(this.swigCPtr, this), false);
    }

    public void setAlgorithm(btCollisionAlgorithm btcollisionalgorithm) {
        CollisionJNI.btBroadphasePair_algorithm_set(this.swigCPtr, this, btCollisionAlgorithm.getCPtr(btcollisionalgorithm), btcollisionalgorithm);
    }

    public btCollisionAlgorithm getAlgorithm() {
        long jBtBroadphasePair_algorithm_get = CollisionJNI.btBroadphasePair_algorithm_get(this.swigCPtr, this);
        if (jBtBroadphasePair_algorithm_get == 0) {
            return null;
        }
        return new btCollisionAlgorithm(jBtBroadphasePair_algorithm_get, false);
    }

    public void setInternalInfo1(long j) {
        CollisionJNI.btBroadphasePair_internalInfo1_set(this.swigCPtr, this, j);
    }

    public long getInternalInfo1() {
        return CollisionJNI.btBroadphasePair_internalInfo1_get(this.swigCPtr, this);
    }

    public void setInternalTmpValue(int i) {
        CollisionJNI.btBroadphasePair_internalTmpValue_set(this.swigCPtr, this, i);
    }

    public int getInternalTmpValue() {
        return CollisionJNI.btBroadphasePair_internalTmpValue_get(this.swigCPtr, this);
    }
}
