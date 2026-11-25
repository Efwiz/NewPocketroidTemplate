package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btTypedObject extends BulletBase {
    private long swigCPtr;

    protected btTypedObject(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btTypedObject(long j, boolean z) {
        this("btTypedObject", j, z);
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

    public static long getCPtr(btTypedObject bttypedobject) {
        if (bttypedobject == null) {
            return 0L;
        }
        return bttypedobject.swigCPtr;
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
                LinearMathJNI.delete_btTypedObject(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btTypedObject(int i) {
        this(LinearMathJNI.new_btTypedObject(i), true);
    }

    public void setObjectType(int i) {
        LinearMathJNI.btTypedObject_objectType_set(this.swigCPtr, this, i);
    }

    public int getObjectType() {
        return LinearMathJNI.btTypedObject_objectType_get(this.swigCPtr, this);
    }
}
