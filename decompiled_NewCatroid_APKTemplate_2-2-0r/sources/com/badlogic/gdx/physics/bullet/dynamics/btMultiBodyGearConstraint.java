package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyGearConstraint extends btMultiBodyConstraint {
    private long swigCPtr;

    protected btMultiBodyGearConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btMultiBodyGearConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultiBodyGearConstraint(long j, boolean z) {
        this("btMultiBodyGearConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btMultiBodyGearConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultiBodyGearConstraint btmultibodygearconstraint) {
        if (btmultibodygearconstraint == null) {
            return 0L;
        }
        return btmultibodygearconstraint.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodyGearConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btMultiBodyGearConstraint(btMultiBody btmultibody, int i, btMultiBody btmultibody2, int i2, Vector3 vector3, Vector3 vector4, Matrix3 matrix3, Matrix3 matrix4) {
        this(DynamicsJNI.new_btMultiBodyGearConstraint(btMultiBody.getCPtr(btmultibody), btmultibody, i, btMultiBody.getCPtr(btmultibody2), btmultibody2, i2, vector3, vector4, matrix3, matrix4), true);
    }

    public Vector3 getPivotInA() {
        return DynamicsJNI.btMultiBodyGearConstraint_getPivotInA(this.swigCPtr, this);
    }

    public void setPivotInA(Vector3 vector3) {
        DynamicsJNI.btMultiBodyGearConstraint_setPivotInA(this.swigCPtr, this, vector3);
    }

    public Vector3 getPivotInB() {
        return DynamicsJNI.btMultiBodyGearConstraint_getPivotInB(this.swigCPtr, this);
    }

    public Matrix3 getFrameInA() {
        return DynamicsJNI.btMultiBodyGearConstraint_getFrameInA(this.swigCPtr, this);
    }

    public void setFrameInA(Matrix3 matrix3) {
        DynamicsJNI.btMultiBodyGearConstraint_setFrameInA(this.swigCPtr, this, matrix3);
    }

    public Matrix3 getFrameInB() {
        return DynamicsJNI.btMultiBodyGearConstraint_getFrameInB(this.swigCPtr, this);
    }
}
