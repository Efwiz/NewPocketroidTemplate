package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyJointLimitConstraint extends btMultiBodyConstraint {
    private long swigCPtr;

    protected btMultiBodyJointLimitConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btMultiBodyJointLimitConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultiBodyJointLimitConstraint(long j, boolean z) {
        this("btMultiBodyJointLimitConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btMultiBodyJointLimitConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultiBodyJointLimitConstraint btmultibodyjointlimitconstraint) {
        if (btmultibodyjointlimitconstraint == null) {
            return 0L;
        }
        return btmultibodyjointlimitconstraint.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btMultiBodyJointLimitConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btMultiBodyJointLimitConstraint(btMultiBody btmultibody, int i, float f, float f2) {
        this(DynamicsJNI.new_btMultiBodyJointLimitConstraint(btMultiBody.getCPtr(btmultibody), btmultibody, i, f, f2), true);
    }
}
