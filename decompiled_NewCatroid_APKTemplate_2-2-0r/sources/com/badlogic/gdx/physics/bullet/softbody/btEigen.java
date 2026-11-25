package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btEigen extends BulletBase {
    private long swigCPtr;

    protected btEigen(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btEigen(long j, boolean z) {
        this("btEigen", j, z);
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

    public static long getCPtr(btEigen bteigen) {
        if (bteigen == null) {
            return 0L;
        }
        return bteigen.swigCPtr;
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
                SoftbodyJNI.delete_btEigen(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static int system(Matrix3 matrix3, btMatrix3x3 btmatrix3x3, btVector3 btvector3) {
        return SoftbodyJNI.btEigen_system__SWIG_0(matrix3, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3, btVector3.getCPtr(btvector3), btvector3);
    }

    public static int system(Matrix3 matrix3, btMatrix3x3 btmatrix3x3) {
        return SoftbodyJNI.btEigen_system__SWIG_1(matrix3, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
    }

    public btEigen() {
        this(SoftbodyJNI.new_btEigen(), true);
    }
}
