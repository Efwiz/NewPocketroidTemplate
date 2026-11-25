package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btMprCollisionDescription extends BulletBase {
    private long swigCPtr;

    protected btMprCollisionDescription(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMprCollisionDescription(long j, boolean z) {
        this("btMprCollisionDescription", j, z);
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

    public static long getCPtr(btMprCollisionDescription btmprcollisiondescription) {
        if (btmprcollisiondescription == null) {
            return 0L;
        }
        return btmprcollisiondescription.swigCPtr;
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
                CollisionJNI.delete_btMprCollisionDescription(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setFirstDir(btVector3 btvector3) {
        CollisionJNI.btMprCollisionDescription_firstDir_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getFirstDir() {
        long jBtMprCollisionDescription_firstDir_get = CollisionJNI.btMprCollisionDescription_firstDir_get(this.swigCPtr, this);
        if (jBtMprCollisionDescription_firstDir_get == 0) {
            return null;
        }
        return new btVector3(jBtMprCollisionDescription_firstDir_get, false);
    }

    public void setMaxGjkIterations(int i) {
        CollisionJNI.btMprCollisionDescription_maxGjkIterations_set(this.swigCPtr, this, i);
    }

    public int getMaxGjkIterations() {
        return CollisionJNI.btMprCollisionDescription_maxGjkIterations_get(this.swigCPtr, this);
    }

    public void setMaximumDistanceSquared(float f) {
        CollisionJNI.btMprCollisionDescription_maximumDistanceSquared_set(this.swigCPtr, this, f);
    }

    public float getMaximumDistanceSquared() {
        return CollisionJNI.btMprCollisionDescription_maximumDistanceSquared_get(this.swigCPtr, this);
    }

    public void setGjkRelError2(float f) {
        CollisionJNI.btMprCollisionDescription_gjkRelError2_set(this.swigCPtr, this, f);
    }

    public float getGjkRelError2() {
        return CollisionJNI.btMprCollisionDescription_gjkRelError2_get(this.swigCPtr, this);
    }

    public btMprCollisionDescription() {
        this(CollisionJNI.new_btMprCollisionDescription(), true);
    }
}
