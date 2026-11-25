package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btMLCPSolverInterface extends BulletBase {
    private long swigCPtr;

    protected btMLCPSolverInterface(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMLCPSolverInterface(long j, boolean z) {
        this("btMLCPSolverInterface", j, z);
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

    public static long getCPtr(btMLCPSolverInterface btmlcpsolverinterface) {
        if (btmlcpsolverinterface == null) {
            return 0L;
        }
        return btmlcpsolverinterface.swigCPtr;
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
                DynamicsJNI.delete_btMLCPSolverInterface(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public boolean solveMLCP(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t2, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t3, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t, int i, boolean z) {
        return DynamicsJNI.btMLCPSolverInterface_solveMLCP__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t2), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t3), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t4), SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t), i, z);
    }

    public boolean solveMLCP(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t2, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t3, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t, int i) {
        return DynamicsJNI.btMLCPSolverInterface_solveMLCP__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t2), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t3), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t4), SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t), i);
    }
}
