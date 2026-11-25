package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btSliderConstraint extends btTypedConstraint {
    private long swigCPtr;

    protected btSliderConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btSliderConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSliderConstraint(long j, boolean z) {
        this("btSliderConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btSliderConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSliderConstraint btsliderconstraint) {
        if (btsliderconstraint == null) {
            return 0L;
        }
        return btsliderconstraint.swigCPtr;
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
                DynamicsJNI.delete_btSliderConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btSliderConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btSliderConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btSliderConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btSliderConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btSliderConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btSliderConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btSliderConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btSliderConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btSliderConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        this(DynamicsJNI.new_btSliderConstraint__SWIG_0(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, z), true);
    }

    public btSliderConstraint(btRigidBody btrigidbody, Matrix4 matrix4, boolean z) {
        this(DynamicsJNI.new_btSliderConstraint__SWIG_1(btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4, z), true);
    }

    public void getInfo1NonVirtual(btTypedConstraint.btConstraintInfo1 btconstraintinfo1) {
        DynamicsJNI.btSliderConstraint_getInfo1NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo1.getCPtr(btconstraintinfo1), btconstraintinfo1);
    }

    public void getInfo2NonVirtual(btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5, Vector3 vector3, Vector3 vector4, float f, float f2) {
        DynamicsJNI.btSliderConstraint_getInfo2NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2, matrix4, matrix5, vector3, vector4, f, f2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public btRigidBody getRigidBodyAConst() {
        return btRigidBody.getInstance(DynamicsJNI.btSliderConstraint_getRigidBodyAConst(this.swigCPtr, this), false);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public btRigidBody getRigidBodyBConst() {
        return btRigidBody.getInstance(DynamicsJNI.btSliderConstraint_getRigidBodyBConst(this.swigCPtr, this), false);
    }

    public Matrix4 getCalculatedTransformA() {
        return DynamicsJNI.btSliderConstraint_getCalculatedTransformA(this.swigCPtr, this);
    }

    public Matrix4 getCalculatedTransformB() {
        return DynamicsJNI.btSliderConstraint_getCalculatedTransformB(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetAConst() {
        return DynamicsJNI.btSliderConstraint_getFrameOffsetAConst(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetBConst() {
        return DynamicsJNI.btSliderConstraint_getFrameOffsetBConst(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetA() {
        return DynamicsJNI.btSliderConstraint_getFrameOffsetA(this.swigCPtr, this);
    }

    public Matrix4 getFrameOffsetB() {
        return DynamicsJNI.btSliderConstraint_getFrameOffsetB(this.swigCPtr, this);
    }

    public float getLowerLinLimit() {
        return DynamicsJNI.btSliderConstraint_getLowerLinLimit(this.swigCPtr, this);
    }

    public void setLowerLinLimit(float f) {
        DynamicsJNI.btSliderConstraint_setLowerLinLimit(this.swigCPtr, this, f);
    }

    public float getUpperLinLimit() {
        return DynamicsJNI.btSliderConstraint_getUpperLinLimit(this.swigCPtr, this);
    }

    public void setUpperLinLimit(float f) {
        DynamicsJNI.btSliderConstraint_setUpperLinLimit(this.swigCPtr, this, f);
    }

    public float getLowerAngLimit() {
        return DynamicsJNI.btSliderConstraint_getLowerAngLimit(this.swigCPtr, this);
    }

    public void setLowerAngLimit(float f) {
        DynamicsJNI.btSliderConstraint_setLowerAngLimit(this.swigCPtr, this, f);
    }

    public float getUpperAngLimit() {
        return DynamicsJNI.btSliderConstraint_getUpperAngLimit(this.swigCPtr, this);
    }

    public void setUpperAngLimit(float f) {
        DynamicsJNI.btSliderConstraint_setUpperAngLimit(this.swigCPtr, this, f);
    }

    public boolean getUseLinearReferenceFrameA() {
        return DynamicsJNI.btSliderConstraint_getUseLinearReferenceFrameA(this.swigCPtr, this);
    }

    public float getSoftnessDirLin() {
        return DynamicsJNI.btSliderConstraint_getSoftnessDirLin(this.swigCPtr, this);
    }

    public float getRestitutionDirLin() {
        return DynamicsJNI.btSliderConstraint_getRestitutionDirLin(this.swigCPtr, this);
    }

    public float getDampingDirLin() {
        return DynamicsJNI.btSliderConstraint_getDampingDirLin(this.swigCPtr, this);
    }

    public float getSoftnessDirAng() {
        return DynamicsJNI.btSliderConstraint_getSoftnessDirAng(this.swigCPtr, this);
    }

    public float getRestitutionDirAng() {
        return DynamicsJNI.btSliderConstraint_getRestitutionDirAng(this.swigCPtr, this);
    }

    public float getDampingDirAng() {
        return DynamicsJNI.btSliderConstraint_getDampingDirAng(this.swigCPtr, this);
    }

    public float getSoftnessLimLin() {
        return DynamicsJNI.btSliderConstraint_getSoftnessLimLin(this.swigCPtr, this);
    }

    public float getRestitutionLimLin() {
        return DynamicsJNI.btSliderConstraint_getRestitutionLimLin(this.swigCPtr, this);
    }

    public float getDampingLimLin() {
        return DynamicsJNI.btSliderConstraint_getDampingLimLin(this.swigCPtr, this);
    }

    public float getSoftnessLimAng() {
        return DynamicsJNI.btSliderConstraint_getSoftnessLimAng(this.swigCPtr, this);
    }

    public float getRestitutionLimAng() {
        return DynamicsJNI.btSliderConstraint_getRestitutionLimAng(this.swigCPtr, this);
    }

    public float getDampingLimAng() {
        return DynamicsJNI.btSliderConstraint_getDampingLimAng(this.swigCPtr, this);
    }

    public float getSoftnessOrthoLin() {
        return DynamicsJNI.btSliderConstraint_getSoftnessOrthoLin(this.swigCPtr, this);
    }

    public float getRestitutionOrthoLin() {
        return DynamicsJNI.btSliderConstraint_getRestitutionOrthoLin(this.swigCPtr, this);
    }

    public float getDampingOrthoLin() {
        return DynamicsJNI.btSliderConstraint_getDampingOrthoLin(this.swigCPtr, this);
    }

    public float getSoftnessOrthoAng() {
        return DynamicsJNI.btSliderConstraint_getSoftnessOrthoAng(this.swigCPtr, this);
    }

    public float getRestitutionOrthoAng() {
        return DynamicsJNI.btSliderConstraint_getRestitutionOrthoAng(this.swigCPtr, this);
    }

    public float getDampingOrthoAng() {
        return DynamicsJNI.btSliderConstraint_getDampingOrthoAng(this.swigCPtr, this);
    }

    public void setSoftnessDirLin(float f) {
        DynamicsJNI.btSliderConstraint_setSoftnessDirLin(this.swigCPtr, this, f);
    }

    public void setRestitutionDirLin(float f) {
        DynamicsJNI.btSliderConstraint_setRestitutionDirLin(this.swigCPtr, this, f);
    }

    public void setDampingDirLin(float f) {
        DynamicsJNI.btSliderConstraint_setDampingDirLin(this.swigCPtr, this, f);
    }

    public void setSoftnessDirAng(float f) {
        DynamicsJNI.btSliderConstraint_setSoftnessDirAng(this.swigCPtr, this, f);
    }

    public void setRestitutionDirAng(float f) {
        DynamicsJNI.btSliderConstraint_setRestitutionDirAng(this.swigCPtr, this, f);
    }

    public void setDampingDirAng(float f) {
        DynamicsJNI.btSliderConstraint_setDampingDirAng(this.swigCPtr, this, f);
    }

    public void setSoftnessLimLin(float f) {
        DynamicsJNI.btSliderConstraint_setSoftnessLimLin(this.swigCPtr, this, f);
    }

    public void setRestitutionLimLin(float f) {
        DynamicsJNI.btSliderConstraint_setRestitutionLimLin(this.swigCPtr, this, f);
    }

    public void setDampingLimLin(float f) {
        DynamicsJNI.btSliderConstraint_setDampingLimLin(this.swigCPtr, this, f);
    }

    public void setSoftnessLimAng(float f) {
        DynamicsJNI.btSliderConstraint_setSoftnessLimAng(this.swigCPtr, this, f);
    }

    public void setRestitutionLimAng(float f) {
        DynamicsJNI.btSliderConstraint_setRestitutionLimAng(this.swigCPtr, this, f);
    }

    public void setDampingLimAng(float f) {
        DynamicsJNI.btSliderConstraint_setDampingLimAng(this.swigCPtr, this, f);
    }

    public void setSoftnessOrthoLin(float f) {
        DynamicsJNI.btSliderConstraint_setSoftnessOrthoLin(this.swigCPtr, this, f);
    }

    public void setRestitutionOrthoLin(float f) {
        DynamicsJNI.btSliderConstraint_setRestitutionOrthoLin(this.swigCPtr, this, f);
    }

    public void setDampingOrthoLin(float f) {
        DynamicsJNI.btSliderConstraint_setDampingOrthoLin(this.swigCPtr, this, f);
    }

    public void setSoftnessOrthoAng(float f) {
        DynamicsJNI.btSliderConstraint_setSoftnessOrthoAng(this.swigCPtr, this, f);
    }

    public void setRestitutionOrthoAng(float f) {
        DynamicsJNI.btSliderConstraint_setRestitutionOrthoAng(this.swigCPtr, this, f);
    }

    public void setDampingOrthoAng(float f) {
        DynamicsJNI.btSliderConstraint_setDampingOrthoAng(this.swigCPtr, this, f);
    }

    public void setPoweredLinMotor(boolean z) {
        DynamicsJNI.btSliderConstraint_setPoweredLinMotor(this.swigCPtr, this, z);
    }

    public boolean getPoweredLinMotor() {
        return DynamicsJNI.btSliderConstraint_getPoweredLinMotor(this.swigCPtr, this);
    }

    public void setTargetLinMotorVelocity(float f) {
        DynamicsJNI.btSliderConstraint_setTargetLinMotorVelocity(this.swigCPtr, this, f);
    }

    public float getTargetLinMotorVelocity() {
        return DynamicsJNI.btSliderConstraint_getTargetLinMotorVelocity(this.swigCPtr, this);
    }

    public void setMaxLinMotorForce(float f) {
        DynamicsJNI.btSliderConstraint_setMaxLinMotorForce(this.swigCPtr, this, f);
    }

    public float getMaxLinMotorForce() {
        return DynamicsJNI.btSliderConstraint_getMaxLinMotorForce(this.swigCPtr, this);
    }

    public void setPoweredAngMotor(boolean z) {
        DynamicsJNI.btSliderConstraint_setPoweredAngMotor(this.swigCPtr, this, z);
    }

    public boolean getPoweredAngMotor() {
        return DynamicsJNI.btSliderConstraint_getPoweredAngMotor(this.swigCPtr, this);
    }

    public void setTargetAngMotorVelocity(float f) {
        DynamicsJNI.btSliderConstraint_setTargetAngMotorVelocity(this.swigCPtr, this, f);
    }

    public float getTargetAngMotorVelocity() {
        return DynamicsJNI.btSliderConstraint_getTargetAngMotorVelocity(this.swigCPtr, this);
    }

    public void setMaxAngMotorForce(float f) {
        DynamicsJNI.btSliderConstraint_setMaxAngMotorForce(this.swigCPtr, this, f);
    }

    public float getMaxAngMotorForce() {
        return DynamicsJNI.btSliderConstraint_getMaxAngMotorForce(this.swigCPtr, this);
    }

    public float getLinearPos() {
        return DynamicsJNI.btSliderConstraint_getLinearPos(this.swigCPtr, this);
    }

    public float getAngularPos() {
        return DynamicsJNI.btSliderConstraint_getAngularPos(this.swigCPtr, this);
    }

    public boolean getSolveLinLimit() {
        return DynamicsJNI.btSliderConstraint_getSolveLinLimit(this.swigCPtr, this);
    }

    public float getLinDepth() {
        return DynamicsJNI.btSliderConstraint_getLinDepth(this.swigCPtr, this);
    }

    public boolean getSolveAngLimit() {
        return DynamicsJNI.btSliderConstraint_getSolveAngLimit(this.swigCPtr, this);
    }

    public float getAngDepth() {
        return DynamicsJNI.btSliderConstraint_getAngDepth(this.swigCPtr, this);
    }

    public void calculateTransforms(Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btSliderConstraint_calculateTransforms(this.swigCPtr, this, matrix4, matrix5);
    }

    public void testLinLimits() {
        DynamicsJNI.btSliderConstraint_testLinLimits(this.swigCPtr, this);
    }

    public void testAngLimits() {
        DynamicsJNI.btSliderConstraint_testAngLimits(this.swigCPtr, this);
    }

    public Vector3 getAncorInA() {
        return DynamicsJNI.btSliderConstraint_getAncorInA(this.swigCPtr, this);
    }

    public Vector3 getAncorInB() {
        return DynamicsJNI.btSliderConstraint_getAncorInB(this.swigCPtr, this);
    }

    public boolean getUseFrameOffset() {
        return DynamicsJNI.btSliderConstraint_getUseFrameOffset(this.swigCPtr, this);
    }

    public void setUseFrameOffset(boolean z) {
        DynamicsJNI.btSliderConstraint_setUseFrameOffset(this.swigCPtr, this, z);
    }

    public void setFrames(Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btSliderConstraint_setFrames(this.swigCPtr, this, matrix4, matrix5);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f, int i2) {
        DynamicsJNI.btSliderConstraint_setParam__SWIG_0(this.swigCPtr, this, i, f, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f) {
        DynamicsJNI.btSliderConstraint_setParam__SWIG_1(this.swigCPtr, this, i, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i, int i2) {
        return DynamicsJNI.btSliderConstraint_getParam__SWIG_0(this.swigCPtr, this, i, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i) {
        return DynamicsJNI.btSliderConstraint_getParam__SWIG_1(this.swigCPtr, this, i);
    }

    public int getFlags() {
        return DynamicsJNI.btSliderConstraint_getFlags(this.swigCPtr, this);
    }
}
