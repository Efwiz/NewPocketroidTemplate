package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btMotionState extends BulletBase {
    private long swigCPtr;

    protected btMotionState(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMotionState(long j, boolean z) {
        this("btMotionState", j, z);
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

    public static long getCPtr(btMotionState btmotionstate) {
        if (btmotionstate == null) {
            return 0L;
        }
        return btmotionstate.swigCPtr;
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
                LinearMathJNI.delete_btMotionState(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        LinearMathJNI.btMotionState_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        LinearMathJNI.btMotionState_change_ownership(this, this.swigCPtr, true);
    }

    public void getWorldTransform(Matrix4 matrix4) {
        LinearMathJNI.btMotionState_getWorldTransform(this.swigCPtr, this, matrix4);
    }

    public void setWorldTransform(Matrix4 matrix4) {
        LinearMathJNI.btMotionState_setWorldTransform(this.swigCPtr, this, matrix4);
    }

    public btMotionState() {
        this(LinearMathJNI.new_btMotionState(), true);
        LinearMathJNI.btMotionState_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
