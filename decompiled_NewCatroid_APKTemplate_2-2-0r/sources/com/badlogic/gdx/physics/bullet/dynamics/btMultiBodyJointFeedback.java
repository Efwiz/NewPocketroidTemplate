package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btSpatialForceVector;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyJointFeedback extends BulletBase {
    private long swigCPtr;

    protected btMultiBodyJointFeedback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiBodyJointFeedback(long j, boolean z) {
        this("btMultiBodyJointFeedback", j, z);
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

    public static long getCPtr(btMultiBodyJointFeedback btmultibodyjointfeedback) {
        if (btmultibodyjointfeedback == null) {
            return 0L;
        }
        return btmultibodyjointfeedback.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodyJointFeedback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setReactionForces(btSpatialForceVector btspatialforcevector) {
        DynamicsJNI.btMultiBodyJointFeedback_reactionForces_set(this.swigCPtr, this, btSpatialForceVector.getCPtr(btspatialforcevector), btspatialforcevector);
    }

    public btSpatialForceVector getReactionForces() {
        long jBtMultiBodyJointFeedback_reactionForces_get = DynamicsJNI.btMultiBodyJointFeedback_reactionForces_get(this.swigCPtr, this);
        if (jBtMultiBodyJointFeedback_reactionForces_get == 0) {
            return null;
        }
        return new btSpatialForceVector(jBtMultiBodyJointFeedback_reactionForces_get, false);
    }

    public btMultiBodyJointFeedback() {
        this(DynamicsJNI.new_btMultiBodyJointFeedback(), true);
    }
}
