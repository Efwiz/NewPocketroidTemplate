package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btHinge2Constraint extends btGeneric6DofSpring2Constraint {
    private long swigCPtr;

    protected btHinge2Constraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btHinge2Constraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btHinge2Constraint(long j, boolean z) {
        this("btHinge2Constraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btHinge2Constraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btHinge2Constraint bthinge2constraint) {
        if (bthinge2constraint == null) {
            return 0L;
        }
        return bthinge2constraint.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btHinge2Constraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btHinge2Constraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btHinge2Constraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btHinge2Constraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btHinge2Constraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btHinge2Constraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btHinge2Constraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btHinge2Constraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btHinge2Constraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btHinge2Constraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        this(DynamicsJNI.new_btHinge2Constraint(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4, vector5), true);
    }

    public Vector3 getAnchor() {
        return DynamicsJNI.btHinge2Constraint_getAnchor(this.swigCPtr, this);
    }

    public Vector3 getAnchor2() {
        return DynamicsJNI.btHinge2Constraint_getAnchor2(this.swigCPtr, this);
    }

    public Vector3 getAxis1() {
        return DynamicsJNI.btHinge2Constraint_getAxis1(this.swigCPtr, this);
    }

    public Vector3 getAxis2() {
        return DynamicsJNI.btHinge2Constraint_getAxis2(this.swigCPtr, this);
    }

    public float getAngle1() {
        return DynamicsJNI.btHinge2Constraint_getAngle1(this.swigCPtr, this);
    }

    public float getAngle2() {
        return DynamicsJNI.btHinge2Constraint_getAngle2(this.swigCPtr, this);
    }

    public void setUpperLimit(float f) {
        DynamicsJNI.btHinge2Constraint_setUpperLimit(this.swigCPtr, this, f);
    }

    public void setLowerLimit(float f) {
        DynamicsJNI.btHinge2Constraint_setLowerLimit(this.swigCPtr, this, f);
    }
}
