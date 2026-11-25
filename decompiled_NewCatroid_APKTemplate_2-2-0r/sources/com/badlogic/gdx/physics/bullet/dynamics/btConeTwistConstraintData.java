package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btConeTwistConstraintData extends BulletBase {
    private long swigCPtr;

    protected btConeTwistConstraintData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConeTwistConstraintData(long j, boolean z) {
        this(DynamicsConstants.btConeTwistConstraintDataName, j, z);
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

    public static long getCPtr(btConeTwistConstraintData btconetwistconstraintdata) {
        if (btconetwistconstraintdata == null) {
            return 0L;
        }
        return btconetwistconstraintdata.swigCPtr;
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
                DynamicsJNI.delete_btConeTwistConstraintData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintData bttypedconstraintdata) {
        DynamicsJNI.btConeTwistConstraintData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintData.getCPtr(bttypedconstraintdata), bttypedconstraintdata);
    }

    public btTypedConstraintData getTypeConstraintData() {
        long jBtConeTwistConstraintData_typeConstraintData_get = DynamicsJNI.btConeTwistConstraintData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtConeTwistConstraintData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintData(jBtConeTwistConstraintData_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btConeTwistConstraintData_rbAFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbAFrame() {
        long jBtConeTwistConstraintData_rbAFrame_get = DynamicsJNI.btConeTwistConstraintData_rbAFrame_get(this.swigCPtr, this);
        if (jBtConeTwistConstraintData_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtConeTwistConstraintData_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btConeTwistConstraintData_rbBFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbBFrame() {
        long jBtConeTwistConstraintData_rbBFrame_get = DynamicsJNI.btConeTwistConstraintData_rbBFrame_get(this.swigCPtr, this);
        if (jBtConeTwistConstraintData_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtConeTwistConstraintData_rbBFrame_get, false);
    }

    public void setSwingSpan1(float f) {
        DynamicsJNI.btConeTwistConstraintData_swingSpan1_set(this.swigCPtr, this, f);
    }

    public float getSwingSpan1() {
        return DynamicsJNI.btConeTwistConstraintData_swingSpan1_get(this.swigCPtr, this);
    }

    public void setSwingSpan2(float f) {
        DynamicsJNI.btConeTwistConstraintData_swingSpan2_set(this.swigCPtr, this, f);
    }

    public float getSwingSpan2() {
        return DynamicsJNI.btConeTwistConstraintData_swingSpan2_get(this.swigCPtr, this);
    }

    public void setTwistSpan(float f) {
        DynamicsJNI.btConeTwistConstraintData_twistSpan_set(this.swigCPtr, this, f);
    }

    public float getTwistSpan() {
        return DynamicsJNI.btConeTwistConstraintData_twistSpan_get(this.swigCPtr, this);
    }

    public void setLimitSoftness(float f) {
        DynamicsJNI.btConeTwistConstraintData_limitSoftness_set(this.swigCPtr, this, f);
    }

    public float getLimitSoftness() {
        return DynamicsJNI.btConeTwistConstraintData_limitSoftness_get(this.swigCPtr, this);
    }

    public void setBiasFactor(float f) {
        DynamicsJNI.btConeTwistConstraintData_biasFactor_set(this.swigCPtr, this, f);
    }

    public float getBiasFactor() {
        return DynamicsJNI.btConeTwistConstraintData_biasFactor_get(this.swigCPtr, this);
    }

    public void setRelaxationFactor(float f) {
        DynamicsJNI.btConeTwistConstraintData_relaxationFactor_set(this.swigCPtr, this, f);
    }

    public float getRelaxationFactor() {
        return DynamicsJNI.btConeTwistConstraintData_relaxationFactor_get(this.swigCPtr, this);
    }

    public void setDamping(float f) {
        DynamicsJNI.btConeTwistConstraintData_damping_set(this.swigCPtr, this, f);
    }

    public float getDamping() {
        return DynamicsJNI.btConeTwistConstraintData_damping_get(this.swigCPtr, this);
    }

    public void setPad(String str) {
        DynamicsJNI.btConeTwistConstraintData_pad_set(this.swigCPtr, this, str);
    }

    public String getPad() {
        return DynamicsJNI.btConeTwistConstraintData_pad_get(this.swigCPtr, this);
    }

    public btConeTwistConstraintData() {
        this(DynamicsJNI.new_btConeTwistConstraintData(), true);
    }
}
