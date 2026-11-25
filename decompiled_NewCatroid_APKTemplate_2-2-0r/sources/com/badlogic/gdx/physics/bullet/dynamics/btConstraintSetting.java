package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btConstraintSetting extends BulletBase {
    private long swigCPtr;

    protected btConstraintSetting(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConstraintSetting(long j, boolean z) {
        this("btConstraintSetting", j, z);
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

    public static long getCPtr(btConstraintSetting btconstraintsetting) {
        if (btconstraintsetting == null) {
            return 0L;
        }
        return btconstraintsetting.swigCPtr;
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
                DynamicsJNI.delete_btConstraintSetting(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btConstraintSetting() {
        this(DynamicsJNI.new_btConstraintSetting(), true);
    }

    public void setTau(float f) {
        DynamicsJNI.btConstraintSetting_tau_set(this.swigCPtr, this, f);
    }

    public float getTau() {
        return DynamicsJNI.btConstraintSetting_tau_get(this.swigCPtr, this);
    }

    public void setDamping(float f) {
        DynamicsJNI.btConstraintSetting_damping_set(this.swigCPtr, this, f);
    }

    public float getDamping() {
        return DynamicsJNI.btConstraintSetting_damping_get(this.swigCPtr, this);
    }

    public void setImpulseClamp(float f) {
        DynamicsJNI.btConstraintSetting_impulseClamp_set(this.swigCPtr, this, f);
    }

    public float getImpulseClamp() {
        return DynamicsJNI.btConstraintSetting_impulseClamp_get(this.swigCPtr, this);
    }
}
