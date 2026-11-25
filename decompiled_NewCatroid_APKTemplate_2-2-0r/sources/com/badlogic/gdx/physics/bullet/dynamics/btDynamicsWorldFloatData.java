package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btDynamicsWorldFloatData extends BulletBase {
    private long swigCPtr;

    protected btDynamicsWorldFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDynamicsWorldFloatData(long j, boolean z) {
        this("btDynamicsWorldFloatData", j, z);
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

    public static long getCPtr(btDynamicsWorldFloatData btdynamicsworldfloatdata) {
        if (btdynamicsworldfloatdata == null) {
            return 0L;
        }
        return btdynamicsworldfloatdata.swigCPtr;
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
                DynamicsJNI.delete_btDynamicsWorldFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setSolverInfo(btContactSolverInfoFloatData btcontactsolverinfofloatdata) {
        DynamicsJNI.btDynamicsWorldFloatData_solverInfo_set(this.swigCPtr, this, btContactSolverInfoFloatData.getCPtr(btcontactsolverinfofloatdata), btcontactsolverinfofloatdata);
    }

    public btContactSolverInfoFloatData getSolverInfo() {
        long jBtDynamicsWorldFloatData_solverInfo_get = DynamicsJNI.btDynamicsWorldFloatData_solverInfo_get(this.swigCPtr, this);
        if (jBtDynamicsWorldFloatData_solverInfo_get == 0) {
            return null;
        }
        return new btContactSolverInfoFloatData(jBtDynamicsWorldFloatData_solverInfo_get, false);
    }

    public void setGravity(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btDynamicsWorldFloatData_gravity_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getGravity() {
        long jBtDynamicsWorldFloatData_gravity_get = DynamicsJNI.btDynamicsWorldFloatData_gravity_get(this.swigCPtr, this);
        if (jBtDynamicsWorldFloatData_gravity_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtDynamicsWorldFloatData_gravity_get, false);
    }

    public btDynamicsWorldFloatData() {
        this(DynamicsJNI.new_btDynamicsWorldFloatData(), true);
    }
}
