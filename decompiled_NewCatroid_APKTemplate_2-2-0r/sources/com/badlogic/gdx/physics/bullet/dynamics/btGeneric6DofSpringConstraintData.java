package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btGeneric6DofSpringConstraintData extends BulletBase {
    private long swigCPtr;

    protected btGeneric6DofSpringConstraintData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGeneric6DofSpringConstraintData(long j, boolean z) {
        this(DynamicsConstants.btGeneric6DofSpringConstraintDataName, j, z);
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

    public static long getCPtr(btGeneric6DofSpringConstraintData btgeneric6dofspringconstraintdata) {
        if (btgeneric6dofspringconstraintdata == null) {
            return 0L;
        }
        return btgeneric6dofspringconstraintdata.swigCPtr;
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
                DynamicsJNI.delete_btGeneric6DofSpringConstraintData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void set6dofData(btGeneric6DofConstraintData btgeneric6dofconstraintdata) {
        DynamicsJNI.btGeneric6DofSpringConstraintData_6dofData_set(this.swigCPtr, this, btGeneric6DofConstraintData.getCPtr(btgeneric6dofconstraintdata), btgeneric6dofconstraintdata);
    }

    public btGeneric6DofConstraintData get6dofData() {
        long jBtGeneric6DofSpringConstraintData_6dofData_get = DynamicsJNI.btGeneric6DofSpringConstraintData_6dofData_get(this.swigCPtr, this);
        if (jBtGeneric6DofSpringConstraintData_6dofData_get == 0) {
            return null;
        }
        return new btGeneric6DofConstraintData(jBtGeneric6DofSpringConstraintData_6dofData_get, false);
    }

    public void setSpringEnabled(int[] iArr) {
        DynamicsJNI.btGeneric6DofSpringConstraintData_springEnabled_set(this.swigCPtr, this, iArr);
    }

    public int[] getSpringEnabled() {
        return DynamicsJNI.btGeneric6DofSpringConstraintData_springEnabled_get(this.swigCPtr, this);
    }

    public void setEquilibriumPoint(float[] fArr) {
        DynamicsJNI.btGeneric6DofSpringConstraintData_equilibriumPoint_set(this.swigCPtr, this, fArr);
    }

    public float[] getEquilibriumPoint() {
        return DynamicsJNI.btGeneric6DofSpringConstraintData_equilibriumPoint_get(this.swigCPtr, this);
    }

    public void setSpringStiffness(float[] fArr) {
        DynamicsJNI.btGeneric6DofSpringConstraintData_springStiffness_set(this.swigCPtr, this, fArr);
    }

    public float[] getSpringStiffness() {
        return DynamicsJNI.btGeneric6DofSpringConstraintData_springStiffness_get(this.swigCPtr, this);
    }

    public void setSpringDamping(float[] fArr) {
        DynamicsJNI.btGeneric6DofSpringConstraintData_springDamping_set(this.swigCPtr, this, fArr);
    }

    public float[] getSpringDamping() {
        return DynamicsJNI.btGeneric6DofSpringConstraintData_springDamping_get(this.swigCPtr, this);
    }

    public btGeneric6DofSpringConstraintData() {
        this(DynamicsJNI.new_btGeneric6DofSpringConstraintData(), true);
    }
}
