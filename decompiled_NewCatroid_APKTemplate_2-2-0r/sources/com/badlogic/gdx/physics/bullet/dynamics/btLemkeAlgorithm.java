package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btLemkeAlgorithm extends BulletBase {
    private long swigCPtr;

    protected btLemkeAlgorithm(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btLemkeAlgorithm(long j, boolean z) {
        this("btLemkeAlgorithm", j, z);
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

    public static long getCPtr(btLemkeAlgorithm btlemkealgorithm) {
        if (btlemkealgorithm == null) {
            return 0L;
        }
        return btlemkealgorithm.swigCPtr;
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
                DynamicsJNI.delete_btLemkeAlgorithm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btLemkeAlgorithm(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t, int i) {
        this(DynamicsJNI.new_btLemkeAlgorithm__SWIG_0(SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t), i), true);
    }

    public btLemkeAlgorithm(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t) {
        this(DynamicsJNI.new_btLemkeAlgorithm__SWIG_1(SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t)), true);
    }

    public int getInfo() {
        return DynamicsJNI.btLemkeAlgorithm_getInfo(this.swigCPtr, this);
    }

    public int getSteps() {
        return DynamicsJNI.btLemkeAlgorithm_getSteps(this.swigCPtr, this);
    }

    public void setSystem(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t) {
        DynamicsJNI.btLemkeAlgorithm_setSystem(this.swigCPtr, this, SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t));
    }

    public SWIGTYPE_p_btVectorXT_float_t solve(long j) {
        return new SWIGTYPE_p_btVectorXT_float_t(DynamicsJNI.btLemkeAlgorithm_solve__SWIG_0(this.swigCPtr, this, j), true);
    }

    public SWIGTYPE_p_btVectorXT_float_t solve() {
        return new SWIGTYPE_p_btVectorXT_float_t(DynamicsJNI.btLemkeAlgorithm_solve__SWIG_1(this.swigCPtr, this), true);
    }
}
