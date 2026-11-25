package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class InternalTickCallback extends BulletBase {
    private long swigCPtr;

    protected InternalTickCallback(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public InternalTickCallback(long j, boolean z) {
        this("InternalTickCallback", j, z);
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

    public static long getCPtr(InternalTickCallback internalTickCallback) {
        if (internalTickCallback == null) {
            return 0L;
        }
        return internalTickCallback.swigCPtr;
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
                DynamicsJNI.delete_InternalTickCallback(this.swigCPtr);
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
        DynamicsJNI.InternalTickCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        DynamicsJNI.InternalTickCallback_change_ownership(this, this.swigCPtr, true);
    }

    public InternalTickCallback(btDynamicsWorld btdynamicsworld, boolean z) {
        this(DynamicsJNI.new_InternalTickCallback__SWIG_0(btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld, z), true);
        DynamicsJNI.InternalTickCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public InternalTickCallback(btDynamicsWorld btdynamicsworld) {
        this(DynamicsJNI.new_InternalTickCallback__SWIG_1(btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld), true);
        DynamicsJNI.InternalTickCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public InternalTickCallback() {
        this(DynamicsJNI.new_InternalTickCallback__SWIG_2(), true);
        DynamicsJNI.InternalTickCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public void onInternalTick(btDynamicsWorld btdynamicsworld, float f) {
        if (getClass() == InternalTickCallback.class) {
            DynamicsJNI.InternalTickCallback_onInternalTick(this.swigCPtr, this, btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld, f);
        } else {
            DynamicsJNI.InternalTickCallback_onInternalTickSwigExplicitInternalTickCallback(this.swigCPtr, this, btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld, f);
        }
    }

    public void detach() {
        DynamicsJNI.InternalTickCallback_detach__SWIG_0(this.swigCPtr, this);
    }

    public void attach(btDynamicsWorld btdynamicsworld, boolean z) {
        DynamicsJNI.InternalTickCallback_attach__SWIG_0(this.swigCPtr, this, btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld, z);
    }

    public void attach() {
        DynamicsJNI.InternalTickCallback_attach__SWIG_1(this.swigCPtr, this);
    }

    public static void detach(btDynamicsWorld btdynamicsworld, boolean z) {
        DynamicsJNI.InternalTickCallback_detach__SWIG_1(btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld, z);
    }
}
