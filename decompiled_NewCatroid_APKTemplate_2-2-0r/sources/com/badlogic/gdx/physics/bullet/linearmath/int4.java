package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class int4 extends BulletBase {
    private long swigCPtr;

    protected int4(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public int4(long j, boolean z) {
        this("int4", j, z);
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

    public static long getCPtr(int4 int4Var) {
        if (int4Var == null) {
            return 0L;
        }
        return int4Var.swigCPtr;
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
                LinearMathJNI.delete_int4(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setX(int i) {
        LinearMathJNI.int4_x_set(this.swigCPtr, this, i);
    }

    public int getX() {
        return LinearMathJNI.int4_x_get(this.swigCPtr, this);
    }

    public void setY(int i) {
        LinearMathJNI.int4_y_set(this.swigCPtr, this, i);
    }

    public int getY() {
        return LinearMathJNI.int4_y_get(this.swigCPtr, this);
    }

    public void setZ(int i) {
        LinearMathJNI.int4_z_set(this.swigCPtr, this, i);
    }

    public int getZ() {
        return LinearMathJNI.int4_z_get(this.swigCPtr, this);
    }

    public void setW(int i) {
        LinearMathJNI.int4_w_set(this.swigCPtr, this, i);
    }

    public int getW() {
        return LinearMathJNI.int4_w_get(this.swigCPtr, this);
    }

    public int4() {
        this(LinearMathJNI.new_int4__SWIG_0(), true);
    }

    public int4(int i, int i2, int i3, int i4) {
        this(LinearMathJNI.new_int4__SWIG_1(i, i2, i3, i4), true);
    }

    public int operatorSubscriptConst(int i) {
        return LinearMathJNI.int4_operatorSubscriptConst(this.swigCPtr, this, i);
    }

    public SWIGTYPE_p_int operatorSubscript(int i) {
        return new SWIGTYPE_p_int(LinearMathJNI.int4_operatorSubscript(this.swigCPtr, this, i), false);
    }
}
