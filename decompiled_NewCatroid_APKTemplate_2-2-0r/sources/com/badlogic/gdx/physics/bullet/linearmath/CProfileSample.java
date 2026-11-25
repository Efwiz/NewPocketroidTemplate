package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class CProfileSample extends BulletBase {
    private long swigCPtr;

    protected CProfileSample(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public CProfileSample(long j, boolean z) {
        this("CProfileSample", j, z);
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

    public static long getCPtr(CProfileSample cProfileSample) {
        if (cProfileSample == null) {
            return 0L;
        }
        return cProfileSample.swigCPtr;
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
                LinearMathJNI.delete_CProfileSample(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public CProfileSample(String str) {
        this(LinearMathJNI.new_CProfileSample(str), true);
    }
}
