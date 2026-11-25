package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btSubSimplexClosestResult extends BulletBase {
    private long swigCPtr;

    protected btSubSimplexClosestResult(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSubSimplexClosestResult(long j, boolean z) {
        this("btSubSimplexClosestResult", j, z);
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

    public static long getCPtr(btSubSimplexClosestResult btsubsimplexclosestresult) {
        if (btsubsimplexclosestresult == null) {
            return 0L;
        }
        return btsubsimplexclosestresult.swigCPtr;
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
                CollisionJNI.delete_btSubSimplexClosestResult(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setClosestPointOnSimplex(btVector3 btvector3) {
        CollisionJNI.btSubSimplexClosestResult_closestPointOnSimplex_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getClosestPointOnSimplex() {
        long jBtSubSimplexClosestResult_closestPointOnSimplex_get = CollisionJNI.btSubSimplexClosestResult_closestPointOnSimplex_get(this.swigCPtr, this);
        if (jBtSubSimplexClosestResult_closestPointOnSimplex_get == 0) {
            return null;
        }
        return new btVector3(jBtSubSimplexClosestResult_closestPointOnSimplex_get, false);
    }

    public void setUsedVertices(btUsageBitfield btusagebitfield) {
        CollisionJNI.btSubSimplexClosestResult_usedVertices_set(this.swigCPtr, this, btUsageBitfield.getCPtr(btusagebitfield), btusagebitfield);
    }

    public btUsageBitfield getUsedVertices() {
        long jBtSubSimplexClosestResult_usedVertices_get = CollisionJNI.btSubSimplexClosestResult_usedVertices_get(this.swigCPtr, this);
        if (jBtSubSimplexClosestResult_usedVertices_get == 0) {
            return null;
        }
        return new btUsageBitfield(jBtSubSimplexClosestResult_usedVertices_get, false);
    }

    public void setBarycentricCoords(float[] fArr) {
        CollisionJNI.btSubSimplexClosestResult_barycentricCoords_set(this.swigCPtr, this, fArr);
    }

    public float[] getBarycentricCoords() {
        return CollisionJNI.btSubSimplexClosestResult_barycentricCoords_get(this.swigCPtr, this);
    }

    public void setDegenerate(boolean z) {
        CollisionJNI.btSubSimplexClosestResult_degenerate_set(this.swigCPtr, this, z);
    }

    public boolean getDegenerate() {
        return CollisionJNI.btSubSimplexClosestResult_degenerate_get(this.swigCPtr, this);
    }

    public void reset() {
        CollisionJNI.btSubSimplexClosestResult_reset(this.swigCPtr, this);
    }

    public boolean isValid() {
        return CollisionJNI.btSubSimplexClosestResult_isValid(this.swigCPtr, this);
    }

    public void setBarycentricCoordinates(float f, float f2, float f3, float f4) {
        CollisionJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_0(this.swigCPtr, this, f, f2, f3, f4);
    }

    public void setBarycentricCoordinates(float f, float f2, float f3) {
        CollisionJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_1(this.swigCPtr, this, f, f2, f3);
    }

    public void setBarycentricCoordinates(float f, float f2) {
        CollisionJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_2(this.swigCPtr, this, f, f2);
    }

    public void setBarycentricCoordinates(float f) {
        CollisionJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_3(this.swigCPtr, this, f);
    }

    public void setBarycentricCoordinates() {
        CollisionJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_4(this.swigCPtr, this);
    }

    public btSubSimplexClosestResult() {
        this(CollisionJNI.new_btSubSimplexClosestResult(), true);
    }
}
