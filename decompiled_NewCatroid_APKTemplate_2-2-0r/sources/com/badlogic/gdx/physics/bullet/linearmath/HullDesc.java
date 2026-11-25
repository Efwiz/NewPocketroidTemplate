package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class HullDesc extends BulletBase {
    private long swigCPtr;

    protected HullDesc(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public HullDesc(long j, boolean z) {
        this("HullDesc", j, z);
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

    public static long getCPtr(HullDesc hullDesc) {
        if (hullDesc == null) {
            return 0L;
        }
        return hullDesc.swigCPtr;
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
                LinearMathJNI.delete_HullDesc(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public HullDesc() {
        this(LinearMathJNI.new_HullDesc__SWIG_0(), true);
    }

    public HullDesc(int i, long j, btVector3 btvector3, long j2) {
        this(LinearMathJNI.new_HullDesc__SWIG_1(i, j, btVector3.getCPtr(btvector3), btvector3, j2), true);
    }

    public HullDesc(int i, long j, btVector3 btvector3) {
        this(LinearMathJNI.new_HullDesc__SWIG_2(i, j, btVector3.getCPtr(btvector3), btvector3), true);
    }

    public boolean HasHullFlag(int i) {
        return LinearMathJNI.HullDesc_HasHullFlag(this.swigCPtr, this, i);
    }

    public void SetHullFlag(int i) {
        LinearMathJNI.HullDesc_SetHullFlag(this.swigCPtr, this, i);
    }

    public void ClearHullFlag(int i) {
        LinearMathJNI.HullDesc_ClearHullFlag(this.swigCPtr, this, i);
    }

    public void setMFlags(long j) {
        LinearMathJNI.HullDesc_mFlags_set(this.swigCPtr, this, j);
    }

    public long getMFlags() {
        return LinearMathJNI.HullDesc_mFlags_get(this.swigCPtr, this);
    }

    public void setMVcount(long j) {
        LinearMathJNI.HullDesc_mVcount_set(this.swigCPtr, this, j);
    }

    public long getMVcount() {
        return LinearMathJNI.HullDesc_mVcount_get(this.swigCPtr, this);
    }

    public void setMVertices(btVector3 btvector3) {
        LinearMathJNI.HullDesc_mVertices_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getMVertices() {
        long jHullDesc_mVertices_get = LinearMathJNI.HullDesc_mVertices_get(this.swigCPtr, this);
        if (jHullDesc_mVertices_get == 0) {
            return null;
        }
        return new btVector3(jHullDesc_mVertices_get, false);
    }

    public void setMVertexStride(long j) {
        LinearMathJNI.HullDesc_mVertexStride_set(this.swigCPtr, this, j);
    }

    public long getMVertexStride() {
        return LinearMathJNI.HullDesc_mVertexStride_get(this.swigCPtr, this);
    }

    public void setMNormalEpsilon(float f) {
        LinearMathJNI.HullDesc_mNormalEpsilon_set(this.swigCPtr, this, f);
    }

    public float getMNormalEpsilon() {
        return LinearMathJNI.HullDesc_mNormalEpsilon_get(this.swigCPtr, this);
    }

    public void setMMaxVertices(long j) {
        LinearMathJNI.HullDesc_mMaxVertices_set(this.swigCPtr, this, j);
    }

    public long getMMaxVertices() {
        return LinearMathJNI.HullDesc_mMaxVertices_get(this.swigCPtr, this);
    }

    public void setMMaxFaces(long j) {
        LinearMathJNI.HullDesc_mMaxFaces_set(this.swigCPtr, this, j);
    }

    public long getMMaxFaces() {
        return LinearMathJNI.HullDesc_mMaxFaces_get(this.swigCPtr, this);
    }
}
