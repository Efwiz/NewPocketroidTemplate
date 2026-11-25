package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btGeneric6DofSpring2Constraint extends btTypedConstraint {
    private long swigCPtr;

    protected btGeneric6DofSpring2Constraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btGeneric6DofSpring2Constraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGeneric6DofSpring2Constraint(long j, boolean z) {
        this("btGeneric6DofSpring2Constraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btGeneric6DofSpring2Constraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGeneric6DofSpring2Constraint btgeneric6dofspring2constraint) {
        if (btgeneric6dofspring2constraint == null) {
            return 0L;
        }
        return btgeneric6dofspring2constraint.swigCPtr;
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
                DynamicsJNI.delete_btGeneric6DofSpring2Constraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btGeneric6DofSpring2Constraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, int i) {
        this(DynamicsJNI.new_btGeneric6DofSpring2Constraint__SWIG_0(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, i), true);
    }

    public btGeneric6DofSpring2Constraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5) {
        this(DynamicsJNI.new_btGeneric6DofSpring2Constraint__SWIG_1(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5), true);
    }

    public btGeneric6DofSpring2Constraint(btRigidBody btrigidbody, Matrix4 matrix4, int i) {
        this(DynamicsJNI.new_btGeneric6DofSpring2Constraint__SWIG_2(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4, i), true);
    }

    public btGeneric6DofSpring2Constraint(btRigidBody btrigidbody, Matrix4 matrix4) {
        this(DynamicsJNI.new_btGeneric6DofSpring2Constraint__SWIG_3(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4), true);
    }

    public btRotationalLimitMotor2 getRotationalLimitMotor(int i) {
        long jBtGeneric6DofSpring2Constraint_getRotationalLimitMotor = DynamicsJNI.btGeneric6DofSpring2Constraint_getRotationalLimitMotor(this.swigCPtr, this, i);
        if (jBtGeneric6DofSpring2Constraint_getRotationalLimitMotor == 0) {
            return null;
        }
        return new btRotationalLimitMotor2(jBtGeneric6DofSpring2Constraint_getRotationalLimitMotor, false);
    }

    public btTranslationalLimitMotor2 getTranslationalLimitMotor() {
        long jBtGeneric6DofSpring2Constraint_getTranslationalLimitMotor = DynamicsJNI.btGeneric6DofSpring2Constraint_getTranslationalLimitMotor(this.swigCPtr, this);
        if (jBtGeneric6DofSpring2Constraint_getTranslationalLimitMotor == 0) {
            return null;
        }
        return new btTranslationalLimitMotor2(jBtGeneric6DofSpring2Constraint_getTranslationalLimitMotor, false);
    }

    public void calculateTransforms(Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_calculateTransforms__SWIG_0(this.swigCPtr, this, matrix4, matrix5);
    }

    public void calculateTransforms() {
        DynamicsJNI.btGeneric6DofSpring2Constraint_calculateTransforms__SWIG_1(this.swigCPtr, this);
    }

    public Matrix4 getCalculatedTransformA() {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getCalculatedTransformA(this.swigCPtr, this);
    }

    public Matrix4 getCalculatedTransformB() {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getCalculatedTransformB(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetAConst() {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getFrameOffsetAConst(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetBConst() {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getFrameOffsetBConst(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetA() {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getFrameOffsetA(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetB() {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getFrameOffsetB(this.swigCPtr, this);
    }

    public Vector3 getAxis(int i) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getAxis(this.swigCPtr, this, i);
    }

    public float getAngle(int i) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getAngle(this.swigCPtr, this, i);
    }

    public float getRelativePivotPosition(int i) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getRelativePivotPosition(this.swigCPtr, this, i);
    }

    public void setFrames(Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setFrames(this.swigCPtr, this, matrix4, matrix5);
    }

    public void setLinearLowerLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setLinearLowerLimit(this.swigCPtr, this, vector3);
    }

    public void getLinearLowerLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_getLinearLowerLimit(this.swigCPtr, this, vector3);
    }

    public void setLinearUpperLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setLinearUpperLimit(this.swigCPtr, this, vector3);
    }

    public void getLinearUpperLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_getLinearUpperLimit(this.swigCPtr, this, vector3);
    }

    public void setAngularLowerLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setAngularLowerLimit(this.swigCPtr, this, vector3);
    }

    public void setAngularLowerLimitReversed(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setAngularLowerLimitReversed(this.swigCPtr, this, vector3);
    }

    public void getAngularLowerLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_getAngularLowerLimit(this.swigCPtr, this, vector3);
    }

    public void getAngularLowerLimitReversed(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_getAngularLowerLimitReversed(this.swigCPtr, this, vector3);
    }

    public void setAngularUpperLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setAngularUpperLimit(this.swigCPtr, this, vector3);
    }

    public void setAngularUpperLimitReversed(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setAngularUpperLimitReversed(this.swigCPtr, this, vector3);
    }

    public void getAngularUpperLimit(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_getAngularUpperLimit(this.swigCPtr, this, vector3);
    }

    public void getAngularUpperLimitReversed(Vector3 vector3) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_getAngularUpperLimitReversed(this.swigCPtr, this, vector3);
    }

    public void setLimit(int i, float f, float f2) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setLimit(this.swigCPtr, this, i, f, f2);
    }

    public void setLimitReversed(int i, float f, float f2) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setLimitReversed(this.swigCPtr, this, i, f, f2);
    }

    public boolean isLimited(int i) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_isLimited(this.swigCPtr, this, i);
    }

    public void setRotationOrder(int i) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setRotationOrder(this.swigCPtr, this, i);
    }

    public int getRotationOrder() {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getRotationOrder(this.swigCPtr, this);
    }

    public void setAxis(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setAxis(this.swigCPtr, this, vector3, vector4);
    }

    public void setBounce(int i, float f) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setBounce(this.swigCPtr, this, i, f);
    }

    public void enableMotor(int i, boolean z) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_enableMotor(this.swigCPtr, this, i, z);
    }

    public void setServo(int i, boolean z) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setServo(this.swigCPtr, this, i, z);
    }

    public void setTargetVelocity(int i, float f) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setTargetVelocity(this.swigCPtr, this, i, f);
    }

    public void setServoTarget(int i, float f) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setServoTarget(this.swigCPtr, this, i, f);
    }

    public void setMaxMotorForce(int i, float f) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setMaxMotorForce(this.swigCPtr, this, i, f);
    }

    public void enableSpring(int i, boolean z) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_enableSpring(this.swigCPtr, this, i, z);
    }

    public void setStiffness(int i, float f, boolean z) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setStiffness__SWIG_0(this.swigCPtr, this, i, f, z);
    }

    public void setStiffness(int i, float f) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setStiffness__SWIG_1(this.swigCPtr, this, i, f);
    }

    public void setDamping(int i, float f, boolean z) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setDamping__SWIG_0(this.swigCPtr, this, i, f, z);
    }

    public void setDamping(int i, float f) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setDamping__SWIG_1(this.swigCPtr, this, i, f);
    }

    public void setEquilibriumPoint() {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setEquilibriumPoint__SWIG_0(this.swigCPtr, this);
    }

    public void setEquilibriumPoint(int i) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setEquilibriumPoint__SWIG_1(this.swigCPtr, this, i);
    }

    public void setEquilibriumPoint(int i, float f) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setEquilibriumPoint__SWIG_2(this.swigCPtr, this, i, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f, int i2) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setParam__SWIG_0(this.swigCPtr, this, i, f, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f) {
        DynamicsJNI.btGeneric6DofSpring2Constraint_setParam__SWIG_1(this.swigCPtr, this, i, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i, int i2) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getParam__SWIG_0(this.swigCPtr, this, i, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_getParam__SWIG_1(this.swigCPtr, this, i);
    }

    public static float btGetMatrixElem(Matrix3 matrix3, int i) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_btGetMatrixElem(matrix3, i);
    }

    public static boolean matrixToEulerXYZ(Matrix3 matrix3, Vector3 vector3) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_matrixToEulerXYZ(matrix3, vector3);
    }

    public static boolean matrixToEulerXZY(Matrix3 matrix3, Vector3 vector3) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_matrixToEulerXZY(matrix3, vector3);
    }

    public static boolean matrixToEulerYXZ(Matrix3 matrix3, Vector3 vector3) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_matrixToEulerYXZ(matrix3, vector3);
    }

    public static boolean matrixToEulerYZX(Matrix3 matrix3, Vector3 vector3) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_matrixToEulerYZX(matrix3, vector3);
    }

    public static boolean matrixToEulerZXY(Matrix3 matrix3, Vector3 vector3) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_matrixToEulerZXY(matrix3, vector3);
    }

    public static boolean matrixToEulerZYX(Matrix3 matrix3, Vector3 vector3) {
        return DynamicsJNI.btGeneric6DofSpring2Constraint_matrixToEulerZYX(matrix3, vector3);
    }
}
