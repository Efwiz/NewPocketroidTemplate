package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_RSORT_TOKEN_COMPARATOR extends BulletBase {
    private long swigCPtr;

    protected GIM_RSORT_TOKEN_COMPARATOR(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_RSORT_TOKEN_COMPARATOR(long j, boolean z) {
        this("GIM_RSORT_TOKEN_COMPARATOR", j, z);
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

    public static long getCPtr(GIM_RSORT_TOKEN_COMPARATOR gim_rsort_token_comparator) {
        if (gim_rsort_token_comparator == null) {
            return 0L;
        }
        return gim_rsort_token_comparator.swigCPtr;
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
                CollisionJNI.delete_GIM_RSORT_TOKEN_COMPARATOR(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public int operatorFunctionCall(GIM_RSORT_TOKEN gim_rsort_token, GIM_RSORT_TOKEN gim_rsort_token2) {
        return CollisionJNI.GIM_RSORT_TOKEN_COMPARATOR_operatorFunctionCall(this.swigCPtr, this, GIM_RSORT_TOKEN.getCPtr(gim_rsort_token), gim_rsort_token, GIM_RSORT_TOKEN.getCPtr(gim_rsort_token2), gim_rsort_token2);
    }

    public GIM_RSORT_TOKEN_COMPARATOR() {
        this(CollisionJNI.new_GIM_RSORT_TOKEN_COMPARATOR(), true);
    }
}
