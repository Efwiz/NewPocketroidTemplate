package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.RayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyDynamicsWorld;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftMultiBodyDynamicsWorld extends btMultiBodyDynamicsWorld {
    private long swigCPtr;

    protected btSoftMultiBodyDynamicsWorld(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btSoftMultiBodyDynamicsWorld_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSoftMultiBodyDynamicsWorld(long j, boolean z) {
        this("btSoftMultiBodyDynamicsWorld", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btSoftMultiBodyDynamicsWorld_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSoftMultiBodyDynamicsWorld btsoftmultibodydynamicsworld) {
        if (btsoftmultibodydynamicsworld == null) {
            return 0L;
        }
        return btsoftmultibodydynamicsworld.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                SoftbodyJNI.delete_btSoftMultiBodyDynamicsWorld(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSoftMultiBodyDynamicsWorld(btDispatcher btdispatcher, btBroadphaseInterface btbroadphaseinterface, btMultiBodyConstraintSolver btmultibodyconstraintsolver, btCollisionConfiguration btcollisionconfiguration, btSoftBodySolver btsoftbodysolver) {
        this(SoftbodyJNI.new_btSoftMultiBodyDynamicsWorld__SWIG_0(btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface, btMultiBodyConstraintSolver.getCPtr(btmultibodyconstraintsolver), btmultibodyconstraintsolver, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration, btSoftBodySolver.getCPtr(btsoftbodysolver), btsoftbodysolver), true);
    }

    public btSoftMultiBodyDynamicsWorld(btDispatcher btdispatcher, btBroadphaseInterface btbroadphaseinterface, btMultiBodyConstraintSolver btmultibodyconstraintsolver, btCollisionConfiguration btcollisionconfiguration) {
        this(SoftbodyJNI.new_btSoftMultiBodyDynamicsWorld__SWIG_1(btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface, btMultiBodyConstraintSolver.getCPtr(btmultibodyconstraintsolver), btmultibodyconstraintsolver, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
    }

    public void addSoftBody(btSoftBody btsoftbody, int i, int i2) {
        SoftbodyJNI.btSoftMultiBodyDynamicsWorld_addSoftBody__SWIG_0(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, i, i2);
    }

    public void addSoftBody(btSoftBody btsoftbody, int i) {
        SoftbodyJNI.btSoftMultiBodyDynamicsWorld_addSoftBody__SWIG_1(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, i);
    }

    public void addSoftBody(btSoftBody btsoftbody) {
        SoftbodyJNI.btSoftMultiBodyDynamicsWorld_addSoftBody__SWIG_2(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody);
    }

    public void removeSoftBody(btSoftBody btsoftbody) {
        SoftbodyJNI.btSoftMultiBodyDynamicsWorld_removeSoftBody(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody);
    }

    public int getDrawFlags() {
        return SoftbodyJNI.btSoftMultiBodyDynamicsWorld_getDrawFlags(this.swigCPtr, this);
    }

    public void setDrawFlags(int i) {
        SoftbodyJNI.btSoftMultiBodyDynamicsWorld_setDrawFlags(this.swigCPtr, this, i);
    }

    public btSoftBodyWorldInfo getWorldInfo() {
        return new btSoftBodyWorldInfo(SoftbodyJNI.btSoftMultiBodyDynamicsWorld_getWorldInfo(this.swigCPtr, this), false);
    }

    public btSoftBodyWorldInfo getWorldInfoConst() {
        return new btSoftBodyWorldInfo(SoftbodyJNI.btSoftMultiBodyDynamicsWorld_getWorldInfoConst(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t getSoftBodyArray() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t(SoftbodyJNI.btSoftMultiBodyDynamicsWorld_getSoftBodyArray(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t getSoftBodyArrayConst() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t(SoftbodyJNI.btSoftMultiBodyDynamicsWorld_getSoftBodyArrayConst(this.swigCPtr, this), false);
    }

    public static void rayTestSingle(Matrix4 matrix4, Matrix4 matrix5, btCollisionObject btcollisionobject, btCollisionShape btcollisionshape, Matrix4 matrix6, RayResultCallback rayResultCallback) {
        SoftbodyJNI.btSoftMultiBodyDynamicsWorld_rayTestSingle(matrix4, matrix5, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, matrix6, RayResultCallback.getCPtr(rayResultCallback), rayResultCallback);
    }
}
