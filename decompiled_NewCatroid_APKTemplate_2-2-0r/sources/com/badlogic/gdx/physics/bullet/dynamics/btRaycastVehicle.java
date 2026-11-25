package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btRaycastVehicle extends btActionInterface {
    private long swigCPtr;

    protected btRaycastVehicle(String str, long j, boolean z) {
        super(str, DynamicsJNI.btRaycastVehicle_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btRaycastVehicle(long j, boolean z) {
        this("btRaycastVehicle", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btRaycastVehicle_SWIGUpcast(j), z);
    }

    public static long getCPtr(btRaycastVehicle btraycastvehicle) {
        if (btraycastvehicle == null) {
            return 0L;
        }
        return btraycastvehicle.swigCPtr;
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
                DynamicsJNI.delete_btRaycastVehicle(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class btVehicleTuning extends BulletBase {
        private long swigCPtr;

        protected btVehicleTuning(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public btVehicleTuning(long j, boolean z) {
            this("btVehicleTuning", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(j, z);
        }

        public static long getCPtr(btVehicleTuning btvehicletuning) {
            if (btvehicletuning == null) {
                return 0L;
            }
            return btvehicletuning.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    DynamicsJNI.delete_btRaycastVehicle_btVehicleTuning(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public btVehicleTuning() {
            this(DynamicsJNI.new_btRaycastVehicle_btVehicleTuning(), true);
        }

        public void setSuspensionStiffness(float f) {
            DynamicsJNI.btRaycastVehicle_btVehicleTuning_suspensionStiffness_set(this.swigCPtr, this, f);
        }

        public float getSuspensionStiffness() {
            return DynamicsJNI.btRaycastVehicle_btVehicleTuning_suspensionStiffness_get(this.swigCPtr, this);
        }

        public void setSuspensionCompression(float f) {
            DynamicsJNI.btRaycastVehicle_btVehicleTuning_suspensionCompression_set(this.swigCPtr, this, f);
        }

        public float getSuspensionCompression() {
            return DynamicsJNI.btRaycastVehicle_btVehicleTuning_suspensionCompression_get(this.swigCPtr, this);
        }

        public void setSuspensionDamping(float f) {
            DynamicsJNI.btRaycastVehicle_btVehicleTuning_suspensionDamping_set(this.swigCPtr, this, f);
        }

        public float getSuspensionDamping() {
            return DynamicsJNI.btRaycastVehicle_btVehicleTuning_suspensionDamping_get(this.swigCPtr, this);
        }

        public void setMaxSuspensionTravelCm(float f) {
            DynamicsJNI.btRaycastVehicle_btVehicleTuning_maxSuspensionTravelCm_set(this.swigCPtr, this, f);
        }

        public float getMaxSuspensionTravelCm() {
            return DynamicsJNI.btRaycastVehicle_btVehicleTuning_maxSuspensionTravelCm_get(this.swigCPtr, this);
        }

        public void setFrictionSlip(float f) {
            DynamicsJNI.btRaycastVehicle_btVehicleTuning_frictionSlip_set(this.swigCPtr, this, f);
        }

        public float getFrictionSlip() {
            return DynamicsJNI.btRaycastVehicle_btVehicleTuning_frictionSlip_get(this.swigCPtr, this);
        }

        public void setMaxSuspensionForce(float f) {
            DynamicsJNI.btRaycastVehicle_btVehicleTuning_maxSuspensionForce_set(this.swigCPtr, this, f);
        }

        public float getMaxSuspensionForce() {
            return DynamicsJNI.btRaycastVehicle_btVehicleTuning_maxSuspensionForce_get(this.swigCPtr, this);
        }
    }

    public btRaycastVehicle(btVehicleTuning btvehicletuning, btRigidBody btrigidbody, btVehicleRaycaster btvehicleraycaster) {
        this(DynamicsJNI.new_btRaycastVehicle(btVehicleTuning.getCPtr(btvehicletuning), btvehicletuning, btRigidBody.getCPtr(btrigidbody), btrigidbody, btVehicleRaycaster.getCPtr(btvehicleraycaster), btvehicleraycaster), true);
    }

    public Matrix4 getChassisWorldTransform() {
        return DynamicsJNI.btRaycastVehicle_getChassisWorldTransform(this.swigCPtr, this);
    }

    public float rayCast(btWheelInfo btwheelinfo) {
        return DynamicsJNI.btRaycastVehicle_rayCast(this.swigCPtr, this, btWheelInfo.getCPtr(btwheelinfo), btwheelinfo);
    }

    public void updateVehicle(float f) {
        DynamicsJNI.btRaycastVehicle_updateVehicle(this.swigCPtr, this, f);
    }

    public void resetSuspension() {
        DynamicsJNI.btRaycastVehicle_resetSuspension(this.swigCPtr, this);
    }

    public float getSteeringValue(int i) {
        return DynamicsJNI.btRaycastVehicle_getSteeringValue(this.swigCPtr, this, i);
    }

    public void setSteeringValue(float f, int i) {
        DynamicsJNI.btRaycastVehicle_setSteeringValue(this.swigCPtr, this, f, i);
    }

    public void applyEngineForce(float f, int i) {
        DynamicsJNI.btRaycastVehicle_applyEngineForce(this.swigCPtr, this, f, i);
    }

    public Matrix4 getWheelTransformWS(int i) {
        return DynamicsJNI.btRaycastVehicle_getWheelTransformWS(this.swigCPtr, this, i);
    }

    public void updateWheelTransform(int i, boolean z) {
        DynamicsJNI.btRaycastVehicle_updateWheelTransform__SWIG_0(this.swigCPtr, this, i, z);
    }

    public void updateWheelTransform(int i) {
        DynamicsJNI.btRaycastVehicle_updateWheelTransform__SWIG_1(this.swigCPtr, this, i);
    }

    public btWheelInfo addWheel(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, btVehicleTuning btvehicletuning, boolean z) {
        return new btWheelInfo(DynamicsJNI.btRaycastVehicle_addWheel(this.swigCPtr, this, vector3, vector4, vector5, f, f2, btVehicleTuning.getCPtr(btvehicletuning), btvehicletuning, z), false);
    }

    public int getNumWheels() {
        return DynamicsJNI.btRaycastVehicle_getNumWheels(this.swigCPtr, this);
    }

    public void setWheelInfo(SWIGTYPE_p_btAlignedObjectArrayT_btWheelInfo_t sWIGTYPE_p_btAlignedObjectArrayT_btWheelInfo_t) {
        DynamicsJNI.btRaycastVehicle_wheelInfo_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btWheelInfo_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btWheelInfo_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btWheelInfo_t getWheelInfo() {
        long jBtRaycastVehicle_wheelInfo_get = DynamicsJNI.btRaycastVehicle_wheelInfo_get(this.swigCPtr, this);
        if (jBtRaycastVehicle_wheelInfo_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btWheelInfo_t(jBtRaycastVehicle_wheelInfo_get, false);
    }

    public btWheelInfo getWheelInfo(int i) {
        return new btWheelInfo(DynamicsJNI.btRaycastVehicle_getWheelInfo(this.swigCPtr, this, i), false);
    }

    public btWheelInfo getWheelInfoConst(int i) {
        return new btWheelInfo(DynamicsJNI.btRaycastVehicle_getWheelInfoConst(this.swigCPtr, this, i), false);
    }

    public void updateWheelTransformsWS(btWheelInfo btwheelinfo, boolean z) {
        DynamicsJNI.btRaycastVehicle_updateWheelTransformsWS__SWIG_0(this.swigCPtr, this, btWheelInfo.getCPtr(btwheelinfo), btwheelinfo, z);
    }

    public void updateWheelTransformsWS(btWheelInfo btwheelinfo) {
        DynamicsJNI.btRaycastVehicle_updateWheelTransformsWS__SWIG_1(this.swigCPtr, this, btWheelInfo.getCPtr(btwheelinfo), btwheelinfo);
    }

    public void setBrake(float f, int i) {
        DynamicsJNI.btRaycastVehicle_setBrake(this.swigCPtr, this, f, i);
    }

    public void setPitchControl(float f) {
        DynamicsJNI.btRaycastVehicle_setPitchControl(this.swigCPtr, this, f);
    }

    public void updateSuspension(float f) {
        DynamicsJNI.btRaycastVehicle_updateSuspension(this.swigCPtr, this, f);
    }

    public void updateFriction(float f) {
        DynamicsJNI.btRaycastVehicle_updateFriction(this.swigCPtr, this, f);
    }

    public btRigidBody getRigidBody() {
        return btRigidBody.getInstance(DynamicsJNI.btRaycastVehicle_getRigidBody(this.swigCPtr, this), false);
    }

    public btRigidBody getRigidBodyConst() {
        return btRigidBody.getInstance(DynamicsJNI.btRaycastVehicle_getRigidBodyConst(this.swigCPtr, this), false);
    }

    public int getRightAxis() {
        return DynamicsJNI.btRaycastVehicle_getRightAxis(this.swigCPtr, this);
    }

    public int getUpAxis() {
        return DynamicsJNI.btRaycastVehicle_getUpAxis(this.swigCPtr, this);
    }

    public int getForwardAxis() {
        return DynamicsJNI.btRaycastVehicle_getForwardAxis(this.swigCPtr, this);
    }

    public Vector3 getForwardVector() {
        return DynamicsJNI.btRaycastVehicle_getForwardVector(this.swigCPtr, this);
    }

    public float getCurrentSpeedKmHour() {
        return DynamicsJNI.btRaycastVehicle_getCurrentSpeedKmHour(this.swigCPtr, this);
    }

    public void setCoordinateSystem(int i, int i2, int i3) {
        DynamicsJNI.btRaycastVehicle_setCoordinateSystem(this.swigCPtr, this, i, i2, i3);
    }

    public int getUserConstraintType() {
        return DynamicsJNI.btRaycastVehicle_getUserConstraintType(this.swigCPtr, this);
    }

    public void setUserConstraintType(int i) {
        DynamicsJNI.btRaycastVehicle_setUserConstraintType(this.swigCPtr, this, i);
    }

    public void setUserConstraintId(int i) {
        DynamicsJNI.btRaycastVehicle_setUserConstraintId(this.swigCPtr, this, i);
    }

    public int getUserConstraintId() {
        return DynamicsJNI.btRaycastVehicle_getUserConstraintId(this.swigCPtr, this);
    }
}
