package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class btSolveProjectedGaussSeidel extends btMLCPSolverInterface {
    private long swigCPtr;

    protected btSolveProjectedGaussSeidel(String str, long j, boolean z) {
        super(str, DynamicsJNI.btSolveProjectedGaussSeidel_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSolveProjectedGaussSeidel(long j, boolean z) {
        this("btSolveProjectedGaussSeidel", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMLCPSolverInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btSolveProjectedGaussSeidel_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSolveProjectedGaussSeidel btsolveprojectedgaussseidel) {
        if (btsolveprojectedgaussseidel == null) {
            return 0L;
        }
        return btsolveprojectedgaussseidel.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMLCPSolverInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMLCPSolverInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btSolveProjectedGaussSeidel(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setLeastSquaresResidualThreshold(float f) {
        DynamicsJNI.btSolveProjectedGaussSeidel_leastSquaresResidualThreshold_set(this.swigCPtr, this, f);
    }

    public float getLeastSquaresResidualThreshold() {
        return DynamicsJNI.btSolveProjectedGaussSeidel_leastSquaresResidualThreshold_get(this.swigCPtr, this);
    }

    public void setLeastSquaresResidual(float f) {
        DynamicsJNI.btSolveProjectedGaussSeidel_leastSquaresResidual_set(this.swigCPtr, this, f);
    }

    public float getLeastSquaresResidual() {
        return DynamicsJNI.btSolveProjectedGaussSeidel_leastSquaresResidual_get(this.swigCPtr, this);
    }

    public btSolveProjectedGaussSeidel() {
        this(DynamicsJNI.new_btSolveProjectedGaussSeidel(), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMLCPSolverInterface
    public boolean solveMLCP(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t2, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t3, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t, int i, boolean z) {
        return DynamicsJNI.btSolveProjectedGaussSeidel_solveMLCP__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t2), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t3), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t4), SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t), i, z);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMLCPSolverInterface
    public boolean solveMLCP(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t2, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t3, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t, int i) {
        return DynamicsJNI.btSolveProjectedGaussSeidel_solveMLCP__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t2), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t3), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t4), SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t), i);
    }
}
