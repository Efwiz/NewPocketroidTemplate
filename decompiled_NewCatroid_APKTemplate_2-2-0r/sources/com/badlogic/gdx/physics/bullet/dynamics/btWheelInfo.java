package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btWheelInfo extends BulletBase {
    private long swigCPtr;

    protected btWheelInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btWheelInfo(long j, boolean z) {
        this("btWheelInfo", j, z);
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

    public static long getCPtr(btWheelInfo btwheelinfo) {
        if (btwheelinfo == null) {
            return 0L;
        }
        return btwheelinfo.swigCPtr;
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
                DynamicsJNI.delete_btWheelInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class RaycastInfo extends BulletBase {
        private long swigCPtr;

        protected RaycastInfo(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public RaycastInfo(long j, boolean z) {
            this("RaycastInfo", j, z);
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

        public static long getCPtr(RaycastInfo raycastInfo) {
            if (raycastInfo == null) {
                return 0L;
            }
            return raycastInfo.swigCPtr;
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
                    DynamicsJNI.delete_btWheelInfo_RaycastInfo(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setContactNormalWS(btVector3 btvector3) {
            DynamicsJNI.btWheelInfo_RaycastInfo_contactNormalWS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getContactNormalWS() {
            long jBtWheelInfo_RaycastInfo_contactNormalWS_get = DynamicsJNI.btWheelInfo_RaycastInfo_contactNormalWS_get(this.swigCPtr, this);
            if (jBtWheelInfo_RaycastInfo_contactNormalWS_get == 0) {
                return null;
            }
            return new btVector3(jBtWheelInfo_RaycastInfo_contactNormalWS_get, false);
        }

        public void setContactPointWS(btVector3 btvector3) {
            DynamicsJNI.btWheelInfo_RaycastInfo_contactPointWS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getContactPointWS() {
            long jBtWheelInfo_RaycastInfo_contactPointWS_get = DynamicsJNI.btWheelInfo_RaycastInfo_contactPointWS_get(this.swigCPtr, this);
            if (jBtWheelInfo_RaycastInfo_contactPointWS_get == 0) {
                return null;
            }
            return new btVector3(jBtWheelInfo_RaycastInfo_contactPointWS_get, false);
        }

        public void setSuspensionLength(float f) {
            DynamicsJNI.btWheelInfo_RaycastInfo_suspensionLength_set(this.swigCPtr, this, f);
        }

        public float getSuspensionLength() {
            return DynamicsJNI.btWheelInfo_RaycastInfo_suspensionLength_get(this.swigCPtr, this);
        }

        public void setHardPointWS(btVector3 btvector3) {
            DynamicsJNI.btWheelInfo_RaycastInfo_hardPointWS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getHardPointWS() {
            long jBtWheelInfo_RaycastInfo_hardPointWS_get = DynamicsJNI.btWheelInfo_RaycastInfo_hardPointWS_get(this.swigCPtr, this);
            if (jBtWheelInfo_RaycastInfo_hardPointWS_get == 0) {
                return null;
            }
            return new btVector3(jBtWheelInfo_RaycastInfo_hardPointWS_get, false);
        }

        public void setWheelDirectionWS(btVector3 btvector3) {
            DynamicsJNI.btWheelInfo_RaycastInfo_wheelDirectionWS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getWheelDirectionWS() {
            long jBtWheelInfo_RaycastInfo_wheelDirectionWS_get = DynamicsJNI.btWheelInfo_RaycastInfo_wheelDirectionWS_get(this.swigCPtr, this);
            if (jBtWheelInfo_RaycastInfo_wheelDirectionWS_get == 0) {
                return null;
            }
            return new btVector3(jBtWheelInfo_RaycastInfo_wheelDirectionWS_get, false);
        }

        public void setWheelAxleWS(btVector3 btvector3) {
            DynamicsJNI.btWheelInfo_RaycastInfo_wheelAxleWS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getWheelAxleWS() {
            long jBtWheelInfo_RaycastInfo_wheelAxleWS_get = DynamicsJNI.btWheelInfo_RaycastInfo_wheelAxleWS_get(this.swigCPtr, this);
            if (jBtWheelInfo_RaycastInfo_wheelAxleWS_get == 0) {
                return null;
            }
            return new btVector3(jBtWheelInfo_RaycastInfo_wheelAxleWS_get, false);
        }

        public void setIsInContact(boolean z) {
            DynamicsJNI.btWheelInfo_RaycastInfo_isInContact_set(this.swigCPtr, this, z);
        }

        public boolean getIsInContact() {
            return DynamicsJNI.btWheelInfo_RaycastInfo_isInContact_get(this.swigCPtr, this);
        }

        public void setGroundObject(long j) {
            DynamicsJNI.btWheelInfo_RaycastInfo_groundObject_set(this.swigCPtr, this, j);
        }

        public long getGroundObject() {
            return DynamicsJNI.btWheelInfo_RaycastInfo_groundObject_get(this.swigCPtr, this);
        }

        public RaycastInfo() {
            this(DynamicsJNI.new_btWheelInfo_RaycastInfo(), true);
        }
    }

    public void setRaycastInfo(RaycastInfo raycastInfo) {
        DynamicsJNI.btWheelInfo_raycastInfo_set(this.swigCPtr, this, RaycastInfo.getCPtr(raycastInfo), raycastInfo);
    }

    public RaycastInfo getRaycastInfo() {
        long jBtWheelInfo_raycastInfo_get = DynamicsJNI.btWheelInfo_raycastInfo_get(this.swigCPtr, this);
        if (jBtWheelInfo_raycastInfo_get == 0) {
            return null;
        }
        return new RaycastInfo(jBtWheelInfo_raycastInfo_get, false);
    }

    public void setWorldTransform(btTransform bttransform) {
        DynamicsJNI.btWheelInfo_worldTransform_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getWorldTransform() {
        long jBtWheelInfo_worldTransform_get = DynamicsJNI.btWheelInfo_worldTransform_get(this.swigCPtr, this);
        if (jBtWheelInfo_worldTransform_get == 0) {
            return null;
        }
        return new btTransform(jBtWheelInfo_worldTransform_get, false);
    }

    public void setChassisConnectionPointCS(btVector3 btvector3) {
        DynamicsJNI.btWheelInfo_chassisConnectionPointCS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getChassisConnectionPointCS() {
        long jBtWheelInfo_chassisConnectionPointCS_get = DynamicsJNI.btWheelInfo_chassisConnectionPointCS_get(this.swigCPtr, this);
        if (jBtWheelInfo_chassisConnectionPointCS_get == 0) {
            return null;
        }
        return new btVector3(jBtWheelInfo_chassisConnectionPointCS_get, false);
    }

    public void setWheelDirectionCS(btVector3 btvector3) {
        DynamicsJNI.btWheelInfo_wheelDirectionCS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getWheelDirectionCS() {
        long jBtWheelInfo_wheelDirectionCS_get = DynamicsJNI.btWheelInfo_wheelDirectionCS_get(this.swigCPtr, this);
        if (jBtWheelInfo_wheelDirectionCS_get == 0) {
            return null;
        }
        return new btVector3(jBtWheelInfo_wheelDirectionCS_get, false);
    }

    public void setWheelAxleCS(btVector3 btvector3) {
        DynamicsJNI.btWheelInfo_wheelAxleCS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getWheelAxleCS() {
        long jBtWheelInfo_wheelAxleCS_get = DynamicsJNI.btWheelInfo_wheelAxleCS_get(this.swigCPtr, this);
        if (jBtWheelInfo_wheelAxleCS_get == 0) {
            return null;
        }
        return new btVector3(jBtWheelInfo_wheelAxleCS_get, false);
    }

    public void setSuspensionRestLength1(float f) {
        DynamicsJNI.btWheelInfo_suspensionRestLength1_set(this.swigCPtr, this, f);
    }

    public float getSuspensionRestLength1() {
        return DynamicsJNI.btWheelInfo_suspensionRestLength1_get(this.swigCPtr, this);
    }

    public void setMaxSuspensionTravelCm(float f) {
        DynamicsJNI.btWheelInfo_maxSuspensionTravelCm_set(this.swigCPtr, this, f);
    }

    public float getMaxSuspensionTravelCm() {
        return DynamicsJNI.btWheelInfo_maxSuspensionTravelCm_get(this.swigCPtr, this);
    }

    public float getSuspensionRestLength() {
        return DynamicsJNI.btWheelInfo_getSuspensionRestLength(this.swigCPtr, this);
    }

    public void setWheelsRadius(float f) {
        DynamicsJNI.btWheelInfo_wheelsRadius_set(this.swigCPtr, this, f);
    }

    public float getWheelsRadius() {
        return DynamicsJNI.btWheelInfo_wheelsRadius_get(this.swigCPtr, this);
    }

    public void setSuspensionStiffness(float f) {
        DynamicsJNI.btWheelInfo_suspensionStiffness_set(this.swigCPtr, this, f);
    }

    public float getSuspensionStiffness() {
        return DynamicsJNI.btWheelInfo_suspensionStiffness_get(this.swigCPtr, this);
    }

    public void setWheelsDampingCompression(float f) {
        DynamicsJNI.btWheelInfo_wheelsDampingCompression_set(this.swigCPtr, this, f);
    }

    public float getWheelsDampingCompression() {
        return DynamicsJNI.btWheelInfo_wheelsDampingCompression_get(this.swigCPtr, this);
    }

    public void setWheelsDampingRelaxation(float f) {
        DynamicsJNI.btWheelInfo_wheelsDampingRelaxation_set(this.swigCPtr, this, f);
    }

    public float getWheelsDampingRelaxation() {
        return DynamicsJNI.btWheelInfo_wheelsDampingRelaxation_get(this.swigCPtr, this);
    }

    public void setFrictionSlip(float f) {
        DynamicsJNI.btWheelInfo_frictionSlip_set(this.swigCPtr, this, f);
    }

    public float getFrictionSlip() {
        return DynamicsJNI.btWheelInfo_frictionSlip_get(this.swigCPtr, this);
    }

    public void setSteering(float f) {
        DynamicsJNI.btWheelInfo_steering_set(this.swigCPtr, this, f);
    }

    public float getSteering() {
        return DynamicsJNI.btWheelInfo_steering_get(this.swigCPtr, this);
    }

    public void setRotation(float f) {
        DynamicsJNI.btWheelInfo_rotation_set(this.swigCPtr, this, f);
    }

    public float getRotation() {
        return DynamicsJNI.btWheelInfo_rotation_get(this.swigCPtr, this);
    }

    public void setDeltaRotation(float f) {
        DynamicsJNI.btWheelInfo_deltaRotation_set(this.swigCPtr, this, f);
    }

    public float getDeltaRotation() {
        return DynamicsJNI.btWheelInfo_deltaRotation_get(this.swigCPtr, this);
    }

    public void setRollInfluence(float f) {
        DynamicsJNI.btWheelInfo_rollInfluence_set(this.swigCPtr, this, f);
    }

    public float getRollInfluence() {
        return DynamicsJNI.btWheelInfo_rollInfluence_get(this.swigCPtr, this);
    }

    public void setMaxSuspensionForce(float f) {
        DynamicsJNI.btWheelInfo_maxSuspensionForce_set(this.swigCPtr, this, f);
    }

    public float getMaxSuspensionForce() {
        return DynamicsJNI.btWheelInfo_maxSuspensionForce_get(this.swigCPtr, this);
    }

    public void setEngineForce(float f) {
        DynamicsJNI.btWheelInfo_engineForce_set(this.swigCPtr, this, f);
    }

    public float getEngineForce() {
        return DynamicsJNI.btWheelInfo_engineForce_get(this.swigCPtr, this);
    }

    public void setBrake(float f) {
        DynamicsJNI.btWheelInfo_brake_set(this.swigCPtr, this, f);
    }

    public float getBrake() {
        return DynamicsJNI.btWheelInfo_brake_get(this.swigCPtr, this);
    }

    public void setBIsFrontWheel(boolean z) {
        DynamicsJNI.btWheelInfo_bIsFrontWheel_set(this.swigCPtr, this, z);
    }

    public boolean getBIsFrontWheel() {
        return DynamicsJNI.btWheelInfo_bIsFrontWheel_get(this.swigCPtr, this);
    }

    public void setClientInfo(long j) {
        DynamicsJNI.btWheelInfo_clientInfo_set(this.swigCPtr, this, j);
    }

    public long getClientInfo() {
        return DynamicsJNI.btWheelInfo_clientInfo_get(this.swigCPtr, this);
    }

    public btWheelInfo() {
        this(DynamicsJNI.new_btWheelInfo__SWIG_0(), true);
    }

    public btWheelInfo(btWheelInfoConstructionInfo btwheelinfoconstructioninfo) {
        this(DynamicsJNI.new_btWheelInfo__SWIG_1(btWheelInfoConstructionInfo.getCPtr(btwheelinfoconstructioninfo), btwheelinfoconstructioninfo), true);
    }

    public void updateWheel(btRigidBody btrigidbody, RaycastInfo raycastInfo) {
        DynamicsJNI.btWheelInfo_updateWheel(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, RaycastInfo.getCPtr(raycastInfo), raycastInfo);
    }

    public void setClippedInvContactDotSuspension(float f) {
        DynamicsJNI.btWheelInfo_clippedInvContactDotSuspension_set(this.swigCPtr, this, f);
    }

    public float getClippedInvContactDotSuspension() {
        return DynamicsJNI.btWheelInfo_clippedInvContactDotSuspension_get(this.swigCPtr, this);
    }

    public void setSuspensionRelativeVelocity(float f) {
        DynamicsJNI.btWheelInfo_suspensionRelativeVelocity_set(this.swigCPtr, this, f);
    }

    public float getSuspensionRelativeVelocity() {
        return DynamicsJNI.btWheelInfo_suspensionRelativeVelocity_get(this.swigCPtr, this);
    }

    public void setWheelsSuspensionForce(float f) {
        DynamicsJNI.btWheelInfo_wheelsSuspensionForce_set(this.swigCPtr, this, f);
    }

    public float getWheelsSuspensionForce() {
        return DynamicsJNI.btWheelInfo_wheelsSuspensionForce_get(this.swigCPtr, this);
    }

    public void setSkidInfo(float f) {
        DynamicsJNI.btWheelInfo_skidInfo_set(this.swigCPtr, this, f);
    }

    public float getSkidInfo() {
        return DynamicsJNI.btWheelInfo_skidInfo_get(this.swigCPtr, this);
    }
}
