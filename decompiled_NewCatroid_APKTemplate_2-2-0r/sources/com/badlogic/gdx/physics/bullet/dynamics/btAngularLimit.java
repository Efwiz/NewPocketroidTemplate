package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btAngularLimit extends BulletBase {
    private long swigCPtr;

    protected btAngularLimit(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btAngularLimit(long j, boolean z) {
        this("btAngularLimit", j, z);
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

    public static long getCPtr(btAngularLimit btangularlimit) {
        if (btangularlimit == null) {
            return 0L;
        }
        return btangularlimit.swigCPtr;
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
                DynamicsJNI.delete_btAngularLimit(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btAngularLimit() {
        this(DynamicsJNI.new_btAngularLimit(), true);
    }

    public void set(float f, float f2, float f3, float f4, float f5) {
        DynamicsJNI.btAngularLimit_set__SWIG_0(this.swigCPtr, this, f, f2, f3, f4, f5);
    }

    public void set(float f, float f2, float f3, float f4) {
        DynamicsJNI.btAngularLimit_set__SWIG_1(this.swigCPtr, this, f, f2, f3, f4);
    }

    public void set(float f, float f2, float f3) {
        DynamicsJNI.btAngularLimit_set__SWIG_2(this.swigCPtr, this, f, f2, f3);
    }

    public void set(float f, float f2) {
        DynamicsJNI.btAngularLimit_set__SWIG_3(this.swigCPtr, this, f, f2);
    }

    public void test(float f) {
        DynamicsJNI.btAngularLimit_test(this.swigCPtr, this, f);
    }

    public float getSoftness() {
        return DynamicsJNI.btAngularLimit_getSoftness(this.swigCPtr, this);
    }

    public float getBiasFactor() {
        return DynamicsJNI.btAngularLimit_getBiasFactor(this.swigCPtr, this);
    }

    public float getRelaxationFactor() {
        return DynamicsJNI.btAngularLimit_getRelaxationFactor(this.swigCPtr, this);
    }

    public float getCorrection() {
        return DynamicsJNI.btAngularLimit_getCorrection(this.swigCPtr, this);
    }

    public float getSign() {
        return DynamicsJNI.btAngularLimit_getSign(this.swigCPtr, this);
    }

    public float getHalfRange() {
        return DynamicsJNI.btAngularLimit_getHalfRange(this.swigCPtr, this);
    }

    public boolean isLimit() {
        return DynamicsJNI.btAngularLimit_isLimit(this.swigCPtr, this);
    }

    public void fit(SWIGTYPE_p_float sWIGTYPE_p_float) {
        DynamicsJNI.btAngularLimit_fit(this.swigCPtr, this, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public float getError() {
        return DynamicsJNI.btAngularLimit_getError(this.swigCPtr, this);
    }

    public float getLow() {
        return DynamicsJNI.btAngularLimit_getLow(this.swigCPtr, this);
    }

    public float getHigh() {
        return DynamicsJNI.btAngularLimit_getHigh(this.swigCPtr, this);
    }
}
