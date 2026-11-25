package com.badlogic.gdx.physics.bullet.linearmath;

/* JADX INFO: loaded from: classes8.dex */
public class btVector4 extends btVector3 {
    private long swigCPtr;

    protected btVector4(String str, long j, boolean z) {
        super(str, LinearMathJNI.btVector4_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btVector4(long j, boolean z) {
        this("btVector4", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btVector3, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(LinearMathJNI.btVector4_SWIGUpcast(j), z);
    }

    public static long getCPtr(btVector4 btvector4) {
        if (btvector4 == null) {
            return 0L;
        }
        return btvector4.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btVector3, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btVector3, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                LinearMathJNI.delete_btVector4(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btVector4() {
        this(LinearMathJNI.new_btVector4__SWIG_0(), true);
    }

    public btVector4(float f, float f2, float f3, float f4) {
        this(LinearMathJNI.new_btVector4__SWIG_1(f, f2, f3, f4), true);
    }

    public btVector4 absolute4() {
        return new btVector4(LinearMathJNI.btVector4_absolute4(this.swigCPtr, this), true);
    }

    public float getW() {
        return LinearMathJNI.btVector4_getW(this.swigCPtr, this);
    }

    public int maxAxis4() {
        return LinearMathJNI.btVector4_maxAxis4(this.swigCPtr, this);
    }

    public int minAxis4() {
        return LinearMathJNI.btVector4_minAxis4(this.swigCPtr, this);
    }

    public int closestAxis4() {
        return LinearMathJNI.btVector4_closestAxis4(this.swigCPtr, this);
    }

    public void setValue(float f, float f2, float f3, float f4) {
        LinearMathJNI.btVector4_setValue(this.swigCPtr, this, f, f2, f3, f4);
    }
}
