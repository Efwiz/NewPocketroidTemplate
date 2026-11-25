package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyJointMotor extends btMultiBodyConstraint {
    private long swigCPtr;

    protected btMultiBodyJointMotor(String str, long j, boolean z) {
        super(str, DynamicsJNI.btMultiBodyJointMotor_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultiBodyJointMotor(long j, boolean z) {
        this("btMultiBodyJointMotor", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btMultiBodyJointMotor_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultiBodyJointMotor btmultibodyjointmotor) {
        if (btmultibodyjointmotor == null) {
            return 0L;
        }
        return btmultibodyjointmotor.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btMultiBodyJointMotor(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btMultiBodyJointMotor(btMultiBody btmultibody, int i, float f, float f2) {
        this(DynamicsJNI.new_btMultiBodyJointMotor__SWIG_0(btMultiBody.getCPtr(btmultibody), btmultibody, i, f, f2), true);
    }

    public btMultiBodyJointMotor(btMultiBody btmultibody, int i, int i2, float f, float f2) {
        this(DynamicsJNI.new_btMultiBodyJointMotor__SWIG_1(btMultiBody.getCPtr(btmultibody), btmultibody, i, i2, f, f2), true);
    }

    public void setVelocityTarget(float f, float f2) {
        DynamicsJNI.btMultiBodyJointMotor_setVelocityTarget__SWIG_0(this.swigCPtr, this, f, f2);
    }

    public void setVelocityTarget(float f) {
        DynamicsJNI.btMultiBodyJointMotor_setVelocityTarget__SWIG_1(this.swigCPtr, this, f);
    }

    public void setPositionTarget(float f, float f2) {
        DynamicsJNI.btMultiBodyJointMotor_setPositionTarget__SWIG_0(this.swigCPtr, this, f, f2);
    }

    public void setPositionTarget(float f) {
        DynamicsJNI.btMultiBodyJointMotor_setPositionTarget__SWIG_1(this.swigCPtr, this, f);
    }

    public float getErp() {
        return DynamicsJNI.btMultiBodyJointMotor_getErp(this.swigCPtr, this);
    }

    public void setRhsClamp(float f) {
        DynamicsJNI.btMultiBodyJointMotor_setRhsClamp(this.swigCPtr, this, f);
    }
}
