package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class _btMprSupport_t extends BulletBase {
    private long swigCPtr;

    protected _btMprSupport_t(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public _btMprSupport_t(long j, boolean z) {
        this("_btMprSupport_t", j, z);
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

    public static long getCPtr(_btMprSupport_t _btmprsupport_t) {
        if (_btmprsupport_t == null) {
            return 0L;
        }
        return _btmprsupport_t.swigCPtr;
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
                CollisionJNI.delete__btMprSupport_t(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setV(btVector3 btvector3) {
        CollisionJNI._btMprSupport_t_v_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getV() {
        long j_btMprSupport_t_v_get = CollisionJNI._btMprSupport_t_v_get(this.swigCPtr, this);
        if (j_btMprSupport_t_v_get == 0) {
            return null;
        }
        return new btVector3(j_btMprSupport_t_v_get, false);
    }

    public void setV1(btVector3 btvector3) {
        CollisionJNI._btMprSupport_t_v1_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getV1() {
        long j_btMprSupport_t_v1_get = CollisionJNI._btMprSupport_t_v1_get(this.swigCPtr, this);
        if (j_btMprSupport_t_v1_get == 0) {
            return null;
        }
        return new btVector3(j_btMprSupport_t_v1_get, false);
    }

    public void setV2(btVector3 btvector3) {
        CollisionJNI._btMprSupport_t_v2_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getV2() {
        long j_btMprSupport_t_v2_get = CollisionJNI._btMprSupport_t_v2_get(this.swigCPtr, this);
        if (j_btMprSupport_t_v2_get == 0) {
            return null;
        }
        return new btVector3(j_btMprSupport_t_v2_get, false);
    }

    public _btMprSupport_t() {
        this(CollisionJNI.new__btMprSupport_t(), true);
    }
}
