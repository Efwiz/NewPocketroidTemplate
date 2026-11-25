package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btConeTwistConstraint extends btTypedConstraint {
    private long swigCPtr;

    protected btConeTwistConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btConeTwistConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btConeTwistConstraint(long j, boolean z) {
        this("btConeTwistConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btConeTwistConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btConeTwistConstraint btconetwistconstraint) {
        if (btconetwistconstraint == null) {
            return 0L;
        }
        return btconetwistconstraint.swigCPtr;
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
                DynamicsJNI.delete_btConeTwistConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btConeTwistConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btConeTwistConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btConeTwistConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btConeTwistConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btConeTwistConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btConeTwistConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btConeTwistConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btConeTwistConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btConeTwistConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5) {
        this(DynamicsJNI.new_btConeTwistConstraint__SWIG_0(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5), true);
    }

    public btConeTwistConstraint(btRigidBody btrigidbody, Matrix4 matrix4) {
        this(DynamicsJNI.new_btConeTwistConstraint__SWIG_1(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4), true);
    }

    public void getInfo1NonVirtual(btTypedConstraint.btConstraintInfo1 btconstraintinfo1) {
        DynamicsJNI.btConeTwistConstraint_getInfo1NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo1.getCPtr(btconstraintinfo1), btconstraintinfo1);
    }

    public void getInfo2NonVirtual(btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Matrix3 matrix3, Matrix3 matrix6) {
        DynamicsJNI.btConeTwistConstraint_getInfo2NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2, matrix4, matrix5, matrix3, matrix6);
    }

    public void updateRHS(float f) {
        DynamicsJNI.btConeTwistConstraint_updateRHS(this.swigCPtr, this, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public btRigidBody getRigidBodyAConst() {
        return btRigidBody.getInstance(DynamicsJNI.btConeTwistConstraint_getRigidBodyAConst(this.swigCPtr, this), false);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public btRigidBody getRigidBodyBConst() {
        return btRigidBody.getInstance(DynamicsJNI.btConeTwistConstraint_getRigidBodyBConst(this.swigCPtr, this), false);
    }

    public void setAngularOnly(boolean z) {
        DynamicsJNI.btConeTwistConstraint_setAngularOnly(this.swigCPtr, this, z);
    }

    public boolean getAngularOnly() {
        return DynamicsJNI.btConeTwistConstraint_getAngularOnly(this.swigCPtr, this);
    }

    public void setLimit(int i, float f) {
        DynamicsJNI.btConeTwistConstraint_setLimit__SWIG_0(this.swigCPtr, this, i, f);
    }

    public float getLimit(int i) {
        return DynamicsJNI.btConeTwistConstraint_getLimit(this.swigCPtr, this, i);
    }

    public void setLimit(float f, float f2, float f3, float f4, float f5, float f6) {
        DynamicsJNI.btConeTwistConstraint_setLimit__SWIG_1(this.swigCPtr, this, f, f2, f3, f4, f5, f6);
    }

    public void setLimit(float f, float f2, float f3, float f4, float f5) {
        DynamicsJNI.btConeTwistConstraint_setLimit__SWIG_2(this.swigCPtr, this, f, f2, f3, f4, f5);
    }

    public void setLimit(float f, float f2, float f3, float f4) {
        DynamicsJNI.btConeTwistConstraint_setLimit__SWIG_3(this.swigCPtr, this, f, f2, f3, f4);
    }

    public void setLimit(float f, float f2, float f3) {
        DynamicsJNI.btConeTwistConstraint_setLimit__SWIG_4(this.swigCPtr, this, f, f2, f3);
    }

    public Matrix4 getAFrame() {
        return DynamicsJNI.btConeTwistConstraint_getAFrame(this.swigCPtr, this);
    }

    public Matrix4 getBFrame() {
        return DynamicsJNI.btConeTwistConstraint_getBFrame(this.swigCPtr, this);
    }

    public int getSolveTwistLimit() {
        return DynamicsJNI.btConeTwistConstraint_getSolveTwistLimit(this.swigCPtr, this);
    }

    public int getSolveSwingLimit() {
        return DynamicsJNI.btConeTwistConstraint_getSolveSwingLimit(this.swigCPtr, this);
    }

    public float getTwistLimitSign() {
        return DynamicsJNI.btConeTwistConstraint_getTwistLimitSign(this.swigCPtr, this);
    }

    public void calcAngleInfo() {
        DynamicsJNI.btConeTwistConstraint_calcAngleInfo(this.swigCPtr, this);
    }

    public void calcAngleInfo2(Matrix4 matrix4, Matrix4 matrix5, Matrix3 matrix3, Matrix3 matrix6) {
        DynamicsJNI.btConeTwistConstraint_calcAngleInfo2(this.swigCPtr, this, matrix4, matrix5, matrix3, matrix6);
    }

    public float getSwingSpan1() {
        return DynamicsJNI.btConeTwistConstraint_getSwingSpan1(this.swigCPtr, this);
    }

    public float getSwingSpan2() {
        return DynamicsJNI.btConeTwistConstraint_getSwingSpan2(this.swigCPtr, this);
    }

    public float getTwistSpan() {
        return DynamicsJNI.btConeTwistConstraint_getTwistSpan(this.swigCPtr, this);
    }

    public float getLimitSoftness() {
        return DynamicsJNI.btConeTwistConstraint_getLimitSoftness(this.swigCPtr, this);
    }

    public float getBiasFactor() {
        return DynamicsJNI.btConeTwistConstraint_getBiasFactor(this.swigCPtr, this);
    }

    public float getRelaxationFactor() {
        return DynamicsJNI.btConeTwistConstraint_getRelaxationFactor(this.swigCPtr, this);
    }

    public float getTwistAngle() {
        return DynamicsJNI.btConeTwistConstraint_getTwistAngle(this.swigCPtr, this);
    }

    public boolean isPastSwingLimit() {
        return DynamicsJNI.btConeTwistConstraint_isPastSwingLimit(this.swigCPtr, this);
    }

    public float getDamping() {
        return DynamicsJNI.btConeTwistConstraint_getDamping(this.swigCPtr, this);
    }

    public void setDamping(float f) {
        DynamicsJNI.btConeTwistConstraint_setDamping(this.swigCPtr, this, f);
    }

    public void enableMotor(boolean z) {
        DynamicsJNI.btConeTwistConstraint_enableMotor(this.swigCPtr, this, z);
    }

    public boolean isMotorEnabled() {
        return DynamicsJNI.btConeTwistConstraint_isMotorEnabled(this.swigCPtr, this);
    }

    public float getMaxMotorImpulse() {
        return DynamicsJNI.btConeTwistConstraint_getMaxMotorImpulse(this.swigCPtr, this);
    }

    public boolean isMaxMotorImpulseNormalized() {
        return DynamicsJNI.btConeTwistConstraint_isMaxMotorImpulseNormalized(this.swigCPtr, this);
    }

    public void setMaxMotorImpulse(float f) {
        DynamicsJNI.btConeTwistConstraint_setMaxMotorImpulse(this.swigCPtr, this, f);
    }

    public void setMaxMotorImpulseNormalized(float f) {
        DynamicsJNI.btConeTwistConstraint_setMaxMotorImpulseNormalized(this.swigCPtr, this, f);
    }

    public float getFixThresh() {
        return DynamicsJNI.btConeTwistConstraint_getFixThresh(this.swigCPtr, this);
    }

    public void setFixThresh(float f) {
        DynamicsJNI.btConeTwistConstraint_setFixThresh(this.swigCPtr, this, f);
    }

    public void setMotorTarget(Quaternion quaternion) {
        DynamicsJNI.btConeTwistConstraint_setMotorTarget(this.swigCPtr, this, quaternion);
    }

    public Quaternion getMotorTarget() {
        return DynamicsJNI.btConeTwistConstraint_getMotorTarget(this.swigCPtr, this);
    }

    public void setMotorTargetInConstraintSpace(Quaternion quaternion) {
        DynamicsJNI.btConeTwistConstraint_setMotorTargetInConstraintSpace(this.swigCPtr, this, quaternion);
    }

    public Vector3 GetPointForAngle(float f, float f2) {
        return DynamicsJNI.btConeTwistConstraint_GetPointForAngle(this.swigCPtr, this, f, f2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f, int i2) {
        DynamicsJNI.btConeTwistConstraint_setParam__SWIG_0(this.swigCPtr, this, i, f, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f) {
        DynamicsJNI.btConeTwistConstraint_setParam__SWIG_1(this.swigCPtr, this, i, f);
    }

    public void setFrames(Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btConeTwistConstraint_setFrames(this.swigCPtr, this, matrix4, matrix5);
    }

    public Matrix4 getFrameOffsetA() {
        return DynamicsJNI.btConeTwistConstraint_getFrameOffsetA(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetB() {
        return DynamicsJNI.btConeTwistConstraint_getFrameOffsetB(this.swigCPtr, this);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i, int i2) {
        return DynamicsJNI.btConeTwistConstraint_getParam__SWIG_0(this.swigCPtr, this, i, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i) {
        return DynamicsJNI.btConeTwistConstraint_getParam__SWIG_1(this.swigCPtr, this, i);
    }

    public int getFlags() {
        return DynamicsJNI.btConeTwistConstraint_getFlags(this.swigCPtr, this);
    }
}
