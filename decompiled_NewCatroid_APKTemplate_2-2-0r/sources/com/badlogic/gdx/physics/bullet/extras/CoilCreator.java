package com.badlogic.gdx.physics.bullet.extras;

/* JADX INFO: loaded from: classes8.dex */
public class CoilCreator extends MultiBodyTreeCreator {
    private long swigCPtr;

    protected CoilCreator(String str, long j, boolean z) {
        super(str, ExtrasJNI.CoilCreator_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public CoilCreator(long j, boolean z) {
        this("CoilCreator", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.MultiBodyTreeCreator, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(ExtrasJNI.CoilCreator_SWIGUpcast(j), z);
    }

    public static long getCPtr(CoilCreator coilCreator) {
        if (coilCreator == null) {
            return 0L;
        }
        return coilCreator.swigCPtr;
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
                ExtrasJNI.delete_CoilCreator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public CoilCreator(int i) {
        this(ExtrasJNI.new_CoilCreator(i), true);
    }
}
