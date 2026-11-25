package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btIntSortPredicate extends BulletBase {
    private long swigCPtr;

    protected btIntSortPredicate(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btIntSortPredicate(long j, boolean z) {
        this("btIntSortPredicate", j, z);
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

    public static long getCPtr(btIntSortPredicate btintsortpredicate) {
        if (btintsortpredicate == null) {
            return 0L;
        }
        return btintsortpredicate.swigCPtr;
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
                LinearMathJNI.delete_btIntSortPredicate(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public boolean operatorFunctionCall(int i, int i2) {
        return LinearMathJNI.btIntSortPredicate_operatorFunctionCall(this.swigCPtr, this, i, i2);
    }

    public btIntSortPredicate() {
        this(LinearMathJNI.new_btIntSortPredicate(), true);
    }
}
