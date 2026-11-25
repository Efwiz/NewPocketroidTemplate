package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;

/* JADX INFO: loaded from: classes8.dex */
public class btDispatcherInfo extends BulletBase {
    private long swigCPtr;

    public static final class DispatchFunc {
        public static final int DISPATCH_CONTINUOUS = 2;
        public static final int DISPATCH_DISCRETE = 1;
    }

    protected btDispatcherInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDispatcherInfo(long j, boolean z) {
        this("btDispatcherInfo", j, z);
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

    public static long getCPtr(btDispatcherInfo btdispatcherinfo) {
        if (btdispatcherinfo == null) {
            return 0L;
        }
        return btdispatcherinfo.swigCPtr;
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
                CollisionJNI.delete_btDispatcherInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btDispatcherInfo() {
        this(CollisionJNI.new_btDispatcherInfo(), true);
    }

    public void setTimeStep(float f) {
        CollisionJNI.btDispatcherInfo_timeStep_set(this.swigCPtr, this, f);
    }

    public float getTimeStep() {
        return CollisionJNI.btDispatcherInfo_timeStep_get(this.swigCPtr, this);
    }

    public void setStepCount(int i) {
        CollisionJNI.btDispatcherInfo_stepCount_set(this.swigCPtr, this, i);
    }

    public int getStepCount() {
        return CollisionJNI.btDispatcherInfo_stepCount_get(this.swigCPtr, this);
    }

    public void setDispatchFunc(int i) {
        CollisionJNI.btDispatcherInfo_dispatchFunc_set(this.swigCPtr, this, i);
    }

    public int getDispatchFunc() {
        return CollisionJNI.btDispatcherInfo_dispatchFunc_get(this.swigCPtr, this);
    }

    public void setTimeOfImpact(float f) {
        CollisionJNI.btDispatcherInfo_timeOfImpact_set(this.swigCPtr, this, f);
    }

    public float getTimeOfImpact() {
        return CollisionJNI.btDispatcherInfo_timeOfImpact_get(this.swigCPtr, this);
    }

    public void setUseContinuous(boolean z) {
        CollisionJNI.btDispatcherInfo_useContinuous_set(this.swigCPtr, this, z);
    }

    public boolean getUseContinuous() {
        return CollisionJNI.btDispatcherInfo_useContinuous_get(this.swigCPtr, this);
    }

    public void setDebugDraw(btIDebugDraw btidebugdraw) {
        CollisionJNI.btDispatcherInfo_debugDraw_set(this.swigCPtr, this, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public btIDebugDraw getDebugDraw() {
        long jBtDispatcherInfo_debugDraw_get = CollisionJNI.btDispatcherInfo_debugDraw_get(this.swigCPtr, this);
        if (jBtDispatcherInfo_debugDraw_get == 0) {
            return null;
        }
        return new btIDebugDraw(jBtDispatcherInfo_debugDraw_get, false);
    }

    public void setEnableSatConvex(boolean z) {
        CollisionJNI.btDispatcherInfo_enableSatConvex_set(this.swigCPtr, this, z);
    }

    public boolean getEnableSatConvex() {
        return CollisionJNI.btDispatcherInfo_enableSatConvex_get(this.swigCPtr, this);
    }

    public void setEnableSPU(boolean z) {
        CollisionJNI.btDispatcherInfo_enableSPU_set(this.swigCPtr, this, z);
    }

    public boolean getEnableSPU() {
        return CollisionJNI.btDispatcherInfo_enableSPU_get(this.swigCPtr, this);
    }

    public void setUseEpa(boolean z) {
        CollisionJNI.btDispatcherInfo_useEpa_set(this.swigCPtr, this, z);
    }

    public boolean getUseEpa() {
        return CollisionJNI.btDispatcherInfo_useEpa_get(this.swigCPtr, this);
    }

    public void setAllowedCcdPenetration(float f) {
        CollisionJNI.btDispatcherInfo_allowedCcdPenetration_set(this.swigCPtr, this, f);
    }

    public float getAllowedCcdPenetration() {
        return CollisionJNI.btDispatcherInfo_allowedCcdPenetration_get(this.swigCPtr, this);
    }

    public void setUseConvexConservativeDistanceUtil(boolean z) {
        CollisionJNI.btDispatcherInfo_useConvexConservativeDistanceUtil_set(this.swigCPtr, this, z);
    }

    public boolean getUseConvexConservativeDistanceUtil() {
        return CollisionJNI.btDispatcherInfo_useConvexConservativeDistanceUtil_get(this.swigCPtr, this);
    }

    public void setConvexConservativeDistanceThreshold(float f) {
        CollisionJNI.btDispatcherInfo_convexConservativeDistanceThreshold_set(this.swigCPtr, this, f);
    }

    public float getConvexConservativeDistanceThreshold() {
        return CollisionJNI.btDispatcherInfo_convexConservativeDistanceThreshold_get(this.swigCPtr, this);
    }
}
