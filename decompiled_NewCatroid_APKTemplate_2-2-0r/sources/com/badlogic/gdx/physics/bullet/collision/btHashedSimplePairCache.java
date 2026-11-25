package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btHashedSimplePairCache extends BulletBase {
    private long swigCPtr;

    protected btHashedSimplePairCache(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btHashedSimplePairCache(long j, boolean z) {
        this("btHashedSimplePairCache", j, z);
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

    public static long getCPtr(btHashedSimplePairCache bthashedsimplepaircache) {
        if (bthashedsimplepaircache == null) {
            return 0L;
        }
        return bthashedsimplepaircache.swigCPtr;
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
                CollisionJNI.delete_btHashedSimplePairCache(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btHashedSimplePairCache() {
        this(CollisionJNI.new_btHashedSimplePairCache(), true);
    }

    public void removeAllPairs() {
        CollisionJNI.btHashedSimplePairCache_removeAllPairs(this.swigCPtr, this);
    }

    public long removeOverlappingPair(int i, int i2) {
        return CollisionJNI.btHashedSimplePairCache_removeOverlappingPair(this.swigCPtr, this, i, i2);
    }

    public btSimplePair addOverlappingPair(int i, int i2) {
        long jBtHashedSimplePairCache_addOverlappingPair = CollisionJNI.btHashedSimplePairCache_addOverlappingPair(this.swigCPtr, this, i, i2);
        if (jBtHashedSimplePairCache_addOverlappingPair == 0) {
            return null;
        }
        return new btSimplePair(jBtHashedSimplePairCache_addOverlappingPair, false);
    }

    public btSimplePair getOverlappingPairArrayPtr() {
        long jBtHashedSimplePairCache_getOverlappingPairArrayPtr = CollisionJNI.btHashedSimplePairCache_getOverlappingPairArrayPtr(this.swigCPtr, this);
        if (jBtHashedSimplePairCache_getOverlappingPairArrayPtr == 0) {
            return null;
        }
        return new btSimplePair(jBtHashedSimplePairCache_getOverlappingPairArrayPtr, false);
    }

    public btSimplePair getOverlappingPairArrayPtrConst() {
        long jBtHashedSimplePairCache_getOverlappingPairArrayPtrConst = CollisionJNI.btHashedSimplePairCache_getOverlappingPairArrayPtrConst(this.swigCPtr, this);
        if (jBtHashedSimplePairCache_getOverlappingPairArrayPtrConst == 0) {
            return null;
        }
        return new btSimplePair(jBtHashedSimplePairCache_getOverlappingPairArrayPtrConst, false);
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSimplePair_t getOverlappingPairArray() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSimplePair_t(CollisionJNI.btHashedSimplePairCache_getOverlappingPairArray(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSimplePair_t getOverlappingPairArrayConst() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSimplePair_t(CollisionJNI.btHashedSimplePairCache_getOverlappingPairArrayConst(this.swigCPtr, this), false);
    }

    public btSimplePair findPair(int i, int i2) {
        long jBtHashedSimplePairCache_findPair = CollisionJNI.btHashedSimplePairCache_findPair(this.swigCPtr, this, i, i2);
        if (jBtHashedSimplePairCache_findPair == 0) {
            return null;
        }
        return new btSimplePair(jBtHashedSimplePairCache_findPair, false);
    }

    public int GetCount() {
        return CollisionJNI.btHashedSimplePairCache_GetCount(this.swigCPtr, this);
    }

    public int getNumOverlappingPairs() {
        return CollisionJNI.btHashedSimplePairCache_getNumOverlappingPairs(this.swigCPtr, this);
    }
}
