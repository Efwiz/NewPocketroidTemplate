package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.physics.bullet.dynamics.btMultiBody;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyTreeCreator extends MultiBodyTreeCreator {
    private long swigCPtr;

    protected btMultiBodyTreeCreator(String str, long j, boolean z) {
        super(str, ExtrasJNI.btMultiBodyTreeCreator_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultiBodyTreeCreator(long j, boolean z) {
        this("btMultiBodyTreeCreator", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.MultiBodyTreeCreator, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(ExtrasJNI.btMultiBodyTreeCreator_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultiBodyTreeCreator btmultibodytreecreator) {
        if (btmultibodytreecreator == null) {
            return 0L;
        }
        return btmultibodytreecreator.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.MultiBodyTreeCreator, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.MultiBodyTreeCreator, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ExtrasJNI.delete_btMultiBodyTreeCreator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btMultiBodyTreeCreator() {
        this(ExtrasJNI.new_btMultiBodyTreeCreator(), true);
    }

    public int createFromBtMultiBody(btMultiBody btmultibody, boolean z) {
        return ExtrasJNI.btMultiBodyTreeCreator_createFromBtMultiBody__SWIG_0(this.swigCPtr, this, btMultiBody.getCPtr(btmultibody), btmultibody, z);
    }

    public int createFromBtMultiBody(btMultiBody btmultibody) {
        return ExtrasJNI.btMultiBodyTreeCreator_createFromBtMultiBody__SWIG_1(this.swigCPtr, this, btMultiBody.getCPtr(btmultibody), btmultibody);
    }
}
