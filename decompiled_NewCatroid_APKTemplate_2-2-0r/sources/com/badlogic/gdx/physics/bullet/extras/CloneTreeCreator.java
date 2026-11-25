package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.physics.bullet.inversedynamics.MultiBodyTree;

/* JADX INFO: loaded from: classes8.dex */
public class CloneTreeCreator extends MultiBodyTreeCreator {
    private long swigCPtr;

    protected CloneTreeCreator(String str, long j, boolean z) {
        super(str, ExtrasJNI.CloneTreeCreator_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public CloneTreeCreator(long j, boolean z) {
        this("CloneTreeCreator", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.MultiBodyTreeCreator, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(ExtrasJNI.CloneTreeCreator_SWIGUpcast(j), z);
    }

    public static long getCPtr(CloneTreeCreator cloneTreeCreator) {
        if (cloneTreeCreator == null) {
            return 0L;
        }
        return cloneTreeCreator.swigCPtr;
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
                ExtrasJNI.delete_CloneTreeCreator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public CloneTreeCreator(MultiBodyTree multiBodyTree) {
        this(ExtrasJNI.new_CloneTreeCreator(MultiBodyTree.getCPtr(multiBodyTree), multiBodyTree), true);
    }
}
