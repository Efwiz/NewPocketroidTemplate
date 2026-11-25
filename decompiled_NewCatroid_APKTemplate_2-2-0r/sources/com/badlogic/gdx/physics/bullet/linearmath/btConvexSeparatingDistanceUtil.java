package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexSeparatingDistanceUtil extends BulletBase {
    private long swigCPtr;

    protected btConvexSeparatingDistanceUtil(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConvexSeparatingDistanceUtil(long j, boolean z) {
        this("btConvexSeparatingDistanceUtil", j, z);
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

    public static long getCPtr(btConvexSeparatingDistanceUtil btconvexseparatingdistanceutil) {
        if (btconvexseparatingdistanceutil == null) {
            return 0L;
        }
        return btconvexseparatingdistanceutil.swigCPtr;
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
                LinearMathJNI.delete_btConvexSeparatingDistanceUtil(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btConvexSeparatingDistanceUtil(float f, float f2) {
        this(LinearMathJNI.new_btConvexSeparatingDistanceUtil(f, f2), true);
    }

    public float getConservativeSeparatingDistance() {
        return LinearMathJNI.btConvexSeparatingDistanceUtil_getConservativeSeparatingDistance(this.swigCPtr, this);
    }

    public void updateSeparatingDistance(Matrix4 matrix4, Matrix4 matrix5) {
        LinearMathJNI.btConvexSeparatingDistanceUtil_updateSeparatingDistance(this.swigCPtr, this, matrix4, matrix5);
    }

    public void initSeparatingDistance(Vector3 vector3, float f, Matrix4 matrix4, Matrix4 matrix5) {
        LinearMathJNI.btConvexSeparatingDistanceUtil_initSeparatingDistance(this.swigCPtr, this, vector3, f, matrix4, matrix5);
    }
}
