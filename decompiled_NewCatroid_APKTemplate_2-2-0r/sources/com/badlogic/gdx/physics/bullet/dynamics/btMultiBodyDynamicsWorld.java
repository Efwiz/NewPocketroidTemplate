package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyDynamicsWorld extends btDiscreteDynamicsWorld {
    private long swigCPtr;

    protected btMultiBodyDynamicsWorld(String str, long j, boolean z) {
        super(str, DynamicsJNI.btMultiBodyDynamicsWorld_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultiBodyDynamicsWorld(long j, boolean z) {
        this("btMultiBodyDynamicsWorld", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btMultiBodyDynamicsWorld_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultiBodyDynamicsWorld btmultibodydynamicsworld) {
        if (btmultibodydynamicsworld == null) {
            return 0L;
        }
        return btmultibodydynamicsworld.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btMultiBodyDynamicsWorld(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btMultiBodyDynamicsWorld(btDispatcher btdispatcher, btBroadphaseInterface btbroadphaseinterface, btMultiBodyConstraintSolver btmultibodyconstraintsolver, btCollisionConfiguration btcollisionconfiguration) {
        this(DynamicsJNI.new_btMultiBodyDynamicsWorld(btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface, btMultiBodyConstraintSolver.getCPtr(btmultibodyconstraintsolver), btmultibodyconstraintsolver, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
    }

    public void addMultiBody(btMultiBody btmultibody, int i, int i2) {
        DynamicsJNI.btMultiBodyDynamicsWorld_addMultiBody__SWIG_0(this.swigCPtr, this, btMultiBody.getCPtr(btmultibody), btmultibody, i, i2);
    }

    public void addMultiBody(btMultiBody btmultibody, int i) {
        DynamicsJNI.btMultiBodyDynamicsWorld_addMultiBody__SWIG_1(this.swigCPtr, this, btMultiBody.getCPtr(btmultibody), btmultibody, i);
    }

    public void addMultiBody(btMultiBody btmultibody) {
        DynamicsJNI.btMultiBodyDynamicsWorld_addMultiBody__SWIG_2(this.swigCPtr, this, btMultiBody.getCPtr(btmultibody), btmultibody);
    }

    public void removeMultiBody(btMultiBody btmultibody) {
        DynamicsJNI.btMultiBodyDynamicsWorld_removeMultiBody(this.swigCPtr, this, btMultiBody.getCPtr(btmultibody), btmultibody);
    }

    public int getNumMultibodies() {
        return DynamicsJNI.btMultiBodyDynamicsWorld_getNumMultibodies(this.swigCPtr, this);
    }

    public btMultiBody getMultiBody(int i) {
        long jBtMultiBodyDynamicsWorld_getMultiBody = DynamicsJNI.btMultiBodyDynamicsWorld_getMultiBody(this.swigCPtr, this, i);
        if (jBtMultiBodyDynamicsWorld_getMultiBody == 0) {
            return null;
        }
        return new btMultiBody(jBtMultiBodyDynamicsWorld_getMultiBody, false);
    }

    public btMultiBody getMultiBodyConst(int i) {
        long jBtMultiBodyDynamicsWorld_getMultiBodyConst = DynamicsJNI.btMultiBodyDynamicsWorld_getMultiBodyConst(this.swigCPtr, this, i);
        if (jBtMultiBodyDynamicsWorld_getMultiBodyConst == 0) {
            return null;
        }
        return new btMultiBody(jBtMultiBodyDynamicsWorld_getMultiBodyConst, false);
    }

    public void addMultiBodyConstraint(btMultiBodyConstraint btmultibodyconstraint) {
        DynamicsJNI.btMultiBodyDynamicsWorld_addMultiBodyConstraint(this.swigCPtr, this, btMultiBodyConstraint.getCPtr(btmultibodyconstraint), btmultibodyconstraint);
    }

    public int getNumMultiBodyConstraints() {
        return DynamicsJNI.btMultiBodyDynamicsWorld_getNumMultiBodyConstraints(this.swigCPtr, this);
    }

    public btMultiBodyConstraint getMultiBodyConstraint(int i) {
        long jBtMultiBodyDynamicsWorld_getMultiBodyConstraint = DynamicsJNI.btMultiBodyDynamicsWorld_getMultiBodyConstraint(this.swigCPtr, this, i);
        if (jBtMultiBodyDynamicsWorld_getMultiBodyConstraint == 0) {
            return null;
        }
        return new btMultiBodyConstraint(jBtMultiBodyDynamicsWorld_getMultiBodyConstraint, false);
    }

    public btMultiBodyConstraint getMultiBodyConstraintConst(int i) {
        long jBtMultiBodyDynamicsWorld_getMultiBodyConstraintConst = DynamicsJNI.btMultiBodyDynamicsWorld_getMultiBodyConstraintConst(this.swigCPtr, this, i);
        if (jBtMultiBodyDynamicsWorld_getMultiBodyConstraintConst == 0) {
            return null;
        }
        return new btMultiBodyConstraint(jBtMultiBodyDynamicsWorld_getMultiBodyConstraintConst, false);
    }

    public void removeMultiBodyConstraint(btMultiBodyConstraint btmultibodyconstraint) {
        DynamicsJNI.btMultiBodyDynamicsWorld_removeMultiBodyConstraint(this.swigCPtr, this, btMultiBodyConstraint.getCPtr(btmultibodyconstraint), btmultibodyconstraint);
    }

    public void integrateTransforms(float f) {
        DynamicsJNI.btMultiBodyDynamicsWorld_integrateTransforms(this.swigCPtr, this, f);
    }

    public void debugDrawMultiBodyConstraint(btMultiBodyConstraint btmultibodyconstraint) {
        DynamicsJNI.btMultiBodyDynamicsWorld_debugDrawMultiBodyConstraint(this.swigCPtr, this, btMultiBodyConstraint.getCPtr(btmultibodyconstraint), btmultibodyconstraint);
    }

    public void forwardKinematics() {
        DynamicsJNI.btMultiBodyDynamicsWorld_forwardKinematics(this.swigCPtr, this);
    }

    public void clearMultiBodyConstraintForces() {
        DynamicsJNI.btMultiBodyDynamicsWorld_clearMultiBodyConstraintForces(this.swigCPtr, this);
    }

    public void clearMultiBodyForces() {
        DynamicsJNI.btMultiBodyDynamicsWorld_clearMultiBodyForces(this.swigCPtr, this);
    }
}
