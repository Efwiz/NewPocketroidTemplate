package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btGeneric6DofSpringConstraint extends btGeneric6DofConstraint {
    private long swigCPtr;

    protected btGeneric6DofSpringConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btGeneric6DofSpringConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGeneric6DofSpringConstraint(long j, boolean z) {
        this("btGeneric6DofSpringConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btGeneric6DofSpringConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGeneric6DofSpringConstraint btgeneric6dofspringconstraint) {
        if (btgeneric6dofspringconstraint == null) {
            return 0L;
        }
        return btgeneric6dofspringconstraint.swigCPtr;
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
                DynamicsJNI.delete_btGeneric6DofSpringConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btGeneric6DofSpringConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btGeneric6DofSpringConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btGeneric6DofSpringConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btGeneric6DofSpringConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btGeneric6DofSpringConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btGeneric6DofSpringConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btGeneric6DofSpringConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btGeneric6DofSpringConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btGeneric6DofSpringConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        this(DynamicsJNI.new_btGeneric6DofSpringConstraint__SWIG_0(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, z), true);
    }

    public btGeneric6DofSpringConstraint(btRigidBody btrigidbody, Matrix4 matrix4, boolean z) {
        this(DynamicsJNI.new_btGeneric6DofSpringConstraint__SWIG_1(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4, z), true);
    }

    public void enableSpring(int i, boolean z) {
        DynamicsJNI.btGeneric6DofSpringConstraint_enableSpring(this.swigCPtr, this, i, z);
    }

    public void setStiffness(int i, float f) {
        DynamicsJNI.btGeneric6DofSpringConstraint_setStiffness(this.swigCPtr, this, i, f);
    }

    public void setDamping(int i, float f) {
        DynamicsJNI.btGeneric6DofSpringConstraint_setDamping(this.swigCPtr, this, i, f);
    }

    public void setEquilibriumPoint() {
        DynamicsJNI.btGeneric6DofSpringConstraint_setEquilibriumPoint__SWIG_0(this.swigCPtr, this);
    }

    public void setEquilibriumPoint(int i) {
        DynamicsJNI.btGeneric6DofSpringConstraint_setEquilibriumPoint__SWIG_1(this.swigCPtr, this, i);
    }

    public void setEquilibriumPoint(int i, float f) {
        DynamicsJNI.btGeneric6DofSpringConstraint_setEquilibriumPoint__SWIG_2(this.swigCPtr, this, i, f);
    }

    public boolean isSpringEnabled(int i) {
        return DynamicsJNI.btGeneric6DofSpringConstraint_isSpringEnabled(this.swigCPtr, this, i);
    }

    public float getStiffness(int i) {
        return DynamicsJNI.btGeneric6DofSpringConstraint_getStiffness(this.swigCPtr, this, i);
    }

    public float getDamping(int i) {
        return DynamicsJNI.btGeneric6DofSpringConstraint_getDamping(this.swigCPtr, this, i);
    }

    public float getEquilibriumPoint(int i) {
        return DynamicsJNI.btGeneric6DofSpringConstraint_getEquilibriumPoint(this.swigCPtr, this, i);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint
    public void setAxis(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btGeneric6DofSpringConstraint_setAxis(this.swigCPtr, this, vector3, vector4);
    }
}
