package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionObjectDoubleData extends BulletBase {
    private long swigCPtr;

    protected btCollisionObjectDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionObjectDoubleData(long j, boolean z) {
        this("btCollisionObjectDoubleData", j, z);
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

    public static long getCPtr(btCollisionObjectDoubleData btcollisionobjectdoubledata) {
        if (btcollisionobjectdoubledata == null) {
            return 0L;
        }
        return btcollisionobjectdoubledata.swigCPtr;
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
                CollisionJNI.delete_btCollisionObjectDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBroadphaseHandle(long j) {
        CollisionJNI.btCollisionObjectDoubleData_broadphaseHandle_set(this.swigCPtr, this, j);
    }

    public long getBroadphaseHandle() {
        return CollisionJNI.btCollisionObjectDoubleData_broadphaseHandle_get(this.swigCPtr, this);
    }

    public void setCollisionShape(long j) {
        CollisionJNI.btCollisionObjectDoubleData_collisionShape_set(this.swigCPtr, this, j);
    }

    public long getCollisionShape() {
        return CollisionJNI.btCollisionObjectDoubleData_collisionShape_get(this.swigCPtr, this);
    }

    public void setRootCollisionShape(btCollisionShapeData btcollisionshapedata) {
        CollisionJNI.btCollisionObjectDoubleData_rootCollisionShape_set(this.swigCPtr, this, btCollisionShapeData.getCPtr(btcollisionshapedata), btcollisionshapedata);
    }

    public btCollisionShapeData getRootCollisionShape() {
        long jBtCollisionObjectDoubleData_rootCollisionShape_get = CollisionJNI.btCollisionObjectDoubleData_rootCollisionShape_get(this.swigCPtr, this);
        if (jBtCollisionObjectDoubleData_rootCollisionShape_get == 0) {
            return null;
        }
        return new btCollisionShapeData(jBtCollisionObjectDoubleData_rootCollisionShape_get, false);
    }

    public void setName(String str) {
        CollisionJNI.btCollisionObjectDoubleData_name_set(this.swigCPtr, this, str);
    }

    public String getName() {
        return CollisionJNI.btCollisionObjectDoubleData_name_get(this.swigCPtr, this);
    }

    public void setWorldTransform(btTransformDoubleData bttransformdoubledata) {
        CollisionJNI.btCollisionObjectDoubleData_worldTransform_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getWorldTransform() {
        long jBtCollisionObjectDoubleData_worldTransform_get = CollisionJNI.btCollisionObjectDoubleData_worldTransform_get(this.swigCPtr, this);
        if (jBtCollisionObjectDoubleData_worldTransform_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtCollisionObjectDoubleData_worldTransform_get, false);
    }

    public void setInterpolationWorldTransform(btTransformDoubleData bttransformdoubledata) {
        CollisionJNI.btCollisionObjectDoubleData_interpolationWorldTransform_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getInterpolationWorldTransform() {
        long jBtCollisionObjectDoubleData_interpolationWorldTransform_get = CollisionJNI.btCollisionObjectDoubleData_interpolationWorldTransform_get(this.swigCPtr, this);
        if (jBtCollisionObjectDoubleData_interpolationWorldTransform_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtCollisionObjectDoubleData_interpolationWorldTransform_get, false);
    }

    public void setInterpolationLinearVelocity(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btCollisionObjectDoubleData_interpolationLinearVelocity_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getInterpolationLinearVelocity() {
        long jBtCollisionObjectDoubleData_interpolationLinearVelocity_get = CollisionJNI.btCollisionObjectDoubleData_interpolationLinearVelocity_get(this.swigCPtr, this);
        if (jBtCollisionObjectDoubleData_interpolationLinearVelocity_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtCollisionObjectDoubleData_interpolationLinearVelocity_get, false);
    }

    public void setInterpolationAngularVelocity(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btCollisionObjectDoubleData_interpolationAngularVelocity_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getInterpolationAngularVelocity() {
        long jBtCollisionObjectDoubleData_interpolationAngularVelocity_get = CollisionJNI.btCollisionObjectDoubleData_interpolationAngularVelocity_get(this.swigCPtr, this);
        if (jBtCollisionObjectDoubleData_interpolationAngularVelocity_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtCollisionObjectDoubleData_interpolationAngularVelocity_get, false);
    }

    public void setAnisotropicFriction(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btCollisionObjectDoubleData_anisotropicFriction_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getAnisotropicFriction() {
        long jBtCollisionObjectDoubleData_anisotropicFriction_get = CollisionJNI.btCollisionObjectDoubleData_anisotropicFriction_get(this.swigCPtr, this);
        if (jBtCollisionObjectDoubleData_anisotropicFriction_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtCollisionObjectDoubleData_anisotropicFriction_get, false);
    }

    public void setContactProcessingThreshold(double d) {
        CollisionJNI.btCollisionObjectDoubleData_contactProcessingThreshold_set(this.swigCPtr, this, d);
    }

    public double getContactProcessingThreshold() {
        return CollisionJNI.btCollisionObjectDoubleData_contactProcessingThreshold_get(this.swigCPtr, this);
    }

    public void setDeactivationTime(double d) {
        CollisionJNI.btCollisionObjectDoubleData_deactivationTime_set(this.swigCPtr, this, d);
    }

    public double getDeactivationTime() {
        return CollisionJNI.btCollisionObjectDoubleData_deactivationTime_get(this.swigCPtr, this);
    }

    public void setFriction(double d) {
        CollisionJNI.btCollisionObjectDoubleData_friction_set(this.swigCPtr, this, d);
    }

    public double getFriction() {
        return CollisionJNI.btCollisionObjectDoubleData_friction_get(this.swigCPtr, this);
    }

    public void setRollingFriction(double d) {
        CollisionJNI.btCollisionObjectDoubleData_rollingFriction_set(this.swigCPtr, this, d);
    }

    public double getRollingFriction() {
        return CollisionJNI.btCollisionObjectDoubleData_rollingFriction_get(this.swigCPtr, this);
    }

    public void setContactDamping(double d) {
        CollisionJNI.btCollisionObjectDoubleData_contactDamping_set(this.swigCPtr, this, d);
    }

    public double getContactDamping() {
        return CollisionJNI.btCollisionObjectDoubleData_contactDamping_get(this.swigCPtr, this);
    }

    public void setContactStiffness(double d) {
        CollisionJNI.btCollisionObjectDoubleData_contactStiffness_set(this.swigCPtr, this, d);
    }

    public double getContactStiffness() {
        return CollisionJNI.btCollisionObjectDoubleData_contactStiffness_get(this.swigCPtr, this);
    }

    public void setRestitution(double d) {
        CollisionJNI.btCollisionObjectDoubleData_restitution_set(this.swigCPtr, this, d);
    }

    public double getRestitution() {
        return CollisionJNI.btCollisionObjectDoubleData_restitution_get(this.swigCPtr, this);
    }

    public void setHitFraction(double d) {
        CollisionJNI.btCollisionObjectDoubleData_hitFraction_set(this.swigCPtr, this, d);
    }

    public double getHitFraction() {
        return CollisionJNI.btCollisionObjectDoubleData_hitFraction_get(this.swigCPtr, this);
    }

    public void setCcdSweptSphereRadius(double d) {
        CollisionJNI.btCollisionObjectDoubleData_ccdSweptSphereRadius_set(this.swigCPtr, this, d);
    }

    public double getCcdSweptSphereRadius() {
        return CollisionJNI.btCollisionObjectDoubleData_ccdSweptSphereRadius_get(this.swigCPtr, this);
    }

    public void setCcdMotionThreshold(double d) {
        CollisionJNI.btCollisionObjectDoubleData_ccdMotionThreshold_set(this.swigCPtr, this, d);
    }

    public double getCcdMotionThreshold() {
        return CollisionJNI.btCollisionObjectDoubleData_ccdMotionThreshold_get(this.swigCPtr, this);
    }

    public void setHasAnisotropicFriction(int i) {
        CollisionJNI.btCollisionObjectDoubleData_hasAnisotropicFriction_set(this.swigCPtr, this, i);
    }

    public int getHasAnisotropicFriction() {
        return CollisionJNI.btCollisionObjectDoubleData_hasAnisotropicFriction_get(this.swigCPtr, this);
    }

    public void setCollisionFlags(int i) {
        CollisionJNI.btCollisionObjectDoubleData_collisionFlags_set(this.swigCPtr, this, i);
    }

    public int getCollisionFlags() {
        return CollisionJNI.btCollisionObjectDoubleData_collisionFlags_get(this.swigCPtr, this);
    }

    public void setIslandTag1(int i) {
        CollisionJNI.btCollisionObjectDoubleData_islandTag1_set(this.swigCPtr, this, i);
    }

    public int getIslandTag1() {
        return CollisionJNI.btCollisionObjectDoubleData_islandTag1_get(this.swigCPtr, this);
    }

    public void setCompanionId(int i) {
        CollisionJNI.btCollisionObjectDoubleData_companionId_set(this.swigCPtr, this, i);
    }

    public int getCompanionId() {
        return CollisionJNI.btCollisionObjectDoubleData_companionId_get(this.swigCPtr, this);
    }

    public void setActivationState1(int i) {
        CollisionJNI.btCollisionObjectDoubleData_activationState1_set(this.swigCPtr, this, i);
    }

    public int getActivationState1() {
        return CollisionJNI.btCollisionObjectDoubleData_activationState1_get(this.swigCPtr, this);
    }

    public void setInternalType(int i) {
        CollisionJNI.btCollisionObjectDoubleData_internalType_set(this.swigCPtr, this, i);
    }

    public int getInternalType() {
        return CollisionJNI.btCollisionObjectDoubleData_internalType_get(this.swigCPtr, this);
    }

    public void setCheckCollideWith(int i) {
        CollisionJNI.btCollisionObjectDoubleData_checkCollideWith_set(this.swigCPtr, this, i);
    }

    public int getCheckCollideWith() {
        return CollisionJNI.btCollisionObjectDoubleData_checkCollideWith_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        CollisionJNI.btCollisionObjectDoubleData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return CollisionJNI.btCollisionObjectDoubleData_padding_get(this.swigCPtr, this);
    }

    public btCollisionObjectDoubleData() {
        this(CollisionJNI.new_btCollisionObjectDoubleData(), true);
    }
}
