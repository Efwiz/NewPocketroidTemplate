package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btSliderConstraintDoubleData extends BulletBase {
    private long swigCPtr;

    protected btSliderConstraintDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSliderConstraintDoubleData(long j, boolean z) {
        this("btSliderConstraintDoubleData", j, z);
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

    public static long getCPtr(btSliderConstraintDoubleData btsliderconstraintdoubledata) {
        if (btsliderconstraintdoubledata == null) {
            return 0L;
        }
        return btsliderconstraintdoubledata.swigCPtr;
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
                DynamicsJNI.delete_btSliderConstraintDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintDoubleData bttypedconstraintdoubledata) {
        DynamicsJNI.btSliderConstraintDoubleData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintDoubleData.getCPtr(bttypedconstraintdoubledata), bttypedconstraintdoubledata);
    }

    public btTypedConstraintDoubleData getTypeConstraintData() {
        long jBtSliderConstraintDoubleData_typeConstraintData_get = DynamicsJNI.btSliderConstraintDoubleData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtSliderConstraintDoubleData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintDoubleData(jBtSliderConstraintDoubleData_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btSliderConstraintDoubleData_rbAFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbAFrame() {
        long jBtSliderConstraintDoubleData_rbAFrame_get = DynamicsJNI.btSliderConstraintDoubleData_rbAFrame_get(this.swigCPtr, this);
        if (jBtSliderConstraintDoubleData_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtSliderConstraintDoubleData_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btSliderConstraintDoubleData_rbBFrame_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getRbBFrame() {
        long jBtSliderConstraintDoubleData_rbBFrame_get = DynamicsJNI.btSliderConstraintDoubleData_rbBFrame_get(this.swigCPtr, this);
        if (jBtSliderConstraintDoubleData_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtSliderConstraintDoubleData_rbBFrame_get, false);
    }

    public void setLinearUpperLimit(double d) {
        DynamicsJNI.btSliderConstraintDoubleData_linearUpperLimit_set(this.swigCPtr, this, d);
    }

    public double getLinearUpperLimit() {
        return DynamicsJNI.btSliderConstraintDoubleData_linearUpperLimit_get(this.swigCPtr, this);
    }

    public void setLinearLowerLimit(double d) {
        DynamicsJNI.btSliderConstraintDoubleData_linearLowerLimit_set(this.swigCPtr, this, d);
    }

    public double getLinearLowerLimit() {
        return DynamicsJNI.btSliderConstraintDoubleData_linearLowerLimit_get(this.swigCPtr, this);
    }

    public void setAngularUpperLimit(double d) {
        DynamicsJNI.btSliderConstraintDoubleData_angularUpperLimit_set(this.swigCPtr, this, d);
    }

    public double getAngularUpperLimit() {
        return DynamicsJNI.btSliderConstraintDoubleData_angularUpperLimit_get(this.swigCPtr, this);
    }

    public void setAngularLowerLimit(double d) {
        DynamicsJNI.btSliderConstraintDoubleData_angularLowerLimit_set(this.swigCPtr, this, d);
    }

    public double getAngularLowerLimit() {
        return DynamicsJNI.btSliderConstraintDoubleData_angularLowerLimit_get(this.swigCPtr, this);
    }

    public void setUseLinearReferenceFrameA(int i) {
        DynamicsJNI.btSliderConstraintDoubleData_useLinearReferenceFrameA_set(this.swigCPtr, this, i);
    }

    public int getUseLinearReferenceFrameA() {
        return DynamicsJNI.btSliderConstraintDoubleData_useLinearReferenceFrameA_get(this.swigCPtr, this);
    }

    public void setUseOffsetForConstraintFrame(int i) {
        DynamicsJNI.btSliderConstraintDoubleData_useOffsetForConstraintFrame_set(this.swigCPtr, this, i);
    }

    public int getUseOffsetForConstraintFrame() {
        return DynamicsJNI.btSliderConstraintDoubleData_useOffsetForConstraintFrame_get(this.swigCPtr, this);
    }

    public btSliderConstraintDoubleData() {
        this(DynamicsJNI.new_btSliderConstraintDoubleData(), true);
    }
}
