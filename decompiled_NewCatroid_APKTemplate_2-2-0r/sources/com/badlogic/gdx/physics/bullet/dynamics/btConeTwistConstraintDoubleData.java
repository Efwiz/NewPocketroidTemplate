package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btConeTwistConstraintDoubleData extends BulletBase {
    private long swigCPtr;

    protected btConeTwistConstraintDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConeTwistConstraintDoubleData(long j, boolean z) {
        this("btConeTwistConstraintDoubleData", j, z);
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

    public static long getCPtr(btConeTwistConstraintDoubleData btconetwistconstraintdoubledata) {
        if (btconetwistconstraintdoubledata == null) {
            return 0L;
        }
        return btconetwistconstraintdoubledata.swigCPtr;
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
                DynamicsJNI.delete_btConeTwistConstraintDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintDoubleData bttypedconstraintdoubledata) {
        DynamicsJNI.btConeTwistConstraintDoubleData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintDoubleData.getCPtr(bttypedconstraintdoubledata), bttypedconstraintdoubledata);
    }

    public btTypedConstraintDoubleData getTypeConstraintData() {
        long jBtConeTwistConstraintDoubleData_typeConstraintData_get = DynamicsJNI.btConeTwistConstraintDoubleData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtConeTwistConstraintDoubleData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintDoubleData(jBtConeTwistConstraintDoubleData_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btConeTwistConstraintDoubleData_rbAFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbAFrame() {
        long jBtConeTwistConstraintDoubleData_rbAFrame_get = DynamicsJNI.btConeTwistConstraintDoubleData_rbAFrame_get(this.swigCPtr, this);
        if (jBtConeTwistConstraintDoubleData_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtConeTwistConstraintDoubleData_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btConeTwistConstraintDoubleData_rbBFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbBFrame() {
        long jBtConeTwistConstraintDoubleData_rbBFrame_get = DynamicsJNI.btConeTwistConstraintDoubleData_rbBFrame_get(this.swigCPtr, this);
        if (jBtConeTwistConstraintDoubleData_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtConeTwistConstraintDoubleData_rbBFrame_get, false);
    }

    public void setSwingSpan1(double d) {
        DynamicsJNI.btConeTwistConstraintDoubleData_swingSpan1_set(this.swigCPtr, this, d);
    }

    public double getSwingSpan1() {
        return DynamicsJNI.btConeTwistConstraintDoubleData_swingSpan1_get(this.swigCPtr, this);
    }

    public void setSwingSpan2(double d) {
        DynamicsJNI.btConeTwistConstraintDoubleData_swingSpan2_set(this.swigCPtr, this, d);
    }

    public double getSwingSpan2() {
        return DynamicsJNI.btConeTwistConstraintDoubleData_swingSpan2_get(this.swigCPtr, this);
    }

    public void setTwistSpan(double d) {
        DynamicsJNI.btConeTwistConstraintDoubleData_twistSpan_set(this.swigCPtr, this, d);
    }

    public double getTwistSpan() {
        return DynamicsJNI.btConeTwistConstraintDoubleData_twistSpan_get(this.swigCPtr, this);
    }

    public void setLimitSoftness(double d) {
        DynamicsJNI.btConeTwistConstraintDoubleData_limitSoftness_set(this.swigCPtr, this, d);
    }

    public double getLimitSoftness() {
        return DynamicsJNI.btConeTwistConstraintDoubleData_limitSoftness_get(this.swigCPtr, this);
    }

    public void setBiasFactor(double d) {
        DynamicsJNI.btConeTwistConstraintDoubleData_biasFactor_set(this.swigCPtr, this, d);
    }

    public double getBiasFactor() {
        return DynamicsJNI.btConeTwistConstraintDoubleData_biasFactor_get(this.swigCPtr, this);
    }

    public void setRelaxationFactor(double d) {
        DynamicsJNI.btConeTwistConstraintDoubleData_relaxationFactor_set(this.swigCPtr, this, d);
    }

    public double getRelaxationFactor() {
        return DynamicsJNI.btConeTwistConstraintDoubleData_relaxationFactor_get(this.swigCPtr, this);
    }

    public void setDamping(double d) {
        DynamicsJNI.btConeTwistConstraintDoubleData_damping_set(this.swigCPtr, this, d);
    }

    public double getDamping() {
        return DynamicsJNI.btConeTwistConstraintDoubleData_damping_get(this.swigCPtr, this);
    }

    public btConeTwistConstraintDoubleData() {
        this(DynamicsJNI.new_btConeTwistConstraintDoubleData(), true);
    }
}
