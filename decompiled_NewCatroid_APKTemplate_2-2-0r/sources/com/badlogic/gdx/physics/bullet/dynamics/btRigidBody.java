package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btRigidBody extends btCollisionObject {
    protected btMotionState motionState;
    private long swigCPtr;

    protected btRigidBody(String str, long j, boolean z) {
        super(str, DynamicsJNI.btRigidBody_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btRigidBody(long j, boolean z) {
        this("btRigidBody", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btRigidBody_SWIGUpcast(j), z);
    }

    public static long getCPtr(btRigidBody btrigidbody) {
        if (btrigidbody == null) {
            return 0L;
        }
        return btrigidbody.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btRigidBody(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static btRigidBody getInstance(long j) {
        return (btRigidBody) btCollisionObject.getInstance(j);
    }

    public static btRigidBody getInstance(long j, boolean z) {
        if (j == 0) {
            return null;
        }
        btRigidBody btrigidbody = getInstance(j);
        return btrigidbody == null ? new btRigidBody(j, z) : btrigidbody;
    }

    public btRigidBody(btRigidBodyConstructionInfo btrigidbodyconstructioninfo) {
        this(false, btrigidbodyconstructioninfo);
        refCollisionShape(btrigidbodyconstructioninfo.getCollisionShape());
        refMotionState(btrigidbodyconstructioninfo.getMotionState());
    }

    public btRigidBody(float f, btMotionState btmotionstate, btCollisionShape btcollisionshape, Vector3 vector3) {
        this(false, f, btmotionstate, btcollisionshape, vector3);
        refCollisionShape(btcollisionshape);
        refMotionState(btmotionstate);
    }

    public btRigidBody(float f, btMotionState btmotionstate, btCollisionShape btcollisionshape) {
        this(false, f, btmotionstate, btcollisionshape);
        refCollisionShape(btcollisionshape);
        refMotionState(btmotionstate);
    }

    public void setMotionState(btMotionState btmotionstate) {
        refMotionState(btmotionstate);
        internalSetMotionState(btmotionstate);
    }

    protected void refMotionState(btMotionState btmotionstate) {
        btMotionState btmotionstate2 = this.motionState;
        if (btmotionstate2 == btmotionstate) {
            return;
        }
        if (btmotionstate2 != null) {
            btmotionstate2.release();
        }
        this.motionState = btmotionstate;
        if (btmotionstate != null) {
            btmotionstate.obtain();
        }
    }

    public btMotionState getMotionState() {
        return this.motionState;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        btMotionState btmotionstate = this.motionState;
        if (btmotionstate != null) {
            btmotionstate.release();
        }
        this.motionState = null;
        super.dispose();
    }

    public static class btRigidBodyConstructionInfo extends BulletBase {
        protected btCollisionShape collisionShape;
        protected btMotionState motionState;
        private long swigCPtr;

        protected btRigidBodyConstructionInfo(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public btRigidBodyConstructionInfo(long j, boolean z) {
            this("btRigidBodyConstructionInfo", j, z);
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

        public static long getCPtr(btRigidBodyConstructionInfo btrigidbodyconstructioninfo) {
            if (btrigidbodyconstructioninfo == null) {
                return 0L;
            }
            return btrigidbodyconstructioninfo.swigCPtr;
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
                    DynamicsJNI.delete_btRigidBody_btRigidBodyConstructionInfo(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setMotionState(btMotionState btmotionstate) {
            refMotionState(btmotionstate);
            setI_motionState(btmotionstate);
        }

        protected void refMotionState(btMotionState btmotionstate) {
            btMotionState btmotionstate2 = this.motionState;
            if (btmotionstate2 == btmotionstate) {
                return;
            }
            if (btmotionstate2 != null) {
                btmotionstate2.release();
            }
            this.motionState = btmotionstate;
            if (btmotionstate != null) {
                btmotionstate.obtain();
            }
        }

        public btMotionState getMotionState() {
            return this.motionState;
        }

        public void setCollisionShape(btCollisionShape btcollisionshape) {
            refCollisionShape(btcollisionshape);
            setI_collisionShape(btcollisionshape);
        }

        protected void refCollisionShape(btCollisionShape btcollisionshape) {
            btCollisionShape btcollisionshape2 = this.collisionShape;
            if (btcollisionshape2 == btcollisionshape) {
                return;
            }
            if (btcollisionshape2 != null) {
                btcollisionshape2.release();
            }
            this.collisionShape = btcollisionshape;
            if (btcollisionshape != null) {
                btcollisionshape.obtain();
            }
        }

        public btCollisionShape getCollisionShape() {
            return this.collisionShape;
        }

        public btRigidBodyConstructionInfo(float f, btMotionState btmotionstate, btCollisionShape btcollisionshape, Vector3 vector3) {
            this(false, f, btmotionstate, btcollisionshape, vector3);
            refMotionState(btmotionstate);
            refCollisionShape(btcollisionshape);
        }

        public btRigidBodyConstructionInfo(float f, btMotionState btmotionstate, btCollisionShape btcollisionshape) {
            this(false, f, btmotionstate, btcollisionshape);
            refMotionState(btmotionstate);
            refCollisionShape(btcollisionshape);
        }

        @Override // com.badlogic.gdx.physics.bullet.BulletBase, com.badlogic.gdx.utils.Disposable
        public void dispose() {
            btMotionState btmotionstate = this.motionState;
            if (btmotionstate != null) {
                btmotionstate.release();
            }
            this.motionState = null;
            btCollisionShape btcollisionshape = this.collisionShape;
            if (btcollisionshape != null) {
                btcollisionshape.release();
            }
            this.collisionShape = null;
            super.dispose();
        }

        public void setMass(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_mass_set(this.swigCPtr, this, f);
        }

        public float getMass() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_mass_get(this.swigCPtr, this);
        }

        private void setI_motionState(btMotionState btmotionstate) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_i_motionState_set(this.swigCPtr, this, btMotionState.getCPtr(btmotionstate), btmotionstate);
        }

        private btMotionState getI_motionState() {
            long jBtRigidBody_btRigidBodyConstructionInfo_i_motionState_get = DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_i_motionState_get(this.swigCPtr, this);
            if (jBtRigidBody_btRigidBodyConstructionInfo_i_motionState_get == 0) {
                return null;
            }
            return new btMotionState(jBtRigidBody_btRigidBodyConstructionInfo_i_motionState_get, false);
        }

        public void setStartWorldTransform(btTransform bttransform) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_startWorldTransform_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
        }

        public btTransform getStartWorldTransform() {
            long jBtRigidBody_btRigidBodyConstructionInfo_startWorldTransform_get = DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_startWorldTransform_get(this.swigCPtr, this);
            if (jBtRigidBody_btRigidBodyConstructionInfo_startWorldTransform_get == 0) {
                return null;
            }
            return new btTransform(jBtRigidBody_btRigidBodyConstructionInfo_startWorldTransform_get, false);
        }

        private void setI_collisionShape(btCollisionShape btcollisionshape) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_i_collisionShape_set(this.swigCPtr, this, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
        }

        private btCollisionShape getI_collisionShape() {
            long jBtRigidBody_btRigidBodyConstructionInfo_i_collisionShape_get = DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_i_collisionShape_get(this.swigCPtr, this);
            if (jBtRigidBody_btRigidBodyConstructionInfo_i_collisionShape_get == 0) {
                return null;
            }
            return btCollisionShape.newDerivedObject(jBtRigidBody_btRigidBodyConstructionInfo_i_collisionShape_get, false);
        }

        public void setLocalInertia(btVector3 btvector3) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_localInertia_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getLocalInertia() {
            long jBtRigidBody_btRigidBodyConstructionInfo_localInertia_get = DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_localInertia_get(this.swigCPtr, this);
            if (jBtRigidBody_btRigidBodyConstructionInfo_localInertia_get == 0) {
                return null;
            }
            return new btVector3(jBtRigidBody_btRigidBodyConstructionInfo_localInertia_get, false);
        }

        public void setLinearDamping(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_linearDamping_set(this.swigCPtr, this, f);
        }

        public float getLinearDamping() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_linearDamping_get(this.swigCPtr, this);
        }

        public void setAngularDamping(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_angularDamping_set(this.swigCPtr, this, f);
        }

        public float getAngularDamping() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_angularDamping_get(this.swigCPtr, this);
        }

        public void setFriction(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_friction_set(this.swigCPtr, this, f);
        }

        public float getFriction() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_friction_get(this.swigCPtr, this);
        }

        public void setRollingFriction(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_rollingFriction_set(this.swigCPtr, this, f);
        }

        public float getRollingFriction() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_rollingFriction_get(this.swigCPtr, this);
        }

        public void setSpinningFriction(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_spinningFriction_set(this.swigCPtr, this, f);
        }

        public float getSpinningFriction() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_spinningFriction_get(this.swigCPtr, this);
        }

        public void setRestitution(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_restitution_set(this.swigCPtr, this, f);
        }

        public float getRestitution() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_restitution_get(this.swigCPtr, this);
        }

        public void setLinearSleepingThreshold(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_linearSleepingThreshold_set(this.swigCPtr, this, f);
        }

        public float getLinearSleepingThreshold() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_linearSleepingThreshold_get(this.swigCPtr, this);
        }

        public void setAngularSleepingThreshold(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_angularSleepingThreshold_set(this.swigCPtr, this, f);
        }

        public float getAngularSleepingThreshold() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_angularSleepingThreshold_get(this.swigCPtr, this);
        }

        public void setAdditionalDamping(boolean z) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalDamping_set(this.swigCPtr, this, z);
        }

        public boolean getAdditionalDamping() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalDamping_get(this.swigCPtr, this);
        }

        public void setAdditionalDampingFactor(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalDampingFactor_set(this.swigCPtr, this, f);
        }

        public float getAdditionalDampingFactor() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalDampingFactor_get(this.swigCPtr, this);
        }

        public void setAdditionalLinearDampingThresholdSqr(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalLinearDampingThresholdSqr_set(this.swigCPtr, this, f);
        }

        public float getAdditionalLinearDampingThresholdSqr() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalLinearDampingThresholdSqr_get(this.swigCPtr, this);
        }

        public void setAdditionalAngularDampingThresholdSqr(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalAngularDampingThresholdSqr_set(this.swigCPtr, this, f);
        }

        public float getAdditionalAngularDampingThresholdSqr() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalAngularDampingThresholdSqr_get(this.swigCPtr, this);
        }

        public void setAdditionalAngularDampingFactor(float f) {
            DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalAngularDampingFactor_set(this.swigCPtr, this, f);
        }

        public float getAdditionalAngularDampingFactor() {
            return DynamicsJNI.btRigidBody_btRigidBodyConstructionInfo_additionalAngularDampingFactor_get(this.swigCPtr, this);
        }

        private btRigidBodyConstructionInfo(boolean z, float f, btMotionState btmotionstate, btCollisionShape btcollisionshape, Vector3 vector3) {
            this(DynamicsJNI.new_btRigidBody_btRigidBodyConstructionInfo__SWIG_0(z, f, btMotionState.getCPtr(btmotionstate), btmotionstate, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, vector3), true);
        }

        private btRigidBodyConstructionInfo(boolean z, float f, btMotionState btmotionstate, btCollisionShape btcollisionshape) {
            this(DynamicsJNI.new_btRigidBody_btRigidBodyConstructionInfo__SWIG_1(z, f, btMotionState.getCPtr(btmotionstate), btmotionstate, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape), true);
        }
    }

    public void proceedToTransform(Matrix4 matrix4) {
        DynamicsJNI.btRigidBody_proceedToTransform(this.swigCPtr, this, matrix4);
    }

    public void predictIntegratedTransform(float f, Matrix4 matrix4) {
        DynamicsJNI.btRigidBody_predictIntegratedTransform(this.swigCPtr, this, f, matrix4);
    }

    public void saveKinematicState(float f) {
        DynamicsJNI.btRigidBody_saveKinematicState(this.swigCPtr, this, f);
    }

    public void applyGravity() {
        DynamicsJNI.btRigidBody_applyGravity(this.swigCPtr, this);
    }

    public void setGravity(Vector3 vector3) {
        DynamicsJNI.btRigidBody_setGravity(this.swigCPtr, this, vector3);
    }

    public Vector3 getGravity() {
        return DynamicsJNI.btRigidBody_getGravity(this.swigCPtr, this);
    }

    public void setDamping(float f, float f2) {
        DynamicsJNI.btRigidBody_setDamping(this.swigCPtr, this, f, f2);
    }

    public float getLinearDamping() {
        return DynamicsJNI.btRigidBody_getLinearDamping(this.swigCPtr, this);
    }

    public float getAngularDamping() {
        return DynamicsJNI.btRigidBody_getAngularDamping(this.swigCPtr, this);
    }

    public float getLinearSleepingThreshold() {
        return DynamicsJNI.btRigidBody_getLinearSleepingThreshold(this.swigCPtr, this);
    }

    public float getAngularSleepingThreshold() {
        return DynamicsJNI.btRigidBody_getAngularSleepingThreshold(this.swigCPtr, this);
    }

    public void applyDamping(float f) {
        DynamicsJNI.btRigidBody_applyDamping(this.swigCPtr, this, f);
    }

    public void setMassProps(float f, Vector3 vector3) {
        DynamicsJNI.btRigidBody_setMassProps(this.swigCPtr, this, f, vector3);
    }

    public Vector3 getLinearFactor() {
        return DynamicsJNI.btRigidBody_getLinearFactor(this.swigCPtr, this);
    }

    public void setLinearFactor(Vector3 vector3) {
        DynamicsJNI.btRigidBody_setLinearFactor(this.swigCPtr, this, vector3);
    }

    public float getInvMass() {
        return DynamicsJNI.btRigidBody_getInvMass(this.swigCPtr, this);
    }

    public Matrix3 getInvInertiaTensorWorld() {
        return DynamicsJNI.btRigidBody_getInvInertiaTensorWorld(this.swigCPtr, this);
    }

    public void integrateVelocities(float f) {
        DynamicsJNI.btRigidBody_integrateVelocities(this.swigCPtr, this, f);
    }

    public void setCenterOfMassTransform(Matrix4 matrix4) {
        DynamicsJNI.btRigidBody_setCenterOfMassTransform(this.swigCPtr, this, matrix4);
    }

    public void applyCentralForce(Vector3 vector3) {
        DynamicsJNI.btRigidBody_applyCentralForce(this.swigCPtr, this, vector3);
    }

    public Vector3 getTotalForce() {
        return DynamicsJNI.btRigidBody_getTotalForce(this.swigCPtr, this);
    }

    public Vector3 getTotalTorque() {
        return DynamicsJNI.btRigidBody_getTotalTorque(this.swigCPtr, this);
    }

    public Vector3 getInvInertiaDiagLocal() {
        return DynamicsJNI.btRigidBody_getInvInertiaDiagLocal(this.swigCPtr, this);
    }

    public void setInvInertiaDiagLocal(Vector3 vector3) {
        DynamicsJNI.btRigidBody_setInvInertiaDiagLocal(this.swigCPtr, this, vector3);
    }

    public void setSleepingThresholds(float f, float f2) {
        DynamicsJNI.btRigidBody_setSleepingThresholds(this.swigCPtr, this, f, f2);
    }

    public void applyTorque(Vector3 vector3) {
        DynamicsJNI.btRigidBody_applyTorque(this.swigCPtr, this, vector3);
    }

    public void applyForce(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btRigidBody_applyForce(this.swigCPtr, this, vector3, vector4);
    }

    public void applyCentralImpulse(Vector3 vector3) {
        DynamicsJNI.btRigidBody_applyCentralImpulse(this.swigCPtr, this, vector3);
    }

    public void applyTorqueImpulse(Vector3 vector3) {
        DynamicsJNI.btRigidBody_applyTorqueImpulse(this.swigCPtr, this, vector3);
    }

    public void applyImpulse(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btRigidBody_applyImpulse(this.swigCPtr, this, vector3, vector4);
    }

    public void clearForces() {
        DynamicsJNI.btRigidBody_clearForces(this.swigCPtr, this);
    }

    public void updateInertiaTensor() {
        DynamicsJNI.btRigidBody_updateInertiaTensor(this.swigCPtr, this);
    }

    public Vector3 getCenterOfMassPosition() {
        return DynamicsJNI.btRigidBody_getCenterOfMassPosition(this.swigCPtr, this);
    }

    public Quaternion getOrientation() {
        return DynamicsJNI.btRigidBody_getOrientation(this.swigCPtr, this);
    }

    public Matrix4 getCenterOfMassTransform() {
        return DynamicsJNI.btRigidBody_getCenterOfMassTransform(this.swigCPtr, this);
    }

    public Vector3 getLinearVelocity() {
        return DynamicsJNI.btRigidBody_getLinearVelocity(this.swigCPtr, this);
    }

    public Vector3 getAngularVelocity() {
        return DynamicsJNI.btRigidBody_getAngularVelocity(this.swigCPtr, this);
    }

    public void setLinearVelocity(Vector3 vector3) {
        DynamicsJNI.btRigidBody_setLinearVelocity(this.swigCPtr, this, vector3);
    }

    public void setAngularVelocity(Vector3 vector3) {
        DynamicsJNI.btRigidBody_setAngularVelocity(this.swigCPtr, this, vector3);
    }

    public Vector3 getVelocityInLocalPoint(Vector3 vector3) {
        return DynamicsJNI.btRigidBody_getVelocityInLocalPoint(this.swigCPtr, this, vector3);
    }

    public void translate(Vector3 vector3) {
        DynamicsJNI.btRigidBody_translate(this.swigCPtr, this, vector3);
    }

    public void getAabb(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btRigidBody_getAabb(this.swigCPtr, this, vector3, vector4);
    }

    public float computeImpulseDenominator(Vector3 vector3, Vector3 vector4) {
        return DynamicsJNI.btRigidBody_computeImpulseDenominator(this.swigCPtr, this, vector3, vector4);
    }

    public float computeAngularImpulseDenominator(Vector3 vector3) {
        return DynamicsJNI.btRigidBody_computeAngularImpulseDenominator(this.swigCPtr, this, vector3);
    }

    public void updateDeactivation(float f) {
        DynamicsJNI.btRigidBody_updateDeactivation(this.swigCPtr, this, f);
    }

    public boolean wantsSleeping() {
        return DynamicsJNI.btRigidBody_wantsSleeping(this.swigCPtr, this);
    }

    public btBroadphaseProxy getBroadphaseProxyConst() {
        return btBroadphaseProxy.internalTemp(DynamicsJNI.btRigidBody_getBroadphaseProxyConst(this.swigCPtr, this), false);
    }

    public btBroadphaseProxy getBroadphaseProxy() {
        return btBroadphaseProxy.internalTemp(DynamicsJNI.btRigidBody_getBroadphaseProxy(this.swigCPtr, this), false);
    }

    public void setNewBroadphaseProxy(btBroadphaseProxy btbroadphaseproxy) {
        DynamicsJNI.btRigidBody_setNewBroadphaseProxy(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    private btMotionState internalGetMotionState() {
        long jBtRigidBody_internalGetMotionState = DynamicsJNI.btRigidBody_internalGetMotionState(this.swigCPtr, this);
        if (jBtRigidBody_internalGetMotionState == 0) {
            return null;
        }
        return new btMotionState(jBtRigidBody_internalGetMotionState, false);
    }

    private btMotionState getMotionStateConst() {
        long jBtRigidBody_getMotionStateConst = DynamicsJNI.btRigidBody_getMotionStateConst(this.swigCPtr, this);
        if (jBtRigidBody_getMotionStateConst == 0) {
            return null;
        }
        return new btMotionState(jBtRigidBody_getMotionStateConst, false);
    }

    private void internalSetMotionState(btMotionState btmotionstate) {
        DynamicsJNI.btRigidBody_internalSetMotionState(this.swigCPtr, this, btMotionState.getCPtr(btmotionstate), btmotionstate);
    }

    public void setContactSolverType(int i) {
        DynamicsJNI.btRigidBody_contactSolverType_set(this.swigCPtr, this, i);
    }

    public int getContactSolverType() {
        return DynamicsJNI.btRigidBody_contactSolverType_get(this.swigCPtr, this);
    }

    public void setFrictionSolverType(int i) {
        DynamicsJNI.btRigidBody_frictionSolverType_set(this.swigCPtr, this, i);
    }

    public int getFrictionSolverType() {
        return DynamicsJNI.btRigidBody_frictionSolverType_get(this.swigCPtr, this);
    }

    public void setAngularFactor(Vector3 vector3) {
        DynamicsJNI.btRigidBody_setAngularFactor__SWIG_0(this.swigCPtr, this, vector3);
    }

    public void setAngularFactor(float f) {
        DynamicsJNI.btRigidBody_setAngularFactor__SWIG_1(this.swigCPtr, this, f);
    }

    public Vector3 getAngularFactor() {
        return DynamicsJNI.btRigidBody_getAngularFactor(this.swigCPtr, this);
    }

    public boolean isInWorld() {
        return DynamicsJNI.btRigidBody_isInWorld(this.swigCPtr, this);
    }

    public void addConstraintRef(btTypedConstraint bttypedconstraint) {
        DynamicsJNI.btRigidBody_addConstraintRef(this.swigCPtr, this, btTypedConstraint.getCPtr(bttypedconstraint), bttypedconstraint);
    }

    public void removeConstraintRef(btTypedConstraint bttypedconstraint) {
        DynamicsJNI.btRigidBody_removeConstraintRef(this.swigCPtr, this, btTypedConstraint.getCPtr(bttypedconstraint), bttypedconstraint);
    }

    public btTypedConstraint getConstraintRef(int i) {
        long jBtRigidBody_getConstraintRef = DynamicsJNI.btRigidBody_getConstraintRef(this.swigCPtr, this, i);
        if (jBtRigidBody_getConstraintRef == 0) {
            return null;
        }
        return new btTypedConstraint(jBtRigidBody_getConstraintRef, false);
    }

    public int getNumConstraintRefs() {
        return DynamicsJNI.btRigidBody_getNumConstraintRefs(this.swigCPtr, this);
    }

    public void setFlags(int i) {
        DynamicsJNI.btRigidBody_setFlags(this.swigCPtr, this, i);
    }

    public int getFlags() {
        return DynamicsJNI.btRigidBody_getFlags(this.swigCPtr, this);
    }

    public Vector3 computeGyroscopicImpulseImplicit_World(float f) {
        return DynamicsJNI.btRigidBody_computeGyroscopicImpulseImplicit_World(this.swigCPtr, this, f);
    }

    public Vector3 computeGyroscopicImpulseImplicit_Body(float f) {
        return DynamicsJNI.btRigidBody_computeGyroscopicImpulseImplicit_Body(this.swigCPtr, this, f);
    }

    public Vector3 computeGyroscopicForceExplicit(float f) {
        return DynamicsJNI.btRigidBody_computeGyroscopicForceExplicit(this.swigCPtr, this, f);
    }

    public Vector3 getLocalInertia() {
        return DynamicsJNI.btRigidBody_getLocalInertia(this.swigCPtr, this);
    }

    private btRigidBody(boolean z, btRigidBodyConstructionInfo btrigidbodyconstructioninfo) {
        this(DynamicsJNI.new_btRigidBody__SWIG_0(z, btRigidBodyConstructionInfo.getCPtr(btrigidbodyconstructioninfo), btrigidbodyconstructioninfo), true);
    }

    private btRigidBody(boolean z, float f, btMotionState btmotionstate, btCollisionShape btcollisionshape, Vector3 vector3) {
        this(DynamicsJNI.new_btRigidBody__SWIG_1(z, f, btMotionState.getCPtr(btmotionstate), btmotionstate, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, vector3), true);
    }

    private btRigidBody(boolean z, float f, btMotionState btmotionstate, btCollisionShape btcollisionshape) {
        this(DynamicsJNI.new_btRigidBody__SWIG_2(z, f, btMotionState.getCPtr(btmotionstate), btmotionstate, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape), true);
    }
}
