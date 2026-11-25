package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_PAIR extends BulletBase {
    private long swigCPtr;

    protected GIM_PAIR(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_PAIR(long j, boolean z) {
        this("GIM_PAIR", j, z);
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

    public static long getCPtr(GIM_PAIR gim_pair) {
        if (gim_pair == null) {
            return 0L;
        }
        return gim_pair.swigCPtr;
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
                CollisionJNI.delete_GIM_PAIR(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setIndex1(int i) {
        CollisionJNI.GIM_PAIR_index1_set(this.swigCPtr, this, i);
    }

    public int getIndex1() {
        return CollisionJNI.GIM_PAIR_index1_get(this.swigCPtr, this);
    }

    public void setIndex2(int i) {
        CollisionJNI.GIM_PAIR_index2_set(this.swigCPtr, this, i);
    }

    public int getIndex2() {
        return CollisionJNI.GIM_PAIR_index2_get(this.swigCPtr, this);
    }

    public GIM_PAIR() {
        this(CollisionJNI.new_GIM_PAIR__SWIG_0(), true);
    }

    public GIM_PAIR(GIM_PAIR gim_pair) {
        this(CollisionJNI.new_GIM_PAIR__SWIG_1(getCPtr(gim_pair), gim_pair), true);
    }

    public GIM_PAIR(int i, int i2) {
        this(CollisionJNI.new_GIM_PAIR__SWIG_2(i, i2), true);
    }
}
