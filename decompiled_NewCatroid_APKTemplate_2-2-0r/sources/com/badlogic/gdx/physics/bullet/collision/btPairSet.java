package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btPairSet extends btGimPairArray {
    private long swigCPtr;

    protected btPairSet(String str, long j, boolean z) {
        super(str, CollisionJNI.btPairSet_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btPairSet(long j, boolean z) {
        this("btPairSet", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGimPairArray, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btPairSet_SWIGUpcast(j), z);
    }

    public static long getCPtr(btPairSet btpairset) {
        if (btpairset == null) {
            return 0L;
        }
        return btpairset.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGimPairArray, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGimPairArray, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btPairSet(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btPairSet() {
        this(CollisionJNI.new_btPairSet(), true);
    }

    public void push_pair(int i, int i2) {
        CollisionJNI.btPairSet_push_pair(this.swigCPtr, this, i, i2);
    }

    public void push_pair_inv(int i, int i2) {
        CollisionJNI.btPairSet_push_pair_inv(this.swigCPtr, this, i, i2);
    }
}
