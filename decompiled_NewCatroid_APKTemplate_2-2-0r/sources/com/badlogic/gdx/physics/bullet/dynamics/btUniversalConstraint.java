package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btUniversalConstraint extends btGeneric6DofConstraint {
    private long swigCPtr;

    protected btUniversalConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btUniversalConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btUniversalConstraint(long j, boolean z) {
        this("btUniversalConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btUniversalConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btUniversalConstraint btuniversalconstraint) {
        if (btuniversalconstraint == null) {
            return 0L;
        }
        return btuniversalconstraint.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btUniversalConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btUniversalConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btUniversalConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btUniversalConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btUniversalConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btUniversalConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btUniversalConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btUniversalConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btUniversalConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btUniversalConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        this(DynamicsJNI.new_btUniversalConstraint(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4, vector5), true);
    }

    public Vector3 getAnchor() {
        return DynamicsJNI.btUniversalConstraint_getAnchor(this.swigCPtr, this);
    }

    public Vector3 getAnchor2() {
        return DynamicsJNI.btUniversalConstraint_getAnchor2(this.swigCPtr, this);
    }

    public Vector3 getAxis1() {
        return DynamicsJNI.btUniversalConstraint_getAxis1(this.swigCPtr, this);
    }

    public Vector3 getAxis2() {
        return DynamicsJNI.btUniversalConstraint_getAxis2(this.swigCPtr, this);
    }

    public float getAngle1() {
        return DynamicsJNI.btUniversalConstraint_getAngle1(this.swigCPtr, this);
    }

    public float getAngle2() {
        return DynamicsJNI.btUniversalConstraint_getAngle2(this.swigCPtr, this);
    }

    public void setUpperLimit(float f, float f2) {
        DynamicsJNI.btUniversalConstraint_setUpperLimit(this.swigCPtr, this, f, f2);
    }

    public void setLowerLimit(float f, float f2) {
        DynamicsJNI.btUniversalConstraint_setLowerLimit(this.swigCPtr, this, f, f2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint
    public void setAxis(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btUniversalConstraint_setAxis(this.swigCPtr, this, vector3, vector4);
    }
}
