package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class PHullResult extends BulletBase {
    private long swigCPtr;

    protected PHullResult(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public PHullResult(long j, boolean z) {
        this("PHullResult", j, z);
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

    public static long getCPtr(PHullResult pHullResult) {
        if (pHullResult == null) {
            return 0L;
        }
        return pHullResult.swigCPtr;
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
                LinearMathJNI.delete_PHullResult(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public PHullResult() {
        this(LinearMathJNI.new_PHullResult(), true);
    }

    public void setMVcount(long j) {
        LinearMathJNI.PHullResult_mVcount_set(this.swigCPtr, this, j);
    }

    public long getMVcount() {
        return LinearMathJNI.PHullResult_mVcount_get(this.swigCPtr, this);
    }

    public void setMIndexCount(long j) {
        LinearMathJNI.PHullResult_mIndexCount_set(this.swigCPtr, this, j);
    }

    public long getMIndexCount() {
        return LinearMathJNI.PHullResult_mIndexCount_get(this.swigCPtr, this);
    }

    public void setMFaceCount(long j) {
        LinearMathJNI.PHullResult_mFaceCount_set(this.swigCPtr, this, j);
    }

    public long getMFaceCount() {
        return LinearMathJNI.PHullResult_mFaceCount_get(this.swigCPtr, this);
    }

    public void setMVertices(btVector3 btvector3) {
        LinearMathJNI.PHullResult_mVertices_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMVertices() {
        long jPHullResult_mVertices_get = LinearMathJNI.PHullResult_mVertices_get(this.swigCPtr, this);
        if (jPHullResult_mVertices_get == 0) {
            return null;
        }
        return new btVector3(jPHullResult_mVertices_get, false);
    }

    public void setIndices(SWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t sWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t) {
        LinearMathJNI.PHullResult_Indices_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t getIndices() {
        long jPHullResult_Indices_get = LinearMathJNI.PHullResult_Indices_get(this.swigCPtr, this);
        if (jPHullResult_Indices_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_unsigned_int_t(jPHullResult_Indices_get, false);
    }
}
