package com.badlogic.gdx.physics.bullet.extras;

/* JADX INFO: loaded from: classes8.dex */
public class DillCreator extends MultiBodyTreeCreator {
    private long swigCPtr;

    protected DillCreator(String str, long j, boolean z) {
        super(str, ExtrasJNI.DillCreator_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public DillCreator(long j, boolean z) {
        this("DillCreator", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.MultiBodyTreeCreator, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(ExtrasJNI.DillCreator_SWIGUpcast(j), z);
    }

    public static long getCPtr(DillCreator dillCreator) {
        if (dillCreator == null) {
            return 0L;
        }
        return dillCreator.swigCPtr;
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
                ExtrasJNI.delete_DillCreator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public DillCreator(int i) {
        this(ExtrasJNI.new_DillCreator(i), true);
    }
}
