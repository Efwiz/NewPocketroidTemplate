package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.RayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftRigidDynamicsWorld extends btDiscreteDynamicsWorld {
    private long swigCPtr;

    protected btSoftRigidDynamicsWorld(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btSoftRigidDynamicsWorld_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSoftRigidDynamicsWorld(long j, boolean z) {
        this("btSoftRigidDynamicsWorld", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btSoftRigidDynamicsWorld_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSoftRigidDynamicsWorld btsoftrigiddynamicsworld) {
        if (btsoftrigiddynamicsworld == null) {
            return 0L;
        }
        return btsoftrigiddynamicsworld.swigCPtr;
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
                SoftbodyJNI.delete_btSoftRigidDynamicsWorld(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSoftRigidDynamicsWorld(btDispatcher btdispatcher, btBroadphaseInterface btbroadphaseinterface, btConstraintSolver btconstraintsolver, btCollisionConfiguration btcollisionconfiguration, btSoftBodySolver btsoftbodysolver) {
        this(SoftbodyJNI.new_btSoftRigidDynamicsWorld__SWIG_0(btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface, btConstraintSolver.getCPtr(btconstraintsolver), btconstraintsolver, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration, btSoftBodySolver.getCPtr(btsoftbodysolver), btsoftbodysolver), true);
    }

    public btSoftRigidDynamicsWorld(btDispatcher btdispatcher, btBroadphaseInterface btbroadphaseinterface, btConstraintSolver btconstraintsolver, btCollisionConfiguration btcollisionconfiguration) {
        this(SoftbodyJNI.new_btSoftRigidDynamicsWorld__SWIG_1(btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface, btConstraintSolver.getCPtr(btconstraintsolver), btconstraintsolver, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
    }

    public void addSoftBody(btSoftBody btsoftbody, int i, int i2) {
        SoftbodyJNI.btSoftRigidDynamicsWorld_addSoftBody__SWIG_0(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, i, i2);
    }

    public void addSoftBody(btSoftBody btsoftbody, int i) {
        SoftbodyJNI.btSoftRigidDynamicsWorld_addSoftBody__SWIG_1(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, i);
    }

    public void addSoftBody(btSoftBody btsoftbody) {
        SoftbodyJNI.btSoftRigidDynamicsWorld_addSoftBody__SWIG_2(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody);
    }

    public void removeSoftBody(btSoftBody btsoftbody) {
        SoftbodyJNI.btSoftRigidDynamicsWorld_removeSoftBody(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody);
    }

    public int getDrawFlags() {
        return SoftbodyJNI.btSoftRigidDynamicsWorld_getDrawFlags(this.swigCPtr, this);
    }

    public void setDrawFlags(int i) {
        SoftbodyJNI.btSoftRigidDynamicsWorld_setDrawFlags(this.swigCPtr, this, i);
    }

    public btSoftBodyWorldInfo getWorldInfo() {
        return new btSoftBodyWorldInfo(SoftbodyJNI.btSoftRigidDynamicsWorld_getWorldInfo(this.swigCPtr, this), false);
    }

    public btSoftBodyWorldInfo getWorldInfoConst() {
        return new btSoftBodyWorldInfo(SoftbodyJNI.btSoftRigidDynamicsWorld_getWorldInfoConst(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t getSoftBodyArray() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t(SoftbodyJNI.btSoftRigidDynamicsWorld_getSoftBodyArray(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t getSoftBodyArrayConst() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t(SoftbodyJNI.btSoftRigidDynamicsWorld_getSoftBodyArrayConst(this.swigCPtr, this), false);
    }

    public static void rayTestSingle(Matrix4 matrix4, Matrix4 matrix5, btCollisionObject btcollisionobject, btCollisionShape btcollisionshape, Matrix4 matrix6, RayResultCallback rayResultCallback) {
        SoftbodyJNI.btSoftRigidDynamicsWorld_rayTestSingle(matrix4, matrix5, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, matrix6, RayResultCallback.getCPtr(rayResultCallback), rayResultCallback);
    }
}
