package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btHingeAccumulatedAngleConstraint extends btHingeConstraint {
    private long swigCPtr;

    protected btHingeAccumulatedAngleConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btHingeAccumulatedAngleConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btHingeAccumulatedAngleConstraint(long j, boolean z) {
        this("btHingeAccumulatedAngleConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btHingeAccumulatedAngleConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btHingeAccumulatedAngleConstraint bthingeaccumulatedangleconstraint) {
        if (bthingeaccumulatedangleconstraint == null) {
            return 0L;
        }
        return bthingeaccumulatedangleconstraint.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btHingeAccumulatedAngleConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btHingeAccumulatedAngleConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btHingeAccumulatedAngleConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btHingeAccumulatedAngleConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btHingeAccumulatedAngleConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btHingeAccumulatedAngleConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btHingeAccumulatedAngleConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btHingeAccumulatedAngleConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btHingeAccumulatedAngleConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btHingeAccumulatedAngleConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, boolean z) {
        this(DynamicsJNI.new_btHingeAccumulatedAngleConstraint__SWIG_0(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4, vector5, vector6, z), true);
    }

    public btHingeAccumulatedAngleConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        this(DynamicsJNI.new_btHingeAccumulatedAngleConstraint__SWIG_1(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4, vector5, vector6), true);
    }

    public btHingeAccumulatedAngleConstraint(btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4, boolean z) {
        this(DynamicsJNI.new_btHingeAccumulatedAngleConstraint__SWIG_2(btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, vector4, z), true);
    }

    public btHingeAccumulatedAngleConstraint(btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4) {
        this(DynamicsJNI.new_btHingeAccumulatedAngleConstraint__SWIG_3(btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, vector4), true);
    }

    public btHingeAccumulatedAngleConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        this(DynamicsJNI.new_btHingeAccumulatedAngleConstraint__SWIG_4(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, z), true);
    }

    public btHingeAccumulatedAngleConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5) {
        this(DynamicsJNI.new_btHingeAccumulatedAngleConstraint__SWIG_5(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5), true);
    }

    public btHingeAccumulatedAngleConstraint(btRigidBody btrigidbody, Matrix4 matrix4, boolean z) {
        this(DynamicsJNI.new_btHingeAccumulatedAngleConstraint__SWIG_6(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4, z), true);
    }

    public btHingeAccumulatedAngleConstraint(btRigidBody btrigidbody, Matrix4 matrix4) {
        this(DynamicsJNI.new_btHingeAccumulatedAngleConstraint__SWIG_7(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4), true);
    }

    public float getAccumulatedHingeAngle() {
        return DynamicsJNI.btHingeAccumulatedAngleConstraint_getAccumulatedHingeAngle(this.swigCPtr, this);
    }

    public void setAccumulatedHingeAngle(float f) {
        DynamicsJNI.btHingeAccumulatedAngleConstraint_setAccumulatedHingeAngle(this.swigCPtr, this, f);
    }
}
