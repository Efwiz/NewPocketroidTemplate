package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionObjectFloatData extends BulletBase {
    private long swigCPtr;

    protected btCollisionObjectFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionObjectFloatData(long j, boolean z) {
        this(CollisionConstants.btCollisionObjectDataName, j, z);
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

    public static long getCPtr(btCollisionObjectFloatData btcollisionobjectfloatdata) {
        if (btcollisionobjectfloatdata == null) {
            return 0L;
        }
        return btcollisionobjectfloatdata.swigCPtr;
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
                CollisionJNI.delete_btCollisionObjectFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBroadphaseHandle(long j) {
        CollisionJNI.btCollisionObjectFloatData_broadphaseHandle_set(this.swigCPtr, this, j);
    }

    public long getBroadphaseHandle() {
        return CollisionJNI.btCollisionObjectFloatData_broadphaseHandle_get(this.swigCPtr, this);
    }

    public void setCollisionShape(long j) {
        CollisionJNI.btCollisionObjectFloatData_collisionShape_set(this.swigCPtr, this, j);
    }

    public long getCollisionShape() {
        return CollisionJNI.btCollisionObjectFloatData_collisionShape_get(this.swigCPtr, this);
    }

    public void setRootCollisionShape(btCollisionShapeData btcollisionshapedata) {
        CollisionJNI.btCollisionObjectFloatData_rootCollisionShape_set(this.swigCPtr, this, btCollisionShapeData.getCPtr(btcollisionshapedata), btcollisionshapedata);
    }

    public btCollisionShapeData getRootCollisionShape() {
        long jBtCollisionObjectFloatData_rootCollisionShape_get = CollisionJNI.btCollisionObjectFloatData_rootCollisionShape_get(this.swigCPtr, this);
        if (jBtCollisionObjectFloatData_rootCollisionShape_get == 0) {
            return null;
        }
        return new btCollisionShapeData(jBtCollisionObjectFloatData_rootCollisionShape_get, false);
    }

    public void setName(String str) {
        CollisionJNI.btCollisionObjectFloatData_name_set(this.swigCPtr, this, str);
    }

    public String getName() {
        return CollisionJNI.btCollisionObjectFloatData_name_get(this.swigCPtr, this);
    }

    public void setWorldTransform(btTransformFloatData bttransformfloatdata) {
        CollisionJNI.btCollisionObjectFloatData_worldTransform_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getWorldTransform() {
        long jBtCollisionObjectFloatData_worldTransform_get = CollisionJNI.btCollisionObjectFloatData_worldTransform_get(this.swigCPtr, this);
        if (jBtCollisionObjectFloatData_worldTransform_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtCollisionObjectFloatData_worldTransform_get, false);
    }

    public void setInterpolationWorldTransform(btTransformFloatData bttransformfloatdata) {
        CollisionJNI.btCollisionObjectFloatData_interpolationWorldTransform_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getInterpolationWorldTransform() {
        long jBtCollisionObjectFloatData_interpolationWorldTransform_get = CollisionJNI.btCollisionObjectFloatData_interpolationWorldTransform_get(this.swigCPtr, this);
        if (jBtCollisionObjectFloatData_interpolationWorldTransform_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtCollisionObjectFloatData_interpolationWorldTransform_get, false);
    }

    public void setInterpolationLinearVelocity(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btCollisionObjectFloatData_interpolationLinearVelocity_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getInterpolationLinearVelocity() {
        long jBtCollisionObjectFloatData_interpolationLinearVelocity_get = CollisionJNI.btCollisionObjectFloatData_interpolationLinearVelocity_get(this.swigCPtr, this);
        if (jBtCollisionObjectFloatData_interpolationLinearVelocity_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtCollisionObjectFloatData_interpolationLinearVelocity_get, false);
    }

    public void setInterpolationAngularVelocity(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btCollisionObjectFloatData_interpolationAngularVelocity_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getInterpolationAngularVelocity() {
        long jBtCollisionObjectFloatData_interpolationAngularVelocity_get = CollisionJNI.btCollisionObjectFloatData_interpolationAngularVelocity_get(this.swigCPtr, this);
        if (jBtCollisionObjectFloatData_interpolationAngularVelocity_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtCollisionObjectFloatData_interpolationAngularVelocity_get, false);
    }

    public void setAnisotropicFriction(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btCollisionObjectFloatData_anisotropicFriction_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getAnisotropicFriction() {
        long jBtCollisionObjectFloatData_anisotropicFriction_get = CollisionJNI.btCollisionObjectFloatData_anisotropicFriction_get(this.swigCPtr, this);
        if (jBtCollisionObjectFloatData_anisotropicFriction_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtCollisionObjectFloatData_anisotropicFriction_get, false);
    }

    public void setContactProcessingThreshold(float f) {
        CollisionJNI.btCollisionObjectFloatData_contactProcessingThreshold_set(this.swigCPtr, this, f);
    }

    public float getContactProcessingThreshold() {
        return CollisionJNI.btCollisionObjectFloatData_contactProcessingThreshold_get(this.swigCPtr, this);
    }

    public void setDeactivationTime(float f) {
        CollisionJNI.btCollisionObjectFloatData_deactivationTime_set(this.swigCPtr, this, f);
    }

    public float getDeactivationTime() {
        return CollisionJNI.btCollisionObjectFloatData_deactivationTime_get(this.swigCPtr, this);
    }

    public void setFriction(float f) {
        CollisionJNI.btCollisionObjectFloatData_friction_set(this.swigCPtr, this, f);
    }

    public float getFriction() {
        return CollisionJNI.btCollisionObjectFloatData_friction_get(this.swigCPtr, this);
    }

    public void setRollingFriction(float f) {
        CollisionJNI.btCollisionObjectFloatData_rollingFriction_set(this.swigCPtr, this, f);
    }

    public float getRollingFriction() {
        return CollisionJNI.btCollisionObjectFloatData_rollingFriction_get(this.swigCPtr, this);
    }

    public void setContactDamping(float f) {
        CollisionJNI.btCollisionObjectFloatData_contactDamping_set(this.swigCPtr, this, f);
    }

    public float getContactDamping() {
        return CollisionJNI.btCollisionObjectFloatData_contactDamping_get(this.swigCPtr, this);
    }

    public void setContactStiffness(float f) {
        CollisionJNI.btCollisionObjectFloatData_contactStiffness_set(this.swigCPtr, this, f);
    }

    public float getContactStiffness() {
        return CollisionJNI.btCollisionObjectFloatData_contactStiffness_get(this.swigCPtr, this);
    }

    public void setRestitution(float f) {
        CollisionJNI.btCollisionObjectFloatData_restitution_set(this.swigCPtr, this, f);
    }

    public float getRestitution() {
        return CollisionJNI.btCollisionObjectFloatData_restitution_get(this.swigCPtr, this);
    }

    public void setHitFraction(float f) {
        CollisionJNI.btCollisionObjectFloatData_hitFraction_set(this.swigCPtr, this, f);
    }

    public float getHitFraction() {
        return CollisionJNI.btCollisionObjectFloatData_hitFraction_get(this.swigCPtr, this);
    }

    public void setCcdSweptSphereRadius(float f) {
        CollisionJNI.btCollisionObjectFloatData_ccdSweptSphereRadius_set(this.swigCPtr, this, f);
    }

    public float getCcdSweptSphereRadius() {
        return CollisionJNI.btCollisionObjectFloatData_ccdSweptSphereRadius_get(this.swigCPtr, this);
    }

    public void setCcdMotionThreshold(float f) {
        CollisionJNI.btCollisionObjectFloatData_ccdMotionThreshold_set(this.swigCPtr, this, f);
    }

    public float getCcdMotionThreshold() {
        return CollisionJNI.btCollisionObjectFloatData_ccdMotionThreshold_get(this.swigCPtr, this);
    }

    public void setHasAnisotropicFriction(int i) {
        CollisionJNI.btCollisionObjectFloatData_hasAnisotropicFriction_set(this.swigCPtr, this, i);
    }

    public int getHasAnisotropicFriction() {
        return CollisionJNI.btCollisionObjectFloatData_hasAnisotropicFriction_get(this.swigCPtr, this);
    }

    public void setCollisionFlags(int i) {
        CollisionJNI.btCollisionObjectFloatData_collisionFlags_set(this.swigCPtr, this, i);
    }

    public int getCollisionFlags() {
        return CollisionJNI.btCollisionObjectFloatData_collisionFlags_get(this.swigCPtr, this);
    }

    public void setIslandTag1(int i) {
        CollisionJNI.btCollisionObjectFloatData_islandTag1_set(this.swigCPtr, this, i);
    }

    public int getIslandTag1() {
        return CollisionJNI.btCollisionObjectFloatData_islandTag1_get(this.swigCPtr, this);
    }

    public void setCompanionId(int i) {
        CollisionJNI.btCollisionObjectFloatData_companionId_set(this.swigCPtr, this, i);
    }

    public int getCompanionId() {
        return CollisionJNI.btCollisionObjectFloatData_companionId_get(this.swigCPtr, this);
    }

    public void setActivationState1(int i) {
        CollisionJNI.btCollisionObjectFloatData_activationState1_set(this.swigCPtr, this, i);
    }

    public int getActivationState1() {
        return CollisionJNI.btCollisionObjectFloatData_activationState1_get(this.swigCPtr, this);
    }

    public void setInternalType(int i) {
        CollisionJNI.btCollisionObjectFloatData_internalType_set(this.swigCPtr, this, i);
    }

    public int getInternalType() {
        return CollisionJNI.btCollisionObjectFloatData_internalType_get(this.swigCPtr, this);
    }

    public void setCheckCollideWith(int i) {
        CollisionJNI.btCollisionObjectFloatData_checkCollideWith_set(this.swigCPtr, this, i);
    }

    public int getCheckCollideWith() {
        return CollisionJNI.btCollisionObjectFloatData_checkCollideWith_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btCollisionObjectFloatData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btCollisionObjectFloatData_padding_get(this.swigCPtr, this);
    }

    public btCollisionObjectFloatData() {
        this(CollisionJNI.new_btCollisionObjectFloatData(), true);
    }
}
