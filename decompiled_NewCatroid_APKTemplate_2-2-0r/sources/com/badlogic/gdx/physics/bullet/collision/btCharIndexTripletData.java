package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btCharIndexTripletData extends BulletBase {
    private long swigCPtr;

    protected btCharIndexTripletData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCharIndexTripletData(long j, boolean z) {
        this("btCharIndexTripletData", j, z);
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

    public static long getCPtr(btCharIndexTripletData btcharindextripletdata) {
        if (btcharindextripletdata == null) {
            return 0L;
        }
        return btcharindextripletdata.swigCPtr;
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
                CollisionJNI.delete_btCharIndexTripletData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setValues(short[] sArr) {
        CollisionJNI.btCharIndexTripletData_values_set(this.swigCPtr, this, sArr);
    }

    public short[] getValues() {
        return CollisionJNI.btCharIndexTripletData_values_get(this.swigCPtr, this);
    }

    public void setPad(char c) {
        CollisionJNI.btCharIndexTripletData_pad_set(this.swigCPtr, this, c);
    }

    public char getPad() {
        return CollisionJNI.btCharIndexTripletData_pad_get(this.swigCPtr, this);
    }

    public btCharIndexTripletData() {
        this(CollisionJNI.new_btCharIndexTripletData(), true);
    }
}
