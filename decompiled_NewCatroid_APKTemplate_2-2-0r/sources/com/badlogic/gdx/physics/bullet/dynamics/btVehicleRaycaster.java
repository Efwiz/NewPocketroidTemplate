package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btVehicleRaycaster extends BulletBase {
    private long swigCPtr;

    protected btVehicleRaycaster(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btVehicleRaycaster(long j, boolean z) {
        this("btVehicleRaycaster", j, z);
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

    public static long getCPtr(btVehicleRaycaster btvehicleraycaster) {
        if (btvehicleraycaster == null) {
            return 0L;
        }
        return btvehicleraycaster.swigCPtr;
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
                DynamicsJNI.delete_btVehicleRaycaster(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class btVehicleRaycasterResult extends BulletBase {
        private long swigCPtr;

        protected btVehicleRaycasterResult(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public btVehicleRaycasterResult(long j, boolean z) {
            this("btVehicleRaycasterResult", j, z);
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

        public static long getCPtr(btVehicleRaycasterResult btvehicleraycasterresult) {
            if (btvehicleraycasterresult == null) {
                return 0L;
            }
            return btvehicleraycasterresult.swigCPtr;
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
                    DynamicsJNI.delete_btVehicleRaycaster_btVehicleRaycasterResult(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public btVehicleRaycasterResult() {
            this(DynamicsJNI.new_btVehicleRaycaster_btVehicleRaycasterResult(), true);
        }

        public void setHitPointInWorld(btVector3 btvector3) {
            DynamicsJNI.btVehicleRaycaster_btVehicleRaycasterResult_hitPointInWorld_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getHitPointInWorld() {
            long jBtVehicleRaycaster_btVehicleRaycasterResult_hitPointInWorld_get = DynamicsJNI.btVehicleRaycaster_btVehicleRaycasterResult_hitPointInWorld_get(this.swigCPtr, this);
            if (jBtVehicleRaycaster_btVehicleRaycasterResult_hitPointInWorld_get == 0) {
                return null;
            }
            return new btVector3(jBtVehicleRaycaster_btVehicleRaycasterResult_hitPointInWorld_get, false);
        }

        public void setHitNormalInWorld(btVector3 btvector3) {
            DynamicsJNI.btVehicleRaycaster_btVehicleRaycasterResult_hitNormalInWorld_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getHitNormalInWorld() {
            long jBtVehicleRaycaster_btVehicleRaycasterResult_hitNormalInWorld_get = DynamicsJNI.btVehicleRaycaster_btVehicleRaycasterResult_hitNormalInWorld_get(this.swigCPtr, this);
            if (jBtVehicleRaycaster_btVehicleRaycasterResult_hitNormalInWorld_get == 0) {
                return null;
            }
            return new btVector3(jBtVehicleRaycaster_btVehicleRaycasterResult_hitNormalInWorld_get, false);
        }

        public void setDistFraction(float f) {
            DynamicsJNI.btVehicleRaycaster_btVehicleRaycasterResult_distFraction_set(this.swigCPtr, this, f);
        }

        public float getDistFraction() {
            return DynamicsJNI.btVehicleRaycaster_btVehicleRaycasterResult_distFraction_get(this.swigCPtr, this);
        }
    }

    public long castRay(Vector3 vector3, Vector3 vector4, btVehicleRaycasterResult btvehicleraycasterresult) {
        return DynamicsJNI.btVehicleRaycaster_castRay(this.swigCPtr, this, vector3, vector4, btVehicleRaycasterResult.getCPtr(btvehicleraycasterresult), btvehicleraycasterresult);
    }
}
