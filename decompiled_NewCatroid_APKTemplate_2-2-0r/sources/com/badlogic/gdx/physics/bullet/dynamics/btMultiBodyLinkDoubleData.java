package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btQuaternionDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyLinkDoubleData extends BulletBase {
    private long swigCPtr;

    protected btMultiBodyLinkDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiBodyLinkDoubleData(long j, boolean z) {
        this("btMultiBodyLinkDoubleData", j, z);
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

    public static long getCPtr(btMultiBodyLinkDoubleData btmultibodylinkdoubledata) {
        if (btmultibodylinkdoubledata == null) {
            return 0L;
        }
        return btmultibodylinkdoubledata.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodyLinkDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setZeroRotParentToThis(btQuaternionDoubleData btquaterniondoubledata) {
        DynamicsJNI.btMultiBodyLinkDoubleData_zeroRotParentToThis_set(this.swigCPtr, this, btQuaternionDoubleData.getCPtr(btquaterniondoubledata), btquaterniondoubledata);
    }

    public btQuaternionDoubleData getZeroRotParentToThis() {
        long jBtMultiBodyLinkDoubleData_zeroRotParentToThis_get = DynamicsJNI.btMultiBodyLinkDoubleData_zeroRotParentToThis_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkDoubleData_zeroRotParentToThis_get == 0) {
            return null;
        }
        return new btQuaternionDoubleData(jBtMultiBodyLinkDoubleData_zeroRotParentToThis_get, false);
    }

    public void setParentComToThisComOffset(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btMultiBodyLinkDoubleData_parentComToThisComOffset_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getParentComToThisComOffset() {
        long jBtMultiBodyLinkDoubleData_parentComToThisComOffset_get = DynamicsJNI.btMultiBodyLinkDoubleData_parentComToThisComOffset_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkDoubleData_parentComToThisComOffset_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtMultiBodyLinkDoubleData_parentComToThisComOffset_get, false);
    }

    public void setThisPivotToThisComOffset(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btMultiBodyLinkDoubleData_thisPivotToThisComOffset_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getThisPivotToThisComOffset() {
        long jBtMultiBodyLinkDoubleData_thisPivotToThisComOffset_get = DynamicsJNI.btMultiBodyLinkDoubleData_thisPivotToThisComOffset_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkDoubleData_thisPivotToThisComOffset_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtMultiBodyLinkDoubleData_thisPivotToThisComOffset_get, false);
    }

    public void setJointAxisTop(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointAxisTop_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getJointAxisTop() {
        long jBtMultiBodyLinkDoubleData_jointAxisTop_get = DynamicsJNI.btMultiBodyLinkDoubleData_jointAxisTop_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkDoubleData_jointAxisTop_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtMultiBodyLinkDoubleData_jointAxisTop_get, false);
    }

    public void setJointAxisBottom(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointAxisBottom_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getJointAxisBottom() {
        long jBtMultiBodyLinkDoubleData_jointAxisBottom_get = DynamicsJNI.btMultiBodyLinkDoubleData_jointAxisBottom_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkDoubleData_jointAxisBottom_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtMultiBodyLinkDoubleData_jointAxisBottom_get, false);
    }

    public void setLinkInertia(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btMultiBodyLinkDoubleData_linkInertia_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getLinkInertia() {
        long jBtMultiBodyLinkDoubleData_linkInertia_get = DynamicsJNI.btMultiBodyLinkDoubleData_linkInertia_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkDoubleData_linkInertia_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtMultiBodyLinkDoubleData_linkInertia_get, false);
    }

    public void setLinkMass(double d) {
        DynamicsJNI.btMultiBodyLinkDoubleData_linkMass_set(this.swigCPtr, this, d);
    }

    public double getLinkMass() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_linkMass_get(this.swigCPtr, this);
    }

    public void setParentIndex(int i) {
        DynamicsJNI.btMultiBodyLinkDoubleData_parentIndex_set(this.swigCPtr, this, i);
    }

    public int getParentIndex() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_parentIndex_get(this.swigCPtr, this);
    }

    public void setJointType(int i) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointType_set(this.swigCPtr, this, i);
    }

    public int getJointType() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointType_get(this.swigCPtr, this);
    }

    public void setDofCount(int i) {
        DynamicsJNI.btMultiBodyLinkDoubleData_dofCount_set(this.swigCPtr, this, i);
    }

    public int getDofCount() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_dofCount_get(this.swigCPtr, this);
    }

    public void setPosVarCount(int i) {
        DynamicsJNI.btMultiBodyLinkDoubleData_posVarCount_set(this.swigCPtr, this, i);
    }

    public int getPosVarCount() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_posVarCount_get(this.swigCPtr, this);
    }

    public void setJointPos(double[] dArr) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointPos_set(this.swigCPtr, this, dArr);
    }

    public double[] getJointPos() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointPos_get(this.swigCPtr, this);
    }

    public void setJointVel(double[] dArr) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointVel_set(this.swigCPtr, this, dArr);
    }

    public double[] getJointVel() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointVel_get(this.swigCPtr, this);
    }

    public void setJointTorque(double[] dArr) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointTorque_set(this.swigCPtr, this, dArr);
    }

    public double[] getJointTorque() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointTorque_get(this.swigCPtr, this);
    }

    public void setJointDamping(double d) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointDamping_set(this.swigCPtr, this, d);
    }

    public double getJointDamping() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointDamping_get(this.swigCPtr, this);
    }

    public void setJointFriction(double d) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointFriction_set(this.swigCPtr, this, d);
    }

    public double getJointFriction() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointFriction_get(this.swigCPtr, this);
    }

    public void setJointLowerLimit(double d) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointLowerLimit_set(this.swigCPtr, this, d);
    }

    public double getJointLowerLimit() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointLowerLimit_get(this.swigCPtr, this);
    }

    public void setJointUpperLimit(double d) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointUpperLimit_set(this.swigCPtr, this, d);
    }

    public double getJointUpperLimit() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointUpperLimit_get(this.swigCPtr, this);
    }

    public void setJointMaxForce(double d) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointMaxForce_set(this.swigCPtr, this, d);
    }

    public double getJointMaxForce() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointMaxForce_get(this.swigCPtr, this);
    }

    public void setJointMaxVelocity(double d) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointMaxVelocity_set(this.swigCPtr, this, d);
    }

    public double getJointMaxVelocity() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointMaxVelocity_get(this.swigCPtr, this);
    }

    public void setLinkName(String str) {
        DynamicsJNI.btMultiBodyLinkDoubleData_linkName_set(this.swigCPtr, this, str);
    }

    public String getLinkName() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_linkName_get(this.swigCPtr, this);
    }

    public void setJointName(String str) {
        DynamicsJNI.btMultiBodyLinkDoubleData_jointName_set(this.swigCPtr, this, str);
    }

    public String getJointName() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_jointName_get(this.swigCPtr, this);
    }

    public void setLinkCollider(btCollisionObjectDoubleData btcollisionobjectdoubledata) {
        DynamicsJNI.btMultiBodyLinkDoubleData_linkCollider_set(this.swigCPtr, this, btCollisionObjectDoubleData.getCPtr(btcollisionobjectdoubledata), btcollisionobjectdoubledata);
    }

    public btCollisionObjectDoubleData getLinkCollider() {
        long jBtMultiBodyLinkDoubleData_linkCollider_get = DynamicsJNI.btMultiBodyLinkDoubleData_linkCollider_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkDoubleData_linkCollider_get == 0) {
            return null;
        }
        return new btCollisionObjectDoubleData(jBtMultiBodyLinkDoubleData_linkCollider_get, false);
    }

    public void setPaddingPtr(String str) {
        DynamicsJNI.btMultiBodyLinkDoubleData_paddingPtr_set(this.swigCPtr, this, str);
    }

    public String getPaddingPtr() {
        return DynamicsJNI.btMultiBodyLinkDoubleData_paddingPtr_get(this.swigCPtr, this);
    }

    public btMultiBodyLinkDoubleData() {
        this(DynamicsJNI.new_btMultiBodyLinkDoubleData(), true);
    }
}
