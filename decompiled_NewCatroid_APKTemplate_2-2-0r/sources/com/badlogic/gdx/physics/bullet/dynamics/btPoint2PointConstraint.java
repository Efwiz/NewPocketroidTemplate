package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btPoint2PointConstraint extends btTypedConstraint {
    private long swigCPtr;

    protected btPoint2PointConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btPoint2PointConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btPoint2PointConstraint(long j, boolean z) {
        this("btPoint2PointConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btPoint2PointConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btPoint2PointConstraint btpoint2pointconstraint) {
        if (btpoint2pointconstraint == null) {
            return 0L;
        }
        return btpoint2pointconstraint.swigCPtr;
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
                DynamicsJNI.delete_btPoint2PointConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btPoint2PointConstraint_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btPoint2PointConstraint_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btPoint2PointConstraint_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btPoint2PointConstraint_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btPoint2PointConstraint_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btPoint2PointConstraint_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btPoint2PointConstraint_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btPoint2PointConstraint_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setUseSolveConstraintObsolete(boolean z) {
        DynamicsJNI.btPoint2PointConstraint_useSolveConstraintObsolete_set(this.swigCPtr, this, z);
    }

    public boolean getUseSolveConstraintObsolete() {
        return DynamicsJNI.btPoint2PointConstraint_useSolveConstraintObsolete_get(this.swigCPtr, this);
    }

    public void setSetting(btConstraintSetting btconstraintsetting) {
        DynamicsJNI.btPoint2PointConstraint_setting_set(this.swigCPtr, this, btConstraintSetting.getCPtr(btconstraintsetting), btconstraintsetting);
    }

    public btConstraintSetting getSetting() {
        long jBtPoint2PointConstraint_setting_get = DynamicsJNI.btPoint2PointConstraint_setting_get(this.swigCPtr, this);
        if (jBtPoint2PointConstraint_setting_get == 0) {
            return null;
        }
        return new btConstraintSetting(jBtPoint2PointConstraint_setting_get, false);
    }

    public btPoint2PointConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4) {
        this(DynamicsJNI.new_btPoint2PointConstraint__SWIG_0(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4), true);
    }

    public btPoint2PointConstraint(btRigidBody btrigidbody, Vector3 vector3) {
        this(DynamicsJNI.new_btPoint2PointConstraint__SWIG_1(btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3), true);
    }

    public void getInfo1NonVirtual(btTypedConstraint.btConstraintInfo1 btconstraintinfo1) {
        DynamicsJNI.btPoint2PointConstraint_getInfo1NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo1.getCPtr(btconstraintinfo1), btconstraintinfo1);
    }

    public void getInfo2NonVirtual(btTypedConstraint.btConstraintInfo2 btconstraintinfo2, Matrix4 matrix4, Matrix4 matrix5) {
        DynamicsJNI.btPoint2PointConstraint_getInfo2NonVirtual(this.swigCPtr, this, btTypedConstraint.btConstraintInfo2.getCPtr(btconstraintinfo2), btconstraintinfo2, matrix4, matrix5);
    }

    public void updateRHS(float f) {
        DynamicsJNI.btPoint2PointConstraint_updateRHS(this.swigCPtr, this, f);
    }

    public void setPivotA(Vector3 vector3) {
        DynamicsJNI.btPoint2PointConstraint_setPivotA(this.swigCPtr, this, vector3);
    }

    public void setPivotB(Vector3 vector3) {
        DynamicsJNI.btPoint2PointConstraint_setPivotB(this.swigCPtr, this, vector3);
    }

    public Vector3 getPivotInA() {
        return DynamicsJNI.btPoint2PointConstraint_getPivotInA(this.swigCPtr, this);
    }

    public Vector3 getPivotInB() {
        return DynamicsJNI.btPoint2PointConstraint_getPivotInB(this.swigCPtr, this);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f, int i2) {
        DynamicsJNI.btPoint2PointConstraint_setParam__SWIG_0(this.swigCPtr, this, i, f, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public void setParam(int i, float f) {
        DynamicsJNI.btPoint2PointConstraint_setParam__SWIG_1(this.swigCPtr, this, i, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i, int i2) {
        return DynamicsJNI.btPoint2PointConstraint_getParam__SWIG_0(this.swigCPtr, this, i, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint
    public float getParam(int i) {
        return DynamicsJNI.btPoint2PointConstraint_getParam__SWIG_1(this.swigCPtr, this, i);
    }

    public int getFlags() {
        return DynamicsJNI.btPoint2PointConstraint_getFlags(this.swigCPtr, this);
    }
}
