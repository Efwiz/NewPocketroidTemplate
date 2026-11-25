package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyConstraint extends BulletBase {
    private long swigCPtr;

    protected btMultiBodyConstraint(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiBodyConstraint(long j, boolean z) {
        this("btMultiBodyConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(j, z);
    }

    public static long getCPtr(btMultiBodyConstraint btmultibodyconstraint) {
        if (btmultibodyconstraint == null) {
            return 0L;
        }
        return btmultibodyconstraint.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btMultiBodyConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btMultiBodyConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btMultiBodyConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btMultiBodyConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btMultiBodyConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btMultiBodyConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btMultiBodyConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btMultiBodyConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btMultiBodyConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void updateJacobianSizes() {
        DynamicsJNI.btMultiBodyConstraint_updateJacobianSizes(this.swigCPtr, this);
    }

    public void allocateJacobiansMultiDof() {
        DynamicsJNI.btMultiBodyConstraint_allocateJacobiansMultiDof(this.swigCPtr, this);
    }

    public void setFrameInB(Matrix3 matrix3) {
        DynamicsJNI.btMultiBodyConstraint_setFrameInB(this.swigCPtr, this, matrix3);
    }

    public void setPivotInB(Vector3 vector3) {
        DynamicsJNI.btMultiBodyConstraint_setPivotInB(this.swigCPtr, this, vector3);
    }

    public void finalizeMultiDof() {
        DynamicsJNI.btMultiBodyConstraint_finalizeMultiDof(this.swigCPtr, this);
    }

    public int getIslandIdA() {
        return DynamicsJNI.btMultiBodyConstraint_getIslandIdA(this.swigCPtr, this);
    }

    public int getIslandIdB() {
        return DynamicsJNI.btMultiBodyConstraint_getIslandIdB(this.swigCPtr, this);
    }

    public void createConstraintRows(SWIGTYPE_p_btAlignedObjectArrayT_btMultiBodySolverConstraint_t sWIGTYPE_p_btAlignedObjectArrayT_btMultiBodySolverConstraint_t, btMultiBodyJacobianData btmultibodyjacobiandata, btContactSolverInfo btcontactsolverinfo) {
        DynamicsJNI.btMultiBodyConstraint_createConstraintRows(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btMultiBodySolverConstraint_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btMultiBodySolverConstraint_t), btMultiBodyJacobianData.getCPtr(btmultibodyjacobiandata), btmultibodyjacobiandata, btContactSolverInfo.getCPtr(btcontactsolverinfo), btcontactsolverinfo);
    }

    public int getNumRows() {
        return DynamicsJNI.btMultiBodyConstraint_getNumRows(this.swigCPtr, this);
    }

    public btMultiBody getMultiBodyA() {
        long jBtMultiBodyConstraint_getMultiBodyA = DynamicsJNI.btMultiBodyConstraint_getMultiBodyA(this.swigCPtr, this);
        if (jBtMultiBodyConstraint_getMultiBodyA == 0) {
            return null;
        }
        return new btMultiBody(jBtMultiBodyConstraint_getMultiBodyA, false);
    }

    public btMultiBody getMultiBodyB() {
        long jBtMultiBodyConstraint_getMultiBodyB = DynamicsJNI.btMultiBodyConstraint_getMultiBodyB(this.swigCPtr, this);
        if (jBtMultiBodyConstraint_getMultiBodyB == 0) {
            return null;
        }
        return new btMultiBody(jBtMultiBodyConstraint_getMultiBodyB, false);
    }

    public void internalSetAppliedImpulse(int i, float f) {
        DynamicsJNI.btMultiBodyConstraint_internalSetAppliedImpulse(this.swigCPtr, this, i, f);
    }

    public float getAppliedImpulse(int i) {
        return DynamicsJNI.btMultiBodyConstraint_getAppliedImpulse(this.swigCPtr, this, i);
    }

    public float getPosition(int i) {
        return DynamicsJNI.btMultiBodyConstraint_getPosition(this.swigCPtr, this, i);
    }

    public void setPosition(int i, float f) {
        DynamicsJNI.btMultiBodyConstraint_setPosition(this.swigCPtr, this, i, f);
    }

    public boolean isUnilateral() {
        return DynamicsJNI.btMultiBodyConstraint_isUnilateral(this.swigCPtr, this);
    }

    public FloatBuffer jacobianA(int i) {
        return DynamicsJNI.btMultiBodyConstraint_jacobianA(this.swigCPtr, this, i);
    }

    public FloatBuffer jacobianAConst(int i) {
        return DynamicsJNI.btMultiBodyConstraint_jacobianAConst(this.swigCPtr, this, i);
    }

    public FloatBuffer jacobianB(int i) {
        return DynamicsJNI.btMultiBodyConstraint_jacobianB(this.swigCPtr, this, i);
    }

    public FloatBuffer jacobianBConst(int i) {
        return DynamicsJNI.btMultiBodyConstraint_jacobianBConst(this.swigCPtr, this, i);
    }

    public float getMaxAppliedImpulse() {
        return DynamicsJNI.btMultiBodyConstraint_getMaxAppliedImpulse(this.swigCPtr, this);
    }

    public void setMaxAppliedImpulse(float f) {
        DynamicsJNI.btMultiBodyConstraint_setMaxAppliedImpulse(this.swigCPtr, this, f);
    }

    public void debugDraw(btIDebugDraw btidebugdraw) {
        DynamicsJNI.btMultiBodyConstraint_debugDraw(this.swigCPtr, this, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public void setGearRatio(float f) {
        DynamicsJNI.btMultiBodyConstraint_setGearRatio(this.swigCPtr, this, f);
    }

    public void setGearAuxLink(int i) {
        DynamicsJNI.btMultiBodyConstraint_setGearAuxLink(this.swigCPtr, this, i);
    }

    public void setRelativePositionTarget(float f) {
        DynamicsJNI.btMultiBodyConstraint_setRelativePositionTarget(this.swigCPtr, this, f);
    }

    public void setErp(float f) {
        DynamicsJNI.btMultiBodyConstraint_setErp(this.swigCPtr, this, f);
    }
}
