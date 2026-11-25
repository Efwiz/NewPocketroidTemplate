package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;

/* JADX INFO: loaded from: classes8.dex */
public class btCharacterControllerInterface extends btActionInterface {
    private long swigCPtr;

    protected btCharacterControllerInterface(String str, long j, boolean z) {
        super(str, DynamicsJNI.btCharacterControllerInterface_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btCharacterControllerInterface(long j, boolean z) {
        this("btCharacterControllerInterface", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btCharacterControllerInterface_SWIGUpcast(j), z);
    }

    public static long getCPtr(btCharacterControllerInterface btcharactercontrollerinterface) {
        if (btcharactercontrollerinterface == null) {
            return 0L;
        }
        return btcharactercontrollerinterface.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btCharacterControllerInterface(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setWalkDirection(Vector3 vector3) {
        DynamicsJNI.btCharacterControllerInterface_setWalkDirection(this.swigCPtr, this, vector3);
    }

    public void setVelocityForTimeInterval(Vector3 vector3, float f) {
        DynamicsJNI.btCharacterControllerInterface_setVelocityForTimeInterval(this.swigCPtr, this, vector3, f);
    }

    public void reset(btCollisionWorld btcollisionworld) {
        DynamicsJNI.btCharacterControllerInterface_reset(this.swigCPtr, this, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld);
    }

    public void warp(Vector3 vector3) {
        DynamicsJNI.btCharacterControllerInterface_warp(this.swigCPtr, this, vector3);
    }

    public void preStep(btCollisionWorld btcollisionworld) {
        DynamicsJNI.btCharacterControllerInterface_preStep(this.swigCPtr, this, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld);
    }

    public void playerStep(btCollisionWorld btcollisionworld, float f) {
        DynamicsJNI.btCharacterControllerInterface_playerStep(this.swigCPtr, this, btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld, f);
    }

    public boolean canJump() {
        return DynamicsJNI.btCharacterControllerInterface_canJump(this.swigCPtr, this);
    }

    public void jump(Vector3 vector3) {
        DynamicsJNI.btCharacterControllerInterface_jump__SWIG_0(this.swigCPtr, this, vector3);
    }

    public void jump() {
        DynamicsJNI.btCharacterControllerInterface_jump__SWIG_1(this.swigCPtr, this);
    }

    public boolean onGround() {
        return DynamicsJNI.btCharacterControllerInterface_onGround(this.swigCPtr, this);
    }

    public void setUpInterpolate(boolean z) {
        DynamicsJNI.btCharacterControllerInterface_setUpInterpolate(this.swigCPtr, this, z);
    }
}
