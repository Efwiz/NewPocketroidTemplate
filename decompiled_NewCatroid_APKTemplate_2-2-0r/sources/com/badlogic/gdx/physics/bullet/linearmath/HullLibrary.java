package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class HullLibrary extends BulletBase {
    private long swigCPtr;

    protected HullLibrary(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public HullLibrary(long j, boolean z) {
        this("HullLibrary", j, z);
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

    public static long getCPtr(HullLibrary hullLibrary) {
        if (hullLibrary == null) {
            return 0L;
        }
        return hullLibrary.swigCPtr;
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
                LinearMathJNI.delete_HullLibrary(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setVertexIndexMapping(SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        LinearMathJNI.HullLibrary_vertexIndexMapping_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_int_t getVertexIndexMapping() {
        long jHullLibrary_vertexIndexMapping_get = LinearMathJNI.HullLibrary_vertexIndexMapping_get(this.swigCPtr, this);
        if (jHullLibrary_vertexIndexMapping_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_int_t(jHullLibrary_vertexIndexMapping_get, false);
    }

    public int CreateConvexHull(HullDesc hullDesc, HullResult hullResult) {
        return LinearMathJNI.HullLibrary_CreateConvexHull(this.swigCPtr, this, HullDesc.getCPtr(hullDesc), hullDesc, HullResult.getCPtr(hullResult), hullResult);
    }

    public int ReleaseResult(HullResult hullResult) {
        return LinearMathJNI.HullLibrary_ReleaseResult(this.swigCPtr, this, HullResult.getCPtr(hullResult), hullResult);
    }

    public HullLibrary() {
        this(LinearMathJNI.new_HullLibrary(), true);
    }
}
