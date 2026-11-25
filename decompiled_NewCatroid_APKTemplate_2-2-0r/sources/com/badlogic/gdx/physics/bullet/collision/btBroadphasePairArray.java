package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.utils.Array;

/* JADX INFO: loaded from: classes8.dex */
public class btBroadphasePairArray extends BulletBase {
    private long swigCPtr;

    protected btBroadphasePairArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBroadphasePairArray(long j, boolean z) {
        this("btBroadphasePairArray", j, z);
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

    public static long getCPtr(btBroadphasePairArray btbroadphasepairarray) {
        if (btbroadphasepairarray == null) {
            return 0L;
        }
        return btbroadphasepairarray.swigCPtr;
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
                CollisionJNI.delete_btBroadphasePairArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public Array<btCollisionObject> getCollisionObjects(Array<btCollisionObject> array, btCollisionObject btcollisionobject, int[] iArr) {
        int collisionObjects = getCollisionObjects(iArr, iArr.length, (int) btCollisionObject.getCPtr(btcollisionobject));
        for (int i = 0; i < collisionObjects; i++) {
            array.add(btCollisionObject.getInstance(iArr[i], false));
        }
        return array;
    }

    public int getCollisionObjectsValue(int[] iArr, btCollisionObject btcollisionobject) {
        return getCollisionObjectsValue(iArr, iArr.length, (int) btCollisionObject.getCPtr(btcollisionobject));
    }

    public int size() {
        return CollisionJNI.btBroadphasePairArray_size(this.swigCPtr, this);
    }

    public btBroadphasePair at(int i) {
        return btBroadphasePair.internalTemp(CollisionJNI.btBroadphasePairArray_at(this.swigCPtr, this, i), false);
    }

    public int getCollisionObjects(int[] iArr, int i, int i2) {
        return CollisionJNI.btBroadphasePairArray_getCollisionObjects(this.swigCPtr, this, iArr, i, i2);
    }

    public int getCollisionObjectsValue(int[] iArr, int i, int i2) {
        return CollisionJNI.btBroadphasePairArray_getCollisionObjectsValue(this.swigCPtr, this, iArr, i, i2);
    }

    public btBroadphasePairArray() {
        this(CollisionJNI.new_btBroadphasePairArray(), true);
    }
}
