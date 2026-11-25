package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;

/* JADX INFO: loaded from: classes8.dex */
public class btKinematicCharacterController extends btCharacterControllerInterface {
    private long swigCPtr;

    protected btKinematicCharacterController(String str, long j, boolean z) {
        super(str, DynamicsJNI.btKinematicCharacterController_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btKinematicCharacterController(long j, boolean z) {
        this("btKinematicCharacterController", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btCharacterControllerInterface, com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btKinematicCharacterController_SWIGUpcast(j), z);
    }

    public static long getCPtr(btKinematicCharacterController btkinematiccharactercontroller) {
        if (btkinematiccharactercontroller == null) {
            return 0L;
        }
        return btkinematiccharactercontroller.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btCharacterControllerInterface, com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btCharacterControllerInterface, com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btKinematicCharacterController(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btKinematicCharacterController_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btKinematicCharacterController_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btKinematicCharacterController_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btKinematicCharacterController_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btKinematicCharacterController_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btKinematicCharacterController_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btKinematicCharacterController_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btKinematicCharacterController_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btKinematicCharacterController(btPairCachingGhostObject btpaircachingghostobject, btConvexShape btconvexshape, float f, Vector3 vector3) {
        this(DynamicsJNI.new_btKinematicCharacterController__SWIG_0(btPairCachingGhostObject.getCPtr(btpaircachingghostobject), btpaircachingghostobject, btConvexShape.getCPtr(btconvexshape), btconvexshape, f, vector3), true);
    }

    public btKinematicCharacterController(btPairCachingGhostObject btpaircachingghostobject, btConvexShape btconvexshape, float f) {
        this(DynamicsJNI.new_btKinematicCharacterController__SWIG_1(btPairCachingGhostObject.getCPtr(btpaircachingghostobject), btpaircachingghostobject, btConvexShape.getCPtr(btconvexshape), btconvexshape, f), true);
    }

    public void setUp(Vector3 vector3) {
        DynamicsJNI.btKinematicCharacterController_setUp(this.swigCPtr, this, vector3);
    }

    public Vector3 getUp() {
        return DynamicsJNI.btKinematicCharacterController_getUp(this.swigCPtr, this);
    }

    public void setAngularVelocity(Vector3 vector3) {
        DynamicsJNI.btKinematicCharacterController_setAngularVelocity(this.swigCPtr, this, vector3);
    }

    public Vector3 getAngularVelocity() {
        return DynamicsJNI.btKinematicCharacterController_getAngularVelocity(this.swigCPtr, this);
    }

    public void setLinearVelocity(Vector3 vector3) {
        DynamicsJNI.btKinematicCharacterController_setLinearVelocity(this.swigCPtr, this, vector3);
    }

    public Vector3 getLinearVelocity() {
        return DynamicsJNI.btKinematicCharacterController_getLinearVelocity(this.swigCPtr, this);
    }

    public void setLinearDamping(float f) {
        DynamicsJNI.btKinematicCharacterController_setLinearDamping(this.swigCPtr, this, f);
    }

    public float getLinearDamping() {
        return DynamicsJNI.btKinematicCharacterController_getLinearDamping(this.swigCPtr, this);
    }

    public void setAngularDamping(float f) {
        DynamicsJNI.btKinematicCharacterController_setAngularDamping(this.swigCPtr, this, f);
    }

    public float getAngularDamping() {
        return DynamicsJNI.btKinematicCharacterController_getAngularDamping(this.swigCPtr, this);
    }

    public void setStepHeight(float f) {
        DynamicsJNI.btKinematicCharacterController_setStepHeight(this.swigCPtr, this, f);
    }

    public float getStepHeight() {
        return DynamicsJNI.btKinematicCharacterController_getStepHeight(this.swigCPtr, this);
    }

    public void setFallSpeed(float f) {
        DynamicsJNI.btKinematicCharacterController_setFallSpeed(this.swigCPtr, this, f);
    }

    public float getFallSpeed() {
        return DynamicsJNI.btKinematicCharacterController_getFallSpeed(this.swigCPtr, this);
    }

    public void setJumpSpeed(float f) {
        DynamicsJNI.btKinematicCharacterController_setJumpSpeed(this.swigCPtr, this, f);
    }

    public float getJumpSpeed() {
        return DynamicsJNI.btKinematicCharacterController_getJumpSpeed(this.swigCPtr, this);
    }

    public void setMaxJumpHeight(float f) {
        DynamicsJNI.btKinematicCharacterController_setMaxJumpHeight(this.swigCPtr, this, f);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btCharacterControllerInterface
    public void jump(Vector3 vector3) {
        DynamicsJNI.btKinematicCharacterController_jump__SWIG_0(this.swigCPtr, this, vector3);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btCharacterControllerInterface
    public void jump() {
        DynamicsJNI.btKinematicCharacterController_jump__SWIG_1(this.swigCPtr, this);
    }

    public void applyImpulse(Vector3 vector3) {
        DynamicsJNI.btKinematicCharacterController_applyImpulse(this.swigCPtr, this, vector3);
    }

    public void setGravity(Vector3 vector3) {
        DynamicsJNI.btKinematicCharacterController_setGravity(this.swigCPtr, this, vector3);
    }

    public Vector3 getGravity() {
        return DynamicsJNI.btKinematicCharacterController_getGravity(this.swigCPtr, this);
    }

    public void setMaxSlope(float f) {
        DynamicsJNI.btKinematicCharacterController_setMaxSlope(this.swigCPtr, this, f);
    }

    public float getMaxSlope() {
        return DynamicsJNI.btKinematicCharacterController_getMaxSlope(this.swigCPtr, this);
    }

    public void setMaxPenetrationDepth(float f) {
        DynamicsJNI.btKinematicCharacterController_setMaxPenetrationDepth(this.swigCPtr, this, f);
    }

    public float getMaxPenetrationDepth() {
        return DynamicsJNI.btKinematicCharacterController_getMaxPenetrationDepth(this.swigCPtr, this);
    }

    public btPairCachingGhostObject getGhostObject() {
        long jBtKinematicCharacterController_getGhostObject = DynamicsJNI.btKinematicCharacterController_getGhostObject(this.swigCPtr, this);
        if (jBtKinematicCharacterController_getGhostObject == 0) {
            return null;
        }
        return new btPairCachingGhostObject(jBtKinematicCharacterController_getGhostObject, false);
    }

    public void setUseGhostSweepTest(boolean z) {
        DynamicsJNI.btKinematicCharacterController_setUseGhostSweepTest(this.swigCPtr, this, z);
    }
}
