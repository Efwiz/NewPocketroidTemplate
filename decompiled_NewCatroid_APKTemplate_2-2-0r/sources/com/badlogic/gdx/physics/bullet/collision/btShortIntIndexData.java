package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btShortIntIndexData extends BulletBase {
    private long swigCPtr;

    protected btShortIntIndexData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btShortIntIndexData(long j, boolean z) {
        this("btShortIntIndexData", j, z);
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

    public static long getCPtr(btShortIntIndexData btshortintindexdata) {
        if (btshortintindexdata == null) {
            return 0L;
        }
        return btshortintindexdata.swigCPtr;
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
                CollisionJNI.delete_btShortIntIndexData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setValue(short s) {
        CollisionJNI.btShortIntIndexData_value_set(this.swigCPtr, this, s);
    }

    public short getValue() {
        return CollisionJNI.btShortIntIndexData_value_get(this.swigCPtr, this);
    }

    public void setPad(String str) {
        CollisionJNI.btShortIntIndexData_pad_set(this.swigCPtr, this, str);
    }

    public String getPad() {
        return CollisionJNI.btShortIntIndexData_pad_get(this.swigCPtr, this);
    }

    public btShortIntIndexData() {
        this(CollisionJNI.new_btShortIntIndexData(), true);
    }
}
