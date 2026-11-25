package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btQuaternionFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyLinkFloatData extends BulletBase {
    private long swigCPtr;

    protected btMultiBodyLinkFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiBodyLinkFloatData(long j, boolean z) {
        this(DynamicsConstants.btMultiBodyLinkDataName, j, z);
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

    public static long getCPtr(btMultiBodyLinkFloatData btmultibodylinkfloatdata) {
        if (btmultibodylinkfloatdata == null) {
            return 0L;
        }
        return btmultibodylinkfloatdata.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodyLinkFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setZeroRotParentToThis(btQuaternionFloatData btquaternionfloatdata) {
        DynamicsJNI.btMultiBodyLinkFloatData_zeroRotParentToThis_set(this.swigCPtr, this, btQuaternionFloatData.getCPtr(btquaternionfloatdata), btquaternionfloatdata);
    }

    public btQuaternionFloatData getZeroRotParentToThis() {
        long jBtMultiBodyLinkFloatData_zeroRotParentToThis_get = DynamicsJNI.btMultiBodyLinkFloatData_zeroRotParentToThis_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkFloatData_zeroRotParentToThis_get == 0) {
            return null;
        }
        return new btQuaternionFloatData(jBtMultiBodyLinkFloatData_zeroRotParentToThis_get, false);
    }

    public void setParentComToThisComOffset(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btMultiBodyLinkFloatData_parentComToThisComOffset_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getParentComToThisComOffset() {
        long jBtMultiBodyLinkFloatData_parentComToThisComOffset_get = DynamicsJNI.btMultiBodyLinkFloatData_parentComToThisComOffset_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkFloatData_parentComToThisComOffset_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtMultiBodyLinkFloatData_parentComToThisComOffset_get, false);
    }

    public void setThisPivotToThisComOffset(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btMultiBodyLinkFloatData_thisPivotToThisComOffset_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getThisPivotToThisComOffset() {
        long jBtMultiBodyLinkFloatData_thisPivotToThisComOffset_get = DynamicsJNI.btMultiBodyLinkFloatData_thisPivotToThisComOffset_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkFloatData_thisPivotToThisComOffset_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtMultiBodyLinkFloatData_thisPivotToThisComOffset_get, false);
    }

    public void setJointAxisTop(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointAxisTop_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getJointAxisTop() {
        long jBtMultiBodyLinkFloatData_jointAxisTop_get = DynamicsJNI.btMultiBodyLinkFloatData_jointAxisTop_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkFloatData_jointAxisTop_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtMultiBodyLinkFloatData_jointAxisTop_get, false);
    }

    public void setJointAxisBottom(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointAxisBottom_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getJointAxisBottom() {
        long jBtMultiBodyLinkFloatData_jointAxisBottom_get = DynamicsJNI.btMultiBodyLinkFloatData_jointAxisBottom_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkFloatData_jointAxisBottom_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtMultiBodyLinkFloatData_jointAxisBottom_get, false);
    }

    public void setLinkInertia(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btMultiBodyLinkFloatData_linkInertia_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getLinkInertia() {
        long jBtMultiBodyLinkFloatData_linkInertia_get = DynamicsJNI.btMultiBodyLinkFloatData_linkInertia_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkFloatData_linkInertia_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtMultiBodyLinkFloatData_linkInertia_get, false);
    }

    public void setDofCount(int i) {
        DynamicsJNI.btMultiBodyLinkFloatData_dofCount_set(this.swigCPtr, this, i);
    }

    public int getDofCount() {
        return DynamicsJNI.btMultiBodyLinkFloatData_dofCount_get(this.swigCPtr, this);
    }

    public void setLinkMass(float f) {
        DynamicsJNI.btMultiBodyLinkFloatData_linkMass_set(this.swigCPtr, this, f);
    }

    public float getLinkMass() {
        return DynamicsJNI.btMultiBodyLinkFloatData_linkMass_get(this.swigCPtr, this);
    }

    public void setParentIndex(int i) {
        DynamicsJNI.btMultiBodyLinkFloatData_parentIndex_set(this.swigCPtr, this, i);
    }

    public int getParentIndex() {
        return DynamicsJNI.btMultiBodyLinkFloatData_parentIndex_get(this.swigCPtr, this);
    }

    public void setJointType(int i) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointType_set(this.swigCPtr, this, i);
    }

    public int getJointType() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointType_get(this.swigCPtr, this);
    }

    public void setJointPos(float[] fArr) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointPos_set(this.swigCPtr, this, fArr);
    }

    public float[] getJointPos() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointPos_get(this.swigCPtr, this);
    }

    public void setJointVel(float[] fArr) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointVel_set(this.swigCPtr, this, fArr);
    }

    public float[] getJointVel() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointVel_get(this.swigCPtr, this);
    }

    public void setJointTorque(float[] fArr) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointTorque_set(this.swigCPtr, this, fArr);
    }

    public float[] getJointTorque() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointTorque_get(this.swigCPtr, this);
    }

    public void setPosVarCount(int i) {
        DynamicsJNI.btMultiBodyLinkFloatData_posVarCount_set(this.swigCPtr, this, i);
    }

    public int getPosVarCount() {
        return DynamicsJNI.btMultiBodyLinkFloatData_posVarCount_get(this.swigCPtr, this);
    }

    public void setJointDamping(float f) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointDamping_set(this.swigCPtr, this, f);
    }

    public float getJointDamping() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointDamping_get(this.swigCPtr, this);
    }

    public void setJointFriction(float f) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointFriction_set(this.swigCPtr, this, f);
    }

    public float getJointFriction() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointFriction_get(this.swigCPtr, this);
    }

    public void setJointLowerLimit(float f) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointLowerLimit_set(this.swigCPtr, this, f);
    }

    public float getJointLowerLimit() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointLowerLimit_get(this.swigCPtr, this);
    }

    public void setJointUpperLimit(float f) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointUpperLimit_set(this.swigCPtr, this, f);
    }

    public float getJointUpperLimit() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointUpperLimit_get(this.swigCPtr, this);
    }

    public void setJointMaxForce(float f) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointMaxForce_set(this.swigCPtr, this, f);
    }

    public float getJointMaxForce() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointMaxForce_get(this.swigCPtr, this);
    }

    public void setJointMaxVelocity(float f) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointMaxVelocity_set(this.swigCPtr, this, f);
    }

    public float getJointMaxVelocity() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointMaxVelocity_get(this.swigCPtr, this);
    }

    public void setLinkName(String str) {
        DynamicsJNI.btMultiBodyLinkFloatData_linkName_set(this.swigCPtr, this, str);
    }

    public String getLinkName() {
        return DynamicsJNI.btMultiBodyLinkFloatData_linkName_get(this.swigCPtr, this);
    }

    public void setJointName(String str) {
        DynamicsJNI.btMultiBodyLinkFloatData_jointName_set(this.swigCPtr, this, str);
    }

    public String getJointName() {
        return DynamicsJNI.btMultiBodyLinkFloatData_jointName_get(this.swigCPtr, this);
    }

    public void setLinkCollider(btCollisionObjectFloatData btcollisionobjectfloatdata) {
        DynamicsJNI.btMultiBodyLinkFloatData_linkCollider_set(this.swigCPtr, this, btCollisionObjectFloatData.getCPtr(btcollisionobjectfloatdata), btcollisionobjectfloatdata);
    }

    public btCollisionObjectFloatData getLinkCollider() {
        long jBtMultiBodyLinkFloatData_linkCollider_get = DynamicsJNI.btMultiBodyLinkFloatData_linkCollider_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkFloatData_linkCollider_get == 0) {
            return null;
        }
        return new btCollisionObjectFloatData(jBtMultiBodyLinkFloatData_linkCollider_get, false);
    }

    public void setPaddingPtr(String str) {
        DynamicsJNI.btMultiBodyLinkFloatData_paddingPtr_set(this.swigCPtr, this, str);
    }

    public String getPaddingPtr() {
        return DynamicsJNI.btMultiBodyLinkFloatData_paddingPtr_get(this.swigCPtr, this);
    }

    public btMultiBodyLinkFloatData() {
        this(DynamicsJNI.new_btMultiBodyLinkFloatData(), true);
    }
}
