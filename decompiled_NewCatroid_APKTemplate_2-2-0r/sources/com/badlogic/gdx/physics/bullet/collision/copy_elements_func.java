package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class copy_elements_func extends BulletBase {
    private long swigCPtr;

    protected copy_elements_func(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public copy_elements_func(long j, boolean z) {
        this("copy_elements_func", j, z);
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

    public static long getCPtr(copy_elements_func copy_elements_funcVar) {
        if (copy_elements_funcVar == null) {
            return 0L;
        }
        return copy_elements_funcVar.swigCPtr;
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
                CollisionJNI.delete_copy_elements_func(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public copy_elements_func() {
        this(CollisionJNI.new_copy_elements_func(), true);
    }
}
