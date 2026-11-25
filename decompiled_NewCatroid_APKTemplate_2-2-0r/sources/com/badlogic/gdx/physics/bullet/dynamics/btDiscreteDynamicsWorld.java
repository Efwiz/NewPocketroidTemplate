package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btSimulationIslandManager;

/* JADX INFO: loaded from: classes8.dex */
public class btDiscreteDynamicsWorld extends btDynamicsWorld {
    private long swigCPtr;

    protected btDiscreteDynamicsWorld(String str, long j, boolean z) {
        super(str, DynamicsJNI.btDiscreteDynamicsWorld_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDiscreteDynamicsWorld(long j, boolean z) {
        this("btDiscreteDynamicsWorld", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btDiscreteDynamicsWorld_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDiscreteDynamicsWorld btdiscretedynamicsworld) {
        if (btdiscretedynamicsworld == null) {
            return 0L;
        }
        return btdiscretedynamicsworld.swigCPtr;
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
                DynamicsJNI.delete_btDiscreteDynamicsWorld(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btDiscreteDynamicsWorld_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btDiscreteDynamicsWorld_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btDiscreteDynamicsWorld_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btDiscreteDynamicsWorld_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btDiscreteDynamicsWorld_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btDiscreteDynamicsWorld_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btDiscreteDynamicsWorld_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btDiscreteDynamicsWorld_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btDiscreteDynamicsWorld(btDispatcher btdispatcher, btBroadphaseInterface btbroadphaseinterface, btConstraintSolver btconstraintsolver, btCollisionConfiguration btcollisionconfiguration) {
        this(DynamicsJNI.new_btDiscreteDynamicsWorld(btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface, btConstraintSolver.getCPtr(btconstraintsolver), btconstraintsolver, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public int stepSimulation(float f, int i, float f2) {
        return DynamicsJNI.btDiscreteDynamicsWorld_stepSimulation__SWIG_0(this.swigCPtr, this, f, i, f2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public int stepSimulation(float f, int i) {
        return DynamicsJNI.btDiscreteDynamicsWorld_stepSimulation__SWIG_1(this.swigCPtr, this, f, i);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public int stepSimulation(float f) {
        return DynamicsJNI.btDiscreteDynamicsWorld_stepSimulation__SWIG_2(this.swigCPtr, this, f);
    }

    public void synchronizeSingleMotionState(btRigidBody btrigidbody) {
        DynamicsJNI.btDiscreteDynamicsWorld_synchronizeSingleMotionState(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public void addConstraint(btTypedConstraint bttypedconstraint, boolean z) {
        DynamicsJNI.btDiscreteDynamicsWorld_addConstraint__SWIG_0(this.swigCPtr, this, btTypedConstraint.getCPtr(bttypedconstraint), bttypedconstraint, z);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public void addConstraint(btTypedConstraint bttypedconstraint) {
        DynamicsJNI.btDiscreteDynamicsWorld_addConstraint__SWIG_1(this.swigCPtr, this, btTypedConstraint.getCPtr(bttypedconstraint), bttypedconstraint);
    }

    public btSimulationIslandManager getSimulationIslandManager() {
        long jBtDiscreteDynamicsWorld_getSimulationIslandManager = DynamicsJNI.btDiscreteDynamicsWorld_getSimulationIslandManager(this.swigCPtr, this);
        if (jBtDiscreteDynamicsWorld_getSimulationIslandManager == 0) {
            return null;
        }
        return new btSimulationIslandManager(jBtDiscreteDynamicsWorld_getSimulationIslandManager, false);
    }

    public btSimulationIslandManager getSimulationIslandManagerConst() {
        long jBtDiscreteDynamicsWorld_getSimulationIslandManagerConst = DynamicsJNI.btDiscreteDynamicsWorld_getSimulationIslandManagerConst(this.swigCPtr, this);
        if (jBtDiscreteDynamicsWorld_getSimulationIslandManagerConst == 0) {
            return null;
        }
        return new btSimulationIslandManager(jBtDiscreteDynamicsWorld_getSimulationIslandManagerConst, false);
    }

    public btCollisionWorld getCollisionWorld() {
        long jBtDiscreteDynamicsWorld_getCollisionWorld = DynamicsJNI.btDiscreteDynamicsWorld_getCollisionWorld(this.swigCPtr, this);
        if (jBtDiscreteDynamicsWorld_getCollisionWorld == 0) {
            return null;
        }
        return new btCollisionWorld(jBtDiscreteDynamicsWorld_getCollisionWorld, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionWorld
    public void addCollisionObject(btCollisionObject btcollisionobject, int i, int i2) {
        DynamicsJNI.btDiscreteDynamicsWorld_addCollisionObject__SWIG_0(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i, i2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionWorld
    public void addCollisionObject(btCollisionObject btcollisionobject, int i) {
        DynamicsJNI.btDiscreteDynamicsWorld_addCollisionObject__SWIG_1(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionWorld
    public void addCollisionObject(btCollisionObject btcollisionobject) {
        DynamicsJNI.btDiscreteDynamicsWorld_addCollisionObject__SWIG_2(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public void addRigidBody(btRigidBody btrigidbody) {
        DynamicsJNI.btDiscreteDynamicsWorld_addRigidBody__SWIG_0(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
    public void addRigidBody(btRigidBody btrigidbody, int i, int i2) {
        DynamicsJNI.btDiscreteDynamicsWorld_addRigidBody__SWIG_1(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, i, i2);
    }

    public void debugDrawConstraint(btTypedConstraint bttypedconstraint) {
        DynamicsJNI.btDiscreteDynamicsWorld_debugDrawConstraint(this.swigCPtr, this, btTypedConstraint.getCPtr(bttypedconstraint), bttypedconstraint);
    }

    public void applyGravity() {
        DynamicsJNI.btDiscreteDynamicsWorld_applyGravity(this.swigCPtr, this);
    }

    public void setNumTasks(int i) {
        DynamicsJNI.btDiscreteDynamicsWorld_setNumTasks(this.swigCPtr, this, i);
    }

    public void updateVehicles(float f) {
        DynamicsJNI.btDiscreteDynamicsWorld_updateVehicles(this.swigCPtr, this, f);
    }

    public void setSynchronizeAllMotionStates(boolean z) {
        DynamicsJNI.btDiscreteDynamicsWorld_setSynchronizeAllMotionStates(this.swigCPtr, this, z);
    }

    public boolean getSynchronizeAllMotionStates() {
        return DynamicsJNI.btDiscreteDynamicsWorld_getSynchronizeAllMotionStates(this.swigCPtr, this);
    }

    public void setApplySpeculativeContactRestitution(boolean z) {
        DynamicsJNI.btDiscreteDynamicsWorld_setApplySpeculativeContactRestitution(this.swigCPtr, this, z);
    }

    public boolean getApplySpeculativeContactRestitution() {
        return DynamicsJNI.btDiscreteDynamicsWorld_getApplySpeculativeContactRestitution(this.swigCPtr, this);
    }

    public void setLatencyMotionStateInterpolation(boolean z) {
        DynamicsJNI.btDiscreteDynamicsWorld_setLatencyMotionStateInterpolation(this.swigCPtr, this, z);
    }

    public boolean getLatencyMotionStateInterpolation() {
        return DynamicsJNI.btDiscreteDynamicsWorld_getLatencyMotionStateInterpolation(this.swigCPtr, this);
    }
}
