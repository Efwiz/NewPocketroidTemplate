package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btAngleCompareFunc extends BulletBase {
    private long swigCPtr;

    protected btAngleCompareFunc(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btAngleCompareFunc(long j, boolean z) {
        this("btAngleCompareFunc", j, z);
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

    public static long getCPtr(btAngleCompareFunc btanglecomparefunc) {
        if (btanglecomparefunc == null) {
            return 0L;
        }
        return btanglecomparefunc.swigCPtr;
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
                LinearMathJNI.delete_btAngleCompareFunc(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setAnchor(btVector3 btvector3) {
        LinearMathJNI.btAngleCompareFunc_anchor_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAnchor() {
        long jBtAngleCompareFunc_anchor_get = LinearMathJNI.btAngleCompareFunc_anchor_get(this.swigCPtr, this);
        if (jBtAngleCompareFunc_anchor_get == 0) {
            return null;
        }
        return new btVector3(jBtAngleCompareFunc_anchor_get, false);
    }

    public btAngleCompareFunc(Vector3 vector3) {
        this(LinearMathJNI.new_btAngleCompareFunc(vector3), true);
    }

    public boolean operatorFunctionCall(GrahamVector3 grahamVector3, GrahamVector3 grahamVector4) {
        return LinearMathJNI.btAngleCompareFunc_operatorFunctionCall(this.swigCPtr, this, GrahamVector3.getCPtr(grahamVector3), grahamVector3, GrahamVector3.getCPtr(grahamVector4), grahamVector4);
    }
}
