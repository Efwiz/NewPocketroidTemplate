package com.badlogic.gdx.physics.bullet.extras;

/* JADX INFO: loaded from: classes8.dex */
public class SimpleTreeCreator extends MultiBodyTreeCreator {
    private long swigCPtr;

    protected SimpleTreeCreator(String str, long j, boolean z) {
        super(str, ExtrasJNI.SimpleTreeCreator_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public SimpleTreeCreator(long j, boolean z) {
        this("SimpleTreeCreator", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.MultiBodyTreeCreator, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(ExtrasJNI.SimpleTreeCreator_SWIGUpcast(j), z);
    }

    public static long getCPtr(SimpleTreeCreator simpleTreeCreator) {
        if (simpleTreeCreator == null) {
            return 0L;
        }
        return simpleTreeCreator.swigCPtr;
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
                ExtrasJNI.delete_SimpleTreeCreator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public SimpleTreeCreator(int i) {
        this(ExtrasJNI.new_SimpleTreeCreator(i), true);
    }
}
