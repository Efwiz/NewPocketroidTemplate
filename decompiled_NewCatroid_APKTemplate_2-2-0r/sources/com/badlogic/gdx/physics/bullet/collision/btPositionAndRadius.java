package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btPositionAndRadius extends BulletBase {
    private long swigCPtr;

    protected btPositionAndRadius(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPositionAndRadius(long j, boolean z) {
        this("btPositionAndRadius", j, z);
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

    public static long getCPtr(btPositionAndRadius btpositionandradius) {
        if (btpositionandradius == null) {
            return 0L;
        }
        return btpositionandradius.swigCPtr;
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
                CollisionJNI.delete_btPositionAndRadius(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPos(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btPositionAndRadius_pos_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getPos() {
        long jBtPositionAndRadius_pos_get = CollisionJNI.btPositionAndRadius_pos_get(this.swigCPtr, this);
        if (jBtPositionAndRadius_pos_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtPositionAndRadius_pos_get, false);
    }

    public void setRadius(float f) {
        CollisionJNI.btPositionAndRadius_radius_set(this.swigCPtr, this, f);
    }

    public float getRadius() {
        return CollisionJNI.btPositionAndRadius_radius_get(this.swigCPtr, this);
    }

    public btPositionAndRadius() {
        this(CollisionJNI.new_btPositionAndRadius(), true);
    }
}
