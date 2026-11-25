package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btWheelInfoConstructionInfo extends BulletBase {
    private long swigCPtr;

    protected btWheelInfoConstructionInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btWheelInfoConstructionInfo(long j, boolean z) {
        this("btWheelInfoConstructionInfo", j, z);
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

    public static long getCPtr(btWheelInfoConstructionInfo btwheelinfoconstructioninfo) {
        if (btwheelinfoconstructioninfo == null) {
            return 0L;
        }
        return btwheelinfoconstructioninfo.swigCPtr;
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
                DynamicsJNI.delete_btWheelInfoConstructionInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setChassisConnectionCS(btVector3 btvector3) {
        DynamicsJNI.btWheelInfoConstructionInfo_chassisConnectionCS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getChassisConnectionCS() {
        long jBtWheelInfoConstructionInfo_chassisConnectionCS_get = DynamicsJNI.btWheelInfoConstructionInfo_chassisConnectionCS_get(this.swigCPtr, this);
        if (jBtWheelInfoConstructionInfo_chassisConnectionCS_get == 0) {
            return null;
        }
        return new btVector3(jBtWheelInfoConstructionInfo_chassisConnectionCS_get, false);
    }

    public void setWheelDirectionCS(btVector3 btvector3) {
        DynamicsJNI.btWheelInfoConstructionInfo_wheelDirectionCS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getWheelDirectionCS() {
        long jBtWheelInfoConstructionInfo_wheelDirectionCS_get = DynamicsJNI.btWheelInfoConstructionInfo_wheelDirectionCS_get(this.swigCPtr, this);
        if (jBtWheelInfoConstructionInfo_wheelDirectionCS_get == 0) {
            return null;
        }
        return new btVector3(jBtWheelInfoConstructionInfo_wheelDirectionCS_get, false);
    }

    public void setWheelAxleCS(btVector3 btvector3) {
        DynamicsJNI.btWheelInfoConstructionInfo_wheelAxleCS_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getWheelAxleCS() {
        long jBtWheelInfoConstructionInfo_wheelAxleCS_get = DynamicsJNI.btWheelInfoConstructionInfo_wheelAxleCS_get(this.swigCPtr, this);
        if (jBtWheelInfoConstructionInfo_wheelAxleCS_get == 0) {
            return null;
        }
        return new btVector3(jBtWheelInfoConstructionInfo_wheelAxleCS_get, false);
    }

    public void setSuspensionRestLength(float f) {
        DynamicsJNI.btWheelInfoConstructionInfo_suspensionRestLength_set(this.swigCPtr, this, f);
    }

    public float getSuspensionRestLength() {
        return DynamicsJNI.btWheelInfoConstructionInfo_suspensionRestLength_get(this.swigCPtr, this);
    }

    public void setMaxSuspensionTravelCm(float f) {
        DynamicsJNI.btWheelInfoConstructionInfo_maxSuspensionTravelCm_set(this.swigCPtr, this, f);
    }

    public float getMaxSuspensionTravelCm() {
        return DynamicsJNI.btWheelInfoConstructionInfo_maxSuspensionTravelCm_get(this.swigCPtr, this);
    }

    public void setWheelRadius(float f) {
        DynamicsJNI.btWheelInfoConstructionInfo_wheelRadius_set(this.swigCPtr, this, f);
    }

    public float getWheelRadius() {
        return DynamicsJNI.btWheelInfoConstructionInfo_wheelRadius_get(this.swigCPtr, this);
    }

    public void setSuspensionStiffness(float f) {
        DynamicsJNI.btWheelInfoConstructionInfo_suspensionStiffness_set(this.swigCPtr, this, f);
    }

    public float getSuspensionStiffness() {
        return DynamicsJNI.btWheelInfoConstructionInfo_suspensionStiffness_get(this.swigCPtr, this);
    }

    public void setWheelsDampingCompression(float f) {
        DynamicsJNI.btWheelInfoConstructionInfo_wheelsDampingCompression_set(this.swigCPtr, this, f);
    }

    public float getWheelsDampingCompression() {
        return DynamicsJNI.btWheelInfoConstructionInfo_wheelsDampingCompression_get(this.swigCPtr, this);
    }

    public void setWheelsDampingRelaxation(float f) {
        DynamicsJNI.btWheelInfoConstructionInfo_wheelsDampingRelaxation_set(this.swigCPtr, this, f);
    }

    public float getWheelsDampingRelaxation() {
        return DynamicsJNI.btWheelInfoConstructionInfo_wheelsDampingRelaxation_get(this.swigCPtr, this);
    }

    public void setFrictionSlip(float f) {
        DynamicsJNI.btWheelInfoConstructionInfo_frictionSlip_set(this.swigCPtr, this, f);
    }

    public float getFrictionSlip() {
        return DynamicsJNI.btWheelInfoConstructionInfo_frictionSlip_get(this.swigCPtr, this);
    }

    public void setMaxSuspensionForce(float f) {
        DynamicsJNI.btWheelInfoConstructionInfo_maxSuspensionForce_set(this.swigCPtr, this, f);
    }

    public float getMaxSuspensionForce() {
        return DynamicsJNI.btWheelInfoConstructionInfo_maxSuspensionForce_get(this.swigCPtr, this);
    }

    public void setBIsFrontWheel(boolean z) {
        DynamicsJNI.btWheelInfoConstructionInfo_bIsFrontWheel_set(this.swigCPtr, this, z);
    }

    public boolean getBIsFrontWheel() {
        return DynamicsJNI.btWheelInfoConstructionInfo_bIsFrontWheel_get(this.swigCPtr, this);
    }

    public btWheelInfoConstructionInfo() {
        this(DynamicsJNI.new_btWheelInfoConstructionInfo(), true);
    }
}
