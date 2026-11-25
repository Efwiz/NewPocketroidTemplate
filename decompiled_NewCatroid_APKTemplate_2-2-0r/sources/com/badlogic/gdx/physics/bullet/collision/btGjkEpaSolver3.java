package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btGjkEpaSolver3 extends BulletBase {
    private long swigCPtr;

    protected btGjkEpaSolver3(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGjkEpaSolver3(long j, boolean z) {
        this("btGjkEpaSolver3", j, z);
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

    public static long getCPtr(btGjkEpaSolver3 btgjkepasolver3) {
        if (btgjkepasolver3 == null) {
            return 0L;
        }
        return btgjkepasolver3.swigCPtr;
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
                CollisionJNI.delete_btGjkEpaSolver3(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class sResults extends BulletBase {
        private long swigCPtr;

        public static final class eStatus {
            public static final int EPA_Failed = 3;
            public static final int GJK_Failed = 2;
            public static final int Penetrating = 1;
            public static final int Separated = 0;
        }

        protected sResults(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public sResults(long j, boolean z) {
            this("sResults", j, z);
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

        public static long getCPtr(sResults sresults) {
            if (sresults == null) {
                return 0L;
            }
            return sresults.swigCPtr;
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
                    CollisionJNI.delete_btGjkEpaSolver3_sResults(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setStatus(int i) {
            CollisionJNI.btGjkEpaSolver3_sResults_status_set(this.swigCPtr, this, i);
        }

        public int getStatus() {
            return CollisionJNI.btGjkEpaSolver3_sResults_status_get(this.swigCPtr, this);
        }

        public void setWitnesses(btVector3 btvector3) {
            CollisionJNI.btGjkEpaSolver3_sResults_witnesses_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getWitnesses() {
            long jBtGjkEpaSolver3_sResults_witnesses_get = CollisionJNI.btGjkEpaSolver3_sResults_witnesses_get(this.swigCPtr, this);
            if (jBtGjkEpaSolver3_sResults_witnesses_get == 0) {
                return null;
            }
            return new btVector3(jBtGjkEpaSolver3_sResults_witnesses_get, false);
        }

        public void setNormal(btVector3 btvector3) {
            CollisionJNI.btGjkEpaSolver3_sResults_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getNormal() {
            long jBtGjkEpaSolver3_sResults_normal_get = CollisionJNI.btGjkEpaSolver3_sResults_normal_get(this.swigCPtr, this);
            if (jBtGjkEpaSolver3_sResults_normal_get == 0) {
                return null;
            }
            return new btVector3(jBtGjkEpaSolver3_sResults_normal_get, false);
        }

        public void setDistance(float f) {
            CollisionJNI.btGjkEpaSolver3_sResults_distance_set(this.swigCPtr, this, f);
        }

        public float getDistance() {
            return CollisionJNI.btGjkEpaSolver3_sResults_distance_get(this.swigCPtr, this);
        }

        public sResults() {
            this(CollisionJNI.new_btGjkEpaSolver3_sResults(), true);
        }
    }

    public btGjkEpaSolver3() {
        this(CollisionJNI.new_btGjkEpaSolver3(), true);
    }
}
