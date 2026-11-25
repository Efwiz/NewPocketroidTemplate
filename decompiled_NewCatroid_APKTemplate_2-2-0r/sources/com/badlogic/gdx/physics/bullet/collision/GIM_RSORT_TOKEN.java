package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_RSORT_TOKEN extends BulletBase {
    private long swigCPtr;

    protected GIM_RSORT_TOKEN(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_RSORT_TOKEN(long j, boolean z) {
        this("GIM_RSORT_TOKEN", j, z);
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

    public static long getCPtr(GIM_RSORT_TOKEN gim_rsort_token) {
        if (gim_rsort_token == null) {
            return 0L;
        }
        return gim_rsort_token.swigCPtr;
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
                CollisionJNI.delete_GIM_RSORT_TOKEN(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setKey(long j) {
        CollisionJNI.GIM_RSORT_TOKEN_key_set(this.swigCPtr, this, j);
    }

    public long getKey() {
        return CollisionJNI.GIM_RSORT_TOKEN_key_get(this.swigCPtr, this);
    }

    public void setValue(long j) {
        CollisionJNI.GIM_RSORT_TOKEN_value_set(this.swigCPtr, this, j);
    }

    public long getValue() {
        return CollisionJNI.GIM_RSORT_TOKEN_value_get(this.swigCPtr, this);
    }

    public GIM_RSORT_TOKEN() {
        this(CollisionJNI.new_GIM_RSORT_TOKEN__SWIG_0(), true);
    }

    public GIM_RSORT_TOKEN(GIM_RSORT_TOKEN gim_rsort_token) {
        this(CollisionJNI.new_GIM_RSORT_TOKEN__SWIG_1(getCPtr(gim_rsort_token), gim_rsort_token), true);
    }

    public boolean operatorLessThan(GIM_RSORT_TOKEN gim_rsort_token) {
        return CollisionJNI.GIM_RSORT_TOKEN_operatorLessThan(this.swigCPtr, this, getCPtr(gim_rsort_token), gim_rsort_token);
    }

    public boolean operatorGreaterThan(GIM_RSORT_TOKEN gim_rsort_token) {
        return CollisionJNI.GIM_RSORT_TOKEN_operatorGreaterThan(this.swigCPtr, this, getCPtr(gim_rsort_token), gim_rsort_token);
    }
}
