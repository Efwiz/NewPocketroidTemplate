package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btSimplePair extends BulletBase {
    private long swigCPtr;

    protected btSimplePair(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSimplePair(long j, boolean z) {
        this("btSimplePair", j, z);
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

    public static long getCPtr(btSimplePair btsimplepair) {
        if (btsimplepair == null) {
            return 0L;
        }
        return btsimplepair.swigCPtr;
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
                CollisionJNI.delete_btSimplePair(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSimplePair(int i, int i2) {
        this(CollisionJNI.new_btSimplePair(i, i2), true);
    }

    public void setIndexA(int i) {
        CollisionJNI.btSimplePair_indexA_set(this.swigCPtr, this, i);
    }

    public int getIndexA() {
        return CollisionJNI.btSimplePair_indexA_get(this.swigCPtr, this);
    }

    public void setIndexB(int i) {
        CollisionJNI.btSimplePair_indexB_set(this.swigCPtr, this, i);
    }

    public int getIndexB() {
        return CollisionJNI.btSimplePair_indexB_get(this.swigCPtr, this);
    }

    public void setUserPointer(long j) {
        CollisionJNI.btSimplePair_userPointer_set(this.swigCPtr, this, j);
    }

    public long getUserPointer() {
        return CollisionJNI.btSimplePair_userPointer_get(this.swigCPtr, this);
    }

    public void setUserValue(int i) {
        CollisionJNI.btSimplePair_userValue_set(this.swigCPtr, this, i);
    }

    public int getUserValue() {
        return CollisionJNI.btSimplePair_userValue_get(this.swigCPtr, this);
    }
}
