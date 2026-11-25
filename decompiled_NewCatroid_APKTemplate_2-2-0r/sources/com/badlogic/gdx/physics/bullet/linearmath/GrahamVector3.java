package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class GrahamVector3 extends btVector3 {
    private long swigCPtr;

    protected GrahamVector3(String str, long j, boolean z) {
        super(str, LinearMathJNI.GrahamVector3_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public GrahamVector3(long j, boolean z) {
        this("GrahamVector3", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btVector3, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(LinearMathJNI.GrahamVector3_SWIGUpcast(j), z);
    }

    public static long getCPtr(GrahamVector3 grahamVector3) {
        if (grahamVector3 == null) {
            return 0L;
        }
        return grahamVector3.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btVector3, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btVector3, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                LinearMathJNI.delete_GrahamVector3(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public GrahamVector3(Vector3 vector3, int i) {
        this(LinearMathJNI.new_GrahamVector3(vector3, i), true);
    }

    public void setAngle(float f) {
        LinearMathJNI.GrahamVector3_angle_set(this.swigCPtr, this, f);
    }

    public float getAngle() {
        return LinearMathJNI.GrahamVector3_angle_get(this.swigCPtr, this);
    }

    public void setOrgIndex(int i) {
        LinearMathJNI.GrahamVector3_orgIndex_set(this.swigCPtr, this, i);
    }

    public int getOrgIndex() {
        return LinearMathJNI.GrahamVector3_orgIndex_get(this.swigCPtr, this);
    }
}
