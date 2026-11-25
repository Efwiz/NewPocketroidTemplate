package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class _btMprSimplex_t extends BulletBase {
    private long swigCPtr;

    protected _btMprSimplex_t(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public _btMprSimplex_t(long j, boolean z) {
        this("_btMprSimplex_t", j, z);
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

    public static long getCPtr(_btMprSimplex_t _btmprsimplex_t) {
        if (_btmprsimplex_t == null) {
            return 0L;
        }
        return _btmprsimplex_t.swigCPtr;
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
                CollisionJNI.delete__btMprSimplex_t(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPs(_btMprSupport_t _btmprsupport_t) {
        CollisionJNI._btMprSimplex_t_ps_set(this.swigCPtr, this, _btMprSupport_t.getCPtr(_btmprsupport_t), _btmprsupport_t);
    }

    public _btMprSupport_t getPs() {
        long j_btMprSimplex_t_ps_get = CollisionJNI._btMprSimplex_t_ps_get(this.swigCPtr, this);
        if (j_btMprSimplex_t_ps_get == 0) {
            return null;
        }
        return new _btMprSupport_t(j_btMprSimplex_t_ps_get, false);
    }

    public void setLast(int i) {
        CollisionJNI._btMprSimplex_t_last_set(this.swigCPtr, this, i);
    }

    public int getLast() {
        return CollisionJNI._btMprSimplex_t_last_get(this.swigCPtr, this);
    }

    public _btMprSimplex_t() {
        this(CollisionJNI.new__btMprSimplex_t(), true);
    }
}
