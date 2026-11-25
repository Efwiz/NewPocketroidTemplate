package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btShortIntIndexTripletData extends BulletBase {
    private long swigCPtr;

    protected btShortIntIndexTripletData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btShortIntIndexTripletData(long j, boolean z) {
        this("btShortIntIndexTripletData", j, z);
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

    public static long getCPtr(btShortIntIndexTripletData btshortintindextripletdata) {
        if (btshortintindextripletdata == null) {
            return 0L;
        }
        return btshortintindextripletdata.swigCPtr;
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
                CollisionJNI.delete_btShortIntIndexTripletData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setValues(short[] sArr) {
        CollisionJNI.btShortIntIndexTripletData_values_set(this.swigCPtr, this, sArr);
    }

    public short[] getValues() {
        return CollisionJNI.btShortIntIndexTripletData_values_get(this.swigCPtr, this);
    }

    public void setPad(String str) {
        CollisionJNI.btShortIntIndexTripletData_pad_set(this.swigCPtr, this, str);
    }

    public String getPad() {
        return CollisionJNI.btShortIntIndexTripletData_pad_get(this.swigCPtr, this);
    }

    public btShortIntIndexTripletData() {
        this(CollisionJNI.new_btShortIntIndexTripletData(), true);
    }
}
