package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btSliderConstraintData extends BulletBase {
    private long swigCPtr;

    protected btSliderConstraintData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSliderConstraintData(long j, boolean z) {
        this(DynamicsConstants.btSliderConstraintDataName, j, z);
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

    public static long getCPtr(btSliderConstraintData btsliderconstraintdata) {
        if (btsliderconstraintdata == null) {
            return 0L;
        }
        return btsliderconstraintdata.swigCPtr;
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
                DynamicsJNI.delete_btSliderConstraintData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setTypeConstraintData(btTypedConstraintData bttypedconstraintdata) {
        DynamicsJNI.btSliderConstraintData_typeConstraintData_set(this.swigCPtr, this, btTypedConstraintData.getCPtr(bttypedconstraintdata), bttypedconstraintdata);
    }

    public btTypedConstraintData getTypeConstraintData() {
        long jBtSliderConstraintData_typeConstraintData_get = DynamicsJNI.btSliderConstraintData_typeConstraintData_get(this.swigCPtr, this);
        if (jBtSliderConstraintData_typeConstraintData_get == 0) {
            return null;
        }
        return new btTypedConstraintData(jBtSliderConstraintData_typeConstraintData_get, false);
    }

    public void setRbAFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btSliderConstraintData_rbAFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbAFrame() {
        long jBtSliderConstraintData_rbAFrame_get = DynamicsJNI.btSliderConstraintData_rbAFrame_get(this.swigCPtr, this);
        if (jBtSliderConstraintData_rbAFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtSliderConstraintData_rbAFrame_get, false);
    }

    public void setRbBFrame(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btSliderConstraintData_rbBFrame_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getRbBFrame() {
        long jBtSliderConstraintData_rbBFrame_get = DynamicsJNI.btSliderConstraintData_rbBFrame_get(this.swigCPtr, this);
        if (jBtSliderConstraintData_rbBFrame_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtSliderConstraintData_rbBFrame_get, false);
    }

    public void setLinearUpperLimit(float f) {
        DynamicsJNI.btSliderConstraintData_linearUpperLimit_set(this.swigCPtr, this, f);
    }

    public float getLinearUpperLimit() {
        return DynamicsJNI.btSliderConstraintData_linearUpperLimit_get(this.swigCPtr, this);
    }

    public void setLinearLowerLimit(float f) {
        DynamicsJNI.btSliderConstraintData_linearLowerLimit_set(this.swigCPtr, this, f);
    }

    public float getLinearLowerLimit() {
        return DynamicsJNI.btSliderConstraintData_linearLowerLimit_get(this.swigCPtr, this);
    }

    public void setAngularUpperLimit(float f) {
        DynamicsJNI.btSliderConstraintData_angularUpperLimit_set(this.swigCPtr, this, f);
    }

    public float getAngularUpperLimit() {
        return DynamicsJNI.btSliderConstraintData_angularUpperLimit_get(this.swigCPtr, this);
    }

    public void setAngularLowerLimit(float f) {
        DynamicsJNI.btSliderConstraintData_angularLowerLimit_set(this.swigCPtr, this, f);
    }

    public float getAngularLowerLimit() {
        return DynamicsJNI.btSliderConstraintData_angularLowerLimit_get(this.swigCPtr, this);
    }

    public void setUseLinearReferenceFrameA(int i) {
        DynamicsJNI.btSliderConstraintData_useLinearReferenceFrameA_set(this.swigCPtr, this, i);
    }

    public int getUseLinearReferenceFrameA() {
        return DynamicsJNI.btSliderConstraintData_useLinearReferenceFrameA_get(this.swigCPtr, this);
    }

    public void setUseOffsetForConstraintFrame(int i) {
        DynamicsJNI.btSliderConstraintData_useOffsetForConstraintFrame_set(this.swigCPtr, this, i);
    }

    public int getUseOffsetForConstraintFrame() {
        return DynamicsJNI.btSliderConstraintData_useOffsetForConstraintFrame_get(this.swigCPtr, this);
    }

    public btSliderConstraintData() {
        this(DynamicsJNI.new_btSliderConstraintData(), true);
    }
}
