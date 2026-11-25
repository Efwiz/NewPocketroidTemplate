package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class HullResult extends BulletBase {
    private long swigCPtr;

    protected HullResult(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public HullResult(long j, boolean z) {
        this("HullResult", j, z);
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

    public static long getCPtr(HullResult hullResult) {
        if (hullResult == null) {
            return 0L;
        }
        return hullResult.swigCPtr;
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
                LinearMathJNI.delete_HullResult(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public HullResult() {
        this(LinearMathJNI.new_HullResult(), true);
    }

    public void setMPolygons(boolean z) {
        LinearMathJNI.HullResult_mPolygons_set(this.swigCPtr, this, z);
    }

    public boolean getMPolygons() {
        return LinearMathJNI.HullResult_mPolygons_get(this.swigCPtr, this);
    }

    public void setMNumOutputVertices(long j) {
        LinearMathJNI.HullResult_mNumOutputVertices_set(this.swigCPtr, this, j);
    }

    public long getMNumOutputVertices() {
        return LinearMathJNI.HullResult_mNumOutputVertices_get(this.swigCPtr, this);
    }

    public void setOutputVertices(btVector3Array btvector3array) {
        LinearMathJNI.HullResult_OutputVertices_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public btVector3Array getOutputVertices() {
        long jHullResult_OutputVertices_get = LinearMathJNI.HullResult_OutputVertices_get(this.swigCPtr, this);
        if (jHullResult_OutputVertices_get == 0) {
            return null;
        }
        return new btVector3Array(jHullResult_OutputVertices_get, false);
    }

    public void setMNumFaces(long j) {
        LinearMathJNI.HullResult_mNumFaces_set(this.swigCPtr, this, j);
    }

    public long getMNumFaces() {
        return LinearMathJNI.HullResult_mNumFaces_get(this.swigCPtr, this);
    }

    public void setMNumIndices(long j) {
        LinearMathJNI.HullResult_mNumIndices_set(this.swigCPtr, this, j);
    }

    public long getMNumIndices() {
        return LinearMathJNI.HullResult_mNumIndices_get(this.swigCPtr, this);
    }

    public void setIndices(SWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t sWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t) {
        LinearMathJNI.HullResult_Indices_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t getIndices() {
        long jHullResult_Indices_get = LinearMathJNI.HullResult_Indices_get(this.swigCPtr, this);
        if (jHullResult_Indices_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t(jHullResult_Indices_get, false);
    }
}
