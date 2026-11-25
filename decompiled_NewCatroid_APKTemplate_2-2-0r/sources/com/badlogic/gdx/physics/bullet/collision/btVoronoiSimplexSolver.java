package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btVoronoiSimplexSolver extends BulletBase {
    private long swigCPtr;

    protected btVoronoiSimplexSolver(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btVoronoiSimplexSolver(long j, boolean z) {
        this("btVoronoiSimplexSolver", j, z);
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

    public static long getCPtr(btVoronoiSimplexSolver btvoronoisimplexsolver) {
        if (btvoronoisimplexsolver == null) {
            return 0L;
        }
        return btvoronoisimplexsolver.swigCPtr;
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
                CollisionJNI.delete_btVoronoiSimplexSolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btVoronoiSimplexSolver_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btVoronoiSimplexSolver_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btVoronoiSimplexSolver_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btVoronoiSimplexSolver_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btVoronoiSimplexSolver_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btVoronoiSimplexSolver_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btVoronoiSimplexSolver_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btVoronoiSimplexSolver_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setNumVertices(int i) {
        CollisionJNI.btVoronoiSimplexSolver_numVertices_set(this.swigCPtr, this, i);
    }

    public int getNumVertices() {
        return CollisionJNI.btVoronoiSimplexSolver_numVertices_get(this.swigCPtr, this);
    }

    public void setSimplexVectorW(btVector3 btvector3) {
        CollisionJNI.btVoronoiSimplexSolver_simplexVectorW_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getSimplexVectorW() {
        long jBtVoronoiSimplexSolver_simplexVectorW_get = CollisionJNI.btVoronoiSimplexSolver_simplexVectorW_get(this.swigCPtr, this);
        if (jBtVoronoiSimplexSolver_simplexVectorW_get == 0) {
            return null;
        }
        return new btVector3(jBtVoronoiSimplexSolver_simplexVectorW_get, false);
    }

    public void setSimplexPointsP(btVector3 btvector3) {
        CollisionJNI.btVoronoiSimplexSolver_simplexPointsP_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getSimplexPointsP() {
        long jBtVoronoiSimplexSolver_simplexPointsP_get = CollisionJNI.btVoronoiSimplexSolver_simplexPointsP_get(this.swigCPtr, this);
        if (jBtVoronoiSimplexSolver_simplexPointsP_get == 0) {
            return null;
        }
        return new btVector3(jBtVoronoiSimplexSolver_simplexPointsP_get, false);
    }

    public void setSimplexPointsQ(btVector3 btvector3) {
        CollisionJNI.btVoronoiSimplexSolver_simplexPointsQ_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getSimplexPointsQ() {
        long jBtVoronoiSimplexSolver_simplexPointsQ_get = CollisionJNI.btVoronoiSimplexSolver_simplexPointsQ_get(this.swigCPtr, this);
        if (jBtVoronoiSimplexSolver_simplexPointsQ_get == 0) {
            return null;
        }
        return new btVector3(jBtVoronoiSimplexSolver_simplexPointsQ_get, false);
    }

    public void setCachedP1(btVector3 btvector3) {
        CollisionJNI.btVoronoiSimplexSolver_cachedP1_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getCachedP1() {
        long jBtVoronoiSimplexSolver_cachedP1_get = CollisionJNI.btVoronoiSimplexSolver_cachedP1_get(this.swigCPtr, this);
        if (jBtVoronoiSimplexSolver_cachedP1_get == 0) {
            return null;
        }
        return new btVector3(jBtVoronoiSimplexSolver_cachedP1_get, false);
    }

    public void setCachedP2(btVector3 btvector3) {
        CollisionJNI.btVoronoiSimplexSolver_cachedP2_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getCachedP2() {
        long jBtVoronoiSimplexSolver_cachedP2_get = CollisionJNI.btVoronoiSimplexSolver_cachedP2_get(this.swigCPtr, this);
        if (jBtVoronoiSimplexSolver_cachedP2_get == 0) {
            return null;
        }
        return new btVector3(jBtVoronoiSimplexSolver_cachedP2_get, false);
    }

    public void setCachedV(btVector3 btvector3) {
        CollisionJNI.btVoronoiSimplexSolver_cachedV_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getCachedV() {
        long jBtVoronoiSimplexSolver_cachedV_get = CollisionJNI.btVoronoiSimplexSolver_cachedV_get(this.swigCPtr, this);
        if (jBtVoronoiSimplexSolver_cachedV_get == 0) {
            return null;
        }
        return new btVector3(jBtVoronoiSimplexSolver_cachedV_get, false);
    }

    public void setLastW(btVector3 btvector3) {
        CollisionJNI.btVoronoiSimplexSolver_lastW_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getLastW() {
        long jBtVoronoiSimplexSolver_lastW_get = CollisionJNI.btVoronoiSimplexSolver_lastW_get(this.swigCPtr, this);
        if (jBtVoronoiSimplexSolver_lastW_get == 0) {
            return null;
        }
        return new btVector3(jBtVoronoiSimplexSolver_lastW_get, false);
    }

    public void setEqualVertexThreshold(float f) {
        CollisionJNI.btVoronoiSimplexSolver_equalVertexThreshold_set(this.swigCPtr, this, f);
    }

    public float getEqualVertexThreshold() {
        return CollisionJNI.btVoronoiSimplexSolver_equalVertexThreshold_get(this.swigCPtr, this);
    }

    public void setCachedValidClosest(boolean z) {
        CollisionJNI.btVoronoiSimplexSolver_cachedValidClosest_set(this.swigCPtr, this, z);
    }

    public boolean getCachedValidClosest() {
        return CollisionJNI.btVoronoiSimplexSolver_cachedValidClosest_get(this.swigCPtr, this);
    }

    public void setCachedBC(btSubSimplexClosestResult btsubsimplexclosestresult) {
        CollisionJNI.btVoronoiSimplexSolver_cachedBC_set(this.swigCPtr, this, btSubSimplexClosestResult.getCPtr(btsubsimplexclosestresult), btsubsimplexclosestresult);
    }

    public btSubSimplexClosestResult getCachedBC() {
        long jBtVoronoiSimplexSolver_cachedBC_get = CollisionJNI.btVoronoiSimplexSolver_cachedBC_get(this.swigCPtr, this);
        if (jBtVoronoiSimplexSolver_cachedBC_get == 0) {
            return null;
        }
        return new btSubSimplexClosestResult(jBtVoronoiSimplexSolver_cachedBC_get, false);
    }

    public void setNeedsUpdate(boolean z) {
        CollisionJNI.btVoronoiSimplexSolver_needsUpdate_set(this.swigCPtr, this, z);
    }

    public boolean getNeedsUpdate() {
        return CollisionJNI.btVoronoiSimplexSolver_needsUpdate_get(this.swigCPtr, this);
    }

    public void removeVertex(int i) {
        CollisionJNI.btVoronoiSimplexSolver_removeVertex(this.swigCPtr, this, i);
    }

    public void reduceVertices(btUsageBitfield btusagebitfield) {
        CollisionJNI.btVoronoiSimplexSolver_reduceVertices(this.swigCPtr, this, btUsageBitfield.getCPtr(btusagebitfield), btusagebitfield);
    }

    public boolean updateClosestVectorAndPoints() {
        return CollisionJNI.btVoronoiSimplexSolver_updateClosestVectorAndPoints(this.swigCPtr, this);
    }

    public boolean closestPtPointTetrahedron(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, btSubSimplexClosestResult btsubsimplexclosestresult) {
        return CollisionJNI.btVoronoiSimplexSolver_closestPtPointTetrahedron(this.swigCPtr, this, vector3, vector4, vector5, vector6, vector7, btSubSimplexClosestResult.getCPtr(btsubsimplexclosestresult), btsubsimplexclosestresult);
    }

    public int pointOutsideOfPlane(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7) {
        return CollisionJNI.btVoronoiSimplexSolver_pointOutsideOfPlane(this.swigCPtr, this, vector3, vector4, vector5, vector6, vector7);
    }

    public boolean closestPtPointTriangle(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, btSubSimplexClosestResult btsubsimplexclosestresult) {
        return CollisionJNI.btVoronoiSimplexSolver_closestPtPointTriangle(this.swigCPtr, this, vector3, vector4, vector5, vector6, btSubSimplexClosestResult.getCPtr(btsubsimplexclosestresult), btsubsimplexclosestresult);
    }

    public btVoronoiSimplexSolver() {
        this(CollisionJNI.new_btVoronoiSimplexSolver(), true);
    }

    public void reset() {
        CollisionJNI.btVoronoiSimplexSolver_reset(this.swigCPtr, this);
    }

    public void addVertex(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        CollisionJNI.btVoronoiSimplexSolver_addVertex(this.swigCPtr, this, vector3, vector4, vector5);
    }

    public boolean closest(Vector3 vector3) {
        return CollisionJNI.btVoronoiSimplexSolver_closest(this.swigCPtr, this, vector3);
    }

    public float maxVertex() {
        return CollisionJNI.btVoronoiSimplexSolver_maxVertex(this.swigCPtr, this);
    }

    public boolean fullSimplex() {
        return CollisionJNI.btVoronoiSimplexSolver_fullSimplex(this.swigCPtr, this);
    }

    public int getSimplex(btVector3 btvector3, btVector3 btvector4, btVector3 btvector5) {
        return CollisionJNI.btVoronoiSimplexSolver_getSimplex(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4, btVector3.getCPtr(btvector5), btvector5);
    }

    public boolean inSimplex(Vector3 vector3) {
        return CollisionJNI.btVoronoiSimplexSolver_inSimplex(this.swigCPtr, this, vector3);
    }

    public void backup_closest(Vector3 vector3) {
        CollisionJNI.btVoronoiSimplexSolver_backup_closest(this.swigCPtr, this, vector3);
    }

    public boolean emptySimplex() {
        return CollisionJNI.btVoronoiSimplexSolver_emptySimplex(this.swigCPtr, this);
    }

    public void compute_points(Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btVoronoiSimplexSolver_compute_points(this.swigCPtr, this, vector3, vector4);
    }

    public int m_numVerticesVar() {
        return CollisionJNI.btVoronoiSimplexSolver_m_numVerticesVar(this.swigCPtr, this);
    }
}
