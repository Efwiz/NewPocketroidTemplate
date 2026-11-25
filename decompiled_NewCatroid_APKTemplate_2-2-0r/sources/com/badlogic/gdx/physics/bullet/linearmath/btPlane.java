package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btPlane extends BulletBase {
    private long swigCPtr;

    protected btPlane(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPlane(long j, boolean z) {
        this("btPlane", j, z);
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

    public static long getCPtr(btPlane btplane) {
        if (btplane == null) {
            return 0L;
        }
        return btplane.swigCPtr;
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
                LinearMathJNI.delete_btPlane(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setNormal(btVector3 btvector3) {
        LinearMathJNI.btPlane_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getNormal() {
        long jBtPlane_normal_get = LinearMathJNI.btPlane_normal_get(this.swigCPtr, this);
        if (jBtPlane_normal_get == 0) {
            return null;
        }
        return new btVector3(jBtPlane_normal_get, false);
    }

    public void setDist(float f) {
        LinearMathJNI.btPlane_dist_set(this.swigCPtr, this, f);
    }

    public float getDist() {
        return LinearMathJNI.btPlane_dist_get(this.swigCPtr, this);
    }

    public btPlane(Vector3 vector3, float f) {
        this(LinearMathJNI.new_btPlane__SWIG_0(vector3, f), true);
    }

    public btPlane() {
        this(LinearMathJNI.new_btPlane__SWIG_1(), true);
    }
}
