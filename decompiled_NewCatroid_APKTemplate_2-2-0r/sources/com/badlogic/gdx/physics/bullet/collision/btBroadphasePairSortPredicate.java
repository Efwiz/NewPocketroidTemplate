package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btBroadphasePairSortPredicate extends BulletBase {
    private long swigCPtr;

    protected btBroadphasePairSortPredicate(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBroadphasePairSortPredicate(long j, boolean z) {
        this("btBroadphasePairSortPredicate", j, z);
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

    public static long getCPtr(btBroadphasePairSortPredicate btbroadphasepairsortpredicate) {
        if (btbroadphasepairsortpredicate == null) {
            return 0L;
        }
        return btbroadphasepairsortpredicate.swigCPtr;
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
                CollisionJNI.delete_btBroadphasePairSortPredicate(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public boolean operatorFunctionCall(btBroadphasePair btbroadphasepair, btBroadphasePair btbroadphasepair2) {
        return CollisionJNI.btBroadphasePairSortPredicate_operatorFunctionCall(this.swigCPtr, this, btbroadphasepair, btbroadphasepair2);
    }

    public btBroadphasePairSortPredicate() {
        this(CollisionJNI.new_btBroadphasePairSortPredicate(), true);
    }
}
