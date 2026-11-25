package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectWrapper;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBodySolver extends BulletBase {
    private long swigCPtr;

    public static final class SolverTypes {
        public static final int CL_SIMD_SOLVER = 3;
        public static final int CL_SOLVER = 2;
        public static final int CPU_SOLVER = 1;
        public static final int DEFAULT_SOLVER = 0;
        public static final int DX_SIMD_SOLVER = 5;
        public static final int DX_SOLVER = 4;
    }

    protected btSoftBodySolver(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSoftBodySolver(long j, boolean z) {
        this("btSoftBodySolver", j, z);
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

    public static long getCPtr(btSoftBodySolver btsoftbodysolver) {
        if (btsoftbodysolver == null) {
            return 0L;
        }
        return btsoftbodysolver.swigCPtr;
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
                SoftbodyJNI.delete_btSoftBodySolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public int getSolverType() {
        return SoftbodyJNI.btSoftBodySolver_getSolverType(this.swigCPtr, this);
    }

    public boolean checkInitialized() {
        return SoftbodyJNI.btSoftBodySolver_checkInitialized(this.swigCPtr, this);
    }

    public void optimize(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t, boolean z) {
        SoftbodyJNI.btSoftBodySolver_optimize__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t), z);
    }

    public void optimize(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t) {
        SoftbodyJNI.btSoftBodySolver_optimize__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t));
    }

    public void copyBackToSoftBodies(boolean z) {
        SoftbodyJNI.btSoftBodySolver_copyBackToSoftBodies__SWIG_0(this.swigCPtr, this, z);
    }

    public void copyBackToSoftBodies() {
        SoftbodyJNI.btSoftBodySolver_copyBackToSoftBodies__SWIG_1(this.swigCPtr, this);
    }

    public void predictMotion(float f) {
        SoftbodyJNI.btSoftBodySolver_predictMotion(this.swigCPtr, this, f);
    }

    public void solveConstraints(float f) {
        SoftbodyJNI.btSoftBodySolver_solveConstraints(this.swigCPtr, this, f);
    }

    public void updateSoftBodies() {
        SoftbodyJNI.btSoftBodySolver_updateSoftBodies(this.swigCPtr, this);
    }

    public void processCollision(btSoftBody btsoftbody, btCollisionObjectWrapper btcollisionobjectwrapper) {
        SoftbodyJNI.btSoftBodySolver_processCollision__SWIG_0(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper);
    }

    public void processCollision(btSoftBody btsoftbody, btSoftBody btsoftbody2) {
        SoftbodyJNI.btSoftBodySolver_processCollision__SWIG_1(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, btSoftBody.getCPtr(btsoftbody2), btsoftbody2);
    }

    public void setNumberOfPositionIterations(int i) {
        SoftbodyJNI.btSoftBodySolver_setNumberOfPositionIterations(this.swigCPtr, this, i);
    }

    public int getNumberOfPositionIterations() {
        return SoftbodyJNI.btSoftBodySolver_getNumberOfPositionIterations(this.swigCPtr, this);
    }

    public void setNumberOfVelocityIterations(int i) {
        SoftbodyJNI.btSoftBodySolver_setNumberOfVelocityIterations(this.swigCPtr, this, i);
    }

    public int getNumberOfVelocityIterations() {
        return SoftbodyJNI.btSoftBodySolver_getNumberOfVelocityIterations(this.swigCPtr, this);
    }

    public float getTimeScale() {
        return SoftbodyJNI.btSoftBodySolver_getTimeScale(this.swigCPtr, this);
    }
}
