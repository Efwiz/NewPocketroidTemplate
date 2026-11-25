package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btSolve2LinearConstraint extends BulletBase {
    private long swigCPtr;

    protected btSolve2LinearConstraint(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSolve2LinearConstraint(long j, boolean z) {
        this("btSolve2LinearConstraint", j, z);
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

    public static long getCPtr(btSolve2LinearConstraint btsolve2linearconstraint) {
        if (btsolve2linearconstraint == null) {
            return 0L;
        }
        return btsolve2linearconstraint.swigCPtr;
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
                DynamicsJNI.delete_btSolve2LinearConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSolve2LinearConstraint(float f, float f2) {
        this(DynamicsJNI.new_btSolve2LinearConstraint(f, f2), true);
    }

    public void resolveUnilateralPairConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix3 matrix3, Matrix3 matrix4, Vector3 vector3, float f, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, float f2, Vector3 vector8, Vector3 vector9, Vector3 vector10, float f3, Vector3 vector11, Vector3 vector12, Vector3 vector13, float f4, Vector3 vector14, SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2) {
        DynamicsJNI.btSolve2LinearConstraint_resolveUnilateralPairConstraint(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix3, matrix4, vector3, f, vector4, vector5, vector6, vector7, f2, vector8, vector9, vector10, f3, vector11, vector12, vector13, f4, vector14, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2));
    }

    public void resolveBilateralPairConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix3 matrix3, Matrix3 matrix4, Vector3 vector3, float f, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, float f2, Vector3 vector8, Vector3 vector9, Vector3 vector10, float f3, Vector3 vector11, Vector3 vector12, Vector3 vector13, float f4, Vector3 vector14, SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2) {
        DynamicsJNI.btSolve2LinearConstraint_resolveBilateralPairConstraint(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix3, matrix4, vector3, f, vector4, vector5, vector6, vector7, f2, vector8, vector9, vector10, f3, vector11, vector12, vector13, f4, vector14, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2));
    }
}
