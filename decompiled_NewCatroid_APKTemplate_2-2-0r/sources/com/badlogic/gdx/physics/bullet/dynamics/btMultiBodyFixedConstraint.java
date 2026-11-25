package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyFixedConstraint extends btMultiBodyConstraint {
    private long swigCPtr;

    protected btMultiBodyFixedConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btMultiBodyFixedConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultiBodyFixedConstraint(long j, boolean z) {
        this("btMultiBodyFixedConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btMultiBodyFixedConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultiBodyFixedConstraint btmultibodyfixedconstraint) {
        if (btmultibodyfixedconstraint == null) {
            return 0L;
        }
        return btmultibodyfixedconstraint.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodyFixedConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btMultiBodyFixedConstraint(btMultiBody btmultibody, int i, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4, Matrix3 matrix3, Matrix3 matrix4) {
        this(DynamicsJNI.new_btMultiBodyFixedConstraint__SWIG_0(btMultiBody.getCPtr(btmultibody), btmultibody, i, btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, vector4, matrix3, matrix4), true);
    }

    public btMultiBodyFixedConstraint(btMultiBody btmultibody, int i, btMultiBody btmultibody2, int i2, Vector3 vector3, Vector3 vector4, Matrix3 matrix3, Matrix3 matrix4) {
        this(DynamicsJNI.new_btMultiBodyFixedConstraint__SWIG_1(btMultiBody.getCPtr(btmultibody), btmultibody, i, btMultiBody.getCPtr(btmultibody2), btmultibody2, i2, vector3, vector4, matrix3, matrix4), true);
    }

    public Vector3 getPivotInA() {
        return DynamicsJNI.btMultiBodyFixedConstraint_getPivotInA(this.swigCPtr, this);
    }

    public void setPivotInA(Vector3 vector3) {
        DynamicsJNI.btMultiBodyFixedConstraint_setPivotInA(this.swigCPtr, this, vector3);
    }

    public Vector3 getPivotInB() {
        return DynamicsJNI.btMultiBodyFixedConstraint_getPivotInB(this.swigCPtr, this);
    }

    public Matrix3 getFrameInA() {
        return DynamicsJNI.btMultiBodyFixedConstraint_getFrameInA(this.swigCPtr, this);
    }

    public void setFrameInA(Matrix3 matrix3) {
        DynamicsJNI.btMultiBodyFixedConstraint_setFrameInA(this.swigCPtr, this, matrix3);
    }

    public Matrix3 getFrameInB() {
        return DynamicsJNI.btMultiBodyFixedConstraint_getFrameInB(this.swigCPtr, this);
    }
}
