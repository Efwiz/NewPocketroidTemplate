package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btIntIndexData extends BulletBase {
    private long swigCPtr;

    protected btIntIndexData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btIntIndexData(long j, boolean z) {
        this("btIntIndexData", j, z);
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

    public static long getCPtr(btIntIndexData btintindexdata) {
        if (btintindexdata == null) {
            return 0L;
        }
        return btintindexdata.swigCPtr;
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
                CollisionJNI.delete_btIntIndexData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setValue(int i) {
        CollisionJNI.btIntIndexData_value_set(this.swigCPtr, this, i);
    }

    public int getValue() {
        return CollisionJNI.btIntIndexData_value_get(this.swigCPtr, this);
    }

    public btIntIndexData() {
        this(CollisionJNI.new_btIntIndexData(), true);
    }
}
