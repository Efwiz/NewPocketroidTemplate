package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btGearConstraint extends btTypedConstraint {
    private long swigCPtr;

    protected btGearConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btGearConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGearConstraint(long j, boolean z) {
        this("btGearConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btGearConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGearConstraint btgearconstraint) {
        if (btgearconstraint == null) {
            return 0L;
        }
        return btgearconstraint.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btGearConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGearConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, float f) {
        this(DynamicsJNI.new_btGearConstraint__SWIG_0(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4, f), true);
    }

    public btGearConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4) {
        this(DynamicsJNI.new_btGearConstraint__SWIG_1(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4), true);
    }

    public void setAxisA(Vector3 vector3) {
        DynamicsJNI.btGearConstraint_setAxisA(this.swigCPtr, this, vector3);
    }

    public void setAxisB(Vector3 vector3) {
        DynamicsJNI.btGearConstraint_setAxisB(this.swigCPtr, this, vector3);
    }

    public void setRatio(float f) {
        DynamicsJNI.btGearConstraint_setRatio(this.swigCPtr, this, f);
    }

    public Vector3 getAxisA() {
        return DynamicsJNI.btGearConstraint_getAxisA(this.swigCPtr, this);
    }

    public Vector3 getAxisB() {
        return DynamicsJNI.btGearConstraint_getAxisB(this.swigCPtr, this);
    }

    public float getRatio() {
        return DynamicsJNI.btGearConstraint_getRatio(this.swigCPtr, this);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f, int i2) {
        DynamicsJNI.btGearConstraint_setParam__SWIG_0(this.swigCPtr, this, i, f, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f) {
        DynamicsJNI.btGearConstraint_setParam__SWIG_1(this.swigCPtr, this, i, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i, int i2) {
        return DynamicsJNI.btGearConstraint_getParam__SWIG_0(this.swigCPtr, this, i, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i) {
        return DynamicsJNI.btGearConstraint_getParam__SWIG_1(this.swigCPtr, this, i);
    }
}
