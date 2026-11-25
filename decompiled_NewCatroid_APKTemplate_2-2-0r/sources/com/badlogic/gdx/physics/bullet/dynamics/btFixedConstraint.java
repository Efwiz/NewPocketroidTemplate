package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix4;

/* JADX INFO: loaded from: classes8.dex */
public class btFixedConstraint extends btGeneric6DofSpring2Constraint {
    private long swigCPtr;

    protected btFixedConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btFixedConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btFixedConstraint(long j, boolean z) {
        this("btFixedConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btFixedConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btFixedConstraint btfixedconstraint) {
        if (btfixedconstraint == null) {
            return 0L;
        }
        return btfixedconstraint.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint, com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btFixedConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btFixedConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5) {
        this(DynamicsJNI.new_btFixedConstraint(btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5), true);
    }
}
