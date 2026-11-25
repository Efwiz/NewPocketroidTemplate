package com.badlogic.gdx.physics.bullet.extras;

/* JADX INFO: loaded from: classes8.dex */
public class RandomTreeCreator extends MultiBodyTreeCreator {
    private long swigCPtr;

    protected RandomTreeCreator(String str, long j, boolean z) {
        super(str, ExtrasJNI.RandomTreeCreator_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public RandomTreeCreator(long j, boolean z) {
        this("RandomTreeCreator", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.MultiBodyTreeCreator, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(ExtrasJNI.RandomTreeCreator_SWIGUpcast(j), z);
    }

    public static long getCPtr(RandomTreeCreator randomTreeCreator) {
        if (randomTreeCreator == null) {
            return 0L;
        }
        return randomTreeCreator.swigCPtr;
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
                ExtrasJNI.delete_RandomTreeCreator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public RandomTreeCreator(int i, boolean z) {
        this(ExtrasJNI.new_RandomTreeCreator__SWIG_0(i, z), true);
    }

    public RandomTreeCreator(int i) {
        this(ExtrasJNI.new_RandomTreeCreator__SWIG_1(i), true);
    }
}
