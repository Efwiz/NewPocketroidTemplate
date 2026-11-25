package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btHashString extends BulletBase {
    private long swigCPtr;

    protected btHashString(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btHashString(long j, boolean z) {
        this("btHashString", j, z);
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

    public static long getCPtr(btHashString bthashstring) {
        if (bthashstring == null) {
            return 0L;
        }
        return bthashstring.swigCPtr;
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
                LinearMathJNI.delete_btHashString(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public String getString() {
        return LinearMathJNI.btHashString_string_get(this.swigCPtr, this);
    }

    public void setHash(long j) {
        LinearMathJNI.btHashString_hash_set(this.swigCPtr, this, j);
    }

    public long getHash() {
        return LinearMathJNI.btHashString_hash_get(this.swigCPtr, this);
    }

    public btHashString(String str) {
        this(LinearMathJNI.new_btHashString(str), true);
    }

    public int portableStringCompare(String str, String str2) {
        return LinearMathJNI.btHashString_portableStringCompare(this.swigCPtr, this, str, str2);
    }

    public boolean equals(btHashString bthashstring) {
        return LinearMathJNI.btHashString_equals(this.swigCPtr, this, getCPtr(bthashstring), bthashstring);
    }
}
