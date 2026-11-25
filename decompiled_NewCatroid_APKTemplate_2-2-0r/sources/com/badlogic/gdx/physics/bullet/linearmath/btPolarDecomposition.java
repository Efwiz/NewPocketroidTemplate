package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btPolarDecomposition extends BulletBase {
    private long swigCPtr;

    protected btPolarDecomposition(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPolarDecomposition(long j, boolean z) {
        this("btPolarDecomposition", j, z);
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

    public static long getCPtr(btPolarDecomposition btpolardecomposition) {
        if (btpolardecomposition == null) {
            return 0L;
        }
        return btpolardecomposition.swigCPtr;
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
                LinearMathJNI.delete_btPolarDecomposition(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btPolarDecomposition(float f, long j) {
        this(LinearMathJNI.new_btPolarDecomposition__SWIG_0(f, j), true);
    }

    public btPolarDecomposition(float f) {
        this(LinearMathJNI.new_btPolarDecomposition__SWIG_1(f), true);
    }

    public btPolarDecomposition() {
        this(LinearMathJNI.new_btPolarDecomposition__SWIG_2(), true);
    }

    public long decompose(Matrix3 matrix3, Matrix3 matrix4, Matrix3 matrix5) {
        return LinearMathJNI.btPolarDecomposition_decompose(this.swigCPtr, this, matrix3, matrix4, matrix5);
    }

    public long maxIterations() {
        return LinearMathJNI.btPolarDecomposition_maxIterations(this.swigCPtr, this);
    }
}
