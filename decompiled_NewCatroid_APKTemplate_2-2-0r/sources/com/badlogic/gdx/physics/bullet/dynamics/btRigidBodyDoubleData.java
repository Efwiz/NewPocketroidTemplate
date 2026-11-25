package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3DoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btRigidBodyDoubleData extends BulletBase {
    private long swigCPtr;

    protected btRigidBodyDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btRigidBodyDoubleData(long j, boolean z) {
        this("btRigidBodyDoubleData", j, z);
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

    public static long getCPtr(btRigidBodyDoubleData btrigidbodydoubledata) {
        if (btrigidbodydoubledata == null) {
            return 0L;
        }
        return btrigidbodydoubledata.swigCPtr;
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
                DynamicsJNI.delete_btRigidBodyDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCollisionObjectData(btCollisionObjectDoubleData btcollisionobjectdoubledata) {
        DynamicsJNI.btRigidBodyDoubleData_collisionObjectData_set(this.swigCPtr, this, btCollisionObjectDoubleData.getCPtr(btcollisionobjectdoubledata), btcollisionobjectdoubledata);
    }

    public btCollisionObjectDoubleData getCollisionObjectData() {
        long jBtRigidBodyDoubleData_collisionObjectData_get = DynamicsJNI.btRigidBodyDoubleData_collisionObjectData_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_collisionObjectData_get == 0) {
            return null;
        }
        return new btCollisionObjectDoubleData(jBtRigidBodyDoubleData_collisionObjectData_get, false);
    }

    public void setInvInertiaTensorWorld(btMatrix3x3DoubleData btmatrix3x3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_invInertiaTensorWorld_set(this.swigCPtr, this, btMatrix3x3DoubleData.getCPtr(btmatrix3x3doubledata), btmatrix3x3doubledata);
    }

    public btMatrix3x3DoubleData getInvInertiaTensorWorld() {
        long jBtRigidBodyDoubleData_invInertiaTensorWorld_get = DynamicsJNI.btRigidBodyDoubleData_invInertiaTensorWorld_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_invInertiaTensorWorld_get == 0) {
            return null;
        }
        return new btMatrix3x3DoubleData(jBtRigidBodyDoubleData_invInertiaTensorWorld_get, false);
    }

    public void setLinearVelocity(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_linearVelocity_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearVelocity() {
        long jBtRigidBodyDoubleData_linearVelocity_get = DynamicsJNI.btRigidBodyDoubleData_linearVelocity_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_linearVelocity_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtRigidBodyDoubleData_linearVelocity_get, false);
    }

    public void setAngularVelocity(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_angularVelocity_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularVelocity() {
        long jBtRigidBodyDoubleData_angularVelocity_get = DynamicsJNI.btRigidBodyDoubleData_angularVelocity_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_angularVelocity_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtRigidBodyDoubleData_angularVelocity_get, false);
    }

    public void setAngularFactor(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_angularFactor_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAngularFactor() {
        long jBtRigidBodyDoubleData_angularFactor_get = DynamicsJNI.btRigidBodyDoubleData_angularFactor_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_angularFactor_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtRigidBodyDoubleData_angularFactor_get, false);
    }

    public void setLinearFactor(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_linearFactor_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinearFactor() {
        long jBtRigidBodyDoubleData_linearFactor_get = DynamicsJNI.btRigidBodyDoubleData_linearFactor_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_linearFactor_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtRigidBodyDoubleData_linearFactor_get, false);
    }

    public void setGravity(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_gravity_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getGravity() {
        long jBtRigidBodyDoubleData_gravity_get = DynamicsJNI.btRigidBodyDoubleData_gravity_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_gravity_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtRigidBodyDoubleData_gravity_get, false);
    }

    public void setGravity_acceleration(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_gravity_acceleration_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getGravity_acceleration() {
        long jBtRigidBodyDoubleData_gravity_acceleration_get = DynamicsJNI.btRigidBodyDoubleData_gravity_acceleration_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_gravity_acceleration_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtRigidBodyDoubleData_gravity_acceleration_get, false);
    }

    public void setInvInertiaLocal(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_invInertiaLocal_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getInvInertiaLocal() {
        long jBtRigidBodyDoubleData_invInertiaLocal_get = DynamicsJNI.btRigidBodyDoubleData_invInertiaLocal_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_invInertiaLocal_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtRigidBodyDoubleData_invInertiaLocal_get, false);
    }

    public void setTotalForce(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_totalForce_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getTotalForce() {
        long jBtRigidBodyDoubleData_totalForce_get = DynamicsJNI.btRigidBodyDoubleData_totalForce_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_totalForce_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtRigidBodyDoubleData_totalForce_get, false);
    }

    public void setTotalTorque(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btRigidBodyDoubleData_totalTorque_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getTotalTorque() {
        long jBtRigidBodyDoubleData_totalTorque_get = DynamicsJNI.btRigidBodyDoubleData_totalTorque_get(this.swigCPtr, this);
        if (jBtRigidBodyDoubleData_totalTorque_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtRigidBodyDoubleData_totalTorque_get, false);
    }

    public void setInverseMass(double d) {
        DynamicsJNI.btRigidBodyDoubleData_inverseMass_set(this.swigCPtr, this, d);
    }

    public double getInverseMass() {
        return DynamicsJNI.btRigidBodyDoubleData_inverseMass_get(this.swigCPtr, this);
    }

    public void setLinearDamping(double d) {
        DynamicsJNI.btRigidBodyDoubleData_linearDamping_set(this.swigCPtr, this, d);
    }

    public double getLinearDamping() {
        return DynamicsJNI.btRigidBodyDoubleData_linearDamping_get(this.swigCPtr, this);
    }

    public void setAngularDamping(double d) {
        DynamicsJNI.btRigidBodyDoubleData_angularDamping_set(this.swigCPtr, this, d);
    }

    public double getAngularDamping() {
        return DynamicsJNI.btRigidBodyDoubleData_angularDamping_get(this.swigCPtr, this);
    }

    public void setAdditionalDampingFactor(double d) {
        DynamicsJNI.btRigidBodyDoubleData_additionalDampingFactor_set(this.swigCPtr, this, d);
    }

    public double getAdditionalDampingFactor() {
        return DynamicsJNI.btRigidBodyDoubleData_additionalDampingFactor_get(this.swigCPtr, this);
    }

    public void setAdditionalLinearDampingThresholdSqr(double d) {
        DynamicsJNI.btRigidBodyDoubleData_additionalLinearDampingThresholdSqr_set(this.swigCPtr, this, d);
    }

    public double getAdditionalLinearDampingThresholdSqr() {
        return DynamicsJNI.btRigidBodyDoubleData_additionalLinearDampingThresholdSqr_get(this.swigCPtr, this);
    }

    public void setAdditionalAngularDampingThresholdSqr(double d) {
        DynamicsJNI.btRigidBodyDoubleData_additionalAngularDampingThresholdSqr_set(this.swigCPtr, this, d);
    }

    public double getAdditionalAngularDampingThresholdSqr() {
        return DynamicsJNI.btRigidBodyDoubleData_additionalAngularDampingThresholdSqr_get(this.swigCPtr, this);
    }

    public void setAdditionalAngularDampingFactor(double d) {
        DynamicsJNI.btRigidBodyDoubleData_additionalAngularDampingFactor_set(this.swigCPtr, this, d);
    }

    public double getAdditionalAngularDampingFactor() {
        return DynamicsJNI.btRigidBodyDoubleData_additionalAngularDampingFactor_get(this.swigCPtr, this);
    }

    public void setLinearSleepingThreshold(double d) {
        DynamicsJNI.btRigidBodyDoubleData_linearSleepingThreshold_set(this.swigCPtr, this, d);
    }

    public double getLinearSleepingThreshold() {
        return DynamicsJNI.btRigidBodyDoubleData_linearSleepingThreshold_get(this.swigCPtr, this);
    }

    public void setAngularSleepingThreshold(double d) {
        DynamicsJNI.btRigidBodyDoubleData_angularSleepingThreshold_set(this.swigCPtr, this, d);
    }

    public double getAngularSleepingThreshold() {
        return DynamicsJNI.btRigidBodyDoubleData_angularSleepingThreshold_get(this.swigCPtr, this);
    }

    public void setAdditionalDamping(int i) {
        DynamicsJNI.btRigidBodyDoubleData_additionalDamping_set(this.swigCPtr, this, i);
    }

    public int getAdditionalDamping() {
        return DynamicsJNI.btRigidBodyDoubleData_additionalDamping_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        DynamicsJNI.btRigidBodyDoubleData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return DynamicsJNI.btRigidBodyDoubleData_padding_get(this.swigCPtr, this);
    }

    public btRigidBodyDoubleData() {
        this(DynamicsJNI.new_btRigidBodyDoubleData(), true);
    }
}
