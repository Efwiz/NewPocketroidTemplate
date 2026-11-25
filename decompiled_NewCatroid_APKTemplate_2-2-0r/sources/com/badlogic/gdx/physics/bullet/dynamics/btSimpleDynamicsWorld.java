package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;

/* JADX INFO: loaded from: classes8.dex */
public class btSimpleDynamicsWorld extends btDynamicsWorld {
    private long swigCPtr;

    protected btSimpleDynamicsWorld(String str, long j, boolean z) {
        super(str, DynamicsJNI.btSimpleDynamicsWorld_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSimpleDynamicsWorld(long j, boolean z) {
        this("btSimpleDynamicsWorld", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btSimpleDynamicsWorld_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSimpleDynamicsWorld btsimpledynamicsworld) {
        if (btsimpledynamicsworld == null) {
            return 0L;
        }
        return btsimpledynamicsworld.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btSimpleDynamicsWorld(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSimpleDynamicsWorld(btDispatcher btdispatcher, btBroadphaseInterface btbroadphaseinterface, btConstraintSolver btconstraintsolver, btCollisionConfiguration btcollisionconfiguration) {
        this(DynamicsJNI.new_btSimpleDynamicsWorld(btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface, btConstraintSolver.getCPtr(btconstraintsolver), btconstraintsolver, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public int stepSimulation(float f, int i, float f2) {
        return DynamicsJNI.btSimpleDynamicsWorld_stepSimulation__SWIG_0(this.swigCPtr, this, f, i, f2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public int stepSimulation(float f, int i) {
        return DynamicsJNI.btSimpleDynamicsWorld_stepSimulation__SWIG_1(this.swigCPtr, this, f, i);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public int stepSimulation(float f) {
        return DynamicsJNI.btSimpleDynamicsWorld_stepSimulation__SWIG_2(this.swigCPtr, this, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public void addRigidBody(btRigidBody btrigidbody) {
        DynamicsJNI.btSimpleDynamicsWorld_addRigidBody__SWIG_0(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public void addRigidBody(btRigidBody btrigidbody, int i, int i2) {
        DynamicsJNI.btSimpleDynamicsWorld_addRigidBody__SWIG_1(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, i, i2);
    }
}
