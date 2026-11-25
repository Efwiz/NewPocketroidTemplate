package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btElement extends BulletBase {
    private long swigCPtr;

    protected btElement(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btElement(long j, boolean z) {
        this("btElement", j, z);
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

    public static long getCPtr(btElement btelement) {
        if (btelement == null) {
            return 0L;
        }
        return btelement.swigCPtr;
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
                CollisionJNI.delete_btElement(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setId(int i) {
        CollisionJNI.btElement_id_set(this.swigCPtr, this, i);
    }

    public int getId() {
        return CollisionJNI.btElement_id_get(this.swigCPtr, this);
    }

    public void setSz(int i) {
        CollisionJNI.btElement_sz_set(this.swigCPtr, this, i);
    }

    public int getSz() {
        return CollisionJNI.btElement_sz_get(this.swigCPtr, this);
    }

    public btElement() {
        this(CollisionJNI.new_btElement(), true);
    }
}
