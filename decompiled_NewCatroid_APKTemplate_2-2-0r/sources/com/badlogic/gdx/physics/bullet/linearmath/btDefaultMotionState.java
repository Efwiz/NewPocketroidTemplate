package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix4;

/* JADX INFO: loaded from: classes8.dex */
public class btDefaultMotionState extends btMotionState {
    private long swigCPtr;

    protected btDefaultMotionState(String str, long j, boolean z) {
        super(str, LinearMathJNI.btDefaultMotionState_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDefaultMotionState(long j, boolean z) {
        this("btDefaultMotionState", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btMotionState, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(LinearMathJNI.btDefaultMotionState_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDefaultMotionState btdefaultmotionstate) {
        if (btdefaultmotionstate == null) {
            return 0L;
        }
        return btdefaultmotionstate.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btMotionState, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btMotionState, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                LinearMathJNI.delete_btDefaultMotionState(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setGraphicsWorldTrans(btTransform bttransform) {
        LinearMathJNI.btDefaultMotionState_graphicsWorldTrans_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getGraphicsWorldTrans() {
        long jBtDefaultMotionState_graphicsWorldTrans_get = LinearMathJNI.btDefaultMotionState_graphicsWorldTrans_get(this.swigCPtr, this);
        if (jBtDefaultMotionState_graphicsWorldTrans_get == 0) {
            return null;
        }
        return new btTransform(jBtDefaultMotionState_graphicsWorldTrans_get, false);
    }

    public void setCenterOfMassOffset(btTransform bttransform) {
        LinearMathJNI.btDefaultMotionState_centerOfMassOffset_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getCenterOfMassOffset() {
        long jBtDefaultMotionState_centerOfMassOffset_get = LinearMathJNI.btDefaultMotionState_centerOfMassOffset_get(this.swigCPtr, this);
        if (jBtDefaultMotionState_centerOfMassOffset_get == 0) {
            return null;
        }
        return new btTransform(jBtDefaultMotionState_centerOfMassOffset_get, false);
    }

    public void setStartWorldTrans(btTransform bttransform) {
        LinearMathJNI.btDefaultMotionState_startWorldTrans_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getStartWorldTrans() {
        long jBtDefaultMotionState_startWorldTrans_get = LinearMathJNI.btDefaultMotionState_startWorldTrans_get(this.swigCPtr, this);
        if (jBtDefaultMotionState_startWorldTrans_get == 0) {
            return null;
        }
        return new btTransform(jBtDefaultMotionState_startWorldTrans_get, false);
    }

    public void setUserPointer(long j) {
        LinearMathJNI.btDefaultMotionState_userPointer_set(this.swigCPtr, this, j);
    }

    public long getUserPointer() {
        return LinearMathJNI.btDefaultMotionState_userPointer_get(this.swigCPtr, this);
    }

    public long operatorNew(long j) {
        return LinearMathJNI.btDefaultMotionState_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        LinearMathJNI.btDefaultMotionState_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return LinearMathJNI.btDefaultMotionState_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        LinearMathJNI.btDefaultMotionState_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return LinearMathJNI.btDefaultMotionState_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        LinearMathJNI.btDefaultMotionState_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return LinearMathJNI.btDefaultMotionState_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        LinearMathJNI.btDefaultMotionState_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btDefaultMotionState(Matrix4 matrix4, Matrix4 matrix5) {
        this(LinearMathJNI.new_btDefaultMotionState__SWIG_0(matrix4, matrix5), true);
    }

    public btDefaultMotionState(Matrix4 matrix4) {
        this(LinearMathJNI.new_btDefaultMotionState__SWIG_1(matrix4), true);
    }

    public btDefaultMotionState() {
        this(LinearMathJNI.new_btDefaultMotionState__SWIG_2(), true);
    }

    public void getGraphicsWorldTrans(Matrix4 matrix4) {
        LinearMathJNI.btDefaultMotionState_getGraphicsWorldTrans(this.swigCPtr, this, matrix4);
    }

    public void getCenterOfMassOffset(Matrix4 matrix4) {
        LinearMathJNI.btDefaultMotionState_getCenterOfMassOffset(this.swigCPtr, this, matrix4);
    }

    public void getStartWorldTrans(Matrix4 matrix4) {
        LinearMathJNI.btDefaultMotionState_getStartWorldTrans(this.swigCPtr, this, matrix4);
    }
}
