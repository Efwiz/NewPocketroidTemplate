package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class CustomActionInterface extends btActionInterface {
    private long swigCPtr;

    protected CustomActionInterface(String str, long j, boolean z) {
        super(str, DynamicsJNI.CustomActionInterface_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public CustomActionInterface(long j, boolean z) {
        this("CustomActionInterface", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.CustomActionInterface_SWIGUpcast(j), z);
    }

    public static long getCPtr(CustomActionInterface customActionInterface) {
        if (customActionInterface == null) {
            return 0L;
        }
        return customActionInterface.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btActionInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_CustomActionInterface(this.swigCPtr);
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
        DynamicsJNI.CustomActionInterface_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        DynamicsJNI.CustomActionInterface_change_ownership(this, this.swigCPtr, true);
    }

    public void updateAction(float f) {
        DynamicsJNI.CustomActionInterface_updateAction(this.swigCPtr, this, f);
    }

    public void debugDraw() {
        DynamicsJNI.CustomActionInterface_debugDraw(this.swigCPtr, this);
    }

    public CustomActionInterface() {
        this(DynamicsJNI.new_CustomActionInterface(), true);
        DynamicsJNI.CustomActionInterface_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
