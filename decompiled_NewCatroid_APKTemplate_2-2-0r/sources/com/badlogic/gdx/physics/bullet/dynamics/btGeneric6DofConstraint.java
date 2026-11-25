package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btGeneric6DofConstraint extends btTypedConstraint {
    private long swigCPtr;

    protected btGeneric6DofConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btGeneric6DofConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGeneric6DofConstraint(long j, boolean z) {
        this("btGeneric6DofConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btGeneric6DofConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGeneric6DofConstraint btgeneric6dofconstraint) {
        if (btgeneric6dofconstraint == null) {
            return 0L;
        }
        return btgeneric6dofconstraint.swigCPtr;
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
                DynamicsJNI.delete_btGeneric6DofConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btGeneric6DofConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btGeneric6DofConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btGeneric6DofConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btGeneric6DofConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btGeneric6DofConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btGeneric6DofConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btGeneric6DofConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btGeneric6DofConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setUseSolveConstraintObsolete(boolean z) {
        DynamicsJNI.btGeneric6DofConstraint_useSolveConstraintObsolete_set(this.swigCPtr, this, z);
    }

    public boolean getUseSolveConstraintObsolete() {
        return DynamicsJNI.btGeneric6DofConstraint_useSolveConstraintObsolete_get(this.swigCPtr, this);
    }

    public btGeneric6DofConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        this(DynamicsJNI.new_btGeneric6DofConstraint__SWIG_0(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, z), true);
    }

    public btGeneric6DofConstraint(btRigidBody btrigidbody, Matrix4 matrix4, boolean z) {
        this(DynamicsJNI.new_btGeneric6DofConstraint__SWIG_1(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4, z), true);
    }

    public void calculateTransforms(Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btGeneric6DofConstraint_calculateTransforms__SWIG_0(this.swigCPtr, this, matrix4, matrix5);
    }

    public void calculateTransforms() {
        DynamicsJNI.btGeneric6DofConstraint_calculateTransforms__SWIG_1(this.swigCPtr, this);
    }

    public Matrix4 getCalculatedTransformA() {
        return DynamicsJNI.btGeneric6DofConstraint_getCalculatedTransformA(this.swigCPtr, this);
    }

    public Matrix4 getCalculatedTransformB() {
        return DynamicsJNI.btGeneric6DofConstraint_getCalculatedTransformB(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetAConst() {
        return DynamicsJNI.btGeneric6DofConstraint_getFrameOffsetAConst(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetBConst() {
        return DynamicsJNI.btGeneric6DofConstraint_getFrameOffsetBConst(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetA() {
        return DynamicsJNI.btGeneric6DofConstraint_getFrameOffsetA(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetB() {
        return DynamicsJNI.btGeneric6DofConstraint_getFrameOffsetB(this.swigCPtr, this);
    }

    public void getInfo1NonVirtual(btTypedConstraint.btConstraintInfo1 btconstraintinfo1) {
        DynamicsJNI.btGeneric6DofConstraint_getInfo1NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo1.getCPtr(btconstraintinfo1), btconstraintinfo1);
    }

    public void getInfo2NonVirtual(btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        DynamicsJNI.btGeneric6DofConstraint_getInfo2NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2, matrix4, matrix5, vector3, vector4, vector5, vector6);
    }

    public void updateRHS(float f) {
        DynamicsJNI.btGeneric6DofConstraint_updateRHS(this.swigCPtr, this, f);
    }

    public Vector3 getAxis(int i) {
        return DynamicsJNI.btGeneric6DofConstraint_getAxis(this.swigCPtr, this, i);
    }

    public float getAngle(int i) {
        return DynamicsJNI.btGeneric6DofConstraint_getAngle(this.swigCPtr, this, i);
    }

    public float getRelativePivotPosition(int i) {
        return DynamicsJNI.btGeneric6DofConstraint_getRelativePivotPosition(this.swigCPtr, this, i);
    }

    public void setFrames(Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btGeneric6DofConstraint_setFrames(this.swigCPtr, this, matrix4, matrix5);
    }

    public boolean testAngularLimitMotor(int i) {
        return DynamicsJNI.btGeneric6DofConstraint_testAngularLimitMotor(this.swigCPtr, this, i);
    }

    public void setLinearLowerLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofConstraint_setLinearLowerLimit(this.swigCPtr, this, vector3);
    }

    public void getLinearLowerLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofConstraint_getLinearLowerLimit(this.swigCPtr, this, vector3);
    }

    public void setLinearUpperLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofConstraint_setLinearUpperLimit(this.swigCPtr, this, vector3);
    }

    public void getLinearUpperLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofConstraint_getLinearUpperLimit(this.swigCPtr, this, vector3);
    }

    public void setAngularLowerLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofConstraint_setAngularLowerLimit(this.swigCPtr, this, vector3);
    }

    public void getAngularLowerLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofConstraint_getAngularLowerLimit(this.swigCPtr, this, vector3);
    }

    public void setAngularUpperLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofConstraint_setAngularUpperLimit(this.swigCPtr, this, vector3);
    }

    public void getAngularUpperLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofConstraint_getAngularUpperLimit(this.swigCPtr, this, vector3);
    }

    public btRotationalLimitMotor getRotationalLimitMotor(int i) {
        long jBtGeneric6DofConstraint_getRotationalLimitMotor = DynamicsJNI.btGeneric6DofConstraint_getRotationalLimitMotor(this.swigCPtr, this, i);
        if (jBtGeneric6DofConstraint_getRotationalLimitMotor == 0) {
            return null;
        }
        return new btRotationalLimitMotor(jBtGeneric6DofConstraint_getRotationalLimitMotor, false);
    }

    public btTranslationalLimitMotor getTranslationalLimitMotor() {
        long jBtGeneric6DofConstraint_getTranslationalLimitMotor = DynamicsJNI.btGeneric6DofConstraint_getTranslationalLimitMotor(this.swigCPtr, this);
        if (jBtGeneric6DofConstraint_getTranslationalLimitMotor == 0) {
            return null;
        }
        return new btTranslationalLimitMotor(jBtGeneric6DofConstraint_getTranslationalLimitMotor, false);
    }

    public void setLimit(int i, float f, float f2) {
        DynamicsJNI.btGeneric6DofConstraint_setLimit(this.swigCPtr, this, i, f, f2);
    }

    public boolean isLimited(int i) {
        return DynamicsJNI.btGeneric6DofConstraint_isLimited(this.swigCPtr, this, i);
    }

    public void calcAnchorPos() {
        DynamicsJNI.btGeneric6DofConstraint_calcAnchorPos(this.swigCPtr, this);
    }

    public int get_limit_motor_info2(btRotationalLimitMotor btrotationallimitmotor, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, int i, Vector3 vector7, int i2, int i3) {
        return DynamicsJNI.btGeneric6DofConstraint_get_limit_motor_info2__SWIG_0(this.swigCPtr, this, btRotationalLimitMotor.getCPtr(btrotationallimitmotor), btrotationallimitmotor, matrix4, matrix5, vector3, vector4, vector5, vector6, btTypedConstraint.btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2, i, vector7, i2, i3);
    }

    public int get_limit_motor_info2(btRotationalLimitMotor btrotationallimitmotor, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, btTypedConstraint.btConstraintInfo2 btconstraintinfo2, int i, Vector3 vector7, int i2) {
        return DynamicsJNI.btGeneric6DofConstraint_get_limit_motor_info2__SWIG_1(this.swigCPtr, this, btRotationalLimitMotor.getCPtr(btrotationallimitmotor), btrotationallimitmotor, matrix4, matrix5, vector3, vector4, vector5, vector6, btTypedConstraint.btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2, i, vector7, i2);
    }

    public boolean getUseFrameOffset() {
        return DynamicsJNI.btGeneric6DofConstraint_getUseFrameOffset(this.swigCPtr, this);
    }

    public void setUseFrameOffset(boolean z) {
        DynamicsJNI.btGeneric6DofConstraint_setUseFrameOffset(this.swigCPtr, this, z);
    }

    public boolean getUseLinearReferenceFrameA() {
        return DynamicsJNI.btGeneric6DofConstraint_getUseLinearReferenceFrameA(this.swigCPtr, this);
    }

    public void setUseLinearReferenceFrameA(boolean z) {
        DynamicsJNI.btGeneric6DofConstraint_setUseLinearReferenceFrameA(this.swigCPtr, this, z);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f, int i2) {
        DynamicsJNI.btGeneric6DofConstraint_setParam__SWIG_0(this.swigCPtr, this, i, f, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f) {
        DynamicsJNI.btGeneric6DofConstraint_setParam__SWIG_1(this.swigCPtr, this, i, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i, int i2) {
        return DynamicsJNI.btGeneric6DofConstraint_getParam__SWIG_0(this.swigCPtr, this, i, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i) {
        return DynamicsJNI.btGeneric6DofConstraint_getParam__SWIG_1(this.swigCPtr, this, i);
    }

    public void setAxis(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btGeneric6DofConstraint_setAxis(this.swigCPtr, this, vector3, vector4);
    }

    public int getFlags() {
        return DynamicsJNI.btGeneric6DofConstraint_getFlags(this.swigCPtr, this);
    }
}
