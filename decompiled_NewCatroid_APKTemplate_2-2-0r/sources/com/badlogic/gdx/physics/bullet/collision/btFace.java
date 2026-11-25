package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btFace extends BulletBase {
    private long swigCPtr;

    protected btFace(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btFace(long j, boolean z) {
        this("btFace", j, z);
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

    public static long getCPtr(btFace btface) {
        if (btface == null) {
            return 0L;
        }
        return btface.swigCPtr;
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
                CollisionJNI.delete_btFace(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setIndices(SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        CollisionJNI.btFace_indices_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_int_t getIndices() {
        long jBtFace_indices_get = CollisionJNI.btFace_indices_get(this.swigCPtr, this);
        if (jBtFace_indices_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_int_t(jBtFace_indices_get, false);
    }

    public void setPlane(float[] fArr) {
        CollisionJNI.btFace_plane_set(this.swigCPtr, this, fArr);
    }

    public float[] getPlane() {
        return CollisionJNI.btFace_plane_get(this.swigCPtr, this);
    }

    public btFace() {
        this(CollisionJNI.new_btFace(), true);
    }
}
