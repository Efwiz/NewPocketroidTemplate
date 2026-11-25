package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btDynamicsWorldDoubleData extends BulletBase {
    private long swigCPtr;

    protected btDynamicsWorldDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDynamicsWorldDoubleData(long j, boolean z) {
        this("btDynamicsWorldDoubleData", j, z);
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

    public static long getCPtr(btDynamicsWorldDoubleData btdynamicsworlddoubledata) {
        if (btdynamicsworlddoubledata == null) {
            return 0L;
        }
        return btdynamicsworlddoubledata.swigCPtr;
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
                DynamicsJNI.delete_btDynamicsWorldDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setSolverInfo(btContactSolverInfoDoubleData btcontactsolverinfodoubledata) {
        DynamicsJNI.btDynamicsWorldDoubleData_solverInfo_set(this.swigCPtr, this, btContactSolverInfoDoubleData.getCPtr(btcontactsolverinfodoubledata), btcontactsolverinfodoubledata);
    }

    public btContactSolverInfoDoubleData getSolverInfo() {
        long jBtDynamicsWorldDoubleData_solverInfo_get = DynamicsJNI.btDynamicsWorldDoubleData_solverInfo_get(this.swigCPtr, this);
        if (jBtDynamicsWorldDoubleData_solverInfo_get == 0) {
            return null;
        }
        return new btContactSolverInfoDoubleData(jBtDynamicsWorldDoubleData_solverInfo_get, false);
    }

    public void setGravity(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btDynamicsWorldDoubleData_gravity_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getGravity() {
        long jBtDynamicsWorldDoubleData_gravity_get = DynamicsJNI.btDynamicsWorldDoubleData_gravity_get(this.swigCPtr, this);
        if (jBtDynamicsWorldDoubleData_gravity_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtDynamicsWorldDoubleData_gravity_get, false);
    }

    public btDynamicsWorldDoubleData() {
        this(DynamicsJNI.new_btDynamicsWorldDoubleData(), true);
    }
}
