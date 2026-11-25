package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btGeneric6DofSpringConstraintDoubleData2 extends BulletBase {
    private long swigCPtr;

    protected btGeneric6DofSpringConstraintDoubleData2(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGeneric6DofSpringConstraintDoubleData2(long j, boolean z) {
        this("btGeneric6DofSpringConstraintDoubleData2", j, z);
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

    public static long getCPtr(btGeneric6DofSpringConstraintDoubleData2 btgeneric6dofspringconstraintdoubledata2) {
        if (btgeneric6dofspringconstraintdoubledata2 == null) {
            return 0L;
        }
        return btgeneric6dofspringconstraintdoubledata2.swigCPtr;
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
                DynamicsJNI.delete_btGeneric6DofSpringConstraintDoubleData2(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void set6dofData(btGeneric6DofConstraintDoubleData2 btgeneric6dofconstraintdoubledata2) {
        DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_6dofData_set(this.swigCPtr, this, btGeneric6DofConstraintDoubleData2.getCPtr(btgeneric6dofconstraintdoubledata2), btgeneric6dofconstraintdoubledata2);
    }

    public btGeneric6DofConstraintDoubleData2 get6dofData() {
        long jBtGeneric6DofSpringConstraintDoubleData2_6dofData_get = DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_6dofData_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpringConstraintDoubleData2_6dofData_get == 0) {
            return null;
        }
        return new btGeneric6DofConstraintDoubleData2(jBtGeneric6DofSpringConstraintDoubleData2_6dofData_get, false);
    }

    public void setSpringEnabled(int[] iArr) {
        DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_springEnabled_set(this.swigCPtr, this, iArr);
    }

    public int[] getSpringEnabled() {
        return DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_springEnabled_get(this.swigCPtr, this);
    }

    public void setEquilibriumPoint(double[] dArr) {
        DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_equilibriumPoint_set(this.swigCPtr, this, dArr);
    }

    public double[] getEquilibriumPoint() {
        return DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_equilibriumPoint_get(this.swigCPtr, this);
    }

    public void setSpringStiffness(double[] dArr) {
        DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_springStiffness_set(this.swigCPtr, this, dArr);
    }

    public double[] getSpringStiffness() {
        return DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_springStiffness_get(this.swigCPtr, this);
    }

    public void setSpringDamping(double[] dArr) {
        DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_springDamping_set(this.swigCPtr, this, dArr);
    }

    public double[] getSpringDamping() {
        return DynamicsJNI.btGeneric6DofSpringConstraintDoubleData2_springDamping_get(this.swigCPtr, this);
    }

    public btGeneric6DofSpringConstraintDoubleData2() {
        this(DynamicsJNI.new_btGeneric6DofSpringConstraintDoubleData2(), true);
    }
}
