package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btPointerUid extends BulletBase {
    private long swigCPtr;

    protected btPointerUid(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPointerUid(long j, boolean z) {
        this("btPointerUid", j, z);
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

    public static long getCPtr(btPointerUid btpointeruid) {
        if (btpointeruid == null) {
            return 0L;
        }
        return btpointeruid.swigCPtr;
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
                LinearMathJNI.delete_btPointerUid(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPtr(long j) {
        LinearMathJNI.btPointerUid_ptr_set(this.swigCPtr, this, j);
    }

    public long getPtr() {
        return LinearMathJNI.btPointerUid_ptr_get(this.swigCPtr, this);
    }

    public void setUniqueIds(int[] iArr) {
        LinearMathJNI.btPointerUid_uniqueIds_set(this.swigCPtr, this, iArr);
    }

    public int[] getUniqueIds() {
        return LinearMathJNI.btPointerUid_uniqueIds_get(this.swigCPtr, this);
    }

    public btPointerUid() {
        this(LinearMathJNI.new_btPointerUid(), true);
    }
}
