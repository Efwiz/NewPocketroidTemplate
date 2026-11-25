package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btGjkCollisionDescription extends BulletBase {
    private long swigCPtr;

    protected btGjkCollisionDescription(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGjkCollisionDescription(long j, boolean z) {
        this("btGjkCollisionDescription", j, z);
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

    public static long getCPtr(btGjkCollisionDescription btgjkcollisiondescription) {
        if (btgjkcollisiondescription == null) {
            return 0L;
        }
        return btgjkcollisiondescription.swigCPtr;
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
                CollisionJNI.delete_btGjkCollisionDescription(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setFirstDir(btVector3 btvector3) {
        CollisionJNI.btGjkCollisionDescription_firstDir_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getFirstDir() {
        long jBtGjkCollisionDescription_firstDir_get = CollisionJNI.btGjkCollisionDescription_firstDir_get(this.swigCPtr, this);
        if (jBtGjkCollisionDescription_firstDir_get == 0) {
            return null;
        }
        return new btVector3(jBtGjkCollisionDescription_firstDir_get, false);
    }

    public void setMaxGjkIterations(int i) {
        CollisionJNI.btGjkCollisionDescription_maxGjkIterations_set(this.swigCPtr, this, i);
    }

    public int getMaxGjkIterations() {
        return CollisionJNI.btGjkCollisionDescription_maxGjkIterations_get(this.swigCPtr, this);
    }

    public void setMaximumDistanceSquared(float f) {
        CollisionJNI.btGjkCollisionDescription_maximumDistanceSquared_set(this.swigCPtr, this, f);
    }

    public float getMaximumDistanceSquared() {
        return CollisionJNI.btGjkCollisionDescription_maximumDistanceSquared_get(this.swigCPtr, this);
    }

    public void setGjkRelError2(float f) {
        CollisionJNI.btGjkCollisionDescription_gjkRelError2_set(this.swigCPtr, this, f);
    }

    public float getGjkRelError2() {
        return CollisionJNI.btGjkCollisionDescription_gjkRelError2_get(this.swigCPtr, this);
    }

    public btGjkCollisionDescription() {
        this(CollisionJNI.new_btGjkCollisionDescription(), true);
    }
}
