package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;

/* JADX INFO: loaded from: classes8.dex */
public class btDynamicsWorld extends btCollisionWorld {
    private long swigCPtr;

    protected btDynamicsWorld(String str, long j, boolean z) {
        super(str, DynamicsJNI.btDynamicsWorld_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDynamicsWorld(long j, boolean z) {
        this("btDynamicsWorld", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btDynamicsWorld_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDynamicsWorld btdynamicsworld) {
        if (btdynamicsworld == null) {
            return 0L;
        }
        return btdynamicsworld.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btDynamicsWorld(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public int stepSimulation(float f, int i, float f2) {
        return DynamicsJNI.btDynamicsWorld_stepSimulation__SWIG_0(this.swigCPtr, this, f, i, f2);
    }

    public int stepSimulation(float f, int i) {
        return DynamicsJNI.btDynamicsWorld_stepSimulation__SWIG_1(this.swigCPtr, this, f, i);
    }

    public int stepSimulation(float f) {
        return DynamicsJNI.btDynamicsWorld_stepSimulation__SWIG_2(this.swigCPtr, this, f);
    }

    public void addConstraint(btTypedConstraint bttypedconstraint, boolean z) {
        DynamicsJNI.btDynamicsWorld_addConstraint__SWIG_0(this.swigCPtr, this, btTypedConstraint.getCPtr(bttypedconstraint), bttypedconstraint, z);
    }

    public void addConstraint(btTypedConstraint bttypedconstraint) {
        DynamicsJNI.btDynamicsWorld_addConstraint__SWIG_1(this.swigCPtr, this, btTypedConstraint.getCPtr(bttypedconstraint), bttypedconstraint);
    }

    public void removeConstraint(btTypedConstraint bttypedconstraint) {
        DynamicsJNI.btDynamicsWorld_removeConstraint(this.swigCPtr, this, btTypedConstraint.getCPtr(bttypedconstraint), bttypedconstraint);
    }

    public void addAction(btActionInterface btactioninterface) {
        DynamicsJNI.btDynamicsWorld_addAction(this.swigCPtr, this, btActionInterface.getCPtr(btactioninterface), btactioninterface);
    }

    public void removeAction(btActionInterface btactioninterface) {
        DynamicsJNI.btDynamicsWorld_removeAction(this.swigCPtr, this, btActionInterface.getCPtr(btactioninterface), btactioninterface);
    }

    public void setGravity(Vector3 vector3) {
        DynamicsJNI.btDynamicsWorld_setGravity(this.swigCPtr, this, vector3);
    }

    public Vector3 getGravity() {
        return DynamicsJNI.btDynamicsWorld_getGravity(this.swigCPtr, this);
    }

    public void synchronizeMotionStates() {
        DynamicsJNI.btDynamicsWorld_synchronizeMotionStates(this.swigCPtr, this);
    }

    public void addRigidBody(btRigidBody btrigidbody) {
        DynamicsJNI.btDynamicsWorld_addRigidBody__SWIG_0(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody);
    }

    public void addRigidBody(btRigidBody btrigidbody, int i, int i2) {
        DynamicsJNI.btDynamicsWorld_addRigidBody__SWIG_1(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, i, i2);
    }

    public void removeRigidBody(btRigidBody btrigidbody) {
        DynamicsJNI.btDynamicsWorld_removeRigidBody(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody);
    }

    public void setConstraintSolver(btConstraintSolver btconstraintsolver) {
        DynamicsJNI.btDynamicsWorld_setConstraintSolver(this.swigCPtr, this, btConstraintSolver.getCPtr(btconstraintsolver), btconstraintsolver);
    }

    public btConstraintSolver getConstraintSolver() {
        long jBtDynamicsWorld_getConstraintSolver = DynamicsJNI.btDynamicsWorld_getConstraintSolver(this.swigCPtr, this);
        if (jBtDynamicsWorld_getConstraintSolver == 0) {
            return null;
        }
        return new btConstraintSolver(jBtDynamicsWorld_getConstraintSolver, false);
    }

    public int getNumConstraints() {
        return DynamicsJNI.btDynamicsWorld_getNumConstraints(this.swigCPtr, this);
    }

    public btTypedConstraint getConstraint(int i) {
        long jBtDynamicsWorld_getConstraint = DynamicsJNI.btDynamicsWorld_getConstraint(this.swigCPtr, this, i);
        if (jBtDynamicsWorld_getConstraint == 0) {
            return null;
        }
        return new btTypedConstraint(jBtDynamicsWorld_getConstraint, false);
    }

    public btTypedConstraint getConstraintConst(int i) {
        long jBtDynamicsWorld_getConstraintConst = DynamicsJNI.btDynamicsWorld_getConstraintConst(this.swigCPtr, this, i);
        if (jBtDynamicsWorld_getConstraintConst == 0) {
            return null;
        }
        return new btTypedConstraint(jBtDynamicsWorld_getConstraintConst, false);
    }

    public int getWorldType() {
        return DynamicsJNI.btDynamicsWorld_getWorldType(this.swigCPtr, this);
    }

    public void clearForces() {
        DynamicsJNI.btDynamicsWorld_clearForces(this.swigCPtr, this);
    }

    public void setInternalTickCallback(SWIGTYPE_p_f_p_btDynamicsWorld_float__void sWIGTYPE_p_f_p_btDynamicsWorld_float__void, long j, boolean z) {
        DynamicsJNI.btDynamicsWorld_setInternalTickCallback__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_f_p_btDynamicsWorld_float__void.getCPtr(sWIGTYPE_p_f_p_btDynamicsWorld_float__void), j, z);
    }

    public void setInternalTickCallback(SWIGTYPE_p_f_p_btDynamicsWorld_float__void sWIGTYPE_p_f_p_btDynamicsWorld_float__void, long j) {
        DynamicsJNI.btDynamicsWorld_setInternalTickCallback__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_f_p_btDynamicsWorld_float__void.getCPtr(sWIGTYPE_p_f_p_btDynamicsWorld_float__void), j);
    }

    public void setInternalTickCallback(SWIGTYPE_p_f_p_btDynamicsWorld_float__void sWIGTYPE_p_f_p_btDynamicsWorld_float__void) {
        DynamicsJNI.btDynamicsWorld_setInternalTickCallback__SWIG_2(this.swigCPtr, this, SWIGTYPE_p_f_p_btDynamicsWorld_float__void.getCPtr(sWIGTYPE_p_f_p_btDynamicsWorld_float__void));
    }

    public void setWorldUserInfo(long j) {
        DynamicsJNI.btDynamicsWorld_setWorldUserInfo(this.swigCPtr, this, j);
    }

    public long getWorldUserInfo() {
        return DynamicsJNI.btDynamicsWorld_getWorldUserInfo(this.swigCPtr, this);
    }

    public btContactSolverInfo getSolverInfo() {
        return new btContactSolverInfo(DynamicsJNI.btDynamicsWorld_getSolverInfo(this.swigCPtr, this), false);
    }

    public btContactSolverInfo getSolverInfoConst() {
        return new btContactSolverInfo(DynamicsJNI.btDynamicsWorld_getSolverInfoConst(this.swigCPtr, this), false);
    }

    public void addVehicle(btActionInterface btactioninterface) {
        DynamicsJNI.btDynamicsWorld_addVehicle(this.swigCPtr, this, btActionInterface.getCPtr(btactioninterface), btactioninterface);
    }

    public void removeVehicle(btActionInterface btactioninterface) {
        DynamicsJNI.btDynamicsWorld_removeVehicle(this.swigCPtr, this, btActionInterface.getCPtr(btactioninterface), btactioninterface);
    }

    public void addCharacter(btActionInterface btactioninterface) {
        DynamicsJNI.btDynamicsWorld_addCharacter(this.swigCPtr, this, btActionInterface.getCPtr(btactioninterface), btactioninterface);
    }

    public void removeCharacter(btActionInterface btactioninterface) {
        DynamicsJNI.btDynamicsWorld_removeCharacter(this.swigCPtr, this, btActionInterface.getCPtr(btactioninterface), btactioninterface);
    }
}
