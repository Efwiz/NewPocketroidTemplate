package com.badlogic.gdx.physics.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes8.dex */
public class BulletBase implements Disposable {
    private long cPointer;
    public final String className;
    protected boolean destroyed;
    private boolean disposed;
    private int refCount;
    protected boolean swigCMemOwn;

    protected BulletBase(String str, long j, boolean z) {
        this.className = str;
        this.swigCMemOwn = z;
        this.cPointer = j;
    }

    public void obtain() {
        this.refCount++;
    }

    public void release() {
        int i = this.refCount - 1;
        this.refCount = i;
        if (i > 0 || !Bullet.useRefCounting) {
            return;
        }
        dispose();
    }

    public boolean isObtained() {
        return this.refCount > 0;
    }

    protected void construct() {
        this.destroyed = false;
    }

    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCMemOwn = z;
        this.cPointer = j;
        construct();
    }

    public boolean equals(Object obj) {
        return (obj instanceof BulletBase) && ((BulletBase) obj).cPointer == this.cPointer;
    }

    public int hashCode() {
        return (int) this.cPointer;
    }

    public long getCPointer() {
        return this.cPointer;
    }

    public void takeOwnership() {
        this.swigCMemOwn = true;
    }

    public void releaseOwnership() {
        this.swigCMemOwn = false;
    }

    public boolean hasOwnership() {
        return this.swigCMemOwn;
    }

    protected void delete() {
        this.cPointer = 0L;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        if (this.refCount > 0 && Bullet.useRefCounting && Bullet.enableLogging) {
            Gdx.f82app.error("Bullet", "Disposing " + toString() + " while it still has " + this.refCount + " references.");
        }
        this.disposed = true;
        delete();
    }

    public boolean isDisposed() {
        return this.disposed;
    }

    public String toString() {
        return this.className + "(" + this.cPointer + "," + this.swigCMemOwn + Constant.AFTER_QUTO;
    }

    protected void destroy() {
        try {
            if (this.destroyed && Bullet.enableLogging) {
                Gdx.f82app.error("Bullet", "Already destroyed " + toString());
            }
            this.destroyed = true;
            if (!this.swigCMemOwn || this.disposed) {
                return;
            }
            if (Bullet.enableLogging) {
                Gdx.f82app.error("Bullet", "Disposing " + toString() + " due to garbage collection.");
            }
            dispose();
        } catch (Throwable th) {
            Gdx.f82app.error("Bullet", "Exception while destroying " + toString(), th);
        }
    }

    protected void finalize() throws Throwable {
        if (!this.destroyed && Bullet.enableLogging) {
            Gdx.f82app.error("Bullet", "The " + this.className + " class does not override the finalize method.");
        }
        super.finalize();
    }
}
