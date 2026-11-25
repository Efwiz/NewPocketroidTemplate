package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;

/* JADX INFO: loaded from: classes8.dex */
public class btContactConstraint extends btTypedConstraint {
    private long swigCPtr;

    protected btContactConstraint(String str, long j, boolean z) {
        super(str, DynamicsJNI.btContactConstraint_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btContactConstraint(long j, boolean z) {
        this("btContactConstraint", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint, com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btContactConstraint_SWIGUpcast(j), z);
    }

    public static long getCPtr(btContactConstraint btcontactconstraint) {
        if (btcontactconstraint == null) {
            return 0L;
        }
        return btcontactconstraint.swigCPtr;
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
                DynamicsJNI.delete_btContactConstraint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setContactManifold(btPersistentManifold btpersistentmanifold) {
        DynamicsJNI.btContactConstraint_setContactManifold(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public btPersistentManifold getContactManifold() {
        long jBtContactConstraint_getContactManifold = DynamicsJNI.btContactConstraint_getContactManifold(this.swigCPtr, this);
        if (jBtContactConstraint_getContactManifold == 0) {
            return null;
        }
        return new btPersistentManifold(jBtContactConstraint_getContactManifold, false);
    }

    public btPersistentManifold getContactManifoldConst() {
        long jBtContactConstraint_getContactManifoldConst = DynamicsJNI.btContactConstraint_getContactManifoldConst(this.swigCPtr, this);
        if (jBtContactConstraint_getContactManifoldConst == 0) {
            return null;
        }
        return new btPersistentManifold(jBtContactConstraint_getContactManifoldConst, false);
    }
}
