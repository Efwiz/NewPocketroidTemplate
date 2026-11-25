package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btConstraintRow extends BulletBase {
    private long swigCPtr;

    protected btConstraintRow(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConstraintRow(long j, boolean z) {
        this("btConstraintRow", j, z);
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

    public static long getCPtr(btConstraintRow btconstraintrow) {
        if (btconstraintrow == null) {
            return 0L;
        }
        return btconstraintrow.swigCPtr;
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
                CollisionJNI.delete_btConstraintRow(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setNormal(float[] fArr) {
        CollisionJNI.btConstraintRow_normal_set(this.swigCPtr, this, fArr);
    }

    public float[] getNormal() {
        return CollisionJNI.btConstraintRow_normal_get(this.swigCPtr, this);
    }

    public void setRhs(float f) {
        CollisionJNI.btConstraintRow_rhs_set(this.swigCPtr, this, f);
    }

    public float getRhs() {
        return CollisionJNI.btConstraintRow_rhs_get(this.swigCPtr, this);
    }

    public void setJacDiagInv(float f) {
        CollisionJNI.btConstraintRow_jacDiagInv_set(this.swigCPtr, this, f);
    }

    public float getJacDiagInv() {
        return CollisionJNI.btConstraintRow_jacDiagInv_get(this.swigCPtr, this);
    }

    public void setLowerLimit(float f) {
        CollisionJNI.btConstraintRow_lowerLimit_set(this.swigCPtr, this, f);
    }

    public float getLowerLimit() {
        return CollisionJNI.btConstraintRow_lowerLimit_get(this.swigCPtr, this);
    }

    public void setUpperLimit(float f) {
        CollisionJNI.btConstraintRow_upperLimit_set(this.swigCPtr, this, f);
    }

    public float getUpperLimit() {
        return CollisionJNI.btConstraintRow_upperLimit_get(this.swigCPtr, this);
    }

    public void setAccumImpulse(float f) {
        CollisionJNI.btConstraintRow_accumImpulse_set(this.swigCPtr, this, f);
    }

    public float getAccumImpulse() {
        return CollisionJNI.btConstraintRow_accumImpulse_get(this.swigCPtr, this);
    }

    public btConstraintRow() {
        this(CollisionJNI.new_btConstraintRow(), true);
    }
}
