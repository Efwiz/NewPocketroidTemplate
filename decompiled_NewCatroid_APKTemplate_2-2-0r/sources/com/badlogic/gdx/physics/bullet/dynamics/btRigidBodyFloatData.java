package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3FloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btRigidBodyFloatData extends BulletBase {
    private long swigCPtr;

    protected btRigidBodyFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btRigidBodyFloatData(long j, boolean z) {
        this(DynamicsConstants.btRigidBodyDataName, j, z);
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

    public static long getCPtr(btRigidBodyFloatData btrigidbodyfloatdata) {
        if (btrigidbodyfloatdata == null) {
            return 0L;
        }
        return btrigidbodyfloatdata.swigCPtr;
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
                DynamicsJNI.delete_btRigidBodyFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCollisionObjectData(btCollisionObjectFloatData btcollisionobjectfloatdata) {
        DynamicsJNI.btRigidBodyFloatData_collisionObjectData_set(this.swigCPtr, this, btCollisionObjectFloatData.getCPtr(btcollisionobjectfloatdata), btcollisionobjectfloatdata);
    }

    public btCollisionObjectFloatData getCollisionObjectData() {
        long jBtRigidBodyFloatData_collisionObjectData_get = DynamicsJNI.btRigidBodyFloatData_collisionObjectData_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_collisionObjectData_get == 0) {
            return null;
        }
        return new btCollisionObjectFloatData(jBtRigidBodyFloatData_collisionObjectData_get, false);
    }

    public void setInvInertiaTensorWorld(btMatrix3x3FloatData btmatrix3x3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_invInertiaTensorWorld_set(this.swigCPtr, this, btMatrix3x3FloatData.getCPtr(btmatrix3x3floatdata), btmatrix3x3floatdata);
    }

    public btMatrix3x3FloatData getInvInertiaTensorWorld() {
        long jBtRigidBodyFloatData_invInertiaTensorWorld_get = DynamicsJNI.btRigidBodyFloatData_invInertiaTensorWorld_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_invInertiaTensorWorld_get == 0) {
            return null;
        }
        return new btMatrix3x3FloatData(jBtRigidBodyFloatData_invInertiaTensorWorld_get, false);
    }

    public void setLinearVelocity(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_linearVelocity_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearVelocity() {
        long jBtRigidBodyFloatData_linearVelocity_get = DynamicsJNI.btRigidBodyFloatData_linearVelocity_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_linearVelocity_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtRigidBodyFloatData_linearVelocity_get, false);
    }

    public void setAngularVelocity(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_angularVelocity_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularVelocity() {
        long jBtRigidBodyFloatData_angularVelocity_get = DynamicsJNI.btRigidBodyFloatData_angularVelocity_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_angularVelocity_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtRigidBodyFloatData_angularVelocity_get, false);
    }

    public void setAngularFactor(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_angularFactor_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAngularFactor() {
        long jBtRigidBodyFloatData_angularFactor_get = DynamicsJNI.btRigidBodyFloatData_angularFactor_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_angularFactor_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtRigidBodyFloatData_angularFactor_get, false);
    }

    public void setLinearFactor(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_linearFactor_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinearFactor() {
        long jBtRigidBodyFloatData_linearFactor_get = DynamicsJNI.btRigidBodyFloatData_linearFactor_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_linearFactor_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtRigidBodyFloatData_linearFactor_get, false);
    }

    public void setGravity(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_gravity_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getGravity() {
        long jBtRigidBodyFloatData_gravity_get = DynamicsJNI.btRigidBodyFloatData_gravity_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_gravity_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtRigidBodyFloatData_gravity_get, false);
    }

    public void setGravity_acceleration(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_gravity_acceleration_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getGravity_acceleration() {
        long jBtRigidBodyFloatData_gravity_acceleration_get = DynamicsJNI.btRigidBodyFloatData_gravity_acceleration_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_gravity_acceleration_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtRigidBodyFloatData_gravity_acceleration_get, false);
    }

    public void setInvInertiaLocal(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_invInertiaLocal_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getInvInertiaLocal() {
        long jBtRigidBodyFloatData_invInertiaLocal_get = DynamicsJNI.btRigidBodyFloatData_invInertiaLocal_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_invInertiaLocal_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtRigidBodyFloatData_invInertiaLocal_get, false);
    }

    public void setTotalForce(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_totalForce_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getTotalForce() {
        long jBtRigidBodyFloatData_totalForce_get = DynamicsJNI.btRigidBodyFloatData_totalForce_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_totalForce_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtRigidBodyFloatData_totalForce_get, false);
    }

    public void setTotalTorque(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btRigidBodyFloatData_totalTorque_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getTotalTorque() {
        long jBtRigidBodyFloatData_totalTorque_get = DynamicsJNI.btRigidBodyFloatData_totalTorque_get(this.swigCPtr, this);
        if (jBtRigidBodyFloatData_totalTorque_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtRigidBodyFloatData_totalTorque_get, false);
    }

    public void setInverseMass(float f) {
        DynamicsJNI.btRigidBodyFloatData_inverseMass_set(this.swigCPtr, this, f);
    }

    public float getInverseMass() {
        return DynamicsJNI.btRigidBodyFloatData_inverseMass_get(this.swigCPtr, this);
    }

    public void setLinearDamping(float f) {
        DynamicsJNI.btRigidBodyFloatData_linearDamping_set(this.swigCPtr, this, f);
    }

    public float getLinearDamping() {
        return DynamicsJNI.btRigidBodyFloatData_linearDamping_get(this.swigCPtr, this);
    }

    public void setAngularDamping(float f) {
        DynamicsJNI.btRigidBodyFloatData_angularDamping_set(this.swigCPtr, this, f);
    }

    public float getAngularDamping() {
        return DynamicsJNI.btRigidBodyFloatData_angularDamping_get(this.swigCPtr, this);
    }

    public void setAdditionalDampingFactor(float f) {
        DynamicsJNI.btRigidBodyFloatData_additionalDampingFactor_set(this.swigCPtr, this, f);
    }

    public float getAdditionalDampingFactor() {
        return DynamicsJNI.btRigidBodyFloatData_additionalDampingFactor_get(this.swigCPtr, this);
    }

    public void setAdditionalLinearDampingThresholdSqr(float f) {
        DynamicsJNI.btRigidBodyFloatData_additionalLinearDampingThresholdSqr_set(this.swigCPtr, this, f);
    }

    public float getAdditionalLinearDampingThresholdSqr() {
        return DynamicsJNI.btRigidBodyFloatData_additionalLinearDampingThresholdSqr_get(this.swigCPtr, this);
    }

    public void setAdditionalAngularDampingThresholdSqr(float f) {
        DynamicsJNI.btRigidBodyFloatData_additionalAngularDampingThresholdSqr_set(this.swigCPtr, this, f);
    }

    public float getAdditionalAngularDampingThresholdSqr() {
        return DynamicsJNI.btRigidBodyFloatData_additionalAngularDampingThresholdSqr_get(this.swigCPtr, this);
    }

    public void setAdditionalAngularDampingFactor(float f) {
        DynamicsJNI.btRigidBodyFloatData_additionalAngularDampingFactor_set(this.swigCPtr, this, f);
    }

    public float getAdditionalAngularDampingFactor() {
        return DynamicsJNI.btRigidBodyFloatData_additionalAngularDampingFactor_get(this.swigCPtr, this);
    }

    public void setLinearSleepingThreshold(float f) {
        DynamicsJNI.btRigidBodyFloatData_linearSleepingThreshold_set(this.swigCPtr, this, f);
    }

    public float getLinearSleepingThreshold() {
        return DynamicsJNI.btRigidBodyFloatData_linearSleepingThreshold_get(this.swigCPtr, this);
    }

    public void setAngularSleepingThreshold(float f) {
        DynamicsJNI.btRigidBodyFloatData_angularSleepingThreshold_set(this.swigCPtr, this, f);
    }

    public float getAngularSleepingThreshold() {
        return DynamicsJNI.btRigidBodyFloatData_angularSleepingThreshold_get(this.swigCPtr, this);
    }

    public void setAdditionalDamping(int i) {
        DynamicsJNI.btRigidBodyFloatData_additionalDamping_set(this.swigCPtr, this, i);
    }

    public int getAdditionalDamping() {
        return DynamicsJNI.btRigidBodyFloatData_additionalDamping_get(this.swigCPtr, this);
    }

    public btRigidBodyFloatData() {
        this(DynamicsJNI.new_btRigidBodyFloatData(), true);
    }
}
