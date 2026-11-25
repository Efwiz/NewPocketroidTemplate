package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btHingeConstraint extends btTypedConstraint {
    private long swigCPtr;

    protected btHingeConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btHingeConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btHingeConstraint(long j, boolean z) {
        this("btHingeConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btHingeConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btHingeConstraint bthingeconstraint) {
        if (bthingeconstraint == null) {
            return 0L;
        }
        return bthingeconstraint.swigCPtr;
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
                DynamicsJNI.delete_btHingeConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btHingeConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btHingeConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btHingeConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btHingeConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btHingeConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btHingeConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btHingeConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btHingeConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btHingeConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, boolean z) {
        this(DynamicsJNI.new_btHingeConstraint__SWIG_0(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4, vector5, vector6, z), true);
    }

    public btHingeConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        this(DynamicsJNI.new_btHingeConstraint__SWIG_1(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4, vector5, vector6), true);
    }

    public btHingeConstraint(btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4, boolean z) {
        this(DynamicsJNI.new_btHingeConstraint__SWIG_2(btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, vector4, z), true);
    }

    public btHingeConstraint(btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4) {
        this(DynamicsJNI.new_btHingeConstraint__SWIG_3(btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, vector4), true);
    }

    public btHingeConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        this(DynamicsJNI.new_btHingeConstraint__SWIG_4(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, z), true);
    }

    public btHingeConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5) {
        this(DynamicsJNI.new_btHingeConstraint__SWIG_5(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5), true);
    }

    public btHingeConstraint(btRigidBody btrigidbody, Matrix4 matrix4, boolean z) {
        this(DynamicsJNI.new_btHingeConstraint__SWIG_6(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4, z), true);
    }

    public btHingeConstraint(btRigidBody btrigidbody, Matrix4 matrix4) {
        this(DynamicsJNI.new_btHingeConstraint__SWIG_7(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4), true);
    }

    public void getInfo1NonVirtual(btTypedConstraint.btConstraintInfo1 btconstraintinfo1) {
        DynamicsJNI.btHingeConstraint_getInfo1NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo1.getCPtr(btconstraintinfo1), btconstraintinfo1);
    }

    public void getInfo2NonVirtual(btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btHingeConstraint_getInfo2NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2, matrix4, matrix5, vector3, vector4);
    }

    public void getInfo2Internal(btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btHingeConstraint_getInfo2Internal(this.swigCPtr, this, btTypedConstraint.btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2, matrix4, matrix5, vector3, vector4);
    }

    public void getInfo2InternalUsingFrameOffset(btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btHingeConstraint_getInfo2InternalUsingFrameOffset(this.swigCPtr, this, btTypedConstraint.btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2, matrix4, matrix5, vector3, vector4);
    }

    public void updateRHS(float f) {
        DynamicsJNI.btHingeConstraint_updateRHS(this.swigCPtr, this, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public btRigidBody getRigidBodyAConst() {
        return btRigidBody.getInstance(DynamicsJNI.btHingeConstraint_getRigidBodyAConst(this.swigCPtr, this), false);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public btRigidBody getRigidBodyBConst() {
        return btRigidBody.getInstance(DynamicsJNI.btHingeConstraint_getRigidBodyBConst(this.swigCPtr, this), false);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public btRigidBody getRigidBodyA() {
        return btRigidBody.getInstance(DynamicsJNI.btHingeConstraint_getRigidBodyA(this.swigCPtr, this), false);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public btRigidBody getRigidBodyB() {
        return btRigidBody.getInstance(DynamicsJNI.btHingeConstraint_getRigidBodyB(this.swigCPtr, this), false);
    }

    public Matrix4 getFrameOffsetA() {
        return DynamicsJNI.btHingeConstraint_getFrameOffsetA(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetB() {
        return DynamicsJNI.btHingeConstraint_getFrameOffsetB(this.swigCPtr, this);
    }

    public void setFrames(Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btHingeConstraint_setFrames(this.swigCPtr, this, matrix4, matrix5);
    }

    public void setAngularOnly(boolean z) {
        DynamicsJNI.btHingeConstraint_setAngularOnly(this.swigCPtr, this, z);
    }

    public void enableAngularMotor(boolean z, float f, float f2) {
        DynamicsJNI.btHingeConstraint_enableAngularMotor(this.swigCPtr, this, z, f, f2);
    }

    public void enableMotor(boolean z) {
        DynamicsJNI.btHingeConstraint_enableMotor(this.swigCPtr, this, z);
    }

    public void setMaxMotorImpulse(float f) {
        DynamicsJNI.btHingeConstraint_setMaxMotorImpulse(this.swigCPtr, this, f);
    }

    public void setMotorTargetVelocity(float f) {
        DynamicsJNI.btHingeConstraint_setMotorTargetVelocity(this.swigCPtr, this, f);
    }

    public void setMotorTarget(Quaternion quaternion, float f) {
        DynamicsJNI.btHingeConstraint_setMotorTarget__SWIG_0(this.swigCPtr, this, quaternion, f);
    }

    public void setMotorTarget(float f, float f2) {
        DynamicsJNI.btHingeConstraint_setMotorTarget__SWIG_1(this.swigCPtr, this, f, f2);
    }

    public void setLimit(float f, float f2, float f3, float f4, float f5) {
        DynamicsJNI.btHingeConstraint_setLimit__SWIG_0(this.swigCPtr, this, f, f2, f3, f4, f5);
    }

    public void setLimit(float f, float f2, float f3, float f4) {
        DynamicsJNI.btHingeConstraint_setLimit__SWIG_1(this.swigCPtr, this, f, f2, f3, f4);
    }

    public void setLimit(float f, float f2, float f3) {
        DynamicsJNI.btHingeConstraint_setLimit__SWIG_2(this.swigCPtr, this, f, f2, f3);
    }

    public void setLimit(float f, float f2) {
        DynamicsJNI.btHingeConstraint_setLimit__SWIG_3(this.swigCPtr, this, f, f2);
    }

    public float getLimitSoftness() {
        return DynamicsJNI.btHingeConstraint_getLimitSoftness(this.swigCPtr, this);
    }

    public float getLimitBiasFactor() {
        return DynamicsJNI.btHingeConstraint_getLimitBiasFactor(this.swigCPtr, this);
    }

    public float getLimitRelaxationFactor() {
        return DynamicsJNI.btHingeConstraint_getLimitRelaxationFactor(this.swigCPtr, this);
    }

    public void setAxis(Vector3 vector3) {
        DynamicsJNI.btHingeConstraint_setAxis(this.swigCPtr, this, vector3);
    }

    public boolean hasLimit() {
        return DynamicsJNI.btHingeConstraint_hasLimit(this.swigCPtr, this);
    }

    public float getLowerLimit() {
        return DynamicsJNI.btHingeConstraint_getLowerLimit(this.swigCPtr, this);
    }

    public float getUpperLimit() {
        return DynamicsJNI.btHingeConstraint_getUpperLimit(this.swigCPtr, this);
    }

    public float getHingeAngle() {
        return DynamicsJNI.btHingeConstraint_getHingeAngle__SWIG_0(this.swigCPtr, this);
    }

    public float getHingeAngle(Matrix4 matrix4, Matrix4 matrix5) {
        return DynamicsJNI.btHingeConstraint_getHingeAngle__SWIG_1(this.swigCPtr, this, matrix4, matrix5);
    }

    public void testLimit(Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btHingeConstraint_testLimit(this.swigCPtr, this, matrix4, matrix5);
    }

    public Matrix4 getAFrameConst() {
        return DynamicsJNI.btHingeConstraint_getAFrameConst(this.swigCPtr, this);
    }

    public Matrix4 getBFrameConst() {
        return DynamicsJNI.btHingeConstraint_getBFrameConst(this.swigCPtr, this);
    }

    public Matrix4 getAFrame() {
        return DynamicsJNI.btHingeConstraint_getAFrame(this.swigCPtr, this);
    }

    public Matrix4 getBFrame() {
        return DynamicsJNI.btHingeConstraint_getBFrame(this.swigCPtr, this);
    }

    public int getSolveLimit() {
        return DynamicsJNI.btHingeConstraint_getSolveLimit(this.swigCPtr, this);
    }

    public float getLimitSign() {
        return DynamicsJNI.btHingeConstraint_getLimitSign(this.swigCPtr, this);
    }

    public boolean getAngularOnly() {
        return DynamicsJNI.btHingeConstraint_getAngularOnly(this.swigCPtr, this);
    }

    public boolean getEnableAngularMotor() {
        return DynamicsJNI.btHingeConstraint_getEnableAngularMotor(this.swigCPtr, this);
    }

    public float getMotorTargetVelocity() {
        return DynamicsJNI.btHingeConstraint_getMotorTargetVelocity(this.swigCPtr, this);
    }

    public float getMaxMotorImpulse() {
        return DynamicsJNI.btHingeConstraint_getMaxMotorImpulse(this.swigCPtr, this);
    }

    public boolean getUseFrameOffset() {
        return DynamicsJNI.btHingeConstraint_getUseFrameOffset(this.swigCPtr, this);
    }

    public void setUseFrameOffset(boolean z) {
        DynamicsJNI.btHingeConstraint_setUseFrameOffset(this.swigCPtr, this, z);
    }

    public boolean getUseReferenceFrameA() {
        return DynamicsJNI.btHingeConstraint_getUseReferenceFrameA(this.swigCPtr, this);
    }

    public void setUseReferenceFrameA(boolean z) {
        DynamicsJNI.btHingeConstraint_setUseReferenceFrameA(this.swigCPtr, this, z);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f, int i2) {
        DynamicsJNI.btHingeConstraint_setParam__SWIG_0(this.swigCPtr, this, i, f, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f) {
        DynamicsJNI.btHingeConstraint_setParam__SWIG_1(this.swigCPtr, this, i, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i, int i2) {
        return DynamicsJNI.btHingeConstraint_getParam__SWIG_0(this.swigCPtr, this, i, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i) {
        return DynamicsJNI.btHingeConstraint_getParam__SWIG_1(this.swigCPtr, this, i);
    }

    public int getFlags() {
        return DynamicsJNI.btHingeConstraint_getFlags(this.swigCPtr, this);
    }
}
