package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btHashInt;

/* JADX INFO: loaded from: classes8.dex */
public class btHashMapInternalShortBtHashIntBtTriangleInfo extends BulletBase {
    private long swigCPtr;

    protected btHashMapInternalShortBtHashIntBtTriangleInfo(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btHashMapInternalShortBtHashIntBtTriangleInfo(long j, boolean z) {
        this("btHashMapInternalShortBtHashIntBtTriangleInfo", j, z);
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

    public static long getCPtr(btHashMapInternalShortBtHashIntBtTriangleInfo bthashmapinternalshortbthashintbttriangleinfo) {
        if (bthashmapinternalshortbthashintbttriangleinfo == null) {
            return 0L;
        }
        return bthashmapinternalshortbthashintbttriangleinfo.swigCPtr;
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
                CollisionJNI.delete_btHashMapInternalShortBtHashIntBtTriangleInfo(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void insert(btHashInt bthashint, btTriangleInfo bttriangleinfo) {
        CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_insert(this.swigCPtr, this, btHashInt.getCPtr(bthashint), bthashint, btTriangleInfo.getCPtr(bttriangleinfo), bttriangleinfo);
    }

    public void remove(btHashInt bthashint) {
        CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_remove(this.swigCPtr, this, btHashInt.getCPtr(bthashint), bthashint);
    }

    public int size() {
        return CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_size(this.swigCPtr, this);
    }

    public btTriangleInfo getAtIndexConst(int i) {
        long jBtHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndexConst = CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndexConst(this.swigCPtr, this, i);
        if (jBtHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndexConst == 0) {
            return null;
        }
        return new btTriangleInfo(jBtHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndexConst, false);
    }

    public btTriangleInfo getAtIndex(int i) {
        long jBtHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndex = CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndex(this.swigCPtr, this, i);
        if (jBtHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndex == 0) {
            return null;
        }
        return new btTriangleInfo(jBtHashMapInternalShortBtHashIntBtTriangleInfo_getAtIndex, false);
    }

    public btHashInt getKeyAtIndex(int i) {
        return new btHashInt(CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_getKeyAtIndex(this.swigCPtr, this, i), true);
    }

    public btHashInt getKeyAtIndexConst(int i) {
        return new btHashInt(CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_getKeyAtIndexConst(this.swigCPtr, this, i), true);
    }

    public btTriangleInfo operatorSubscript(btHashInt bthashint) {
        long jBtHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscript = CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscript(this.swigCPtr, this, btHashInt.getCPtr(bthashint), bthashint);
        if (jBtHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscript == 0) {
            return null;
        }
        return new btTriangleInfo(jBtHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscript, false);
    }

    public btTriangleInfo operatorSubscriptConst(btHashInt bthashint) {
        long jBtHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscriptConst = CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscriptConst(this.swigCPtr, this, btHashInt.getCPtr(bthashint), bthashint);
        if (jBtHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscriptConst == 0) {
            return null;
        }
        return new btTriangleInfo(jBtHashMapInternalShortBtHashIntBtTriangleInfo_operatorSubscriptConst, false);
    }

    public btTriangleInfo findConst(btHashInt bthashint) {
        long jBtHashMapInternalShortBtHashIntBtTriangleInfo_findConst = CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_findConst(this.swigCPtr, this, btHashInt.getCPtr(bthashint), bthashint);
        if (jBtHashMapInternalShortBtHashIntBtTriangleInfo_findConst == 0) {
            return null;
        }
        return new btTriangleInfo(jBtHashMapInternalShortBtHashIntBtTriangleInfo_findConst, false);
    }

    public btTriangleInfo find(btHashInt bthashint) {
        long jBtHashMapInternalShortBtHashIntBtTriangleInfo_find = CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_find(this.swigCPtr, this, btHashInt.getCPtr(bthashint), bthashint);
        if (jBtHashMapInternalShortBtHashIntBtTriangleInfo_find == 0) {
            return null;
        }
        return new btTriangleInfo(jBtHashMapInternalShortBtHashIntBtTriangleInfo_find, false);
    }

    public int findIndex(btHashInt bthashint) {
        return CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_findIndex(this.swigCPtr, this, btHashInt.getCPtr(bthashint), bthashint);
    }

    public void clear() {
        CollisionJNI.btHashMapInternalShortBtHashIntBtTriangleInfo_clear(this.swigCPtr, this);
    }

    public btHashMapInternalShortBtHashIntBtTriangleInfo() {
        this(CollisionJNI.new_btHashMapInternalShortBtHashIntBtTriangleInfo(), true);
    }
}
