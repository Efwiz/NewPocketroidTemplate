package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btQuadWord extends BulletBase {
    private long swigCPtr;

    protected btQuadWord(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btQuadWord(long j, boolean z) {
        this("btQuadWord", j, z);
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

    public static long getCPtr(btQuadWord btquadword) {
        if (btquadword == null) {
            return 0L;
        }
        return btquadword.swigCPtr;
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
                LinearMathJNI.delete_btQuadWord(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public float getX() {
        return LinearMathJNI.btQuadWord_getX(this.swigCPtr, this);
    }

    public float getY() {
        return LinearMathJNI.btQuadWord_getY(this.swigCPtr, this);
    }

    public float getZ() {
        return LinearMathJNI.btQuadWord_getZ(this.swigCPtr, this);
    }

    public void setX(float f) {
        LinearMathJNI.btQuadWord_setX(this.swigCPtr, this, f);
    }

    public void setY(float f) {
        LinearMathJNI.btQuadWord_setY(this.swigCPtr, this, f);
    }

    public void setZ(float f) {
        LinearMathJNI.btQuadWord_setZ(this.swigCPtr, this, f);
    }

    public void setW(float f) {
        LinearMathJNI.btQuadWord_setW(this.swigCPtr, this, f);
    }

    public float x() {
        return LinearMathJNI.btQuadWord_x(this.swigCPtr, this);
    }

    public float y() {
        return LinearMathJNI.btQuadWord_y(this.swigCPtr, this);
    }

    public float z() {
        return LinearMathJNI.btQuadWord_z(this.swigCPtr, this);
    }

    public float w() {
        return LinearMathJNI.btQuadWord_w(this.swigCPtr, this);
    }

    public FloatBuffer operatorbtScalarPtr() {
        return LinearMathJNI.btQuadWord_operatorbtScalarPtr(this.swigCPtr, this);
    }

    public FloatBuffer operatorbtConstScalarPtr() {
        return LinearMathJNI.btQuadWord_operatorbtConstScalarPtr(this.swigCPtr, this);
    }

    public boolean operatorEqualTo(btQuadWord btquadword) {
        return LinearMathJNI.btQuadWord_operatorEqualTo(this.swigCPtr, this, getCPtr(btquadword), btquadword);
    }

    public boolean operatorNotEqualTo(btQuadWord btquadword) {
        return LinearMathJNI.btQuadWord_operatorNotEqualTo(this.swigCPtr, this, getCPtr(btquadword), btquadword);
    }

    public void setValue(float f, float f2, float f3) {
        LinearMathJNI.btQuadWord_setValue__SWIG_0(this.swigCPtr, this, f, f2, f3);
    }

    public void setValue(float f, float f2, float f3, float f4) {
        LinearMathJNI.btQuadWord_setValue__SWIG_1(this.swigCPtr, this, f, f2, f3, f4);
    }

    public btQuadWord() {
        this(LinearMathJNI.new_btQuadWord__SWIG_0(), true);
    }

    public btQuadWord(float f, float f2, float f3) {
        this(LinearMathJNI.new_btQuadWord__SWIG_1(f, f2, f3), true);
    }

    public btQuadWord(float f, float f2, float f3, float f4) {
        this(LinearMathJNI.new_btQuadWord__SWIG_2(f, f2, f3, f4), true);
    }

    public void setMax(btQuadWord btquadword) {
        LinearMathJNI.btQuadWord_setMax(this.swigCPtr, this, getCPtr(btquadword), btquadword);
    }

    public void setMin(btQuadWord btquadword) {
        LinearMathJNI.btQuadWord_setMin(this.swigCPtr, this, getCPtr(btquadword), btquadword);
    }
}
