package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class btLemkeSolver extends btMLCPSolverInterface {
    private long swigCPtr;

    protected btLemkeSolver(String str, long j, boolean z) {
        super(str, DynamicsJNI.btLemkeSolver_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btLemkeSolver(long j, boolean z) {
        this("btLemkeSolver", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMLCPSolverInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btLemkeSolver_SWIGUpcast(j), z);
    }

    public static long getCPtr(btLemkeSolver btlemkesolver) {
        if (btlemkesolver == null) {
            return 0L;
        }
        return btlemkesolver.swigCPtr;
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
                DynamicsJNI.delete_btLemkeSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setMaxValue(float f) {
        DynamicsJNI.btLemkeSolver_maxValue_set(this.swigCPtr, this, f);
    }

    public float getMaxValue() {
        return DynamicsJNI.btLemkeSolver_maxValue_get(this.swigCPtr, this);
    }

    public void setDebugLevel(int i) {
        DynamicsJNI.btLemkeSolver_debugLevel_set(this.swigCPtr, this, i);
    }

    public int getDebugLevel() {
        return DynamicsJNI.btLemkeSolver_debugLevel_get(this.swigCPtr, this);
    }

    public void setMaxLoops(int i) {
        DynamicsJNI.btLemkeSolver_maxLoops_set(this.swigCPtr, this, i);
    }

    public int getMaxLoops() {
        return DynamicsJNI.btLemkeSolver_maxLoops_get(this.swigCPtr, this);
    }

    public void setUseLoHighBounds(boolean z) {
        DynamicsJNI.btLemkeSolver_useLoHighBounds_set(this.swigCPtr, this, z);
    }

    public boolean getUseLoHighBounds() {
        return DynamicsJNI.btLemkeSolver_useLoHighBounds_get(this.swigCPtr, this);
    }

    public btLemkeSolver() {
        this(DynamicsJNI.new_btLemkeSolver(), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMLCPSolverInterface
    public boolean solveMLCP(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t2, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t3, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t, int i, boolean z) {
        return DynamicsJNI.btLemkeSolver_solveMLCP__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t2), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t3), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t4), SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t), i, z);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMLCPSolverInterface
    public boolean solveMLCP(SWIGTYPE_p_btMatrixXT_float_t sWIGTYPE_p_btMatrixXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t2, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t3, SWIGTYPE_p_btVectorXT_float_t sWIGTYPE_p_btVectorXT_float_t4, SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t, int i) {
        return DynamicsJNI.btLemkeSolver_solveMLCP__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_btMatrixXT_float_t.getCPtr(sWIGTYPE_p_btMatrixXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t2), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t3), SWIGTYPE_p_btVectorXT_float_t.getCPtr(sWIGTYPE_p_btVectorXT_float_t4), SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t), i);
    }
}
